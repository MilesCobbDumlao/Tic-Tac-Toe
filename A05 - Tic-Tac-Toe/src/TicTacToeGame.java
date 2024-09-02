import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.*;
public class TicTacToeGame extends JFrame{

	private JButton [][] ticTacToe = new JButton [3][3]; //Creates a double array of buttons
	private MyListener myListener = new MyListener(); 
	private Font myFont = new Font("Arial", Font.BOLD, 120); //I used Arial font because I liked the look of the X's and O's, Made the font bold and 120 to make it clearly visible on the grid
	private int userCounter = 0;
	private int compCounter = 0;
	private boolean winner= false;
	public static void main(String[] args) {
		new TicTacToeGame();

	}
	
	public TicTacToeGame() {
		this.setVisible(true); // Makes window visible
		this.setSize(1000,1000); // Makes a 1000 x 1000 pixel window, Wanted to make window a square and easily visible
		this.setTitle("Tic-Tac-Toe"); //Sets title of window to Tic-Tac-Toe
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Ends program when window is closed
		createButtons(); //Creates buttons for game
		this.setLayout(new GridLayout(3,3)); //Creates a 3 x 3 grid layout, I used a grid layout because Tic-Tac-Toe has rows and columns and the grid layout places the buttons in rows and columns
	}
	
	public void createButtons() {
		for(int i = 0; i<ticTacToe.length; i++) {
			for(int j=0; j<ticTacToe.length; j++) { //Iterates through array
				ticTacToe[i][j] = new JButton(); //Creates a button 
				ticTacToe[i][j].addActionListener(myListener); //Makes buttons click-able
				ticTacToe[i][j].setFont(myFont); 
				this.add(ticTacToe[i][j]); //Adds a button to grid 
				ticTacToe[i][j].setVisible(true); //Makes array of buttons visible
			}
		}
	}
	
	private class MyListener implements ActionListener{

	
		public void actionPerformed(ActionEvent e) {
			for(int i = 0; i<ticTacToe.length; i++) {
				for(int j=0; j<ticTacToe.length; j++) { //Iterates through array
					if(e.getSource() == ticTacToe[i][j]) { //If a button is clicked
						ticTacToe[i][j].setText("X"); //Makes an X on clicked button
						ticTacToe[i][j].setEnabled(false); //Disables button
						verticalWin(); //Checks if there's a winner after user makes a move
						if(moveAvailable() && winner == false) { //Computer makes a move if there's moves available and no winner
							computerMove();
							verticalWin(); //Checks if there's a winner after computer makes a move
						}
					}
				}
			}
			
		}
		
	}
	
	public void computerMove () {
		int rand = (int)(Math.random() * ticTacToe.length);
		int rand2 = (int)(Math.random() * ticTacToe.length);
		
		do {
			rand = (int)(Math.random() * ticTacToe.length); 
			rand2 = (int)(Math.random() * ticTacToe.length);
			
		}
		while(!ticTacToe[rand][rand2].isEnabled()); //Creates random numbers in a range of 0-2 until an index in the double array that's enabled is found 
		
		ticTacToe[rand][rand2].setText("O"); //Makes an O on index that's enabled
		ticTacToe[rand][rand2].setEnabled(false); //Disables button after O
	}
	
	public void verticalWin() {
		//Vertical Win
		
		for(int i = 0; i<ticTacToe.length; i++) {
			for(int j = 0; j<ticTacToe.length; j++) {
				if(ticTacToe[j][i].getText() == "X") { //Checks grid by columns for X's
					userCounter++; //Adds to user counter
				}
				else if(ticTacToe[j][i].getText() == "O") { //Checks grid by columns for O's
					compCounter++; //Adds to computer counter
				}
				if(userCounter == ticTacToe.length) { //If user counter gets to 3 
					JOptionPane.showMessageDialog(new JFrame(), "YOU V WON! Results stored to Tfile.txt ", "WINNER!", JOptionPane.PLAIN_MESSAGE); // Makes a new window saying user wins
					winner = true; 
					disable(); //Disables remaining buttons 
					try {
						createOutputFile(); //Prints game results to a file 
					}
					catch(IOException IOE){
						System.out.println("File Error");
					}
					return;
				}
				else if(compCounter == ticTacToe.length) { //If computer counter gets to 3 
					JOptionPane.showMessageDialog(new JFrame(), "YOU V LOST:( Results stored to Tfile.txt", "LOSER!", JOptionPane.PLAIN_MESSAGE); //Makes a new window saying computer wins
					winner  = true;
					disable(); //Disables remaining buttons 
					try {
						createOutputFile(); //Prints game results to a file 
					}
					catch(IOException IOE){
						System.out.println("File Error");
					}
					return;
				}
				
			}
			userCounter = 0; //Resets user counter if there isn't 3 X's in a column
			compCounter = 0; //Resets computer counter if there isn't 3 O's in a column
			
		}
		horizontalWin(); //Checks if there's a horizontal win
	}
	
