package companyValueGui;

import java.util.EventObject;

public class SynchEvent extends EventObject {

	private int row;

	public SynchEvent(Object source, int row) {
		super(source);
		this.row = row;
	}

	public int getRow() {
		return row;
	}
}
