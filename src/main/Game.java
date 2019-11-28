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
		
		HeuristicPlayer.setR(3);
		
		//defining Heuristic Player
		HeuristicPlayer heuristicPlayer= new HeuristicPlayer();
		heuristicPlayer.setId(1);
		heuristicPlayer.setName("Heuristic Player");
		heuristicPlayer.setBoard(board);
		heuristicPlayer.setScore(0);
		heuristicPlayer.setX(-board.getM()/2);
		heuristicPlayer.setY(-board.getN()/2);
		
		
		//defining Random Player
		Player randomPlayer=new Player();
		randomPlayer.setId(2);
		randomPlayer.setName("Random Player");
		randomPlayer.setBoard(board);
		randomPlayer.setScore(0);
		randomPlayer.setX(board.getM()/2);
		randomPlayer.setY(board.getN()/2);
		
		
		//game
		Game game = new Game(1);
		do {
			System.out.println("ROUND " + game.getRound());
			
			//print table
			String[][] rep = board.getStringRepresentation(heuristicPlayer, randomPlayer);
			for (int i=0; i<board.getN(); i++) {
				for(int j=0; j<board.getM(); j++) {
					System.out.print("[" + rep[i][j] + "]");
				}
				System.out.print("\n");
			}
			
			//player move
			heuristicPlayer.move(randomPlayer);
			if(HeuristicPlayer.kill(heuristicPlayer, randomPlayer, 2)) {
				System.out.println(heuristicPlayer.getName() + " killed " + randomPlayer.getName() + "!");
				winner = heuristicPlayer.getName();
				break;
			}
			randomPlayer.move();
			if(HeuristicPlayer.kill(randomPlayer, heuristicPlayer, 2)) {
				System.out.println(randomPlayer.getName() + " killed " + heuristicPlayer.getName() + "!");
				winner = randomPlayer.getName();
				break;
			}
			
			//statistics
			heuristicPlayer.statistics(game.getRound());
			
			//resizing board every 3 rounds
			if(game.getRound()%3 == 0) {
				board.resizeBoard(heuristicPlayer, randomPlayer);
			}
			
			//next round
			game.setRound(game.getRound() + 1);
			System.out.println("");
		}while(board.getN()>4 && board.getM()>4);
		
		System.out.println("\nRESULT:");
		//print score
		System.out.println(heuristicPlayer.getName() + " score: " + heuristicPlayer.getScore());
		System.out.println(randomPlayer.getName() + " score: " + randomPlayer.getScore());
		//print winner
		if(winner == "") {
			if(heuristicPlayer.getScore() > randomPlayer.getScore()) {
				winner = heuristicPlayer.getName();
			}else if(heuristicPlayer.getScore() < randomPlayer.getScore()) {
				winner = randomPlayer.getName();
			}else {
				winner = "Draw!";
			}
		}
		System.out.println("Winner: " + winner);
	}

}