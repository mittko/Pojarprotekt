package utils;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;

import run.JustFrame;

public class OverridenComboBox extends JComboBox<Object>{

	DefaultComboBoxModel<Object> dcbm = null;
	public OverridenComboBox() {
		dcbm = new DefaultComboBoxModel<Object>();
		dcbm.addElement("Януари");
		dcbm.addElement("Февруари");
		dcbm.addElement("Март");
		dcbm.addElement("Април");
		dcbm.addElement("Май");
		dcbm.addElement("Юни");
		dcbm.addElement("Юли");
		dcbm.addElement("Август");
		dcbm.addElement("Септември");
		dcbm.addElement("Октомври");
		dcbm.addElement("Ноември");
		dcbm.addElement("Декември");
		this.setRenderer(new ComboRenderer());
		this.setModel(dcbm);
		
	}
	@Override
	public Color getBackground() {
		return Color.white;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OverridenComboBox ovcb = new OverridenComboBox();
        JustFrame f = new JustFrame(ovcb);
        f.pack();
	}
      class ComboRenderer extends DefaultListCellRenderer  {
    	  
    	  public ComboRenderer() {
    	
    	  }
    	 @Override
    	 public Component getListCellRendererComponent(JList list,Object value,int row,
    	   boolean isSelected,boolean cellHasFocus) {
    		 Component c = super.getListCellRendererComponent(list, value, row, isSelected, 
    				 cellHasFocus);
    		 if(list.getLeadSelectionIndex() == row) {
    			 c.setBackground(Color.ORANGE);
    		 } else {
    			 c.setBackground(Color.WHITE);
    		 }
    	//	 System.out.println(row);
    		 return c;
    	 }
		
      }
}
