package companyValueGui;

import java.util.EventObject;

public class ClickEvent extends EventObject{
	
	private String text;
	
	public ClickEvent(Object source, String text) {
		super(source);
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
