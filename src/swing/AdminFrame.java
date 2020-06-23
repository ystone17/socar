package swing;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import javax.swing.table.*;

import Main.Main;

import java.awt.*;
import java.awt.event.*;

public class AdminFrame extends JPanel implements ActionListener, ListSelectionListener {
	private static final long serialVersionUID = 1L;
	JTextField state = null;
	JPasswordField rocation = null;
	JTable table = null;
	SwingMain main;
	JFrame frame;
	DefaultListModel m = new DefaultListModel();
	JLabel label;
	Color color = new Color(0x00CCFF);
	JList jlist;

	public AdminFrame() {
		frame = new JFrame("MainTable");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();

		// Create and set up the window.
		// JFrame frame = new JFrame("MainTable");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setTitle("Admin Page - Main"); // 타이틀

		Image img = kit.getImage("key.png");
		frame.setIconImage(img);// 타이틀에 사진 넣기

		frame.setBounds(100, 100, 900, 640);
		frame.setLocation(screenSize.width / 2 - frame.getSize().width / 2,
				screenSize.height / 2 - frame.getSize().height / 2);
		frame.setPreferredSize(new Dimension(900, 640));

		// super(new BorderLayout()); //Layout 지정
		userTableInit();
		JPanel pane1 = makeTopPane();
		pane1.setOpaque(true);
		pane1.setBackground(Color.WHITE);

		frame.add(pane1, BorderLayout.PAGE_START);
		JPanel pane2 = makeMiddlePane();
		pane2.setBackground(Color.WHITE);
		frame.add(pane2, BorderLayout.CENTER);
		JPanel pane3 = makeBottomPane();
		pane3.setBackground(Color.WHITE);
		frame.add(pane3, BorderLayout.PAGE_END);
		frame.setVisible(true);

	}

	void userTableInit() {
		// final String[] columnNames = {"No", "User", "ID", "PW", "Reservation"};
		DefaultTableModel tableModel = new DefaultTableModel();
		// for (User s : UserMgr.users)
		// tableModel.addRow(s.getTexts());

		table = new JTable(tableModel);
		table.setPreferredScrollableViewportSize(new Dimension(1000, 600));
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ListSelectionModel rowSM = table.getSelectionModel();
		rowSM.addListSelectionListener(this);
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);

	}

	JPanel makeTopPane() {
		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout());
		pane.setOpaque(true);
		pane.setBackground(Color.WHITE);

		JPanel searchPN = new JPanel();
		searchPN.setLayout(new FlowLayout());
		searchPN.setOpaque(true);
		searchPN.setBackground(Color.WHITE);

		JPanel userEditPN = new JPanel();
		userEditPN.setLayout(new FlowLayout());
		userEditPN.setSize(500, 100);

		JButton uEdit = new JButton("Show all of my users");
		uEdit.setBackground(Color.GRAY);
		uEdit.setForeground(Color.WHITE);
		uEdit.setFont(new Font("Tahoma", Font.BOLD, 21));
		uEdit.addActionListener(new ActionListener() {
			// 만들어진 버튼 "새 창 띄우기"에 버튼이 눌러지면 발생하는 행동을 정의
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new AdminUserTableFrame().main(null);
			}
		});

		pane.add(uEdit, BorderLayout.WEST);

		JButton Btn1 = new JButton("Exit");// 검색 버튼
		Btn1.setBackground(Color.GRAY);
		Btn1.setForeground(Color.WHITE);
		Btn1.setFont(new Font("Tahoma", Font.BOLD, 21));
		Btn1.addActionListener(new ActionListener() {// 검색 버튼 엑션 추가
			public void actionPerformed(ActionEvent e) {
				// go to button function
				Main.writeAll();
				System.exit(0);
			}
		});
		
		searchPN.add(Btn1);
		pane.add(searchPN, BorderLayout.EAST);
		return pane;
	}

	JPanel makeMiddlePane() {
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());
		panel1.setBackground(Color.WHITE);

		JPanel pane = new JPanel();
		pane.setBackground(Color.WHITE);
		pane.setLayout(new FlowLayout());
		label = new JLabel(new ImageIcon("./adminpage.png"), JLabel.CENTER);
		label.setSize(50, 50);
		pane.add(label);

		JPanel pane1 = new JPanel();
		pane1.setLayout(new FlowLayout());
		pane1.setBackground(Color.WHITE);

		JButton logout = new JButton("LOG OUT");
		logout.setBackground(Color.WHITE);
		logout.setForeground(Color.GRAY);
		logout.setFont(new Font("Tahoma", Font.BOLD, 21));
		logout.addActionListener(new MenuActionListener());

		pane1.add(logout);
		panel1.add(pane, BorderLayout.CENTER);
		panel1.add(pane1, BorderLayout.LINE_END);

		JPanel panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
		addObject(panel2); // 두번째 panel
		panel2.setBorder(new TitledBorder(new LineBorder(color, 5), "예약 가능 차량 목록"));
		/*
		 * JPanel panel3 = new JPanel(); panel3.setLayout(new BoxLayout(panel3,
		 * BoxLayout.Y_AXIS)); panel3.add(new JButton("추가"), ); panel3.add(new
		 * JButton("삭제"));
		 */
		JPanel panel4 = new JPanel();
		panel4.setLayout(new BoxLayout(panel4, BoxLayout.X_AXIS));
		panel4.add(panel2);
