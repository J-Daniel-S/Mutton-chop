package companyValueModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

//free api is here: https://financialmodelingprep.com/developer/docs/

public class WebReaderFilter {
	private String url;
//	these are for testing so that the data does not need to be manually updated yoy if testing must be resumed
	private boolean test;
	private Map<String, String> detailsTesting;

//	if you add a counter do not forget to reset it at the end of the import
	private int countEarn = 0;
	private int countDebt = 0;
	private int countCF = 0;
	private int countDiv = 0;
	private int countRev = 0;
	private int countAssets = 0;
	private int countEquity = 0;
	private int countGw = 0;
	private int countCapex = 0;
	private int countBb = 0;
	private int countDvp = 0;

	public WebReaderFilter() {
	}

	public WebReaderFilter(boolean test) {
		this.test = test;
		this.detailsTesting = new HashMap<String, String>();
	}

	public boolean checkDate(Stock stock) throws IOException {
		URL url = new URL("https://financialmodelingprep.com/api/v3/company/profile/" + stock.getTicker());
		return false;
	}

	public HashMap<String, String> getStockDetails(String ticker) throws IOException {

		URL url = new URL("https://financialmodelingprep.com/api/v3/company/profile/" + ticker);
		URL urlQuote = new URL("https://financialmodelingprep.com/api/v3/quote/" + ticker);
		URL urlIncome = new URL("https://financialmodelingprep.com/api/v3/financials/income-statement/" + ticker);
		URL urlBalance = new URL(
				"https://financialmodelingprep.com/api/v3/financials/balance-sheet-statement/" + ticker);
		URL urlCash = new URL("https://financialmodelingprep.com/api/v3/financials/cash-flow-statement/" + ticker);
		URL urlEV = new URL("https://financialmodelingprep.com/api/v3/enterprise-value/" + ticker);

		HashMap<String, String> details = new HashMap<>();

		details.putIfAbsent("ticker", ticker);

		details = addToDetails(url, details);
		details = addToDetails(urlQuote, details);
		details = addToDetails(urlIncome, details);
		details = addToDetails(urlBalance, details);
		details = addToDetails(urlCash, details);
		details = addToDetails(urlEV, details);

		resetCounts();

		if (detailsTesting != null) {
			detailsTesting = details;
		}

		return details;
	}

	/**
	 * 
	 */
	private void resetCounts() {
		countDebt = 0;
		countCF = 0;
		countDiv = 0;
		countEarn = 0;
		countRev = 0;
		countAssets = 0;
		countEquity = 0;
		countGw = 0;
		countCapex = 0;
		countBb = 0;
		countDvp = 0;
	}

	private HashMap<String, String> addToDetails(URL url, HashMap<String, String> details)
			throws UnsupportedEncodingException, IOException {
//		filters the information from the API before adding to the HashMap
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
			for (String line; (line = reader.readLine()) != null;) {
				if (!line.contains("Consolidated") && line.contains("date") || line.contains("EBITDA\"")
						|| line.contains("price") || line.contains("companyName") || line.contains("industry")
						|| line.contains("sector") || line.contains("description") || line.contains("marketCap")
						|| line.contains("yearHigh") || line.contains("yearLow") || line.contains("Number of Shares")
						|| line.contains("Cash and cash equivalents")
//						everything below here has years worth of data on the api requiring handling.  Dividend payments and dividends per share are covered by the last one
						|| line.contains("Goodwill and Intangible Assets") || line.contains("Total assets")
						|| line.contains("Long-term debt") || line.contains("Total shareholders equity")
						|| line.contains("Operating Cash Flow") || line.contains("Capital Expenditure")
						|| line.contains("Enterprise Value") || line.contains("Net Income\"")
						|| line.contains("\"Revenue") || line.contains("Dividend payments")
						|| line.contains("Issuance (b") || line.contains("Dividend p")

				) {
					line = line.replace("\"", "").replace(",", "");
					String[] keyValue = line.split(":");
					keyValue[0] = keyValue[0].trim();
					keyValue[1] = keyValue[1].trim();
					keyValue = formatAddedNumbers(keyValue);
					keyValue = priorYearsValues(keyValue, details);
					if (!keyValue[0].equals("")) {
						details.putIfAbsent(keyValue[0], keyValue[1]);
					}
				}
			}
		}
		return details;
	}

	/*
	 * Removes scientific notation and any decimal points beyond 0.00 in floating
	 * point numbers.
	 * 
	 * The else if in this method may cause format problems down the line but for
	 * now it works: it converts any numbers > 10e6 from double format to long
	 * formatted string by cutting off the last two digits which SHOULD be ".0"
	 */
	private String[] formatAddedNumbers(String[] keyValue) {
		if (keyValue[0].equals("yearLow") || keyValue[0].equals("yearHigh") || keyValue[0].equals("priceAvg200")
				|| keyValue[0].equals("Dividend per Share") || keyValue[0].equals("priceAvg50")) {
			keyValue[1] = Evaluator.roundFloats(keyValue[1]);
		}
		if (keyValue[0].contains("marketCap") || keyValue[0].equals("Revenue") || keyValue[0].equals("Net Income")
				|| keyValue[0].equals("EBITDA") || keyValue[0].equals("Total assets")
				|| keyValue[0].equals("Enterprise Value") || keyValue[0].equals("Total shareholders equity")
				|| keyValue[0].equals("EBITDA") || keyValue[0].equals("Number of Shares")) {
			keyValue[1] = Evaluator.formatSciNotation(keyValue[1]);
		} else if (keyValue[1].contains("000.0")) {
			keyValue[1] = keyValue[1].substring(0, keyValue[1].length() - 2);
		}
		return keyValue;
	}

