package db.NewExtinguisher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Exceptions.JustExceptionDialog;

public class ExtinguishersInfo implements Comparable<ExtinguishersInfo> {

	private String type;
	private String wheight;
	private String category;
	private String brand;
	private String quantity;
	private String invoiceByKontragent;
	private String dateString;
	private String kontragent;
	final private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	private Date date;

	public ExtinguishersInfo(String type, String wheight, String category,
			String brand, String quantity, String invoiceByKontragent,
			String kontragent, String dateString) {
		super();
		this.type = type;
		this.wheight = wheight;
		this.category = category;
		this.brand = brand;
		this.quantity = quantity;
		this.invoiceByKontragent = invoiceByKontragent;
		this.kontragent = kontragent;
		this.dateString = dateString;
		try {
			this.date = sdf.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			JustExceptionDialog.showException(e);
			e.printStackTrace();
		}
	}

	public String getDateString() {
		return dateString;
	}

	@Override
	public int compareTo(ExtinguishersInfo o) {
		// TODO Auto-generated method stub
		return this.date.compareTo(o.date);
	}

	public String getType() {
		return type;
	}

	public String getWheight() {
		return wheight;
	}

	public String getCategory() {
		return category;
	}

	public String getBrand() {
		return brand;
	}

	public int getQuantity() {
		return Integer.parseInt(quantity);
	}

	public String getInvoiceByKontragent() {
		return invoiceByKontragent;
	}

	public String getKontragent() {
		return kontragent;
	}

	public Date getDate() {
		return date;
	}

}
