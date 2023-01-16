package db;

import Exceptions.DBException;
import Log.DB_Err;
import net.GetCurrentIP;
import utility.MainPanel;

import java.sql.*;

public class RemoveTable extends MainPanel {

	public RemoveTable() {
		super();
	}

	public static void deleteTable(String tableName) {
		Connection connect = null;
		Statement stat = null;
		String delete = "delete from " + tableName;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			stat.execute(delete);
			System.out.println("table deleted succesfully!");
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
				e.printStackTrace();
			}
		}

	}

	private int deleteArtikulFrom(String destination, String numOfDocument) {
		Connection connect = null;
		Statement stat = null;
		int update = 0;
		String sqlCommand = "delete from " + destination + " where barcod = '"
				+ numOfDocument + "'";
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			update = stat.executeUpdate(sqlCommand);
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
				e.printStackTrace();
			}
		}
		return update;
	}

	public static int deleteClient(String client, String destination,
			String name) {
		Connection connect = null;
		PreparedStatement stat = null;
		int delete = 0;
		String command = "delete from " + destination + " where " + name
				+ " = ?";
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.prepareStatement(command);
			stat.setString(1, client);
			delete = stat.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка ", e);
			DB_Err.writeErros(e.toString());
			Log.DB_Err.writeErros(e.toString());
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
				DBException.DBExceptions("Грешка ", e);
				DB_Err.writeErros(e.toString());
				Log.DB_Err.writeErros(e.toString());
				e.printStackTrace();
			}
		}
		return delete;
	}

	private int deleteDocument(String destination, String numOfDocument) {
		Connection connect = null;
		Statement stat = null;
		int update = 0;
		String sqlCommand = "delete from " + destination + " where number = '"
				+ numOfDocument + "'";
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			update = stat.executeUpdate(sqlCommand);
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
				e.printStackTrace();
			}
		}
		return update;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RemoveTable r = new RemoveTable();
		r.deleteDocument(MainPanel.PROTOKOL,"0016979");
		// r.deleteTable(PARTS_PRICE);
		// r.deleteTable(PROTOKOL_NUMBER);
		// r.deleteTable(FIRM);
		// r.deleteTable(PERSON);

		// r.deleteTable(PROTOKOL);
		// r.deleteTable(SERVICE);
		// r.deleteTable(BRACK);
		// r.deleteTable(INVOICE_NUMBER);
		// r.deleteTable(PROFORM_NUMBER);
		// r.deleteTable(PROFORM_CHILD);
		// r.deleteTable(PROFORM_PARENT);
		// r.deleteTable(INVOICE_CHILD);
		// r.deleteTable(INVOICE_PARENT);
		// r.deleteTable(ACQUITTANCE_CHILD);
		// r.deleteTable(ACQUITTANCE_PARENT);
		// r.deleteTable(ARTIKULS);
		// r.deleteTable(NEW_EXTINGUISHERS);
		// r.deleteTable(SERIAL_TABLE);
		// r.deleteTable(MainPanel.PARTS_QUANTITY);
		// r.deleteTable(SALES);
		// r.deleteTable(NEW_EXTINGUISHERS);
		// int rw = r.deleteDocument(PROTOKOL, "0010636");

		// System.out.println(rw);
	}

}
