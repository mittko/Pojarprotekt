package invoice.Sklad;

import admin.Artikul.Workers.BiggestPriceForInvoiceWorker;
import invoice.InvoiceEditors.TableCellEditors;
import invoice.InvoiceRenderer.AutoSortRenderer;
import utility.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SkladArtikulFrame extends MainPanel {

	public static JTable skladTable = null;
	public static DefaultTableModel skladModel = null;
	private final double clientDiscountInPercentage;
	public static JTextField moveScrollField = null;
	private final boolean isDiscountSelected;
	private final DefaultTableModel invoiceTableModel;
	public static ArrayList<Object[]> DATA_LIST = null;

	public SkladArtikulFrame(final DefaultTableModel invoiceTableModel
	/*
	 * final JTextField sumFields
	 */, final double clientDiscount, final boolean isDiscountSelect) {

		this.invoiceTableModel = invoiceTableModel;
		this.clientDiscountInPercentage = clientDiscount;
		this.isDiscountSelected = isDiscountSelect;

		JPanel basePanel = new JPanel();
		basePanel.setLayout(new BorderLayout());

		DATA_LIST = new ArrayList<Object[]>();

		skladModel = new DefaultTableModel(new String[] { "Артикули",
				"Налични", "М.ед", "Ед. Цена", "Брой", "V", "Контрагент",
				"Фактура" }, 0) {

			/**
					 * 
					 */
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			@Override
			public Class getColumnClass(int column) {
				switch (column) {
				case 0:
					return String.class;
				case 1:
					return String.class;
				case 2:
					return String.class;
				case 3:
					return String.class;
				case 4:
					return String.class;
				case 5:
					return Boolean.class;
				case 6:
					return String.class;
				case 7:
					return String.class;
				default:
					return String.class;
				}
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 4 || column == 5) {
					return true;
				}
				return false;
			}
		};
		JTextField helpTextField = new JTextField();
		helpTextField.setCaret(new RedCaret());

		skladTable = new JTable(skladModel);

		skladTable.getColumnModel().getColumn(4)
				.setCellEditor(new TableCellEditors(helpTextField));

		skladTable.setDefaultRenderer(Object.class, new AutoSortRenderer(
				skladTable));
		skladTable.setRowHeight(MainPanel.getFontSize() + 15);

		skladTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent me) {
				if (skladTable.getSelectedColumn() == 5) {
					Boolean isSelected = (Boolean) skladTable.getValueAt(
							skladTable.getSelectedRow(), 5);
					if (isSelected) {
						skladTable.editCellAt(skladTable.getSelectedRow(), 4);

						Component editor = skladTable.getEditorComponent(); // cell
																			// editor
						editor.requestFocusInWindow();
					} else {
						skladTable.setValueAt("", skladTable.getSelectedRow(),
								4);// / clear cell content
					}
				}
			}
		});

		// JTable rowTable = new CommonResources.RowNumberTable(skladTable); //
		// ****

		final JScrollPane scroll = new JScrollPane(skladTable,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension(this.WIDTH - 50, this.HEIGHT / 2));

		// scroll.setRowHeaderView(rowTable); // ****
		// scroll.setCorner(JScrollPane.UPPER_LEFT_CORNER,// ****
		// rowTable.getTableHeader());// ****

		JPanel northPanel = new JPanel();// GradientPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		northPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		TooltipButton addButton = new TooltipButton("Магазин");
		addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		addButton.setPreferredSize(new Dimension(fm.stringWidth(addButton
				.getText()) + 40, 50));
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				addArtikul();
			}

		});
		TooltipButton exportToExcellButton = new TooltipButton();
		/*
		 * exportToExcellButton.setPreferredSize(new
		 * Dimension(fm.stringWidth(exportToExcellButton .getText()) + 40, 50));
		 */
		exportToExcellButton.setPreferredSize(new Dimension((int) (addButton
				.getPreferredSize().getWidth() * 0.6), (int) (addButton
				.getPreferredSize().getHeight())));
		;
		exportToExcellButton.setToolTipText(getHTML_Text("ЗАПИШИ В EXCEL"));
		exportToExcellButton.setAutoSizedIcon(exportToExcellButton,
				new LoadIcon().setIcons(excellImage));
		// exportToExcellButton.addActionListener(new ActionListener() {

		// @Override
		// void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		// JDialog jd = (JDialog) SwingUtilities
		// .getWindowAncestor(SkladArtikulFrame.this);
		// jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		// ExportToExcellSkladWorker excellWorker = new
		// ExportToExcellSkladWorker(
		// skladTable, skladModel, "Артикули.xls", jd);
		// excellWorker.execute();
		// }

		// });

		moveScrollField = new EditableField("Търсене ", 20) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Font getFont() {
				return new Font(Font.MONOSPACED, Font.ITALIC,
						MainPanel.getFontSize() + 10);
			}
		};
		// moveScrollField.setPreferredSize(new Dimension(800, 50));
		moveScrollField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {

				skladModel.setRowCount(0);
				String b = moveScrollField.getText().toLowerCase();
				if (b.length() > 0) {
					for (Object[] obj : DATA_LIST) {
						String a = obj[0].toString().toLowerCase();
						if (a.startsWith(b) || a.contains(b)) {
							skladModel.addRow(obj);
						}
					}
				}
			}
		});
		// northPanel.add(exportToExcellButton);
		northPanel.add(addButton);

		northPanel.add(moveScrollField);
		basePanel.add(northPanel, BorderLayout.NORTH);
		basePanel.add(scroll, BorderLayout.CENTER);

		this.add(basePanel);
	}

	double getDiscount(double val, double discount) {
		return (val * (discount / 100));
	}

	public void addArtikul() {
		for (int row = 0; row < skladTable.getRowCount(); row++) {
			if ((Boolean) skladTable.getValueAt(row, 5)) {

				TableCellEditor cellEditor = skladTable.getColumnModel()
						.getColumn(4).getCellEditor();
				cellEditor.stopCellEditing();

				String artikul = skladTable.getValueAt(row, 0).toString(); // artikul

				String skladQuantity = skladTable.getValueAt(row, 1).toString();

				/*
				 * String med = skladTable.getValueAt( row, 2).toString(); //
				 * мерна // единица
				 */

				// String value = "";
				// try {
				// value = skladTable.getValueAt(row, 3).toString();
				// } catch (Exception ex) {
				// JOptionPane.showMessageDialog(null, ex.toString());
				// return;
				// }
				String quantity = skladTable.getValueAt(row, 4).toString();

				double val = new BiggestPriceForInvoiceWorker(artikul)
						.doInBackground();
				if (val == 0) {
					// ако се получи грешка при взимане на цената от база данни
					// се взема текущата от показаната в таблицата
					val = Double.parseDouble(skladTable.getValueAt(row, 3)
							.toString());
				}

				double discountInSum = getDiscount(val,
						clientDiscountInPercentage);
				if (isDiscountSelected) {
					val = val - discountInSum;
				}

				double userQ = 0;

				try {
					userQ = Double.parseDouble(quantity);
					double availableQ = Double.parseDouble(skladQuantity);

					/*
					 * if (availableQ <= 0) {
					 * JOptionPane.showMessageDialog(null,
					 * "Артикулът не е наличен"); return; }
					 */
					if (userQ <= 0) {
						JOptionPane
								.showMessageDialog(null,
										"Не може да въвеждате "
												+ "отрицателни \n стойности "
												+ "или 0 !!! (  " + (row + 1)
												+ "ред )");
						return;
					}

					  if (userQ > availableQ) {
					  JOptionPane.showMessageDialog(null,
					  "Броят надхвъля наличноста в склада! ( " + (row + 1) +
					  " ред)"); return; }

				} catch (Exception ex) {
					JOptionPane
							.showMessageDialog(null,
									"Не е въведено количество! (" + (row + 1)
											+ " ред)");
					return;
				}

				String allSum = "";
				try {
					allSum = MyMath.round(
							Double.parseDouble(quantity) * MyMath.round(val, 2),
							2) + "";
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null,"" + ex.getMessage());
					return;
				}

				// check for dublicate artikuls
				String kontragent = skladTable.getValueAt(row, 6).toString();
				String invoiceByKontragent = skladTable.getValueAt(row, 7)
						.toString();

				 for (int i = 0; i < invoiceTableModel.getRowCount(); i++) {
					 if (invoiceTableModel.getValueAt(i, 0).toString()
					 .equals(artikul) && invoiceTableModel.getValueAt(i,6).toString().equals(kontragent)
					 && invoiceTableModel.getValueAt(i, 7).toString().equals(invoiceByKontragent)) {
					 JOptionPane.showMessageDialog(null,
					 "Този артикул вече е въведен! > " + artikul + " " + kontragent + " " + invoiceByKontragent);
					 return;
					 }
				 }


//				invoiceTableModel.addRow(new Object[] { artikul,
//						userQ < 2 ? "брой" : "броя", quantity,
//						MyMath.round(val, 2), allSum,
//						MyMath.round(discountInSum, 2), kontragent,
//						invoiceByKontragent });  OLD LOGIC THAT WORK !!!

				invoiceTableModel.addRow(new Object[] { artikul,
						userQ < 2 ? "брой" : "броя", quantity,
						MyMath.round(val, 2), allSum,
						clientDiscountInPercentage, kontragent,
						invoiceByKontragent });
				// clear selected values
				skladTable.setValueAt("", row, 4);
				skladTable.setValueAt(false, row, 5);

			}
		}
		goodbyeCruelWorld();
	}

	private void goodbyeCruelWorld() {
		JDialog jd = (JDialog) SwingUtilities
				.getWindowAncestor(SkladArtikulFrame.this);
		jd.dispose();
	}

}