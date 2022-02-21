package Invoice.worker;

import javax.swing.SwingWorker;

import db.Discount.DiscountDB;

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
