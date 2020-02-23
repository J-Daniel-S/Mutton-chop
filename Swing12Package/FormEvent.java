package Swing12Package;

import java.util.EventObject;

//	this class does not have to extend eventObject but we're going to do it
//	All of the swing events derive from this object
public class FormEvent extends EventObject {

	private String name;
	private String occupation;

	public FormEvent(Object source) {
		super(source);
	}

	public FormEvent(Object source, String name, String occupation) {
		super(source);

		this.name = name;
		this.occupation = occupation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

}
