package items;

//Class Food: sets the variables, getters and setters for the food items

public class Food {
	
	//declaring variables
	
		private int id;
		private int x;
		private int y;
		private int points;
		
		//constructors
		
		public Food() {
			
		}
		
		public Food(int id, int x, int y, int points) {
			this.id = id;
			this.x = x;
			this.y = y;
			this.points=points;
		}
		
		public Food(Food f) {
			this.id = f.id;
			this.x = f.x;
			this.y = f.y;
			this.points = f.points;
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
		public void setPoints(int points) {
			this.points = points;
		}

}