package astar.fifteen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import astar.Astar;
import astar.State;

public class Fifteen {
	public static void main(String[] args) {
		int size = sideSize * sideSize;
		terminateField = getTerminalState(sideSize, size);

		FifteenRules rules = new FifteenRules2(sideSize, terminateField);
		FifteenState startState = new FifteenState(null, sideSize);

		if (startField == null) {
			startField = generateStartState(rules, stepCount);
		}

		startState.setField(startField);

		System.out.println(startState.toString());

		if (!FifteenState.checkState(startField, sideSize)) {
			System.out
					.println("\nДанное состояние нельзя привести к терминальному.\n"
							+ "См. http://ru.wikipedia.org/wiki/Пятнашки\n");
			System.exit(1);
		}

		Astar<FifteenState, FifteenRules> astar = new Astar<FifteenState, FifteenRules>(
				rules);
		long time = System.currentTimeMillis();
		Collection<State> res = astar.search(startState);
		time = System.currentTimeMillis() - time;

		if (res == null) {
			System.out.println("Solution not found.");

			return;
		} else {
			for (State s : res) {
				System.out.println(s.toString());
			}
		}
	}

	/**
	 * Генерирует начальное состояние путем swapCount начальных перестановок.
	 * 
	 * @param rules
	 * @param swapCount
	 *            количество перестановок.
	 * @return сгенерированное начальное состояние.
	 */
	private static byte[] generateStartState1(FifteenRules rules, int swapCount) {
		int stepCount = swapCount;
		byte[] startState = rules.getTerminateState();
		int[] actions = rules.getActions();
		Random r = new Random();
		while (stepCount > 0) {
			int j = r.nextInt(actions.length);
			byte[] state = rules.doAction(startState, actions[j]);
			if (state != null) {
				startState = state;
				stepCount--;
			}
		}
		return startState;
	}

	private static byte[] generateStartState(FifteenRules rules, int swapCount) {
		// int stepCount = swapCount;
		List<Byte> startState = new ArrayList<>();
		for (byte b : rules.getTerminateState())
			startState.add(b);
		//
		// int[] actions = rules.getActions();
		// Random r = new Random();
		// while (stepCount > 0) {
		// int j = r.nextInt(actions.length);
		// byte[] state = rules.doAction(startState, actions[j]);
		// if (state != null) {
		// startState = state;
		// stepCount--;
		// }
		// }
		Collections.shuffle(startState);
		byte[] s = new byte[startState.size()];
		for (int i = 0; i < startState.size(); i++)
			s[i] = startState.get(i);

		return s;
	}

	/**
	 * Генерирует терминальное состояние, как упорядоченную последовательность
	 * чисел.
	 * 
	 * @param sideSize
	 * @param size
	 * @return
	 */
	private static byte[] getTerminalState(int sideSize, int size) {
		if (terminateField == null) {
			terminateField = new byte[size];
			byte k = 0;
			for (int i = 0; i < sideSize; i++) {
				for (int j = 0; j < sideSize; j++) {
					terminateField[j + i * sideSize] = ++k;
				}
			}
			terminateField[size - 1] = 0;
		}
		return terminateField;
	}

	private static byte[] startField;

	private static byte[] terminateField;

	private static int stepCount = 10;

	private static int sideSize = 4;
}