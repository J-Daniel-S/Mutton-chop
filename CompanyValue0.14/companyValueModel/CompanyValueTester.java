package companyValueModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.DecimalFormat;

import org.junit.jupiter.api.Test;

class CompanyValueTester {
	
	/*
	 * -------------------------IMPORTANT------------------------------------------
	 * All tests that have to do with the profile HashMap are dependent on 2019
	 * numbers from the financial API. These tests will no longer pass once INTC's
	 * fiscal year changes. Update the numbers if testing on the HashMap needs to be
	 * resumed
	 * -------------------------IMPORTANT------------------------------------------
	 */

	StocksList stocks;

	/*
	 * profile Keys: [companyName], [EBITDA], [Enterprise Value], [description],
	 * [industry], [yearHigh], [price], [sector], [yearLow], [marketCap], [ticker],
	 * [Revenue Growth], [Cash and cash equivalents]
	 * 
	 * Array keys: [Revenuei], [Long-term debti], [Dividend per Sharei], [Operating
	 * Cash Flowi], [Net Incomei], [Goodwill and Intangible Assetsi], [Total
	 * shareholders equityi], [Capital Expenditurei], [Total assetsi], ["Issuance (buybacks) of shares"i]
	 * ["Dividend payments"i]
	 * 
	 * keys(i) with numbers at the end represent (0) = this year, (-1) = year prior,
	 * etc.
	 */

	@Test
	void testTicker() throws IOException {
		WebReaderFilter adder = new WebReaderFilter();
		Stock stock = new Stock("INTC");
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		stocks = new StocksList();
		stocks.addStock(stock);
//		stock.getProfile().forEach((k, v) -> System.out.println(k + " : " + v));
		assertEquals("INTC", stock.getTicker());
	}

	/*
	 * -----------------------SEE NOTE AT THE TOP-----------------------------------
	 * 
	 * tests some of the information from the webreader to make sure that the values
	 * are properly assigned.
	 * 
	 */

//	This method also indirectly tests Evaluator.formatSciNotation() and .roundFloats()
//	The first assertion is a hard value for revenue to ensure that the data matches the new years data from the api.  Check this at the url posted in WebReaderFilter
	@Test
	void testProfileValues() throws IOException {
		WebReaderFilter adder = new WebReaderFilter(true);
		Stock stock = new Stock("INTC");
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		assertEquals("71965000000", stock.getProfile().get("Revenue0"));
		assertEquals(adder.getTestList().get("companyName"), stock.getProfile().get("companyName"));
		assertEquals(adder.getTestList().get("Cash and cash equivalents"),
				stock.getProfile().get("Cash and cash equivalents"));
		assertEquals(adder.getTestList().get("Dividend per Share0"), stock.getProfile().get("Dividend per Share0"));
		assertEquals(adder.getTestList().get("Long-term debt-1"), stock.getProfile().get("Long-term debt-1"));
		assertEquals(adder.getTestList().get("Revenue-2"), stock.getProfile().get("Revenue-2"));
		assertEquals(adder.getTestList().get("Total assets0"), stock.getProfile().get("Total assets0"));
		assertEquals(adder.getTestList().get("Enterprise Value"), stock.getProfile().get("Enterprise Value"));
	}

//	these tests the arrays with which I will calculate the percentages and ratios.  They're not useful.
//	@Test
//	void testCashFlowArray() throws IOException {
//		WebReaderFilter adder = new WebReaderFilter();
//		Stock stock = new Stock("INTC");
//		stock.setProfile(adder.getStockDetails(stock.getTicker()));
//		stock.setArrays(stock.getProfile());
//		BigInteger[] arr = stock.getCashFlowArray();
//		assertEquals(BigInteger.valueOf(33145000000L), arr[0]);
//		assertEquals(BigInteger.valueOf(29432000000L), arr[1]);
//		assertEquals(BigInteger.valueOf(22110000000L), arr[2]);
//	}
//
//	@Test
//	void testDebtArray() throws IOException {
//		WebReaderFilter adder = new WebReaderFilter();
//		Stock stock = new Stock("INTC");
//		stock.setProfile(adder.getStockDetails(stock.getTicker()));
//		stock.setArrays(stock.getProfile());
//		BigInteger[] arr = stock.getDebtArray();
//		assertEquals(BigInteger.valueOf(25308000000L), arr[0]);
//		assertEquals(BigInteger.valueOf(25098000000L), arr[1]);
//		assertEquals(BigInteger.valueOf(25037000000L), arr[2]);
//	}
//
//	@Test
//	void testdivPerShareArray() throws IOException {
//		WebReaderFilter adder = new WebReaderFilter();
//		Stock stock = new Stock("INTC");
//		stock.setProfile(adder.getStockDetails(stock.getTicker()));
//		stock.setArrays(stock.getProfile());
//		BigDecimal[] arr = stock.getDivPerShareArray();
//		assertEquals(BigDecimal.valueOf(1.25), arr[0]);
//		assertEquals(BigDecimal.valueOf(1.18), arr[1]);
//	}
//
//	@Test
//	void testRevenueArray() throws IOException {
//		WebReaderFilter adder = new WebReaderFilter();
//		Stock stock = new Stock("INTC");
//		stock.setProfile(adder.getStockDetails(stock.getTicker()));
//		stock.setArrays(stock.getProfile());
//		BigInteger[] arr = stock.getRevenueArray();
//		assertEquals(BigInteger.valueOf(71965000000L), arr[0]);
//		assertEquals(BigInteger.valueOf(70848000000L), arr[1]);
//		assertEquals(BigInteger.valueOf(62761000000L), arr[2]);
//	}

