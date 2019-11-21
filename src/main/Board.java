package main;

import items.Food;
import items.Trap;
import items.Weapon;

/*Class Board: sets the variables, getters and setters for the board, 
initializes the items and the board, resizes the borders of the board and 
displays a copy of the board to the player*/

public class Board {
	
	//declaring variables
	
	private int N, M;
	private int W, F, T;
	private int[][] weaponAreaLimits= new int[4][2];
	private int[][] foodAreaLimits= new int[4][2];
	private int trapAreaLimits[][]= new int[4][2];
	private Weapon[] weapons;
	private Food[] food;
	private Trap[] traps;
	
	//constructors
	
	public Board() {
	}
	
	public Board(int N, int M, int W, int F, int T) {
		this.N=N;
		this.M=M;
		this.W=W;
		this.F=F;
		this.T=T;
	}
	
	public Board(Board b) {
		this.N = b.N;
		this.M = b.M;
		this.W = b.W;
		this.F = b.F;
		this.T = b.T;
	}
	
	//getters
	
	public int getN() {
		return N;
	}
	public int getM() {
		return M;
	}
	public int getW() {
		return W;
	}
	public int getF() {
		return F;
	}
	public int getT() {
		return T;
	}
	public int[][] getWeaponAreaLimits(){
		return weaponAreaLimits;
	}
	public int[][] getFoodAreaLimits(){
		return foodAreaLimits;
	}
	public int[][] getTrapAreaLimits(){
		return trapAreaLimits;
	}
	public Weapon[] getWeapons(){
		return weapons;
	}
	public Food[] getFood() {
		return food;
	}
	public Trap[] getTraps() {
		return traps;
	}
	
	//setters
	
	public void setN(int N) {
		this.N=N;
	}
	public void setM(int M) {
		this.M=M;
	}
	public void setW(int W) {
		this.W=W;
	}
	public void setF(int F) {
		this.F=F;
	}
	public void setT(int T) {
		this.T=T;
	}
	public void setWeaponAreaLimits(int[][] weaponAreaLimits) {
		this.weaponAreaLimits=weaponAreaLimits;
	}
	public void setFoodAreaLimits(int[][] foodAreaLimits) {
		this.foodAreaLimits=foodAreaLimits;
	}
	public void setTrapAreaLimits(int[][] trapAreaLimits) {
		this.trapAreaLimits=trapAreaLimits;
	}
	public void setWeapons(Weapon[] weapons) {
		this.weapons=weapons;
	}
	public void setFood(Food[] food) {
		this.food=food;
	}
	public void setTraps(Trap[] traps) {
		this.traps=traps;
	}
	
	//initializing weapons
	public void createRandomWeapon() {
		
		//local variables
		int[][] areaLimits = getWeaponAreaLimits();
		Weapon[] weapons = new Weapon[getW()];
		
		for (int i=0; i<getW(); i++) {
			weapons[i] = new Weapon(i+1, 0, 0, i%2 + 1, "");
			
			//setting coordinates
			boolean ok = true;
			do {
				do {
					weapons[i].setX((int)(Math.random() * ((areaLimits[2][0] - areaLimits[0][0]) + 1)) + areaLimits[0][0]);	
				}while(weapons[i].getX() == 0);
				do {
					weapons[i].setY((int)(Math.random() * ((areaLimits[2][1] - areaLimits[0][0]) + 1)) + areaLimits[0][0]);	
				}while(weapons[i].getY() == 0);
				
				//check for other weapons
				for(int j=0; j<i; j++) {
					if((weapons[i].getX() == weapons[j].getX()) && (weapons[i].getY() == weapons[j].getY())) {
						ok = false;
						break;
					}else {
						ok = true;
					}
				}
			}while(!ok);
		}
		
		//setting type
		for (int i=0; i<(getW()/3); i++) {
			weapons[i].setType("pistol");
		}
		for (int i=getW()/3; i<2*getW()/3; i++) {
			weapons[i].setType("bow");
		}
		for (int i=2*getW()/3; i<getW(); i++) {
			weapons[i].setType("sword");
		}
		
		setWeapons(weapons);
		
	}
	
	//initializing traps
	public void createRandomTrap() {
		//local variables
		int[][] areaLimits = getTrapAreaLimits();
		Trap[] traps = new Trap[getT()]; 
		
		for (int i=0; i<getT(); i++) {
			traps[i] = new Trap(i+1, 0, 0, "", ((int)(Math.random()*10) + 1)*(-1));
			
			//setting coordinates
			boolean ok = true;
			do {
				//choosing side of areaLimits
				switch((int)(Math.random()*4) + 1) {
				//up
				case 1:
					do {
						traps[i].setX((int)(Math.random()*((areaLimits[1][0] - areaLimits[0][0]) + 1)) + areaLimits[0][0]);
					}while(traps[i].getX() == 0);
					traps[i].setY(areaLimits[0][1]);
					break;
				//right
				case 2:
					traps[i].setX(areaLimits[1][0]);
					do {
						traps[i].setY((int)(Math.random()*((areaLimits[2][1] - areaLimits[1][1]) + 1)) + areaLimits[1][1]);
					}while(traps[i].getY() == 0);
					break;
				//down
				case 3:
					do {
						traps[i].setX((int)(Math.random()*((areaLimits[2][0] - areaLimits[3][0]) + 1)) + areaLimits[3][0]);
					}while(traps[i].getX() == 0);
					traps[i].setY(areaLimits[2][1]);
					break;
				//left
				case 4:
					traps[i].setX(areaLimits[3][0]);
					do {
						traps[i].setY((int)(Math.random()*((areaLimits[3][1] - areaLimits[0][1]) + 1)) + areaLimits[0][1]);
					}while(traps[i].getY() == 0);
					break;
				}
				
				//checking for other traps
				for(int j=0; j<i; j++) {
					if(traps[i].getX() == traps[j].getX() && traps[i].getY() == traps[j].getY()) {
						ok = false;
						break;
					}else {
						ok = true;
					}
				}
			}while(!ok);
			
			//setting type
			if (i % 2 == 0) {
				traps[i].setType("rope");
			} else {
				traps[i].setType("animal");
			}
		}
		
		setTraps(traps);
		
	}
	
