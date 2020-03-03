package miniStockModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WebReader {
	private String url;

	public WebReader() {
	}

//	free api is here: https://financialmodelingprep.com/developer/docs/

	public String getFromYahoo(String ticker) throws IOException {

		String url = "https://finance.yahoo.com/quote/" + ticker;

		Document doc = Jsoup.connect(url).timeout(5000).get();
		String name = doc.title();
//		trend2W 10W 9M - the key for now to get the price from the text until stack overflow answers your question
//		System.out.println(doc);
//		int index = doc.toString().indexOf("\"currentPrice\":{\"raw\":");
//		System.out.println("index: " + index);
//		System.out.println(doc.toString().substring(index, index + 30));

		if (!name.contains(ticker)) {
			return null;
		}

		String[] subtitle = name.split("Stock Price");
		name = subtitle[0].trim();
		System.out.println(name);

		return name;
	}

	public HashMap<String, String> getStockDetails(String ticker) throws IOException {
		URL url = new URL("https://financialmodelingprep.com/api/v3/company/profile/" + ticker);

		HashMap<String, String> details = new HashMap<>();

		details.put("ticker", ticker);

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
			for (String line; (line = reader.readLine()) != null;) {
				if (line.contains("price") || line.contains("mktCap") || line.contains("companyName")
						|| line.contains("industry") || line.contains("sector") || line.contains("description")) {
					line = line.replace("\"", "").replace(",", "");
					String[] keyValue = line.split(":");
					details.put(keyValue[0].trim(), keyValue[1].trim());
				}

			}
		}

//		details.forEach((k, v) -> System.out.println(k + " : " + v));
		return details;
	}

	public void updatePrice() {
		System.out.println("updatePrice() called: We'll get to this");
	}
}
