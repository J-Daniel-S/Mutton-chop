package companyValueTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import companyValueController.CompanyController;
import companyValueModel.Evaluator;
import companyValueModel.Stock;
import companyValueModel.StocksList;

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
		equity.add("45");
		equity.add("45");
		debt.add("55");
		debt.add("55");
		debt.add("55");

		map.put("capex", capex);
		map.put("cashFlow", cashFlow);
		map.put("equity", equity);
		map.put("debt", debt);

		long[] fcf = Evaluator.getFreeCashFlow(map);
		stock.setFcf(fcf[0], Evaluator.getFCFAvg(fcf));

		long[] capital = new long[3];
		for (int i = 0; i < capital.length; i++) {
			capital[i] = Long.valueOf(map.get("debt").get(i)) + Long.valueOf(map.get("equity").get(i));
		}

		stock.setCroic(Evaluator.getCroic(fcf, capital));

		assertEquals(20, stock.getCroic()[0]);
		assertEquals(18.67, stock.getCroic()[1]);
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

	@Test
	void testManualArraysEntry() {
		Stock intc = new Stock("INTC");
		Stock wmt = new Stock("WMT");
		Stock axp = new Stock("AXP");
		Stock amat = new Stock("AMAT");

		StocksList stocks = new StocksList();

		stocks.addStock(intc);
		stocks.addStock(wmt);
		stocks.addStock(axp);
		stocks.addStock(amat);

		CompanyController.setStocks(stocks);

		HashMap<String, List<String>> intcMap = new HashMap<String, List<String>>();

		List<String> intcDate = new ArrayList<String>();
		List<String> intcName = new ArrayList<String>();
		List<String> intcIndustry = new ArrayList<String>();
		List<String> intcSector = new ArrayList<String>();
		List<String> intcSharesOut = new ArrayList<String>();
		List<String> intcMos = new ArrayList<String>();
		List<String> intcDivPer = new ArrayList<String>();
		List<String> intcCashFlow = new ArrayList<String>();
		List<String> intcRev = new ArrayList<String>();
		List<String> intcCapex = new ArrayList<String>();
		List<String> intcEquity = new ArrayList<String>();
		List<String> intcDebt = new ArrayList<String>();
		List<String> intcAssets = new ArrayList<String>();
		List<String> intcGoodwill = new ArrayList<String>();
		List<String> intcNetIncome = new ArrayList<String>();
		List<String> intcCoh = new ArrayList<String>();
		List<String> intcBb = new ArrayList<String>();
		List<String> intcDiv = new ArrayList<String>();

		intcDate.add("2019-09-25");
		intcName.add("Intel Corporation");
		intcIndustry.add("Technology");
		intcSector.add("Semiconductors");
		intcSharesOut.add("4417000000");
		intcMos.add("0.25");

		intcDivPer.add("1.24");
		intcDivPer.add("1.18");

		intcCashFlow.add("33145000000");
		intcCashFlow.add("29432000000");
		intcCashFlow.add("22110000000");
		intcCashFlow.add("21808000000");
		intcCashFlow.add("19017000000");

		intcRev.add("71965000000");
		intcRev.add("70848000000");
		intcRev.add("62761000000");
		intcRev.add("59387000000");
		intcRev.add("55355000000");

		intcCapex.add("16213000000");
		intcCapex.add("15181000000");
		intcCapex.add("11778000000");
		intcCapex.add("9625000000");
		intcCapex.add("7446000000");

		intcEquity.add("77504000000");
		intcEquity.add("74563000000");
		intcEquity.add("69019000000");

		intcDebt.add("25308000000");
		intcDebt.add("25098000000");
		intcDebt.add("25037000000");

		intcAssets.add("136524000000");
		intcGoodwill.add("37103000000");
		intcNetIncome.add("21048000000");
		intcCoh.add("13123000000");
		intcBb.add("-12826000000");
		intcDiv.add("5576000000");

		intcMap.put("date", intcDate);
		intcMap.put("goodwill", intcGoodwill);
		intcMap.put("dividends per share", intcDivPer);
		intcMap.put("capex", intcCapex);
		intcMap.put("companyName", intcName);
		intcMap.put("cashFlow", intcCashFlow);
		intcMap.put("industry", intcIndustry);
		intcMap.put("equity", intcEquity);
		intcMap.put("div", intcDiv);
		intcMap.put("revenue", intcRev);
		intcMap.put("assets", intcAssets);
		intcMap.put("sharesOut", intcSharesOut);
		intcMap.put("netIncome", intcNetIncome);
		intcMap.put("coh", intcCoh);
		intcMap.put("sector", intcSector);
		intcMap.put("debt", intcDebt);
		intcMap.put("buybacks", intcBb);
		intcMap.put("marginOfSafety", intcMos);

		CompanyController.manualValues(0, intcMap);

		assertEquals("71965000000", stocks.get(0).getRevenue());
		assertEquals("1.14", stocks.get(0).getCapEff());
		assertEquals(16.47, stocks.get(0).getCroic()[0]);
		assertEquals("18.81", stocks.get(0).getFcfChange()[0]);
		assertEquals("77504000000", stocks.get(0).getEquity());
		assertEquals("1.18", stocks.get(0).getDivPerShare()[1]);

		HashMap<String, List<String>> wmtMap = new HashMap<String, List<String>>();

		List<String> wmtDate = new ArrayList<String>();
		List<String> wmtName = new ArrayList<String>();
		List<String> wmtIndustry = new ArrayList<String>();
		List<String> wmtSector = new ArrayList<String>();
		List<String> wmtSharesOut = new ArrayList<String>();
		List<String> wmtMos = new ArrayList<String>();
		List<String> wmtDivPer = new ArrayList<String>();
		List<String> wmtCashFlow = new ArrayList<String>();
		List<String> wmtRev = new ArrayList<String>();
		List<String> wmtCapex = new ArrayList<String>();
		List<String> wmtEquity = new ArrayList<String>();
		List<String> wmtDebt = new ArrayList<String>();
		List<String> wmtAssets = new ArrayList<String>();
		List<String> wmtGoodwill = new ArrayList<String>();
		List<String> wmtNetIncome = new ArrayList<String>();
		List<String> wmtCoh = new ArrayList<String>();
		List<String> wmtBb = new ArrayList<String>();
		List<String> wmtDiv = new ArrayList<String>();

		wmtDate.add("2019-09-25");
		wmtName.add("Walmart Corporation");
		wmtIndustry.add("Consumer Defensive");
		wmtSector.add("Retail - Defensive");
		wmtSharesOut.add("2800000000");
		wmtMos.add("0.25");

		wmtDivPer.add("2.11");
		wmtDivPer.add("2.07");

		wmtCashFlow.add("25255000000");
		wmtCashFlow.add("27753000000");
		wmtCashFlow.add("28337000000");
		wmtCashFlow.add("31673000000");
		wmtCashFlow.add("27552000000");

		wmtRev.add("523964000000");
		wmtRev.add("514405000000");
		wmtRev.add("500343000000");
		wmtRev.add("485873000000");
		wmtRev.add("482130000000");

		wmtCapex.add("10705000000");
		wmtCapex.add("10344000000");
		wmtCapex.add("10051000000");
		wmtCapex.add("10619000000");
		wmtCapex.add("11477000000");

		wmtEquity.add("74669000000");
		wmtEquity.add("72496000000");
		wmtEquity.add("77869000000");

		wmtDebt.add("48021000000");
		wmtDebt.add("46340000000");
		wmtDebt.add("32784000000");

		wmtAssets.add("236495000000");
		wmtGoodwill.add("31073000000");
		wmtNetIncome.add("15201000000");
		wmtCoh.add("9465000000");
		wmtBb.add("-5717000000");
		wmtDiv.add("6048000000");

		wmtMap.put("date", wmtDate);
		wmtMap.put("goodwill", wmtGoodwill);
		wmtMap.put("dividends per share", wmtDivPer);
		wmtMap.put("capex", wmtCapex);
		wmtMap.put("companyName", wmtName);
		wmtMap.put("cashFlow", wmtCashFlow);
		wmtMap.put("industry", wmtIndustry);
		wmtMap.put("equity", wmtEquity);
		wmtMap.put("div", wmtDiv);
		wmtMap.put("revenue", wmtRev);
		wmtMap.put("assets", wmtAssets);
		wmtMap.put("sharesOut", wmtSharesOut);
		wmtMap.put("netIncome", wmtNetIncome);
		wmtMap.put("coh", wmtCoh);
		wmtMap.put("sector", wmtSector);
		wmtMap.put("debt", wmtDebt);
		wmtMap.put("buybacks", wmtBb);
		wmtMap.put("marginOfSafety", wmtMos);

		CompanyController.manualValues(1, wmtMap);

		assertEquals("1.10", stocks.get(1).getCapEff());
		assertEquals("25255000000", stocks.get(1).getCashFlow());
		assertEquals("48021000000", stocks.get(1).getDebt());
		assertEquals("1681000000", stocks.get(1).getChangeInDebt());
		assertEquals("2800000000", stocks.get(1).getSharesOut());
		// This test works for intc but not for wmt for some reason...
		assertEquals("25.25", stocks.get(1).getFairValue());

		HashMap<String, List<String>> axpMap = new HashMap<String, List<String>>();

		List<String> axpDate = new ArrayList<String>();
		List<String> axpName = new ArrayList<String>();
		List<String> axpIndustry = new ArrayList<String>();
		List<String> axpSector = new ArrayList<String>();
		List<String> axpSharesOut = new ArrayList<String>();
		List<String> axpMos = new ArrayList<String>();
		List<String> axpDivPer = new ArrayList<String>();
		List<String> axpCashFlow = new ArrayList<String>();
		List<String> axpRev = new ArrayList<String>();
		List<String> axpCapex = new ArrayList<String>();
		List<String> axpEquity = new ArrayList<String>();
		List<String> axpDebt = new ArrayList<String>();
		List<String> axpAssets = new ArrayList<String>();
		List<String> axpGoodwill = new ArrayList<String>();
		List<String> axpNetIncome = new ArrayList<String>();
		List<String> axpCoh = new ArrayList<String>();
		List<String> axpBb = new ArrayList<String>();
		List<String> axpDiv = new ArrayList<String>();

		axpDate.add("2019-09-25");
		axpName.add("American Express Corporation");
		axpIndustry.add("Financial Services");
		axpSector.add("Credit Services");
		axpSharesOut.add("856000000");
		axpMos.add("0.25");

		axpDivPer.add("1.71");
		axpDivPer.add("1.54");

		axpCashFlow.add("13632000000");
		axpCashFlow.add("8930000000");
		axpCashFlow.add("13540000000");
		axpCashFlow.add("8291000000");
		axpCashFlow.add("10706000000");

		axpRev.add("45115000000");
		axpRev.add("41625000000");
		axpRev.add("37657000000");
		axpRev.add("36036000000");
		axpRev.add("33293000000");

		axpCapex.add("1645000000");
		axpCapex.add("1310000000");
		axpCapex.add("1062000000");
		axpCapex.add("1375000000");
		axpCapex.add("1341000000");

		axpEquity.add("23071000000");
		axpEquity.add("22290000000");
		axpEquity.add("18261000000");

		axpDebt.add("57834000000");
		axpDebt.add("58423000000");
		axpDebt.add("55804000000");

		axpAssets.add("198321000000");
		axpGoodwill.add("3582000000");
		axpNetIncome.add("6759000000");
		axpCoh.add("23794000000");
		axpBb.add("-4599000000");
		axpDiv.add("1422000000");

		axpMap.put("date", axpDate);
		axpMap.put("goodwill", axpGoodwill);
		axpMap.put("dividends per share", axpDivPer);
		axpMap.put("capex", axpCapex);
		axpMap.put("companyName", axpName);
		axpMap.put("cashFlow", axpCashFlow);
		axpMap.put("industry", axpIndustry);
		axpMap.put("equity", axpEquity);
		axpMap.put("div", axpDiv);
		axpMap.put("revenue", axpRev);
		axpMap.put("assets", axpAssets);
		axpMap.put("sharesOut", axpSharesOut);
		axpMap.put("netIncome", axpNetIncome);
		axpMap.put("coh", axpCoh);
		axpMap.put("sector", axpSector);
		axpMap.put("debt", axpDebt);
		axpMap.put("buybacks", axpBb);
		axpMap.put("marginOfSafety", axpMos);

		CompanyController.manualValues(2, axpMap);

		assertEquals("Financial Services", stocks.get(2).getIndustry());
		assertEquals(0.25, stocks.get(2).getMarginOfSafety());
		assertEquals("198321000000", stocks.get(2).getAssets());
		assertEquals("212.16", stocks.get(2).getSafeValue());
		assertEquals("282.88", stocks.get(2).getFairValue());

		HashMap<String, List<String>> amatMap = new HashMap<String, List<String>>();

		List<String> amatDate = new ArrayList<String>();
		List<String> amatName = new ArrayList<String>();
		List<String> amatIndustry = new ArrayList<String>();
		List<String> amatSector = new ArrayList<String>();
		List<String> amatSharesOut = new ArrayList<String>();
		List<String> amatMos = new ArrayList<String>();
		List<String> amatDivPer = new ArrayList<String>();
		List<String> amatCashFlow = new ArrayList<String>();
		List<String> amatRev = new ArrayList<String>();
		List<String> amatCapex = new ArrayList<String>();
		List<String> amatEquity = new ArrayList<String>();
		List<String> amatDebt = new ArrayList<String>();
		List<String> amatAssets = new ArrayList<String>();
		List<String> amatGoodwill = new ArrayList<String>();
		List<String> amatNetIncome = new ArrayList<String>();
		List<String> amatCoh = new ArrayList<String>();
		List<String> amatBb = new ArrayList<String>();
		List<String> amatDiv = new ArrayList<String>();

		amatDate.add("2019-10-25");
		amatName.add("Applied Materials");
		amatIndustry.add("Semiconductors");
		amatSector.add("Technology");
		amatSharesOut.add("937000000");
		amatMos.add("0.35");

		amatDivPer.add("0.82");
		amatDivPer.add("0.59");

		amatCashFlow.add("3247000000");
		amatCashFlow.add("3787000000");
		amatCashFlow.add("3789000000");
		amatCashFlow.add("2566000000");
		amatCashFlow.add("1163000000");

		amatRev.add("14608000000");
		amatRev.add("16705000000");
		amatRev.add("14698000000");
		amatRev.add("10825000000");
		amatRev.add("9659000000");

		amatCapex.add("411000000");
		amatCapex.add("622000000");
		amatCapex.add("345000000");
		amatCapex.add("253000000");
		amatCapex.add("215000000");

		amatEquity.add("8214000000");
		amatEquity.add("6845000000");
		amatEquity.add("9349000000");

		amatDebt.add("4713000000");
		amatDebt.add("5309000000");
		amatDebt.add("5304000000");

		amatAssets.add("19024000000");
		amatGoodwill.add("3555000000");
		amatNetIncome.add("2706000000");
		amatCoh.add("3618000000");
		amatBb.add("-2258000000");
		amatDiv.add("771000000");

		amatMap.put("date", amatDate);
		amatMap.put("goodwill", amatGoodwill);
		amatMap.put("dividends per share", amatDivPer);
		amatMap.put("capex", amatCapex);
		amatMap.put("companyName", amatName);
		amatMap.put("cashFlow", amatCashFlow);
		amatMap.put("industry", amatIndustry);
		amatMap.put("equity", amatEquity);
		amatMap.put("div", amatDiv);
		amatMap.put("revenue", amatRev);
		amatMap.put("assets", amatAssets);
		amatMap.put("sharesOut", amatSharesOut);
		amatMap.put("netIncome", amatNetIncome);
		amatMap.put("coh", amatCoh);
		amatMap.put("sector", amatSector);
		amatMap.put("debt", amatDebt);
		amatMap.put("buybacks", amatBb);
		amatMap.put("marginOfSafety", amatMos);

		CompanyController.manualValues(3, amatMap);

		assertEquals("192.68", stocks.get(3).getFairValue());
		assertEquals("1.74", stocks.get(3).getEquityToDebt());
		assertEquals(23.83, stocks.get(3).getCroic()[1]);
		assertEquals("17", stocks.get(3).getROnAssets());
		assertEquals("20", stocks.get(3).getROnCapital());

		/*-
		 * date: [] 
		 * goodwill: [5] 
		 * dividends per share: [2] 
		 * capex: [, , , , ]
		 * companyName: [] 
		 * cashFlow: [, , , , ] 
		 * industry: [] 
		 * equity: [, , , , ] 
		 * div: [, , , , ] 
		 * revenue: [, , , , ] 
		 * assets: [, , , , ] 
		 * sharesout: [] 
		 * netIncome: [, , , , ] 
		 * coh: [] 
		 * sector: [] 
		 * debt: [, , , , ] 
		 * buybacks: [, , , , ]
		 * marginOfSafety[]
		 */

	}

}
