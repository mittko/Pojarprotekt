package newextinguisher;

import clients.NewClient;
import newextinguisher.renderers.NewExtingusherRenderer;
import newextinguisher.workers.PrintProtokolWorker;
import newextinguisher.workers.SaveInProtokolFromNewExtinguisherWorker;
import newextinguisher.workers.StickerPrinterWorker;
import admin.sklad.Shop_SkladExtinuisher;
import admin.sklad.workers.SeeAllNewExtinguisherWorker;
import db.Invoice.InvoiceNumber;
import generators.BarcodGenerator;
import generators.GenerateSO;
import menu.RunInvoice;
import mydate.MyGetDate;
import run.JDialoger;
import utils.*;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.TreeMap;

public class NewExtinguisherWindow extends MainPanel {

	private ClientsListComboBox2 clientCombo = null;
	public static TooltipButton dbButton = null;
	public TooltipButton printerButton = null;
	private TooltipButton skladButton = null;

	public static DefaultTableModel dftm = null;
	public MyTable table = null;

	public static BevelLabel protokolNumLabel = null;

	public Shop_SkladExtinuisher skladNewExtinguisher = null;
	private int CURRENT_ROW = -1;

	private String SERVICE_NUMBER = null;
	public String protokolNumberForInvoice = "0000000000";// slujebna stoinost v
															// nachaloto
	public static int[] barcod_digits = null;
	public static int[] allDigits = null;
	public static final GenerateSO genSO = new GenerateSO();
	public static final BarcodGenerator bGen = new BarcodGenerator();

	public NewExtinguisherWindow(final String serviceNumber,
			String protokolNumber) {

		this.SERVICE_NUMBER = serviceNumber;

		allDigits = new int[13];

		barcod_digits = new int[2];
		// init service number

		for (int i = 0; i < SERVICE_NUMBER.length(); i++) {
			allDigits[i] = SERVICE_NUMBER.charAt(i) - 48;
		}

		JPanel basePanel = new JPanel();
		basePanel.setLayout(new BorderLayout());
		basePanel.setBorder(BorderFactory.createLineBorder(Color.black));

		JPanel northPanel = new JPanel(); // GradientPanel();
		northPanel.setPreferredSize(new Dimension(
				(int) (this.WIDTH * 1.0) - 20, (int) (this.HEIGHT * 0.15)));

		JPanel clientPanel = new JPanel();
		clientPanel.setLayout(new GridLayout(2, 1));
		clientPanel.setOpaque(false);

		JLabel clientLabel = new JLabel("Избери Клиент");

		clientCombo = new ClientsListComboBox2();
		clientCombo.setBorder(BorderFactory.createLoweredBevelBorder());
		clientCombo.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().width * 0.25), (int) (northPanel
				.getPreferredSize().height * 0.35)));
		clientCombo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				skladButton.setEnabled(e.getItem().toString().length() > 0);
			}

		});

		JPanel serialPanel = new JPanel();
		serialPanel.setLayout(new GridLayout(2, 1));
		serialPanel.setOpaque(false);

		TooltipButton stickerButton = new TooltipButton();

		stickerButton.setToolTipText(getHTML_Text("ГЕНЕРИРАЙ СТИКЕР"));
		stickerButton.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.045), (int) (northPanel
				.getPreferredSize().getHeight() * 0.5)));
		stickerButton.setAutoSizedIcon(stickerButton, new LoadIcon().setIcons(stickerImage));
		stickerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (dftm.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "Няма въведени данни!");
					return;
				}
