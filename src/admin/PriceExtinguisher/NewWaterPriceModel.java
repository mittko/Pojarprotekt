package admin.PriceExtinguisher;

import javax.swing.table.DefaultTableModel;

public class NewWaterPriceModel extends DefaultTableModel {
	private static final Object[][] obj = {
		{"1","6 �����"},
		{"3","6 �����"},
		{"1","9 �����"},
		{"3","9 �����"},
		{"1","12 �����"},
		{"3","12 �����"},
		{"1","25 �����"},
		{"3","25 �����"},
   };
	public NewWaterPriceModel() {
		super(new String[]{"����","���������","�����","����"},0);
		Object[] brands = Local.TextReader.getData("Local/brand2.txt");
		for(int b = 1;b < brands.length;b++) {
			for(int row = 0;row < obj.length;row++) {
				this.addRow(new Object[]{obj[row][1],obj[row][0],brands[b],""});
			}
		}
	}
	
}
