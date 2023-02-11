package utils;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingWorker;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import db.PartsPrice.PriceTable;

public class CustomButton extends DynamicButton implements ActionListener{
	
    public boolean choiced = false;
    public boolean isEditable = true;
    public  Border choiceBorder = new LineBorder(Color.RED,(MainPanel.getFontSize() + 1) / 3);
    public  Border defaultBorder = null;
    
    private CustomTooltip ct = null;
    
    private double price;
  
    
	public CustomButton() {
		super();
		this.addActionListener(this);
		defaultBorder = this.getBorder();
		this.setEnabled(false);
	}
	
	
	public double getPrice() {
		return this.price;
	}

	/*public double[] getQuantityAndPrice() {
		return this.price;
	}*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
 /*      
       SwingUtilities.invokeLater(new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			CustomButton cm = new CustomButton();
			cm.setIcon(new ImageIcon("src/Images/parts.png"));
			Framer f = new Framer(cm);
			f.pack();
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
    	   
       });*/
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(isEditable == false) { // не може да се редактира
			return;
		}
 
		  if(!choiced) {
			//    price = getPriceofPartsFromDB(Worker.tModel);
				this.setBorder(choiceBorder);
			} else {
			this.setBorder(defaultBorder);
		}
		choiced = !choiced;
		
	}

/*	@Override
	public JToolTip createToolTip() {
		if(ct == null) {
			ct = new CustomTooltip();
			ct.setComponent(this);
		}
		return ct;
	}*/
	
	public double getPriceofPartsFromDB(DefaultTableModel dftm) {
	
		String partName = this.getName(); // part's name
		String type = dftm.getValueAt(0, 1).toString(); // type
		String wheight = dftm.getValueAt(0, 2).toString(); // wheight
		String category = dftm.getValueAt(0, 5).toString(); // category
        SW sw  =null;
        double p = 0;
		
		    sw = new SW(partName,type,wheight,category);
			try {
				p = sw.doInBackground();
				} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return p;
	}
	
	 class SW extends SwingWorker<Double,Double> {

		 String part;
		 String type;
		 String category;
		 String wheight;
		 double backPrice;
		 
		 public SW(String part,String type,String wheight,String category) {
			 this.part = part;
			 this.type = type;
			 this.category = category;
			 this.wheight = wheight;
		 }
		@Override
		protected Double doInBackground() throws Exception {
			// TODO Auto-generated method stub
		   backPrice = PriceTable.getPartPriceFromDB(part, type, category,wheight);
		   return backPrice;
		}
		
	};
}
