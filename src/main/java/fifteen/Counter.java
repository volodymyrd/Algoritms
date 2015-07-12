package fifteen;

public class Counter implements Comparable<Counter> {

	public enum StepType {
		TOP, DOWN, LEFT, RIGHT;
	}

	private String name;
	private int x;
	private int y;
	private int currentX;
	private int currentY;
	protected int currentPos;

	public Counter(int currentX, int currentY, int x, int y, String name) {
		this.currentX = currentX;
		this.currentY = currentY;
		this.x = x;
		this.y = y;
		to2Name(name);

		recalcIndex();
	}

	public Counter copy() {
		Counter c = new Counter(currentX, currentY, x, y, name);

		return c;
	}

	public long currentCost() {
		//System.out.println("currentCost " + this);
		return (x - currentX) * (x - currentX) + (y - currentY)
				* (y - currentY);
	}

	public long emptyCost(Counter empty) {
		//System.out.println("emptyCost " + this);
		//System.out.println("emptyCost " + empty);
		return (empty.currentX - currentX) * (empty.currentX - currentX)
				+ (empty.currentY - currentY) * (empty.currentY - currentY);

	}

	public Counter(int currentPos, int pos, String name) {
		to2Name(name);
		this.currentPos = currentPos;

		recalcCurrentCoordinate(currentPos, 1);
		recalcCoordinate(pos, 1);
	}

	public int step(StepType type) {
		switch (type) {
		case DOWN:
			return down();
		case LEFT:
			return left();
		case RIGHT:
			return right();
		case TOP:
			return top();
		}

		return -1;
	}

	public int antiStep(StepType type) {
		switch (type) {
		case DOWN:
			return top();
		case LEFT:
			return right();
		case RIGHT:
			return left();
		case TOP:
			return down();
		}

		return -1;
	}

	private int top() {
		if (currentY > 1) {
			currentY -= 1;

			recalcIndex();

			return currentPos;
		} else
			return -1;
	}

	private int down() {
		if (currentY < Fifteen.MAX_Y) {
			currentY += 1;

			recalcIndex();
		} else
			return -1;

		return currentPos;
	}

	private int left() {
		if (currentX > 1) {
			currentX -= 1;

			recalcIndex();
		} else
			return -1;

		return currentPos;

	}

	private int right() {
		if (currentX < Fifteen.MAX_X) {
			currentX += 1;

			recalcIndex();
		} else
			return -1;

		return currentPos;
	}

	public int getCurrentX() {
		return currentX;
	}

	public int getCurrentY() {
		return currentY;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getName() {
		return name;
	}

	public int getCurrentPos() {
		return currentPos;
	}

	@Override
	public int compareTo(Counter o) {
		return currentPos - o.currentPos;
	}

	private void recalcIndex() {
		currentPos = Fifteen.MAX_X * (currentY - 1) + currentX - 1;
		// currentPos = currentY * currentX - 1;
	}

	private void recalcCurrentCoordinate(int index, int row) {
		if (index < row * Fifteen.MAX_X) {
			currentY = row;
			currentX = (index + 1) - Fifteen.MAX_X * (currentY - 1);
		} else
			recalcCurrentCoordinate(index, row + 1);
	}

	private void recalcCoordinate(int index, int row) {
		if (index < row * Fifteen.MAX_X) {
			y = row;
			x = (index + 1) - Fifteen.MAX_X * (y - 1);
		} else
			recalcCoordinate(index, row + 1);
	}

	private void to2Name(String n) {
		name = n;

		while (name.length() < 2)
			name = "_" + name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + currentPos;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Counter other = (Counter) obj;
		if (currentPos != other.currentPos)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Counter [name=" + name + ", x=" + x + ", y=" + y
				+ ", currentX=" + currentX + ", currentY=" + currentY
				+ ", currentPos=" + currentPos + "]";
	}
}