//	gathers five years worth of data on the keys below
	private String[] priorYearsValues(String[] keyValue, HashMap<String, String> details) {

		int count = -5;
		if (keyValue[0].equals("Long-term debt") && countDebt > count) {
			keyValue[0] = keyValue[0] + countDebt;
			countDebt--;
		} else if (keyValue[0].equals("Operating Cash Flow") && countCF > count) {
			keyValue[0] = keyValue[0] + countCF;
			countCF--;
		} else if (keyValue[0].equals("Revenue") && countRev > count) {
			keyValue[0] = keyValue[0] + countRev;
			countRev--;
		} else if (keyValue[0].equals("Net Income") && countEarn > count) {
			keyValue[0] = keyValue[0] + countEarn;
			countEarn--;
		} else if (keyValue[0].equals("Total assets") && countAssets > count) {
			keyValue[0] = keyValue[0] + countAssets;
			countAssets--;
		} else if (keyValue[0].equals("Goodwill and Intangible Assets") && countGw > count) {
			keyValue[0] = keyValue[0] + countGw;
			countGw--;
		} else if (keyValue[0].equals("Total shareholders equity") && countEquity > count) {
			keyValue[0] = keyValue[0] + countEquity;
			countEquity--;
		} else if (keyValue[0].equals("Capital Expenditure") && countCapex > count) {
			keyValue[0] = keyValue[0] + countCapex;
			countCapex--;
		} else if (keyValue[0].contains("Issuance (buybacks) of shares") && countBb > count) {
			keyValue[0] = keyValue[0] + countBb;
			countBb--;
		} else if (keyValue[0].contains("Dividend payments") && countDvp > count) {
			keyValue[0] = keyValue[0] + countDvp;
			countDvp--;
		} else if (keyValue[0].equals("Dividend per Share") && countDiv > -2) {
			keyValue[0] = keyValue[0] + countDiv;
			countDiv--;
		}

//		filters out the unchanged keys and revenue growth (r growth causes problems when building arrays later)
		if (keyValue[0].equals("Long-term debt") || keyValue[0].equals("Operating Cash Flow")
				|| keyValue[0].equals("Dividend per Share") || keyValue[0].equals("Revenue")
				|| keyValue[0].equals("Goodwill and Intangible Assets") || keyValue[0].equals("Total assets")
				|| keyValue[0].equals("Total shareholders equity") || keyValue[0].equals("Capital Expenditure")
				|| keyValue[0].equals("Net Income") || keyValue[0].contains("Growth")
				|| keyValue[0].equals("Issuance (buybacks) of shares") || keyValue[0].equals("Dividend payments")) {
			keyValue[0] = "";
		}
		return keyValue;
	}

	/*
	 * This method will reserve a thread to update the stock price every 60 seconds
	 * or so
	 */
	public void updatePrice() {
		System.out.println("updatePrice() called: We'll get to this");
	}

	public Map<String, String> getTestList() {
		return detailsTesting;
	}
}