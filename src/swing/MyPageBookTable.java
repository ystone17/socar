package swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Book.Book;
import Main.Main;
import User.User;
import swing.LoginFrame;

public class MyPageBookTable extends JPanel implements ActionListener, ListSelectionListener {

	private static final long serialVersionUID = 1L;
	JTable table = null;
	JTextField edits[] = new JTextField[7];

	public MyPageBookTable() {
		super(new BorderLayout());
		userTableInit();
	}

	void userTableInit() {
		final String[] columnNames = { "BookNumber", "ID", "carNumber", "date", "start", "end", "price" };
		DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
		if (Main.me.getBook() != null) {
			for (Book b : Main.me.getBook())
				tableModel.addRow(b.getRowString());
		}
		table = new JTable(tableModel);
		table.setPreferredScrollableViewportSize(new Dimension(500, 220));
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ListSelectionModel rowSM = table.getSelectionModel();
		rowSM.addListSelectionListener(this);
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);
	}

	int selectedIndex = -1;

	private void moveSelectedToEdits() {
		// TODO Auto-generated method stub
	}

	public void actionPerformed(ActionEvent e) {
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