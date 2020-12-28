package org.kodluyoruz;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class SosGame {
	
	private int boardSize;
	private char[][] board;
	private boolean isGameEnd = false;
	private int playerPoint;
	private int computerPoint;
	
	private int[] indexOfLastInput = new int[2];	
	private HashMap<Integer,Integer> emptyCoordinates = new HashMap<Integer,Integer>();
	
	private static final int playerFlag = 1;
	private static final int computerFlag = -1;
	
	private char computerChar;
	private char playerChar;
	
	private int playFirst;
	private int playSecond;
	
	Scanner sc = new Scanner(System.in);
	Random rand = new Random();


	// Constructor
	public SosGame(int boardSize) {
		this.boardSize = boardSize + 2;
		board = new char[this.boardSize][this.boardSize];
		isGameEnd = false;
		this.playerPoint = 0;
		this.computerPoint = 0;
	
    	createBoard();
    	printBoard();
    	decideCharOfPlayers();
    	whoWillPlayFirst();
    	
	}
	
	public void playGame() {		
		while(!isGameEnd) {		
    		move(playFirst);
    		printBoard();
    		while(isThereSOS(playFirst) && !isGameEnd) {
    			printBoard();
    			printPoints();
    			move(playFirst);
    			printBoard();   			
    		}
    		if(!isGameEnd) {
    			move(playSecond);
    			printBoard();
    		}
    		while(isThereSOS(playSecond) && !isGameEnd) {
    			printBoard();
    			printPoints();
    			move(playSecond);
    			printBoard();  			
    		}
    	}
    	
    	System.out.println("END of the GAME");
    	printPoints();
    	
    	if(playerPoint > computerPoint) System.out.println("PLAYER WINS");
    	else if(playerPoint == computerPoint) System.out.println("ODD");
    	else System.out.println("COMPUTER WINS");
	}
	
	private void move(int who) {
		if(who == playerFlag) {
			playerMove();
		}
		if(who == computerFlag) {
			computerMove();
		}
	}
	
	private void printPoints() {
		System.out.println("PLAYER POINT = " + playerPoint);
		System.out.println("COMPUTER POINT = " + computerPoint);
	}
	
	private boolean isThereSOS(int flag) {
		int currX = indexOfLastInput[0];
		int currY = indexOfLastInput[1];
		boolean res = false;
		
		if(currX+2 <= boardSize-1 && ("" + board[currX][currY] + board[currX+1][currY] + board[currX+2][currY]).toUpperCase().equals("SOS")) {
			this.makeLowerCaseAndUpdatePoints(currX, currY, currX+1, currY, currX+2, currY, flag);
			res = true;
		}
		
		if(currX-1 >= 0 && currX+1 <= boardSize-1 && ("" + board[currX-1][currY] + board[currX][currY] + board[currX+1][currY]).toUpperCase().equals("SOS")) {
			this.makeLowerCaseAndUpdatePoints(currX-1, currY, currX, currY, currX+1, currY, flag);
			res = true;
		}
		
		if(currX-2 >= 0 && ("" + board[currX-2][currY] + board[currX-1][currY] + board[currX][currY]).toUpperCase().equals("SOS")) {
			this.makeLowerCaseAndUpdatePoints(currX-2, currY, currX-1, currY, currX, currY, flag);
			res = true;
		}
		
		if(currY+2 <= boardSize-1 && ("" + board[currX][currY] + board[currX][currY+1] + board[currX][currY+2]).toUpperCase().equals("SOS")) {
			this.makeLowerCaseAndUpdatePoints(currX, currY, currX, currY+1, currX, currY+2, flag);
			res = true;
		}
		
		if(currY-1 >= 0 && currY+1 <= boardSize-1 && ("" + board[currX][currY-1] + board[currX][currY] + board[currX][currY+1]).toUpperCase().equals("SOS")) {
			this.makeLowerCaseAndUpdatePoints(currX, currY-1, currX, currY, currX, currY+1, flag);
			res = true;
		}
		
		if(currY-2 >= 0 && ("" + board[currX][currY-2] + board[currX][currY-1] + board[currX][currY]).toUpperCase().equals("SOS")) {
			this.makeLowerCaseAndUpdatePoints(currX, currY-2, currX, currY-1, currX, currY, flag);
			res = true;
		}
		
		if(currX+2 <= boardSize-1 && currY+2 <= boardSize-1 && ("" + board[currX][currY] + board[currX+1][currY+1] + board[currX+2][currY+2]).toUpperCase().equals("SOS")) {
			this.makeLowerCaseAndUpdatePoints(currX, currY, currX+1, currY+1, currX+2, currY+2, flag);
			res = true;
		}
		
		if(currX+1 <= boardSize-1 && currY+1 <= boardSize-1 && currX-1 >= 0 && currY-1 >= 0 && 
		  ("" + board[currX-1][currY-1] + board[currX][currY] + board[currX+1][currY+1]).toUpperCase().equals("SOS")) {
			
			this.makeLowerCaseAndUpdatePoints(currX-1, currY-1, currX, currY, currX+1, currY+1, flag);
			res = true;
		}
		
		if(currX-2 >=0 && currY-2 >= 0 && 
		  ("" + board[currX-2][currY-2] + board[currX-1][currY-1] + board[currX][currY]).toUpperCase().equals("SOS")) {
			
			this.makeLowerCaseAndUpdatePoints(currX-2, currY-2, currX-1, currY-1, currX, currY, flag);
			res = true;
		}
		
		if(currX+2 <= boardSize-1 && currY-2 >= 0 && 
		  ("" + board[currX][currY] + board[currX+1][currY-1] + board[currX+2][currY-2]).toUpperCase().equals("SOS")) {
			
			this.makeLowerCaseAndUpdatePoints(currX, currY, currX+1, currY-1, currX+2, currY-2, flag);
			res = true;
		}
		
		if(currX+1 <= boardSize-1 && currX-1 >= 0 && currY+1 <= boardSize-1 && currY-1 >= 0 &&
		  ("" + board[currX-1][currY+1] + board[currX][currY] + board[currX+1][currY-1]).toUpperCase().equals("SOS")) {
			
			this.makeLowerCaseAndUpdatePoints(currX-1, currY+1, currX, currY, currX+1, currY-1, flag);
			res = true;
		}
		
		if(currX-2 >= 0 && currY+2 <= boardSize-1 && 
		  ("" + board[currX-2][currY+2] + board[currX-1][currY+1] + board[currX][currY]).toUpperCase().equals("SOS")) {
			
			this.makeLowerCaseAndUpdatePoints(currX-2, currY+2, currX-1, currY+1, currX, currY, flag);
			res = true;
		}
				
		return res;
	}
	
	private void makeLowerCaseAndUpdatePoints(int a, int b, int c, int d, int e, int f, int flag) {
		board[a][b] = Character.toLowerCase(board[a][b]);
		board[c][d] = Character.toLowerCase(board[c][d]);
		board[e][f] = Character.toLowerCase(board[e][f]);
		if(flag == playerFlag) {
			playerPoint++;
		}
		else {
			computerPoint++;
		}
	}
	
	private void printBoard() {
    	for(int i=0; i<boardSize; i++) {
    		for(int j=0; j<boardSize; j++) {
    			System.out.print(board[i][j] + " ");
    		}
    		System.out.println();
    	}
    }
	
	private void createBoard() {
    	for(int i=0; i<boardSize; i++) {
    		for(int j=0; j<boardSize; j++) {
    			if(i==0 || j==0 || i==boardSize-1 || j==boardSize-1) {
    				if(i==0 && j!=0 && j!=boardSize-1) {
    					board[i][j] = (char)(j+'0');
    					continue;
    				}
    				if(j==0 && i!=0 && i!=boardSize-1) {
    					board[i][j] = (char)(i+'0');
    					continue;
    				}  				   				
    				board[i][j] = '*';
    				continue;
    			}
    			board[i][j] = ' ';		
    		}
    	}
        System.out.println((boardSize-2) + " * " + (boardSize-2) + " Board Created !!!" );
    }
	
	private void playerMove() {
    	System.out.println("Enter your move (x y)");
		int x = sc.nextInt();
		int y = sc.nextInt();
		//char c = sc.next().charAt(0);
		
		while(board[x][y] != ' ') {
			System.out.println("This square not empty");
			System.out.println("Enter Again !!");
			x = sc.nextInt();
			y = sc.nextInt();		
		}
		board[x][y] = playerChar;
		indexOfLastInput[0] = x;
    	indexOfLastInput[1] = y;
    	
    	emptyCoordinates();

    }
	
	private void computerMove() {
    	
		emptyCoordinates();
    	int randomNo = rand.nextInt(emptyCoordinates.size());
    	int i = 0;
    	int computerX = 0;
    	int computerY = 0;
    	for(Integer x : emptyCoordinates.keySet()) {
    		if(i == randomNo) {
    			computerX = x;
    			computerY = emptyCoordinates.get(x);
    		}
    		i++;
    	}
    	System.out.println("Computer Decided to Move to " + computerX + "," + computerY);
    	board[computerX][computerY] = computerChar;
    	indexOfLastInput[0] = computerX;
    	indexOfLastInput[1] = computerY;
    	emptyCoordinates();
    }
	
	// This Method holds empty Coordinates of matrix in hashmap.
	private void emptyCoordinates() {
		if(emptyCoordinates.size() != 0)
			emptyCoordinates.clear();
		
		for(int i=0; i<boardSize; i++) {
    		for(int j=0; j<boardSize; j++) {
    			if(board[i][j] == ' ') {
    				emptyCoordinates.put(i, j);
    			}
    		}
		}
		
		if(emptyCoordinates.size() == 0) isGameEnd = true;
	}
	
	private void whoWillPlayFirst() {
		if(rand.nextInt(10) % 2 == 0) {
			playFirst = playerFlag;
			playSecond = computerFlag;
		}
		else {
			playFirst = computerFlag;
			playSecond = playerFlag;
		}
		
	}

	private void decideCharOfPlayers() {
		if(rand.nextInt(10) % 2 == 0) {
			computerChar = 'S';
			playerChar = 'O';
		}
		else {
			computerChar = 'O';
			playerChar = 'S';
		}
	}
}
