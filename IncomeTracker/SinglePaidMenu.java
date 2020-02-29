package IncomeTracker;

import java.text.DecimalFormat;
import java.util.IllegalFormatException;
import java.util.Optional;
import java.util.Scanner;

public interface SinglePaidMenu {
	
	static void foundPaid(Paid paid) {
		paidMenu(paid);
	}

	static void paidMenu(Paid paid) {
		StringBuilder sb = new StringBuilder(260);
		sb.append("What would you like to do with ").append(paid.getName())
			.append("?\n")
			.append("--------------------------------------------")
			.append("\n1) View details")
			.append("\n2) Add shift")
			.append("\n3) Add bonus")
			.append("\n4) Move to second week of pay period")
			.append("\n5) Add pay day")
			.append("\n6) View pay days")
			.append("\n7) Change hourly pay")
			.append("\n8) Change night status")
			.append("\n9) Change withholdings")
			.append("\n10) Delete "+ paid.getName())
			.append("\n11) Return to main menu");
		System.out.println(sb.toString());
		Scanner selection = new Scanner(System.in);
		String select = selection.nextLine();
		try {
			switch (Integer.parseInt(select)) {
			case 1:
				printDetails(paid);
				break;
			case 2:
				addShift(paid);
				break;
			case 3:
				addBonus(paid);
				break;
			case 4:
				endWeek1(paid);
				break;
			case 5:
				payDay(paid);
				break;
			case 6:
				viewPayDays(paid);
				break;
			case 7:
				changeRate(paid);
				break;
			case 8:
				changeNightStatus(paid);
				break;
			case 9:
				changeWithholdings(paid);
				break;
			case 10:
				delete(paid);
				break;
			case 11:
				Main.mainMenu();
				break;
			default:
				System.out.println("Please enter a valid response");
				paidMenu(paid);
			}
		} catch (NumberFormatException e) {
			System.out.println("Please enter a valid response");
			paidMenu(paid);
		}
	
	}

	static void printDetails(Paid paid) {
		DecimalFormat df = new DecimalFormat("0.00");
		StringBuilder deets = new StringBuilder(180);
		deets.append(paid.getName()).append("'s details: \n").append("Hourly Pay: $")
			.append(paid.getRate()).append("\nHours Worked this pay period: ")
			.append(Double.valueOf(df.format(paid.getHoursWorked()+paid.getLastWeekHours())))
			.append("\nGross Pay: $").append(df.format(paid.getGrossPay()
					+paid.getLastWeekGrossPay()))
			.append("\nAfter taxes: $").append(df.format(paid.getEarnings()
					+paid.getLastWeekEarnings()))
			.append("\nNight Status: ").append(paid.getNight());
		System.out.println(deets.toString());
		if (!paid.getWithholdings().isEmpty()) {
			System.out.println("Withholdings per pay check: $" + 
					df.format(paid.getWithholdings().get("Withholdings")));
		}
		paidMenu(paid);
	}

//	works
	static void addShift(Paid paid) {
		DecimalFormat df = new DecimalFormat("0.00");
		StringBuilder s = new StringBuilder(100);
		s.append("Current Hours worked: ").append(paid.getHoursWorked())
			.append("\nCurrent Earnings: $").append(df.format(paid.getEarnings()))
			.append("\nHow many hours would you like to add?");
		System.out.println(s.toString());
		Scanner h = new Scanner(System.in);
		String hours = h.nextLine();
		Income.addShift(paid, Double.valueOf(hours));
		StringBuilder sb = new StringBuilder(50);
		sb.append("Hours accrued: ").append(Double
			.valueOf(df.format(paid.getHoursWorked()))).append("\nEarnings to date: $")
			.append(df.format(paid.getEarnings()));
		System.out.println(sb.toString());
		paidMenu(paid);
	}
//	works
	static void addBonus(Paid paid) {
		DecimalFormat df = new DecimalFormat("0.00");
		System.out.println("How much was the bonus?");
		Scanner b = new Scanner(System.in);
		String bonus = b.nextLine();
		Income.bonus(paid, Double.valueOf(bonus));
		StringBuilder sb = new StringBuilder();
		sb.append("Bonus applied. Earnings Balance: ")
			.append(df.format(paid.getEarnings()));
		System.out.println(sb.toString());
		paidMenu(paid);
		
	}
	static void endWeek1(Paid paid) {
		DecimalFormat df = new DecimalFormat("0.00");
		Income.weekEnds(paid);
		StringBuilder sb = new StringBuilder(30);
		sb.append("Earnings this week: ")
			.append(df.format(paid.getLastWeekEarnings()));
		System.out.println(sb.toString());
		paidMenu(paid);
		
	}

