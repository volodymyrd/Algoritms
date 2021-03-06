package coursera.alg_part1.union_find;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuickFind {

	private int[] id;

	public QuickFind(int N) {
		id = new int[N];
		for (int i = 0; i < N; i++)
			id[i] = i;
	}

	public boolean connected(int p, int q) {
		return id[p] == id[q];
	}

	public void union(int p, int q) {
		int pid = id[p];
		int qid = id[q];
		for (int i = 0; i < id.length; i++)
			if (id[i] == pid)
				id[i] = qid;
	}

	public Map<Integer, List<Integer>> groups() {
		Map<Integer, List<Integer>> gr = new HashMap<>();

		for (int i = 0; i < id.length; i++) {
			if (gr.containsKey(id[i])) {
				gr.get(id[i]).add(i);
			} else {
				List<Integer> e = new ArrayList<>();
				e.add(i);
				gr.put(id[i], e);
			}
		}
		return gr;
	}

	@Override
	public String toString() {
		return Arrays.toString(id);
	}

	public static void main(String[] args) {
	}
}