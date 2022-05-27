package invoice.Sklad.Worker;

import db.Artikul.Artikuli_DB;
import invoice.Sklad.SkladArtikulFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static utility.MainPanel.AVAILABLE_ARTIKULS;

public class LoadAllArtikulsFromInvoiceWorker extends SwingWorker {
	
	private ArrayList<Object[]> result = null;
	private JDialog  jd = null;
	
	public LoadAllArtikulsFromInvoiceWorker(JDialog jd) {
		this.jd = jd;
	}
	@Override
	protected Void doInBackground() throws Exception {
		// TODO Auto-generated method stub
		// do request from db
		try {

			result = Artikuli_DB.getAvailableArtikuls(AVAILABLE_ARTIKULS);
			if (result == null) {
				return null;
			}
		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					// if is success
                     if(result.size() > 0) {

						 for (Object[] objects : result) {
							 // set in the table imediatelly
//						 String newValue = String.format(Locale.ROOT,
//								 "%.2f",
//								 Double.parseDouble(result.get(row)[3].toString().replace(",", ".")));// format to two decimal places after points
//
							 SkladArtikulFrame.skladModel.addRow(new Object[]{
									 objects[0], // �������
									 objects[1], // ����������
									 objects[2], // �.��
									 objects[3], // ��.����
									 "", // value to add
									 false,
									 objects[4],// �������
									 objects[5] // ����������});
							 });
							 // store data in list to change table dinamically
							 SkladArtikulFrame.DATA_LIST.add(new Object[]{
									 objects[0], // �������
									 objects[1], // ����������
									 objects[2], // �.��
									 objects[3], // ��.����
									 "", // value to add
									 false,
									 objects[4],// �������
									 objects[5] // ����������});
							 });
						 }
                   } else {
                	   JOptionPane.showMessageDialog(null, "���� �������� ���������!");
                   }
					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			});
		}
		return null;
	}
	
}
