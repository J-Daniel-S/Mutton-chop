package companyValueGui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import companyValueController.CompanyController;

public class MultiEntryPanel extends JPanel {

	private ClickListener listener;
	private HashMap<String, JTextField[]> input;

	public MultiEntryPanel(int row, MainFrame mainFrame) {
		input = new HashMap<>();
		layoutComponents(row, mainFrame);
		setSize(800, 300);
	}

	private void layoutComponents(int row, MainFrame mainFrame) {

		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

///////////////		First row   /////////////////		
		int yPlace = 0;
		gc.gridy = yPlace;

		int xPlace = 0;

		JLabel name = new JLabel("Company Name: ");

		gc.weightx = 0.5;
		gc.weighty = 1;

		gc.gridx = xPlace;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		int orient = GridBagConstraints.SOUTHEAST;

		gc.anchor = orient;

		add(name, gc);
		xPlace++;

		//////////
		JTextField nameField = new JTextField(12);

		gc.weightx = 0.5;
		gc.weighty = 1;

		gc.gridx = xPlace;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		add(nameField, gc);
		xPlace++;

		addToMap("companyName", nameField);

		//////////

		gc.weightx = 0.5;
		gc.weighty = 1;

		gc.gridx = xPlace;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = orient;

		add(new JLabel("Cash On Hand:"), gc);
		xPlace++;

		//////////
		JTextField cashField = new JTextField(12);

		gc.weightx = 0.5;
		gc.weighty = 1;

		gc.gridx = xPlace;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		add(cashField, gc);
		xPlace++;

		addToMap("coh", cashField);

		//////////

		gc.weightx = 0.5;
		gc.weighty = 1;

		gc.gridx = xPlace;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = orient;

		add(new JLabel("Sector:"), gc);
		xPlace++;

		//////////
		JTextField sectorField = new JTextField(12);

		gc.weightx = 0.5;
		gc.weighty = 1;

		gc.gridx = xPlace;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		add(sectorField, gc);
		xPlace++;

		addToMap("sector", sectorField);

///////////////		Second row   /////////////////
		yPlace++;
		gc.gridy = yPlace;
		//////////

		xPlace = 0;

		gc.weightx = 0.5;
		gc.weighty = 1;

		gc.gridx = xPlace;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = orient;

		add(new JLabel("Industry:"), gc);
		xPlace++;

		//////////
		JTextField industryField = new JTextField(12);

		gc.weightx = 1;
		gc.weighty = 1;

		gc.gridx = xPlace;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		add(industryField, gc);
		xPlace++;

		addToMap("industry", industryField);

		//////////

		gc.weightx = 1;
		gc.weighty = 1;

		gc.gridx = xPlace;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = orient;

		add(new JLabel("Outstanding Shares:"), gc);
		xPlace++;

		//////////
		JTextField sharesField = new JTextField(12);

		gc.weightx = 1;
		gc.weighty = 1;

		gc.gridx = xPlace;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		add(sharesField, gc);
		xPlace++;

		addToMap("sharesOut", sharesField);

		//////////

		gc.weightx = 1;
		gc.weighty = 1;

		gc.gridx = xPlace;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = orient;

		add(new JLabel("Date of 10K:"), gc);
		xPlace++;

		//////////
		JTextField dateField = new JTextField(12);

		gc.weightx = 1;
		gc.weighty = 1;

		gc.gridx = xPlace;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		add(dateField, gc);
		xPlace++;

		addToMap("date", dateField);

///////////////		Mos row   /////////////////
		yPlace++;
		gc.gridy = yPlace;
		//////////

		xPlace = 0;

		gc.weightx = 0.5;
		gc.weighty = 1;

		gc.gridx = xPlace;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = orient;

		add(new JLabel("Margin Of Safety:"), gc);
		xPlace++;

		//////////
		JTextField mosField = new JTextField(12);

		gc.weightx = 1;
		gc.weighty = 1;

		gc.gridx = xPlace;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		add(mosField, gc);

		addToMap("marginOfSafety", mosField);

///////////////		Labels row   /////////////////
		yPlace++;
		gc.gridy = yPlace;
		JLabel[] yearLables = yearLables();

		//////
		xPlace = 0;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.gridx = xPlace;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		int south = GridBagConstraints.SOUTH;

		gc.anchor = south;

		add(yearLables[xPlace], gc);
		xPlace++;

		//////
		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = south;

		add(yearLables[xPlace], gc);
		xPlace++;

		//////
		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = south;

		add(yearLables[xPlace], gc);
		xPlace++;

		//////
		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = south;

		add(yearLables[xPlace], gc);
		xPlace++;

		//////
		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = south;

		add(yearLables[xPlace], gc);
		xPlace++;

		//////
		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = south;

		add(yearLables[xPlace], gc);
		xPlace++;

///////////////		Dividends per share row   /////////////////
		yPlace++;
		gc.gridy = yPlace;

		//////////

		xPlace = 0;

		gc.weightx = 0.5;
		gc.weighty = 1;

		gc.gridx = xPlace;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		int labelOrient = GridBagConstraints.EAST;

		gc.anchor = labelOrient;

		add(new JLabel("Dividends per share: "), gc);
		xPlace++;

		//////////
		JTextField[] divField = new JTextField[2];
		divField[0] = new JTextField(12);
		divField[1] = new JTextField(12);

		gc.weightx = 1;
		gc.weighty = 1;

		gc.gridx = xPlace;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		add(divField[0], gc);
		xPlace++;

		divField[1] = new JTextField(12);

		gc.weightx = 1;
		gc.weighty = 1;

		gc.gridx = xPlace;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		add(divField[1], gc);
		xPlace++;

		addToMap("dividends per share", divField);
///////////////		Income row   /////////////////
		yPlace++;
		gc.gridy = yPlace;

		gc.weighty = 0.7;

		JLabel income = new JLabel("Income Statement");
		income.setForeground(Color.GRAY);

		gc.fill = GridBagConstraints.NONE;

		gc.gridx = 3;

		gc.anchor = GridBagConstraints.CENTER;

		add(income, gc);

///////////////		Revenue row   /////////////////
		yPlace++;
		gc.gridy = yPlace;

		JTextField[] revFields = textFields();
		JLabel rev = new JLabel("Revenue: ");

		xPlace = 0;

		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		orient = GridBagConstraints.CENTER;

		gc.anchor = labelOrient;

		add(rev, gc);
		xPlace++;

		////////
		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = orient;

		add(revFields[xPlace - 1], gc);
		xPlace++;

		////////
		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = orient;

		add(revFields[xPlace - 1], gc);
		xPlace++;

		////////
		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = orient;

		add(revFields[xPlace - 1], gc);
		xPlace++;

		////////
		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = orient;

		add(revFields[xPlace - 1], gc);
		xPlace++;

		////////
		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = orient;

		add(revFields[xPlace - 1], gc);
		xPlace++;

		addToMap("revenue", revFields);

///////////////		Cash flow row   /////////////////
		yPlace++;
		gc.gridy = yPlace;

		gc.weighty = 0.7;

		JLabel cashFlow = new JLabel("Cash Flow");
		cashFlow.setForeground(Color.GRAY);

		gc.fill = GridBagConstraints.NONE;

		gc.gridx = 3;

		add(cashFlow, gc);

///////////////		Cash flow from ops row   /////////////////
		yPlace++;
		gc.gridy = yPlace;

		JTextField[] cashFields = textFields();
		JLabel cash = new JLabel("Net Cash From Operations: ");

		xPlace = 0;

		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = labelOrient;

		add(cash, gc);
		xPlace++;

		////////
		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = orient;

		add(cashFields[xPlace - 1], gc);
		xPlace++;

		////////
		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = orient;

		add(cashFields[xPlace - 1], gc);
		xPlace++;

		////////
		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = orient;

		add(cashFields[xPlace - 1], gc);
		xPlace++;

		////////
		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = orient;

		add(cashFields[xPlace - 1], gc);
		xPlace++;

		////////
		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = orient;

		add(cashFields[xPlace - 1], gc);
		xPlace++;

		addToMap("cashFlow", cashFields);

///////////////		Net Income row   /////////////////
		yPlace++;
		gc.gridy = yPlace;

		JTextField[] netIncomeFields = textFields();
		JLabel earn = new JLabel("Net Income (Earnings): ");

		xPlace = 0;

		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = labelOrient;

		add(earn, gc);
		xPlace++;

		////////
		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = orient;

		add(netIncomeFields[xPlace - 1], gc);

		addToMap("netIncome", netIncomeFields);

///////////////		Capital Expenditures row   /////////////////
		yPlace++;
		gc.gridy = yPlace;

		JTextField[] capexFields = textFields();
		JLabel cap = new JLabel("Capital Expenditures/investments: ");

		xPlace = 0;

		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = labelOrient;

		add(cap, gc);
		xPlace++;

		////////
		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = orient;

		add(capexFields[xPlace - 1], gc);
		xPlace++;

		////////
		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = orient;

		add(capexFields[xPlace - 1], gc);
		xPlace++;

		////////
		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = orient;

		add(capexFields[xPlace - 1], gc);
		xPlace++;

		////////
		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = orient;

		add(capexFields[xPlace - 1], gc);
		xPlace++;

		////////
		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = orient;

		add(capexFields[xPlace - 1], gc);
		xPlace++;

		addToMap("capex", capexFields);

///////////////		Dividends row   /////////////////
		yPlace++;
		gc.gridy = yPlace;

		JTextField[] divFields = textFields();
		JLabel div = new JLabel("Dividends paid: ");

		xPlace = 0;

		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = labelOrient;

		add(div, gc);
		xPlace++;

		////////
		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = orient;

		add(divFields[xPlace - 1], gc);

		addToMap("div", divFields);

///////////////		buybacks row   /////////////////
		yPlace++;
		gc.gridy = yPlace;

		JTextField[] bbFields = textFields();
		JLabel buy = new JLabel("Sale (buybacks) of stock: ");

		xPlace = 0;

		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = labelOrient;

		add(buy, gc);
		xPlace++;

		////////
		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = orient;

		add(bbFields[xPlace - 1], gc);
		xPlace++;

		addToMap("buybacks", bbFields);

///////////////		Balance sheet row   /////////////////
		yPlace++;
		gc.gridy = yPlace;

		gc.weighty = 0.7;

		JLabel balance = new JLabel("Balance Sheet");
		balance.setForeground(Color.GRAY);

		gc.fill = GridBagConstraints.NONE;

		gc.gridx = 3;

		add(balance, gc);

///////////////		Assets row   /////////////////
		yPlace++;
		gc.gridy = yPlace;

		JTextField[] assetFields = textFields();
		JLabel assets = new JLabel("Total Assets: ");

		xPlace = 0;

		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = labelOrient;

		add(assets, gc);
		xPlace++;

		////////
		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = orient;

		add(assetFields[xPlace - 1], gc);

		addToMap("assets", assetFields);

///////////////		Goodwill and Intangibles row   /////////////////
		yPlace++;
		gc.gridy = yPlace;

		JTextField[] gwFields = textFields();
		JLabel gw = new JLabel("Goodwill and Intangible Assets: ");

		xPlace = 0;

		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = labelOrient;

		add(gw, gc);
		xPlace++;

		////////
		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = orient;

		add(gwFields[xPlace - 1], gc);

		addToMap("goodwill", gwFields);

///////////////		Equity row   /////////////////
		yPlace++;
		gc.gridy = yPlace;

		JTextField[] equityFields = textFields();
		JLabel equity = new JLabel("Total Shareholder Equity: ");

		xPlace = 0;

		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = labelOrient;

		add(equity, gc);
		xPlace++;

		////////
		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = orient;

		add(equityFields[xPlace - 1], gc);
		xPlace++;

		////////
		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = orient;

		add(equityFields[xPlace - 1], gc);

		xPlace++;

		////////
		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = orient;

		add(equityFields[xPlace - 1], gc);

		addToMap("equity", equityFields);

///////////////		Debt row   /////////////////
		yPlace++;
		gc.gridy = yPlace;

		JTextField[] debtFields = textFields();
		JLabel debt = new JLabel("Total Long-Term Debt: ");

		xPlace = 0;

		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = labelOrient;

		add(debt, gc);
		xPlace++;

		////////
		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = orient;

		add(debtFields[xPlace - 1], gc);
		xPlace++;

		////////
		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = orient;

		add(debtFields[xPlace - 1], gc);
		xPlace++;

		////////
		gc.gridx = xPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = orient;

		add(debtFields[xPlace - 1], gc);

		addToMap("debt", debtFields);

///////////////		Hyperlink and MOS row   /////////////////
		yPlace++;
		xPlace = 2;

		String url = "https://finance.yahoo.com/quote/" + CompanyController.getStocks().get(row).getTicker()
				+ "/financials?p=" + CompanyController.getStocks().get(row).getTicker();

		String text = "Click for Data";

		JLabel link = new JLabel(text);

		setHyperlink(mainFrame, url, link, text);

		gc.gridx = xPlace;
		gc.gridy = yPlace;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;

		gc.insets = setInsets();

		gc.anchor = GridBagConstraints.CENTER;

		add(link, gc);
		xPlace++;

	}

