package companyValueModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StocksList {

	private List<Stock> stocks;

	public StocksList() {
		stocks = new ArrayList<Stock>();
	}

	public void addStock(String ticker) {
		Stock stock = new Stock(ticker.toUpperCase());
		stocks.add(stock);
	}

	public void addStock(HashMap<String, String> profile) {
		Stock stock = new Stock(profile);
		stocks.add(stock);
	}

	public List<Stock> getStocks() {
		return stocks;
	}

	public Stock get(int index) {
		return stocks.get(index);
	}
	
//	for testing
	public void addStock(Stock stock) {
		stocks.add(stock);
	}

	public void setStocks(List<Stock> theStocks) {
		this.stocks = theStocks;
	}
}