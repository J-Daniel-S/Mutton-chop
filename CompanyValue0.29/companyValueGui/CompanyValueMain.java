package companyValueGui;

import javax.swing.SwingUtilities;

public class CompanyValueMain {
	public static void main(String[] args) {
		runGui();
	}

	public static void runGui() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainFrame();
			}
		});
	}
}