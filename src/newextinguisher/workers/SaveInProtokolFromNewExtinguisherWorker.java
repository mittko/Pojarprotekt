package newextinguisher.workers;

import newextinguisher.NewExtinguisherWindow;
import db.NewExtinguisher.ExtinguishersInfo;
import db.NewExtinguisher.NewExtinguishers_DB;
import db.Protokol.ProtokolNumber;
import db.Protokol.ProtokolTable;
import db.ServiceOrder.ServiceNumber;
import generators.GenerateSO;
import generators.NumGenerator;
import generators.ProtokolGenerator;
import mydate.MyGetDate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class SaveInProtokolFromNewExtinguisherWorker extends SwingWorker {

	private JDialog jd;
	private String CURRENT_CLIENT = null;
	private String SERVICE_NUMBER = null;
	private String protokol_Number = null;
	private int result = 0;

	private final ServiceNumber soTable = new ServiceNumber();
	private final ProtokolGenerator pg = new ProtokolGenerator();
	private int[] next_number = null;
	private String newServiceNumber = null;
	private int updateServiceNum = 0;
	private int updateProtokolNum = 0;
	private String[] NUMBERS = new String[2]; // Protokol -> 1 index and Service
												// numbers -> 0 index
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

		try {

			// update service number
			SERVICE_NUMBER = GenerateSO.nextSO(); // soTable.getSO_Number();

			// update protokol number
			protokol_Number = ProtokolNumber.getProtokolNumber();

			int[] insertResult = ProtokolTable.batchInsertNewExtinguishers(
					newExtinguisherTableModel, CURRENT_CLIENT,
					MyGetDate.getDateAfterToday(365), // TO
					"не", // P
					"не", // HI
					"", // parts
					protokol_Number, // protokolNumber
					"Георги Ильов",//MainPanel.personName, // person
					MyGetDate.getReversedSystemDate());// date
			for (int j : insertResult) {
				if (j == 1) {
					result = 1;
					break;
				}
			}
			if (result == 1) {

				newServiceNumber = NumGenerator.updateNum(SERVICE_NUMBER);
				updateServiceNum = GenerateSO
						.updateServiceOrder(newServiceNumber); // soTable.updateSO_InDB(newServiceNumber);//updateServiceNum

				next_number = pg.updateProtokol(protokol_Number);
				updateProtokolNum = ProtokolNumber.updateProtokolNumberInDB(pg
						.digitsToString(next_number));

				NUMBERS[0] = newServiceNumber; // -> next service number
				NUMBERS[1] = protokol_Number; // -> current protokol number for
												// invoice

				updateNewExtinguisherQuantity2();

			}

		} finally {

			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					if (result > 0 && updateServiceNum > 0
							&& updateProtokolNum > 0) {

						JOptionPane.showMessageDialog(null,
								"Данните са записани успешно!");

						NewExtinguisherWindow.protokolNumLabel.setName(pg
								.digitsToString(next_number));

						NewExtinguisherWindow.dftm.setRowCount(0);
						// NewExtinguisherWindow.updateQuantityOfExtinguisherModel.setRowCount(0);

					}

				}

			});
		}
		return updateServiceNum > 0 ? NUMBERS : null;
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
				ArrayList<ExtinguishersInfo> availableExtinguishers = NewExtinguishers_DB
						.getAvailableExtinguishersByInvoiceAndKontragent(
								kontragent, invoice, typeExtinguisher, wheight,
								category/*, brand*/);
			//	 System.out.println("size = " + availableExtinguishers.size());
				for (ExtinguishersInfo ext : availableExtinguishers) {

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
	// private void updateNewExtinguisherQuantity() {
	// HashMap<String, Integer> mapQuantity = new HashMap<String, Integer>();
	// int rows = NewExtinguisher2.updateQuantityOfExtinguisherModel
	// .getRowCount();
	// int columns = NewExtinguisher2.updateQuantityOfExtinguisherModel
	// .getColumnCount();
	// for (int row = 0; row < rows; row++) {
	// StringBuilder keyBuilder = new StringBuilder();
	// for (int column = 0; column < columns; column++) {
	// keyBuilder
	// .append(NewExtinguisher2.updateQuantityOfExtinguisherModel
	// .getValueAt(row, column));
	// if (column < columns - 1) {
	// keyBuilder.append("-");
	// }
	// }
	// Integer value = mapQuantity.get(keyBuilder.toString());
	// if (value == null) {
	// value = 0;
	// }
	// mapQuantity.put(keyBuilder.toString(), value + 1);
	// }
	// NewExtinguishers_DB.updateNewExtinguisherQuantityWithBatch(mapQuantity);
	//
	// }

}
