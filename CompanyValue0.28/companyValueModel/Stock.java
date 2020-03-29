package companyValueModel;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Stock {

	private String ticker;
	private String name;
	private String sector;
	private String industry;
	private String description;

	private String currentPrice;
	private double marginOfSafety;
//	remember to convert to string
	private String lastDate;
	private String cashYield;
	private long mCap;
	private String sharesOut;

	private boolean oddPercentGrowth = false;
	private boolean newCompany = false;
	private String safeValue;
	private String fairValue;
	private String[] fcf;
	private String rOnAssets;
	private String rOnCapital;
	private double croic;
	private String equityToDebt;
	private long cashOnHand;
	private String cashToDebt;
	private String changeInDebt;
	private String[] capEfficiency;
//	contains [0] current and [1] average
	private String[] fcfChange;
//	contains [0] current and [1] average
	private String[] profitMargin;

	/*
	 * Keys: [companyName], [description], [industry], [yearHigh], [price],
	 * [sector], [yearLow], [marketCap], [ticker], [Revenue Growth], [Cash and cash
	 * equivalents], [Number of Shares], [date]
	 * 
	 * Array keys: [Revenuei], [Long-term debti], [Dividend per Sharei], [Operating
	 * Cash Flowi], [Net Incomei], [Goodwill and Intangible Assetsi], [Total
	 * shareholders equityi], [Capital Expenditurei], [Total assetsi]
	 * 
	 * keys(i) with numbers at the end represent (0) = this year, (-1) = year prior,
	 * etc.
	 */

	private Map<String, String> profile;

	public Stock(String ticker) {
		this.ticker = ticker.toUpperCase();
		this.marginOfSafety = 0.5;
		this.fcf = new String[2];
		this.fcfChange = new String[2];
		this.profitMargin = new String[2];
		this.capEfficiency = new String[5];
	}

	public Stock(HashMap<String, String> profile) {
		this.profile = profile;
		this.marginOfSafety = 0.5;
		this.ticker = profile.get("ticker").toUpperCase();
		this.fcf = new String[2];
		this.fcfChange = new String[2];
		this.profitMargin = new String[2];
		this.capEfficiency = new String[5];
	}

	public Map<String, String> getProfile() {
		return profile;
	}

	public void setProfile(HashMap<String, String> profile) {
		this.profile = profile;
	}

	public String getProfileTicker() {
		return this.getProfile().get("ticker");
	}

	public String getTicker() {
		return ticker;
	}

	public void setCurrentPrice(String newPrice) {
		currentPrice = newPrice;
	}

	public String getCurrentPrice() {
		return currentPrice;
	}

	public double getMarginOfSafety() {
		return marginOfSafety;
	}

	public void setMarginOfSafety(double mos) {
		this.marginOfSafety = mos;
	}

	public LocalDate getLastDate() {
		return LocalDate.parse(lastDate);
	}

	public void setLastDate(String string) {
		String dateFromWeb = profile.get("date");
		String expectedFormat = "yyyy-MM-dd";
		DateTimeFormatter df = DateTimeFormatter.ofPattern(expectedFormat);
		LocalDate date = LocalDate.parse(dateFromWeb, df);
		this.lastDate = date.toString();
	}

	public String getCashYield() {
		return cashYield;
	}

	public void setCashYield(double cashYield) {
		DecimalFormat df = new DecimalFormat("0.0");
		this.cashYield = df.format(cashYield);
	}

	public Long getMCap() {
		return mCap;
	}

	public void setMCap(String mCap) {
		this.mCap = Long.valueOf(mCap);
	}

	public String getSafeValue() {
		return safeValue;
	}

	public void setSafeValue(BigDecimal safeValue) {
		this.safeValue = safeValue.toString();
	}

	public String getFairValue() {
		return fairValue;
	}

	public void setFairValue(BigDecimal fairValue) {
		this.fairValue = fairValue.toString();
	}

	public String getSharesOut() {
		return sharesOut;
	}

	public void setSharesOut(Long sharesOut) {
		if (!this.sharesOut.equals(0)) {
			this.sharesOut = sharesOut.toString();
		}
	}

	public void setSharesOut(String sharesOut) {
		this.sharesOut = sharesOut;
	}

	public String[] getFcf() {
		return fcf;
	}

	public void setFcf(Long fcfCurrent, Long fcfAvg) {
		this.fcf[0] = fcfCurrent.toString();
		this.fcf[1] = fcfAvg.toString();
	}

	public String getROnAssets() {
		return rOnAssets;
	}

	public void setROnAssets(Integer rOnAssets) {
		this.rOnAssets = rOnAssets.toString();
	}

	public Double getCroic() {
		return croic;
	}

	public void setCroic(Double croic) {
		this.croic = croic;
	}

	public String getROnCapital() {
		return rOnCapital;
	}

	public void setROnCapital(Integer rOnEquity) {
		this.rOnCapital = rOnEquity.toString();
	}

	public String getEquityToDebt() {
		return equityToDebt;
	}

	public void setEquityToDebt(String string) {
		this.equityToDebt = string.toString();
	}

	public Long getCOH() {
		return cashOnHand;
	}

	public void setCOH(Long coh) {
		this.cashOnHand = coh;
	}

	public String getCashToDebt() {
		return cashToDebt;
	}

	public void setCashToDebt(Double cashToDebt) {
		this.cashToDebt = cashToDebt.toString();
	}

	public String getChangeInDebt() {
		return changeInDebt;
	}

	public void setChangeInDebt(Long changeInDebt) {
		this.changeInDebt = changeInDebt.toString();
	}

	public String[] getCapEff() {
		return capEfficiency;
	}

	public void setCapEff(Integer[] capEfficiency) {
		this.capEfficiency = Arrays.stream(capEfficiency).map(i -> i.toString()).toArray(String[]::new);
	}

	public String[] getFcfChange() {
		return fcfChange;
	}

	public void setFcfChange(Double[] fcfChange) {
		this.fcfChange[0] = fcfChange[0].toString();
		this.fcfChange[1] = fcfChange[1].toString();
	}

	public String[] getProfitMargin() {
		return profitMargin;
	}

	public void setProfitMargin(Long current, int avg) {
		this.profitMargin[0] = current.toString();
		this.profitMargin[1] = Integer.valueOf(avg).toString();

	}

	public boolean isOddPercentGrowth() {
		return oddPercentGrowth;
	}

	public void setOddPercentGrowth(boolean oddPercentGrowth) {
		this.oddPercentGrowth = oddPercentGrowth;
	}

	public boolean isNewCompany() {
		return newCompany;
	}

	public void setNewCompany(boolean newCompany) {
		this.newCompany = newCompany;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

}