package office;

import Document.TextFieldLimit;
import NewClient.NewClient;
import office.Renderers.ExtinguisherRenderer;
import office.Renderers.MyComboRenderer;
import office.Renderers.MyTableRenderer;
import office.ServiceModels.*;
import office.ServiceOrderWorkers.PrintPDFBarcodesWorker;
import office.ServiceOrderWorkers.PrintSerialBarcodesWorker;
import office.ServiceOrderWorkers.PrinterServiceOrderWorker;
import office.ServiceOrderWorkers.SaveinServiceOrderDBWorker;
import db.GetFromScanner;
import db.SerialNumber.SerialTable;
import db.ServiceOrder.ServiceNumber;
import generators.BarcodGenerator;
import generators.GenerateSO;
import mydate.MyGetDate;
import run.JDialoger;
import run.JustFrame;
import utility.*;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;

public class ServiceOrder extends MainPanel {

	private final JPanel cardPanel; // store new and old extinguisher panel

	// extinguisher
	private final static String newExt = "Въведен пожарогасител";

	private final static String oldExt = "Завеждане на пожарогасител";

	private final JTextField readBarcod;

	private final JTextField serialNumber;

	private final ClientsListComboBox2 clientCombo;

	private final JComboBox<Object> type;

	private final WheighDustModel dustModel = new WheighDustModel();

	private final WheighWaterModel waterModel = new WheighWaterModel();

	private final WheightWaterFameModel waterFameModel = new WheightWaterFameModel();

	private final WheightCO2Model co2Model = new WheightCO2Model();

	private final JComboBox<String> wheight;

	private EditableField numberExtinguisherField = null;

	private TooltipButton numberExt_Button = null;

	private final Cat2_4Model cat2_4 = new Cat2_4Model();

	private final Cat1_3Model cat1_3 = new Cat1_3Model();

	private final Cat5Model cat5 = new Cat5Model();

	private final JComboBox<String> category;

	private final JComboBox<Object> brand;

	public DefaultTableModel tModel = null;

	public JTable table;

	private static HashSet<String> isBarcodAndSerialEntered = null; // static
																	// because
																	// is for
																	// all
																	// created
																	// object

	public String SERVICE_NUMBER = null;

	private Object[] fromBarcod = null;

	private Object[] fromSerial = null;

	private final SerialTable bt = new SerialTable();

	private BevelLabel clientLabel = null;

	public BevelLabel serviceNumberLabel = null;

	private GenerateSO genSO = null;

	private BarcodGenerator bGen = null;

	private int[] allDigits = null;

	private int[] barcodDigits = null;

	public String CURRENT_CLIENT = "";

	private String serial = null; // curr serial

	private int CURRENT_ROW = -1;

	public ArrayList<String> updateExtinguisher = null;

	public JustFrame newClientFrame = null;

	private HeaderTable headerTable;

