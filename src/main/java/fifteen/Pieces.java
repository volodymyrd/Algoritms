package fifteen;

public class Pieces {
	private int currentX;
	private int currentY;
	private final int x;
	private final int y;
	private final String name;

	public Pieces(int currentX, int currentY, int x, int y, String name) {
		this.currentX = currentX;
		this.currentY = currentY;
		this.x = x;
		this.y = y;
		this.name = name;
	}

	public void Top() {
		if (currentY < Main.MAX_Y)
			currentY += 1;
	}

	public boolean Down() {

		currentY -= 1;

		return true;
	}

	public boolean Left() {

		currentX -= 1;

		return true;
	}

	public boolean Right() {

		currentX += 1;

		return true;
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
}