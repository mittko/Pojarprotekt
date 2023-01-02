package office.ServiceOrderWorkers;


import ThermalPrinters.LPQ58Printers.ESCPos;

import javax.swing.*;
import java.io.ByteArrayOutputStream;

public class PrintSerialBarcodesWorker extends SwingWorker {

	private String barcode;
    private String clientName;
	public PrintSerialBarcodesWorker(String barcode, String clientName) {
		this.barcode = barcode;
		this.clientName = clientName;
	}
	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub

		final ESCPos escPos = new ESCPos();
		final int type = 67;
		final int h = 30;//71;
		final int w = 3;//2;
		final int font = 0;
		final int pos = 1;
		escPos.escInit();
		escPos.printBarcode(barcode, type, h, w, font, pos,clientName, (byte)3);
		final ByteArrayOutputStream byteArrayOutputStream = escPos.getPrinter();
		escPos.printHexBytes(byteArrayOutputStream.toByteArray(),"LPQ58(ESC)");

//		PrintToSerialPort printToSerialPort = new PrintToSerialPort();
//		if(!PrintToSerialPort.isConnected()) {
//			PrintToSerialPort.connectToPort();
//		}
//		if(PrintToSerialPort.isConnected()) {
//			printToSerialPort.sendMessage(barcode);
//		}
		return null;
	}

}
