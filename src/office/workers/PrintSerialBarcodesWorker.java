package office.workers;


import exceptions.ErrorDialog;
import thermalprinters.lpq58.ESCPos;

import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.util.HashSet;

public class PrintSerialBarcodesWorker extends SwingWorker {

	private static final HashSet<String> enteredNumbers = new HashSet<>();
	private final String barcode;
    private final String clientName;
	public PrintSerialBarcodesWorker(String barcode, String clientName) {
		this.barcode = barcode;
		this.clientName = clientName;
	}
	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub

		if(!enteredNumbers.contains(barcode)) {

			final ESCPos escPos = new ESCPos();
			final int type = 67;
			final int h = 30;//71;
			final int w = 3;//2;
			final int font = 0;
			final int pos = 1;
			escPos.escInit();
			escPos.printBarcode(barcode, type, h, w, font, pos, clientName, (byte) 3);
			final ByteArrayOutputStream byteArrayOutputStream = escPos.getPrinter();
			escPos.printHexBytes(byteArrayOutputStream.toByteArray(), "LPQ58(ESC)");//""LPQ58(ESC)");

			enteredNumbers.add(barcode);
		} else {
			ErrorDialog.showErrorMessage("���� ����� ���� � ������� !");
		}

//		PrintToSerialPort printToSerialPort = new PrintToSerialPort();
//		if(!PrintToSerialPort.isConnected()) {
//			PrintToSerialPort.connectToPort();
//		}
//		if(PrintToSerialPort.isConnected()) {
//			printToSerialPort.sendMessage(barcode);
//		}
		return null;
	}

	public static void clearEnteredNumbers() {
		if(enteredNumbers != null) {
			enteredNumbers.clear();
		}
	}

}
