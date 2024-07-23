package invoice.invoicewindow;

import db.Client.FirmTable;
import invoice.fiskal.CreateBonFPrint;
import invoice.renderers.CustomTableCellRenderer;
import invoice.SaveInInvoiceDBDialog;
import invoice.sklad.SkladArtiklulPanel;
import invoice.sklad.workers.LoadAllArtikulsFromInvoiceWorker;
import invoice.workers.GetDiscountWorker;
import invoice.workers.SellWithFiskalBonWorker;
import mydate.MyGetDate;
import run.JDialoger;
import utils.*;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ArtikulTab extends MainPanel {

	private final ClientsListComboBox2 clientCombo;
	private static JTextField discountField;
	private static DiscountButtonArtikuli choiceDiscountButton;

	private static JButton registrationVatCheckBox;
	final ImageIcon selectedIcon = new LoadIcon().setIcons(yesImage);
	private static JComboBox<String> paymentCombo;

	private final TooltipButton skladButton;

	public static DefaultTableModel dftm;
	private final MyTable onlyArticulsTable;
	private static JTextField sumFieldNoTax;
	private static JTextField sumField;


	@SuppressWarnings("unchecked")
	public ArtikulTab() {

		JPanel northPanel = new JPanel();// GradientPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		northPanel.setPreferredSize(new Dimension(
				(int) (this.WIDTH * 1.0) - 20, (int) (this.HEIGHT * 0.1)));

		clientCombo = new ClientsListComboBox2();
		clientCombo.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.2), (int) (northPanel
				.getPreferredSize().getHeight() * 0.5)));
		clientCombo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getItem().toString().length() > 0) {
					skladButton.setEnabled(true);
				} else {
					skladButton.setEnabled(false);

					clear();
				}
			}

		});
		clientCombo.getEditor().getEditorComponent()
				.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent ke) {
						if (!skladButton.isEnabled()) {
							return;
						}
						if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
							try {
								String discount = new GetDiscountWorker(
										clientCombo.getSelectedItem()
												.toString()).doInBackground();
								discountField.setText(discount);
								choiceDiscountButton.setDefaultIcon();

								String registrationVat = FirmTable.getHasFirmVatRegistration(
										clientCombo.getSelectedItem().toString());
								registrationVatCheckBox.setSelected(
										registrationVat.equals("��"));
								if ((registrationVatCheckBox.isSelected())) {
									setDynamicSizedIcon(registrationVatCheckBox, selectedIcon);
								} else {
									registrationVatCheckBox.setIcon(null);
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					}
				});
		discountField = new JTextField(2);
		discountField.setForeground(Color.red);
		discountField.setFont(new Font(Font.DIALOG, Font.BOLD,
				getFontSize() + 10));
		discountField.setEditable(false);
		discountField.setBorder(BorderFactory.createLoweredBevelBorder());
		discountField.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.05), (int) (northPanel
				.getPreferredSize().getHeight() * 0.75)));

		paymentCombo = new JComboBox<String>(new String[] { "����� �� �������",
				"� ����", "�� ������ ���" });
		paymentCombo.setRenderer(new ComboRenderer());
		paymentCombo.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.2), (int) (northPanel
				.getPreferredSize().getHeight() * 0.5)));

		skladButton = new TooltipButton();
		skladButton.setEnabled(false);
		skladButton.setToolTipText(getHTML_Text("������ ��������"));
		skladButton.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.045), (int) (northPanel
				.getPreferredSize().getHeight() * 0.75)));

		skladButton.setAutoSizedIcon(skladButton, new LoadIcon().setIcons(artikuliImage));
		skladButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (discountField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"�� � �������� ��������!");
					return;
				}
				SkladArtiklulPanel skladArtikulPanel = new SkladArtiklulPanel(
						dftm/*
							 * , sumField
							 */, discountField.getText().isEmpty() ? 0 : Double
						.parseDouble(discountField.getText()),
						choiceDiscountButton.isSelected());

				LoadAllArtikulsFromInvoiceWorker loader = new LoadAllArtikulsFromInvoiceWorker(
						skladArtikulPanel,
						new JDialog());
				loader.execute();

				JDialoger jDialog = new JDialoger();
				jDialog.setContentPane(skladArtikulPanel);
				jDialog.setTitle("��������");
				jDialog.setResizable(false);
				jDialog.Show();
			}

		});

		TooltipButton dbButton = new TooltipButton();
		dbButton.setToolTipText(getHTML_Text("������ � ���� �����"));
		dbButton.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.045), (int) (northPanel
				.getPreferredSize().getHeight() * 0.75)));

		dbButton.setAutoSizedIcon(dbButton, new LoadIcon().setIcons(dbImage));
		dbButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (dftm.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "���� ������� �����!");
					return;
				}
				if (discountField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"�� � �������� ��������!");
					return;
				}
				if (paymentCombo.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(null,
							"�� � ������ ����� �� �������!");
					return;
				}
				if (sumField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"�� � ����������� ������ ����!");
					return;
				}

				SaveInInvoiceDBDialog save = new SaveInInvoiceDBDialog(
						"-",// here protokol
						// number is not
						// needed and is
						// marked as -
						// (without
						// protokol)
						clientCombo.getSelectedItem().toString(), paymentCombo
						.getSelectedItem().toString(), discountField
						.getText(), MyMath.round(getDanOsnova(), 2)+"",
						personName, MyGetDate
						.getReversedSystemDate(), true, // invoice
						false, // proform
						true, // acquittance
						dftm, null, // there is invoice num label
						null, // there is no proform num label
						null,
						registrationVatCheckBox.isSelected()); // acquittance num label
				JDialoger jDialoger = new JDialoger();
				jDialoger.setContentPane(save);
				jDialoger.Show();

			}

		});

		TooltipButton eraserButton = new TooltipButton();
		eraserButton.setToolTipText(getHTML_Text("������  �������"));
		eraserButton.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.045), (int) (northPanel
				.getPreferredSize().getHeight() * 0.75)));

		eraserButton.setAutoSizedIcon(eraserButton, new LoadIcon().setIcons(eraserImage));
		// eraserButton.setIcon(setIcons(eraserImage));
		eraserButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (dftm.getRowCount() == 0)
					return;

				int yes_no = JOptionPane.showOptionDialog(null,
						"�������� �� ������ �� �������� ������� ?", "",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, new String[] {
								"��", "��" }, // this is the array
						"default");

				if (yes_no == 0) {
					dftm.setRowCount(0);
					clear();
				}
			}

		});


		JPanel centerPanel = new JPanel();

		dftm = new DefaultTableModel(new String[] {
				"��� �� ����������� ������", "�����", "�-��", "��. ����",
				"��������", "�������� � %", "����������", "������� �� ��������" },
				0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 1 || column == 5;
			}
		};


		onlyArticulsTable = new MyTable(dftm) {
			@Override
			public void onTableChanged(TableModelEvent tableModelEvent) {
				super.onTableChanged(tableModelEvent);


				switch (tableModelEvent.getType()) {
					case TableModelEvent.INSERT:
					case TableModelEvent.DELETE:
						ArrayList<Double> totals = new ArrayList<>();
						for(int row = 0;row < dftm.getRowCount();row++) {
							double total = Double.parseDouble(dftm.getValueAt(row,3).toString());
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
				dftm.removeRow(onlyArticulsTable.getSelectedRow());
				if(dftm.getRowCount() == 0) {
					clear();
				}
			}
		};
		// ����� �o�o�� ������� � �o��������
		onlyArticulsTable.getColumnModel().getColumn(6).setMinWidth(0);
		onlyArticulsTable.getColumnModel().getColumn(6).setMaxWidth(0);
		onlyArticulsTable.getColumnModel().getColumn(6).setWidth(0);
		onlyArticulsTable.getColumnModel().getColumn(7).setMinWidth(0);
		onlyArticulsTable.getColumnModel().getColumn(7).setMaxWidth(0);
		onlyArticulsTable.getColumnModel().getColumn(7).setWidth(0);

		onlyArticulsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				Point point = me.getPoint();
				int currentRow = onlyArticulsTable.rowAtPoint(point);
				onlyArticulsTable.setRowSelectionInterval(currentRow,
						currentRow);
			}
		});
		onlyArticulsTable.setDefaultRenderer(Object.class,
				new CustomTableCellRenderer(onlyArticulsTable));
		onlyArticulsTable.setRowHeight(MainPanel.getFontSize() + 15);


		JScrollPane scroll = new JScrollPane(onlyArticulsTable,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension((int) (this.WIDTH * 1.0) - 20,
				(int) (this.HEIGHT * 0.64)));
		centerPanel.add(scroll);

		JPanel southPanel = new JPanel();
		southPanel.setPreferredSize(new Dimension(
				(int) (this.WIDTH * 1.0) - 20, (int) (this.HEIGHT * 0.07)));
		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		sumFieldNoTax = new JTextField(4);
		sumFieldNoTax.setForeground(Color.green.darker());
		sumFieldNoTax.setFont(new Font(Font.DIALOG, Font.BOLD,
				getFontSize() + 10));
		sumFieldNoTax.setEditable(false);
		sumFieldNoTax.setBorder(BorderFactory.createLoweredBevelBorder());
		sumFieldNoTax.setPreferredSize((new Dimension((int) (southPanel
				.getPreferredSize().getWidth() * 0.2), (int) (southPanel
				.getPreferredSize().getHeight() * 0.8))));

		sumField = new JTextField(4);
		sumField.setForeground(Color.red);
		sumField.setFont(new Font(Font.DIALOG, Font.BOLD, getFontSize() + 10));
		sumField.setEditable(false);
		sumField.setBorder(BorderFactory.createLoweredBevelBorder());
		sumField.setPreferredSize((new Dimension((int) (southPanel
				.getPreferredSize().getWidth() * 0.2), (int) (southPanel
				.getPreferredSize().getHeight() * 0.8))));

		choiceDiscountButton = new DiscountButtonArtikuli(dftm, sumField,
				sumFieldNoTax);
		choiceDiscountButton.setToolTipText(getHTML_Text("������ ��������"));
		choiceDiscountButton.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.045), (int) (northPanel
				.getPreferredSize().getHeight() * 0.75)));

        registrationVatCheckBox = new JButton();
