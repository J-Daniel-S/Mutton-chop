package companyValueTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.jupiter.api.Test;

import companyValueModel.Evaluator;
import companyValueModel.FMPWebReaderFilter;
import companyValueModel.Stock;
import companyValueModel.StocksList;

class EvaluatorTester {

	String ticker = "INTC";

	/*
	 * -------------------------IMPORTANT------------------------------------------
	 * All tests that have to do with the profile HashMap are dependent on 2019
	 * numbers from the financial API. These tests will no longer pass once INTC's
	 * fiscal year changes. Update the numbers if testing on the HashMap needs to be
	 * resumed
	 * 
	 * Some data (e.g. price, marketCap, and some others) change day by day and when
	 * the exchange is open, minute by minute and so must be checked against
	 * real-time numbers
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
	 * replace [marketCap] with [mktCap] for updatePriceData();
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
		FMPWebReaderFilter adder = new FMPWebReaderFilter();
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		stocks = new StocksList();
		stocks.addStock(stock);
		stock.getProfile().forEach((k, v) -> System.out.println(k + " : " + v));
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
		FMPWebReaderFilter adder = new FMPWebReaderFilter(true);
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
		FMPWebReaderFilter adder = new FMPWebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		Long[] profitMargin = Evaluator.getProfitMargins(stock);
		assertEquals(Long.valueOf(46), profitMargin[0]);
		assertEquals(Long.valueOf(41), profitMargin[1]);
		assertEquals(Long.valueOf(35), profitMargin[2]);
	}

	@Test
	void testMarginAverage() throws IOException {
		FMPWebReaderFilter adder = new FMPWebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		Long[] profitMargin = Evaluator.getProfitMargins(stock);
		int result = Evaluator.avgProfitMargin(profitMargin);
		assertEquals(38, result);
	}

//	tests the method that extracts a usable String(long) from scientific notation (Evalutator.changeSciNotation(String))
	@Test
	void testEvaluatorSciFormater() throws IOException {
		FMPWebReaderFilter adder = new FMPWebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		long rev = Long.valueOf(Evaluator.formatSciNotation((stock.getProfile().get("Revenue0"))));
		assertEquals(71965000000L, rev);
	}

//	tests the method that returns cash return on assets
	@Test
	void testEvaluatorCroic() throws IOException {
		FMPWebReaderFilter adder = new FMPWebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		double[] croic = Evaluator.getCroic(stock);
		double test = 16.47;
		double test1 = 13.92;
		try {
			assertTrue(test == croic[0]);
		} catch (AssertionError e) {
			System.err.println("Assertion failure in testEvaluatorCroic() in CompanyValueTester.  Expected " + test
					+ ", was " + croic);
		}
		boolean result = (test == croic[0]);
		assertEquals(true, result);
		try {
			assertTrue(test1 == croic[1]);
		} catch (AssertionError e) {
			System.err.println("Assertion failure in testEvaluatorCroic() in CompanyValueTester.  Expected " + test
					+ ", was " + croic);
		}
		boolean result1 = (test1 == croic[1]);
		assertEquals(true, result1);
	}

//	tests coh/debt ratio
	@Test
	void testCOHVDebt() throws IOException {
		FMPWebReaderFilter adder = new FMPWebReaderFilter(true);
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
		FMPWebReaderFilter adder = new FMPWebReaderFilter(true);
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
		FMPWebReaderFilter adder = new FMPWebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		long[] fcf = Evaluator.getFreeCashFlow(stock);
		assertEquals(16932000000L, fcf[0]);
		assertEquals(12183000000L, fcf[3]);
		assertEquals(11571000000L, fcf[4]);
	}

	@Test
	void testFCFChange() throws IOException {
		FMPWebReaderFilter adder = new FMPWebReaderFilter(true);
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
		FMPWebReaderFilter adder = new FMPWebReaderFilter(true);
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
		FMPWebReaderFilter adder = new FMPWebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		double test = 3.0;
		try {
			assertTrue(test == Double.valueOf(Evaluator.getEquityDebtRatio(stock)));
		} catch (AssertionError e) {
			System.err.println("Assertion failure in testCashYield() in CompanyValueTester.  Expected " + test
					+ ", was " + Evaluator.getEquityDebtRatio(stock));
		}
		boolean result = (test == Double.valueOf(Evaluator.getEquityDebtRatio(stock)));
		assertEquals(true, result);
	}

	@Test
	void testChangeInDebt() throws IOException {
		FMPWebReaderFilter adder = new FMPWebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		long change = Evaluator.getChangeInDebt(stock);
		assertEquals(210000000, change);
	}

	@Test
	void testCapitalEfficiency() throws IOException {
		FMPWebReaderFilter adder = new FMPWebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		Double capEff = Evaluator.getCapitalEfficiency(stock);
		assertEquals(Double.valueOf(18), capEff);

	}

	@Test
	void testYOYChangeForKey() throws IOException {
		FMPWebReaderFilter adder = new FMPWebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		long[] change = Evaluator.getYOYChangeForKey(stock, "Net Income");
		long[] changeR = Evaluator.getYOYChangeForKey(stock, "Revenue");
		assertEquals(-5000000, change[0]);
		assertEquals(4032000000L, changeR[3]);
	}

	@Test
	void testReturnOn() throws IOException {
		FMPWebReaderFilter adder = new FMPWebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		int[] return1 = Evaluator.getReturnOn("assets", stock);
		int[] return2 = Evaluator.getReturnOn("capital", stock);
		assertEquals(21, return1[0]);
		assertEquals(11, return2[3]);
	}

	@Test
	void testGetSharesOutstanding() throws IOException {
		FMPWebReaderFilter adder = new FMPWebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		String sOut = Evaluator.getSharesOutstanding(stock);
		assertEquals("4417000000", sOut);
	}

	@Test
	void testGetTotalDCF10Percent() throws IOException {
		FMPWebReaderFilter adder = new FMPWebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		long dcf = Evaluator.getTotalDcf(stock, "", "10");
		assertEquals(224926412628L, dcf);
	}

//	make sure percentage (the String required by getTotalDCF) is always in two digits e.g. 06 for 6%
	@Test
	void testGetTotalDCF6Percent() throws IOException {
		FMPWebReaderFilter adder = new FMPWebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		long dcf = Evaluator.getTotalDcf(stock, "", "06");
		assertEquals(307062947158L, dcf);
	}

	@Test
	void testGetTotalDCF9Percent() throws IOException {
		FMPWebReaderFilter adder = new FMPWebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		long dcf = Evaluator.getTotalDcf(stock, "", "09");
		assertEquals(241982629422L, dcf);
	}

	@Test
	void testGetFCFAverage() throws IOException {
		FMPWebReaderFilter adder = new FMPWebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		long avgFCF = Evaluator.getFCFAvg(stock);
		assertEquals(14611750000L, avgFCF);
	}

	@Test
	void testGetCurrentEquity() throws IOException {
		FMPWebReaderFilter adder = new FMPWebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		long equity = Evaluator.getCurrentEquity(stock);
		assertEquals(77504000000L, equity);
	}

//	@Test
//	void testcalculateDCFMultipliersWith10Percent() {
//		BigDecimal divisor = BigDecimal.valueOf(1.10);
//		BigDecimal[] dcfMultipliers = Evaluator.calculateDCFMultipliers(divisor);
//		assertEquals(BigDecimal.valueOf(0.90909), dcfMultipliers[0]);
//		assertEquals(BigDecimal.valueOf(0.35049), dcfMultipliers[10]);
//		assertEquals(BigDecimal.valueOf(0.16351), dcfMultipliers[18]);
//	}
//
//	@Test
//	void testcalculateDCFMultipliersWith7Percent() {
//		BigDecimal divisor = BigDecimal.valueOf(1.07);
//		BigDecimal[] dcfMultipliers = Evaluator.calculateDCFMultipliers(divisor);
//		assertEquals(BigDecimal.valueOf(0.93458), dcfMultipliers[0]);
//		assertEquals(BigDecimal.valueOf(0.47509), dcfMultipliers[10]);
//		assertEquals(BigDecimal.valueOf(0.27651), dcfMultipliers[18]);
//	}
//
//	@Test
//	void testcalculateDCFMultipliersWith13Percent() {
//		BigDecimal divisor = BigDecimal.valueOf(1.13);
//		BigDecimal[] dcfMultipliers = Evaluator.calculateDCFMultipliers(divisor);
//		assertEquals(BigDecimal.valueOf(0.88496), dcfMultipliers[0]);
//		assertEquals(BigDecimal.valueOf(0.26070).setScale(5), dcfMultipliers[10]);
//		assertEquals(BigDecimal.valueOf(0.09806), dcfMultipliers[18]);
//	}

	@Test
	void testGetFCFPlusEquity() throws IOException {
		FMPWebReaderFilter adder = new FMPWebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		long fcfPlusEquity = Evaluator.getTotalFutureValue(stock);
		assertEquals(302430412628L, fcfPlusEquity);
	}

	@Test
	void testFairValuePerShare() throws IOException {
		FMPWebReaderFilter adder = new FMPWebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		BigDecimal test = BigDecimal.valueOf(68.47).setScale(2);
		BigDecimal value = Evaluator.getFairValue(stock).setScale(2, RoundingMode.HALF_UP);
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
		FMPWebReaderFilter adder = new FMPWebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		BigDecimal test = BigDecimal.valueOf(34.23).setScale(2);
		BigDecimal value = Evaluator.getSafeValue(stock).setScale(2, RoundingMode.HALF_UP);
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
		FMPWebReaderFilter adder = new FMPWebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		stock.setLastDate(stock.getProfile().get("date"));
		boolean dateSame = Evaluator.checkDate(stock);
		assertTrue(dateSame);
	}

	@Test
	void testUpdatePriceData() throws IOException {
		FMPWebReaderFilter adder = new FMPWebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		stock.setCurrentPrice("14.56");
		Evaluator.updatePriceData(stock);
		assertEquals("54.43", stock.getCurrentPrice());
	}

	@Test
	void testUpdateMCap() throws IOException {
		FMPWebReaderFilter adder = new FMPWebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		stock.setMCap("4.56");
		Evaluator.updatePriceData(stock);
		assertEquals("232797110000", stock.getMCap());
	}

	@Test
	void testUpdateCashYield() throws IOException {
		FMPWebReaderFilter adder = new FMPWebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		stock.setCashYield(4.56);
		Evaluator.getFreeCashFlow(stock);
		Evaluator.updatePriceData(stock);
		assertEquals("6.3", stock.getCashYield());
	}

	@Test
	void testFutureValue() throws IOException {
		FMPWebReaderFilter adder = new FMPWebReaderFilter(true);
		Stock stock = new Stock(ticker);
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		stock.setFutureValue(297463780412L);
		Evaluator.updatePriceData(stock);
		assertEquals(Long.valueOf(297463780412L), stock.getFutureValue());
	}

//	@Test
//	void testAddMultipleStocksWriteFile() throws IOException {
//		if (Files.notExists(JSONConverter.getPath())) {
//			FMPWebReaderFilter adder = new FMPWebReaderFilter(true);
//			StocksList stocks = new StocksList();
//			Stock intc = new Stock("intc");
//			intc.setProfile(adder.getStockDetails(intc.getTicker()));
//			Evaluator.storeDetails(intc);
//			stocks.addStock(intc);

//			Stock hcc = new Stock("HCC");
//			hcc.setProfile(adder.getStockDetails(hcc.getTicker()));
////		hcc.getProfile().forEach((k, v) -> System.out.println(k + " :" + v));
//			Evaluator.storeDetails(hcc);
//			stocks.addStock(hcc);

//			Stock msft = new Stock("msft");
//			msft.setProfile(adder.getStockDetails(msft.getTicker()));
//			Evaluator.storeDetails(msft);
//			stocks.addStock(msft);
//
//			Stock vlo = new Stock("vlo");
//			vlo.setProfile(adder.getStockDetails(vlo.getTicker()));
//			Evaluator.storeDetails(vlo);
//			stocks.addStock(vlo);
//
//			Stock flmn = new Stock("flmn");
//			flmn.setProfile(adder.getStockDetails(flmn.getTicker()));
////		flmn.getProfile().forEach((k, v) -> System.out.println(k + " :" + v));
//			Evaluator.storeDetails(flmn);
//			stocks.addStock(flmn);
//
//			Stock msb = new Stock("msb");
//			msb.setProfile(adder.getStockDetails(msb.getTicker()));
////		msb.getProfile().forEach((k, v) -> System.out.println(k + " :" + v));
//			Evaluator.storeDetails(msb);
//			stocks.addStock(msb);
//
//			Stock two = new Stock("two");
//			two.setProfile(adder.getStockDetails(two.getTicker()));
////		two.getProfile().forEach((k, v) -> System.out.println(k + " :" + v));
//			Evaluator.storeDetails(two);
//			stocks.addStock(two);
//
//			Stock psec = new Stock("psec");
//			psec.setProfile(adder.getStockDetails(psec.getTicker()));
////		psec.getProfile().forEach((k, v) -> System.out.println(k + " :" + v));
//			Evaluator.storeDetails(psec);
//			stocks.addStock(psec);
//
////		returns weird values data due to (?) exchange rates
//			Stock sand = new Stock("sand");
//			sand.setProfile(adder.getStockDetails(sand.getTicker()));
////		sand.getProfile().forEach((k, v) -> System.out.println(k + " :" + v));
//			Evaluator.storeDetails(sand);
//			stocks.addStock(sand);
//
//			Stock nvda = new Stock("nvda");
//			nvda.setProfile(adder.getStockDetails(nvda.getTicker()));
////		nvda.getProfile().forEach((k, v) -> System.out.println(k + " :" + v));
//			Evaluator.storeDetails(nvda);
//			stocks.addStock(nvda);
//
//			Stock form = new Stock("form");
//			form.setProfile(adder.getStockDetails(form.getTicker()));
////		form.getProfile().forEach((k, v) -> System.out.println(k + " :" + v));
//			Evaluator.storeDetails(form);
//			stocks.addStock(form);

//			JSONConverter.convertToJSON(stocks);
//			JSONConverter.convertToJSON(stocks, JSONConverter.getPath());
//			assertTrue(Files.exists(JSONConverter.getPath()));
//		}
//	}

//	Still working on this
//	@Test
//	void testLoadExistingFile() throws IOException {
//		StocksList stocks = JSONConverter.convertFromJSON(JSONConverter.getPath());
//		assertEquals("INTC", stocks.getStocks().get(0).getProfile().get("ticker"));
//	}

	/*
	 * profile Keys: [companyName], [EBITDA], [Enterprise Value], [description],
	 * [industry], [yearHigh], [price], [sector], [yearLow], [marketCap], [ticker],
	 * [Revenue Growth], [Cash and cash equivalents], [Number of Shares]
	 * 
	 * replace [marketCap] with [mktCap] for updatePriceData();
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