package admin.PriceExtinguisher;

import javax.swing.table.DefaultTableModel;

public class NewPrahABCPriceModel extends DefaultTableModel {
			private static final Object[][] obj = {
				{"2","1 ��"},
				{"4","1 ��"},
				{"2","2 ��"},
				{"4","2 ��"},
				{"2","3 ��"},
				{"4","3 ��"},
				{"2","6 ��"},
				{"4","6 ��"},
				{"2","9 ��"},
				{"4","9 ��"},
				{"2","12 ��"},
				{"4","12 ��"},
				{"2","25 ��"},
				{"4","25 ��"},
				{"2","50 ��"},
				{"4","50 ��"},
				{"2","100 ��"},
				{"4","100 ��"},
			
		};
		private Object[] brands = null;
		
		public NewPrahABCPriceModel() {
		super(new String[]{"����","���������","�����","����"},0);
		brands = Local.TextReader.getData("Local/brand2.txt");
		for(int b = 1;b < brands.length;b++) {
		for(int row = 0;row < obj.length;row++) {
			this.addRow(new Object[]{obj[row][1],obj[row][0],brands[b],""});
		  }
		}
}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
