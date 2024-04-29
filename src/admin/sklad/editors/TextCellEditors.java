package admin.sklad.editors;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;

public class TextCellEditors extends DefaultCellEditor {
private JTextField tf = null;
	
	public TextCellEditors(JTextField arg0) {
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
    	if((Boolean) table.getValueAt(row, 7)) {
    		tf.setEnabled(true);
    		tf.setEditable(true);
    	} else {
    	   tf.setEditable(false);
    		tf.setEnabled(false);
    	}
    	return tf;
    }
}
