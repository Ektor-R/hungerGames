package main;

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
	
	
	//main
	public static void main(String[] args) {
		
		int[][] weaponAreaLimits = {{-2, -2}, {2, -2}, {2, 2}, {-2, 2}};
		int[][] foodAreaLimits = {{-3, -3}, {3, -3}, {3, 3}, {-3, 3}};
		int[][] trapAreaLimits = {{-4, -4}, {4, -4}, {4, 4}, {-4 ,4}};
		
		//creating board
		Board board=new Board(20, 20, 6, 10, 8);
		board.setWeaponAreaLimits(weaponAreaLimits);
		board.setFoodAreaLimits(foodAreaLimits);
		board.setTrapAreaLimits(trapAreaLimits);
		board.createBoard();
		
		//defining player1
		Player p1=new Player();
		p1.setId(1);
		p1.setName("Player1");
		p1.setBoard(board);
		p1.setScore(0);
		p1.setX(-board.getM()/2);
		p1.setY(-board.getN()/2);
		
		
		//defining player2
		Player p2=new Player();
		p2.setId(2);
		p2.setName("Player2");
		p2.setBoard(board);
		p2.setScore(0);
		p2.setX(board.getM()/2);
		p2.setY(board.getN()/2);
		
		
		//game
		Game game = new Game(1);
		do {
			System.out.println("ROUND " + game.getRound());
			
			//print table
			String[][] rep = board.getStringRepresentation();
			for (int i=0; i<board.getN(); i++) {
				for(int j=0; j<board.getM(); j++) {
					System.out.print("[" + rep[i][j] + "]");
				}
				System.out.print("\n");
			}
			
			//player move
			p1.move();
			p2.move();			  
			
			//resizing board every 3 rounds
			if(game.getRound()%3 == 0) {
				board.resizeBoard(p1, p2);
			}
			
			//next round
			game.setRound(game.getRound() + 1);
		}while(board.getN()>4 && board.getM()>4);
		
		//print score
		System.out.println("Player 1 score: " + p1.getScore());
		System.out.println("Player 2 score: " + p2.getScore());
		
	}

}