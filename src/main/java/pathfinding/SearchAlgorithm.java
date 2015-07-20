package pathfinding;

/**
 * Interface for all algorithms
 *
 */
public interface SearchAlgorithm<N extends Node> {
	public Solution resolve(N start, N goal);
}