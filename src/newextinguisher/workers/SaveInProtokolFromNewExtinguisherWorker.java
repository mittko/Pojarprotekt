package newextinguisher.workers;

import generators.ProtokolGenerator;
import http.RequestCallback2;
import http.new_extinguishers.NewExtinguisherService;
import http.protokol.ProtokolService;
import models.PartsModel;
import models.ProtokolModel;
import models.ProtokolModels;
import newextinguisher.NewExtinguisherWindow;
import models.ExtinguisherModel;
import db.NewExtinguisher.NewExtinguishers_DB;
import db.Protokol.ProtokolNumber;
import db.Protokol.ProtokolTable;
import db.ServiceOrder.ServiceNumber;
import generators.GenerateSO;
import generators.NumGenerator;
import mydate.MyGetDate;
import utils.MainPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class SaveInProtokolFromNewExtinguisherWorker extends SwingWorker {

	private JDialog jd;
	private String CURRENT_CLIENT = null;
	private String SERVICE_NUMBER = null;

	private final ServiceNumber soTable = new ServiceNumber();
	private final ProtokolGenerator pg = new ProtokolGenerator();
	private int[] next_number = null;
	private String newServiceNumber = null;
	private int updateServiceNum = 0;

	private DefaultTableModel newExtinguisherTableModel;

	public SaveInProtokolFromNewExtinguisherWorker(JDialog jDialog, String currentClient,
												   DefaultTableModel newExtinguisherTableModel) {
		this.jd = jDialog;
		this.CURRENT_CLIENT = currentClient;
		this.newExtinguisherTableModel = newExtinguisherTableModel;
	}

	@Override
	public String[] doInBackground() throws Exception {
		// TODO Auto-generated method stub



			ArrayList<ProtokolModel> protokolModelList = new ArrayList<>();

			for(int index = 0;index < newExtinguisherTableModel.getRowCount();index++) {

				String type = newExtinguisherTableModel.getValueAt(index, 0).toString() + " ( Нов )"; // type
				String weight =	newExtinguisherTableModel.getValueAt(index,1).toString(); // weight
				String barcod =	newExtinguisherTableModel.getValueAt(index, 2).toString(); // barcod
				String serialNumber = newExtinguisherTableModel.getValueAt(index, 3).toString(); // serial
				String category = newExtinguisherTableModel.getValueAt(index, 4).toString(); // category
				String brand =	newExtinguisherTableModel.getValueAt(index, 5).toString(); // brand

				String parts = 	""; // parts
				String value =	newExtinguisherTableModel.getValueAt(index,6).toString(); // value
				String kontragent = "-";// ПОЖАРПРОТЕКТ 00Д
				String invoiceByKontragent ="-";// 0000001
				String additional_data = newExtinguisherTableModel.getValueAt(index, 7).toString();// допълнителни данни


				ProtokolModel protokolModel = new ProtokolModel();
				protokolModel.setClient(CURRENT_CLIENT);
				protokolModel.setType(type);
				protokolModel.setWheight(weight);
				protokolModel.setBarcod(barcod);
				protokolModel.setSerial(serialNumber);
				protokolModel.setCategory(category);
				protokolModel.setBrand(brand);
				protokolModel.setT_O(MyGetDate.getDateAfterToday(365));
				protokolModel.setP("не");
				protokolModel.setHi("не");
				protokolModel.setParts(parts);
				protokolModel.setValue(String.valueOf(value));
				protokolModel.setPerson("Георги Ильов");
				protokolModel.setDate(MyGetDate.getReversedSystemDate());
				protokolModel.setUptodate("null");
				protokolModel.setKontragent(kontragent);
				protokolModel.setInvoiceByKontragent(invoiceByKontragent);
				protokolModel.setAdditional_data(additional_data);

				protokolModelList.add(protokolModel);
			}

			ProtokolModels body = new ProtokolModels();
			body.setList(protokolModelList);
			body.setParts(new ArrayList<>());

			    ProtokolService service = new ProtokolService();
				service.insertProtokol(body, new RequestCallback2() {
					@Override
					public <T> void callback(T t) {
						jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

						String nextProtokol = (String) t;
						if(nextProtokol != null) {
							// update service number
							SERVICE_NUMBER = GenerateSO.nextSO(); // soTable.getSO_Number();
							newServiceNumber = NumGenerator.updateNum(SERVICE_NUMBER);
							GenerateSO.updateServiceOrder(newServiceNumber); // soTable.updateSO_InDB(newServiceNumber);//updateServiceNum


							NewExtinguisherWindow.dftm.setRowCount(0);

							JOptionPane.showMessageDialog(null,
									"Данните са записани успешно!");
						}
					}
				});


			//	updateNewExtinguisherQuantity2();

			

		return null;
	}

	// Тук си ги намалява в База Данни - Нови Пожарогасители както трябва оше
	// при продажба и издаване на Протокол
	private void updateNewExtinguisherQuantity2() {
		for (int row = 0; row < newExtinguisherTableModel.getRowCount(); row++) {

			if (newExtinguisherTableModel.getValueAt(row, 7) != null) {
				// колона 7 е за контрагента ако е null или Пожарпротект
				// значи пожарогасителя е излезнал от работилницата
				// ako e null значи е направено обслужване в работилницата иначе e нов пожарогасител
				int quantityToDecrease = 1;// one row for every extinguisher
				String typeExtinguisher = newExtinguisherTableModel.getValueAt(
						row, 0).toString();
				String wheight = newExtinguisherTableModel.getValueAt(row, 1)
						.toString();
				String category = newExtinguisherTableModel.getValueAt(row, 4)
						.toString();
				String brand = newExtinguisherTableModel.getValueAt(row, 5)
						.toString();
				String kontragent = newExtinguisherTableModel
						.getValueAt(row, 7).toString();
				String invoice = newExtinguisherTableModel.getValueAt(row, 8)
						.toString();
				ArrayList<ExtinguisherModel> availableExtinguishers = NewExtinguishers_DB
						.getAvailableExtinguishersByInvoiceAndKontragent(
								kontragent, invoice, typeExtinguisher, wheight,
								category/*, brand*/);
			//	 System.out.println("size = " + availableExtinguishers.size());
				for (ExtinguisherModel ext : availableExtinguishers) {

					// System.out.printf("%s %s %s %s %s %s %s %d\n", ext
					// .getDate().toString(),
					// ext.getInvoiceByKontragent(), ext.getKontragent(),
					// ext.getType(), ext.getWheight(), ext.getCategory(),
					// ext.getBrand(), ext.getQuantity());

					if (quantityToDecrease > 0) {
						int decrease = NewExtinguishers_DB
								.decreaseExtinguisher_Quantity(ext.getType(),
										ext.getWheight(), ext.getCategory(),
										ext.getBrand(),
										ext.getInvoiceByKontragent(),
										ext.getKontragent(), quantityToDecrease);
						 System.out.println("decrease = " + decrease);

						quantityToDecrease--;
						System.out.println(quantityToDecrease);
					}

				}
			}
		}
	}


}