//				final StickerPrinterWorkerOldVersion sp =
//						new StickerPrinterWorkerOldVersion(MainPanel.ТО,
//								NewExtinguisherWindow.dftm.getValueAt(NewExtinguisherWindow.this.CURRENT_ROW,
//										2).toString(), MyGetDate.getDateAfterToday(365));
//				sp.execute();
				StickerPrinterWorker sp = new StickerPrinterWorker(
						/*MainPanel.ТО,*/ dftm.getValueAt(CURRENT_ROW, 2)
								.toString(), MyGetDate.getDateAfterToday(365),
						"не","не", true, false, false);
				sp.execute();
			}

		});

		dbButton = new TooltipButton();
		dbButton.setToolTipText(getHTML_Text("ЗАПИШИ В БАЗА ДАННИ"));
		dbButton.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.045), (int) (northPanel
				.getPreferredSize().getHeight() * 0.5)));
		dbButton.setAutoSizedIcon(dbButton, new LoadIcon().setIcons(dbImage));
		dbButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (dftm.getRowCount() == 0) {
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
				if (yes_no != 0) {
					return;
				}
				JDialog jd = ((JDialog) (SwingUtilities
						.getWindowAncestor(NewExtinguisherWindow.this)));
				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));

				SaveInProtokolFromNewExtinguisherWorker sw = new SaveInProtokolFromNewExtinguisherWorker(jd,
						clientCombo.getSelectedItem().toString(), dftm);

				try {
					String[] NUMBERS = sw.doInBackground();
					// update service and protokol numbers
					SERVICE_NUMBER = NUMBERS[0];
					protokolNumberForInvoice = NUMBERS[1];

					barcod_digits = new int[2];
					for (int i = 0; i < SERVICE_NUMBER.length(); i++) {
						allDigits[i] = SERVICE_NUMBER.charAt(i) - 48;
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		});

		TooltipButton eraserButton = new TooltipButton();
		eraserButton.setToolTipText(getHTML_Text("ИЗТРИЙ ДАННИТЕ"));
		eraserButton.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.045), (int) (northPanel
				.getPreferredSize().getHeight() * 0.5)));

		eraserButton.setAutoSizedIcon(eraserButton, new LoadIcon().setIcons(eraserImage));
		eraserButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (dftm.getRowCount() == 0)
					return;

				int yes_no = JOptionPane.showOptionDialog(null,
						"Наистина ли искате да изтриете данните ?", "",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, new String[] {
								"Да", "Не" }, // this is the array
						"default");
				if (yes_no != 0)
					return;

				dftm.setRowCount(0);
				// updateQuantityOfExtinguisherModel.removeRow(CURRENT_ROW);
				CURRENT_ROW = -1;
				StickerPrinterWorker.clearEnteredNumbers();
				// this logic clear all table contents
				/*
				 * dftm.setRowCount(0);
				 * updateQuantityOfExtinguisherModel.setRowCount(0);// update
				 * quantity in skald
				 * 
				 * barcod_digits = new int[2]; for(int i = 0;i <
				 * SERVICE_NUMBER.length();i++) { allDigits[i] =
				 * (int)(SERVICE_NUMBER.charAt(i) - 48); }
				 */

			}
		});

		printerButton = new TooltipButton();
		printerButton.setToolTipText(getHTML_Text("ПРИНТИРАЙ ПРОТОКОЛ 2815"));
		printerButton.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.045), (int) (northPanel
				.getPreferredSize().getHeight() * 0.5)));

		printerButton.setAutoSizedIcon(printerButton, new LoadIcon().setIcons(printerImage));
		printerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (table.getRowCount() > 0) {
					if (!tableChecker()) {
						JOptionPane.showMessageDialog(null,
								"Има незаписани данни!");
						return;
					}
					JDialog jd = (JDialog) SwingUtilities
							.getWindowAncestor(NewExtinguisherWindow.this);
					jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));

					PrintProtokolWorker printProtokol = new PrintProtokolWorker(
							dftm, clientCombo.getSelectedItem().toString(),
							new TreeMap<Object, Integer>(), jd);
					printProtokol.execute();
				} else {
					JOptionPane.showMessageDialog(null, "Няма въведени данни!");
				}
			}

		});

		TooltipButton clientButton = new TooltipButton();
		clientButton.setToolTipText(getHTML_Text("ВЪВЕДИ НОВ КЛИЕНТ"));
		clientButton.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.045), (int) (northPanel
				.getPreferredSize().getHeight() * 0.5)));

		clientButton.setAutoSizedIcon(clientButton, new LoadIcon().setIcons(clientsImage));

		clientButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				NewClient nc = new NewClient();
				JDialoger jDialog = new JDialoger();
				jDialog.setContentPane(nc);
				jDialog.setTitle("Въвеждане на нови клиенти");
				jDialog.setResizable(false);
				jDialog.Show();
			}

		});

		skladButton = new TooltipButton();
		skladButton.setEnabled(false);
		skladButton.setToolTipText(getHTML_Text("ИЗБЕРИ ПОЖАРОГАСИТЕЛИ"));
		skladButton.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.045), (int) (northPanel
				.getPreferredSize().getHeight() * 0.5)));
		skladButton.setAutoSizedIcon(skladButton, new LoadIcon().setIcons(skladExtImage));

		skladButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				// load data in other thread

				JDialog jd = (JDialog) SwingUtilities
						.getWindowAncestor(NewExtinguisherWindow.this);
				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));

				SeeAllNewExtinguisherWorker seeAll = new SeeAllNewExtinguisherWorker(
						jd);
				ArrayList<Object[]> data = null;
				try {
					data = seeAll.doInBackground();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// open frame
				skladNewExtinguisher = new Shop_SkladExtinuisher(data);
				JDialoger jDialog = new JDialoger();
				jDialog.setContentPane(skladNewExtinguisher);
				jDialog.setTitle("Нови пожарогасители");
				jDialog.setResizable(false);
				jDialog.Show();

			}

		});

		clientPanel.add(clientLabel);
		clientPanel.add(clientCombo);

		northPanel.add(clientPanel);
		northPanel.add(serialPanel);
		northPanel.add(skladButton);
		northPanel.add(stickerButton);
		northPanel.add(dbButton);
		northPanel.add(eraserButton);
		northPanel.add(printerButton);
		northPanel.add(clientButton);

		TooltipButton invoiceButton = new TooltipButton();
		invoiceButton.setToolTipText(getHTML_Text("ОТВОРИ ФАКТУРИ"));
		invoiceButton.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.045), (int) (northPanel
				.getPreferredSize().getHeight() * 0.5)));
		invoiceButton.setAutoSizedIcon(invoiceButton, new LoadIcon().setIcons(invoiceImage));
		invoiceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingWorker sw = new SwingWorker() {

					private String invoiceNumber = null;
					private String proformNumber = null;

					@Override
					protected Object doInBackground() throws Exception {
						// TODO Auto-generated method stub
						try {

							invoiceNumber = InvoiceNumber.getInvoiceNumber();
							proformNumber = InvoiceNumber.getProformNumber();
						} finally {
							SwingUtilities.invokeLater(new RunInvoice(
									invoiceNumber, proformNumber, protokolNumberForInvoice,null));
						}
						return null;
					}

				};
				sw.execute();
			}
		});
		northPanel.add(invoiceButton);

		JPanel centerPanel = new JPanel();



		dftm = new DefaultTableModel(new String[] { "Вид", "Маса", "Баркод",
				"Монтаж. номер", "Категория", "Марка", "Ед. Цена",
				"Контрагент", "Фактура по доставка", "Доп. данни" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 3 || column == 9;
			}
		};

		table = new MyTable(dftm) {
			@Override
			public void onTableChanged(TableModelEvent tableModelEvent) {
				super.onTableChanged(tableModelEvent);

				if (dftm.getRowCount() > 0) {
					clientCombo.setEnabled(false);
					dbButton.setEnabled(true);
				} else {
					clientCombo.setEnabled(true);
					dbButton.setEnabled(false);
				}
			}

			@Override
			public void removeAt(int row) {
				super.removeAt(row);
				dftm.removeRow(row);
			}
		};
		table.setDefaultRenderer(Object.class,
				new NewExtingusherRenderer(table));
		table.setRowHeight(MainPanel.getFontSize() + 15);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				CURRENT_ROW = table.getSelectedRow();
			}
		});
		// изтрий колони фактура и контрагент
		table.getColumnModel().getColumn(7).setMinWidth(0);
		table.getColumnModel().getColumn(7).setMaxWidth(0);
		table.getColumnModel().getColumn(7).setWidth(0);
		table.getColumnModel().getColumn(8).setMinWidth(0);
		table.getColumnModel().getColumn(8).setMaxWidth(0);
		table.getColumnModel().getColumn(8).setWidth(0);

		JScrollPane scrollPane = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(this.WIDTH - 20,
				(int) (this.HEIGHT * 0.65)));


		centerPanel.setLayout(new GridLayout(1, 1));
		centerPanel.add(scrollPane);

		JPanel southPanel = new JPanel(); // GradientPanel();
		southPanel.setPreferredSize(new Dimension(this.WIDTH - 20,
				(int) (this.HEIGHT * 0.08)));
		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		float labelHeight = (int) (southPanel.getPreferredSize().getHeight() * 0.8);
		BevelLabel sallerLabel = new BevelLabel(labelHeight);

		sallerLabel.setTitle(Enums.Оператор.name() +  ": ");
		sallerLabel.setName(personName);

		BevelLabel dateLabel = new BevelLabel(labelHeight);

		dateLabel.setTitle(" Дата : ");
		dateLabel.setName(MyGetDate.getReversedSystemDate());

		protokolNumLabel = new BevelLabel(labelHeight);

		protokolNumLabel.setTitle(" Протокол \u2116 ");
		protokolNumLabel.setName(protokolNumber);

		southPanel.add(sallerLabel);
		southPanel.add(dateLabel);
		southPanel.add(protokolNumLabel);

		basePanel.add(northPanel, BorderLayout.NORTH);
		basePanel.add(centerPanel, BorderLayout.CENTER);
		basePanel.add(southPanel, BorderLayout.SOUTH);

		this.add(basePanel);

		StickerPrinterWorker.clearEnteredNumbers();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static void addBarcodes(int index) {

		barcod_digits = bGen.next_barcod(barcod_digits);
		if (barcod_digits == null) {
			return;
		}
		System.arraycopy(barcod_digits, 0, allDigits, 10, 2);

		allDigits[12] = bGen.getCheckSum13(allDigits);

		dftm.setValueAt(genSO.digitsToString(allDigits), index, 2);
	}

	private boolean tableChecker() {
		for (int row = 0; row < dftm.getRowCount(); row++) {
			for (int column = 0; column < dftm.getColumnCount() - 1; column++) {
				if (dftm.getValueAt(row, column).toString().isEmpty()) {
					return false;
				}
			}
		}
		return true;
	}

}
