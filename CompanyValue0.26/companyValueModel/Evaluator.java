package companyValueModel;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;

/*
 * This interface contains static factory methods and methods used to calculate and transmute data as it is moved from object to object
 * 
 */

/*
 * profile Keys: [companyName], [EBITDA], [Enterprise Value], [description],
 * [industry], [yearHigh], [price], [sector], [yearLow], [marketCap], [ticker],
 * [Revenue Growth], [Cash and cash equivalents], [Number of Shares]
 * 
 * replace [marketCap] with [mktCap] for updatePriceData();
 * 
 * Array keys: [Revenuei], [Long-term debti], [Dividend per Sharei], [Operating
 * Cash Flowi], [Net Incomei], [Goodwill and Intangible Assetsi], [Total
 * shareholders equityi], [Capital Expenditurei], [Total assetsi], ["Issuance (buybacks) of shares"i]
 * ["Dividend payments"i]
 * 
 * keys(i) with numbers at the end represent (0) = this year, (-1) = year prior,
 * etc.
 */

public interface Evaluator {
	
//	removes scientific notation
	static String formatSciNotation(String number, String ticker) {
		try {
			return number = BigDecimal.valueOf(Double.parseDouble(number)).toPlainString();
		} catch (NumberFormatException e) {
			if (number.isEmpty()) {
				System.out.println("Empty data string in profile for " + ticker);
			}
			return "0";
		}
	}
	
	static String formatSciNotation(String number) {
		try {
			return number = BigDecimal.valueOf(Double.parseDouble(number)).toPlainString();
		} catch (NumberFormatException e) {
			if (number.isEmpty()) {
				System.out.println("Empty data string in formatScieNotation");
			}
			return "0";
		}
	}

//	limits floats to a format usable for currency
	static String roundFloats(String number) {
		DecimalFormat df = new DecimalFormat("0.00");
		return number = df.format(Double.valueOf(number));
	}
	
