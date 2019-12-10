package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import items.Food;
import items.Trap;
import items.Weapon;

public class MinMaxPlayer extends Player {

    //local variables
    ArrayList<Integer[]> path = new ArrayList<Integer[]>();
	
	//constructors
	public MinMaxPlayer() {
		
	}
	public MinMaxPlayer(ArrayList<Integer[]> path) {
		this.path = path;
	}
	
	//getters
	public ArrayList<Integer[]> getPath(){
		return path;
	}
	
	//setters
	public void setPath(ArrayList<Integer[]> path) {
		this.path = path;
	}
	
	//finds all available moves given x, y and board
	public static ArrayList<Integer> availableMoves(int x, int y, Board board){
		ArrayList<Integer> availableMoves = new ArrayList<Integer>();
		for(int i = 1; i<=8; i++) {
			switch(i) {
			case 1:
				if(y == -board.getN()/2) {
					break;
				}else {
					availableMoves.add(i);
					break;
				}
			case 2:
				if(x == board.getM()/2 || y == -board.getN()/2) {
					break;
				}else {
					availableMoves.add(i);
					break;
				}
			case 3:
				if(x == board.getM()/2) {
					break;
				}else {
					availableMoves.add(i);
					break;
				}
			case 4:
				if(x == board.getM()/2 || y == board.getN()/2) {
					break;
				}else {
					availableMoves.add(i);
					break;
				}
			case 5:
				if(y == board.getN()/2) {
					break;
				}else {
					availableMoves.add(i);
					break;
				}
			case 6:
				if(x == -board.getM()/2 || y == board.getN()/2) {
					break;
				}else {
					availableMoves.add(i);
					break;
				}
			case 7:
				if(x == -board.getM()/2) {
					break;
				}else {
					availableMoves.add(i);
					break;
				}
			case 8:
				if(x == -board.getM()/2 || y == -board.getN()/2) {
					break;
				}else {
					availableMoves.add(i);
					break;
				}
			}
		}
		return availableMoves;
	}
	
	//calculates new position given move
	public static int[] newPosition(int dice, int x, int y) {
		switch(dice) {
		//move 1: up
		case 1:
			if(y == 1) {
				y = -1;
			}else {
				y--;
			}
			break;
		//move 2: up-right
		case 2:
			if(x == -1) {
				x = 1;
			}else {
				x++;
			}
			if(y == 1) {
				y = -1;
			}else {
				y--;
			}
			break;
		//move 3: right
		case 3:
			if(x == -1) {
				x = 1;
			}else {
				x++;
			}
			break;
		//move 4: down-right
		case 4:
			if(x == -1) {
				x = 1;
			}else {
				x++;
			}
			if(y == -1) {
				y = 1;
			}else {
				y++;
			}
			break;
		//move 5: down
		case 5:
			if(y == -1) {
				y = 1;
			}else {
				y++;
			}
			break;
		//move 6: down-left
		case 6:
			if(x == 1) {
				x = -1;
			}else {
				x--;
				}
			if(y == -1) {
				y = 1;
			}else {
				y++;
			}
			break;
		//move 7: left
		case 7:
			if(x == 1) {
				x = -1;
			}else {
				x--;
			}
			break;
		//move 8: up-left
		case 8:
			if(x == 1) {
				x = -1;
			}else {
				x--;
			}
			if(y == 1) {
				y = -1;
			}else {
				y--;
			}
			break;
		}
		int[] newPosition = {x,y};
		return newPosition;
	}
	
	//calculates distance between two tiles given x,y
	public static float tileDistance(int x1, int y1, int x2, int y2) {
		//local variables
		float distance = 0;
		int dx = x1 - x2; 
		int dy = y1 - y2;
		
		//skipping 0 for x
		if(x1 * x2 < 0) {
			if(dx > 0) {
				dx--;
			}else {
				dx++;
			}
		}
		
		//skipping 0 for y
		if(y1 * y2 < 0) {
			if(dy > 0) {
				dy--;
			}else {
				dy++;
			}
		}
		
		//calculating distance
		distance = Math.max(Math.abs(dx), Math.abs(dy));		
		
		return distance;
	}
	
	//calculates the distance between the 2 players
	public float playerDistance(Player p) {
		
		float distance = tileDistance(getX(), getY(), p.getX(), p.getY());
		
		return distance;
	}

