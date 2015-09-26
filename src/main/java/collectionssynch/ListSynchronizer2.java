package collectionssynch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;

public class ListSynchronizer2<L, R, K> {
	private Comparator<K> comparator;

	public static class Pair<Lt, Rt> {
		private final Lt left;
		private final Rt right;

		public Pair(Lt left, Rt right) {
			this.left = left;
			this.right = right;
		}

		public Lt getLeft() {
			return left;
		}

		public Rt getRight() {
			return right;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((left == null) ? 0 : left.hashCode());
			result = prime * result + ((right == null) ? 0 : right.hashCode());
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
			Pair<Lt, Rt> other = (Pair<Lt, Rt>) obj;
			if (left == null) {
				if (other.left != null)
					return false;
			} else if (!left.equals(other.left))
				return false;
			if (right == null) {
				if (other.right != null)
					return false;
			} else if (!right.equals(other.right))
				return false;
			return true;
		}
	}

	private List<L> insertItems = new ArrayList<>();
	private List<Pair<L, R>> updateItems = new ArrayList<>();
	private List<R> removeItems = new ArrayList<>();

	public List<L> getInsertItems() {
		return insertItems;
	}

	public List<Pair<L, R>> getUpdateItems() {
		return updateItems;
	}

	public List<R> getRemoveItems() {
		return removeItems;
	}

	public ListSynchronizer2(Comparator<K> comparator) {
		this.comparator = comparator;
	}
	
	public void synchronize(final SortedMap<K, L> leftItems, final SortedMap<K, R> rightItems) {
		List<K> rKeyList = new ArrayList<>(rightItems.keySet());
		List<K> lKeyList = new ArrayList<>(leftItems.keySet());

		leftItems.forEach((k, l) -> {
			int i = Collections.binarySearch(rKeyList, k, comparator);
			if (i > -1)
				updateItems.add(new Pair<L, R>(l, rightItems.get(rKeyList.get(i))));
			else
				insertItems.add(l);
		});

		rightItems.forEach((k, r) -> {
			int i = Collections.binarySearch(lKeyList, k, comparator);
			if (i < 0)
				removeItems.add(r);
		});
	}
}