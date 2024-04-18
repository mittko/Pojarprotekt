package invoice.worker;

import db.�rtikul.ArtikulInfo;
import db.�rtikul.Artikuli_DB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class DecreaseArtikulQuantityWorker extends SwingWorker {

	private DefaultTableModel artikulModel;
    private String dbTable;
	public DecreaseArtikulQuantityWorker(String dbTable,DefaultTableModel artikulModel) {
		this.dbTable = dbTable;
		this.artikulModel = artikulModel;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub
		try {

			// ��� ���������� ���� ���� �������������� (��� �� �����������) ��
			// �� ������ ��
			// ������������, ������ ��� �� ������� ������������ �������� � ����
			// ����� - ��������
			// �� � ���� ����� - ���� �������������� (�� �� ������ �� ����� ��
			// ����� ����� ��
			// ����������� �� � ���������) � � �� ������ ������ ���� �� �
			// �������� �� �������� �� ����
			// �������������� !!!
			for (int row = 0; row < artikulModel.getRowCount(); row++) {

				if (artikulModel.getValueAt(row, 7) != null) {
					// ako e null znachi e napraveno obslujwane inache e artikul
					int quantityToDecrease = Integer.parseInt(artikulModel
							.getValueAt(row, 2).toString());
					String artikul = artikulModel.getValueAt(row, 0).toString();

					String kontragent = artikulModel.getValueAt(row, 6)
							.toString();

					String invoiceByKontragent = artikulModel
							.getValueAt(row, 7).toString();

					ArrayList<ArtikulInfo> availableArtikuls = Artikuli_DB
							.getAvailableArtikulsByInvoiceAndKontragent(
									dbTable,
									artikul, kontragent, invoiceByKontragent);
					// this method returns artikuls sorted !!!!
					for (ArtikulInfo art : availableArtikuls) {

						if (quantityToDecrease > art.getQuantity()) {
							// decrease art.getQuantity
							Artikuli_DB.decreaseArtikul_Quantity(dbTable,
									art.getArtikulName(), art.getKontragent(),"client" ,
                                    art.getInvoiceByKontragent(),"invoice" , art.getQuantity());

							quantityToDecrease -= art.getQuantity();

						} else if (quantityToDecrease <= art.getQuantity()) {
							// decrease quantity
							Artikuli_DB.decreaseArtikul_Quantity(dbTable,
									art.getArtikulName(), art.getKontragent(),"client" ,
                                    art.getInvoiceByKontragent(), "invoice", quantityToDecrease);

							// System.out.printf("%s %s %s %d %d %s",
							// art.getArtikulName(), art.getKontragent(),
							// art.getInvoiceByKontragent(),
							// quantityToDecrease, art.getQuantity()
							// - quantityToDecrease,
							// GetDate.getReversedSystemDate());

							quantityToDecrease = 0;
							break;
						}
					}
				}
			}

		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
				}

			});
		}
		return null;
	}
}
