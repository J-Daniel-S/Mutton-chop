package miniStockModel;

import java.util.ArrayList;
import java.util.List;

public class StocksList {
	
	private List<Stock> stocks;
	
	public StocksList() {
		stocks = new ArrayList<Stock>();
	}
	
	public void addStock(Stock stock) {
		stocks.add(stock);
	}

	public List<Stock> getStocks() {
		return stocks;
	}
	
}
