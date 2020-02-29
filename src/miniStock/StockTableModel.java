package miniStock;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import miniStockModel.Stock;

public class StockTableModel extends AbstractTableModel {
	
	private List<Stock> stocks;
//	
	public StockTableModel() {
	}
	
	public void setData(List<Stock> stocks) {
		this.stocks = stocks;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
