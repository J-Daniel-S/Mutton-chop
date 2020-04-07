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

		CompanyController controller = CompanyController.getInstance();

		tabs = new JTabbedPane();
		toolBar = new ToolBar(this);
		infoTablePanel = new InfoTablePanel();
		finTablePanel = new FinancialTablePanel();
		retTablePanel = new ReturnTablePanel();

		textPanel = new TextPanel();

		tabs.insertTab("Information", null, infoTablePanel, "General Information About the Company", 0);
		tabs.insertTab("Financial Efficiency", null, finTablePanel, "Financial ratios", 1);
		tabs.insertTab("Return On Investment", null, retTablePanel, "Prospective returns on investment", 2);

		setJMenuBar(menuBar(controller));

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
				JOptionPane.showMessageDialog(MainFrame.this, "Values left blank will be treated as zeros");
				MultiEntryPanel multi = new MultiEntryPanel(e.getRow(), this);
				HashMap<String, JTextField[]> input = DataEntryHandler.multiEntry(e.getColumnName(), e.getRow(),
						MainFrame.this, multi);
				if (!input.isEmpty()) {
					try {
						HashMap<String, List<String>> output = getInputValues(input, e.getRow());
						CompanyController.manualValues(e.getRow(), output);
					} catch (NullPointerException e2) {
						textPanel.addText("empty form");
						e2.printStackTrace();
					} catch (NumberFormatException e3) {
						textPanel.addText("Ensure proper number format and try again");
						e3.printStackTrace();
					}
					multi.setListener((ClickEvent err) -> {
						textPanel.addText(err.getText());
					});
				}
				break;
			}
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		}

	}

	private HashMap<String, List<String>> getInputValues(HashMap<String, JTextField[]> input, int row) {
		HashMap<String, List<String>> output = new HashMap<>();
		try {
			input.forEach(
					(k, v) -> output.put(k, Arrays.stream(v).map(text -> text.getText()).collect(Collectors.toList())));
		} catch (NullPointerException err) {
			err.printStackTrace();
		}
		return output;
	}

	private void checkEmptyField(String string) {// throws EmptyFieldException {
//		EmptyFieldException empty = new EmptyFieldException();
		if (string.isEmpty()) {
//			throw empty;
			string = "0";
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
							notFoundPane(ticker);
						}
					} catch (IOException e1) {
						cantConnectPane(ticker);
					}
				} else {
					JOptionPane.showMessageDialog(MainFrame.this, "The list already contains that stock");
				}

			}
		});
	}

	private void cantConnectPane(String ticker) {
		int action = JOptionPane.showConfirmDialog(MainFrame.this, "Try again later", "Cannot Connect to Internet",
				JOptionPane.CANCEL_OPTION);
		if (action == JOptionPane.YES_OPTION) {
			TableController.setData(CompanyController.getStocks(), infoTablePanel, finTablePanel, retTablePanel);
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		}
	}

	private void notFoundPane(String ticker) {
		int action = JOptionPane.showConfirmDialog(MainFrame.this, "Check ticker and try again", "Company Not Found",
				JOptionPane.CANCEL_OPTION);
		if (action == JOptionPane.YES_OPTION) {
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

	private JMenuBar menuBar(CompanyController controller) {
		JMenuBar menuBar = new JMenuBar();

		JMenu sort = new JMenu("Sort Stocks by...");
		buildSortMenu(sort);

//		JMenu manual = new JMenu("Enter Data Manually for...");
//		buildManualMenu(manual);

		JMenu expectedReturn = new JMenu("Expected Return on Investment...");
		buildReturnMenu(expectedReturn, controller);

		menuBar.add(sort);
//		menuBar.add(manual);
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
			CompanyController.sortByTicker(this);
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		});

		price.addActionListener((ActionEvent e) -> {
			CompanyController.sortByPrice(this);
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		});

		sector.addActionListener((ActionEvent e) -> {
			CompanyController.sortBySector(this);
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		});

		industry.addActionListener((ActionEvent e) -> {
			CompanyController.sortByIndustry(this);
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		});

		mcap.addActionListener((ActionEvent e) -> {
			CompanyController.sortByMCap(this);
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		});

		croic.addActionListener((ActionEvent e) -> {
			CompanyController.sortByCroic(this);
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		});

		prof.addActionListener((ActionEvent e) -> {
			CompanyController.sortByProfitMargin(this);
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		});

		avgProf.addActionListener((ActionEvent e) -> {
			CompanyController.sortByAvgProfMargin(this);
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		});

		roa.addActionListener((ActionEvent e) -> {
			CompanyController.sortByROnAssets(this);
			;
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		});

		roe.addActionListener((ActionEvent e) -> {
			CompanyController.sortByROnEquity(this);
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		});

		fcf.addActionListener((ActionEvent e) -> {
			CompanyController.sortByFCFGrowth(this);
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		});

		avgfcf.addActionListener((ActionEvent e) -> {
			CompanyController.sortByAvgFcfGrowth(this);
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		});

		coh.addActionListener((ActionEvent e) -> {
			CompanyController.sortByCOH(this);
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		});

		eqdt.addActionListener((ActionEvent e) -> {
			CompanyController.sortByEquityDebtRatio(this);
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		});

		cohdt.addActionListener((ActionEvent e) -> {
			CompanyController.sortByCashDebtRatio(this);
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		});

		yield.addActionListener((ActionEvent e) -> {
			CompanyController.sortByCashYield(this);
			TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
		});

		fairValue.addActionListener((ActionEvent e) -> {
			CompanyController.sortByFairValue(this);
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

//	private void buildManualMenu(JMenu manual) {
//		String[] tickers = CompanyController.getStocks().stream().map(s -> s.getTicker()).toArray(String[]::new);
//		for (int i = 0; i < tickers.length; i++) {
//			JMenuItem item = new JMenuItem(tickers[i]);
////			addManualEntryListenerPanel(i, item);
//			manual.add(item);
//		}
//	}

	private void addManualEntryListenerPanel(int i, JMenuItem item) {
		int row = i;
		item.addActionListener((ActionEvent e) -> {
			JOptionPane.showMessageDialog(MainFrame.this, "Values left blank will be treated as zeros");
			MultiEntryPanel multi = new MultiEntryPanel(row, this);
			HashMap<String, JTextField[]> input = DataEntryHandler.multiEntry("menu entry", row, MainFrame.this, multi);
			try {
				HashMap<String, List<String>> output = getInputValues(input, row);
				CompanyController.manualValues(row, output);
			} catch (NullPointerException e2) {
				textPanel.addText("empty form");
				e2.printStackTrace();
			} catch (NumberFormatException e3) {
				textPanel.addText("Ensure proper number format and try again");
				e3.printStackTrace();
			}
			multi.setListener((ClickEvent err) -> {
				textPanel.addText(err.getText());
			});
		});
	}

	private void buildReturnMenu(JMenu expectedReturn, CompanyController controller) {
		JMenuItem six = new JMenuItem("6%");
		JMenuItem seven = new JMenuItem("7%");
		JMenuItem eight = new JMenuItem("8%");
		JMenuItem nine = new JMenuItem("9%");
		JMenuItem ten = new JMenuItem("10%");
		JMenuItem eleven = new JMenuItem("11%");
		JMenuItem twelve = new JMenuItem("12%");
		JMenuItem thirteen = new JMenuItem("13%");
		JMenuItem fourteen = new JMenuItem("14%");
		JMenuItem fifteen = new JMenuItem("15%");
		JMenuItem twenty = new JMenuItem("20%");

		six.addActionListener((ActionEvent e) -> {
			controller.setReturnOnInvestment("6");
		});
		seven.addActionListener((ActionEvent e) -> {
			controller.setReturnOnInvestment("7");
		});
		eight.addActionListener((ActionEvent e) -> {
			controller.setReturnOnInvestment("8");
		});
		nine.addActionListener((ActionEvent e) -> {
			controller.setReturnOnInvestment("9");
		});
		ten.addActionListener((ActionEvent e) -> {
			controller.setReturnOnInvestment("10");
		});
		eleven.addActionListener((ActionEvent e) -> {
			controller.setReturnOnInvestment("11");
		});
		twelve.addActionListener((ActionEvent e) -> {
			controller.setReturnOnInvestment("12");
		});
		thirteen.addActionListener((ActionEvent e) -> {
			controller.setReturnOnInvestment("13");
		});
		fourteen.addActionListener((ActionEvent e) -> {
			controller.setReturnOnInvestment("14");
		});
		fifteen.addActionListener((ActionEvent e) -> {
			controller.setReturnOnInvestment("15");
		});
		twenty.addActionListener((ActionEvent e) -> {
			controller.setReturnOnInvestment("20");
		});

		expectedReturn.add(six);
		expectedReturn.add(seven);
		expectedReturn.add(eight);
		expectedReturn.add(nine);
		expectedReturn.add(ten);
		expectedReturn.add(eleven);
		expectedReturn.add(twelve);
		expectedReturn.add(thirteen);
		expectedReturn.add(fourteen);
		expectedReturn.add(fifteen);
		expectedReturn.add(twenty);

	}

	public void tickerChange() {
		JOptionPane.showMessageDialog(MainFrame.this, "Cannot change ticker. Remove and add again.");
	}

	public void numberFormatError() {
		JOptionPane.showMessageDialog(MainFrame.this,
				"Cannot convert entry to appropriate number type. Please try again or resynch with the web database");
	}

//	public int getReturnOnInvestment() {
//		return this.returnOnInvestment;
//	}
}