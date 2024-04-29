package admin.sklad;

import newextinguisher.NewExtinguisherWindow;
import admin.sklad.editors.TextCellEditors;
import admin.sklad.renderers.NewExtRenderer;
import utils.EditableField;
import utils.MainPanel;
import utils.RedCaret;
import utils.TooltipButton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Shop_SkladExtinuisher extends MainPanel {

	public DefaultTableModel skladExtinguisherModel = null;
	public static JTable table = null;
	private EditableField searchField = null;
    private final JComboBox<String> jComboBox;
	public Shop_SkladExtinuisher(final ArrayList<Object[]> data) {

		JPanel basePanel = new JPanel();
		basePanel.setLayout(new BorderLayout());
		basePanel.setBorder(BorderFactory.createLineBorder(Color.black));

		JPanel northPanel = new JPanel();// GradientPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		jComboBox = new JComboBox<>(new String[]{"носим","возим"});

		final TooltipButton sellButton = new TooltipButton("Магазин");
		sellButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

		sellButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				// make input validate

				for (int i = 0; i < skladExtinguisherModel.getRowCount(); i++) {
					if ((Boolean) skladExtinguisherModel.getValueAt(i, 7)) {

						TableCellEditor editor = table.getColumnModel()
								.getColumn(6).getCellEditor();
						editor.stopCellEditing();

						int quantity = Integer.parseInt(skladExtinguisherModel
								.getValueAt(i, 4).toString());

						int toShop = 0;

						try {
							toShop = Integer.parseInt(skladExtinguisherModel
									.getValueAt(i, 6).toString());
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null,
									"Грешно въведено колечество! > " + (i + 1)
											+ " ред");
							return;
						}
						if (toShop > quantity || toShop == 0) {
							JOptionPane.showMessageDialog(null,
									"Грешно въведено колечество! > " + (i + 1)
											+ " ред");
							return;
						}

						// finally check if this extinguisher is entered
						// StringBuilder sb = new StringBuilder();
						// for (int s = 0; s < 4; s++) {
						// sb.append(skladExtinguisherModel.getValueAt(i, s));
						// }
						//
						// if (checkIfAlreadyEntered(sb.toString(),
						// skladExtinguisherModel.getValueAt(0, 8) .toString(),
						// skladExtinguisherModel .getValueAt(0, 9).toString()))
						// { JOptionPane.showMessageDialog(null,
						// "Този пожарогасител вече е въведен!"); return; }

						addToShop(i, toShop);

						setExtinguisherQuantity(i, toShop); // get extinguisher
															// quantity to
															// update
						// clear
						skladExtinguisherModel.setValueAt("", i, 6);
						skladExtinguisherModel.setValueAt(false, i, 7);

					}
				}

				goodbyeCruelWorld();
			}

		});

		JPanel centerPanel = new JPanel();

		skladExtinguisherModel = new DefaultTableModel(new Object[] { "Вид",
				"Маса", "Кат", "Марка", "Налични", "Цена", "Въведи", "v",
				"Контрагент", "Фактура", }, 0) {

			@Override
			public Class getColumnClass(int column) {
				if (column == 7) {
					return Boolean.class;
				} else {
					return Object.class;
				}
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return column > 5;
			}
		};

		// init skladExtinguisherModel

		for (Object[] datum : data) {
			skladExtinguisherModel.addRow(new Object[]{datum[0], // артикул
					datum[1], // количество
					datum[2], // м.ед
					datum[3], // ед.цена
					datum[4], // м.ед
					datum[5], // ед.цена
					"", // value to add
					false,
					datum[7],// контрагент
					datum[6] // фактура по доставка});
			});

		}

		table = new JTable(skladExtinguisherModel);

		JTextField helpField = new JTextField();
		helpField.setCaret(new RedCaret());
		table.getColumnModel().getColumn(6)
				.setCellEditor(new TextCellEditors(helpField));

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent me) {
				if (table.getSelectedColumn() == 7) {
					Boolean isSelected = (Boolean) table.getValueAt(
							table.getSelectedRow(), table.getSelectedColumn());
					if (isSelected) {
						table.editCellAt(table.getSelectedRow(), 6);

						Component editor = table.getEditorComponent();
						editor.requestFocusInWindow();
					} else {
						table.setValueAt("", table.getSelectedRow(), 6); // clear
																			// content
																			// in
																			// cell
					}
				}
			}
		});

		table.setDefaultRenderer(Object.class, new NewExtRenderer());
		table.setRowHeight(MainPanel.getFontSize() + 15);
		setColumnsWidth(table);

		// JTable rowTable = new CommonResources.RowNumberTable(table); //****

		JScrollPane scrollPane = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension((this.WIDTH - 50),
				this.HEIGHT / 2));

		// scrollPane.setRowHeaderView(rowTable); //****
		// scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER,//****
		// rowTable.getTableHeader());//****

		centerPanel.add(scrollPane);

		searchField = new EditableField("Търсене", 10) {
			private static final long serialVersionUID = 1L;

			@Override
			public Font getFont() {
				return new Font(Font.MONOSPACED, Font.ITALIC,
						MainPanel.getFontSize() + 10);
			}
		};
		// searchField.setPreferredSize(new Dimension(20,35));
		searchField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
				skladExtinguisherModel.setRowCount(0);
				for (Object[] datum : data) {
					String item = searchField.getText().toLowerCase();
					String target = datum[0].toString().toLowerCase();
					if ((item.length() > 0 && target.startsWith(item))
							|| item.length() > 0 && target.contains(item))
						skladExtinguisherModel.addRow(new Object[]{
								datum[0], // артикул
								datum[1], // количество
								datum[2], // м.ед
								datum[3], // ед.цена
								datum[4], // м.ед
								datum[5], // ед.цена
								"", // value to add
								false,
//								datum[6],// фактура Тук съм допуснал глупава грешка по невнимание
//								datum[7] // контрагент});
								datum[7],// контрагент
								datum[6] // фактура по доставка});
						});
				}
			}
		});

		northPanel.add(jComboBox);
		northPanel.add(sellButton);
		northPanel.add(searchField);

		basePanel.add(northPanel, BorderLayout.NORTH);
		basePanel.add(centerPanel, BorderLayout.CENTER);

		this.add(basePanel);
	}

	private void addToShop(int index, int num) {
		for (int i = 0; i < num; i++) {
			Object[] obj = new Object[10];
			obj[0] = skladExtinguisherModel.getValueAt(index, 0);
			obj[1] = skladExtinguisherModel.getValueAt(index, 1);
			obj[2] = "";
			obj[3] = "";
			obj[4] = skladExtinguisherModel.getValueAt(index, 2);
			obj[5] = skladExtinguisherModel.getValueAt(index, 3) + " , " + jComboBox.getSelectedItem();
			obj[6] = skladExtinguisherModel.getValueAt(index, 5);
			obj[7] = skladExtinguisherModel.getValueAt(index, 8);
			obj[8] = skladExtinguisherModel.getValueAt(index, 9);
			obj[9] = "";// допълнителни данни
			NewExtinguisherWindow.dftm.insertRow(0, obj);

			NewExtinguisherWindow.addBarcodes(0);


			System.out.println(skladExtinguisherModel.getValueAt(index, 8));
			System.out.println(skladExtinguisherModel.getValueAt(index, 9));
		}

		// addBarcodes(num);
	}

	private void setColumnsWidth(JTable table) {
		TableColumnModel tcm = table.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(this.WIDTH / 3);
		tcm.getColumn(1).setPreferredWidth(50);
		tcm.getColumn(2).setPreferredWidth(50);
		tcm.getColumn(3).setPreferredWidth(90);
		tcm.getColumn(4).setPreferredWidth(50);
		tcm.getColumn(5).setPreferredWidth(50);
		tcm.getColumn(6).setPreferredWidth(50);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				JFrame jf = new JFrame();
				Shop_SkladExtinuisher nep = new Shop_SkladExtinuisher(
						new ArrayList<Object[]>());
				jf.add(nep);
				jf.pack();
				jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				jf.setVisible(true);
			}

		});
	}

	private boolean checkIfAlreadyEntered(String extinguisher,
			String kontragent, String invoiceByKontragent) {

		for (int row = 0; row < NewExtinguisherWindow.dftm.getRowCount(); row++) {
			StringBuilder sb = new StringBuilder();
			sb.append(NewExtinguisherWindow.dftm.getValueAt(row, 0));
			sb.append(NewExtinguisherWindow.dftm.getValueAt(row, 1));
			sb.append(NewExtinguisherWindow.dftm.getValueAt(row, 4));
			sb.append(NewExtinguisherWindow.dftm.getValueAt(row, 5));
			if (sb.toString().equals(extinguisher)
					&& kontragent.equals(NewExtinguisherWindow.dftm.getValueAt(
							0, 7).toString())
					&& invoiceByKontragent.equals(NewExtinguisherWindow.dftm
							.getValueAt(0, 8).toString())) {
				return true;
			}
		}
		return false;
	}

	private void setExtinguisherQuantity(int index, int num) { // index of
																// selected
																// extinguisher
		for (int i = 0; i < num; i++) {
			Object[] obj = new Object[4];
			obj[0] = skladExtinguisherModel.getValueAt(index, 0); // type
			obj[1] = skladExtinguisherModel.getValueAt(index, 1); // wheight
			obj[2] = skladExtinguisherModel.getValueAt(index, 2); // category
			obj[3] = skladExtinguisherModel.getValueAt(index, 3); // brand

			// NewExtinguisherWindow.updateQuantityOfExtinguisherModel.insertRow(
			// 0, obj);
		}
	}

	private void goodbyeCruelWorld() {
		JDialog jd = (JDialog) SwingUtilities
				.getWindowAncestor(Shop_SkladExtinuisher.this);
		jd.dispose();
	}
}
