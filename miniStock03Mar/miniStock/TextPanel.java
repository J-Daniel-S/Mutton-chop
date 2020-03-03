package miniStock;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextPanel extends JPanel {

	private JTextArea textArea;

	public TextPanel() {
		Dimension dim = getPreferredSize();
		dim.width = 350;
		setPreferredSize(dim);

		textArea = new JTextArea();

		setLayout(new BorderLayout());

		add(new JScrollPane(textArea), BorderLayout.CENTER);
	}

	public void appendText(String text) {
		text = text + "\n";
		textArea.append(text);
	}
}