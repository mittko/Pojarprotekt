package db.Client;

import Exceptions.DBException;
import Log.DB_Err;
import db.modify.AddColumn;
import net.GetCurrentIP;
import utility.MainPanel;

import java.sql.*;
import java.util.ArrayList;

public class FirmTable extends MainPanel {

	/*
	 * static Connection connect = null; static Statement statement = null;
	 */

	private void createFirmTable2() {
		Connection connect = null;
		Statement stat = null;
		String sql = null;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			sql = "create table "
					+ FIRM
					+ " (firm varchar(100),city varchar (50),address varchar(100),"
					+ "eik varchar(50),mol varchar(50),email varchar(30),"
					+ "person varchar(50),telPerson varchar(30),bank varchar(30),bic varchar(30),"
					+ "iban varchar(30),discount varchar(10),primary key (firm))";
			stat.execute(sql);
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

	public int updateAnyColumnValue(String column, String value) {
		Connection connect = null;
		Statement stat = null;
		String command = String.format("update %s set %s = '%s'",
				MainPanel.FIRM, column, value);
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
			}
		}
	}

	public static int insertIntoFirmTable(String firm, String city,
			String address, String eik, String mol, String email,
			String person, String telPerson, String bank, String bic,
			String iban, String discount, String incorrectPerson, String vatRegistration) {
		Connection connect = null;
		Statement statement = null;
		String sql = null;
		int insertion = 0;
		if (email.isEmpty()) {
			email = "";
		}
		if (person.isEmpty()) {
			person = "";
		}
		if (telPerson.isEmpty()) {
			telPerson = "";
		}
		if (bank.isEmpty()) {
			bank = "";
		}
		if (bic.isEmpty()) {
			bic = "";
		}
		if (iban.isEmpty()) {
			iban = "";
		}
		if (discount.isEmpty()) {
			discount = "0";
		}
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			statement = connect.createStatement();
			sql = "insert into " + FIRM + " values (" + "'" + firm + "','"
					+ city + "','" + address + "','" + eik + "','" + mol
					+ "','" + email + "','" + person + "','" + telPerson
					+ "','" + bank + "','" + bic + "','" + iban + "','"
					+ discount + "','" + incorrectPerson + "','"
					+ vatRegistration + "')";
			insertion = statement.executeUpdate(sql);
			// JOptionPane.showMessageDialog(null,
			// "Данните са записани успешно!");
			return insertion;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return insertion;
			// e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connect != null) {
					connect.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
				return insertion;
			}

		}

	}

	public static int editFirmTable(String OLD_FIRM_NAME, String NEW_FIRM_NAME,
			String city, String address, String eik, String mol, String email,
			String person, String telPerson, String bank, String bic,
			String iban, String discount, String incorrectPerson, String vatRegistration) {
		Connection connect = null;
		Statement statement = null;
		String sql = null;
		int insertion = 0;
		if (email.isEmpty()) {
			email = "";
		}
		if (person.isEmpty()) {
			person = "";
		}
		if (telPerson.isEmpty()) {
			telPerson = "";
		}
		if (bank.isEmpty()) {
			bank = "";
		}
		if (bic.isEmpty()) {
			bic = "";
		}
		if (iban.isEmpty()) {
			iban = "";
		}
		if (discount.isEmpty()) {
			discount = "0";
		}
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			statement = connect.createStatement();
			sql = "update " + FIRM + " set firm = '" + NEW_FIRM_NAME
					+ "' ,city = '" + city + "',address = '" + address
					+ "',eik = '" + eik + "',mol = '" + mol + "',email = '"
					+ email + "',person = '" + person + "',telPerson = '"
					+ telPerson + "',bank = '" + bank + "',bic = '" + bic
					+ "',iban = '" + iban + "',discount = '" + discount
					+ "', incorrectPerson = '" + incorrectPerson
					+ "', vat_registration = '" + vatRegistration
					+ "'  where firm like '" + OLD_FIRM_NAME + "'";
			insertion = statement.executeUpdate(sql);

			return insertion;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return insertion;
			// e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connect != null) {
					connect.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
			}

		}

	}

	public static int updateFirmNameInTable(String table, String oldName,
			String newName) {
		Connection connect = null;
		Statement stat = null;
		String command = "";
		int update = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			command = "update " + table + " set client = '" + newName
					+ "' where client like '" + oldName + "'";
			stat.executeUpdate(command);
			update = stat.getUpdateCount();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Log.DB_Err.writeErros(e.toString());
			DBException.DBExceptions("Грешка", e);
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
				DBException.DBExceptions("Грешка", e);
				e.printStackTrace();
			}

		}

		return update;
	}

	public static ArrayList<String> getFirmInfo(String firm) {
		Connection connect = null;
		Statement stat = null;
		String query = "select city, telPerson from " + MainPanel.FIRM
				+ " where firm = '" + firm + "'";
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		ArrayList<String> result = new ArrayList<String>();
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(query);
			while (rs.next()) {
				result.add(rs.getString(1));
				result.add(rs.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Log.DB_Err.writeErros(e.toString());
			DBException.DBExceptions("Грешка", e);
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
				DBException.DBExceptions("Грешка", e);
				e.printStackTrace();
			}

		}

		return result;
	}

	public static String getHasFirmVatRegistration(String firm) {
		Connection connect = null;
		Statement stat = null;
		String query = "select vat_registration from " + MainPanel.FIRM
				+ " where firm = '" + firm + "'";
		ResultSet rs = null;
		String registrationVat = "не";
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery("select * from " + MainPanel.FIRM);
			rs = stat.executeQuery(query);
			while (rs.next()) {
				registrationVat = rs.getString(1);
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("DB Error", e);
			Log.DB_Err.writeErros(e.toString());
			e.printStackTrace();

		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stat != null) {
					stat.close();
				}
				if (connect != null) {
					connect.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				DBException.DBExceptions("DB Error", e);
				Log.DB_Err.writeErros(e.toString());
				e.printStackTrace();

			}
		}
		return registrationVat;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FirmTable ft = new FirmTable();
		// ft.createFirmTable2();
		AddColumn addColumn = new AddColumn();
		addColumn.addColumn(FIRM, "incorrectPerson", 2);
		// Conecting.testInfo("FirmTable");
	}

}
