package swing26Controller;

import java.util.List;

import swing26Gui.FormEvent;
import swing26Model.AgeCategory;
import swing26Model.Database;
import swing26Model.EmploymentCategory;
import swing26Model.Gender;
import swing26Model.Person;

//	We can import gui stuff to the controller but we must NEVER import gui stuff into the data model

public class Controller {
	Database db = new Database();

//	This passes info from backend to frontend without your gui having to reference it
	public List<Person> getPeople() {
		return db.getPeople();
	}

	public void addPerson(FormEvent e) {
		String name = e.getName();
		String occupation = e.getOccupation();
		int ageCatId = e.getAgeCategory();
		String empCat = e.getEmploymentCategory();
		boolean isUS = e.isUsCitizen();
		String taxId = e.getTaxId();
		String gender = e.getGender();

		AgeCategory ageCategory = null;

		switch (ageCatId) {
		case 0:
			ageCategory = ageCategory.child;
			break;
		case 1:
			ageCategory = ageCategory.adult;
			break;
		case 2:
			ageCategory = ageCategory.senior;
			break;
		}

		EmploymentCategory empCategory;

		if (empCat.toLowerCase().equals("employed")) {
			empCategory = EmploymentCategory.employed;
		} else if (empCat.toLowerCase().equals("self-employed")) {
			empCategory = EmploymentCategory.selfEmployed;
		} else if (empCat.toLowerCase().equals("unemployed")) {
			empCategory = EmploymentCategory.unemployed;
		} else {
			empCategory = EmploymentCategory.other;
			System.err.println(empCat);
		}

		Gender genderCat;

		if (gender.toLowerCase().equals("male")) {
			genderCat = Gender.male;
		} else {
			genderCat = Gender.female;
		}

		Person person = new Person(name, occupation, ageCategory, empCategory, taxId, isUS, genderCat);

		db.addPerson(person);

		List<Person> people = db.getPeople();

		people.forEach(x -> System.out.println(x.getName()));
	}
}
