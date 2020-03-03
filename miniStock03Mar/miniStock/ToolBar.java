package miniStock;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

public class ToolBar extends JToolBar {

	private JButton addButton;

	private JLabel tickerLabel;
	private JTextField tickerField;
	private TickerListener listener;

	public ToolBar() {
		setBorder(BorderFactory.createEtchedBorder());
		tickerLabel = new JLabel("Stock Ticker");
		tickerField = new JTextField(6);

		setLayout(new FlowLayout(FlowLayout.LEFT));

		addButton = new JButton("Add");
		addButton.setMnemonic(KeyEvent.VK_A);

		add(tickerLabel);
		add(tickerField);
		add(addButton);

		addButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String ticker = tickerField.getText();
				AddEvent addEvent = new AddEvent(this, ticker);

				if (listener != null) {
					listener.addEventOccurred(addEvent);
				}
				tickerField.selectAll();
				tickerField.replaceSelection("");
			}
		});
	}

	public void setListener(TickerListener listener) {
		this.listener = listener;
	}
}
