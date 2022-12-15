package admin.AdminTableModel;

import db.PartsPrice.PriceTable;
import utility.MainPanel;
import utility.MyMath;

import javax.swing.table.DefaultTableModel;
import java.util.Random;

public class CO2Model extends DefaultTableModel {

	
	private static final Object[] parts = {
		MainPanel.BarzVentil1_Model,MainPanel.BarzVentil2_Model,MainPanel.BarzVentil3_Model,MainPanel.BarzVentil3Model_Prava_Rezba,MainPanel.Sphlend,
		MainPanel.Plomba,MainPanel.Snegoobrazuvatel1_Model,MainPanel.Snegoobrazuvatel2_Model,MainPanel.Snegoobrazuvatel3_Model,MainPanel.Sonda,
	    MainPanel.DrajkaZaPojarogasitel,MainPanel.ZapresovaneNaNakrainik,MainPanel.TvardoHodovoKolelo,MainPanel.KoleloZaVisokoTeglo,MainPanel.RemontKolicka,
	    MainPanel.BoyaKolichka,MainPanel.BoyaPojarogasitel,MainPanel.Etiket,
			MainPanel.SadZaGasitelnoVeshtestvo,MainPanel.GasitelnoVeshtestvoCO2,
			MainPanel.CenaPrezarejdane,MainPanel.CenaTehnichesko,MainPanel.CenaHidrostatichno,
			MainPanel.CenaPrezarejdaneITehnichesko,MainPanel.CenaPrezarejdaneTehnicheskoIHidrostatichno
	};
	private static final Object[][] weightAndCategory = {
		{"5","1.4 ��"},
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
	private static Object[][] reinitObj() {
		Object[][] newObj = new Object[(parts.length * weightAndCategory.length)][4];
		int p = 0;
		for (Object o : parts) {
			for (int j = 0; j < weightAndCategory.length; j++, p++) {
				newObj[p][0] = o;
				newObj[p][1] = MainPanel.type_CO2;
				newObj[p][2] = weightAndCategory[j][0];
				newObj[p][3] = weightAndCategory[j][1];
			}
		}
		return newObj;
	}
	public CO2Model() {
	   super(reinitObj(),new Object[]{"�������� �����","���","���","����","����"});
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

		String[] nowiArtikuli = {MainPanel.CenaPrezarejdane,
				MainPanel.CenaTehnichesko,
				MainPanel.CenaHidrostatichno,
				MainPanel.CenaPrezarejdaneITehnichesko,
				MainPanel.CenaPrezarejdaneTehnicheskoIHidrostatichno};
		for(String nowArtikul : nowiArtikuli) {
			for (Object[] objects : weightAndCategory) {
				PriceTable.initPartPriceTable(nowArtikul, MainPanel.type_CO2,
						objects[1].toString(), objects[0].toString(), 1.5);
			}
		}
	}

}
