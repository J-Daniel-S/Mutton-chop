package companyValueModel;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//this class needs to be added to the front end
public class Searcher {

	private Searcher() {

	}

	static Stock searchByTicker(StocksList stocks, String search) {

		Optional<Stock> findStock = stocks.getStocks().stream().filter(stk -> stk.getTicker().contains(search))
				.findAny();

		Stock stock = findStock.get();
		return stock;
	}

	static Stock searchByName(StocksList stocks, String search) {

		Optional<Stock> findStock = stocks.getStocks().stream().filter(stk -> stk.getName().contains(search)).findAny();

		Stock stock = findStock.get();
		return stock;
	}

	static List<Stock> searchByIndustry(StocksList stocks, String search) {
		List<Stock> findStocks = stocks.getStocks().stream().filter(stk -> stk.getIndustry().contains(search))
				.collect(Collectors.toList());
		return findStocks;
	}

	static List<Stock> searchBySector(StocksList stocks, String search) {
		List<Stock> findStocks = stocks.getStocks().stream().filter(stk -> stk.getSector().contains(search))
				.collect(Collectors.toList());
		return findStocks;
	}
}