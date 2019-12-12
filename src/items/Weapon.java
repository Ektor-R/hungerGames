package items;

//Class Weapon: sets the variables, getters and setters for the weapons

public class Weapon {
	
	//declaring variables
	
	private int id;
	private int x;
	private int y;
	private int playerId;
	private String type;
	
	//constructors
	
	public Weapon() {
		id = -1;
		x = 0;
		y = 0;
		playerId = -1;
		type = null;
	}
	
	public Weapon(int id, int x, int y, int playerId, String type) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.playerId = playerId;
		this.type = type;
	}
	
	public Weapon(Weapon w) {
		id = w.getId();
		x = w.getX();
		y = w.getY();
		playerId = w.getPlayerId();
		type = w.getType();
	}
	
	//getters
	
	public int getId() {
		return id;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getPlayerId() {
		return playerId;
	}
	public String getType() {
		return type;
	}
	
	//setters
	
	public void setId(int id) {
		this.id = id;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	public void setType (String type) {
		this.type = type;
	}

}