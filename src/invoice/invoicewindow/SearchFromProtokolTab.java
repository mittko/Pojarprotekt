package invoice.invoicewindow;
import acquittance.windows.SaveInAcquittanceDBDialog;
import http.RequestCallback;
import http.sklad.GetArtikulService;
import invoice.fiskal.CreateBonFPrint;
import invoice.renderers.CustomTableCellRenderer;
import invoice.SaveInInvoiceDBDialog;
import invoice.sklad.SkladArtiklulPanel;
import invoice.sklad.SkladArtikulFrame;
import invoice.workers.ProtokolSearchWorker;
import invoice.workers.SellWithFiskalBonWorker;
import clients.NewClient;
import models.ArtikulModel;
import mydate.MyGetDate;
import run.JDialoger;
import utils.*;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

//import utility.CheckButtonArtikuli;

public class SearchFromProtokolTab extends MainPanel {

	private static EditableField searchField;

	private final EditableField dateField;

	public final static ImageIcon selectedIcon = new LoadIcon().setIcons(yesImage);
	public static JButton registrationVatCheckBox = new JButton();

	public static JTextField discountField;

	public static DiscountButtonArtikuli choiceDiscountButton;

	private static JComboBox<String> paymenCombo;

	public static BevelLabel clientLabel;

	public static DefaultTableModel invoiceTableModel = null;
	private final MyTable invoiceTable;

	private final BevelLabel invoiceNumberLabel = null;

	private final BevelLabel proformNumLabel = null;

	private static JTextField sumFieldNoTax;// no tax
	public static JTextField sumField; // with tax

	public static String INVOICE_CURRENT_CLIENT;

	public static HashSet<String> protokolNumberSet = new HashSet<String>();

	private String protokolNumber = "";

	public SearchFromProtokolTab(final boolean isGrey, final String prtNumber) {

		INVOICE_CURRENT_CLIENT = "";
		protokolNumberSet.clear();

		JPanel northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		northPanel.setPreferredSize(new Dimension(
				(int) (this.WIDTH * 1.0) - 20, (int) (this.HEIGHT * 0.1)));

		searchField = new EditableField("Протокол №", 9);
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


		if(prtNumber != null) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					searchField.requestFocusInWindow();
					searchField.requestFocus();
					searchField.setText(prtNumber);
					try {
						Robot robot = new Robot();
						robot.keyPress(KeyEvent.VK_ENTER);
					} catch (AWTException e) {
						throw new RuntimeException(e);
					}

				}
			});
		}

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
				.getPreferredSize().getHeight() * 0.75)));

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
		skladButton.setToolTipText(getHTML_Text("ИЗБЕРИ АРТИКУЛИ"));
		skladButton.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.045), (int) (northPanel
				.getPreferredSize().getHeight() * 0.75)));

		skladButton.setAutoSizedIcon(skladButton, new LoadIcon().setIcons(isGrey ? greyArtikuliImage : artikuliImage));
		skladButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (invoiceTableModel.getRowCount() == 0)
					return;
				JDialog jd = (JDialog) (SwingUtilities
						.getWindowAncestor(SearchFromProtokolTab.this));

				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));

				SkladArtikulFrame frame = null;
				if(isGrey) {
					frame = new SkladArtiklulPanel(
							invoiceTableModel/*
					 * , sumField
					 */, discountField.getText().isEmpty() ? 0 : Double
							.parseDouble(discountField.getText()),
							choiceDiscountButton.isSelected()) {
						@Override
						public boolean isGrey() {
							return true;
						}
					} ;

				} else {
					frame = new SkladArtiklulPanel(
							invoiceTableModel/*
					 * , sumField
					 */, Double.parseDouble(discountField
							.getText()), choiceDiscountButton.isSelected());
				}

				GetArtikulService service = new GetArtikulService();
				SkladArtikulFrame finalFrame = frame;
				service.getArtikuls(frame.isGrey(),false, new RequestCallback() {
					@Override
					public <T> void callback(List<T> objects) {
						finalFrame.loadArtikuls((ArrayList<ArtikulModel>) objects);
					}
				});
