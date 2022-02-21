package ServiceOrder.ServiceOrderWorkers;

import generators.GenerateSO;
import generators.NumGenerator;

import java.awt.Cursor;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import ServiceOrder.ServiceOrder;
import db.Common;
import db.Protokol.ProtokolTable;

public class SaveinServiceOrderDBWorker extends SwingWorker {
	// private int result = 0;
	private boolean insert = false;
	// private int updateBarcodeInST = 0;
	// private int updateBarcodeInPT = 0;
	private JDialog jd;
	// private final SO_Table so_DB = new SO_Table();
	private String SERVICE_NUMBER = null;
	private String CURRENT_CLIENT = null;

	private ServiceOrder ref;

	public SaveinServiceOrderDBWorker(JDialog jd, String serviceNumber,
			String currentClient) {
		this.jd = jd;
		this.SERVICE_NUMBER = serviceNumber;
		this.CURRENT_CLIENT = currentClient;

	}

	public SaveinServiceOrderDBWorker(JDialog jd, ServiceOrder ref) {
		this.jd = jd;
		this.SERVICE_NUMBER = ref.SERVICE_NUMBER;
		this.CURRENT_CLIENT = ref.CURRENT_CLIENT;
		this.ref = ref;
	}

	public SaveinServiceOrderDBWorker(JDialog jd) {
		this.jd = jd;
	}

	@Override
	public String doInBackground() throws Exception {
		// TODO Auto-generated method stub

		try {

			insert = Common.batchInsertIntoServiceOrder(CURRENT_CLIENT,
					ref.tModel);// ServiceOrder.tModel);
			/*
			 * for (int index = 0; index < ServiceOrder.tModel.getRowCount();
			 * index++) {
			 * 
			 * result = saveInDB(index);
			 * 
			 * if(result == 0) { break; }
			 * 
			 * }
			 */

			if (insert) {
				// update service order number
				String updateNumber = NumGenerator.updateNum(SERVICE_NUMBER);
				int update = GenerateSO.updateServiceOrder(updateNumber);// so_DB.updateSO_InDB(updateNumber);
				// System.out.println("update" + update);
				// delete old barcodes
				if (ref.updateExtinguisher.size() > 0) { // (ServiceOrder.updateExtinguisher.size()
															// > 0) {

					ProtokolTable
							.setExtinguisherUpToDateWithBatch(ref.updateExtinguisher);
					// delete this -> old protokols needed
					// MainTable.deleteExtinguisherWithBatch(MainTable.SERVICE,
					// ref.updateExtinguisher);
					// MainTable.deleteExtinguisherWithBatch(MainTable.PROTOKOL,
					// ref.updateExtinguisher);
				}

			}
			SERVICE_NUMBER = GenerateSO.nextSO(); // so_DB.getSO_Number();

		} finally {

			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // reset
																		// cursor

					if (insert) {

						JOptionPane.showMessageDialog(null,
								"Данните са записани успешно!");
						ref.updateExtinguisher.clear();
						ref.serviceNumberLabel.setName(SERVICE_NUMBER);
						ref.tModel.setRowCount(0);
					}

				}

			});
		}
		return SERVICE_NUMBER;
	}

	/*
	 * int saveInDB(int modelIndex) { // then insert new updated data int result
	 * = 0; // result from insertion result =
	 * MainTable.insertIntoServiceTableDB( CURRENT_CLIENT, // client
	 * ServiceOrder.tModel.getValueAt(modelIndex, 0).toString(), // type
	 * ServiceOrder.tModel.getValueAt(modelIndex, 1).toString(), // wheight
	 * ServiceOrder.tModel.getValueAt(modelIndex, 2).toString(), // barcod
	 * ServiceOrder.tModel.getValueAt(modelIndex, 3).toString(), // serial
	 * ServiceOrder.tModel.getValueAt(modelIndex, 4).toString(), // category
	 * ServiceOrder.tModel.getValueAt(modelIndex, 5).toString(), // brand
	 * ServiceOrder.tModel.getValueAt(modelIndex, 6).toString(), // T_O
	 * ServiceOrder.tModel.getValueAt(modelIndex, 7).toString(), // P
	 * ServiceOrder.tModel.getValueAt(modelIndex, 8).toString(), // HI
	 * ServiceOrder.tModel.getValueAt(modelIndex, 9).toString(), // done
	 * ServiceOrder.tModel.getValueAt(modelIndex, 10).toString(), //
	 * double_written ServiceOrder.tModel.getValueAt(modelIndex, 11).toString(),
	 * // номер // на // сервизна // поръчка
	 * ServiceOrder.tModel.getValueAt(modelIndex, 12).toString(), // продавач
	 * ServiceOrder.tModel.getValueAt(modelIndex, 13).toString());// date return
	 * result;
	 * 
	 * }
	 */
}
