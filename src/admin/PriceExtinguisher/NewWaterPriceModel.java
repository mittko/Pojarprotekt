package admin.PriceExtinguisher;

import javax.swing.table.DefaultTableModel;

public class NewWaterPriceModel extends DefaultTableModel {
	private static final Object[][] obj = {
		{"1","6 литра"},
		{"3","6 литра"},
		{"1","9 литра"},
		{"3","9 литра"},
		{"1","12 литра"},
		{"3","12 литра"},
		{"1","25 литра"},
		{"3","25 литра"},
   };
	public NewWaterPriceModel() {
		super(new String[]{"Маса","Категория","Марка","Цена"},0);
		Object[] brands = Local.TextReader.getData("Local/brand2.txt");
		for(int b = 1;b < brands.length;b++) {
			for(int row = 0;row < obj.length;row++) {
				this.addRow(new Object[]{obj[row][1],obj[row][0],brands[b],""});
			}
		}
	}
	
}