	static String roundFloats(String number, String ticker) {
		try {
			DecimalFormat df = new DecimalFormat("0.00");
			return number = df.format(Double.valueOf(number));
		} catch (NumberFormatException e) {
			if (number.isEmpty()) {
				System.out.println("Empty data string in profile for " + ticker);
			}
			return "0.0";
		}
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
	static Long[] getProfitMargins(Stock stock) {
		Long[] margins = new Long[5];
		int j = 0;

		for (int i = 0; i < margins.length; i++) {
			String revenue = stock.getProfile().get("Revenue" + j);
			try {
				margins[i] = (long) (Double.parseDouble(stock.getProfile().get("Operating Cash Flow" + j)) * 100L)
						/ Long.valueOf(revenue);
			} catch (NumberFormatException e) {
				System.out.println("Missing revenue data for " + stock.getProfileTicker());
				margins[i] = 0L;
				continue;
			} catch (NullPointerException e) {
				System.out.println("Fewer than 5 years of cash flow data for " + stock.getProfile().get("ticker")
						+ " in year " + i);
				margins[i] = 0L;
			}
			j--;
		}
		return margins;
	}

//	compares the current profit margin to the average over the last five years (inclusive)
	static int avgProfitMargin(Long[] margins) {
		try {
			return (int) Arrays.stream(margins).mapToLong(a -> a).average().getAsDouble();
		} catch (NullPointerException e) {
			return 0;
		}
	}

	static double setCroic(Stock stock) {
		double croic = 0;
		long equity, fcf, debt, capEx, cf;

		try {
			equity = Long.valueOf(Evaluator.formatSciNotation(stock.getProfile().get("Total shareholders equity0")));
			debt = Long.valueOf(Evaluator.formatSciNotation(stock.getProfile().get("Long-term debt0")));
			cf = Long.valueOf(Evaluator.formatSciNotation(stock.getProfile().get("Operating Cash Flow0")));
			capEx = Long.valueOf(Evaluator.formatSciNotation(stock.getProfile().get("Capital Expenditure0")));
		} catch (NumberFormatException e) {
			equity = Double.valueOf(Evaluator.formatSciNotation(stock.getProfile().get("Total shareholders equity0"))).longValue();
			debt = Double.valueOf(Evaluator.formatSciNotation(stock.getProfile().get("Long-term debt0"))).longValue();
			cf = Double.valueOf(Evaluator.formatSciNotation(stock.getProfile().get("Operating Cash Flow0"))).longValue();
			capEx = Double.valueOf(Evaluator.formatSciNotation(stock.getProfile().get("Capital Expenditure0"))).longValue();
		}


		fcf = cf - capEx;
		if (fcf == 0) {
			System.out.println("FCF data missing");
		}

		try {
			croic = ((fcf * 100.0) / (equity + debt));
		} catch (ArithmeticException e) {
			if (equity + debt == 0) {
				croic = 0;
			}
			System.out.println("equity/debt data missing for " + stock.getProfile().get("ticker"));
		}
		
		if (croic > 100.0) {
			return 1000.0;
		}

		return Evaluator.roundFloats(croic);
	}

	static double getCashToDebt(Stock stock) {
		double ratio;
		long debt, coh;
		DecimalFormat df = new DecimalFormat("0.00");

		debt = Double.valueOf(stock.getProfile().get("Long-term debt0")).longValue();
		coh = getCashOnHand(stock);

		if (debt != 0) {
			String rat = df.format((double) coh / (double) debt);
			ratio = Double.valueOf(rat);

			return ratio;
		}
		
		return -1.00;
	}

	static long getCashOnHand(Stock stock) {
		return Double.valueOf(stock.getProfile().get("Cash and cash equivalents")).longValue();
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
				arr[i] = Double.valueOf(stock.getProfile().get(key + j)).longValue();
			} catch (NullPointerException e) {
				System.out.println("Key not found in profile\n" + e.getStackTrace());
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
	static Double[] getFCFChangePercentRecentAndAverageArr(Stock stock) throws NumberFormatException {
		Double[] arr = new Double[2];
		long[] fcf = Evaluator.getFreeCashFlow(stock);
		double avg = 0;

		for (int i = 0; i < (Arrays.stream(fcf).count() - 1); i++) {
			avg += Evaluator.getYOYFCFChange(fcf, i);
		}

		avg = avg / 4;
		
//		this block helps mitigate the negative growth from the early years of new companies
		if (avg < 0) {
			avg = 0;
			for (int i = 0; i < (Arrays.stream(fcf).count() - 2); i++) {
				avg += Evaluator.getYOYFCFChange(fcf, i);
			}
			
			avg = avg/3;
		}
		
		if (avg < 0) {
			avg = Evaluator.getYOYFCFChange(fcf, 0);
			stock.setOddPercentGrowth(true);
		}

		arr[0] = Evaluator.roundFloats(Evaluator.getYOYFCFChange(fcf, 0));
		arr[1] = Evaluator.roundFloats(avg);
		return arr;
	}

//	returns percent change from the year specified by count and the year prior
	static double getYOYFCFChange(long[] arr, int year) {
		if (arr[year+1] == 0) {
			System.out.println("Free Cash Flow data missing");
			return 0.0;
		}
		BigDecimal change = (((BigDecimal.valueOf(arr[year]).setScale(4, RoundingMode.HALF_EVEN)
				.subtract(BigDecimal.valueOf(arr[year + 1])).setScale(4, RoundingMode.HALF_EVEN)
				.multiply(BigDecimal.valueOf(100.0)).setScale(4, RoundingMode.HALF_EVEN)
				.divide(BigDecimal.valueOf(arr[year + 1]), RoundingMode.HALF_EVEN))));
		double change1 = change.doubleValue();
		return change1;
	}

	static double getCashYield(Stock stock) {
		long mCap = 0;
		if (stock.getProfile().containsKey("mktCap")) {
			mCap = Long.valueOf(stock.getProfile().get("mktCap"));
		} else if (stock.getProfile().containsKey("marketCap")) {
			mCap = Long.valueOf(stock.getProfile().get("marketCap"));
		}
		if (mCap == 0) {
			mCap = Long.valueOf(stock.getMCap());
		}
		long fcf = Evaluator.getFCFAvg(stock);
		double yield = Evaluator.roundFloats(fcf * 100.0 / mCap);
		return yield;
	}

	static long getCurrentFCF(Stock stock) {
		return Arrays.stream(Evaluator.getFreeCashFlow(stock)).findFirst().getAsLong();
	}

	static String getEquityDebtRatio(Stock stock) {
		double equity = Arrays.stream(Evaluator.valuesArray(stock, "Total shareholders equity")).findFirst().getAsLong();
		double debt = Arrays.stream(Evaluator.valuesArray(stock, "Long-term debt")).findFirst().getAsLong();

		Double equityDebt = Evaluator.roundFloats(equity/debt);
		
		try {
			return equityDebt.toString();
		} catch (ArithmeticException e) {
			return "-1.0";
		}

	}

	static long getChangeInDebt(Stock stock) {
		long change = Arrays.stream(Evaluator.valuesArray(stock, "Long-term debt")).findFirst().getAsLong()
				- Arrays.stream(Evaluator.valuesArray(stock, "Long-term debt"), 1, 2).findAny().getAsLong();
		return change;
	}

//	capex comes in positive, you want the other two in negative.  Positive bb = selling of shares
	static Integer[] getCapitalEfficiency(Stock stock) {
		long[] div, capex, bb;
		Integer[] capEff;

		capex = Evaluator.valuesArray(stock, "Capital Expenditure");
		div = Evaluator.valuesArray(stock, "Dividend payments");
		bb = Evaluator.valuesArray(stock, "Issuance (buybacks) of shares");

		capEff = calculateCapEff(capex, div, bb, stock);

		return capEff;
	}

//	This method is a mess.  It takes capital expenditures and determines whether the company spent more on that or on rewarding shareholders
//	expressed in an int to be rendered as a percentage.  Negative percentage indicates more spent on capex than than div and bb
	static Integer[] calculateCapEff(long[] capex, long[] div, long[] bb, Stock stock) {
		long[] cap = new long[5];
		double[] temp = new double[5];
		Integer[] capEff = new Integer[5];

		for (int i = 0; i < Arrays.stream(cap).count(); i++) {
			try {
				cap[i] = div[i] + bb[i];
			} catch (ArrayIndexOutOfBoundsException e) {
				stock.setNewCompany(true);
				cap[i] = -1;
			}
			
		}

		for (int i = 0; i < Arrays.stream(capex).count(); i++) {
			temp[i] = (capex[i] < Math.abs(cap[i])) ? Math.abs(cap[i]) - capex[i]
					: Math.negateExact(capex[i] - Math.abs(cap[i]));
		}

		for (int i = 0; i < Arrays.stream(cap).count(); i++) {

			try {
				if (capex[i] == 0) {
					capEff[i] = 0;
					continue;
				}
				capEff[i] = (int) ((temp[i] / capex[i]) * 100);
			} catch (ArrayIndexOutOfBoundsException e) {
				capEff[i] = -1;
			}
		}
		return capEff;
	}

//	Renders an array indicating the difference between this years number and last years, by key
//	For instance, if you enter the key "Revenue" it will return an array with the revenues for the last 4 years.
	static long[] getYOYChangeForKey(Stock stock, String key) throws ArrayIndexOutOfBoundsException {
		long[] arr = Evaluator.valuesArray(stock, key), change = new long[4];
//		this is to throw an exception if the key does not exist
		if (arr[0] == 0 && arr[1] == 0) {
			System.err.println("Key does not exist in YOYChange method.");
		}

		int j = 0;

		for (int i = 0; i < Arrays.stream(arr).count() - 1; i++) {
			change[j] = arr[i] - arr[i + 1];
			if (j < 3) {
				j++;
			}
		}

		return change;
	}

	static int[] getReturnOn(String key, Stock stock) {
		String[] keys;
		long[] arr = new long[5], arr1 = Evaluator.valuesArray(stock, "Net Income");
		int[] returnOn = new int[5];

		keys = checkAssetsOrCapital(key);

		if (keys[0].equals("Total assets")) {
			arr = tAssets(keys, stock);
		} else if (keys[0].equals("Total shareholders equity")) {
			arr = iCapital(keys, stock);
		} else {
			returnOn = null;
		}

		for (int i = 0; i < Arrays.stream(arr).count(); i++) {
			try {
				returnOn[i] = (int) (arr1[i] * 100 / arr[i]);
//				this if block catches likely erroneous data from the api
				if (returnOn[i] > 100) {
					returnOn[i] = 1000;
				}
			} catch (ArithmeticException e) {
				returnOn[i] = 0;
				System.out.println("Missing " + key + " data for " + stock.getProfile().get("ticker"));
			}
			
		}
		return returnOn;
	}

	static String[] checkAssetsOrCapital(String key) {
		String[] keys = new String[2];
		if (key.equals("assets")) {
			keys[0] = "Total assets";
			keys[1] = "Goodwill and Intangible Assets";
		} else if (key.equals("capital")) {
			keys[0] = "Total shareholders equity";
			keys[1] = "Long-term debt";
		} else {
			System.err.println("Invalid key in getReturnOn()");
			return null;
		}
		return keys;
	}

	static long[] tAssets(String[] keys, Stock stock) {
		long[] arrAs = Evaluator.valuesArray(stock, keys[0]);
		long[] arrGw = Evaluator.valuesArray(stock, keys[1]);
		long[] arr = new long[arrAs.length];

		for (int i = 0; i < arr.length; i++) {
			arr[i] = arrAs[i] - arrGw[i];
		}
		return arr;
	}

	static long[] iCapital(String[] keys, Stock stock) {
		long[] arrEq = Evaluator.valuesArray(stock, keys[0]);
		long[] arrDt = Evaluator.valuesArray(stock, keys[1]);
		long[] arr = new long[arrEq.length];

		for (int i = 0; i < arr.length; i++) {
			arr[i] = arrEq[i] + arrDt[i];
		}
		return arr;
	}

	static String getSharesOutstanding(Stock stock) {
		String sOut = stock.getProfile().get("Number of Shares");
		return sOut;
	}

	static double getEvEbitda(Stock stock) {
		long ev, ebitda;

		ev = Evaluator.getEv(stock);
		ebitda = Evaluator.getEbitda(stock);

		double evEbitda = Evaluator.roundFloats(ev * 1.0 / ebitda);

		return evEbitda;
	}

	static long getEbitda(Stock stock) {
		long ebitda = Long.valueOf(stock.getProfile().get("EBITDA"));
		return ebitda;
	}

	static long getEv(Stock stock) {
		long ev;
		try {
			ev = Long.valueOf(stock.getProfile().get("Enterprise Value"));
		} catch (NumberFormatException e) {
			ev = Double.valueOf(stock.getProfile().get("Enterprise Value")).longValue();
		}
		return ev;
	}

	/*
	 * fcf*(1/(1.%^counter)) - discounted cash flow formula. This is the most
	 * complex math done by the program. the percent here will be the percent return
	 * expected by the investor.
	 * 
	 * This formula takes the average of the last three years of fcf with the most
	 * recent number weighted double, extrapolates the growth rate over the next 20
	 * years (decreasing by 95% every year) with a minimum growth rate of 5%,
	 * discounts them to present value, then adds it together to determine the
	 * future value of the business. This number will be added to shareholder's
	 * equity and then divided by shares outstanding to find out the fair market
	 * value of each company share.
	 */
	static long getTotalDCF(Stock stock, String percent) {
		// returns an array that contains [0] the current fcf growth "x%" and [1] the
		// average
		Double[] fcfGrowthArr = Evaluator.getFCFChangePercentRecentAndAverageArr(stock);

		long[] fcfForCalculation = new long[19];

		long equity = Evaluator.getCurrentEquity(stock);

//		sets cashFlowGrowthRate to 1.x (1xx%) to extrapolate fcf growth over the next 20 years
		BigDecimal cashFlowGrowthRate = BigDecimal.valueOf(fcfGrowthArr[1]).setScale(3, RoundingMode.HALF_EVEN)
				.divide(BigDecimal.valueOf(100).setScale(3, RoundingMode.HALF_EVEN))
				.add(BigDecimal.valueOf(1).setScale(3, RoundingMode.HALF_EVEN));
		
		
		if (cashFlowGrowthRate.compareTo(BigDecimal.valueOf(2.0)) > 0 == true || 
				cashFlowGrowthRate.compareTo(BigDecimal.valueOf(-2.0)) < 0 == true) {
			cashFlowGrowthRate = BigDecimal.valueOf(0);
			stock.setOddPercentGrowth(true);
		}

		// returns the average fcf over the last three years with double weight given to
		// the most recent year
		fcfForCalculation[0] = Evaluator.getFCFAvg(stock);
//		Initial value assigned to dcf because this year's cash is not discounted
		long dcf = fcfForCalculation[0];

//		sets the divisor by which we decrease dcfMultipliers year over year to the rate of return desired
		String divisorString = "1." + percent;
		BigDecimal divisor = BigDecimal.valueOf(Double.valueOf(divisorString)).setScale(8, RoundingMode.HALF_EVEN);

//		Contains the values by which each years extrapolated fcf will be multiplied by to obtain the discounted value
		BigDecimal[] dcfMultipliers = calculateDCFMultipliers(divisor);

		dcf = calculateAndTotalDCF(dcfMultipliers, fcfForCalculation, cashFlowGrowthRate, dcf);

		return dcf;
	}

//	calculates and fills the dcf multipliers array
	static BigDecimal[] calculateDCFMultipliers(BigDecimal divisor) {
		BigDecimal[] dcfMultipliers = new BigDecimal[19];
		for (int i = 1; i < 20; i++) {
			dcfMultipliers[i - 1] = ((BigDecimal.valueOf(1L).setScale(5, RoundingMode.HALF_EVEN).divide(divisor.pow(i),
					RoundingMode.HALF_EVEN)));
		}
		return dcfMultipliers;
	}

//	takes in all of the constants/variables and calculates and returns the total discounted cash flow
//	this method is tested by the test for getTotalDCF() method
	static long calculateAndTotalDCF(BigDecimal[] dcfMultipliers, long[] fcfForCalculation,
			BigDecimal cashFlowGrowthRate, long dcf) {
//		cash flow growth rate decreases by 95% each year stopping at 5% growth rate
		for (int i = 1; i < 19; i++) {
//			this statement's formula is fcf[i] = lastYearsFCF+(lastyr*growthrate)
//			fcfForCalculation[0] is already assigned to current FCF
			fcfForCalculation[i] += cashFlowGrowthRate
					.multiply(BigDecimal.valueOf(fcfForCalculation[i - 1]).setScale(3, RoundingMode.HALF_EVEN))
					.longValue();

//			this block decreases projected growth rate by 95% each time the loop is run, stopping at 5% projected growth
			if (cashFlowGrowthRate.compareTo(BigDecimal.valueOf(1.05)) > 0 == true) {
				cashFlowGrowthRate = cashFlowGrowthRate.setScale(3, RoundingMode.HALF_EVEN)
						.multiply(BigDecimal.valueOf(0.9931).setScale(2, RoundingMode.HALF_EVEN));
			} else if (cashFlowGrowthRate.compareTo(BigDecimal.valueOf(1.05)) < 0 == true) {
				cashFlowGrowthRate = BigDecimal.valueOf(1.05);
			}

//			this last statement totals discounted cash flow
			dcf += dcfMultipliers[i - 1]
					.multiply(BigDecimal.valueOf(fcfForCalculation[i]).setScale(2, RoundingMode.HALF_EVEN)).longValue();
		}
		return dcf;
	}

	static long getCurrentEquity(Stock stock) {
		long equity;
		try {
			equity = Long.valueOf(stock.getProfile().get("Total shareholders equity0"));
		} catch (NumberFormatException e) {
			equity = Double.valueOf(stock.getProfile().get("Total shareholders equity0")).longValue();
		}
		
		return equity;
	}

//	Returns the average of the last three years fcf with current fcf double-weighted
	static long getFCFAvg(Stock stock) {
		long[] fcf = Evaluator.getFreeCashFlow(stock);
		long avg = (2 * fcf[0] + fcf[1] + fcf[2]) / 4;
		return avg;
	}

	static long getTotalFutureValue(Stock stock) {
		long equity = getCurrentEquity(stock);
		long totalDcf = getTotalDCF(stock, "10");
		long total = equity + totalDcf;
		return total;
	}

	static BigDecimal getFairValue(Stock stock) {
		BigDecimal total = BigDecimal.valueOf(getTotalFutureValue(stock));
		BigDecimal sharesOutstanding = BigDecimal.valueOf(Double.valueOf(getSharesOutstanding(stock))
				.longValue()).setScale(7, RoundingMode.HALF_EVEN);
		try {
			BigDecimal shareValue = (total.divide(sharesOutstanding, MathContext.DECIMAL32)).setScale(2,
					RoundingMode.HALF_EVEN);
			return shareValue;
		} catch (ArithmeticException e) {
			return BigDecimal.valueOf(0);
		}
	}

//	still testing but looks good
	static BigDecimal getSafeValue(Stock stock) {
		String margin = Double.valueOf(stock.getMarginOfSafety()).toString();
		BigDecimal fValue = getFairValue(stock);
		BigDecimal mos = BigDecimal.valueOf(1).subtract(BigDecimal.valueOf(Double.valueOf(margin)));
		BigDecimal safeValue = fValue.multiply(mos).setScale(2, RoundingMode.HALF_EVEN);
		return safeValue;
	}

//	this method checks the date each time the program is launched
	static boolean checkDate(Stock stock) {
		boolean dateCheck = false;
		String newDate = "";
		FMPWebReaderFilter wr = new FMPWebReaderFilter();
		try {
			newDate = wr.checkReturnDate(stock.getTicker());
		} catch (IOException e) {
			System.out.println("Could not retrieve date\nStill need to implement method");
			e.printStackTrace();
		}
		if (stock.getLastDate().toString().equals(newDate.toString())) {
			dateCheck = true;
		}
		return dateCheck;
	}

	static String getPrice(Stock stock) {
		String price = stock.getProfile().get("price");
		return price;
	}

	static String getDate(Stock stock) {
		String date = stock.getProfile().get("date");
		return date;
	}

	static String getMarketCap(Stock stock) {
		String mCap = stock.getProfile().get("marketCap");
		return mCap;
	}

//	These catch blocks need to redirect to other methods that use old data if they are triggered
	static void updatePriceData(Stock stock) {
		HashMap<String, String> newPriceData = new HashMap<>();
		FMPWebReaderFilter wr = new FMPWebReaderFilter();
		try {
			newPriceData = wr.updatePriceData(stock);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		stock.setCurrentPrice(newPriceData.get("price"));
		stock.setMCap(newPriceData.get("mktCap"));
		stock.setCashYield(Evaluator.getCashYield(stock));
		stock.setEnterpriseValue(newPriceData.get("Enterprise Value"));
		stock.setEvEbitda(Evaluator.getEvEbitda(stock));
		stock.setSharesOut(newPriceData.get("Number of Shares"));
	}

	static void clearProfile(Stock stock) {
		stock.setProfile(null);
	}

//	This method will be called on all new stock to move data from the profile(temporary hashmap) to the permanent details stored therein
//	It will also be called by the program every time a new annual report is released.
	static void storeDetails(Stock stock) {
		stock.setName(stock.getProfile().get("companyName"));
		stock.setSector(stock.getProfile().get("sector"));
		stock.setDescription(stock.getProfile().get("description"));
		stock.setIndustry(stock.getProfile().get("industry"));
		
		stock.setCapEff(Evaluator.getCapitalEfficiency(stock));
		stock.setCashToDebt(Evaluator.getCashToDebt(stock));
		stock.setCashYield(Evaluator.getCashYield(stock));
		stock.setChangeInDebt(Evaluator.getChangeInDebt(stock));
		stock.setCOH(Evaluator.getCashOnHand(stock));
		stock.setCroic(Evaluator.setCroic(stock));
		stock.setCurrentPrice(Evaluator.getPrice(stock));
		stock.setEnterpriseValue(Evaluator.getEv(stock));
		stock.setEquityToDebt(Evaluator.getEquityDebtRatio(stock));
		stock.setProfitMargin(Evaluator.getProfitMargins(stock)[0],
					Evaluator.avgProfitMargin(Evaluator.getProfitMargins(stock)));

		stock.setEvEbitda(Evaluator.getEvEbitda(stock));
		stock.setFcf(Evaluator.getFreeCashFlow(stock)[0], Evaluator.getFCFAvg(stock));
		stock.setFcfChange(Evaluator.getFCFChangePercentRecentAndAverageArr(stock));
		stock.setLastDate(Evaluator.getDate(stock));
		stock.setMCap(Evaluator.getMarketCap(stock));
		if (Long.valueOf(stock.getMCap()) > 50000000000L) {
			stock.setMarginOfSafety(0.25);
		} else if (Long.valueOf(stock.getMCap()) > 20000000000L) {
			stock.setMarginOfSafety(0.35);
		}
		stock.setROnAssets(Evaluator.getReturnOn("assets", stock)[0]);
		stock.setROnCapital(Evaluator.getReturnOn("capital", stock)[0]);
		stock.setFairValue(Evaluator.getFairValue(stock));
		stock.setSafeValue(Evaluator.getSafeValue(stock));
		stock.setSharesOut(Evaluator.getSharesOutstanding(stock));

		Evaluator.clearProfile(stock);
	}
	
//	for testing.  Remember, you need this!
	static void storeDetails(Stock stock, boolean test) {
		stock.setName(stock.getProfile().get("companyName"));
		stock.setSector(stock.getProfile().get("sector"));
		stock.setDescription(stock.getProfile().get("description"));
		stock.setIndustry(stock.getProfile().get("industry"));
		
		stock.setCapEff(Evaluator.getCapitalEfficiency(stock));
		stock.setCashToDebt(Evaluator.getCashToDebt(stock));
		stock.setCashYield(Evaluator.getCashYield(stock));
		stock.setChangeInDebt(Evaluator.getChangeInDebt(stock));
		stock.setCOH(Evaluator.getCashOnHand(stock));
		stock.setCroic(Evaluator.setCroic(stock));
		stock.setCurrentPrice(Evaluator.getPrice(stock));
		stock.setEnterpriseValue(Evaluator.getEv(stock));
		stock.setEquityToDebt(Evaluator.getEquityDebtRatio(stock));
		stock.setProfitMargin(Evaluator.getProfitMargins(stock)[0],
					Evaluator.avgProfitMargin(Evaluator.getProfitMargins(stock)));
		stock.setEvEbitda(Evaluator.getEvEbitda(stock));
		stock.setFcf(Evaluator.getFreeCashFlow(stock)[0], Evaluator.getFCFAvg(stock));
		stock.setFcfChange(Evaluator.getFCFChangePercentRecentAndAverageArr(stock));
		stock.setLastDate(Evaluator.getDate(stock));
		stock.setMCap(Evaluator.getMarketCap(stock));
		if (Long.valueOf(stock.getMCap()) > 50000000000L) {
			stock.setMarginOfSafety(0.25);
		} else if (Long.valueOf(stock.getMCap()) > 20000000000L) {
			stock.setMarginOfSafety(0.35);
		}
		stock.setROnAssets(Evaluator.getReturnOn("assets", stock)[0]);
		stock.setROnCapital(Evaluator.getReturnOn("capital", stock)[0]);
		stock.setFairValue(Evaluator.getFairValue(stock));
		stock.setSafeValue(Evaluator.getSafeValue(stock));
		stock.setSharesOut(Evaluator.getSharesOutstanding(stock));
	}

	/*-
	 * profile Keys: [companyName], [EBITDA], [Enterprise Value], [description],
	 * [industry], [yearHigh], [price], [sector], [yearLow], [marketCap], [ticker],
	 * [Revenue Growth], [Cash and cash equivalents], [Number of Shares], [date]
	 * 
	 * replace [marketCap] with [mktCap] for updatePriceData();
	 * 
	 * Array keys: [Revenuei], [Long-term debti], [Dividend per Sharei], [Operating
	 * Cash Flowi], [Net Incomei], [Goodwill and Intangible Assetsi], Total shareholders equityi], 
	 * [Capital Expenditurei], [Total assetsi],
	 * ["Issuance (buybacks) of shares"i] ["Dividend payments"i]
	 * 
	 * keys(i) with numbers at the end represent (0) = this year, (-1) = year prior,
	 * etc.
	 */

}