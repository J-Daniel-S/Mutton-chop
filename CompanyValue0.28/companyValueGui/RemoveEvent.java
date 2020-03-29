package companyValueGui;

import java.util.EventObject;

public class RemoveEvent extends EventObject {

	private String ticker;
	private boolean remove;

	public RemoveEvent(Object source, String text, boolean remove) {
		super(source);
		this.ticker = text;
		this.remove = remove;
	}

	public String getTicker() {
		return ticker;
	}

	public boolean isRemove() {
		return remove;
	}

}
