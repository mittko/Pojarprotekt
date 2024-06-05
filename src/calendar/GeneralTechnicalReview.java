package calendar;

import calendar.models.DetailsModel;
import calendar.models.MyTModel;
import calendar.renderers.DetailsRenderer;
import calendar.renderers.MyTableCellRenderer;
import exportoexcell.diary.ChoiseDateForDiary;
import db.Calendar.GeneralTechnicalReviewDB;
import http.technical_review.TechnicalReviewService;
import menu.MainMenu;
import mydate.MyGetDate;
import run.JDialoger;
import utils.HeaderTable;
import utils.LoadIcon;
import utils.MainPanel;
import utils.TooltipButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.*;

public class GeneralTechnicalReview extends MainPanel {

	private final JTable mainTable;
	private MyTModel tModel = null;
	private int tableRow;

	private final CardLayout cardLayout = new CardLayout();
	private JPanel cardPanel = null;

	private ArrayList<Object[]> list = null; // get info from Main

	private final TreeMap<Protokol_ID, ArrayList<Extinguishers>> detailsMap = new TreeMap<Protokol_ID, ArrayList<Extinguishers>>();
	private final HashSet<String> helpSet = new HashSet<String>();
	private final HashMap<String, ArrayList<Extinguishers>> mouseClickMap = new HashMap<String, ArrayList<Extinguishers>>();

	private String KEY = "";

	private final HeaderTable headerTable;
	private final HeaderTable headerTable2;

