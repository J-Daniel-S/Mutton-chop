package miniStock;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	
	private TextPanel textPanel;
	private FormPanel formPanel;
	
	public MainFrame() {
		super("Who's Undervalued");
		
		textPanel = new TextPanel();
		formPanel = new FormPanel();
		
		formPanel.setFormListener(new FormListener() {
			public void formEventOccurred(AddEvent e) {
				String ticker = e.getTicker();
				
				if (!ticker.isEmpty()) {
					textPanel.appendText(ticker);
				}
			}
			
		});
		
		add(textPanel, BorderLayout.CENTER);
		add(formPanel, BorderLayout.WEST);
	
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
