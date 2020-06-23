package swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Book.Book;
import License.License;
import Main.Main;


public class MyPageFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtName;
	private JTextField txtId;
	private JTextField txtBirth;
	private JTextField textType;
	private JTextField textNum;
	private JTextField textGet;
	private JTextField textDate;
	SwingMain main;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyPageFrame frame = new MyPageFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MyPageFrame() {
		setResizable(true);
		setBounds(100, 100, 900, 640);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.getImage("mainicon.png");
		setIconImage(img);
		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
		setTitle(Main.me.getId()+" 님의 MyPage");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.WHITE);
		menuBar.setForeground(Color.GRAY);
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("MENU");
		mnNewMenu.setForeground(Color.GRAY);
		mnNewMenu.setFont(new Font("Tahoma", Font.BOLD, 17));
		JMenuItem mntmNewMenuItem = new JMenuItem("Main Page");
		mntmNewMenuItem.setForeground(Color.GRAY);
		mntmNewMenuItem.setFont(new Font("Tahoma", Font.BOLD, 17));
		mntmNewMenuItem.addActionListener(new MenuActionListener());
		menuBar.add(mnNewMenu);
		mnNewMenu.add(mntmNewMenuItem);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(2, 2, 0, 0));
		contentPane.setBackground(Color.WHITE);

		JPanel panel_up = new JPanel();
		contentPane.add(panel_up);
		panel_up.setLayout(new BoxLayout(panel_up, BoxLayout.X_AXIS));

		JPanel panel_up_l = new JPanel();
		panel_up.add(panel_up_l);
		panel_up_l.setLayout(new GridLayout(0, 2, 0, 0));
		panel_up_l.setBackground(Color.WHITE);

		JPanel panel_up_r = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_up_r.getLayout();
		panel_up_r.setBackground(Color.WHITE);
		panel_up.add(panel_up_r);
		
		JLabel lblId = new JLabel("아이디");
		lblId.setHorizontalAlignment(SwingConstants.CENTER);
		panel_up_l.add(lblId);

		txtId = new JTextField();
		txtId.setHorizontalAlignment(SwingConstants.CENTER);
		txtId.setText(Main.me.getId());
		panel_up_l.add(txtId);
		txtId.setColumns(5);

		JLabel lblName = new JLabel("이름");
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		panel_up_l.add(lblName);

		txtName = new JTextField();
		txtName.setHorizontalAlignment(SwingConstants.CENTER);
		txtName.setText(Main.me.getName());
		panel_up_l.add(txtName);
		txtName.setColumns(5);

		JLabel lblBirth = new JLabel("생년월일");
		lblBirth.setHorizontalAlignment(SwingConstants.CENTER);
		panel_up_l.add(lblBirth);

		txtBirth = new JTextField();
		txtBirth.setHorizontalAlignment(SwingConstants.CENTER);
		txtBirth.setText(Main.me.getBirthDay());
		panel_up_l.add(txtBirth);
		txtBirth.setColumns(5);

		ArrayList<Book> b = Main.me.getBook();
		JLabel lblSf;
		if (b.isEmpty()) {
			lblSf = new JLabel(Main.me.lastBookString(), new ImageIcon("./mainIcon.png"), JLabel.CENTER);
		} else {
			ImageIcon nowImage = Main.cmgr.findMap(b.get(b.size() - 1).getCarNumber()).getImage();
			lblSf = new JLabel(Main.me.lastBookString(), nowImage, JLabel.CENTER);
		}
		panel_up_r.add(lblSf);

		JPanel panel_down = new JPanel();
		contentPane.add(panel_down);
		panel_down.setLayout(new GridLayout(0, 2, 0, 0));
		panel_down.setBackground(Color.WHITE);

		JPanel panel_down_l = new JPanel();
		panel_down.add(panel_down_l);
		panel_down_l.setLayout(new GridLayout(0, 2, 0, 0));
		panel_down_l.setBackground(Color.WHITE);

		JLabel labelNum = new JLabel("면허 번호");
		labelNum.setHorizontalAlignment(SwingConstants.CENTER);
		panel_down_l.add(labelNum);

		textNum = new JTextField();
		textNum.setHorizontalAlignment(SwingConstants.CENTER);
		panel_down_l.add(textNum);
		textNum.setColumns(10);
		if (!(Main.me.getMyLicenseNumber().equals("0"))) {
			textNum.setText(Main.me.getMyLicenseNumber());
		}

		JLabel labelType = new JLabel("면허 종류");
		labelType.setHorizontalAlignment(SwingConstants.CENTER);
		panel_down_l.add(labelType);

		textType = new JTextField();
		textType.setHorizontalAlignment(SwingConstants.CENTER);
		panel_down_l.add(textType);
		textType.setColumns(10);
		if (!(Main.me.getMyLicenseNumber().equals("0"))) {
			textType.setText(Main.lmgr.findMap(Main.me.getMyLicenseNumber()).getLicenseType());
		}

		JLabel labelGet = new JLabel("발급일");
		labelGet.setHorizontalAlignment(SwingConstants.CENTER);
		panel_down_l.add(labelGet);

		textGet = new JTextField();
		textGet.setHorizontalAlignment(SwingConstants.CENTER);
		panel_down_l.add(textGet);
		textGet.setColumns(10);
		if (!(Main.me.getMyLicenseNumber().equals("0"))) {
			textGet.setText(Main.lmgr.findMap(Main.me.getMyLicenseNumber()).getStartDayString());
		}

		JLabel labelDate = new JLabel("갱신 날짜");
		labelDate.setHorizontalAlignment(SwingConstants.CENTER);
		panel_down_l.add(labelDate);

		textDate = new JTextField();
		textDate.setHorizontalAlignment(SwingConstants.CENTER);
		panel_down_l.add(textDate);
		textDate.setColumns(10);
		if (!(Main.me.getMyLicenseNumber().equals("0"))) {
			textDate.setText(Main.lmgr.findMap(Main.me.getMyLicenseNumber()).getEndDayString());
		}

		JButton button = new JButton("License register");//license
		button.setBackground(Color.GRAY);
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Tahoma", Font.BOLD, 21));
		panel_down_l.add(button);
		button.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {

				boolean isOverLap = Main.lmgr.registeLicense( // 텍스트필드에 받아온 값들을 이용해 라이센스를 하나 만듬
						textNum.getText(), textType.getText(), textGet.getText(), textDate.getText());
				if (isOverLap) {
					JOptionPane.showMessageDialog(null, "This information already exists", "ERROR", JOptionPane.ERROR_MESSAGE);
					System.out.println("중복");
				} else {
					Main.me.setLicenseNumber(textNum.getText());
				}

			}
		});

		JPanel panel_down_r = new JPanel();
		FlowLayout flowLayout2 = (FlowLayout) panel_down_r.getLayout();
		flowLayout2.setVgap(100);
		panel_down.add(panel_down_r);
		panel_down_r.setBackground(Color.WHITE);

		JButton btnNewButton = new JButton("My Reservation");
		btnNewButton.setBackground(Color.GRAY);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 21));
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				MyPageBookTableFrame.main(null);// 예약 내역 보여주기
			}
		});
		panel_down_r.add(btnNewButton);
		setVisible(true);
		FocusEvent();
	}

	private void FocusEvent() {
		textNum.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				if (textNum.getText().trim().length() == 0) {
					textNum.setText("면허 번호");
				}
			}

			public void focusGained(FocusEvent e) {
				if (textNum.getText().trim().equals("면허 번호")) {
					textNum.setText("");
				}
			}
		});
		textType.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				if (textType.getText().trim().length() == 0) {
					textType.setText("1종보통 or 2종보통");
				}
			}

			public void focusGained(FocusEvent e) {
				if (textType.getText().trim().equals("1종보통 or 2종보통")) {
					textType.setText("");
				}
			}
		});
		textDate.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				if (textDate.getText().trim().length() == 0) {
					textDate.setText("yyyy-mm-dd");
				}
			}

			public void focusGained(FocusEvent e) {
				if (textDate.getText().trim().equals("yyyy-mm-dd")) {
					textDate.setText("");
				}
			}
		});
		textGet.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				if (textGet.getText().trim().length() == 0) {
					textGet.setText("yyyy-mm-dd");
				}
			}

			public void focusGained(FocusEvent e) {
				if (textGet.getText().trim().equals("yyyy-mm-dd")) {
					textGet.setText("");
				}
			}
		});
	}

	class MenuActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// 사용자가 Load 메뉴아이템을 선택하는 경우 처리할 작업 구현

			setVisible(false);
			new UserFrame();
		}
	}

}
