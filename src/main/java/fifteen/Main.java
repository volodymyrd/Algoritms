package fifteen;

public class Main {

	public static void main(String[] args) {
		Fifteen f = Fifteen.newGame();
		f.printSet();

		// System.out.println(f.getSet());

		// f.nextStep();
		int i = 0;
		for (;i < 500; i++) {
			if (f.nextStep()) {
				//System.out.println("next step:");
				//f.printSet();
			} else
				break;
		}

		System.out.println("finish for " + i + " steps");
		f.printSet();
	}
}