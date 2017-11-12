import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MazeGame extends JFrame{
	
	private int width;
	private int height;
	private MazeCell [][] cells;
	
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
		cells = factory.create1(20, 20, 2, 2);
		Container container = this.getContentPane();
		JPanel panel = new GamePanel();
		container.add(panel);
	}
	
	class GamePanel extends JPanel{
		@Override
		public void paint(Graphics graphics) {
			super.paint(graphics);
			Image wallImage=Toolkit.getDefaultToolkit().getImage("wall.jpg");
			Image roadImage=Toolkit.getDefaultToolkit().getImage("road.jpg");
			int startX = 200;
			int startY = 0;
			int horizontalBorder = 100;
			int verticalBorder = 10;
			int consoleheight = 10;
			int gameHeight = height - verticalBorder*2 - consoleheight;
			int roadHeight = (int)((gameHeight/cells.length)*0.8);
			int wallHeight = (int)((gameHeight/cells.length)*0.2);
			
			for(int i=0; i<cells.length; i++) {
				startX = 200;
				for(int j=0; j<cells[i].length; j++) {
					graphics.drawImage(roadImage, startX, startY, roadHeight, roadHeight, this);
					if(cells[i][j].rightCell != null) {
						graphics.drawImage(roadImage, startX + roadHeight, startY, wallHeight, roadHeight, this);//右下
					}else {						
						graphics.drawImage(wallImage, startX + roadHeight, startY, wallHeight, roadHeight, this);//右下
					}
					if (cells[i][j].downCell != null) {
						graphics.drawImage(roadImage, startX, startY + roadHeight, roadHeight, wallHeight, this);//下
					}else {						
						graphics.drawImage(wallImage, startX, startY + roadHeight, roadHeight, wallHeight, this);//下
					}
					graphics.drawImage(wallImage, startX + roadHeight, startY + roadHeight, wallHeight, wallHeight, this);//虚无
					startX += roadHeight + wallHeight;
				}
				startY += roadHeight + wallHeight;
			}
		}
	}
}
