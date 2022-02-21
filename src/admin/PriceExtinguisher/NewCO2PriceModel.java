package admin.PriceExtinguisher;

import javax.swing.table.DefaultTableModel;

public class NewCO2PriceModel extends DefaultTableModel {
		private static final Object[][] obj = {
			{"5","1.5 кг"},
			{"5","2 кг"},
			{"5","3 кг"},
			{"5","3.5 кг"},
			{"5","5 кг"},
			{"5","10 кг"},
			{"5","15 кг"},
			{"5","20 кг"},
			{"5","25 кг"},
			{"5","30 кг"},
			{"5","40 кг"},
			{"5","45 кг"}
	};
	public NewCO2PriceModel() {
		super(new String[]{"Маса","Категория","Марка","Цена"},0);
		Object[] brands = Local.TextReader.getData("Local/brand2.txt");
		for(int b = 1;b < brands.length;b++) {
			for(int row = 0;row < obj.length;row++) {
				this.addRow(new Object[]{obj[row][1],obj[row][0],brands[b],""});
			}
		}
	}
}
