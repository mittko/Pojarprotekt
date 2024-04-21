package invoice.Sklad;

import admin.аrtikul.Workers.BiggestPriceForInvoiceWorker;
import db.аrtikul.Artikuli_DB;
import invoice.InvoiceEditors.TableCellEditors;
import invoice.InvoiceRenderer.AutoSortRenderer;
import utils.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public abstract class SkladArtikulFrame extends MainPanel implements ILoadArtikuls {

	public JTable skladTable;
	public DefaultTableModel skladModel;

	public static JTextField moveScrollField = null;

	public static ArrayList<Object[]> DATA_LIST = null;

	public SkladArtikulFrame() {

		JPanel basePanel = new JPanel();
		basePanel.setLayout(new BorderLayout());

		DATA_LIST = new ArrayList<>();

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
				if (column == 5) {
					return Boolean.class;
				}
				return String.class;
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 4 || column == 5;
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

		final JScrollPane scroll = new JScrollPane(skladTable,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension(this.WIDTH - 50, this.HEIGHT / 2));


		JPanel northPanel = new JPanel();
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
				//addArtikul();

				addArtikuls(skladTable);
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

				// search artikul by barcode if no items by name are found

				if(ke.getKeyCode() == KeyEvent.VK_ENTER && skladModel.getRowCount() == 0) {
					if (skladModel.getRowCount() == 0) {
						for (Object[] obj : DATA_LIST) {
							if (moveScrollField.getText().equals(obj[8])) {
								skladModel.addRow(obj);
							}
						}
					}
				}

			}
		});

		northPanel.add(addButton);

		northPanel.add(moveScrollField);
		basePanel.add(northPanel, BorderLayout.NORTH);
		basePanel.add(scroll, BorderLayout.CENTER);

		this.add(basePanel);
	}

	protected double getDiscount(double val, double discount) {
		return (val * (discount / 100));
	}

	public abstract void addArtikuls(JTable skladTable);

	@Override
	public void loadArtikuls(ArrayList<Object[]> result) {
		for (Object[] objects : result) {
			// set in the table imediatelly
//						 String newValue = String.format(Locale.ROOT,
//								 "%.2f",
//								 Double.parseDouble(result.get(row)[3].toString().replace(",", ".")));// format to two decimal places after points
//
			skladModel.addRow(new Object[]{
					objects[0], // артикул
					objects[1], // количество
					objects[2], // м.ед
					objects[3], // ед.цена
					"", // value to add
					false,
					objects[4],// фактура
					objects[5], // контрагент});
					objects[9] // баркод
			});
			// store data in list to change table dinamically
			DATA_LIST.add(new Object[]{
					objects[0], // артикул
					objects[1], // количество
					objects[2], // м.ед
					objects[3], // ед.цена
					"", // value to add
					false,
					objects[4],// фактура
					objects[5], // контрагент});
					objects[9] // баркод
			});
		}
	}


}