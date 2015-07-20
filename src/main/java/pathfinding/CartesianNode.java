package pathfinding;

public class CartesianNode extends Node {
	private int dimension;
	private byte[] values;

	public CartesianNode(int dimension, byte[] values) {
		super();
		this.dimension = dimension;
		this.values = values;
	}

	public byte[] getValues() {
		return values;
	}

	public void setValues(byte[] values) {
		this.values = values;
	}

	public int getDimension() {
		return dimension;
	}
}