package reports;

import files.TextReader;
import http.RequestCallback;
import http.reports.GetReportsService;
import models.CreditNoteReports;
import models.DeliveryReports;
import models.InvoiceModel;
import reports.gui_edt.*;
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
import java.util.HashMap;
import java.util.List;

public class ReportDialog extends MainPanel {

	private JRadioButtonMenuItem rbmi = null;
	private JRadioButtonMenuItem rbmi2 = null;
	private JRadioButtonMenuItem rbmi3 = null;
	private JRadioButtonMenuItem rbmi4 = null;
	private JRadioButtonMenuItem rbmi5 = null;
	private JRadioButtonMenuItem rbmi6 = null;
	private JRadioButtonMenuItem rbmi7 = null;
	private JRadioButtonMenuItem rbmi8 = null;
	private JRadioButtonMenuItem rbmi9 = null;
	private JRadioButtonMenuItem rbmi9A = null;
	private JRadioButtonMenuItem rbmi10 = null;
	private JRadioButtonMenuItem rbmi11 = null;

	private JRadioButtonMenuItem rbm12 = null;

	private EditableField fromDate = null;
	private EditableField toDate = null;
	private EditableField so_field = null;
	private EditableField prot_field = null;
	private EditableField fact_field = null;
	private EditableField acquittanceField = null;
	private EditableField serial = null;
	private EditableField barcod = null;
	private final ArtikulsListComboBox artikulsComboBox;
	private final ArtikulsListComboBox newExtinguishersComboBox;

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
		rbmi7.setVisible(false);
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
		rbmi8.setVisible(false);
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
		rbmi9A = new JRadioButtonMenuItem("Доставки Нови Пожарогасители");
		rbmi9A.setCursor(new Cursor(Cursor.HAND_CURSOR));
		rbmi9A.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				title = rbmi9A.getText();
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
		rbm12 = new JRadioButtonMenuItem("Кредитно Известие");
		rbm12.setCursor(new Cursor(Cursor.HAND_CURSOR));
		rbm12.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				destination = CREDIT_NOTE;
				title = rbm12.getText();
				setComponentState(false, false, true, false, false, false,
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

		bGroup.add(rbmi9);
		menu.add(rbmi9);
		menu.addSeparator();

		bGroup.add(rbmi10);
		menu.add(rbmi10);
		menu.addSeparator();

		bGroup.add(rbmi11);
		menu.add(rbmi11);
		menu.addSeparator();

		bGroup.add(rbm12);
		menu.add(rbm12);

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

		newExtinguishersComboBox = new ArtikulsListComboBox(NEW_EXTINGUISHERS);

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

		Object[] wheightFire = TextReader.getData("files/wheight2.txt");
		wheightCombo = new JComboBox<Object>(wheightFire);
		wheightCombo.setBorder(BorderFactory.createLoweredBevelBorder());

		JLabel catLabel = new JLabel("Категория");
		catLabel.setBorder(BorderFactory.createLoweredBevelBorder());

		catCombo = new JComboBox<Object>(new Object[] { "", "1", "2", "3", "4",
				"5" });
		catCombo.setBorder(BorderFactory.createLoweredBevelBorder());

		JLabel brandLabel = new JLabel("Марка");
		brandLabel.setBorder(BorderFactory.createLoweredBevelBorder());

		Object[] brandFire = TextReader.getData("files/brand2.txt");
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

								openSO_Table_Protokol_Brack(destination, jDialog);

								break;
							case INVOICE_PARENT:
							case PROFORM_PARENT:


								// get info for parent
								switch (destination) {
									case INVOICE_PARENT:
										invoiceTitle = "Фактура";

										openInvoices(invoiceTitle,jDialog);
										break;
									case PROFORM_PARENT:
										invoiceTitle = "Проформа";

										openInvoices(invoiceTitle,jDialog);
										break;
									default:
										break;
								}


								break;
							case ACQUITTANCE_PARENT:

								openAcquittance(jDialog);

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


								openDelivery(jDialog);

								break;
							}
							case SELLS: {
								if (fromDate.getText().isEmpty()
										|| toDate.getText().isEmpty()) {
									JOptionPane.showMessageDialog(null,
											"Не е избрана начална и крайна дата");
									jDialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
									return null;
								}
								// също трябва да се добави проверка продажбата да е след датата на доставката !!!

								openSales(fromDate.getText(),toDate.getText(),jDialog);
								break;
							}
							case AVAILABILITY: {
								if (fromDate.getText().isEmpty()
										|| toDate.getText().isEmpty()) {
									JOptionPane.showMessageDialog(null,
											"Не е избрана начална и крайна дата");
									jDialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
									return null;
								}
								String from = fromDate.getText();
								String to = toDate.getText();

								openAvailability(from,to,jDialog);
								break;
							} case CREDIT_NOTE: {

								openCreditNotes(jDialog);

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

	private void openSO_Table_Protokol_Brack(String dest, JDialog jDialog) {
		GetReportsService getReportsService = new GetReportsService();

		HashMap<String, String> optionsParam = new HashMap<>();

		if (clientCombo.getSelectedItem() != null && !clientCombo.getSelectedItem().equals("")) {
			optionsParam.put("client",clientCombo.getSelectedItem().toString());
		}

		if (!so_field.getText().isEmpty() && dest.equals(SERVICE)) {
			optionsParam.put("number",so_field.getText());
		}
		if (!prot_field.getText().isEmpty()
				&& (dest.equals(PROTOKOL) || dest.equals(BRACK))) {
			optionsParam.put("number",prot_field.getText());
		}

		if (typeCombo.getSelectedItem() != null && !typeCombo.getSelectedItem().equals("")) {
			optionsParam.put("type",typeCombo.getSelectedItem().toString());
		}
		if (wheightCombo.getSelectedItem() != null && !wheightCombo.getSelectedItem().equals("")) {
			optionsParam.put("wheight",wheightCombo.getSelectedItem().toString());
		}
		if (catCombo.getSelectedItem() != null && !catCombo.getSelectedItem().equals("")) {
			optionsParam.put("category",catCombo.getSelectedItem().toString());
		}
		if (brandCombo.getSelectedItem() != null && !brandCombo.getSelectedItem().equals("")) {
			optionsParam.put("brand",brandCombo.getSelectedItem().toString());
		}

		if (doingCombo.isEnabled() && doingCombo.getSelectedItem() != null &&
				!doingCombo.getSelectedItem().equals("")) {

			String selectedItem = doingCombo.getSelectedItem().toString();

			switch (selectedItem) {
				case "ТО":
					optionsParam.put("doing", "ТО");
					break;
				case "П":
					optionsParam.put("doing","П");
					break;
				case "ХИ":
					optionsParam.put("doing","ХИ");
					break;
				case "ТО,П":
					optionsParam.put("doing","ТО,П");
					break;
				case "ТО,П,ХИ":
					optionsParam.put("doing","ТО,П,ХИ");
					break;
			}

		}
		if (!serial.getText().isEmpty()) {
			optionsParam.put("serial",serial.getText());
		} else if (!barcod.getText().isEmpty()) {
			optionsParam.put("barcode",barcod.getText());
		}

		if (!fromDate.getText().isEmpty() && !toDate.getText().isEmpty()) {
			optionsParam.put("fromDate",fromDate.getText());
			optionsParam.put("toDate",toDate.getText());
		}

		switch (dest) {
			case SERVICE:
				title = SO_Title;
				getReportsService.getServiceOrders(optionsParam, new RequestCallback() {
					@Override
					public <T> void callback(List<T> objects) {

						jDialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
						if(objects != null) {
							SwingUtilities.invokeLater(new EDTSO_Pr_Br(
									(ArrayList) objects, No, "Справки " + title,
									destination));
							No = ""; // clear current number
						}
					}
				});
				break;
			case PROTOKOL:
				title = Protokol_Title;


				getReportsService.getProtokols(optionsParam, new RequestCallback() {
					@Override
					public <T> void callback(List<T> objects) {
						jDialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

						if(objects != null) {
							SwingUtilities.invokeLater(new EDTSO_Pr_Br(
									(ArrayList) objects, No, "Справки " + title,
									destination));
							No = ""; // clear current number
						}
					}
				});
				break;
			case BRACK:
				title = Brack_Title;

				getReportsService.getBrack(optionsParam, new RequestCallback() {
					@Override
					public <T> void callback(List<T> objects) {

						jDialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

						if(objects != null) {
							SwingUtilities.invokeLater(new EDTSO_Pr_Br(
									(ArrayList) objects, No, "Справки " + title,
									destination));
							No = ""; // clear current number
						}
					}
				});
				break;
			default:
				break;
		}

	}

	//
	private String openAcquittance(JDialog jDialog) {
		HashMap<String, String> optionParam = new HashMap<>();

		if (clientCombo.getSelectedItem() != null && !clientCombo.getSelectedItem().equals("")) {
			optionParam.put("client",clientCombo.getSelectedItem().toString());
		}
		if (!acquittanceField.getText().isEmpty()) {
			optionParam.put("acquittance",acquittanceField.getText());
		}
		// add date
		if (!fromDate.getText().isEmpty() && !toDate.getText().isEmpty()) {
			optionParam.put("fromDate",fromDate.getText());
			optionParam.put("toDate",toDate.getText());
		}
		if (artikulsComboBox.getSelectedItem() != null && !artikulsComboBox.getSelectedItem().toString().equals("")) {
			optionParam.put("artikul",artikulsComboBox.getSelectedItem().toString());
		}
		GetReportsService service = new GetReportsService();
		service.getAcquittance(optionParam, new RequestCallback() {
			@Override
			public <T> void callback(List<T> objects) {
				EDTAcquitance edt = new EDTAcquitance((ArrayList<Object[]>) objects,
						jDialog, "Справки Стокова Разписка");
				SwingUtilities.invokeLater(edt);
			}
		});
		return null;
	}


	private void openInvoices(String invoiceTitle, JDialog jDialog) {
		HashMap<String, String> optionsParam = new HashMap<>();

		if (!clientCombo.getSelectedItem().equals("")) {
			optionsParam.put("client",clientCombo.getSelectedItem().toString());
		}
		if (!fact_field.getText().isEmpty()) {
			optionsParam.put("invoice",fact_field.getText());
		}
		// add date
		if (!fromDate.getText().isEmpty() && !toDate.getText().isEmpty()) {
			optionsParam.put("fromDate",fromDate.getText());
			optionsParam.put("toDate", toDate.getText());
		}
		if (!artikulsComboBox.getSelectedItem().toString().equals("")) {
			optionsParam.put("artikul",artikulsComboBox.getSelectedItem().toString());
		}

		GetReportsService service = new GetReportsService();
		if(invoiceTitle.equals("Фактура")) {
			service.getInvoices(optionsParam, new RequestCallback() {
				@Override
				public <T> void callback(List<T> objects) {
					jDialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					if(objects != null) {
						SwingUtilities
								.invokeLater(new EDTInvoice((ArrayList) objects,
										No, "Справки " + invoiceTitle));
						No = "";
					}

				}
			});
		} else {

			service.getProforms(optionsParam, new RequestCallback() {
				@Override
				public <T> void callback(List<T> objects) {
					jDialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					if(objects != null) {
						SwingUtilities
								.invokeLater(new EDTInvoice((ArrayList) objects,
										No, "Справки " + invoiceTitle));
						No = "";
					}
				}
			});
		}

	}


	private void openDelivery(JDialog jDialog) {

		HashMap<String, String> optionsParam = new HashMap<>();


		if (clientCombo.getSelectedItem() != null && !clientCombo.getSelectedItem().equals("")) {

			optionsParam.put("kontragent",clientCombo.getSelectedItem().toString());
		}
		if (!fact_field.getText().isEmpty()) {

			optionsParam.put("invoiceByKontragent",fact_field.getText());
		}
		if (artikulsComboBox.getSelectedItem() != null && !artikulsComboBox.getSelectedItem().toString().equals("")) {

			optionsParam.put("artikul",artikulsComboBox.getSelectedItem().toString());
		}
		if (!fromDate.getText().isEmpty() && !toDate.getText().isEmpty()) {

			optionsParam.put("fromDate",fromDate.getText());
			optionsParam.put("toDate",toDate.getText());
		}

        GetReportsService service = new GetReportsService();
		service.getDeliveries(optionsParam, new RequestCallback() {
			@Override
			public <T> void callback(List<T> objects) {

				if(objects != null) {
					EDTDelivery edt = new EDTDelivery((ArrayList) objects,
							jDialog, "Справка Доставки "
							+ fromDate.getText() + " - "
							+ toDate.getText());
					SwingUtilities.invokeLater(edt);
				}
			}
		});
	}

	private ArrayList<DeliveryReports> deliveryDataForSales;
	private ArrayList<InvoiceModel> invoiceDataForSales;

	private void openSales(String fromDate, String toDate, JDialog jDialog) {
		deliveryDataForSales = null;
		invoiceDataForSales = null;
          GetReportsService service = new GetReportsService();
		HashMap<String, String> optionsParam = new HashMap<>();
		optionsParam.put("fromDate",fromDate);
		optionsParam.put("toDate",toDate);

		if(artikulsComboBox.getSelectedItem() != null && !artikulsComboBox.getSelectedItem().toString().isEmpty()) {
			optionsParam.put("artikul",artikulsComboBox.getSelectedItem().toString());
		}
		service.getDeliveryDataForSale(optionsParam, new RequestCallback() {
			@Override
			public <T> void callback(List<T> objects) {
				deliveryDataForSales = (ArrayList<DeliveryReports>) objects;
				if(deliveryDataForSales != null && invoiceDataForSales != null) {
					EDTSales edt = new EDTSales(invoiceDataForSales,
							deliveryDataForSales, jDialog, "Справка Продажби "
							+ fromDate + " - "
							+ toDate);
					SwingUtilities.invokeLater(edt);
				}
			}
		});
		service.getInvoiceDataForSale(optionsParam, new RequestCallback() {
			@Override
			public <T> void callback(List<T> objects) {
				invoiceDataForSales = (ArrayList<InvoiceModel>) objects;
				if(deliveryDataForSales != null && invoiceDataForSales != null) {
					EDTSales edt = new EDTSales(invoiceDataForSales,
							deliveryDataForSales, jDialog, "Справка Продажби "
							+ fromDate + " - "
							+ toDate);
					SwingUtilities.invokeLater(edt);
				}
			}
		});
	}

	List<DeliveryReports> deliveryDataForAvailabilityBeforeSelectedDate;
	List<DeliveryReports> deliveryDataForAvailabilityBetweenSelectedDates;
	List<InvoiceModel> invoiceDataForAvailabilityReportsBeforeSelectedDates;

	List<InvoiceModel> invoiceDataForAvailabilityReportsBetweenSelectedDates;

	private void openAvailability(String from, String to, JDialog jDialog) {

		deliveryDataForAvailabilityBeforeSelectedDate = null;
		deliveryDataForAvailabilityBetweenSelectedDates = null;
		invoiceDataForAvailabilityReportsBeforeSelectedDates = null;
		invoiceDataForAvailabilityReportsBetweenSelectedDates = null;

		String oneDayBeforeForm = MyGetDate.getDateBeforeAnotherDate(1,
				MyGetDate.getDateFromString(fromDate
						.getText()));

		HashMap<String, String> optionsParam = new HashMap<>();
		optionsParam.put("fromDate","01.06.2016");
		optionsParam.put("toDate",oneDayBeforeForm);

		if(artikulsComboBox.getSelectedItem() != null && !artikulsComboBox.getSelectedItem().toString().isEmpty()) {
			optionsParam.put("artikul",artikulsComboBox.getSelectedItem().toString());
		}

		GetReportsService service = new GetReportsService();
		service.getDeliveryDataForAvailability(optionsParam, new RequestCallback() {
			@Override
			public <T> void callback(List<T> objects) {
				deliveryDataForAvailabilityBeforeSelectedDate = (List<DeliveryReports>) objects;
				if(deliveryDataForAvailabilityBeforeSelectedDate != null
						&& deliveryDataForAvailabilityBetweenSelectedDates != null
						&& invoiceDataForAvailabilityReportsBeforeSelectedDates != null
						&& invoiceDataForAvailabilityReportsBetweenSelectedDates != null) {
					EDTAvailability edt = new EDTAvailability(
							(ArrayList) deliveryDataForAvailabilityBeforeSelectedDate,
							(ArrayList) invoiceDataForAvailabilityReportsBeforeSelectedDates,
							(ArrayList) deliveryDataForAvailabilityBetweenSelectedDates,
							(ArrayList) invoiceDataForAvailabilityReportsBetweenSelectedDates, "01.06.2016", oneDayBeforeForm,
							jDialog, "Справка Наличност " + oneDayBeforeForm
							+ " - " + to);
					SwingUtilities.invokeLater(edt);
				}
			}
		});
		service.getInvoiceDataForAvailability(optionsParam, new RequestCallback() {
			@Override
			public <T> void callback(List<T> objects) {
				invoiceDataForAvailabilityReportsBeforeSelectedDates = (List<InvoiceModel>) objects;
				if(deliveryDataForAvailabilityBeforeSelectedDate != null
						&& deliveryDataForAvailabilityBetweenSelectedDates != null
						&& invoiceDataForAvailabilityReportsBeforeSelectedDates != null
						&& invoiceDataForAvailabilityReportsBetweenSelectedDates != null) {
					EDTAvailability edt = new EDTAvailability(
							(ArrayList) deliveryDataForAvailabilityBeforeSelectedDate,
							(ArrayList) invoiceDataForAvailabilityReportsBeforeSelectedDates,
							(ArrayList) deliveryDataForAvailabilityBetweenSelectedDates,
							(ArrayList) invoiceDataForAvailabilityReportsBetweenSelectedDates, "01.06.2016", oneDayBeforeForm,
							jDialog, "Справка Наличност " + oneDayBeforeForm
							+ " - " + to);
					SwingUtilities.invokeLater(edt);
				}
			}
		});

		optionsParam.clear();
		optionsParam.put("fromDate",from);
		optionsParam.put("toDate",to);
		if(artikulsComboBox.getSelectedItem() != null && !artikulsComboBox.getSelectedItem().toString().isEmpty()) {
			optionsParam.put("artikul",artikulsComboBox.getSelectedItem().toString());
		}
		service.getDeliveryDataForAvailability(optionsParam, new RequestCallback() {
			@Override
			public <T> void callback(List<T> objects) {
				deliveryDataForAvailabilityBetweenSelectedDates = (List<DeliveryReports>) objects;
				if(deliveryDataForAvailabilityBeforeSelectedDate != null
						&& deliveryDataForAvailabilityBetweenSelectedDates != null
						&& invoiceDataForAvailabilityReportsBeforeSelectedDates != null
						&& invoiceDataForAvailabilityReportsBetweenSelectedDates != null) {
					EDTAvailability edt = new EDTAvailability(
							(ArrayList) deliveryDataForAvailabilityBeforeSelectedDate,
							(ArrayList) invoiceDataForAvailabilityReportsBeforeSelectedDates,
							(ArrayList) deliveryDataForAvailabilityBetweenSelectedDates,
							(ArrayList) invoiceDataForAvailabilityReportsBetweenSelectedDates, from, to,
							jDialog, "Справка Наличност " + oneDayBeforeForm
							+ " - " + to);
					SwingUtilities.invokeLater(edt);
				}
			}
		});
		service.getInvoiceDataForAvailability(optionsParam, new RequestCallback() {
			@Override
			public <T> void callback(List<T> objects) {
				invoiceDataForAvailabilityReportsBetweenSelectedDates = (List<InvoiceModel>) objects;
				if(deliveryDataForAvailabilityBeforeSelectedDate != null
						&& deliveryDataForAvailabilityBetweenSelectedDates != null
						&& invoiceDataForAvailabilityReportsBeforeSelectedDates != null
						&& invoiceDataForAvailabilityReportsBetweenSelectedDates != null) {
					EDTAvailability edt = new EDTAvailability(
							(ArrayList) deliveryDataForAvailabilityBeforeSelectedDate,
							(ArrayList) invoiceDataForAvailabilityReportsBeforeSelectedDates,
							(ArrayList) deliveryDataForAvailabilityBetweenSelectedDates,
							(ArrayList) invoiceDataForAvailabilityReportsBetweenSelectedDates, from, to,
							jDialog, "Справка Наличност " + oneDayBeforeForm
							+ " - " + to);
					SwingUtilities.invokeLater(edt);
				}
			}
		});

	}

	private void openCreditNotes(JDialog jDialog) {
		GetReportsService service = new GetReportsService();
		HashMap<String, String> optionsParam = new HashMap<>();
		if (!fact_field.getText().isEmpty()) {
			optionsParam.put("invoice",fact_field.getText());
		}
		service.getCreditNotes(optionsParam, new RequestCallback() {
			@Override
			public <T> void callback(List<T> objects) {
				jDialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

				List<CreditNoteReports> creditNotes = (List<CreditNoteReports>) objects;
				EDTCreditNote edtCreditNote = new EDTCreditNote((ArrayList) creditNotes,"Кредитно Известие");
				SwingUtilities.invokeLater(edtCreditNote);
			}
		});
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
}
