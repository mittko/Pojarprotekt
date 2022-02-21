package Reports.ReportsRenderers;

import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import Reports.ReportsModel.RowEditorModel;

public class JTableX extends JTable{

	protected RowEditorModel rm;
	
	public JTableX() {
		super();
	rm = null;
	}
	public JTableX(TableModel tm) {
		super(tm);
		rm = null;
	}
	public JTableX(TableModel tm,TableColumnModel tcm) {
		super(tm,tcm);
		rm = null;
	}
	public JTableX(TableModel tm,TableColumnModel tcm,ListSelectionModel lsm) {
		super(tm,tcm,lsm);
	rm = null;
	}
	public JTableX(int rows,int columns) {
		super(rows,columns);
		rm = null;
	}
	public JTableX(final Vector rowData,final Vector columnNames) {
		super(rowData,columnNames);
		rm = null;
}
	public JTableX(final Object[][] rowData,final Object[][] columnNames) {
		super(rowData,columnNames);
		rm = null;
	}
	public JTableX(TableModel tm,RowEditorModel rem) {
		super(tm,null,null);
		this.rm = rem;
	}
	public void setRowEditorModel(RowEditorModel rem) {
		this.rm = rem;
	}
	public RowEditorModel getEditorModel() {
		return rm;
	}
	public TableCellEditor getCellEditor(int row,int column) {
		TableCellEditor tmpEditor = null;	if(rm != null) {
			tmpEditor = rm.getEditor(row);
		}
		if(tmpEditor != null) {
			return tmpEditor;
		}
		return super.getCellEditor(row, column);	}
	

}
