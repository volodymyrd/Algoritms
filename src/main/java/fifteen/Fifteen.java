package fifteen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fifteen.Counter.StepType;

public class Fifteen {

	public final static int MAX_X = 4;
	public final static int MAX_Y = 4;

	private List<Counter> set = new ArrayList<>(MAX_X * MAX_Y);
	private final Deque<List<Counter>> history = new LimitedQueue<>(5);

	private Fifteen() {
		List<Integer> index = new ArrayList<>();
		for (int i = 0; i < MAX_X * MAX_Y; i++)
			index.add(i);

		Random r = new Random();

		for (int i = 1; i < MAX_X * MAX_Y; i++) {
			Counter c;

			do {
				int v = r.nextInt(MAX_X * MAX_Y);

				c = new Counter(v, i - 1, String.valueOf(i));
			} while (set.contains(c));

			set.add(c);

			index.remove(Integer.valueOf(c.getCurrentPos()));
		}

		set.add(new EmptyCounter(index.get(0)));
	}

	// private Fifteen(List<String> initSet) {
	//
	// }

	public static Fifteen newGame() {
		return new Fifteen();
	}

	public boolean nextStep() {

		List<Counter> prev = history.peek();

		List<Counter> topSet = step(StepType.TOP);
		if (topSet != null && prev != null && equals(prev, topSet))
			topSet = set;

		List<Counter> downSet = step(StepType.DOWN);
		if (downSet != null && prev != null && equals(prev, downSet))
			downSet = set;

		List<Counter> leftSet = step(StepType.LEFT);
		if (leftSet != null && prev != null && equals(prev, leftSet))
			leftSet = set;

		List<Counter> rightSet = step(StepType.RIGHT);
		if (rightSet != null && prev != null && equals(prev, rightSet))
			rightSet = set;

		long currentCost = cost(findEmpty(set), set);
		long topCost = cost(findEmpty(topSet), topSet);
		long downCost = cost(findEmpty(downSet), downSet);
		long leftCost = cost(findEmpty(leftSet), leftSet);
		long rightCost = cost(findEmpty(rightSet), rightSet);

		List<Long> costs = Stream.of(currentCost, topCost, downCost, leftCost,
				rightCost).collect(Collectors.toList());

		System.out.println("costs:" + costs);

		history.push(set);

		int m = min(costs);

		switch (m) {
		case 0:
			costs = Stream.of(topCost, downCost, leftCost, rightCost).collect(
					Collectors.toList());

			m = min(costs);
			switch (m) {
			case 0:
				set = topSet;
				if (topCost == 0)
					return false;
				break;
			case 1:
				set = downSet;
				if (downCost == 0)
					return false;
				break;
			case 2:
				set = leftSet;
				if (leftCost == 0)
					return false;
				break;
			case 3:
				set = rightSet;
				if (rightCost == 0)
					return false;
				break;
			}
			break;
		case 1:
			set = topSet;
			if (topCost == 0)
				return false;
			break;
		case 2:
			set = downSet;
			if (downCost == 0)
				return false;
			break;
		case 3:
			set = leftSet;
			if (leftCost == 0)
				return false;
			break;
		case 4:
			set = rightSet;
			if (rightCost == 0)
				return false;
			break;
		}

		return true;
	}

	private int min(List<Long> list) {
		long m = Collections.min(list);

		for (int i = 0; i < list.size(); i++)
			if (m == list.get(i))
				return i;

		return -1;
	}

	private boolean equals(List<Counter> s1, List<Counter> s2) {

		Collections.sort(s1);
		Collections.sort(s2);
		for (int i = 0; i < s1.size(); i++)
			if (!s1.get(i).equals(s2.get(i)))
				return false;

		return true;
	}

	private List<Counter> step(StepType type) {

		List<Counter> copySet = copy();

		EmptyCounter empty = findEmpty(copySet);

		int op = empty.getCurrentPos();
		int np;

		if ((np = empty.step(type)) != -1) {
			Counter c = copySet.get(np);
			c.antiStep(type);
			Collections.swap(copySet, op, np);

			return copySet;
		} else
			return set;
	}

	private List<Counter> copy() {
		List<Counter> s = new ArrayList<>(MAX_X * MAX_Y);
		for (Counter c : set)
			s.add(c.copy());

		return s;
	}

	private EmptyCounter findEmpty(List<Counter> set) {
		return (EmptyCounter) set.stream()
				.filter(e -> e instanceof EmptyCounter).findFirst().get();
	}

	private long cost(EmptyCounter empty, List<Counter> set) {
		long c = 0;

		for (Counter e : set) {
			// c += (e.currentCost() + e.emptyCost(empty));
			c += e.currentCost();
		}

		return c;
	}

	// private void roolback() {
	// try {
	// set = history.pop();
	// } catch (NoSuchElementException ex) {
	// }
	// }

	public List<Counter> getSet() {
		Collections.sort(set);

		return set;
	}

	public void printSet() {
		Collections.sort(set);

		printSubSet(set, 1);

		System.out.println("");
	}

	public void printSet(List<Counter> set) {
		Collections.sort(set);

		printSubSet(set, 1);

		System.out.println("");
	}

	private void printSubSet(List<Counter> set, int row) {
		System.out.println("");

		set.subList((row - 1) * MAX_X, row * MAX_X).stream().forEach(e -> {
			System.out.print(e.getName());
		});

		if (row < MAX_Y)
			printSubSet(set, row + 1);
	}
}