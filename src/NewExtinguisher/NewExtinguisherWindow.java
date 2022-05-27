package NewExtinguisher;

import NewClient.NewClient;
import NewExtinguisher.Renderers.NewExtingusherRenderer;
import NewExtinguisher.SwingWorkers.PrintProtokolWorker;
import NewExtinguisher.SwingWorkers.SaveInProtokolFromNewExtinguisherWorker;
import NewExtinguisher.SwingWorkers.StickerPrinterWorker;
import admin.SkladExtinguisher.Shop_SkladExtinuisher;
import admin.SkladExtinguisher.Workers.SeeAllNewExtinguisherWorker;
import generators.BarcodGenerator;
import generators.GenerateSO;
import mydate.MyGetDate;
import run.JDialoger;
import utility.*;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
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
	// private TooltipButton invoiceButton = null;

	// public static DefaultTableModel updateQuantityOfExtinguisherModel = null;
	public static DefaultTableModel dftm = null;
	public JTable table = null;

	public static BevelLabel protokolNumLabel = null;

	public Shop_SkladExtinuisher skladNewExtinguisher = null;
	private int CURRENT_ROW = -1;

	private String SERVICE_NUMBER = null;
	public String protokolNumberForInvoice = "0000000000";// slujebna stoinost v
															// nachaloto

	// private int[] digits = null;
	public static int[] barcod_digits = null;
	public static int[] allDigits = null;
	public static final GenerateSO genSO = new GenerateSO();
	public static final BarcodGenerator bGen = new BarcodGenerator();

	public NewExtinguisherWindow(final String serviceNumber,
			String protokolNumber) {

		this.SERVICE_NUMBER = serviceNumber;
		// PROTOKOL_NUMBER = protokolNumber;

		allDigits = new int[13];

		barcod_digits = new int[2];
		// init service number
		// digits = genSO.stringToArray(serviceNumber);

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

		JLabel clientLabel = new JLabel("������ ������");

		clientCombo = new ClientsListComboBox2();
		clientCombo.setBorder(BorderFactory.createLoweredBevelBorder());
		clientCombo.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().width * 0.25), (int) (northPanel
				.getPreferredSize().height * 0.35)));
		clientCombo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getItem().toString().length() > 0) {
					skladButton.setEnabled(true);
				} else {
					skladButton.setEnabled(false);
				}
			}

		});

		JPanel serialPanel = new JPanel();
		serialPanel.setLayout(new GridLayout(2, 1));
		serialPanel.setOpaque(false);

		TooltipButton stickerButton = new TooltipButton();

		// stickerButton.setIcon(new LoadIcon().setIcons(stickerImage));
		stickerButton.setToolTipText(getHTML_Text("��������� ������"));
		stickerButton.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.045), (int) (northPanel
				.getPreferredSize().getHeight() * 0.5)));
		stickerButton.setAutoSizedIcon(stickerButton, new LoadIcon().setIcons(stickerImage));
		// stickerButton.new LoadIcon().setIconsize();
		stickerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (dftm.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "���� �������� �����!");
					return;
				}
