package swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Menu;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import Book.Book;
import Lend.Car;
import Main.Main;

public class UserFrame extends JFrame {
	JFrame jFrame;
	DefaultListModel m;
	JLabel label;
	static JList jlist;
	SwingMain main;
	Color color = new Color(0x00CCFF);

	UserFrame() {

		jFrame = new JFrame(Main.me.getId() + " 님 환영합니다 !!!"); // frame 생성
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		Image img = kit.getImage("mainicon.png");
		jFrame.setIconImage(img);// 타이틀에 사진 넣기

		JPanel panel1 = new JPanel();
		panel1.setBackground(Color.WHITE);
		panel1.setLayout(new FlowLayout());
		FlowLayout flowLayout = (FlowLayout) panel1.getLayout();
		flowLayout.setHgap(50);

		ArrayList<Book> b = Main.me.getBook();
		JLabel label;
		if (b == null) {
			label = new JLabel("", new ImageIcon("mainIcon.png"), JLabel.CENTER);
		} else {
			ImageIcon nowImage = Main.cmgr.findMap(b.get(b.size() - 1).getCarNumber()).getImage();
			Image chImage = nowImage.getImage(); // ImageIcon을 Image로 변환.
			Image szImage = chImage.getScaledInstance(360, 230, java.awt.Image.SCALE_SMOOTH);
			ImageIcon afterImage = new ImageIcon(szImage); // Image로 ImageIcon 생성
			label = new JLabel(Main.me.lastBookString(), afterImage, JLabel.CENTER);
		}
		JPanel labelP = new JPanel();
		labelP.setBackground(Color.WHITE);
		JLabel userlabel = new JLabel(Main.me.getId());
		userlabel.setForeground(color);
		userlabel.setFont(new Font("", Font.BOLD, 20));
		labelP.add(userlabel);
		labelP.add(new JLabel(" 님의 최근 예약 기록")).setFont(new Font("", Font.BOLD, 17));
		panel1.add(labelP, BorderLayout.NORTH);
		panel1.add(label, FlowLayout.CENTER);

		JPanel panel2 = new JPanel();
		panel2.setBackground(Color.WHITE);
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
		addList(panel2); // 두번째 panel
		panel2.setBorder(new TitledBorder(new LineBorder(color, 5), "예약 가능 차량 목록"));

		JPanel panel3 = new JPanel();
		panel3.setBackground(Color.WHITE);
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));
		JButton bookBtn = new JButton("Reserve");
		bookBtn.setBackground(Color.GRAY);
		bookBtn.setForeground(Color.WHITE);
		bookBtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		bookBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int index = jlist.getSelectedIndex();
				if (index != -1) {
					String carNumber = Main.cmgr.getLendList().get(index).getSigniture();
					System.out.println(carNumber);
					new swing.BookDialog(carNumber);
				}
			}
		});
		JButton endBtn = new JButton("Exit");
		endBtn.setBackground(Color.GRAY);
		endBtn.setForeground(Color.WHITE);
		endBtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		endBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Main.writeAll();
				System.exit(0);
			}
		});
		panel3.add(bookBtn);
		panel3.add(endBtn);

		JPanel panel4 = new JPanel();
		panel4.setBackground(Color.WHITE);
		panel4.setLayout(new BoxLayout(panel4, BoxLayout.X_AXIS));
		panel4.add(panel2);
		panel4.add(panel3);

		JPanel panel5 = new JPanel();
		panel5.setBackground(Color.WHITE);
		panel5.setLayout(new BoxLayout(panel5, BoxLayout.Y_AXIS));
		panel5.add(panel1);
		panel5.add(panel4); // 세번째 panel에 panel1,2 추가

		jFrame.add(panel5);
		makeMenu();

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		jFrame.setBounds(100, 100, 900, 720);
		jFrame.setLocation(dim.width / 2 - jFrame.getSize().width / 2, dim.height / 2 - jFrame.getSize().height / 2);
		jFrame.setPreferredSize(new Dimension(900, 720));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setVisible(true); // frame에 panel 추가

	}

	void makeMenu() {
		JMenuBar mb = new JMenuBar();
		mb.setBackground(Color.WHITE);
		mb.setForeground(Color.GRAY);
		JMenu screenMenu = new JMenu("MENU");
		screenMenu.setForeground(Color.GRAY);
		screenMenu.setFont(new Font("Tahoma", Font.BOLD, 17));
		JMenuItem jmi = new JMenuItem("My Page");
		jmi.setForeground(Color.GRAY);
		jmi.setFont(new Font("Tahoma", Font.BOLD, 17));
		jmi.addActionListener(new MenuActionListener());
		screenMenu.add(jmi);

		mb.add(screenMenu);
		jFrame.setJMenuBar(mb);
	}

	class MenuActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// 사용자가 Load 메뉴아이템을 선택하는 경우 처리할 작업 구현
			jFrame.setVisible(false);
			new MyPageFrame();
		}
	}

	void addList(JPanel panel) {

		jlist = new JList();
		jlist.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		m = new DefaultListModel();
		// jlist.setBorder(new TitledBorder(new LineBorder(Color.red, 5), "예약 가능 차량
		// 목록"));

		for (Car c : Main.cmgr.getLendList()) {
			m.addElement(new ButtonData("[" + c.getSigniture() + "] [" + c.getName() + "] 시간 당 " + c.getPrice() + "원",
					c.getImage()));
		}
		jlist.setModel(m);
		jlist.setCellRenderer(new ButtonRenderer());
		JScrollPane jsp = new JScrollPane(jlist, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panel.add(jsp);

	}

	class ButtonRenderer extends DefaultListCellRenderer {
		public Component getListCellRendererComponent(JList lst, Object val, int idx, boolean isSelected,
				boolean hasFocus) {
			JLabel comp = null;

			if (val instanceof ButtonData) {
				/* 보여주어야 하는 데이터가 ButtonData인 경우 */
				ButtonData data = (ButtonData) val;
				String txt;
				/*
				 * 아이템이 선택된 상태이면 문자열에 "<- Selected"를 추가한다. "
				 */
				txt = data.szName;
				/* 부모 클래스의 메소드 호출 */
				comp = (JLabel) super.getListCellRendererComponent(lst, txt, idx, isSelected, hasFocus);
				/* 아이콘을 정한다. */
				comp.setIcon(data.xIcon);
				/* 경계선을 바꾼다. */
				comp.setBorder(new BevelBorder(BevelBorder.RAISED));
			} else if (val instanceof String) {
				/* 보여주어야 하는 데이터가 일반 문자열인 경우 */
				comp = (JLabel) super.getListCellRendererComponent(lst, val, idx, isSelected, hasFocus);
			}
			return comp;
		}
	}

	class ButtonData {
		String szName; // 이름
		Icon xIcon; // 이미지

		public ButtonData(String name, Icon icon) {
			szName = name;
			xIcon = icon;
		}

		public String toString() {
			return szName;
		}
	}

	void setMain(SwingMain m) {
		main = m;
	}

	public JList getJList() {
		return jlist;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new UserFrame();

	}
}
