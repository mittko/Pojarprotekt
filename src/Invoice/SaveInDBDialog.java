package Invoice;

import Exceptions.DBException;
import Invoice.Fiskal.CreateBonFPrint;
import Invoice.worker.*;
import invoicewindow.ArtikulTab;
import invoicewindow.SearchFromProformTab;
import invoicewindow.SearchFromProtokolTab;
import mydate.MyGetDate;
import utility.BevelLabel;
import utility.MainPanel;
import utility.MyMath;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

public class SaveInDBDialog extends MainPanel {

	private static final long serialVersionUID = 1L;
	private JRadioButton invoiceRadioButton = null;
	private JRadioButton proformRadioButton = null;
	private JRadioButton acquittanceRadioButton = null;
	private JRadioButton fiskalRadioButton = null;
	private JButton saveData = null;
	private String dest = "";

	/*
	 * private boolean isInvoice = false; private boolean isProform = false;
	 * private boolean isAcquittance = false;
	 */

	private String protokolNumber = null;
	private String CLIENT;
	private String payment = null;
	private String discount = null;
	private String sum = null;
	private String personName = null;
	private String date = null;
	private DefaultTableModel dftm;
	private BevelLabel invoiceLabel;
	private BevelLabel proformLabel;
	private BevelLabel acquittanceLabel;
	// private InvoiceGenerator ig = null;

	public static boolean WRITE_IN_INVOICE_SUCCESS;
	public static boolean WRITE_IN_PROFORM_SUCCESS;
	public static boolean WRITE_IN_ACQUITTANCE_SUCCESS;
	public static boolean WRITE_IN_FISKAL;