	public void horizontalWin() {
		for(int i = 0; i<ticTacToe.length; i++) {
			for(int j = 0; j<ticTacToe.length; j++) {
				if(ticTacToe[i][j].getText() == "X") { //Checks grid by rows for X's
					userCounter++; //Adds to user counter
				}
				else if(ticTacToe[i][j].getText() == "O") { //Checks grid by rows for O's
					compCounter++; //Adds to computer counter
				}
				if(userCounter == ticTacToe.length) { //If user get's 3 X's in one row
					JOptionPane.showMessageDialog(new JFrame(), "YOU H WON! Results stored to Tfile.txt", "WINNER!", JOptionPane.PLAIN_MESSAGE); //Makes a new window saying user wins
					winner  = true;
					disable(); //disables remaining buttons
					try {
						createOutputFile(); //Prints game results to a file
					}
					catch(IOException IOE){
						System.out.println("File Error");
					}
					return;
				}
				else if(compCounter == ticTacToe.length) {
					JOptionPane.showMessageDialog(new JFrame(), "YOU H LOST:( Results stored to Tfile.txt", "LOSER!", JOptionPane.PLAIN_MESSAGE); //Makes a new window saying computer sins
					winner  = true;
					disable(); //disables remaining buttons
					try {
						createOutputFile();
					}
					catch(IOException IOE){
						System.out.println("File Error");
					}
					return;
				}
				
			}
			userCounter = 0; //Resets user counter if there isn't 3 X's in a row
			compCounter = 0; //Resets computer counter if there isn't 3 O's in a row
		}
		diagonal(); //Checks if there's a diagonal win 
	}
	
	public void diagonal() {
		//Left Diagonal Win 
		for(int i = 0; i<ticTacToe.length; i++) { 
			if(ticTacToe[i][i].getText() == "X") { //Checks for X's at [0][0], [1][1], and [2][2] of Tic-Tac-Toe array
				userCounter++; //Adds to user counter
			}
			else if(ticTacToe[i][i].getText() == "O") { //Checks for O's at [0][0], [1][1], and [2][2] of Tic-Tac-Toe array
				compCounter++; //Adds to computer counter
			}
		}
		if(userCounter == ticTacToe.length) { //If user gets 3 X's in a diagonal 
			JOptionPane.showMessageDialog(new JFrame(), "YOU D WON! Results stored to Tfile.txt", "WINNER!", JOptionPane.PLAIN_MESSAGE); //Makes a new window saying user wins
			winner  = true;
			disable(); //Disables remaining buttons 
			try {
				createOutputFile(); //Prints game results to a file 
			}
			catch(IOException IOE){
				System.out.println("File Error");
			}
			return;
		}
		else if(compCounter == ticTacToe.length) { //If computer gets 3 O's in a diagonal 
			JOptionPane.showMessageDialog(new JFrame(), "YOU D LOST:( Results stored to Tfile.txt", "LOSER!", JOptionPane.PLAIN_MESSAGE);
			winner  = true;
			disable(); //Disables remaining buttons 
			try {
				createOutputFile(); //Prints game results to a file 
			}
			catch(IOException IOE){
				System.out.println("File Error");
			}
			return;
		}
		else {
			userCounter = 0; //Resets user counter if there isn't 3 X's in a diagonal
			compCounter = 0; //Resets computer counter if there isn't 3 O's in a diagonal 
		}
		//Right diagonal win 
		for(int i = 0; i<ticTacToe.length; i++) {
			if(ticTacToe[i][(ticTacToe.length -1)-i].getText() == "X") { //Checks for X's at [0][2], [1][1], and [2][0] of array of Tic-Tac-Toe array
				userCounter++; //Adds to user counter
			}
			else if(ticTacToe[i][(ticTacToe.length -1)-i].getText() == "O") { //Checks for O's at [0][2], [1][1], and [2][0] of Tic-Tac-Toe array 
				compCounter++; //Adds to computer counter 
			}
		}
		if(userCounter == ticTacToe.length) { //If user get's 3 X's in a diagonal 
			JOptionPane.showMessageDialog(new JFrame(), "YOU D WON! Results stored to Tfile.txt", "WINNER!", JOptionPane.PLAIN_MESSAGE); //Makes a new window saying user wins
			winner  = true;
			disable(); //Disables remaining buttons 
			try {
				createOutputFile(); //Prints game results to a file 
			}
			catch(IOException IOE){
				System.out.println("File Error");
			}
			return;
		}
		else if(compCounter == ticTacToe.length) { //If computer get's 3 O's in a diagonal 
			JOptionPane.showMessageDialog(new JFrame(), "YOU D LOST:( Results stored to Tfile.txt", "LOSER!", JOptionPane.PLAIN_MESSAGE); //Makes a new window saying computer wins
			winner  = true;
			disable(); //Disables remaining buttons 
			try {
				createOutputFile(); //Prints game results to a file
			}
			catch(IOException IOE){
				System.out.println("File Error");
			}
			return;
		}
		else {
			userCounter = 0; //Resets user counter if there isn't 3 X's in a diagonal 
			compCounter = 0; //Resets computer counter if there isn't 3 O's in a diagonal 
		}
	}
	
	public boolean moveAvailable() {
		for(int i = 0; i<ticTacToe.length; i++) {
			for(int j = 0; j<ticTacToe.length; j++) { //Iterates through array
				if(ticTacToe[i][j].getText().equals("")) { //If there isn't an X or O in the array 
					return true; //There is a move available 
				}
			}
		}
		return false; //No moves available 
	}
	public void disable() {
		for(int i = 0; i<ticTacToe.length; i++) {
			for(int j = 0; j<ticTacToe.length; j++) { //Iterates through array 
				ticTacToe[i][j].setEnabled(false); //Disables button in array 
			}
		}
		
	}
	
	public void createOutputFile() throws IOException {
		FileWriter fw = new FileWriter(new File("Tfile.txt")); //Creates a new text file 
		BufferedWriter  bw = new BufferedWriter(fw);
		PrintWriter pw = new PrintWriter(bw);
		String winner = "";
		if(userCounter == ticTacToe.length) { //If user wins 
			winner = "TicTacToe Game, winner: the user"; 
		}
		else if(compCounter == ticTacToe.length) { //If computer wins 
			winner = "TicTacToe Game, winner: the computer";
		}
		pw.println(winner);
		pw.close();
	}
}
