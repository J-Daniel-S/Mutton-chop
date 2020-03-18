package companyValueGui;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.SwingUtilities;

import companyValueModel.Evaluator;
import companyValueModel.Stock;
import companyValueModel.StocksList;
import companyValueModel.WebReaderFilter;
import json.JSONConverter;

public class CompanyValueMain {
	public static void main(String[] args) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					new MainFrame();
				}
			});
	}
}