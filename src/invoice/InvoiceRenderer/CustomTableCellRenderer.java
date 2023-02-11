package invoice.InvoiceRenderer;

import utils.MainPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Locale;

public class CustomTableCellRenderer extends DefaultTableCellRenderer {

	private Color background = null;
	private Font priceFont = null;
	private JTable refferenceTable = null;
	
	public CustomTableCellRenderer(JTable refferenceTable) {
		background = Color.decode("0xD8E6FF");
		priceFont =  new Font(Font.DIALOG,Font.CENTER_BASELINE, MainPanel.getFontSize());
		this.refferenceTable = refferenceTable;
		setColumnsWidth(refferenceTable);
	}
	
	public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,
			boolean hasFocus,int row,int column) {
		
		if(column == 3 || column == 4) {
			if(value.toString().contains(",")) {
				value = value.toString().replace(",", "."); // english comma
			}
			if(value.toString().contains(",")) { // bulgarian comma
				value = value.toString().replace(",", ".");
			}
			value = String.format(Locale.ROOT, "%.2f", Double.parseDouble(value.toString())); // to set two decimal after points
		 }
		Component c = super.getTableCellRendererComponent(table, value, 
				isSelected, hasFocus, row, column);
		if(column > 2) {
			c.setForeground(Color.RED);
		} else {
			c.setForeground(Color.BLACK);
		}
		if(row % 2 == 0) {
		c.setBackground(background);
		} else {
			c.setBackground(Color.white);
		}
		if(isSelected) {
			c.setBackground(Color.yellow);
		}
		if(column == 0) {
		this.setHorizontalAlignment(JLabel.LEFT);
		} else {
			this.setHorizontalAlignment(JLabel.CENTER);
		}
		
		
		c.setFont(priceFont);
		
		return c;
	}
	
	private void setColumnsWidth(JTable table) {
		table.getColumnModel().getColumn(0)
		.setPreferredWidth(new MainPanel().WIDTH * 3 / 6);

		table.getColumnModel().getColumn(1)
				.setPreferredWidth(50);
		table.getColumnModel().getColumn(2)
				.setPreferredWidth(30);
		table.getColumnModel().getColumn(3)
				.setPreferredWidth(75);
		table.getColumnModel().getColumn(4)
				.setPreferredWidth(75);
/*		table.getColumnModel().getColumn(5)
		.setPreferredWidth(100);*/
		table.getTableHeader().setReorderingAllowed(false);
//		table.getTableHeader().setResizingAllowed(false);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
