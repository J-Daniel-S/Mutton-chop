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

	private int countFCF = 0;
	private int countDebt = 0;
	private int countCF = 0;
	private int countDiv = 0;

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

//		details.forEach((k, v) -> System.out.println(k + " : " + v));

		countFCF = 0;
		countDebt = 0;
		countCF = 0;
		countDiv = 0;

		return details;
	}

	private HashMap<String, String> addToDetails(URL url, HashMap<String, String> details)
			throws UnsupportedEncodingException, IOException {
//		filters the information from the API before adding to the HashMap
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
			for (String line; (line = reader.readLine()) != null;) {
				if (!line.contains("Consolidated") && line.contains("date") || line.contains("\"Revenue")
						|| line.contains("Net Income\"") || line.contains("Dividend per Share")
						|| line.contains("EBITDA\"") || line.contains("price") || line.contains("companyName")
						|| line.contains("industry") || line.contains("sector") || line.contains("description")
						|| line.contains("marketCap") || line.contains("yearHigh") || line.contains("yearLow")
//						everything below here has years worth of data on the api requiring handling
						|| line.contains("Cash and short-term investments")
						|| line.contains("Goodwill and Intangible Assets") || line.contains("Total assets")
						|| line.contains("Long-term debt") || line.contains("Total shareholders equity")
						|| line.contains("Operating Cash Flow") || line.contains("Capital Expenditure")
						|| line.contains("Dividend payments") || line.contains("Free Cash Flow\"")
						|| line.contains("Enterprise Value")

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
		if (keyValue[0].equals("yearLow") || keyValue[0].equals("yearHigh")) {
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
		if (keyValue[0].equals("Free Cash Flow") && countFCF > -3) {
			keyValue[0] = keyValue[0] + countFCF;
			countFCF--;
		} else if (keyValue[0].equals("Long-term debt") && countDebt > -3) {
			keyValue[0] = keyValue[0] + countDebt;
			countDebt--;
		} else if (keyValue[0].equals("Operating Cash Flow") && countCF > -3) {
			keyValue[0] = keyValue[0] + countCF;
			countCF--;
		} else if (keyValue[0].equals("Dividend per Share") && countDiv > -2) {
			keyValue[0] = keyValue[0] + countDiv;
			countDiv--;
		}
//		filters out the unchanged keys
		if (keyValue[0].equals("Free Cash Flow") || keyValue[0].equals("Long-term debt")
				|| keyValue[0].equals("Operating Cash Flow") || keyValue[0].equals("Dividend per Share")) {
			keyValue[0] = "";
		}
		return keyValue;
	}

	public void updatePrice() {
		System.out.println("updatePrice() called: We'll get to this");
	}
}