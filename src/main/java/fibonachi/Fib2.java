package fibonachi;

import java.math.BigDecimal;

public class Fib2 {

	private BigDecimal calc(int n) {
		if (n == 0)
			return new BigDecimal(0);
		else if (n == 1)
			return new BigDecimal(1);
		else {
			BigDecimal[] f = new BigDecimal[n + 1];
			f[0] = new BigDecimal(0);
			f[1] = new BigDecimal(1);

			for (int i = 2; i <= n; i++)
				f[i] = f[i - 1].add(f[i - 2]);

			return f[n];
		}

	}

	public static void main(String[] args) {
		long t1 = System.currentTimeMillis();
		System.out.println("Result: " + new Fib2().calc(10000));
		System.out.println("Alg worked: " + (System.currentTimeMillis() - t1) + " ms.");
	}

}