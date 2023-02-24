package invoice.worker;

import db.Client.FirmTable;
import invoicewindow.SearchFromProformTab;

import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import db.Proform.ProformChildDB;
import db.Proform.ProformParent_DB;

public class ProformSearchWorker extends SwingWorker<Object, Object> {

	private String proformCurrNumber = null; // this which is invoked for
												// reports
	private String proformPayment = null;
	private String proformDiscount = null;
	private String proformPrice = null;
	private String proformClient = null;
	private String proformSaller = null;
	private String proformDate = null;
	private String protokolNumber = null;
	private ArrayList<String> getParentInfo = null;
	private ArrayList<Object[]> getChildInfo = null;
	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub
		try {

			getParentInfo = ProformParent_DB
					.getParentInfo(SearchFromProformTab.searchProformField
							.getText());

			getChildInfo = ProformChildDB
					.getProformChildInfo(SearchFromProformTab.searchProformField
							.getText());

			/*for (Object[] childeInfo : getChildInfo) {
				for (Object o : childeInfo) {
					System.out.printf("%s\n", o);
				}
			}*/
			if (getParentInfo != null && getParentInfo.size() > 0) {
				proformCurrNumber = getParentInfo.get(0);// номер на
															// проформа
				proformPayment = getParentInfo.get(1); // заплащане
				proformDiscount = getParentInfo.get(2); // отстъпка
				proformPrice = String.format(Locale.ROOT, "%.2f",
						Double.parseDouble(getParentInfo.get(3))).replace(",",
						"."); // крайна цена
				proformClient = getParentInfo.get(4); //

				// init client

				proformSaller = getParentInfo.get(5);

				// init saller

				proformDate = getParentInfo.get(6);

				protokolNumber = getParentInfo.get(7);

			}
		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (getParentInfo != null) {
						if (getParentInfo.size() == 0) {
							JOptionPane.showMessageDialog(null,
									"Няма резултат от търсенето!");
							return;
						}
					}
					if(!SearchFromProformTab.clientLabel.getName().isEmpty()
					 && !SearchFromProformTab.clientLabel.getName().equals(proformClient)) {
						JOptionPane.showMessageDialog(null,
								"Въведен е друг клиент !");
						return;
					}
					if (getChildInfo != null && getChildInfo.size() > 0) {
						for (Object[] objects : getChildInfo) {
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
