package companyValueGui;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public abstract class BaseTablePanel extends JPanel {

	private ClickListener clickListener;
//	this is assigned and used in each child class
	private JPopupMenu popup;
	private RemoveListener removeListener;
	private CheckListener checkListener;
	private UpdateListener updateListener;
	private SynchListener synchListener;
	private QueryListener queryListener;

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

	public SynchListener getSynchListener() {
		return synchListener;
	}

	public void setSynchListener(SynchListener synchListener) {
		this.synchListener = synchListener;
	}

	public QueryListener getQueryListener() {
		return queryListener;
	}

	public void setQueryListener(QueryListener queryListener) {
		this.queryListener = queryListener;
	}
}