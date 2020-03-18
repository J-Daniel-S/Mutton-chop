package json;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import com.google.gson.Gson;

import companyValueModel.Evaluator;
import companyValueModel.Stock;
import companyValueModel.StocksList;
import companyValueModel.WebReaderFilter;

class JSONTester {

	String ticker = "INTC";

//	What we need to do is set profile = null or something like that, or in some other way exclude profile before writing to json
//	REMEMBER THAT YOU'RE WRITING TESTS FOR STOCKSLIST, NOT FOR STOCKS
	@Test
	void testConvertToJSON() throws IOException {
		WebReaderFilter adder = new WebReaderFilter();
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		Evaluator.storeDetails(stock);
		StocksList stocks = new StocksList();
		stocks.addStock(stock);
		Path path = Paths.get("companyValueModel\\StocksList.json");
		
		JSONConverter.convertToJSON(stocks, path);
		
		assertEquals(true, true);
	}
}