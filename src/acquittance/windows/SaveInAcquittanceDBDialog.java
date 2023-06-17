package acquittance.windows;

import Exceptions.DBException;
import invoice.worker.DecreaseArtikulQuantityWorker;
import invoice.worker.GetAqcuittanceNumberWorker;
import invoice.worker.SaveInAcquittanceWorker;
import invoicewindow.SearchFromProformTab;
import invoicewindow.SearchFromProtokolTab;
import utils.BevelLabel;
import utils.MainPanel;
import utils.MyMath;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;


public class SaveInAcquittanceDBDialog extends MainPanel {

	private static final long serialVersionUID = 1L;
	private JCheckBox acquittanceRadioButton = null;
	private JButton saveData = null;
	Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
	/*
	 * private boolean isInvoice = false; private boolean isProform = false;
	 * private boolean isAcquittance = false;
	 */

	private final String CLIENT;
	private final DefaultTableModel dftm;

	public static boolean WRITE_IN_ACQUITTANCE_SUCCESS;

	private final DefaultTableModel copyOriginTableModel = new DefaultTableModel();

	public SaveInAcquittanceDBDialog(final String protokolNumber, String client,
									 final String payment, final String discount, final String sum,
									 final String personName, final String date, boolean calledFromInvoiceWindow,
									 final boolean calledFromProformWindow,
									 final DefaultTableModel dftm, final BevelLabel invoiceLabel,
									 final BevelLabel proformLabel, final BevelLabel acquittanceLabel) {
		// parameters to save data
		this.CLIENT = client;

		this.dftm = dftm;

		WRITE_IN_ACQUITTANCE_SUCCESS = false;
		acquittanceRadioButton = new JCheckBox("Стокова Разписка");
		acquittanceRadioButton.setVisible(MainPanel.ACCESS_MENU[ACCESS_ACQUITTANCE]);
		acquittanceRadioButton.setOpaque(false);
		acquittanceRadioButton.setPreferredSize(new Dimension(200, 50));
		acquittanceRadioButton.setFont(getFONT());
		acquittanceRadioButton.setCursor(HAND_CURSOR);
		acquittanceRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				saveData.setEnabled(acquittanceRadioButton.isSelected());
			}

		});


		JLabel questionLabel = new JLabel(
				"  Как желаете да съхраните данните ?");

		JPanel gridBagPanel = new JPanel();
		gridBagPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		gridBagPanel.setLayout(new GridBagLayout());

		saveData = new JButton("Запиши");
		saveData.setCursor(HAND_CURSOR);
		saveData.setEnabled(false);
		saveData.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

					JDialog jd = (JDialog) SwingUtilities
						.getWindowAncestor(SaveInAcquittanceDBDialog.this);

				copyOriginDefaultTabelModel();

				WRITE_IN_ACQUITTANCE_SUCCESS = false;

			 if( acquittanceRadioButton.isSelected()) {
						jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));

						try {
							GetAqcuittanceNumberWorker getAcquittanceNumber = new GetAqcuittanceNumberWorker();
							String updateAcquittanceNumber = getAcquittanceNumber
									.doInBackground();
							SaveInAcquittanceWorker saveInAcquittance = new SaveInAcquittanceWorker(
									copyOriginTableModel, updateAcquittanceNumber,
									MyMath.round(Double.parseDouble(sum) , 2),
									// without ДДС sum/1.2 по старо му
									personName, CLIENT, date, acquittanceLabel, jd);
					     	WRITE_IN_ACQUITTANCE_SUCCESS = saveInAcquittance.doInBackground();
							try {
								if (WRITE_IN_ACQUITTANCE_SUCCESS ) {
										DecreaseArtikulQuantityWorker decreaseArtikul =
												new DecreaseArtikulQuantityWorker(
														GREY_AVAILABLE_ARTIKULS,
												copyOriginTableModel);
										decreaseArtikul.execute();

								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								DBException.DBExceptions(
										"Error during saving acquittance !", e);
							}
						} catch (Exception ex) {
							// TODO Auto-generated catch block
							ex.printStackTrace();
							DBException.DBExceptions(
									"Error during saving acquittance !", ex);
						}
					}

				SearchFromProtokolTab.clear();
				SearchFromProformTab.clear();
			    GreyArtikulTab.clear(); // ?????

				jd.dispose();

			}

		});

		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.add(saveData);

		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.fill = GridBagConstraints.HORIZONTAL;
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.gridwidth = 1;
		// gbc1.insets = new Insets(5, 5, 5, 5);

		gridBagPanel.add(questionLabel, gbc1);

		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.fill = GridBagConstraints.HORIZONTAL;
		gbc2.gridx = 0;
		gbc2.gridy = 1;
		gbc2.gridwidth = 1;
		// gbc2.insets = new Insets(5, 0, 5, 5);

		gridBagPanel.add(acquittanceRadioButton, gbc2);

		GridBagConstraints gbc6 = new GridBagConstraints();
		gbc6.fill = GridBagConstraints.HORIZONTAL;
		gbc6.gridx = 1;
		gbc6.gridy = 1;
		gbc6.gridwidth = 1;
		// gbc5.insets = new Insets(5, 0, 5, 5);
		// basePanel.add(gridBagPanel);

		gridBagPanel.add(buttonPanel, gbc6);

		this.add(gridBagPanel);

	}

	private void copyOriginDefaultTabelModel() {
		copyOriginTableModel.setRowCount(0);

		int rowCount = this.dftm.getRowCount();

		int colCount = this.dftm.getColumnCount();

		for (int column = 0; column < dftm.getColumnCount(); column++) {
			copyOriginTableModel.addColumn(this.dftm.getColumnName(column));
		}
		Vector<Vector<Object>> copy = new Vector<Vector<Object>>(rowCount);

		for (int row = 0; row < rowCount; row++) {

			Vector<Object> newRow = new Vector<Object>(colCount);
			copy.add(newRow);

			for (int col = 0; col < colCount; col++) {
				newRow.add(this.dftm.getValueAt(row, col));
			}

			copyOriginTableModel.insertRow(row, newRow);
		}

	}
}
