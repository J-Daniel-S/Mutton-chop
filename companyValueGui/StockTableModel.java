package companyValueGui;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import companyValueModel.Stock;

public class StockTableModel extends AbstractTableModel {

	private String[] colName = { "Ticker", "Company Name", "Market Cap (Last Quarterly)", "Sector", "Industry" };
	private String ticker;
	private List<Stock> stocks;

	public StockTableModel() {
		stocks = new ArrayList<Stock>();

	}

	public void setData(List<Stock> stocks) {
		this.stocks = stocks;
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
		case 2:
			String mCap = formatCap(stock.getProfile().get("marketCap"));
			return mCap;
		case 3:
			return stock.getProfile().get("sector");
		case 4:
			return stock.getProfile().get("industry");
		}

		return null;
	}

	private String formatCap(String mCap) {
		DecimalFormat df = new DecimalFormat("0.00");
		double cap = Double.valueOf(mCap);
		String size = "";
		if (cap / 1000000000 > 1) {
			cap = cap / 1000000000;
			size = " Billion";
		} else if (cap / 1000000 >= 1) {
			cap = cap / 1000000;
			size = " Million";
		}
		mCap = df.format(cap) + size;
		return mCap;
	}
}