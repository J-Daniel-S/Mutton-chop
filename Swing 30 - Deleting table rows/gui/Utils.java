package gui;

public class Utils {

//	can get a class like this from java utils swing tutorial page

	public static String getFileExtension(String name) {

		int pointIndex = name.lastIndexOf(".");

//		will return -1 if there is no "." in the name
		if (pointIndex == -1) {
			return null;
		}

//		will return this if "." is the last char in the String
		if (pointIndex == name.length() - 1) {
			return null;
		}

		return name.substring(pointIndex + 1, name.length());
	}

}
