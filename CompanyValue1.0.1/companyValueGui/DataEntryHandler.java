package companyValueGui;

import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import companyValueController.CompanyController;

public class DataEntryHandler {
//	this class passes the hashmap from the data entry panes to the mainframe.  There shouldn't be any computing done here

	static int checkColumnName(String name, int row, String entry) {
//		returns 1 if a single entry is enough to fix the problem, 2 if multiple entries are needed
//		This is left over from the functionality that I removed.  I'l fix it
		if (name.equals("Market Capitalization") || name.contains("Current") || name.contains("Cash Yield")) {
			return 3;
		} else {
			return 2;
		}
	}

	static HashMap<String, JTextField[]> multiEntry(String name, int row, MainFrame frame, MultiEntryPanel panel) {
		HashMap<String, JTextField[]> input = new HashMap<>();
		UIManager.put("OptionPane.minimumSize", new Dimension(500, 350));
		input = multiOptionPane(row, frame, panel, input);
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
}