package coursera.alg_part1.union_find;

public class Calc {

	public static void main(String[] args) {
		int a[] = { 1000, 2000, 4000, 8000, 16000, 32000, 64000 };

		for (int i : a) {
			System.out.println(i + ": " + T(i));
		}
	}

	private static double T(int N) {
		return 6.25 * Math.pow(10, -9) * N * N;
	}

}