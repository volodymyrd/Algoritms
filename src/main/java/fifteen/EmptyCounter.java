package fifteen;

public class EmptyCounter extends Counter {

	public EmptyCounter(int currentPos) {
		super(currentPos, -1, "__");
	}

	@Override
	public EmptyCounter copy() {
		EmptyCounter c = new EmptyCounter(currentPos);

		return c;
	}
}
