package miniStock;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FormPanel extends JPanel {
	
	private JLabel tickerLabel;
	private JTextField tickerField;
	private JButton addButton;
	private FormListener formListener;
	
	public FormPanel() {
		Dimension dim = getPreferredSize();
		dim.width = 500;
		setPreferredSize(dim);
		
		tickerLabel = new JLabel("Stock Ticker");
		tickerField = new JTextField(6);
		
		addButton = new JButton("Add");
		
		addButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String ticker = tickerField.getText();
				System.out.println(ticker);
			
				AddEvent addEvent = new AddEvent(this, ticker);
				
				if (formListener != null) {
					formListener.formEventOccurred(addEvent);
				}
			}
		});
		
		layoutComponents();
	}
	
	public void setFormListener(FormListener formListener) {
		this.formListener = formListener;
	}

	private void layoutComponents() {
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		
///////////////		first row   /////////////////
		gc.gridy = 0;
		
		gc.weightx = 1;
		gc.weighty = 0.2;

		gc.gridx = 0;

		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		
		add(tickerLabel, gc);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		
		add(tickerField, gc);
		
		gc.gridx = 2;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		
		add(addButton, gc);

		
	}

}
