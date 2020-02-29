package IncomeTracker;

import java.text.DecimalFormat;
import java.util.HashMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Paid")
@XmlAccessorType(XmlAccessType.FIELD)
class Paid {
	private String name;
	private double hoursWorked;
	private double lastWeekHours;
	double eveningHours;
	double nightHours;
	private double rate;
	private static double eveningDifferential;
	private static double nightDifferential;
	private static double weekendDifferential;
	private double taxes;
	private double lastWeekTaxes;
	private double grossPay;
	private double lastWeekGrossPay;
	private double earnings;
	private double lastWeekEarnings;
	private boolean night = false;
	private boolean week1 = true;

	//hold past payDays
	private HashMap<Integer, Double> payDaysNet;
	private HashMap<Integer, Double> payDaysGross;
	private HashMap<Integer, Double> payDaysTax;
	private HashMap<String, Double> withholdings;
	
	//constructor used in the main method
	Paid(String name, double rate) {
		this.name = name;
		this.rate = rate;
		payDaysGross = new HashMap<Integer, Double>();
		payDaysNet = new HashMap<Integer, Double>();
		payDaysTax = new HashMap<Integer, Double>();
		withholdings = new HashMap<String, Double>();
	}
	//New paid with hours; for testing
	Paid(double hours) {
		splitAndAddHours(hours);
		hoursWorked = hours;
		payDaysGross = new HashMap<Integer, Double>();
		payDaysNet = new HashMap<Integer, Double>();
		payDaysTax = new HashMap<Integer, Double>();
		withholdings = new HashMap<String, Double>();
	}
//	for testing
	Paid(double hours, double rate) {
		this.rate = rate;
		splitAndAddHours(hours);
		hoursWorked = hours;
		payDaysGross = new HashMap<Integer, Double>();
		payDaysNet = new HashMap<Integer, Double>();
		payDaysTax = new HashMap<Integer, Double>();
		withholdings = new HashMap<String, Double>();
	}
	
	//New paid with new hours.  Possibly just for testing.
	Paid(Paid paid, double hours) {
		hoursWorked = paid.getHoursWorked() +hours;
		splitAndAddHours(hours);
		rate = paid.getRate();
		grossPay = paid.getGrossPay()+hoursWorked*rate;
		payDaysGross = new HashMap<Integer, Double>();
		payDaysNet = new HashMap<Integer, Double>();
		payDaysTax = new HashMap<Integer, Double>();
		withholdings = new HashMap<String, Double>();
	}
	
	//Default paid
	Paid() {
		payDaysGross = new HashMap<Integer, Double>();
		payDaysNet = new HashMap<Integer, Double>();
		payDaysTax = new HashMap<Integer, Double>();
		withholdings = new HashMap<String, Double>();
	}
	
	double getRate() {
		return rate;
	}
	
	
	String getName() {
		return name;
	}
	
	void setName(String name) {
		this.name = name;
	}
	
	void setRate(double rate) {
		this.rate = rate;
	}

	double getGrossPay() {
		return this.grossPay;
	}
	
	void setGrossPay(double grossPay) {
		this.grossPay = grossPay;
	}
	
	double getEarnings() {
		return earnings;
	}

	void setEarnings(double earnings) {
		this.earnings = earnings;
	}

	double getHoursWorked() {
		return hoursWorked;
	}


	boolean getNight() {
		return night;
	}

	void setNight(boolean night) {
		this.night = night;
	}
	
	static void setDifferential(double eveningDifferential,
			double nightDifferential, double weekendDifferential) {
		Paid.eveningDifferential = eveningDifferential;
		Paid.nightDifferential = nightDifferential;
		Paid.weekendDifferential = weekendDifferential;
	}

	double getEveningDifferential() {
		return eveningDifferential;
	}

	double getNightDifferential() {
		return nightDifferential;
	}
	
	public static double getWeekendDifferential() {
		return weekendDifferential;
	}

