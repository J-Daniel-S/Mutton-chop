package companyValueGui;

import java.util.EventListener;

public interface UpdateListener extends EventListener {
	public void updateEventOccurred(UpdateEvent e);
}
