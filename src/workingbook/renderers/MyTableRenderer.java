package workingbook.renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class MyTableRenderer extends DefaultTableCellRenderer {

	public MyTableRenderer(JTable refferenceTable) {
		Color background = this.getBackground();// Color.decode("0xD8E6FF");//getBackground();
		hideColumns(refferenceTable);
		  setColumnsWidth(refferenceTable);
	}
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		// TODO Auto-generated method stub
	   Component c = super.getTableCellRendererComponent(table, value, isSelected, 
			   hasFocus, row, column);
	   this.setBackground(table.getBackground());
	   // table.setBackground() force CPU to max (maybe call  getTableCellRendererComponent)
	   if(column > 8) {
		   this.setForeground(Color.RED);
	   } else {
		   this.setForeground(Color.BLACK);
	   }
	   this.setHorizontalAlignment(JLabel.CENTER);
	   return c;
	}
	
	private void hideColumns(JTable table) {
	table.getColumnModel().getColumn(0).setMinWidth(0);
	table.getColumnModel().getColumn(0).setMaxWidth(0);
	
	table.getTableHeader().setReorderingAllowed(false);
  //  table.getTableHeader().setResizingAllowed(false);
	}
	private void setColumnsWidth(JTable table) {
	//	table.getColumnModel().getColumn(0).setPreferredWidth(50); -> column 0 is hidden
		table.getColumnModel().getColumn(1).setPreferredWidth(50);
		table.getColumnModel().getColumn(2).setPreferredWidth(20);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.getColumnModel().getColumn(5).setPreferredWidth(20);
		table.getColumnModel().getColumn(6).setPreferredWidth(65);
		table.getColumnModel().getColumn(7).setPreferredWidth(45);
		table.getColumnModel().getColumn(8).setPreferredWidth(45);
		table.getColumnModel().getColumn(9).setPreferredWidth(45);
	}
}
