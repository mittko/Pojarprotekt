package invoice.worker;

import db.AcquittanceDB.AcquittanceNumber;

import javax.swing.*;

public class GetAqcuittanceNumberWorker extends SwingWorker {

	public GetAqcuittanceNumberWorker() {
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public String doInBackground() throws Exception {
		// TODO Auto-generated method stub
		String number = AcquittanceNumber.getAcquittanceNumber();
		return number;
	}

}
