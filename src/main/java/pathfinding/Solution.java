package pathfinding;

public class Solution {
	private Path path;
	private long time;
	private long exploredNode;
	private boolean timeout;

	public Solution() {
	}

	/**
	 * @return the optimal path to reach to goal
	 */
	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	/**
	 * @return the time in nanoseconds take by the algorithm to find the
	 *         solution
	 */
	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * @return the number of nodes explored by the algorithm
	 */
	public long getExploredNode() {
		return exploredNode;
	}

	public void setExploredNode(long exploredNode) {
		this.exploredNode = exploredNode;
	}

	public boolean isTimeoutOccured() {
		return timeout;
	}

	public void setTimeoutOccured(boolean timeout) {
		this.timeout = timeout;
	}
}