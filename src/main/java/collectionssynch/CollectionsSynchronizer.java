package collectionssynch;

import java.util.*;
import java.util.stream.Collectors;

public class CollectionsSynchronizer<L, R> {
    public interface ItemComparator<L, R> {
        boolean itemsEquals(L leftItem, R rightItem);
    }

    public interface ItemInserter<L> {
        void insertElement(L item);
    }
    public interface ItemUpdater<L, R> {
        void updateItem(L leftItem, R rightItem);
    }
    public interface ItemRemover<R> {
        void removeItem(R item);
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

    private ItemInserter<L> itemInserter;
    private ItemUpdater<L, R> itemUpdater;
    private ItemRemover<R> itemRemover;

    public CollectionsSynchronizer(ItemComparator<L,R> itemComparator) {
        this.itemComparator = itemComparator;
    }

    public CollectionsSynchronizer(ItemInserter<L> itemInserter, ItemUpdater<L, R> itemUpdater,
                                   ItemRemover<R> itemRemover, ItemComparator<L, R> itemComparator) {
        this.itemInserter = itemInserter;
        this.itemUpdater = itemUpdater;
        this.itemRemover = itemRemover;
        this.itemComparator = itemComparator;
    }

    public void synchronize(List<L> leftItems, List<R> rightItems) {
        if (itemInserter == null || itemUpdater == null || itemRemover == null) {
            throw new NullPointerException("Set item workers");
        }
        final List<L> leftNotNullItems = (leftItems == null) ? Collections.EMPTY_LIST: leftItems;
        final List<R> rightNotNullItems = (rightItems == null) ? Collections.EMPTY_LIST: rightItems;

        leftNotNullItems.stream().forEach(l -> {
            boolean isNewElement = rightNotNullItems.stream()
                    .filter(r -> itemComparator.itemsEquals(l, r))
                    .count() == 0;
            if (isNewElement) {
                itemInserter.insertElement(l);
            }

            rightNotNullItems.stream().filter(r -> itemComparator.itemsEquals(l, r))
                    .forEach(r -> itemUpdater.updateItem(l, r));
        });

        rightNotNullItems.stream()
                .filter(r ->
                        leftNotNullItems.stream().filter(l -> itemComparator.itemsEquals(l, r)).count() == 0)
                .forEach(itemRemover::removeItem);
    }

    public List<L> itemsToInsert(List<L> leftItems, List<R> rightItems) {
        final List<L> leftNotNullItems = (leftItems == null) ? Collections.EMPTY_LIST: leftItems;
        final List<R> rightNotNullItems = (rightItems == null) ? Collections.EMPTY_LIST: rightItems;
        return leftNotNullItems.stream()
                .filter(l -> rightNotNullItems.stream().filter(r -> itemComparator.itemsEquals(l, r)).count() == 0)
                .collect(Collectors.toList());
    }

    public List<R> itemsToDelete(List<L> leftItems, List<R> rightItems) {
        final List<L> leftNotNullItems = (leftItems == null) ? Collections.EMPTY_LIST: leftItems;
        final List<R> rightNotNullItems = (rightItems == null) ? Collections.EMPTY_LIST: rightItems;
        return rightNotNullItems.stream()
                .filter(r -> leftNotNullItems.stream().filter(l -> itemComparator.itemsEquals(l, r)).count() == 0)
                .collect(Collectors.toList());
    }

    public List<Pair<L, R>> itemsToUpdate(List<L> leftItems, List<R> rightItems) {
        final List<L> leftNotNullItems = (leftItems == null) ? Collections.EMPTY_LIST: leftItems;
        final List<R> rightNotNullItems = (rightItems == null) ? Collections.EMPTY_LIST: rightItems;
        List<Pair<L, R>> itemsToUpdate = new ArrayList<>();
        leftNotNullItems.stream()
                .forEach(l -> rightNotNullItems.stream()
                        .filter(r -> itemComparator.itemsEquals(l, r))
                .forEach(r -> itemsToUpdate.add(new Pair<>(l, r))));
        return itemsToUpdate;
    }

    public void setItemInserter(ItemInserter<L> itemInserter) {
        this.itemInserter = itemInserter;
    }

    public void setItemUpdater(ItemUpdater<L, R> itemUpdater) {
        this.itemUpdater = itemUpdater;
    }

    public void setItemRemover(ItemRemover<R> itemRemover) {
        this.itemRemover = itemRemover;
    }
}
