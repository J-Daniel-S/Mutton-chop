package companyValueGui;

import java.util.EventListener;

public interface QueryListener extends EventListener {
	public void queryEventOccurred(QueryEvent e);
}
