package miniStock;

import java.util.EventListener;

public interface FormListener extends EventListener {
	public void formEventOccurred(AddEvent e);
}