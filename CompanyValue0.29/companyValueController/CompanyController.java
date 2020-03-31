package companyValueController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import companyValueModel.Evaluator;
import companyValueModel.FMPWebReaderFilter;
import companyValueModel.Stock;
import companyValueModel.StocksList;

public interface CompanyController {
	StocksList stocks = new StocksList();
	final ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);

	public static HashMap<String, String> getStockDetails(String ticker) throws IOException {
		FMPWebReaderFilter wr = new FMPWebReaderFilter();
		HashMap<String, String> details = wr.getStockDetails(ticker);
		return details;
	}

	// checks to see if the list contains the stock already. A return of true stops
	// adding the duplicate stock.
	public static boolean checkTicker(String ticker) {
		return stocks.getTickers().contains(ticker);
	}

	public static void removeStock(String ticker) {
		stocks.getStocks().removeIf(a -> a.getTicker().equals(ticker));
	}

	public static void addStock(HashMap<String, String> profile) {
		stocks.addStock(profile);
	}

	public static void addStock(String ticker) {
		stocks.addStock(ticker);
	}

	public static List<Stock> getStocks() {
		return stocks.getStocks();
	}

	public static void updatePrice() {
		Updater.update();
	}

	public static void sortByTicker() {
		Sorter.sortByTicker(stocks);
	}

	public static void sortByPrice() {
		Sorter.sortByPrice(stocks);
	}

	public static void sortByMCap() {
		Sorter.sortByMCap(stocks);
	}

	public static void sortBySector() {
		Sorter.sortBySector(stocks);
	}

	public static void sortByIndustry() {
		Sorter.sortByIndustry(stocks);
	}

	public static void sortByCroic() {
		Sorter.sortByCroic(stocks);
	}

	public static void sortByProfitMargin() {
		Sorter.sortByProfitMargin(stocks);
	}

	public static void sortByAvgProfMargin() {
		Sorter.sortByAvgProfMargin(stocks);
	}

	public static void sortByROnAssets() {
		Sorter.sortByROnAssets(stocks);
	}

	public static void sortByROnEquity() {
		Sorter.sortByROnEquity(stocks);
	}

	public static void sortByFCFGrowth() {
		Sorter.sortByFCFGrowth(stocks);
	}

	public static void sortByAvgFcfGrowth() {
		Sorter.sortByAvgFcfGrowth(stocks);
	}

	public static void sortByCOH() {
		Sorter.sortByCOH(stocks);
	}

	public static void sortByEquityDebtRatio() {
		Sorter.sortByEquityDebtRatio(stocks);
	}

	public static void sortByCashDebtRatio() {
		Sorter.sortByCashDebtRatio(stocks);
	}

	public static void sortByCashYield() {
		Sorter.sortByCashYield(stocks);
	}

	public static void sortByFairValue() {
		Sorter.sortByFairValue(stocks);
	}

	public static boolean noRevenue(int row) {
		boolean missing = stocks.getStocks().get(row).getRevenue().equals("0.0");
		return missing;
	}

	public static void recalculate(String ticker) {
		Optional<Stock> changed = stocks.getStocks().stream().filter(s -> s.getTicker().equals(ticker)).findFirst();

		if (changed.isPresent()) {
			Evaluator.recalculate(changed.get());
		}

	}
}