package Swing12Package;

import javax.swing.SwingUtilities;

public class App {
	/*
	 * This tutorial shows us how to create an event that transfers information
	 * entered on the formPanel to the textPanel inherent in the MainFrame This
	 * tutorial ends at 1hr 51 min and 55 sec and is 20 min and 45 sec long
	 * 
	 * We created a FormEvent to store the text entered into the fields.
	 * 
	 * We then wrote a formPanel.setFormListener method in our MainFrame that
	 * accepted an anonymous class that implements the new custom event. It
	 * retrieves the information from the event and then appends it to the textPanel
	 * 
	 * We then added a FormListener interface that could be used to create the
	 * anonymous class accepted by the previous method.
	 * 
	 * We also added an actionlistener to the okay button on the formpanel that
	 * retrieves the name and occupation and passes them to a new formEvent that
	 * accepts the event, the name, and the occupation. It then passes the event to
	 * formListener.formEventOccurred if formListener != null.
	 * 
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainFrame();
			}
		});
	}
}