//		panel4.add(panel3);

		JPanel panel5 = new JPanel();
		panel5.setLayout(new BoxLayout(panel5, BoxLayout.Y_AXIS));
		panel5.add(panel1);
		panel5.add(panel4); // 세번째 panel에 panel1,2 추가

		return panel5;

	}

	public void addObject(JPanel panel) {
		jlist = new JList();
		jlist.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		// jlist.setBorder(new TitledBorder(new LineBorder(Color.red, 5), "예약 가능 차량
		// 목록"));

		m.addElement(new ButtonData("레이/휘발유\n 40하4145 190원/km", new ImageIcon("./ray.png")));
		m.addElement(new ButtonData("티볼리 에어(디젤)/경유\n 46하9505 180원/km", new ImageIcon("./티볼리에어.png")));
		m.addElement(new ButtonData("아반떼AD/휘발유\n 40하1625 90원/km", new ImageIcon("./아반떼ad.png")));
		m.addElement(new ButtonData("쏘울(전기차)\n 11허1087 0원/km", new ImageIcon("./쏘울.png")));
		m.addElement(new ButtonData("코나(전기차)\n 109하1010 0원/km", new ImageIcon("./코나.png")));
		jlist.setModel(m);
		jlist.setCellRenderer(new ButtonRenderer());
		JScrollPane jsp = new JScrollPane(jlist, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panel.add(jsp);
		jlist.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        if (evt.getClickCount() == 2) {
		        	int index = jlist.getSelectedIndex();
					if(index != -1) {
						String carNumber = Main.cmgr.getLendList().get(index).getSigniture();
		        	new AdminFrameBookTableFrame(carNumber);
					}
		        } 
		    }
		});
	}

	JPanel makeBottomPane() {
		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout());
		pane.setBackground(Color.WHITE);

		// pane.setBackground(Color.WHITE);
		JPanel pane1 = new JPanel();
		pane1.setLayout(new FlowLayout());
		pane1.setBackground(Color.WHITE);

		JPanel pane2 = new JPanel();
		pane2.setLayout(new FlowLayout());
		pane2.setBackground(Color.WHITE);

		JPanel pane3 = new JPanel();
		pane3.setLayout(new FlowLayout());
		pane3.setBackground(Color.WHITE);
		JPanel pane4 = new JPanel();
		pane4.setLayout(new FlowLayout());
		pane4.setBackground(Color.WHITE);

		JLabel state = new JLabel("state");
		state.setForeground(color);
		state.setFont(new Font("Tahoma", Font.BOLD, 21));

		JLabel location = new JLabel("location");
		location.setForeground(color);
		location.setFont(new Font("Tahoma", Font.BOLD, 21));

		JTextField stateIn = new JTextField("Input new Car's information");
		stateIn.setFont(new Font("Tahoma", Font.ITALIC, 15));
		stateIn.setForeground(Color.GRAY);
		stateIn.setColumns(15);
		stateIn.addMouseListener(new MouseAdapter() { // 클릭시 글자 지워짐
			@Override
			public void mouseClicked(MouseEvent e) {
				stateIn.setForeground(Color.BLACK);
				stateIn.setText(null);
			}
		});
		JTextField locationIn = new JTextField("Input new Car's icon");
		locationIn.setFont(new Font("Tahoma", Font.ITALIC, 15));
		locationIn.setForeground(Color.GRAY);
		locationIn.setColumns(15);
		locationIn.addMouseListener(new MouseAdapter() { // 클릭시 글자 지워짐
			@Override
			public void mouseClicked(MouseEvent e) {
				locationIn.setForeground(Color.BLACK);
				locationIn.setText(null);
			}
		});

		String inputState = state.getText();
		String inputRocation = new String(locationIn.getText());

		JButton add = new JButton("ADD");
		add.setBackground(Color.GRAY);
		add.setForeground(Color.WHITE);
		add.setFont(new Font("Tahoma", Font.BOLD, 21));
		add.addActionListener(new ActionListener() {
			// 만들어진 버튼 "새 창 띄우기"에 버튼이 눌러지면 발생하는 행동을 정의
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				m.addElement(new ButtonData(inputState, new ImageIcon(" " + rocation)));
				jlist.setModel(m);
			}
		});

		JButton delete = new JButton("DELETE");
		delete.setBackground(Color.GRAY);
		delete.setForeground(Color.WHITE);
		delete.setFont(new Font("Tahoma", Font.BOLD, 21));
		delete.addActionListener(new ActionListener() {
			// 만들어진 버튼 "새 창 띄우기"에 버튼이 눌러지면 발생하는 행동을 정의
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// m.removeElementAt();;
				// jlist.setModel(m);
			}
		});

//		uEdit.addActionListener(new ActionListener() {
//	            // 만들어진 버튼 "새 창 띄우기"에 버튼이 눌러지면 발생하는 행동을 정의
//	            @Override
//	            public void actionPerformed(ActionEvent e) {
//	                // TODO Auto-generated method stub
//	            	dispose();
//	                //main.logout();
//	            }
//		});
		pane1.add(add);
		pane1.add(delete);

		pane3.add(state);
		pane3.add(stateIn);
		pane4.add(location);
		pane4.add(locationIn);
		pane2.add(pane3);
		pane2.add(pane4);

		pane.add(pane2, BorderLayout.WEST);
		pane.add(pane1, BorderLayout.EAST);// 추가 삭제 버튼

		return pane;
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
				if (isSelected) {
					txt = data.szName + "     사용중";
				} else {
					txt = data.szName;
				}
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

	class MenuActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// 사용자가 Load 메뉴아이템을 선택하는 경우 처리할 작업 구현
			frame.setVisible(false);
			new SwingMain();
		}
	}

	private void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new AdminFrame();

	}

}
