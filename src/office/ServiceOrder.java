package office;

import db.Protokol.ProtokolTable;
import document.TextFieldLimit;
import exceptions.ErrorDialog;
import clients.NewClient;
import db.Client.ClientTable;
import generators.NumGenerator;
import http.RequestCallback2;
import http.client.GetClientService;
import http.service_order.ServiceOrderService;
import models.Firm;
import models.ProtokolModels;
import models.ServiceOrderBodyList;
import models.ServiceOrderModel;
import office.renderers.ExtinguisherRenderer;
import office.renderers.MyComboRenderer;
import office.renderers.MyTableRenderer;
import office.models.*;
import office.workers.PrintSerialBarcodesWorker;
import office.workers.PrinterServiceOrderWorker;
import office.workers.SaveinServiceOrderDBWorker;
import db.GetFromScanner;
import db.SerialNumber.SerialTable;
import generators.BarcodGenerator;
import generators.GenerateSO;
import mydate.MyGetDate;
import run.JDialoger;
import utils.*;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.TableModelEvent;
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

	public MyTable table;

	private static HashSet<String> isBarcodAndSerialEntered = null; // static
	// because
	// is for
	// all
	// created
	// object

	public String SERVICE_NUMBER = null;

	private final SerialTable bt = new SerialTable();

	private BevelLabel clientLabel = null;

	public BevelLabel serviceNumberLabel;

	private final GenerateSO genSO;

	private final BarcodGenerator bGen;

	private final int[] allDigits;

	private int[] barcodDigits;

	public String CURRENT_CLIENT = "";

	private String serial = null; // curr serial

	private int CURRENT_ROW = -1;

	public ArrayList<String> updateExtinguisher;


	public ServiceOrder(String serviceNumber) {
		// first init
		PrintSerialBarcodesWorker.clearEnteredNumbers();

		SERVICE_NUMBER = serviceNumber;

		isBarcodAndSerialEntered = new HashSet<String>();

		genSO = new GenerateSO();

		bGen = new BarcodGenerator();

		//ServiceNumber so_Table = new ServiceNumber();

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

				if (!isBarcodAndSerialEntered.contains(readBarcod.getText().trim())) {


					ServiceOrderService service = new ServiceOrderService();
					service.getProtokolInfoByBarcode(
							readBarcod.getText().trim(),
							null,new RequestCallback2() {
						@Override
						public <T> void callback(T t) {
							ProtokolModels protokolModel = (ProtokolModels) t;

								if (protokolModel != null) {

									if (tModel.getRowCount() > 0
											&& !CURRENT_CLIENT
											.equals(protokolModel.getClient())) {
										ErrorDialog.showErrorMessage(
												"Въведен е друг клиент "
														+ protokolModel.getClient());
										readBarcod.setText("");
										return;
									}

									GetClientService service1 = new GetClientService();
									service1.getFirm(protokolModel.getClient(), new RequestCallback2() {
										@Override
										public <T> void callback(T t) {
											Firm firm = (Firm)t;
											if(firm == null) {
											ErrorDialog.showErrorMessage(
												String.format("Не е намерен такъв клиент %s",
														protokolModel.getClient()));
										// най-вероятно клиента е преименуван
											}
										}
									});


									String weight = protokolModel.getWheight();

									if(!isWeightValid(weight)) {
										ErrorDialog.showErrorMessage(String.format("Грешен формат на количество %s",
												weight));
										return;
									}


									insertDataFromScanner(protokolModel);

									updateExtinguisher
											.add(protokolModel.getBarcod());

									// set serial and barcode in
									// hashtable as entered
									isBarcodAndSerialEntered
											.add(protokolModel.getBarcod());
									isBarcodAndSerialEntered
											.add(protokolModel.getSerial());
									//
									CURRENT_CLIENT = protokolModel.getClient();
									clientLabel.setName(CURRENT_CLIENT);

									clientCombo.setSelectedItem(CURRENT_CLIENT);
									clientCombo.setEnabled(false);
								} else {
									JOptionPane
											.showMessageDialog(
													null,
													"Не е намерен такъв елемент!");
								}

							readBarcod.setText("");
						}
					});

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

				if (!isBarcodAndSerialEntered.contains(serialNumber.getText().trim())) {


					ServiceOrderService service = new ServiceOrderService();
					service.getProtokolInfoByBarcode(
							null,
							serialNumber.getText(),new RequestCallback2() {
						@Override
						public <T> void callback(T t) {
							ProtokolModels protokolModel = (ProtokolModels) t;

							if (protokolModel != null) {

								if (tModel.getRowCount() > 0
										&& !CURRENT_CLIENT
										.equals(protokolModel.getClient())) {
									ErrorDialog.showErrorMessage(
											"Въведен е друг клиент "
													+ protokolModel.getClient());
									serialNumber.setText("");
									return;
								}

								GetClientService service1 = new GetClientService();
								service1.getFirm(protokolModel.getClient(), new RequestCallback2() {
									@Override
									public <T> void callback(T t) {
										Firm firm = (Firm)t;
										if(firm == null) {
											ErrorDialog.showErrorMessage(
													String.format("Не е намерен такъв клиент %s",
															protokolModel.getClient()));
											// най-вероятно клиента е преименуван
										}
									}
								});

								String weight = protokolModel.getWheight();

								if(!isWeightValid(weight)) {
									ErrorDialog.showErrorMessage(String.format("Грешен формат на количество %s",
											weight));
									return;
								}


								insertDataFromScanner(protokolModel);

								updateExtinguisher.add(protokolModel.getBarcod());

								// set serial and barcode in
								// hashtable as entered
								isBarcodAndSerialEntered
										.add(protokolModel.getBarcod());
								isBarcodAndSerialEntered
										.add(protokolModel.getSerial());
								//
								CURRENT_CLIENT = protokolModel.getClient();
								clientLabel.setName(CURRENT_CLIENT);

								clientCombo.setSelectedItem(CURRENT_CLIENT);
								clientCombo.setEnabled(false);
							} else {
								JOptionPane
										.showMessageDialog(
												null,
												"Не е намерен такъв елемент!");
							}

							serialNumber.setText("");
						}
					});

				} else {
					JOptionPane.showMessageDialog(new JLabel(),
							"Този номер вече е въведен!");
					serialNumber.setText("");


				}
			}
		});


		// order buttons
		TooltipButton printerButton = new TooltipButton();
		printerButton.setPreferredSize(new Dimension((int) (northHelpPanel
				.getPreferredSize().getWidth() * 0.045), (int) (northHelpPanel
				.getPreferredSize().getHeight() * 0.75)));

		printerButton.setAutoSizedIcon(printerButton, new LoadIcon().setIcons(printerImage));

		printerButton.setToolTipText(getHTML_Text("ПРИНТИРАЙ СЕРВИЗНА ПОРЪЧКА"));
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

		barcodButton.setToolTipText(getHTML_Text("ГЕНЕРИРАЙ БАРКОД"));
		barcodButton.setPreferredSize(new Dimension((int) (northHelpPanel
				.getPreferredSize().getWidth() * 0.045), (int) (northHelpPanel
				.getPreferredSize().getHeight() * 0.75)));

		barcodButton.setAutoSizedIcon(barcodButton, new LoadIcon().setIcons(barcodeImage));

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

