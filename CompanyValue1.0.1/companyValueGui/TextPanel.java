package companyValueGui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextPanel extends JPanel {

	private JTextArea textArea;

	public TextPanel() {
		Dimension dim = getPreferredSize();
		dim.height = 100;
		this.setMinimumSize(dim);
		dim.height = 140;
		this.setPreferredSize(dim);
		dim.height = 250;
		this.setMaximumSize(dim);

		textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);

		textArea.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				textArea.setEditable(true);
			}

			@Override
			public void focusGained(FocusEvent e) {
				textArea.setEditable(false);
			}
		});

		setLayout(new BorderLayout());

		add(new JScrollPane(textArea), BorderLayout.CENTER);
	}

	public void addText(String text) {
		textArea.setText("");
		text = text + "\n";
		textArea.append(text);
	}

	public void appendText(String text) {
		text = text + "\n";
		textArea.append(text);
	}
}