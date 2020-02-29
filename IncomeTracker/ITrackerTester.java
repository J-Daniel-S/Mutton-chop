package IncomeTracker;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import java.text.DecimalFormat;

import org.junit.jupiter.api.Test;

class ITrackerTester {

	@Test
	void testGrossPayAndDayShift() {
		Paid jeremy = new Paid(0 , 10);
		jeremy.setNight(false);
		Income.addShift(jeremy, 1.0);
		assertEquals(10, jeremy.getGrossPay());
	}
	
	@Test
	void testRate() {
		Paid jeremy = new Paid(0, 10);
		assertEquals(jeremy.getRate(), 10);
	}
	
	@Test
	void testAdditiveHours() {
		Paid jeremy = new Paid(12);
		jeremy = new Paid(jeremy, 12);
		assertEquals(24, jeremy.getHoursWorked());
	}
	
	@Test
	void testShiftDifferential() {
		Paid jeremy = new Paid(0, 10);
		Paid.setDifferential(3.5, 4, 2);
		Income.addShift(jeremy, 4.5);
		assertEquals(60.75, jeremy.getGrossPay());
		Income.addShift(jeremy,  1);
		assertEquals(74.25, jeremy.getGrossPay());
	}
	
	@Test
	void testOvertimeShift() {
		//This test inaccurately represents shift differential due
		//to the fact that Paid.addHours() deals with single shifts, not 
		//large chunks of hours.
		Paid jeremy = new Paid(36, 22.82);
		DecimalFormat df = new DecimalFormat("0.00");
		Paid.setDifferential(3.5, 4, 2);
		Income.addShift(jeremy, 0);
		assertEquals(963.27, jeremy.getGrossPay());
		Income.addShift(jeremy, 12);
		assertEquals(1374.14, Double.valueOf(df.format(jeremy.getGrossPay())));
	}
	
	@Test
	void testIncomeWeekEnds() {
		Paid jeremy = new Paid(12, 22.82);
		Paid.setDifferential(3.5, 4, 2);
		Income.addShift(jeremy, 0);
		Income.weekEnds(jeremy);
		assertTrue(jeremy.getGrossPay() == 0 && 
				jeremy.getHoursWorked() == 0);
	}
	
	@Test
	void testHourSplit() {
		Paid jeremy = new Paid(12);
		assertEquals(4.5, jeremy.getEveningHours());
		assertEquals(7.5, jeremy.getNightHours());
		Income.addShift(jeremy, 12);
		assertEquals(9, jeremy.getEveningHours());
		assertEquals(15, jeremy.getNightHours());
	}
	
	@Test
	void testDifferentials() {
		DecimalFormat df = new DecimalFormat("0.00");
		Paid jeremy = new Paid(0, 22.82);
		Paid.setDifferential(3.5, 4, 2);
		Income.addShift(jeremy, 12);
		assertEquals(319.59, Double.valueOf(df.format(jeremy.getGrossPay())));
		
	}
	
	@Test
	void testFullWeeksPay() {
		DecimalFormat df = new DecimalFormat("0.00");
		Paid jeremy = new Paid(0, 22.82);
		Paid.setDifferential(3.5, 4, 2);
		Income.addShift(jeremy, 12);
		Income.addShift(jeremy, 12);
		Income.addShift(jeremy, 12);
		Income.addShift(jeremy, 12);
		assertEquals(1369.64, Double.valueOf(df.format(jeremy.getGrossPay())));
	}
	
	@Test
	void testSumPayDaysGross() {
		DecimalFormat df = new DecimalFormat("0.00");
		Paid jeremy = new Paid(0, 22.82);
		Paid.setDifferential(3.5, 4, 2);
		Income.addShift(jeremy, 12);
		Income.payDay(jeremy);
		Income.addShift(jeremy, 12);
		Income.payDay(jeremy);
		Income.addShift(jeremy, 12);
		Income.payDay(jeremy);
		assertEquals(958.77, Double.valueOf(df.format(jeremy.sumPayDaysGross())));
	}
	

	
	@Test
	void testSumPayDaysNet() {
		DecimalFormat df = new DecimalFormat("0.00");
		Paid jeremy = new Paid(0, 22.82);
		Paid.setDifferential(3.5, 4, 2);
		Taxes.taxRate.setTaxRate(0.15);
		Income.addShift(jeremy, 12);
		Income.payDay(jeremy);
		Income.addShift(jeremy, 12);
		Income.payDay(jeremy);
		Income.addShift(jeremy, 12);
		Income.payDay(jeremy);
		assertEquals(814.95, Double.valueOf(df.format(jeremy.sumPayDaysNet())));
	}
	
