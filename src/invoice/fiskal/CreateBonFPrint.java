package invoice.fiskal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import static utils.MainPanel.DOCUMENTS_PATH;

public class CreateBonFPrint extends CreateBon {
	private final String comma = ",";
	private final String semicolon = ";";
	private final String logicalNumber = "1";
	private final String defaultField = "______,_,__";
	private final String shtand = "1";
	private final String openInvoice = "0";

	private final String PAYMENT_CODE = "0";

	private final String TAX = "C";
	private final String addValue = "2";
	private final String substractValue = "1";
	private final String надбавка = "20.00";

	public CreateBonFPrint() {

	}

	public ArrayList<String> makeReciept(DefaultTableModel dftm) {
		ArrayList<String> sellsList = new ArrayList<String>();
		sellsList.add(openBon());
		/*
		 * for(int row = 0;row < dftm.getRowCount();row++) { String obslujvane =
		 * dftm.getValueAt(row, 0).toString();
		 * 
		 * if(obslujvane.contains("Техническо") &&
		 * obslujvane.contains("Презареждане") &&
		 * obslujvane.contains("Хидростатично")) { obslujvane =
		 * obslujvane.replace
		 * ("Техническо обслужване, Презареждане и Хидростатично изпитване",
		 * "Хидростатично изпитване"); System.out.println(obslujvane); }else
		 * if(obslujvane.contains("Техническо") &&
		 * obslujvane.contains("Презареждане") &&
		 * !obslujvane.contains("Хидростатично")) { obslujvane =
		 * obslujvane.replace("Техническо обслужване и Презареждане " ,
		 * "Техническо обслужване "); System.out.println(obslujvane); } else {
		 * System.out.println("err"); } sellsList.add(makeReciept(
		 * dftm.getValueAt(row, 0).toString(), dftm.getValueAt(row,
		 * 3).toString(), // edininichna cena dftm.getValueAt(row,
		 * 2).toString())); // quantity }
		 */

		// sellsList.add(addTax()); НАДБАВКА ПО СУМА
		sellsList.add(makeReciept("", // услуги
				"4.0", // edininichna cena
				"1")); // quantity
		sellsList.add(endOfBon());
		return sellsList;
	}

	private String openBon() {
		// open bon
		String openBon = "48";
		String operator = "1";
		String password = "0000";
		return openBon +
				comma +
				logicalNumber +
				comma +
				defaultField +
				semicolon +
				// PARAMETERS
				operator +
				semicolon +
				password +
				semicolon +
				shtand +
				semicolon +
				openInvoice +
				semicolon;
	}

	public ArrayList<String> makeReciept(String price) {

		ArrayList<String> sellsList = new ArrayList<String>();
		sellsList.add(openBon());

		sellsList.add(makeReciept("Услуга", // услуги
				price, // price edininichna cena
				"1")); // quantity
		sellsList.add(endOfBon());
		return sellsList;
	}

	private String makeReciept(String artikul, String price, String quantity) {
		// SELL COMMAND
		String SELL = "S";
		String stokGroup = "2";
		String taxGroup = "2";
		String end = "0";
		return SELL +
				comma +
				logicalNumber +
				comma +
				defaultField +
				semicolon +
				artikul +
				semicolon +
				price +
				semicolon +
				quantity +
				semicolon +
				shtand +
				semicolon +
				// PARAMETERS
				// ????
				stokGroup +
				semicolon +
				taxGroup +
				semicolon +
				openInvoice +
				semicolon +
				end +
				semicolon;
	}

	private String endOfBon() {
		// END COMMAND
		String END_AND_PAYMENT = "T";
		String sb = END_AND_PAYMENT +
				comma +
				logicalNumber +
				comma +
				defaultField +
				semicolon;
		/*
		 * sb.append(PAYMENT_CODE); sb.append(semicolon); sb.append(finalSum);
		 * sb.append(semicolon); sb.append(semicolon); sb.append(semicolon);
		 * sb.append(semicolon);
		 */
		return sb;
	}

	public ArrayList<String> doZOrder() {
		ArrayList<String> sellsList = new ArrayList<String>();
		String DAILY_ORDER = "Z";
		String sb = DAILY_ORDER +
				comma +
				logicalNumber +
				comma +
				defaultField +
				semicolon;
		sellsList.add(sb);
		return sellsList;
	}

	@Override
	public void executeFiskalOperation(ArrayList<String> sellsList) {
		File file = new File(DOCUMENTS_PATH+"\\FPrint_logs\\SELLS\\sell.txt");//("D:/FPrint_logs/SELLS/sell.txt");
		try (PrintStream stream = new PrintStream(file)) {
			for (String s : sellsList) {
				stream.printf("%s\n", s);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.toString());
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/*
	 * private String addTax() { StringBuilder sb = new StringBuilder();
	 * sb.append(TAX); sb.append(comma); sb.append(logicalNumber);
	 * sb.append(comma); sb.append(defaultField); sb.append(semicolon);
	 * sb.append(addValue); sb.append(semicolon); sb.append(надбавка);
	 * sb.append(semicolon); sb.append(semicolon); sb.append(semicolon);
	 * sb.append(semicolon); return sb.toString(); }
	 */
}
