package admin.models;

import db.PartsPrice.PriceTable;
import utils.MainPanel;
import utils.MyMath;

import javax.swing.table.DefaultTableModel;
import java.util.Random;

public class CO2Model extends DefaultTableModel {

	private static final Random rnd = new Random();

	private static final Object[] partsObject = {
			MainPanel.BarzVentil1_Model,MainPanel.BarzVentil2_Model,MainPanel.BarzVentil3_Model,MainPanel.BarzVentil3Model_Prava_Rezba,MainPanel.Sphlend,
			MainPanel.Plomba,MainPanel.Snegoobrazuvatel1_Model,MainPanel.Snegoobrazuvatel2_Model,MainPanel.Snegoobrazuvatel3_Model,MainPanel.Sonda,
			MainPanel.DrajkaZaPojarogasitel,MainPanel.ZapresovaneNaNakrainik,MainPanel.TvardoHodovoKolelo,MainPanel.KoleloZaVisokoTeglo,MainPanel.RemontKolicka,
			MainPanel.BoyaKolichka,MainPanel.BoyaPojarogasitel,MainPanel.Etiket,MainPanel.SadZaGasitelnoVeshtestvo,
			MainPanel.GasitelnoVeshtestvoCO2,MainPanel.CENA_TEHNICHESKO,MainPanel.CENA_PREZAREJDANE
			,MainPanel.CENA_HIDROSTATICHNO_IZPITVANE
	};
	private static final Object[][] obj = {
			{"5","1.4 Í„"},
			{"5","2 Í„"},
			{"5","3 Í„"},
			{"5","3.5 Í„"},
			{"5","5 Í„"},
			{"5","10 Í„"},
			{"5","15 Í„"},
			{"5","20 Í„"},
			{"5","25 Í„"},
			{"5","30 Í„"},
			{"5","40 Í„"},
			{"5","45 Í„"}
};
	private static final int parts = partsObject.length;
	private static final int var = obj.length;
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
		super(reinitObj(),new Object[]{"–ÂÁÂ‚ÌË ˜‡ÒÚË","¬Ë‰"," ‡Ú","Ã‡Ò‡","÷ÂÌ‡"});
	}

	@Override
	public boolean isCellEditable(int row,int column) {
		return column >= 4;
	}

	public static void main(String[] args) {
		//	testInit();
		addNewArtikuls();
	}

	private static void addNewArtikuls() {
		String newArtikulToBeAdded = MainPanel.CENA_HIDROSTATICHNO_IZPITVANE;
		String forType = MainPanel.type_CO2;
		double price = 0;//MyMath.round(rnd.nextDouble(), 2)
		for (Object[] objects : obj) {

			String weight = objects[1].toString();
			String category = objects[0].toString();

			PriceTable.initPartPriceTable(newArtikulToBeAdded, forType,
					weight, category, price);
		}
	}

	static void testInit() {
		// WARNING !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		// here category(obj[j][0]) and wheight(obj[j][1]) values are swaped in init command ,
		// because there is wrong (swapped) populated data in db table

		for (Object o : partsObject) {
			for (int j = 0; j < var; j++) {
				PriceTable.initPartPriceTable(o.toString(), MainPanel.type_CO2,
						obj[j][1].toString(), obj[j][0].toString(), MyMath.round(rnd.nextDouble(), 2));
			}
		}
		System.out.println("done!");
	}

}