//				final StickerPrinterWorkerOldVersion sp =
//						new StickerPrinterWorkerOldVersion(MainPanel.��,
//								NewExtinguisherWindow.dftm.getValueAt(NewExtinguisherWindow.this.CURRENT_ROW,
//										2).toString(), MyGetDate.getDateAfterToday(365));
//				sp.execute();
				StickerPrinterWorker sp = new StickerPrinterWorker(
						/*MainPanel.��,*/ dftm.getValueAt(CURRENT_ROW, 2)
								.toString(), MyGetDate.getDateAfterToday(365),
						"��","��", 1);
				sp.execute();
			}

		});

		dbButton = new TooltipButton();
		// dbButton.setIcon(new LoadIcon().setIcons(dbImage));
		// dbButton.setAutoSizedIcon(dbButton, 40, 40,new LoadIcon().setIcons(dbImage));
		dbButton.setToolTipText(getHTML_Text("������ � ���� �����"));
		dbButton.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.045), (int) (northPanel
				.getPreferredSize().getHeight() * 0.5)));
		dbButton.setAutoSizedIcon(dbButton, new LoadIcon().setIcons(dbImage));
		// dbButton.setPreferredSize(new Dimension(55,55));
		dbButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (dftm.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "���� �������� �����!");
					return;
				}
				if (!tableChecker()) {
					JOptionPane
							.showMessageDialog(null, "��� ���������� �����!");
					return;
				}
				int yes_no = JOptionPane.showOptionDialog(null,
						"������� �� �� ��������� ���������� �����?", "",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, new String[] {
								"��", "��" }, // this is the array
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
		// eraserButton.setIcon(new LoadIcon().setIcons(eraserImage));
		eraserButton.setToolTipText(getHTML_Text("������ �������"));
		eraserButton.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.045), (int) (northPanel
				.getPreferredSize().getHeight() * 0.5)));

		eraserButton.setAutoSizedIcon(eraserButton, new LoadIcon().setIcons(eraserImage));
		// eraserButton.setPreferredSize(new Dimension(55,55));
		eraserButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (dftm.getRowCount() == 0)
					return;

				if (CURRENT_ROW == -1) {
					JOptionPane.showMessageDialog(null,
							"�� � ������ �������������");
					return;
				}
				int yes_no = JOptionPane.showOptionDialog(null,
						"�������� �� ������ �� �������� ������� ?", "",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, new String[] {
								"��", "��" }, // this is the array
						"default");
				if (yes_no != 0)
					return;

				dftm.removeRow(CURRENT_ROW);
				// updateQuantityOfExtinguisherModel.removeRow(CURRENT_ROW);
				CURRENT_ROW = -1;
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
		// printerButton.setSizeInPercent(24, 14);
		// printerButton.setIcon(new LoadIcon().setIcons(printerImage));
		printerButton.setToolTipText(getHTML_Text("��������� �������� 2815"));
		printerButton.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.045), (int) (northPanel
				.getPreferredSize().getHeight() * 0.5)));

		printerButton.setAutoSizedIcon(printerButton, new LoadIcon().setIcons(printerImage));
		// printerButton.setEnabled(false);
		printerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (table.getRowCount() > 0) {
					if (!tableChecker()) {
						JOptionPane.showMessageDialog(null,
								"��� ���������� �����!");
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
					JOptionPane.showMessageDialog(null, "���� �������� �����!");
				}
			}

		});

		TooltipButton clientButton = new TooltipButton();
		clientButton.setToolTipText(getHTML_Text("������ ��� ������"));
		clientButton.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.045), (int) (northPanel
				.getPreferredSize().getHeight() * 0.5)));
		;
		clientButton.setAutoSizedIcon(clientButton, new LoadIcon().setIcons(clientsImage));
		// clientButton.setIcon(new LoadIcon().setIcons(clientsImage));
		// clientButton.setPreferredSize(new Dimension(55,55));
		clientButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				NewClient nc = new NewClient();
				JDialoger jDialog = new JDialoger();
				jDialog.setContentPane(nc);
				jDialog.setTitle("��������� �� ���� �������");
				jDialog.setResizable(false);
				jDialog.Show();
			}

		});

		skladButton = new TooltipButton();
		skladButton.setEnabled(false);
		skladButton.setToolTipText(getHTML_Text("������ ��������������"));
		skladButton.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.045), (int) (northPanel
				.getPreferredSize().getHeight() * 0.5)));
		skladButton.setAutoSizedIcon(skladButton, new LoadIcon().setIcons(skladExtImage));
		// skladButton.setIcon(new LoadIcon().setIcons(skladExtImage));
		// skladButton.setPreferredSize(new Dimension(55,55));
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
				jDialog.setTitle("���� ��������������");
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
		// northPanel.add(invoiceButton);

		JPanel centerPanel = new JPanel();

		// updateQuantityOfExtinguisherModel = new DefaultTableModel(new
		// String[] {
		// "���", "����", "���������", "�����" }, 0);

		dftm = new DefaultTableModel(new String[] { "���", "����", "������",
				"������. �����", "���������", "�����", "��. ����",
				"����������", "������� �� ��������", "���. �����" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 3 || column == 9;
			}
		};
		dftm.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent arg0) {
				// TODO Auto-generated method stub
				if (dftm.getRowCount() > 0) {
					clientCombo.setEnabled(false);
					dbButton.setEnabled(true);
				} else {
					clientCombo.setEnabled(true);
					dbButton.setEnabled(false);
				}
			}

		});

		table = new JTable(dftm);
		table.setDefaultRenderer(Object.class,
				new NewExtingusherRenderer(table));
		table.setRowHeight(MainPanel.getFontSize() + 15);
		// resizeColumnWidth(table);
		// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				CURRENT_ROW = table.getSelectedRow();
			}
		});
		// ������ ������ ������� � ����������
		table.getColumnModel().getColumn(7).setMinWidth(0);
		table.getColumnModel().getColumn(7).setMaxWidth(0);
		table.getColumnModel().getColumn(7).setWidth(0);
		table.getColumnModel().getColumn(8).setMinWidth(0);
		table.getColumnModel().getColumn(8).setMaxWidth(0);
		table.getColumnModel().getColumn(8).setWidth(0);
		// JTable rowTable = new CommonResources.RowNumberTable(table); //****

		JScrollPane scrollPane = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(this.WIDTH - 20,
				(int) (this.HEIGHT * 0.65)));

		// scrollPane.setRowHeaderView(rowTable); //****
		// scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER,//****
		// rowTable.getTableHeader());//****

		centerPanel.setLayout(new GridLayout(1, 1));
		centerPanel.add(scrollPane);

		JPanel southPanel = new JPanel(); // GradientPanel();
		southPanel.setPreferredSize(new Dimension(this.WIDTH - 20,
				(int) (this.HEIGHT * 0.08)));
		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		float labelHeight = (int) (southPanel.getPreferredSize().getHeight() * 0.8);
		BevelLabel sallerLabel = new BevelLabel(labelHeight);
		/*
		 * sallerLabel.setPreferredSize(new Dimension(
		 * (int)(southPanel.getPreferredSize().getWidth() * 0.3),
		 * (int)(southPanel.getPreferredSize().getHeight() * 0.8)));
		 */
		sallerLabel.setTitle(Enums.��������.name() +  ": ");
		sallerLabel.setName(personName);

		BevelLabel dateLabel = new BevelLabel(labelHeight);
		/*
		 * dateLabel.setPreferredSize(new Dimension(
		 * (int)(southPanel.getPreferredSize().getWidth() * 0.15),
		 * (int)(southPanel.getPreferredSize().getHeight() * 0.8)));
		 */
		dateLabel.setTitle(" ���� : ");
		dateLabel.setName(MyGetDate.getReversedSystemDate());

		protokolNumLabel = new BevelLabel(labelHeight);
		/*
		 * protokolNumLabel.setPreferredSize(new Dimension(
		 * (int)(southPanel.getPreferredSize().getWidth() * 0.20),
		 * (int)(southPanel.getPreferredSize().getHeight() * 0.8)));
		 */
		protokolNumLabel.setTitle(" �������� \u2116 ");
		protokolNumLabel.setName(protokolNumber);

		southPanel.add(sallerLabel);
		southPanel.add(dateLabel);
		southPanel.add(protokolNumLabel);

		basePanel.add(northPanel, BorderLayout.NORTH);
		basePanel.add(centerPanel, BorderLayout.CENTER);
		basePanel.add(southPanel, BorderLayout.SOUTH);

		this.add(basePanel);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static void addBarcodes(int index) {

		barcod_digits = bGen.next_barcod(barcod_digits);
		if (barcod_digits == null) {
			return;
		}
		for (int j = 10; j <= 11; j++) {
			allDigits[j] = barcod_digits[j - 10];
		}

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
	// public void resizeColumnWidth(JTable table) {
	// final TableColumnModel columnModel = table.getColumnModel();
	// for (int column = 0; column < table.getColumnCount(); column++) {
	// int width = 50;//300; // Min width
	// for (int row = 0; row < table.getRowCount(); row++) {
	// TableCellRenderer renderer = table.getCellRenderer(row, column);
	// Component comp = table.prepareRenderer(renderer, row, column);
	// width = Math.max(comp.getPreferredSize().width, width);
	// }
	// columnModel.getColumn(column).setPreferredWidth(width + getFontSize());
	// }
	//
	// }
}