	public GeneralTechnicalReview() {

		this.setLayout(new FlowLayout());
		this.setBackground(Color.decode("0xFF1141"));

		JPanel mainPanel = new JPanel();
		mainPanel.setOpaque(false);

		JPanel tablePanel = new JPanel();
		tablePanel.setOpaque(false);

		Object[] title = { "Клиент", "ТО", "П", "ХИ" };
		tModel = new MyTModel(title);
		mainTable = new JTable(tModel);

		mainTable.setDefaultRenderer(Object.class, new MyTableCellRenderer(
				mainTable));
		mainTable.setRowHeight(MainPanel.getFontSize() + 15);

		mainTable.getColumnModel().getColumn(0)
				.setPreferredWidth((int) (this.WIDTH * 0.2));
		mainTable.getColumnModel().getColumn(1)
				.setPreferredWidth((int) (this.WIDTH * 0.05));
		mainTable.getColumnModel().getColumn(2)
				.setPreferredWidth((int) (this.WIDTH * 0.05));
		mainTable.getColumnModel().getColumn(3)
				.setPreferredWidth((int) (this.WIDTH * 0.05));
		mainTable.getTableHeader().setReorderingAllowed(false);

		JScrollPane scroll = new JScrollPane(mainTable,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		headerTable = new HeaderTable(mainTable);
		scroll.setRowHeaderView(headerTable.getRowsColumnTable());

		mainPanel.setPreferredSize(new Dimension((int) (this.WIDTH * 1.0) - 25,
				(int) (this.HEIGHT * 0.82)));

		scroll.setPreferredSize(new Dimension((int) ((mainPanel
				.getPreferredSize().getWidth()) * 0.5), (int) (mainPanel
				.getPreferredSize().getHeight() * 0.90)));

		mainPanel.add(scroll);

		tablePanel.add(mainPanel);

		JPanel detailsPanel = new JPanel();
		detailsPanel.setPreferredSize(new Dimension((int) (this.WIDTH * 0.4),
				(int) (this.HEIGHT * 0.735)));
		detailsPanel.setLayout(new BorderLayout());

		Object[] title2 = new Object[] { "Вид", "ТО", "П", "ХИ", "Доп. данни" };
		final DetailsModel detailsModel = new DetailsModel(title2);

		JTable detailsTable = new JTable(detailsModel);
		detailsTable.getColumnModel().getColumn(0)
				.setPreferredWidth((int) (this.WIDTH * 0.15));
		detailsTable.getColumnModel().getColumn(1)
				.setPreferredWidth((int) (this.WIDTH * 0.04));
		detailsTable.getColumnModel().getColumn(2)
				.setPreferredWidth((int) (this.WIDTH * 0.04));
		detailsTable.getColumnModel().getColumn(3)
				.setPreferredWidth((int) (this.WIDTH * 0.04));
		detailsTable.getColumnModel().getColumn(4)
				.setPreferredWidth((int) (this.WIDTH * 0.15));

		detailsTable.setDefaultRenderer(Object.class, new DetailsRenderer(
				detailsTable));
		detailsTable.setRowHeight(MainPanel.getFontSize() + 15);

		JScrollPane scroll2 = new JScrollPane(detailsTable,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		// show rows on the left
		headerTable2 = new HeaderTable(detailsTable);
		scroll2.setRowHeaderView(headerTable2.getRowsColumnTable());

		JLabel label = new JLabel(
				"<html><font>Оставащи дни до следващо обслужване</font></html>");
		label.setPreferredSize(new Dimension((int) detailsPanel
				.getPreferredSize().getWidth(), 40));
		label.setBorder(BorderFactory.createLineBorder(Color.black));

		detailsPanel.add(label, BorderLayout.NORTH);
		detailsPanel.add(scroll2, BorderLayout.CENTER);

		mainPanel.add(detailsPanel);

		mainTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {

				tableRow = mainTable.rowAtPoint(evt.getPoint());
				if (tableRow > -1 && evt.getClickCount() == 2) {
					KEY = mainTable.getValueAt(tableRow, 0).toString();
					ArrayList<Extinguishers> values = mouseClickMap.get(KEY);
					if (values != null) {
						detailsModel.appendData(values);
					}
					// show rows on the left
					headerTable2.tableModel.fireTableDataChanged();
					headerTable2.tableModel.fireTableRowsUpdated(0,
							headerTable2.tableModel.getRowCount() - 1);

				}

			}
		});

		Box box = Box.createHorizontalBox();

		box.setPreferredSize(new Dimension((int) (this.WIDTH * 1.0) - 20,
				(int) (this.HEIGHT * 0.1)));

		tablePanel.setPreferredSize(new Dimension(
				(int) (this.WIDTH * 1.0) - 20, (int) (this.HEIGHT * 0.75)));

		box.add(Box.createGlue());


		TooltipButton calendarButton = new TooltipButton();

		calendarButton
				.setToolTipText(getHTML_Text("ВИЖ ПОЖАРОГАСИТЕЛИ ПО МЕСЕЦИ"));
		calendarButton.setPreferredSize(new Dimension((int) (box
				.getPreferredSize().getWidth() * 0.045), (int) (box
				.getPreferredSize().getHeight() * 0.8)));

		calendarButton
				.setAutoSizedIcon(calendarButton, new LoadIcon().setIcons(calendarImage));

		calendarButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				TechnicalReviewByMonths mp = new TechnicalReviewByMonths();
				JDialoger jDialog = new JDialoger();
				jDialog.setContentPane(mp);
				jDialog.setResizable(false);
				jDialog.setTitle("Наближаващо обслужване по месеци");
				jDialog.Show();
			}

		});


		TooltipButton next = new TooltipButton();
		next.setToolTipText(getHTML_Text("ПРЕМИНИ КЪМ МЕНЮ"));
		next.setPreferredSize(new Dimension((int) (box.getPreferredSize()
				.getWidth() * 0.045),
				(int) (box.getPreferredSize().getHeight() * 0.8)));
		;
		next.setAutoSizedIcon(next, new LoadIcon().setIcons(enterImage2));

		next.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cardLayout.next(cardPanel);
			}

		});

		TooltipButton diaryButton = new TooltipButton();
		diaryButton.setToolTipText(getHTML_Text("Създай дневник"));
		diaryButton.setPreferredSize(new Dimension((int) (box
				.getPreferredSize().getWidth() * 0.045), (int) (box
				.getPreferredSize().getHeight() * 0.8)));
		diaryButton.setAutoSizedIcon(diaryButton, new LoadIcon().setIcons(diaryImage));

		diaryButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				JDialoger jd = new JDialoger();
				ChoiseDateForDiary choise = new ChoiseDateForDiary(jd);
				jd.setContentPane(choise);
				jd.setResizable(false);
				jd.Show();

			}

		});
		box.add(diaryButton);
		box.add(new JLabel("     "));
		box.add(calendarButton);
		box.add(new JLabel("      "));
		box.add(next);

		cardPanel = new JPanel();
		cardPanel.setPreferredSize(new Dimension((int) (this.WIDTH * 1.0) - 20,
				(int) (this.HEIGHT * 0.75)));
		cardPanel.setLayout(cardLayout);

		cardPanel.add(tablePanel);

		MainMenu mm = new MainMenu();

		cardPanel.add(mm);

		cardPanel.setOpaque(false);
		JPanel noMinimize = new JPanel();
		noMinimize.setOpaque(false);
		noMinimize.setLayout(new BorderLayout());

		noMinimize.add(cardPanel, BorderLayout.CENTER);

		noMinimize.add(box, BorderLayout.SOUTH);
		this.add(noMinimize);

	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void loadData() {
		final JFrame f = (JFrame) SwingUtilities
				.getWindowAncestor(GeneralTechnicalReview.this);
		f.setCursor(new Cursor(Cursor.WAIT_CURSOR));

		final String from = "01." + MyGetDate.getCurrentMonth() + "."
				+ MyGetDate.getCurrentYear();
		final String to = MyGetDate.getDaysFromCurrentMonth() + "."
				+ MyGetDate.getCurrentMonth() + "."
				+ MyGetDate.getCurrentYear();

		TechnicalReviewService service = new TechnicalReviewService();

		int a = 0;

		service.getTechnicalReview(from,to);

		SwingWorker<Boolean, Void> sw = new SwingWorker<Boolean, Void>() {

			@SuppressWarnings("finally")
			@Override
			protected Boolean doInBackground() throws Exception {
				// TODO Auto-generated method stub

				try {
					// load saller data
					// MainPanel.loadSallerData();

					// then ????? 01 or curr date


					list = GeneralTechnicalReviewDB.getTechnicalPreview(from,
							to);

					getResult(list);

				} finally {
					SwingUtilities.invokeLater(new Runnable() {

						@Override
						public void run() {

							// TODO Auto-generated method stub
							visualizeResult();

							f.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

						}

					});

				}
				return true;
			}
		};
		sw.execute();
	}

	private void getResult(ArrayList<Object[]> list) {
		ArrayList<Extinguishers> value = null;
		for (Object[] key : list) {
			String id = key[0] + " " + key[6];
			String used_number = key[6].toString();
			String additional_data = key[7] != null ? key[7].toString() : " - ";

			// ArrayList<Extinguishers> value = detailsMap.get(id); // check if
			// key (client)
			// exist in hashtable
			// key[0] -> client
			// key[1] -> type
			// key[2] -> wheight
			// key[3] -> T_O
			// key[4] -> P
			// key[5] -> HI
			// key[6] -> number
			// key[7] -. additional_dataDA
			if (!helpSet.contains(used_number)) {
				value = new ArrayList<Extinguishers>();
				value.add(new Extinguishers(id, key[1], key[2], !key[3]
						.equals("не") ? MyGetDate.getUrgentDays(
						MyGetDate.getReversedSystemDate(), key[3].toString())
						: key[3], !key[4].equals("не") ? MyGetDate.getUrgentDays(
						MyGetDate.getReversedSystemDate(), key[4].toString())
						: key[4], !key[5].equals("не") ? MyGetDate.getUrgentDays(
						MyGetDate.getReversedSystemDate(), key[5].toString())
						: key[5], additional_data));

				detailsMap.put(
						new Protokol_ID(key[0].toString(), key[6].toString()),
						value);

				mouseClickMap.put(id, value);

				helpSet.add(used_number);

			} else {
				value.add(new Extinguishers(id, key[1], key[2], !key[3]
						.equals("не") ? MyGetDate.getUrgentDays(
						MyGetDate.getReversedSystemDate(), key[3].toString())
						: key[3], !key[4].equals("не") ? MyGetDate.getUrgentDays(
						MyGetDate.getReversedSystemDate(), key[4].toString())
						: key[4], !key[5].equals("не") ? MyGetDate.getUrgentDays(
						MyGetDate.getReversedSystemDate(), key[5].toString())
						: key[5], additional_data));
			}
		}
	}

	private void visualizeResult() {
		ArrayList<Extinguishers> list = new ArrayList<Extinguishers>();
		for (Map.Entry<Protokol_ID, ArrayList<Extinguishers>> map : detailsMap
				.entrySet()) {
			Extinguishers value = map.getValue().get(0);
			list.add(value); // value
		}
		// Collections.sort(list);
		for (Extinguishers ext : list) {
			Object[] mostUrgent = new Object[] { ext.client, ext.TO_date,
					ext.P_Date, ext.HI_Date };
			tModel.addRow(mostUrgent);
		}
		// update rows to show on the left
		headerTable.tableModel.fireTableDataChanged();
		headerTable.tableModel.fireTableRowsUpdated(0,
				headerTable.tableModel.getRowCount() - 1);
	}
}