	static void payDay(Paid paid) {
		DecimalFormat df = new DecimalFormat("0.00");
		Income.payDay(paid);
		StringBuilder sb = new StringBuilder(50);
		sb.append("\nGross Pay this period: $")
			.append(df.format(paid.getPayDaysGrossLast()))
			.append("\nAfter taxes: $").append(df.format(paid.getPayDaysNetLast()));
		System.out.println(sb.toString());
		paidMenu(paid);		
	}
	
	static void viewPayDays(Paid paid) {
		if (paid.getPayDaysGross().isEmpty()) {
			System.out.println("No pay days recorded");
			paidMenu(paid);
		} else {
			paid.printPayDaysGross();
			paid.printPayDaysNet();
			paidMenu(paid);
		}
	}
	

	static void changeRate(Paid paid) {
		DecimalFormat df = new DecimalFormat("0.00");
		StringBuilder sb = new StringBuilder(50);
		sb.append("Current hourly rate: $").append(df.format(paid.getRate()))
		.append("\nPlease enter new rate, or enter \"r\" to return");
		System.out.println(sb.toString());
		Scanner r = new Scanner(System.in);
		String rate = r.nextLine();
		if (rate.toLowerCase().startsWith("r")) {
			System.out.println("Returning");
			paidMenu(paid);
		} else {
			try {
				paid.setRate(Double.valueOf(rate));
				System.out.println("New wage: $" + df.format(paid.getRate()));
				paidMenu(paid);
			} catch (IllegalFormatException e) {
//				this try catch block returns to the main menu for some reason
				System.out.println("Please enter a valid wage");
				changeRate(paid);
			}
		}
	}
	
	static void changeNightStatus(Paid paid) {
		StringBuilder sb = new StringBuilder(50);
		sb.append("Current status: ").append(paid.getNight())
			.append("\nChange? yes or no");
		System.out.println(sb.toString());
		Scanner R = new Scanner(System.in);
		String r = R.nextLine();
		if (r.contentEquals("yes") || r.contentEquals("y")) {
			paid.setNight(!paid.getNight());
			System.out.println("Night status changed");
		} else if (r.contentEquals("no") || 
				r.contentEquals("n")) {
			System.out.println("Returning to menu");
			paidMenu(paid);
		} else {
			System.out.println("Please try again");
			changeNightStatus(paid);
		}
	}
	

	static void changeWithholdings(Paid paid) {
		DecimalFormat df = new DecimalFormat("0.00");
		StringBuilder sb = new StringBuilder(40);
		sb.append("Please enter the amount to be withheld: ");
		System.out.println(sb.toString());
		Scanner s = new Scanner(System.in);
		String am = s.nextLine();
		try {
			double amount = Double.valueOf(am);
			paid.setWithholdings("Withholdings", Double.valueOf(df.format(amount)));
			StringBuilder sbuild = new StringBuilder(40);
			sbuild.append("Amount to be withheld each pay day: $")
				.append(df.format(paid.getWithholdings().get("Withholdings")));
			System.out.println(sbuild.toString());
			paidMenu(paid);
		} catch (IllegalFormatException e) {
//			this try catch block returns to the main menu for some reason
			System.out.println("Please enter a valid number");
			changeWithholdings(paid);
		}
	}
	
	static void delete(Paid paid) {
		Main.deletePaid(paid);
	}

}
