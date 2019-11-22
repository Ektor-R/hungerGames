package main;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import items.Food;
import items.Trap;
import items.Weapon;

//class HeuristicPlayer

public class HeuristicPlayer extends Player {
	
	//declaring variables 
	
	private ArrayList<Integer[]> path = new ArrayList<Integer[]>();
	private static int r;
	
	
	//constructors
	
	public HeuristicPlayer() {
		
	}
	
	public HeuristicPlayer(ArrayList<Integer[]> path) {
		this.path = path;
	}
	
	
	//getters
	
	public ArrayList<Integer[]> getPath(){
		return path;
	}
	
	public static int getR() {
		return r;
	}
	
	//setters
	
	public void setPath(ArrayList<Integer[]> path) {
		this.path = path;
	}
	
	public static void setR(int newR) {
		r = newR;
	}

	
	//calculates the distance between the 2 players
	public float playerDistance(Player p) {
		//local variables
		float distance = 0;
		int dx = getX() - p.getX(); 
		int dy = getY() - p.getY();
		
		//skipping 0 for x
		if(getX() * p.getX() < 0) {
			if(dx > 0) {
				dx--;
			}else {
				dx++;
			}
		}
		
		//skipping 0 for y
		if(getY() * p.getY() < 0) {
			if(dy > 0) {
				dy--;
			}else {
				dy++;
			}
		}
		
		//calculating distance
		//distance = (float)Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
		distance = Math.max(Math.abs(dx), Math.abs(dy));
		
		if(distance > getR()) {
			distance = -1;
		}
		
		return distance;
	}
	
	public double f(int P, int B, int S, int T, int F, int K) {
		double evaluation;
		float gainPistol = 0.4f;
		float gainBow = 0.2f;
		float gainSword = 0.1f;
		float avoidTraps = 0.1f;
		float gainPoints = 0.2f;
		float forceKill = 0f;
		
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
	
	public double evaluate(int dice, Player p) {
		//local variables
		double evaluation = 0;
		int x = 0, y = 0;
		Board board = getBoard();
		boolean ok = true;
		
		switch(dice) {
		
		//move 1: up
		case 1:
			if(getY() == -board.getN()/2) {
				ok = false;
				break;
			}else {
				x = getX();
				if(getY() == 1) {
					y = -1;
				}else {
					y = getY() - 1;
				}
				break;
			}
		
		//move 2: up-right
		case 2:
			if(getX() == board.getM()/2 || getY() == -board.getN()/2) {
				ok = false;
				break;
			}else {
				if(getX() == -1) {
					x = 1;
				}else {
					x = getX() + 1;
				}
				if(getY() == 1) {
					y = -1;
				}else {
					y = getY() - 1;
				}
				break;
			}
			
		//move 3: right
		case 3:
			if(getX() == board.getM()/2) {
				ok = false;
				break;
			}else {
				if(getX() == -1) {
					x = 1;
				}else {
					x = getX() + 1;
				}
				y = getY();
				break;
			}
			
		//move 4: down-right
		case 4:
			if(getX() == board.getM()/2 || getY() == board.getN()/2) {
				ok = false;
				break;
			}else {
				if(getX() == -1) {
					x = 1;
				}else {
					x = getX() + 1;
				}
				if(getY() == -1) {
					y = 1;
				}else {
					y = getY() + 1;
				}
				break;
			}
			
		//move 5: down
		case 5:
			if(getY() == board.getN()/2) {
				ok = false;
				break;
			}else {
				x = getX();
				if(getY() == -1) {
					y = 1;
				}else {
					y = getY() + 1;
				}
				break;
			}
			
		//move 6: down-left
		case 6:
			if(getX() == -board.getM()/2 || getY() == board.getN()/2) {
				ok = false;
				break;
			}else {
				if(getX() == 1) {
					x = -1;
				}else {
					x = getX() - 1;
					}
				if(getY() == -1) {
					y = 1;
				}else {
					y = getY() + 1;
				}
				break;
			}
			
			//move 7: left
		case 7:
			if(getX() == -board.getM()/2) {
				ok = false;
				break;
			}else {
				if(getX() == 1) {
					x = -1;
				}else {
					x = getX() - 1;
				}
				y = getY();
				break;
			}
			
		//move 8: up-left
		case 8:
			if(getX() == -board.getM()/2 || getY() == -board.getN()/2) {
				ok = false;
				break;
			}else {
				if(getX() == 1) {
					x = -1;
				}else {
					x = getX() - 1;
				}
				if(getY() == 1) {
					y = -1;
				}else {
					y = getY() - 1;
				}
				break;
			}
		}
		
		if(ok) {
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
				}
			}
			
			//check for food
			Food[] food = board.getFood();
			for(int i=0; i<board.getF(); i++) {
				if((food[i].getX() == x) && (food[i].getY() == y)) {
					evaluation += f(0, 0, 0, 0, food[i].getPoints(), 0);
				}
			}
			
			if(getPistol()!= null) {
				if(playerDistance(p) != -1) {
					evaluation += f(0, 0, 0, 0, 0, 10 + getR() - (int)playerDistance(p));
				}
			}
		}else {
			evaluation = -10;
		}
		
