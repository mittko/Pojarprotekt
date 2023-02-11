package invoice;

import Exceptions.DBException;
import invoice.Fiskal.CreateBonFPrint;
import invoice.worker.*;
import invoicewindow.ArtikulTab;
import invoicewindow.SearchFromProformTab;
import invoicewindow.SearchFromProtokolTab;
import mydate.MyGetDate;
import utils.BevelLabel;
import utils.MainPanel;
import utils.MyMath;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

public class SaveInInvoiceDBDialog extends MainPanel {

	private static final long serialVersionUID = 1L;
	private JCheckBox invoiceRadioButton = null;
	private JCheckBox proformRadioButton = null;
	private JCheckBox acquittanceRadioButton = null;
	private JCheckBox fiskalRadioButton = null;
	private JButton saveData = null;
	private String dest = "";

	/*
	 * private boolean isInvoice = false; private boolean isProform = false;
	 * private boolean isAcquittance = false;
	 */

	private final String CLIENT;
	private final DefaultTableModel dftm;
	// private InvoiceGenerator ig = null;

	public static boolean WRITE_IN_INVOICE_SUCCESS;
	public static boolean WRITE_IN_PROFORM_SUCCESS;
	public static boolean WRITE_IN_ACQUITTANCE_SUCCESS;
	public static boolean WRITE_IN_FISKAL_SUCCESS;

	private final DefaultTableModel copyOriginTableModel = new DefaultTableModel();

	private boolean isVatRegistered;

	public SaveInInvoiceDBDialog(final String parentTable, final String childTable, final String protokolNumber, String client,
								 final String payment, final String discount, final String sum,
								 final String personName, final String date, boolean calledFromInvoiceWindow,
								 final boolean calledFromProformWindow, boolean isAcquittance,
								 final DefaultTableModel dftm, final BevelLabel invoiceLabel,
								 final BevelLabel proformLabel, final BevelLabel acquittanceLabel
	                             , final boolean isVatRegistered) {
		// parameters to save data
		this.CLIENT = client;
		this.isVatRegistered = isVatRegistered;


		this.dftm = dftm;

		WRITE_IN_INVOICE_SUCCESS = false;
		WRITE_IN_PROFORM_SUCCESS = false;
		WRITE_IN_ACQUITTANCE_SUCCESS = false;
		WRITE_IN_FISKAL_SUCCESS = false;

		invoiceRadioButton = new JCheckBox("Фактура");
		Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
		invoiceRadioButton.setCursor(HAND_CURSOR);
		invoiceRadioButton.setOpaque(false);
		invoiceRadioButton.setFont(getFONT());

		invoiceRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(!calledFromProformWindow) {// && !payment.equals("В брой")) {
					proformRadioButton.setEnabled(!invoiceRadioButton.isSelected());
				}
				if(payment.equals("В брой")) {
					acquittanceRadioButton.setEnabled(!invoiceRadioButton.isSelected());
				}
				if(payment.equals("В брой") || calledFromProformWindow) {
					fiskalRadioButton.setSelected(invoiceRadioButton.isSelected());
				}
				proformRadioButton.setSelected(false);
				acquittanceRadioButton.setSelected(false);

				saveData.setEnabled(invoiceRadioButton.isSelected());
				dest = "invoice";
			}

		});

		proformRadioButton = new JCheckBox("Про-форма ");
		proformRadioButton.setCursor(HAND_CURSOR);
		proformRadioButton.setOpaque(false);
		proformRadioButton.setPreferredSize(new Dimension(200, 50));
		proformRadioButton.setFont(getFONT());
		proformRadioButton.setEnabled(!calledFromProformWindow);// && !payment.equals("В брой")

		proformRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				invoiceRadioButton.setEnabled(!proformRadioButton.isSelected());
				invoiceRadioButton.setSelected(false);

				if(!calledFromProformWindow && payment.equals("В брой")) {
					acquittanceRadioButton.setEnabled(!proformRadioButton.isSelected());
					fiskalRadioButton.setEnabled(!proformRadioButton.isSelected());
				}
				acquittanceRadioButton.setSelected(false);
				fiskalRadioButton.setSelected(false);

				saveData.setEnabled(proformRadioButton.isSelected());
				dest = "proform";
			}

		});

		acquittanceRadioButton = new JCheckBox("Стокова Разписка");
		acquittanceRadioButton.setVisible(MainPanel.ACCESS_MENU[ACCESS_ACQUITTANCE]);
		acquittanceRadioButton.setCursor(HAND_CURSOR);
		acquittanceRadioButton.setOpaque(false);
		acquittanceRadioButton.setPreferredSize(new Dimension(200, 50));
		acquittanceRadioButton.setFont(getFONT());
		acquittanceRadioButton.setEnabled(calledFromInvoiceWindow  && payment.equals("В брой"));
		acquittanceRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				invoiceRadioButton.setEnabled(!acquittanceRadioButton.isSelected());
				invoiceRadioButton.setSelected(false);

				/*if(!payment.equals("В брой")) {
					proformRadioButton.setEnabled(!acquittanceRadioButton.isSelected());
				}*/
				if(payment.equals("В брой")) {
					fiskalRadioButton.setSelected(acquittanceRadioButton.isSelected());
				}
				proformRadioButton.setEnabled(!acquittanceRadioButton.isSelected());
				proformRadioButton.setSelected(false);

				saveData.setEnabled(acquittanceRadioButton.isSelected());
				dest = "acquittance";
			}

		});
		fiskalRadioButton = new JCheckBox("Фискален бон");
		fiskalRadioButton.setCursor(HAND_CURSOR);
		fiskalRadioButton.setOpaque(false);
		fiskalRadioButton.setPreferredSize(new Dimension(200, 50));
		fiskalRadioButton.setFont(getFONT());
		fiskalRadioButton.setEnabled(payment.equals("В брой"));
		fiskalRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				invoiceRadioButton.setEnabled(!fiskalRadioButton.isSelected());
				invoiceRadioButton.setSelected(false);

