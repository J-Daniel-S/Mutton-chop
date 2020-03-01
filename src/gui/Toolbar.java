
package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Toolbar extends JPanel implements ActionListener {
	private JButton helloButton;
	private JButton goodbyeButton;

	private StringListener textListener;

	public Toolbar() {
		setBorder(BorderFactory.createEtchedBorder());
		helloButton = new JButton("Hello, my son");
		goodbyeButton = new JButton("Goodbye, my son");

		helloButton.addActionListener(this);
		goodbyeButton.addActionListener(this);

		setLayout(new FlowLayout(FlowLayout.LEFT));

		add(helloButton);
		add(goodbyeButton);
	}

	public void setStringListener(StringListener listener) {
		this.textListener = listener;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		JButton clicked = (JButton) e.getSource();

		if (clicked == helloButton) {
			if (textListener != null) {
				textListener.textEmitted("From whence do you come?\n");
			}
		} else if (clicked == goodbyeButton) {
			if (textListener != null) {
				textListener.textEmitted("But why are you leaving?\n");
			}
		}
	}
}