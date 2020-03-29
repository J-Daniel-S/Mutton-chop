package companyValueGui;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import companyValueController.CompanyController;
import companyValueModel.Stock;

public class FinancialTableModel extends BaseTableModel {

	private String[] colName = { "Ticker", "Profit Margin (Average)", "Cash Return on Invested Capital",
			"Return on Assets", "Return on Equity", "Free Cash Flow (FCF)", "Change in FCF", "Cash on Hand",
			"Equity/Debt Ratio", "Cash/Debt Ratio" };
	private List<Stock> stocks;

	public FinancialTableModel() {
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
			return nullCheck(row, CompanyController.getStocks().get(row).getProfitMargin()[0] + "%" + " ("
					+ CompanyController.getStocks().get(row).getProfitMargin()[1] + "%)");
		case 2:
			return nullCheck(row, CompanyController.getStocks().get(row).getCroic() + "%");
		case 3:
			return nullCheck(row, CompanyController.getStocks().get(row).getROnAssets() + "%");
		case 4:
			return nullCheck(row, checkROnCapital(CompanyController.getStocks().get(row).getROnCapital()) + "%");
		case 5:
			return nullCheck(row, formatCapital(CompanyController.getStocks().get(row).getFcf()[0]));
		case 6:
			return nullCheck(row, CompanyController.getStocks().get(row).getFcfChange()[0] + "%");
		case 7:
			return nullCheck(row, formatCapital(CompanyController.getStocks().get(row).getCOH()));
		case 8:
			return nullCheck(row, checkForDebt(CompanyController.getStocks().get(row).getEquityToDebt()));
		case 9:
			return nullCheck(row, checkForDebt(CompanyController.getStocks().get(row).getCashToDebt()));
		}

		return null;
	}

	private String formatCapital(String cap) {
		if (cap == null) {
			return "";
		}
		DecimalFormat df = new DecimalFormat("#,##0.00");
		double quant = Double.valueOf(cap);
		String size = "";
		if (quant % 10000 != 0) {
			df = new DecimalFormat("#,###.000");
		}
		if (quant / 1000000000 > 1) {
			quant = quant / 1000000000;
			size = " Billion";
		} else if (quant / 1000000 >= 1) {
			quant = quant / 1000000;
			size = " Million";
		}
		return "$" + df.format(quant) + size;
	}

	private String formatCapital(Long cap) {
		if (cap == null) {
			return "";
		}
		DecimalFormat df = new DecimalFormat("#,##0.00");
		double quant = Double.valueOf(cap);
		String size = "";
		if (quant % 10000 != 0) {
			df = new DecimalFormat("#,###.000");
		}
		if (quant / 1000000000 > 1) {
			quant = quant / 1000000000;
			size = " Billion";
		} else if (quant / 1000000 >= 1) {
			quant = quant / 1000000;
			size = " Million";
		}
		return "$" + df.format(quant) + size;
	}

	private String checkForDebt(String ratio) {
		if (ratio == null) {
			return "";
		}
		if (ratio.equals("-1.0")) {
			return "No Long Term Debt";

		} else {
			return ratio;
		}
	}

	private String checkROnCapital(String roc) {
		if (roc == null) {
			return "";
		}
		if (roc.equals("1000")) {
			return "Erroneous data from API: Please enter manually";
		} else {
			return roc;
		}
	}

}