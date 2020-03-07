package companyValueModel;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public interface Evaluator {

	public static String changeSciNotation(String number) {
		return number = BigDecimal.valueOf(Double.parseDouble(number)).toPlainString();
	}

	public static Long[] calculateMargins(Stock stock) {
		Long[] margins = new Long[3];
		int j = 0;

		for (int i = 0; i < margins.length; i++) {
			String revenue = Evaluator.changeSciNotation(stock.getProfile().get("Revenue" + j));
			margins[i] = (long) (Double.parseDouble(stock.getProfile().get("Operating Cash Flow" + j)) * 100L)
					/ Long.valueOf(revenue);
			j--;
		}
		return margins;
	}

	public static int calculateCroic(Stock stock) {
		int croic = 0;
		long equity, fcf, debt, capEx, cf;

		equity = Long.valueOf(Evaluator.changeSciNotation(stock.getProfile().get("Total shareholders equity0")));
		debt = Long.valueOf(Evaluator.changeSciNotation(stock.getProfile().get("Long-term debt0")));
		cf = Long.valueOf(Evaluator.changeSciNotation(stock.getProfile().get("Operating Cash Flow0")));
		capEx = Long.valueOf(Evaluator.changeSciNotation(stock.getProfile().get("Capital Expenditure0")));

		fcf = cf - capEx;

		croic = (int) ((fcf * 100) / (equity + debt));

		return croic;
	}

	public static double getCashToDebt(Stock stock, DecimalFormat df) {
		double ratio;
		long debt, coh;

		debt = Long.valueOf(Evaluator.changeSciNotation(stock.getProfile().get("Long-term debt0")));
		coh = Long.valueOf(Evaluator.changeSciNotation(stock.getProfile().get("Cash and cash equivalents")));

		String rat = df.format((double) coh / (double) debt);
		ratio = Double.valueOf(rat);

		return ratio;
	}

}
