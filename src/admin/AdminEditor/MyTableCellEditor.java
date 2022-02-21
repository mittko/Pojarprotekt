package admin.AdminEditor;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

public class MyTableCellEditor extends AbstractCellEditor implements TableCellEditor{

	JComponent component = null;
	
	public MyTableCellEditor() {
		super();
		component = new JTextField(10);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return ((JTextField)component).getText();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table,Object value,boolean isSelected,
			int row,int column) {
		((JTextField)component).setText((String)value);
		return component;
	}

}