	private void addToMap(String key, JTextField field) {
		JTextField[] fields = { field };
		input.put(key, fields);
	}

	private void addToMap(String key, JTextField[] fields) {
		input.put(key, fields);
	}

	private void setHyperlink(MainFrame mainFrame, String url, JLabel link, String text) {
		link.setForeground(Color.BLUE.darker());
		link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		link.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI(url));
				} catch (IOException | URISyntaxException err) {
					ClickEvent click = new ClickEvent(mainFrame, "Cannot reach Yahoo Finance");
					if (listener != null) {
						listener.clickEventOccurred(click);
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				link.setText("<html><a href=''>" + text + "</a></html>");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				link.setText(text);
			}
		});
	}

	public ClickListener getListener() {
		return listener;
	}

	public void setListener(ClickListener listener) {
		this.listener = listener;
	}

	private JLabel[] yearLables() {
		JLabel[] labels = new JLabel[6];
		String text;

		for (int i = 0; i < labels.length; i++) {
			if (i == 0) {
				text = "Data for Year:";
			} else if (i == 1) {
				text = "Current Year";
			} else {
				text = "Year prior";
			}
			JLabel label = new JLabel(text);
			label.setForeground(Color.GRAY);
			labels[i] = label;
		}

		return labels;
	}

	private JTextField[] textFields() {
		JTextField[] fields = new JTextField[5];

		for (int i = 0; i < fields.length; i++) {
			JTextField field = new JTextField(12);
			fields[i] = field;
		}
		return fields;
	}

	private Insets setInsets() {
		return new Insets(4, 2, 4, 2);
	}

	public HashMap<String, JTextField[]> getInput() {
		return input;
	}

}