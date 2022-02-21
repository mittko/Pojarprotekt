package admin.PriceExtinguisher;

import javax.swing.table.DefaultTableModel;

public class NewCO2PriceModel extends DefaultTableModel {
		private static final Object[][] obj = {
			{"5","1.5 ��"},
			{"5","2 ��"},
			{"5","3 ��"},
			{"5","3.5 ��"},
			{"5","5 ��"},
			{"5","10 ��"},
			{"5","15 ��"},
			{"5","20 ��"},
			{"5","25 ��"},
			{"5","30 ��"},
			{"5","40 ��"},
			{"5","45 ��"}
	};
	public NewCO2PriceModel() {
		super(new String[]{"����","���������","�����","����"},0);
		Object[] brands = Local.TextReader.getData("Local/brand2.txt");
		for(int b = 1;b < brands.length;b++) {
			for(int row = 0;row < obj.length;row++) {
				this.addRow(new Object[]{obj[row][1],obj[row][0],brands[b],""});
			}
		}
	}
}
