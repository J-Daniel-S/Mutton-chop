  
package swing15Package;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

	private TextPanel textPanel;
	private Toolbar toolbar;
	private FormPanel formPanel;

	public MainFrame() {
		super("Hello World!");

		setLayout(new BorderLayout());

		toolbar = new Toolbar();
		textPanel = new TextPanel();
		formPanel = new FormPanel();

		/*
		 * This interface is being used to call an anonymous class here. This anonymous
		 * class is then being used to elicit behavior from the textPanel without the
		 * toolbar having to know anything about the textpanel whatsoever
		 */

		toolbar.setStringListener(new StringListener() {
			public void textEmitted(String text) {
				textPanel.appendText(text);

			}

		});

		formPanel.setFormListener(new FormListener() {
			public void formEventOccurred(FormEvent e) {
				String name = e.getName();
				String occupation = e.getOccupation();
				int ageCat = e.getAgeCategory();
				String empCat = e.getEmploymentCategory();

				if (!name.isEmpty() || !occupation.isEmpty()) {
					StringBuilder text = new StringBuilder(100);
					text.append(name).append(": ").append(occupation).append(": ").append(ageCat)
						.append(": ").append(empCat).append("\n");

					textPanel.appendText(text.toString());
				}
				
			}
		});

		add(formPanel, BorderLayout.WEST);
		add(toolbar, BorderLayout.NORTH);
		add(textPanel, BorderLayout.CENTER);

		setSize(600, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

}