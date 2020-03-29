package companyValueController;

import java.util.Collections;

import companyValueModel.StocksList;

public class Sorter {

//	this class is unnecessary other than it being a container to hold all of the sorting methods for ease of maintenance

	public static void sortByTicker(StocksList stocks) {
		Collections.sort(stocks.getStocks(), (a, b) -> a.getTicker().compareTo(b.getTicker()));
	}

	public static void sortByPrice(StocksList stocks) {
		Collections.sort(stocks.getStocks(),
				(a, b) -> Double.valueOf(a.getCurrentPrice()).compareTo(Double.valueOf(b.getCurrentPrice())));
	}

	public static void sortByMCap(StocksList stocks) {
		Collections.sort(stocks.getStocks(), (a, b) -> a.getMCap().compareTo(b.getMCap()));
	}

	public static void sortBySector(StocksList stocks) {
		Collections.sort(stocks.getStocks(), (a, b) -> a.getSector().compareTo(b.getSector()));
	}

	public static void sortByIndustry(StocksList stocks) {
		Collections.sort(stocks.getStocks(), (a, b) -> a.getIndustry().compareTo(b.getIndustry()));
	}

	public static void sortByCroic(StocksList stocks) {
		Collections.sort(stocks.getStocks(), (a, b) -> a.getCroic().compareTo(b.getCroic()));
	}

	public static void sortByProfitMargin(StocksList stocks) {
		Collections.sort(stocks.getStocks(),
				(a, b) -> Double.valueOf(a.getProfitMargin()[0]).compareTo(Double.valueOf(b.getProfitMargin()[0])));
	}

	public static void sortByAvgProfMargin(StocksList stocks) {
		Collections.sort(stocks.getStocks(),
				(a, b) -> Double.valueOf(a.getProfitMargin()[1]).compareTo(Double.valueOf(b.getProfitMargin()[1])));
	}

	public static void sortByROnAssets(StocksList stocks) {
		Collections.sort(stocks.getStocks(),
				(a, b) -> Double.valueOf(a.getROnAssets()).compareTo(Double.valueOf(b.getROnAssets())));
	}

	public static void sortByROnEquity(StocksList stocks) {
		Collections.sort(stocks.getStocks(),
				(a, b) -> Double.valueOf(a.getROnCapital()).compareTo(Double.valueOf(b.getROnCapital())));
	}

	public static void sortByFCFGrowth(StocksList stocks) {
		Collections.sort(stocks.getStocks(),
				(a, b) -> Double.valueOf(a.getFcfChange()[0]).compareTo(Double.valueOf(b.getFcfChange()[0])));
	}

	public static void sortByAvgFcfGrowth(StocksList stocks) {
		Collections.sort(stocks.getStocks(),
				(a, b) -> Double.valueOf(a.getFcfChange()[1]).compareTo(Double.valueOf(b.getFcfChange()[1])));
	}

	public static void sortByCOH(StocksList stocks) {
		Collections.sort(stocks.getStocks(), (a, b) -> a.getCOH().compareTo(b.getCOH()));
	}

	public static void sortByEquityDebtRatio(StocksList stocks) {
		Collections.sort(stocks.getStocks(),
				(a, b) -> Double.valueOf(a.getEquityToDebt()).compareTo(Double.valueOf(b.getEquityToDebt())));
	}

	public static void sortByCashDebtRatio(StocksList stocks) {
		Collections.sort(stocks.getStocks(),
				(a, b) -> Double.valueOf(a.getCashToDebt()).compareTo(Double.valueOf(b.getCashToDebt())));
	}

	public static void sortByCashYield(StocksList stocks) {
		Collections.sort(stocks.getStocks(),
				(a, b) -> Double.valueOf(a.getCashYield()).compareTo(Double.valueOf(b.getCashYield())));
	}

	public static void sortByFairValue(StocksList stocks) {
		Collections.sort(stocks.getStocks(),
				(a, b) -> Double.valueOf(a.getFairValue()).compareTo(Double.valueOf(b.getFairValue())));
	}
}
