package companyValueGui;

import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import companyValueController.CompanyController;

public class DataEntryHandler {

	static int checkColumnName(String name, int row, String entry) {
//		returns 1 if a single entry is enough to fix the problem, 2 if multiple entries are needed
		if (name.equals("Shares Outstanding") || name.contains("Company N") || name.contains("Cash o")
				|| name.contains("Desired Margin") || name.equals("Sector") || name.equals("Industry")) {
			return 1;
		} else {
			return 2;
		}
	}

	static HashMap<String, JTextField[]> multiEntry(String name, int row, MainFrame frame, MultiEntryPanel panel) {
		HashMap<String, JTextField[]> input = new HashMap<>();
		UIManager.put("OptionPane.minimumSize", new Dimension(500, 350));
		if (name.contains("Profit")) {
			input = multiOptionPane(row, frame, panel, input);
		}

		else if (name.contains("Cash return")) {
			input = multiOptionPane(row, frame, panel, input);
		}

		else if (name.contains("Return on A")) {
			input = multiOptionPane(row, frame, panel, input);
		}

		else if (name.contains("Return on I")) {
			input = multiOptionPane(row, frame, panel, input);
		}

		else if (name.contains("Free Cash F")) {
			input = multiOptionPane(row, frame, panel, input);
		}

		else if (name.contains("Change in F")) {
			input = multiOptionPane(row, frame, panel, input);
		}

		else if (name.contains("Equity/Debt")) {
			input = multiOptionPane(row, frame, panel, input);
		}

		else if (name.contains("Cash/Debt")) {
			input = multiOptionPane(row, frame, panel, input);
		}

		else if (name.contains("Buy and Hold M")) {
			input = multiOptionPane(row, frame, panel, input);
		}

		else if (name.contains("Buy and Hold S")) {
			input = multiOptionPane(row, frame, panel, input);
		}

		else if (name.contains("Current Margin")) {
			input = multiOptionPane(row, frame, panel, input);
		} else if (name.contains("Cash Y")) {
			input = multiOptionPane(row, frame, panel, input);
		}

		else if (name.contains("Dividends Pe")) {
			input = multiOptionPane(row, frame, panel, input);
		}

		else if (name.contains("FCF G")) {
			input = multiOptionPane(row, frame, panel, input);
		}
		UIManager.put("OptionPane.minimumSize", null);

		return input;
	}

	private static HashMap<String, JTextField[]> multiOptionPane(int row, MainFrame frame, MultiEntryPanel panel,
			HashMap<String, JTextField[]> input) {
		int response = JOptionPane.showConfirmDialog(frame, panel,
				"Please Enter and Confirm Values for " + CompanyController.getStocks().get(row).getTicker(),
				JOptionPane.OK_CANCEL_OPTION);
		if (response == JOptionPane.OK_OPTION) {
			input = panel.getInput();
			return input;
		} else {
			return new HashMap<String, JTextField[]>();
		}
	}

	public static HashMap<String, JTextField[]> multiNullValues(int row, MainFrame frame,
			HashMap<String, JTextField[]> input, MultiEntryPanel panel) {
		System.out.println("reached");
		int response = JOptionPane.showConfirmDialog(frame, panel,
				"Please Enter and Confirm Values for " + CompanyController.getStocks().get(row).getTicker(),
				JOptionPane.OK_CANCEL_OPTION);
		if (response == JOptionPane.OK_OPTION) {
			input = panel.getInput();
			return input;
		} else {
			return new HashMap<String, JTextField[]>();
		}

	}

	static void singleEntry(String name, int row, ClickEvent e, MainFrame mainFrame) {
		long conversion = 0;
		double convert = 0.0;
		String entry = "";

		if (name.equals("Shares Outstanding")) {

			entry = JOptionPane.showInputDialog(e.getColumnName(),
					CompanyController.getStocks().get(e.getRow()).getSharesOut());
			if (entry != null && (entry.length() > 0)) {
				conversion = tryParse(name, row, entry, mainFrame, conversion);
				CompanyController.getStocks().get(row).setSharesOut(conversion);
			}
		} else if (name.equals("Sector")) {
			entry = JOptionPane.showInputDialog(e.getColumnName(),
					CompanyController.getStocks().get(e.getRow()).getSector());
			if (entry != null && (entry.length() > 0)) {
				CompanyController.getStocks().get(row).setSector(entry);
			}
		} else if (name.equals("Industry")) {
			entry = JOptionPane.showInputDialog(e.getColumnName(),
					CompanyController.getStocks().get(e.getRow()).getIndustry());
			if (entry != null && (entry.length() > 0)) {
				CompanyController.getStocks().get(row).setIndustry(entry);
			}
		} else if (name.contains("Company N")) {
			entry = JOptionPane.showInputDialog(e.getColumnName(),
					CompanyController.getStocks().get(e.getRow()).getName());
			if (entry != null && (entry.length() > 0)) {
				CompanyController.getStocks().get(row).setName(entry);
			}
		} else if (name.contains("Cash o")) {
			entry = JOptionPane.showInputDialog(e.getColumnName(),
					CompanyController.getStocks().get(e.getRow()).getCOH());
			if (entry != null && (entry.length() > 0)) {
				conversion = tryParse(name, row, entry, mainFrame, conversion);
				CompanyController.getStocks().get(row).setCOH(conversion);
			}
		} else if (name.contains("Desired Margin")) {
			entry = JOptionPane.showInputDialog(e.getColumnName(),
					CompanyController.getStocks().get(e.getRow()).getMarginOfSafety());
			if (entry != null && (entry.length() > 0)) {
				convert = tryParse(name, row, entry, mainFrame, convert);
				CompanyController.getStocks().get(row).setMarginOfSafety(convert);
				CompanyController.recalculate(CompanyController.getStocks().get(row).getTicker());
			}
		}
	}

//	sets failed conversion to 1 to avoid dividing by zero accidentally
	private static long tryParse(String name, int row, String entry, MainFrame mainFrame, long conversion) {
		try {
			conversion = Long.valueOf(entry);
		} catch (NumberFormatException e) {
			mainFrame.numberFormatError();
			conversion = 1L;
		}
		return conversion;
	}

//	sets failed conversion to 1 to avoid dividing by zero accidentally
	private static double tryParse(String name, int row, String entry, MainFrame mainFrame, double convert) {
		if (name.startsWith(".")) {
			StringBuilder sb = new StringBuilder();
			sb.append("0").append(name);
			name = sb.toString();
		}
		try {
			convert = Double.valueOf(entry);
		} catch (NumberFormatException e) {
			mainFrame.numberFormatError();
			convert = 1.0;
		}
		return convert;
	}

}
