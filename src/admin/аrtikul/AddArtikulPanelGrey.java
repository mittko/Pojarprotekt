

//
// Decompiled by Procyon v0.5.36
//

package admin.‡rtikul;

import admin.‡rtikul.Workers.InsertArtikulWorker;
import db.‡rtikul.Artikuli_DB;
import mydate.MyGetDate;
import utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddArtikulPanelGrey extends AddArtikulDialog
{

	public AddArtikulPanelGrey(String artikulItem, String skladitem, String invoiceNumber, String client, String percentProfit) {
		super(GREY_AVAILABLE_ARTIKULS,artikulItem, skladitem,  invoiceNumber, client, percentProfit);
	}

	public static void main(final String[] args) {
	}

	@Override
	public void saveInDB(ClientsListComboBox2 clientComboBox, ArtikulsListComboBox artikulsComboBox, JTextField skladField, JTextField medField, JTextField deliveryValueField, JTextField bigFinalValueField, EditableField invoiceField, JTextField dateField, JTextField personField, JTextField percentProfitField, EditableField barcodeField) {
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
		simpleDateFormat.setLenient(false);
		Date checkDate = null;
		try {
			checkDate = simpleDateFormat.parse(dateField.getText());
		}
		catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "√Â¯ÂÌ ÙÓÏ‡Ú Ì‡ ‰‡Ú‡ !");
			return;
		}
		final JDialog jd = (JDialog)SwingUtilities.getWindowAncestor(AddArtikulPanelGrey.this);
		jd.setCursor(new Cursor(3));

		final InsertArtikulWorker add = new InsertArtikulWorker(
				GREY_AVAILABLE_ARTIKULS,
				clientComboBox.getSelectedItem().toString(), artikulsComboBox.getSelectedItem().toString(),
				skladField.getText(), medField.getText(),
				deliveryValueField.getText(),
				bigFinalValueField.getText(),
				invoiceField.getText(),
				dateField.getText(), personField.getText(),
				percentProfitField.getText(),
				barcodeField.getText(), jd);
		add.execute();
	}

	@Override
	public ArrayList<String> selectArtikulsByBarcode(String barcode) {
		return Artikuli_DB.selectArtikulByBarcode(String.format("select * from %s where barcode = '%s'",
				GREY_AVAILABLE_ARTIKULS,barcode));
	}


}

