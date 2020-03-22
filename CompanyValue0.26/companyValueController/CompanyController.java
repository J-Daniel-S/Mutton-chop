package companyValueController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import companyValueModel.Stock;
import companyValueModel.StocksList;
import companyValueModel.FMPWebReaderFilter;

public interface CompanyController {
	StocksList stocks = new StocksList();

	public static HashMap<String, String> getStockDetails(String ticker) throws IOException {
		FMPWebReaderFilter wr = new FMPWebReaderFilter();
		HashMap<String, String> details = null;
		details = wr.getStockDetails(ticker);
		return details;
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
}
