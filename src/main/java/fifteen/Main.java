package fifteen;

public class Main {
	
	public static void main(String[] args) {
		Fifteen f = Fifteen.newGame();
		f.printSet();
		
		//System.out.println(f.getSet());
		
		f.nextStep();		
		f.printSet();
	}
}