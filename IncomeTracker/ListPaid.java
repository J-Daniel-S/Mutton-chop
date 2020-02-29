package IncomeTracker;

import java.util.*;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "Paids")
public class ListPaid {
	
	private List<Paid> listPaid = new ArrayList<Paid>();
	
	@XmlElement(name = "Paid")
	public List<Paid> getListPaid() {
		return listPaid;
	}

	public void setListPaid(List<Paid> listPaid) {
		this.listPaid = listPaid;
	}
	
	

}
