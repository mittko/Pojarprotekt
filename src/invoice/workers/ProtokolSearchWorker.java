package invoice.workers;

import db.Client.FirmTable;
import db.Discount.DiscountDB;
import db.PartsPrice.PriceTable;
import db.Protokol.ProtokolTable;
import invoice.invoicewindow.SearchFromProtokolTab;
import models.ProtokolModel;
import utils.EditableField;
import utils.MainPanel;
import utils.MyMath;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

//inner class
public class ProtokolSearchWorker {
	private ArrayList<ProtokolModel> result = new ArrayList<>();
	public static String discount = null;
	public static double doubleDiscount = 0;
	private String protokolNum = null;
	private final float DDS = 1.0f;
	private final EditableField copySearchField;

	private final TreeMap<String, Info> mapInfo = new TreeMap<String, Info>();
	private final DefaultTableModel fromInvoiceTableModel;
	public static ArrayList<ProtokolModel> moreProtokolsList = new ArrayList<>(); // store

	public ProtokolSearchWorker(EditableField searchField,
								DefaultTableModel dftm, ArrayList<ProtokolModel> result) {
		this.copySearchField = searchField;
		this.protokolNum = copySearchField.getText().trim();
		this.fromInvoiceTableModel = dftm;
		this.result = result;
	}


