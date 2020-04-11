package companyValueModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
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

	private String safeValue;
	private String safeValueCroic;
	private String fairValue;
	private String fairValueCroic;
	private long futureValue;
	private String[] fcf;
	private String rOnAssets;
	private String rOnCapital;
	private String equityToDebt;
	private String cashToDebt;
	private String changeInDebt;
	private String capEfficiency;
//	contains [0] current and [1] average
	private String[] fcfChange;
//	contains [0] current and [1] average
	private String[] profitMargin;
//	contains [0] current and [1] average
	private double[] croic;
	private String revenue;
	private String cashFlow;
	private String assets;
	private String equity;
	private String debt;
	private String[] divPerShare;
	private long cashOnHand;
//	tells the program whether or not this stock should be refreshed with data from the api
//	this is turned off in cases of manual entry
	private boolean autoUpdate = true;

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
		this.divPerShare = new String[2];
		this.croic = new double[2];
		this.autoUpdate = true;
	}

	public Stock(HashMap<String, String> profile) {
		this.profile = profile;
		this.marginOfSafety = 0.5;
		this.ticker = profile.get("ticker").toUpperCase();
		this.fcf = new String[2];
		this.fcfChange = new String[2];
		this.profitMargin = new String[2];
		this.divPerShare = new String[2];
		this.croic = new double[2];
		this.autoUpdate = true;
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

	public String getRevenue() {
		return revenue;
	}

	public void setRevenue(String revenue) {
		this.revenue = revenue;
	}

	public String getCashFlow() {
		return cashFlow;
	}

	public void setCashFlow(String cashFlow) {
		this.cashFlow = cashFlow;
	}

	public String getAssets() {
		return assets;
	}

	public void setAssets(String assets) {
		this.assets = assets;
	}

	public String getEquity() {
		return equity;
	}

	public void setEquity(String equity) {
		this.equity = equity;
	}

	public String getDebt() {
		return debt;
	}

	public void setDebt(String debt) {
		this.debt = debt;
	}

	public void setDivPerShare(String div, String lastDiv) {
		this.divPerShare[0] = div;
		this.divPerShare[1] = lastDiv;
	}

	public String[] getDivPerShare() {
		return divPerShare;
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

	public void setLastDate(String date) {
		this.lastDate = date;
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
		fairValue = fairValue.setScale(2, RoundingMode.HALF_UP);
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

	public double[] getCroic() {
		return croic;
	}

	public void setCroic(double[] croic) {
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

	public void setCOH(String coh) {
		this.cashOnHand = Long.valueOf(coh);
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

	public String getCapEff() {
		DecimalFormat df = new DecimalFormat("0.00");
		Double capEff = Double.valueOf(capEfficiency);
		String eff = df.format(capEff);
		return eff;
	}

	public void setCapEff(Double capEfficiency) {
		this.capEfficiency = capEfficiency.toString();
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

	public boolean isAutoUpdate() {
		return autoUpdate;
	}

	public void setAutoUpdate(boolean update) {
		this.autoUpdate = update;
	}

	public Long getFutureValue() {
		return futureValue;
	}

	public void setFutureValue(long l) {
		this.futureValue = Long.valueOf(l);
	}

	public String getSafeValueCroic() {
		return safeValueCroic;
	}

	public void setSafeValueCroic(String safeValueCroic) {
		this.safeValueCroic = safeValueCroic;
	}

	public String getFairValueCroic() {
		return fairValueCroic;
	}

	public void setFairValueCroic(String fairValueCroic) {
		this.fairValueCroic = fairValueCroic;
	}

}