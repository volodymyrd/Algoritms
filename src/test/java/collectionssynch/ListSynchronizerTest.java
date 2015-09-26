package collectionssynch;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import static org.junit.Assert.*;

public class ListSynchronizerTest {

	@Test
	public void shouldFoundAllInsertsUpdateRemoves() {
		List<Integer> l = Stream.of(1, 2, 3, 4, 5).collect(Collectors.toList());
		List<Integer> r = Stream.of(2, 3, 4, 5, 9).collect(Collectors.toList());

		ListSynchronizer<Integer, Integer> ls = new ListSynchronizer<>((le, re) -> Objects.equals(le, re));
		ls.synchronize(l, r);

		assertEquals(Stream.of(1).collect(Collectors.toList()), ls.getInsertItems());
		assertEquals(
				Stream.of(new ListSynchronizer.Pair(2, 2), new ListSynchronizer.Pair(3, 3),
						new ListSynchronizer.Pair(4, 4), new ListSynchronizer.Pair(5, 5)).collect(Collectors.toList()),
				ls.getUpdateItems());
		assertEquals(Stream.of(9).collect(Collectors.toList()), ls.getRemoveItems());
	}
}