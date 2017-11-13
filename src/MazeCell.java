

import java.util.LinkedList;
import java.util.Stack;

public class MazeCell {
	
	public int X;
	public int Y;
	
	private MazeCell parentCell;

	public MazeCell upCell = null;
	public MazeCell downCell = null;
	public MazeCell leftCell = null;
	public MazeCell rightCell = null;
	
	private boolean isUsed = false;
	private int scale = 0;
	
	public MazeCell(int x, int y) {
		this.X = x;
		this.Y = y;
		parentCell = this;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	public MazeCell getParentCell() {
		return parentCell;
	}

	public void setParentCell(MazeCell parentCell) {
		this.parentCell = parentCell;
	}
	
}














