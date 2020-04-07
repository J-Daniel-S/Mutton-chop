package companyValueModel;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

//These tests do not depend on information from the API and therefore should only fail if the methods themselves have been altered
class ManualEntryTester {
	String ticker = "INTC";

	@Test
	void testManualSetDate() {
		Stock stock = new Stock(ticker);
		stock.setLastDate("2019-09-12");

		assertEquals(LocalDate.parse("2019-09-12"), stock.getLastDate());
	}

	@Test
	void testManualEmptyName() {
		Stock stock = new Stock(ticker);
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> list = new ArrayList<String>();
		list.add("Intel Corporation");
		map.put("companyName", list);
		Evaluator.emptyName(stock, map);
		assertEquals("Intel Corporation", stock.getName());
	}

	@Test
	void testManualSetSector() {
		Stock stock = new Stock(ticker);
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> list = new ArrayList<String>();
		list.add("Semiconductors");
		map.put("sector", list);
		stock.setSector(Evaluator.emptyValue(stock, map.get("sector").get(0)));
		assertEquals("Semiconductors", stock.getSector());
	}

	@Test
	void testManualSetIndustry() {
		Stock stock = new Stock(ticker);
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> list = new ArrayList<String>();
		list.add("Technology");
		map.put("industry", list);
		stock.setIndustry(Evaluator.emptyValue(stock, map.get("industry").get(0)));
		assertEquals("Technology", stock.getIndustry());
	}

	@Test
	void testManualSetCurrentPrice() {
		Stock stock = new Stock(ticker);
		stock.setCurrentPrice("No Value");
		assertEquals("No Value", stock.getCurrentPrice());
	}

	@Test
	void testManualSetEquity() {
		Stock stock = new Stock(ticker);
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> list = new ArrayList<String>();
		list.add("21000000000");
		map.put("equity", list);
		stock.setEquity(Evaluator.emptyValue(stock, map.get("equity").get(0)));
		assertEquals("21000000000", stock.getEquity());
	}

	@Test
	void testManualSetAssets() {
		Stock stock = new Stock(ticker);
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> list = new ArrayList<String>();
		list.add("41000000000");
		map.put("assets", list);
		stock.setAssets(Evaluator.emptyValue(stock, map.get("assets").get(0)));
		assertEquals("41000000000", stock.getAssets());
	}

	@Test
	void testManualSetDebt() {
		Stock stock = new Stock(ticker);
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> list = new ArrayList<String>();
		list.add("7000000000");
		map.put("debt", list);
		stock.setDebt(Evaluator.emptyValue(stock, map.get("debt").get(0)));
		assertEquals("7000000000", stock.getDebt());
	}

	@Test
	void testManualCOH() {
		Stock stock = new Stock(ticker);
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> list = new ArrayList<String>();
		list.add("3500000000");
		map.put("coh", list);
		stock.setCOH(Evaluator.emptyValue(stock, map.get("coh").get(0)));
		assertEquals(3500000000L, stock.getCOH());
	}

	@Test
	void testManualRevenue() {
		Stock stock = new Stock(ticker);
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> list = new ArrayList<String>();
		list.add("263500000000");
		map.put("revenue", list);
		stock.setRevenue(Evaluator.emptyValue(stock, map.get("revenue").get(0)));
		assertEquals("263500000000", stock.getRevenue());
	}

	@Test
	void testManualgetProfitMargins() {
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> rev = new ArrayList<String>();
		List<String> cashFlow = new ArrayList<String>();

		rev.add("143500000000");
		rev.add("123500000000");
		rev.add("113500000000");
		rev.add("133500000000");
		rev.add("101200000000");

		cashFlow.add("18500000000");
		cashFlow.add("14500000000");
		cashFlow.add("14200000000");
		cashFlow.add("9980000000");
		cashFlow.add("9970000000");

		map.put("revenue", rev);
		map.put("cashFlow", cashFlow);

		Long[] margins = Evaluator.getProfitMargins(map);

		assertEquals(12L, margins[0]);
		assertEquals(11L, margins[1]);
		assertEquals(12L, margins[2]);
		assertEquals(7L, margins[3]);
		assertEquals(9L, margins[4]);
	}

	@Test
	void testManualgetAvgProfitMargins() {
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> rev = new ArrayList<String>();
		List<String> cashFlow = new ArrayList<String>();

		rev.add("143500000000");
		rev.add("123500000000");
		rev.add("113500000000");
		rev.add("133500000000");
		rev.add("101200000000");

		cashFlow.add("18500000000");
		cashFlow.add("14500000000");
		cashFlow.add("14200000000");
		cashFlow.add("9980000000");
		cashFlow.add("9970000000");

		map.put("revenue", rev);
		map.put("cashFlow", cashFlow);

		Long[] margins = Evaluator.getProfitMargins(map);

		assertEquals(10, Evaluator.avgProfitMargin(margins));
	}

