package main;

import Graphics.GUI;
import Graphics.GUI.BoardPanel;
import Graphics.GUI.PlayerAPanel;
import Graphics.GUI.PlayerBPanel;
import Graphics.GUI.RoundPanel;

/*
 * 1: name: Konstantinos Gialamas, AEM: 9722, e-mail:gialamask@ece.auth.gr, phone:6987311122
 * 2: name: Ektoras-Thomas Rontos, AEM: 9477, e-mail:rontekto@ece.auth.gr, phone:6959408130
 */

/*Class Game: sets the round variable, getter and setter
and includes the main method*/

public class Game {
	
	//declaring variables
	
	private int round;
	
	//constructor
	
	public Game() {
		
	}
	
	public Game(int round) {
		this.round=round;
	}
	
	//getter
	
	public int getRound() {
		return round;
	}
	
	//setter
	
	public void setRound(int round) {
		this.round=round;
	}
	
	public static void gameplay(Board board, MinMaxPlayer minMaxPlayer, Player randomPlayer) {
		String winner = null;
		int minMaxScore = minMaxPlayer.getScore();
		int randomScore = randomPlayer.getScore();
		
		//game
		Game game = new Game(1);
		do {
			System.out.println("ROUND " + game.getRound());
			RoundPanel.setRound(game.getRound());
			
			//print table
			String[][] rep = board.getStringRepresentation(minMaxPlayer, randomPlayer);
			for (int i=0; i<board.getN(); i++) {
				for(int j=0; j<board.getM(); j++) {
					System.out.print("[" + rep[i][j] + "]");
				}
				System.out.print("\n");
			}
			BoardPanel.setTiles(rep);
			
			//player move
			minMaxPlayer.getNextMove(minMaxPlayer.getX(), minMaxPlayer.getY(), randomPlayer.getX(), randomPlayer.getY(), randomPlayer);
			PlayerAPanel.setPlayerATotalScore(minMaxPlayer.getScore());
			PlayerAPanel.setPlayerAMoveScore(minMaxPlayer.getScore() - minMaxScore);
			minMaxScore = minMaxPlayer.getScore();
			if(minMaxPlayer.getScore() < 0) {
				System.out.println(minMaxPlayer.getName() + " died!");
				winner = randomPlayer.getName();
				break;
			}else if(HeuristicPlayer.kill(minMaxPlayer, randomPlayer, 2)) {
				System.out.println(minMaxPlayer.getName() + " killed " + randomPlayer.getName() + "!");
				winner = minMaxPlayer.getName();
				break;
			}
			
			randomPlayer.move();
			PlayerBPanel.setPlayerBTotalScore(randomPlayer.getScore());
			PlayerBPanel.setPlayerBMoveScore(randomPlayer.getScore() - randomScore);
			randomScore = randomPlayer.getScore();
			if(randomPlayer.getScore() < 0) {
				System.out.println(randomPlayer.getName() + " died!");
				winner = minMaxPlayer.getName();
				break;
			}else if(HeuristicPlayer.kill(randomPlayer, minMaxPlayer, 2)) {
				System.out.println(randomPlayer.getName() + " killed " + minMaxPlayer.getName() + "!");
				winner = randomPlayer.getName();
				break;
			}
			
			//resizing board every 3 rounds
			if(game.getRound()%3 == 0) {
				board.resizeBoard(minMaxPlayer, randomPlayer);
			}
			
			//next round
			game.setRound(game.getRound() + 1);
			System.out.println("");
		}while(board.getN()>4 && board.getM()>4);
		
		System.out.println("\nRESULT:");
		//print score
		System.out.println(minMaxPlayer.getName() + " score: " + minMaxPlayer.getScore());
		System.out.println(randomPlayer.getName() + " score: " + randomPlayer.getScore());
		//print winner
		if(winner == null) {
			if(minMaxPlayer.getScore() > randomPlayer.getScore()) {
				winner = minMaxPlayer.getName();
			}else if(minMaxPlayer.getScore() < randomPlayer.getScore()) {
				winner = randomPlayer.getName();
			}else {
				winner = "Draw!";
			}
		}
		System.out.println("Winner: " + winner);
	}
	
	//main
	public static void main(String[] args) {
		new GUI();
	}

}