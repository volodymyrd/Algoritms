package pathfinding;

public interface HeuristicFunction<N extends Node> {
	int h(N n);
}