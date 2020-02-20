package IncomeTracker;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

interface Taxes {
	@XmlRootElement
	class taxRate {
		private static double taxRate;
		
		private double xmlTaxRate;
		@XmlElement
		public double getXmlTaxRate() {
			return xmlTaxRate;
		}

		public void setXmlTaxRate(double rate) {
			this.xmlTaxRate = rate;
		}

		static double getTaxRate() {
			return taxRate;
		}
		
		static void setTaxRate(double rate) {
			taxRate = rate;
		}
	}
	
	static void tax(Paid paid) {
		paid.setEarnings(paid.getGrossPay() -
				(paid.getGrossPay()*taxRate.taxRate)
				);
		paid.setTaxes(paid.getGrossPay()*taxRate.taxRate);
	}
	
	static void storeTax(Paid paid, int key) {
		double taxes = paid.getGrossPay()*taxRate.taxRate+
				paid.getLastWeekGrossPay()*taxRate.taxRate;
		paid.getPayDaysTax().put(
				key,
				taxes
				);
	}

}
