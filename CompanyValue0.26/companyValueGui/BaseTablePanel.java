package companyValueGui;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public abstract class BaseTablePanel extends JPanel {
	
	private ClickListener clickListener;
	private JPopupMenu popup;
	
	public ClickListener getClickListener() {
		return this.clickListener;
	}
	
	public void setListener(ClickListener listener) {
		this.clickListener = listener;
	}
	
	public JPopupMenu getPopup() {
		return popup;
	}

}
