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
	}
	
	public Weapon(int id, int x, int y, int playerId, String type) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.playerId = playerId;
		this.type = type;
	}
	
	public Weapon(Weapon w) {
		this.id = w.getId();
		this.x = w.getX();
		this.y = w.getY();
		this.playerId = w.getPlayerId();
		this.type = w.getType();
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