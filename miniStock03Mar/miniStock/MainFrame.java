package miniStock;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import miniStockModel.Stock;
import miniStockModel.StocksList;
import miniStockModel.WebReader;

public class MainFrame extends JFrame {

	private ToolBar toolBar;
	private StockTablePanel tablePanel;
	private StocksList stocks;
	private WebReader webReader;

	public MainFrame() {
		super("Who's Undervalued");

		tablePanel = new StockTablePanel();
		toolBar = new ToolBar();
		stocks = new StocksList();
		webReader = new WebReader();

		tickerAddedInToolbar();

		setLayout(new BorderLayout());

		add(toolBar, BorderLayout.NORTH);
		add(tablePanel, BorderLayout.CENTER);

		setMinimumSize(new Dimension(500, 400));
		setSize(1000, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

//	generates the event that results in the stock being added to the table
//	This method currently interfaces directly with the db.  This will be fixed when I add the controller
	private void tickerAddedInToolbar() {
		toolBar.setListener(new TickerListener() {
			public void addEventOccurred(AddEvent e) {
				String ticker = e.getTicker().toUpperCase();
				String name = null;
				HashMap<String, String> companyDetails;

				if (!ticker.isEmpty()) {
					try {
						companyDetails = webReader.getStockDetails(ticker);
						name = companyDetails.get("companyName");
						if (name != null) {
							int action = JOptionPane.showConfirmDialog(MainFrame.this, "Add " + name + " to table?",
									"Confirmation: Company found", JOptionPane.YES_NO_OPTION);
							if (action == JOptionPane.YES_OPTION) {
								stocks.addStock(companyDetails);
								List<Stock> stks = stocks.getStocks();
								tablePanel.setData(stks);
								tablePanel.refresh();
							}
						} else {
							int action = JOptionPane.showConfirmDialog(MainFrame.this,
									"Add " + ticker + " to table anyway?", "Company Not Found",
									JOptionPane.YES_NO_OPTION);
							if (action == JOptionPane.YES_OPTION) {
								stocks.addStock(ticker);
								List<Stock> stks = stocks.getStocks();
								tablePanel.setData(stks);
								tablePanel.refresh();
							}
						}
					} catch (IOException e1) {
						int action = JOptionPane.showConfirmDialog(MainFrame.this,
								"Add " + ticker + " to table anyway?", "Cannot Connect to Internet",
								JOptionPane.YES_NO_OPTION);
						if (action == JOptionPane.YES_OPTION) {
							stocks.addStock(ticker);
							List<Stock> stks = stocks.getStocks();
							tablePanel.setData(stks);
							tablePanel.refresh();
						}
					}
				}
			}

		});
	}

}