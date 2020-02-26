package swing26Gui;

import javax.swing.SwingUtilities;

public class App {
	/*
	 * This tutorial begins at 4:05:32 and ends at 4:26:46. Wherein we add
	 * PersonTableModel and TablePanel classes. We also added a getPeople() method
	 * to the controller and then added passed the list to tablePanel via MainFrame.
	 * We also added a getDB() method to persontablemodel.
	 * 
	 * We also added a table refresher to the formEventListener on mainFrame and
	 * added column headers to TablePanel.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainFrame();
			}
		});
	}
}
