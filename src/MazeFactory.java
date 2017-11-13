
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

import org.omg.CosNaming.NamingContextPackage.CannotProceed;

public class MazeFactory {
	
	private MazeCell entryCell = null;
	private MazeCell exitCell = null;
	
	private Random random =new Random();
	private LinkedList<Wall> walls = new LinkedList<Wall>();
	MazeCell [][] cells;
	private int row = 0;
	private int col = 0;
	
	public MazeCell[][] create1(int row, int col, int startRow, int endRow) {
		this.row = row;
		this.col = col;
		cells = new MazeCell[row][col];
		boolean hasUnicom = false;
		
		for(int i=0; i<row; i++) {
			for(int j=0; j<col; j++) {
				MazeCell cell = new MazeCell(i, j);
				if( i==startRow && j==0 ) {
					entryCell = cell;
				}
				if( i==endRow && j==(col-1) ) {
					exitCell = cell;
				}
				
				cells[i][j] = cell;
			}
		}
		
		addAjacentWall(entryCell);
		entryCell.setUsed(true);
		while (!walls.isEmpty()) {
			Wall wall = walls.get(random.nextInt(walls.size()));
			if ( wall.cell1.isUsed() != wall.cell2.isUsed()) {
				wall.openWall();
				if (!wall.cell1.isUsed()) {
					wall.cell1.setUsed(true);
					addAjacentWall(wall.cell1);
				}else {
					wall.cell2.setUsed(true);
					addAjacentWall(wall.cell2);
				}
			}
			walls.remove(wall);
		}
		
		return cells;
		
	}
	
	
	
	private void addAjacentWall(MazeCell cell) {
		if( cell.X-1 >= 0 && cell.upCell == null ) {
			walls.add(new Wall(cell, cells[cell.X-1][cell.Y]));
		}
		if( cell.Y-1 >= 0 && cell.leftCell == null ) {
			walls.add(new Wall(cell, cells[cell.X][cell.Y-1]));
		}
		if( (cell.X+1) <= (row-1) && cell.downCell == null  ) {
			walls.add(new Wall(cell, cells[cell.X + 1][cell.Y]));
		}
		if( (cell.Y+1) <= (col-1) && cell.rightCell == null ) {
			walls.add(new Wall(cell, cells[cell.X][cell.Y+1]));
		}
	}

}
