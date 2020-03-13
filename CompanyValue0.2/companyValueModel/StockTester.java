package companyValueModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.jupiter.api.Test;

class StockTester {
	String ticker = "INTC";

	@Test
	void testProfileDateFormatConversion() throws IOException {
		WebReaderFilter adder = new WebReaderFilter();
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		stock.setLastDate(stock.getProfile().get("date"));
		assertEquals("2019-12-28", stock.getLastDate().toString());
	}

	@Test
	void testMarginOfSafety() {
		Stock stock = new Stock(ticker);
		stock.setMarginOfSafety(0.4);
		double test = 0.4;
		double mos = stock.getMarginOfSafety();
		try {
			assertTrue(test == mos);
		} catch (AssertionError e) {
			System.err.println("Assertion failure in testMarginOfSafety() in CompanyValueTester.  Expected " + test
					+ ", was " + mos);
		}
		boolean result = (test == mos);
		assertEquals(true, result);
	}

	@Test
	void testFairValue() throws IOException {
		Stock stock = new Stock(ticker);
		stock.setFairValue(BigDecimal.valueOf(10.00));
		WebReaderFilter adder = new WebReaderFilter();
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		stock.setFairValue(Evaluator.getShareValue(stock).setScale(2, RoundingMode.HALF_UP));
		assertEquals("68.47", stock.getFairValue());
	}

	@Test
	void testSafeValue() throws IOException {
		Stock stock = new Stock(ticker);
		WebReaderFilter adder = new WebReaderFilter();
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		stock.setFairValue(Evaluator.getShareValue(stock).setScale(2, RoundingMode.HALF_UP));
		stock.setMarginOfSafety(.50);
		stock.setSafeValue();
		assertEquals("34.23", stock.getSafeValue());
	}

	@Test
	void testEvEbitda() throws IOException {
		Stock stock = new Stock(ticker);
		WebReaderFilter adder = new WebReaderFilter();
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		stock.setEnterpriseValue("1100000000000");
		Evaluator.updatePriceData(stock);
		assertEquals("8.56", stock.getEvEbitda());
	}

}