	double getEveningHours() {
		return eveningHours;
	}

	double getNightHours() {
		return nightHours;
	}
	
	void addHours(double hours) {
		if (hoursWorked < 80) {
			splitAndAddHours(hours);
			hoursWorked += hours;
		} else {
			hoursWorked = 80;
			eveningHours = 28;
			nightHours = 52;
		}
	}
	
	void splitAndAddHours(double hours) {
		if (hours > 4.5) {
			this.eveningHours += 4.5;
			this.nightHours += hours - 4.5;
		} else {
			this.eveningHours += hours;
		}
	}

	HashMap<Integer, Double> getPayDaysNet() {
		return payDaysNet;
	}
	
	void printPayDaysNet() {
		DecimalFormat df = new DecimalFormat("0.00");
		payDaysNet.values().forEach(v -> System.out.println("Earnings: $"+df.format(v)));
	}
	
	public double getPayDaysNetLast() {
		return payDaysNet.get(payDaysNet.size());
	}

	public Double sumPayDaysNet() {
		double total = payDaysNet.values().stream()
				.mapToDouble(Double::doubleValue).sum();
		return total;
	}
	
	HashMap<Integer, Double> getPayDaysGross() {
		return payDaysGross;
	}
	
	void printPayDaysGross() {
		DecimalFormat df = new DecimalFormat("0.00");
		payDaysGross.values().forEach(v -> System.out.println("Gross pay: $"+df.format(v)));
	}
	
	public double getPayDaysGrossLast() {
		return payDaysGross.get(payDaysGross.size());
	}

	public Double sumPayDaysGross() {
		double total = payDaysGross.values().stream()
				.mapToDouble(Double::doubleValue).sum();
		return total;
	}
	
	HashMap<Integer, Double> getPayDaysTax() {
		return payDaysTax;
	}

	public Double sumPayDaysTax() {
		double total = payDaysTax.values().stream()
				.mapToDouble(Double::doubleValue).sum();
		return total;
	}
	
	public HashMap<String, Double> getWithholdings() {
		return withholdings;
	}
	
	public void setWithholdings(String key, double amount) {
		withholdings.put(
				key, amount
				);		
	}
	
	public Double sumWitholdings() {
		double total = payDaysTax.values().stream()
				.mapToDouble(Double::doubleValue).sum();
		return total;
	}

	void clearHours() {
		this.nightHours = 0;
		this.eveningHours = 0;
		this.hoursWorked = 0;
	}
	
	public double getLastWeekGrossPay() {
		return lastWeekGrossPay;
	}

	public void setLastWeekGrossPay(double lastWeekgrossPay) {
		this.lastWeekGrossPay = lastWeekgrossPay;
	}

	public double getLastWeekHours() {
		return lastWeekHours;
	}

	void setLastWeekHours(double hoursWorked) {
		lastWeekHours = hoursWorked;
	}
	
	public boolean getWeek1() {
		return week1;
	}

	public void setWeek1(boolean week1) {
		this.week1 = week1;
	}


	public double getTaxes() {
		return taxes;
	}

	public void setTaxes(double taxes) {
		this.taxes = taxes;
	}
	
	public double getLastWeekTaxes() {
		return lastWeekTaxes;
	}

	public void setLastWeekTaxes(double lastWeekTaxes) {
		this.lastWeekTaxes = lastWeekTaxes;
	}

	public double getLastWeekEarnings() {
		return lastWeekEarnings;
	}

	public void setLastWeekEarnings(double lastWeekEarnings) {
		this.lastWeekEarnings = lastWeekEarnings;
	}

	void clearWeek1() {
		lastWeekGrossPay = 0;
		lastWeekEarnings = 0;
		lastWeekTaxes = 0;
		lastWeekHours = 0;
		week1 = true;
	}

	void clearPay() {
		grossPay = 0;
		earnings = 0;
		taxes = 0;
	}
}
