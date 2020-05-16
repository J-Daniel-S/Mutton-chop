package companyValueController;

import java.util.List;

import companyValueModel.Evaluator;
import companyValueModel.Stock;

public class Updater {

	public static void update() {
		List<Stock> stocks = CompanyController.getStocks();

		for (Stock stock : stocks) {
			if (stock.isAutoUpdate() == true) {
				Evaluator.updatePriceData(stock);
			}
		}
	}
}
