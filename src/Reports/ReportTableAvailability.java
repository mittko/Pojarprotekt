package Reports;

import Reports.ReportsModel.ColumnGroup;
import Reports.ReportsModel.GroupableTableHeader;
import Reports.ReportsRenderers.AvailabilityTableRenderer;
import Reports.ReportsWorkers.ExportToExcellWorkerAvailable;

import mydate.MyGetDate;
import run.JustFrame;
import utility.LoadIcon;
import utility.MainPanel;
import utility.MyMath;
import utility.TooltipButton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class ReportTableAvailability extends MainPanel {

	public ReportTableAvailability(ArrayList<Object[]> deliveryBeforeFirstSelectedDate,
                                   ArrayList<Object[]> invoiceBeforeFirstSelectedDate,
                                   ArrayList<Object[]> deliveryBetweenSelectedDates,
                                   ArrayList<Object[]> invoiceBetweenSelectedDates, final String from,
                                   final String to, final String title) {

		JPanel childContainer = new JPanel();
		JPanel northPanel = new JPanel();
		childContainer.setLayout(new BorderLayout());

		JLabel titleLabel = new JLabel(title);

		String[] columns = new String[] { "Артикул", "Брой", "Ед. цена",
				"Ст-ст", "Брой", "Ед. цена", "Ст-ст", "Брой", "Ед. цена",
				"Ст-ст", "Брой", "Ед. цена", "Ст-ст" };

		final DefaultTableModel dftm = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		final JTable table = new JTable(dftm) {
			@Override
			protected JTableHeader createDefaultTableHeader() {
				return new GroupableTableHeader(columnModel);
			}
		};

		TableColumnModel cm = table.getColumnModel();
		ColumnGroup g_name = new ColumnGroup("Налични към " + MyGetDate.getDateBeforeAnotherDate(1,
				MyGetDate.getDateFromString(from)));
		g_name.add(cm.getColumn(1));
		g_name.add(cm.getColumn(2));
		g_name.add(cm.getColumn(3));

		ColumnGroup g_lang = new ColumnGroup("Доставки за период " + from
				+ " - " + to);
		g_lang.add(cm.getColumn(4));
		g_lang.add(cm.getColumn(5));
		g_lang.add(cm.getColumn(6));

		ColumnGroup g_other = new ColumnGroup("Продажби за период " + from
				+ " - " + to);
		g_other.add(cm.getColumn(7));
		g_other.add(cm.getColumn(8));
		g_other.add(cm.getColumn(9));

		// g_lang.add(g_other);
		ColumnGroup g_other2 = new ColumnGroup("Наличност към " + to);
		g_other2.add(cm.getColumn(10));
		g_other2.add(cm.getColumn(11));
		g_other2.add(cm.getColumn(12));

		GroupableTableHeader header = (GroupableTableHeader) table
				.getTableHeader();
		header.addColumnGroup(g_name);
		header.addColumnGroup(g_lang);
		header.addColumnGroup(g_other);
		header.addColumnGroup(g_other2);

		table.setDefaultRenderer(Object.class, new AvailabilityTableRenderer());

		TreeMap<String, Artikul> artikuls = new TreeMap<String, Artikul>();
		for (Object[] objects : deliveryBeforeFirstSelectedDate) {

			String artikul = objects[0].toString();

			int deliveryQuantity = Integer
					.parseInt(objects[1].toString());

			double deliveryValue = Double
					.parseDouble(objects[2].toString()
							.replace(",", "."));

			String deliveryDate = objects[3].toString();

			if (!artikuls.containsKey(artikul)) {
				artikuls.put(artikul, new Artikul(0, 0, 0, 0, 0,
						0, 0, 0));
			}
			Artikul art = artikuls.get(artikul);

			art.updateFirstDeliveryBeforeSelectedDate(MyGetDate.getDateFromString(deliveryDate));
			art.deliveryQuantityBeforeFirstSelectedDate += deliveryQuantity;
			art.deliveryPriceBeforeFirstSelectedDate = deliveryValue;

		}
		for (Object[] objects : invoiceBeforeFirstSelectedDate) {

			String artikul = objects[0].toString();

			int invoiceQuantity = Integer.parseInt(objects[1]
					.toString());
			double invoicePrice = Double
					.parseDouble(objects[2].toString()
							.replace(",", "."));

			if (artikuls.containsKey(artikul)) { // не може да има продажби без
				// да има доставки ???
				// ако няма доставки преди тази дата не записва продажбите преди
				// датата !!!

				Artikul art = artikuls.get(artikul);

				String invoiceDateStr = objects[3].toString();
				Date invoiceDate = MyGetDate.getDateFromString(invoiceDateStr);

				int compare = invoiceDate.compareTo(art.firstDeliveryBeforeSelectedDate);
				if (compare >= 0) {
					art.sellQuantityBeforeFirstSelectedDate += invoiceQuantity;
					art.sellPriceBeforeFirstSelectedDate = invoicePrice;
				}

			}

		}

		for (Object[] deliveryBetweenSelectedDate : deliveryBetweenSelectedDates) {

			String artikul = deliveryBetweenSelectedDate[0].toString();

			int deliveryQuantity = Integer.parseInt(deliveryBetweenSelectedDate[1]
					.toString());
			double deliveryValue = Double
					.parseDouble(deliveryBetweenSelectedDate[2].toString()
							.replace(",", "."));
			String deliveryDate = deliveryBetweenSelectedDate[3].toString();

			if (!artikuls.containsKey(artikul)) {
				artikuls.put(artikul, new Artikul(0, 0, 0, 0, 0,
						0, 0, 0));
			}
			Artikul art = artikuls.get(artikul);

			art.deliveryQuantityAfterFirstSelectedDate += deliveryQuantity;
			art.deliveryPriceAfterFirstSelectedDate = deliveryValue;
			art.updateFirstDeliveryBeforeSelectedDate(MyGetDate.getDateFromString(deliveryDate));

		}
		for (Object[] invoiceBetweenSelectedDate : invoiceBetweenSelectedDates) {

			String artikul = invoiceBetweenSelectedDate[0].toString();

			int invoiceQuantity = Integer.parseInt(invoiceBetweenSelectedDate[1]
					.toString());
			double invoiceValue = Double.parseDouble(invoiceBetweenSelectedDate[2]
					.toString().replace(",", "."));


			if (artikuls.containsKey(artikul)) {

				Artikul art = artikuls.get(artikul);


				String invoiceDateStr = invoiceBetweenSelectedDate[3].toString();
				Date invoiceDate = MyGetDate.getDateFromString(invoiceDateStr);


				int compare = invoiceDate.compareTo(art.firstDeliveryBeforeSelectedDate);
				if (compare >= 0) {
					art.sellQuantityAfterFirstSelectedDate += invoiceQuantity;
					art.sellPriceAfterFirstSelectedDate = invoiceValue;

				}

			}

		}

		for (Map.Entry<String, Artikul> map : artikuls.entrySet()) {
			Artikul art = map.getValue();
			String artikul = map.getKey();// artikul
			int availableQuantityBeforeFirstDate = art.deliveryQuantityBeforeFirstSelectedDate
					- art.sellQuantityBeforeFirstSelectedDate;// количество артикул //
														// преди първ дата
			double availablePriceBeforeFirstDate = art.deliveryPriceBeforeFirstSelectedDate;// or
																					// art.sellPriceBeforeFirstDate
																					// //
																					// ?
																					// //
																					// цена
																					// //
																					// на
																					// //
																					// артикул
																					// //
																					// преди
																					// *
																					// //
																					// първа
																					// //
																					// дата
			double availableArtikulValuesBeforeFirstDate = availableQuantityBeforeFirstDate
					* availablePriceBeforeFirstDate;// обща стойност на артикул
													// // преди първа дата

			int deliveryQuantityAfterFirstDate = art.deliveryQuantityAfterFirstSelectedDate;// брой
																					// //
																					// доставки
																					// //
																					// след
																					// ////
																					// *
																					// първа
																					// //дата
			double deliveryPriceAfterFirstDate = art.deliveryPriceAfterFirstSelectedDate;// цена
																					// //
																					// на
																					// //
																					// доставка
																					// //
																					// след
																					// //

			double deliveryValuesAfterFirstDate = deliveryQuantityAfterFirstDate
					* deliveryPriceAfterFirstDate;// обща

			int sellQuantityAfterFirstDate = art.sellQuantityAfterFirstSelectedDate;
			double sellArtikulPriceAfterFirstDate = art.sellPriceAfterFirstSelectedDate;
			double sellArtikulValuesAfterFirstDate = sellQuantityAfterFirstDate
					* sellArtikulPriceAfterFirstDate;// обша // стойност // на
														// // продажби

			int finlArtikulQuantityAfterFirstDate = availableQuantityBeforeFirstDate
					+ (deliveryQuantityAfterFirstDate - sellQuantityAfterFirstDate);
			double finalSellArtikulPriceAfterFirstDate = deliveryQuantityAfterFirstDate > 0 ? deliveryPriceAfterFirstDate
					: availablePriceBeforeFirstDate;
			double finalSellArtikulValue = finlArtikulQuantityAfterFirstDate
					* finalSellArtikulPriceAfterFirstDate;

		//	if(finalSellArtikulValue >= 0) има бъг => продажбите са повече от доставките ???
			dftm.addRow(new Object[] { artikul,
					availableQuantityBeforeFirstDate,
					MyMath.round(availablePriceBeforeFirstDate, 2),
					MyMath.round(availableArtikulValuesBeforeFirstDate, 2),
					deliveryQuantityAfterFirstDate,
					MyMath.round(deliveryPriceAfterFirstDate, 2),
					MyMath.round(deliveryValuesAfterFirstDate, 2),
					sellQuantityAfterFirstDate,
					MyMath.round(sellArtikulPriceAfterFirstDate, 2),
					MyMath.round(sellArtikulValuesAfterFirstDate, 2),
					finlArtikulQuantityAfterFirstDate,
					MyMath.round(finalSellArtikulPriceAfterFirstDate, 2),
					MyMath.round(finalSellArtikulValue, 2) });
		}

		setColumnSize(table);
		table.setRowHeight(MainPanel.getFontSize() + 15);

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JScrollPane scrollPane = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		// scrollPane.getVerticalScrollBar().setUI(new YourUI());
		// scrollPane.getHorizontalScrollBar().setUI(new YourUI());
		scrollPane.setPreferredSize(new Dimension(WIDTH - 50, HEIGHT - 70));

		TooltipButton excellButton = new TooltipButton();
		excellButton.setPreferredSize(new Dimension(50, 50));
		excellButton.setToolTipText(getHTML_Text("ЗАПИШИ В EXCEL"));
		excellButton.setAutoSizedIcon(excellButton, new LoadIcon().setIcons(excellImage));
		excellButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JDialog jd = (JDialog) SwingUtilities
						.getWindowAncestor(ReportTableAvailability.this);
				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				ExportToExcellWorkerAvailable excellWorker = new ExportToExcellWorkerAvailable(
						table, dftm, from, to, title + ".xls", jd);
				excellWorker.execute();
			}

		});
		northPanel.add(titleLabel);
		northPanel.add(excellButton);
		childContainer.add(northPanel, BorderLayout.NORTH);
		childContainer.add(scrollPane, BorderLayout.CENTER);

		this.add(childContainer);
	}

	private void setColumnSize(JTable table) {
		table.getColumnModel().getColumn(0).setPreferredWidth(400);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.getColumnModel().getColumn(5).setPreferredWidth(150);
		table.getColumnModel().getColumn(6).setPreferredWidth(150);
		table.getColumnModel().getColumn(7).setPreferredWidth(100);
		table.getColumnModel().getColumn(8).setPreferredWidth(150);
		table.getColumnModel().getColumn(9).setPreferredWidth(150);
		table.getColumnModel().getColumn(10).setPreferredWidth(100);
		table.getColumnModel().getColumn(11).setPreferredWidth(150);
		table.getColumnModel().getColumn(12).setPreferredWidth(150);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				ReportTableAvailability r = new ReportTableAvailability(null,
						null, null, null, "01.06.2016", "11.02.2021",
						"Справка Доставки");
				JustFrame f = new JustFrame(r);
				f.setTitle("Справки");
				f.pack();
			}

		});
	}

	private class Artikul {
		int deliveryQuantityBeforeFirstSelectedDate;
		double deliveryPriceBeforeFirstSelectedDate;
		int sellQuantityBeforeFirstSelectedDate;
		double sellPriceBeforeFirstSelectedDate;

		int deliveryQuantityAfterFirstSelectedDate;
		double deliveryPriceAfterFirstSelectedDate;
		int sellQuantityAfterFirstSelectedDate;
		double sellPriceAfterFirstSelectedDate;

		private Date firstDeliveryBeforeSelectedDate = MyGetDate.getDateFromString(MyGetDate
				.getReversedSystemDate());
//		private Date firstDeliveryAfterSelectedDate = MyGetDate.getDateFromString(MyGetDate
//						.getReversedSystemDate());

		public Artikul(
				int deliveryQuantityBeforeFirstSelectedDate,
				double deliveryPriceBeforeFirstSelectedDate,
				int sellQuantityBeforeFirstSelectedDate,
				double sellPriceBeforeFirstSelectedDate,
				int deliveryQuantityAfterFirstSelectedDate,
				double deliveryPriceAfterFirstSelectedDate,
				int sellQuantityAfterFirstSelectedDate, double sellPriceAfterFirstSelectedDate) {
			super();

			// this.deliveryDate =
			// GetDate.getDateFromString(deliveryStringDate);

			this.deliveryQuantityBeforeFirstSelectedDate = deliveryQuantityBeforeFirstSelectedDate;
			this.deliveryPriceBeforeFirstSelectedDate = deliveryPriceBeforeFirstSelectedDate;
			this.sellQuantityBeforeFirstSelectedDate = sellQuantityBeforeFirstSelectedDate;
			this.sellPriceBeforeFirstSelectedDate = sellPriceBeforeFirstSelectedDate;
			this.deliveryQuantityAfterFirstSelectedDate = deliveryQuantityAfterFirstSelectedDate;
			this.deliveryPriceAfterFirstSelectedDate = deliveryPriceAfterFirstSelectedDate;
			this.sellQuantityAfterFirstSelectedDate = sellQuantityAfterFirstSelectedDate;
			this.sellPriceAfterFirstSelectedDate = sellPriceAfterFirstSelectedDate;
		}

		public void updateFirstDeliveryBeforeSelectedDate(Date deliveryDate) {
			int compare = deliveryDate.compareTo(this.firstDeliveryBeforeSelectedDate);
			if (compare < 0) {
				this.firstDeliveryBeforeSelectedDate = deliveryDate;
			}
//			System.out.println("dates " + deliveryDate + " xxx "
//					+ this.firstDeliveryBeforeSelectedDate);
		}
//		public void updateFirstDeliveryAfterSelectedDate(Date deliveryDate) {
//			int compare = deliveryDate.compareTo(this.firstDeliveryAfterSelectedDate);
//			if (compare < 0) {
//				this.firstDeliveryAfterSelectedDate = deliveryDate;
//				// System.out.println(this.firstDeliveryDate);
//			}
////			System.out.println("dates " + deliveryDate + " xxx "
////					+ this.firstDeliveryAfterSelectedDate);
//		}
	}

}
