package companyValueGui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

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

	private TextPanel textPanel;

	public MainFrame() {
		super("Company Value and Comparison");

		tabs = new JTabbedPane();
//		
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
		clicked();

		setLayout(new BorderLayout());

		add(toolBar, BorderLayout.NORTH);
		add(tabs, BorderLayout.CENTER);
		add(textPanel, BorderLayout.SOUTH);

		setMinimumSize(new Dimension(500, 400));
		setSize(1000, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void addToTextPanel(String text) {
		textPanel.addText(text);
	}

	private void clicked() {
		TableController.setListener(infoTablePanel, new ClickListener() {
			public void clickEventOccurred(ClickEvent e) {
				addToTextPanel(e.getText());
			}
		});
		TableController.setListener(finTablePanel, new ClickListener() {
			public void clickEventOccurred(ClickEvent e) {
				addToTextPanel(e.getText());
			}
		});
		TableController.setListener(retTablePanel, new ClickListener() {
			public void clickEventOccurred(ClickEvent e) {
				addToTextPanel(e.getText());
			}
		});
	}

//	generates the event that results in the stock being added to the table
//	This method currently interfaces directly with the db.  This will be fixed when I add the controller
	private void tickerAddedInToolbar() {
		toolBar.setListener(new TickerListener() {
			public void addEventOccurred(AddEvent e) {
				String ticker = e.getTicker().toUpperCase();
				String name = null;
				HashMap<String, String> profile;

				if (!ticker.isEmpty()) {
					try {
						profile = CompanyController.getStockDetails(ticker);
						name = profile.get("companyName");
						if (name != null) {
							int action = JOptionPane.showConfirmDialog(MainFrame.this, "Add " + name + " to table?",
									"Confirmation: Company found", JOptionPane.YES_NO_OPTION);
							if (action == JOptionPane.YES_OPTION) {
								CompanyController.addStock(profile);
								TableController.setData(CompanyController.getStocks(), infoTablePanel, finTablePanel,
										retTablePanel);
								TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
								MainFrame.this.addToTextPanel(CompanyController.getStocks()
										.get(CompanyController.getStocks().size() - 1).getDescription());
								if (CompanyController.getStocks().get(CompanyController.getStocks().size() - 1)
										.getDescription() == null) {
									MainFrame.this.addToTextPanel("No description from API");
								}
							}
						} else {
							int action = JOptionPane.showConfirmDialog(MainFrame.this,
									"Add " + ticker + " to table anyway?", "Company Not Found",
									JOptionPane.YES_NO_OPTION);
							if (action == JOptionPane.YES_OPTION) {
								CompanyController.addStock(ticker);
								TableController.setData(CompanyController.getStocks(), infoTablePanel, finTablePanel,
										retTablePanel);
								TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
							}
						}
					} catch (IOException e1) {
						int action = JOptionPane.showConfirmDialog(MainFrame.this,
								"Add " + ticker + " to table anyway?", "Cannot Connect to Internet",
								JOptionPane.YES_NO_OPTION);
						if (action == JOptionPane.YES_OPTION) {
							CompanyController.addStock(ticker);
							TableController.setData(CompanyController.getStocks(), infoTablePanel, finTablePanel,
									retTablePanel);
							TableController.refresh(infoTablePanel, finTablePanel, retTablePanel);
						}
					}
				}
			}

		});
	}

	private JMenuBar menuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu sort = new JMenu("Sort Stocks by...");
		JMenuItem ticker = new JMenuItem("Ticker Symbol");
		JMenuItem name = new JMenuItem("Company Name");
		JMenuItem price = new JMenuItem("price");
		JMenuItem sector = new JMenuItem("Sector");
		JMenuItem industry = new JMenuItem("Industry");

		sort.add(ticker);
		sort.add(name);
		sort.add(price);
		sort.add(sector);
		sort.add(industry);

		menuBar.add(sort);

		return menuBar;
	}
}