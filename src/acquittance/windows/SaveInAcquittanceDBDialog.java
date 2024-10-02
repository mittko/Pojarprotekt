package acquittance.windows;

import invoice.workers.SaveInAcquittance;
import invoice.invoicewindow.SearchFromProformTab;
import invoice.invoicewindow.SearchFromProtokolTab;
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
	private final JCheckBox acquittanceRadioButton;
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

	public SaveInAcquittanceDBDialog(String client,
									 final String payment, final String discount, final String sum,
									 final String personName, final String date,
									 final DefaultTableModel dftm,
									 final BevelLabel acquittanceLabel) {
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


							SaveInAcquittance saveInAcquittance = new SaveInAcquittance(
									copyOriginTableModel,
									MyMath.round(Double.parseDouble(sum) / 1.2f, 2),
									// without ДДС sum/1.2 по старо му
									personName, CLIENT, date,  jd) {
								@Override
								public String getArtilulTable() {
									return GREY_AVAILABLE_ARTIKULS;
								}
							};
					     	saveInAcquittance.save();

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

		gridBagPanel.add(questionLabel, gbc1);

		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.fill = GridBagConstraints.HORIZONTAL;
		gbc2.gridx = 0;
		gbc2.gridy = 1;
		gbc2.gridwidth = 1;

		gridBagPanel.add(acquittanceRadioButton, gbc2);

		GridBagConstraints gbc6 = new GridBagConstraints();
		gbc6.fill = GridBagConstraints.HORIZONTAL;
		gbc6.gridx = 1;
		gbc6.gridy = 1;
		gbc6.gridwidth = 1;

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
