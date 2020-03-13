package companyValueGui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextPanel extends JPanel {

	private JTextArea textArea;

	public TextPanel() {
		Dimension dim = getPreferredSize();
		dim.height = 100;
		setPreferredSize(dim);

		textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);

		setLayout(new BorderLayout());

		add(new JScrollPane(textArea), BorderLayout.CENTER);
	}

	public void addText(String text) {
		textArea.setText("");
		text = text + "\n";
		textArea.append(text);
	}
}