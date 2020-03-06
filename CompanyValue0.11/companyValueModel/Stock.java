package companyValueModel;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;

public class Stock {

	private String ticker;
	private BigDecimal currentPrice;

	private BigInteger[] freeCashFlow;
	private BigInteger[] cashFlow;
	private BigInteger[] debt;
	private BigDecimal[] divPerShare;

	/*
	 * Keys: [companyName], [EBITDA], [Enterprise Value], [description], [industry],
	 * [yearHigh], [price], [Total shareholders equity], [Goodwill and Intangible
	 * Assets], [Capital Expenditure], [sector], [yearLow], [marketCap], [Dividend
	 * payments], [ticker], [Revenue Growth], [Cash and short-term investments],
	 * [Net Income], [Revenue]
	 * 
	 * Array keys: [Long-term debt-1], [Free Cash Flow0], [Long-term debt-2],
	 * [Long-term debt0], [Free Cash Flow-2], [Dividend per Share0], [Free Cash
	 * Flow-1], [Dividend per Share-1], [Operating Cash Flow0], [Operating Cash
	 * Flow-1], [Operating Cash Flow-2]
	 * 
	 * keys with numbers at the end represent (0) = this year, (-1) = year prior,
	 * etc.
	 */

	private HashMap<String, String> profile;

	public Stock(String ticker) {
		this.ticker = ticker;
	}

	public Stock(HashMap<String, String> profile) {
		this.profile = profile;
		this.ticker = profile.get("ticker");
		freeCashFlow = new BigInteger[3];
		cashFlow = new BigInteger[3];
		debt = new BigInteger[3];
		divPerShare = new BigDecimal[2];
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

	public void setArrays(HashMap<String, String> profile) throws NumberFormatException {
		int j = 0;
//		this line throws the NPE.  It disappears if I replace cashFlow.length with 3
		for (int i = 0; i < cashFlow.length; i++) {
//			This line was to verify that the key existed (it does)
			System.out.println(profile.get("Operating Cash Flow" + j));

			double flow = Double.parseDouble(profile.get("Operating Cash Flow" + j));
			BigInteger cf = BigInteger.valueOf((long) flow);
			j--;
//			This method is incomplete
		}
	}

	public BigInteger[] getCashFlowArray() {
		return cashFlow;
	}

	public BigInteger[] getFreeCashFlowArray() {
		return freeCashFlow;
	}

	public BigInteger[] getDebtArray() {
		return debt;
	}

	public BigDecimal[] getDivPerShare() {
		return divPerShare;
	}
}