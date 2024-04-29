package parts.models;

import javax.swing.table.DefaultTableModel;

public class TModel extends DefaultTableModel {
	
	public TModel(Object[] obj,int o) {
		super(obj,o);
	}
	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0,c).getClass();
	}
	@Override
	public boolean isCellEditable(int row,int col) {
		return false;
	}
	/**
	 * @param args
	 */
	

}
