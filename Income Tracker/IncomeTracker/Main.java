package IncomeTracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlRootElement;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
	public static List<Paid> payees;

	public static void main(String[] args) {
		
		Path source = Paths.get("IncomeTracker\\payees.xml");
		Path taxSource = Paths.get("IncomeTracker\\taxRate.xml");
		payees = new ArrayList<>();
		payees = importPayees(payees, source);
		Taxes.taxRate.setTaxRate(IXml.unmarshal(0.15, taxSource));
//		to be reworked
//		Paid.setDifferential(3.5, 4, 2);
		mainMenu();
	}

	//populates payees from xml document
	static List<Paid> importPayees(List<Paid> payees, Path source) {
		if (Files.notExists(source)) {
			IXml.createXmlFile(source);
		} else {
//			marshalTest(source, payees);
			payees = IXml.unmarshal(payees, source);
		}
		return payees;
	}
	
	static void mainMenu() {
		System.out.println("Select from the following using the numbers:");
		System.out.println("--------------------------------------------");
		System.out.println("1) View and select job or employee");
		System.out.println("2) Enter new job or employee");
		System.out.println("3) Taxes");
		System.out.println("4) Save and Exit");
		System.out.println("5) Exit without saving");
		Scanner selection = new Scanner(System.in);
		String select = selection.nextLine();
		try {
			switch (Integer.parseInt(select)) {
			case 1:
				if (!payees.isEmpty()) {
					existingPaid();
				} else {
					System.out.println("No jobs or employees");
					mainMenu();
				}
				break;
			case 2:
				newPaid();
				break;
			case 3:
				changeTaxes();
				break;
			case 4:
				saveAndExit();
				break;
			case 5:
				exit();
				break;
			default:
				System.out.println("Please enter a valid response");
				mainMenu();
			}
		} catch (NumberFormatException e) {
			System.out.println("Please enter a valid response");
			mainMenu();
		}
	}

	private static void saveAndExit() {
		IXml.marshal(Paths.get("IncomeTracker\\payees.xml"), payees);
		IXml.marshal(Paths.get("IncomeTracker\\taxRate.xml"), Taxes.taxRate.getTaxRate());
		System.out.println("Changes saved: Exiting");
		System.exit(0);
	}
	
	private static void exit() {
		System.out.println("Exiting");
		System.exit(0);
	}
//	works
	private static void changeTaxes() {
		StringBuilder s = new StringBuilder(40);
		s.append("Current tax rate: ").append(Taxes.taxRate.getTaxRate()*100)
			.append("%").append("\nChange tax rate?");
		System.out.println(s.toString());
		Scanner R = new Scanner(System.in);
		String r = R.nextLine();
		if (r.contentEquals("yes") ||
				r.contentEquals("y")) {
//			changing taxes here
			StringBuilder sb = new StringBuilder(50);
			sb.append("Enter the new tax rate: ").append("(\nFormat example: 15.6)");
			System.out.println(sb.toString());
			Scanner in = new Scanner(System.in);
			String rate = in.nextLine();
			Taxes.taxRate.setTaxRate(Double.valueOf(rate)/100);
			StringBuilder builder = new StringBuilder(15);
			builder.append("New tax rate: ").append(Taxes.taxRate.getTaxRate()*100)
				.append("%");
			System.out.println(builder.toString());
			mainMenu();	
		} else if (r.contentEquals("no") || 
				r.contentEquals("n")) {
			System.out.println("Returning to main menu");
			mainMenu();
		} else {
			System.out.println("Please try again");
			changeTaxes();
		}
	}
