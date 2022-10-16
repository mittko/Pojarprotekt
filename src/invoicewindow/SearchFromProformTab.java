package invoicewindow;

import invoice.InvoiceRenderer.CustomTableCellRenderer;
import invoice.SaveInInvoiceDBDialog;
import invoice.worker.ProformSearchWorker;
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
import java.util.HashSet;

public class SearchFromProformTab extends MainPanel {

	private JPanel northPanel;
	public static EditableField searchProformField;
	public static BevelLabel dateLabel;
	public static BevelLabel discountLabel;
	public static BevelLabel paymentLabel;
	private JPanel centerPanel;
	public static DefaultTableModel proformTableModel;
	private JTable invoiceTable2;
	private JPanel southPanel;
	public static BevelLabel clientLabel;
	public static BevelLabel proformNumLabel;
	public static BevelLabel sallerLabel;
	public static JTextField sumField;
	public static String PROTOKOL_NUMBER = "";
    private final HashSet<String> insertedNumbers = new HashSet<>();
	public SearchFromProformTab() {
		northPanel = new JPanel();// GradientPanel();
		northPanel.setPreferredSize(new Dimension(
				(int) (this.WIDTH * 1.0) - 20, (int) (this.HEIGHT * 0.09)));
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		searchProformField = new EditableField("Проформа \u2116", 9);
		searchProformField.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.3), (int) (northPanel
				.getPreferredSize().getHeight() * 0.7)));
		searchProformField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (searchProformField.getText().isEmpty()) { // no set number
					return;
				}
				if(insertedNumbers.contains(searchProformField.getText())) {
					JOptionPane.showMessageDialog(null,
							"Повтаряш се номер на проформа !");
					return;
				} else {
					insertedNumbers.add(searchProformField.getText());
				}
				ProformSearchWorker prfs = new ProformSearchWorker(); // in
				// proform
				prfs.execute();

			}

		});
		float labelHeight1 = (int) (northPanel.getPreferredSize().getHeight() * 0.7);

		dateLabel = new BevelLabel(labelHeight1);
		/*
		 * dateLabel.setPreferredSize(new Dimension(
		 * (int)(northPanel.getPreferredSize().getWidth() * 0.15),
		 * (int)(northPanel.getPreferredSize().getHeight() * 0.7)));
		 */
		dateLabel.setTitle(" Дата : ");
		dateLabel.setName(" ");

		discountLabel = new BevelLabel(labelHeight1);
		/*
		 * discountLabel.setPreferredSize(new Dimension(
		 * (int)(northPanel.getPreferredSize().getWidth() * 0.15),
		 * (int)(northPanel.getPreferredSize().getHeight() * 0.7)));
		 */
		discountLabel.setTitle("Отсъпка в % :    ");
		discountLabel.setName("");

		paymentLabel = new BevelLabel(labelHeight1);
		/*
		 * paymentLabel.setPreferredSize(new Dimension(
		 * (int)(northPanel.getPreferredSize().getWidth() * 0.33),
		 * (int)(northPanel.getPreferredSize().getHeight() * 0.7)));
		 */
		paymentLabel.setTitle("Начин на плащане : ");
		paymentLabel.setName(" ");

		TooltipButton rubberButton = new TooltipButton();
		// rubberButton.setPreferredSize(new Dimension(55,55));
		// rubberButton.setIcon(setIcons(eraserImage));
		rubberButton.setToolTipText(getHTML_Text("ИЗТРИЙ ДАННИТЕ"));
		rubberButton.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.045), (int) (northPanel
				.getPreferredSize().getHeight() * 0.8)));

		rubberButton.setAutoSizedIcon(rubberButton, new LoadIcon().setIcons(eraserImage));
		rubberButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (proformTableModel.getRowCount() == 0)
					return;

				int yes_no = JOptionPane.showOptionDialog(null,
						"Наистина ли искате да изтриете данните ?", "",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, new String[] {
								"Да", "Не" }, // this is the array
						"default");
				if (yes_no == 0) {
					insertedNumbers.clear();
					clear();
				}
			}

		});
		TooltipButton dbButton = new TooltipButton();
		// dbButton.setPreferredSize(new Dimension(55, 55));
		// dbButton.setIcon(setIcons(dbImage));
		dbButton.setToolTipText(getHTML_Text("ЗАПИШИ В БАЗА ДАННИ"));
		dbButton.setPreferredSize(new Dimension((int) (northPanel
				.getPreferredSize().getWidth() * 0.045), (int) (northPanel
				.getPreferredSize().getHeight() * 0.8)));

		dbButton.setAutoSizedIcon(dbButton, new LoadIcon().setIcons(dbImage));
		dbButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (proformTableModel.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "Няма въведени данни!");
					return;
				}

				SaveInInvoiceDBDialog save = new SaveInInvoiceDBDialog(INVOICE_PARENT,INVOICE_CHILD,
						PROTOKOL_NUMBER,
						clientLabel.getName(), paymentLabel.getName(),
						discountLabel.getName(), MyMath.round(
								Double.parseDouble(sumField.getText()), 2)
								+ "", // getDanOsnova()
						personName, MyGetDate.getReversedSystemDate(),
						"invoiceNameField.getText()",
						true, // invoice
						false, // proform
						true, // acquittance
						proformTableModel, null,// there is no invoice label,
						proformNumLabel, null // there is no acquittance label
				);
				JDialoger jDialoger = new JDialoger();
				jDialoger.setContentPane(save);
				jDialoger.Show();
			}

		});

		northPanel.add(searchProformField);
		//northPanel.add(discountLabel);
		northPanel.add(paymentLabel);
		northPanel.add(dateLabel);
		northPanel.add(rubberButton);
		northPanel.add(dbButton);

		centerPanel = new JPanel();

		proformTableModel = new DefaultTableModel(new String[] {
				"Вид на извършената услуга", "Мярка", "К-во", "Ед. Цена",
				"Стойност", "Отстъпка в %", "Контрагент", "Фактура по доставка" },
				0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 1;
			}
		};
		proformTableModel.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				switch (e.getType()) {
					case TableModelEvent.INSERT:
					case TableModelEvent.DELETE:
                        calcFinalSum();
						break;
				}
			}
		});
		invoiceTable2 = new JTable(proformTableModel);
		invoiceTable2.setDefaultRenderer(Object.class,
				new CustomTableCellRenderer(invoiceTable2));
		invoiceTable2.setRowHeight(MainPanel.getFontSize() + 15);
		// скрий колони отстъпка в %, фактура и контрагент
		invoiceTable2.getColumnModel().getColumn(5).setMinWidth(0);
		invoiceTable2.getColumnModel().getColumn(5).setMaxWidth(0);
		invoiceTable2.getColumnModel().getColumn(5).setWidth(0);
		invoiceTable2.getColumnModel().getColumn(5).setMinWidth(0);
		invoiceTable2.getColumnModel().getColumn(5).setMaxWidth(0);
		invoiceTable2.getColumnModel().getColumn(5).setWidth(0);
		invoiceTable2.getColumnModel().getColumn(6).setMinWidth(0);
		invoiceTable2.getColumnModel().getColumn(6).setMaxWidth(0);
		invoiceTable2.getColumnModel().getColumn(6).setWidth(0);
		invoiceTable2.getColumnModel().getColumn(7).setMinWidth(0);
		invoiceTable2.getColumnModel().getColumn(7).setMaxWidth(0);
		invoiceTable2.getColumnModel().getColumn(7).setWidth(0);

		// JTable rowTable = new CommonResources.RowNumberTable(invoiceTable2);
		// // ****

		JScrollPane scroll = new JScrollPane(invoiceTable2,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension((int) (this.WIDTH * 1.0) - 20,
				(int) (this.HEIGHT * 0.58)));

		// scroll.setRowHeaderView(rowTable); // ****
		// scroll.setCorner(JScrollPane.UPPER_LEFT_CORNER,// ****
		// rowTable.getTableHeader());// ****

		centerPanel.add(scroll);

		southPanel = new JPanel();
		southPanel.setPreferredSize(new Dimension(
				(int) (this.WIDTH * 1.0) - 20, (int) (this.HEIGHT * 0.065)));
		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 0));

		float labelHeight2 = (int) (southPanel.getPreferredSize().getHeight() * 0.85);
		clientLabel = new BevelLabel(labelHeight2);
		clientLabel.setTitle("Клиент : ");
		clientLabel.setName("");
		/*
		 * clientLabel.setPreferredSize(new Dimension(
		 * (int)(southPanel.getPreferredSize().getWidth() * 0.25),
		 * (int)(southPanel.getPreferredSize().getHeight() * 0.85)));
		 */

		proformNumLabel = new BevelLabel(labelHeight2);
		proformNumLabel.setTitle("Проформа \u2116 ");
		proformNumLabel.setName("");
		/*
		 * proformNumLabel.setPreferredSize(new Dimension(
		 * (int)(southPanel.getPreferredSize().getWidth() * 0.2),
		 * (int)(southPanel.getPreferredSize().getHeight() * 0.85)));
		 */

		sallerLabel = new BevelLabel(labelHeight2);
		sallerLabel.setTitle(Enums.Оператор.name() + ": ");
		sallerLabel.setName("");
		/*
		 * sallerLabel.setPreferredSize(new Dimension(
		 * (int)(southPanel.getPreferredSize().getWidth() * 0.2),
		 * (int)(southPanel.getPreferredSize().getHeight() * 0.85)));
		 */
		/*
		 * BevelLabel currentDateLabel = new BevelLabel();
		 * currentDateLabel.setTitle("Дата : ");
		 * currentDateLabel.setName(GetDate.getReversedSystemDate());
		 */
		BevelLabel sumLabel = new BevelLabel(labelHeight2);
		/*
		 * sumLabel.setPreferredSize(new Dimension(
		 * (int)(southPanel.getPreferredSize().getWidth() * 0.12),
		 * (int)(southPanel.getPreferredSize().getHeight() * 0.85)));
		 */
		sumLabel.setTitle("Крайна Цена с ДДС: ");
		sumLabel.setName("");

		sumField = new JTextField(4);
		sumField.setEditable(false);
		sumField.setForeground(Color.red);
		sumField.setFont(new Font(Font.DIALOG, Font.BOLD, getFontSize() + 10));
		sumField.setBorder(BorderFactory.createLoweredBevelBorder());
		sumField.setPreferredSize(new Dimension((int) (southPanel
				.getPreferredSize().getWidth() * 0.2), (int) (southPanel
				.getPreferredSize().getHeight() * 0.85)));

		southPanel.add(clientLabel);
		southPanel.add(proformNumLabel);
		southPanel.add(sallerLabel);
		// southPanel.add(dateLabel);
		southPanel.add(sumLabel);
		southPanel.add(sumField);

		this.setLayout(new BorderLayout());
		this.add(northPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
	}

	/*
	 * public static double getDanOsnova() { double d = 0; for(int row = 0;row <
	 * proformTableModel.getRowCount();row++) { d +=
	 * Double.parseDouble(proformTableModel.getValueAt(row, 4).toString()); }
	 * return d; }
	 */
	public static void clear() {
		searchProformField.setText("");
		discountLabel.setName("");
		paymentLabel.setName("");
		dateLabel.setName("");
		clientLabel.setName("");
		proformNumLabel.setName("");
		sallerLabel.setName("");
		sumField.setText("");
		proformTableModel.setRowCount(0);
	}
	private void calcFinalSum() {
		double valu = 0.0;
		for (int row = 0; row < proformTableModel.getRowCount(); row++) {
			valu += Double.parseDouble(proformTableModel.getValueAt(row, 4)
					.toString());
		}
		//sumFieldNoTax.setText(String.format("%.2f", valu).replace(",", "."));
		// set ДДС
		valu *= 1.2;
		// set final value
		String finalValue = String.format("%.2f", valu).replace(",", ".");
		sumField.setText(finalValue);
	}
}
