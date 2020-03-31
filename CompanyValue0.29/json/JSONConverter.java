package json;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import companyValueModel.StocksList;

public class JSONConverter {
	private static Path path = Paths.get("src\\json\\JSONList.json");
	private static Type stockType = new TypeToken<StocksList>() {
	}.getType();

	public static void convertToJSON(StocksList stocks, Path path) {
		Gson json = new Gson();
		String storedStocks = json.toJson(stocks, stockType);// I also tried "StocksList.class" here
		checkForFile(path);
		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
			writer.write(storedStocks);
		} catch (IOException e) {
			e.printStackTrace();
			// handle later
		}
	}

	static void checkForFile(Path path) {
		if (Files.notExists(path)) {
			try {
				Files.createFile(path);
			} catch (IOException e) {
				e.printStackTrace();
				// handle later
			}
		}
	}

	// this doesn't work
	public static StocksList convertFromJSON(Path path) {
		StocksList stocksList = new StocksList();
		Gson json = new Gson();
		String fromJson;
		try {
			fromJson = Files.readAllBytes(path).toString();
			stocksList = json.fromJson(fromJson, stockType);
			return stocksList;
		} catch (IOException e) {
			return stocksList;
		}
	}
}