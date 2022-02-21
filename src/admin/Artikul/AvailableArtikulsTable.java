package admin.Artikul;

import admin.Artikul.Renderers.ArtikulRenderer;
import admin.Artikul.Workers.DeleteArtikulWorker;
import admin.Artikul.Workers.LoadAllArtikulsWorker;
import mydate.MyGetDate;
import run.JDialoger;
import utility.EditableField;
import utility.LoadIcon;
import utility.MainPanel;
import utility.TooltipButton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class AvailableArtikulsTable extends MainPanel {

	private EditableField searchField = null;

	public static JTable table = null;
	public static DefaultTableModel artikulTableModel = null;
	public static ArrayList<Object[]> helpSearchFieldList = new ArrayList<Object[]>();

	public static int CURRENT_ROW = -1;

	public AvailableArtikulsTable() {

		JPanel basePanel = new JPanel();
		basePanel.setLayout(new BorderLayout());
		basePanel.setBorder(BorderFactory.createLineBorder(Color.black));

		JPanel northPanel = new JPanel();// GradientPanel();
		northPanel.setLayout(new GridBagLayout());// FlowLayout(FlowLayout.LEFT, 20, 5));
		// northPanel.setPreferredSize(new Dimension((int)
		// (Toolkit.getDefaultToolkit().getScreenSize().width * 0.95), 70));
		searchField = new EditableField("Търсене", 10) {
			private static final long serialVersionUID = 2L;

			@Override
			public Font getFont() {
				return new Font(Font.MONOSPACED, Font.LAYOUT_NO_START_CONTEXT,
						MainPanel.getFontSize() + 10);
			}
		};
		searchField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
				artikulTableModel.setRowCount(0);
				CURRENT_ROW = -1;
				String b = searchField.getText().toLowerCase();
				// here may to optimize with binary search !!!
				// but work only for startsWith(string) no with contains(string)
				/*
				 * if(b.length() > 0) { int left = 0; int right =
				 * helpSearchFieldList.size(); int middle = (left + right) / 2;
				 * 
				 * while(left <= right) { Object[] obj =
				 * helpSearchFieldList.get(middle); String a =
				 * obj[0].toString().toLowerCase(); if(a.startsWith(b)) { break;
				 * } else if(b.compareTo(a) < 0) { right = middle - 1; } else
				 * if(b.compareTo(a) > 0) { left = middle + 1; } middle = (left
				 * + right) / 2; }
				 * 
				 * for(int m = left;m <= right;m++) { Object[] o =
				 * helpSearchFieldList.get(m); String c =
				 * o[0].toString().toLowerCase(); if(c.startsWith(b)) {
				 * artikulTableModel.addRow(o); } }
				 * 
				 * }
				 */
				if (b.length() > 0) {
					for (int i = 0; i < helpSearchFieldList.size(); i++) {
						Object[] obj = helpSearchFieldList.get(i);

						String a = obj[0].toString().toLowerCase();
						if (a.startsWith(b)) {
							artikulTableModel.addRow(obj);
						} else if (a.contains(b)) {
							artikulTableModel.addRow(obj);
						}
					}
				}
			}
		});
		TooltipButton loadButton = new TooltipButton();// ("Опресни данните");
		loadButton.setPreferredSize(new Dimension((int) (searchField
				.getPreferredSize().getWidth() * 0.3), (int) (searchField
				.getPreferredSize().getHeight() * 0.85)));

		loadButton.setAutoSizedIcon(loadButton, new LoadIcon().setIcons(refreshImage));
		loadButton.setToolTipText(getHTML_Text("ЗАРЕДИ ДАНННИТЕ"));
		loadButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				/*
				 * if(artikulTableModel.getRowCount() == 0) return;
				 */
				JDialog jd = ((JDialog) SwingUtilities
						.getWindowAncestor(AvailableArtikulsTable.this));
				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				LoadAllArtikulsWorker load = new LoadAllArtikulsWorker(jd);
				load.execute();
			}

		});

		/*
		 * viewButton = new TooltipButton("Виж текуща цена");
		 * 
		 * viewButton.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent arg0) { // TODO
		 * Auto-generated method stub if(artikulTableModel.getRowCount() == 0)
		 * return;
		 * 
		 * if(CURRENT_ROW == -1) { JOptionPane.showMessageDialog(null,
		 * "Не е избран пожарогасител !"); return; }
		 * 
		 * if(!table.getValueAt(CURRENT_ROW, 3).toString().isEmpty()) {
		 * 
		 * return; }
		 * 
		 * JDialog jd =
		 * (JDialog)SwingUtilities.getWindowAncestor(ArtikulsMainFrame.this);
		 * jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		 * seeCurrentPriceOfArtikulWorker vw = new
		 * seeCurrentPriceOfArtikulWorker( table.getValueAt(CURRENT_ROW,
		 * 0).toString(), jd); try { String value = vw.doInBackground();
		 * 
		 * if(value != null) { table.setValueAt(value,CURRENT_ROW, 3); } } catch
		 * (Exception e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } }
		 * 
		 * });
		 */

		// private TooltipButton viewButton = null;
		TooltipButton editPriceButton = new TooltipButton("Запази нова цена");
		editPriceButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				// int[] selectedToDelete = table.getSelectedRows();
				String artikulItem = "";
				String skladItem = "";
				String medItem = "";
				String valueItem = "";
				String fakturaItem = "";
				String kontragentItem = "";
				String dateItem = "";
				String operatorItem = "";
				String percentProfitItem = "";
				// System.out.println("CURRENT_ROW = " + CURRENT_ROW);
				if (CURRENT_ROW >= 0) {
					artikulItem = table.getValueAt(CURRENT_ROW, 0).toString();
					skladItem = table.getValueAt(CURRENT_ROW, 1).toString();
					medItem = table.getValueAt(CURRENT_ROW, 2).toString();
					valueItem = table.getValueAt(CURRENT_ROW, 3).toString();
					fakturaItem = table.getValueAt(CURRENT_ROW, 4).toString();
					kontragentItem = table.getValueAt(CURRENT_ROW, 5)
							.toString();
					dateItem = MyGetDate.getReversedSystemDate();// table.getValueAt(CURRENT_ROW,
																// 6).toString();
					operatorItem = table.getValueAt(CURRENT_ROW, 7).toString();
					percentProfitItem = table.getValueAt(CURRENT_ROW, 8)
							.toString();
				}

				ChangePriceArtikulDialog newArtikul = new ChangePriceArtikulDialog(
						artikulItem, skladItem, medItem, valueItem,
						fakturaItem, kontragentItem, dateItem, operatorItem,
						percentProfitItem);
				JDialoger jd = new JDialoger();
				jd.setContentPane(newArtikul);
				jd.setResizable(false);
				jd.setTitle("Добави артикул с нова цена");
				jd.Show();

				// OLD SOLUTION
				// if (artikulTableModel.getRowCount() == 0)
				// return;
				//
				// if (CURRENT_ROW == -1) {
				// JOptionPane.showMessageDialog(null,
				// "Не е избран пожарогасител !");
				// return;
				// }
				// if (table.getValueAt(CURRENT_ROW, 3).toString().isEmpty()) {
				// JOptionPane.showMessageDialog(null,
				// "Не е въведена стойност!");
				// return;
				// }
				// int yes_no = JOptionPane.showOptionDialog(null,
				// "Желаете ли да съхраните въведените данни?", "",
				// JOptionPane.YES_NO_OPTION,
				// JOptionPane.QUESTION_MESSAGE, null, new String[] {
				// "Да", "Не" }, // this is the array
				// "default");
				// if (yes_no == 0) {
				// JDialog jd = (JDialog) SwingUtilities
				// .getWindowAncestor(AvailableArtikulsTable.this);
				// jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				//
				// ChangePriceOfArtikulWorker ew = new
				// ChangePriceOfArtikulWorker(
				// jd);
				// ew.execute();
				// }

			}

		});
		TooltipButton editQuantityButton = new TooltipButton("Редактирай к-во");
		editQuantityButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (artikulTableModel.getRowCount() == 0)
					return;

				if (CURRENT_ROW == -1) {
					JOptionPane
							.showMessageDialog(null, "Не е избран артикул !");
					return;
				}

				String artikulItem = "";
				String skladItem = "";
				String medItem = "";
				String valueItem = "";
				String fakturaItem = "";
				String kontragentItem = "";
				String dateItem = "";
				String operatorItem = "";
				String percentProfitItem = "";
				// System.out.println("CURRENT_ROW = " + CURRENT_ROW);
				if (CURRENT_ROW >= 0) {
					artikulItem = table.getValueAt(CURRENT_ROW, 0).toString();
					skladItem = table.getValueAt(CURRENT_ROW, 1).toString();
					medItem = table.getValueAt(CURRENT_ROW, 2).toString();
					valueItem = table.getValueAt(CURRENT_ROW, 3).toString();
					fakturaItem = table.getValueAt(CURRENT_ROW, 4).toString();
					kontragentItem = table.getValueAt(CURRENT_ROW, 5)
							.toString();
					dateItem = MyGetDate.getReversedSystemDate();// table.getValueAt(CURRENT_ROW,
																// 6).toString();
					operatorItem = table.getValueAt(CURRENT_ROW, 7).toString();
					percentProfitItem = table.getValueAt(CURRENT_ROW, 8)
							.toString();
				}

				ChangeQuantityArtikulDialog newArtikul = new ChangeQuantityArtikulDialog(
						artikulItem, skladItem, medItem, valueItem,
						fakturaItem, kontragentItem, dateItem, operatorItem,
						percentProfitItem);
				JDialoger jd = new JDialoger();
				jd.setContentPane(newArtikul);
				jd.setResizable(false);
				jd.setTitle("Редактиране на количество");
				jd.Show();

			}
		});

		TooltipButton deleteButton = new TooltipButton("Изтрий артикул");

		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (artikulTableModel.getRowCount() == 0)
					return;

				if (CURRENT_ROW == -1) {
					JOptionPane
							.showMessageDialog(null, "Не е избран артикул !");
					return;
				}
				// int[] selectedToDelete = table.getSelectedRows();
				String item = table.getValueAt(CURRENT_ROW, 0).toString();
				String kontragentItem = table.getValueAt(CURRENT_ROW, 5)
						.toString();
				String invoiceItem = table.getValueAt(CURRENT_ROW, 4)
						.toString();
				int yes_no = JOptionPane.showOptionDialog(null,
						"Сигурни ли сте че искате да изтриете артикула ?", "",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, new String[] {
								"Да", "Не" }, // this is the array
						"default");
				if (yes_no == 0) {
					DeleteArtikulWorker dw = new DeleteArtikulWorker(
							(JDialog) SwingUtilities
									.getWindowAncestor(AvailableArtikulsTable.this),
							item, kontragentItem, invoiceItem);
					dw.execute();
				}
			}

		});
		TooltipButton addArtikulButton = new TooltipButton("Добави нов артикул");

		addArtikulButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				// int[] selectedToDelete = table.getSelectedRows();
				String artikulItem = "";
				String skladItem = "";
				String medItem = "";
				String valueItem = "";
				String fakturaItem = "";
				String kontragentItem = "";
				String dateItem = "";
				String operatorItem = "";
				String percentProfitItem = "";
				// System.out.println("CURRENT_ROW = " + CURRENT_ROW);
				if (CURRENT_ROW >= 0) {
					artikulItem = table.getValueAt(CURRENT_ROW, 0).toString();
					// skladItem = table.getValueAt(CURRENT_ROW, 1).toString();
					medItem = table.getValueAt(CURRENT_ROW, 2).toString();
					valueItem = "0";
					table.getValueAt(CURRENT_ROW, 3).toString();
					// fakturaItem = table.getValueAt(CURRENT_ROW,
					// 4).toString();
					kontragentItem = table.getValueAt(CURRENT_ROW, 5)
							.toString();
					dateItem = MyGetDate.getReversedSystemDate();// table.getValueAt(CURRENT_ROW,
																// 6).toString();
					operatorItem = table.getValueAt(CURRENT_ROW, 7).toString();
					percentProfitItem = table.getValueAt(CURRENT_ROW, 8)
							.toString();
				}

				AddArtikulDialog newArtikul = new AddArtikulDialog(artikulItem,
						skladItem, medItem, valueItem, fakturaItem,
						kontragentItem, dateItem, operatorItem,
						percentProfitItem);
				JDialoger jd = new JDialoger();
				jd.setContentPane(newArtikul);
				jd.setResizable(false);
				jd.setTitle("Добави артикул");
				jd.Show();

			}

		});
		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.fill = GridBagConstraints.HORIZONTAL;
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.gridwidth = 2;
		gbc1.insets = new Insets(0, 0, 0, 0);

		GridBagConstraints gbc6 = new GridBagConstraints();
		gbc6.fill = GridBagConstraints.HORIZONTAL;
		gbc6.gridx = 2;
		gbc6.gridy = 0;
		gbc6.gridwidth = 1;
		gbc6.insets = new Insets(0, 0, 0, 0);
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.fill = GridBagConstraints.HORIZONTAL;
		gbc2.gridx = 3;
		gbc2.gridy = 0;
		gbc2.gridwidth = 1;
		gbc2.insets = new Insets(0, 0, 0, 0);

		GridBagConstraints gbc3 = new GridBagConstraints();
		gbc3.fill = GridBagConstraints.HORIZONTAL;
		gbc3.gridx = 0;
		gbc3.gridy = 1;
		gbc3.gridwidth = 1;
		gbc3.insets = new Insets(0, 0, 0, 0);

		GridBagConstraints gbc4 = new GridBagConstraints();
		gbc4.fill = GridBagConstraints.HORIZONTAL;
		gbc4.gridx = 1;
		gbc4.gridy = 1;
		gbc4.gridwidth = 1;
		gbc4.insets = new Insets(0, 0, 0, 0);

		GridBagConstraints gbc5 = new GridBagConstraints();
		gbc5.fill = GridBagConstraints.HORIZONTAL;
		gbc5.gridx = 2;
		gbc5.gridy = 1;
		gbc5.gridwidth = 1;
		gbc5.insets = new Insets(0, 0, 0, 0);


		GridBagConstraints gbc8 = new GridBagConstraints();
		gbc8.fill = GridBagConstraints.HORIZONTAL;
		gbc8.gridx = 3;
		gbc8.gridy = 1;
		gbc8.gridwidth = 1;
		gbc8.insets = new Insets(0, 0, 0, 0);

		TooltipButton editArtikulNameButton = new TooltipButton("Прейменувай артикул");
		editArtikulNameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String artikulItem = "";
				if(CURRENT_ROW >= 0) {
					artikulItem = table.getValueAt(CURRENT_ROW, 0).toString();
					RenameArtikulNameDialog renameArtikulNameDialog =
							new RenameArtikulNameDialog(artikulItem);
					JDialoger jd = new JDialoger();
					jd.setContentPane(renameArtikulNameDialog);
					jd.setResizable(false);
					jd.setTitle("Преименувай артикул");
					jd.Show();
				}

			}
		});

		northPanel.add(searchField,gbc1);
		northPanel.add(loadButton,gbc2);
		// northPanel.add(viewButton);
		northPanel.add(editQuantityButton,gbc3);
		northPanel.add(editPriceButton,gbc4);
		northPanel.add(deleteButton,gbc5);
		northPanel.add(addArtikulButton, gbc6);
		northPanel.add(editArtikulNameButton, gbc8);
		JPanel centerPanel = new JPanel();

		artikulTableModel = new DefaultTableModel(new String[] { "Артикули",
				"Налични", "Мерна Ед", "Ед. Цена", "Фактура", "Контрагент",
				"Дата", "Оператор", "Процент Печалба" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 1 || column == 3) {
					return true;
				}
				return false;
			}
		};

		table = new JTable(artikulTableModel);

		// sorting data
		// TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(
		// table.getModel());
		// table.setRowSorter(sorter);
		// List<RowSorter.SortKey> sortKeys = new ArrayList<>(1);
		// sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
		// sorter.setSortKeys(sortKeys);
		//

		table.setDefaultRenderer(Object.class, new ArtikulRenderer());
		table.setRowHeight(MainPanel.getFontSize() + 15);
		table.getTableHeader().setReorderingAllowed(false);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				CURRENT_ROW = table.getSelectedRow();
			}
		});
		//
		setColumnsWidth();
		//
		// JTable rowTable = new CommonResources.RowNumberTable(table); //****

		JScrollPane scroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // this line set
															// horizontal scroll
															// bar

		scroll.setPreferredSize(new Dimension(this.WIDTH - 50, this.HEIGHT - 70));
		// scroll.getVerticalScrollBar().setUI(new YourUI());
		// scroll.getHorizontalScrollBar().setUI(new YourUI());

		// scroll.setRowHeaderView(rowTable); //****
		// scroll.setCorner(JScrollPane.UPPER_LEFT_CORNER,//****
		// rowTable.getTableHeader());//****

		centerPanel.add(scroll);

		basePanel.add(northPanel, BorderLayout.NORTH);
		basePanel.add(centerPanel, BorderLayout.CENTER);
		this.add(basePanel);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				AvailableArtikulsTable art = new AvailableArtikulsTable();
				JFrame jf = new JFrame();
				jf.add(art);
				jf.pack();
				jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				jf.setVisible(true);
			}

		});

	}

	private void setColumnsWidth() {
		table.getColumnModel().getColumn(0).setPreferredWidth(this.WIDTH / 3);
		table.getColumnModel().getColumn(1).setPreferredWidth(this.WIDTH / 20);
		table.getColumnModel().getColumn(2).setPreferredWidth(this.WIDTH / 20);
		table.getColumnModel().getColumn(3).setPreferredWidth(this.WIDTH / 20);
		table.getColumnModel().getColumn(4).setPreferredWidth(this.WIDTH / 10);
		table.getColumnModel().getColumn(5).setPreferredWidth(this.WIDTH / 5);
		table.getColumnModel().getColumn(6).setPreferredWidth(this.WIDTH / 10);
		table.getColumnModel().getColumn(7).setPreferredWidth(this.WIDTH / 10);
		table.getColumnModel().getColumn(8).setPreferredWidth(this.WIDTH / 20);

	}

}
