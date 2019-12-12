package main;

import java.util.ArrayList;

/*class Node: creates node objects for the MinMaxAlgorithm tree*/

public class Node {
    
    //local variables
    private Node parent;
    private ArrayList<Node> children;
    private int nodeDepth;
    private int[] nodeMove;
    private Board nodeBoard;
    private double nodeEvaluation;

    //constructors 
    public Node(){
    	parent = null;
    	children = new ArrayList<Node>();
    	nodeDepth = 0;
    	nodeMove = new int[3];
    	nodeBoard = null;
    	nodeEvaluation = 0;
    }

    //getters
    public Node getParent(){
        return parent;
    }
    public ArrayList<Node> getChildren(){
        return children;
    }
    public int getNodeDepth(){
        return nodeDepth;
    }
    public int[] getNodeMove(){
        return nodeMove;
    }
    public Board getNodeBoard(){
        return nodeBoard;
    }
    public double getNodeEvaluation(){
        return nodeEvaluation;
    }

    //setters
    public void setParent(Node parent){
        this.parent = parent;
    }
    public void setChildren(ArrayList<Node> children){
        this.children = children;
    }
    public void setNodeDepth(int nodeDepth){
        this.nodeDepth = nodeDepth;
    }
    public void setNodeMove(int[] nodeMove){
        this.nodeMove = nodeMove;
    }
    public void setNodeBoard(Board nodeBoard){
        this.nodeBoard = nodeBoard;
    }
    public void setNodeEvaluation(double nodeEvaluation){
        this.nodeEvaluation = nodeEvaluation;
    }

}
