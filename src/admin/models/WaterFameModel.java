package admin.models;

import db.PartsPrice.PriceTable;
import utils.MainPanel;
import utils.MyMath;

import javax.swing.table.DefaultTableModel;
import java.util.Random;

public class WaterFameModel extends DefaultTableModel{

	private static final Random rnd = new Random();

	private static final Object[] partsObject =
			{MainPanel.Glava,MainPanel.Manometar,MainPanel.Sphlend,MainPanel.Uplatnenie,MainPanel.Zatvor,
					MainPanel.Plomba,MainPanel.Markuch,MainPanel.DarjachZaMarkuch,MainPanel.Prujina,MainPanel.Struinik,
					MainPanel.Patron,MainPanel.Sonda,MainPanel.Struinik4,MainPanel.BarbutajnaTraba,MainPanel.IglichkaZaPompane,
					MainPanel.KapachkaZaUplatnenie,MainPanel.BoyaPojarogasitel,MainPanel.Etiket,MainPanel.SadZaGasitelnoVeshtestvo,
					MainPanel.GasitelnoVeshtestvoVodaPyana,MainPanel.CENA_TEHNICHESKO,MainPanel.CENA_PREZAREJDANE
					,MainPanel.CENA_HIDROSTATICHNO_IZPITVANE};

	private static final Object[][] obj = {
			{"1","6 литра"},
			{"3","6 литра"},
			{"1","9 литра"},
			{"3","9 литра"},
			{"1","12 литра"},
			{"3","12 литра"},
			{"1","25 литра"},
			{"3","25 литра"},
			{"1","50 литра"},
			{"3","50 литра"},
			{"1","100 литра"},
			{"3","100 литра"},
};

	private static final int parts = partsObject.length;
	private static final int var = obj.length;;

	private static Object[][] reinitObj() {
		Object[][] newObj = new Object[(parts * var)][4];
		int p = 0;
		for (Object o : partsObject) {
			for (int j = 0; j < var; j++, p++) {
				newObj[p][0] = o;
				newObj[p][1] = MainPanel.type_Water_Fame;
				newObj[p][2] = obj[j][0];
				newObj[p][3] = obj[j][1];
			}
		}
		return newObj;
	}

	public WaterFameModel() {
		super(reinitObj(),new Object[]{"Резервни части","Вид","Кат","Маса","Цена"});
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
		String forType = MainPanel.type_Water_Fame;
		double price = 0;//MyMath.round(rnd.nextDouble(), 2)
		for (Object[] objects : obj) {

			String weight = objects[1].toString();
			String category = objects[0].toString();

			PriceTable.initPartPriceTable(newArtikulToBeAdded, forType,
					weight, category, price);
		}
	}

	static void Add() {
		// init additional elements into db
		// WARNING !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		// here category(obj[j][0]) and wheight(obj[j][1]) values are swaped in init command ,
		// because there is wrong (swapped) populated data in db table

		Object[][] addObj = {
				{"1","50 литра"},
				{"3","50 литра"},
				{"1","100 литра"},
				{"3","100 литра"},
		};
		for(int i = 0;i < partsObject.length - 1;i++) {
			for(int j = 0;j < 4;j++) {
				PriceTable.initPartPriceTable(partsObject[i].toString(),MainPanel.type_Water_Fame,
						addObj[j][1].toString(), addObj[j][0].toString(), 0);
			}
		}
		System.out.println("done!");

	}

	static void testInit() {
		// WARNING !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		// here category(obj[j][0]) and wheight(obj[j][1]) values are swaped in init command ,
		// because there is wrong (swapped) populated data in db table

		for (Object o : partsObject) {
			for (int j = 0; j < var; j++) {
				PriceTable.initPartPriceTable(o.toString(), MainPanel.type_Water_Fame,
						obj[j][1].toString(), obj[j][0].toString(), MyMath.round(rnd.nextDouble(), 2));
			}
		}
		System.out.println("done!");
	}

}
