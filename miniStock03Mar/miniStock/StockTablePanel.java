package miniStock;

import java.awt.BorderLayout;
import java.util.List;

/*
 * Data to fill this table will come from the list of stocks maintained on the back end.  It will be passed through the controller via a method called
 * getStocks() which will retrieve the list of stocks from the back end and pass it to the table
 * 
 */

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import miniStockModel.Stock;

public class StockTablePanel extends JPanel {

	private JTable table;
	private StockTableModel tableModel;
	private JTextArea textArea;

	public StockTablePanel() {

		tableModel = new StockTableModel();
		table = new JTable(tableModel);

		table.setRowHeight(25);

		setLayout(new BorderLayout());

		add(new JScrollPane(table), BorderLayout.CENTER);
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