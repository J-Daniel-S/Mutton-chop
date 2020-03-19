package companyValueGui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import companyValueController.CompanyController;

public class MainFrame extends JFrame {

	private ToolBar toolBar;
	private StockTablePanel tablePanel;
	private TextPanel textPanel;

	private CompanyController controller;

	public MainFrame() {
		super("Who's Undervalued");

		toolBar = new ToolBar();
		tablePanel = new StockTablePanel();
		textPanel = new TextPanel();

		controller = new CompanyController();

		tickerAddedInToolbar();

		setLayout(new BorderLayout());

		add(toolBar, BorderLayout.NORTH);
		add(tablePanel, BorderLayout.CENTER);
		add(textPanel, BorderLayout.SOUTH);

		setMinimumSize(new Dimension(500, 400));
		setSize(1000, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void addToTextPanel(String text) {
		textPanel.addText(text);
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
						profile = controller.getStockDetails(ticker);
						name = profile.get("companyName");
						if (name != null) {
							int action = JOptionPane.showConfirmDialog(MainFrame.this, "Add " + name + " to table?",
									"Confirmation: Company found", JOptionPane.YES_NO_OPTION);
							if (action == JOptionPane.YES_OPTION) {
								controller.addStock(profile);
								tablePanel.setData(controller.getStocks());
								tablePanel.refresh();
								MainFrame.this
										.addToTextPanel(controller.getStocks()
												.get(controller.getStocks().size() - 1).getProfile().get("description"));
								if (controller.getStocks().get(
														controller.getStocks().size() - 1).getProfile().get("description")
												== null) {
									MainFrame.this.addToTextPanel("No description from API");
								}
//								System.out.println(stks.get(0).getCashFlowArray().length);
							}
						} else {
							int action = JOptionPane.showConfirmDialog(MainFrame.this,
									"Add " + ticker + " to table anyway?", "Company Not Found",
									JOptionPane.YES_NO_OPTION);
							if (action == JOptionPane.YES_OPTION) {
								controller.addStock(ticker);
								tablePanel.setData(controller.getStocks());
								tablePanel.refresh();
							}
						}
					} catch (IOException e1) {
						int action = JOptionPane.showConfirmDialog(MainFrame.this,
								"Add " + ticker + " to table anyway?", "Cannot Connect to Internet",
								JOptionPane.YES_NO_OPTION);
						if (action == JOptionPane.YES_OPTION) {
							controller.addStock(ticker);
							tablePanel.setData(controller.getStocks());
							tablePanel.refresh();
						}
					}
				}
			}

		});
	}
}