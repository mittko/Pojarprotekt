package admin.PriceExtinguisher;

import javax.swing.table.DefaultTableModel;

public class NewPrahABCPriceModel extends DefaultTableModel {
			private static final Object[][] obj = {
				{"2","1 кг"},
				{"4","1 кг"},
				{"2","2 кг"},
				{"4","2 кг"},
				{"2","3 кг"},
				{"4","3 кг"},
				{"2","6 кг"},
				{"4","6 кг"},
				{"2","9 кг"},
				{"4","9 кг"},
				{"2","12 кг"},
				{"4","12 кг"},
				{"2","25 кг"},
				{"4","25 кг"},
				{"2","50 кг"},
				{"4","50 кг"},
				{"2","100 кг"},
				{"4","100 кг"},
			
		};
		private Object[] brands = null;
		
		public NewPrahABCPriceModel() {
		super(new String[]{"Маса","Категория","Марка","Цена"},0);
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
