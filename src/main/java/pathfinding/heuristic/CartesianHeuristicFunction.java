package pathfinding.heuristic;

import pathfinding.CartesianNode;
import pathfinding.HeuristicFunction;

public abstract class CartesianHeuristicFunction implements HeuristicFunction<CartesianNode> {

	@Override
	public int h(CartesianNode n) {
		int cachedValue = n.getH();

		if (cachedValue == -1) {
			cachedValue = calculate(n);

			n.setH(cachedValue);
		}

		return cachedValue;
	}

	protected abstract int calculate(CartesianNode node);
}