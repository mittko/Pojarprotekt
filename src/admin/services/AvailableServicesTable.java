package admin.services;

import admin.Artikul.RenameArtikulNameDialog;
import admin.Artikul.Renderers.ArtikulRenderer;
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


public class AvailableServicesTable extends MainPanel {

	private EditableField searchField = null;

	public static JTable table = null;
	public static DefaultTableModel serviceTableModel = null;
	public static ArrayList<Object[]> helpSearchFieldList = new ArrayList<Object[]>();

	public static int CURRENT_ROW = -1;

	public AvailableServicesTable() {

		JPanel basePanel = new JPanel();
		basePanel.setLayout(new BorderLayout());
		basePanel.setBorder(BorderFactory.createLineBorder(Color.black));

		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridBagLayout());

		searchField = new EditableField("Търсене", 10) {
			private static final long serialVersionUID = 2L;

			@Override
			public Font getFont() {
				return new Font(Font.MONOSPACED, Font.ITALIC,
						MainPanel.getFontSize() + 10);
			}
		};
		searchField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
				serviceTableModel.setRowCount(0);
				CURRENT_ROW = -1;
				String b = searchField.getText().toLowerCase();
				if (b.length() > 0) {
					for (Object[] obj : helpSearchFieldList) {
						String a = obj[0].toString().toLowerCase();
						if (a.startsWith(b)) {
							serviceTableModel.addRow(obj);
						} else if (a.contains(b)) {
							serviceTableModel.addRow(obj);
						}
					}
				}
			}
		});
		TooltipButton loadButton = new TooltipButton();
		loadButton.setPreferredSize(new Dimension((int) (searchField
				.getPreferredSize().getWidth() * 0.3), (int) (searchField
				.getPreferredSize().getHeight() * 0.85)));

		loadButton.setAutoSizedIcon(loadButton, new LoadIcon().setIcons(refreshImage));
		loadButton.setToolTipText(getHTML_Text("ЗАРЕДИ ДАНННИТЕ"));
		loadButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JDialog jd = ((JDialog) SwingUtilities
						.getWindowAncestor(AvailableServicesTable.this));
				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				LoadAllServicesWorker load = new LoadAllServicesWorker(jd);
				load.execute();
			}

		});

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

				ChangePriceServiceDialog newService = new ChangePriceServiceDialog(
						artikulItem, skladItem, medItem, valueItem,
						fakturaItem, kontragentItem, dateItem, operatorItem,
						percentProfitItem);
				JDialoger jd = new JDialoger();
				jd.setContentPane(newService);
				jd.setResizable(false);
				jd.setTitle("Добави услуга с нова цена");
				jd.Show();


			}

		});
		TooltipButton editQuantityButton = new TooltipButton("Редактирай к-во");
		editQuantityButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (serviceTableModel.getRowCount() == 0)
					return;

				if (CURRENT_ROW == -1) {
					JOptionPane
							.showMessageDialog(null, "Не е избрана услуга !");
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

				ChangeQuantityServiceDialog newArtikul = new ChangeQuantityServiceDialog(
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

		TooltipButton deleteButton = new TooltipButton("Изтрий услуга");

		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (serviceTableModel.getRowCount() == 0)
					return;

				if (CURRENT_ROW == -1) {
					JOptionPane
							.showMessageDialog(null, "Не е избрана услуга !");
					return;
				}
				// int[] selectedToDelete = table.getSelectedRows();
				String item = table.getValueAt(CURRENT_ROW, 0).toString();
				String kontragentItem = table.getValueAt(CURRENT_ROW, 5)
						.toString();
				String invoiceItem = table.getValueAt(CURRENT_ROW, 4)
						.toString();
				int yes_no = JOptionPane.showOptionDialog(null,
						"Сигурни ли сте че искате да изтриете услугата ?", "",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, new String[] {
								"Да", "Не" }, // this is the array
						"default");
				if (yes_no == 0) {
					DeleteServicesWorker dw = new DeleteServicesWorker(
							AVAILABLE_SERVICES,
							(JDialog) SwingUtilities
									.getWindowAncestor(AvailableServicesTable.this),
							item, kontragentItem, invoiceItem);
					dw.execute();
				}
			}

		});
		TooltipButton addArtikulButton = new TooltipButton("Добави нова услуга");
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
				if (CURRENT_ROW >= 0) {
					artikulItem = table.getValueAt(CURRENT_ROW, 0).toString();
					medItem = table.getValueAt(CURRENT_ROW, 2).toString();
					valueItem = "0";
					table.getValueAt(CURRENT_ROW, 3).toString();
					kontragentItem = table.getValueAt(CURRENT_ROW, 5)
							.toString();
					dateItem = MyGetDate.getReversedSystemDate();
					operatorItem = table.getValueAt(CURRENT_ROW, 7).toString();
					percentProfitItem = table.getValueAt(CURRENT_ROW, 8)
							.toString();
				}

				AddServiceDialog newService = new AddServiceDialog(artikulItem,
						skladItem, medItem, valueItem, fakturaItem,
						kontragentItem, dateItem, operatorItem,
						percentProfitItem);
				JDialoger jd = new JDialoger();
				jd.setContentPane(newService);
				jd.setResizable(false);
				jd.setTitle("Добави услуга");
				jd.Show();

			}

		});
		GridBagConstraints gbc00 = new GridBagConstraints();
		gbc00.fill = GridBagConstraints.HORIZONTAL;
		gbc00.gridx = 0;
		gbc00.gridy = 0;
		gbc00.gridwidth = 2;
		gbc00.insets = new Insets(0, 0, 0, 0);

		GridBagConstraints gbc20 = new GridBagConstraints();
		gbc20 .fill = GridBagConstraints.HORIZONTAL;
		gbc20 .gridx = 2;
		gbc20 .gridy = 0;
		gbc20 .gridwidth = 1;
		gbc20 .insets = new Insets(0, 0, 0, 0);
		GridBagConstraints gbc30 = new GridBagConstraints();
		gbc30.fill = GridBagConstraints.HORIZONTAL;
		gbc30.gridx = 3;
		gbc30.gridy = 0;
		gbc30.gridwidth = 1;
		gbc30.insets = new Insets(0, 0, 0, 0);

		GridBagConstraints gbc01 = new GridBagConstraints();
		gbc01.fill = GridBagConstraints.HORIZONTAL;
		gbc01.gridx = 0;
		gbc01.gridy = 1;
		gbc01.gridwidth = 1;
		gbc01.insets = new Insets(0, 0, 0, 0);

		GridBagConstraints gbc11 = new GridBagConstraints();
		gbc11.fill = GridBagConstraints.HORIZONTAL;
		gbc11.gridx = 1;
		gbc11.gridy = 1;
		gbc11.gridwidth = 1;
		gbc11.insets = new Insets(0, 0, 0, 0);

		GridBagConstraints gbc21 = new GridBagConstraints();
		gbc21.fill = GridBagConstraints.HORIZONTAL;
		gbc21.gridx = 2;
		gbc21.gridy = 1;
		gbc21.gridwidth = 1;
		gbc21.insets = new Insets(0, 0, 0, 0);


		GridBagConstraints gbc31 = new GridBagConstraints();
		gbc31.fill = GridBagConstraints.HORIZONTAL;
		gbc31.gridx = 3;
		gbc31.gridy = 1;
		gbc31.gridwidth = 1;
		gbc31.insets = new Insets(0, 0, 0, 0);

		TooltipButton editArtikulNameButton = new TooltipButton("Прейменувай услуга");
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
					jd.setTitle("Преименувай услуга");
					jd.Show();
				}

			}
		});

		northPanel.add(searchField,gbc00);
		northPanel.add(loadButton,gbc20);
		// northPanel.add(viewButton);
		northPanel.add(editQuantityButton,gbc30);
		northPanel.add(editPriceButton,gbc01);
		northPanel.add(deleteButton,gbc11);
		northPanel.add(addArtikulButton, gbc21);
		northPanel.add(editArtikulNameButton, gbc31);
		JPanel centerPanel = new JPanel();

		serviceTableModel = new DefaultTableModel(new String[] { "Услуги",
				"Налични", "Мерна Ед", "Ед. Цена", "Фактура", "Контрагент",
				"Дата", "Оператор", "Процент Печалба" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 1 || column == 3;
			}
		};

		table = new JTable(serviceTableModel);


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

		JScrollPane scroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // this line set
															// horizontal scroll
															// bar

		scroll.setPreferredSize(new Dimension(this.WIDTH - 50, this.HEIGHT - 70));


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
				AvailableServicesTable art = new AvailableServicesTable();
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
