package companyValueGui;

import java.util.EventObject;

public class AddEvent extends EventObject {

	private String ticker;

	public AddEvent(Object source) {
		super(source);
	}

	public AddEvent(Object source, String ticker) {
		super(source);
		this.ticker = ticker;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
}