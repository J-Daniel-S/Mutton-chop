package json;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.google.gson.Gson;

import companyValueModel.Stock;
import companyValueModel.StocksList;

public class JSONConverter {

//	for testing
	public static void convertToJSON(StocksList stocks) {
		
		Gson json = new Gson();
		String storedStock = json.toJson(stocks);
		System.out.println(storedStock);
	}
	
	public static void convertToJSON(StocksList stocks, Path path) {
		Gson json = new Gson();
		String storedStock = json.toJson(stocks);
		try {
			Files.write(path, storedStock.getBytes(), StandardOpenOption.WRITE);
		} catch (IOException e) {
			e.printStackTrace();
//			sysout about being unable to write
		}
	}
	
//	this method isn't needed but the path is
	public static void jsonFileToString(StocksList stocks) {
		Path path = Paths.get("companyValueModel\\StocksList.json");
	}
	
	void checkForFile(Path path, StocksList stocks) {
		if (Files.notExists(path)) {
			try {
				Files.createFile(path);
			} catch (IOException e) {
				e.printStackTrace();
//				sysout warn that file cannot be created
			}
		} else {
			System.out.println("Maynard");
		}
	}
	
//	just starting this
	public static void convertFromJSON(StocksList stocks, Path path) {
		Gson json = new Gson();
		String fromJson;
		try {
			fromJson = Files.readAllBytes(path).toString();
			stocks = json.fromJson(fromJson, StocksList.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}