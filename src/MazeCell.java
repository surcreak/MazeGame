

import java.util.LinkedList;

public class MazeCell {
	
	public int X;
	public int Y;
	
	public MazeCell rootCell;
	
	public MazeCell upCell = null;
	public MazeCell downCell = null;
	public MazeCell leftCell = null;
	public MazeCell rightCell = null;
	
	private boolean isUsed = false;
	
	public MazeCell(int x, int y) {
		this.X = x;
		this.Y = y;
		rootCell = this;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}
	
}