	//decides the strategy and importance of each available move
	//P: pistol, B: bow, S: sword, T: trap, F: food, K: kill (enemy player)
	public double f(int P, int B, int S, int T, int F, int K) {
		double evaluation;
		float gainPistol = 0.4f;
		float gainBow = 0.2f;
		float gainSword = 0.1f;
		float avoidTraps = 0.1f;
		float gainPoints = 0.3f;
		float forceKill = -0.3f;
		
		if(getPistol() != null) {
			gainPistol = 0;
			forceKill = 0.4f;
		}
		if(getBow() != null) {
			gainBow = 0;
		}
		if(getSword() != null) {
			gainSword = 0;
		}
		
		evaluation = gainPistol*P + gainBow*B + gainSword*S + avoidTraps*T + gainPoints*F + forceKill*K;
		
		return evaluation;
	}

	//evaluates given move
	public double evaluate(int dice, int x, int y, Player opponent) {
		//local variables
		double evaluation = 0;
		Board board = getBoard();
		int [] pos = {x, y}; //initial position
		
		//move player
		int[] newPos = newPosition(dice, x, y);
		x = newPos[0];
		y = newPos[1];
		
		//check for weapons
		Weapon[] weapons = board.getWeapons();
		for(int i=0; i<board.getW(); i++) {
			if(
					(weapons[i].getX() == x) && 
					(weapons[i].getY() == y) && 
					(weapons[i].getPlayerId() == getId())) {
				switch(weapons[i].getType()) {
				
				case "bow":
					evaluation += f(0, 10, 0, 0, 0, 0);
					break;
				case "pistol":
					evaluation += f(10, 0, 0, 0, 0, 0);
					break;
				case "sword":
					evaluation += f(0, 0, 10, 0, 0, 0);
					break;
				}
				break;
			}
		}
		
		//check for traps
		Trap[] traps = board.getTraps();
		for(int i=0; i<board.getT(); i++) {
			if((traps[i].getX() == x) && (traps[i].getY() == y)) {
				
				switch(traps[i].getType()) {
				case "rope":
					if(getSword() == null) {
						evaluation += f(0, 0, 0, traps[i].getPoints(), 0, 0);
						break;
					}
				case "animal":
					if(getBow() == null) {
						evaluation += f(0, 0, 0, traps[i].getPoints(), 0, 0);
						break;
					}
				}
				break;
			}
		}
		
		//check for food
		Food[] food = board.getFood();
		for(int i=0; i<board.getF(); i++) {
			if((food[i].getX() == x) && (food[i].getY() == y)) {
				evaluation += f(0, 0, 0, 0, food[i].getPoints(), 0);
				break;
			}
		}
		
		//check distance to opponent
		setX(x);
		setY(y);
		if(playerDistance(opponent) != -1) {
			evaluation += f(0, 0, 0, 0, 0, (10/board.getN()) * (board.getN() - (int)playerDistance(opponent)) );
		}
		setX(pos[0]);
		setY(pos[1]);
			
		return evaluation;
	}
	
	int chooseMinMaxMove(Node root) {
		ArrayList<Node> rootChildren = root.getChildren();
		Node rootChild;
		ArrayList<Node> nodeChildren = new ArrayList<Node>();
		Node nodeChild;
		double min = -10, max = -10;
		int[] nodeMove;
		ArrayList<Integer> bestMove = new ArrayList<Integer>();
		
		//depth 2
		for (int i=0; i<rootChildren.size(); i++) {
			rootChild = rootChildren.get(i);
			nodeChildren = rootChild.getChildren();
			
			//depth 3
			for(int j=0; j<nodeChildren.size(); j++) {
				nodeChild = nodeChildren.get(j);
				if(nodeChild.getNodeEvaluation() < min) {
					min = nodeChild.getNodeEvaluation();
				}
			}
			rootChild.setNodeEvaluation(min);
			nodeMove = rootChild.getNodeMove();
			if(rootChild.getNodeEvaluation() > max) {
				max = rootChild.getNodeEvaluation();
				bestMove.clear();
				bestMove.add(nodeMove[2]);
			}else if(rootChild.getNodeEvaluation() == max) {
				bestMove.add(nodeMove[2]);
			}
		}
		int move = bestMove.get((int)(Math.random()*bestMove.size()));
		return move;
	}
	
