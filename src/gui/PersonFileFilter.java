package gui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class PersonFileFilter extends FileFilter {

	@Override
	public boolean accept(File file) {

//		This line makes it so we can see folders in files we are looking in
		if (file.isDirectory()) {
			return true;
		}

		String name = file.getName();

		String extension = Utils.getFileExtension(name);

		if (extension == null) {
			return false;
		} else if (extension.equals("per")) {
			return true;
		}
		return false;
	}

	public String getDescription() {
		return "Person database files (*.per)";
	}

}
