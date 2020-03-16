package companyValueGui;

import java.io.IOException;

import com.google.gson.Gson;

import companyValueModel.Evaluator;
import companyValueModel.Stock;
import companyValueModel.WebReaderFilter;

public class CompanyValueMain {

	public static void main(String[] args) {
//		SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//				new MainFrame();
//			}
//		});

		WebReaderFilter adder = new WebReaderFilter();
		Stock stock = new Stock("INTC");
		try {
			stock.setProfile(adder.getStockDetails(stock.getTicker()));
		} catch (IOException e) {
			e.printStackTrace();
		}
//		stock.getProfile().forEach((k, v) -> System.out.println(k + ": " + v));
		Evaluator.storeDetails(stock);
		Gson json = new Gson();
		String storedStock = json.toJson(stock);
		System.out.println(storedStock);
	}

}
