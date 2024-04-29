package parts.editors;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class TEditor extends AbstractCellEditor  implements TableCellEditor{
   JLabel label = null;
	public TEditor() {
		label = new JLabel();
	}
	

	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return label.getText();
	}

	@Override
	public Component getTableCellEditorComponent(JTable arg0, Object arg1,
			boolean arg2, int arg3, int arg4) {
		
		// TODO Auto-generated method stub
		return label;
	}

}
