package companyValueModel;

import java.math.BigDecimal;
import java.math.*;
import java.util.HashMap;

public class Stock {
	
	private String ticker;
	private BigDecimal currentPrice;
	
//	contains price, marketCap, companyName, industry, sector, and description
	private HashMap<String, String> profile;

	public Stock(String ticker) {
		this.ticker = ticker;
	}

	public Stock(HashMap<String, String> profile) {
		this.profile = profile;
		this.ticker = profile.get("ticker");
	}

	public HashMap<String, String> getProfile() {
		return profile;
	}

	public void setProfile(HashMap<String, String> profile) {
		this.profile = profile;
	}

	public String getTicker() {
		return ticker;
	}

	public void setCurrentPrice(BigDecimal price) {
		currentPrice = price;
	}

	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}
}