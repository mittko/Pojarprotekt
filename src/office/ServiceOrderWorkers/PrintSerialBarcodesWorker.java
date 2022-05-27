package office.ServiceOrderWorkers;


import javax.swing.*;

public class PrintSerialBarcodesWorker extends SwingWorker {

	private String barcode;

	public PrintSerialBarcodesWorker(String barcode) {
		this.barcode = barcode;
	}
	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub

	/*	PrintToSerialPort printToSerialPort = new PrintToSerialPort();
		if(!PrintToSerialPort.isConnected()) {
			PrintToSerialPort.connectToPort();
		}
		if(PrintToSerialPort.isConnected()) {
			printToSerialPort.sendMessage(barcode);
		}*/
		return null;
	}

}
