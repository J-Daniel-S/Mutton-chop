package companyValueGui;

import java.util.EventObject;

public class QueryEvent extends EventObject {

	private String text;

	public QueryEvent(Object source, String text) {
		super(source);
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
