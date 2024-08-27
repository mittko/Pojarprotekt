package invoice.workers;

import db.AcquittanceDB.AcquittanceChildDB;
import db.AcquittanceDB.AcquittanceNumber;
import db.AcquittanceDB.AcuittanceParentDB;
import db.Invoice.InvoiceNumber;
import http.RequestCallback2;
import http.invoice.ProformService;
import invoice.PrintInvoiceDialog;
import models.AcquittanceModel;
import models.AcquittanceModels;
import run.JDialoger;
import utils.BevelLabel;
import utils.MyMath;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

import static utils.MainPanel.ACQUITTANCE_CHILD;
import static utils.MainPanel.ACQUITTANCE_PARENT;

public class SaveInAcquittanceWorker {

	private final DefaultTableModel dftm;

	private final double value;
	private final String saller;
	private final String client;
	private final String date;
	private final JDialog jd;
	private int updateParent;
	private int updateChild;


	public SaveInAcquittanceWorker(DefaultTableModel dftm,
			double value, String saller, String client, String date,
			 JDialog jd) {
		this.dftm = dftm;
		this.value = value;
		this.saller = saller;
		this.client = client;
		this.date = date;
		this.jd = jd;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


	public void save() {
		// TODO Auto-generated method stub

					AcquittanceModel parentModel = new AcquittanceModel();
					parentModel.setValue(value+"");
					parentModel.setClient(client);
					parentModel.setSaller(saller);
					parentModel.setDate(date);


					ArrayList<AcquittanceModel> childModels = new ArrayList<>();
							for (int row = 0; row < dftm.getRowCount(); row++) {
								AcquittanceModel childModel = new AcquittanceModel();
								childModel.setArtikul(dftm.getValueAt(row,0).toString());
								childModel.setMed(dftm.getValueAt(row,1).toString());
								childModel.setQuantity(dftm.getValueAt(row,2).toString());
								childModel.setPrice(dftm.getValueAt(row,3).toString());
								childModel.setValue(dftm.getValueAt(row,4).toString());
								childModel.setClient(client);

								childModels.add(childModel);


							}

						AcquittanceModels body = new AcquittanceModels();
						body.setParentModel(parentModel);
						body.setChildModels(childModels);

						ProformService service = new ProformService();
						service.insertAcquittance(body, new RequestCallback2() {
							@Override
							public <T> void callback(T t) {
								jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

								String nextNumber = (String) t;
								if(nextNumber != null) {
									showPrintDialog(nextNumber);
								}
							}
						});

	}

	private void showPrintDialog(String acquittanceNumberAsString) {
		// call print dialog
		PrintInvoiceDialog pd = new PrintInvoiceDialog(dftm, null, // invoice
																	// number
				null, // proform number
				acquittanceNumberAsString, // acquittance number
				client, date, MyMath.round(value, 2), "заплащане", // payment
																	// this
																	// is
																	// no
																	// needed
																	// for
																	// acquittance
				false, // invoice
				false, // proform
				true); // acquittance

		JDialoger jDialoger = new JDialoger();
		jDialoger.setContentPane(pd);
		jDialoger.Show();
	}

	private String getParentTable() {
		return ACQUITTANCE_PARENT;
	}
	private String getChildTable() {
		return ACQUITTANCE_CHILD;
	}
}
