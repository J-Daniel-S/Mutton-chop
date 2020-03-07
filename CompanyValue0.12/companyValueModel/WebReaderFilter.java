package companyValueModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;

//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;

public class WebReaderFilter {
	private String url;

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

	public WebReaderFilter() {
	}

//	free api is here: https://financialmodelingprep.com/developer/docs/

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

		details.forEach((k, v) -> System.out.println(k + " : " + v));

//		ebitda, ev, revenue, net income, total assets - sci

//		goodwill, operating cash flow, priceAvg, dividend per share, capex, cash, revenue growth, equity - to long

		countDebt = 0;
		countCF = 0;
		countDiv = 0;
		countEarn = 0;
		countRev = 0;
		countAssets = 0;
		countEquity = 0;
		countGw = 0;
		countCapex = 0;

		return details;
	}

	private HashMap<String, String> addToDetails(URL url, HashMap<String, String> details)
			throws UnsupportedEncodingException, IOException {
//		filters the information from the API before adding to the HashMap
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
			for (String line; (line = reader.readLine()) != null;) {
				if (!line.contains("Consolidated") && line.contains("date") || line.contains("Dividend per Share")
						|| line.contains("EBITDA\"") || line.contains("price") || line.contains("companyName")
						|| line.contains("industry") || line.contains("sector") || line.contains("description")
						|| line.contains("marketCap") || line.contains("yearHigh") || line.contains("yearLow")
						|| line.contains("Cash and cash equivalents")
//						everything below here has years worth of data on the api requiring handling
						|| line.contains("Goodwill and Intangible Assets") || line.contains("Total assets")
						|| line.contains("Long-term debt") || line.contains("Total shareholders equity")
						|| line.contains("Operating Cash Flow") || line.contains("Capital Expenditure")
						|| line.contains("Enterprise Value") || line.contains("Net Income\"")
						|| line.contains("\"Revenue")

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

	private String[] formatAddedNumbers(String[] keyValue) {
		DecimalFormat df = new DecimalFormat("0.00");
		if (keyValue[0].equals("yearLow") || keyValue[0].equals("yearHigh")
				|| keyValue[0].equals("Dividend per Share")) {
			keyValue[1] = df.format(Double.valueOf(keyValue[1]));
		}
		if (keyValue[0].equals("marketCap") || keyValue[0].equals("Revenue") || keyValue[0].equals("Net Income")
				|| keyValue[0].equals("EBITDA")) {
			keyValue[1] = BigDecimal.valueOf(Double.parseDouble(keyValue[1])).toString();
		}
		return keyValue;
	}

//	gathers three years worth of data on the keys below
	private String[] priorYearsValues(String[] keyValue, HashMap<String, String> details) {
		if (keyValue[0].equals("Long-term debt") && countDebt > -3) {
			keyValue[0] = keyValue[0] + countDebt;
			countDebt--;
		} else if (keyValue[0].equals("Operating Cash Flow") && countCF > -3) {
			keyValue[0] = keyValue[0] + countCF;
			countCF--;
		} else if (keyValue[0].equals("Revenue") && countRev > -3) {
			keyValue[0] = keyValue[0] + countRev;
			countRev--;
		} else if (keyValue[0].equals("Net Income") && countEarn > -3) {
			keyValue[0] = keyValue[0] + countEarn;
			countEarn--;
		} else if (keyValue[0].equals("Total assets") && countAssets > -3) {
			keyValue[0] = keyValue[0] + countAssets;
			countAssets--;
		} else if (keyValue[0].equals("Goodwill and Intangible Assets") && countGw > -3) {
			keyValue[0] = keyValue[0] + countGw;
			countGw--;
		} else if (keyValue[0].equals("Total shareholders equity") && countEquity > -3) {
			keyValue[0] = keyValue[0] + countEquity;
			countEquity--;
		} else if (keyValue[0].equals("Capital Expenditure") && countCapex > -3) {
			keyValue[0] = keyValue[0] + countCapex;
			countCapex--;
		} else if (keyValue[0].equals("Dividend per Share") && countDiv > -2) {
			keyValue[0] = keyValue[0] + countDiv;
			countDiv--;
		}

//		filters out the unchanged keys
		if (keyValue[0].equals("Long-term debt") || keyValue[0].equals("Operating Cash Flow")
				|| keyValue[0].equals("Dividend per Share") || keyValue[0].equals("Revenue")
				|| keyValue[0].equals("Goodwill and Intangible Assets") || keyValue[0].equals("Total assets")
				|| keyValue[0].equals("Total shareholders equity") || keyValue[0].equals("Capital Expenditure")
				|| keyValue[0].equals("Net Income")) {
			keyValue[0] = "";
		}
		return keyValue;
	}

	public void updatePrice() {
		System.out.println("updatePrice() called: We'll get to this");
	}
}