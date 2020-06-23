package swing;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;

import Main.Main;

public class MyPageBookTableFrame {
	/**
	 * Create the GUI and show it. For thread safety, this method should be invoked
	 * from the event-dispatching thread.
	 */
	private static void createAndShowGUI() {

		Toolkit kit = Toolkit.getDefaultToolkit();

		// Create and set up the window.
		JFrame frame = new JFrame("BookTable");
		frame.setBounds(510, 290, 300, 200);
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		MyPageBookTable newContentPane = new MyPageBookTable(); // demo 객체
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);// 객체에 컨테이너 만듦
		frame.setTitle(Main.me.getId()+ " 님의 예약 내역"); // 타이틀

		Image img = kit.getImage("mainIcon.png");
		frame.setIconImage(img);// 타이틀에 사진 넣기

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
