package swing15Package;

import javax.swing.SwingUtilities;

public class App {
	/*
	 *	This tutorial begins at 2:11:41 and ends at 2:24:22.  Combo boxes are a combination of textboxes and listboxes.
	 *	These can be editable.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainFrame();
			}
		});
	}
}