package pathfinding.heuristic;

import pathfinding.CartesianNode;

public class ManhattanDistance extends CartesianHeuristicFunction {

	@Override
	protected int calculate(CartesianNode node) {
		int counter = 0;

		byte[] values = node.getValues();
		int dimension = node.getDimension();

		for (int i = 0; i < values.length; i++) {
			int value = values[i];

			if (value == 0) {
				continue;
			}

			int row = i / dimension;
			int column = i % dimension;
			int expectedRow = (value - 1) / dimension;
			int expectedColumn = (value - 1) % dimension;

			int difference = Math.abs(row - expectedRow)
					+ Math.abs(column - expectedColumn);

			counter += difference;
		}

		return counter;
	}
}