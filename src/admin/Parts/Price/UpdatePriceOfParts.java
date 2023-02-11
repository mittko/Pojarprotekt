package admin.Parts.Price;

import run.JustFrame;
import utils.MainPanel;

import javax.swing.*;
import java.awt.*;


public class UpdatePriceOfParts extends MainPanel {

	JComboBox<Object> typeBox = null;
	JComboBox<Object> partsBox = null;
	DefaultComboBoxModel<Object> cmb = null;
	JPanel northPanel = null;
	

	ButtonPanel buttonPanel = null;
	TablePanel tablePanel = null;
	
	public UpdatePriceOfParts() {
		JPanel basePanel = new JPanel();
	//	basePanel.setLayout(new BorderLayout());
	
		northPanel = new JPanel();
	
		northPanel.setLayout(new BorderLayout());
		basePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		buttonPanel = new ButtonPanel();
	//	buttonPanel.setOpaque(false);
		
		tablePanel = new TablePanel();
		
		northPanel.add(buttonPanel,BorderLayout.NORTH);
		northPanel.add(tablePanel,BorderLayout.CENTER);
		
		basePanel.add(northPanel);
	
		
		this.add(basePanel);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
      SwingUtilities.invokeLater(new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			UpdatePriceOfParts update = new UpdatePriceOfParts();
			JustFrame f = new JustFrame(update);
			f.pack();
			f.setTitle("Update Price");
			f.setResizable(false);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
    	  
      });
	}

}
