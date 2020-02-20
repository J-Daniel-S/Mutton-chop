package IncomeTracker;

public interface Withholdings {
	
	static void withhold(Paid paid) {
		paid.setEarnings(paid.getEarnings() - paid.sumWitholdings());
	}

}
