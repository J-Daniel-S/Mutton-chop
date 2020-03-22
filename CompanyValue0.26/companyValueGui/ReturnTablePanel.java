package companyValueGui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JMenuItem;

/*
 * Data to fill this table will come from the list of stocks maintained on the back end.  It will be passed through the controller via a method called
 * getStocks() which will retrieve the list of stocks from the back end and pass it to the table
 * 
 */

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import companyValueModel.Stock;

public class ReturnTablePanel extends BaseTablePanel {

	private JTable table;
	private ReturnTableModel tableModel;
	private JPopupMenu popup;
	private JTextArea textArea;
	private ClickListener clickListener;

	public ReturnTablePanel() {
		
		tableModel = new ReturnTableModel();
		table = new JTable(tableModel);
		popup = new JPopupMenu();
		
		JMenuItem query = new JMenuItem("What's this?");
		popup.add(query);

		table.setRowHeight(25);
		
		centerJustifyColumns();
		
		TableController.addMouseListener(this, table, tableModel, popup);

		setLayout(new BorderLayout());

		add(new JScrollPane(table), BorderLayout.CENTER);
		autoResizeTable();
	}
	
	private void centerJustifyColumns() {
		DefaultTableCellRenderer centerRender = new DefaultTableCellRenderer();
		centerRender.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(String.class, centerRender);
	}
	
	public void setData(List<Stock> stocks) {
		tableModel.setData(stocks);
	}

	public void refresh() {
		tableModel.fireTableDataChanged();
	}

	private void autoResizeTable() {
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	}
}