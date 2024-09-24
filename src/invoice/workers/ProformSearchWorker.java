package invoice.workers;

import db.Client.FirmTable;
import http.invoice.ProformService;
import invoice.invoicewindow.SearchFromProformTab;

import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import db.Proform.ProformChildDB;
import db.Proform.ProformParent_DB;
import models.Firm;
import models.InvoiceModel;
import models.InvoiceModels;

public class ProformSearchWorker {

	private String proformClient = null;
	private InvoiceModel getParentInfo = null;
	private ArrayList<InvoiceModel> getChildInfo = null;

	private final Firm firm;

	public ProformSearchWorker(InvoiceModels invoiceModels) {
		this.firm = invoiceModels.getFirm();
		this.getParentInfo = invoiceModels.getParentInvoiceModel();
		this.getChildInfo = (ArrayList<InvoiceModel>) invoiceModels.getChildInvoiceModels();
	}


	public Object doSearch() {
		// TODO Auto-generated method stub


			if(!SearchFromProformTab.clientLabel.getName().isEmpty()
					&& !SearchFromProformTab.clientLabel.getName().equals(proformClient)) {
				JOptionPane.showMessageDialog(null,
						"Въведен е друг клиент !");
				return null;
			}

			if (getParentInfo != null) {

				// this which is invoked for
				String proformCurrNumber = getParentInfo.getId();// номер на
				// проформа
				// reports
				String proformPayment = getParentInfo.getPayment(); // заплащане
				String proformDiscount = getParentInfo.getDiscount(); // отстъпка
//				String proformPrice = String.format(Locale.ROOT, "%.2f",
//						Double.parseDouble(getParentInfo.get(0).getPrice())).replace(",",
//						"."); // крайна цена
				proformClient = getParentInfo.getClient(); //

				String proformSaller = getParentInfo.getSaller();

				String proformDate = getParentInfo.getDate();

				String protokolNumber = getParentInfo.getProtokol();

				if (getChildInfo != null && getChildInfo.size() > 0) {
					for (InvoiceModel model : getChildInfo) {

						Object[] objects = {model.getMake(),model.getMed(),model.getQuantity(),model.getPrice(),
								model.getValue(),model.getDiscount(),model.getKontragent(),model.getInvoiceByKontragent()};

						SearchFromProformTab.proformTableModel
								.addRow(objects);
					}
				}

				SearchFromProformTab.dateLabel.setName(proformDate);
				SearchFromProformTab.discountLabel.setName(proformDiscount);
				SearchFromProformTab.paymentLabel.setName(proformPayment);
				SearchFromProformTab.clientLabel.setName(proformClient);
				SearchFromProformTab.proformNumLabel.setName(proformCurrNumber);
				SearchFromProformTab.sallerLabel.setName(proformSaller);
				SearchFromProformTab.PROTOKOL_NUMBER = protokolNumber;

				String registrationVat = firm.getVat_registration();
				SearchFromProformTab.registrationVatCheckBox.setSelected(
						registrationVat.equals("да"));
				SearchFromProformTab.switchRegistrationVat();
			}

		return null;
	}

}
