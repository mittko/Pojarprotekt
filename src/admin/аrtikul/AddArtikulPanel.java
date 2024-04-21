

//
// Decompiled by Procyon v0.5.36
//

package admin.аrtikul;

import Exceptions.ErrorDialog;
import admin.аrtikul.Workers.InsertArtikulWorker;
import db.аrtikul.Artikuli_DB;
import mydate.MyGetDate;
import run.JDialoger;
import utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddArtikulPanel extends AddArtikulDialog
{


	private IEditArtikuls iEditArtikuls;
	private String dbTable;
	public AddArtikulPanel(IEditArtikuls iEditArtikuls,String dbTable, String artikulItem,
						   String skladitem,  String invoiceNumber, String client, String percentProfit) {
		super(dbTable,artikulItem, skladitem,  invoiceNumber, client, percentProfit);
		this.dbTable = dbTable;
		this.iEditArtikuls = iEditArtikuls;
	}

	public static void main(final String[] args) {
		AddArtikulPanel addArtikulDialog = new AddArtikulPanel(null,"","", "","", "",  "") ;
		JDialoger jd = new JDialoger();
		jd.setContentPane(addArtikulDialog);
		jd.setResizable(false);
		jd.setTitle("Добави артикул");
		jd.Show();
	}


	@Override
	public void saveInDB(ClientsListComboBox2 clientComboBox,
						 ArtikulsListComboBox artikulsComboBox,
						 JTextField skladField, JTextField medField,
						 JTextField deliveryValueField,
						 JTextField bigFinalValueField,
						 EditableField invoiceField,
						 JTextField dateField,
						 JTextField personField,JTextField percentProfitField,
						 EditableField barcodeField) {

		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
		simpleDateFormat.setLenient(false);
		Date checkDate = null;
		try {
			checkDate = simpleDateFormat.parse(dateField.getText());
		} catch (ParseException e) {
			ErrorDialog.showErrorMessage( "Грешен формат на дата !");
			return;
		}
		iEditArtikuls.addArtikul(
				clientComboBox.getSelectedItem().toString(),
				artikulsComboBox.getEditor().getItem().toString(),
				skladField.getText(), medField.getText(),
				deliveryValueField.getText(),
				bigFinalValueField.getText(),
				invoiceField.getText(),
				dateField.getText(), personField.getText(),
				percentProfitField.getText(),
				barcodeField.getText());
		;
	}

	@Override
	public ArrayList<String> selectArtikulsByBarcode(String barcode) {
		return Artikuli_DB.selectArtikulByBarcode(String.format("select * from %s where barcode = '%s'",
				this.dbTable,barcode));
	}

}