	int[] getNextMove(int x, int y, int xOp, int yOp, Player opponent) {
		int[] bestMove = new int [3];
		Node root = new Node();
		Board board = getBoard();
		root.setNodeBoard(board);
		ArrayList<Integer[]> path = getPath();
		Integer[] info = {0 , 0, 0, 0, 0}; //0:dice 1:score 2:weapons 3:trap 4:food
		
		createMySubtree(root, 1, x, y, xOp, yOp, opponent);
		bestMove[2] = chooseMinMaxMove(root);
		info[0] = bestMove[2];
		int [] newPos = newPosition( bestMove[2], x, y);
		bestMove[0] = newPos[0];
		bestMove[1] = newPos[1];
		
		//move player
		setX(bestMove[0]);
		setY(bestMove[1]);
		System.out.println(getName() + " moves to " + getX() + ", " + getY());
		
		//check for weapons
		Weapon[] weapons = board.getWeapons();
		for(int i=0; i<board.getW(); i++) {
			if(
					(weapons[i].getX() == getX()) && 
					(weapons[i].getY() == getY()) && 
					(weapons[i].getPlayerId() == getId())) {
				switch(weapons[i].getType()) {
				case "bow":
					setBow(weapons[i]);
					break;
				case "pistol":
					setPistol(weapons[i]);
					break;
				case "sword":
					setSword(weapons[i]);
					break;
				}
				System.out.println(getName() + " gets the " + weapons[i].getType() + "!");
				info[2]++;
				weapons[i].setX(0);
				weapons[i].setY(0);
				break;
			}
		}
		//check for traps
		Trap[] traps = board.getTraps();
		for(int i=0; i<board.getT(); i++) {
			if((traps[i].getX() == getX()) && (traps[i].getY() == getY())) {
				switch(traps[i].getType()) {
				case "rope":
					if(getSword() == null) {
						setScore(getScore() + traps[i].getPoints());
						info[1] = traps[i].getPoints();
						System.out.println(getName() + " falls in a trap(rope) for " + traps[i].getPoints() + " points.");
						break;
					}else {
						System.out.println(getName() + " falls in a trap(rope) but has a sword!");
						traps[i].setX(0);
						traps[i].setY(0);
						break;
					}
				case "animal":
					if(getBow() == null) {
						setScore(getScore() + traps[i].getPoints());
						info[1] = traps[i].getPoints();
						System.out.println(getName() + " falls in a trap(animal) for " + traps[i].getPoints() + " points.");
						break;
					}else {
						System.out.println(getName() + " falls in a trap(animal) but has a bow!");
						traps[i].setX(0);
						traps[i].setY(0);
						break;
					}
				}
				info[3]++;
				break;
			}
		}
		//check for food
		Food[] food = board.getFood();
		for(int i=0; i<board.getF(); i++) {
			if((food[i].getX() == getX()) && (food[i].getY() == getY())) {
				setScore(getScore() + food[i].getPoints());
				info[1] = food[i].getPoints();
				System.out.println(getName() + " gets food for " + food[i].getPoints() + " points!");
				info[4]++;
				food[i].setX(0);
				food[i].setY(0);
				break;
			}
		}
		//path
		path.add(info);
		
		return bestMove;
	}
	
	
	void createMySubtree(Node root, int depth, int x, int y, int xOp, int yOp, Player opponent) {
		Board board = root.getNodeBoard();
		ArrayList<Integer> availableMoves = availableMoves( x, y, board);
		
		for(int j=0; j<availableMoves.size(); j++) {
			Board boardClone = new Board(root.getNodeBoard());
			//copy player's weapons and score
			Weapon bow = new Weapon(getBow());
			Weapon pistol = new Weapon(getPistol());
			Weapon sword = new Weapon(getSword());
			int score = getScore();
			
			int[] newPos = newPosition(availableMoves.get(j), x, y);
			x = newPos[0];
			y = newPos[1];
			
			//check for weapons
			Weapon[] weapons = boardClone.getWeapons();
			for(int i=0; i<boardClone.getW(); i++) {
				if(
						(weapons[i].getX() == x) && 
						(weapons[i].getY() == y) && 
						(weapons[i].getPlayerId() == getId())) {
					switch(weapons[i].getType()) {
					case "bow":
						bow = weapons[i];
						break;
					case "pistol":
						pistol = weapons[i];
						break;
					case "sword":
						sword = weapons[i];
						break;
					}
					weapons[i].setX(0);
					weapons[i].setY(0);
					break;
				}
			}
			//check for traps
			Trap[] traps = boardClone.getTraps();
			for(int i=0; i<boardClone.getT(); i++) {
				if((traps[i].getX() == x) && (traps[i].getY() == y)) {
					switch(traps[i].getType()) {
					case "rope":
						if(sword == null) {
							score += traps[i].getPoints();
							break;
						}else {
							traps[i].setX(0);
							traps[i].setY(0);
							break;
						}
					case "animal":
						if(bow == null) {
							score += traps[i].getPoints();
							break;
						}else {
							traps[i].setX(0);
							traps[i].setY(0);
							break;
						}
					}
					break;
				}
			}
			//check for food
			Food[] food = boardClone.getFood();
			for(int i=0; i<boardClone.getF(); i++) {
				if((food[i].getX() == x) && (food[i].getY() == y)) {
					score += food[i].getPoints();
					food[i].setX(0);
					food[i].setY(0);
					break;
				}
			}
			
			Node newNode = new Node();
			newNode.setParent(root);
			newNode.setNodeBoard(boardClone);
			newNode.setNodeEvaluation(evaluate(availableMoves.get(j), x, y, opponent));
			
			ArrayList<Node> rootChildren = root.getChildren();
			rootChildren.add(newNode);
			
			createOpponentSubTree(newNode, depth + 1, x, y, xOp, yOp, opponent);
		}
		
	}
	
	
	void createOpponentSubTree(Node parent, int depth, int x, int y, int xOp, int yOp, Player opponent) {
		Board board = parent.getNodeBoard();
		ArrayList<Integer> availableMoves = availableMoves( xOp, yOp, board);
		
		for(int j=0; j<availableMoves.size(); j++) {
			Board boardClone = new Board(parent.getNodeBoard());
			//copy player's weapons and score
			Weapon bow = new Weapon(opponent.getBow());
			Weapon pistol = new Weapon(opponent.getPistol());
			Weapon sword = new Weapon(opponent.getSword());
			int score = opponent.getScore();
			
			int[] newPos = newPosition(availableMoves.get(j), xOp, yOp);
			xOp = newPos[0];
			yOp = newPos[1];
			
			//check for weapons
			Weapon[] weapons = boardClone.getWeapons();
			for(int i=0; i<boardClone.getW(); i++) {
				if(
						(weapons[i].getX() == xOp) && 
						(weapons[i].getY() == yOp) && 
						(weapons[i].getPlayerId() == opponent.getId())) {
					switch(weapons[i].getType()) {
					case "bow":
						bow = weapons[i];
						break;
					case "pistol":
						pistol = weapons[i];
						break;
					case "sword":
						sword = weapons[i];
						break;
					}
					weapons[i].setX(0);
					weapons[i].setY(0);
					break;
				}
			}
			//check for traps
			Trap[] traps = boardClone.getTraps();
			for(int i=0; i<boardClone.getT(); i++) {
				if((traps[i].getX() == xOp) && (traps[i].getY() == yOp)) {
					switch(traps[i].getType()) {
					case "rope":
						if(sword == null) {
							score += traps[i].getPoints();
							break;
						}else {
							traps[i].setX(0);
							traps[i].setY(0);
							break;
						}
					case "animal":
						if(bow == null) {
							score += traps[i].getPoints();
							break;
						}else {
							traps[i].setX(0);
							traps[i].setY(0);
							break;
						}
					}
					break;
				}
			}
			//check for food
			Food[] food = boardClone.getFood();
			for(int i=0; i<boardClone.getF(); i++) {
				if((food[i].getX() == xOp) && (food[i].getY() == yOp)) {
					score += food[i].getPoints();
					food[i].setX(0);
					food[i].setY(0);
					break;
				}
			}
			
			Node newNode = new Node();
			newNode.setParent(parent);
			newNode.setNodeBoard(boardClone);
			newNode.setNodeEvaluation(parent.getNodeEvaluation() - evaluate(availableMoves.get(j), xOp, yOp, opponent));
			
			ArrayList<Node> rootChildren = parent.getChildren();
			rootChildren.add(newNode);
			
		}
	}
	

}
