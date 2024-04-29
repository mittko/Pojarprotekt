package invoice.renderers;

import utils.MainPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.Locale;


public class AutoSortRenderer extends DefaultTableCellRenderer {
   
    private Color background = null;
    private JTable refferenceTable = null;
	private final DecimalFormat format = new DecimalFormat("0.#");
	private Object helpQ;
    public AutoSortRenderer() {
    	background = Color.decode("0xD8E6FF");
    }
	public AutoSortRenderer(JTable refferenceTable) {
	 background = Color.decode("0xD8E6FF");
	 this.refferenceTable = refferenceTable;
	 setColumnsWidth(refferenceTable);
	}
	@Override
	public Component getTableCellRendererComponent(JTable table,Object value,
			boolean isSelected,boolean hasFocus,int row,int column) {
    	helpQ = value;
    	if(column == 1) {
    		value = value.toString().replace(",", ".");
			value = format.format(Double.parseDouble(value.toString()));
		}
		if(column == 3) {
			if(value.toString().contains(",")) {
				value = value.toString().replace(",", "."); // english comma
			}
			if(value.toString().contains(",")) { // bulgarian comma
				value = value.toString().replace(",", ".");
			}
			value = String.format(Locale.ROOT, "%.2f", Double.parseDouble(value.toString())); // set two decimals after points
			
		 }
		Component c = super.getTableCellRendererComponent(
				table, value, isSelected, hasFocus, row, column);
	
		if(row % 2 == 0) {
			c.setBackground(background);
			} else {
			c.setBackground(Color.white);
			}
		if(column == 0) {
			this.setHorizontalAlignment(JLabel.LEFT);
		} else {
			this.setHorizontalAlignment(JLabel.CENTER);
		}
	
		if(column == 1) {
			double quantity = Double.parseDouble(helpQ.toString().replace(",","."));
				if(quantity <= 5) {
					c.setForeground(Color.red);
		    	} else if(quantity > 5) {
				    c.setForeground(Color.green.darker().darker());
			    } else {
				    c.setForeground(Color.black);
			    }
		} else if(column == 3) {
			c.setForeground(Color.blue);
	   	}  else {
	   		c.setForeground(Color.black);
		}
		/*String wantedArtikul = SkladArtikulFrame.moveScrollField.getText();
		if(wantedArtikul.toString().length() > 0 && 
				table.getValueAt(row, 0).toString().toLowerCase().
				startsWith(wantedArtikul.toLowerCase()) ) {
			this.setBackground(Color.cyan);
		} else if(wantedArtikul.toString().length() > 0 && 
				table.getValueAt(row, 0).toString().toLowerCase().
				contains(wantedArtikul.toLowerCase())) {
			this.setBackground(Color.cyan);
		}*/
		if(isSelected) {
			c.setBackground(Color.yellow);
		}
		
		Boolean isCellSelected = (Boolean)table.getValueAt(row, 5);
		if(isCellSelected) {
			c.setBackground(Color.cyan);
		} 
		
		return c;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
    private void setColumnsWidth(JTable table) {
    	table.getTableHeader().setReorderingAllowed(false);
	//table.getTableHeader().setResizingAllowed(false);
	table.getColumnModel().getColumn(0).setPreferredWidth(150);
    	table.getTableHeader().getColumnModel().getColumn(0)
		.setPreferredWidth((new MainPanel().getWidth()) / 3);
       table.getTableHeader().getColumnModel().getColumn(1)
		.setPreferredWidth(50);
        table.getTableHeader().getColumnModel().getColumn(2)
	.setPreferredWidth(50);
        table.getTableHeader().getColumnModel().getColumn(3)
	.setPreferredWidth(100);
        table.getTableHeader().getColumnModel().getColumn(4)
		.setPreferredWidth(50);
       table.getTableHeader().getColumnModel().getColumn(5)
		.setPreferredWidth(50);
    }
}
