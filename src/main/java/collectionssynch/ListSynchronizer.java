package collectionssynch;

import java.util.ArrayList;
import java.util.List;

public class ListSynchronizer<L, R> {
	public interface ItemComparator<L, R> {
		boolean itemsEquals(L leftItem, R rightItem);
	}

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

	private final ItemComparator<L, R> itemComparator;

	private List<L> insertItems = new ArrayList<>();
	private List<Pair<L, R>> updateItems = new ArrayList<>();
	private List<R> removeItems = new ArrayList<>();

	public ListSynchronizer(ItemComparator<L, R> itemComparator) {
		this.itemComparator = itemComparator;
	}

	public List<L> getInsertItems() {
		return insertItems;
	}

	public List<Pair<L, R>> getUpdateItems() {
		return updateItems;
	}

	public List<R> getRemoveItems() {
		return removeItems;
	}

	public void synchronize(final List<L> leftItems, final List<R> rightItems) {
		if (isEmpty(leftItems) && isEmpty(rightItems))
			return;
		else if (!isEmpty(leftItems) && isEmpty(rightItems)) {
			insertItems = new ArrayList<>(leftItems);

			return;
		} else if (isEmpty(leftItems) && !isEmpty(rightItems)) {
			removeItems = new ArrayList<>(removeItems);

			return;
		}

		List<R> rightItemsCopy = new ArrayList<>(rightItems.size());

		leftItems.stream().forEach(l -> {
			boolean found = false;
			for (R r : rightItems) {
				if (itemComparator.itemsEquals(l, r)) {
					found = true;
					Pair<L, R> p = new Pair<>(l, r);
					updateItems.add(p);
					rightItemsCopy.add(r);

					break;
				}

				removeItems.add(r);
			}

			if (!found)
				insertItems.add(l);
		});

		removeItems.removeAll(rightItemsCopy);
	}

	private boolean isEmpty(List<?> l) {
		return l == null || l.size() == 0;
	}
}