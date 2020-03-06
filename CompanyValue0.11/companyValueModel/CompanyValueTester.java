package companyValueModel;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigInteger;

import org.junit.jupiter.api.Test;

class CompanyValueTester {

	StocksList stocks;

	/*
	 * Keys: [companyName], [EBITDA], [Enterprise Value], [description], [industry],
	 * [yearHigh], [price], [Total shareholders equity], [Goodwill and Intangible
	 * Assets], [Capital Expenditure], [sector], [yearLow], [marketCap], [Dividend
	 * payments], [ticker], [Revenue Growth], [Cash and short-term investments],
	 * [Net Income], [Revenue]
	 * 
	 * Array keys: [Long-term debt-1], [Free Cash Flow0], [Long-term debt-2],
	 * [Long-term debt0], [Free Cash Flow-2], [Dividend per Share0], [Free Cash
	 * Flow-1], [Dividend per Share-1]
	 * 
	 * keys with numbers at the end represent (0) = this year, (-1) = year prior,
	 * etc.
	 */

	@Test
	void testTicker() throws IOException {
		WebReaderFilter adder = new WebReaderFilter();
		Stock stock = new Stock("INTC");
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		assertEquals("INTC", stock.getTicker());
	}

	@Test
	void testDetailsValues() throws IOException {
		WebReaderFilter adder = new WebReaderFilter();
		Stock stock = new Stock("INTC");
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		stocks = new StocksList();
		stocks.addStock(stock);

		assertEquals("Intel Corporation", stock.getProfile().get("companyName"));
		assertEquals("5276000000.0", stock.getProfile().get("Cash and short-term investments"));
		assertEquals("1.24659065504", stock.getProfile().get("Dividend per Share0"));
		assertEquals("14251000000.0", stock.getProfile().get("Free Cash Flow-1"));

	}

	@Test
	void testCFArray() throws IOException {
		WebReaderFilter adder = new WebReaderFilter();
		Stock stock = new Stock("INTC");
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
//		This method call throws the NPE
		stock.setArrays(stock.getProfile());
		BigInteger[] arr = stock.getCashFlowArray();
		assertEquals(BigInteger.valueOf(33145000000L), arr[0]);
	}

}
