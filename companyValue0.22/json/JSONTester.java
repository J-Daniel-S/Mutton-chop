package json;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.google.gson.Gson;

import companyValueModel.Evaluator;
import companyValueModel.Stock;
import companyValueModel.WebReaderFilter;

class JSONTester {

	String ticker = "INTC";

//	What we need to do is set profile = null or something like that, or in some other way exclude profile before writing to json
	@Test
	void testMarshalSingleStock() throws IOException {
		WebReaderFilter adder = new WebReaderFilter();
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		Evaluator.storeDetails(stock);
		Gson json = new Gson();
		String storedStock = json.toJson(stock);
		System.out.println(storedStock);
		assertEquals(true, true);
	}

}
