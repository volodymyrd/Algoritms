package fibonachi;

import java.math.BigDecimal;

public class Fib1 {

	private BigDecimal calc(long n) {
		if (n == 0)
			return new BigDecimal(0);
		else if (n == 1)
			return new BigDecimal(1);
		else
			return calc(n - 1).add(calc(n - 2));
	}

	public static void main(String[] args) {
		long t1 = System.currentTimeMillis();
		System.out.println("Result: " + new Fib1().calc(45));
		System.out.println("Alg worked: " + (System.currentTimeMillis() - t1) + " ms.");
	}

}