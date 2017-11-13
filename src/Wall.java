public class Wall {
	public MazeCell cell1;
	public MazeCell cell2;

	public Wall(MazeCell cell1, MazeCell cell2) {
		this.cell1 = cell1;
		this.cell2 = cell2;
	}
	
	public void openWall() {
		if (cell1.X == cell2.X) {
			if (cell1.Y > cell2.Y) {
				cell1.leftCell = cell2;
				cell2.rightCell = cell1;
			}else {
				cell1.rightCell = cell2;
				cell2.leftCell = cell1;
			}
		}else {
			if (cell1.X > cell2.X) {
				cell1.upCell = cell2;
				cell2.downCell = cell1;
			}else {
				cell1.downCell = cell2;
				cell2.upCell = cell1;
			}
		}
	}
}