package json;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import companyValueModel.StocksList;

public class JSONConverter {
	private static final Path path = Paths.get(".\\Stocks\\StocksList.json");
	private static final Gson json = new GsonBuilder().disableHtmlEscaping().disableInnerClassSerialization().create();

	public static void convertToJSON(final StocksList stocks, final Path path) {
		Path folderPath = Paths.get(".\\Stocks");
		if (folderPath != null) {
			new File(folderPath.toString()).mkdir();
		}
		checkForFile(path);
		try (final BufferedWriter writer = Files.newBufferedWriter(path)) {
			writer.write(json.toJson(stocks));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void checkForFile(Path path) {
		if (Files.notExists(path)) {
			try {
				Files.createFile(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static StocksList convertFromJSON(final Path path) {
		StocksList stocksList = new StocksList();
		try {
			stocksList = json.fromJson(Files.newBufferedReader(path), StocksList.class);
			return stocksList;
		} catch (IOException e) {
			return stocksList;
		}
	}

	public static Path getPath() {
		return path;
	}
}