//				    if(!payment.equals("В брой")) { // is not proform window
//						proformRadioButton.setEnabled(!fiskalRadioButton.isSelected());
//					}
				proformRadioButton.setEnabled(!fiskalRadioButton.isSelected());
				proformRadioButton.setSelected(false);
				if(payment.equals("В брой")) {
					acquittanceRadioButton.setEnabled(!fiskalRadioButton.isSelected());
				}
				acquittanceRadioButton.setSelected(false);

				saveData.setEnabled(fiskalRadioButton.isSelected());
				dest = "fiskal";

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
						.getWindowAncestor(SaveInInvoiceDBDialog.this);

				copyOriginDefaultTabelModel();

				WRITE_IN_INVOICE_SUCCESS = false;
				WRITE_IN_PROFORM_SUCCESS = false;
				WRITE_IN_ACQUITTANCE_SUCCESS = false;
				WRITE_IN_FISKAL_SUCCESS = false;

				if((invoiceRadioButton.isSelected() && fiskalRadioButton.isSelected())
						|| invoiceRadioButton.isSelected()) {
					jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));

					GetInvoiceNumberWorker getInvoiceNumber = new GetInvoiceNumberWorker();
					String updatedInvoiceNumber;
					try {
						updatedInvoiceNumber = getInvoiceNumber
								.doInBackground();
						SaveInInvoiceDBWorker saveinInvoice = new SaveInInvoiceDBWorker(
								parentTable,childTable,
								jd, payment, discount, sum, CLIENT, personName,
								date, updatedInvoiceNumber, protokolNumber,
								copyOriginTableModel, invoiceLabel, isVatRegistered);

						try {
							WRITE_IN_INVOICE_SUCCESS = saveinInvoice
									.doInBackground();
							if (WRITE_IN_INVOICE_SUCCESS) {
								DecreaseArtikulQuantityWorker decreaseArtikul =
										new DecreaseArtikulQuantityWorker(
												AVAILABLE_ARTIKULS,
												copyOriginTableModel);
								decreaseArtikul.execute();
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
					if (fiskalRadioButton.isSelected()
							&& WRITE_IN_INVOICE_SUCCESS) {
						CreateBonFPrint fiskal = new CreateBonFPrint();
						ArrayList<String> commandList = fiskal.makeReciept(sum);
						SellWithFiskalBonWorker sellWorker = new SellWithFiskalBonWorker(
								commandList, fiskal, jd);
						sellWorker.execute();
					}
				} else if(proformRadioButton.isSelected()) {

					jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
					GetProformNumberWorker getProformNumber = new GetProformNumberWorker();
					String updateProformNumber = null;
					try {
						updateProformNumber = getProformNumber.doInBackground();
						SaveInProformDBWorker saveinProform = new SaveInProformDBWorker(
								jd, payment, discount, sum, CLIENT, personName,
								date, updateProformNumber, protokolNumber,
								copyOriginTableModel, proformLabel,isVatRegistered);
						try {
							WRITE_IN_PROFORM_SUCCESS = saveinProform
									.doInBackground();
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
				} else if( (acquittanceRadioButton.isSelected() && fiskalRadioButton.isSelected())
						|| acquittanceRadioButton.isSelected()) {
					jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));

					try {
						GetAqcuittanceNumberWorker getAcquittanceNumber = new GetAqcuittanceNumberWorker();
						String updateAcquittanceNumber = getAcquittanceNumber
								.doInBackground();
						SaveInAcquittanceWorker saveInAcquittance = new SaveInAcquittanceWorker(
								copyOriginTableModel, updateAcquittanceNumber,
								MyMath.round(Double.parseDouble(sum), 2),
								// по старо му sum/1.2 without ДДС
								personName, CLIENT, date, acquittanceLabel, jd);
						WRITE_IN_ACQUITTANCE_SUCCESS = saveInAcquittance.doInBackground();
						try {
							// acquittance + fiskal bon
							if (WRITE_IN_ACQUITTANCE_SUCCESS && fiskalRadioButton.isSelected()) {
								// save in reports sells(invoices)
								String fiskalOfficialNumber = MyGetDate
										.generateFiskalBonNumber();
								SaveFiskalBonInInvoiceDBWorker saveFiskalInInvoice =
										new SaveFiskalBonInInvoiceDBWorker(
												parentTable,childTable,
												jd, payment, discount, sum, CLIENT, personName,
												date, fiskalOfficialNumber, protokolNumber,
												copyOriginTableModel, invoiceLabel);
								boolean WRITE_IN_FISKAL_SUCCESS = saveFiskalInInvoice
										.doInBackground();

								if(WRITE_IN_FISKAL_SUCCESS) {
									DecreaseArtikulQuantityWorker decreaseArtikul =
											new DecreaseArtikulQuantityWorker(
													AVAILABLE_ARTIKULS,
													copyOriginTableModel);
									decreaseArtikul.execute();

									CreateBonFPrint fiskal = new CreateBonFPrint();
									ArrayList<String> commandList = fiskal.makeReciept(sum);
									SellWithFiskalBonWorker sellWorker = new SellWithFiskalBonWorker(
											commandList, fiskal, jd);
									sellWorker.execute();
								}
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
				} else if(fiskalRadioButton.isSelected()) {
					jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
					String fiskalOfficialNumber = MyGetDate
							.generateFiskalBonNumber();
					SaveFiskalBonInInvoiceDBWorker saveFiskalInInvoice = new
							SaveFiskalBonInInvoiceDBWorker(
									parentTable,childTable,
							jd, payment, discount, sum, CLIENT, personName,
							date, fiskalOfficialNumber, protokolNumber,
							copyOriginTableModel, invoiceLabel);
					try {
						WRITE_IN_FISKAL_SUCCESS = saveFiskalInInvoice
								.doInBackground();
						if(WRITE_IN_FISKAL_SUCCESS && fiskalRadioButton.isSelected()) {
							DecreaseArtikulQuantityWorker decreaseArtikul =
									new DecreaseArtikulQuantityWorker(
											AVAILABLE_ARTIKULS,
											copyOriginTableModel);
							decreaseArtikul.execute();

							CreateBonFPrint fiskal = new CreateBonFPrint();
							ArrayList<String> commandList = fiskal
									.makeReciept(sum);
							SellWithFiskalBonWorker sellWorker = new SellWithFiskalBonWorker(
									commandList, fiskal, jd);
							sellWorker.execute();
						}
					} catch (Exception ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
						DBException.DBExceptions(
								"Error during saving invoice !", ex);
					}
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
