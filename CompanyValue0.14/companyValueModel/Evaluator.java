package companyValueModel;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.stream.Collector;

/*
 * This interface contains static factory methods and methods used to calculate and transmute data as it is moved from object to object
 * 
 */

/*
 * profile Keys: [companyName], [EBITDA], [Enterprise Value], [description],
 * [industry], [yearHigh], [price], [sector], [yearLow], [marketCap], [ticker],
 * [Revenue Growth], [Cash and cash equivalents]
 * 
 * Array keys: [Revenuei], [Long-term debti], [Dividend per Sharei], [Operating
 * Cash Flowi], [Net Incomei], [Goodwill and Intangible Assetsi], [Total
 * shareholders equityi], [Capital Expenditurei], [Total assetsi], ["Issuance (buybacks) of shares"i]
 * ["Dividend payments"i]
 * 
 * keys(i) with numbers at the end represent (0) = this year, (-1) = year prior,
 * etc.
 */

interface Evaluator {

//	removes scientific notation
	static String formatSciNotation(String number) {
		return number = BigDecimal.valueOf(Double.parseDouble(number)).toPlainString();
	}

//	limits floats to a format usable for currency
	static String roundFloats(String number) {
		DecimalFormat df = new DecimalFormat("0.00");
		return number = df.format(Double.valueOf(number));
	}

	static double roundFloats(double number) {
		DecimalFormat df = new DecimalFormat("0.00");
		return number = Double.valueOf(df.format(Double.valueOf(number)));
	}

//	this method converts a small float (e.g. revenue growth) to a format usable for calculations: divides by 100 and limits to 0.0000
	static double usePercent(String number) {
		DecimalFormat df = new DecimalFormat("0.0000");
		double num = Double.valueOf(number);
		num /= 100;
		System.out.println("useable percentage: " + num);
		return num;
	}

//	returns profit margins for the last 5 years
	static Long[] calculateMargins(Stock stock) {
		Long[] margins = new Long[5];
		int j = 0;

		for (int i = 0; i < margins.length; i++) {
			String revenue = stock.getProfile().get("Revenue" + j);
			margins[i] = (long) (Double.parseDouble(stock.getProfile().get("Operating Cash Flow" + j)) * 100L)
					/ Long.valueOf(revenue);
			j--;
		}
		return margins;
	}

//	compares the current profit margin to the average over the last five years (inclusive)
	static int changeInMargin(Long[] margins) throws NumberFormatException {
		return (int) Arrays.stream(margins).mapToLong(a -> a).average().getAsDouble();
	}

	static double calculateCroic(Stock stock) {
		double croic = 0;
		long equity, fcf, debt, capEx, cf;

		equity = Long.valueOf(Evaluator.formatSciNotation(stock.getProfile().get("Total shareholders equity0")));
		debt = Long.valueOf(Evaluator.formatSciNotation(stock.getProfile().get("Long-term debt0")));
		cf = Long.valueOf(Evaluator.formatSciNotation(stock.getProfile().get("Operating Cash Flow0")));
		capEx = Long.valueOf(Evaluator.formatSciNotation(stock.getProfile().get("Capital Expenditure0")));

		fcf = cf - capEx;

		croic = ((fcf * 100.0) / (equity + debt));

		return croic;
	}

	static double getCashToDebt(Stock stock, DecimalFormat df) {
		double ratio;
		long debt, coh;

		debt = Long.valueOf(stock.getProfile().get("Long-term debt0"));
		coh = Long.valueOf(stock.getProfile().get("Cash and cash equivalents"));

		String rat = df.format((double) coh / (double) debt);
		ratio = Double.valueOf(rat);

		return ratio;
	}

	/*
	 * Array factory method for calculations this method throws if the value = 0.0
	 * which sometimes results if a number that is normally large is 0.0;
	 */
	static long[] valuesArray(Stock stock, String key) throws NumberFormatException {

		int j = 0;
		int count = (int) stock.getProfile().entrySet().stream().filter(entry -> entry.getKey().contains(key)).count();
		long[] arr = new long[count];

		for (int i = 0; i < arr.length; i++) {
			try {
				arr[i] = Long.valueOf(stock.getProfile().get(key + j));
			} catch (NullPointerException e) {
				System.out.println("Key not found in profile\n" + e.getStackTrace());
			} catch (NumberFormatException e2) {
//				this catches a missed zero parse to long during data import
				if (stock.getProfile().get(key + j).equals("0.0")) {
					arr[i] = 0;
				}
				System.err.println("Invalid input from WebFilterReader.  Cannot convert to Long");
				System.out.println(e2.getStackTrace());
			}

			j--;
		}
		return arr;
	}

