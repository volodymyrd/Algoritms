package pathfinding.algorithms;

import pathfinding.CartesianNode;
import pathfinding.FifteenNode;
import pathfinding.HeuristicFunction;
import pathfinding.Node;
import pathfinding.Path;
import pathfinding.SearchAlgorithm;
import pathfinding.Solution;
import pathfinding.heuristic.ManhattanDistance;

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

		if (current.equals(goal)) {
			return current;
		}

		exploredNodes++;

		if (exploredNodes % 10000 == 0) {
			// System.err.println("explored nodes for this treshold
			// "+currentCostBound+
			// " : "+exploredNodes);
		}

		//System.out.println(current);
		N[] children = current.getChildren();

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

	public static void main(String[] args) {
		HeuristicFunction<CartesianNode> h = new ManhattanDistance();

		FifteenNode goal = FifteenNode.getGoalState(4);
		System.out.println("goal:" + goal);
		FifteenNode start = new FifteenNode(4, new byte[]{15,9,13,5,10,8,4,11,14,6,7,3,2,12,0,1});//FifteenNode.createRandom(4);
		System.out.println("init:" + start);

		IDAStar<CartesianNode> idas = new IDAStar<>(h);

		Solution s = idas.resolve(start, goal);

		System.out.println("time:" + s.getTime());
		System.out.println("nodes:" + s.getExploredNode());
		System.out.println("nodes:" + s.getPath().getNodes().length);
		for(Node n : s.getPath().getNodes()){
			System.out.println("act" + ((FifteenNode)n).getAction());
			System.out.println(n);
		}
	}
}