	//initializing food
	public void createRandomFood() {
		//local variables
		int[][] areaLimits = getFoodAreaLimits();
		Food[] food = new Food[getF()];
		
		for (int i=0; i<getF(); i++) {
			food[i] = new Food(i+1, 0, 0, (int)(Math.random()*10) + 1 );
			
			//setting coordinates
			boolean ok = true;
			do {
				//choosing side of areaLimits
				switch((int)(Math.random()*4) + 1) {
				//up
				case 1:
					do {
						food[i].setX((int)(Math.random()*((areaLimits[1][0] - areaLimits[0][0]) + 1)) + areaLimits[0][0]);
					}while(food[i].getX() == 0);
					food[i].setY(areaLimits[0][1]);
					break;
				//right
				case 2:
					food[i].setX(areaLimits[1][0]);
					do {
						food[i].setY((int)(Math.random()*((areaLimits[2][1] - areaLimits[1][1]) + 1)) + areaLimits[1][1]);
					}while(food[i].getY() == 0);
					break;
				//down
				case 3:
					do {
						food[i].setX((int)(Math.random()*((areaLimits[2][0] - areaLimits[3][0]) + 1)) + areaLimits[3][0]);
					}while(food[i].getX() == 0);
					food[i].setY(areaLimits[2][1]);
					break;
				//left
				case 4:
					food[i].setX(areaLimits[3][0]);
					do {
						food[i].setY((int)(Math.random()*((areaLimits[3][1] - areaLimits[0][1]) + 1)) + areaLimits[0][1]);
					}while(food[i].getY() == 0);
					break;
				}
				
				//checking for other food
				for(int j=0; j<i; j++) {
					if(food[i].getX() == food[j].getX() && food[i].getY() == food[j].getY()) {
						ok = false;
						break;
					}else {
						ok = true;	
					}
				}
			}while(!ok);
		}
		
		setFood(food);
		
	}
	
	//initializing board
	public void createBoard() {
		createRandomWeapon();
		createRandomTrap();
		createRandomFood();
		
	}
	
	//resizing board 
	public void resizeBoard(Player p1, Player p2) {
		
		//checking if player(s) is/are on the outer tiles
		if(!(
				(Math.abs(p1.getX()) == getM()/2) || 
				(Math.abs(p1.getY()) == getN()/2) || 
				(Math.abs(p2.getX()) == getM()/2) ||
				(Math.abs(p2.getY()) == getN()/2)
				)){
			setN(getN() - 2);
			setM(getM() - 2);
		}
	}
	
	//table representing the game's board 
	public String[][] getStringRepresentation(){
		//local variables
		String[][] representation = new String[getN()][getM()];
		int tileX, tileY;
		
		//setting values vertically
		for(int i=0; i<getN(); i++) {
			//skipping 0 for Y
			if(i < getN()/2) {
				tileY = -getN()/2 + i;
			}
			else {
				tileY = -getN()/2 + i + 1;
			}
			
			//setting values horizontally
			for(int j=0; j<getM(); j++) {
				
				//skipping 0 for X
				if(j < getM()/2) {
					tileX = -getM()/2 + j;
				}else {
					tileX = -getM()/2 + j + 1;
				}
				//default value for empty tile
				representation[i][j] ="___";
				
				//checking for weapons
				for(int z=0; z<getW(); z++) {
					if ((weapons[z].getX() == tileX) && (weapons[z].getY() == tileY)) {
						representation[i][j] = "W" + weapons[z].getPlayerId() + weapons[z].getId();
					}
				}
				//checking for traps
				for(int z=0; z<getT(); z++) {
					if((traps[z].getX() == tileX) && (traps[z].getY() == tileY)){
						representation[i][j] = "T" + traps[z].getId() + " ";
					}
				}
				//checking for food
				for(int z=0; z<getF(); z++) {
					if((food[z].getX() == tileX) && (food[z].getY() == tileY)) {
						//this if is for better presentation
						if(food[z].getId()<10) {
							representation[i][j] = "F" + food[z].getId() + " ";
						}else {
							representation[i][j] = "F" + food[z].getId();
						}
					}
				}
			}
		}
		return representation;
	}

}