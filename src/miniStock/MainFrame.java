package miniStock;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

//we'll probably have to remove these imports later
import miniStockModel.Stock;
import miniStockModel.StocksList;

public class MainFrame extends JFrame {
	
	private TextPanel textPanel;
	private FormPanel formPanel;
	private StockTablePanel tablePanel;
	private StocksList stocks;
	
	public MainFrame() {
		super("Who's Undervalued");
		
		tablePanel = new StockTablePanel();
		formPanel = new FormPanel();
		textPanel = new TextPanel();
		stocks = new StocksList();
		
//		a method to pass the list of stocks to the stocktablepanel is required here
				
		formPanel.setFormListener(new FormListener() {
			public void formEventOccurred(AddEvent e) {
				String ticker = e.getTicker();
				
				if (!ticker.isEmpty()) {
					Stock stock = new Stock(ticker.toUpperCase());
//					for now we'll add this directly and fix it when we add the controller
					stocks.addStock(stock);
					List<Stock> stks = stocks.getStocks();
					textPanel.appendText(stks.get(stks.size()-1).getTicker());
				}
			}
			
		});
		
//		add tablePanel here
		add(textPanel, BorderLayout.CENTER);
		add(formPanel, BorderLayout.WEST);
	
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
