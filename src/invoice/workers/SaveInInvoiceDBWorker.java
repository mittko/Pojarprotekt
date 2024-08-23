package invoice.workers;

import db.Invoice.InvoiceChildDB;
import db.Invoice.InvoiceNumber;
import db.Invoice.InvoiceParent_DB;
import http.RequestCallback2;
import http.invoice.ProformService;
import invoice.PrintInvoiceDialog;
import models.InvoiceModel;
import models.InvoiceModels;
import run.JDialoger;
import utils.BevelLabel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

import static utils.MainPanel.INVOICE_CHILD;
import static utils.MainPanel.INVOICE_PARENT;

public class SaveInInvoiceDBWorker extends SwingWorker {

	private int parent = 0;
	private int child = 0;
	private JDialog jd = null;
	private String payment = null;
	private String discount = null;
	private String sum = null;
	private String currentClient = null;
	private String personName = null;
	private String date = null;
	private String PROTOKOL_NUMBER = null;
	private final DefaultTableModel dftm;


	private boolean isVatRegistered;
	public SaveInInvoiceDBWorker(JDialog jd, String payment, String discount,
								 String sum, String currentClient, String personName, String date,
								 String protokolNumber,
								 DefaultTableModel dftm, boolean isVatRegistered) {

		this.jd = jd;
		this.payment = payment;
		this.discount = discount;
		this.sum = sum;
		this.currentClient = currentClient;// taken from combobox
		this.personName = personName;
		this.date = date;
		this.PROTOKOL_NUMBER = protokolNumber;
		this.dftm = dftm;
		this.isVatRegistered = isVatRegistered;
	}

	@Override
	public Boolean doInBackground() throws Exception {
		// TODO Auto-generated method stub

		InvoiceModels models = new InvoiceModels();
		InvoiceModel parentInvoice = new InvoiceModel();
		parentInvoice.setPayment(payment);
		parentInvoice.setDiscount(discount);
		parentInvoice.setValue(sum);
		parentInvoice.setClient(currentClient);
		parentInvoice.setSaller(personName);
		parentInvoice.setDate(date);
		parentInvoice.setProtokol(PROTOKOL_NUMBER);
		ArrayList<InvoiceModel> childModels = new ArrayList<>();
		for(int i = 0; i < dftm.getRowCount();i++) {
			InvoiceModel childModel = new InvoiceModel();

			childModel.setMake(dftm.getValueAt(i,0).toString());
			childModel.setMed(dftm.getValueAt(i,1).toString());
			childModel.setQuantity(dftm.getValueAt(i,2).toString());
			childModel.setPrice(dftm.getValueAt(i, 3).toString());
			childModel.setValue(dftm.getValueAt(i, 4).toString());
			childModel.setClient(currentClient);
			childModel.setKontragent(dftm.getValueAt(i,6).toString());
			childModel.setInvoiceByKontragent(dftm.getValueAt(i,7).toString());

			childModels.add(childModel);

		}
		models.setParentInvoiceModel(parentInvoice);
		models.setChildInvoiceModels(childModels);


		ProformService service = new ProformService();
		if(isInvoice()) {
			service.insertInvoice(models, new RequestCallback2() {
				@Override
				public <T> void callback(T t) {
					String nextInvoiceNumber = (String) t;
					if (nextInvoiceNumber != null) {
						showPrintDialog(nextInvoiceNumber);
					}
				}
			});
		} else {
          service.insertProform(models, new RequestCallback2() {
			  @Override
			  public <T> void callback(T t) {
				  String nextProformNumber = (String) t;
				  if(nextProformNumber != null) {
					  showPrintDialog(nextProformNumber);
				  }
			  }
		  });
		}



		return parent > 0 && child > 0;
	}

	private void showPrintDialog(String invoiceNumber) {
		PrintInvoiceDialog pd = new PrintInvoiceDialog(dftm, isInvoice() ? invoiceNumber : null, // invoice
																			// number
				isInvoice() ? null : invoiceNumber, // proform number
				null, // acquittance number
				currentClient, date, Double.parseDouble(sum), payment, isInvoice(), // print
																				// protokol
				!isInvoice(),// print proform
				false); // print acquittance

		JDialoger jDialoger = new JDialoger();
		jDialoger.setContentPane(pd);
		jDialoger.Show();
	}
	public boolean isInvoice() {
		return true;
	}
	public String getParentTable() {
		return INVOICE_PARENT;
	}
	public String getChildTable() {
		return INVOICE_CHILD;
	}
}
