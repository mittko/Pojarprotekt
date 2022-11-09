package admin.AdminTableModel;

import db.PartsPrice.PriceTable;
import utility.MainPanel;
import utility.MyMath;

import javax.swing.table.DefaultTableModel;
import java.util.Random;

public class CO2Model extends DefaultTableModel {
	private static final int parts = 21;
	private static final int var = 12 * 1;
	private static final Random rnd = new Random();
	
	private static final Object[] partsObject = {
		MainPanel.BarzVentil1_Model,MainPanel.BarzVentil2_Model,MainPanel.BarzVentil3_Model,MainPanel.BarzVentil3Model_Prava_Rezba,MainPanel.Sphlend,
		MainPanel.Plomba,MainPanel.Snegoobrazuvatel1_Model,MainPanel.Snegoobrazuvatel2_Model,MainPanel.Snegoobrazuvatel3_Model,MainPanel.Sonda,
	    MainPanel.DrajkaZaPojarogasitel,MainPanel.ZapresovaneNaNakrainik,MainPanel.TvardoHodovoKolelo,MainPanel.KoleloZaVisokoTeglo,MainPanel.RemontKolicka,
	    MainPanel.BoyaKolichka,MainPanel.BoyaPojarogasitel,MainPanel.Etiket,
			MainPanel.SadZaGasitelnoVeshtestvo,MainPanel.GasitelnoVeshtestvoCO2,
			MainPanel.CenaPrezarejdane,MainPanel.CenaTehnichesko,MainPanel.CenaHidrostatichno, MainPanel.CenaPrezarejdaneITehnichesko,MainPanel.CenaPrezarejdaneTehnicheskoIHidrostatichno
	};
	private static final Object[][] obj = {
		{"5","1.4 кг"},
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
	private static Object[][] reinitObj() {
		Object[][] newObj = new Object[(parts * var)][4];
		int p = 0;
		for (Object o : partsObject) {
			for (int j = 0; j < var; j++, p++) {
				newObj[p][0] = o;
				newObj[p][1] = MainPanel.type_CO2;
				newObj[p][2] = obj[j][0];
				newObj[p][3] = obj[j][1];
			}
		}
		return newObj;
	}
	public CO2Model() {
	   super(reinitObj(),new Object[]{"Резервни части","Вид","Кат","Маса","Цена"});
	}
	@Override
	public boolean isCellEditable(int row,int column) {
		return column >= 4;
	}
	public static void main(String[] args) {
		testInit();
	}
	static void testInit() {
		// WARNING !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 	
		// here category(obj[j][0]) and wheight(obj[j][1]) values are swapped in init command ,
	 // because there is wrong (swapped) populated data in db table

//		for (Object o : partsObject) {
//			for (int j = 0; j < var; j++) {
//				PriceTable.initPartPriceTable(o.toString(), MainPanel.type_CO2,
//						obj[j][1].toString(), obj[j][0].toString(), MyMath.round(rnd.nextDouble(), 2));
//			}
//		}
//		System.out.println("done!");

		// Добавяне на нов артикул за тип Воден
		for (Object[] objects : obj) {
			PriceTable.initPartPriceTable(MainPanel.CenaPrezarejdane, MainPanel.type_CO2,
					objects[1].toString(), objects[0].toString(), 0);
		}
	}

}
