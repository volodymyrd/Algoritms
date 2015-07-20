package pathfinding;

public class Node {
	private int h = -1;
	private int cost = 0;
	private Node parent = null;

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public void setParent(Node parent) {
		this.parent = parent;
		cost = parent.getCost() + 1;
	}

	public Node getParent() {
		return parent;
	}

	public int getCost() {
		return cost;
	}
	
	public abstract <> 
}