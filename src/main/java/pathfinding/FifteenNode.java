package pathfinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FifteenNode extends CartesianNode {

	public enum Move {

		UP(0, -1), DOWN(0, 1), RIGHT(1, 0), LEFT(-1, 0);

		private int horizontalMove;
		private int verticalMove;

		private Move(int horizontal, int vertical) {
			this.horizontalMove = horizontal;
			this.verticalMove = vertical;
		}

		public int getHorizontalMove() {
			return horizontalMove;
		}

		public int getVerticalMove() {
			return verticalMove;
		}

		public CellLocation getNextCellLocation(CellLocation currentLocation) {
			return new CellLocation(getNextRow(currentLocation.getRowIndex()),
					getNextColumn(currentLocation.getColumnIndex()));
		}

		private int getNextRow(int currentRow) {
			return currentRow + verticalMove;
		}

		private int getNextColumn(int currentColumn) {
			return currentColumn + horizontalMove;
		}

		public Move getInverse() {
			switch (this) {
			case UP:
				return DOWN;
			case DOWN:
				return UP;
			case LEFT:
				return RIGHT;
			case RIGHT:
				return LEFT;

			}
			return null;
		}
	}

	public class Action {

		private Move m;
		private CellLocation cell;

		public Action() {
		}

		public Action(CellLocation cell, Move m) {
			this.m = m;
			this.cell = cell;
		}

		public CellLocation getCellLocation() {
			return cell;
		}

		public Move getMove() {
			return m;
		}

		/**
		 * Apply this action to a node and return the new node
		 */
		public FifteenNode applyTo(final FifteenNode s) {
			byte value = s.getCellValue(cell);

			CellLocation nextCell = m.getNextCellLocation(cell);

			FifteenNode newState = new FifteenNode(s.getDimension(), s.getValues());
			newState.setCellValue(nextCell, value);
			newState.setCellValue(cell, (byte) 0);

			return newState;
		}

		@Override
		public String toString() {
			return m + "(" + cell.getRowIndex() + "," + cell.getColumnIndex() + ")";
		}

		public boolean isInverse(Action a) {
			return a != null && m.getInverse() == a.getMove();
		}
	}

	private int hashCode = -1;
	private Action nextAction;
	//private FifteenNode that = this;

	public FifteenNode(int dimension, byte[] values) {
		super(dimension, values);
	}

	public Action getAction() {
		return nextAction;
	}

	public void setAction(Action next) {
		this.nextAction = next;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FifteenNode) {
			FifteenNode s2 = (FifteenNode) obj;

			return Arrays.equals(getValues(), s2.getValues());
		}

		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == -1) {
			int result = 17;
			for (int i = 0; i < getValues().length; i++) {
				result = 31 * result + getValues()[i];
			}
			hashCode = result;
		}

		return hashCode;
	}

	@SuppressWarnings("unchecked")
	@Override
	public FifteenNode[] getChildren() {
		List<Action> possibleActions = getPossibleActions();
		Action oldAction = getAction();

		// don't count inverse move
		int childrenSize = oldAction == null ? possibleActions.size() : possibleActions.size() - 1;
		FifteenNode[] children = new FifteenNode[childrenSize];

		int i = 0;
		for (Action a : possibleActions) {
			if (!a.isInverse(oldAction)) {

				FifteenNode child = a.applyTo(this);
				child.setParent(this);
				child.setAction(a);
				if(i == children.length){
					System.out.println(i);
				}
				children[i++] = child;
			}

		}

		return children;
	}

	public static class CellLocation {

		private int rowIndex;
		private int columnIndex;

		public CellLocation() {
		}

		public CellLocation(int rowIndex, int columnIndex) {
			this.rowIndex = rowIndex;
			this.columnIndex = columnIndex;
		}

		public int getRowIndex() {
			return rowIndex;
		}

		public int getColumnIndex() {
			return columnIndex;
		}
	}

	public static FifteenNode createRandom(int dimension) {
		FifteenNode s = new FifteenNode(dimension, getGoalState(dimension).getValues());

		Action old = null;

		Random r = new Random();
		for (int i = 0; i < 50; i++) {
			List<Action> actions = s.getPossibleActions();
			// pick an action randomly
			int index = r.nextInt(actions.size());
			Action a = actions.get(index);
			if (old != null && old.isInverse(a)) {
				if (index == 0) {
					index = 1;
				} else {
					index--;
				}
				a = actions.get(index);
			}
			s = a.applyTo(s);
			old = a;
		}

		return s;
	}

	public static FifteenNode getGoalState(int dimension) {
		assert dimension == 3 || dimension == 4 : "Wrong dimension : " + dimension;

		if (dimension == 3) {
			return createGoalState(3);
		} else if (dimension == 4) {
			return createGoalState(4);
		}

		return null;
	}

	private static FifteenNode createGoalState(int dimension) {

		int nbrOfCells = dimension * dimension;
		byte[] goalCells = new byte[nbrOfCells];
		for (byte i = 1; i < goalCells.length; i++) {
			goalCells[i - 1] = i;
		}
		goalCells[nbrOfCells - 1] = 0;

		return new FifteenNode(dimension, goalCells);

	}

	public byte getCellValue(CellLocation cell) {
		return getCellValue(cell.rowIndex, cell.columnIndex);
	}

	public byte getCellValue(int rowIndex, int columnIndex) {
		return getValues()[rowIndex * getDimension() + columnIndex];
	}

	public void setCellValue(CellLocation cell, byte value) {
		getValues()[cell.getRowIndex() * getDimension() + cell.getColumnIndex()] = value;

		reset();
	}

	private void reset() {
		hashCode = -1;
	}

	private CellLocation getEmptyCellLocation() {
		for (int i = 0; i < getValues().length; i++) {
			if (getValues()[i] == 0) {
				return new CellLocation(i / getDimension(), i % getDimension());
			}

		}

		throw new RuntimeException("No Empty cell found");
	}

	public List<Action> getPossibleActions() {
		List<Action> actions = new ArrayList<Action>();

		CellLocation emptyCell = getEmptyCellLocation();

		if (emptyCell.getRowIndex() > 0) {
			CellLocation upCell = new CellLocation(emptyCell.getRowIndex() - 1, emptyCell.getColumnIndex());
			actions.add(new Action(upCell, Move.DOWN));
		}

		if (emptyCell.getRowIndex() < getDimension() - 1) {
			CellLocation upCell = new CellLocation(emptyCell.getRowIndex() + 1, emptyCell.getColumnIndex());
			actions.add(new Action(upCell, Move.UP));
		}

		if (emptyCell.getColumnIndex() > 0) {
			CellLocation upCell = new CellLocation(emptyCell.getRowIndex(), emptyCell.getColumnIndex() - 1);
			actions.add(new Action(upCell, Move.RIGHT));
		}

		if (emptyCell.getColumnIndex() < getDimension() - 1) {
			CellLocation upCell = new CellLocation(emptyCell.getRowIndex(), emptyCell.getColumnIndex() + 1);
			actions.add(new Action(upCell, Move.LEFT));
		}

		return actions;
	}

	@Override
	public String toString() {
		return Arrays.toString(getValues());
	}

	public static void main(String[] args) {
		FifteenNode goal = getGoalState(4);
		FifteenNode start = createRandom(4);
		System.out.println("goal:" + goal);
		System.out.println("start:" + start);
	}

}