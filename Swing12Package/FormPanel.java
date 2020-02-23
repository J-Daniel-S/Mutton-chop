package Swing12Package;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

/*	This tutorial deals with adding the JLabels and JTextFields to the panels.  It is about as complex and important
 * as the listener tutorial. 
 */
public class FormPanel extends JPanel {

	private JLabel nameLabel;
	private JLabel occupationLabel;
	private JTextField nameField;
	private JTextField occupationField;
	private JButton okayButton;
	private FormListener formListener;

	public FormPanel() {
		Dimension dim = getPreferredSize();
		dim.width = 250;
		setPreferredSize(dim);

		nameLabel = new JLabel("Name: ");
		occupationLabel = new JLabel("Occupation: ");
		nameField = new JTextField(10);
		occupationField = new JTextField(10);

		okayButton = new JButton("OK");

		okayButton.addActionListener(new ActionListener() {
//			remember that this is an anonymous class
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String occupation = occupationField.getText();

				FormEvent event = new FormEvent(this, name, occupation);

				if (formListener != null) {
					formListener.formEventOccurred(event);
				}
			}
		});

		Border innerBorder = BorderFactory.createTitledBorder("Add a Fellow");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();
//		do not leave this out
///////////////		first row   /////////////////
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.gridy = 0;
		gc.fill = GridBagConstraints.NONE;
//		This next line adds an indent between different objects in the grid
		gc.insets = new Insets(0, 0, 0, 5);
//		This next line fixes the issue where all of the labels, fields, and buttons are spread out
		gc.anchor = GridBagConstraints.LINE_END;

		add(nameLabel, gc);

		gc.gridx = 1;
		gc.gridy = 0;
		gc.insets = new Insets(0, 0, 0, 0);
//		He said something about not needing to repeat it but doing so for clarity anyway
		gc.anchor = GridBagConstraints.LINE_START;
		add(nameField, gc);

///////////////		second row   /////////////////

		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.gridy = 1;
		gc.insets = new Insets(0, 0, 0, 5);
		gc.anchor = GridBagConstraints.LINE_END;
		add(occupationLabel, gc);

		gc.gridx = 1;
		gc.gridy = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(occupationField, gc);

///////////////		third row   /////////////////
//		The weight determines the sizes of the components relative to each other
		gc.weightx = 1;
		gc.weighty = 20;

		gc.gridx = 1;
		gc.gridy = 2;
		gc.insets = new Insets(0, 0, 0, 21);
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		add(okayButton, gc);
	}

	public void setFormListener(FormListener listener) {
		this.formListener = listener;
	}
}
