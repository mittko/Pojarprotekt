package admin.Artikul.Workers;

import db.Artikul.Artikuli_DB;
import utility.ArtikulsListComboBox;
import utility.ClientsListComboBox2;
import utility.EditableField;
import utility.MainPanel;

import javax.swing.*;
import java.awt.*;

public class InsertArtikulWorker extends SwingWorker {
	private String artikul = null;
	private int quantity = 0;
	private String med = null;
	private String deliveryValue;
	private String saleValue = null;
	private String invoiceNumber = null;
	private String client = null;
	private String date;
	private String seller = null;
	private String percentProfit = null;

	private int insertIntoArtikuls = 0;
	private int insertIntoDeliveryArtikuls = 0;

	ClientsListComboBox2 clientsComboBox = null;
	ArtikulsListComboBox artikulsComboBox = null;
	JTextField skladField = null;
	JTextField medField = null;
	JTextField previousValueField = null;
	JTextField deliveryValueField = null;
	JTextField bigFinalValueField = null;
	EditableField invoiceField = null;
	JTextField dateField = null;
	JTextField personField = null;
	JTextField percentProfitField = null;
	JDialog jd = null;
    String dbTable;
	public InsertArtikulWorker(String dbTable, ClientsListComboBox2 clientsComboBox,
			ArtikulsListComboBox artikulsComboBox, JTextField skladField,
			JTextField medField, JTextField previousValueField,
			JTextField deliveryValueField, JTextField bigFinalValueField,
			EditableField invoiceField, JTextField dateField,
			JTextField personField, JTextField percentProfitField, JDialog jd) {
		super();

		this.dbTable = dbTable;
		this.clientsComboBox = clientsComboBox;
		this.artikulsComboBox = artikulsComboBox;
		this.skladField = skladField;
		this.medField = medField;
		this.previousValueField = previousValueField;
		this.deliveryValueField = deliveryValueField;
		this.bigFinalValueField = bigFinalValueField;
		this.invoiceField = invoiceField;
		this.dateField = dateField;
		this.personField = personField;
		this.percentProfitField = percentProfitField;
		this.jd = jd;

		this.artikul = this.artikulsComboBox.getEditor().getItem().toString();
		this.quantity = Integer.parseInt(this.skladField.getText());
		this.med = this.medField.getText();
		this.deliveryValue = this.deliveryValueField.getText();
		this.saleValue = this.bigFinalValueField.getText();
		this.invoiceNumber = this.invoiceField.getText();
		this.client = this.clientsComboBox.getEditor().getItem().toString();
		this.date = this.dateField.getText();
		this.seller = this.personField.getText();
		this.percentProfit = this.percentProfitField.getText();
		this.jd = jd;
	}

	public InsertArtikulWorker(String artikul, int quantity, String med,
			String deliveryValue, String saleValue, String invoiceNumber,
			String client, String date, String seller, String percentProfit,
			JDialog jd) {
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
		// TODO Auto-generated method stub
		try {
			System.out.println("артикул с фактура");
			insertIntoArtikuls = Artikuli_DB
					.insertIntoArtikulTable(dbTable,artikul,
							quantity, // -> this is int
							med, saleValue, invoiceNumber, client, date,
							seller, percentProfit);
            if(dbTable.equals(MainPanel.AVAILABLE_ARTIKULS)) {
				insertIntoDeliveryArtikuls = Artikuli_DB
						.insertIntoDeliveryArtikulTable(
								artikul,
								quantity, // ->
								// this
								// is
								// int
								med, deliveryValue, client, invoiceNumber, date,
								seller);
			} else {
				System.out.println("артикул без фактура");
			}

		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (insertIntoArtikuls > 0) {
						JOptionPane.showMessageDialog(null,
								"Данните са записани успешно!");
						clear();
					}
					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}

			});
		}
		return null;
	}

	void clear() {
		this.artikulsComboBox.getEditor().setItem("");
		this.skladField.setText("");
	//	this.medField.setText("");
		this.previousValueField.setText("");
		this.deliveryValueField.setText("");
		this.bigFinalValueField.setText("");
	//	this.invoiceField.setText("");
	//	this.dateField.setText("");
	//	this.personField.setText("");
		this.percentProfitField.setText("");
	}
}