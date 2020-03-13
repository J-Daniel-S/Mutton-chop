package companyValueModel;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Stock {

	private String ticker;
	private String currentPrice;
	private double marginOfSafety;
	private LocalDate lastDate;
	private String cashYield;
	private String MCap;
	private String enterpriseValue;

	private String safeValue;
	private String fairValue;
	private String evEbitda;

	/*
	 * Keys: [companyName], [EBITDA], [Enterprise Value], [description], [industry],
	 * [yearHigh], [price], [sector], [yearLow], [marketCap], [ticker], [Revenue
	 * Growth], [Cash and cash equivalents], [Number of Shares], [date]
	 * 
	 * Array keys: [Revenuei], [Long-term debti], [Dividend per Sharei], [Operating
	 * Cash Flowi], [Net Incomei], [Goodwill and Intangible Assetsi], [Total
	 * shareholders equityi], [Capital Expenditurei], [Total assetsi]
	 * 
	 * keys(i) with numbers at the end represent (0) = this year, (-1) = year prior,
	 * etc.
	 */

	private Map<String, String> profile;

	public Stock(String ticker) {
		this.ticker = ticker;
	}

	public Stock(HashMap<String, String> profile) {
		this.profile = profile;
		this.ticker = profile.get("ticker");
	}

	public Map<String, String> getProfile() {
		return profile;
	}

	public void setProfile(HashMap<String, String> profile) {
		this.profile = profile;
	}

	public String getTicker() {
		return ticker;
	}

	public void setCurrentPrice(String newPrice) {
		currentPrice = newPrice;
	}

	public String getCurrentPrice() {
		return currentPrice;
	}

	public double getMarginOfSafety() {
		return marginOfSafety;
	}

	public void setMarginOfSafety(double mos) {
		this.marginOfSafety = mos;
	}

	public LocalDate getLastDate() {
		return lastDate;
	}

	public void setLastDate(String string) {
		String dateFromWeb = profile.get("date");
		String expectedFormat = "yyyy-MM-dd";
		DateTimeFormatter df = DateTimeFormatter.ofPattern(expectedFormat);
		LocalDate date = LocalDate.parse(dateFromWeb, df);
		this.lastDate = date;
	}

	public String getCashYield() {
		return cashYield;
	}

	public void setCashYield(double cashYield) {
		DecimalFormat df = new DecimalFormat("0.0");
		this.cashYield = df.format(cashYield);
	}

	public String getMCap() {
		return MCap;
	}

	public void setMCap(String mCap) {
		MCap = mCap;
	}

	public String getSafeValue() {
		return safeValue;
	}

	public void setSafeValue() {
		this.safeValue = Evaluator.getSafeValue(this, String.valueOf(this.marginOfSafety)).toString();
	}

	public String getFairValue() {
		return fairValue;
	}

	public void setFairValue(BigDecimal fairValue) {
		this.fairValue = fairValue.toString();
	}

	public String getEnterpriseValue() {
		return enterpriseValue;
	}

	public void setEnterpriseValue(String enterpriseValue) {
		this.enterpriseValue = enterpriseValue;
	}

	public String getEvEbitda() {
		return evEbitda;
	}

	public void setEvEbitda(Double evEbitda) {
		this.evEbitda = evEbitda.toString();
	}

}