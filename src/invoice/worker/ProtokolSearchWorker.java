package invoice.worker;

import WorkingBook.WorkingBook;
import db.Client.FirmTable;
import db.Discount.DiscountDB;
import db.PartsPrice.PriceTable;
import db.Protokol.ProtokolTable;
import invoicewindow.SearchFromProtokolTab;
import utils.EditableField;
import utils.MainPanel;
import utils.MyMath;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import static invoicewindow.SearchFromProtokolTab.registrationVatCheckBox;
import static invoicewindow.SearchFromProtokolTab.selectedIcon;

//inner class
public class ProtokolSearchWorker extends SwingWorker<Object, Object> {
	private ArrayList<Object[]> result = new ArrayList<Object[]>();
	public static String discount = null;
	public static double doubleDiscount = 0;
	private String protokolNum = null;
	private float DDS = 1.0f;
	private EditableField copySearchField;

	private TreeMap<String, Info> mapInfo = new TreeMap<String, Info>();
	private DefaultTableModel fromInvoiceTableModel;
	public static ArrayList<Object[]> moreProtokolsList = new ArrayList<Object[]>(); // store

	public ProtokolSearchWorker(EditableField searchField,
			DefaultTableModel dftm) {
		this.copySearchField = searchField;
		this.protokolNum = copySearchField.getText();
		this.fromInvoiceTableModel = dftm;
	}

