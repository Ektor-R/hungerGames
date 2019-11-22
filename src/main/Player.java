package main;

import items.Weapon;
import items.Trap;
import items.Food;

/*Class Player: sets the variables, getters and setters for the player,
chooses one of the available moves for the player, moves the player
and makes all the necessary actions after the move */

public class Player {
	
	//declaring variables
	
	private int id;
	private String name;
	private Board board;
	private int score;
	private int x, y;
	private Weapon bow, pistol, sword;
	
	//constructors
	
	public Player() {
		
	}
	
	public Player(int id, String name, Board board, int score, int x, int y, 
			Weapon bow, Weapon pistol, Weapon sword) {
		this.id=id;
		this.name=name;
		this.board=board;
		this.score=score;
		this.x=x;
		this.y=y;
		this.bow=bow;
		this.pistol=pistol;
		this.sword=sword;
	}
	
	//getters
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Board getBoard() {
		return board;
	}
	public int getScore() {
		return score;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public Weapon getBow() {
		return bow;
	}
	public Weapon getPistol() {
		return pistol;
	}
	public Weapon getSword() {
		return sword;
	}
	
	//setters
	
	public void setId(int id) {
		this.id=id;
	}
	public void setName(String name) {
		this.name=name;
	}
	public void setBoard(Board board) {
		this.board=board;
	}
	public void setScore(int score) {
		this.score=score;
	}
	public void setX(int x) {
		this.x=x;
	}
	public void setY(int y) {
		this.y=y;
	}
	public void setBow(Weapon bow) {
		this.bow=bow;
	}
	public void setPistol(Weapon pistol) {
		this.pistol=pistol;
	}
	public void setSword(Weapon sword) {
		this.sword=sword;
	}
	
	//chooses one of the available moves
	public int[] getRandomMove(){
		//local variables
		int[] randomMove = new int[2];
		boolean ok = false;
		
		do {
			switch((int)(Math.random()*8) +1) {
			
			//move 1: up
			case 1:
				if(getY() == -board.getN()/2) {
					break;
				}else {
					randomMove[0] = getX();
					if(getY() == 1) {
						randomMove[1] = -1;
					}else {
						randomMove[1] = getY() - 1;
					}
					
					ok=true;
					break;
				}
			
			//move 2: up-right
			case 2:
				if((getX() == board.getM()/2) || (getY() == -board.getN()/2)) {
					break;
				}else {
					if(getX() == -1) {
						randomMove[0] = 1;
					}else {
						randomMove[0] = getX() + 1;
					}
					if(getY() == 1) {
						randomMove[1] = -1;
					}else {
						randomMove[1] = getY() - 1;
					}
					
					ok=true;
					break;
				}
				
			//move 3: right
			case 3:
				if(getX() == board.getM()/2) {
					break;
				}else {
					if(getX() == -1) {
						randomMove[0] = 1;
					}else {
						randomMove[0] = getX() + 1;
					}
					randomMove[1] = getY();
					
					ok=true;
					break;
				}
				
			//move 4: down-right
			case 4:
				if((getX() == board.getM()/2) || (getY() == board.getN()/2)) {
					break;
				}else {
					if(getX() == -1) {
						randomMove[0] = 1;
					}else {
						randomMove[0] = getX() + 1;
					}
					if(getY() == -1) {
						randomMove[1] = 1;
					}else {
						randomMove[1] = getY() + 1;
					}
					
					ok=true;
					break;
				}
				
			//move 5: down
			case 5:
				if(getY() == board.getN()/2) {
					break;
				}else {
					randomMove[0] = getX();
					if(getY() == -1) {
						randomMove[1] = 1;
					}else {
						randomMove[1] = getY() + 1;
					}
					
					ok=true;
					break;
				}
				
			//move 6: down-left
			case 6:
				if((getX() == -board.getM()/2) || (getY() == board.getN()/2)) {
					break;
				}else {
					if(getX() == 1) {
						randomMove[0] = -1;
					}else {
						randomMove[0] = getX() - 1;
					}
					if(getY() == -1) {
						randomMove[1] = 1;
					}else {
						randomMove[1] = getY() + 1;
					}
					
					ok=true;
					break;
				}
				
				//move 7: left
			case 7:
				if(getX() == -board.getM()/2) {
					break;
				}else {
					if(getX() == 1) {
						randomMove[0] = -1;
					}else {
						randomMove[0] = getX() - 1;
					}
					randomMove[1] = getY();
					
					ok=true;
					break;
				}
				
			//move 8: up-left
			case 8:
				if((getX() == -board.getM()/2) || (getY() == -board.getN()/2)) {
					break;
				}else {
					if(getX() == 1) {
						randomMove[0] = -1;
					}else {
						randomMove[0] = getX() - 1;
					}
					if(getY() == 1) {
						randomMove[1] = -1;
					}else {
						randomMove[1] = getY() - 1;
					}
					
					ok=true;
					break;
				}
			
			}
		}while(!ok);
		
		return randomMove;
	}
	
	//moves the player and makes actions
	public int[] move() {
		//local variables
		int[] randomMove = getRandomMove();
		int W = 0, T = 0, F = 0;
		
		setX(randomMove[0]);
		setY(randomMove[1]);
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
				W++;				
				weapons[i].setX(0);
				weapons[i].setY(0);
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
						System.out.println(getName() + " falls in a trap(animal) for " + traps[i].getPoints() + " points.");
						break;
					}else {
						System.out.println(getName() + " falls in a trap(animal) but has a bow!");
						traps[i].setX(0);
						traps[i].setY(0);
						break;
					}
				
				}
				
				T++;
			}
		}
		
		//check for food
		Food[] food = board.getFood();
		for(int i=0; i<board.getF(); i++) {
			if((food[i].getX() == getX()) && (food[i].getY() == getY())) {
				
				setScore(getScore() + food[i].getPoints());
				
				System.out.println(getName() + " gets food for " + food[i].getPoints() + " points!");
				F++;
				food[i].setX(0);
				food[i].setY(0);
			}
		}
		
		int[] move= {getX(), getY(), W, F, T};
		
		return move;
	}

}