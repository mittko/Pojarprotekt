package calendar.models;


import javax.swing.table.DefaultTableModel;


public class MyTModel extends DefaultTableModel {

	public MyTModel() {
		
	}
	public MyTModel(Object[] columnNames) {
		super(columnNames,0);
		
	}
	public boolean isCellEditable(int row,int col) {
		return false;
	}
	
	

}