	private final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);

	private DefaultTableModel copyOriginTableModel = new DefaultTableModel();

	public SaveInDBDialog(final String protokolNumber, String client,
			final String payment, final String discount, final String sum,
			final String personName, final String date, boolean isInvoice,
			boolean isProform, boolean isAcquittance,
			final DefaultTableModel dftm, final BevelLabel invoiceLabel,
			final BevelLabel proformLabel, final BevelLabel acquittanceLabel) {
		// parameters to save data
		this.protokolNumber = protokolNumber;
		this.CLIENT = client;
		this.payment = payment;
		this.discount = discount;
		this.sum = sum;
		this.personName = personName;
		this.date = date;
		// set enabled/disabled radio buttons
		// this.isInvoice = isInvoice;
		// this.isProform = isProform;
		// this.isAcquittance = isAcquittance;

		this.dftm = dftm;
		this.invoiceLabel = invoiceLabel;
		this.proformLabel = proformLabel;
		this.acquittanceLabel = acquittanceLabel;

		WRITE_IN_INVOICE_SUCCESS = false;
		WRITE_IN_PROFORM_SUCCESS = false;
		WRITE_IN_ACQUITTANCE_SUCCESS = false;
		WRITE_IN_FISKAL = false;
		ButtonGroup bg = new ButtonGroup();

		invoiceRadioButton = new JRadioButton("Фактура");
		invoiceRadioButton.setCursor(HAND_CURSOR);
		invoiceRadioButton.setOpaque(false);
		invoiceRadioButton.setFont(getFONT());
		invoiceRadioButton.setEnabled(isInvoice);

		invoiceRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				saveData.setEnabled(true);
				dest = "invoice";
			}

		});

		proformRadioButton = new JRadioButton("Про-форма ");
		proformRadioButton.setCursor(HAND_CURSOR);
		proformRadioButton.setOpaque(false);
		proformRadioButton.setPreferredSize(new Dimension(200, 50));
		proformRadioButton.setFont(getFONT());
		proformRadioButton.setEnabled(isProform);

		proformRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				saveData.setEnabled(true);
				dest = "proform";
			}

		});

		acquittanceRadioButton = new JRadioButton("Стокова Разписка");
		acquittanceRadioButton.setVisible(MainPanel.ACCESS_MENU[ACCESS_ACQUITTANCE]);
		acquittanceRadioButton.setCursor(HAND_CURSOR);
		acquittanceRadioButton.setOpaque(false);
		acquittanceRadioButton.setPreferredSize(new Dimension(200, 50));
		acquittanceRadioButton.setFont(getFONT());
		acquittanceRadioButton.setEnabled(isAcquittance);
		acquittanceRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				saveData.setEnabled(true);
				dest = "acquittance";
			}

		});
		fiskalRadioButton = new JRadioButton("Фискален бон");
		fiskalRadioButton.setCursor(HAND_CURSOR);
		fiskalRadioButton.setOpaque(false);
		fiskalRadioButton.setPreferredSize(new Dimension(200, 50));
		fiskalRadioButton.setFont(getFONT());
		fiskalRadioButton.setEnabled(payment.equals("В брой") ? true : false);
		fiskalRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				saveData.setEnabled(true);
				dest = "fiskal";

			}

		});

		bg.add(invoiceRadioButton);

		bg.add(proformRadioButton);

		bg.add(acquittanceRadioButton);

		bg.add(fiskalRadioButton);

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
						.getWindowAncestor(SaveInDBDialog.this);

				copyOriginDefaultTabelModel();

				WRITE_IN_INVOICE_SUCCESS = false;
				WRITE_IN_PROFORM_SUCCESS = false;
				WRITE_IN_ACQUITTANCE_SUCCESS = false;
				WRITE_IN_FISKAL = false;

				switch (dest) {
					case "invoice":
						jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));

						GetInvoiceNumberWorker getInvoiceNumber = new GetInvoiceNumberWorker();
						String updatedInvoiceNumber = null;
						try {
							updatedInvoiceNumber = getInvoiceNumber
									.doInBackground();
							SaveInInvoiceDBWorker saveinInvoice = new SaveInInvoiceDBWorker(
									jd, payment, discount, sum, CLIENT, personName,
									date, updatedInvoiceNumber, protokolNumber,
									copyOriginTableModel, invoiceLabel);

							try {
								WRITE_IN_INVOICE_SUCCESS = saveinInvoice
										.doInBackground();
								if (WRITE_IN_INVOICE_SUCCESS) {
									DecreaseArtikulQuantityWorker decreaseArtikul = new DecreaseArtikulQuantityWorker(
											copyOriginTableModel);
									decreaseArtikul.execute();

									invoiceRadioButton.setEnabled(false);
									proformRadioButton.setEnabled(false);
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								DBException.DBExceptions(
										"Error during update artikuls quantity !",
										e);
							}
						} catch (Exception ex) {
							// TODO Auto-generated catch block
							ex.printStackTrace();
							DBException.DBExceptions(
									"Error  during saving invoice !", ex);
						}

						// invoice + fiskal bon
						if (fiskalRadioButton.isEnabled()
								&& WRITE_IN_INVOICE_SUCCESS) {
							CreateBonFPrint bon = new CreateBonFPrint();
							ArrayList<String> commandList = bon.makeReciept(sum);
							SellWithFiskalBonWorker sellWorker = new SellWithFiskalBonWorker(
									commandList, bon, jd);
							sellWorker.execute();
							fiskalRadioButton.setEnabled(false);
						}

						break;
					case "proform":
						jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
						GetProformNumberWorker getProformNumber = new GetProformNumberWorker();
						String updateProformNumber = null;
						try {
							updateProformNumber = getProformNumber.doInBackground();
							SaveInProformDBWorker saveinProform = new SaveInProformDBWorker(
									jd, payment, discount, sum, CLIENT, personName,
									date, updateProformNumber, protokolNumber,
									copyOriginTableModel, proformLabel);
							try {
								WRITE_IN_PROFORM_SUCCESS = saveinProform
										.doInBackground();
								if (WRITE_IN_PROFORM_SUCCESS) {

									proformRadioButton.setEnabled(false);
									invoiceRadioButton.setEnabled(false);
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								DBException.DBExceptions(
										"Error during saving proform !", e);
							}
						} catch (Exception ex2) {
							// TODO Auto-generated catch block
							ex2.printStackTrace();
							DBException.DBExceptions(
									"Error during saving proform !", ex2);
						}

						break;
					case "acquittance":
						jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));

						GetAqcuittanceNumberWorker getAcquittanceNumber = new GetAqcuittanceNumberWorker();
						String updateAcquittanceNumber = null;
						try {
							updateAcquittanceNumber = getAcquittanceNumber
									.doInBackground();
							SaveInAcquittanceWorker saveInAcquittance = new SaveInAcquittanceWorker(
									copyOriginTableModel, updateAcquittanceNumber,
									MyMath.round(Double.parseDouble(sum) / 1.2, 2), // without
									// ДДС
									personName, CLIENT, date, acquittanceLabel, jd);
							try {
								WRITE_IN_ACQUITTANCE_SUCCESS = saveInAcquittance
										.doInBackground();
								if (WRITE_IN_ACQUITTANCE_SUCCESS) {
									acquittanceRadioButton.setEnabled(false);
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

						break;
					case "fiskal":
						jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));

						/*
						 * double doubleSum = 0.0; for(int row = 0;row <
						 * dftm.getRowCount();row++) { doubleSum +=
						 * Double.parseDouble(dftm.getValueAt(row, 4).toString());
						 * // final sum }
						 *
						 * System.out.println(doubleSum);
						 */
						String fiskalOfficialNumber = MyGetDate
								.generateFiskalBonNumber();
						SaveFiskalBonInInvoiceDBWorker saveFiskalInInvoice = new SaveFiskalBonInInvoiceDBWorker(
								jd, payment, discount, sum, CLIENT, personName,
								date, fiskalOfficialNumber, protokolNumber,
								copyOriginTableModel, invoiceLabel);

						try {
							WRITE_IN_INVOICE_SUCCESS = saveFiskalInInvoice
									.doInBackground();
							if (WRITE_IN_INVOICE_SUCCESS) {
								CreateBonFPrint bon = new CreateBonFPrint();
								ArrayList<String> commandList = bon
										.makeReciept(sum);
								SellWithFiskalBonWorker sellWorker = new SellWithFiskalBonWorker(
										commandList, bon, jd);
								sellWorker.execute();

								DecreaseArtikulQuantityWorker decreaseArtikul = new DecreaseArtikulQuantityWorker(
										copyOriginTableModel);
								decreaseArtikul.execute();

								invoiceRadioButton.setEnabled(false);
								proformRadioButton.setEnabled(false);
								fiskalRadioButton.setEnabled(false);
							}
						} catch (Exception ex) {
							// TODO Auto-generated catch block
							ex.printStackTrace();
							DBException.DBExceptions(
									"Error  during saving invoice !", ex);
						}

						break;
				}
				SearchFromProtokolTab.clear();
				SearchFromProformTab.clear();
				ArtikulTab.clear(); // ?????

				// clear dest
				dest = "";
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

		gridBagPanel.add(invoiceRadioButton, gbc2);

		GridBagConstraints gbc3 = new GridBagConstraints();
		gbc3.fill = GridBagConstraints.HORIZONTAL;
		gbc3.gridx = 1;
		gbc3.gridy = 1;
		gbc3.gridwidth = 2;
		// gbc3.insets = new Insets(15, 0, 5, 5);

		gridBagPanel.add(proformRadioButton, gbc3);

		GridBagConstraints gbc4 = new GridBagConstraints();
		gbc4.fill = GridBagConstraints.HORIZONTAL;
		gbc4.gridx = 0;
		gbc4.gridy = 2;
		gbc4.gridwidth = 1;
		// gbc4.insets = new Insets(5, 0, 5, 5);

		gridBagPanel.add(acquittanceRadioButton, gbc4);

		GridBagConstraints gbc5 = new GridBagConstraints();
		gbc5.fill = GridBagConstraints.HORIZONTAL;
		gbc5.gridx = 1;
		gbc5.gridy = 2;
		gbc5.gridwidth = 1;
		// gbc5.insets = new Insets(5, 0, 5, 5);

		gridBagPanel.add(fiskalRadioButton, gbc5);

		GridBagConstraints gbc6 = new GridBagConstraints();
		gbc6.fill = GridBagConstraints.HORIZONTAL;
		gbc6.gridx = 1;
		gbc6.gridy = 3;
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
