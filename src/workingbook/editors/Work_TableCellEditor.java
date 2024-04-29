package workingbook.editors;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;

public class Work_TableCellEditor  extends DefaultCellEditor {

    JTextField textField;
	public Work_TableCellEditor(JTextField textField) {
		super(textField);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		// TODO Auto-generated method stub
		Component c = super.getTableCellEditorComponent(table, value, isSelected, row, column);
		if(c instanceof JTextField) {
			textField = (JTextField)c;
		}
		return textField;
	}
}
