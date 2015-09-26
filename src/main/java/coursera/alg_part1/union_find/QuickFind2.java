package coursera.alg_part1.union_find;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuickFind2 {

	private int[] id;

	public QuickFind2(int N) {
		id = new int[N];
		for (int i = 0; i < N; i++)
			id[i] = i;
	}

	public boolean connected(int p, int q) {
		return root(p) == root(q);
	}

	public void union(int p, int q) {
		int rootP = root(p);
		int rootQ = root(q);
		id[rootP] = rootQ;
	}

	private int root(int i) {
		while (i != id[i])
			i = id[i];

		return i;
	}

	@Override
	public String toString() {
		return Arrays.toString(id);
	}

	public Map<Integer, List<Integer>> groups() {
		Map<Integer, List<Integer>> gr = new HashMap<>();

		for (int i = 0; i < id.length; i++) {
			int root = root(id[i]);
			if (gr.containsKey(root)) {
				gr.get(root).add(i);
			} else {
				List<Integer> e = new ArrayList<>();
				e.add(i);
				gr.put(root, e);
			}
		}
		return gr;
	}

	public static void main(String[] args) {
	}
}