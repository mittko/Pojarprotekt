//package Invoice.Fiskal;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.PrintStream;
//import java.util.ArrayList;
//
//import javax.swing.JOptionPane;
//
///* This class executes DPrint fiskal printer commands */
//
//public class CreateBonDPrint extends CreateBon {
//	private final String comma = ",";
//	private final String semicolon = ";";
//	private final String logicalNumber = "1";
//	private final String defaultField = "______,_,__";
//
//	// PARAMETERS
//	private final String operator = "1";
//	private final String password = "0000";
//	private final String shtand = "1";
//	private final String openInvoice = "0";
//
//	// SELL COMMAND
//	private final String SELL = "S";// ÔÓ‰‡Ê·‡ ÔÓ Ò‚Ó·Ó‰Ì‡ ˆÂÌ‡
//	private final String DAILY_ORDER = "Z";
//	// PARAMETERS
//	private final String itemGroup = "1"; // ????
//	private final String taxGroup = "2";
//	private final String end = "0";
//	private String PAYMENT_CODE = "0";
//	// END COMMAND
//	private final String END_AND_PAYMENT = "T";
//
//	public CreateBonDPrint() {
//
//	}
//
//	public ArrayList<String> makeReciept(String price) {
//		ArrayList<String> sellsList = new ArrayList<String>();
//		sellsList.add(reciept("ÛÒÎÛ„Ë", price, // price edininichna cena
//				"1")); // quantity
//		return sellsList;
//	}
//
//	private String reciept(String item, String price, String quantity) {
//		// StringBuilder sb = new StringBuilder();
//		// sb.append(SELL);
//		// sb.append(comma);
//		// sb.append(logicalNumber);
//		// sb.append(comma);
//		// sb.append(defaultField);
//		// sb.append(semicolon);
//		// sb.append(item);
//		// sb.append(semicolon);
//		// sb.append(price);
//		// sb.append(semicolon);
//		// sb.append(quantity);
//		// sb.append(semicolon);
//		// sb.append(shtand);
//		// sb.append(semicolon);
//		// sb.append(itemGroup);
//		// sb.append(semicolon);
//		// sb.append(taxGroup);
//		// sb.append(semicolon);
//		// sb.append(end);
//		// sb.append(semicolon);
//		// sb.append(end);
//		// sb.append(semicolon);
//		// sb.append("\n");
//		// sb.append(executePayment(price));
//
//		// String s = "S,1,______,_,__;’Àﬂ¡;2;1;;;1;;;DY923456-1001-0000001;"
//		// + "\nS,1,______,_,__;¬Œƒ¿;1.05;11;;;3;-15.22;;DY923456-1001-0000002;"
//		// +
//		// "\nS,1,______,_,__;¡¿ƒ≈ÃŒ¬¿ “Œ–“¿ —⁄— —Ã≈“¿Õ¿;12.87;2;;;4;;5.10;DY923456-1001-0000003;"
//		// +
//		// "\nS,1,______,_,__;Ã¿À»ÕŒ¬¿ “Œ–“¿;14.97;3;;;5;;-10.00;DY923456-1001-0000004;"
//		// + "\nT,1,______,_,__;";
//
//		String s1 = "W,1,______,_,__;”ÒÎÛ„Ë;" + price
//				+ ";1;;;2;DY923456-0001-9999998;;" + "\nT,1,______,_,__;";
//		return s1;
//	}
//
//	/*
//	 * private String executePayment(String sum) { StringBuilder sb = new
//	 * StringBuilder(); sb.append(END_AND_PAYMENT); sb.append(comma);
//	 * sb.append(logicalNumber); sb.append(comma); sb.append(defaultField);
//	 * sb.append(semicolon); // sb.append(PAYMENT_CODE); //
//	 * sb.append(semicolon); // sb.append(sum); // sb.append(semicolon); //
//	 * sb.append(semicolon); // sb.append(semicolon); // sb.append(semicolon);
//	 * return sb.toString(); }
//	 */
//
//	public ArrayList<String> doZOrder() {
//		ArrayList<String> sellsList = new ArrayList<String>();
//		StringBuilder sb = new StringBuilder();
//		sb.append(DAILY_ORDER);
//		sb.append(comma);
//		sb.append(logicalNumber);
//		sb.append(comma);
//		sb.append(defaultField);
//		sb.append(semicolon);
//		sellsList.add(sb.toString());
//		return sellsList;
//	}
//
//	@Override
//	public void executeFiskalOperation(ArrayList<String> sellsList) {
//		File file = new File("D:\\DPrint_Log\\DEFEXEC.txt");
//		PrintStream stream = null;
//		try {
//			stream = new PrintStream(file);
//			for (int i = 0; i < sellsList.size(); i++) {
//				stream.printf("%s\n", sellsList.get(i));
//			}
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			JOptionPane.showMessageDialog(null, e.toString());
//			e.printStackTrace();
//		} finally {
//			if (stream != null) {
//				stream.close();
//			}
//		}
//	}
//
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		CreateBonDPrint createBonDPrint = new CreateBonDPrint();
//		// ArrayList<String> sellsList = createBon.makeReciept("2");
//		// createBon.executeFiskalOperation(sellsList);
//
//		ArrayList<String> zorder = createBonDPrint.doZOrder();
//		createBonDPrint.executeFiskalOperation(zorder);
//
//	}
//
//}
