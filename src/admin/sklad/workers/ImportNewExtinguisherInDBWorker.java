package admin.sklad.workers;

import db.аrtikul.Artikuli_DB;
import db.NewExtinguisher.NewExtinguishers_DB;
import utils.BrandListComboBox;
import utils.ClientsListComboBox2;
import utils.MainPanel;

import javax.swing.*;
import java.awt.*;

public class ImportNewExtinguisherInDBWorker extends SwingWorker {

	private String type = null;
	private String wheight = null;
	private String category = null;
	private String brand = null;
	private int quantity = 0;
	private String finalValue;
	private String deliveryValue;
	private String invoice;
	private String client;
	private String date;
	private String operator;
	private String percentProfit;
	private JDialog jd = null;
	private int insert = 0;

	JComboBox<String> typeCombo;
	JComboBox<String> wheightCombo;
	JComboBox<String> categoryCombo;
	BrandListComboBox brandCombo;
	JTextField quantityField;
	JTextField finalValueField;
	JTextField deliveryValueField;
	JTextField previousValueField;
	JTextField invoiceField;
	ClientsListComboBox2 kontragentsComboBox;
	JTextField dateField;
	JTextField operatorField;
	JTextField percentProfitField;

	public ImportNewExtinguisherInDBWorker(JComboBox<String> typeCombo,
			JComboBox<String> wheightCombo, JComboBox<String> categoryCombo,
			BrandListComboBox brandCombo, JTextField quantityField,
			JTextField finalValueField, JTextField deliveryValueField,
			JTextField previousValueField, JTextField invoiceField,
			ClientsListComboBox2 kontragentsComboBox, JTextField dateField,
			JTextField operatorField, JTextField percentProfitField, JDialog jd) {
		super();
		this.jd = jd;
		this.typeCombo = typeCombo;
		this.wheightCombo = wheightCombo;
		this.categoryCombo = categoryCombo;
		this.brandCombo = brandCombo;
		this.quantityField = quantityField;
		this.finalValueField = finalValueField;
		this.deliveryValueField = deliveryValueField;
		this.previousValueField = previousValueField;
		this.invoiceField = invoiceField;
		this.kontragentsComboBox = kontragentsComboBox;
		this.dateField = dateField;
		this.operatorField = operatorField;
		this.percentProfitField = percentProfitField;

		this.type = typeCombo.getSelectedItem().toString();
		this.wheight = wheightCombo.getSelectedItem().toString();
		this.category = categoryCombo.getSelectedItem().toString();
		this.brand = brandCombo.getSelectedItem().toString();
		this.quantity = Integer.parseInt(quantityField.getText());
		this.finalValue = finalValueField.getText();
		this.deliveryValue = deliveryValueField.getText();
		this.invoice = invoiceField.getText();
		this.client = kontragentsComboBox.getSelectedItem().toString();
		this.date = dateField.getText();
		this.operator = operatorField.getText();
		this.percentProfit = percentProfitField.getText();

	}

	public ImportNewExtinguisherInDBWorker(String type, String wheight,
			String category, String brand, int quantity, String finalValue,
			String deliveryValue, String invoice, String client, String date,
			String operator, String percentProfit, JDialog jDialog) {
		this.type = type;
		this.wheight = wheight;
		this.category = category;
		this.brand = brand;
		this.quantity = quantity;
		this.finalValue = finalValue;
		this.deliveryValue = deliveryValue;
		this.invoice = invoice;
		this.client = client;
		this.date = date;
		this.operator = operator;
		this.percentProfit = percentProfit;
		this.jd = jDialog;
	}

	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub

		try {

			insert = NewExtinguishers_DB.setExtinguisher(
					MainPanel.NEW_EXTINGUISHERS, type, wheight, category,
					brand, quantity, finalValue, invoice, client, date,
					operator, percentProfit);

			// записваме го и в доставки за да можем да направим справка след
			// това
			int insertIntoDeliveryArtikuls = Artikuli_DB
					.insertIntoDeliveryArtikulTable(
							type + " ( Нов ) " + wheight,
							quantity, // ->
										// this
										// is
										// int
							"броя", deliveryValue, client, invoice, date,
							operator);

		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (insert > 0) {
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

	private void clear() {
		this.typeCombo.getEditor().setItem("");
		this.wheightCombo.getEditor().setItem("");
		this.categoryCombo.getEditor().setItem("");
		this.brandCombo.getEditor().setItem("");
		this.quantityField.setText("");
		this.finalValueField.setText("");
		this.deliveryValueField.setText("");
		this.previousValueField.setText("");
	//	this.invoiceField.setText("");
	//	this.dateField.setText("");
	//	this.operatorField.setText("");
		this.percentProfitField.setText("");
	}
}