//					PrintPDFBarcodesWorker printBarcode = new PrintPDFBarcodesWorker(
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


					ServiceOrderService service = new ServiceOrderService();
					ServiceOrderBodyList body = new ServiceOrderBodyList();
					ArrayList<ServiceOrderModel> models = new ArrayList<>();
					for(int row = 0;row < tModel.getRowCount();row++) {
						ServiceOrderModel serviceOrderModel = new ServiceOrderModel();

						serviceOrderModel.setClient(CURRENT_CLIENT);
						serviceOrderModel.setType(tModel.getValueAt(row,0).toString());
						serviceOrderModel.setWheight(tModel.getValueAt(row,1).toString());
						serviceOrderModel.setBarcod(tModel.getValueAt(row,2).toString());
						serviceOrderModel.setSerial(tModel.getValueAt(row,3).toString());
						serviceOrderModel.setCategory(tModel.getValueAt(row,4).toString());
						serviceOrderModel.setBrand(tModel.getValueAt(row,5).toString());
						serviceOrderModel.setT_O(tModel.getValueAt(row,6).toString());
						serviceOrderModel.setP(tModel.getValueAt(row,7).toString());
						serviceOrderModel.setHi(tModel.getValueAt(row,8).toString());
						serviceOrderModel.setDone(tModel.getValueAt(row,9).toString());
						serviceOrderModel.setNumber(tModel.getValueAt(row,10).toString());
						serviceOrderModel.setPerson(tModel.getValueAt(row,11).toString());
						serviceOrderModel.setDate(tModel.getValueAt(row,12).toString());
						serviceOrderModel.setAdditional_data(tModel.getValueAt(row,13).toString());

						models.add(serviceOrderModel);
					}

					body.setServiceOrderModels(models);
					service.insertServiceOrder(body, new RequestCallback2() {
						@Override
						public <T> void callback(T t) {
							int insert = (Integer)t;

							jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // reset
							// cursor

							if (insert > 0) {



								String updateNumber = NumGenerator.updateNum(SERVICE_NUMBER);
								GenerateSO.updateServiceOrder(updateNumber);
								SERVICE_NUMBER = GenerateSO.nextSO();

								barcodDigits = new int[2];
								for (int i = 0; i < SERVICE_NUMBER.length(); i++) {
									allDigits[i] = SERVICE_NUMBER.charAt(i) - 48;
								}

								JOptionPane.showMessageDialog(null,
										"Данните са записани успешно!");
								updateExtinguisher.clear();
								serviceNumberLabel.setName(SERVICE_NUMBER);
								tModel.setRowCount(0);
							}
						}
					});

					readBarcod.requestFocus();
				}
			}
		});

		TooltipButton eraserButton = new TooltipButton();

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

				int yes_no = JOptionPane.showOptionDialog(null,
						"Наистина ли искате да изтриете данните ?", "",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, new String[] {
								"Да", "Не" }, // this is the array
						"default");
				if (yes_no != 0)
					return;

				tModel.setRowCount(0);

				readBarcod.requestFocus();
			}
		});

		TooltipButton clientButton = new TooltipButton();

		clientButton.setToolTipText(getHTML_Text("ВЪВЕДИ НОВ КЛИЕНТ"));
		clientButton.setPreferredSize(new Dimension((int) (northHelpPanel
				.getPreferredSize().getWidth() * 0.045), (int) (northHelpPanel
				.getPreferredSize().getHeight() * 0.75)));

		clientButton.setAutoSizedIcon(clientButton, new LoadIcon().setIcons(clientsImage));

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

					registerNewExtinguishers();
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

		numberExt_Button
				.setToolTipText(getHTML_Text("ВЪВЕДИ БРОЙ ПОЖАРОГАСИТЕЛИ"));

		numberExt_Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				registerNewExtinguishers();

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

		final TooltipButton serialNumber_Button = new TooltipButton();
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
									if (serial != null) {
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

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int col) {
				return col == 3 || col == 5 || col == 13;
			}
		};

		table = new MyTable(tModel) {
			@Override
			public void onTableChanged(TableModelEvent tableModelEvent) {
				super.onTableChanged(tableModelEvent);
				if (tModel.getRowCount() == 0)
					clear();
			}

			@Override
			public void removeAt(int row) {
				super.removeAt(row);
				tModel.removeRow(row);
			}
		};
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

		JPanel south = new JPanel(); // / GradientPanel
		south.setPreferredSize(new Dimension((int) (this.WIDTH * 1.0) - 20,
				(int) (this.HEIGHT * 0.09)));
		south.setBorder(BorderFactory.createLineBorder(Color.black));
		south.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		float labelHeight = (int) (south.getPreferredSize().getHeight() * 0.7);
		BevelLabel sallerLabel = new BevelLabel(labelHeight);

		sallerLabel.setTitle(Enums.Operator.name() + ": ");//("  Продавач: ");
		sallerLabel.setName(personName);

		clientLabel = new BevelLabel(labelHeight);

		clientLabel.setTitle(" Клиент  : ");
		clientLabel.setName("");

		BevelLabel dateLabel = new BevelLabel(labelHeight);

		dateLabel.setTitle("  Дата : ");
		dateLabel.setName(MyGetDate.getReversedSystemDate());

		serviceNumberLabel = new BevelLabel(labelHeight);

		serviceNumberLabel.setTitle(" Серв. поръчка № ");
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



	private void insertDataFromScanner(ProtokolModels protokolModel) {

		/*
		 * if(rows + tModel.getRowCount() > 99) {
		 * JOptionPane.showMessageDialog(null,"Надхвърлен лимит на елементи");
		 * return; }
		 */
		// object[0] -> is client

		// filter data (remove unneeded text)
		String type = protokolModel.getType();
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
		String brand = protokolModel.getBrand();
		int index = brand.indexOf(", носим");
		int index2 = brand.indexOf(", возим");
		if(index != -1) {
			brand = brand.substring(0,index-1);
		}
		if(index2 != -1) {
			brand = brand.substring(0,index2-1);
		}

		// тъй като килограмите идват от таблица протокол като двойка числа едното
		// указващо реалните килограми след презареждането и второто килограмите на самият съд
		// тук в сервизна поръчка трябва да извлечем само втората стойност (килограмите на съда)
		// иначе в работилницата ще покаже бъг.Трябва да се увеличи рамера на полето weight в база данни Протоколи
		// защото гърми
		String weight = protokolModel.getWheight();
		String[] spl = weight.split("/");
		if(spl.length == 1) {
			weight = spl[0].trim();
		} else if(spl.length > 1) {
			weight = spl[spl.length-1].trim();
		}
		Object[] obj = new Object[] {
				type, // type
				weight,
				protokolModel.getBarcod(), // barcod
				protokolModel.getSerial(), // serial
				protokolModel.getCategory(), // category
				brand,// brand
				protokolModel.getT_O(), // TO
				protokolModel.getP(), // P
				protokolModel.getHi(), // HI
				"не", // done
				SERVICE_NUMBER, personName, MyGetDate.getReversedSystemDate(),
				protokolModel.getAdditional_data() != null ? protokolModel.getAdditional_data() : "" };
		// add barcode
		// презаписва баркодовете
		addOneBarcodeFromScanner(0, obj);
	}

	private void addOneBarcodeFromScanner(int row, Object[] obj) {
		barcodDigits = bGen.next_barcod(barcodDigits);
		if (barcodDigits == null) {
			return;
		}
		System.arraycopy(barcodDigits, 0, allDigits, 10, 2);

		tModel.insertRow(0, obj);
		allDigits[12] = bGen.getCheckSum13(allDigits);
		tModel.setValueAt(genSO.digitsToString(allDigits), row, 2);

	}

	void registerNewExtinguishers() {
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

			insertDataForRegistrationExtinguishers(rows); // add barcodes to every
			// extinguisher

			CURRENT_CLIENT = clientCombo.getSelectedItem().toString();
			clientLabel.setText(" Клиент  : " + CURRENT_CLIENT);

			reset();
			clientCombo.setEnabled(false);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Невалиден вход !");
		}
	}
	private void insertDataForRegistrationExtinguishers(int rows) {

		for (int i = rows - 1; i >= 0; i--) {
			if (barcodDigits != null) {
				addExtinguisherForRegisterExtinguishers(0);
			} else {
				JOptionPane.showMessageDialog(null,
						"Не могат да бъдат въведени повече елементи!");
				break;
			}
		}
	}
	private void addExtinguisherForRegisterExtinguishers(int index) {

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
	public void clear() {

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
		PrintSerialBarcodesWorker.clearEnteredNumbers();
	}

	// reset type and wheight to previous init content
	private void reset() {
		type.setSelectedIndex(0);
		wheight.setSelectedIndex(0);
		numberExtinguisherField.setText("");
	}

	private boolean isWeightValid(String weight) {
		if(weight == null) {
			return false;
		}
		// тъй като килограмите идват от таблица протокол като двойка числа едното
		// указващо реалните килограми след презареждането и второто килограмите на самият съд
		// тук в сервизна поръчка трябва да извлечем само втората стойност (килограмите на съда)
		// иначе в работилницата ще покаже бъг.Трябва да се увеличи рамера на полето weight в база данни Протоколи
		// защото гърми

		String[] spl = weight.split("/");
		if(spl.length == 1) {
			weight = spl[0].trim();
		} else if(spl.length > 1) {
			weight = spl[spl.length-1].trim();
		}

		int index = weight.indexOf(' ');
		return index > 0 && Character.isDigit(weight.charAt(index - 1));
	}

}
