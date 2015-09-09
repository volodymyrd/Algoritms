package coursera.alg_part1.union_find;

import java.util.Arrays;

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

	public static void main(String[] args) {
		int[] a = { 1, 2, 3 };

		for (int i : a)
			i = i + 1;

		System.out.println(Arrays.toString(a));
	}
}