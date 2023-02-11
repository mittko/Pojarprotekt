

// 
// Decompiled by Procyon v0.5.36
// 

package admin.Artikul.Workers;

import javax.swing.SwingUtilities;
import java.awt.Cursor;
import javax.swing.JOptionPane;

import db.Artikul.Artikuli_DB;
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
	JTextField artikulCodeField;
	JDialog jd;
	String dbTable;
	String code;

	public InsertArtikulWorker(final String dbTable, final ClientsListComboBox2 clientsComboBox, final ArtikulsListComboBox artikulsComboBox, final JTextField skladField, final JTextField medField, final JTextField deliveryValueField, final JTextField bigFinalValueField, final EditableField invoiceField, final JTextField dateField, final JTextField personField, final JTextField percentProfitField, final JTextField artikulCodeField, final JDialog jd) {
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
		this.clientsComboBox = null;
		this.artikulsComboBox = null;
		this.skladField = null;
		this.medField = null;
		this.deliveryValueField = null;
		this.bigFinalValueField = null;
		this.invoiceField = null;
		this.dateField = null;
		this.personField = null;
		this.percentProfitField = null;
		this.jd = null;
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
		this.artikulCodeField = artikulCodeField;
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
		this.code = artikulCodeField.getText();
		this.jd = jd;
	}

	public InsertArtikulWorker(final String artikul, final int quantity, final String med, final String deliveryValue, final String saleValue, final String invoiceNumber, final String client, final String date, final String seller, final String percentProfit, final JDialog jd) {
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
		this.clientsComboBox = null;
		this.artikulsComboBox = null;
		this.skladField = null;
		this.medField = null;
		this.deliveryValueField = null;
		this.bigFinalValueField = null;
		this.invoiceField = null;
		this.dateField = null;
		this.personField = null;
		this.percentProfitField = null;
		this.jd = null;
		this.artikul = artikul;
		this.quantity = quantity;
		this.med = med;
		this.deliveryValue = deliveryValue;
		this.saleValue = saleValue;
		this.invoiceNumber = invoiceNumber;
		this.client = client;
		this.date = date;
		this.seller = seller;
		this.percentProfit = percentProfit;
		this.jd = jd;
	}

	@Override
	protected Object doInBackground() throws Exception {
		try {
			this.insertIntoArtikuls = Artikuli_DB.insertIntoArtikulTable(this.dbTable, this.artikul, this.quantity, this.med, this.saleValue, this.invoiceNumber, this.client, this.date, this.seller, this.percentProfit, this.code);
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
								"Error from InsertArtikulWorker!");
						InsertArtikulWorker.this.clear();
					}
					InsertArtikulWorker.this.jd.setCursor(new Cursor(0));
				}
			});
		}
		return null;
	}

	void clear() {
		this.artikulsComboBox.getEditor().setItem("");
		this.skladField.setText("");
		this.deliveryValueField.setText("");
		this.bigFinalValueField.setText("");
		this.percentProfitField.setText("");
		this.artikulCodeField.setText("");
	}
}

