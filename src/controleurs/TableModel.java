package controleurs;

import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel {

	private Object[][] object;
	private String[] title;

	public TableModel(Object[][] object, String[] title) {
		super();
		this.object = object;
		this.title = title;
	}

	@Override
	public int getColumnCount() {
		return this.title.length;
	}

	@Override
	public int getRowCount() {
		return this.object.length;
	}

	@Override
	public Object getValueAt(int row, int col) {
		return this.object[row][col];
	}

	public String getColumnName(int col) {
		return this.title[col];
	}

	public Class getColumnClass(int col) {

		return this.object[0][col].getClass();
	}

	public boolean isCellEditable(int row, int col) {
		return true;
	}

}
