/*package admin.SkladExtinguisher;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import CommonResources.MainPanel;
import CommonResources.MyCombo3;
import ServiceOrder.ServiceModels.Cat1_3Model;
import ServiceOrder.ServiceModels.Cat2_4Model;
import ServiceOrder.ServiceModels.Cat5Model;
import ServiceOrder.ServiceModels.WheighDustModel;
import ServiceOrder.ServiceModels.WheighWaterModel;
import ServiceOrder.ServiceModels.WheightCO2Model;
import db.NewExtinguisher.NewExtinguishers_DB;

public class SetPriceOfNewExtinguisher extends MainPanel {

	   
	   private JPanel basePanel = null;
	   
	   private JLabel typeLabel = null;
	
	   private final String[] types = new String[]{"Вид",
			   type_Prah_ABC,type_Prah_BC,type_Water,type_Water_Fame,type_CO2};
	   private JComboBox<String> typeCombo = null;
	   private JLabel wheightLabel = null;
	   private final WheighDustModel wheightDustModel = new WheighDustModel();
	   private final WheighWaterModel wheightWaterModel = new WheighWaterModel();
	   private final WheightCO2Model wheightCO2Model = new WheightCO2Model();
	   private JComboBox<String> wheightCombo = null;
	   private JLabel categoryLabel = null;
	   private final Cat1_3Model cat1_3 = new Cat1_3Model();
	   private final Cat2_4Model cat2_4 = new Cat2_4Model();
	   private final Cat5Model cat5 = new Cat5Model();
	   private JComboBox<String> categoryCombo = null;
	   private JLabel brandlabel = null;
	   private MyCombo3 brandCombo = null;
	   private JLabel priceLabel = null;
       private JTextField priceField = null;
	   private JButton importButton = null;
	   
	   private final DefaultComboBoxModel emptyModel = new DefaultComboBoxModel();
	   
	   public SetPriceOfNewExtinguisher() {
		   basePanel = new JPanel();//GradientPanel();
		   basePanel.setLayout(new GridBagLayout());
		   basePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		   basePanel.setPreferredSize(new Dimension((this.WIDTH * 2) / 3,this.HEIGHT-300));
		   
		   typeLabel = new JLabel("Вид");
		   
		   typeCombo = new JComboBox<String>(types);
		   typeCombo.setEditable(true);
		   
		   typeCombo.setPreferredSize(new Dimension(this.WIDTH / 3,35));
		   typeCombo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String item = typeCombo.getSelectedItem().toString();
				if(item.equals(types[1]) || item.equals(types[2])) {
			          categoryCombo.setModel(cat2_4);
			          wheightCombo.setModel(wheightDustModel);
				} else if(item.equals(types[3])  || item.equals(types[4])) {
					  categoryCombo.setModel(cat1_3);
					  wheightCombo.setModel(wheightWaterModel);
				} else if(item.equals(types[5])) {
					  categoryCombo.setModel(cat5);
					  wheightCombo.setModel(wheightCO2Model);
				} 
				
				
			}
			   
		   });
		   
		   wheightLabel = new JLabel("Маса");
		   
		   wheightCombo = new JComboBox<String>(new String[]{"Маса"});
		   wheightCombo.setEditable(true);
		   
		   wheightCombo.setPreferredSize(new Dimension(150,35));
		   
		   categoryLabel = new JLabel("Категория");
		   
		   categoryCombo = new JComboBox<String>(new String[]{"Категория"});
		   categoryCombo.setEditable(true);
		   
		   categoryCombo.setPreferredSize(new Dimension(150,35));
		   
		   brandlabel = new JLabel("Марка");
		   
	//	   Object[] brands = TextReader.getData("Local/brand2.txt");
		   
		//   DefaultComboBoxModel<String> cbm = new DefaultComboBoxModel(brands);
		   
		   brandCombo = new MyCombo3();//new JComboBox<String>();
		   brandCombo.setEditable(true);
	//	   brandCombo.setModel(cbm);
		   
		   brandCombo.setPreferredSize(new Dimension(200,35));

	       priceLabel = new JLabel("Ед. Цена -> ");
		   
		   priceField = new JTextField(5);
		   priceField.setForeground(Color.red);
		   
		   importButton = new JButton();
		   importButton.setText("Запиши");
		   importButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(!checkInput()) {
					return;
				}
				  final JDialog jd = (JDialog)SwingUtilities.getWindowAncestor(SetPriceOfNewExtinguisher.this);
				    jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				   
				setPriceWorker iw = new setPriceWorker(typeCombo.getSelectedItem().toString(),
						wheightCombo.getSelectedItem().toString(),categoryCombo.getSelectedItem().toString(),
						brandCombo.getSelectedItem().toString(),priceField.getText());
				iw.execute();
			}
			   
		   });
		   int[] x = {0,0,1,1,0,0,1,1,0,1,0,1,0};
		   int[] y = {0,1,0,1,2,3,2,3,4,4,5,5,6};
		   
		   GridBagConstraints[] gbc = new GridBagConstraints[x.length];
		   for(int i = 0;i < x.length;i++) {
			   gbc[i] = new GridBagConstraints();
			   gbc[i].gridx = x[i];
			   gbc[i].gridy = y[i];
			   gbc[i].insets = new Insets(10,10,10,10);
		   }
		   
		   basePanel.add(typeLabel,gbc[0]);
		   basePanel.add(typeCombo, gbc[1]);
		   basePanel.add(wheightLabel, gbc[2]);
		   basePanel.add(wheightCombo, gbc[3]);
		   basePanel.add(categoryLabel, gbc[4]);
		   basePanel.add(categoryCombo,gbc[5]);
		   basePanel.add(brandlabel, gbc[6]);
		   basePanel.add(brandCombo, gbc[7]);
		
		   basePanel.add(priceLabel, gbc[8]);
		   basePanel.add(priceField, gbc[9]);
		   basePanel.add(importButton, gbc[10]);
		   
		   this.add(basePanel);
	   }
	   private boolean checkInput() {
			if(typeCombo.getSelectedIndex() == 0) {
				JOptionPane.showMessageDialog(null, "Не е избран вид");
				return false;
			}
			if(wheightCombo.getSelectedIndex() == 0) {
				JOptionPane.showMessageDialog(null, "Не е избрана маса");
				return false;
			}
			if(categoryCombo.getSelectedIndex() == 0) {
				JOptionPane.showMessageDialog(null, "Не е избрана категория");
				return false;
			}
			if(brandCombo.getSelectedIndex() == 0) {
				JOptionPane.showMessageDialog(null, "Не е избрана марка");
				return false;
			}
		
			try {
				Double checkPrice = Double.parseDouble(priceField.getText());
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Грешно въведена цена!");
					return false;
				}
			return true;
		}
	   
	   
	   class setPriceWorker extends SwingWorker {

		  private String type = null;
		  private String wheight = null;
		  private String category = null;
		  private String brand = null;
		  private String price = null;
		  private int update = 0;
		  
		  public setPriceWorker(String type,String wheight,String category,String brand,String price) {
			  this.type = type;
			  this.wheight = wheight;
			  this.category = category;
			  this.brand = brand;
			  this.price = price;
		  }
		@Override
		protected Object doInBackground() throws Exception {
			// TODO Auto-generated method stub
			  
			    try {
			 
				update = NewExtinguishers_DB.updatePrice(NEW_EXTINGUISHERS, type, wheight, category, brand, price);
			    } finally {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						  final JDialog jd = (JDialog)SwingUtilities.getWindowAncestor(SetPriceOfNewExtinguisher.this);
						    jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
						   
						if(update > 0) {
							JOptionPane.showMessageDialog(null, "Данните са записани успешно!");
						}
						 jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					}
					
				});
			}
			return null;
		}
		
	 }
	  

}
*/