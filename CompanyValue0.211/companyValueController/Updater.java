package companyValueController;

import java.util.List;

import companyValueModel.Evaluator;
import companyValueModel.Stock;

public class Updater {

	public static void update() {
		List<Stock> stocks = CompanyController.getStocks();
		stocks.stream().forEach(s -> Evaluator.updatePriceData(s));
	}
}