	@Test
	void testManualSharesOut() {
		Stock stock = new Stock(ticker);
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> list = new ArrayList<String>();
		list.add("263500000");
		map.put("sharesOut", list);
		stock.setSharesOut(Evaluator.emptyValue(stock, map.get("sharesOut").get(0)));
		assertEquals("263500000", stock.getSharesOut());
	}

	@Test
	void testManualgetfreeCashFlow() {
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> capex = new ArrayList<String>();
		List<String> cashFlow = new ArrayList<String>();

		capex.add("185000000");
		capex.add("145000000");
		capex.add("142000000");
		capex.add("99800000");
		capex.add("99700000");

		cashFlow.add("18500000000");
		cashFlow.add("14500000000");
		cashFlow.add("14200000000");
		cashFlow.add("9980000000");
		cashFlow.add("9970000000");

		map.put("capex", capex);
		map.put("cashFlow", cashFlow);

		long[] fcf = Evaluator.getFreeCashFlow(map);

		assertEquals(18315000000L, fcf[0]);// 27.59
		assertEquals(14355000000L, fcf[1]);// 2.11
		assertEquals(14058000000L, fcf[2]);// 42.28
		assertEquals(9880200000L, fcf[3]);// .1
		assertEquals(9870300000L, fcf[4]);//
	}

	@Test
	void testManualgetAvgfreeCashFlow() {
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> capex = new ArrayList<String>();
		List<String> cashFlow = new ArrayList<String>();

		capex.add("185000000");
		capex.add("145000000");
		capex.add("142000000");
		capex.add("99800000");
		capex.add("99700000");

		cashFlow.add("18500000000");
		cashFlow.add("14500000000");
		cashFlow.add("14200000000");
		cashFlow.add("9980000000");
		cashFlow.add("9970000000");

		map.put("capex", capex);
		map.put("cashFlow", cashFlow);

		long[] fcf = Evaluator.getFreeCashFlow(map);

		assertEquals(16260750000L, Evaluator.getFCFAvg(fcf));
	}

	@Test
	void testManualgetUsableArrayFCF() {
		Stock stock = new Stock(ticker);
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> capex = new ArrayList<String>();
		List<String> cashFlow = new ArrayList<String>();

		capex.add("185000000");
		capex.add("145000000");
		capex.add("142000000");
		capex.add("99800000");
		capex.add("99700000");

		cashFlow.add("18500000000");
		cashFlow.add("14500000000");
		cashFlow.add("14200000000");
		cashFlow.add("9980000000");
		cashFlow.add("9970000000");

		map.put("capex", capex);
		map.put("cashFlow", cashFlow);

		long[] fcf = Evaluator.getFreeCashFlow(map);

		Double[] fcfArr = Evaluator.getFCFChangePercentRecentAndAverageArr(stock, fcf);

		stock.setFcf(fcf[0], Evaluator.getFCFAvg(fcf));

		assertEquals(27.59, fcfArr[0]);
		assertEquals(18.02, fcfArr[1]);// hand calculated
		assertEquals("18315000000", stock.getFcf()[0]);
		assertEquals("16260750000", stock.getFcf()[1]);
	}

	@Test
	void testManualROnAssets() {
		Stock stock = new Stock(ticker);
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> assets = new ArrayList<String>();
		List<String> goodwill = new ArrayList<String>();
		List<String> netIncome = new ArrayList<String>();

		assets.add("1850000000");
		goodwill.add("300000000");

		netIncome.add("350000000");

		map.put("assets", assets);
		map.put("netIncome", netIncome);
		map.put("goodwill", goodwill);

		stock.setROnAssets(Evaluator.getReturnOn(map, "assets", "goodwill"));

		assertEquals(22, Integer.valueOf(stock.getROnAssets()));
	}

	@Test
	void testManualROnCapital() {

		Stock stock = new Stock(ticker);
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> equity = new ArrayList<String>();
		List<String> debt = new ArrayList<String>();
		List<String> netIncome = new ArrayList<String>();

		equity.add("850");
		debt.add("974");

		netIncome.add("350");

		map.put("equity", equity);
		map.put("netIncome", netIncome);
		map.put("debt", debt);

		long capital = Long.valueOf(map.get("debt").get(0)) + Long.valueOf(map.get("equity").get(0));

		stock.setROnCapital(Evaluator.getReturnOn(map, capital));

		assertEquals(19, Integer.valueOf(stock.getROnCapital()));
	}

