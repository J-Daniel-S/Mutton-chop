package miniStock;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import miniStockModel.Stock;

public class StockTableModel extends AbstractTableModel {

	private String[] colName = { "Ticker", "Company Name" };
	private String ticker;
	private List<Stock> stocks;

	public StockTableModel() {
		stocks = new ArrayList<Stock>();

	}

	public void setData(List<Stock> stocks) {
		this.stocks = stocks;
//		stocks.forEach(x -> System.out.println(x.getTicker()));
	}

	@Override
	public String getColumnName(int column) {
		return colName[column];
	}

	@Override
	public int getColumnCount() {
		return colName.length;
	}

	@Override
	public int getRowCount() {
		return stocks.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Stock stock = stocks.get(row);
		switch (col) {
		case 0:
			return stock.getTicker();
		case 1:
			return stock.getProfile().get("companyName");
		}
		return null;
	}
}
