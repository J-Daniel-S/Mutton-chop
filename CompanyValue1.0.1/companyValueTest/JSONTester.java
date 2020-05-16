package companyValueTest;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.google.gson.Gson;

import companyValueModel.Evaluator;
import companyValueModel.FMPWebReaderFilter;
import companyValueModel.Stock;

class JSONTester {

	String ticker = "INTC";

	@Test
	void testMarshalSingleStock() throws IOException {
		FMPWebReaderFilter adder = new FMPWebReaderFilter();
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		Evaluator.storeDetails(stock);
		Gson json = new Gson();
		String storedStock = json.toJson(stock);
		System.out.println(storedStock);
		assertEquals(true, false);
	}

}
