package admin.Team.Renderer;

import utility.BevelLabel;
import utility.MainPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.net.URL;

public class TeamRenderer extends DefaultTableCellRenderer{
    private BevelLabel accessLabelIcon = null;
 
    private BevelLabel no_accessLabelIcon = null;
    
    public TeamRenderer(float height) {
    	accessLabelIcon = new BevelLabel(height);
    	accessLabelIcon.setText("");
    	
    	no_accessLabelIcon = new BevelLabel(height);
    	no_accessLabelIcon.setText("");
    
    	accessLabelIcon.setAutoSizedIcon(accessLabelIcon, getImageIcon(MainPanel.acceptImage));
    	no_accessLabelIcon.setAutoSizedIcon(no_accessLabelIcon, getImageIcon(MainPanel.noImage));
   
    }
	
	@Override
	public Component getTableCellRendererComponent(JTable table,Object obj,boolean isSelected,
			boolean hasFocus,int row,int column) {
		Component c = super.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, column);
	//	table.setBackground(c.getBackground());
		if(column > 1 && (boolean)table.getValueAt(row, column) == true) {
			return accessLabelIcon;
		} 
		return no_accessLabelIcon;
	}
	 public ImageIcon getImageIcon(String image) {
		 URL url = null;
		 try {
		 url = getClass().getClassLoader().getResource("Images/"+image);
		 } finally {
			// setJavaIcon(image.substring(0,image.indexOf(".")));
			 if(url == null)
			 JOptionPane.showMessageDialog(null, "Грешка при зареждане на изображенията!","Error",JOptionPane.ERROR_MESSAGE);
			 
		 }
		 return new ImageIcon(url);
	 }
}
