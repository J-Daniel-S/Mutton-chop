package IncomeTracker;

public interface Income {

//	increments hours, gross pay, and pay after taxes
	static void addShift(Paid paid, double hours) {
		paid.addHours(hours);
		double overtime = overtime(paid);
		incrementPay(paid, overtime);
	}
	
	static void incrementPay(Paid paid, double overtime) {
		double grossPay = 0;
		if (paid.getHoursWorked() > 40) {
			grossPay = paid.getRate() * 40;
		} else {
			grossPay = paid.getRate() * paid.getHoursWorked();
		}
		paid.setGrossPay(grossPay + overtime + differentialPay(paid));
		Taxes.tax(paid);
	}
	
	static void bonus(Paid paid, double bonus) {
		paid.setGrossPay(paid.getGrossPay()+bonus);
		Taxes.tax(paid);
	}
	
	static void weekEnds(Paid paid) {
		if (paid.getWeek1() == true) {
			changeWeeks(paid);
		} else {
			Income.payDay(paid);
		}
	}
//	do not use outside of this interface
	static void changeWeeks(Paid paid) {
		paid.setLastWeekHours(paid.getHoursWorked());
		paid.setLastWeekGrossPay(paid.getGrossPay());
		paid.setLastWeekEarnings(paid.getEarnings());
		paid.setLastWeekTaxes(paid.getTaxes());
		paid.clearPay();
		paid.setWeek1(false);
		paid.clearHours();
	}
	
	static void payDay(Paid paid) {
		Withholdings.withhold(paid);
		addToPayDays(paid);
		paid.clearHours();
		paid.clearWeek1();
		paid.clearPay();
	}

	static double differentialPay(Paid paid) {
		double diffGrossPay = paid.getNight() ? 
				(paid.getEveningDifferential())*
				paid.getEveningHours()+ 
				paid.getNightHours()*
				paid.getNightDifferential(): 0;
		return diffGrossPay;
	}
	
	//Returns pay for overtime hours (correct number)
	static double overtime(Paid paid) {
		double overtimeHours = overtimeHours(paid);
		double overtimeGrossPay = overtimeHours*paid.getRate()*1.5;
		return overtimeGrossPay;
	}
	
	
	//Returns the proper number of hours
	static double overtimeHours(Paid paid) {
		boolean overtime = paid.getHoursWorked() > 40;
		double overtimeHours = overtime ? paid.getHoursWorked() % 40 : 0;
		return overtimeHours;
	}
	
	//Adds each chunk of hours to payDays HashMap
	static void addToPayDays(Paid paid) {
		int key = paid.getPayDaysGross().size()+1;
		paid.getPayDaysGross().put(
				key,
				paid.getGrossPay()+paid.getLastWeekGrossPay()
				);
		paid.getPayDaysNet().put(
				key, paid.getGrossPay()+paid.getLastWeekGrossPay() -
				(paid.getGrossPay()+paid.getLastWeekGrossPay())*Taxes.taxRate.getTaxRate()
				);
		Taxes.storeTax(paid, key);
	}
}
