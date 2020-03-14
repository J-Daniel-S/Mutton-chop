package companyValueModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.junit.jupiter.api.Test;

class EvaluatorTester {

	String ticker = "INTC";

	/*
	 * -------------------------IMPORTANT------------------------------------------
	 * All tests that have to do with the profile HashMap are dependent on 2019
	 * numbers from the financial API. These tests will no longer pass once INTC's
	 * fiscal year changes. Update the numbers if testing on the HashMap needs to be
	 * resumed
	 * 
	 * I'll have to address this at some point in the near future
	 * -------------------------IMPORTANT------------------------------------------
	 */

	StocksList stocks;

	/*
	 * profile Keys: [companyName], [EBITDA], [Enterprise Value], [description],
	 * [industry], [yearHigh], [price], [sector], [yearLow], [marketCap], [ticker],
	 * [Revenue Growth], [Cash and cash equivalents], [Number of Shares]
	 * 
	 * Array keys: [Revenuei], [Long-term debti], [Dividend per Sharei], [Operating
	 * Cash Flowi], [Net Incomei], [Goodwill and Intangible Assetsi], [Total
	 * shareholders equityi], [Capital Expenditurei], [Total assetsi],
	 * ["Issuance (buybacks) of shares"i] ["Dividend payments"i]
	 * 
	 * keys(i) with numbers at the end represent (0) = this year, (-1) = year prior,
	 * etc.
	 */

