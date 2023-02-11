package Calendar;

import Calendar.calendarrenderer.MontsTableRenderer;
import db.Calendar.TenhicalReviewByMonthsDB;
import mydate.MyGetDate;
import run.JustFrame;
import utils.HeaderTable;
import utils.MainPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class TechnicalReviewByMonths extends MainPanel {

	private JPanel mainPanel = null;
	private String[] clientTitle = null;
	private String[] detailsTitle = null;
	private DefaultTableModel clientTableModel = null; // client table model
	private DefaultTableModel detailsTableModel = null; // default table model
	private JTable clientTable = null;
	private JTable detailsTable = null;

	// private JPanel buttonPanel = null;

	private ArrayList<Object[]> detailList = new ArrayList<Object[]>();

	private HashSet<String> helpSet = new HashSet<String>();

	private TreeMap<Protokol_ID, ArrayList<Extinguishers>> resultMap = new TreeMap<Protokol_ID, ArrayList<Extinguishers>>();
	private HashMap<String, ArrayList<Extinguishers>> mouseClickMap = new HashMap<String, ArrayList<Extinguishers>>();
	private Object KEY = new Object();
	private String MONTH = "";
	private String from = new String();// GetDate.getReversedSystemDate();//
	private String to = "";
	// shows number rows on the left
	private HeaderTable headerTable;
	private HeaderTable headerTable2;

	public TechnicalReviewByMonths() {

		clientTitle = new String[] { "Клиент" };
		clientTableModel = new DefaultTableModel(clientTitle, 0) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};

		clientTable = new JTable(clientTableModel);
		clientTable.setDefaultRenderer(Object.class, new MontsTableRenderer());
		clientTable.setRowHeight(MainPanel.getFontSize() + 15);

		// JTable rowTable = new CommonResources.RowNumberTable(clientTable);
		// //****

		clientTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {

				if (me.getClickCount() == 2) {
					int row = clientTable.getSelectedRow();
					if (row >= 0) {
						KEY = clientTable.getValueAt(row, 0);

						if (detailsTableModel.getRowCount() > 0) {
							detailsTableModel.setRowCount(0);
						}

						if (resultMap != null) {
							ArrayList<Extinguishers> value = mouseClickMap
									.get(KEY);
							if (value != null) {
								for (int i = 0; i < value.size(); i++) {
									Extinguishers ext = value.get(i);
									detailsTableModel.addRow(new Object[] {
											ext.type + " " + ext.wheight,
											ext.TO_date, ext.P_Date,
											ext.HI_Date, ext.additional_data });
								}
								// show rows on the left
								headerTable2.tableModel.fireTableDataChanged();
								headerTable2.tableModel
										.fireTableRowsUpdated(0,
												headerTable2.tableModel
														.getRowCount() - 1);

							}
						}
					}
				}
			}
		});

		JScrollPane scroll = new JScrollPane(clientTable,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension(this.WIDTH / 3, this.HEIGHT - 200));
		// show rows on the left

		headerTable = new HeaderTable(clientTable);
		scroll.setRowHeaderView(headerTable.getRowsColumnTable());

		detailsTitle = new String[] { "Вид", "ТО", "П", "ХИ", "Доп. данни" };
		detailsTableModel = new DefaultTableModel(detailsTitle, 0);
		detailsTable = new JTable(detailsTableModel);
		detailsTable.getColumnModel().getColumn(0)
				.setPreferredWidth((int) (this.WIDTH * 0.2));
		detailsTable.getColumnModel().getColumn(1)
				.setPreferredWidth((int) (this.WIDTH * 0.05));
		detailsTable.getColumnModel().getColumn(2)
				.setPreferredWidth((int) (this.WIDTH * 0.05));
		detailsTable.getColumnModel().getColumn(3)
				.setPreferredWidth((int) (this.WIDTH * 0.05));

		// detailsTable.getColumnModel().getColumn(4)
		// .setPreferredWidth((int) (this.WIDTH * 0.05));

		// JTable rowTable2 = new CommonResources.RowNumberTable(detailsTable);
		// //****

		detailsTable.setDefaultRenderer(Object.class, new MontsTableRenderer(
				detailsTable));
		detailsTable.setRowHeight(MainPanel.getFontSize() + 15);

		JScrollPane scroll2 = new JScrollPane(detailsTable,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll2.setPreferredSize(new Dimension(this.WIDTH / 2,
				this.HEIGHT - 200));
		// show rows on the left
		headerTable2 = new HeaderTable(detailsTable);
		scroll2.setRowHeaderView(headerTable2.getRowsColumnTable());

		mainPanel = new JPanel();
		mainPanel.setOpaque(false);
		mainPanel.setPreferredSize(new Dimension(this.WIDTH - 20,
				this.HEIGHT - 100));
		mainPanel.add(scroll);
		mainPanel.add(scroll2);

		JPanel helpPane = new JPanel();
		helpPane.setOpaque(false);
		helpPane.setLayout(new BorderLayout());
		helpPane.add(mainPanel, BorderLayout.CENTER);

		JPanel northPanel = new JPanel();

		northPanel.setOpaque(false);

		final JComboBox<Object> ovcb = new JComboBox<Object>(new Object[] {
				"Януари", "Февруари", "Март", "Април", "Май", "Юни", "Юли",
				"Август", "Септември", "Октомври", "Ноември", "Декември" });
		ovcb.addActionListener(new ActionListener() {

			int currentMonth = Integer.parseInt(MyGetDate.getCurrentMonth());
			int choosedMonth;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				switch (ovcb.getSelectedItem().toString()) {
				case "Януари":
					from = "01.01.";
					to = "31.01.";
					choosedMonth = 1;
					break;
				case "Февруари":
					from = "01.02.";
					to = "28.02.";
					choosedMonth = 2;
					break;
				case "Март":
					from = "01.03.";
					to = "31.03.";
					choosedMonth = 3;
					break;
				case "Април":
					from = "01.04.";
					to = "30.04.";
					choosedMonth = 4;
					break;
				case "Май":
					from = "01.05.";
					to = "31.05.";
					choosedMonth = 5;
					break;
				case "Юни":
					from = "01.06.";
					to = "30.06.";
					choosedMonth = 6;
					break;
				case "Юли":
					from = "01.07.";
					to = "31.07.";
					choosedMonth = 7;
					break;
				case "Август":
					from = "01.08.";
					to = "31.08.";
					choosedMonth = 8;
					break;
				case "Септември":
					from = "01.09.";
					to = "30.09.";
					choosedMonth = 9;
					break;
				case "Октомври":
					from = "01.10.";
					to = "31.10.";
					choosedMonth = 10;
					break;
				case "Ноември":
					from = "01.11.";
					to = "30.11.";
					choosedMonth = 11;
					break;
				case "Декември":
					from = "01.12.";
					to = "31.12.";
					choosedMonth = 12;
					break;
				default:
					break;
				}
				// logic here
				String currentYear;
				if (choosedMonth < currentMonth) {
					currentYear = MyGetDate.getYearAfterYears(1);
				} else {
					currentYear = MyGetDate.getCurrentYear();
				}

				from = from + currentYear;
				to = to + currentYear;

				Worker w = new Worker();
				w.execute();

			}

		});
		ovcb.setPreferredSize(new Dimension(200, 50));

		northPanel.add(ovcb);

		helpPane.add(northPanel, BorderLayout.NORTH);

		this.setBackground(Color.decode("0xFF1141"));
		this.add(helpPane);
		this.setPreferredSize(new Dimension(this.WIDTH - 20, this.HEIGHT - 90));
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TechnicalReviewByMonths mp = new TechnicalReviewByMonths();
		JustFrame f = new JustFrame(mp);
		f.setTitle("Календар");
	}

	class Worker extends SwingWorker<Void, Void> {

		public Worker() {

		}

		@Override
		protected Void doInBackground() throws Exception {
			// TODO Auto-generated method stub
			final JDialog ancestor = (JDialog) SwingUtilities
					.getWindowAncestor(TechnicalReviewByMonths.this);

			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					// hide column numbers
					headerTable.tableModel.setRowCount(0);
					headerTable2.tableModel.setRowCount(0);
					// clear table contents
					clientTableModel.setRowCount(0);
					detailsTableModel.setRowCount(0);
					ancestor.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				}

			});

			try {

				detailList = TenhicalReviewByMonthsDB.getDetailsFrom_To(from,
						to);

				// ArrayList<Extinguishers> extList = null;
				if (detailList.size() > 0) {

					helpSet.clear();

					resultMap.clear();

					mouseClickMap.clear();

					for (int i = 0; i < detailList.size(); i++) {

						Object[] extinguisher = detailList.get(i);

						String used_number = extinguisher[6].toString();

						String id = extinguisher[0] + " " + extinguisher[6];

						String additional_data = extinguisher[7] != null ? extinguisher[7]
								.toString() : " - ";

						Extinguishers newExt = new Extinguishers(
								id,
								extinguisher[1],
								extinguisher[2],
								!extinguisher[3].equals("не") ? MyGetDate.getUrgentDays(
										MyGetDate.getReversedSystemDate(),
										extinguisher[3].toString())
										: extinguisher[3],
								!extinguisher[4].equals("не") ? MyGetDate.getUrgentDays(
										MyGetDate.getReversedSystemDate(),
										extinguisher[4].toString())
										: extinguisher[4],
								!extinguisher[5].equals("не") ? MyGetDate.getUrgentDays(
										MyGetDate.getReversedSystemDate(),
										extinguisher[5].toString())
										: extinguisher[5], additional_data);

						if (!helpSet.contains(used_number)) {

							ArrayList<Extinguishers> extList = new ArrayList<Extinguishers>();
							extList.add(newExt);

							resultMap.put(
									new Protokol_ID(extinguisher[0].toString(),
											extinguisher[6].toString()),
									extList);

							helpSet.add(used_number);

							mouseClickMap.put(id, extList);
						} else {
							ArrayList<Extinguishers> extList = mouseClickMap
									.get(id);
							if (extList != null)
								extList.add(newExt);
						}

					}

				}

			} finally {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

						// sort innermap order by urgentdays and insert into
						// table model
						if (detailList.size() > 0) {

							ArrayList<Extinguishers> sorted = new ArrayList<Extinguishers>();
							for (Map.Entry<Protokol_ID, ArrayList<Extinguishers>> clients : resultMap
									.entrySet()) {
								Extinguishers client = clients.getValue()
										.get(0);

								sorted.add(client);
							}
							// Collections.sort(sorted);

							for (int i = 0; i < sorted.size(); i++) {

								clientTableModel.addRow(new Object[] { sorted
										.get(i).client });
							}
						}
						setSetVerticalScrollBars(clientTable);

						detailList.clear();
						ancestor.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
						// show rows on the left

						headerTable.tableModel.fireTableDataChanged();
						headerTable.tableModel.fireTableRowsUpdated(0,
								headerTable.tableModel.getRowCount() - 1);

					}
				});

			}
			return null;

		}
	}

	private void setSetVerticalScrollBars(JTable table) {
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		for (int column = 0; column < table.getColumnCount(); column++) {
			TableColumn tableColumn = table.getColumnModel().getColumn(column);
			int preferredWidth = tableColumn.getMinWidth();
			int maxWidth = tableColumn.getMaxWidth();

			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer cellRenderer = table.getCellRenderer(row,
						column);
				Component c = table.prepareRenderer(cellRenderer, row, column);
				int width = c.getPreferredSize().width
						+ table.getIntercellSpacing().width;
				preferredWidth = Math.max(preferredWidth, width);

				// We've exceeded the maximum width, no need to check other rows
				if (preferredWidth >= maxWidth) {
					preferredWidth = maxWidth;
					break;
				}
			}

			tableColumn.setPreferredWidth(preferredWidth);
		}
	}
}
