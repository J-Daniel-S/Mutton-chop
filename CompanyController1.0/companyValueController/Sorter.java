package companyValueController;

import java.util.Collections;

import javax.swing.JOptionPane;

import companyValueGui.MainFrame;
import companyValueModel.StocksList;

public class Sorter {

//	this class is unnecessary other than it being a container to hold all of the sorting methods for ease of maintenance

	public static void sortByTicker(StocksList stocks, MainFrame frame) {
		Collections.sort(stocks.getStocks(), (a, b) -> a.getTicker().compareTo(b.getTicker()));
	}

	public static void sortByPrice(StocksList stocks, MainFrame frame) {
		try {
			Collections.sort(stocks.getStocks(),
					(a, b) -> Double.valueOf(a.getCurrentPrice()).compareTo(Double.valueOf(b.getCurrentPrice())));
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(frame, "Cannot sort while the stocks in the list contain empty values",
					"Sort Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void sortByMCap(StocksList stocks, MainFrame frame) {
		try {
			Collections.sort(stocks.getStocks(), (a, b) -> a.getMCap().compareTo(b.getMCap()));
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(frame, "Cannot sort while the stocks in the list contain empty values",
					"Sort Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void sortBySector(StocksList stocks, MainFrame frame) {
		try {
			Collections.sort(stocks.getStocks(), (a, b) -> a.getSector().compareTo(b.getSector()));
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(frame, "Cannot sort while the stocks in the list contain empty values",
					"Sort Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void sortByIndustry(StocksList stocks, MainFrame frame) {
		try {
			Collections.sort(stocks.getStocks(), (a, b) -> a.getIndustry().compareTo(b.getIndustry()));
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(frame, "Cannot sort while the stocks in the list contain empty values",
					"Sort Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void sortByCroic(StocksList stocks, MainFrame frame) {
		try {
			Collections.sort(stocks.getStocks(),
					(a, b) -> Double.valueOf(a.getCroic()[0]).compareTo(Double.valueOf(b.getCroic()[0])));
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(frame, "Cannot sort while the stocks in the list contain empty values",
					"Sort Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void sortByProfitMargin(StocksList stocks, MainFrame frame) {
		try {
			Collections.sort(stocks.getStocks(),
					(a, b) -> Double.valueOf(a.getProfitMargin()[0]).compareTo(Double.valueOf(b.getProfitMargin()[0])));
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(frame, "Cannot sort while the stocks in the list contain empty values",
					"Sort Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void sortByAvgProfMargin(StocksList stocks, MainFrame frame) {
		try {
			Collections.sort(stocks.getStocks(),
					(a, b) -> Double.valueOf(a.getProfitMargin()[1]).compareTo(Double.valueOf(b.getProfitMargin()[1])));
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(frame, "Cannot sort while the stocks in the list contain empty values",
					"Sort Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void sortByROnAssets(StocksList stocks, MainFrame frame) {
		try {
			Collections.sort(stocks.getStocks(),
					(a, b) -> Double.valueOf(a.getROnAssets()).compareTo(Double.valueOf(b.getROnAssets())));
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(frame, "Cannot sort while the stocks in the list contain empty values",
					"Sort Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void sortByROnEquity(StocksList stocks, MainFrame frame) {
		try {
			Collections.sort(stocks.getStocks(),
					(a, b) -> Double.valueOf(a.getROnCapital()).compareTo(Double.valueOf(b.getROnCapital())));
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(frame, "Cannot sort while the stocks in the list contain empty values",
					"Sort Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void sortByFCFGrowth(StocksList stocks, MainFrame frame) {
		try {
			Collections.sort(stocks.getStocks(),
					(a, b) -> Double.valueOf(a.getFcfChange()[0]).compareTo(Double.valueOf(b.getFcfChange()[0])));
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(frame, "Cannot sort while the stocks in the list contain empty values",
					"Sort Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void sortByAvgFcfGrowth(StocksList stocks, MainFrame frame) {
		try {
			Collections.sort(stocks.getStocks(),
					(a, b) -> Double.valueOf(a.getFcfChange()[1]).compareTo(Double.valueOf(b.getFcfChange()[1])));
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(frame, "Cannot sort while the stocks in the list contain empty values",
					"Sort Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void sortByCOH(StocksList stocks, MainFrame frame) {
		try {
			Collections.sort(stocks.getStocks(), (a, b) -> a.getCOH().compareTo(b.getCOH()));
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(frame, "Cannot sort while the stocks in the list contain empty values",
					"Sort Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void sortByEquityDebtRatio(StocksList stocks, MainFrame frame) {
		try {
			Collections.sort(stocks.getStocks(),
					(a, b) -> Double.valueOf(a.getEquityToDebt()).compareTo(Double.valueOf(b.getEquityToDebt())));
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(frame, "Cannot sort while the stocks in the list contain empty values",
					"Sort Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void sortByCashDebtRatio(StocksList stocks, MainFrame frame) {
		try {
			Collections.sort(stocks.getStocks(),
					(a, b) -> Double.valueOf(a.getCashToDebt()).compareTo(Double.valueOf(b.getCashToDebt())));
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(frame, "Cannot sort while the stocks in the list contain empty values",
					"Sort Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void sortByCashYield(StocksList stocks, MainFrame frame) {
		try {
			Collections.sort(stocks.getStocks(),
					(a, b) -> Double.valueOf(a.getCashYield()).compareTo(Double.valueOf(b.getCashYield())));
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(frame, "Cannot sort while the stocks in the list contain empty values",
					"Sort Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void sortByFairValue(StocksList stocks, MainFrame frame) {
		try {
			Collections.sort(stocks.getStocks(),
					(a, b) -> Double.valueOf(a.getFairValue()).compareTo(Double.valueOf(b.getFairValue())));
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(frame, "Cannot sort while the stocks in the list contain empty values",
					"Sort Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}