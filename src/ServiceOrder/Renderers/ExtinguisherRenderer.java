package ServiceOrder.Renderers;

import java.awt.Component;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListCellRenderer;

public class ExtinguisherRenderer extends JLabel implements ListCellRenderer{
   
	ImageIcon icon1 = null;
	ImageIcon icon2 = null;
    ImageIcon[] icons = new ImageIcon[2];
    final static String  oldExt = "Въведен пожарогасител         ";
	final static String newExt = "Завеждане на пожарогасител";
	String[] text = {oldExt,newExt};
	
	public ExtinguisherRenderer() {
		icon1 = getIcon("oldExt.png");
		icon2 = getIcon("newExt.png");
		icons[0] =icon1;
		icons[1] = icon2;
		setOpaque(true);
		setHorizontalAlignment(CENTER);
		setVerticalAlignment(CENTER);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean hasFocus) {
		// TODO Auto-generated method stub
		
	     if (index == list.getLeadSelectionIndex()) {
	    	 setBackground(list.getSelectionBackground());
	    	 setForeground(list.getSelectionForeground());
	    	 } else {
	    	 setBackground(list.getBackground());
	    	 setForeground(list.getForeground());
	   	 }
	      switch(index) {
	      case 0 : setIcon(icons[0]);  setText(text[0]);break;
	      case 1 : setIcon(icons[1]);  setText(text[1]);break;
	      default :setIcon(icons[list.getSelectedIndex()]);  setText(text[list.getSelectedIndex()]);break;
	      }
		   
	
	     if(isSelected) {
	    	 setIcon(icons[list.getSelectedIndex()]);
		     setText(text[list.getSelectedIndex()]);
	     } else {
	    	
	     }
		return this;
	}
     private ImageIcon getIcon(String image) {
    	 URL url = null;
		 try {
		 url = getClass().getClassLoader().getResource("Images/"+image);
		 } finally {
			// setJavaIcon(image.substring(0,image.indexOf(".")));
			 if(url == null)
			 JOptionPane.showMessageDialog(null, "Грешка при зареждане на изображенията!");
		 }
		 return new ImageIcon(url);
     }
}
