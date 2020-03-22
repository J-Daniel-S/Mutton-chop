package companyValueGui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import companyValueModel.Stock;

public class TableController {

	public static void setData(List<Stock> stocks, InfoTablePanel info, FinancialTablePanel fin, ReturnTablePanel ret) {
		info.setData(stocks);
		fin.setData(stocks);
		ret.setData(stocks);
	}

	public static void refresh(InfoTablePanel info, FinancialTablePanel fin, ReturnTablePanel ret) {
		info.refresh();
		fin.refresh();
		ret.refresh();
	}
	
	public static void setListener(InfoTablePanel panel, ClickListener listener) {
		panel.setListener(listener);
	}
	
	public static void setListener(FinancialTablePanel panel, ClickListener listener) {
		panel.setListener(listener);
	}
	
	public static void setListener(ReturnTablePanel panel, ClickListener listener) {
		panel.setListener(listener);
	}
	
	public static void addMouseListener(BaseTablePanel panel, JTable table, 
			BaseTableModel tableModel, JPopupMenu popup) {
		table.addMouseListener(new MouseAdapter( ) {
			public void mouseClicked(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				int col = table.columnAtPoint(e.getPoint());
				table.getSelectionModel().setSelectionInterval(row, row);
				
				ClickEvent ce = new ClickEvent(this, "");
				
				if (col == 0) {
					 ce.setText(tableModel.getStocks().get(table.rowAtPoint(e.getPoint())).getDescription());
					 if(panel.getClickListener() != null) {
						 panel.getClickListener().clickEventOccurred(ce);
					 }
				}
				
				if (e.getButton() == MouseEvent.BUTTON3) {
					popup.show(table, e.getX(), e.getY());
				}
			}
		});
	}
}
