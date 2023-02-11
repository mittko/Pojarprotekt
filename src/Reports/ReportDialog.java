package Reports;

import Local.TextReader;
import Reports.gui_edt.*;
import db.Common;
import db.NewExtinguisher.NewExtinguishers_DB;
import db.Report.ReportRequest;
import mydate.MyGetDate;
import run.JustFrame;
import utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ReportDialog extends MainPanel {

	private JRadioButtonMenuItem rbmi = null;
	private JRadioButtonMenuItem rbmi2 = null;
	private JRadioButtonMenuItem rbmi3 = null;
	private JRadioButtonMenuItem rbmi4 = null;
	private JRadioButtonMenuItem rbmi5 = null;
	private JRadioButtonMenuItem rbmi6 = null;
	private JRadioButtonMenuItem rbmi7 = null;
	private JRadioButtonMenuItem rbmi8 = null;
	// private JMenu deliveryMenu = new JMenu("Доставки");
	private JRadioButtonMenuItem rbmi9 = null;
	private JRadioButtonMenuItem rbmi9А = null;
	private JRadioButtonMenuItem rbmi10 = null;
	private JRadioButtonMenuItem rbmi11 = null;

	private EditableField fromDate = null;
	private EditableField toDate = null;
	private EditableField so_field = null;
	private EditableField prot_field = null;
	private EditableField fact_field = null;
	private EditableField acquittanceField = null;
	private EditableField serial = null;
	private EditableField barcod = null;
	private final ArtikulsListComboBox artikulsComboBox;
	private final NewExtinguishersComboBox newExtinguishersComboBox;

	private ClientsListComboBox2 clientCombo = null;

	private JComboBox<Object> typeCombo = null;
	private JComboBox<Object> wheightCombo = null;
	private JComboBox<Object> catCombo = null;
	private JComboBox<Object> brandCombo = null;
	private JComboBox<Object> doingCombo = null;

	// private final String hexColor = "0xFFBD0A";

	private String destination = null;
	public static final String SO_Title = "Сервизна Поръчка";
	public static final String Protokol_Title = "Протокол";
	public static final String Brack_Title = "Брак";
	private String title = null;
	private String invoiceTitle = null;
	private String No = "";
	public JustFrame clientFrame = null;
	public JustFrame reportTableFrame = null;
	public JustFrame invoiceTableFrame = null;

	ArrayList<Object[]> d = null;

	public ReportDialog() {

		JPanel basePanel = new JPanel();

		basePanel.setOpaque(false);
		basePanel.setLayout(new GridLayout(1, 2, 100, 10));

		JPanel eastPanel = new JPanel();
		eastPanel.setOpaque(false);
		eastPanel.setLayout(new GridLayout(12, 1, 10, 10));

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorder(BorderFactory.createLineBorder(Color.black, 1));

		JMenu menu = new JMenu("Търсене във :                 ");
		menu.setCursor(new Cursor(Cursor.HAND_CURSOR));
		menu.setPreferredSize(new Dimension(230, 30));
		menu.setBorder(BorderFactory.createRaisedBevelBorder());

		ButtonGroup bGroup = new ButtonGroup();
		rbmi = new JRadioButtonMenuItem("Сервизна Поръчка");
		rbmi.setCursor(new Cursor(Cursor.HAND_CURSOR));
		rbmi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				destination = SERVICE;
				title = rbmi.getText();
				setComponentState(true, false, false, false, true, true, false,
						false, true, true, true, true, false);
			}

		});

		rbmi2 = new JRadioButtonMenuItem("Протоколи");
		rbmi2.setCursor(new Cursor(Cursor.HAND_CURSOR));
		rbmi2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				title = rbmi2.getText();
				destination = PROTOKOL;
				setComponentState(false, true, false, false, true, true, false,
						false, true, true, true, true, true);
			}

		});
		rbmi3 = new JRadioButtonMenuItem("Брак");
		rbmi3.setCursor(new Cursor(Cursor.HAND_CURSOR));
		rbmi3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				title = rbmi3.getText();
				destination = BRACK;
				setComponentState(false, true, false, false, true, true, false,
						false, true, true, true, true, false);
			}

		});
		rbmi4 = new JRadioButtonMenuItem("Фактури");
		rbmi4.setCursor(new Cursor(Cursor.HAND_CURSOR));
		rbmi4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				title = rbmi4.getText();
				destination = INVOICE_PARENT;
				setComponentState(false, false, true, false, false, false,
						true, false, false, false, false, false, false);
			}

		});

		rbmi5 = new JRadioButtonMenuItem("Про-форма фактура");
		rbmi5.setCursor(new Cursor(Cursor.HAND_CURSOR));
		rbmi5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				title = rbmi5.getText();
				destination = PROFORM_PARENT;
				setComponentState(false, false, true, false, false, false,
						true, false, false, false, false, false, false);
			}

		});

		rbmi6 = new JRadioButtonMenuItem("Стокови Разписки");
		rbmi6.setVisible(ACCESS_MENU[ACCESS_ACQUITTANCE]);
		rbmi6.setCursor(new Cursor(Cursor.HAND_CURSOR));
		rbmi6.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				title = rbmi6.getText();
				destination = ACQUITTANCE_PARENT;
				setComponentState(false, false, false, true, false, false,
						true, false, false, false, false, false, false);
			}

		});

		rbmi7 = new JRadioButtonMenuItem("Артикули");
		rbmi7.setCursor(new Cursor(Cursor.HAND_CURSOR));
		rbmi7.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				title = rbmi7.getText();
				destination = AVAILABLE_ARTIKULS;
				setComponentState(false, false, true, false, false, false,
						true, false, false, false, false, false, false);
			}

		});

		rbmi8 = new JRadioButtonMenuItem("Нови Пожарогасители");
		rbmi8.setCursor(new Cursor(Cursor.HAND_CURSOR));
		rbmi8.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				title = rbmi8.getText();
				destination = NEW_EXTINGUISHERS;
				setComponentState(false, false, true, false, false, false,
						false, true, false, false, false, false, false);
			}

		});

		rbmi9 = new JRadioButtonMenuItem("Доставки Артикули");
		rbmi9.setCursor(new Cursor(Cursor.HAND_CURSOR));
		rbmi9.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				title = rbmi9.getText();
				destination = DELIVERY_ARTIKULS;
				setComponentState(false, false, true, false, false, false,
						true, false, false, false, false, false, false);
			}

		});
		rbmi9А = new JRadioButtonMenuItem("Доставки Нови Пожарогасители");
		rbmi9А.setCursor(new Cursor(Cursor.HAND_CURSOR));
		rbmi9А.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				title = rbmi9А.getText();
				destination = DELIVERY_ARTIKULS;
				setComponentState(false, false, true, false, false, false,
						true, false, false, false, false, false, false);
			}

		});
		rbmi10 = new JRadioButtonMenuItem("Продажби");
		rbmi10.setCursor(new Cursor(Cursor.HAND_CURSOR));
		rbmi10.addActionListener(new ActionListener() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * java.awt.event.ActionListener#actionPerformed(java.awt.event.
			 * ActionEvent)
			 */
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				title = rbmi10.getText();
				destination = SELLS;
				setComponentState(false, false, true, false, false, false,
						true, false, false, false, false, false, false);
			}

		});
		rbmi11 = new JRadioButtonMenuItem("Наличност");
		rbmi11.setCursor(new Cursor(Cursor.HAND_CURSOR));
		rbmi11.addActionListener(new ActionListener() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * java.awt.event.ActionListener#actionPerformed(java.awt.event.
			 * ActionEvent)
			 */
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				title = rbmi11.getText();
				destination = AVAILABILITY;
				setComponentState(false, false, false, false, false, false,
						true, false, false, false, false, false, false);
			}

		});
		bGroup.add(rbmi);
		menu.add(rbmi);

		menu.addSeparator();

		bGroup.add(rbmi2);
		menu.add(rbmi2);
		menu.addSeparator();

		bGroup.add(rbmi3);
		menu.add(rbmi3);
		menu.addSeparator();

		bGroup.add(rbmi4);
		menu.add(rbmi4);
		menu.addSeparator();

		bGroup.add(rbmi5);
		menu.add(rbmi5);
		menu.addSeparator();

		bGroup.add(rbmi6);
		menu.add(rbmi6);
		menu.addSeparator();

		bGroup.add(rbmi7);
		menu.add(rbmi7);
		menu.addSeparator();

		bGroup.add(rbmi8);
		menu.add(rbmi8);
		menu.addSeparator();

		// deliveryMenu.add(rbmi9);
		// deliveryMenu.add(rbmi9А);

		bGroup.add(rbmi9);
		menu.add(rbmi9);
		menu.addSeparator();

		bGroup.add(rbmi10);
		menu.add(rbmi10);
		menu.addSeparator();

		bGroup.add(rbmi11);
		menu.add(rbmi11);

		menuBar.add(menu);

		Dimension fieldDimension = new Dimension(20, 40);

		fromDate = new EditableField("От XX.XX.XXXX", 12);
		fromDate.setPreferredSize(fieldDimension);

		toDate = new EditableField("До XX.XX.XXXX", 12);
		toDate.setPreferredSize(fieldDimension);

		so_field = new EditableField("Сервизна Поръчка", 12);
		so_field.setPreferredSize(fieldDimension);

		prot_field = new EditableField("Протокол", 12);
		prot_field.setPreferredSize(fieldDimension);

		fact_field = new EditableField("Фактура", 12);
		fact_field.setPreferredSize(fieldDimension);

		acquittanceField = new EditableField("Стокови Разписки", 12);
		acquittanceField.setVisible(ACCESS_MENU[ACCESS_ACQUITTANCE]);
		acquittanceField.setPreferredSize(fieldDimension);

		serial = new EditableField("Монтажен Номер", 12);
		serial.setPreferredSize(fieldDimension);

		barcod = new EditableField("Баркод", 12);
		barcod.setPreferredSize(fieldDimension);

		artikulsComboBox = new ArtikulsListComboBox(AVAILABLE_ARTIKULS);

		newExtinguishersComboBox = new NewExtinguishersComboBox();

		clientCombo = new ClientsListComboBox2();
		clientCombo.setPreferredSize(new Dimension(230, 30));
		clientCombo.setBorder(BorderFactory.createLoweredBevelBorder());

		JPanel westPanel = new JPanel();
		westPanel.setOpaque(false);
		GridLayout westLayout = new GridLayout(11, 1, 10, 10);
		westPanel.setLayout(westLayout);

		JLabel typeLabel = new JLabel("Вид");
		typeLabel.setBorder(BorderFactory.createLoweredBevelBorder());

		typeCombo = new JComboBox<Object>(new Object[] { "",
				MainPanel.type_Prah_BC, MainPanel.type_Prah_ABC,
				MainPanel.type_Water, MainPanel.type_Water_Fame,
				MainPanel.type_CO2 });
		typeCombo.setBorder(BorderFactory.createLoweredBevelBorder());

		JLabel wheightLabel = new JLabel("Маса");
		wheightLabel.setBorder(BorderFactory.createLoweredBevelBorder());

		Object[] wheightFire = TextReader.getData("Local/wheight2.txt");
		wheightCombo = new JComboBox<Object>(wheightFire);
		wheightCombo.setBorder(BorderFactory.createLoweredBevelBorder());

		JLabel catLabel = new JLabel("Категория");
		catLabel.setBorder(BorderFactory.createLoweredBevelBorder());

		catCombo = new JComboBox<Object>(new Object[] { "", "1", "2", "3", "4",
				"5" });
		catCombo.setBorder(BorderFactory.createLoweredBevelBorder());

		JLabel brandLabel = new JLabel("Марка");
		brandLabel.setBorder(BorderFactory.createLoweredBevelBorder());

		Object[] brandFire = TextReader.getData("Local/brand2.txt");
		brandCombo = new JComboBox<Object>(brandFire);
		brandCombo.setBorder(BorderFactory.createLoweredBevelBorder());

		JLabel doingLabel = new JLabel("Вид обслужване");
		doingLabel.setBorder(BorderFactory.createLoweredBevelBorder());

		doingCombo = new JComboBox<Object>(new Object[] { "", "ТО", "П", "ХИ",
				"ТО,П", "ТО,П,ХИ" });
		doingCombo.setBorder(BorderFactory.createLoweredBevelBorder());

		JButton searchButton = new JButton();
		searchButton.setText("Търсене");
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (destination == null) {
					JOptionPane.showMessageDialog(null,
							"Не е посочено търсене!");
					return;
				}

				SwingWorker<Void, Void> sw = new SwingWorker<Void, Void>() {

					@Override
					protected Void doInBackground() throws Exception {
						// TODO Auto-generated method stub
						JDialog jDialog = ((JDialog) (SwingUtilities
								.getWindowAncestor(ReportDialog.this)));
						jDialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));

						ArrayList<Object[]> data = null;

						switch (destination) {
							case SERVICE:
							case PROTOKOL:
							case BRACK:
								try {

									data = ReportRequest
											.getReports(buildCommandForSO_Table_Protokol_Brack(destination));
								} finally {
									switch (destination) {
										case SERVICE:
											title = SO_Title;
											break;
										case PROTOKOL:
											title = Protokol_Title;
											break;
										case BRACK:
											title = Brack_Title;
											break;
										default:
											break;
									}
									SwingUtilities.invokeLater(new EDTSO_Pr_Br(
											data, jDialog, No, "Справки " + title,
											destination));
									No = ""; // clear current number

								}
								break;
							case INVOICE_PARENT:
							case PROFORM_PARENT:
								try {

									// get info for parent
									switch (destination) {
										case INVOICE_PARENT:
											invoiceTitle = "Фактура";
											d = Common
													.getInfoForParentAndChildTable(buildCommandForInvoice());
											break;
										case PROFORM_PARENT:
											invoiceTitle = "Проформа";
											d = Common
													.getInfoForParentAndChildTable(buildCommandForProform());
											break;
										default:
											break;
									}
								} finally {

									SwingUtilities
											.invokeLater(new EDTInvoice(d, jDialog,
													No, "Справки " + invoiceTitle));
									No = "";

								}

								break;
							case ACQUITTANCE_PARENT:

								try {
									d = Common
											.getInfoForParentAndChildTable(buildCommandForAcquittance());

								} finally {
									EDTAcquitance edt = new EDTAcquitance(d,
											jDialog, "Справки Стокова Разписка");
									SwingUtilities.invokeLater(edt);

								}
								break;
							case AVAILABLE_ARTIKULS: {
								String command = buildSearchCommandForArtikuls();

								///
								ArrayList<Object[]> dataa = ReportRequest
										.getReports(command);
								EDTArtikuls edt = new EDTArtikuls(dataa, jDialog,
										"Артикули");
								SwingUtilities.invokeLater(edt);
								break;
							}
							case NEW_EXTINGUISHERS: {
								ArrayList<Object[]> newExtinguishers = NewExtinguishers_DB
										.getNewExtinguishers(buildCommandForNewExtinguishers());
								EDTNewExtinguishers edt = new EDTNewExtinguishers(
										newExtinguishers, jDialog,
										"Нови пожарогасители");
								SwingUtilities.invokeLater(edt);
								break;
							}
							case DELIVERY_ARTIKULS: {
								String command =
										buildCommandForDeliveryArtikuls();
								ArrayList<Object[]> getDelivery = ReportRequest.getReportsForDelivery
										(command, 1);
								EDTDelivery edt = new EDTDelivery(getDelivery,
										jDialog, "Справка Доставки "
										+ fromDate.getText() + " - "
										+ toDate.getText());
								SwingUtilities.invokeLater(edt);

								break;
							}
							case SELLS: {
								// също трябва да се добави проверка продажбата да е след датата на доставката !!!
								String from = fromDate.getText();
								String to = toDate.getText();
								ArrayList<Object[]> getDelivery = ReportRequest
										.getReportsForSales(buildCommandForSales2FromTo(
												"01.06.2016", to),4);
								ArrayList<Object[]> getInvoice = ReportRequest
										.getReportsForSales(buildCommandForSalesFromTo(
												from, to), 5);

								EDTSales edt = new EDTSales(getInvoice,
										getDelivery, jDialog, "Справка Продажби "
										+ fromDate.getText() + " - "
										+ toDate.getText());
								SwingUtilities.invokeLater(edt);
								break;
							}
							case AVAILABILITY: {

								if (fromDate.getText().isEmpty()
										|| toDate.getText().isEmpty()) {
									JOptionPane.showMessageDialog(null,
											"Не е избрана дата");
									return null;
								}
								String from = fromDate.getText();
								String to = toDate.getText();
								String oneDayBeforeForm = MyGetDate.getDateBeforeAnotherDate(1,
										MyGetDate.getDateFromString(fromDate
												.getText()));

								// System.out.printf("%s %s\n", from, to);
								// System.out.printf("%s %s\n", from2, to);
								ArrayList<Object[]> deliveryBeforeFirstSelectedDate = ReportRequest
										.getReportsForAvailability(buildCommandForDeliveryFromTo(
												"01.06.2016", oneDayBeforeForm), 1);
								ArrayList<Object[]> invoiceBeforeFirstSelectedDate = ReportRequest
										.getReportsForAvailability(buildCommandForInvoiceFromTo(
												"01.06.2016", oneDayBeforeForm), 1);

								ArrayList<Object[]> deliveryBetweenSelectedDates = ReportRequest
										.getReportsForAvailability(buildCommandForDeliveryFromTo(
												from, to), 1);
								ArrayList<Object[]> invoiceBetweenSelectedDates = ReportRequest
										.getReportsForAvailability(buildCommandForInvoiceFromTo(
												from, to), 1);
								EDTAvailability edt = new EDTAvailability(
										deliveryBeforeFirstSelectedDate, invoiceBeforeFirstSelectedDate,
										deliveryBetweenSelectedDates, invoiceBetweenSelectedDates, from, to,
										jDialog, "Справка Наличност " + oneDayBeforeForm
										+ " - " + to);
								SwingUtilities.invokeLater(edt);
								break;
							}
						}

						return null;
					}

				};
				sw.execute();
			}

		});

		searchButton.setPreferredSize(new Dimension(200, 30));

		eastPanel.add(menuBar);

		JLabel emptyLabel = new JLabel(" Клиенти ");
		emptyLabel.setBorder(BorderFactory.createLoweredBevelBorder());

		eastPanel.add(clientCombo);
		eastPanel.add(fromDate);
		eastPanel.add(toDate);
		eastPanel.add(so_field);
		eastPanel.add(prot_field);
		eastPanel.add(fact_field);
		eastPanel.add(acquittanceField);
		eastPanel.add(serial);
		eastPanel.add(barcod);
		eastPanel.add(artikulsComboBox);// (artikuliField);
		eastPanel.add(newExtinguishersComboBox);

		westPanel.add(typeLabel);
		westPanel.add(typeCombo);
		westPanel.add(wheightLabel);
		westPanel.add(wheightCombo);
		westPanel.add(catLabel);
		westPanel.add(catCombo);
		westPanel.add(brandLabel);
		westPanel.add(brandCombo);
		// test
		westPanel.add(doingLabel);
		westPanel.add(doingCombo);

		// westPanel.add(new JLabel());
		westPanel.add(searchButton);

		basePanel.add(eastPanel);
		basePanel.add(westPanel);

		JPanel moreWidthPanel = new JPanel();// GradientPanel();

		moreWidthPanel.add(basePanel);
		moreWidthPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		setComponentState(false, false, false, false, false, false, false,
				false, false, false, false, false, false);
		this.add(moreWidthPanel);

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				ReportDialog r = new ReportDialog();
				JustFrame f = new JustFrame(r);
				f.setTitle("Справки");
				f.pack();
			}

		});
	}

	private String buildCommandForSO_Table_Protokol_Brack(String dest) {

		StringBuilder mainCommand1 = new StringBuilder();
		int selectedCriterii = 0;
		String selectWhat = "";
		switch (dest) {
		case SERVICE:
			selectWhat = "select client, type, wheight, barcod, serial, category, brand, "
					+ "T_O, P, HI, done, number, person, date , additional_data from ";
			break;
		case PROTOKOL:
			selectWhat = "select client, type, wheight, barcod, serial, category, brand,"
					+ " T_O, P, HI, parts, value, number, person, date , kontragent, invoiceByKontragent, additional_data, uptodate from ";
			break;
		case BRACK:
			selectWhat = "select * from ";
			break;
		default:
			break;
		}

		mainCommand1.append(selectWhat).append(dest); // "select * from "

		if (!clientCombo.getSelectedItem().equals("")) {
			if (selectedCriterii == 0) {
				mainCommand1.append(" where ");
			} else if (selectedCriterii > 0) {
				mainCommand1.append(" and ");
			}
			String client = clientCombo.getSelectedItem().toString();
			mainCommand1.append("client = " + "'").append(client).append("'");
			selectedCriterii++;
		}

		if (!so_field.getText().isEmpty() && dest.equals(SERVICE)) {
			if (selectedCriterii == 0) {
				mainCommand1.append(" where ");
			} else if (selectedCriterii > 0) {
				mainCommand1.append(" and ");
			}
			// this works !!! mainCommand1.append("substr(barcod,1,10) like " +
			// "'"
			// + so_field.getText() + "'");
			mainCommand1.append("number = " + "'").append(so_field.getText()).append("'");
			No = so_field.getText();
			selectedCriterii++;
		}
		if (!prot_field.getText().isEmpty()
				&& (dest.equals(PROTOKOL) || dest.equals(BRACK))) {
			if (selectedCriterii == 0) {
				mainCommand1.append(" where ");
			} else if (selectedCriterii > 0) {
				mainCommand1.append(" and ");
			}
			// if (dest.equals(PROTOKOL)) {
			mainCommand1.append("number = " + "'").append(prot_field.getText()).append("'");
			// }
			No = prot_field.getText();
			selectedCriterii++;
		}

		if (!typeCombo.getSelectedItem().equals("")) {
			if (selectedCriterii == 0) {
				mainCommand1.append(" where ");
			} else if (selectedCriterii > 0) {
				mainCommand1.append(" and ");
			}
			mainCommand1.append("type = " + "'" + typeCombo.getSelectedItem()
					+ "'");
			selectedCriterii++;
		}
		if (!wheightCombo.getSelectedItem().equals("")) {
			if (selectedCriterii == 0) {
				mainCommand1.append(" where ");
			} else if (selectedCriterii > 0) {
				mainCommand1.append(" and ");
			}
			mainCommand1.append("wheight = " + "'").append(wheightCombo.getSelectedItem()).append("'");
			selectedCriterii++;
		}
		if (!catCombo.getSelectedItem().equals("")) {
			if (selectedCriterii == 0) {
				mainCommand1.append(" where ");
			} else if (selectedCriterii > 0) {
				mainCommand1.append(" and ");
			}
			mainCommand1.append("category = " + "'").append(catCombo.getSelectedItem()).append("'");
			selectedCriterii++;
		}
		if (!brandCombo.getSelectedItem().equals("")) {
			if (selectedCriterii == 0) {
				mainCommand1.append(" where ");
			} else if (selectedCriterii > 0) {
				mainCommand1.append(" and ");
			}
			mainCommand1.append("brand = " + "'").append(brandCombo.getSelectedItem()).append("'");
			selectedCriterii++;
		}

		if (doingCombo.isEnabled() && !doingCombo.getSelectedItem().equals("")) {
			if (selectedCriterii == 0) {
				mainCommand1.append(" where ");
			} else if (selectedCriterii > 0) {
				mainCommand1.append(" and ");
			}
			String selectedItem = doingCombo.getSelectedItem().toString();

			switch (selectedItem) {
				case "ТО":
					mainCommand1
							.append("T_O not = 'не' and P = 'не' and HI = 'не'");
					break;
				case "П":
					mainCommand1
							.append("P not = 'не' and T_O = 'не' and HI = 'не'");
					break;
				case "ХИ":
					mainCommand1
							.append("HI not = 'не' and T_O = 'не' and P = 'не'");
					break;
				case "ТО,П":
					mainCommand1
							.append("T_O not = 'не' and P not = 'не' and HI = 'не'");
					break;
				case "ТО,П,ХИ":
					mainCommand1
							.append("T_O not = 'не' and P not = 'не' and HI not = 'не'");
					break;
			}

			selectedCriterii++;
		} else {
			// do nothing
		}
		if (!serial.getText().isEmpty()) {
			if (selectedCriterii == 0) {
				mainCommand1.append(" where ");
			} else if (selectedCriterii > 0) {
				mainCommand1.append(" and ");
			}
			mainCommand1.append("serial = " + "'").append(serial.getText()).append("'");
			selectedCriterii++;
			No = serial.getText();
		} else if (!barcod.getText().isEmpty()) {
			if (selectedCriterii == 0) {
				mainCommand1.append(" where ");
			} else if (selectedCriterii > 0) {
				mainCommand1.append(" and ");
			}
			mainCommand1.append("barcod = " + "'").append(barcod.getText()).append("'");
			selectedCriterii++;
			No = barcod.getText();
		}

		if (!fromDate.getText().isEmpty() && !toDate.getText().isEmpty()) {
			if (selectedCriterii == 0) {
				mainCommand1.append(" where ");
			} else if (selectedCriterii > 0) {
				mainCommand1.append(" and ");
			}
			mainCommand1.append("date between " + "Date('").append(fromDate.getText()).append("')").append(" and ").append("Date('").append(toDate.getText()).append("')");
			selectedCriterii++;
		}

		// if (destination.equals(SERVICE) || destination.equals(PROTOKOL)) {
		// if (flag) {
		// mainCommand1.append(" and ");
		// } else {
		// mainCommand1.append(" where ");
		// }
		// mainCommand1.append("uptodate = 'not null'");
		// }

		// add sorting
		mainCommand1.append(" order by CAST(date as DATE) desc");

		return mainCommand1.toString();
	}

	//
	private String buildCommandForAcquittance() {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from " + ACQUITTANCE_PARENT + ","
				+ ACQUITTANCE_CHILD
				+ " where AcquittanceParentDB.id = AcquittanceChildDB.id");

		if (!clientCombo.getSelectedItem().equals("")) {
			sb.append(" and AcquittanceParentDB.client = '").append(clientCombo.getSelectedItem()).append("'");
		}
		if (!acquittanceField.getText().isEmpty()) {
			sb.append(" and AcquittanceParentDB.id = " + "'").append(acquittanceField.getText()).append("'");
		}
		// add date
		if (!fromDate.getText().isEmpty() && !toDate.getText().isEmpty()) {
			sb.append(" and AcquittanceParentDB.date between " + "Date('").append(fromDate.getText()).append("')").append(" and ").append("Date('").append(toDate.getText()).append("')");
		}
		if (!artikulsComboBox.getSelectedItem().toString().equals("")) {
			sb.append(" and AcquittanceChildDB.artikul = " + "'").append(artikulsComboBox.getSelectedItem().toString()).append("'");
		}
		sb.append(" order by CAST(date as DATE) desc");
		return sb.toString();
	}

	private String buildCommandForProform() {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from " + PROFORM_PARENT + "," + PROFORM_CHILD
				+ " where ProformParentDB.id = ProformChildDB2.id");

		if (!clientCombo.getSelectedItem().equals("")) {
			sb.append(" and ProformParentDB.client = '").append(clientCombo.getSelectedItem()).append("'");
		}
		if (!fact_field.getText().isEmpty()) {
			sb.append(" and ProformParentDB.id = " + "'").append(fact_field.getText()).append("'");
		}
		// add date
		if (!fromDate.getText().isEmpty() && !toDate.getText().isEmpty()) {
			sb.append(" and ProformParentDB.date between " + "Date('").append(fromDate.getText()).append("')").append(" and ").append("Date('").append(toDate.getText()).append("')");
		}
		if (!artikulsComboBox.getSelectedItem().toString().equals("")) {
			sb.append(" and ProformChildDB2.make = " + "'").append(artikulsComboBox.getSelectedItem().toString()).append("'");
		}
		sb.append(" order by CAST(date as DATE) desc");
		return sb.toString();
	}

	private String buildCommandForInvoice() {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from " + INVOICE_PARENT + "," + INVOICE_CHILD
				+ " where InvoiceParentDB5.id = InvoiceChildDB7.id");

		if (!clientCombo.getSelectedItem().equals("")) {
			sb.append(" and InvoiceParentDB5.client = '").append(clientCombo.getSelectedItem()).append("'");
		}
		if (!fact_field.getText().isEmpty()) {
			sb.append(" and InvoiceParentDB5.id = " + "'").append(fact_field.getText()).append("'");
		}
		// add date
		if (!fromDate.getText().isEmpty() && !toDate.getText().isEmpty()) {
			sb.append(" and InvoiceParentDB5.date between " + "Date('").append(fromDate.getText()).append("')").append(" and ").append("Date('").append(toDate.getText()).append("')");
		}
		if (!artikulsComboBox.getSelectedItem().toString().equals("")) {
			sb.append(" and InvoiceChildDB7.artikul = " + "'").append(artikulsComboBox.getSelectedItem().toString()).append("'");
		}
		sb.append(" order by CAST(date as DATE) desc");
		return sb.toString();
	}

	private String buildSearchCommandForArtikuls() {
		StringBuilder mainCommand1 = new StringBuilder();

		int selectedCriterii = 0;

		mainCommand1.append("select * from " + AVAILABLE_ARTIKULS);

		if (!clientCombo.getSelectedItem().equals("")) {
			if (selectedCriterii == 0) {
				mainCommand1.append(" where ");
			} else if (selectedCriterii > 0) {
				mainCommand1.append(" and ");
			}
			mainCommand1.append("client = " + "'").append(clientCombo.getSelectedItem()).append("'");
			selectedCriterii++;
		}
		if (!fact_field.getText().isEmpty()) {
			if (selectedCriterii == 0) {
				mainCommand1.append(" where invoice = " + "'").append(fact_field.getText()).append("'");
			} else if (selectedCriterii > 0) {
				mainCommand1.append(" and invoice = " + "'").append(fact_field.getText()).append("'");
			}
			selectedCriterii++;
		}
		if (!artikulsComboBox.getSelectedItem().toString().equals("")) {
			if (selectedCriterii == 0) {
				mainCommand1.append(" where ");
			} else if (selectedCriterii > 0) {
				mainCommand1.append(" and ");
			}
			mainCommand1.append("artikul = " + "'").append(artikulsComboBox.getSelectedItem().toString()).append("'");
			selectedCriterii++;
		}

		if (!fromDate.getText().isEmpty() && !toDate.getText().isEmpty()) {
			if (selectedCriterii == 0) {
				mainCommand1.append(" where ");
			} else if (selectedCriterii > 0) {
				mainCommand1.append(" and ");
			}
			mainCommand1.append("date between " + "Date('").append(fromDate.getText()).append("')").append(" and ").append("Date('").append(toDate.getText()).append("')");
			selectedCriterii++;
		}
		 mainCommand1.append(" order by CAST(date as DATE) desc");
		return mainCommand1.toString();
	}

	private String buildCommandForNewExtinguishers() {
		StringBuilder mainCommand1 = new StringBuilder();

		int selectedCriterii = 0;

		mainCommand1.append("select * from " + NEW_EXTINGUISHERS);

		if (!clientCombo.getSelectedItem().equals("")) {
			if (selectedCriterii == 0) {
				mainCommand1.append(" where ");
			} else if (selectedCriterii > 0) {
				mainCommand1.append(" and ");
			}
			mainCommand1.append("client = " + "'").append(clientCombo.getSelectedItem()).append("'");
			selectedCriterii++;
		}
		if (!fact_field.getText().isEmpty()) {
			if (selectedCriterii == 0) {
				mainCommand1.append(" where invoice = " + "'").append(fact_field.getText()).append("'");
			} else if (selectedCriterii > 0) {
				mainCommand1.append(" and invoice = " + "'").append(fact_field.getText()).append("'");
			}
			selectedCriterii++;
		}
		if (!newExtinguishersComboBox.getSelectedItem().toString().equals("")) {
			if (selectedCriterii == 0) {
				mainCommand1.append(" where ");
			} else if (selectedCriterii > 0) {
				mainCommand1.append(" and ");
			}
			mainCommand1.append("type = " + "'").append(newExtinguishersComboBox.getSelectedItem().toString()).append("'");
			selectedCriterii++;
		}

		if (!fromDate.getText().isEmpty() && !toDate.getText().isEmpty()) {
			if (selectedCriterii == 0) {
				mainCommand1.append(" where ");
			} else if (selectedCriterii > 0) {
				mainCommand1.append(" and ");
			}
			mainCommand1.append("date between " + "Date('" + fromDate.getText()
					+ "')" + " and " + "Date('" + toDate.getText() + "')");
			selectedCriterii++;
		}
		mainCommand1.append(" order by CAST(date as DATE)");
		return mainCommand1.toString();
	}

	private String buildCommandForDeliveryArtikuls() {
		StringBuilder mainCommand1 = new StringBuilder();

		int selectedCriterii = 0;
		mainCommand1.append("select artikul, quantity, "
				+ "med, value, kontragent, invoiceByKontragent,"
				+ " date, operator from " + DELIVERY_ARTIKULS);

		if (!clientCombo.getSelectedItem().equals("")) {
			if (selectedCriterii == 0) {
				mainCommand1.append(" where ");
			} else if (selectedCriterii > 0) {
				mainCommand1.append(" and ");
			}
			mainCommand1.append("kontragent = " + "'").append(clientCombo.getSelectedItem()).append("'");
			selectedCriterii++;
		}
		if (!fact_field.getText().isEmpty()) {
			if (selectedCriterii == 0) {
				mainCommand1.append(" where invoiceByKontragent = " + "'"
						+ fact_field.getText() + "'");
			} else if (selectedCriterii > 0) {
				mainCommand1.append(" and invoiceByKontragent = " + "'").append(fact_field.getText()).append("'");
			}
			selectedCriterii++;
		}
		if (!artikulsComboBox.getSelectedItem().toString().equals("")) {
			if (selectedCriterii == 0) {
				mainCommand1.append(" where ");
			} else if (selectedCriterii > 0) {
				mainCommand1.append(" and ");
			}
			mainCommand1.append("artikul = " + "'").append(artikulsComboBox.getSelectedItem().toString()).append("'");
			selectedCriterii++;
		}
		if (!fromDate.getText().isEmpty() && !toDate.getText().isEmpty()) {
			if (selectedCriterii == 0) {
				mainCommand1.append(" where ");
			} else if (selectedCriterii > 0) {
				mainCommand1.append(" and ");
			}
			mainCommand1.append("date between Date('").append(fromDate.getText()).append("') and Date('").append(toDate.getText()).append("')");
			selectedCriterii++;
		}
		//mainCommand1.append(" order by CAST(date as DATE)");

		return mainCommand1.toString();
	}


	private String buildCommandForSales2FromTo(String from, String to) {
		StringBuilder mainCommand1 = new StringBuilder();
		mainCommand1.append("select DeliveryArtikulsDB2.invoiceByKontragent, DeliveryArtikulsDB2.kontragent," +
				" DeliveryArtikulsDB2.date, DeliveryArtikulsDB2.artikul, DeliveryArtikulsDB2.value from " +
				DELIVERY_ARTIKULS + " where DeliveryArtikulsDB2.date between " + "Date('").append(from).append("')").append(" and ").append("Date('").append(to).append("')");
		if (!artikulsComboBox.getSelectedItem().toString().equals("")) {
			mainCommand1.append(" and DeliveryArtikulsDB2.artikul = " + "'").append(artikulsComboBox.getSelectedItem().toString()).append("'");
		}
		return mainCommand1.toString();
	}

	private String buildCommandForSalesFromTo(String from, String to) {
		StringBuilder sb = new StringBuilder();
		sb.append("select InvoiceChildDB7.id, InvoiceChildDB7.client, InvoiceChildDB7.invoiceByKontragent, InvoiceChildDB7.kontragent,"
				+ " InvoiceChildDB7.artikul, InvoiceChildDB7.med, InvoiceChildDB7.quantity," +
				" InvoiceChildDB7.price, InvoiceParentDB5.date from " +
				INVOICE_CHILD + "," + INVOICE_PARENT + " where InvoiceParentDB5.id = InvoiceChildDB7.id" +
				" and InvoiceParentDB5.date between " + "Date('").append(from).append("')").append(" and ").append("Date('").append(to).append("')");
		if (!artikulsComboBox.getSelectedItem().toString().equals("")) {
			sb.append(" and  InvoiceChildDB7.artikul = " + "'").append(artikulsComboBox.getSelectedItem().toString()).append("'");
		}
		return sb.toString();
	}

	private String buildCommandForDeliveryFromTo(String from, String to) {
		StringBuilder mainCommand1 = new StringBuilder();
		mainCommand1.append("select DeliveryArtikulsDB2.artikul, DeliveryArtikulsDB2.quantity, DeliveryArtikulsDB2.value, DeliveryArtikulsDB2.date from "
				+ DELIVERY_ARTIKULS + " where DeliveryArtikulsDB2.date between "
				+ "Date('").append(from).append("')").append(" and ").append("Date('").append(to).append("')");
		if (!artikulsComboBox.getSelectedItem().toString().equals("")) {
			mainCommand1.append(" and DeliveryArtikulsDB2.artikul = " + "'").append(artikulsComboBox.getSelectedItem().toString()).append("'");
		}
		return mainCommand1.toString();
	}

	private String buildCommandForInvoiceFromTo(String from, String to) {
		StringBuilder sb = new StringBuilder();
		sb.append("select InvoiceChildDB7.artikul, InvoiceChildDB7.quantity," +
				" InvoiceChildDB7.price, InvoiceParentDB5.date from " +
				INVOICE_CHILD + "," + INVOICE_PARENT +
				" where InvoiceParentDB5.id = InvoiceChildDB7.id" +
				" and InvoiceParentDB5.date between " + "Date('")
				.append(from).append("')").append(" and ").append("Date('").append(to).append("')");
		if (!artikulsComboBox.getSelectedItem().toString().equals("")) {
			sb.append(" and  InvoiceChildDB7.artikul = " + "'").append(artikulsComboBox.getSelectedItem().toString()).append("'");
		}
		return sb.toString();
	}

	private void setComponentState(boolean so, boolean prot, boolean fact,
			boolean acq, boolean ser, boolean bar, boolean artikul,
			boolean newExtinguisher, boolean type, boolean whei, boolean cat,
			boolean brand, boolean doing) {
		so_field.setEditable(so);
		prot_field.setEditable(prot);
		fact_field.setEditable(fact);
		acquittanceField.setEditable(acq);
		serial.setEditable(ser);
		barcod.setEditable(bar);
		artikulsComboBox.setEnabled(artikul);
		newExtinguishersComboBox.setEnabled(newExtinguisher);
		typeCombo.setEnabled(type);
		wheightCombo.setEnabled(whei);
		catCombo.setEnabled(cat);
		brandCombo.setEnabled(brand);
		doingCombo.setEnabled(doing);

	}

}
