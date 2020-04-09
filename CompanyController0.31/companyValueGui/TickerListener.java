package companyValueGui;

import java.util.EventListener;

public interface TickerListener extends EventListener {
	public void addEventOccurred(AddEvent e);
}
