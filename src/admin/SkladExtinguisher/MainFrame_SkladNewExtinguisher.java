package admin.SkladExtinguisher;

import admin.SkladExtinguisher.Renderers.NewExtRenderer2;
import admin.SkladExtinguisher.Workers.DeleteExtinguisherWorker;
import admin.SkladExtinguisher.Workers.SeeAllNewExtinguisherWorker;
import run.JDialoger;
import utils.LoadIcon;
import utils.MainPanel;
import utils.TooltipButton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MainFrame_SkladNewExtinguisher extends MainPanel {

	private JPanel basePanel = null;
	private JPanel northPanel = null;

	private TooltipButton seeDataButton = null;
	private TooltipButton addNewExtinguisherInSkladButton = null;
	private TooltipButton setPriceOfExtinguisherButton = null;
	private TooltipButton setNewQuantityButton = null;
	private TooltipButton deleteExtinguisherButton = null;
	private JPanel centerPanel = null;

	public static DefaultTableModel skladExtinguisherModel = null;
	public static JTable table = null;
	private JScrollPane scrollPane = null;
	private int SELECTED_INDEX;

	private String serviceNumber = null;

	public MainFrame_SkladNewExtinguisher(String serviceNumber) {
		SELECTED_INDEX = -1;
		this.serviceNumber = serviceNumber;

		basePanel = new JPanel();
		basePanel.setLayout(new BorderLayout());
		basePanel.setBorder(BorderFactory.createLineBorder(Color.black));

		northPanel = new JPanel();// GradientPanel();
		// northPanel.setPreferredSize(new Dimension((int) (this.WIDTH * 1.0),
		// (int) (this.HEIGHT * 0.07)));
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		addNewExtinguisherInSkladButton = new TooltipButton("Добави нов");

		addNewExtinguisherInSkladButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				String kontragent = "";
				String type = "";
				String wheight = "";
				String category = "";
				String brand = "";
				String prevValue = "";
				String percentProfit = "";
				if (SELECTED_INDEX != -1) {
					kontragent = skladExtinguisherModel.getValueAt(
							SELECTED_INDEX, 7).toString();

					type = skladExtinguisherModel.getValueAt(SELECTED_INDEX, 0)
							.toString();
					wheight = skladExtinguisherModel.getValueAt(SELECTED_INDEX,
							1).toString();

					category = skladExtinguisherModel.getValueAt(
							SELECTED_INDEX, 2).toString();

					brand = skladExtinguisherModel
							.getValueAt(SELECTED_INDEX, 3).toString();

					prevValue = skladExtinguisherModel.getValueAt(
							SELECTED_INDEX, 5).toString();

					percentProfit = skladExtinguisherModel.getValueAt(
							SELECTED_INDEX, 10).toString();
				}
				AddNewExtinguisherDialog addExt = new AddNewExtinguisherDialog(
						kontragent, prevValue, percentProfit);
				JDialoger jDialoger = new JDialoger();
				jDialoger.setTitle("Добави нов пожарогасител");
				jDialoger.setContentPane(addExt);
				jDialoger.setResizable(false);
				jDialoger.Show();
			}

		});
		setNewQuantityButton = new TooltipButton("Промени количество ");
		setNewQuantityButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (SELECTED_INDEX == -1) {
					JOptionPane.showMessageDialog(null,
							"Не е избран пожарогасител!");
					return;
				}
				int newQuantity = 0;

				try {
					newQuantity = Integer.parseInt(skladExtinguisherModel
							.getValueAt(SELECTED_INDEX, 4).toString());
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Невалидни данни!");
					return;
				}
				String kontragent = skladExtinguisherModel.getValueAt(
						SELECTED_INDEX, 7).toString();

				String invoiceByKontragent = skladExtinguisherModel.getValueAt(
						SELECTED_INDEX, 6).toString();

				String type = skladExtinguisherModel.getValueAt(SELECTED_INDEX,
						0).toString();
				String wheight = skladExtinguisherModel.getValueAt(
						SELECTED_INDEX, 1).toString();

				String category = skladExtinguisherModel.getValueAt(
						SELECTED_INDEX, 2).toString();

				String brand = skladExtinguisherModel.getValueAt(
						SELECTED_INDEX, 3).toString();
				ChangeQuantityNewExtinguisherDialog changeQuanityDialog = new ChangeQuantityNewExtinguisherDialog(
						kontragent, invoiceByKontragent, type, wheight,
						category, brand);
				JDialoger jDialoger = new JDialoger();
				jDialoger.setTitle("Добави нов пожарогасител");
				jDialoger.setContentPane(changeQuanityDialog);
				jDialoger.setResizable(false);
				jDialoger.Show();

				/*
				 * int yes_no = JOptionPane.showOptionDialog(null,
				 * "Желаете ли да съхраните въведените данни?", "",
				 * JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
				 * null, new String[] { "Да", "Не" }, // this is the array
				 * "default"); if (yes_no != 0) { return; } final JDialog jd =
				 * (JDialog) SwingUtilities
				 * .getWindowAncestor(MainFrame_SkladNewExtinguisher.this);
				 * jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				 * 
				 * UpdateNewQuantityOfExtinguisgerInDB updateQuantityWorker =
				 * new UpdateNewQuantityOfExtinguisgerInDB(
				 * skladExtinguisherModel.getValueAt(SELECTED_INDEX, 0)
				 * .toString(), skladExtinguisherModel.getValueAt(
				 * SELECTED_INDEX, 1).toString(),
				 * skladExtinguisherModel.getValueAt(SELECTED_INDEX, 2)
				 * .toString(), skladExtinguisherModel.getValueAt(
				 * SELECTED_INDEX, 3).toString(), newQuantity, jd);
				 * updateQuantityWorker.execute();
				 */
			}

		});

		setPriceOfExtinguisherButton = new TooltipButton("Запази нова цена");
		// setPriceOfExtinguisherButton.setEnabled(false);

		setPriceOfExtinguisherButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (SELECTED_INDEX == -1) {
					JOptionPane.showMessageDialog(null,
							"Не е избран пожарогасител!");
					return;
				}
				String kontragent = "";
				String invoiceByKontragent = "";
				String date = "";
				String type = "";
				String wheight = "";
				String category = "";
				String brand = "";
				String operator = "";
				if (SELECTED_INDEX != -1) {
					kontragent = skladExtinguisherModel.getValueAt(
							SELECTED_INDEX, 7).toString();

					invoiceByKontragent = skladExtinguisherModel.getValueAt(
							SELECTED_INDEX, 6).toString();

					date = skladExtinguisherModel.getValueAt(SELECTED_INDEX, 8)
							.toString();

					type = skladExtinguisherModel.getValueAt(SELECTED_INDEX, 0)
							.toString();
					wheight = skladExtinguisherModel.getValueAt(SELECTED_INDEX,
							1).toString();

					category = skladExtinguisherModel.getValueAt(
							SELECTED_INDEX, 2).toString();

					brand = skladExtinguisherModel
							.getValueAt(SELECTED_INDEX, 3).toString();

					operator = skladExtinguisherModel.getValueAt(
							SELECTED_INDEX, 9).toString();

					ChangePriceOfNewExtinguisherDialog addExt = new ChangePriceOfNewExtinguisherDialog(
							kontragent, invoiceByKontragent, date, type,
							wheight, category, brand, operator);
					JDialoger jDialoger = new JDialoger();
					jDialoger.setTitle("Добави нов пожарогасител");
					jDialoger.setContentPane(addExt);
					jDialoger.setResizable(false);
					jDialoger.Show();
				}
			}

		});
		deleteExtinguisherButton = new TooltipButton("Изтрий пожарогасител");
		deleteExtinguisherButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int index = SELECTED_INDEX;
				if (index == -1) {
					JOptionPane.showMessageDialog(null,
							"Не е избран пожарогасител!!!\n");
					return;
				}
				int yes_no = JOptionPane.showOptionDialog(null,
						"Наистина ли искате да изтриете пожарогасителя?", "",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, new String[] {
								"Да", "Не" }, // this is the array
						"default");
				if (yes_no != 0) {
					return;
				}
				String type = table.getValueAt(index, 0).toString();
				String wheight = table.getValueAt(index, 1).toString();
				String category = table.getValueAt(index, 2).toString();
				String brand = table.getValueAt(index, 3).toString();
				String invoice = table.getValueAt(index, 6).toString();
				String kontragent = table.getValueAt(index, 7).toString();
				DeleteExtinguisherWorker del = new DeleteExtinguisherWorker(
						type, wheight, category, brand, invoice, kontragent);
				del.execute();
			}

		});
		seeDataButton = new TooltipButton();
		seeDataButton.setPreferredSize(new Dimension((int) (this.WIDTH * 0.05),
				(int) (this.HEIGHT * 0.05)));

		seeDataButton.setAutoSizedIcon(seeDataButton, new LoadIcon().setIcons(refreshImage));
		seeDataButton.setToolTipText(getHTML_Text("ЗАРЕДИ ДАНННИТЕ"));
		seeDataButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JDialog jd = (JDialog) SwingUtilities
						.getWindowAncestor(MainFrame_SkladNewExtinguisher.this);
				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				SeeAllNewExtinguisherWorker seeAll = new SeeAllNewExtinguisherWorker(
						jd);
				ArrayList<Object[]> data = null;
				try {
					data = seeAll.doInBackground();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (data != null) {

					if (skladExtinguisherModel.getRowCount() > 0) {
						skladExtinguisherModel.setRowCount(0);
					}

					for (int i = 0; i < data.size(); i++) {
						skladExtinguisherModel.addRow(data.get(i));

					}
				}

			}

		});
		northPanel.add(seeDataButton);
		northPanel.add(addNewExtinguisherInSkladButton);
		northPanel.add(setNewQuantityButton);
		northPanel.add(setPriceOfExtinguisherButton);
		northPanel.add(deleteExtinguisherButton);

		centerPanel = new JPanel();

		skladExtinguisherModel = new DefaultTableModel(new Object[] { "Вид",
				"Маса", "Кат", "Марка", "Налични", "Цена", "Фактура",
				"Контрагент", "Дата", "Оператор", "Процент печалба" }, 0);

		table = new JTable(skladExtinguisherModel);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				SELECTED_INDEX = table.getSelectedRow();
			}
		});

		table.setDefaultRenderer(Object.class, new NewExtRenderer2());
		table.setRowHeight(MainPanel.getFontSize() + 15);
		table.getTableHeader().setReorderingAllowed(false);
		setColumnsWidth(table);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // this line set

		// JTable rowTable = new CommonResources.RowNumberTable(table); // ****

		scrollPane = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		scrollPane.setPreferredSize(new Dimension((this.WIDTH - 50),
				this.HEIGHT - 70));

		// scrollPane.setRowHeaderView(rowTable); // ****

		// scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER,// ****
		// rowTable.getTableHeader());// ****

		centerPanel.add(scrollPane);

		basePanel.add(northPanel, BorderLayout.NORTH);
		basePanel.add(centerPanel, BorderLayout.CENTER);

		this.add(basePanel);
	}

	private void setColumnsWidth(JTable table) {
		TableColumnModel tcm = table.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(this.WIDTH / 5);
		tcm.getColumn(1).setPreferredWidth(this.WIDTH / 15);
		tcm.getColumn(2).setPreferredWidth(this.WIDTH / 15);
		tcm.getColumn(3).setPreferredWidth(this.WIDTH / 10);
		tcm.getColumn(4).setPreferredWidth(this.WIDTH / 15);
		tcm.getColumn(5).setPreferredWidth(this.WIDTH / 15);
		tcm.getColumn(6).setPreferredWidth(this.WIDTH / 15);
		tcm.getColumn(7).setPreferredWidth(this.WIDTH / 5);
		tcm.getColumn(8).setPreferredWidth(this.WIDTH / 12);
		tcm.getColumn(9).setPreferredWidth(this.WIDTH / 12);
		tcm.getColumn(10).setPreferredWidth(this.WIDTH / 20);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				JFrame jf = new JFrame();
				MainFrame_SkladNewExtinguisher nep = new MainFrame_SkladNewExtinguisher(
						"0000000000000");
				jf.add(nep);
				jf.pack();
				jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				jf.setVisible(true);
			}

		});

	}

}