//	works but primitive
	private static void newPaid() {
		Scanner n = new Scanner(System.in);
		String name;
		double rate = 0;
		System.out.print("Enter \"r\" to return\nEnter name: ");
		name = n.nextLine();
		if (name.matches(".*\\d.*")) {
			System.out.println("Invalid number");
			newPaid();
		} else if (name.contentEquals("r".toLowerCase())) {
			System.out.println("Returning to main menu");
			mainMenu();
		}
		System.out.print("Enter hourly wage: ");
		try {
			rate = Double.valueOf(n.nextLine());
		} catch (Exception e) {
			System.out.println("Please enter a valid number");
			newPaid();
		}
		generatePaid(name, rate);
	}

	private static void generatePaid(String name, double rate) {
		Paid paid = new Paid(name, rate);
		StringBuilder sb = new StringBuilder(75);
		sb.append("You have entered:\n").append("Name: ").append(paid.getName())
			.append("\nWage: $").append(paid.getRate()).append("per hour")
			.append("\nCorrect? Y or N\n");
		System.out.println(sb.toString());
		Scanner response = new Scanner(System.in);
		String r = response.nextLine();
		if (r.toLowerCase().contentEquals("y".toLowerCase())) {
			System.out.println("Track-ee entered");
			payees.add(paid);
			mainMenu();
		} else if (r.toLowerCase().contentEquals("n")) {
			System.out.println("Returning to menu");
			newPaid();
		} else {
			System.out.println("Invalid response");
			mainMenu();
		}
	}

	private static void existingPaid() {
		System.out.println("Select from the following using the numbers:");
		System.out.println("--------------------------------------------");
		System.out.println("1) Search Track-ees");
		System.out.println("2) Display Trackees");
		System.out.println("3) Return to previous menu");
		Scanner selection = new Scanner(System.in);
		String select = selection.nextLine();
		try {
			switch (Integer.parseInt(select)) {
			case 1:
				search();
				break;
			case 2:
				payees.forEach(m -> System.out.println(m.getName()));
				break;
			case 3:
				mainMenu();
				break;
			default:
				System.out.println("Please enter a valid response");
				existingPaid();
			}
		} catch (NumberFormatException e) {
			System.out.println("Please enter a valid response");
			existingPaid();
		}
	}


	private static void search() {
		System.out.println("Enter \"r\" to return to the previous menu");
		System.out.println("Enter the name of the person you are searching for:");
		System.out.println("---------------------------------------------------");
		Scanner response = new Scanner(System.in);
		String who = response.nextLine();
		if (who.toLowerCase().contentEquals("r")) {
			existingPaid();	
		} else {
			Paid paid = searchByNameReturnPaid(who);
			if (paid != null) {
				SinglePaidMenu.foundPaid(paid);
			} else if (paid == null) {
				searchPaidByLetter(who);
			}
		}
	}

	private static void searchPaidByLetter(String who) {
			StringBuilder sb = new StringBuilder(55);
			sb.append("Do you see the name below?\n").append("--------------------------\n");
			System.out.println(sb.toString());
			String start = Character.toString(who.charAt(0));
			List<Paid> list = 
					payees.stream().filter(p -> p.getName().toLowerCase()
					.startsWith(start)).collect(Collectors.toList());
			list.forEach(p -> System.out.println(p.getName()));
			System.out.println("Enter \"yes\" or \"no\"");
			Scanner reply = new Scanner(System.in);
			String r = reply.nextLine().toLowerCase();
				if (r.contentEquals("yes") ||
						r.contentEquals("y")) {
					System.out.print("Enter desired name: ");
					Scanner entry = new Scanner(System.in);
					String e = entry.nextLine().toLowerCase();
					if (list.stream()
							.map(p -> new String(p.getName().toLowerCase()))
							.anyMatch(e::contains)
							){
						Optional<Paid> paid = list.stream()
							.filter(p -> e.equals(p.getName().toLowerCase())).findAny();
						SinglePaidMenu.foundPaid(paid.get());
					} else {
						System.out.println("That Track-ee does not exist");
						existingPaid();
					}
				} else if (r.contentEquals("no") || 
						r.contentEquals("n")) {
					search();
				} else {
					System.out.println("Please try again");
					search();
				}			
	}

	private static Paid searchByNameReturnPaid(String who) {
		Paid paid = payees.stream()
			.filter(payee -> who.toLowerCase().equals(payee.getName()
					.toLowerCase()))
			.findAny()
			.orElse(null);
		return paid;
	}
	
	static void deletePaid(Paid paid) {
		payees.remove(paid);
		System.out.println(paid.getName() + " removed");
		mainMenu();
	}
}
