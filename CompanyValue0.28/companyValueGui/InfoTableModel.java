package companyValueGui;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import companyValueController.CompanyController;
import companyValueModel.Stock;

public class InfoTableModel extends BaseTableModel {

	private String[] colName = { "Ticker", "Company Name", "Current Price", "Market Capitalization",
			"Shares Outstanding", "Sector", "Industry" };
	private List<Stock> stocks;

	public InfoTableModel() {
		stocks = new ArrayList<>();
	}

	public void setData(List<Stock> stocks) {
		this.stocks = stocks;
	}

	public List<Stock> getStocks() {
		return stocks;
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
			return nullCheck(row, CompanyController.getStocks().get(row).getName());
		case 2:
			return nullCheck(row, "$" + formatPrice(CompanyController.getStocks().get(row).getCurrentPrice()));
		case 3:
			return nullCheck(row, formatCap(CompanyController.getStocks().get(row).getMCap()));
		case 4:
			return nullCheck(row, formatShares(CompanyController.getStocks().get(row).getSharesOut()));
		case 5:
			return nullCheck(row, CompanyController.getStocks().get(row).getSector());
		case 6:
			return nullCheck(row, CompanyController.getStocks().get(row).getIndustry());
		}

		return null;
	}

	private String formatPrice(String price) {
		try {
			DecimalFormat df = new DecimalFormat("#,###.00");
			return df.format(price);
		} catch (IllegalArgumentException e) {
			return price;
		}

	}

	private String formatCap(Long mCap) {
		if (mCap == null) {
			return "";
		}
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
		if (cap == 0.0) {
			return "Erroneous data from API.";
		}

		return "$" + df.format(cap) + size;
	}

	private String formatShares(String sharesOut) {
		if (sharesOut == null) {
			return "";
		}
		DecimalFormat df = new DecimalFormat("#,###.00");
		double cap = Double.valueOf(sharesOut);
		if (cap % 10000 != 0) {
			df = new DecimalFormat("#,##0.000");
		}
		String size = " Million";
		if (cap / 1000000 >= 1) {
			cap = cap / 1000000;
		}
		sharesOut = df.format(cap) + size;

		if (cap == 0.0) {
			return "Erroneous data from API.";
		}

		return sharesOut;
	}
}