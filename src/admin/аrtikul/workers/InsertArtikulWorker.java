

// 
// Decompiled by Procyon v0.5.36
// 

package admin.аrtikul.workers;

import javax.swing.SwingUtilities;
import java.awt.Cursor;
import javax.swing.JOptionPane;

import db.аrtikul.Artikuli_DB;
import javax.swing.JDialog;
import utils.EditableField;
import javax.swing.JTextField;
import utils.ArtikulsListComboBox;
import utils.ClientsListComboBox2;
import javax.swing.SwingWorker;

public class InsertArtikulWorker extends SwingWorker
{
	private String artikul;
	private int quantity;
	private String med;
	private String deliveryValue;
	private String saleValue;
	private String invoiceNumber;
	private String client;
	private String date;
	private String seller;
	private String percentProfit;
	private int insertIntoArtikuls;
	private String barcode;
	private int insertIntoDeliveryArtikuls;
	ClientsListComboBox2 clientsComboBox;
	ArtikulsListComboBox artikulsComboBox;
	JTextField skladField;
	JTextField medField;
	JTextField deliveryValueField;
	JTextField bigFinalValueField;
	EditableField invoiceField;
	JTextField dateField;
	JTextField personField;
	JTextField percentProfitField;
	JDialog jd;
	String dbTable;

	public InsertArtikulWorker(final String dbTable, final ClientsListComboBox2 clientsComboBox,
							   final ArtikulsListComboBox artikulsComboBox, final JTextField skladField,
							   final JTextField medField, final JTextField deliveryValueField,
							   final JTextField bigFinalValueField, final EditableField invoiceField,
							   final JTextField dateField, final JTextField personField,
							   final JTextField percentProfitField,
							   final EditableField barcodeField,
							   final JDialog jd) {
		this.artikul = null;
		this.quantity = 0;
		this.med = null;
		this.saleValue = null;
		this.invoiceNumber = null;
		this.client = null;
		this.seller = null;
		this.percentProfit = null;
		this.insertIntoArtikuls = 0;
		this.insertIntoDeliveryArtikuls = 0;
		this.dbTable = dbTable;
		this.clientsComboBox = clientsComboBox;
		this.artikulsComboBox = artikulsComboBox;
		this.skladField = skladField;
		this.medField = medField;
		this.deliveryValueField = deliveryValueField;
		this.bigFinalValueField = bigFinalValueField;
		this.invoiceField = invoiceField;
		this.dateField = dateField;
		this.personField = personField;
		this.percentProfitField = percentProfitField;
		this.jd = jd;
		this.artikul = artikulsComboBox.getEditor().getItem().toString();
		this.quantity = Integer.parseInt(skladField.getText());
		this.med = medField.getText();
		this.deliveryValue = this.deliveryValueField.getText();
		this.saleValue = this.bigFinalValueField.getText();
		this.invoiceNumber = this.invoiceField.getText();
		this.client = this.clientsComboBox.getEditor().getItem().toString();
		this.date = this.dateField.getText();
		this.seller = this.personField.getText();
		this.percentProfit = this.percentProfitField.getText();
		this.barcode = barcodeField.getText();
		this.jd = jd;
	}
	public InsertArtikulWorker(final String dbTable,final String client,
							   final String artikul, String sklad,
							   final String med, final String deliveryValue,
							   final String biggestValue, final String invoice,
							   final String date, final String person,
							   final String percentProfit,
							   final String barcode,
							   final JDialog jd) {


		this.dbTable = dbTable;
		this.artikul = artikul;
		this.quantity = Integer.parseInt(sklad);
		this.med = med;
		this.deliveryValue = deliveryValue;
		this.saleValue = biggestValue;
		this.invoiceNumber = invoice;
		this.client = client;
		this.date = date;
		this.seller = person;
		this.percentProfit = percentProfit;
		this.barcode = barcode;
		this.jd = jd;
	}


	@Override
	protected Object doInBackground() throws Exception {
		try {
			this.insertIntoArtikuls = Artikuli_DB.insertIntoArtikulTable(this.dbTable, this.artikul, this.quantity, this.med, this.saleValue,
					this.invoiceNumber, this.client, this.date, this.seller, this.percentProfit,this.barcode);
			if (this.dbTable.equals("ArtikulsDB")) {
				this.insertIntoDeliveryArtikuls = Artikuli_DB.insertIntoDeliveryArtikulTable(this.artikul, this.quantity, this.med, this.deliveryValue, this.client, this.invoiceNumber, this.date, this.seller);
			}
		}
		finally {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					if (InsertArtikulWorker.this.insertIntoArtikuls > 0) {
						JOptionPane.showMessageDialog(null,
								"Данните са записани успешно !");
						InsertArtikulWorker.this.clear();
					}
					InsertArtikulWorker.this.jd.setCursor(new Cursor(0));
				}
			});
		}
		return null;
	}

	void clear() {
//		this.artikulsComboBox.getEditor().setItem("");
//		this.skladField.setText("");
//		this.deliveryValueField.setText("");
//		this.bigFinalValueField.setText("");
//		this.percentProfitField.setText("");
	}
}

