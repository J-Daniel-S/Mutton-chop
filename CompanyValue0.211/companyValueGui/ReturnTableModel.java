package companyValueGui;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import companyValueController.CompanyController;
import companyValueModel.Stock;

public class ReturnTableModel extends BaseTableModel {

	private String[] colName = { "Ticker", "Current Price", "Buy and Hold Market Value", "Buy and Hold Safe Value",
			"Current Margin of Safety", "Desired Margin of Safety", "Cash Yield", "Dividends Per Share (Year Prior)",
			"Capital Efficiency", "FCF Growth", "Average FCF Growth (past 5 years)" };
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
			return nullCheck(row, "$" + CompanyController.getStocks().get(row).getCurrentPrice());
		case 2:
			return nullCheck(row, "$" + CompanyController.getStocks().get(row).getFairValue());
		case 3:
			return nullCheck(row, "$" + CompanyController.getStocks().get(row).getSafeValue());
		case 4:
			return currentMarginOfSafety(row);
		case 5:
			return nullAndOneCheck(row,
					(long) (CompanyController.getStocks().get(row).getMarginOfSafety() * 100) + "%");
		case 6:
			return nullCheck(row, CompanyController.getStocks().get(row).getCashYield() + "%");
		case 7:
			return nullCheck(row, "$" + CompanyController.getStocks().get(row).getDivPerShare()[0] + " ($"
					+ CompanyController.getStocks().get(row).getDivPerShare()[1] + ")");
		case 8:
			return nullCheck(row, CompanyController.getStocks().get(row).getCapEff());
		case 9:
			return nullCheck(row, CompanyController.getStocks().get(row).getFcfChange()[0] + "%");
		case 10:
			return nullCheck(row, CompanyController.getStocks().get(row).getFcfChange()[1] + "%");
		}
		return null;
	}

	protected String nullAndOneCheck(int row, String string) {

		if (CompanyController.getStocks().get(row).getCurrentPrice() == null
				|| CompanyController.getStocks().get(row).getMarginOfSafety() == 1.0) {
			return "";
		} else {
			return string;
		}
	}

	private String currentMarginOfSafety(int row) {
		if (CompanyController.getStocks().get(row).getCurrentPrice() == null) {
			return "";
		}
		DecimalFormat df = new DecimalFormat("0.0");
		double ratio;
		try {
			ratio = 1 - (Double.valueOf(CompanyController.getStocks().get(row).getCurrentPrice()) * 100)
					/ (Double.valueOf(CompanyController.getStocks().get(row).getFairValue()) * 100);
			return df.format(ratio * 100) + "%";
		} catch (NumberFormatException e) {
			return "0";
		}

	}
}