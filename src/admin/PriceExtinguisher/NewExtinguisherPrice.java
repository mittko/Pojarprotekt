package admin.PriceExtinguisher;

import utils.MainPanel;

public class NewExtinguisherPrice extends MainPanel {
/*
	private JPanel basePanel = null;
	private JPanel northPanel = null;
	private JPanel centerPanel = null;
	private TooltipButton viewButton = null;
	private JComboBox<String> combo = null;
	private TooltipButton changeButton = null;
	private JScrollPane scroll = null;
	private JTable table = null;
	private DefaultTableModel dftm = null;
	
	private int CURRENT_ROW = 0;
	
	public NewExtinguisherPrice() {
		JTextField tableEditor = new JTextField();
		tableEditor.setDocument(new TextFieldLimit(5));
		
		basePanel = new JPanel();
		basePanel.setLayout(new BorderLayout());
		basePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT,20,10));
		
		centerPanel = new JPanel();
		
		viewButton = new TooltipButton();
		viewButton.setText("Виж текуща Цена");
		viewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(combo.getSelectedItem().equals("Вид")) {
					return;
				}
				
				TableModel tModel = table.getModel();
				if(!tModel.getValueAt(CURRENT_ROW, 3).toString().isEmpty()) {
					return;
			//		tModel.setValueAt("", CURRENT_ROW, 3);
				}
 				// see current price
				viewPriceWorker viewPrice = new viewPriceWorker(
						combo.getSelectedItem().toString(), // type
						tModel.getValueAt(CURRENT_ROW, 0).toString(), // wheight
						tModel.getValueAt(CURRENT_ROW, 1).toString(), // category
						tModel.getValueAt(CURRENT_ROW, 2).toString(), // brand
						CURRENT_ROW);
				viewPrice.execute();
				NewExtinguisherDB_Price  p = new NewExtinguisherDB_Price();
		//		TableModel tModel = table.getModel();
				for(int row = 0;row < table.getRowCount();row++) {
					p.setValues(NEW_EXT_PRICE, 
							combo.getSelectedItem().toString(),
							tModel.getValueAt(row, 0).toString(), // wheight
							tModel.getValueAt(row, 1).toString(), // category
							tModel.getValueAt(row, 2).toString(), // brand
							getRandomPrice());
				}
				System.out.println("done");
			}
			
		});
	//	viewButton.setPreferredSize(new Dimension(50,50));
		
		changeButton = new TooltipButton();
		changeButton.setText("Запази нова цена");
		changeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				TableModel tModel = table.getModel();
				try {
				Double d = Double.parseDouble(tModel.getValueAt(CURRENT_ROW, 3).toString());
				} catch(Exception e) {
					JOptionPane.showMessageDialog(null, "Невалидни данни!");
					return;
				}
				int yes_no = JOptionPane.showOptionDialog(null,
						"Желаете ли да съхраните въведените данни?", "",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, new String[] {
								"Да", "Не" }, // this is the array
						"default");
				if (yes_no == 0) {
					changePriceWorker changePrice = new changePriceWorker(
							combo.getSelectedItem().toString(),
							tModel.getValueAt(CURRENT_ROW, 0).toString(), 
							tModel.getValueAt(CURRENT_ROW, 1).toString(), 
							tModel.getValueAt(CURRENT_ROW, 2).toString(),
							tModel.getValueAt(CURRENT_ROW, 3).toString());
					changePrice.execute();
				}
			}
			
		});
		
		combo = new JComboBox<String>(new String[]{"Вид",type_Prah_BC,
				type_Prah_ABC,type_Water,type_Water_Fame,type_CO2});
		combo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				switch(combo.getSelectedItem().toString()) {
				case  type_Prah_BC : table.setModel(new NewPrahBCPriceModel()); break;
				case type_Prah_ABC : table.setModel(new NewPrahABCPriceModel()); break;
				case type_Water : table.setModel(new NewWaterPriceModel()); break;
				case type_Water_Fame : table.setModel(new NewWaterFamePriceModel()); break;
				case type_CO2 : table.setModel(new NewCO2PriceModel()); break;
				default : table.setModel(dftm); break;
				}
				
				table.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(tableEditor));
			}
			
		});
		northPanel.add(viewButton);
		northPanel.add(changeButton);
		northPanel.add(combo);
		
		dftm = new DefaultTableModel(new String[]{"Маса","Категория","Марка","Цена"},0) {};
		table = new JTable(dftm);
	
		table.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent me) {
				CURRENT_ROW = table.getSelectedRow();
			}
		});
		table.setDefaultRenderer(Object.class, new NewExtinguisherPriceRenderer());
	
		scroll = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		centerPanel.add(scroll);
		
		basePanel.add(northPanel,BorderLayout.NORTH);
		basePanel.add(scroll,BorderLayout.CENTER);
		
		
		
		this.add(basePanel);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
       SwingUtilities.invokeLater(new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			JFrame jf = new JFrame();
			NewExtinguisherPrice nep = new NewExtinguisherPrice();
			jf.add(nep);
			jf.pack();
			jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			jf.setVisible(true);
		  }
    	   
       });
	}
     private String getRandomPrice() {
    	 Random rnd = new Random();
    	 double price = rnd.nextDouble() * 50;
    	 return MyMath.round(price, 2) + "";
     }
     class viewPriceWorker extends SwingWorker {
        private String type = null;
        private String wheight = null;
        private String category = null;
        private String brand = null;
        int row = 0;
    	 public viewPriceWorker(String type,String wheight,String category,String brand,int row) {
    		 this.type = type;
    		 this.wheight = wheight;
    		 this.category = category;
    		 this.brand = brand;
    		 this.row = row;
    	 }
		@Override
		protected Void doInBackground() throws Exception {
			// TODO Auto-generated method stub
			String price = NewExtinguisherDB_Price.getPrice(NEW_EXT_PRICE, 
					type, wheight, category, brand);
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(price != null) {
						table.getModel().setValueAt(price, row, 3);
					}
				}
				
			});
			return null;
		}
    	 
     }
     class changePriceWorker extends SwingWorker {

    	  private String type = null;
          private String wheight = null;
          private String category = null;
          private String brand = null;
          private String newPrice = null;
      	 public changePriceWorker(String type,String wheight,String category,String brand,String newPrice) {
      		 this.type = type;
      		 this.wheight = wheight;
      		 this.category = category;
      		 this.brand = brand;
      		 this.newPrice = newPrice;
      	 }
		@Override
		protected Object doInBackground() throws Exception {
			// TODO Auto-generated method stub
			int updatePrice = NewExtinguisherDB_Price.setNewPrice(NEW_EXT_PRICE, 
					type, wheight, category, brand,newPrice);
			System.out.println(updatePrice);
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(updatePrice > 0) {
					JOptionPane.showMessageDialog(null, "Промените са записани успешно!");
					} else {
						
					}
				}
				
			});
			return null;
		}
    	 
     }*/
}
