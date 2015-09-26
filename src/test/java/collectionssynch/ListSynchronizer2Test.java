package collectionssynch;

import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import static org.junit.Assert.*;

public class ListSynchronizer2Test {

	@Test
	public void shouldFoundAllInsertsUpdateRemoves() {
		List<Integer> l = Stream.of(1, 2, 3, 4, 5).collect(Collectors.toList());
		List<Integer> r = Stream.of(2, 3, 4, 5, 9).collect(Collectors.toList());

		SortedMap<Integer, Integer> lm = new TreeMap<>();
		SortedMap<Integer, Integer> rm = new TreeMap<>();

		ListSynchronizer2<Integer, Integer, Integer> ls = new ListSynchronizer2<>(new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		});

		l.forEach(e -> lm.put(e, e));
		r.forEach(e -> rm.put(e, e));

		ls.synchronize(lm, rm);

		assertEquals(Stream.of(1).collect(Collectors.toList()), ls.getInsertItems());
		assertEquals(Stream
				.of(new ListSynchronizer2.Pair(2, 2), new ListSynchronizer2.Pair(3, 3),
						new ListSynchronizer2.Pair(4, 4), new ListSynchronizer2.Pair(5, 5))
				.collect(Collectors.toList()), ls.getUpdateItems());
		assertEquals(Stream.of(9).collect(Collectors.toList()), ls.getRemoveItems());
	}
}