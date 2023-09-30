package acquittance.editors;

import javax.swing.*;
import java.awt.*;

public class AcquittanceTableCellEditor extends DefaultCellEditor {

	private JTextField tf = null;

	public AcquittanceTableCellEditor(JTextField arg0) {
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
    	if((Boolean) table.getValueAt(row, 5)) {
    		tf.setEnabled(true);
    		tf.setEditable(true);
    	} else {
    	   tf.setEditable(false);
    		tf.setEnabled(false);
    	}
    	return tf;
    }
}
