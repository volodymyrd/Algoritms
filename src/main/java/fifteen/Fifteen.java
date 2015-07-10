package fifteen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Fifteen {

	public final static int MAX_X = 4;
	public final static int MAX_Y = 4;

	private final List<Counter> set = new ArrayList<>(MAX_X * MAX_Y);
	private final Counter empty;

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

		empty = new Counter(index.get(0), -1, "__");
		set.add(empty);
	}

	// private Fifteen(List<String> initSet) {
	//
	// }

	public static Fifteen newGame() {
		return new Fifteen();
	}

	public boolean nextStep() {
		int op = empty.getCurrentPos();
		int np;

		if ((np = empty.Top()) != -1) {
			Counter c = set.get(np);
			c.Down();
			Collections.swap(set, op, np);
		}

		return true;
	}

	public List<Counter> getSet() {
		Collections.sort(set);

		return set;
	}

	public void printSet() {
		Collections.sort(set);

		printSubSet(1);

		System.out.println("");
	}

	private void printSubSet(int row) {
		System.out.println("");

		set.subList((row - 1) * MAX_X, row * MAX_X).stream().forEach(e -> {
			System.out.print(e.getName());
		});

		if (row < MAX_Y)
			printSubSet(row + 1);
	}
}