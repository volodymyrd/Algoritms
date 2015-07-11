package fifteen;

import java.util.LinkedList;

public class LimitedQueue<E> extends LinkedList<E> {
	private static final long serialVersionUID = 1L;

	private int limit;

	public LimitedQueue(int limit) {
		this.limit = limit;
	}

	@Override
	public void push(E o) {
		super.push(o);
		while (size() > limit) {
			super.removeLast();
		}
	}
}