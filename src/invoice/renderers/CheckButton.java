//package Invoice.InvoiceRenderer;
//
//import java.awt.Cursor;
//import java.awt.Dimension;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.ArrayList;
//
//import javax.swing.Icon;
//import javax.swing.JButton;
//
//import CommonResources.MainPanel;
//import CommonResources.MyMath;
//import InvoiceWindow.SearchPanels.searchFromProtokol;
//
//public class CheckButton extends JButton {
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	private boolean isAllowDiscount = false;
//	private  Icon allowIcon = null;
//	private  Icon disallowIcon = null;
//	private ArrayList<Double> DISCOUNTS = new ArrayList<Double>();
//	
//	public CheckButton() {
//		allowIcon = new MainPanel().setIcons(MainPanel.acceptImage);
//		disallowIcon = null;
//		this.setContentAreaFilled(false);
//        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        
//		this.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent me) {
//				if (!isEnabled()) {
//					return;
//				}
//			   // isAllowDiscount = !isAllowDiscount;
//				setSelected(isAllowDiscount);
//			}
//		});
//		this.setPreferredSize(new Dimension(45,45));
//		
//	}
//
//	/*public boolean isAllowDiscount() {
//		isAllowDiscount = !isAllowDiscount;
//		return isAllowDiscount;
//	}*/
//
//	public void setSelected(boolean flag) {
//		if (!flag) {
//			setIcon(allowIcon);
//			isAllowDiscount = true;
//			calcSum(-1);
//		} else {
//			setIcon(disallowIcon);
//			isAllowDiscount = false;
//			calcSum(1);
//		}
//	}
//
//	public boolean isSelected() {
//		return isAllowDiscount;
//	}
//	private void calcSum(int addOrSubstract) {
//		
//			DISCOUNTS.clear();
//			for(int row = 0;row <  searchFromProtokol.invoiceTableModel.getRowCount(); row++) {
//				double value = Double.parseDouble(searchFromProtokol.
//			    		  invoiceTableModel.getValueAt(row, 5).toString());
//		      DISCOUNTS.add(MyMath.round( value ,2));
//		    }
//		
//		
//		ArrayList<Object[]> helpList = new ArrayList<Object[]>();
//		ArrayList<String> data = new ArrayList<String>();
//	
//	
//		for(int row = 0;row <  searchFromProtokol.invoiceTableModel.getRowCount(); row++) {
//		    	data.clear();
//		 
//			   String doing = searchFromProtokol.invoiceTableModel.getValueAt(row, 0).toString();
//			   data.add(doing);
//			   
//			   String measure = searchFromProtokol.invoiceTableModel.getValueAt(row, 1).toString();
//			   data.add(measure);
//			   
//				int quant = Integer.parseInt(searchFromProtokol.invoiceTableModel
//						.getValueAt(row, 2).toString());  // quantity
//				data.add(quant+"");
//				
//				double valu = Double.parseDouble(searchFromProtokol.invoiceTableModel.getValueAt(row, 3).toString()) +
//						( addOrSubstract * ( DISCOUNTS.get(row) ) ) ; // value + (quant * discount)
//				
//				valu = MyMath.round(valu, 2);
//				
//				data.add(valu+"");
//				double sum = MyMath.round(quant * valu, 2);
//				data.add(sum+"");
//				
//			
//			helpList.add(data.toArray());
//		}
//	
//	
//		for (int row = 0; row < helpList.size(); row++) {
//			Object[] obj = helpList.get(row);
//			String value = obj[3].toString();
//			String sum = obj[4].toString();
//			searchFromProtokol.invoiceTableModel.setValueAt( value, row, 3);
//			searchFromProtokol.invoiceTableModel.setValueAt( sum, row, 4);
//			searchFromProtokol.invoiceTableModel.
//			setValueAt(DISCOUNTS.get(row)  ,row,5); // discount
//		}
//	  
//		searchFromProtokol.calcFinalSum();
//	}
//}
