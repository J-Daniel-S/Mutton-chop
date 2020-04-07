package companyValueGui;

import java.util.EventObject;

public class ClickEvent extends EventObject {

	// passes the object (if applicable), the text, and the variable/columnName from
	// pane to pane

	private String text;
	private String columnName;
	private int row;

	public ClickEvent(Object source, String text) {
		super(source);
		this.text = text;
	}

	public ClickEvent(Object source, String text, String entry, int row) {
		super(source);
		this.text = text;
		this.columnName = entry;
		this.row = row;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setManualEntry(String columnName) {
		this.columnName = columnName;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
}