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
		String winner = "";
		
		int[][] weaponAreaLimits = {{-2, -2}, {2, -2}, {2, 2}, {-2, 2}};
		int[][] foodAreaLimits = {{-3, -3}, {3, -3}, {3, 3}, {-3, 3}};
		int[][] trapAreaLimits = {{-4, -4}, {4, -4}, {4, 4}, {-4 ,4}};
		
		//creating board
		Board board=new Board(20, 20, 6, 10, 8);
		board.setWeaponAreaLimits(weaponAreaLimits);
		board.setFoodAreaLimits(foodAreaLimits);
		board.setTrapAreaLimits(trapAreaLimits);
		board.createBoard();
		
		//defining Min Max Player
		MinMaxPlayer minMaxPlayer= new MinMaxPlayer();
		minMaxPlayer.setId(1);
		minMaxPlayer.setName("Min Max Player");
		minMaxPlayer.setBoard(board);
		minMaxPlayer.setScore(15);
		minMaxPlayer.setX(board.getM()/2);
		minMaxPlayer.setY(board.getN()/2);
		
		
		//defining Random Player
		Player randomPlayer=new Player();
		randomPlayer.setId(2);
		randomPlayer.setName("Random Player");
		randomPlayer.setBoard(board);
		randomPlayer.setScore(15);
		randomPlayer.setX(board.getM()/2);
		randomPlayer.setY(board.getN()/2);
		
		
		//game
		Game game = new Game(1);
		do {
			System.out.println("ROUND " + game.getRound());
			
			//print table
			String[][] rep = board.getStringRepresentation(minMaxPlayer, randomPlayer);
			for (int i=0; i<board.getN(); i++) {
				for(int j=0; j<board.getM(); j++) {
					System.out.print("[" + rep[i][j] + "]");
				}
				System.out.print("\n");
			}
			
			//player move
			minMaxPlayer.move();
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
		if(winner == "") {
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

}