package admin.services;

import db.Artikul.Artikuli_DB;
import utility.ArtikulsListComboBox;
import utility.EditableField;

import javax.swing.*;
import java.awt.*;

public class InsertServicesWorker extends SwingWorker {
	private String artikul = null;
	private int quantity = 0;
	private String med = null;
	private String saleValue = null;
	private String invoiceNumber = null;
	private String client = null;
	private String date;
	private String seller = null;
	private String percentProfit = null;

	private int insertIntoAvailableArtikuls = 0;

	JTextField kontragentField = null;
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
	public InsertServicesWorker(String dbTable, JTextField kontragentField,
                                ArtikulsListComboBox artikulsComboBox, JTextField skladField,
                                JTextField medField, JTextField previousValueField,
                                JTextField deliveryValueField, JTextField bigFinalValueField,
                                EditableField invoiceField, JTextField dateField,
                                JTextField personField, JTextField percentProfitField, JDialog jd) {
		super();

		this.dbTable = dbTable;
		this.kontragentField = kontragentField;
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
		this.saleValue = this.bigFinalValueField.getText();
		this.invoiceNumber = this.invoiceField.getText();
		this.client = kontragentField.getText();
		this.date = this.dateField.getText();
		this.seller = this.personField.getText();
		this.percentProfit = "0";//this.percentProfitField.getText();
		this.jd = jd;
	}

	public InsertServicesWorker(String artikul, int quantity, String med,
                                String deliveryValue, String saleValue, String invoiceNumber,
                                String client, String date, String seller, String percentProfit,
                                JDialog jd) {
		this.artikul = artikul;
		this.quantity = quantity;
		this.med = med;
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

			insertIntoAvailableArtikuls = Artikuli_DB
					.insertIntoArtikulTable(dbTable,artikul,
							quantity, // -> this is int
							med, saleValue, invoiceNumber, client, date,
							seller, percentProfit);

		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (insertIntoAvailableArtikuls > 0) {
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
    //		this.previousValueField.setText("");
	//	this.deliveryValueField.setText("");
		this.bigFinalValueField.setText("");
	//	this.invoiceField.setText("");
	//	this.dateField.setText("");
	//	this.personField.setText("");
	//	this.percentProfitField.setText("");
	}
}