package db.modify;

import Exceptions.DBException;
import db.Client.FirmTable;
import net.GetCurrentIP;
import utils.MainPanel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AddColumn {

	public AddColumn() {
		// TODO Auto-generated constructor stub
	}

	public int addColumn(String table, String columnName, int varcharSize) {
		Connection connect = null;
		Statement stat = null;
		int result = -1;
		String command = "ALTER TABLE " + table + " ADD COLUMN " + columnName
				+ " VARCHAR(" + varcharSize + ")";
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			result = stat.executeUpdate(command);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (stat != null) {
					stat.close();
				}
				if (connect != null) {
					connect.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				Log.DB_Err.writeErros(e.toString());
				DBException.DBExceptions("√Â¯Í‡", e);
				e.printStackTrace();

			}
		}
		return result;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AddColumn alertTable = new AddColumn();

		alertTable.addColumn(MainPanel.CREDIT_NOTE,"credit_note_date",20);

	//	alertTable.addColumn(MainPanel.FIRM, "vat_registration",10);

	//	 int result = alertTable.addColumn(MainPanel.AVAILABLE_ARTIKULS, "code",20);

	//	 int result2 = InitColumnsTable.updateAnyColumnValue(MainPanel.AVAILABLE_ARTIKULS,"code","1");
		// System.out.println("result = " + result);

		// Member.updateAllColumnValues("Acquittance", "yes");

		// int alert = alertTable.addColumn(MainPanel.AVAILABLE_ARTIKULS,
		// "invoice");

		// int alert2 = alertTable.addColumn(MainPanel.AVAILABLE_ARTIKULS,
		// "client");
		// int modify = Artikuli_DB.modifyColumnWidth("client", 50);
		// System.out.println(modify);
		// int alert3 = alertTable.addColumn(MainPanel.AVAILABLE_ARTIKULS,
		// "date");

		// int alert4 = alertTable.addColumn(MainPanel.AVAILABLE_ARTIKULS,
		// "operator");// (varchar 30) int alert5 =

		// int alert5 = alertTable.addColumn(MainPanel.AVAILABLE_ARTIKULS,
		// "percentProfit");
		// System.out.println(alert5);
		// RemoveTable.deleteTable(MainPanel.AVAILABLE_ARTIKULS);

		// int alert6 =
		// alertTable.addColumn(MainPanel.NEW_EXTINGUISHERS,"invoice");
		// System.out.println(alert6);

		// int alert7 = alertTable .addColumn(MainPanel.NEW_EXTINGUISHERS,
		// "client");// varchar(50)
		// System.out.println(alert7);
		// int alert8 = alertTable.addColumn(MainPanel.NEW_EXTINGUISHERS,
		// "date");
		// System.out.println(alert8);

		// int alert9 =
		// alertTable.addColumn(MainPanel.NEW_EXTINGUISHERS,"operator");
		// System.out.println(alert9);

		// int alert10 = alertTable.addColumn(MainPanel.NEW_EXTINGUISHERS,
		// "percentProfit"); // System.out.println("alert = " + alert);
		// System.out.println(alert10);

		// RemoveTable.deleteTable(MainPanel.NEW_EXTINGUISHERS);

		// int alert11 = alertTable.addColumn(MainPanel.INVOICE_CHILD,
		// "kontragent");
		// System.out.println(alert11);

		// int alert12 = alertTable.addColumn(MainPanel.INVOICE_CHILD,
		// "invoiceByKontragent");
		// System.out.println(alert12);

		// int alert11 = InvoiceChildDB.updateAnyColumnValue("kontragent",
		// "œŒ∆¿–œ–Œ“≈ “ ŒŒƒ");
		// System.out.println(alert11);

		// int alert11 = InvoiceChildDB.updateAnyColumnValue(
		// "invoiceByKontragent", "0000001");
		// System.out.println(alert11);

		// int rename = RenameColumnName.renameColumn(MainPanel.INVOICE_CHILD,
		// "make", "artikul");
		// System.out.println(rename);

		// int alert13 = alertTable.addColumn(MainPanel.PROFORM_CHILD,
		// "kontragent");// (varchar 50)
		// System.out.println(alert13);

		// int alert14 = alertTable.addColumn(MainPanel.PROFORM_CHILD,
		// "invoiceByKontragent");
		// System.out.println(alert14);

		// int alert11 = ProformChildDB.updateAnyColumnValue("kontragent",
		// "œŒ∆¿–œ–Œ“≈ “ ŒŒƒ");
		// System.out.println(alert11);

		// int alert11 = ProformChildDB.updateAnyColumnValue(
		// "invoiceByKontragent", "0000001");
		// System.out.println(alert11);

		// int alert = alertTable.addColumn(MainPanel.PROTOKOL, "kontragent");//
		// varchar(50)
		// System.out.println("alert = " + alert);

		// int alert2 = alertTable.addColumn(MainPanel.PROTOKOL,
		// "invoiceByKontragent");
		// System.out.println("alert2 = " + alert2);

		// int result2 = ProtokolTable.updateAnyColumnValue("kontragent",
		// "œŒ∆¿–œ–Œ“≈ “ ŒŒƒ");
		// System.out.println(result2);

		// int result3 =
		// ProtokolTable.updateAnyColumnValue("invoiceByKontragent", "0000001");
		// System.out.println(result3);

		// Artikuli_DB.createDeliveryArtikulDB();

	}
}
