package companyValueGui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import companyValueController.CompanyController;
import companyValueModel.Stock;

public abstract class BaseTableModel extends AbstractTableModel {

//	allows for consolidation into one method on TableController rather than writing three different
//	methods to do one thing on each table

	public abstract List<Stock> getStocks();

	protected String nullCheck(int row, String string) {

		if (CompanyController.getStocks().get(row).getCurrentPrice() == null) {
			return "";
		} else {
			return string;
		}
	}

}