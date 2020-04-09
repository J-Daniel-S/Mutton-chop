package companyValueGui;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import companyValueController.CompanyController;
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
		panel.setClickListener(listener);
	}

	public static void setListener(BaseTablePanel panel, RemoveListener listener) {
		panel.setRemoveListener(listener);
	}

	public static void setListener(BaseTablePanel panel, CheckListener listener) {
		panel.setCheckListener(listener);
	}

	public static void setListener(BaseTablePanel panel, UpdateListener listener) {
		panel.setUpdateListener(listener);
	}

	public static void addMouseListener(BaseTablePanel panel, JTable table, BaseTableModel tableModel,
			JPopupMenu popup) {
		table.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				int col = table.columnAtPoint(e.getPoint());
				table.getSelectionModel().setSelectionInterval(row, row);

				// displays the company's description when the ticker is clicked
				if (col == 0) {
					ClickEvent ce = new ClickEvent(e, "");
					ce.setText(tableModel.getStocks().get(table.rowAtPoint(e.getPoint())).getDescription());
					if (panel.getClickListener() != null) {
						panel.getClickListener().clickEventOccurred(ce);
					}
				}

				// selects the row and column that are right clicked
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
		defineQuery(panel, table, tableModel, popup);

		defineRemove(panel, table, tableModel, popup);

		defineEntry(panel, table, popup);

		defineAutoUpdate(panel, popup, table.getSelectedRow());

		JMenuItem synch = new JMenuItem("Resynch with Database");
		popup.add(synch);

		defineValuesCheck(panel, table, popup);
	}

	private static void defineQuery(BaseTablePanel panel, JTable table, BaseTableModel tableModel, JPopupMenu popup) {
		JMenuItem query = new JMenuItem("What's this?");
		popup.add(query);
		// to be added when I finish descriptions
		query.addActionListener((ActionEvent e) -> {
			ClickEvent ce;
			ce = cellCheck(table, tableModel, e);
			if (panel.getClickListener() != null) {
				panel.getClickListener().clickEventOccurred(ce);
			}
		});
	}

	private static void defineRemove(BaseTablePanel panel, JTable table, BaseTableModel tableModel, JPopupMenu popup) {
		JMenuItem remove = new JMenuItem("Remove stock");
		popup.add(remove);
		remove.addActionListener((ActionEvent e) -> {
//			this also throws array out of bounds exception but does not seem to crash the program as the other one did
			RemoveEvent re = new RemoveEvent(e, tableModel.getStocks().get(table.getSelectedRow()).getTicker(), true);
			if (panel.getRemoveListener() != null) {
				panel.getRemoveListener().removeEventOccurred(re);
			}
		});
	}

	private static void defineEntry(BaseTablePanel panel, JTable table, JPopupMenu popup) {
		JMenuItem entry = new JMenuItem("Manually enter data");
		popup.add(entry);
		entry.addActionListener((ActionEvent e) -> {
			int column = table.getSelectedColumn();
			int row = table.getSelectedRow();
			if (column == -1 || row == -1) {
				ClickEvent ce = new ClickEvent(e, "manual", "err", 0);
				if (panel.getClickListener() != null) {
					panel.getClickListener().clickEventOccurred(ce);
				}
			} else {
				ClickEvent ce = new ClickEvent(e, "manual", table.getColumnName(column), row);
				if (panel.getClickListener() != null) {
					panel.getClickListener().clickEventOccurred(ce);
				}
			}
		});
	}

	private static void defineAutoUpdate(BaseTablePanel panel, JPopupMenu popup, int row) {
		JMenuItem autoUpdate = new JMenuItem("Toggle Auto Update");
		popup.add(autoUpdate);
		autoUpdate.addActionListener((ActionEvent e) -> {
			UpdateEvent ce = new UpdateEvent(e, "autoUpdate", row);
			if (panel.getUpdateListener() != null) {
				panel.getUpdateListener().updateEventOccurred(ce);
			}
		});
	}

	private static void defineValuesCheck(BaseTablePanel panel, JTable table, JPopupMenu popup) {
		JMenuItem valueCheck = new JMenuItem("Show Values To Check Against Yahoo Finance");
		popup.add(valueCheck);
		valueCheck.addActionListener((ActionEvent e) -> {
			if (table.getSelectedColumn() != -1 || table.getSelectedRow() != -1) {
				StringBuilder sb = new StringBuilder();

				sb.append("Check Against Most Recent Values From Yahoo Finance:\n")
						.append("\nDate of last Annual Report: ")
						.append(CompanyController.getStocks().get(table.getSelectedRow()).getLastDate())
						.append("\n\nIncome Statment:").append("Revenue: ")
						.append(CompanyController.getStocks().get(table.getSelectedRow()).getRevenue())
						.append("\n\nBalance Sheet:\n").append("Assets: ")
						.append(CompanyController.getStocks().get(table.getSelectedRow()).getAssets()).append("\n")
						.append("Debt: ").append(CompanyController.getStocks().get(table.getSelectedRow()).getDebt())
						.append("\n").append("Equity: ")
						.append(CompanyController.getStocks().get(table.getSelectedRow()).getEquity())
						.append("\n\nCash Flows Statement:").append("\n").append("Cash Flow from Operations: ")
						.append(CompanyController.getStocks().get(table.getSelectedRow()).getCashFlow());

				String vals = sb.toString();

				CheckEvent event = new CheckEvent(e, vals, table.getSelectedRow());

				if (panel.getCheckListener() != null) {
					panel.getCheckListener().checkEventOccurred(event);
				}
			}

		});
	}

	private static ClickEvent cellCheck(JTable table, BaseTableModel tableModel, ActionEvent e) {
		ClickEvent ce;
		if (table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()).equals("Revenue data missing")) {
			ce = new ClickEvent(e,
					"Revenue data is missing from the API.  Check data accuracy against Yahoo finance and manually enter missing/erroneous values.");
		} else if (table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()).equals("No Long Term Debt")) {
			ce = new ClickEvent(e, "The company may be debt free.  Recommend checking against Yahoo Finance");
		} else if (table.getValueAt(table.getSelectedRow(), table.getSelectedColumn())
				.equals("Cash on hand data missing")) {
			ce = new ClickEvent(e,
					"Data likely missing from API. Check data accuracy against Yahoo finance and manually enter missing/erroneous values.");
		} else {
			ce = new ClickEvent(e, tableModel.getStocks().get(table.getSelectedRow()).getName() + "\n"
					+ table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()));
		}
		return ce;
	}
}