package WorkingBook;

import Parts.Dust;
import Parts.ReasonsBrack;
import Parts.Vodopenen;
import Parts.Water;
import WorkingBook.Renderers.MyTableRenderer;
import WorkingBookWorkers.GetAgentFitWorker;
import db.Common;
import db.GetFromScanner;
import mydate.MyGetDate;
import run.JDialoger;
import run.JustFrame;
import utility.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class WorkingBook extends MainPanel {

	private final EditableField readBarcod;

	private final EditableField serialNumber;

	private JComboBox<Object> comboDoings = null;

	private final JComboBox<Object> jComboBox2;

	private TooltipButton penButton = null;

	private BevelLabel HI_Label = null;
	private BevelLabel fitAgentLabel = null;
	private JPanel centerCenter = null;

	final static String DUST = "Прахов";

	final static String WATER = "Воден";

	final static String VODOPENEN = "Водопенен";

	final static String SCRAB = "БРАК";

	final static String CO2 = "CO2";

	private final Dust dust;

	private final Water water;

	private final Vodopenen vodopenen;

	private final Parts.CO2 co2;

	public static DefaultTableModel tModel = null;

	public static JTable table = null;

	private BevelLabel clientLabel = null;

	private Object[] fromBarcod = null;

	private Object[] fromSerial = null;

	private final HashSet<Object> isBarcodAndSerialEntered = new HashSet<Object>();

	public static HashMap<String, ArrayList<Object>> ext_parts = new HashMap<>();

	public static HashMap<String, ArrayList<Object>> reasons_map = new HashMap<String, ArrayList<Object>>();

	public static HashMap<String, Double> value_map = new HashMap<String, Double>();

	ArrayList<Object> currValues = new ArrayList<Object>();

	public static String CURRENT_CLIENT;

	public static double HI_PRICE = 0; // loaded in front components panel
	public static double TO_PRICE = 0;
	// private final int southHeight = 200;


	public WorkingBook() {
		CURRENT_CLIENT = "";

		JPanel north = new JPanel();

		north.setPreferredSize(new Dimension((int) (this.WIDTH * 1.0) - 20,
				(int) (this.HEIGHT * 0.09)));

		north.setLayout(new GridLayout(0, 1, 2, 2));

		JPanel block = new JPanel();

		block.setLayout(new BorderLayout());

		currValues = new ArrayList<Object>(); // -> get parts and price

		readBarcod = new EditableField("баркод", 10);

		readBarcod.setPreferredSize(new Dimension((int) (north
				.getPreferredSize().getWidth() * 0.15), (int) (north
				.getPreferredSize().getHeight() * 0.7)));

		readBarcod.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				// check if we save data from previous action

				if (comboDoings.isEnabled()) {

					JOptionPane
							.showMessageDialog(null, "Има незаписани данни!");

					readBarcod.setText("");

					return;
				}

				if (!isBarcodAndSerialEntered.contains(readBarcod.getText())) {

					SwingWorker<Boolean, Void> sw = new SwingWorker<Boolean, Void>() {

						@SuppressWarnings("finally")
						@Override
						protected Boolean doInBackground() throws Exception {
							// TODO Auto-generated method stub
							try {
								fromBarcod = GetFromScanner
										.getBarcodFromServiceTableDB3(readBarcod
												.getText());
							} finally {
								SwingUtilities.invokeLater(new Runnable() {

									@Override
									public void run() {
										// TODO Auto-generated method stub
										// check if entered clients are same
										if (fromBarcod != null) {
											if (fromBarcod.length > 0) {

												// check client
												if (!checkClient(fromBarcod[0]
														.toString())) {
													return;
												}

												// for (int i = 0; i <
												// fromBarcod.length; i++) {
												// System.out.printf("%s ",
												// fromBarcod[i]);
												// }
												// System.out.println();
												for (int i = 0; i <= 6; i++) {
													tModel.setValueAt(
															fromBarcod[i], 0, i);
												}

												// set real wheigths in hidden
												// column
												// to write it in db
												tModel.setValueAt(
														fromBarcod[2], 0, 10);
												// set допълнителни данни
												tModel.setValueAt(
														fromBarcod[10], 0, 11);

												HI_Label.setForeground(fromBarcod[9].equals("не") ? Color.BLACK : Color.RED);
												HI_Label.setName(fromBarcod[9]
														.toString());

												comboDoings.setEnabled(true);
												comboDoings.setSelectedIndex(0);

												isBarcodAndSerialEntered
														.add(fromBarcod[3]);

												isBarcodAndSerialEntered
														.add(fromBarcod[4]);

												clientLabel
														.setName(CURRENT_CLIENT);

												switchPanel(fromBarcod[1]); // type

											} else {
												JOptionPane
														.showMessageDialog(
																null,
																"Не е намерен такъв елемент!");
											}
										}
										readBarcod.setText("");

									}

								});


							}
							return true;
						}
					};
					sw.execute();
				} else {

					JOptionPane.showMessageDialog(null,
							"Този номер вече е въведен!");

					readBarcod.setText("");
				}
			}

		});
		serialNumber = new EditableField("сериен " + MainPanel.numeroSign, 8);

		serialNumber.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (comboDoings.isEnabled()) {

					JOptionPane
							.showMessageDialog(null, "Има незаписани данни!");

					serialNumber.setText("");

					return;
				}
				if (!isBarcodAndSerialEntered.contains(serialNumber.getText())) {

					SwingWorker<Boolean, Void> sw = new SwingWorker<Boolean, Void>() {

						@SuppressWarnings("finally")
						@Override
						protected Boolean doInBackground() throws Exception {
							// TODO Auto-generated method stub
							try {
								fromSerial = GetFromScanner
										.getSerialFromServiceTableDB3(serialNumber
												.getText());

								// check if has hydrostatic examination

							} finally {
								SwingUtilities.invokeLater(new Runnable() {

									@Override
									public void run() {
										// TODO Auto-generated method stub
										// check if entered clients are same
										if (fromSerial != null) {
											if (fromSerial.length > 0) {

												if (!checkClient(fromSerial[0]
														.toString())) {
													return;
												}

												for (int i = 0; i <= 6; i++) {
													tModel.setValueAt(
															fromSerial[i], 0, i);
												}

												// set real wheights in hidden
												// column
												// to write it in db
												tModel.setValueAt(
														fromSerial[2], 0, 10);

												// set допълнителни данни
												tModel.setValueAt(
														fromSerial[10], 0, 11);

												HI_Label.setForeground(fromSerial[9].equals("не") ? Color.BLACK : Color.RED);
												HI_Label.setName(fromSerial[9]
														.toString());

												comboDoings.setEnabled(true);
												comboDoings.setSelectedIndex(0);

												isBarcodAndSerialEntered
														.add(fromSerial[3]);// barcod

												isBarcodAndSerialEntered
														.add(fromSerial[4]);// serial

												clientLabel
														.setName(CURRENT_CLIENT);

												switchPanel(fromSerial[1]); // type

												comboDoings.setSelectedIndex(0);
											} else {
												JOptionPane
														.showMessageDialog(
																null,
																"Не е намерен такъв елемент!");
											}
										}
										serialNumber.setText("");
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
				}
			}

		});
		serialNumber.setPreferredSize(new Dimension((int) (north
				.getPreferredSize().getWidth() * 0.11), (int) (north
				.getPreferredSize().getHeight() * 0.7)));

		JPanel pane1 = new JPanel();// GradientPanel();
		pane1.setLayout(new FlowLayout(FlowLayout.LEFT));

		pane1.add(readBarcod);

		pane1.add(serialNumber);


		Object[] doings = { "Обслужване", ТО, ХИ, ТО_П, ТО_П_ХИ, Брак };
		DefaultComboBoxModel<Object> cbm = new DefaultComboBoxModel<Object>(doings);
		comboDoings = new JComboBox<Object>(cbm);
		comboDoings.setEnabled(false);
		comboDoings.setPreferredSize(new Dimension((int) (north
				.getPreferredSize().getWidth() * 0.12), (int) (north
				.getPreferredSize().getHeight() * 0.7)));

		comboDoings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

               String doing = comboDoings.getSelectedItem().toString();
                String type = tModel.getValueAt(0, 1).toString();
                String wheight = tModel.getValueAt(0, 2).toString();
                String category = tModel.getValueAt(0, 5).toString();

                if (doing.equals("Обслужване")) {
                    return;
                }
                if (doing.equals(Брак)) {
                    CardLayout cl = (CardLayout) (centerCenter.getLayout());
                    cl.show(centerCenter, SCRAB);
                } else {
                    setButtonState2(doing, type, wheight, category);
                }

                // set value doings int table
				  setNewDate(doing, type);
             //   setNewDate2(comboDoings.getSelectedItem().toString(), tModel.getValueAt(0,1).toString());
                penButton.setEnabled(true);
			}
		});

        final Object[] jComboBoxItems = {"носим","возим"};
        DefaultComboBoxModel<Object> defaultComboBoxModel = new DefaultComboBoxModel<Object>(jComboBoxItems);
        jComboBox2 = new JComboBox<>(defaultComboBoxModel);
        jComboBox2.setPreferredSize(new Dimension((int) jComboBox2.getPreferredSize().getWidth(), (int) (north
                .getPreferredSize().getHeight() * 0.7)));


		penButton = new TooltipButton();
		// penButton.setIcon(setIcons(penImage));
		// penButton.setPreferredSize(new Dimension(55,55));
		penButton.setEnabled(false);

		penButton.setToolTipText(getHTML_Text("ЗАПИШИ В ОБРАБОТЕНИ"));
		penButton.setPreferredSize(new Dimension((int) (north
				.getPreferredSize().getWidth() * 0.045), (int) (north
				.getPreferredSize().getHeight() * 0.85)));

		penButton.setAutoSizedIcon(penButton, new LoadIcon().setIcons(penImage));

		penButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				// check if entered wheight is valid !!!

				String wheight_kg = tModel.getValueAt(0, 2).toString();// .trim().split("\\s+");
				try {
					wheight_kg = wheight_kg.replace(",", ".");
					Double.parseDouble(wheight_kg);// (wheight_kg[0]);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,
							"Невалидна маса на пожарогасител");
					return;
				}

				int yes_no = JOptionPane.showOptionDialog(null,
						"Желаете ли да съхраните въведените данни?", "",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, new String[] {
								"Да", "Не" }, // this is the array
						"default");
				if (yes_no == 0) {

					// write in view table model
					String key = getExtinguisher(); // get current extinguisher
					// get value
					if (!value_map.containsKey(key)) {
						value_map.put(key, getExtValue());
						// test all value
						// System.out.println("all value = " + getExtValue());
					}
					writeInTableModel(key, getUserDetailsInput()); // write in
																	// table
																	// (->View)
					returnPreviousButtonState(false);//
					clear();

				}

			}
		});



		int labelWidth = (int) (north.getPreferredSize().getWidth() * 0.4);
		int labelHeight = (int) (north.getPreferredSize().getHeight() * 0.4);
		HI_Label = new BevelLabel();
		HI_Label.setPreferredSize(new Dimension(labelWidth, labelHeight));
		HI_Label.setTitle("ХИДРОСТАТИЧНО ИЗПИТВАНЕ : ");
		// HI_Label.setAutoSizedIcon(HI_Label, setIcons(reportsImage));
		HI_Label.setName("");

		// годност на пожарогасително вещество
		fitAgentLabel = new BevelLabel();
		fitAgentLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
		fitAgentLabel.setTitle("ГОДНОСТ НА ГАСИТЕЛНО ВЕЩЕСТВО : ");
		// HI_Label.setAutoSizedIcon(HI_Label, setIcons(reportsImage));
		fitAgentLabel.setName("");

        pane1.add(comboDoings);//attentionButton); // set reports

        pane1.add(jComboBox2); // set combo doing choice

		pane1.add(penButton); // set db button

		JPanel helpPanel = new JPanel();
		helpPanel.setLayout(new BorderLayout());
		helpPanel.add(HI_Label, BorderLayout.NORTH);
		helpPanel.add(fitAgentLabel,BorderLayout.SOUTH);

		pane1.add(helpPanel); // set HI checker

		north.add(pane1);

		JPanel center = new JPanel();

		center.setLayout(new BorderLayout());

		Object[] titles = { "Клиент", "Вид", "Маса", "Баркод",
				"Монтажен номер", "Категория", "Марка", "Техн. Обслужване",
				"Презареждане", "Хидр. Изпитване",
				"Реална маса", "Допълнителни данни" };

		Object[][] rows = { // { "", "", "", "", "", "", "", "","","" },
		{ "", "", "", "", "", "", "", "", "", "", "", "" } };

		tModel = new DefaultTableModel(rows, titles) {

			// private static final long serialVersionUID = 1L;

			@Override
			public void setValueAt(Object value, int row, int column) {
				// iztrivam kg ili litri za da ne se vijdat
				// che gi zatrudniavat !!!
				if (value == null) {
					return;
				}
				String newValue = value.toString();
				if (column == 2) {
					if (newValue.contains("кг")) {
						newValue = newValue.replace("кг", "");
					}
					if (newValue.contains("литра")) {
						newValue = newValue.replace("литра", "");
					}
				}
				super.setValueAt(newValue, row, column);
				fireTableCellUpdated(row, column);
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 2 || column == 6 || column == 7
						|| column == 8 || column == 9;
			}
		};
		table = new JTable(tModel);

		table.removeColumn(table.getColumn("Реална маса"));
		table.removeColumn(table.getColumn("Допълнителни данни"));

		table.setDefaultRenderer(Object.class, new MyTableRenderer(table));
		table.setRowHeight(Common.getFontSize() + 15);
		// table.getColumnModel().getColumn(2).setCellEditor(
		// new Work_TableCellEditor(new CustomTextField()));

		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

		JScrollPane scroll = new JScrollPane(table);

		scroll.setPreferredSize(new Dimension(this.WIDTH - 20,
				(int) (this.HEIGHT * 0.1)));

		centerCenter = new JPanel();

		Dimension dim = new Dimension(this.WIDTH - 20,
				(int) (this.HEIGHT * 0.57));
		// this.HEIGHT -
		// north.getPreferredSize().height -
		// scroll.getPreferredSize().height - southHeight);

		centerCenter.setPreferredSize(dim);

		dust = new Dust(dim);

		water = new Water(dim);

		vodopenen = new Vodopenen(dim);

		co2 = new Parts.CO2(dim);

		ReasonsBrack scrab = new ReasonsBrack();
		CardLayout card = new CardLayout();

		centerCenter.setLayout(card);

		// set name of cards
		dust.setName("dust");

		water.setName("water");

		vodopenen.setName("vodopenen");

		co2.setName("co2");

		scrab.setName("scrab");

		centerCenter.add(dust, DUST);

		centerCenter.add(water, WATER);

		centerCenter.add(vodopenen, VODOPENEN);

		centerCenter.add(co2, CO2);

		centerCenter.add(scrab, SCRAB);

		center.add(scroll, BorderLayout.NORTH);

		center.add(centerCenter, BorderLayout.CENTER);

		JPanel south = new JPanel();// GradientPanel();

		south.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 5));

		south.setPreferredSize(new Dimension((int) (this.WIDTH * 1.0) - 20,
				(int) (this.HEIGHT * 0.09)));// southHeight -100) );
		// setSizeOfSouthPanel(centerCenter.getPreferredSize().height);

		int labHeight = (int) (north.getPreferredSize().getHeight() * 0.9);
		BevelLabel tehnikLabel = new BevelLabel(labHeight);

		tehnikLabel.setTitle(Enums.Оператор.name() +  ": ");
		tehnikLabel.setName(personName);

		clientLabel = new BevelLabel(labHeight);

		clientLabel.setTitle("  Клиент : ");
		clientLabel.setName("");

		BevelLabel dateLabel = new BevelLabel(labHeight);

		dateLabel.setTitle("  Дата :  ");
		dateLabel.setName(MyGetDate.getReversedSystemDate());

		south.add(tehnikLabel);

		south.add(clientLabel);

		south.add(dateLabel);

		block.add(north, BorderLayout.NORTH);

		block.add(center, BorderLayout.CENTER);

		block.add(south, BorderLayout.SOUTH);

		block.setBorder(BorderFactory.createLineBorder(Color.black));

		this.add(block);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JustFrame f = new JustFrame(new WorkingBook());
	}

	private void switchPanel(Object type) {
		// int width = 0;
		int height = 0;

		CardLayout cl = (CardLayout) (centerCenter.getLayout());

		String command = "";
		if (type.toString().contains(type_Prah_BC)
				|| type.toString().contains(type_Prah_ABC)) {
			command = DUST;
			height = dust.getPreferredSize().height;
		} else if (type.toString().contains(type_Water)) {
			command = WATER;
			height = water.getPreferredSize().height;
		} else if (type.toString().contains(type_Water_Fame)) {
			command = VODOPENEN;
			height = vodopenen.getPreferredSize().height;
		} else if (type.toString().contains(type_CO2)) {
			command = CO2;
			height = co2.getPreferredSize().height;
		}
		cl.show(centerCenter, command);

	}

	private void clear() {
		for (int i = 0; i <= 10; i++) {
			tModel.setValueAt("", 0, i);
		}
		comboDoings.setSelectedIndex(0);
		comboDoings.setEnabled(false);

		penButton.setEnabled(false);

	}

	void writeInTableModel(String key, ArrayList<Object> userChoice) {

		Object[] newRow = new Object[12]; // -> 12? this is row to be inserted
											// in
											// models
		newRow[0] = tModel.getValueAt(0, 0);
		newRow[1] = tModel.getValueAt(0, 1);
		newRow[2] = tModel.getValueAt(0, 2).toString().replace(",", ".");
		if (newRow[1].toString().toLowerCase().contains("воден")
				|| newRow[1].toString().toLowerCase().contains("водопенен")) {
			newRow[2] += " литра";
		} else {
			newRow[2] += " кг";
		}
		newRow[3] = tModel.getValueAt(0, 3);
		newRow[4] = tModel.getValueAt(0, 4);
		newRow[5] = tModel.getValueAt(0, 5);
		newRow[6] = tModel.getValueAt(0, 6) + " , " + jComboBox2.getSelectedItem();;
		newRow[7] = tModel.getValueAt(0, 7);
		newRow[8] = tModel.getValueAt(0, 8);
		newRow[9] = tModel.getValueAt(0, 9);
		newRow[10] = tModel.getValueAt(0, 10);
		newRow[11] = tModel.getValueAt(0, 11);
		// for (int i = 0; i <= 10; i++) {
		// newRow[i] = tModel.getValueAt(0, i);
		// }

		if (!comboDoings.getSelectedItem().equals(Брак)) {
			// get parts values and work values

			if (!ext_parts.containsKey(key)) {
				ext_parts.put(key, userChoice);// this is spare parts
												// of choiced
												// extinguisher
			}
			View.dtm_Extinguisher.insertRow(0, newRow); // add to view models

			// call sticker jdialog to choice sticker
			boolean TO = comboDoings.getSelectedItem().toString().contains(ТО);// get from TO date -> !tModel.getValueAt(0,
							// 7).toString().equals("не") ? true: false;

			boolean P = comboDoings.getSelectedItem().toString().contains(П);// get from TO date -> !tModel.getValueAt(0,
							// 8).toString().equals("не") ? true : false;

			boolean HI = comboDoings.getSelectedItem().toString().contains(ХИ);// get from TO date -> !tModel.getValueAt(0,
							// 9).toString().equals("не") ? true : false;

			if (TO || P || HI) { // without HI ????
				final JDialoger jdialog = new JDialoger();
				StickerJDialog sjd = new StickerJDialog(jdialog, TO, P, HI,
						tModel.getValueAt(0,7).toString(),// Дата на следващо техническо обслужване
						tModel.getValueAt(0,8).toString(),// Дата на следващо презареждане
						tModel.getValueAt(0,9).toString(),// Дата на следващо Хидростатично изпитване
						tModel.getValueAt(0, 3).toString());
				jdialog.setContentPane(sjd);
				jdialog.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent we) {
						int yes_no = JOptionPane
								.showOptionDialog(
										null,
										"Желаете ли да затворите диалоговия прозорец ?",
										"", JOptionPane.YES_NO_OPTION,
										JOptionPane.QUESTION_MESSAGE, null,
										new String[] { "Да", "Не" }, // this is
																		// the
																		// array
										"default");
						if (yes_no == 0) {
							jdialog.dispose();
						} else {
							jdialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
						}

					}
				});
				jdialog.Show();


