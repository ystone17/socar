package swing;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class AdminFrameBookTableFrame {
	/**
	 * Create the GUI and show it. For thread safety, this method should be invoked
	 * from the event-dispatching thread.
	 */
	String carNumber;
	private void createAndShowGUI() {

		Toolkit kit = Toolkit.getDefaultToolkit();

		// Create and set up the window.
		JFrame frame = new JFrame("BookTable");
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		AdminFrameBookTable newContentPane = new AdminFrameBookTable(carNumber); // demo 객체
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);// 객체에 컨테이너 만듦
		frame.setTitle("Admin Page - Book Edit"); // 타이틀

		Image img = kit.getImage("key.png");
		frame.setIconImage(img);// 타이틀에 사진 넣기

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	AdminFrameBookTableFrame(String carNumber) {
		this.carNumber = carNumber;
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
