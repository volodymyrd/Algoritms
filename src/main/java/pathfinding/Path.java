package pathfinding;

public class Path {
	private Node[] nodes;

	public static Path fromEndNode(Node endNode) {
		if (endNode == null) {
			return null;
		}

		Path p = new Path(endNode.getCost() + 1);

		Node[] nodes = p.getNodes();
		nodes[endNode.getCost()] = endNode;

		Node parent = endNode.getParent();
		Node current = null;
		while (parent != null) {
			current = parent;
			nodes[current.getCost()] = current;
			parent = current.getParent();
		}

		return p;
	}

	public Path() {
	}

	public Path(int lenght) {
		nodes = new Node[lenght];
	}

	public int length() {
		return nodes.length;
	}

	public Node[] getNodes() {
		return nodes;
	}
}