	static long[] getFreeCashFlow(Stock stock) {
		long[] arrCF = Evaluator.valuesArray(stock, "Operating Cash Flow");
		long[] arrCapex = Evaluator.valuesArray(stock, "Capital Expenditure");
		long[] arr = new long[arrCF.length];

		for (int i = 0; i < arr.length; i++) {
			arr[i] = arrCF[i] - arrCapex[i];
		}
		return arr;
	}

//	returns int[] that contains the percentage change in fcf from last year and how that compares to average
	static double[] getFCFChange(Stock stock) throws NumberFormatException {
		double[] arr = new double[2];
		long[] fcf = Evaluator.getFreeCashFlow(stock);
		double avg = 0;

		for (int i = 0; i < (Arrays.stream(fcf).count() - 1); i++) {
			avg += Evaluator.getYOYChange(fcf, i);
		}

		avg = avg / (Arrays.stream(fcf).count() - 1);

		arr[0] = Evaluator.roundFloats(Evaluator.getYOYChange(fcf, 0));
		arr[1] = Evaluator.roundFloats(avg);

		return arr;
	}

//	returns percent change from the year specified by count and the year prior
	static double getYOYChange(long[] arr, int year) {
		double change = (((arr[year] - arr[year + 1]) * 100.0) / arr[year + 1]);
		return change;
	}

	static double getCashYield(Stock stock) {
		long mCap = Long.valueOf(stock.getProfile().get("marketCap"));
		long fcf = Evaluator.getCurrentFCF(stock);
		double yield = Evaluator.roundFloats(fcf * 100.0 / mCap);
		return yield;
	}

	static long getCurrentFCF(Stock stock) {
		return Arrays.stream(Evaluator.getFreeCashFlow(stock)).findFirst().getAsLong();
	}

	static double getEquityDebtRatio(Stock stock) {
		return Arrays.stream(Evaluator.valuesArray(stock, "Total shareholders equity")).findFirst().getAsLong()
				/ Arrays.stream(Evaluator.valuesArray(stock, "Long-term debt")).findFirst().getAsLong();
	}

	static long getChangeInDebt(Stock stock) {
		long change = Arrays.stream(Evaluator.valuesArray(stock, "Long-term debt")).findFirst().getAsLong()
				- Arrays.stream(Evaluator.valuesArray(stock, "Long-term debt"), 1, 2).findAny().getAsLong();
		return change;
	}
	
	
//	capex comes in positive, you want the other two in negative.  Positive bb = selling of shares
	static double[] getCapitalEfficiency(Stock stock) {
		long[] div, capex, bb;
		double[] capEff;
		
		capex = Evaluator.valuesArray(stock, "Capital Expenditure");
		div = Evaluator.valuesArray(stock, "Dividend payments");
		bb = Evaluator.valuesArray(stock, "Issuance (buybacks) of shares");
		
		capEff = calCapEff(capex, div, bb);
		
		return capEff;
	}

//	This method is a mess.  It takes capital expenditures and determines whether the company spent more on that or on rewarding shareholders
	static double[] calCapEff(long[] capex, long[] div, long[] bb) {
		long[] cap = new long[5];
		double[] temp = new double[5], capEff = new double[5];
		
		for (int i = 0; i < Arrays.stream(cap).count(); i++) {
			cap[i] = div[i] + bb[i];
		}
		
		for (int i = 0; i < Arrays.stream(capex).count(); i++) {
			temp[i] = (capex[i] < Math.abs(cap[i])) ? 
					Math.abs(cap[i]) - capex[i] : 
						Math.negateExact(capex[i] - Math.abs(cap[i]));
		}
		
		for (int i = 0; i < Arrays.stream(cap).count(); i++) {
			capEff[i] = Evaluator.roundFloats(temp[i]/capex[i]);
		}
		return capEff;
	}

	static int getRevenueGrowth() {
		return 0;
	}



	/*
	 * profile Keys: [companyName], [EBITDA], [Enterprise Value], [description],
	 * [industry], [yearHigh], [price], [sector], [yearLow], [marketCap], [ticker],
	 * [Revenue Growth], [Cash and cash equivalents]
	 * 
	 * Array keys: [Revenuei], [Long-term debti], [Dividend per Sharei], [Operating
	 * Cash Flowi], [Net Incomei], [Goodwill and Intangible Assetsi], [Total
	 * shareholders equityi], [Capital Expenditurei], [Total assetsi], ["Issuance (buybacks) of shares"i]
	 * ["Dividend payments"i]
	 * 
	 * keys(i) with numbers at the end represent (0) = this year, (-1) = year prior,
	 * etc.
	 */

}