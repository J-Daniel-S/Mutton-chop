package companyValueGui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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
	private JLabel yahooLabel;
	private ClickListener clickListener;

	public ToolBar(MainFrame frame) {
		setBorder(BorderFactory.createEtchedBorder());
		tickerLabel = new JLabel("Enter Stock Ticker:");
		tickerField = new JTextField(6);
		yahooLabel = new JLabel("Go to Yahoo Finance");

		setLayout(new FlowLayout(FlowLayout.LEFT));

		yahooLabel.setHorizontalAlignment(FlowLayout.TRAILING);

		addButton = new JButton("Add");
		addButton.setMnemonic(KeyEvent.VK_A);

		setHyperlink(frame, yahooLabel);

		add(tickerLabel);
		add(tickerField);
		add(addButton);
		add(yahooLabel);

		tickerListener();
		yahooListener();
	}

	private void yahooListener() {
		// TODO Auto-generated method stub

	}

	private void setHyperlink(MainFrame mainFrame, JLabel link) {
		String url = "https://finance.yahoo.com/";

		link.setForeground(Color.BLUE.darker());
		link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		link.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI(url));
				} catch (IOException | URISyntaxException err) {
					ClickEvent click = new ClickEvent(mainFrame, "Cannot reach Yahoo Finance");
					if (listener != null) {
						clickListener.clickEventOccurred(click);
					}
				}
			}

			String text = link.getText();

			public void mouseEntered(MouseEvent e) {
				link.setText("<html><a href=''>" + text + "</a></html>");
			}

			public void mouseExited(MouseEvent e) {
				link.setText(text);
			}
		});
	}

	private void tickerListener() {
		addButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String ticker = tickerField.getText();
				AddEvent addEvent = new AddEvent(this, ticker);

				if (listener != null) {
					listener.addEventOccurred(addEvent);
				}
				tickerField.selectAll();
				tickerField.replaceSelection("");
				tickerField.requestFocusInWindow();
			}
		});
	}

	public void setListener(TickerListener listener) {
		this.listener = listener;
	}
}