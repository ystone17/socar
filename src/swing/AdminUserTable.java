package swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Account.Account;
import Main.Main;
import User.User;

public class AdminUserTable extends JPanel implements ActionListener, ListSelectionListener {

	private static final long serialVersionUID = 1L;
	JTable table = null;
	JTextField edits[] = new JTextField[5];

	public AdminUserTable() {
		super(new BorderLayout());
		userTableInit();
		JPanel pane = makeBottomPane();
		add(pane, BorderLayout.PAGE_END);
		FocusEvent();
	}

	void userTableInit() {
		final String[] columnNames = { "Id", "Name", "birthday", "Type" };
		DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

		for (User s : Main.umgr.getUserList())
			tableModel.addRow(s.getRowString());

		table = new JTable(tableModel);
		table.setPreferredScrollableViewportSize(new Dimension(500, 220));
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ListSelectionModel rowSM = table.getSelectionModel();
		rowSM.addListSelectionListener(this);
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);
	}

	JPanel makeBottomPane() {
		JPanel pane = new JPanel();
		pane.setLayout(new GridLayout(2, 1));

		JPanel center = new JPanel();
		center.setLayout(new FlowLayout());
		for (int i = 0; i < 5; i++) {
			edits[i] = new JTextField("", 10);
			center.add(edits[i]);
		}

		pane.add(center);

		JPanel bottom = new JPanel();
		bottom.setLayout(new FlowLayout());
		JButton buttons[] = new JButton[2];
		String btnTexts[] = { "추가", "삭제" };
		for (int i = 0; i < 2; i++) {
			buttons[i] = new JButton(btnTexts[i]);
			buttons[i].addActionListener(this);
			bottom.add(buttons[i]);
		}
		pane.add(bottom);
		return pane;
	}
	
	private void FocusEvent() {
		edits[0].setText("아이디");
		edits[1].setText("비밀번호");
		edits[2].setText("이름");
		edits[3].setText("yyyy-mm-dd");
		edits[4].setText("Normal or Admin");
		edits[0].addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				if (edits[0].getText().trim().length() == 0) {
					edits[0].setText("아이디");
				}
			}

			public void focusGained(FocusEvent e) {
				if (edits[0].getText().trim().equals("아이디")) {
					edits[0].setText("");
				}
			}
		});
		edits[1].addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				if (edits[1].getText().trim().length() == 0) {
					edits[1].setText("비밀번호");
				}
			}

			public void focusGained(FocusEvent e) {
				if (edits[1].getText().trim().equals("비밀번호")) {
					edits[1].setText("");
				}
			}
		});
		edits[2].addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				if (edits[2].getText().trim().length() == 0) {
					edits[2].setText("이름");
				}
			}

			public void focusGained(FocusEvent e) {
				if (edits[2].getText().trim().equals("이름")) {
					edits[2].setText("");
				}
			}
		});
		edits[3].addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				if (edits[3].getText().trim().length() == 0) {
					edits[3].setText("yyyy-mm-dd");
				}
			}

			public void focusGained(FocusEvent e) {
				if (edits[3].getText().trim().equals("yyyy-mm-dd")) {
					edits[3].setText("");
				}
			}
		});
		edits[4].addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				if (edits[4].getText().trim().length() == 0) {
					edits[4].setText("Normal or Admin");
				}
			}

			public void focusGained(FocusEvent e) {
				if (edits[4].getText().trim().equals("Normal or Admin")) {
					edits[4].setText("");
				}
			}
		});
	}

	int selectedIndex = -1;

	private void moveSelectedToEdits() {
		// TODO Auto-generated method stub
		String[] texts = Main.umgr.getUserList().get(selectedIndex).getRowString();
		edits[0].setText(texts[0]);
		edits[1].setText(Main.acmgr.getAcList().get(selectedIndex).getPwd());
		for (int i = 2; i < 5; i++) {
			edits[i].setText(texts[i-1]);
		}
	}

	public void actionPerformed(ActionEvent e) {
		DefaultTableModel data = (DefaultTableModel) (table.getModel());
		User u = new User();
		Account a = new Account();
		
		u.setId(edits[0].getText());
		a.setId(edits[0].getText());
		a.setPwd(edits[1].getText());

		u.setName(edits[2].getText());
		u.setBirthDay(edits[3].getText());
		u.setLevel(edits[4].getText());
		
		if (e.getActionCommand().equals("추가")) {
			Main.umgr.getUserList().add(u);
			Main.umgr.putUserMap(u);
			Main.acmgr.getAcList().add(a);
			Main.acmgr.putAcMap(a);

			data.addRow(u.getRowString());

		} else if (e.getActionCommand().equals("삭제")) {
			data.removeRow(selectedIndex);
			Main.umgr.removeHashMap(u);
			Main.umgr.getUserList().remove(selectedIndex);
			Main.acmgr.removeHashMap(a);
			Main.acmgr.getAcList().remove(selectedIndex);
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		ListSelectionModel e = (ListSelectionModel) arg0.getSource();
		if (!e.isSelectionEmpty()) {
			selectedIndex = e.getMinSelectionIndex();
			moveSelectedToEdits();
		}
	}

}