//				LoadAllArtikulsFromInvoiceWorker loader = new LoadAllArtikulsFromInvoiceWorker(
//						frame,
//						jd);
//				loader.execute();

				JDialoger jDialog = new JDialoger();
				jDialog.setContentPane(frame);
				jDialog.setTitle("Артикули");
				jDialog.setResizable(false);
				jDialog.Show();
			}

		});

		TooltipButton rubberButton = new TooltipButton();
		rubberButton.setToolTipText(getHTML_Text("ИЗТРИЙ ДАННИТЕ"));
		rubberButton.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.045), (int) (northPanel
				.getPreferredSize().getHeight() * 0.75)));

		rubberButton.setAutoSizedIcon(rubberButton, new LoadIcon().setIcons(eraserImage));
		rubberButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if(invoiceTableModel.getRowCount() == 0) return;

				int yes_no = JOptionPane.showOptionDialog(null,
						"Наистина ли искате да изтриете данните ?", "",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, new String[] {
								"Да", "Не" }, // this is the array
						"default");

				if (yes_no == 0) {
					invoiceTableModel.setRowCount(0);
                    clear();
				}
			}

		});

		TooltipButton dbButton = new TooltipButton();

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

				if(!isGrey) {
					SaveInInvoiceDBDialog save = new SaveInInvoiceDBDialog(
							protokolNumber,
							INVOICE_CURRENT_CLIENT,
							paymenCombo.getSelectedItem().toString(),
							discountField.getText(),
							MyMath.round(getDanOsnova(), 2) + "",
							personName,
							dateField.getText(),
							true, // invoice
							false, // proform
							true, // acquittance
							invoiceTableModel,
							invoiceNumberLabel,
							proformNumLabel,
							null,
							registrationVatCheckBox.isSelected());

					// clear protokol number to avoid mess
					protokolNumber = "";

					JDialoger jDialoger = new JDialoger();
					jDialoger.setContentPane(save);
					jDialoger.Show();
				} else {
					SaveInAcquittanceDBDialog save = new SaveInAcquittanceDBDialog(
                            // here protokol
							// number is not
							// needed and is
							// marked as -
							// (without
							// protokol)
							INVOICE_CURRENT_CLIENT, paymenCombo
							.getSelectedItem().toString(), discountField
							.getText(), MyMath.round(getDanOsnova(), 2) + "",
							personName,
							MyGetDate.getReversedSystemDate(),
							// invoice
							// proform
							invoiceTableModel,  // there is invoice num label
							// there is no proform num label
							null); // acquittance num label
					JDialoger jDialoger = new JDialoger();
					jDialoger.setContentPane(save);
					jDialoger.Show();
				}

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

		invoiceTable = new MyTable(invoiceTableModel) {
			@Override
			public void onTableChanged(TableModelEvent tableModelEvent) {
				super.onTableChanged(tableModelEvent);

				switch (tableModelEvent.getType()) {
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

			@Override
			public void removeAt(int row) {
				super.removeAt(row);
				invoiceTableModel.removeRow(row);
				if(invoiceTableModel.getRowCount() == 0) {
					clear();
				}
			}
		};

		invoiceTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				Point point = me.getPoint();
				int currentRow = invoiceTable.rowAtPoint(point);
				invoiceTable.setRowSelectionInterval(currentRow, currentRow);
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

		JScrollPane scroll = new JScrollPane(invoiceTable,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension((int) (this.WIDTH * 1.0) - 20,
				(int) (this.HEIGHT * 0.57)));

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
				.getPreferredSize().getHeight() * 0.75)));

		clientLabel = new BevelLabel();
		clientLabel.setTitle("Клиент : ");
		clientLabel.setName("");

		registrationVatCheckBox.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.045), (int) (northPanel
				.getPreferredSize().getHeight() * 0.75)));

		JLabel registrationVatLabel = new JLabel("Регистрация по ДДС");

		northPanel.add(searchField);
		northPanel.add(dateField);
		northPanel.add(registrationVatLabel);
		northPanel.add(registrationVatCheckBox);
		northPanel.add(discountField);
		northPanel.add(choiceDiscountButton);
		northPanel.add(paymenCombo);
		northPanel.add(skladButton);
		northPanel.add(rubberButton);
		northPanel.add(dbButton);
		northPanel.add(newClientButton);

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


		float labelHeight = (int) (southPanel.getPreferredSize().getHeight() * 0.8);

		BevelLabel invoiceNumLabel = new BevelLabel(labelHeight);
		invoiceNumLabel.setTitle("");
		invoiceNumLabel.setName("");

		BevelLabel sallerLabel = new BevelLabel(labelHeight);

		sallerLabel.setTitle(Enums.Operator.name() + ": ");
		sallerLabel.setName(personName);

		BevelLabel sumLabel = new BevelLabel(labelHeight);
		sumLabel.setTitle("");
		sumLabel.setName("Сума с ДДС : ");


		BevelLabel sumLabel2 = new BevelLabel(labelHeight);
		sumLabel2.setTitle("");
		sumLabel2.setName("Сума без ДДС : ");


		TooltipButton daysAccountButton = new TooltipButton();
		daysAccountButton
				.setToolTipText(getHTML_Text("НАПРАВИ ФИСКАЛЕН ОТЧЕТ"));
		daysAccountButton.setPreferredSize(new Dimension((int) (southPanel
				.getPreferredSize().getWidth() * 0.045), (int) (southPanel
				.getPreferredSize().getHeight())));

		daysAccountButton.setAutoSizedIcon(daysAccountButton,
				new LoadIcon().setIcons("ac9.png"));

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

		southPanel.add(clientLabel);
		southPanel.add(sallerLabel);
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
		invoiceTableModel.setRowCount(0);
		searchField.setText("");
		discountField.setText("");
		choiceDiscountButton.setSelected(true); // !!!! attention
		clientLabel.setName("");
		sumFieldNoTax.setText("");
		sumField.setText("");
		paymenCombo.setSelectedIndex(0);
		protokolNumberSet.clear();
		INVOICE_CURRENT_CLIENT = "";
		registrationVatCheckBox.setIcon(null);

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


	public static void switchRegistrationVat() {
		if ((registrationVatCheckBox.isSelected())) {
			setDynamicSizedIcon(registrationVatCheckBox, selectedIcon);
		} else {
			registrationVatCheckBox.setIcon(null);
		}
	}
}