	/*
	 * public ProtokolSearcher(String protokolNum, DefaultTableModel dftm) {
	 * this.protokolNum = protokolNum; this.fromInvoiceTableModel = dftm; }
	 */
	@Override
	public Boolean doInBackground() throws Exception {
		// TODO Auto-generated method stub

		try {

			if (!SearchFromProtokolTab.protokolNumberSet.contains(protokolNum)) {
				result.clear();
				mapInfo.clear();

				result = ProtokolTable.getProtokolInfo(protokolNum);

				if (result != null && result.size() > 0) {
					// init client
					String client = (String) result.get(0)[3];

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

						doCalc(moreProtokolsList);

					} else if (!SearchFromProtokolTab.INVOICE_CURRENT_CLIENT
							.equals("")
							&& client
									.equals(SearchFromProtokolTab.INVOICE_CURRENT_CLIENT)) {

						moreProtokolsList.addAll(result);

						doCalc(moreProtokolsList);

					}
//					else if (!SearchFromProtokolTab.INVOICE_CURRENT_CLIENT
//							.equals("")
//							&& !client
//									.equals(SearchFromProtokolTab.INVOICE_CURRENT_CLIENT)) {
//						// do nothing
//
//					}

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
											.equals(result.get(0)[3])) {

								SearchFromProtokolTab.discountField
										.setText(discount);

								SearchFromProtokolTab.choiceDiscountButton
										.setDefaultIcon();

								SearchFromProtokolTab.clientLabel
										.setName((String) result.get(0)[3]);

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
											.equals(result.get(0)[3])) {
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

		// if(searchFromProtokol.invoiceTableModel.getRowCount() > 0) {
		// searchFromProtokol.invoiceTableModel.setRowCount(0);
		// }

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
		}

	}

	private void doCalc(ArrayList<Object[]> res) {
		// 0 -> TO 1 -> P 2 -> HI 3 -> client 4 -> type 5 -> wheight 6 -> value
		for (Object[] re : res) {

			String type = re[4].toString();

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

			String weightStr = re[5].toString();
			String[] spl = weightStr.split("/");
			String weight = "";
			if (spl.length == 1) {
				weight = spl[0].trim();
	    	} else if(spl.length == 2) {
				weight = spl[1].trim();
			}
			System.out.println("weight " + weight);
			String category = re[6].toString();

			String TO = re[0].toString();
			String P = re[1].toString();
			String HI = re[2].toString();

			String value = String.format("%.2f", re[8]).replace(",",
					".");

			double doubleValue = Double.parseDouble(value);

			String kontragent = re[9].toString();
			String invoiceByKontragent = re[10].toString();

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

			} else if (!TO.equals("не") && P.equals("не") && HI.equals("не")) {
				String typ = "Техническо обслужване на Пожарогасител " + type
						+ " " + weight;

				if (!mapInfo.containsKey(typ)) {

					double price = WorkingBook.TO_PRICE;

					Info info = new Info(typ, "брой", 1,
							MyMath.round(price, 2), doubleDiscount, kontragent,
							invoiceByKontragent);
					mapInfo.put(typ, info);
				} else {
					Info info2 = mapInfo.get(typ);
					info2.myarka = "броя";
					info2.quantity++;

				}
			} else if (TO.equals("не") && P.equals("не") && !HI.equals("не")) {

				String typ2 = "Хидростатично изпитване на Пожарогасител "
						+ type + " " + weight;

				double pric = WorkingBook.HI_PRICE;

				if (!mapInfo.containsKey(typ2)) {
					Info info = new Info(typ2, "брой", 1,
							MyMath.round(pric, 2), doubleDiscount, kontragent,
							invoiceByKontragent);
					mapInfo.put(typ2, info);
				} else {
					Info info2 = mapInfo.get(typ2);
					info2.myarka = "броя";
					info2.quantity++;

				}
			} else if (!TO.equals("не") && !P.equals("не") && HI.equals("не")) {

				String typ3 = "Техническо обслужване и Презареждане на Пожарогасител "
						+ type + " " + weight;

				double prahPrice = PriceTable.getPartPriceFromDB(
						gasitelnoVeshtestvo, type, category, weight);

				double price2 = (prahPrice + WorkingBook.TO_PRICE);

				if (!mapInfo.containsKey(typ3)) {
					Info info = new Info(typ3, "брой", 1, MyMath.round(price2,
							2), doubleDiscount,
							kontragent, invoiceByKontragent);
					mapInfo.put(typ3, info);
				} else {
					Info info2 = mapInfo.get(typ3);
					info2.myarka = "броя";
					info2.quantity++;

				}
			} else if (!TO.equals("не") && !P.equals("не") && !HI.equals("не")) {
				String typ4 = "Техническо обслужване и Презареждане на Пожарогасител "
						+ type + " " + weight;

				double price3 = 0;
				double prahPrice = (PriceTable.getPartPriceFromDB(
						gasitelnoVeshtestvo, type, category, weight));

				price3 = (prahPrice + WorkingBook.TO_PRICE);

				if (!mapInfo.containsKey(typ4)) {
					Info info = new Info(typ4, "брой", 1, MyMath.round(price3,
							2), doubleDiscount,
							kontragent, invoiceByKontragent);
					mapInfo.put(typ4, info);
				} else {
					Info info2 = mapInfo.get(typ4);
					info2.myarka = "броя";
					info2.quantity++;

				}

				String typ5 = "Хидростатично изпитване на Пожарогасител "
						+ type + " " + weight;

				if (!mapInfo.containsKey(typ5)) {

					double pric = (WorkingBook.HI_PRICE);
					Info info = new Info(typ5, "брой", 1,
							MyMath.round(pric, 2), doubleDiscount, kontragent,
							invoiceByKontragent);
					mapInfo.put(typ5, info);
				} else {
					Info info2 = mapInfo.get(typ5);
					info2.myarka = "броя";
					info2.quantity++;

				}

			}
			// calculate parts
			String parts[] = re[7].toString().split(",");
			for (int p = 0; p < parts.length; p++) {

				if (parts[p].equals(gasitelnoVeshtestvo) || parts[p].equals("")) {
					continue;
				}
				double pric = PriceTable.getPartPriceFromDB(parts[p], type,
						category, weight);

				String key = parts[p] + " (" + type + " " + weight + ")";

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
