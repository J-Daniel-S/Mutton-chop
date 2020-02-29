package miniStock;

import java.awt.BorderLayout;
import java.util.List;

/*
 * Data to fill this table will come from the list of stocks maintaned on the back end.  It will be passed through the controller via a method called
 * getStocks() which will retrieve the list of stocks from the backend and pass it to the table
 * 
 */

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import miniStockModel.Stock;

public class StockTablePanel extends JPanel {
	
	private JTable table;
	private StockTableModel tableModel;
	
	public StockTablePanel() {
		tableModel = new StockTableModel();
		table = new JTable(tableModel);
		
		setLayout(new BorderLayout());
		
		add(new JScrollPane(table), BorderLayout.CENTER);
	}
	
	public void setData(List<Stock> stocks) {
		tableModel.setData(stocks);
	}
	
	public void refresh() {
		tableModel.fireTableDataChanged();
	}
}
