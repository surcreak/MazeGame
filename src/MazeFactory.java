
import java.util.LinkedList;
import java.util.Random;

import org.omg.CosNaming.NamingContextPackage.CannotProceed;

public class MazeFactory {
	
	MazeCell entryCell = null;
	MazeCell exitCell = null;
	
	public MazeCell[][] create1(int row, int col, int startRow, int endRow) {
		
		Random random =new Random();
		LinkedList<MazeCell> remainCell = new LinkedList<MazeCell>();
		MazeCell [][] cells = new MazeCell[row][col];
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
				remainCell.add(cell);
			}
		}
		
		while(!hasUnicom) {
			int randomIndex = random.nextInt(remainCell.size());
			MazeCell cell = remainCell.get(randomIndex);
			int direction = random.nextInt(4);
			System.out.println("direction="+direction + " hasUnicom="+hasUnicom+ " remainCell.size()="+remainCell.size() + "  randomIndex="+randomIndex);
			switch (direction) {
			case 0://ио
				if( cell.X-1 > 0 ) {
					MazeCell up = cells[cell.X - 1][cell.Y];
					if (!up.isUsed()) {						
						cell.upCell = up;
						up.downCell = cell;
						hasUnicom = judgUnicom(cell, up);
					}
				}
				break;
			case 1://вС
				if( cell.Y-1 > 0 ) {
					MazeCell left = cells[cell.X][cell.Y - 1];
					if (!left.isUsed()) {						
						cell.leftCell = left;
						left.rightCell = cell;
						hasUnicom = judgUnicom(cell, left);
					}
				}
				break;
			case 2://об
				if( (cell.X+1) < (row-1) ) {
					MazeCell down = cells[cell.X + 1][cell.Y];
					if (!down.isUsed()) {						
						cell.downCell = down;
						down.upCell = cell;
						hasUnicom = judgUnicom(cell, down);
					}
				}
				break;
			case 3://ср
				if( (cell.Y+1) < (col-1) ) {
					MazeCell right = cells[cell.X][cell.Y + 1];
					if (!right.isUsed()) {						
						cell.rightCell = right;
						right.leftCell = cell;
						hasUnicom = judgUnicom(cell, right);
					}
				}
				break;

			default:
				System.out.println("error direction="+direction);
				break;
			}
			
			if ( cell.upCell != null
					&& cell.downCell != null
					&& cell.leftCell != null
					&& cell.rightCell != null) {
//				System.out.println("cell.x="+cell.X + "  cell.y="+cell.Y);
				cell.setUsed(true);
				remainCell.remove(cell);
			}
		}
		
		return cells;
		
	}
	
	private boolean judgUnicom(MazeCell cell1, MazeCell cell2) {
		boolean result = false;
		if (cell1.rootCell == entryCell) {
			if (cell2.rootCell == exitCell) {
				result = true;
			}
			cell2.rootCell = cell1.rootCell;
		}
		
		if(cell2.rootCell == entryCell) {
			if(cell1.rootCell == exitCell) {
				result = true;
			}
			cell1.rootCell = cell2.rootCell;
		}
		cell2.rootCell = cell1.rootCell;
		return result;
	}

}
