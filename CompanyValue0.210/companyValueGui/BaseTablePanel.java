package companyValueGui;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public abstract class BaseTablePanel extends JPanel {

	private ClickListener clickListener;
	private JPopupMenu popup;
	private RemoveListener removeListener;

	public RemoveListener getRemoveListener() {
		return this.removeListener;
	}

	public void setRemoveListener(RemoveListener removeListener) {
		this.removeListener = removeListener;
	}

	public ClickListener getClickListener() {
		return this.clickListener;
	}

	public void setClickListener(ClickListener listener) {
		this.clickListener = listener;
	}
}