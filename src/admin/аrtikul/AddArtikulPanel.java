

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


	public AddArtikulPanel(String artikulItem, String skladitem,  String invoiceNumber, String client, String percentProfit) {
		super(AVAILABLE_ARTIKULS,artikulItem, skladitem,  invoiceNumber, client, percentProfit);
	}

	public static void main(final String[] args) {
		AddArtikulPanel addArtikulDialog = new AddArtikulPanel("", "","", "",  "") ;
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
		final JDialog jd = (JDialog)SwingUtilities.getWindowAncestor(AddArtikulPanel.this);
		jd.setCursor(new Cursor(3));
		final InsertArtikulWorker add = new InsertArtikulWorker(AVAILABLE_ARTIKULS,
				clientComboBox, artikulsComboBox,
				skladField, medField,
				deliveryValueField,
				bigFinalValueField,
				invoiceField,
				dateField, personField,percentProfitField,barcodeField, jd);
		add.execute();
	}

	@Override
	public ArrayList<String> selectArtikulsByBarcode(String barcode) {
		return Artikuli_DB.selectArtikulByBarcode(String.format("select * from %s where barcode = '%s'",
				AVAILABLE_ARTIKULS,barcode));
	}

}

