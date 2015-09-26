package coursera.alg_part1.union_find;

import org.junit.Test;
import static org.junit.Assert.*;

public class QuickFindTest {

	@Test
	public void QFshouldEqualQF2() {
		QuickFind qf = new QuickFind(10);
		QuickFind2 qf2 = new QuickFind2(10);

		qf.union(1, 2);
		qf.union(3, 4);
		qf.union(5, 6);
		qf.union(7, 8);
		qf.union(7, 9);
		qf.union(2, 8);
		qf.union(0, 5);
		qf.union(1, 9);

		qf2.union(1, 2);
		qf2.union(3, 4);
		qf2.union(5, 6);
		qf2.union(7, 8);
		qf2.union(7, 9);
		qf2.union(2, 8);
		qf2.union(0, 5);
		qf2.union(1, 9);

		assertEquals(qf.groups(), qf2.groups());
	}
}