	@Test
	void testTicker() throws IOException {
		WebReaderFilter adder = new WebReaderFilter();
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		stocks = new StocksList();
		stocks.addStock(stock);
//		stock.getProfile().forEach((k, v) -> System.out.println(k + " : " + v));
		assertEquals(ticker, stock.getTicker());
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
		Stock stock = new Stock(ticker);
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

	@Test
	void testProfitMargin() throws IOException {
		WebReaderFilter adder = new WebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		Long[] profitMargin = Evaluator.calculateMargins(stock);
		assertEquals(Long.valueOf(46), profitMargin[0]);
		assertEquals(Long.valueOf(41), profitMargin[1]);
		assertEquals(Long.valueOf(35), profitMargin[2]);
	}

	@Test
	void testMarginAverage() throws IOException {
		WebReaderFilter adder = new WebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		Long[] profitMargin = Evaluator.calculateMargins(stock);
		int result = Evaluator.changeInMargin(profitMargin);
		assertEquals(38, result);
	}

//	tests the method that extracts a usable String(long) from scientific notation (Evalutator.changeSciNotation(String))
	@Test
	void testEvaluatorSciFormater() throws IOException {
		WebReaderFilter adder = new WebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		long rev = Long.valueOf(Evaluator.formatSciNotation((stock.getProfile().get("Revenue0"))));
		assertEquals(71965000000L, rev);
	}

//	tests the method that returns cash return on assets
	@Test
	void testEvaluatorCroic() throws IOException {
		WebReaderFilter adder = new WebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		double croic = Evaluator.calculateCroic(stock);
		double test = 16.47;
		try {
			assertTrue(test == croic);
		} catch (AssertionError e) {
			System.err.println(
					"Assertion failure in testEvaluatorCroic() in CompanyValueTester.  Expected " + test + ", was " + croic);
		}
		boolean result = (test == croic);
		assertEquals(true, result);
	}

//	tests coh/debt ratio
	@Test
	void testCOHVDebt() throws IOException {
		WebReaderFilter adder = new WebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		double ratio = Evaluator.getCashToDebt(stock);
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
		WebReaderFilter adder = new WebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		long[] arr = Evaluator.valuesArray(stock, "Revenue");
//		Arrays.stream(arr).forEach(a -> System.out.println(a));
		assertEquals(71965000000L, arr[0]);
		assertEquals(70848000000L, arr[1]);
		assertEquals(62761000000L, arr[2]);
	}

	@Test
	void testGetFCF() throws IOException {
		WebReaderFilter adder = new WebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		long[] fcf = Evaluator.getFreeCashFlow(stock);
		assertEquals(16932000000L, fcf[0]);
		assertEquals(12183000000L, fcf[3]);
		assertEquals(11571000000L, fcf[4]);
	}

	@Test
	void testFCFChange() throws IOException {
		WebReaderFilter adder = new WebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		Double[] fcf = Evaluator.getFCFChangePercentRecentAndAverageArr(stock);
		try {
			assertTrue(18.81 == fcf[0]);
		} catch (AssertionError e) {
			System.err.println(
					"Assertion failure in testFCFChange() in CompanyValueTester.  Expected 18.81, was " + fcf[0]);
		}
		try {
			assertTrue(11.71 == fcf[1]);
		} catch (AssertionError e) {
			System.err.println(
					"Assertion failure in testFCFChange() in CompanyValueTester.  Expected 11.44, was " + fcf[1]);
		}
		boolean result = (18.81 == fcf[0]);
		boolean result2 = (11.71 == fcf[1]);
		assertEquals(true, result);
		assertEquals(true, result2);

	}

//	The results of this test will change every time the company's stock price or number of shares outstanding changes.
//	to get the intended result, divide the most recent annual free cash flow by the current market cap
	@Test
	void testCashYield() throws IOException {
		WebReaderFilter adder = new WebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		double test = 6.28;
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
		WebReaderFilter adder = new WebReaderFilter(true);
		Stock stock = new Stock(ticker);
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
		WebReaderFilter adder = new WebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		long change = Evaluator.getChangeInDebt(stock);
		assertEquals(210000000, change);
	}

	@Test
	void testCapitalEfficiency() throws IOException {
		WebReaderFilter adder = new WebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		Integer[] capEff = Evaluator.getCapitalEfficiency(stock);
		assertEquals(Integer.valueOf(18), capEff[0]);
		assertEquals(Integer.valueOf(-21), capEff[3]);

	}

	@Test
	void testYOYChangeForKey() throws IOException {
		WebReaderFilter adder = new WebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		long[] change = Evaluator.getYOYChangeForKey(stock, "Net Income");
		long[] changeR = Evaluator.getYOYChangeForKey(stock, "Revenue");
		assertEquals(-5000000, change[0]);
		assertEquals(4032000000L, changeR[3]);
	}

	@Test
	void testReturnOn() throws IOException {
		WebReaderFilter adder = new WebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		int[] return1 = Evaluator.getReturnOn("assets", stock);
		int[] return2 = Evaluator.getReturnOn("capital", stock);
		assertEquals(21, return1[0]);
		assertEquals(11, return2[3]);
	}

	@Test
	void testGetSharesOutstanding() throws IOException {
		WebReaderFilter adder = new WebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		long sOut = Evaluator.getSharesOutstanding(stock);
		assertEquals(4417000000L, sOut);
	}

	@Test
	void testEvEbitda() throws IOException {
		WebReaderFilter adder = new WebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		double evEbitda = Evaluator.getEvEbitda(stock);
		double test = 8.56;
		try {
			assertTrue(test == evEbitda);
		} catch (AssertionError e) {
			System.err.println("Assertion failure in testEvEbitda() in CompanyValueTester.  Expected " + test + ", was "
					+ Evaluator.getCashYield(stock));
		}
		boolean result = (test == evEbitda);
		assertEquals(true, result);
	}

	@Test
	void testGetTotalDCF10Percent() throws IOException {
		WebReaderFilter adder = new WebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		long dcf = Evaluator.getTotalDCF(stock, "10");
		assertEquals(224926412628L, dcf);
	}

//	make sure percentage (the String required by getTotalDCF) is always in two digits e.g. 06 for 6%
	@Test
	void testGetTotalDCF6Percent() throws IOException {
		WebReaderFilter adder = new WebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		long dcf = Evaluator.getTotalDCF(stock, "06");
		assertEquals(307062947158L, dcf);
	}

	@Test
	void testGetTotalDCF9Percent() throws IOException {
		WebReaderFilter adder = new WebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		long dcf = Evaluator.getTotalDCF(stock, "09");
		assertEquals(241982629422L, dcf);
	}

	@Test
	void testGetFCFAverage() throws IOException {
		WebReaderFilter adder = new WebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		long avgFCF = Evaluator.getFCFAvg(stock);
		assertEquals(14611750000L, avgFCF);
	}

	@Test
	void testGetCurrentEquity() throws IOException {
		WebReaderFilter adder = new WebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		long equity = Evaluator.getCurrentEquity(stock);
		assertEquals(77504000000L, equity);
	}

	@Test
	void testcalculateDCFMultipliersWith10Percent() {
		BigDecimal divisor = BigDecimal.valueOf(1.10);
		BigDecimal[] dcfMultipliers = Evaluator.calculateDCFMultipliers(divisor);
		assertEquals(BigDecimal.valueOf(0.90909), dcfMultipliers[0]);
		assertEquals(BigDecimal.valueOf(0.35049), dcfMultipliers[10]);
		assertEquals(BigDecimal.valueOf(0.16351), dcfMultipliers[18]);
	}

	@Test
	void testcalculateDCFMultipliersWith7Percent() {
		BigDecimal divisor = BigDecimal.valueOf(1.07);
		BigDecimal[] dcfMultipliers = Evaluator.calculateDCFMultipliers(divisor);
		assertEquals(BigDecimal.valueOf(0.93458), dcfMultipliers[0]);
		assertEquals(BigDecimal.valueOf(0.47509), dcfMultipliers[10]);
		assertEquals(BigDecimal.valueOf(0.27651), dcfMultipliers[18]);
	}

	@Test
	void testcalculateDCFMultipliersWith13Percent() {
		BigDecimal divisor = BigDecimal.valueOf(1.13);
		BigDecimal[] dcfMultipliers = Evaluator.calculateDCFMultipliers(divisor);
		assertEquals(BigDecimal.valueOf(0.88496), dcfMultipliers[0]);
		assertEquals(BigDecimal.valueOf(0.26070).setScale(5), dcfMultipliers[10]);
		assertEquals(BigDecimal.valueOf(0.09806), dcfMultipliers[18]);
	}

	@Test
	void testGetFCFPlusEquity() throws IOException {
		WebReaderFilter adder = new WebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		long fcfPlusEquity = Evaluator.getTotalFutureValue(stock);
		assertEquals(302430412628L, fcfPlusEquity);
	}

	@Test
	void testFairValuePerShare() throws IOException {
		WebReaderFilter adder = new WebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		BigDecimal test = BigDecimal.valueOf(68.47).setScale(2);
		BigDecimal value = Evaluator.getShareValue(stock).setScale(2, RoundingMode.HALF_UP);
		try {
			assertTrue(test.equals(value));
		} catch (AssertionError e) {
			System.err.println("Assertion failure in testFairValuePerShare() in CompanyValueTester.  Expected " + test
					+ ", was " + value);
		}
		boolean result = (test.equals(value));
		assertEquals(true, result);
	}

	@Test
	void testPriceWithMarginOfSafety() throws IOException {
		WebReaderFilter adder = new WebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		BigDecimal test = BigDecimal.valueOf(34.23).setScale(2);
		BigDecimal value = Evaluator.getSafeValue(stock, ".50").setScale(2, RoundingMode.HALF_UP);
		try {
			assertTrue(test.equals(value));
		} catch (AssertionError e) {
			System.err.println("Assertion failure in testFairValuePerShare() in CompanyValueTester.  Expected " + test
					+ ", was " + value);
		}
		boolean result = (test.equals(value));
		assertEquals(true, result);
	}

//	Tests the method that will check the date each time the program is launched
	@Test
	void testDateCheck() throws IOException {
		WebReaderFilter adder = new WebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		stock.setLastDate(stock.getProfile().get("date"));
		boolean dateSame = Evaluator.checkDate(stock);
		assertTrue(dateSame);
	}

	@Test
	void testUpdatePriceData() throws IOException {
		WebReaderFilter adder = new WebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		stock.setCurrentPrice("14.56");
		Evaluator.updatePriceData(stock);
		assertEquals("54.43", stock.getCurrentPrice());
	}

	@Test
	void testUpdateMCap() throws IOException {
		WebReaderFilter adder = new WebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		stock.setMCap("4.56");
		Evaluator.updatePriceData(stock);
		assertEquals("232797110000", stock.getMCap());
	}

	@Test
	void testUpdateCashYield() throws IOException {
		WebReaderFilter adder = new WebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		stock.setCashYield(4.56);
		Evaluator.getFreeCashFlow(stock);
		Evaluator.updatePriceData(stock);
		assertEquals("6.3", stock.getCashYield());
	}

	@Test
	void testEV() throws IOException {
		WebReaderFilter adder = new WebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		stock.setEnterpriseValue("0");
		Evaluator.updatePriceData(stock);
		assertEquals("281213850000", stock.getEnterpriseValue());
	}

	/*
	 * profile Keys: [companyName], [EBITDA], [Enterprise Value], [description],
	 * [industry], [yearHigh], [price], [sector], [yearLow], [marketCap], [ticker],
	 * [Revenue Growth], [Cash and cash equivalents], [Number of Shares]
	 * 
	 * Array keys: [Revenuei], [Long-term debti], [Dividend per Sharei], [Operating
	 * Cash Flowi], [Net Incomei], [Goodwill and Intangible Assetsi], [Total
	 * shareholders equityi], [Capital Expenditurei], [Total assetsi],
	 * ["Issuance (buybacks) of shares"i] ["Dividend payments"i]
	 * 
	 * keys(i) with numbers at the end represent (0) = this year, (-1) = year prior,
	 * etc.
	 */
}