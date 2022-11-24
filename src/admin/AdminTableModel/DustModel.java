package admin.AdminTableModel;

import db.PartsPrice.PriceTable;
import utility.MainPanel;
import utility.MyMath;

import javax.swing.table.DefaultTableModel;
import java.util.Random;

public class DustModel extends DefaultTableModel {

	
    private static final Object[] parts = new Object[]{
    		MainPanel.Glava,MainPanel.Manometar,MainPanel.Sphlend,MainPanel.Uplatnenie,MainPanel.Zatvor
    		,MainPanel.Plomba,MainPanel.Markuch,MainPanel.DarjachZaMarkuch,MainPanel.Prujina,MainPanel.Struinik,
    		MainPanel.Patron,MainPanel.Sonda,MainPanel.Struinik4,MainPanel.BarbutajnaTraba,MainPanel.IglichkaZaPompane,
    		MainPanel.KapachkaZaUplatnenie,MainPanel.TvardoHodovoKolelo,MainPanel.KoleloZaVisokoTeglo,MainPanel.RemontKolicka,MainPanel.BoyaPojarogasitel,
    		MainPanel.BoyaKolichka,MainPanel.Etiket,MainPanel.SadZaGasitelnoVeshtestvo,MainPanel.PrahBC,
			MainPanel.PrahABC, MainPanel.CenaPrezarejdane,MainPanel.CenaTehnichesko,MainPanel.CenaHidrostatichno,
	        MainPanel.CenaPrezarejdaneITehnichesko,MainPanel.CenaPrezarejdaneTehnicheskoIHidrostatichno};
    
	private static final Object[][] weightAndCategory = {
			{"2","1 ��"},
			{"4","1 ��"},
			{"2","2 ��"},
			{"4","2 ��"},
			{"2","3 ��"},
			{"4","3 ��"},
			{"2","4 ��"},
			{"4","4 ��"},
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

	private static Object[][] reinitObj() {
		Object[][] newObj = new Object[(parts.length * weightAndCategory.length)][4];
		int p = 0;

		for (Object o : parts) {
			for (int j = 0; j < weightAndCategory.length; j++, p++) {
				newObj[p][0] = o;
				newObj[p][1] = MainPanel.type_Prah_BC;
				newObj[p][2] = weightAndCategory[j][0];
				newObj[p][3] = weightAndCategory[j][1];
			}
		}
		return newObj;
	}
	public DustModel() {
		   super(reinitObj(),new Object[]{"�������� �����","���","���","����","����"});
		  
		
	}
	@Override
	public boolean isCellEditable(int row,int column) {
		return column == 4;
	}
	public static void main(String[] args) {
		testInit();
	
	}
	
	static void testInit() {
		// WARNING !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 	
		// here category(obj[j][0]) and wheight(obj[j][1]) values are swaped in init command ,
		// because there is wrong (swapped) populated data in db table
		
	/*	for(int i = 0;i < partsObject.length;i++) {
			for(int j = 0;j < var-2;j++) {
				PriceTable.initPartPriceTable(partsObject[i].toString(),MainPanel.type_Prah_BC, 
						obj[j][1].toString(), obj[j][0].toString(), MyMath.round(rnd.nextDouble(), 2));
			}
		}
		System.out.println("done!");*/

		// init additional parts (for example 4 kg)
//		for (Object o : partsObject) {
//			for (int j = 0; j < 2; j++) {
//				PriceTable.initPartPriceTable(o.toString(), MainPanel.type_Prah_BC,
//						obj[j + 6][1].toString(), obj[j + 6][0].toString(), MyMath.round(rnd.nextDouble(), 2));
//			}
//		}

//		// �������� �� ��� ������� �� ��� ���� ��
		String[] nowiPovarogasiteli = {
				MainPanel.CenaPrezarejdane,
				MainPanel.CenaTehnichesko,
				MainPanel.CenaHidrostatichno,
				MainPanel.CenaPrezarejdaneITehnichesko,
				MainPanel.CenaPrezarejdaneTehnicheskoIHidrostatichno};

		for (String nowPovarogasitel : nowiPovarogasiteli) {
			for (Object[] objects : weightAndCategory) {
				PriceTable.initPartPriceTable(nowPovarogasitel, MainPanel.type_Prah_BC,
						objects[1].toString(), objects[0].toString(), 1.5);
			}

		}
	}
   
}
