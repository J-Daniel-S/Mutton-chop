package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class FormPanel extends JPanel {

	private JLabel nameLabel;
	private JLabel occupationLabel;
	private JTextField nameField;
	private JTextField occupationField;
	private JButton okayButton;
	private FormListener formListener;
	private JList ageList;
	private JComboBox employmentCombo;
	private JCheckBox citizenCheck;
	private JTextField taxField;
	private JLabel taxLabel;

	private JRadioButton maleRadio;
	private JRadioButton femaleRadio;
	private ButtonGroup genderGroup;

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
		citizenCheck = new JCheckBox();
		taxField = new JTextField(10);
		taxLabel = new JLabel("Tax ID: ");
		okayButton = new JButton("OK");

//		setup mnemonics
		okayButton.setMnemonic(KeyEvent.VK_O);

		nameLabel.setDisplayedMnemonic(KeyEvent.VK_N);
		nameLabel.setLabelFor(nameField);

		maleRadio = new JRadioButton("Male");
		femaleRadio = new JRadioButton("Female");

		maleRadio.setActionCommand("male");
		femaleRadio.setActionCommand("female");

		genderGroup = new ButtonGroup();
//		we will get information from the radio buttons using something other than an actionListener
		maleRadio.setSelected(true);

//		setup gender radios
		genderGroup.add(maleRadio);
		genderGroup.add(femaleRadio);

//		setup tax id
		taxLabel.setEnabled(false);
		taxField.setEnabled(false);

		citizenCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean isTicked = citizenCheck.isSelected();
				taxLabel.setEnabled(isTicked);
				taxField.setEnabled(isTicked);
			}

		});

//		setup list box
		DefaultListModel ageModel = new DefaultListModel();
		ageModel.addElement(new AgeCategory(0, "Under 18"));
		ageModel.addElement(new AgeCategory(1, "18 to 65"));
		ageModel.addElement(new AgeCategory(2, "65 or over"));
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

//		okayButton ActionListener
		okayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String occupation = occupationField.getText();
				AgeCategory ageCat = (AgeCategory) ageList.getSelectedValue();
				String empCat = (String) employmentCombo.getSelectedItem();
				String taxId = taxField.getText();
				boolean usCitizen = citizenCheck.isSelected();

				String gender = genderGroup.getSelection().getActionCommand();

				FormEvent event = new FormEvent(this, name, occupation, ageCat.getId(), empCat, taxId, usCitizen,
						gender);

				if (formListener != null) {
					formListener.formEventOccurred(event);
				}

				nameField.selectAll();
				nameField.replaceSelection("");
				occupationField.selectAll();
				occupationField.replaceSelection("");
				taxField.selectAll();
				taxField.replaceSelection("");
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

		gc.gridy++;

		gc.weightx = 1;
		gc.weighty = 0.20;

		gc.gridx = 0;
		gc.insets = new Insets(0, 0, 0, 5);
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		add(new JLabel("US Citizen: "), gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(citizenCheck, gc);

///////////////		Next row   /////////////////

		gc.gridy++;

		gc.weightx = 1;
		gc.weighty = 0.20;

		gc.gridx = 0;
		gc.insets = new Insets(0, 0, 0, 5);
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		add(taxLabel, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(taxField, gc);

///////////////		Next row   /////////////////

		gc.gridy++;

		gc.weightx = 1;
		gc.weighty = 0.05;

		gc.gridx = 0;
		gc.insets = new Insets(0, 0, 0, 5);
		gc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("Gender: "), gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(maleRadio, gc);

///////////////		Next row   /////////////////

		gc.gridy++;

		gc.weightx = 1;
		gc.weighty = 0.05;

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(femaleRadio, gc);

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