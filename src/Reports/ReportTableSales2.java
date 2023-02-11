package Reports;

import Reports.ReportsRenderers.DeliveryTableRenderer;
import Reports.ReportsWorkers.ExportToExcellWorkerSales;
import run.JustFrame;
import utils.LoadIcon;
import utils.MainPanel;
import utils.MyMath;
import utils.TooltipButton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReportTableSales2 extends MainPanel {
	private final DecimalFormat format = new DecimalFormat("0.#");
	public ReportTableSales2(ArrayList<Object[]> invoices,
                             ArrayList<Object[]> delivery, final String title) {
		JPanel childContainer = new JPanel();
		JPanel northPanel = new JPanel();
		childContainer.setLayout(new BorderLayout());

		JLabel titleLabel = new JLabel(title);
		String[] columns = new String[] { "1", "2", "3", "4", "5", "6" };
		final DefaultTableModel dftm = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		final JTable table = new JTable(dftm);
		table.setDefaultRenderer(Object.class, new DeliveryTableRenderer());

		// just create delivery
		HashMap<String,ArrayList<ArtikulDelivery>> deliveryMap = new HashMap<>();
		for (Object[] objects : delivery) {
			String deliveryInvoice = objects[0].toString();
			String deliveryKontragent = objects[1].toString();
			String deliveryDat = objects[2].toString();
			String deliveryArtikul = objects[3].toString();
			String deliveryPrice = objects[4].toString();

			String deliveryKey = deliveryInvoice + "X" + deliveryKontragent;

			if (!deliveryMap.containsKey(deliveryKey)) {
				ArrayList<ArtikulDelivery> value = new ArrayList<>();
				deliveryMap.put(deliveryKey, value);
			}
			ArrayList<ArtikulDelivery> deliveries = deliveryMap.get(deliveryKey);
			deliveries.add(new ArtikulDelivery(deliveryInvoice, deliveryKontragent, deliveryDat,
					deliveryArtikul, deliveryPrice));
		}

		HashMap<String, ArrayList<ArtikulSale>> salesMap = new HashMap<>();

		// create map with all sells / invoices
		for (Object[] objects : invoices) {
			String invoice = objects[0].toString();
			String client = objects[1].toString();
			String invoiceByKontragent = objects[2].toString();
			String kontragent = objects[3].toString();
			String artikul = objects[4].toString();
			String med = objects[5].toString();
			double quantity = Double.parseDouble(objects[6].toString());
			double price = Double.parseDouble(objects[7].toString());
			String date = objects[8].toString();

			String deliveryKey = invoiceByKontragent + "X" + kontragent;

			if(deliveryMap.containsKey(deliveryKey)) {

				ArrayList<ArtikulDelivery> deliveries =
						deliveryMap.get(deliveryKey);
				for(ArtikulDelivery artikulDelivery : deliveries) {

					if(artikulDelivery.getArtikul().equals(artikul)) {
//						if(artikulDelivery.getArtikul().equals("изработване на схема за евакуация А3")) {
//							System.out.println(deliveryKey);
//						}
						String saleKey = invoice + "X" + client;

						if (!salesMap.containsKey(saleKey)) {
							ArrayList<ArtikulSale> value = new ArrayList<>();
							salesMap.put(saleKey, value);
						}
						ArrayList<ArtikulSale> sales = salesMap.get(saleKey);
						sales.add(new ArtikulSale(invoice, client, invoiceByKontragent,
								kontragent, artikul, med, quantity, price,
								Double.parseDouble(
										artikulDelivery.getPrice()
												.replace(",", ".")), date));
						break;
					}
				}

			}
		}

	//	for(Map.Entry<String, ArrayList<ArtikulSale>> m : salesMap.entrySet()) {
		//	System.out.println("key -> " + m.getKey());
	//		for(ArtikulSale artikulSale : m.getValue()) {
		//		System.out.println(artikulSale.artikul);
	//		}
	//	}
		ArrayList<Sale> sales  = new ArrayList<>();
		for(Map.Entry<String, ArrayList<ArtikulSale>> m : salesMap.entrySet()) {
			sales.add(new Sale(m.getKey(), m.getValue()));
		}
		Collections.sort(sales);

		double allValues = 0;

		// populate table model with data
		for (Sale sale  : sales) {
			ArrayList<ArtikulSale> artikulSales = sale.getArtikulSales();
		//	System.out.println("key-> " + sale.invoice );
		//	for(ArtikulSale artikulSale : artikulSales) {
		//		System.out.println(artikulSale.getArtikul() + " " + artikulSale.client + " " + artikulSale.invoice);
		//	}
			if (artikulSales != null && artikulSales.size() > 0) {
				String invoice = artikulSales.get(0).getInvoice();
				dftm.addRow(new Object[] {
						(invoice.length() == 12 ? "Касов бон: " : "Фактура No:  ") + artikulSales.get(0).getInvoice(),
						"Дата:  " + artikulSales.get(0).getDate(),
						"Контрагент:  " + artikulSales.get(0).getClient(), "", "", "" });
				for (ArtikulSale artikulSale : artikulSales) {

					double quantity = artikulSale.getQuantity();
					double sellPrice = artikulSale.getSellPrice();
					double deliveryPrice = artikulSale.getDeliveryPrice();
					double remaining = MyMath.round(quantity
							* (sellPrice - deliveryPrice), 2);

					dftm.addRow(new Object[]{
							"Артикул:  " + artikulSale.getArtikul(),
							"Мярка:  " + artikulSale.getMed(),
							"К-во :  " + format.format(quantity),
							"Прод. цена:  " + sellPrice,
							"Дост. цена " + deliveryPrice,
							"Разлика:  " + remaining});

					// allQuantity += quantity;
					// allPrice += price;
					allValues += remaining;
				}

			}

		}

		BigDecimal bigDecimal = BigDecimal.valueOf(allValues);
		dftm.addRow(new Object[] { "", "", "", "", "",
				"Всичко:  " + bigDecimal.setScale(2, RoundingMode.HALF_UP) });
		setColumnSize(table);
		table.setRowHeight(MainPanel.getFontSize() + 15);

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JScrollPane scrollPane = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
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
						.getWindowAncestor(ReportTableSales2.this);
				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				ExportToExcellWorkerSales excellWorker = new ExportToExcellWorkerSales(
						table, dftm, title + ".xls", jd);
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
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(400);
		table.getColumnModel().getColumn(3).setPreferredWidth(200);
		table.getColumnModel().getColumn(4).setPreferredWidth(200);
		table.getColumnModel().getColumn(5).setPreferredWidth(200);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				ReportTableSales2 r = new ReportTableSales2(null, null,
						"Справка Доставки");
				JustFrame f = new JustFrame(r);
				f.setTitle("Справки");
				f.pack();
			}

		});
	}

	private static class Sale implements Comparable<Sale>{
		private final ArrayList<ArtikulSale> artikulSales;
		private Date sellDate;
		public Sale(String invoice, ArrayList<ArtikulSale> artikulSales) {
			this.artikulSales = artikulSales;
			try {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
				sellDate = simpleDateFormat.parse(artikulSales.get(0).date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		@Override
		public int compareTo(Sale o) {
			return sellDate.compareTo(o.sellDate);
		}

		public ArrayList<ArtikulSale> getArtikulSales() {
			return artikulSales;
		}
	}
	private static class ArtikulSale {
		private final String invoice;
		private final String client;
		private final String invoiceByKontragent;
		private final String kontragent;
		private final String artikul;
		private final String med;
		private final double quantity;
		private final double sellPrice;
		private double deliveryPrice;
		private final String date;

		public ArtikulSale(String invoice, String client, String invoiceByKontragent,
				String kontragent, String artikul, String med, double quantity,
				double sellPrice, double deliveryPrice, String date) {
			super();
			this.invoice = invoice;
			this.client = client;
			this.invoiceByKontragent = invoiceByKontragent;
			this.kontragent = kontragent;
			this.artikul = artikul;
			this.med = med;
			this.quantity = quantity;
			this.sellPrice = sellPrice;
			this.deliveryPrice = deliveryPrice;
			this.date = date;
		}

		public double getDeliveryPrice() {
			return deliveryPrice;
		}

		public void setDeliveryPrice(double deliveryPrice) {
			this.deliveryPrice = deliveryPrice;
		}

		public String getInvoice() {
			return invoice;
		}

		public String getClient() {
			return client;
		}

		public String getInvoiceByKontragent() {
			return invoiceByKontragent;
		}

		public String getKontragent() {
			return kontragent;
		}

		public String getArtikul() {
			return artikul;
		}

		public String getMed() {
			return med;
		}

		public double getQuantity() {
			return quantity;
		}

		public double getSellPrice() {
			return sellPrice;
		}

		public String getDate() {
			return date;
		}

	}

	private static class ArtikulDelivery {
		private final String invoiceByKontragent;
		private final String kontragent;
		private final String date;
		private final String artikul;
		private final String price;

		public ArtikulDelivery(String invoiceByKontragent, String kontragent,
							   String date, String artikul,
							   String price) {
			super();
			this.invoiceByKontragent = invoiceByKontragent;
			this.kontragent = kontragent;
			this.date = date;
			this.artikul = artikul;
			this.price = price;
		}

		public String getInvoiceByKontragent() {
			return invoiceByKontragent;
		}

		public String getKontragent() {
			return kontragent;
		}

		public String getDate() {
			return date;
		}

		public String getArtikul() {
			return artikul;
		}

		public String getPrice() {
			return price;
		}

	}
}
