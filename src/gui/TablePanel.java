package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.Person;

public class TablePanel extends JPanel {

	private JTable table;
	private PersonTableModel tableModel;
	private JPopupMenu popup;
	private PersonTableListener personTableListener;

	public TablePanel() {

		tableModel = new PersonTableModel();
		table = new JTable(tableModel);
//		we added these 4 lines
		popup = new JPopupMenu();

		JMenuItem removeItem = new JMenuItem("Delete Row");
		popup.add(removeItem);

		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				table.getSelectionModel().setSelectionInterval(row, row);

				if (e.getButton() == MouseEvent.BUTTON3) {
//					this method is the only non-deprecated one.  The x and y referred to below are row and column
					popup.show(table, e.getX(), e.getY());
				}
			}

		});

		removeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();

//				we also added this
				if (personTableListener != null) {
					personTableListener.rowDeleted(row);
//					this is more efficient than just telling the table that the data has changed
					tableModel.fireTableRowsDeleted(row, row);
				}
			}
		});

		setLayout(new BorderLayout());
		add(new JScrollPane(table), BorderLayout.CENTER);
	}

	public void setData(List<Person> db) {
		tableModel.setData(db);
	}

	public void refresh() {
		tableModel.fireTableDataChanged();
	}

//	we added this method in this tutorial
	public void setPersonTableListener(PersonTableListener listener) {
		this.personTableListener = listener;
	}
}
