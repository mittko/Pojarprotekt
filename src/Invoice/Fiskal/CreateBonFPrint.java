package Invoice.Fiskal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CreateBonFPrint extends CreateBon {
	private final String comma = ",";
	private final String semicolon = ";";
	private final String openBon = "48";
	private final String logicalNumber = "1";
	private final String defaultField = "______,_,__";
	// PARAMETERS
	private final String operator = "1";
	private final String password = "0000";
	private final String shtand = "1";
	private final String openInvoice = "0";

	// SELL COMMAND
	private final String SELL = "S";
	private final String DAILY_ORDER = "Z";
	// PARAMETERS
	private final String stokGroup = "2"; // ????
	private final String taxGroup = "2";
	private final String end = "0";

	private String PAYMENT_CODE = "0";

	private final String TAX = "C";
	private final String addValue = "2";
	private final String substractValue = "1";
	private final String надбавка = "20.00";
	// END COMMAND
	private final String END_AND_PAYMENT = "T";

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
		StringBuilder sb = new StringBuilder();
		sb.append(openBon);
		sb.append(comma);
		sb.append(logicalNumber);
		sb.append(comma);
		sb.append(defaultField);
		sb.append(semicolon);
		sb.append(operator);
		sb.append(semicolon);
		sb.append(password);
		sb.append(semicolon);
		sb.append(shtand);
		sb.append(semicolon);
		sb.append(openInvoice);
		sb.append(semicolon);
		return sb.toString();
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
		StringBuilder sb = new StringBuilder();
		sb.append(SELL);
		sb.append(comma);
		sb.append(logicalNumber);
		sb.append(comma);
		sb.append(defaultField);
		sb.append(semicolon);
		sb.append(artikul);
		sb.append(semicolon);
		sb.append(price);
		sb.append(semicolon);
		sb.append(quantity);
		sb.append(semicolon);
		sb.append(shtand);
		sb.append(semicolon);
		sb.append(stokGroup);
		sb.append(semicolon);
		sb.append(taxGroup);
		sb.append(semicolon);
		sb.append(openInvoice);
		sb.append(semicolon);
		sb.append(end);
		sb.append(semicolon);
		return sb.toString();
	}

	private String endOfBon() {
		StringBuilder sb = new StringBuilder();
		sb.append(END_AND_PAYMENT);
		sb.append(comma);
		sb.append(logicalNumber);
		sb.append(comma);
		sb.append(defaultField);
		sb.append(semicolon);
		/*
		 * sb.append(PAYMENT_CODE); sb.append(semicolon); sb.append(finalSum);
		 * sb.append(semicolon); sb.append(semicolon); sb.append(semicolon);
		 * sb.append(semicolon);
		 */
		return sb.toString();
	}

	public ArrayList<String> doZOrder() {
		ArrayList<String> sellsList = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		sb.append(DAILY_ORDER);
		sb.append(comma);
		sb.append(logicalNumber);
		sb.append(comma);
		sb.append(defaultField);
		sb.append(semicolon);
		sellsList.add(sb.toString());
		return sellsList;
	}

	@Override
	public void executeFiskalOperation(ArrayList<String> sellsList) {
		File file = new File("D:/FPrint_logs/SELLS/sell.txt");
		PrintStream stream = null;
		try {
			stream = new PrintStream(file);
			for (int i = 0; i < sellsList.size(); i++) {
				stream.printf("%s\n", sellsList.get(i));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.toString());
			e.printStackTrace();
		} finally {
			if (stream != null) {
				stream.close();
			}
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
