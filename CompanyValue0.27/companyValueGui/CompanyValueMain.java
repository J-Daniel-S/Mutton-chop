package companyValueGui;

import javax.swing.SwingUtilities;

public class CompanyValueMain {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainFrame();
			}
		});
	}
}