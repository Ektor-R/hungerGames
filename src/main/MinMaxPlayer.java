package main;

import java.util.ArrayList;

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

}
