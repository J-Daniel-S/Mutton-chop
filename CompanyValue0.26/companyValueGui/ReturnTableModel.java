package companyValueGui;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import companyValueController.CompanyController;
import companyValueModel.Stock;

public class ReturnTableModel extends BaseTableModel {


	private String[] colName = { "Ticker", "Current Price", "Enterprise Value", "EV/EBITDA",
			"Cash Yield", "Buy and Hold Market Value", "Buy and Hold Safe Value", "CROIC", 
			"Change in FCF"
			};
	private List<Stock> stocks;

	public ReturnTableModel() {
		stocks = new ArrayList<>();
	}
	
	public List<Stock> getStocks() {
		return stocks;
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
		switch (col) {
		case 0:
			return CompanyController.getStocks().get(row).getTicker();
		case 1:
			return CompanyController.getStocks().get(row).getCurrentPrice();
		case 2:
			return formatCapital(CompanyController.getStocks().get(row).getEnterpriseValue());
		case 3:
			return CompanyController.getStocks().get(row).getEvEbitda();
		case 4:
			return CompanyController.getStocks().get(row).getCashYield() + "%";
		case 5:
			return "$" + CompanyController.getStocks().get(row).getFairValue();
		case 6:
			return "$" + CompanyController.getStocks().get(row).getSafeValue();
		case 7:
			return CompanyController.getStocks().get(row).getCroic()+"%";
		case 8:
			return CompanyController.getStocks().get(row).getFcfChange()[0] + "%";
		}
		
		return null;
	}

	private String formatCapital(String cap) {
		DecimalFormat df = new DecimalFormat("#,##0.00");
		double quant = Double.valueOf(cap);
		String size = "";
		if (quant % 10000 != 0) {
			df = new DecimalFormat("#,###.000");
		}
		if (quant/1000000000 > 1) {
			quant = quant/1000000000;
			size = " Billion";
		} else if (quant/1000000 >= 1) {
			quant = quant/1000000;
			size = " Million";
		}
		cap = "$"+df.format(quant) + size;
		return cap;
	}
}