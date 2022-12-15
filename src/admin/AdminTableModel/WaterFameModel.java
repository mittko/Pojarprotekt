package admin.AdminTableModel;

import db.PartsPrice.PriceTable;
import utility.MainPanel;
import utility.MyMath;

import javax.swing.table.DefaultTableModel;
import java.util.Random;

public class WaterFameModel extends DefaultTableModel{

	private static final Object[] parts =
		{MainPanel.Glava,MainPanel.Manometar,MainPanel.Sphlend,MainPanel.Uplatnenie,MainPanel.Zatvor,
			MainPanel.Plomba,MainPanel.Markuch,MainPanel.DarjachZaMarkuch,MainPanel.Prujina,MainPanel.Struinik,
			MainPanel.Patron,MainPanel.Sonda,MainPanel.Struinik4,MainPanel.BarbutajnaTraba,MainPanel.IglichkaZaPompane,
			MainPanel.KapachkaZaUplatnenie,MainPanel.BoyaPojarogasitel,MainPanel.Etiket,MainPanel.SadZaGasitelnoVeshtestvo,
			MainPanel.GasitelnoVeshtestvoVodaPyana,
				MainPanel.CenaPrezarejdane,MainPanel.CenaTehnichesko,MainPanel.CenaHidrostatichno,
				MainPanel.CenaPrezarejdaneITehnichesko,MainPanel.CenaPrezarejdaneTehnicheskoIHidrostatichno};
	
	private static final Object[][] weightAndCategory = {
		{"1","6 �����"},
		{"3","6 �����"},
		{"1","9 �����"},
		{"3","9 �����"},
		{"1","12 �����"},
		{"3","12 �����"},
		{"1","25 �����"},
		{"3","25 �����"},
		{"1","50 �����"},
		{"3","50 �����"},
		{"1","100 �����"},
		{"3","100 �����"},
};
	private static Object[][] reinitObj() {
		Object[][] newObj = new Object[(parts.length * weightAndCategory.length)][4];
		int p = 0;
		for (Object part : parts) {
			for (int j = 0; j < weightAndCategory.length; j++, p++) {
				newObj[p][0] = part;
				newObj[p][1] = MainPanel.type_Water_Fame;
				newObj[p][2] = weightAndCategory[j][0];
				newObj[p][3] = weightAndCategory[j][1];
			}
		}
		return newObj;
	}
	public WaterFameModel() {
	   super(reinitObj(),new Object[]{"�������� �����","���","���","����","����"});
	}
	@Override
	public boolean isCellEditable(int row,int column) {
		return column == 4;
	}
	public static void main(String[] args) {
		testInit();
	//	Add();
	}
	 static void Add() {
		 // init additional elements into db
		// WARNING !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 	
			// here category(obj[j][0]) and wheight(obj[j][1]) values are swaped in init command ,
			 // because there is wrong (swapped) populated data in db table
			
			Object[][] addObj = {
				{"1","50 �����"},
				{"3","50 �����"},
				{"1","100 �����"},
				{"3","100 �����"},
			};
		   for(int i = 0;i < parts.length - 1;i++) {
				for(int j = 0;j < 4;j++) {
					PriceTable.initPartPriceTable(parts[i].toString(),MainPanel.type_Water_Fame,
							addObj[j][1].toString(), addObj[j][0].toString(), 0);
				}
			}
			System.out.println("done!");
		
	   }
	static void testInit() {
		// WARNING !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 	
		// here category(obj[j][0]) and wheight(obj[j][1]) values are swaped in init command ,
		 // because there is wrong (swapped) populated data in db table

//		for (Object o : partsObject) {
//			for (int j = 0; j < var; j++) {
//				PriceTable.initPartPriceTable(o.toString(), MainPanel.type_Water_Fame,
//						obj[j][1].toString(), obj[j][0].toString(), MyMath.round(rnd.nextDouble(), 2));
//			}
//		}
//		System.out.println("done!");

		// �������� �� ��� ������� �� ��� �����
		String[] nowiArtikuli = {MainPanel.CenaPrezarejdane,
				MainPanel.CenaTehnichesko,
				MainPanel.CenaHidrostatichno,
				MainPanel.CenaPrezarejdaneITehnichesko,
				MainPanel.CenaPrezarejdaneTehnicheskoIHidrostatichno};
		for(String nowArtikul : nowiArtikuli) {
			for (Object[] objects : weightAndCategory) {
				PriceTable.initPartPriceTable(nowArtikul, MainPanel.type_Water_Fame,
						objects[1].toString(), objects[0].toString(), 1.5);
			}
		}
	}

}
