package companyValueGui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import companyValueModel.Stock;

public abstract class BaseTableModel extends AbstractTableModel {
	
//	allows for consolidation into one method on TableController rather than writing three different
//	methods to do one thing on each table
	
	public abstract List<Stock> getStocks();

}
