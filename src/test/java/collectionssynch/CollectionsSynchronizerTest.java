package collectionssynch;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class CollectionsSynchronizerTest {
	@Test
	public void shouldFoundAllInsertsUpdateRemoves() {
		List<Integer> l = Stream.of(1, 2, 3, 4, 5).collect(Collectors.toList());
		List<Integer> r = Stream.of(2, 3, 4, 5, 9).collect(Collectors.toList());

		CollectionsSynchronizer<Integer, Integer> ls = new CollectionsSynchronizer<>(
				(le, re) -> Objects.equals(le, re));
		// ls.synchronize(l, r);

		assertEquals(Stream.of(1).collect(Collectors.toList()), ls.itemsToInsert(l, r));
		assertEquals(Stream
				.of(new CollectionsSynchronizer.Pair(2, 2), new CollectionsSynchronizer.Pair(3, 3),
						new CollectionsSynchronizer.Pair(4, 4), new CollectionsSynchronizer.Pair(5, 5))
				.collect(Collectors.toList()), ls.itemsToUpdate(l, r));
		assertEquals(Stream.of(9).collect(Collectors.toList()), ls.itemsToDelete(l, r));
	}

}
