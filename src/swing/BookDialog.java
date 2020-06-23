package swing;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.JOptionPane;
import swing.UserFrame.ButtonData;
import swing.UserFrame.ButtonRenderer;
import swing.UserFrame.MenuActionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import Book.Book;
import Book.BookMgr;
import Main.Main;

public class BookDialog extends JPanel implements ActionListener, ListSelectionListener {
	private static final long serialVersionUID = 1L;
	JTextField state = null;
	JFrame frame;
	String year​[] = { "2019", "2020" };
	String month[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };
	String date[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18",
			"19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };
	JComboBox<String> combo;
	JComboBox<String> combo1;
	JComboBox<String> combo2;
	JComboBox<String> tcombo1;
	JComboBox<String> tcombo2;
	JLabel label;
	String bookDay;
	String carNumber;
	String rowData[][];
	JTable table;

	JLabel msg;// 색깔 중 하나를 선택하면, 라벨에 메세지를 띄웁니다.
	JLabel msg1;
	JLabel msg2;

	public BookDialog(String carNumber) {
		this.carNumber = carNumber;
		frame = new JFrame("Book Page");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();

		// Create and set up the window.
		// JFrame frame = new JFrame("MainTable");
		frame.setResizable(true);
		frame.setTitle("Book Page"); // 타이틀
		Image img = kit.getImage("mainicon.png");
		frame.setIconImage(img);// 타이틀에 사진 넣기

		frame.setBounds(100, 100, 1030, 300);
		frame.setLocation(screenSize.width / 2 - frame.getSize().width / 2,
				screenSize.height / 2 - frame.getSize().height / 2);
		frame.setPreferredSize(new Dimension(1030, 300));

		JPanel toppane = makeTopPane();
		toppane.setBackground(Color.WHITE);
		frame.add(toppane, BorderLayout.NORTH);
		JPanel midpane = makeMiddlePane();
		midpane.setBackground(Color.WHITE);
		frame.add(midpane, BorderLayout.CENTER);
		JPanel botpane = makeBottomPane();
		botpane.setBackground(Color.WHITE);
		frame.add(botpane, BorderLayout.SOUTH);

		frame.setVisible(true);
	}

	JPanel makeTopPane() {
		JPanel pane = new JPanel();
		pane.setBackground(Color.WHITE);
		pane.setLayout(new FlowLayout());
		label = new JLabel(new ImageIcon("./book.png"), JLabel.CENTER);
		label.setBounds(140, 120, 200, 50);
		pane.add(label);

		return pane;
	}

	JPanel makeMiddlePane() {

		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout());
		pane.setOpaque(true);
		pane.setBackground(Color.WHITE);

		JPanel pane1 = new JPanel();
		pane1.setLayout(new FlowLayout());
		pane1.setOpaque(true);
		pane1.setBackground(Color.WHITE);

		JPanel pane2 = new JPanel();
		pane2.setLayout(new FlowLayout());
		pane2.setOpaque(true);
		pane2.setBackground(Color.WHITE);

		combo = new JComboBox<String>(this.year​);
		combo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String selectY = combo.getSelectedItem().toString();
			}
		});

		combo1 = new JComboBox<String>(this.month);
		combo1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String selectM = combo1.getSelectedItem().toString();
			}
		});

		combo2 = new JComboBox<String>(this.date);
		combo2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String selectD = combo2.getSelectedItem().toString();
			}
		});

		JLabel front = new JLabel("year/month/date : ");
		front.setBackground(Color.WHITE);
		front.setForeground(Color.DARK_GRAY);
		front.setFont(new Font("Tahoma", Font.BOLD, 17));

		JLabel middle = new JLabel("Time Choice");
		middle.setBackground(Color.WHITE);
		middle.setForeground(Color.DARK_GRAY);
		middle.setFont(new Font("Tahoma", Font.BOLD, 17));

		JButton check = new JButton("Check");
		check.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				for(int i=0;i<24;i++) {
					rowData[0][i] = " ";
					table.setValueAt(" ", 0, i);
				}
				bookDay = combo.getSelectedItem().toString() + "-" + combo1.getSelectedItem().toString() + "-"
						+ combo2.getSelectedItem().toString();

				ArrayList<Book> thisDay = new ArrayList<>();
				
				for (Book b : Main.cmgr.findMap(carNumber).getmyCBookList()) {
					
					if(b.getDateText().equals(bookDay)) {
						thisDay.add(b);
					}
					
				}
				
				for (Book b2 : thisDay) {
					for(int i=b2.getStartTime()-1;i<b2.getEndTime()-1;i++) {
						rowData[0][i] = " ■";
						table.setValueAt(" ■", 0, i);
					}
				}
				
