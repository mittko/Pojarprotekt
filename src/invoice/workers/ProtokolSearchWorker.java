package invoice.workers;

import db.Client.FirmTable;
import db.Discount.DiscountDB;
import db.PartsPrice.PriceTable;
import db.Protokol.ProtokolTable;
import http.RequestCallback2;
import http.sklad.GetArtikulService;
import invoice.invoicewindow.SearchFromProtokolTab;
import models.ProtokolInfo;
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

	private final ProtokolInfo protokolInfo;
	private final ArrayList<ProtokolModel> result;

	public static String discount = null;
	public static double doubleDiscount = 0;
	private String protokolNum = null;
	private final float DDS = 1.0f;
	private final EditableField copySearchField;
	private final TreeMap<String, Info> mapInfo = new TreeMap<String, Info>();
	private final DefaultTableModel fromInvoiceTableModel;
	public static ArrayList<ProtokolModel> moreProtokolsList = new ArrayList<>(); // store

	public ProtokolSearchWorker(EditableField searchField,
								DefaultTableModel dftm, ProtokolInfo protokolInfo) {
		this.copySearchField = searchField;
		this.protokolNum = copySearchField.getText().trim();
		this.fromInvoiceTableModel = dftm;
		this.protokolInfo = protokolInfo;
		this.result = (ArrayList<ProtokolModel>) protokolInfo.getProtokolModels();
	}


	public Boolean doSearch() {
		// TODO Auto-generated method stub
		if (SearchFromProtokolTab.protokolNumberSet
				.contains(protokolNum)) {
			JOptionPane.showMessageDialog(null,
					"Този протокол вече е въведен !");
			return false;
		}
		if (!SearchFromProtokolTab.INVOICE_CURRENT_CLIENT
				.equals("")
				&& !SearchFromProtokolTab.INVOICE_CURRENT_CLIENT
				.equals(result.get(0).getClient())) {
			// return
			JOptionPane.showMessageDialog(null,
					"Въведен е друг клиент !");
			return false;
		}

			//	result.clear();
				mapInfo.clear();


				if (result.size() > 0) {
					// init client
					String client = result.get(0).getClient();

					if (SearchFromProtokolTab.INVOICE_CURRENT_CLIENT.equals("")) {

						moreProtokolsList.clear();

						SearchFromProtokolTab.INVOICE_CURRENT_CLIENT = client;

						discount = protokolInfo.getFirm().getDiscount();

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
		int models = 0;
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

				GetArtikulService service = new GetArtikulService();

				String finalWeight = weight;

				double price = service.getPartPriceSynchronous(parts[p],type,category,weight);

				System.out.println("Part " + parts[p] + " Price " + price + " Weight " + finalWeight);

				String key = parts[p] + " (" + type + " " + finalWeight + ")";
				if(parts[p].contains("Техническо обслужване на Пожарогасител") ||
						parts[p].contains("Презареждане на Пожарогасител")
						|| parts[p].contains("Хидростатично Изпитване на Пожарогасител")) {
					parts[p]  = parts[p] + " " + type
							+ " " + finalWeight;

					System.out.printf("PART %s\n%n",parts[p]);
				}
				if (!mapInfo.containsKey(key)) {

					if (price != 0) {
						Info info = new Info(parts[p], "брой", 1, MyMath.round(
								price, 2), doubleDiscount,
								kontragent, invoiceByKontragent);
						mapInfo.put(key, info);
					}
				} else {
					Info info2 = mapInfo.get(key);
					info2.myarka = "броя";
					info2.quantity++;
				}

			}

		}


			SearchFromProtokolTab.discountField
					.setText(discount);

			SearchFromProtokolTab.choiceDiscountButton
					.setDefaultIcon();

			SearchFromProtokolTab.clientLabel
					.setName( result.get(0).getClient());

			String registrationVat = protokolInfo.getFirm().getVat_registration();

			boolean selected = registrationVat.equals("да");
			SearchFromProtokolTab.registrationVatCheckBox.setSelected(selected);
			SearchFromProtokolTab.switchRegistrationVat();

			vizualizeTable(mapInfo);

			if (copySearchField != null) {
				copySearchField.setText("");
			}

			SearchFromProtokolTab.protokolNumberSet
					.add(protokolNum);

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