	public Boolean doSearch() {
		// TODO Auto-generated method stub

		try {

			if (!SearchFromProtokolTab.protokolNumberSet.contains(protokolNum)) {
			//	result.clear();
				mapInfo.clear();



				if (result.size() > 0) {
					// init client
					String client = result.get(0).getClient();

					if (SearchFromProtokolTab.INVOICE_CURRENT_CLIENT.equals("")) {

						moreProtokolsList.clear();

						SearchFromProtokolTab.INVOICE_CURRENT_CLIENT = client;

						discount = DiscountDB
								.getDiscount(SearchFromProtokolTab.INVOICE_CURRENT_CLIENT);
						// warning !!!!! special case
						// if client is deleted but has protokol in db
						// discount will be empty string
						// so we set value 0
						if (discount.isEmpty()) {
							discount = "0";
						}

						doubleDiscount = Double.parseDouble(discount);

						moreProtokolsList.addAll(result);

						doCalc2(moreProtokolsList);

					} else if (!SearchFromProtokolTab.INVOICE_CURRENT_CLIENT
							.equals("")
							&& client
							.equals(SearchFromProtokolTab.INVOICE_CURRENT_CLIENT)) {

						moreProtokolsList.addAll(result);

						doCalc2(moreProtokolsList);

					}

				}

			}

		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (SearchFromProtokolTab.protokolNumberSet
							.contains(protokolNum)) {
						JOptionPane.showMessageDialog(null,
								"Този протокол вече е въведен !");
						return;
					}
					if (result != null) {
						if (result.size() == 0) {
							JOptionPane.showMessageDialog(null,
									"Няма резултат от търсенето!");
						} else {
							if (!SearchFromProtokolTab.INVOICE_CURRENT_CLIENT
									.equals("")
									&& SearchFromProtokolTab.INVOICE_CURRENT_CLIENT
									.equals(result.get(0).getClient())) {

								SearchFromProtokolTab.discountField
										.setText(discount);

								SearchFromProtokolTab.choiceDiscountButton
										.setDefaultIcon();

								SearchFromProtokolTab.clientLabel
										.setName((String) result.get(0).getClient());

								String registrationVat = FirmTable.getHasFirmVatRegistration(
										SearchFromProtokolTab.INVOICE_CURRENT_CLIENT);
								boolean selected = registrationVat.equals("да");
								SearchFromProtokolTab.registrationVatCheckBox.setSelected(selected);
								SearchFromProtokolTab.switchRegistrationVat();


								vizualizeTable(mapInfo);

								if (copySearchField != null) {
									copySearchField.setText("");
								}

								SearchFromProtokolTab.protokolNumberSet
										.add(protokolNum);

							} else if (!SearchFromProtokolTab.INVOICE_CURRENT_CLIENT
									.equals("")
									&& !SearchFromProtokolTab.INVOICE_CURRENT_CLIENT
									.equals(result.get(0).getClient())) {
								// return
								JOptionPane.showMessageDialog(null,
										"Въведен е друг клиент !");
							}

						}

					}
				}

			});
		}

		return null;
	}

	private void vizualizeTable(TreeMap<String, Info> map) {


		if (fromInvoiceTableModel.getRowCount() > 0) {
			fromInvoiceTableModel.setRowCount(0);
		}
		for (Map.Entry<String, Info> inf : map.entrySet()) {

			Info value = inf.getValue();

			Object[] obj = new Object[] {
					value.key,
					value.myarka,
					value.quantity,
					MyMath.round(value.price, 2),
					MyMath.round(value.quantity * MyMath.round(value.price, 2),
							2), value.discount, value.kontragent,
					value.invoiceByKontragent };
			fromInvoiceTableModel.addRow(obj);

			System.out.println(value.key + " " + value.quantity + " " + MyMath.round(value.price, 2)
			+ " " + MyMath.round(value.quantity * MyMath.round(value.price, 2),
					2));
		}

	}

	private void doCalc2(ArrayList<ProtokolModel> res) {
		// 0 -> TO 1 -> P 2 -> HI 3 -> client 4 -> type 5 -> wheight 6 -> value
		for (ProtokolModel model : res) {

			String type = model.getType();

			String gasitelnoVeshtestvo = "";
			switch (type) {
				case MainPanel.type_Prah_BC:
					gasitelnoVeshtestvo = MainPanel.PrahBC;
					break;
				case MainPanel.type_Prah_ABC:
					gasitelnoVeshtestvo = MainPanel.PrahABC;
					break;
				case MainPanel.type_Water:
					gasitelnoVeshtestvo = MainPanel.GasitelnoVeshtestvoVoda;
					break;
				case MainPanel.type_Water_Fame:
					gasitelnoVeshtestvo = MainPanel.GasitelnoVeshtestvoVodaPyana;
					break;
				case MainPanel.type_CO2:
					gasitelnoVeshtestvo = MainPanel.GasitelnoVeshtestvoCO2;
					break;
				default:
					break;
			}

			String weightStr = model.getWheight();
			String[] spl = weightStr.split("/");
			String weight = "";
			if (spl.length == 1) {
				weight = spl[0].trim();
			} else if(spl.length == 2) {
				weight = spl[1].trim();
			}
			//	System.out.println("weight " + weight);
			String category = model.getCategory();


			String value = String.format("%.2f", Double.parseDouble(model.getValue())).replace(",",
					".");

			double doubleValue = Double.parseDouble(value);

			String kontragent = model.getKontragent();
			String invoiceByKontragent = model.getInvoiceByKontragent();

			if (type.contains("( Нов )")) {
				String key = type + " " + weight;
				if (!mapInfo.containsKey(key)) {
					Info info = new Info(key, "брой", 1, MyMath.round(
							doubleValue, 2), doubleDiscount, kontragent, invoiceByKontragent);
					mapInfo.put(key, info);
				} else {
					Info info2 = mapInfo.get(key);
					info2.myarka = "броя";
					info2.quantity++;

				}

			}
			// calculate parts
			String parts[] = model.getParts().split(",");
			for (int p = 0; p < parts.length; p++) {

				System.out.println("Part " + parts[p] + " Price " + " Weight " + weight);
				if (parts[p].equals(gasitelnoVeshtestvo) || parts[p].equals("")) {
					continue;
				}

				if(type.equals("Воден") || type.equals("Водопенен")) {
					if(weight.endsWith("л")) {
						weight = weight.replace("л","литра");
					}
				}
				double pric = PriceTable.getPartPriceFromDB(parts[p], type,
						category, weight);

				System.out.println("Part " + parts[p] + " Price " + pric + " Weight " + weight);
				String key = parts[p] + " (" + type + " " + weight + ")";
                if(parts[p].contains("Техническо обслужване на Пожарогасител") ||
						parts[p].contains("Презареждане на Пожарогасител")
						|| parts[p].contains("Хидростатично Изпитване на Пожарогасител")) {
					parts[p]  = parts[p] + " " + type
							+ " " + weight;
					System.out.println(String.format("PART %s\n",parts[p]));
				}
				if (!mapInfo.containsKey(key)) {

					if (pric == 0) {
						continue;
					}

					Info info = new Info(parts[p], "брой", 1, MyMath.round(
							pric, 2), doubleDiscount,
							kontragent, invoiceByKontragent);
					mapInfo.put(key, info);
				} else {
					Info info2 = mapInfo.get(key);
					info2.myarka = "броя";
					info2.quantity++;
				}
			}
		}
	}


	double getValueFromDiscount(double val, double discount) {
		return MyMath.getValueFromPercent(val, discount);
		// origin -> MyMath.round( (val * ( discount / 100.0 ) ), 2 );
	}

	class Info {
		public String key;
		public String myarka;
		public int quantity;
		public double price;
		public double discount;
		public String kontragent;
		public String invoiceByKontragent;

		public Info(String key, String myarka, int quantity, double price,
					double discount, String kontragent, String invoiceByKontragent) {
			this.key = key;
			this.myarka = myarka;
			this.quantity = quantity;
			this.price = price - getValueFromDiscount(price, discount);
			this.discount = discount;
			this.kontragent = kontragent;
			this.invoiceByKontragent = invoiceByKontragent;
		}
	}

}
