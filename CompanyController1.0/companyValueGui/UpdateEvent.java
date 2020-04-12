package companyValueGui;

import java.util.EventObject;

public class UpdateEvent extends EventObject {
	private String text;
	private int row;

	public UpdateEvent(Object source, String text, int row) {
		super(source);
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

}
