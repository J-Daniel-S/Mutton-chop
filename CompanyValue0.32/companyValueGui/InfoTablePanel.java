package companyValueGui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import companyValueModel.Stock;

public class InfoTablePanel extends BaseTablePanel {

	private JTable table;
	private InfoTableModel tableModel;
	private JPopupMenu popup;

	public InfoTablePanel() {

		tableModel = new InfoTableModel();
		table = new JTable(tableModel);
		popup = new JPopupMenu();

		TableController.addToPopup(this, table, tableModel, popup);

		table.setRowHeight(25);

		centerJustifyColumns();

		TableController.addMouseListener(this, table, tableModel, popup);

		setLayout(new BorderLayout());

		add(new JScrollPane(table), BorderLayout.CENTER);
		autoResizeTable();
	}

	private void centerJustifyColumns() {
		DefaultTableCellRenderer centerRender = new DefaultTableCellRenderer();
		centerRender.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(String.class, centerRender);
	}

	public void setData(List<Stock> stocks) {
		tableModel.setData(stocks);
	}

	public void refresh() {
		tableModel.fireTableDataChanged();
	}

	private void autoResizeTable() {
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	}
}