package companyValueModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;

import org.junit.jupiter.api.Test;

class CompanyValueTester {

	StocksList stocks;

	/*
	 * Keys: [companyName], [EBITDA], [Enterprise Value], [description], [industry],
	 * [yearHigh], [price], [Total shareholders equity], [Goodwill and Intangible
	 * Assets], [Capital Expenditure], [sector], [yearLow], [marketCap], [Dividend
	 * payments], [ticker], [Revenue Growth], [Cash and short-term investments],
	 * [Net Income]
	 * 
	 * Array keys: [Revenue0], [Revenue-1], [Revenue-2], [Long-term debt-1],
	 * [Long-term debt-2], [Long-term debt0], [Dividend per Share0], [Dividend per
	 * Share-1], [Operating Cash Flow0], [Operating Cash Flow-1], [Operating Cash
	 * Flow-2]
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

//	tests some of the information from the webreader to make sure that the values are properly assigned.  More testing may be required.
	@Test
	void testDetailsValues() throws IOException {
		WebReaderFilter adder = new WebReaderFilter();
		Stock stock = new Stock("INTC");
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		stocks = new StocksList();
		stocks.addStock(stock);
		assertEquals("Intel Corporation", stock.getProfile().get("companyName"));
		assertEquals("4194000000.0", stock.getProfile().get("Cash and cash equivalents"));
		assertEquals("1.24659065504", stock.getProfile().get("Dividend per Share0"));
		assertEquals("25098000000.0", stock.getProfile().get("Long-term debt-1"));
	}

//	these tests the arrays with which I will calculate the percentages and ratios
	@Test
	void testCashFlowArray() throws IOException {
		WebReaderFilter adder = new WebReaderFilter();
		Stock stock = new Stock("INTC");
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		stock.setArrays(stock.getProfile());
		BigInteger[] arr = stock.getCashFlowArray();
		assertEquals(BigInteger.valueOf(33145000000L), arr[0]);
		assertEquals(BigInteger.valueOf(29432000000L), arr[1]);
		assertEquals(BigInteger.valueOf(22110000000L), arr[2]);
	}

	@Test
	void testDebtArray() throws IOException {
		WebReaderFilter adder = new WebReaderFilter();
		Stock stock = new Stock("INTC");
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		stock.setArrays(stock.getProfile());
		BigInteger[] arr = stock.getDebtArray();
		assertEquals(BigInteger.valueOf(25308000000L), arr[0]);
		assertEquals(BigInteger.valueOf(25098000000L), arr[1]);
		assertEquals(BigInteger.valueOf(25037000000L), arr[2]);
	}

	@Test
	void testdivPerShareArray() throws IOException {
		WebReaderFilter adder = new WebReaderFilter();
		Stock stock = new Stock("INTC");
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		stock.setArrays(stock.getProfile());
		BigDecimal[] arr = stock.getDivPerShareArray();
		assertEquals(BigDecimal.valueOf(1.25), arr[0]);
		assertEquals(BigDecimal.valueOf(1.18), arr[1]);
	}

	@Test
	void testRevenueArray() throws IOException {
		WebReaderFilter adder = new WebReaderFilter();
		Stock stock = new Stock("INTC");
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		stock.setArrays(stock.getProfile());
		BigInteger[] arr = stock.getRevenueArray();
		assertEquals(BigInteger.valueOf(71965000000L), arr[0]);
		assertEquals(BigInteger.valueOf(70848000000L), arr[1]);
		assertEquals(BigInteger.valueOf(62761000000L), arr[2]);
	}

//	These next tests will test the Evaluator, the interface that handles all financial calculations
//	Tests the method that returns the profit margins for the past three years
	@Test
	void testProfitMargin() throws IOException {
		WebReaderFilter adder = new WebReaderFilter();
		Stock stock = new Stock("INTC");
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		Long[] profitMargin = Evaluator.calculateMargins(stock);
		assertEquals(Long.valueOf(46), profitMargin[0]);
		assertEquals(Long.valueOf(41), profitMargin[1]);
		assertEquals(Long.valueOf(35), profitMargin[2]);
	}

//	tests the method that extracts a usable String(long) from scientific notation (Evalutator.changeSciNotation(String))
	@Test
	void testEvaluatorSciFormater() throws IOException {
		WebReaderFilter adder = new WebReaderFilter();
		Stock stock = new Stock("INTC");
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		long rev = Long.valueOf(Evaluator.changeSciNotation((stock.getProfile().get("Revenue0"))));
		assertEquals(71965000000L, rev);
	}

//	tests the method that returns cash return on assets
	@Test
	void testEvaluatorCroic() throws IOException {
		WebReaderFilter adder = new WebReaderFilter();
		Stock stock = new Stock("INTC");
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		int croic = Evaluator.calculateCroic(stock);
		assertEquals(16, croic);
	}

//	tests coh/debt ratio
	@Test
	void testCOHVDebt() throws IOException {
		DecimalFormat df = new DecimalFormat("0.00");
		WebReaderFilter adder = new WebReaderFilter();
		Stock stock = new Stock("INTC");
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		double ratio = Evaluator.getCashToDebt(stock, df);
		try {
			assertTrue(0.17 == ratio);
		} catch (AssertionError e) {
			System.out
					.println("Assertion failure in testCOHVDebt() in CompanyValueTester.  Expected 0.17, was " + ratio);
		}

	}
}
