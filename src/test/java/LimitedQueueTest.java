import java.util.Deque;

import static org.junit.Assert.*;
import org.junit.Test;

import fifteen.LimitedQueue;

public class LimitedQueueTest {

	@Test
	public void shouldRemoveLastElement() {
		Deque<String> d = new LimitedQueue<>(3);

		d.push("1");
		d.push("2");
		d.push("3");
		d.push("4");

		assertEquals(3, d.size());
		assertEquals("2", d.peekLast());
	}
}