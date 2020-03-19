package json;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import companyValueModel.Stock;
import companyValueModel.StocksList;

public class JSONConverter {
	private static Path path = Paths.get("src\\json\\JSONList.json");

//	for testing
	public static void convertToJSON(StocksList stocks) {
		
		Gson json = new Gson();
		String storedStock = json.toJson(stocks);
		System.out.println(storedStock);
	}
	
//	for testing
	public static Path getPath() {
		return path;
	}
	
	public static void convertToJSON(StocksList stocks, Path path) {
		Gson json = new Gson();
		String storedStocks = json.toJson(stocks);
		checkForFile(stocks, path);
		try (BufferedWriter writer = Files.newBufferedWriter(path)){
			writer.write(storedStocks);
		} catch (IOException e) {
			e.printStackTrace();
//			sysout about being unable to write
		}
	}
	
//	this method isn't needed but the path is
//	public static void jsonFileToString(StocksList stocks) {
//		Path path = Paths.get("companyValueModel\\StocksList.json");
//	}
	
	static void checkForFile(StocksList stocks, Path path) {
		if (Files.notExists(path)) {
			try {
				Files.createFile(path);
			} catch (IOException e) {
				e.printStackTrace();
//				sysout warn that file cannot be created
			}
		}
	}
	
//	just starting this
	public static StocksList convertFromJSON(Path path) {
		StocksList stocks = new StocksList();
		Gson json = new Gson();
		
//		Type listType = new TypeToken<ArrayList<Stock>>(){}.getType();
		
		String fromJson;
		try {
			fromJson = Files.readAllBytes(path).toString();
//			List<Stock> theStocks = json.fromJson(fromJson, listType);
			stocks = json.fromJson(fromJson, StocksList.class);
//			stocks.setStocks(theStocks);
			return stocks;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stocks;
	}
}