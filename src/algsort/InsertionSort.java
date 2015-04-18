package algsort;

import java.util.Arrays;

public class InsertionSort {

	public static void main(String[] args) {

		System.out.println(Arrays.toString(insertionSort(args)));
	}

	private static <T extends Comparable<T>> T[] insertionSort(T[] args) {
		if (args.length < 1)
			throw new IllegalArgumentException("Length must be greater than 0");

		for (int j = 1; j < args.length; j++) {
			T key = args[j];
			int i = j - 1;

			while (i > -1 && args[i].compareTo(key) > 0) {
				args[i + 1] = args[i];
				i -= 1;
			}

			args[i + 1] = key;
		}

		return args;
	}
}