//		registrationVatCheckBox.setSelected(false);
//		registrationVatCheckBox.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if(!registrationVatCheckBox.isSelected()) {
//					setDynamicSizedIcon(registrationVatCheckBox, selectedIcon);
//					registrationVatCheckBox.setSelected(true);
//				} else {
//					registrationVatCheckBox.setIcon(null);
//					registrationVatCheckBox.setSelected(false);
//				}
//			}
//		});
		registrationVatCheckBox.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.045), (int) (northPanel
				.getPreferredSize().getHeight() * 0.75)));

		JLabel registrationVatLabel = new JLabel("����������� �� ���");

		northPanel.add(clientCombo);
		northPanel.add(registrationVatLabel);
		northPanel.add(registrationVatCheckBox);
		northPanel.add(discountField);
		northPanel.add(choiceDiscountButton);
		northPanel.add(paymentCombo);
		northPanel.add(skladButton);
		northPanel.add(eraserButton);
		northPanel.add(dbButton);


		float labelHeight = (int) (southPanel.getPreferredSize().getHeight() * 0.8);

		BevelLabel sallerLabel = new BevelLabel(labelHeight);

		sallerLabel.setTitle(Enums.Operator.name() +  ": ");
		sallerLabel.setName(personName);

		BevelLabel dateLabel = new BevelLabel(labelHeight);

		dateLabel.setTitle("���� : ");
		dateLabel.setName(MyGetDate.getReversedSystemDate());

		BevelLabel sumLabel1 = new BevelLabel(labelHeight);
		sumLabel1.setTitle("");
		sumLabel1.setName("���� ��� ��� : ");

		BevelLabel sumLabel2 = new BevelLabel(labelHeight);
		sumLabel2.setTitle("");
		sumLabel2.setName("���� � ��� : ");

		southPanel.add(sallerLabel);
		southPanel.add(dateLabel);
		southPanel.add(sumLabel1);
		southPanel.add(sumFieldNoTax);
		southPanel.add(sumLabel2);
		southPanel.add(sumField);

		TooltipButton daysAccountButton = new TooltipButton();
		daysAccountButton
				.setToolTipText(getHTML_Text("������� �������� �����"));
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
						"������� �� �� ��������� ��������\n"
								+ " ����� �� �������?", "",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, new String[] {
								"��", "��" }, // this is the array
						"default");
				if (yes_no != 0) {
					return;
				}
				JDialog jd = (JDialog) (SwingUtilities
						.getWindowAncestor(ArtikulTab.this));
				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));

				CreateBonFPrint bon = new CreateBonFPrint();
				ArrayList<String> commandList = bon.doZOrder();

				SellWithFiskalBonWorker sellWorker = new SellWithFiskalBonWorker(
						commandList, bon, jd);
				sellWorker.execute();
			}

		});
		southPanel.add(daysAccountButton);

		this.setLayout(new BorderLayout());
		this.add(northPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
	}

	public static double getDanOsnova() {
		/*
		 * double d = 0; for(int row = 0;row < dftm.getRowCount();row++) { d +=
		 * Double.parseDouble(dftm.getValueAt(row, 4).toString()); }
		 */
		return Double.parseDouble(sumField.getText());
	}

	public static void clear() {
		// acquittanceNumLabel.setName("");
		dftm.setRowCount(0);
		sumFieldNoTax.setText("");
		sumField.setText("");
		discountField.setText("");
		choiceDiscountButton.setDefaultIcon();
		paymentCombo.setSelectedIndex(0);
		registrationVatCheckBox.setIcon(null);
	}

	public void calcFinalSum() {
		double valu = 0.0;
		for (int row = 0; row < dftm.getRowCount(); row++) {
			valu += Double.parseDouble(dftm.getValueAt(row, 4).toString());
		}
		sumFieldNoTax.setText(String.format("%.2f", valu).replace(",", "."));
		// set ���
		valu *= 1.2;
		// set final value
		String finalValue = String.format("%.2f", valu).replace(",", ".");
		sumField.setText(finalValue);
	}

}
