package org.kodluyoruz;

import java.util.Random;
import java.util.Scanner;

public class Main {
	
    public static void main(String[] args) {
    	// write your code here
    	System.out.println("Enter the board size");
    	Scanner sc = new Scanner(System.in);
    	int boardSize = sc.nextInt();
    	SosGame game = new SosGame(boardSize);
    	game.playGame();
    	
    }
    
    
    
     
}
