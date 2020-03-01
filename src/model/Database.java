package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Database {
//	we also changed this to a linkedlist to facilitate deleting items from the middle of the list
	private List<Person> people;

	public Database() {
		people = new LinkedList<Person>();
	}

	public void addPerson(Person person) {
		people.add(person);
	}

	public List<Person> getPeople() {
//		we added this to keep other classes from modifying the list
		return Collections.unmodifiableList(people);
	}

	public void saveToFile(File file) throws IOException {
		try (FileOutputStream fos = new FileOutputStream(file); ObjectOutputStream oos = new ObjectOutputStream(fos);) {
			Person[] persons = people.toArray(new Person[people.size()]);
//		doing it with an array rather than arraylist saves us from having to deal with potential errors and simplifies it quite a bit
			oos.writeObject(persons);
		}

	}

	public void loadFromFile(File file) throws IOException {
		try (FileInputStream fis = new FileInputStream(file); ObjectInputStream ois = new ObjectInputStream(fis);) {

			try {
				Person[] persons = (Person[]) ois.readObject();
				people.clear();
				people.addAll(Arrays.asList(persons));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public void removePerson(int index) {
		people.remove(index);
	}

}
