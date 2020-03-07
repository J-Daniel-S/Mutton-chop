package companyValueModel;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.HashMap;

public class Stock {

	private String ticker;
	private BigDecimal currentPrice;

//	there arrays are to be deleted.  They will continue to exists until I finish evaluator
	private BigInteger[] freeCashFlowArr;
	private BigInteger[] cashFlowArr;
	private BigInteger[] debtArr;
	private BigInteger[] revenueArr;
	private BigDecimal[] divPerShareArr;

	/*
	 * Keys: [companyName], [EBITDA], [Enterprise Value], [description], [industry],
	 * [yearHigh], [price], [sector], [yearLow], [marketCap], [ticker], [Revenue
	 * Growth], [Cash and cash equivalents]
	 * 
	 * Array keys: [Revenuei], [Long-term debti], [Dividend per Sharei], [Operating
	 * Cash Flowi], [Net Incomei], [Goodwill and Intangible Assetsi], [Total
	 * shareholders equityi], [Capital Expenditurei], [Total assetsi]
	 * 
	 * keys(i) with numbers at the end represent (0) = this year, (-1) = year prior,
	 * etc.
	 */

	private HashMap<String, String> profile;

	public Stock(String ticker) {
		this.ticker = ticker;

		freeCashFlowArr = new BigInteger[3];
		cashFlowArr = new BigInteger[3];
		debtArr = new BigInteger[3];
		revenueArr = new BigInteger[3];
		divPerShareArr = new BigDecimal[2];
	}

	public Stock(HashMap<String, String> profile) {
		this.profile = profile;
		this.ticker = profile.get("ticker");

		freeCashFlowArr = new BigInteger[3];
		cashFlowArr = new BigInteger[3];
		debtArr = new BigInteger[3];
		revenueArr = new BigInteger[3];
		divPerShareArr = new BigDecimal[2];
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

//	this method is dead.  It contains code that may be useful until I finish evaluator though.
	public void setArrays(HashMap<String, String> profile) throws NumberFormatException {
		DecimalFormat df = new DecimalFormat("#0.00");

		int j = 0;
		for (int i = 0; i < cashFlowArr.length; i++) {

//			might should refactor
			double debt = Double.parseDouble(profile.get("Long-term debt" + j));
			BigInteger dt = BigInteger.valueOf((long) debt);
			debtArr[i] = dt;

			double flow = Double.parseDouble(profile.get("Operating Cash Flow" + j));
			BigInteger cf = BigInteger.valueOf((long) flow);
			cashFlowArr[i] = cf;

			double rev = Double.parseDouble(profile.get("Revenue" + j));
			String reve = BigDecimal.valueOf(rev).toPlainString();
			BigInteger revenue = BigInteger.valueOf(Long.parseLong(reve));
			revenueArr[i] = revenue;

			if (i < 2) {
				double div = Double.parseDouble(profile.get("Dividend per Share" + j));
				div = Double.parseDouble(df.format(div));
				BigDecimal dividend = BigDecimal.valueOf((double) div);
				divPerShareArr[i] = dividend;
			}

			j--;
		}
	}

	public BigInteger[] getCashFlowArray() {
		return cashFlowArr;
	}

	public BigInteger[] getRevenueArray() {
		return revenueArr;
	}

	public BigInteger[] getFreeCashFlowArray() {
		return freeCashFlowArr;
	}

	public BigInteger[] getDebtArray() {
		return debtArr;
	}

	public BigDecimal[] getDivPerShareArray() {
		return divPerShareArr;
	}
}