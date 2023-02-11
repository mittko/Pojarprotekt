package admin.AdminTableModel;

import db.PartsPrice.PriceTable;
import utils.MainPanel;
import utils.MyMath;

import javax.swing.table.DefaultTableModel;
import java.util.Random;

public class WaterModel extends DefaultTableModel {

	private static final int parts = 20;
	private static final int var = 6 * 2;
	private static final Random rnd = new Random();
	
	private static final Object[] partsObject =
		{MainPanel.Glava,MainPanel.Manometar,MainPanel.Sphlend,MainPanel.Uplatnenie,MainPanel.Zatvor,
		MainPanel.Plomba,MainPanel.Markuch,MainPanel.DarjachZaMarkuch,MainPanel.Prujina,MainPanel.Struinik,
		MainPanel.Patron,MainPanel.Sonda,MainPanel.Struinik4,MainPanel.BarbutajnaTraba,MainPanel.IglichkaZaPompane,
		MainPanel.KapachkaZaUplatnenie,MainPanel.BoyaPojarogasitel,MainPanel.Etiket,MainPanel.SadZaGasitelnoVeshtestvo,MainPanel.GasitelnoVeshtestvoVoda
		};
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
	private static Object[][] reinitObj() {
		Object[][] newObj = new Object[(parts * var)][4];
		int p = 0;
		for(int i = 0;i < partsObject.length;i++) {
			for(int j = 0;j < var;j++,p++) {
				newObj[p][0] = partsObject[i];
				newObj[p][1] = MainPanel.type_Water;
				newObj[p][2] = obj[j][0];
				newObj[p][3] = obj[j][1];
			}
		}
		return newObj;
	}
	public WaterModel() {
	   super(reinitObj(),new Object[]{"Резервни части","Вид","Кат","Маса","Цена"});
	}
	@Override
	public boolean isCellEditable(int row,int column) {
		if(column == 4) {
			return true;
		}
		return false;
	}
	public static void main(String[] args) {
	 	testInit();
	//	Add();
	}
	   static void Add() {
		   // init additional elements int db
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
					PriceTable.initPartPriceTable(partsObject[i].toString(),MainPanel.type_Water, 
							addObj[j][1].toString(), addObj[j][0].toString(), 0);
				}
			}
			System.out.println("done!");
		
	   }
	static void testInit() {
		// WARNING !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 	
		// here category(obj[j][0]) and wheight(obj[j][1]) values are swaped in init command ,
		 // because there is wrong (swapped) populated data in db table
		
		for(int i = 0;i < partsObject.length;i++) {
			for(int j = 0;j < var;j++) {
				PriceTable.initPartPriceTable(partsObject[i].toString(),MainPanel.type_Water, 
						obj[j][1].toString(), obj[j][0].toString(),  MyMath.round(rnd.nextDouble(), 2));
			}
		}
		System.out.println("done!");
	}
}
