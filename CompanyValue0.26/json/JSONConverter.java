package json;

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
	private static Type stockType = new TypeToken<StocksList>(){}.getType();
//	private static Type listType = new TypeToken<List<Stock>>(){}.getType();
	
//	for testing
	public static void convertToJSON(StocksList stocks) {
		Gson json = new Gson();
		String storedStock = json.toJson(stocks, stockType);
//		String storedStock = json.toJson(stocks, listType);
		System.out.println(storedStock);
	}
	
//	for testing
	public static Path getPath() {
		return path;
	}
	
	public static void convertToJSON(StocksList stocks, Path path) {
		Gson json = new Gson();
		
		String storedStocks = json.toJson(stocks, stockType);
//		List<Stock> temp = stocks.getStocks();
		
//		String storedStocks = json.toJson(temp, listType);
		checkForFile(path);
		try (BufferedWriter writer = Files.newBufferedWriter(path)){
			writer.write(storedStocks);
		} catch (IOException e) {
			e.printStackTrace();
//			sysout about being unable to write
		}
	}
	
	static void checkForFile(Path path) {
		if (Files.notExists(path)) {
			try {
				Files.createFile(path);
			} catch (IOException e) {
				e.printStackTrace();
//				to be handled later
			}
		}
	}
	
//	just starting this
	public static StocksList convertFromJSON(Path path) {
		StocksList stocksList = new StocksList();
//		List<Stock> stocks = new ArrayList<>();
 		Gson json = new Gson();
		String fromJson;
		try {
			fromJson = Files.readAllBytes(path).toString();
//			stocks = json.fromJson(fromJson, listType);
			stocksList = json.fromJson(fromJson, stockType);
//			stocksList.setStocks(stocks);
			return stocksList;
		} catch (IOException e) {
			e.printStackTrace();
//			handle later
		}
		return stocksList;
	}
}