	public ServiceOrder(String serviceNumber) {
		// first init

		SERVICE_NUMBER = serviceNumber;

		isBarcodAndSerialEntered = new HashSet<String>();

		genSO = new GenerateSO();

		bGen = new BarcodGenerator();

		ServiceNumber so_Table = new ServiceNumber();

		allDigits = new int[13];

		barcodDigits = new int[2];
		// init service number

		for (int i = 0; i < SERVICE_NUMBER.length(); i++) {
			allDigits[i] = SERVICE_NUMBER.charAt(i) - 48;
		}

		updateExtinguisher = new ArrayList<String>();

		JPanel north = new JPanel(); // GradientPanel
		north.setPreferredSize(new Dimension((int) (this.WIDTH * 1.0) - 20,
				(int) (this.HEIGHT * 0.2)));

		GridLayout gridLayout = new GridLayout(2, 1, 5, -15);

		north.setLayout(gridLayout);

		north.setBorder(BorderFactory.createLineBorder(Color.black));

		JPanel northHelpPanel = new JPanel();
		northHelpPanel.setPreferredSize(new Dimension((int) (north
				.getPreferredSize().getWidth()), (int) (north
				.getPreferredSize().getHeight() * 0.5)));

		northHelpPanel.setOpaque(false);

		cardPanel = new JPanel();
		cardPanel.setPreferredSize(new Dimension((int) (north
				.getPreferredSize().getWidth()), (int) (north
				.getPreferredSize().getHeight() * 0.5)));

		cardPanel.setLayout(new CardLayout());

		cardPanel.setOpaque(false);

		// switch new and old
		JComboBox<Object> switchExtinguisher = new JComboBox<Object>(new Object[]{oldExt,
				newExt});
		switchExtinguisher.setBorder(BorderFactory.createLoweredBevelBorder());
		switchExtinguisher.setEditable(false);

		ExtinguisherRenderer er = new ExtinguisherRenderer();

		er.setPreferredSize(new Dimension((int) (cardPanel.getPreferredSize()
				.getWidth() * 0.25), (int) (cardPanel.getPreferredSize()
				.getHeight() * 0.5)));

		switchExtinguisher.setRenderer(er);

		switchExtinguisher.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
				CardLayout cl = (CardLayout) cardPanel.getLayout();
				cl.show(cardPanel, (String) arg0.getItem());
			}

		});

		JPanel oldExtinguisher = new JPanel();
		oldExtinguisher.setPreferredSize(new Dimension((int) (north
				.getPreferredSize().getWidth()), (int) (north
				.getPreferredSize().getHeight() * 0.5)));
		// oldExtinguisher.setBorder(BorderFactory.createLineBorder(Color.black));
		// oldExtinguisher.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
		oldExtinguisher.setOpaque(false);

		JLabel enteredExtinguisher = new JLabel(" Налични :     ");

		oldExtinguisher.add(enteredExtinguisher);

		readBarcod = new EditableField("баркод", 10);

		readBarcod.setPreferredSize(new Dimension((int) (oldExtinguisher
				.getPreferredSize().getWidth() * 0.2), (int) (oldExtinguisher
				.getPreferredSize().getHeight() * 0.7)));

		readBarcod.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (!isBarcodAndSerialEntered.contains(readBarcod.getText())) {

					SwingWorker<Boolean, Void> sw = new SwingWorker<Boolean, Void>() {

						@SuppressWarnings("finally")
						@Override
						protected Boolean doInBackground() throws Exception {
							// TODO Auto-generated method stub
							try {

								// check if extinguisher exist from Main3

								fromBarcod = GetFromScanner
										.getBarcodFromProtokolTableDB3(readBarcod
												.getText());

							} finally {
								SwingUtilities.invokeLater(new Runnable() {

									@Override
									public void run() {
										// TODO Auto-generated method stub
										if (fromBarcod != null) {
											if (fromBarcod.length > 0) {

												if (tModel.getRowCount() > 0
														&& !CURRENT_CLIENT
																.equals(fromBarcod[0]
																		.toString())) {
													JOptionPane
															.showMessageDialog(
																	null,
																	"Въведен е друг клиент "
																			+ fromBarcod[0]);
													readBarcod.setText("");
													return;
												}

												insertDataFromScanner(fromBarcod);

												updateExtinguisher
														.add(fromBarcod[3]
																.toString());

												// set serial and barcode in
												// hashtable as entered
												isBarcodAndSerialEntered
														.add(fromBarcod[3] // barcod
																.toString());
												isBarcodAndSerialEntered
														.add(fromBarcod[4] // serial
																.toString());
												//
												CURRENT_CLIENT = fromBarcod[0]
														.toString();
												clientLabel
														.setName(CURRENT_CLIENT);

												clientCombo
														.setSelectedItem(CURRENT_CLIENT);
												clientCombo.setEnabled(false);
											} else {
												JOptionPane
														.showMessageDialog(
																null,
																"Не е намерен такъв елемент!");
											}
										}
										readBarcod.setText("");
										// testJTable();
									}

								});


							}
							return true;
						}
					};
					sw.execute();
				} else {
					JOptionPane.showMessageDialog(new JLabel(),
							"Този номер вече е въведен!");
					readBarcod.setText("");


				}
			}
		});

		serialNumber = new EditableField("сериен " + MainPanel.numeroSign, 8);
		serialNumber.setPreferredSize(new Dimension((int) (oldExtinguisher
				.getPreferredSize().getWidth() * 0.2), (int) (oldExtinguisher
				.getPreferredSize().getHeight() * 0.7)));
		serialNumber.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (!isBarcodAndSerialEntered.contains(serialNumber.getText())) {
					SwingWorker<Boolean, Void> sw = new SwingWorker<Boolean, Void>() {

						@SuppressWarnings("finally")
						@Override
						protected Boolean doInBackground() throws Exception {
							// TODO Auto-generated method stub
							try {

								// first get data from db from Main3

								fromSerial = GetFromScanner
										.getSerialFromProtokolTableDB3(serialNumber
												.getText());

							} finally {
								SwingUtilities.invokeLater(new Runnable() {

									@Override
									public void run() {
										// TODO Auto-generated method stub
										if (fromSerial != null) {
											if (fromSerial.length > 0) {

												if (tModel.getRowCount() > 0
														&& !CURRENT_CLIENT
																.equals(fromSerial[0]
																		.toString())) {
													JOptionPane
															.showMessageDialog(
																	null,
																	"Въведен е друг клиент "
																			+ fromSerial[0]);
													serialNumber.setText("");
													return;
												}
												insertDataFromScanner(fromSerial);

												// add serial to update after
												// save in db
												updateExtinguisher
														.add(fromSerial[3]
																.toString());
												// set serial and barcode in
												// hashtable as entered
												isBarcodAndSerialEntered
														.add(fromSerial[3] // barcod
																.toString());
												isBarcodAndSerialEntered
														.add(fromSerial[4] // serial
																.toString());
												//
												CURRENT_CLIENT = fromSerial[0]
														.toString();
												clientLabel
														.setName(CURRENT_CLIENT);

												// testJTable();

												clientCombo
														.setSelectedItem(CURRENT_CLIENT);
												clientCombo.setEnabled(false);
											} else {
												JOptionPane
														.showMessageDialog(
																null,
																"Не е намерен такъв елемент!");
											}
											serialNumber.setText("");
										}
									}

								});

							}
							return true;
						}
					};
					sw.execute();
				} else {
					JOptionPane.showMessageDialog(new JLabel(),
							"Този номер вече е въведен!");
					serialNumber.setText("");
				}
			}
		});

		// serialNumber.setPreferredSize(new Dimension(20, 50));

		// order buttons
		TooltipButton printerButton = new TooltipButton();
		printerButton.setPreferredSize(new Dimension((int) (northHelpPanel
				.getPreferredSize().getWidth() * 0.045), (int) (northHelpPanel
				.getPreferredSize().getHeight() * 0.75)));

		printerButton.setAutoSizedIcon(printerButton, new LoadIcon().setIcons(printerImage));
		// printerButton.setIcon(new LoadIcon().setIcons(printerImage));
		// printerButton.setPreferredSize(new Dimension(55, 55));
		printerButton
				.setToolTipText(getHTML_Text("ПРИНТИРАЙ СЕРВИЗНА ПОРЪЧКА"));
		printerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (table.getRowCount() > 0) {
					JDialog jd = ((JDialog) (SwingUtilities
							.getWindowAncestor(ServiceOrder.this)));
					jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));

					PrinterServiceOrderWorker pw = new PrinterServiceOrderWorker(
							SERVICE_NUMBER, CURRENT_CLIENT, jd, getSOMap());
					pw.execute();

					readBarcod.requestFocus();
				} else {
					JOptionPane.showMessageDialog(null, "Няма въведени данни!");
				}

			}

		});

		TooltipButton barcodButton = new TooltipButton();

		// barcodButton.setIcon(new LoadIcon().setIcons(barcodeImage));

		barcodButton.setToolTipText(getHTML_Text("ГЕНЕРИРАЙ БАРКОД"));
		barcodButton.setPreferredSize(new Dimension((int) (northHelpPanel
				.getPreferredSize().getWidth() * 0.045), (int) (northHelpPanel
				.getPreferredSize().getHeight() * 0.75)));
		;
		barcodButton.setAutoSizedIcon(barcodButton, new LoadIcon().setIcons(barcodeImage));
		// barcodButton.setPreferredSize(new Dimension(55, 55));

		barcodButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (tModel.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "Няма въведени данни!");
					return;
				}
				if (CURRENT_ROW == -1) {
					JOptionPane.showMessageDialog(null,
							"Не е избран пожарогасител");
					return;
				}
				for (int column = 3; column <= 5; column++) {
					if (table.getValueAt(CURRENT_ROW, column).equals("")) {
						JOptionPane.showMessageDialog(null,
								"Има невъведени данни !");
						return;
					}
				}
				// check if data is entered
				if (table.getSelectedRow() > -1) {

					String number = table.getValueAt(table.getSelectedRow(), 2)
							.toString();

					StringBuilder sb = new StringBuilder();
					for(int i = 0;i < number.length()-1;i++) {
						sb.append(number.charAt(i));
					}
					PrintSerialBarcodesWorker printSerialBarcodesWorker =
							new PrintSerialBarcodesWorker(sb.toString(), CURRENT_CLIENT);
					printSerialBarcodesWorker.execute();

//			tozi kod preskacha edin etiket		PrintPDFBarcodesWorker printBarcode = new PrintPDFBarcodesWorker(
//							number, CURRENT_CLIENT);
//					printBarcode.execute();

				} else {
					JOptionPane.showMessageDialog(null,
							"Не е избран пожарогасител");
				}

				// set cursor in read barcod text field !!!!
				readBarcod.requestFocus();

			}

		});

		TooltipButton dbButton = new TooltipButton();

		// dbButton.setIcon(new LoadIcon().setIcons(dbImage));
		// dbButton.setPreferredSize(new Dimension(55, 55));

		dbButton.setEnabled(true);

		dbButton.setToolTipText(getHTML_Text("ЗАПИШИ В БАЗА ДАННИ"));
		dbButton.setPreferredSize(new Dimension((int) (northHelpPanel
				.getPreferredSize().getWidth() * 0.045), (int) (northHelpPanel
				.getPreferredSize().getHeight() * 0.75)));

		dbButton.setAutoSizedIcon(dbButton, new LoadIcon().setIcons(dbImage));
		dbButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (table.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "Няма въведени данни!");
					return;
				}
				if (!tableChecker()) {
					JOptionPane
							.showMessageDialog(null, "Има незаписани данни!");
					return;
				}
				int yes_no = JOptionPane.showOptionDialog(null,
						"Желаете ли да съхраните въведените данни?", "",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, new String[] {
								"Да", "Не" }, // this is the array
						"default");
				if (yes_no == 0) {
					JDialog jd = ((JDialog) (SwingUtilities
							.getWindowAncestor(ServiceOrder.this)));

					jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));

					// SaveinServiceOrderDBWorker sw2 = new
					// SaveinServiceOrderDBWorker(
					// jd, SERVICE_NUMBER, CURRENT_CLIENT);
					SaveinServiceOrderDBWorker sw2 = new SaveinServiceOrderDBWorker(
							jd, ServiceOrder.this);
					try {

						SERVICE_NUMBER = sw2.doInBackground();
						// digits = genSO.stringToArray(SERVICE_NUMBER); //
						// update
						// digits
						barcodDigits = new int[2];
						for (int i = 0; i < SERVICE_NUMBER.length(); i++) {
							allDigits[i] = SERVICE_NUMBER.charAt(i) - 48;
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					readBarcod.requestFocus();
				}
			}
		});

		TooltipButton eraserButton = new TooltipButton();

		// eraserButton.setIcon(new LoadIcon().setIcons(eraserImage));
		// eraserButton.setPreferredSize(new Dimension(55, 55));

		eraserButton.setToolTipText(getHTML_Text("ИЗТРИЙ ДАННИТЕ"));
		eraserButton.setPreferredSize(new Dimension((int) (northHelpPanel
				.getPreferredSize().getWidth() * 0.045), (int) (northHelpPanel
				.getPreferredSize().getHeight() * 0.75)));

		eraserButton.setAutoSizedIcon(eraserButton, new LoadIcon().setIcons(eraserImage));
		eraserButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (tModel.getRowCount() == 0)
					return;

				if (CURRENT_ROW == -1) {
					JOptionPane.showMessageDialog(null,
							"Не е избран пожарогасител");
					return;
				}
				int yes_no = JOptionPane.showOptionDialog(null,
						"Наистина ли искате да изтриете данните ?", "",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, new String[] {
								"Да", "Не" }, // this is the array
						"default");
				if (yes_no != 0)
					return;

				tModel.removeRow(CURRENT_ROW);
				CURRENT_ROW = -1;

				readBarcod.requestFocus();
				// this logic clear all table contents
				/*
				 * tModel.setRowCount(0); barcodDigits = new int[2]; for (int i
				 * = 0; i < SERVICE_NUMBER.length(); i++) { allDigits[i] =
				 * (int)(SERVICE_NUMBER.charAt(i) - 48); }
				 */
			}
		});

		TooltipButton clientButton = new TooltipButton();

		// clientButton.setIcon(new LoadIcon().setIcons(clientsImage));

		clientButton.setToolTipText(getHTML_Text("ВЪВЕДИ НОВ КЛИЕНТ"));
		clientButton.setPreferredSize(new Dimension((int) (northHelpPanel
				.getPreferredSize().getWidth() * 0.045), (int) (northHelpPanel
				.getPreferredSize().getHeight() * 0.75)));

		clientButton.setAutoSizedIcon(clientButton, new LoadIcon().setIcons(clientsImage));
		// clientButton.setPreferredSize(new Dimension(55, 55));

		clientButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				NewClient nc = new NewClient();
				JDialoger jDialog = new JDialoger();
				jDialog.setContentPane(nc);
				jDialog.setResizable(false);
				jDialog.setTitle("Въвеждане на нови клиенти");
				jDialog.Show();
			}

		});

		// begin make new extinguisher components

		JPanel newExtinguisher = new JPanel();
		newExtinguisher.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		newExtinguisher.setPreferredSize(new Dimension((int) (north
				.getPreferredSize().getWidth()), (int) (north
				.getPreferredSize().getHeight() * 0.5)));
		// newExtinguisher.setBorder(BorderFactory.createLineBorder(Color.black));
		newExtinguisher.setOpaque(false);

		JPanel clientPanel = new JPanel();

		clientPanel.setLayout(new GridLayout(2, 1));

		clientPanel.setOpaque(false);
		clientPanel.setPreferredSize(new Dimension((int) (newExtinguisher
				.getPreferredSize().getWidth() * 0.2), (int) (newExtinguisher
				.getPreferredSize().getHeight() * 0.9)));

		clientPanel.add(new JLabel("Клиент "));

		clientCombo = new ClientsListComboBox2();
		clientCombo.setBorder(BorderFactory.createLoweredBevelBorder());
		clientCombo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
				numberExt_Button.setEnabled(arg0.getItem().toString().length() > 0);
			}

		});

		clientPanel.add(clientCombo);

		/*
		 * clientCombo.setPreferredSize(new Dimension(
		 * (int)(clientPanel.getPreferredSize().getWidth()),
		 * (int)(clientPanel.getPreferredSize().getHeight() * 0.7)));
		 */

		JPanel typePanel = new JPanel();
		typePanel.setPreferredSize(new Dimension((int) (newExtinguisher
				.getPreferredSize().getWidth() * 0.1), (int) (newExtinguisher
				.getPreferredSize().getHeight() * 0.9)));
		typePanel.setLayout(new GridLayout(2, 1));

		typePanel.setOpaque(false);

		// TextReader.getData("Local/type.txt");
		Object[] typesFire = {"Вид", type_Prah_BC, type_Prah_ABC,
				type_Water, type_Water_Fame, type_CO2, type_classF, type_classD};
		type = new JComboBox<Object>(typesFire);
		/*
		 * type.setPreferredSize(new Dimension(
		 * (int)(typePanel.getPreferredSize().getWidth()),
		 * (int)(typePanel.getPreferredSize().getHeight() * 0.9)));
		 */

		type.setBorder(BorderFactory.createLoweredBevelBorder());
		type.setEditable(true);

		type.setRenderer(new MyComboRenderer());

		type.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (type.getSelectedItem().equals("Вид")) {
					return;
				}

				wheight.setSelectedIndex(0);
			}

		});

		typePanel.add(new JLabel("Вид"));

		typePanel.add(type);

		wheight = new JComboBox<String>(new String[] { "Вместимост" });

		wheight.setBorder(BorderFactory.createLoweredBevelBorder());
		wheight.addPopupMenuListener(new PopupMenuListener() {

			@Override
			public void popupMenuCanceled(PopupMenuEvent arg0) {
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
			}

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
				// TODO Auto-generated method stub

				Object item = type.getSelectedItem();// table.getValueAt(CURRENT_ROW,
														// 0);
				if (item != null) {
					if (item.equals(type_Prah_BC) || item.equals(type_Prah_ABC)) {
						wheight.setModel(dustModel);
					} else if (item.equals(type_Water)) {
						wheight.setModel(waterModel);
					} else if (item.equals(type_Water_Fame)) {
						wheight.setModel(waterFameModel);
					} else if (item.equals(type_CO2)) {
						wheight.setModel(co2Model);
					}
				}
			}
		});

		wheight.setEditable(false);

		wheight.setRenderer(new MyComboRenderer());

		JPanel wheightPanel = new JPanel();
		wheightPanel.setPreferredSize(new Dimension((int) (newExtinguisher
				.getPreferredSize().getWidth() * 0.1), (int) (newExtinguisher
				.getPreferredSize().getHeight() * 0.9)));

		wheightPanel.setLayout(new GridLayout(2, 1));

		wheightPanel.setOpaque(false);

		wheightPanel.add(new JLabel("Вместимост"));

		wheightPanel.add(wheight);

		JPanel numberPanel = new JPanel();
		numberPanel.setPreferredSize(new Dimension((int) (newExtinguisher
				.getPreferredSize().getWidth() * 0.15), (int) (newExtinguisher
				.getPreferredSize().getHeight() * 0.9)));

		numberPanel.setLayout(new GridBagLayout());// (new GridLayout(2,3,1,0));
		numberPanel.setOpaque(false);

		numberExtinguisherField = new EditableField("", 4);
		numberExtinguisherField.setPreferredSize(new Dimension(
				(int) (numberPanel.getPreferredSize().getWidth() * 0.3),
				(int) (numberPanel.getPreferredSize().getHeight() * 0.25)));

		numberExtinguisherField.setForeground(Color.red);
		numberExtinguisherField.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD,
				getFontSize() + 2));
		numberExtinguisherField.setDocument(new TextFieldLimit(2));

		// DO NOT DELETE THIS CODE !!!!
		numberExtinguisherField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent ke) {

				if (ke.getKeyChar() == KeyEvent.VK_ENTER) {

					insertExtinguishers();
				}
			}
		});

		numberExt_Button = new TooltipButton();
		numberExt_Button.setPreferredSize(new Dimension((int) (numberPanel
				.getPreferredSize().getWidth() * 0.3), // -> how width to be
														// image in the button
														// area
				(int) (numberPanel.getPreferredSize().getHeight() * 0.3)));
		numberExt_Button.setAutoSizedIcon(numberExt_Button,
				new LoadIcon().setIcons(enterNumberImage));
		// numberExt_Button.setIcon(new LoadIcon().setIcons(enterNumberImage));

		numberExt_Button
				.setToolTipText(getHTML_Text("ВЪВЕДИ БРОЙ ПОЖАРОГАСИТЕЛИ"));

		numberExt_Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				insertExtinguishers();
				// tModel.addRow(new Object[]{"","","","","","","",""});
			}

		});

		// set layout of number panel components
		GridBagConstraints[] gc = new GridBagConstraints[4];
		int[] x = { 0, 1, 0, 1 };
		int[] y = { 0, 0, 1, 1 };
		for (int i = 0; i < x.length; i++) {
			gc[i] = new GridBagConstraints();
			gc[i].gridx = x[i];
			gc[i].gridy = y[i];
			gc[i].ipady = 5;

		}
		gc[2].fill = 1;
		gc[3].fill = 2;
		numberPanel.add(new JLabel("Брой "), gc[0]);

		numberPanel.add(new JLabel("пожарогасители"), gc[1]);

		numberPanel.add(numberExtinguisherField, gc[2]);

		numberPanel.add(numberExt_Button, gc[3]);

		JPanel writerPanel = new JPanel();
		writerPanel.setPreferredSize(new Dimension((int) (newExtinguisher
				.getPreferredSize().getWidth() * 0.125), (int) (newExtinguisher
				.getPreferredSize().getHeight() * 0.9)));
		writerPanel.setLayout(new GridLayout(2, 1, 0, 2));

		writerPanel.setOpaque(false);

		TooltipButton serialNumber_Button = new TooltipButton();
		serialNumber_Button.setText("Въведи номер");
		serialNumber_Button
				.setToolTipText(getHTML_Text("ГЕНЕРИРАЙ МОНТАЖЕН НОМЕР"));

		serialNumber_Button.setOpaque(false);

		serialNumber_Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (tModel.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "Няма въведени данни!");
					return;
				}
				if (CURRENT_ROW == -1 || CURRENT_ROW >= table.getRowCount()) {
					return;
				}
				if (!table.getValueAt(CURRENT_ROW, 3).equals("")) { // serial
					JOptionPane
							.showMessageDialog(null, "Вече е въведен номер!");
					return;
				}
				SwingWorker<Boolean, Void> sw = new SwingWorker<Boolean, Void>() {

					@SuppressWarnings("finally")
					@Override
					protected Boolean doInBackground() throws Exception {
						// TODO Auto-generated method stub

						final JDialog jd = ((JDialog) (SwingUtilities
								.getWindowAncestor(ServiceOrder.this)));
						jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));

						try {

							serial = bt.updateSerial(); // get and update serial
							// from db
						} finally {

							SwingUtilities.invokeLater(new Runnable() {
								@Override
								public void run() {
									jd.setCursor(new Cursor(
											Cursor.DEFAULT_CURSOR));
									if (!serial.equals(null)) {
										tModel.setValueAt(serial, CURRENT_ROW,
												3);
									}

								}

							});
						}
						return true;
					}
				};

				sw.execute();

			}

		});

		writerPanel.add(new JLabel("Монтажен номер"));

		writerPanel.add(serialNumber_Button);

		category = new JComboBox<String>(new String[] { "Категория" }); // (categoryFire);*/

		category.setBorder(BorderFactory.createLoweredBevelBorder());
		category.setEditable(false);

		category.setRenderer(new MyComboRenderer());

		category.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (CURRENT_ROW < table.getRowCount()) {

					if (category.getSelectedItem().equals("Категория")) {
						return;
					}
					tModel.setValueAt(category.getSelectedItem(), CURRENT_ROW,
							4);

				}

			}
		});

		category.addPopupMenuListener(new PopupMenuListener() {

			@Override
			public void popupMenuCanceled(PopupMenuEvent arg0) {
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
			}

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
				// TODO Auto-generated method stub
				if (table.getRowCount() > 0
						&& CURRENT_ROW < table.getRowCount()) {
					Object item = table.getValueAt(CURRENT_ROW, 0);
					if (item.equals(type_Prah_BC) || item.equals(type_Prah_ABC)) {
						category.setModel(cat2_4);
					} else if (item.equals(type_Water)
							|| item.equals(type_Water_Fame)) {
						category.setModel(cat1_3);
					} else if (item.equals(type_CO2)) {
						category.setModel(cat5);
					}

				}
			}

		});
		JPanel categoryPanel = new JPanel();
		categoryPanel.setPreferredSize(new Dimension((int) (newExtinguisher
				.getPreferredSize().getWidth() * 0.125), (int) (newExtinguisher
				.getPreferredSize().getHeight() * 0.9)));
		categoryPanel.setOpaque(false);

		categoryPanel.setLayout(new GridLayout(2, 1));

		JLabel categoryLabel = new JLabel("Категория");

		categoryPanel.add(categoryLabel);

		categoryPanel.add(category);

		brand = new BrandListComboBox();
		brand.setBorder(BorderFactory.createLoweredBevelBorder());
		brand.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (table.getRowCount() > 0
						&& CURRENT_ROW < table.getRowCount()) {

					if (brand == null && brand.getSelectedItem().equals("")) {
						return;
					}

					if (brand.getSelectedIndex() > 0) {
						tModel.setValueAt(brand.getSelectedItem(), CURRENT_ROW,
								5);
					} else {
						tModel.setValueAt("", CURRENT_ROW, 5);
					}

				}

			}

		});

		JLabel brandLabel = new JLabel("Марка");

		brand.setEditable(true);

		JPanel brandPanel = new JPanel();
		brandPanel.setPreferredSize(new Dimension((int) (newExtinguisher
				.getPreferredSize().getWidth() * 0.125), (int) (newExtinguisher
				.getPreferredSize().getHeight() * 0.9)));
		brandPanel.setLayout(new GridLayout(2, 1));

		brandPanel.setOpaque(false);

		brandPanel.add(brandLabel);

		brandPanel.add(brand);

		// make center with table
		JPanel center = new JPanel();

		center.setBorder(BorderFactory.createLineBorder(Color.black));

		tModel = new DefaultTableModel(new String[] { "Вид", "Вместимост",
				"Баркод", "Монтажен номер", "Категория", "Марка", "ТО", "П",
				"ХИ", "Обработен", "Номер на Сервизна Поръчка", "Продавач",
				"Дата", "Допълнителни данни" }, 0) {// , "Допълнителни данни"
			/*
			 * new DefaultTableModel(new String[] { "Вид", "Вместимост",
			 * "Баркод", "Монтажен номер", "Категория", "Марка", "ТО", "П",
			 * "ХИ", "Обработен", "Презаписан", "Номер на Сервизна Поръчка",
			 * "Продавач", "Дата"
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int col) {
				return col == 3 || col == 13;
			}
		};
		tModel.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				// TODO Auto-generated method stub
				if (tModel.getRowCount() == 0)
					cleaner();
			}

		});
		table = new JTable(tModel);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				CURRENT_ROW = table.getSelectedRow();
			}
		});

		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		table.setRowHeight(MainPanel.getFontSize() + 15);
		table.setDefaultRenderer(Object.class, new MyTableRenderer(table));

		JScrollPane scroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension((int) (this.WIDTH * 1.0) - 20,
				(int) (this.HEIGHT * 0.6)));

		// headerTable = new HeaderTable(table);
		// scroll.setRowHeaderView(headerTable.getRowsColumnTable());

		JPanel south = new JPanel(); // / GradientPanel
		south.setPreferredSize(new Dimension((int) (this.WIDTH * 1.0) - 20,
				(int) (this.HEIGHT * 0.09)));
		south.setBorder(BorderFactory.createLineBorder(Color.black));
		south.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		float labelHeight = (int) (south.getPreferredSize().getHeight() * 0.7);
		BevelLabel sallerLabel = new BevelLabel(labelHeight);
		/*
		 * sallerLabel.setPreferredSize(new Dimension(
		 * (int)(south.getPreferredSize().getWidth() * 0.25),
		 * (int)(south.getPreferredSize().getHeight() * 0.7)));
		 */
		sallerLabel.setTitle(Enums.Оператор.name() + ": ");//("  Продавач: ");
		sallerLabel.setName(personName);

		clientLabel = new BevelLabel(labelHeight);
		/*
		 * clientLabel.setPreferredSize(new Dimension(
		 * (int)(south.getPreferredSize().getWidth() * 0.3),
		 * (int)(south.getPreferredSize().getHeight() * 0.7)));
		 */
		clientLabel.setTitle(" Клиент  : ");
		clientLabel.setName("");

		BevelLabel dateLabel = new BevelLabel(labelHeight);
		/*
		 * dateLabel.setPreferredSize(new Dimension(
		 * (int)(south.getPreferredSize().getWidth() * 0.15),
		 * (int)(south.getPreferredSize().getHeight() * 0.7)));
		 */
		dateLabel.setTitle("  Дата : ");
		dateLabel.setName(MyGetDate.getReversedSystemDate());

		serviceNumberLabel = new BevelLabel(labelHeight);
		/*
		 * serviceNumberLabel.setPreferredSize(new Dimension(
		 * (int)(south.getPreferredSize().getWidth() * 0.25),
		 * (int)(south.getPreferredSize().getHeight() * 0.7)));
		 */
		serviceNumberLabel.setTitle(" Серв. поръчка \u2116 ");
		serviceNumberLabel.setName(serviceNumber);

		oldExtinguisher.add(readBarcod);
		oldExtinguisher.add(serialNumber);

		newExtinguisher.add(clientPanel);
		newExtinguisher.add(typePanel);
		newExtinguisher.add(wheightPanel);
		newExtinguisher.add(numberPanel);
		newExtinguisher.add(writerPanel);
		newExtinguisher.add(categoryPanel);
		newExtinguisher.add(brandPanel);

		cardPanel.add(oldExtinguisher, oldExt);
		cardPanel.add(newExtinguisher, newExt);

		northHelpPanel.add(switchExtinguisher);
		northHelpPanel.add(barcodButton);
		northHelpPanel.add(printerButton);
		northHelpPanel.add(dbButton);
		northHelpPanel.add(eraserButton);
		northHelpPanel.add(clientButton);

		north.add(northHelpPanel); // northHelpPanel
		north.add(cardPanel);

		center.setLayout(new GridLayout(1, 1));
		center.add(scroll);

		south.add(sallerLabel);
		south.add(clientLabel);
		south.add(dateLabel);
		south.add(serviceNumberLabel);

		JPanel block = new JPanel();
		block.setLayout(new BorderLayout());
		block.add(north, BorderLayout.NORTH);
		block.add(center, BorderLayout.CENTER);
		block.add(south, BorderLayout.SOUTH);

		this.add(block);

		this.setPreferredSize(new Dimension(this.WIDTH - 20, this.HEIGHT - 80));
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	// make map to create pdf service order
	private TreeMap<Object, Integer> getSOMap() {
		TreeMap<Object, Integer> soMap = new TreeMap<Object, Integer>();
		for (int row = 0; row < table.getRowCount(); row++) {
			String key = table.getValueAt(row, 0) + " "
					+ table.getValueAt(row, 1);
			Integer value = soMap.get(key);
			if (value == null) {
				value = 0;
			}
			soMap.put(key, value + 1);
		}

		return soMap;
	}

	private void insertDataFromNewExtinguishers(int rows) {

		for (int i = rows - 1; i >= 0; i--) {
			if (barcodDigits != null) {
				addExtinguisherFromNewExtinuishers(0);
			} else {
				JOptionPane.showMessageDialog(null,
						"Не могат да бъдат въведени повече елементи!");
				break;
			}
		}

		// testJTable();
	}

	private void insertDataFromScanner(Object[] object) {

		/*
		 * if(rows + tModel.getRowCount() > 99) {
		 * JOptionPane.showMessageDialog(null,"Надхвърлен лимит на елементи");
		 * return; }
		 */
		// object[0] -> is client

		// filter data (remove unneeded text)
		String type = object[1].toString();
		if (type.toUpperCase().contains("ABC")
				|| type.toUpperCase().contains("АВС")) {
			type = type_Prah_ABC;
		} else if (type.toUpperCase().contains("BC")
				|| type.toUpperCase().contains("ВС")) {
			type = type_Prah_BC;
		} else if (type.toLowerCase().contains("воден")) {
			type = type_Water;
		} else if (type.toLowerCase().contains("водопенен")) {
			type = type_Water_Fame;
		} else if (type.toUpperCase().contains("CO2")
				|| type.toUpperCase().contains("СО2")) {
			type = type_CO2;
		}

		// restore brand property (remove content after comma )
		String brand = object[6].toString();
		int index = brand.indexOf(", носим");
		int index2 = brand.indexOf(", возим");
		if(index != -1) {
			brand = brand.substring(0,index-1);
		}
		if(index2 != -1) {
			brand = brand.substring(0,index2-1);
		}
		Object[] obj = new Object[] {
				type, // type
				object[2], // wheight
				object[3], // barcod
				object[4], // serial
				object[5], // category
				brand,// brand
				object[7], // TO
				object[8], // P
				object[9], // HI
				"не", // done
				SERVICE_NUMBER, personName, MyGetDate.getReversedSystemDate(),
				object[10] != null ? object[10] : "" };
		// add barcode
		// презаписва баркодовете
		addOneBarcodFromScanner(0, obj);
	}

	private void addOneBarcodFromScanner(int row, Object[] obj) {
		barcodDigits = bGen.next_barcod(barcodDigits);
		if (barcodDigits == null) {
			return;
		}
		System.arraycopy(barcodDigits, 0, allDigits, 10, 2);

		tModel.insertRow(0, obj);
		allDigits[12] = bGen.getCheckSum13(allDigits);
		tModel.setValueAt(genSO.digitsToString(allDigits), row, 2);

	}

	void insertExtinguishers() {
		if (clientCombo.getSelectedItem() == null
				|| clientCombo.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Не е избран клиент!");
			return;
		}

		if (type.getSelectedItem().equals("Вид")) {
			JOptionPane.showMessageDialog(null,
					"Не е избран вид пожарогасител!");
			return;
		}

		if (wheight.getSelectedItem().equals("Вместимост")) {
			JOptionPane.showMessageDialog(null, "Не е избрана вместимост!");
			return;
		}

		// check if client are same
		if (tModel.getRowCount() > 0
				&& !CURRENT_CLIENT.equals(clientCombo.getSelectedItem()
						.toString())) {
			JOptionPane.showMessageDialog(null, "Избран е друг клиент "
					+ clientCombo.getSelectedItem() + "!");
			clientCombo.setSelectedItem(CURRENT_CLIENT);
			return;
		}

		try {
			int rows = Integer.parseInt(numberExtinguisherField.getText());

			insertDataFromNewExtinguishers(rows); // add barcodes to every
			// extinguisher

			CURRENT_CLIENT = clientCombo.getSelectedItem().toString();
			clientLabel.setText(" Клиент  : " + CURRENT_CLIENT);
			// clientLabel.setPreferredSize(new Dimension(fm
			// .stringWidth(clientLabel.getText()) + 10, 50));
			reset(); // reset
			clientCombo.setEnabled(false);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Невалиден вход !");
		}
	}

	private void addExtinguisherFromNewExtinuishers(int index) {

		barcodDigits = bGen.next_barcod(barcodDigits);
		if (barcodDigits == null) {
			return;
		}
		System.arraycopy(barcodDigits, 0, allDigits, 10, 2);

		tModel.insertRow(0, new Object[] { "", "", "", "", "", "", "", "", "",
				"", "", "", "", "" });
		allDigits[12] = bGen.getCheckSum13(allDigits);
		tModel.setValueAt(genSO.digitsToString(allDigits), index, 2);
		int i = 0;
		tModel.setValueAt(type.getSelectedItem().toString(), i, 0); // type
		tModel.setValueAt(wheight.getSelectedItem().toString(), i, 1); // wheight
		tModel.setValueAt("не", i, 6); // T_O
		tModel.setValueAt("не", i, 7); // P
		tModel.setValueAt("не", i, 8); // HI
		tModel.setValueAt("не", i, 9); // done
		tModel.setValueAt(SERVICE_NUMBER, i, 10);
		tModel.setValueAt(personName, i, 11);
		tModel.setValueAt(MyGetDate.getReversedSystemDate(), i, 12);

		// setColumnsNums();

	}

	// check if all data is filled in jtable
	public boolean tableChecker() {
		for (int row = 0; row < table.getRowCount(); row++) {
			for (int column = 0; column < table.getColumnCount() - 1; column++) {
				if (table.getValueAt(row, column).toString().isEmpty()) {
					return false;
				}
			}
		}
		return true;
	}

	// reset previous init content in jcomponents
	public void cleaner() {

		// init hashset again ?
		isBarcodAndSerialEntered.clear();

		CURRENT_ROW = 0;

		clientLabel.setText(" Клиент  : ");
		clientCombo.setEnabled(true);
		clientCombo.setSelectedItem("");
		type.setSelectedIndex(0);
		wheight.setSelectedIndex(0);
		numberExtinguisherField.setText("");
		category.setSelectedIndex(0);
		brand.setSelectedItem("");

		updateExtinguisher.clear(); // clear list for brack old barcodes
	}

	// reset type and wheight to previous init content
	private void reset() {
		type.setSelectedIndex(0);
		wheight.setSelectedIndex(0);
		numberExtinguisherField.setText("");
	}

}
