package acquittance.renderers;

import utils.MainPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Locale;


public class AcquittanceAutoSortRenderer extends DefaultTableCellRenderer {

    private Color background = null;

	public AcquittanceAutoSortRenderer() {
    	background = Color.decode("0xD8E6FF");
    }

	@Override
	public Component getTableCellRendererComponent(JTable table,Object value,
			boolean isSelected,boolean hasFocus,int row,int column) {
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
			int quantity = Integer.parseInt(value.toString());
				if(quantity <= 5) {
					c.setForeground(Color.red);
		    	} else {
				c.setForeground(Color.green.darker().darker());
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
}