//				if (b.getDateText().equals(bookDay)) {
//					for (int i = 0; i < 24; i++) {
//						if (b.getStartTime() - 1 <= i && i <= b.getEndTime() - 1) // 해당 시간대에 있으면(bookDay[2020-2-4]랑
//						{															// 스트링 비교 참인
//							rowData[0][i] = " ■";												// 부분의) 값이
//							table.setValueAt(" ■", 0, i);
//						}
//						else {
//							rowData[0][i] = " ";
//							table.setValueAt(" ", 0, i); // 해당 시간대에 값이 없으면 null값으로 비운다.
//						}
//					}
//				}
//			}
				
			}
		});
		
		

		pane2.add(middle);

		String[] columnNames = new String[24];
		for (int i = 0; i < 24; i++) {
			columnNames[i] = (i + 1) + "";
		}
		
		rowData = new String[1][24];
		DefaultTableModel tableModel = new DefaultTableModel(rowData, columnNames);
		table = new JTable(tableModel);
		table.setPreferredScrollableViewportSize(new Dimension(1000, 15));
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ListSelectionModel rowSM = table.getSelectionModel();
		rowSM.addListSelectionListener(this);
		JScrollPane scrollPane = new JScrollPane(table);
		pane2.add(scrollPane, BorderLayout.CENTER);

		pane1.add(front, BorderLayout.LINE_START);
		pane1.add(combo);
		pane1.add(combo1);
		pane1.add(combo2);
		pane1.add(check);

		pane.add(pane1, BorderLayout.NORTH);
		pane.add(pane2);
		return pane;

	}

	JPanel makeBottomPane() {
		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout());
		pane.setOpaque(true);
		pane.setBackground(Color.WHITE);
		JPanel pane1 = new JPanel();
		pane1.setLayout(new FlowLayout());
		pane1.setOpaque(true);
		pane1.setBackground(Color.WHITE);

		JLabel stt = new JLabel("Start Time ~ End Time");
		stt.setForeground(Color.DARK_GRAY);
		stt.setFont(new Font("Tahoma", Font.BOLD, 17));

		JTextField tf1 = new JTextField("Input Start Time");
		tf1.setFont(new Font("Tahoma", Font.ITALIC, 15));
		tf1.setForeground(Color.GRAY);
		tf1.setColumns(10);
		tf1.addMouseListener(new MouseAdapter() { // 클릭시 글자 지워짐
			@Override
			public void mouseClicked(MouseEvent e) {
				tf1.setForeground(Color.BLACK);
				tf1.setFont(new Font("Tahoma", Font.BOLD, 21));
				tf1.setText("");
			}
		});
		JTextField tf2 = new JTextField("Input End Time");
		tf2.setFont(new Font("Tahoma", Font.ITALIC, 15));
		tf2.setForeground(Color.GRAY);
		tf2.setColumns(10);
		tf2.addMouseListener(new MouseAdapter() { // 클릭시 글자 지워짐
			@Override
			public void mouseClicked(MouseEvent e) {
				tf2.setForeground(Color.BLACK);
				tf2.setFont(new Font("Tahoma", Font.BOLD, 21));
				tf2.setText(null);
			}
		});
		JLabel tt = new JLabel(" ~ ");
		tt.setForeground(Color.BLACK);

		JButton goBack = new JButton("SUBMIT");
		goBack.setBackground(Color.GRAY);
		goBack.setForeground(Color.WHITE);
		goBack.setFont(new Font("Tahoma", Font.BOLD, 21));
		goBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int startTime = 0, endTime = 0;
				startTime = Integer.parseInt(tf1.getText())-1;
				endTime = Integer.parseInt(tf2.getText())-1;
				boolean overLabCheck = true;
				while(true) {
					for(int i=startTime;i<endTime;i++) {
						
						if(rowData[0][i].equals(" ■")) {
							overLabCheck = false;
							JOptionPane.showMessageDialog(null, "비어있는 시간을 선택 해 주세요.", "ERROR", JOptionPane.ERROR_MESSAGE);
							break;
						}
						
					}
					
					// 중복되는게 있으면 와일문은 브레이크
					if(!(overLabCheck))
						break;
					
					Book b = new Book();
					String lastBookNum = Main.bmgr.getBookList().get(Main.bmgr.getBookList().size()-1).getBookNumber();
					lastBookNum = lastBookNum.replace("A2019-", "");
					b.SetBookNumber("A2019-"+(Integer.parseInt(lastBookNum)+1));
					b.setUserId(Main.me.getId());
					b.setCarNumber(carNumber);
					b.setDate(bookDay);
					b.setStartTime(startTime+1);
					b.setEndTime(endTime+1);
					b.setUsePrice((endTime-startTime)*Main.cmgr.findMap(carNumber).getPrice());
					b.print();
					Main.cmgr.findMap(carNumber).addCBList(b);
					Main.bmgr.addBookList(b);
					break;
				}
			}
		});

		pane1.add(stt);
		pane1.add(tf1);
		pane1.add(tt);
		pane1.add(tf2);

		pane.add(pane1);
		pane.add(goBack, BorderLayout.EAST);

		return pane;

	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}