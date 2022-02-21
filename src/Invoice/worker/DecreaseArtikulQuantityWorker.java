package Invoice.worker;

import java.util.ArrayList;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import db.Artikul.ArtikulInfo;
import db.Artikul.Artikuli_DB;

public class DecreaseArtikulQuantityWorker extends SwingWorker {

	private DefaultTableModel artikulModel;

	public DecreaseArtikulQuantityWorker(DefaultTableModel artikulModel) {
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
					// System.out.printf("���������� = %s ������� = %s\n",
					// kontragent, invoiceByKontragent);

					ArrayList<ArtikulInfo> availableArtikuls = Artikuli_DB
							.getAvailableArtikulsByInvoiceAndKontragent(
									artikul, kontragent, invoiceByKontragent);
					// this method returns artikuls sorted !!!!
					for (ArtikulInfo art : availableArtikuls) {

						// System.out.printf("%s %s %s %d %s\n",
						// art.getInvoice(),
						// art.getClient(), art.getArtikulName(),
						// art.getQuantity(), art.getDate());

						if (quantityToDecrease > art.getQuantity()) {
							// decrease art.getQuantity
							Artikuli_DB.decreaseArtikul_Quantity(
									art.getArtikulName(), art.getKontragent(),
									art.getInvoiceByKontragent(),
									art.getQuantity());

							// System.out.printf("%s %s %s %d %d %s",
							// art.getArtikulName(), art.getKontragent(),
							// art.getInvoiceByKontragent(),
							// art.getQuantity(), 0,
							// GetDate.getReversedSystemDate());

							quantityToDecrease -= art.getQuantity();

						} else if (quantityToDecrease <= art.getQuantity()) {
							// decrease quantity
							Artikuli_DB.decreaseArtikul_Quantity(
									art.getArtikulName(), art.getKontragent(),
									art.getInvoiceByKontragent(),
									quantityToDecrease);

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
