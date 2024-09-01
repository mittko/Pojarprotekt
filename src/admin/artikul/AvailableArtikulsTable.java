package admin.artikul;

import exceptions.DBException;
import exceptions.ErrorDialog;
import admin.artikul.renderers.ArtikulRenderer;
import admin.artikul.workers.*;
import db.artikul.Artikuli_DB;
import http.RequestCallback;
import http.RequestCallback2;
import http.sklad.GetArtikulService;
import http.sklad.IGetArtikuls;
import invoice.sklad.ILoadArtikuls;
import models.ArtikulModel;
import run.JDialoger;
import utils.EditableField;
import utils.LoadIcon;
import utils.MainPanel;
import utils.TooltipButton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;


public abstract class AvailableArtikulsTable extends MainPanel implements ILoadArtikuls, IEditArtikuls {

	private EditableField searchField = null;

	public static JTable table = null;
	public static DefaultTableModel artikulTableModel = null;
	public static ArrayList<Object[]> helpSearchFieldList = new ArrayList<Object[]>();

	public static int CURRENT_ROW = -1;

	public AvailableArtikulsTable() {

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
				artikulTableModel.setRowCount(0);
				CURRENT_ROW = -1;
				String b = searchField.getText().toLowerCase();

				if (b.length() > 0) {
					for (Object[] obj : helpSearchFieldList) {
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

				GetArtikulService service = new GetArtikulService();
				service.getArtikuls(isGrey(), true, new RequestCallback() {
					@Override
					public <T> void callback(List<T> objects) {
						jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

						loadArtikuls((ArrayList<ArtikulModel>) objects);
					}
				});

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
				String percentProfitItem = "";

				if (CURRENT_ROW >= 0) {
					artikulItem = table.getValueAt(CURRENT_ROW, 0).toString();
					skladItem = table.getValueAt(CURRENT_ROW, 1).toString();
					valueItem = table.getValueAt(CURRENT_ROW, 3).toString();
					fakturaItem = table.getValueAt(CURRENT_ROW, 4).toString();
					kontragentItem = table.getValueAt(CURRENT_ROW, 5)
							.toString();
					percentProfitItem = table.getValueAt(CURRENT_ROW, 8)
							.toString();
				}

				ChangePriceArtikulDialog newArtikul = new ChangePriceArtikulDialog(
						AvailableArtikulsTable.this,
						artikulItem, skladItem, valueItem,
						fakturaItem, kontragentItem,
						percentProfitItem);
				JDialoger jd = new JDialoger();
				jd.setContentPane(newArtikul);
				jd.setResizable(false);
				jd.setTitle("Добави артикул с нова цена");
				jd.Show();

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

				if (CURRENT_ROW >= 0) {
					artikulItem = table.getValueAt(CURRENT_ROW, 0).toString();
					skladItem = table.getValueAt(CURRENT_ROW, 1).toString();
					medItem = table.getValueAt(CURRENT_ROW, 2).toString();
					valueItem = table.getValueAt(CURRENT_ROW, 3).toString();
					fakturaItem = table.getValueAt(CURRENT_ROW, 4).toString();
					kontragentItem = table.getValueAt(CURRENT_ROW, 5)
							.toString();
				}

				ChangeQuantityArtikulDialog newArtikul = new ChangeQuantityArtikulDialog(
						AvailableArtikulsTable.this,
						artikulItem, skladItem, medItem, valueItem,
						fakturaItem, kontragentItem
				);
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

					deleteArtikul(item,kontragentItem,invoiceItem);
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
				String fakturaItem = "";
				String kontragentItem = "";
				String percentProfitItem = "";
				if (CURRENT_ROW >= 0) {
					artikulItem = table.getValueAt(CURRENT_ROW, 0).toString();
					kontragentItem = table.getValueAt(CURRENT_ROW, 5)
							.toString();
					percentProfitItem = table.getValueAt(CURRENT_ROW, 8)
							.toString();
				}

				AddArtikulPanel newArtikul = new AddArtikulPanel(
						AvailableArtikulsTable.this,
						getTableName(),
						artikulItem,
						skladItem, fakturaItem,
						kontragentItem,
						percentProfitItem);
				JDialoger jd = new JDialoger();
				jd.setContentPane(newArtikul);
				jd.setResizable(false);
				jd.setTitle("Добави артикул");
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
		gbc20.fill = GridBagConstraints.HORIZONTAL;
		gbc20.gridx = 2;
		gbc20.gridy = 0;
		gbc20.gridwidth = 1;
		gbc20.insets = new Insets(0, 0, 0, 0);

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


		TooltipButton editArtikulNameButton = new TooltipButton("Прейменувай артикул");
		editArtikulNameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String artikulItem = "";
				if(CURRENT_ROW >= 0) {
					artikulItem = table.getValueAt(CURRENT_ROW, 0).toString();
					RenameArtikulNameDialog renameArtikulNameDialog =
							new RenameArtikulNameDialog(AvailableArtikulsTable.this,artikulItem);
					JDialoger jd = new JDialoger();
					jd.setContentPane(renameArtikulNameDialog);
					jd.setResizable(false);
					jd.setTitle("Преименувай артикул");
					jd.Show();
				}

			}
		});