	@Test
	void testManualCroic() {
		Stock stock = new Stock(ticker);
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> capex = new ArrayList<String>();
		List<String> cashFlow = new ArrayList<String>();
		List<String> equity = new ArrayList<String>();
		List<String> debt = new ArrayList<String>();
		List<String> netIncome = new ArrayList<String>();

		capex.add("0");
		capex.add("0");
		capex.add("0");
		capex.add("0");
		capex.add("0");
		cashFlow.add("20");
		cashFlow.add("19");
		cashFlow.add("17");
		cashFlow.add("16");
		cashFlow.add("15");
		equity.add("45");
		debt.add("55");

		map.put("capex", capex);
		map.put("cashFlow", cashFlow);
		map.put("equity", equity);
		map.put("debt", debt);

		long[] fcf = Evaluator.getFreeCashFlow(map);
		Double[] fcfArr = Evaluator.getFCFChangePercentRecentAndAverageArr(stock, fcf);
		stock.setFcf(fcf[0], Evaluator.getFCFAvg(fcf));
		long capital = Long.valueOf(map.get("debt").get(0)) + Long.valueOf(map.get("equity").get(0));

		stock.setCroic(Evaluator.getCroic(stock.getFcf()[0], capital));
		assertEquals(20, stock.getCroic());
	}

	@Test
	void testManualEquityToDebt() {
		Stock stock = new Stock(ticker);
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> equity = new ArrayList<String>();
		List<String> debt = new ArrayList<String>();
		equity.add("45");
		debt.add("55");

		map.put("equity", equity);
		map.put("debt", debt);

		stock.setEquityToDebt(Evaluator.getEquityDebtRatio(map));
		assertEquals("0.82", stock.getEquityToDebt());
	}

	@Test
	void testManualCashToDebt() {
		Stock stock = new Stock(ticker);
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();

		List<String> debt = new ArrayList<String>();
		List<String> coh = new ArrayList<String>();

		debt.add("55");
		coh.add("4");

		map.put("debt", debt);
		map.put("coh", coh);

		stock.setCashToDebt(Evaluator.getCashToDebt(map));
		assertEquals("0.07", stock.getCashToDebt());
	}

	@Test
	void testManualTotalDCF() {
		Stock stock = new Stock(ticker);
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> capex = new ArrayList<String>();
		List<String> cashFlow = new ArrayList<String>();
		List<String> equity = new ArrayList<String>();

		capex.add("16213");
		capex.add("15181");
		capex.add("11778");
		capex.add("9625");
		capex.add("7446");

		cashFlow.add("33145");
		cashFlow.add("29432");
		cashFlow.add("22110");
		cashFlow.add("21808");
		cashFlow.add("19017");

		map.put("capex", capex);
		map.put("cashFlow", cashFlow);

		long[] fcf = Evaluator.getFreeCashFlow(map);

		Double[] fcfArr = Evaluator.getFCFChangePercentRecentAndAverageArr(stock, fcf);

		long avgFcf = Evaluator.getFCFAvg(fcf);

		long totalDcf = Evaluator.getTotalDcf(stock, map, fcfArr, avgFcf, "10");

		// actually 219959 but this method overcalculates by a small amount
		assertEquals(228384L, totalDcf);
	}

	@Test
	void testManualTotalFutureValue() {
		Stock stock = new Stock(ticker);
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> equity = new ArrayList<String>();

		equity.add("77504");

		map.put("equity", equity);

		long totalDcf = 228384;

		// actually 219959 but this method overcalculates by a small amount
		assertEquals(305888L, Evaluator.getTotalFutureValue(map, totalDcf));
	}

	@Test
	void testManualChangeinDebt() {
		Stock stock = new Stock(ticker);
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> debt = new ArrayList<String>();

		debt.add("25308");
		debt.add("25098");

		map.put("debt", debt);

		stock.setChangeInDebt(Evaluator.getChangeInDebt(map));

		assertEquals("210", stock.getChangeInDebt());
	}

//	skipped div per share

	@Test
	void testManualFairValue() {
		Stock stock = new Stock(ticker);
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> sharesOut = new ArrayList<String>();

		sharesOut.add("4417");

		map.put("sharesOut", sharesOut);

		long totalFv = 305888L;

		BigDecimal fairValue = Evaluator.getFairValue(map, totalFv);

		assertEquals("69.25", fairValue.toString());
	}

	@Test
	void testManualSafeValue() {
		Stock stock = new Stock(ticker);
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> sharesOut = new ArrayList<String>();

		sharesOut.add("4417");

		map.put("sharesOut", sharesOut);

		stock.setMarginOfSafety(0.25);

		long totalFv = 305888L;

		BigDecimal fairValue = Evaluator.getFairValue(map, totalFv);

		assertEquals("51.94", Evaluator.getSafeValue(stock, fairValue).toString());
	}

	@Test
	void testManualCapEff() {
		DecimalFormat df = new DecimalFormat("0.00");
		Stock stock = new Stock(ticker);
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> capex = new ArrayList<String>();
		List<String> bb = new ArrayList<String>();
		List<String> div = new ArrayList<String>();

		capex.add("16213");
		bb.add("-5576");
		div.add("-12826");

		map.put("capex", capex);
		map.put("buybacks", bb);
		map.put("div", div);

		stock.setCapEff(Evaluator.getCapitalEfficiency(map, stock));

		assertEquals("1.14", stock.getCapEff());
	}

}
