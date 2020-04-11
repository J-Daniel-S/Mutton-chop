package companyValueGui;

import java.util.EventObject;

public class CheckEvent extends EventObject {
	private String text;
	private int row;

	public CheckEvent(Object arg0, String text, int row) {
		super(arg0);
		this.text = text;
		this.row = row;
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