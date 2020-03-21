package gui;

import javax.swing.SwingUtilities;

public class App {
	/*
	 * This tutorial begins at 4:55:58 and ends at . Wherein we added
	 * PersonTablelistener interface and used it to implement an anonymous class
	 * that listened for the click and told MainFrame (in which it resides) which
	 * row was selected when delete was clicked. We then chain-passed the message to
	 * db by writing a removePerson() method in both controller and db. We also
	 * added the method setPersonTableListener to TablePanel. At this point we had
	 * to remember to tell the table to refresh when the data was changed. We added
	 * this to removeItem.addActionListener anonymous class in TablePanel.
	 * 
	 * count needs fixed. It does not carry over from old saves. xml file to fix
	 * this? csv maybe? JSON?
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainFrame();
			}
		});
	}
}
