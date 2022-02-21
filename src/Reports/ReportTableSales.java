//package Reports;
//
//import java.awt.BorderLayout;
//import java.awt.Cursor;
//import java.awt.Dimension;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.TreeMap;
//
//import javax.swing.JDialog;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JTable;
//import javax.swing.SwingUtilities;
//import javax.swing.table.DefaultTableModel;
//
//import run.JustFrame;
//import utility.MainPanel;
//import utility.MyMath;
//import utility.TooltipButton;
//import Date.GetDate;
//import Reports.ReportsRenderers.DeliveryTableRenderer;
//import Reports.ReportsWorkers.ExportToExcellWorkerSales;
//
//public class ReportTableSales extends MainPanel {
//
//	public ReportTableSales(ArrayList<Object[]> invoices,
//			ArrayList<Object[]> delivery, final String title) {
//		JPanel childContainer = new JPanel();
//		JPanel northPanel = new JPanel();
//		childContainer.setLayout(new BorderLayout());
//
//		JLabel titleLabel = new JLabel(title);
//		String[] columns = new String[] { "1", "2", "3", "4", "5", "6" };
//		final DefaultTableModel dftm = new DefaultTableModel(columns, 0) {
//			@Override
//			public boolean isCellEditable(int row, int column) {
//				return false;
//			}
//		};
//		final JTable table = new JTable(dftm);
//		table.setDefaultRenderer(Object.class, new DeliveryTableRenderer());
//		HashMap<String, ArrayList<Sale>> salesMap = new HashMap<String, ArrayList<Sale>>();
//		// long allQuantity = 0;
//		// double allPrice = 0;
//		// for (int i = 0; i < invoices.size(); i++) {
//		// Object[] o = invoices.get(i);
//		// for (int j = 0; j < o.length; j++) {
//		// System.out.printf("%s ", o[j]);
//		// }
//		// System.out.printf("\n");
//		// }
//
//		// for (int i = 0; i < delivery.size(); i++) {
//		// Object[] o = delivery.get(i);
//		// for (int j = 0; j < o.length; j++) {
//		// System.out.printf("%s ", o[j]);
//		// }
//		// System.out.printf("\n");
//		// }
//
//		// create map with all sells / invoices
//		for (int i = 0; i < invoices.size(); i++) {
//			String invoice = invoices.get(i)[0].toString();
//			String client = invoices.get(i)[1].toString();
//			String invoiceByKontragent = invoices.get(i)[2].toString();
//			String kontragent = invoices.get(i)[3].toString();
//			String artikul = invoices.get(i)[4].toString();
//			String med = invoices.get(i)[5].toString();
//			int quantity = Integer.parseInt(invoices.get(i)[6].toString());
//			double price = Double.parseDouble(invoices.get(i)[7].toString());
//			String date = invoices.get(i)[8].toString();
//			String key = invoiceByKontragent + "X" + kontragent;
//			if (!salesMap.containsKey(key)) {
//				ArrayList<Sale> value = new ArrayList<Sale>();
//				salesMap.put(key, value);
//			}
//			ArrayList<Sale> sales = salesMap.get(key);
//			sales.add(new Sale(invoice, client, invoiceByKontragent,
//					kontragent, artikul, med, quantity, price, 0, date));
//		}
//		// find delivery artikul corresponding to invoice artikul
//		for (int i = 0; i < delivery.size(); i++) {
//			String deliveryInvoice = delivery.get(i)[0].toString();
//			String deliveryKontragent = delivery.get(i)[1].toString();
//			String deliveryDat = delivery.get(i)[2].toString();
//			String deliveryArtikul = delivery.get(i)[3].toString();
//			String deliveryPrice = delivery.get(i)[4].toString();
//
//			String deliveryKey = deliveryInvoice + "X" + deliveryKontragent;
//			if (salesMap.containsKey(deliveryKey)) {
//				ArrayList<Sale> sales = salesMap.get(deliveryKey);
//				for (Sale s : sales) {
//					if (s.getArtikul().equals(deliveryArtikul)) {
//						Date deliveryDate = GetDate
//								.getDateFromString(deliveryDat);
//						Date saleDate = GetDate.getDateFromString(s.getDate());
//						int cmpDate = deliveryDate.compareTo(saleDate);
//						if (cmpDate <= 0) {// ако има доставени преди продажбата
//							s.setDeliveryPrice(Double.parseDouble(deliveryPrice
//									.replace(",", ".")));
//						}
//					}
//				}
//			}
//		}
//		double allValues = 0;
//		// convert first map to second map
//		TreeMap<String, ArrayList<Sale>> foundSales = new TreeMap<String, ArrayList<Sale>>();
//		for (Map.Entry<String, ArrayList<Sale>> map : salesMap.entrySet()) {
//			ArrayList<Sale> sales = map.getValue();
//			for (int i = 0; i < sales.size(); i++) {
//				Sale sale = sales.get(i);
//				String saleKey = sale.getInvoice() + "X" + sale.getKontragent();
//				if (!foundSales.containsKey(saleKey)) {
//					ArrayList<Sale> values = new ArrayList<Sale>();
//					values.add(sale);
//					foundSales.put(saleKey, values);
//				} else {
//					ArrayList<Sale> values = foundSales.get(saleKey);
//					values.add(sale);
//				}
//			}
//		}
//
//		// populate table model with data
//		for (Map.Entry<String, ArrayList<Sale>> map : foundSales.entrySet()) {
//			ArrayList<Sale> sales = map.getValue();
//			if (sales != null && sales.size() > 0) {
//				dftm.addRow(new Object[] {
//						"Фактура No:  " + sales.get(0).getInvoice(),
//						"Дата:  " + sales.get(0).getDate(),
//						"Контрагент:  " + sales.get(0).getClient(), "", "", "" });
//				for (int j = 0; j < sales.size(); j++) {
//
//					int quantity = sales.get(j).getQuantity();
//					double sellPrice = sales.get(j).getSellPrice();
//					double deliveryPrice = sales.get(j).getDeliveryPrice();
//					double remaining = MyMath.round(quantity
//							* (sellPrice - deliveryPrice), 2);
//
//					dftm.addRow(new Object[] {
//							"Артикул:  " + sales.get(j).getArtikul(),
//							"Мярка:  " + sales.get(j).getMed(),
//							"К-во :  " + quantity, "Прод. цена:  " + sellPrice,
//							"Дост. цена " + deliveryPrice,
//							"Разлика:  " + remaining });
//
//					// allQuantity += quantity;
//					// allPrice += price;
//					allValues += remaining;
//				}
//
//			}
//
//		}
//
//		BigDecimal bigDecimal = BigDecimal.valueOf(allValues);
//		dftm.addRow(new Object[] { "", "", "", "", "",
//				"Всичко:  " + bigDecimal.setScale(2, RoundingMode.HALF_UP) });
//		setColumnSize(table);
//		table.setRowHeight(MainPanel.getFontSize() + 15);
//
//		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//		JScrollPane scrollPane = new JScrollPane(table,
//				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
//				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//		// scrollPane.getVerticalScrollBar().setUI(new YourUI());
//		// scrollPane.getHorizontalScrollBar().setUI(new YourUI());
//		scrollPane.setPreferredSize(new Dimension(WIDTH - 50, HEIGHT - 70));
//
//		TooltipButton excellButton = new TooltipButton();
//		excellButton.setPreferredSize(new Dimension(50, 50));
//		excellButton.setToolTipText(getHTML_Text("ЗАПИШИ В EXCEL"));
//		excellButton.setAutoSizedIcon(excellButton, setIcons(excellImage));
//		excellButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				// TODO Auto-generated method stub
//				JDialog jd = (JDialog) SwingUtilities
//						.getWindowAncestor(ReportTableSales.this);
//				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
//				ExportToExcellWorkerSales excellWorker = new ExportToExcellWorkerSales(
//						table, dftm, title + ".xls", jd);
//				excellWorker.execute();
//			}
//
//		});
//		northPanel.add(titleLabel);
//		northPanel.add(excellButton);
//		childContainer.add(northPanel, BorderLayout.NORTH);
//		childContainer.add(scrollPane, BorderLayout.CENTER);
//
//		this.add(childContainer);
//	}
//
//	private void setColumnSize(JTable table) {
//		table.getColumnModel().getColumn(0).setPreferredWidth(400);
//		table.getColumnModel().getColumn(1).setPreferredWidth(200);
//		table.getColumnModel().getColumn(2).setPreferredWidth(400);
//		table.getColumnModel().getColumn(3).setPreferredWidth(200);
//		table.getColumnModel().getColumn(4).setPreferredWidth(200);
//		table.getColumnModel().getColumn(5).setPreferredWidth(200);
//	}
//
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		SwingUtilities.invokeLater(new Runnable() {
//
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				ReportTableSales r = new ReportTableSales(null, null,
//						"Справка Доставки");
//				JustFrame f = new JustFrame(r);
//				f.setTitle("Справки");
//				f.pack();
//			}
//
//		});
//	}
//
//	private class Sale {
//		private String invoice;
//		private String client;
//		private String invoiceByKontragent;
//		private String kontragent;
//		private String artikul;
//		private String med;
//		private int quantity;
//		private double sellPrice;
//		private double deliveryPrice;
//		private String date;
//
//		public Sale(String invoice, String client, String invoiceByKontragent,
//				String kontragent, String artikul, String med, int quantity,
//				double sellPrice, double deliveryPrice, String date) {
//			super();
//			this.invoice = invoice;
//			this.client = client;
//			this.invoiceByKontragent = invoiceByKontragent;
//			this.kontragent = kontragent;
//			this.artikul = artikul;
//			this.med = med;
//			this.quantity = quantity;
//			this.sellPrice = sellPrice;
//			this.deliveryPrice = deliveryPrice;
//			this.date = date;
//		}
//
//		public double getDeliveryPrice() {
//			return deliveryPrice;
//		}
//
//		public void setDeliveryPrice(double deliveryPrice) {
//			this.deliveryPrice = deliveryPrice;
//		}
//
//		public String getInvoice() {
//			return invoice;
//		}
//
//		public String getClient() {
//			return client;
//		}
//
//		public String getInvoiceByKontragent() {
//			return invoiceByKontragent;
//		}
//
//		public String getKontragent() {
//			return kontragent;
//		}
//
//		public String getArtikul() {
//			return artikul;
//		}
//
//		public String getMed() {
//			return med;
//		}
//
//		public int getQuantity() {
//			return quantity;
//		}
//
//		public double getSellPrice() {
//			return sellPrice;
//		}
//
//		public String getDate() {
//			return date;
//		}
//
//	}
//
//}
