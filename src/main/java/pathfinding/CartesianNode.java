package pathfinding;

public abstract class CartesianNode extends Node {

	private int dimension;
	private byte[] values;

	public CartesianNode(int dimension, byte[] values) {
		super();
		this.dimension = dimension;
		int d = dimension * dimension;
		this.values = new byte[d];
		for (int i = 0; i < d; i++)
			this.values[i] = values[i];
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

	@Override
	public abstract <N extends Node> N[] getChildren();
}