package pathfinding.algorithms;

import pathfinding.HeuristicFunction;
import pathfinding.Node;
import pathfinding.Path;
import pathfinding.SearchAlgorithm;
import pathfinding.Solution;

/**
 * Iterative deepening A*
 * 
 * https://github.com/jDramaix/SlidingPuzzle
 */
public class IDAStar<N extends Node> implements SearchAlgorithm<N> {
	private long startTime;
	private long exploredNodes;
	private int nextCostBound;
	private HeuristicFunction<N> heuristic;

	public IDAStar(HeuristicFunction<N> heuristic) {
		this.heuristic = heuristic;
	}

	@Override
	public Solution resolve(N start, N goal) {

		nextCostBound = heuristic.h(start);

		N solutionNode = null;
		startTime = System.nanoTime();
		exploredNodes = 0;

		while (solutionNode == null) {

			int currentCostBound = nextCostBound;

			solutionNode = depthFirstSearch(start, currentCostBound, goal);

			nextCostBound += 2;

		}

		long ellapsedTime = System.nanoTime() - startTime;

		Solution solution = new Solution();
		solution.setPath(Path.fromEndNode(solutionNode));
		solution.setTime(ellapsedTime);
		solution.setExploredNode(exploredNodes);

		return solution;
	}

	private N depthFirstSearch(N current, int currentCostBound, N goal) {

		if (current.getState().equals(goal)) {
			return current;
		}

		exploredNodes++;

		if (exploredNodes % 10000 == 0) {
			// System.err.println("explored nodes for this treshold "+currentCostBound+
			// " : "+exploredNodes);
		}

		N[] children = getChildren(current);

		for (N next : children) {

			int value = next.getCost() + heuristic.h(next);

			if (value <= currentCostBound) {
				N result = depthFirstSearch(next, currentCostBound, goal);
				
				if (result != null) {
					return result;
				}
			}

		}
		return null;

	}
}