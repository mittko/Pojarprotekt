package acquittance.editors;

import javax.swing.*;
import java.awt.*;

public class TableCellEditors2 extends DefaultCellEditor {

	private JTextField tf = null;

	public TableCellEditors2(JTextField arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
    @Override
    public Component getTableCellEditorComponent(JTable table,Object value,boolean isSelected,
    		int row,int column) {
    	Component c = super.getTableCellEditorComponent(table, value, isSelected, row, column);
    	if(c instanceof JTextField) {
    		tf = (JTextField)c;
    	}
    	if((Boolean)table.getValueAt(row, 5) == true) {
    		tf.setEnabled(true);
    		tf.setEditable(true);
    	} else {
    	   tf.setEditable(false);
    		tf.setEnabled(false);
    	}
    	return tf;
    }
}
