package invoice.workers;

import db.Discount.DiscountDB;

import javax.swing.*;

public class GetDiscountWorker extends SwingWorker<String,String> {

	String clientName;
	public GetDiscountWorker(String clientName) {
		this.clientName = clientName;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public String doInBackground() throws Exception {
		// TODO Auto-generated method stub
		return DiscountDB.getDiscount(clientName);
	//	return null;
	}

}
