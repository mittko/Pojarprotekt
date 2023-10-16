package admin.AdminTableModel;

import db.PartsPrice.PriceTable;
import utils.MainPanel;
import utils.MyMath;

import javax.swing.table.DefaultTableModel;
import java.util.Random;

public class DustModel extends DefaultTableModel {

	private static final Random rnd = new Random();

	private static final Object[] partsObject = new Object[]{
			MainPanel.Glava,MainPanel.Manometar,MainPanel.Sphlend,MainPanel.Uplatnenie,MainPanel.Zatvor
			,MainPanel.Plomba,MainPanel.Markuch,MainPanel.DarjachZaMarkuch,MainPanel.Prujina,MainPanel.Struinik,
			MainPanel.Patron,MainPanel.Sonda,MainPanel.Struinik4,MainPanel.BarbutajnaTraba,MainPanel.IglichkaZaPompane,
			MainPanel.KapachkaZaUplatnenie,MainPanel.TvardoHodovoKolelo,MainPanel.KoleloZaVisokoTeglo,MainPanel.RemontKolicka,MainPanel.BoyaPojarogasitel,
			MainPanel.BoyaKolichka,MainPanel.Etiket,MainPanel.SadZaGasitelnoVeshtestvo,MainPanel.PrahBC,MainPanel.PrahABC
			,MainPanel.CENA_TEHNICHESKO,MainPanel.CENA_PREZAREJDANE,MainPanel.CENA_HIDROSTATICHNO_IZPITVANE};

	private static final Object[][] obj = {
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
	private static final int var = obj.length;
	private static final int parts = partsObject.length;
	private static Object[][] reinitObj() {
		Object[][] newObj = new Object[(parts * var)][4];
		int p = 0;

		for(int i = 0;i < partsObject.length;i++) {
			for(int j = 0;j < var;j++,p++) {
				newObj[p][0] = partsObject[i];
				newObj[p][1] = MainPanel.type_Prah_BC;
				newObj[p][2] = obj[j][0];
				newObj[p][3] = obj[j][1];
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
		//	testInit();
      addNewArtikuls();
	}
	private static void addNewArtikuls() {
		String newArtikulToBeAdded = MainPanel.CENA_HIDROSTATICHNO_IZPITVANE;
		String forType = MainPanel.type_Prah_BC;
		double price = 0;//MyMath.round(rnd.nextDouble(), 2)
		for(int j = 0;j < obj.length;j++) {

			String weight = obj[j][1].toString();
			String category = obj[j][0].toString();

			PriceTable.initPartPriceTable(newArtikulToBeAdded, forType,
					weight, category, price);
		}
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
		for(int i = 0;i < partsObject.length;i++) {
			for(int j = 0;j < 2;j++) {
				PriceTable.initPartPriceTable(partsObject[i].toString(),MainPanel.type_Prah_BC,
						obj[j+6][1].toString(), obj[j+6][0].toString(), MyMath.round(rnd.nextDouble(), 2));
			}
		}
	}


}
