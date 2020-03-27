package unused;

import java.util.EventListener;

import companyValueGui.AddEvent;

public interface FormListener extends EventListener {
	public void formEventOccurred(AddEvent e);
}