package invoicewindow;

import invoice.Fiskal.CreateBonFPrint;
import invoice.InvoiceRenderer.CustomTableCellRenderer;
import invoice.SaveInInvoiceDBDialog;
import invoice.Sklad.SkladArtikulFrame;
import invoice.Sklad.Worker.LoadAllArtikulsFromInvoiceWorker;
import invoice.worker.ProtokolSearchWorker;
import invoice.worker.SellWithFiskalBonWorker;
import NewClient.NewClient;
import mydate.MyGetDate;
import run.JDialoger;
import utility.*;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;

//import utility.CheckButtonArtikuli;

public class SearchFromProtokolTab extends MainPanel {

	public static EditableField searchField;

	private final EditableField dateField;

	public static JTextField discountField;

	public static DiscountButtonArtikuli choiceDiscountButton;

	private static JComboBox<String> paymenCombo;

	public static BevelLabel clientLabel;

	public static DefaultTableModel invoiceTableModel = null;
	private final JTable invoiceTable;

	// private DefaultTableModel artikuliModel;

	private final BevelLabel invoiceNumberLabel = null;

	private final BevelLabel proformNumLabel = null;

	private static JTextField sumFieldNoTax;// no tax
	public static JTextField sumField; // with tax

	public static String INVOICE_CURRENT_CLIENT;
	// public static ArrayList<Object[]> moreProtokolsLists = new
	// ArrayList<Object[]>(); // store
	// data
	// from
	// more
	// protokols
	// and
	// one
	// client
	public static HashSet<String> protokolNumberSet = new HashSet<String>();

	final JPopupMenu popupMenu = new JPopupMenu();
	private String protokolNumber = "";

