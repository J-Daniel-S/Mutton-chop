package companyValueGui;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public abstract class BaseTablePanel extends JPanel {

	private ClickListener clickListener;
	private JPopupMenu popup;
	private RemoveListener removeListener;
	private CheckListener checkListener;
	private UpdateListener updateListener;

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

	public CheckListener getCheckListener() {
		return checkListener;
	}

	public void setCheckListener(CheckListener checkListener) {
		this.checkListener = checkListener;
	}

	public UpdateListener getUpdateListener() {
		return updateListener;
	}

	public void setUpdateListener(UpdateListener updateListener) {
		this.updateListener = updateListener;
	}

}