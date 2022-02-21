package Reports;

import Reports.ReportsRenderers.DeliveryTableRenderer;
import Reports.ReportsWorkers.ExportToExcellWorkerDelivery;
import run.JustFrame;
import utility.LoadIcon;
import utility.MainPanel;
import utility.MyMath;
import utility.TooltipButton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReportTableDelivery extends MainPanel {

	public ReportTableDelivery(ArrayList<Object[]> data, final String title) {
		JPanel childContainer = new JPanel();
		JPanel northPanel = new JPanel();
		childContainer.setLayout(new BorderLayout());

		JLabel titleLabel = new JLabel(title);
		String[] columns = new String[] { "1", "2", "3", "4", "5" };
		final DefaultTableModel dftm = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		final JTable table = new JTable(dftm);
		table.setDefaultRenderer(Object.class, new DeliveryTableRenderer());
		// long allQuantity = 0;
		// double allPrice = 0;
		double allValues = 0;
		HashMap<String, ArrayList<ArtikulDelivery>> deliveryMap = new HashMap<>();

		for (Object[] datum : data) {
			String invoice = datum[5].toString();
			String kontragent = datum[4].toString();
			String date = datum[6].toString();
			String artikul = datum[0].toString();
			String med = datum[2].toString();
			String quantity = datum[1].toString();
			String price = datum[3].toString();

			String key = invoice + "X" + kontragent;
			if (!deliveryMap.containsKey(key)) {
				ArrayList<ArtikulDelivery> deliveryList = new ArrayList<>();
				deliveryList.add(new ArtikulDelivery(invoice, kontragent, date,
						artikul, med, quantity, price));
				deliveryMap.put(key, deliveryList);
			} else {
				ArrayList<ArtikulDelivery> deliveryList = deliveryMap.get(key);
				deliveryList.add(new ArtikulDelivery(invoice, kontragent, date,
						artikul, med, quantity, price));
			}
		}
		ArrayList<Delivery> deliveries  = new ArrayList<>();
		for(Map.Entry<String, ArrayList<ArtikulDelivery>> m : deliveryMap.entrySet()) {
			deliveries.add(new Delivery(m.getKey(), m.getValue()));
		}
		Collections.sort(deliveries);

		for (Delivery deliver : deliveries) {
			ArrayList<ArtikulDelivery> values = deliver.artikulsDelivery;
			if (values.size() > 0) {
				ArtikulDelivery delivery0 = values.get(0);
				dftm.addRow(new Object[] {
						"Фактура No:  " + delivery0.getInvoiceByKontragent(),
						"Дата:  " + delivery0.getDate(),
						"Контрагент:  " + delivery0.getKontragent(), "", "" });
				for (ArtikulDelivery delivery : values) {
					double quantity = Double
							.parseDouble(delivery.getQuantity());
					double price = Double.parseDouble(delivery.getPrice()
							.replace(",", "."));

					dftm.addRow(new Object[]{
							"Артикул:  " + delivery.getArtikul(),
							"Мярка:  " + delivery.getMed(),
							"Количество:  " + (int) quantity,
							"Ед. цена:  " + price,
							"Стойност:  " + MyMath.round(quantity * price, 2)});

					// allQuantity += quantity;
					// allPrice += price;
					allValues += (quantity * price);
				}
			}

		}
		BigDecimal bigDecimal = new BigDecimal(allValues);
		dftm.addRow(new Object[] { "", "", "", "",
				"Всичко:  " + bigDecimal.setScale(2, RoundingMode.HALF_UP) });
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
						.getWindowAncestor(ReportTableDelivery.this);
				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				ExportToExcellWorkerDelivery excellWorker = new ExportToExcellWorkerDelivery(
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
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				ReportTableDelivery r = new ReportTableDelivery(null,
						"Справка Доставки");
				JustFrame f = new JustFrame(r);
				f.setTitle("Справки");
				f.pack();
			}

		});
	}

	class Delivery implements Comparable<Delivery> {
		private String invoice;
		private final ArrayList<ArtikulDelivery> artikulsDelivery;
		private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
		private Date deliveryDate;
		public Delivery(String invoice, ArrayList<ArtikulDelivery> artikulsDelivery) {
			this.invoice = invoice;
			this.artikulsDelivery = artikulsDelivery;
			try {
				deliveryDate = simpleDateFormat.parse(artikulsDelivery.get(0).getDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		@Override
		public int compareTo(Delivery o) {
			return deliveryDate.compareTo(o.deliveryDate);
		}

	}
	static class ArtikulDelivery {
		String invoiceByKontragent;
		String kontragent;
		String date;
		String artikul;
		String med;
		String quantity;
		String price;

		public ArtikulDelivery(String invoiceByKontragent, String kontragent,
				String date, String artikul, String med, String quantity,
				String price) {
			super();
			this.invoiceByKontragent = invoiceByKontragent;
			this.kontragent = kontragent;
			this.date = date;
			this.artikul = artikul;
			this.med = med;
			this.quantity = quantity;
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

		public String getMed() {
			return med;
		}

		public String getQuantity() {
			return quantity;
		}

		public String getPrice() {
			return price;
		}

	}
}