		northPanel.add(searchField,gbc00);
		northPanel.add(loadButton, gbc20);
		northPanel.add(editArtikulNameButton, gbc30);
		northPanel.add(editQuantityButton,gbc31);
		northPanel.add(editPriceButton,gbc01);
		northPanel.add(deleteButton,gbc11);
		northPanel.add(addArtikulButton,gbc21);
		//northPanel.add(addArtikulGreyButton,gbc31);


		JPanel centerPanel = new JPanel();

		artikulTableModel = new DefaultTableModel(new String[] { "Артикули",
				"Налични", "Мерна Ед", "Ед. Цена", "Фактура", "Контрагент",
				"Дата", "Оператор", "Процент Печалба" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 1 || column == 3;
			}
		};

		table = new JTable(artikulTableModel);


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

		scroll.setPreferredSize(new Dimension((int)(this.WIDTH * 0.95), (int)(this.HEIGHT * 0.85)));


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
//				ArtikulTable art = new ArtikulTable();
//				JFrame jf = new JFrame();
//				jf.add(art);
//				jf.pack();
//				jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//				jf.setVisible(true);
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


	@Override
	public void loadArtikuls(ArrayList<ArtikulModel> artikuls) {

		helpSearchFieldList.clear();
		for (ArtikulModel model : artikuls) {

			//	artikul, quantity, med , value, client, invoice, date, operator, percentProfit, barcode

			// " (artikul varchar(100),quantity int,"
			//         + "med varchar(20),value varchar(20))"; // artikuli->300
			// да се добавят колони фактура, контрагент, дата, оператор, процент
			// печалба,
			// =>, invoice, client, date, operator, percentProfit,

			Object[] obj = new Object[]{/*datum[9],*/model.getArtikul(), model.getQuantity(),
					model.getMed(), model.getPrice(), model.getInvoice(),
					model.getKontragent(), model.getDate(), model.getPerson(),
					model.getPercentProfit()};
			helpSearchFieldList.add(obj);
		}

		if (artikulTableModel
				.getRowCount() > 0) {
			artikulTableModel.setRowCount(0);
		}
		/*
		 * Collections.sort(data, new Comparator<Object[]>() {
		 *
		 * @Override public int compare(Object[] arg0, Object[]
		 * arg1) { // TODO Auto-generated method stub return
		 * arg0[0].toString().toLowerCase().
		 * compareTo(arg1[0].toString().toLowerCase()); }
		 *
		 * }); PrintStream ps = null; try { ps = new
		 * PrintStream("artikuli.txt"); } catch
		 * (FileNotFoundException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); }
		 */
		// init values
		// ArtikulsMainFrame.helpSearchFieldList.clear();

		// try to optimize to split painting of parts

		for (ArtikulModel model : artikuls) { // d <
			// data.size()

			//	artikul, quantity, med , value, client, invoice, date, operator, percentProfit, barcode
			Object[] obj = new Object[]{/*datum[9],*/model.getArtikul(), model.getQuantity(),
					model.getMed(), model.getPrice(), model.getInvoice(),
					model.getKontragent(), model.getDate(), model.getPerson(),
					model.getPercentProfit()};
			/*
			 * ps.println(data.get(d)[0] + "      " +
			 * data.get(d)[1] + " " + data.get(d)[2] +
			 * "          Цена  " + data.get(d)[3]); ps.println(
			 * "--------------------------------------------------------------------------------------------"
			 * );
			 */
			artikulTableModel.addRow(obj);
			// ArtikulsMainFrame.helpSearchFieldList.add(obj);
		}
	}

