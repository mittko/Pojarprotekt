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
import models.InvoiceModel;
import models.InvoiceModels;

public class ProformSearchWorker {

	private String proformCurrNumber = null; // this which is invoked for
	// reports
	private String proformPayment = null;
	private String proformDiscount = null;
	private String proformClient = null;
	private String proformSaller = null;
	private String proformDate = null;
	private String protokolNumber = null;
	private InvoiceModel getParentInfo = null;
	private ArrayList<InvoiceModel> getChildInfo = null;

	public ProformSearchWorker(InvoiceModel parentModel, ArrayList<InvoiceModel> childModels) {
		this.getParentInfo = parentModel;
		this.getChildInfo = childModels;
	}


	public Object doSearch() {
		// TODO Auto-generated method stub
		try {


			if (getParentInfo != null) {
				proformCurrNumber = getParentInfo.getId();// номер на
				// проформа
				proformPayment = getParentInfo.getPayment(); // заплащане
				proformDiscount = getParentInfo.getDiscount(); // отстъпка
//				String proformPrice = String.format(Locale.ROOT, "%.2f",
//						Double.parseDouble(getParentInfo.get(0).getPrice())).replace(",",
//						"."); // крайна цена
				proformClient = getParentInfo.getClient(); //

				// init client

				proformSaller = getParentInfo.getSaller();

				// init saller

				proformDate = getParentInfo.getDate();

				protokolNumber = getParentInfo.getProtokol();

			}
		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (getParentInfo == null) {

						JOptionPane.showMessageDialog(null,
									"Няма резултат от търсенето!");
							return;

					}
					if(!SearchFromProformTab.clientLabel.getName().isEmpty()
							&& !SearchFromProformTab.clientLabel.getName().equals(proformClient)) {
						JOptionPane.showMessageDialog(null,
								"Въведен е друг клиент !");
						return;
					}
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
					SearchFromProformTab.proformNumLabel
							.setName(proformCurrNumber);
					SearchFromProformTab.sallerLabel.setName(proformSaller);
					//SearchFromProformTab.sumField.setText(proformPrice);
					SearchFromProformTab.PROTOKOL_NUMBER = protokolNumber;

					String registrationVat = FirmTable.getHasFirmVatRegistration(
							proformClient);
					SearchFromProformTab.registrationVatCheckBox.setSelected(
							registrationVat.equals("да"));
					SearchFromProformTab.switchRegistrationVat();
				}

			});

		}
		return null;
	}

}