		return evaluation;
	}
	
	//TODO
	public static boolean kill(Player player1, Player player2, float d) {
		//local variables
		boolean result;
		float distance = 0;
		int dx = player1.getX() - player2.getX(); 
		int dy = player1.getY() - player2.getY();
				
		//skipping 0 for x
		if(player1.getX() * player2.getX() < 0) {
			if(dx > 0) {
				dx--;
			}else {
				dx++;
			}
		}
				
		//skipping 0 for y
		if(player1.getY() * player2.getY() < 0) {
			if(dy > 0) {
				dy--;
			}else {
				dy++;
			}
		}
				
		//calculating distance
		//distance = (float)Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
		distance = Math.max(Math.abs(dx), Math.abs(dy));
				
		if(distance < d && player1.getPistol() != null) {
			result = true;
		}else {
			result = false;
		}
		
		return result;
	}
	
	//TODO
	public int[] move(Player p) {
		//local variables
		int[] move = new int[2];
		ArrayList<Integer[]> path = getPath();
		Integer[] info = {0 , 0, 0, 0, 0}; //0:dice 1:score 2:weapons 3:trap 4:food
		Map<Integer,Double> map = new HashMap<Integer,Double>();
		ArrayList<Integer> bestMove = new ArrayList<Integer>();
		double maxScore = -10;
		Board board = getBoard();
		
		//storing evaluation
		for(int i=1; i<=8; i++) {
			map.put(i, evaluate(i, p));
		}
		
		//best move
		for(Map.Entry<Integer, Double> entry : map.entrySet()) {
			if(entry.getValue() > maxScore) {
				maxScore = entry.getValue();
				bestMove.clear();
				bestMove.add(entry.getKey());
			}else if(entry.getValue() == maxScore) {
				bestMove.add(entry.getKey());
			}
		}
		
		info[0] = bestMove.get((int)(Math.random()*bestMove.size()));
		
		//move player
		switch(info[0]) {
		
		//move 1: up
		case 1:
			if(getY() == 1) {
				setY(-1);
			}else {
				setY(getY() - 1);
			}
			break;
		
		//move 2: up-right
		case 2:
			if(getX() == -1) {
				setX(1);
			}else {
				setX(getX() + 1);
			}
			if(getY() == 1) {
				setY(-1);
			}else {
				setY(getY() - 1);
			}
			break;
			
		//move 3: right
		case 3:
			if(getX() == -1) {
				setX(1);
			}else {
				setX(getX() + 1);
			}
			break;
			
		//move 4: down-right
		case 4:
			if(getX() == -1) {
				setX(1);
			}else {
				setX(getX() + 1);
			}
			if(getY() == -1) {
				setY(1);
			}else {
				setY(getY() + 1);
			}
			break;
			
		//move 5: down
		case 5:
			if(getY() == -1) {
				setY(1);
			}else {
				setY(getY() + 1);
			}
			break;
			
		//move 6: down-left
		case 6:
			if(getX() == 1) {
				setX(-1);
			}else {
				setX(getX() - 1);
			}
			if(getY() == -1) {
				setY(1);
			}else {
				setY(getY() + 1);
			}
			break;
			
		//move 7: left
		case 7:
			if(getX() == 1) {
				setX(-1);
			}else {
				setX(getX() - 1);
			}
			break;
			
		//move 8: up-left
		case 8:
			if(getX() == 1) {
				setX(-1);
			}else {
				setX(getX() - 1);
			}
			if(getY() == 1) {
				setY(-1);
			}else {
				setY(getY() - 1);
			}
			break;
		}
		
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
			}
		}
		
		//path
		path.add(info);
		setPath(path);
		
		move[0] = getX();
		move[1] = getY();
		return move;
	}
	
	
	public void statistics(int round) {
		ArrayList<Integer[]> path = getPath();
		Integer[] stats = path.get(round - 1);
		System.out.println("Statistics: " + getName() + " chose move:" + stats[0] + ", gathered " + stats[2] + " weapons and gained " + stats[1] + " points.");
	}

}
