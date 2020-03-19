package companyValueController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import companyValueModel.Stock;
import companyValueModel.StocksList;
import companyValueModel.WebReaderFilter;

public class CompanyController {
	StocksList stocks = new StocksList();

	public HashMap<String, String> getStockDetails(String ticker) throws IOException {
		WebReaderFilter wr = new WebReaderFilter();
		HashMap<String, String> details = null;
		details = wr.getStockDetails(ticker);
		return details;
	}

	public void addStock(HashMap<String, String> profile) {
		stocks.addStock(profile);
	}
	
	public void addStock(String ticker) {
		stocks.addStock(ticker);
	}

	public List<Stock> getStocks() {
		return stocks.getStocks();
	}

}
