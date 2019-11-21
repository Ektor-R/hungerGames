package items;

//Class Trap: sets the variables, getters and setters for the trap items

public class Trap {
	
	//declaring variables
	
		private int id;
		private int x;
		private int y;
		private String type;
		private int points;
		
		//constructors
		
		public Trap() {
			
		}
		
		public Trap(int id, int x, int y, String type, int points) {
			this.id = id;
			this.x = x;
			this.y = y;
			this.type = type;
			this.points=points;
		}
		
		public Trap(Trap t) {
			this.id = t.id;
			this.x = t.x;
			this.y = t.y;
			this.type = t.type;
			this.points = t.points;
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
		public String getType() {
			return type;
		}
		public int getPoints() {
			return points;
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
		public void setType (String type) {
			this.type = type;
		}
		public void setPoints(int points) {
			this.points=points;
		}

}