//				final JDialoger jdialog = new JDialoger();
//				final StickerJDialogOldVersion sjd =
//						new StickerJDialogOldVersion(jdialog, TO, P, HI,
//								WorkingBook.tModel.getValueAt(0, 3).toString());
//				jdialog.setContentPane(sjd);
//				jdialog.addWindowListener(new WindowAdapter() {
//					@Override
//					public void windowClosing(final WindowEvent we) {
//						final int yes_no = JOptionPane.showOptionDialog(null, "Желаете ли да затворите диалоговият прозорец ?",
//								"", JOptionPane.YES_NO_OPTION,
//								JOptionPane.WARNING_MESSAGE, null, new String[] { "Да", "Не" }, "default");
//						if (yes_no == 0) {
//							jdialog.dispose();
//						} else {
//							jdialog.setDefaultCloseOperation(0);
//						}
//					}
//				});
//				jdialog.Show();
			}

		} else {

			if (!reasons_map.containsKey(key)) {
				reasons_map.put(key, userChoice); // this is reasons
													// of scrab of
													// extinguisher
			}
			Brack.dtm_Scrab.insertRow(0, newRow); // add to scrab models
		}
	}

	private ArrayList<Object> getUserDetailsInput() {
		ArrayList<Object> parts = new ArrayList<Object>();
		ArrayList<Object> reasons = new ArrayList<Object>();

		if (!comboDoings.getSelectedItem().equals(Брак)) {

			Component[] child = centerCenter.getComponents();
			ArrayList<CustomButton> list = null;

			for (Component component : child) {

				if (component.getName().equals("dust") && component.isShowing()) {
					list = dust.list;
					for (CustomButton cm : list) {

						if (cm.choiced) {
							parts.add(cm.getName());
						}
					}
				} else if (component.getName().equals("water")
						&& component.isShowing()) {
					list = water.list;
					for (CustomButton cm : list) {
						if (cm.choiced) {
							parts.add(cm.getName());
						}
					}
				} else if (component.getName().equals("vodopenen")
						&& component.isShowing()) {
					list = vodopenen.list;
					for (CustomButton cm : list) {
						if (cm.choiced) {
							parts.add(cm.getName());
						}
					}
				} else if (component.getName().equals("co2")
						&& component.isShowing()) {
					list = co2.list;
					for (CustomButton cm : list) {
						if (cm.choiced) {
							parts.add(cm.getName());
						}
					}
				}
			}

			return parts;
		} else {
			for (int row = 0; row < ReasonsBrack.boolReasons.length; row++) {
				if (ReasonsBrack.boolReasons[row]) {
					reasons.add(ReasonsBrack.table.getValueAt(row, 0)
							.toString());
				}
			}
			return reasons;
		}
	}

	public String getExtinguisher() {
		return tModel.getValueAt(0, 3).toString();
	}

	private void setButtonState2(String doing, String type, String wheight,
			String category) {
		boolean isTO = comboDoings.getSelectedItem().equals(ТО);
		switch (doing) {
			case ХИ:
				returnPreviousButtonState(false);
				break;
			case ТО:
				returnPreviousButtonState(true);
				WorkerState.setPlomba(type, isTO);
				WorkerState.setEntity(type, false);
				WorkerState.setButtonStateAccordinglyCategory(type, wheight,
						category);
				break;
			case ТО_П:
			case ТО_П_ХИ:
				returnPreviousButtonState(true);
				WorkerState.setPlomba(type, isTO);
				WorkerState.setEntity(type, true);
				WorkerState.setButtonStateAccordinglyCategory(type, wheight,
						category);
				break;
		}
	}

	private double getExtValue() {
		/*
		 * String item = comboDoings.getSelectedItem().toString(); double value
		 * = 0.0; if (item.contains(ТО)) { value += TO_PRICE; } if
		 * (item.contains(ХИ)) { value += HI_PRICE; } Component[] child =
		 * centerCenter.getComponents(); ArrayList<CustomButton> list = null;
		 * 
		 * for (int ch = 0; ch < child.length; ch++) {
		 * 
		 * if (child[ch].getName().equals("dust") && child[ch].isShowing()) {
		 * 
		 * list = dust.list; for (CustomButton cm : list) {
		 * 
		 * if (cm.choiced == true) {
		 * 
		 * value += cm.getPriceofPartsFromDB(tModel);
		 * 
		 * }
		 * 
		 * } } else if (child[ch].getName().equals("water") &&
		 * child[ch].isShowing()) { list = water.list; for (CustomButton cm :
		 * list) { if (cm.choiced == true) {
		 * 
		 * value += cm.getPriceofPartsFromDB(tModel);
		 * 
		 * } } } else if (child[ch].getName().equals("vodopenen") &&
		 * child[ch].isShowing()) { list = vodopenen.list; for (CustomButton cm
		 * : list) { if (cm.choiced == true) {
		 * 
		 * value += cm.getPriceofPartsFromDB(tModel); } } } else if
		 * (child[ch].getName().equals("co2") && child[ch].isShowing()) { list =
		 * co2.list; for (CustomButton cm : list) { if (cm.choiced == true) {
		 * 
		 * 
		 * value += cm.getPriceofPartsFromDB(tModel); } } } } return
		 * MyMath.round(value, 2);
		 */
		return 0;
	}

	private void returnPreviousButtonState(boolean enabled) {

		if (!comboDoings.getSelectedItem().equals(Брак)) {

			Component[] child = centerCenter.getComponents();
			ArrayList<CustomButton> list = null;

			for (Component component : child) {

				if (component.getName().equals("dust") && component.isShowing()) {
					list = dust.list;
					for (CustomButton cm : list) {
						cm.choiced = false;
						cm.setBorder(cm.defaultBorder);
						cm.setEnabled(enabled);
					}
				} else if (component.getName().equals("water")
						&& component.isShowing()) {
					list = water.list;
					for (CustomButton cm : list) {
						cm.choiced = false;
						cm.setBorder(cm.defaultBorder);
						cm.setEnabled(enabled);
					}
				} else if (component.getName().equals("vodopenen")
						&& component.isShowing()) {
					list = vodopenen.list;
					for (CustomButton cm : list) {
						cm.choiced = false;
						cm.setBorder(cm.defaultBorder);
						cm.setEnabled(enabled);
					}
				} else if (component.getName().equals("co2")
						&& component.isShowing()) {
					list = co2.list;
					for (CustomButton cm : list) {
						cm.choiced = false;
						cm.setBorder(cm.defaultBorder);
						cm.setEnabled(enabled);
					}
				}
			}
		} else {

			for (int row = 0; row < ReasonsBrack.boolReasons.length; row++) {
				if (ReasonsBrack.boolReasons[row]) {
					ReasonsBrack.boolReasons[row] = false;
				}
			}
		}
	}


	private boolean checkClient(String client) {
		if (View.dtm_Extinguisher.getRowCount() == 0
				&& Brack.dtm_Scrab.getRowCount() == 0) {
			if (CURRENT_CLIENT.equals("")) {
				CURRENT_CLIENT = client;
				return true;
			} else {
				if (!CURRENT_CLIENT.equals(client)) {
					JOptionPane.showMessageDialog(null,
							"Въведен е друг клиент " + client + "!");
					return false;
				}
			}
		} else {
			if (View.dtm_Extinguisher.getRowCount() != 0) {
				if (!View.dtm_Extinguisher.getValueAt(0, 0).equals(client)) {
					JOptionPane.showMessageDialog(null,
							"Въведен е друг клиент " + client + "!");
					return false;
				}
			}
			if (Brack.dtm_Scrab.getRowCount() != 0) {
				if (!Brack.dtm_Scrab.getValueAt(0, 0).equals(client)) {
					JOptionPane.showMessageDialog(null,
							"Въведен е друг клиент " + client + "!");
					return false;
				}
			}
		}

		return true;
	}

	private void setNewDate(String item, String agent/*type*/) {
		// if there is HI from previous year but no chiosed HI date stay same
		// if it is choised HI gets new date
		if (!item.equals("Обслужване")) {
			String newDate = MyGetDate.getDateAfterToday(365);

			String hiNewDate = MyGetDate.getDateAfterToday(5 * 365);

			String substract_HI_YEAR = "не";
			if (!HI_Label.getName().equals("не")) {
				substract_HI_YEAR = HI_Label.getName();
				// System.out.printf("hi %s\n" + substract_HI_YEAR);
			}
			String godnostNaPovarogasitelnoWeshtestwoo =
					new GetAgentFitWorker(agent/*agent*/).doInBackground();
		   String urgentDays = MyGetDate.getUrgentDays(
            		MyGetDate.getReversedSystemDate()
			, godnostNaPovarogasitelnoWeshtestwoo);
		    int daysAsInt = 0;
            try {
				daysAsInt = Integer.parseInt(urgentDays);
			}catch (Exception e) {
				System.out.println("wrong date in WorkingBook => daysAsInt");
			}
            fitAgentLabel.setForeground(daysAsInt <= 365 ? Color.red : Color.black);

			fitAgentLabel.setName(godnostNaPovarogasitelnoWeshtestwoo);
			// item is from combo getSelectedItem();
			switch (item) {
				case ТО:
					tModel.setValueAt(newDate, 0, 7);
					tModel.setValueAt("не", 0, 8);
					tModel.setValueAt(substract_HI_YEAR, 0, 9);
					break;
				case П:
					tModel.setValueAt(newDate, 0, 8);
					tModel.setValueAt("не", 0, 7);
					tModel.setValueAt(substract_HI_YEAR, 0, 8);
					break;
				case ХИ:
					tModel.setValueAt(hiNewDate, 0, 9);
					tModel.setValueAt("не", 0, 8);
					tModel.setValueAt("не", 0, 7);
					break;
				case ТО_П:
					tModel.setValueAt(newDate, 0, 7);
					tModel.setValueAt(newDate, 0, 8);
					tModel.setValueAt(substract_HI_YEAR, 0, 9);
					break;
				case ТО_П_ХИ:
					tModel.setValueAt(newDate, 0, 7);
					tModel.setValueAt(newDate, 0, 8);
					tModel.setValueAt(hiNewDate, 0, 9);
					break;
			}
		}
	}


