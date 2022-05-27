package office.Renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class MyComboRenderer extends JLabel
implements ListCellRenderer<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel label;
	
	public MyComboRenderer() {
		
		this.setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value,
			int index, boolean isCellected, boolean cellHasFocus) {
		// TODO Auto-generated method stub
		
		DefaultListCellRenderer dfr = new DefaultListCellRenderer();
		label = (JLabel)dfr.getListCellRendererComponent(list, value, 
				index, isCellected, cellHasFocus);
		
	    if(index == 0) {
	    //	label.setBackground(Color.LIGHT_GRAY);
	    	label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	    	
	    }
		return label;
	}
	

}
