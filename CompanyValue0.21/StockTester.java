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
	
	@Test
	void testShareOut() throws IOException {
		Stock stock = new Stock(ticker);
		WebReaderFilter adder = new WebReaderFilter();
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		stock.setSharesOut("0");
		Evaluator.updatePriceData(stock);
		assertEquals("4417000000", stock.getSharesOut());
	}
	
	@Test
	void testFCF() throws IOException {
		Stock stock = new Stock(ticker);
		WebReaderFilter adder = new WebReaderFilter();
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		stock.setFcf(Evaluator.getFreeCashFlow(stock)[0] ,Evaluator.getFCFAvg(stock));
		assertEquals("16932000000", stock.getFcf()[0]);
		assertEquals("14611750000", stock.getFcf()[1]);
	}
	
	@Test
	void testROnAssets() throws IOException {
		Stock stock = new Stock(ticker);
		WebReaderFilter adder = new WebReaderFilter();
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		stock.setROnAssets(Evaluator.getReturnOn("assets", stock)[0]);
		assertEquals("21", stock.getROnAssets());
	}
	
	@Test
	void testCroic() throws IOException {
		Stock stock = new Stock(ticker);
		WebReaderFilter adder = new WebReaderFilter();
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		stock.setCroic(Evaluator.calculateCroic(stock));
		assertEquals("16.47", stock.getCroic());
	}
	
	@Test
	void testROnCapital() throws IOException {
		Stock stock = new Stock(ticker);
		WebReaderFilter adder = new WebReaderFilter();
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		stock.setROnCapital(Evaluator.getReturnOn("capital", stock)[0]);
		assertEquals("20", stock.getROnCapital());
	}
	
	@Test
	void testEquityToDebt() throws IOException {
		Stock stock = new Stock(ticker);
		WebReaderFilter adder = new WebReaderFilter();
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		stock.setEquityToDebt(Evaluator.getEquityDebtRatio(stock));
		assertEquals("3.0", stock.getEquityToDebt());
	}
	
	@Test
	void testCashOnHand() throws IOException {
		Stock stock = new Stock(ticker);
		WebReaderFilter adder = new WebReaderFilter();
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		stock.setCOH(Evaluator.getCashOnHand(stock));
		assertEquals("4194000000", stock.getCOH());
	}
	
	@Test
	void testCOHToDebt() throws IOException {
		Stock stock = new Stock(ticker);
		WebReaderFilter adder = new WebReaderFilter();
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		stock.setCashToDebt(Evaluator.getCashToDebt(stock));
		assertEquals("0.17", stock.getCashToDebt());
	}
	
	@Test
	void testChangeInDebt() throws IOException {
		Stock stock = new Stock(ticker);
		WebReaderFilter adder = new WebReaderFilter();
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		stock.setChangeInDebt(Evaluator.getChangeInDebt(stock));
		assertEquals("210000000", stock.getChangeInDebt());
	}
	
//	contains percentages.  positive indicates rewarding shareholders.  Negative indicates more spent on capital expenditures
	@Test
	void testCapitalEfficiency() throws IOException {
		Stock stock = new Stock(ticker);
		WebReaderFilter adder = new WebReaderFilter();
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		stock.setCapEff(Evaluator.getCapitalEfficiency(stock)[0]);
		assertEquals("18", stock.getCapEff());
	}
	
	@Test
	void testChangeInFCF() throws IOException {
		Stock stock = new Stock(ticker);
		WebReaderFilter adder = new WebReaderFilter();
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		stock.setFcfChange(Evaluator.getFCFChangePercentRecentAndAverageArr(stock));
		assertEquals("18.81", stock.getFcfChange()[0]);
		assertEquals("11.71", stock.getFcfChange()[1]);
	}
	
	@Test
	void testProfitMargin() throws IOException {
		Stock stock = new Stock(ticker);
		WebReaderFilter adder = new WebReaderFilter();
		stock.setProfile(adder.getStockDetails(stock.getTicker()));
		stock.setProfitMargin(Evaluator.calculateMargins(stock)[0], Evaluator.changeInMargin(Evaluator.calculateMargins(stock)));
		assertEquals("46", stock.getProfitMargin()[0]);
		assertEquals("38", stock.getProfitMargin()[1]);
	}
}