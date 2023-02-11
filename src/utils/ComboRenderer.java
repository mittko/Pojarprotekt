package utils;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ComboRenderer extends JLabel implements ListCellRenderer {

	private JLabel label;
	
	public ComboRenderer() {
		this.setOpaque(true);
	}
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		// TODO Auto-generated method stub
		
		DefaultListCellRenderer dlcr = new DefaultListCellRenderer();
		label = (JLabel)dlcr.getListCellRendererComponent(
				list, value, index, isSelected, cellHasFocus);
		label.setBorder(BorderFactory.createLineBorder(Color.black));
		if(index == 0) {
			label.setForeground(Color.WHITE);
			label.setBackground(Color.gray);
		} else 
		if(list.getLeadSelectionIndex() == index) {
			label.setForeground(Color.green);//(Color.orange);
			label.setBackground(Color.gray);
		} else {
			label.setOpaque(false);
		}
		return label;
	}

}
