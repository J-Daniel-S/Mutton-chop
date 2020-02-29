package miniStockModel;

import java.util.ArrayList;

public class Stock {
	
	private String ticker;
//	we'll temporarily pass this directly and fix it later
	public Stock(String ticker) {
		this.ticker = ticker;
	}

	public String getTicker() {
		return ticker;
	}
}
