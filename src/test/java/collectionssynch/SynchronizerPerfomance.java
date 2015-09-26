package collectionssynch;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class SynchronizerPerfomance {
	private static List<Integer> l;
	private static List<Integer> r;

	@BeforeClass
	public static void setUp() {
		Set<Integer> l1 = new HashSet<>();
		Set<Integer> r1 = new HashSet<>();
		Random random1 = new Random(1);
		Random random2 = new Random(5);
		for (int i = 0; i < 100; i++) {
			l1.add(random1.nextInt(105));
			r1.add(random2.nextInt(105));
		}

		l = new ArrayList<>(l1);
		r = new ArrayList<>(r1);

		System.out.println(l);
		System.out.println(r);
	}

	@Test
	public void listTest() {
		long t = System.currentTimeMillis();

		ListSynchronizer<Integer, Integer> ls = new ListSynchronizer<>((le, re) -> Objects.equals(le, re));
		ls.synchronize(l, r);

		System.out.println("insert:" + ls.getInsertItems().size());
		System.out.println("update:" + ls.getUpdateItems().size());
		System.out.println("remove:" + ls.getRemoveItems().size());

		System.out.println("list test: " + (System.currentTimeMillis() - t) / 1000.0 + " sec.");
	}

	@Test
	public void list2Test() {
		long t = System.currentTimeMillis();

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

		System.out.println("insert:" + ls.getInsertItems().size());
		System.out.println("update:" + ls.getUpdateItems().size());
		System.out.println("remove:" + ls.getRemoveItems().size());

		System.out.println("list2 test: " + (System.currentTimeMillis() - t) / 1000.0 + " sec.");
	}

	//@Ignore
	@Test
	public void collectionTest() {
		long t = System.currentTimeMillis();

		CollectionsSynchronizer<Integer, Integer> ls = new CollectionsSynchronizer<>(
				(le, re) -> Objects.equals(le, re));

		System.out.println("insert:" + ls.itemsToInsert(l, r).size());
		System.out.println("update:" + ls.itemsToUpdate(l, r).size());
		System.out.println("remove:" + ls.itemsToDelete(l, r).size());

		System.out.println("collection test: " + (System.currentTimeMillis() - t) / 1000.0 + " sec.");
	}
}