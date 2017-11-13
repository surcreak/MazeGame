import java.awt.AlphaComposite;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.List;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.PrimitiveIterator;
import java.util.Queue;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MazeGame extends JFrame{
	
	private int width;
	private int height;
	private MazeCell [][] cells;
	private int entryRow = 2;
	private int exitRow = 49;
	private int totalRow = 50;
	private int totalCol = 70;
	private boolean success = false;
	private MazeCell currentCell;
	private boolean inTip = false;
	LinkedList<MazeCell> path = new LinkedList<MazeCell>();
	
	MazePanel mazePanel;
	
	public MazeGame() {
		setTitle("Maze Game");
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		width = Toolkit.getDefaultToolkit().getScreenSize().width;
		height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setSize((int) width, (int) height);
		this.setLocation(0, 0);
		setVisible(true);
		this.setResizable(false);
		initUI();
	}
	
	private void initUI() {
		MazeFactory factory = new MazeFactory();
		cells = factory.create1(totalRow, totalCol, entryRow, exitRow);
		Container container = this.getContentPane();
		
		mazePanel = new MazePanel();
		this.addKeyListener(mazePanel);
		
		JButton tipBtn = new JButton("tip");
		tipBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!inTip) {					
					findPath();
					inTip = true;
					mazePanel.repaint();
				}
			}
		});
		tipBtn.addKeyListener(mazePanel);
		mazePanel.add(tipBtn);
		
		container.add(mazePanel);
	}
	
	public void findPath() {
		MazeCell tempCell;
		System.out.println("findPath");
		LinkedList<MazeCell> queue = new LinkedList<MazeCell>();
		queue.push(currentCell);
		currentCell.setUsed(true);
		while (!queue.isEmpty()) {
			MazeCell cell = queue.pop();
			if ( cell == cells[exitRow][totalCol-1] ) {
				path.clear();
				tempCell = cells[exitRow][totalCol-1];
				while ( tempCell != currentCell ) {
					path.add(tempCell);
					tempCell = tempCell.getParentCell();
				}
				return;
			}
			if (cell.upCell != null && !cell.upCell.isUsed()) {
				queue.push(cell.upCell);
				cell.upCell.setUsed(true);
				cell.upCell.setParentCell(cell);
			}
			if (cell.leftCell != null && !cell.leftCell.isUsed()) {
				queue.push(cell.leftCell);
				cell.leftCell.setUsed(true);
				cell.leftCell.setParentCell(cell);
			}
			if (cell.downCell != null && !cell.downCell.isUsed()) {
				queue.push(cell.downCell);
				cell.downCell.setUsed(true);
				cell.downCell.setParentCell(cell);
			}
			if (cell.rightCell != null && !cell.rightCell.isUsed()) {
				queue.push(cell.rightCell);
				cell.rightCell.setUsed(true);
				cell.rightCell.setParentCell(cell);
			}
		}
		System.out.println("findPath end" );
	}
	
	class MazePanel extends JPanel implements KeyListener{
		private int manX, manY;
		private int manWidth, manHeight;
		
		public MazePanel() {
			currentCell = cells[entryRow][0];
		}
		
		@Override
		public void paint(Graphics graphics) {
			super.paint(graphics);
			Image wallImage=Toolkit.getDefaultToolkit().getImage("wall.png");
			Image roadImage=Toolkit.getDefaultToolkit().getImage("road.png");
			Image manImage=Toolkit.getDefaultToolkit().getImage("man.png");
			
			Image upImage=Toolkit.getDefaultToolkit().getImage("up.png");
			Image downImage=Toolkit.getDefaultToolkit().getImage("down.png");
			Image leftImage=Toolkit.getDefaultToolkit().getImage("left.png");
			Image rightImage=Toolkit.getDefaultToolkit().getImage("rifht.png");
			Image successImage=Toolkit.getDefaultToolkit().getImage("success.png");
			
			int horizontalBorder = 100;
			int verticalBorder = 50;
			int consoleheight = 10;
			int gameHeight = height - verticalBorder*2 - consoleheight;
			int roadHeight = (int)((gameHeight/cells.length)*0.8);
			int wallHeight = (int)((gameHeight/cells.length)*0.2);
			
			int startX = 200;
			int startY = verticalBorder;
			
			manWidth = roadHeight;
			manHeight = roadHeight;
			
			for(int i=0; i<cells.length; i++) {
				startX = 200;
				for(int j=0; j<cells[i].length; j++) {
					if(!inTip) {
						cells[i][j].setUsed(false);
						cells[i][j].setParentCell(cells[i][j]);
					}
					graphics.drawImage(roadImage, startX, startY, roadHeight, roadHeight, this);
					
					if(inTip) {
						if ( path.contains(cells[i][j]) ) {
							if (cells[i][j].getParentCell() == cells[i][j].upCell) {
								graphics.drawImage(downImage, startX, startY, roadHeight, roadHeight, this);
							}
							if (cells[i][j].getParentCell() == cells[i][j].downCell) {
								graphics.drawImage(upImage, startX, startY, roadHeight, roadHeight, this);
							}
							if (cells[i][j].getParentCell() == cells[i][j].leftCell) {
								graphics.drawImage(rightImage, startX, startY, roadHeight, roadHeight, this);
							}
							if (cells[i][j].getParentCell() == cells[i][j].rightCell) {
								graphics.drawImage(leftImage, startX, startY, roadHeight, roadHeight, this);
							}
						}
					}
					
					if(cells[i][j].rightCell != null) {
						graphics.drawImage(roadImage, startX + roadHeight, startY, wallHeight, roadHeight, this);//右
					}else {
						if (cells[exitRow][totalCol-1] == cells[i][j]) {
							graphics.drawImage(successImage, startX, startY, roadHeight, roadHeight, this);
						}else {							
							graphics.drawImage(wallImage, startX + roadHeight, startY, wallHeight, roadHeight, this);//右
						}
					}
					if (cells[i][j].downCell != null) {
						graphics.drawImage(roadImage, startX, startY + roadHeight, roadHeight, wallHeight, this);//下
					}else {						
						graphics.drawImage(wallImage, startX, startY + roadHeight, roadHeight, wallHeight, this);//下
					}
					graphics.drawImage(wallImage, startX + roadHeight, startY + roadHeight, wallHeight, wallHeight, this);//虚无
					
					if(cells[i][j] == currentCell) {
						graphics.drawImage(manImage, startX, startY, roadHeight, roadHeight, this);
					}
					
					startX += roadHeight + wallHeight;
					
				}
				startY += roadHeight + wallHeight;
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (!success) {				
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					if (currentCell.upCell != null) {
						currentCell = currentCell.upCell;
						repaint();
					}
					break;
				case KeyEvent.VK_DOWN:
					if (currentCell.downCell != null) {
						currentCell = currentCell.downCell;
						repaint();
					}
					break;
				case KeyEvent.VK_LEFT:
					if (currentCell.leftCell != null) {
						currentCell = currentCell.leftCell;
						repaint();
					}
					break;
				case KeyEvent.VK_RIGHT:
					if (currentCell.rightCell != null) {
						currentCell = currentCell.rightCell;
						repaint();
					}
					break;
				}
				inTip = false;
				if (currentCell == cells[exitRow][totalCol-1]) {
					System.out.println("获胜");
					success = true;
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

	}
}