	public SearchFromProtokolTab() {

		INVOICE_CURRENT_CLIENT = "";
		// moreProtokolsList.clear();
		protokolNumberSet.clear();

		JPanel northPanel = new JPanel();// GradientPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		northPanel.setPreferredSize(new Dimension(
				(int) (this.WIDTH * 1.0) - 20, (int) (this.HEIGHT * 0.1)));

		searchField = new EditableField("Протокол \u2116", 9);
		searchField.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.13), (int) (northPanel
				.getPreferredSize().getHeight() * 0.6)));
		searchField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (searchField.getText().isEmpty()) { // no set number
					return;
				}
				// if (invoiceTableModel.getRowCount() > 0) { // no data entry
				// return;
				// }
				protokolNumber = searchField.getText();

				ProtokolSearchWorker ps = new ProtokolSearchWorker(searchField,
						invoiceTableModel); // search in protokol
				ps.execute();
			}

		});

		dateField = new EditableField(" Дата ", 7);
		dateField.setText(MyGetDate.getReversedSystemDate());
		dateField.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.9), (int) (northPanel
				.getPreferredSize().getHeight() * 0.6)));

		discountField = new JTextField(2);
		discountField.setForeground(Color.red);
		discountField.setFont(new Font(Font.DIALOG, Font.BOLD,
				getFontSize() + 10));
		discountField.setEditable(false);
		discountField.setBorder(BorderFactory.createLoweredBevelBorder());
		discountField.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.05), (int) (northPanel
				.getPreferredSize().getHeight() * 0.6)));

		paymenCombo = new JComboBox<String>(new String[] { "Начин на плащане",
				"В брой", "По банков път" });
		paymenCombo.setRenderer(new ComboRenderer());
		paymenCombo.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.2), (int) (northPanel
				.getPreferredSize().getHeight() * 0.5)));

		TooltipButton newClientButton = new TooltipButton();
		newClientButton.setToolTipText(getHTML_Text("ВЪВЕДИ НОВ КЛИЕНТ"));
		newClientButton.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.045), (int) (northPanel
				.getPreferredSize().getHeight() * 0.75)));

		newClientButton.setAutoSizedIcon(newClientButton,
				new LoadIcon().setIcons(clientsImage));
		// newClientButton.setIcon(setIcons(clientsImage));
		// newClientButton.setPreferredSize(new Dimension(55,55));
		newClientButton.addActionListener(new ActionListener() {

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

		TooltipButton skladButton = new TooltipButton();
		// skladButton.setPreferredSize(new Dimension(55, 55));
		// skladButton.setIcon(setIcons(artikuliImage));
		skladButton.setToolTipText(getHTML_Text("ИЗБЕРИ АРТИКУЛИ"));
		skladButton.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.045), (int) (northPanel
				.getPreferredSize().getHeight() * 0.75)));

		skladButton.setAutoSizedIcon(skladButton, new LoadIcon().setIcons(artikuliImage));
		skladButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (invoiceTableModel.getRowCount() == 0)
					return;
				JDialog jd = (JDialog) (SwingUtilities
						.getWindowAncestor(SearchFromProtokolTab.this));

				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));

				SkladArtikulFrame skladArtikulFrame = new SkladArtikulFrame(
						invoiceTableModel/*
										 * , sumField8
										 */, Double.parseDouble(discountField
								.getText()), choiceDiscountButton.isSelected());

				LoadAllArtikulsFromInvoiceWorker loader = new LoadAllArtikulsFromInvoiceWorker(
						jd);// (JDialog)SwingUtilities.getWindowAncestor(searchFromProtokol.this));
				loader.execute();

				JDialoger jDialog = new JDialoger();
				jDialog.setContentPane(skladArtikulFrame);
				jDialog.setTitle("Артикули");
				jDialog.setResizable(false);
				jDialog.Show();
			}

		});

		TooltipButton rubberButton = new TooltipButton();
		// rubberButton.setPreferredSize(new Dimension(55, 55));
		rubberButton.setToolTipText(getHTML_Text("ИЗТРИЙ ДАННИТЕ"));
		rubberButton.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.045), (int) (northPanel
				.getPreferredSize().getHeight() * 0.75)));

		rubberButton.setAutoSizedIcon(rubberButton, new LoadIcon().setIcons(eraserImage));
		// rubberButton.setIcon(setIcons(eraserImage));
		rubberButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				/*
				 * if(invoiceTableModel.getRowCount() == 0) return;
				 */

				int yes_no = JOptionPane.showOptionDialog(null,
						"Наистина ли искате да изтриете данните ?", "",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, new String[] {
								"Да", "Не" }, // this is the array
						"default");

				if (yes_no == 0)
					// clear artikul quantity
					// artikuliModel.setRowCount(0);

					clear();
			}

		});

		TooltipButton dbButton = new TooltipButton();

		// dbButton.setPreferredSize(new Dimension(55, 55));
		// dbButton.setAutoSizedIcon(dbButton,setIcons(dbImage));
		dbButton.setToolTipText(getHTML_Text("ЗАПИШИ В БАЗА ДАННИ"));
		dbButton.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.045), (int) (northPanel
				.getPreferredSize().getHeight() * 0.75)));

		dbButton.setAutoSizedIcon(dbButton, new LoadIcon().setIcons(dbImage));
		dbButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (invoiceTableModel.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "Няма въведени данни!");
					return;
				}
				if (sumField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Не е калкулирана крайна цена!");
					return;
				}
				if (paymenCombo.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(null,
							"Не е избран начин на плащане!");
					return;
				}

				if (protokolNumber.isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Липсва номер на Протокол");
					return;
				}

				SaveInInvoiceDBDialog save = new SaveInInvoiceDBDialog(INVOICE_PARENT,INVOICE_CHILD
						,protokolNumber,
						INVOICE_CURRENT_CLIENT, paymenCombo.getSelectedItem()
								.toString(), discountField.getText(), MyMath
								.round(getDanOsnova(), 2) + "", personName,
						dateField.getText(),
						"Обслужване на Пожарогасители",
						true, // invoice
						false, // proform
						true, // acquittance
						invoiceTableModel, invoiceNumberLabel, proformNumLabel,
						null);

				// clear protokol number to avoid mess
				protokolNumber = "";

				JDialoger jDialoger = new JDialoger();
				jDialoger.setContentPane(save);
				jDialoger.Show();

			}

		});

		JPanel centerPanel = new JPanel();

		invoiceTableModel = new DefaultTableModel(new String[] {
				"Вид на извършената услуга", "Мярка", "К-во", "Ед. Цена",
				"Стойност", "Отстъпка в %", "Контрагент", "Фактура по доставка" },
				0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				if(!choiceDiscountButton.isSelected()) {
					return column == 1 || column == 5;
				}
				return false;
			}
		};
		// artikuliModel = new DefaultTableModel(
		// new String[] { "Артикули", "К-во" }, 0);

		invoiceTable = new JTable(invoiceTableModel);

		invoiceTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				Point point = me.getPoint();
				int currentRow = invoiceTable.rowAtPoint(point);
				invoiceTable.setRowSelectionInterval(currentRow, currentRow);
			}
		});
		JMenuItem menuItem = new JMenuItem("Изтрий ред");
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// System.out.println(invoiceTable.getSelectedRow());
				invoiceTableModel.removeRow(invoiceTable.getSelectedRow());
				if (invoiceTableModel.getRowCount() == 0) {
					clear();
				}
			/*	else {
					initSumTextField();
				}*/
			}

		});
		popupMenu.add(menuItem);
		invoiceTable.setComponentPopupMenu(popupMenu);

		invoiceTableModel.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent arg0) {
				// TODO Auto-generated method stub
				switch (arg0.getType()) {
				case TableModelEvent.INSERT:
					case TableModelEvent.DELETE:
						ArrayList<Double> totals = new ArrayList<>();
					for(int row = 0;row < invoiceTableModel.getRowCount();row++) {
						double total = Double.parseDouble(invoiceTableModel.getValueAt(row,3).toString());
						totals.add(total);
					}
					choiceDiscountButton.setTotals(totals);
					calcFinalSum();
					break;
					default:
					break;
				}

			}
		});
		invoiceTable.setDefaultRenderer(Object.class,
				new CustomTableCellRenderer(invoiceTable));
		invoiceTable.setRowHeight(MainPanel.getFontSize() + 15);
		// скрий колони фактура и контрагент
		invoiceTable.getColumnModel().getColumn(6).setMinWidth(0);
		invoiceTable.getColumnModel().getColumn(6).setMaxWidth(0);
		invoiceTable.getColumnModel().getColumn(6).setWidth(0);
		invoiceTable.getColumnModel().getColumn(7).setMinWidth(0);
		invoiceTable.getColumnModel().getColumn(7).setMaxWidth(0);
		invoiceTable.getColumnModel().getColumn(7).setWidth(0);
		// JTable rowTable = new CommonResources.RowNumberTable(invoiceTable);
		// // ****

		JScrollPane scroll = new JScrollPane(invoiceTable,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension((int) (this.WIDTH * 1.0) - 20,
				(int) (this.HEIGHT * 0.57)));

		// scroll.setRowHeaderView(rowTable); // ****
		// scroll.setCorner(JScrollPane.UPPER_LEFT_CORNER,// ****
		// rowTable.getTableHeader());// ****

		centerPanel.add(scroll);

		sumFieldNoTax = new JTextField(4);
		sumFieldNoTax.setEditable(false);
		sumFieldNoTax.setForeground(Color.green.darker());
		sumFieldNoTax.setFont(new Font(Font.DIALOG, Font.BOLD,
				getFontSize() + 10));
		sumFieldNoTax.setBorder(BorderFactory.createLoweredBevelBorder());

		sumField = new JTextField(4);
		sumField.setEditable(false);
		sumField.setForeground(Color.red);
		sumField.setFont(new Font(Font.DIALOG, Font.BOLD, getFontSize() + 10));
		sumField.setBorder(BorderFactory.createLoweredBevelBorder());

		choiceDiscountButton = new DiscountButtonArtikuli(invoiceTableModel,
				sumField, sumFieldNoTax);
		choiceDiscountButton.setToolTipText(getHTML_Text("ДОБАВИ ОТСТЪПКА"));
		choiceDiscountButton.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.045), (int) (northPanel
				.getPreferredSize().getHeight() * 0.6)));

		clientLabel = new BevelLabel();
		clientLabel.setTitle("Клиент : ");
		clientLabel.setName("");
		clientLabel.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.25), (int) (northPanel
				.getPreferredSize().getHeight() * 0.7)));

		northPanel.add(searchField);
		northPanel.add(dateField);
		northPanel.add(discountField);
		northPanel.add(choiceDiscountButton);
		northPanel.add(paymenCombo);
		northPanel.add(skladButton);
		northPanel.add(rubberButton);
		northPanel.add(dbButton);
		northPanel.add(newClientButton);
		northPanel.add(clientLabel);

		JPanel southPanel = new JPanel();
		southPanel.setPreferredSize(new Dimension(
				(int) (this.WIDTH * 1.0) - 20, (int) (this.HEIGHT * 0.065)));
		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 0));

		sumField.setPreferredSize(new Dimension((int) (southPanel
				.getPreferredSize().getWidth() * 0.2), (int) (southPanel
				.getPreferredSize().getHeight() * 0.8)));

		sumFieldNoTax.setPreferredSize(new Dimension((int) (southPanel
				.getPreferredSize().getWidth() * 0.2), (int) (southPanel
				.getPreferredSize().getHeight() * 0.8)));
		/*
		 * invoiceNumberLabel = new BevelLabel();
		 * invoiceNumberLabel.setPreferredSize(new Dimension(
		 * (int)(southPanel.getPreferredSize().getWidth() * 0.2),
		 * (int)(southPanel.getPreferredSize().getHeight() * 0.8)));
		 * invoiceNumberLabel.setTitle("Фактура \u2116 ");
		 * invoiceNumberLabel.setName("");
		 */

		/*
		 * proformNumLabel = new BevelLabel();
		 * proformNumLabel.setPreferredSize(new Dimension(
		 * (int)(southPanel.getPreferredSize().getWidth() * 0.2),
		 * (int)(southPanel.getPreferredSize().getHeight() * 0.8)));
		 * proformNumLabel.setTitle("Проформа \u2116 ");
		 * proformNumLabel.setName("");
		 */

		float labelHeight = (int) (southPanel.getPreferredSize().getHeight() * 0.8);
		BevelLabel sallerLabel = new BevelLabel(labelHeight);
		/*
		 * sallerLabel.setPreferredSize(new Dimension(
		 * (int)(southPanel.getPreferredSize().getWidth() * 0.25),
		 * (int)(southPanel.getPreferredSize().getHeight() * 0.8)));
		 */
		sallerLabel.setTitle(Enums.Оператор.name() + ": ");
		sallerLabel.setName(personName);

		BevelLabel sumLabel = new BevelLabel(labelHeight);
		sumLabel.setTitle("");
		sumLabel.setName("Сума с ДДС : ");
		/*
		 * sumLabel.setPreferredSize(new Dimension(
		 * (int)(southPanel.getPreferredSize().getWidth() * 0.17),
		 * (int)(southPanel.getPreferredSize().getHeight() * 0.8)));
		 */

		BevelLabel sumLabel2 = new BevelLabel(labelHeight);
		sumLabel2.setTitle("");
		sumLabel2.setName("Сума без ДДС : ");
		/*
		 * sumLabel2.setPreferredSize(new Dimension(
		 * (int)(southPanel.getPreferredSize().getWidth() * 0.17),
		 * (int)(southPanel.getPreferredSize().getHeight() * 0.8)));
		 */

		TooltipButton daysAccountButton = new TooltipButton();
		daysAccountButton
				.setToolTipText(getHTML_Text("НАПРАВИ ФИСКАЛЕН ОТЧЕТ"));
		daysAccountButton.setPreferredSize(new Dimension((int) (southPanel
				.getPreferredSize().getWidth() * 0.045), (int) (southPanel
				.getPreferredSize().getHeight())));

		daysAccountButton.setAutoSizedIcon(daysAccountButton,
				new LoadIcon().setIcons("ac9.png"));
		// daysAccountButton.setPreferredSize(new Dimension(50,50));
		// daysAccountButton.setAutoSizedIcon(daysAccountButton,
		// setIcons("ac8.png"));
		daysAccountButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				int yes_no = JOptionPane.showOptionDialog(null,
						"Желаете ли да направите финансов\n"
								+ " отчет до момента?", "",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, new String[] {
								"Да", "Не" }, // this is the array
						"default");
				if (yes_no != 0) {
					return;
				}
				JDialog jd = (JDialog) (SwingUtilities
						.getWindowAncestor(SearchFromProtokolTab.this));
				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));

				CreateBonFPrint bon = new CreateBonFPrint();
				ArrayList<String> commandList = bon.doZOrder();

				SellWithFiskalBonWorker sellWorker = new SellWithFiskalBonWorker(
						commandList, bon, jd);
				sellWorker.execute();
			}

		});
		// southPanel.add(invoiceNumberLabel);
		// southPanel.add(proformNumLabel);
		southPanel.add(sallerLabel);
		// southPanel.add(dateLabel);
		southPanel.add(sumLabel2);
		southPanel.add(sumFieldNoTax);
		southPanel.add(sumLabel);
		southPanel.add(sumField);
		southPanel.add(daysAccountButton);

		this.setLayout(new BorderLayout());
		this.add(northPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);

	}

	public static double getDanOsnova() {
		/*
		 * double d = 0; for (int row = 0; row <
		 * invoiceTableModel.getRowCount(); row++) { d +=
		 * Double.parseDouble(invoiceTableModel.getValueAt(row, 4) .toString());
		 * }
		 */
		return Double.parseDouble(sumField.getText());
	}

	public static void clear() {
		searchField.setText("");
		discountField.setText("");
		choiceDiscountButton.setSelected(true); // !!!! attention
		clientLabel.setName("");
		// proformNumLabel.setName("");
		// invoiceNumberLabel.setName("");
		sumFieldNoTax.setText("");
		sumField.setText("");
		paymenCombo.setSelectedIndex(0);
		invoiceTableModel.setRowCount(0);
		// moreProtokolsList.clear();
		protokolNumberSet.clear();
		INVOICE_CURRENT_CLIENT = "";

	}

	public static void calcFinalSum() {

		double valu = 0.0;

		for (int row = 0; row < invoiceTableModel.getRowCount(); row++) {
			valu += Double.parseDouble(invoiceTableModel.getValueAt(row, 4)
					.toString());
		}
		sumFieldNoTax.setText(String.format("%.2f", valu).replace(",", "."));
		// set ДДС
		valu *= 1.2;
		// set final value
		String finalValue = String.format("%.2f", valu).replace(",", ".");
		sumField.setText(finalValue);
	}
}