//	private void setNewDate2(String item, String item2) {
//		if(item2 == null) {
//			return;
//		}
//
//		String датаЗаПрезареждане = null;
//		String agent = "";
//		if(item2.contains("Прах")) {
//			agent = "прах";
//		} else if(item2.contains("Воден")) {
//			agent = "вода";
//		} else if(item2.contains("Водопенен")) {
//			agent = "вода и пяна";
//		} else if(item2.contains("CO2")) {
//			agent = "CO2";
//		}
//		try {
//			датаЗаПрезареждане = new GetExtinguishingAgentFitWorker(agent).doInBackground();
//		} catch (Exception exception) {
//			exception.printStackTrace();
//			датаЗаПрезареждане = "";
//		}
//		System.out.println("Дата са презареждане = " + датаЗаПрезареждане);
//		// if there is HI from previous year but no chiosed HI date stay same
//		// if it is choised HI gets new date
//		System.out.println("item = " + item);
//		if (!item.equals("Обслужване")) {
//			String датаЗаТехническоОбслужване = MyGetDate.getDateAfterToday(365);
//
//			String датаЗаХидростатичноИзпитване = MyGetDate.getDateAfterToday(5 * 365);
//
//			String substract_HI_YEAR = "не";
//			if (!HI_Label.getName().equals("не")) {
//				substract_HI_YEAR = HI_Label.getName();
//				// System.out.printf("hi %s\n" + substract_HI_YEAR);
//			}
//			// item is from combo getSelectedItem();
//			switch (item) {
//				case ТО:
//					System.out.println(ТО);
//					tModel.setValueAt(датаЗаТехническоОбслужване , 0, 7);
//					tModel.setValueAt("не", 0, 8);
//					tModel.setValueAt(substract_HI_YEAR, 0, 9);
//					break;
//				case П:
//					System.out.println(П);
//					tModel.setValueAt(датаЗаПрезареждане, 0, 8);
//					tModel.setValueAt("не", 0, 7);
//					tModel.setValueAt(substract_HI_YEAR, 0, 8);
//					break;
//				case ХИ :
//					System.out.println("ХИ");
//					tModel.setValueAt(датаЗаХидростатичноИзпитване, 0, 9);
//					tModel.setValueAt("не", 0, 8);
//					tModel.setValueAt("не", 0, 7);
//					break;
//				case ТО_П:
//					tModel.setValueAt(датаЗаТехническоОбслужване , 0, 7);
//					tModel.setValueAt(датаЗаПрезареждане, 0, 8);
//					tModel.setValueAt(substract_HI_YEAR, 0, 9);
//					break;
//				case ТО_П_ХИ:
//					tModel.setValueAt(датаЗаТехническоОбслужване , 0, 7);
//					tModel.setValueAt(датаЗаПрезареждане, 0, 8);
//					tModel.setValueAt(датаЗаХидростатичноИзпитване, 0, 9);
//					break;
//			}
//		}
//
//
//	}
}
