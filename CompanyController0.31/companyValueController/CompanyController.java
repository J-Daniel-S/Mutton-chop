package companyValueController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import companyValueGui.MainFrame;
import companyValueGui.MultiEntryPanel;
import companyValueModel.Evaluator;
import companyValueModel.FMPWebReaderFilter;
import companyValueModel.Stock;
import companyValueModel.StocksList;

public class CompanyController {
	private static StocksList stocks = new StocksList();
	private static CompanyController controller;
	final ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);
	private static String returnOnInvestment;

	public static CompanyController getInstance() {
		if (controller == null) {
			controller = new CompanyController();
		}

		return controller;
	}

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

	public static void addStock(String ticker, MainFrame frame, int row) {
		MultiEntryPanel multi = new MultiEntryPanel(row, frame);
		stocks.addStock(ticker);
	}

	public static List<Stock> getStocks() {
		return stocks.getStocks();
	}

	public static void setStocks(StocksList stocks) {
		CompanyController.stocks = stocks;
	}

	public static void updatePrice() {
		Updater.update();
	}

	// handles values entered by the user as opposed to retrieved from the API
	public static void manualValues(int row, HashMap<String, List<String>> profile) {
		Stock stock = stocks.get(row);
		Evaluator.storeDetails(stock, profile);
	}

	public static void sortByTicker(MainFrame frame) {
		Sorter.sortByTicker(stocks, frame);
	}

	public static void sortByPrice(MainFrame frame) {
		Sorter.sortByPrice(stocks, frame);
	}

	public static void sortByMCap(MainFrame frame) {
		Sorter.sortByMCap(stocks, frame);
	}

	public static void sortBySector(MainFrame frame) {
		Sorter.sortBySector(stocks, frame);
	}

	public static void sortByIndustry(MainFrame frame) {
		Sorter.sortByIndustry(stocks, frame);
	}

	public static void sortByCroic(MainFrame frame) {
		Sorter.sortByCroic(stocks, frame);
	}

	public static void sortByProfitMargin(MainFrame frame) {
		Sorter.sortByProfitMargin(stocks, frame);
	}

	public static void sortByAvgProfMargin(MainFrame frame) {
		Sorter.sortByAvgProfMargin(stocks, frame);
	}

	public static void sortByROnAssets(MainFrame frame) {
		Sorter.sortByROnAssets(stocks, frame);
	}

	public static void sortByROnEquity(MainFrame frame) {
		Sorter.sortByROnEquity(stocks, frame);
	}

	public static void sortByFCFGrowth(MainFrame frame) {
		Sorter.sortByFCFGrowth(stocks, frame);
	}

	public static void sortByAvgFcfGrowth(MainFrame frame) {
		Sorter.sortByAvgFcfGrowth(stocks, frame);
	}

	public static void sortByCOH(MainFrame frame) {
		Sorter.sortByCOH(stocks, frame);
	}

	public static void sortByEquityDebtRatio(MainFrame frame) {
		Sorter.sortByEquityDebtRatio(stocks, frame);
	}

	public static void sortByCashDebtRatio(MainFrame frame) {
		Sorter.sortByCashDebtRatio(stocks, frame);
	}

	public static void sortByCashYield(MainFrame frame) {
		Sorter.sortByCashYield(stocks, frame);
	}

	public static void sortByFairValue(MainFrame frame) {
		Sorter.sortByFairValue(stocks, frame);
	}

	public static boolean noRevenue(int row) {

		try {
			boolean missing = stocks.getStocks().get(row).getRevenue().equals("0.0");
			return missing;
		} catch (NullPointerException e) {
			return true;
		}
	}

	public String getReturnOnInvestment() {
		if (controller != null) {
			return controller.returnOnInvestment;
		} else {
			return "10";
		}

	}

	public void setReturnOnInvestment(String returnOnInvestment) {
		controller.returnOnInvestment = returnOnInvestment;
		Evaluator.changedReturn(controller);
	}

//	for this method int 1 stands for past growth rates and int 2 stands for croic
	public static void setEstimatedGrowthRate(int choice) {
		Evaluator.changedGrowthChoice(choice, controller);
	}

	public static void setAutoUpdate(int index, boolean update) {
		stocks.get(index).setAutoUpdate(!update);
	}

}