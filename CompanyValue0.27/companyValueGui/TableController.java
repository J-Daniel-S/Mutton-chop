package companyValueGui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

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

	public static void setListener(BaseTablePanel panel, ClickListener listener) {
		panel.setListener(listener);
	}

	public static void addMouseListener(BaseTablePanel panel, JTable table, BaseTableModel tableModel,
			JPopupMenu popup) {
		table.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				int col = table.columnAtPoint(e.getPoint());
				table.getSelectionModel().setSelectionInterval(row, row);

				if (col == 0) {
					ClickEvent ce = new ClickEvent(this, "");
					ce.setText(tableModel.getStocks().get(table.rowAtPoint(e.getPoint())).getDescription());
					if (panel.getClickListener() != null) {
						panel.getClickListener().clickEventOccurred(ce);
					}
				}

				if (e.getButton() == MouseEvent.BUTTON3) {
					table.clearSelection();
					popup.show(table, e.getX(), e.getY());
					table.getSelectionModel().setSelectionInterval(row, row);
					table.addColumnSelectionInterval(col, col);
				}
			}
		});
	}

	public static void addToPopup(BaseTablePanel panel, JTable table, BaseTableModel tableModel, JPopupMenu popup) {
		JMenuItem query = new JMenuItem("What's this?");
		popup.add(query);
		query.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClickEvent ce = new ClickEvent(this, tableModel.getStocks().get(table.getSelectedRow()).getName() + "\n"
						+ table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()));
				if (panel.getClickListener() != null) {
					panel.getClickListener().clickEventOccurred(ce);
				}
			}
		});
	}
}