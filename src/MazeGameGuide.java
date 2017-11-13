import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class MazeGameGuide extends JFrame {
	public static void main(String args[]) {
		System.out.println("Mazegame main");
//		new MazeGameGuide();
		new MazeGame();
	}

	public MazeGameGuide() {
		setTitle("Maze game guide");
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		double width = Toolkit.getDefaultToolkit().getScreenSize().width;
		double height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setSize((int) width, (int) height);
		this.setLocation(0, 0);
		setVisible(true);
		this.setResizable(false);
		initUI();
	}
	
	private void initUI() {
		Container container = this.getContentPane();
		JButton startBtn = new JButton("Start");
		startBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new MazeGame();
			}
		});
		JPanel panel = new JPanel();
		panel.add(startBtn);
		container.add(panel);
	}
}