	/*
	 * -------------------------END OF DATE-SENSETIVE TESTS-----------------------
	 * 
	 * These next tests will test the Evaluator, the interface that handles all
	 * financial calculations Tests the method that returns the profit margins for
	 * the past three years
	 */

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

	@Test
	void testMarginAverage() throws IOException {
		WebReaderFilter adder = new WebReaderFilter();
		Stock stock = new Stock("INTC");
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		Long[] profitMargin = Evaluator.calculateMargins(stock);
		int result = Evaluator.changeInMargin(profitMargin);
		assertEquals(38, result);
	}

//	tests the method that extracts a usable String(long) from scientific notation (Evalutator.changeSciNotation(String))
	@Test
	void testEvaluatorSciFormater() throws IOException {
		WebReaderFilter adder = new WebReaderFilter();
		Stock stock = new Stock("INTC");
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		long rev = Long.valueOf(Evaluator.formatSciNotation((stock.getProfile().get("Revenue0"))));
		assertEquals(71965000000L, rev);
	}

//	tests the method that returns cash return on assets
	@Test
	void testEvaluatorCroic() throws IOException {
		WebReaderFilter adder = new WebReaderFilter();
		Stock stock = new Stock("INTC");
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		double croic = Evaluator.roundFloats(Evaluator.calculateCroic(stock));
		double test = 16.47;
		try {
			assertTrue(test == croic);
		} catch (AssertionError e) {
			System.err.println(
					"Assertion failure in testCashYield() in CompanyValueTester.  Expected " + test + ", was " + croic);
		}
		boolean result = (test == croic);
		assertEquals(true, result);
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
			System.err
					.println("Assertion failure in testCOHVDebt() in CompanyValueTester.  Expected 0.17, was " + ratio);
		}
		boolean result = (0.17 == ratio);
		assertEquals(true, result);

	}

	@Test
	void testValuesArray() throws IOException {
		WebReaderFilter adder = new WebReaderFilter();
		Stock stock = new Stock("INTC");
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		long[] arr = Evaluator.valuesArray(stock, "Revenue");
//		Arrays.stream(arr).forEach(a -> System.out.println(a));
		assertEquals(71965000000L, arr[0]);
		assertEquals(70848000000L, arr[1]);
		assertEquals(62761000000L, arr[2]);
	}

	@Test
	void testGetFCF() throws IOException {
		WebReaderFilter adder = new WebReaderFilter();
		Stock stock = new Stock("INTC");
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		long[] fcf = Evaluator.getFreeCashFlow(stock);
		assertEquals(16932000000L, fcf[0]);
		assertEquals(12183000000L, fcf[3]);
		assertEquals(11691000000L, fcf[4]);
	}

	@Test
	void testFCFChange() throws IOException {
		WebReaderFilter adder = new WebReaderFilter();
		Stock stock = new Stock("INTC");
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		double[] fcf = Evaluator.getFCFChange(stock);
		try {
			assertTrue(18.81 == fcf[0]);
		} catch (AssertionError e) {
			System.err.println(
					"Assertion failure in testFCFChange() in CompanyValueTester.  Expected 18.81, was " + fcf[0]);
		}
		try {
			assertTrue(11.44 == fcf[1]);
		} catch (AssertionError e) {
			System.err.println(
					"Assertion failure in testCOHVDebt() in CompanyValueTester.  Expected 11.44, was " + fcf[1]);
		}
		boolean result = (18.81 == fcf[0]);
		boolean result2 = (11.44 == fcf[1]);
		assertEquals(true, result);
		assertEquals(true, result2);

	}

	@Test
	void testCashYield() throws IOException {
		WebReaderFilter adder = new WebReaderFilter();
		Stock stock = new Stock("INTC");
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		double test = 7.10;
		try {
			assertTrue(test == Evaluator.getCashYield(stock));
		} catch (AssertionError e) {
			System.err.println("Assertion failure in testCashYield() in CompanyValueTester.  Expected " + test
					+ ", was " + Evaluator.getCashYield(stock));
		}
		boolean result = (test == Evaluator.getCashYield(stock));
		assertEquals(true, result);
	}

	@Test
	void testGetEquityDebtRatio() throws IOException {
		WebReaderFilter adder = new WebReaderFilter();
		Stock stock = new Stock("INTC");
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		double test = 3.0;
		try {
			assertTrue(test == Evaluator.getEquityDebtRatio(stock));
		} catch (AssertionError e) {
			System.err.println("Assertion failure in testCashYield() in CompanyValueTester.  Expected " + test
					+ ", was " + Evaluator.getEquityDebtRatio(stock));
		}
		boolean result = (test == Evaluator.getEquityDebtRatio(stock));
		assertEquals(true, result);
	}

	@Test
	void testChangeInDebt() throws IOException {
		WebReaderFilter adder = new WebReaderFilter();
		Stock stock = new Stock("INTC");
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		long change = Evaluator.getChangeInDebt(stock);
		assertEquals(210000000, change);
	}
	
	@Test
	void testCapitalEfficiency() throws IOException {
		WebReaderFilter adder = new WebReaderFilter();
		Stock stock = new Stock("INTC");
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		double[] capEff = Evaluator.getCapitalEfficiency(stock);
		double test = 0.18, cap = capEff[0];
		try {
			assertTrue(test == cap);
		} catch (AssertionError e) {
			System.err.println("Assertion failure in testCapitalEfficiency() in CompanyValueTester.  Expected " + test
					+ ", was " + cap);
		}
		boolean result = (test == cap);
		assertEquals(true, result);
	}
}