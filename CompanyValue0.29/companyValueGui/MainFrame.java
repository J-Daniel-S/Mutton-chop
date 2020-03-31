package companyValueGui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import companyValueController.CompanyController;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1318460381891989562L;
	private ToolBar toolBar;
	private JTabbedPane tabs;
	private InfoTablePanel infoTablePanel;
	private FinancialTablePanel finTablePanel;
	private ReturnTablePanel retTablePanel;
	final ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);

	private TextPanel textPanel;

	public MainFrame() {
		super("Company Value and Comparison");

		tabs = new JTabbedPane();
		toolBar = new ToolBar();
		infoTablePanel = new InfoTablePanel();
		finTablePanel = new FinancialTablePanel();
		retTablePanel = new ReturnTablePanel();

		textPanel = new TextPanel();

		tabs.insertTab("Information", null, infoTablePanel, "General Information About the Company", 0);
		tabs.insertTab("Financial Efficiency", null, finTablePanel, "Financial ratios", 1);
		tabs.insertTab("Return On Investment", null, retTablePanel, "", 2);

		setJMenuBar(menuBar());

		tickerAddedInToolbar();
		popupClicked();

		setLayout(new BorderLayout());

		add(toolBar, BorderLayout.NORTH);
		add(tabs, BorderLayout.CENTER);
		add(textPanel, BorderLayout.SOUTH);

		setMinimumSize(new Dimension(500, 400));
		setSize(1000, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		refresh();
	}

	private void refresh() {
		final Runnable u = () -> {
			if (!CompanyController.getStocks().isEmpty()) {
				CompanyController.updatePrice();
				TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
			}
		};
		final ScheduledFuture<?> updateHandler = schedule.scheduleAtFixedRate(u, 10, 10, TimeUnit.SECONDS);
	}

	private void addToTextPanel(String text) {
		textPanel.addText(text);
	}

	private void popupClicked() {
		TableController.setListener(infoTablePanel, (ClickEvent e) -> {
			if (e.getText().equals("manual")) {
				entryPopup(infoTablePanel, e);
			} else {
				addToTextPanel(e.getText());
			}
		});
		TableController.setListener(finTablePanel, (ClickEvent e) -> {
			if (e.getText().equals("manual")) {
				entryPopup(finTablePanel, e);
			} else {
				addToTextPanel(e.getText());
			}
		});
		TableController.setListener(retTablePanel, (ClickEvent e) -> {
			if (e.getText().equals("manual")) {
				entryPopup(retTablePanel, e);
			} else {
				addToTextPanel(e.getText());
			}
		});

		TableController.setListener(infoTablePanel, (RemoveEvent e) -> {
			if (e.isRemove() == true) {
				CompanyController.removeStock(e.getTicker());
				TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
			}
		});
		TableController.setListener(finTablePanel, (RemoveEvent e) -> {
			if (e.isRemove() == true) {
				CompanyController.removeStock(e.getTicker());
				TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
			}
		});
		TableController.setListener(retTablePanel, (RemoveEvent e) -> {
			if (e.isRemove() == true) {
				CompanyController.removeStock(e.getTicker());
				TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
			}
		});
	}

	private void entryPopup(BaseTablePanel panel, ClickEvent e) {
		if (e.getColumnName().equals("err")) {
			JOptionPane.showMessageDialog(MainFrame.this, "Column Selection Error.  Please try again.");
		} else if (e.getColumnName().equals("Ticker")) {
			tickerChange();
		} else {
			String column = e.getColumnName();
			int response = DataEntryHandler.checkColumnName(e.getColumnName(), e.getRow(), column);
			switch (response) {
			case 1:
				String entry = e.getColumnName();
				DataEntryHandler.singleEntry(e.getColumnName(), e.getRow(), e, MainFrame.this);
				break;
			case 2:
				MultiEntryPanel multi = new MultiEntryPanel(e.getRow(), this);
				HashMap<String, JTextField[]> input = DataEntryHandler.multiEntry(e.getColumnName(), e.getRow(),
						MainFrame.this, multi);
				try {
					HashMap<String, List<String>> output = getInputValues(input, e.getRow());
				} catch (NullPointerException e2) {
					System.out.println("empty form");
				}
				multi.setListener((ClickEvent err) -> {
					textPanel.addText(err.getText());
				});
				break;
			}
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		}

	}

	private HashMap<String, List<String>> getInputValues(HashMap<String, JTextField[]> input, int row) {
		MultiEntryPanel multi = new MultiEntryPanel(row, this);
		HashMap<String, List<String>> output = new HashMap<>();
		boolean empty = false;
		try {
			input.forEach(
					(k, v) -> output.put(k, Arrays.stream(v).map(text -> text.getText()).collect(Collectors.toList())));

			output.forEach((k, v) -> {
				try {
					checkEmptyField(v.stream().toString());
				} catch (EmptyFieldException e) {
					JOptionPane.showMessageDialog(MainFrame.this, "Please don't leave cells empty");
					DataEntryHandler.multiNullValues(row, MainFrame.this, input, multi);
					// currently thinking about making each cell contain a zero and then just
					// checking to make sure if the user is okay with zero values
					// I'll have to write some logic that excludes zero values from calculation in
					// Evaluator.
				}
			});

		} catch (NullPointerException err) {
			err.printStackTrace();
		}
		return output;
	}

	private void checkEmptyField(String string) throws EmptyFieldException {
		EmptyFieldException empty = new EmptyFieldException();
		if (string.equals("")) {
			throw empty;
		} else {
			return;
		}
	}

//	generates the event that results in the stock being added to the table
	private void tickerAddedInToolbar() {
		toolBar.setListener((AddEvent e) -> {
			String ticker = e.getTicker().toUpperCase();
			String name = null;
			HashMap<String, String> profile;

			if (!ticker.isEmpty()) {
				if (CompanyController.checkTicker(ticker) == false) {
					try {
						profile = CompanyController.getStockDetails(ticker);
						name = profile.get("companyName");
						if (name != null) {
							foundConfirmationPane(name, profile);
						} else {
							notFoundConfirmationPane(ticker);
						}
					} catch (IOException e1) {
						cantConnectConfirmationPane(ticker);
					}
				} else {
					JOptionPane.showMessageDialog(MainFrame.this, "The list already contains that stock");
				}

			}
		});
	}

	private void cantConnectConfirmationPane(String ticker) {
		int action = JOptionPane.showConfirmDialog(MainFrame.this, "Add " + ticker + " to table anyway?",
				"Cannot Connect to Internet", JOptionPane.YES_NO_OPTION);
		if (action == JOptionPane.YES_OPTION) {
			CompanyController.addStock(ticker);
			TableController.setData(CompanyController.getStocks(), infoTablePanel, finTablePanel, retTablePanel);
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		}
	}

	private void notFoundConfirmationPane(String ticker) {
		int action = JOptionPane.showConfirmDialog(MainFrame.this, "Add " + ticker + " to table anyway?",
				"Company Not Found", JOptionPane.YES_NO_OPTION);
		if (action == JOptionPane.YES_OPTION) {
			CompanyController.addStock(ticker);
			TableController.setData(CompanyController.getStocks(), infoTablePanel, finTablePanel, retTablePanel);
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		}
	}

	private void foundConfirmationPane(String name, HashMap<String, String> profile) {
		int action = JOptionPane.showConfirmDialog(MainFrame.this, "Add " + name + " to table?",
				"Confirmation: Company found", JOptionPane.YES_NO_OPTION);
		if (action == JOptionPane.YES_OPTION) {
			CompanyController.addStock(profile);
			TableController.setData(CompanyController.getStocks(), infoTablePanel, finTablePanel, retTablePanel);
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
			MainFrame.this.addToTextPanel(
					CompanyController.getStocks().get(CompanyController.getStocks().size() - 1).getDescription());
			if (CompanyController.getStocks().get(CompanyController.getStocks().size() - 1).getDescription() == null) {
				MainFrame.this.addToTextPanel("No description from API");
			}
		}
	}

	private JMenuBar menuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu sort = new JMenu("Sort Stocks by...");
		buildSortMenu(sort);

		JMenu manual = new JMenu("Enter Data Manually for...");
		buildManualMenu(manual);

		JMenu expectedReturn = new JMenu("Expected Return on Investment...");
		buildReturnMenu(expectedReturn);

		menuBar.add(sort);
		menuBar.add(manual);
		menuBar.add(expectedReturn);

		return menuBar;
	}

	private void buildSortMenu(JMenu sort) {
		JMenuItem ticker = new JMenuItem("Ticker Symbol");
		JMenuItem price = new JMenuItem("price");
		JMenuItem sector = new JMenuItem("Sector");
		JMenuItem industry = new JMenuItem("Industry");
		JMenuItem mcap = new JMenuItem("Market Capitalization");
		JMenuItem croic = new JMenuItem("CROIC");
		JMenuItem prof = new JMenuItem("Profit Margin");
		JMenuItem avgProf = new JMenuItem("Average Profit Margin");
		JMenuItem roa = new JMenuItem("Return on Assets");
		JMenuItem roe = new JMenuItem("Return on Equity");
		JMenuItem fcf = new JMenuItem("Current FCF Growth");
		JMenuItem avgfcf = new JMenuItem("Average FCF Growth");
		JMenuItem coh = new JMenuItem("Cash On Hand");
		JMenuItem eqdt = new JMenuItem("Equity/Debt Ratio");
		JMenuItem cohdt = new JMenuItem("Cash On Hand/Debt Ratio");
		JMenuItem yield = new JMenuItem("Cash Yield");
		JMenuItem fairValue = new JMenuItem("Fair Value");

		ticker.addActionListener((ActionEvent e) -> {
			CompanyController.sortByTicker();
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		});

		price.addActionListener((ActionEvent e) -> {
			CompanyController.sortByPrice();
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		});

		sector.addActionListener((ActionEvent e) -> {
			CompanyController.sortBySector();
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		});

		industry.addActionListener((ActionEvent e) -> {
			CompanyController.sortByIndustry();
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		});

		mcap.addActionListener((ActionEvent e) -> {
			CompanyController.sortByMCap();
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		});

		croic.addActionListener((ActionEvent e) -> {
			CompanyController.sortByCroic();
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		});

		prof.addActionListener((ActionEvent e) -> {
			CompanyController.sortByProfitMargin();
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		});

		avgProf.addActionListener((ActionEvent e) -> {
			CompanyController.sortByAvgProfMargin();
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		});

		roa.addActionListener((ActionEvent e) -> {
			CompanyController.sortByROnAssets();
			;
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		});

		roe.addActionListener((ActionEvent e) -> {
			CompanyController.sortByROnEquity();
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		});

		fcf.addActionListener((ActionEvent e) -> {
			CompanyController.sortByFCFGrowth();
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		});

		avgfcf.addActionListener((ActionEvent e) -> {
			CompanyController.sortByAvgFcfGrowth();
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		});

		coh.addActionListener((ActionEvent e) -> {
			CompanyController.sortByCOH();
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		});

		eqdt.addActionListener((ActionEvent e) -> {
			CompanyController.sortByEquityDebtRatio();
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		});

		cohdt.addActionListener((ActionEvent e) -> {
			CompanyController.sortByCashDebtRatio();
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		});

		yield.addActionListener((ActionEvent e) -> {
			CompanyController.sortByCashYield();
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		});

		fairValue.addActionListener((ActionEvent e) -> {
			CompanyController.sortByFairValue();
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		});

		sort.add(ticker);
		sort.add(price);
		sort.add(sector);
		sort.add(industry);
		sort.add(mcap);
		sort.add(croic);
		sort.add(prof);
		sort.add(avgProf);
		sort.add(roa);
		sort.add(roe);
		sort.add(fcf);
		sort.add(avgfcf);
		sort.add(coh);
		sort.add(eqdt);
		sort.add(cohdt);
		sort.add(yield);
		sort.add(fairValue);
	}

	private void buildManualMenu(JMenu manual) {
//		System.out.println("I'll work on this next");
	}

	private void buildReturnMenu(JMenu expectedReturn) {
		// TODO Auto-generated method stub

	}

	public void tickerChange() {
		JOptionPane.showMessageDialog(MainFrame.this, "Cannot change ticker. Remove and add again.");
	}

	public void numberFormatError() {
		JOptionPane.showMessageDialog(MainFrame.this,
				"Cannot convert entry to appropriate number type. Please try again or resynch with the web database");
	}
}