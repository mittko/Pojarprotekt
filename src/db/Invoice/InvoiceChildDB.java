package db.Invoice;

import Exceptions.DBException;
import net.GetCurrentIP;
import utility.MainPanel;

import java.sql.*;

public class InvoiceChildDB extends MainPanel {

	private void createInvoiceChildDB() {
		Connection connect = null;
		Statement stat = null;
		String command = "create table " + INVOICE_CHILD
				+ " (id varchar(20),make varchar(200),"
				+ "med varchar(20),quantity varchar(50),price varchar(20),"
				+ "value varchar(20),client varchar(100),foreign key (id)"
				+ " references " + INVOICE_PARENT + " (id))";
		// да се добавят колони kontragent, invoiceByKontragent
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			stat.execute(command);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
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
				DBException.DBExceptions("Грешка", e);
				Log.DB_Err.writeErros(e.toString());
				e.printStackTrace();
			}
		}
	}

	public static int insertIntoInvoiceChild(String dbTable,String id, String make,
			String med, String quantity, String price, String value,
			String client, String kontragent, String invoiceByKontragent) {
		Connection connect = null;
		Statement stat = null;
		PreparedStatement ps = null;
		/*
		 * String command2 = "insert into " + INVOICE_CHILD + " values ('" + id
		 * + "','" + make + "','" + med + "','" + quantity + "','" + price +
		 * "','" + value + "','" + client + "')";
		 */
		String command = "insert into " + dbTable
				+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		int insert = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			ps = connect.prepareStatement(command);
			ps.setString(1, id + "");
			ps.setString(2, make);
			ps.setString(3, med);
			ps.setString(4, quantity);
			ps.setString(5, price);
			ps.setString(6, value);
			ps.setString(7, client);
			ps.setString(8, kontragent);
			ps.setString(9, invoiceByKontragent);
			// stat = connect.createStatement();
			// insert = stat.executeUpdate(command2);
			insert = ps.executeUpdate();// stat.executeUpdate(command);
			return insert;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			Log.DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return insert;
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connect != null) {
					connect.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				DBException.DBExceptions("Грешка", e);
				Log.DB_Err.writeErros(e.toString());
				e.printStackTrace();
				return insert;
			}
		}
	}

	public static int updateAnyColumnValue(String column, String value) {
		Connection connect = null;
		Statement stat = null;
		String command = String.format("update %s set %s = '%s'",
				MainPanel.INVOICE_CHILD, column, value);
		int update = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			update = stat.executeUpdate(command);
			return update;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			Log.DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return update;
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
				DBException.DBExceptions("Грешка", e);
				Log.DB_Err.writeErros(e.toString());
				e.printStackTrace();
				return update;
			}
		}
	}

	public static int modifyColumnWidth(String column, int width) {
		Connection connect = null;
		Statement stat = null;
		String modifyString = String.format(
				"alter table %s alter column %s set data type varchar(%d)",
				MainPanel.INVOICE_CHILD, column, width);
		// origin "alter table " + ARTIKULS +
		// " alter column artikul set data type varchar(" + width + ")";
		int modify = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			modify = stat.executeUpdate(modifyString);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
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
				e.printStackTrace();
			}
		}
		return modify;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InvoiceChildDB child = new InvoiceChildDB();
		// child.modifyColumnWidth("kontragent", 50);
		// child.updateAnyColumnValue("kontragent", "ПОЖАРПРОТЕКТ ООД");
		// child.updateAnyColumnValue("invoiceByKontragent", "0000001");
	}

}
