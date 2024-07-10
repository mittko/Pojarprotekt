package calendar;

import calendar.renderers.MontsTableRenderer;
import db.Calendar.TenhicalReviewByMonthsDB;
import http.RequestCallback;
import http.technical_review.TechnicalReviewService;
import models.TechnicalReview;
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
import java.util.List;

public class TechnicalReviewByMonths extends MainPanel {

	private String[] clientTitle = null;
	private DefaultTableModel clientTableModel = null; // client table model
	private DefaultTableModel detailsTableModel = null; // default table model
	private JTable clientTable = null;

	private ArrayList<Object[]> detailList = new ArrayList<Object[]>();

	private final HashSet<String> helpSet = new HashSet<String>();

	private final TreeMap<Protokol_ID, ArrayList<Extinguishers>> resultMap = new TreeMap<Protokol_ID, ArrayList<Extinguishers>>();
	private final HashMap<String, ArrayList<Extinguishers>> mouseClickMap = new HashMap<String, ArrayList<Extinguishers>>();
	private Object KEY = new Object();
	private String from = "";// GetDate.getReversedSystemDate();//
	private String to = "";
	// shows number rows on the left
	private final HeaderTable headerTable;
	private final HeaderTable headerTable2;

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
								for (Extinguishers ext : value) {
									detailsTableModel.addRow(new Object[]{
											ext.type + " " + ext.wheight,
											ext.TO_date, ext.P_Date,
											ext.HI_Date, ext.additional_data});
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

		String[] detailsTitle = new String[]{"Вид", "ТО", "П", "ХИ", "Доп. данни"};
		detailsTableModel = new DefaultTableModel(detailsTitle, 0);
		JTable detailsTable = new JTable(detailsTableModel);
		detailsTable.getColumnModel().getColumn(0)
				.setPreferredWidth((int) (this.WIDTH * 0.2));
		detailsTable.getColumnModel().getColumn(1)
				.setPreferredWidth((int) (this.WIDTH * 0.05));
		detailsTable.getColumnModel().getColumn(2)
				.setPreferredWidth((int) (this.WIDTH * 0.05));
		detailsTable.getColumnModel().getColumn(3)
				.setPreferredWidth((int) (this.WIDTH * 0.05));


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

		JPanel mainPanel = new JPanel();
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

				TechnicalReviewService service = new TechnicalReviewService();
				service.getTechnicalReview(from, to, new RequestCallback() {
					@Override
					public <T> void callback(List<T> objects) {
						
						List<TechnicalReview> technicalReviews = (List<TechnicalReview>) objects;
						// hide column numbers
						headerTable.tableModel.setRowCount(0);
						headerTable2.tableModel.setRowCount(0);
						// clear table contents
						clientTableModel.setRowCount(0);
						detailsTableModel.setRowCount(0);
						final JDialog ancestor = (JDialog) SwingUtilities
								.getWindowAncestor(TechnicalReviewByMonths.this);
						ancestor.setCursor(new Cursor(Cursor.WAIT_CURSOR));


						if (technicalReviews.size() > 0) {

							helpSet.clear();

							resultMap.clear();

							mouseClickMap.clear();

							for (TechnicalReview technicalReview : technicalReviews) {
                                // key[0] -> client
								// key[1] -> type
								// key[2] -> wheight
								// key[3] -> T_O
								// key[4] -> P
								// key[5] -> HI
								// key[6] -> number
								// key[7] -. additional_dataDA
								String used_number = technicalReview.getNumber(); //extinguisher[6].toString();

								String id = technicalReview.getClient() + 
										" " + technicalReview.getNumber();// extinguisher[0] + " " + extinguisher[6];

								String additional_data = 
										technicalReview.getAdditional_data() != null ? technicalReview.getAdditional_data()
												:  " - ";
								//		extinguisher[7] != null ? extinguisher[7].toString() : " - ";

								Extinguishers newExt = new Extinguishers(
										id,
										technicalReview.getType(),
										technicalReview.getWheight(),
										!technicalReview.getT_O().equals("не") ? MyGetDate.getUrgentDays(
												MyGetDate.getReversedSystemDate(),
												technicalReview.getT_O())
												: technicalReview.getT_O(),
										!technicalReview.getP().equals("не") ? MyGetDate.getUrgentDays(
												MyGetDate.getReversedSystemDate(),
												technicalReview.getP())
												: technicalReview.getP(),
										!technicalReview.getHI().equals("не") ? MyGetDate.getUrgentDays(
												MyGetDate.getReversedSystemDate(),
												technicalReview.getHI())
												: technicalReview.getHI(), additional_data);

								if (!helpSet.contains(used_number)) {

									ArrayList<Extinguishers> extList = new ArrayList<>();
									extList.add(newExt);

									resultMap.put(new Protokol_ID(technicalReview.getClient(),
													technicalReview.getNumber()),
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

						if (technicalReviews.size() > 0) {

							ArrayList<Extinguishers> sorted = new ArrayList<>();
							for (Map.Entry<Protokol_ID, ArrayList<Extinguishers>> clients : resultMap
									.entrySet()) {
								Extinguishers client = clients.getValue()
										.get(0);

								sorted.add(client);
							}
							// Collections.sort(sorted);

							for (Extinguishers extinguishers : sorted) {

								clientTableModel.addRow(new Object[]{extinguishers.client});
							}
						}
						setSetVerticalScrollBars(clientTable);

						ancestor.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
						// show rows on the left

						headerTable.tableModel.fireTableDataChanged();
						headerTable.tableModel.fireTableRowsUpdated(0,
								headerTable.tableModel.getRowCount() - 1);
						
					}
				});
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
