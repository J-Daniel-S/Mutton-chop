package swing15Package;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

/*	In this tutorial the FormPanel class got somewhat huge so we refactored by adding the method
 *  layoutComponents() and moved the gridbaglayout to it.
 */
public class FormPanel extends JPanel {

	private JLabel nameLabel;
	private JLabel occupationLabel;
	private JTextField nameField;
	private JTextField occupationField;
	private JButton okayButton;
	private FormListener formListener;
	private JList ageList;
	private JComboBox 	employmentCombo;

	public FormPanel() {
		Dimension dim = getPreferredSize();
		dim.width = 250;
		setPreferredSize(dim);

		nameLabel = new JLabel("Name: ");
		occupationLabel = new JLabel("Occupation: ");
		nameField = new JTextField(10);
		occupationField = new JTextField(10);
		ageList = new JList();
		employmentCombo = new JComboBox();
		
//		setup list box
		DefaultListModel ageModel = new DefaultListModel();
		ageModel.addElement(new AgeCategory(0,"Under 18"));
		ageModel.addElement(new AgeCategory(1,"18 to 65"));
		ageModel.addElement(new AgeCategory(2,"65 or over"));
		ageList.setModel(ageModel);
		
		ageList.setPreferredSize(new Dimension(110, 60));
		ageList.setBorder(BorderFactory.createEtchedBorder());
		ageList.setSelectedIndex(1);
		
//		setup combo box
		DefaultComboBoxModel empModel = new DefaultComboBoxModel();
		empModel.addElement("Employed");
		empModel.addElement("Self-Employed");
		empModel.addElement("Unemployed");
		employmentCombo.setModel(empModel);
		employmentCombo.setEditable(true);

		okayButton = new JButton("OK");

		okayButton.addActionListener(new ActionListener() {
//			remember that this is an anonymous class
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String occupation = occupationField.getText();
				AgeCategory ageCat = (AgeCategory) ageList.getSelectedValue();
				String empCat = (String) employmentCombo.getSelectedItem();
				
//				System.out.println(empCat);

				FormEvent event = new FormEvent(this, name, occupation, ageCat.getId(), empCat);

				if (formListener != null) {
					formListener.formEventOccurred(event);
				}
			}
		});

		Border innerBorder = BorderFactory.createTitledBorder("Add a Fellow");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		layoutComponents();
	}

	public void setFormListener(FormListener listener) {
		this.formListener = listener;
	}
	
	public void layoutComponents() {
		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();
//		do not leave this out
///////////////		first row   /////////////////
		gc.gridy = 0;
		
		gc.weightx = 1;
		gc.weighty = 0.2;

		gc.gridx = 0;

		gc.fill = GridBagConstraints.NONE;
//		This next line adds an indent between different objects in the grid
		gc.insets = new Insets(0, 0, 0, 5);
//		This next line fixes the issue where all of the labels, fields, and buttons are spread out
		gc.anchor = GridBagConstraints.LINE_END;

		add(nameLabel, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
//		He said something about not needing to repeat it but doing so for clarity anyway
		gc.anchor = GridBagConstraints.LINE_START;
		add(nameField, gc);

///////////////		Next row   /////////////////
		
		gc.gridy++;

		gc.weightx = 1;
		gc.weighty = 0.2;

		gc.gridx = 0;

		gc.insets = new Insets(0, 0, 0, 5);
		gc.anchor = GridBagConstraints.LINE_END;
		add(occupationLabel, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(occupationField, gc);

///////////////		Next row   /////////////////
		
		gc.gridy++;
		
		gc.weightx = 1;
		gc.weighty = 0.20;
		
		gc.gridx = 0;
		gc.insets = new Insets(0, 0, 0, 5);
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		add(new JLabel("Age: "), gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 21);
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(ageList, gc);
		
///////////////		Next row   /////////////////
		
	gc.gridy++;
	
	gc.weightx = 1;
	gc.weighty = 0.20;
	
	gc.gridx = 0;
	gc.insets = new Insets(0, 0, 0, 5);
	gc.anchor = GridBagConstraints.FIRST_LINE_END;
	add(new JLabel("Employment: "), gc);

	gc.gridx = 1;
	gc.insets = new Insets(0, 0, 0, 11);
	gc.anchor = GridBagConstraints.FIRST_LINE_START;
	add(employmentCombo, gc);
		
///////////////		Next row   /////////////////
//		The weight determines the sizes of the components relative to each other
		gc.gridy++;
		
		gc.weightx = 1;
		gc.weighty = 10;

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 35);
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		add(okayButton, gc);
	}
}


class AgeCategory {
	private int id;
	private String text;
	
	public AgeCategory(int id, String text) {
		this.id = id;
		this.text = text;
	}
	
	public String toString() {
		return text;
	}
	
	public int getId() {
		return id;
	}
}