	@Override
	public void changeArtikulPrice(String artikul, String biggestPriceValue, String percentProfit, String kontragent, String invoice) {


		JDialog jd = (JDialog) SwingUtilities
				.getWindowAncestor(AvailableArtikulsTable.this);
		jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));


		GetArtikulService service = new GetArtikulService();
		service.editArtikulPrice(biggestPriceValue, percentProfit, artikul, kontragent, invoice, new RequestCallback2() {
			@Override
			public <T> void callback(T t) {

				jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

				JOptionPane.showMessageDialog(null,
						"Данните са записани успешно!");

			}
		});
//		UpdatePriceArtikulWorker add = new UpdatePriceArtikulWorker(
//				getTableName(),
//				artikul, biggestPriceValue,
//				percentProfit, kontragent,
//				invoice, jd);
//		add.execute();
	}

	@Override
	public void changeArtikulQuantity(String artikul, String newQuantity, String kontragent, String invoice) {

		JDialog jDialog = (JDialog) SwingUtilities
				.getWindowAncestor(AvailableArtikulsTable.this);

		GetArtikulService service = new GetArtikulService();
		service.editArtikulQuantity(artikul, kontragent, invoice, newQuantity, new RequestCallback2() {
			@Override
			public <T> void callback(T t) {

				jDialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

				JOptionPane.showMessageDialog(null,
						"Промените са записани успешно!");
			}
		});

	}

	@Override
	public void deleteArtikul(String artikul, String kontragent, String invoice) {
		JDialog jDialog = (JDialog) SwingUtilities
				.getWindowAncestor(AvailableArtikulsTable.this);


		jDialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		GetArtikulService service = new GetArtikulService();
		service.deleteArtikul(artikul, kontragent, invoice, new RequestCallback2() {
			@Override
			public <T> void callback(T t) {

				jDialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

				JOptionPane.showMessageDialog(null,
						"Артикулът е изтрит успешно!");

			}
		});
	}

	@Override
	public void addArtikul(String client, String artikul, String sklad, String med, String deliveryValue,
						   String price, String invoice, String date, String operator, String percentProfit, String barcode) {
		final JDialog jd = (JDialog)SwingUtilities.getWindowAncestor(AvailableArtikulsTable.this);
		jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));

        ArtikulModel model = new ArtikulModel();
		model.setArtikul(artikul);
		model.setQuantity(Integer.parseInt(sklad));
		model.setMed(med);
		model.setPrice(Float.parseFloat(price.replace(",",".")));
		model.setInvoice(invoice);
		model.setKontragent(client);
		model.setDate(date);
		model.setPerson(operator);
		model.setPercentProfit(percentProfit);
		model.setBracod(barcode);
		GetArtikulService service = new GetArtikulService();
		service.inserArtikul(model, new RequestCallback2() {
			@Override
			public <T> void callback(T t) {

				jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

				JOptionPane.showMessageDialog(null,
						"Данните са записани успешно !");
			}
		});
	}

	@Override
	public void renameArtikul(String oldArtikulName, String newArtikulName) {

		JDialog jDialog = (JDialog) SwingUtilities
				.getWindowAncestor(AvailableArtikulsTable.this);

		GetArtikulService service = new GetArtikulService();
		service.renameArtikul(oldArtikulName, newArtikulName, new RequestCallback2() {
			@Override
			public <T> void callback(T t) {

				jDialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

				JOptionPane.showMessageDialog(null,"Артикулът е преименуван успешно !");
			}
		});
	}

	public abstract String getTableName();
}