	@Test
	void testSumPayDaysTax() {
		DecimalFormat df = new DecimalFormat("0.00");
		Paid jeremy = new Paid(0, 22.82);
		Paid.setDifferential(3.5, 4, 2);
		Taxes.taxRate.setTaxRate(0.15);
		Income.addShift(jeremy, 12);
		Income.payDay(jeremy);
		Income.addShift(jeremy, 12);
		Income.payDay(jeremy);
		Income.addShift(jeremy, 12);
		Income.payDay(jeremy);
		assertEquals(143.82, Double.valueOf(df.format(jeremy.sumPayDaysTax())));
	}
	
	@Test
	void testBonus() {
		Paid jeremy = new Paid(0, 22.82);
		Paid.setDifferential(3.5, 4, 2);
		Income.addShift(jeremy, 36);
		Income.bonus(jeremy, 50);
		assertEquals(1013.27, jeremy.getGrossPay());
	}
	
	@Test
	void testName() {
		Paid jeremy = new Paid(0);
		jeremy.setName("Herman");
		assertEquals("Herman" , jeremy.getName());
	}
	
	//not properly set
	@Test
	void testEarnings() {
		Paid jeremy = new Paid(0, 22.82);
		Paid.setDifferential(3.5, 4, 2);
		Taxes.taxRate.setTaxRate(0.15);
		Income.addShift(jeremy, 36);
		Income.bonus(jeremy, 50);
		Taxes.taxRate.setTaxRate(0.15);
		assertEquals(861.2795, jeremy.getEarnings());
	}
	
	@Test
	void testTaxes() {
		Paid jeremy = new Paid(0, 22.82);
		Paid.setDifferential(3.5, 4, 2);
		Income.addShift(jeremy, 36);
		Income.bonus(jeremy, 50);
		assertEquals(151.9905, jeremy.getTaxes());
	}
	
	@Test
	void testIncomeWeekEndsPayHoursReset() {
		DecimalFormat df = new DecimalFormat("0.00");
		Paid jeremy = new Paid(0, 22.82);
		Income.addShift(jeremy, 36);
		Income.weekEnds(jeremy);
		Income.addShift(jeremy, 12);
		assertTrue(319.59 == Double.valueOf(df.format(jeremy.getGrossPay())) &&
				963.27 == Double.valueOf(df.format(jeremy.getLastWeekGrossPay())));
		assertTrue(12 == jeremy.getHoursWorked() && 
				36 == jeremy.getLastWeekHours());
	}
	
	//payDays hashmap starts at key 1
	@Test
	void testIncomePayDayGross() {
		DecimalFormat df = new DecimalFormat("0.00");
		Paid jeremy = new Paid(0, 22.82);
		Income.addShift(jeremy, 36);
		Income.weekEnds(jeremy);
		Income.addShift(jeremy, 12);
		Income.payDay(jeremy);
		assertEquals(1282.86, Double.valueOf(df.format(jeremy.getPayDaysGross().get(1))));
	}
	
	@Test
	void testWeek1bool() {
		Paid jeremy = new Paid(0, 22.82);
		Income.addShift(jeremy, 36);
		Income.weekEnds(jeremy);
		assertFalse(jeremy.getWeek1());
		Income.addShift(jeremy, 12);
		Income.payDay(jeremy);
		assertTrue(jeremy.getWeek1());
	}
	
	@Test
	void testLastWeekEarnings() {
		Paid jeremy = new Paid(0, 22.82);
		Income.addShift(jeremy, 10);
		Income.weekEnds(jeremy);
		assertEquals(226.0575 , jeremy.getLastWeekEarnings());
		assertTrue(jeremy.getEarnings() == 0 && jeremy.getGrossPay() == 0);
	}
	
	@Test
	void testLastWeekTaxes() {
		Paid jeremy = new Paid(0, 22.82);
		Taxes.taxRate.setTaxRate(0.15);
		Income.addShift(jeremy, 10);
		Income.weekEnds(jeremy);
		assertEquals(34.23 , jeremy.getLastWeekTaxes());
		assertTrue(jeremy.getEarnings() == 0 && 
				jeremy.getGrossPay() == 0 && jeremy.getTaxes() == 0);
	}
	
	@Test
	void testNetHashMap() {
		Paid jeremy = new Paid (0, 22.82);
		Taxes.taxRate.setTaxRate(0.15);
		Income.addShift(jeremy, 1);
		Income.payDay(jeremy);
		assertEquals(22.372, jeremy.getPayDaysNet().get(1));
	}
	
	@Test
	void testTaxHashMap() {
		Paid jeremy = new Paid (0, 22.82);
		Taxes.taxRate.setTaxRate(0.15);
		Income.addShift(jeremy, 1);
		Income.payDay(jeremy);
		assertEquals(3.948, jeremy.getPayDaysTax().get(1));
	}
	
	@Test
	void testWitholdingsMap() {
		Paid jeremy = new Paid (0, 22.82);
		Taxes.taxRate.setTaxRate(0.15);
		Income.addShift(jeremy, 1);
		Income.payDay(jeremy);
		jeremy.setWithholdings("withholdings" , 5.0);
		assertEquals(5, jeremy.getWithholdings().get("withholdings"));
	}
	
}
