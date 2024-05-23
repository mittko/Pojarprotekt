package db.Invoice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.GetCurrentIP;
import exceptions.DBException;
import log.DB_Err;
import utils.MainPanel;

public class InvoiceNumber extends MainPanel {

	public void createInvoiceNumber() {
		Connection connect = null;
		Statement stat = null;
		String sql = "create table " + INVOICE_NUMBER
				+ " (invoice_number varchar(20))";
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			stat.execute(sql);
			System.out.println("InvoiceNumber table created succesfully!");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.showErrorMessage("Грешка", e);
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

	private int insertInvoiceNumber(String invoice_number) {
		Connection connect = null;
		Statement stat = null;
		String sql = "insert into " + INVOICE_NUMBER + " values (" + "'"
				+ invoice_number + "'" + ")";
		int insert = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			insert = stat.executeUpdate(sql);
			return insert;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.showErrorMessage("Грешка", e);
			DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return insert;
			// e.printStackTrace();
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
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
				return insert;
				// e.printStackTrace();
			}
		}
	}

	public static String getInvoiceNumber() {
		Connection connect = null;
		Statement stat = null;
		String sql = "select invoice_number from " + INVOICE_NUMBER;
		ResultSet rs = null;
		String invoice_number = null;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				invoice_number = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.showErrorMessage("Грешка", e);
			DB_Err.writeErros(e.toString());
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
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
			}
		}
		return invoice_number;
	}

	public static int updateInvoiceNumber(String newNumber) {
		Connection connect = null;
		Statement stat = null;
		String sql = "update " + INVOICE_NUMBER + " set invoice_number = "
				+ "'" + newNumber + "'";
		int insert = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			insert = stat.executeUpdate(sql);
			return insert;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.showErrorMessage("Грешка", e);
			DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return insert;
			// e.printStackTrace();
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
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
				return insert;
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InvoiceNumber invoice = new InvoiceNumber();
		// updateInvoiceNumber("0000011803");

		// String number = getInvoiceNumber();
		// System.out.println(number);
		String proformNumber = getProformNumber();//
		System.out.println(proformNumber);
		// updateProformNumber("0000001009");
		// System.out.println(proformNumber);
	}

	public void createProformNumber() {
		Connection connect = null;
		Statement stat = null;
		String sql = "create table " + PROFORM_NUMBER
				+ " (proform_number varchar(20))";
		try {
			connect = DriverManager.getConnection("jdbc:derby://"
					+ GetCurrentIP.getIP() + ":1527/C:/DB");
			stat = connect.createStatement();
			stat.execute(sql);
			System.out.println("ProformNumber table created succesfully!");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.showErrorMessage("Грешка", e);
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

	private int insertProformNumber(String proform_number) {
		Connection connect = null;
		Statement stat = null;
		String sql = "insert into " + PROFORM_NUMBER + " values (" + "'"
				+ proform_number + "'" + ")";
		int insert = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			insert = stat.executeUpdate(sql);
			return insert;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.showErrorMessage("Грешка", e);
			DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return insert;
			// e.printStackTrace();
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
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
				return insert;
				// e.printStackTrace();
			}
		}
	}

	public static String getProformNumber() {
		Connection connect = null;
		Statement stat = null;
		String sql = "select proform_number from " + PROFORM_NUMBER;
		ResultSet rs = null;
		String proform_number = null;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				proform_number = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.showErrorMessage("Грешка", e);
			DB_Err.writeErros(e.toString());
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
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
			}
		}
		return proform_number;
	}

	public static int updateProformNumber(String newNumber) {
		Connection connect = null;
		Statement stat = null;
		String sql = "update " + PROFORM_NUMBER + " set proform_number = "
				+ "'" + newNumber + "'";
		int update = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			update = stat.executeUpdate(sql);
			return update;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.showErrorMessage("Грешка", e);
			DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return update;
			// e.printStackTrace();
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
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
				return update;
			}
		}
	}

	/*
	 * 2
	 * 
	 * 
	 * Don't update the primary key. It could cause a lot of problems for you
	 * keeping your data intact, if you have any other tables referencing it.
	 * public static int repairProformNumber(String wrongNumber, String
	 * rightNumber) { Connection connect = null; Statement stat = null; String
	 * sql = "update " + PROFORM_PARENT + " set id = " + "'" + rightNumber + "'"
	 * + " where id = " + "'" + wrongNumber + "'"; int update = 0; try { connect
	 * = DriverManager.getConnection(getCurrentIP.DB_PATH); stat =
	 * connect.createStatement(); update = stat.executeUpdate(sql); return
	 * update; } catch (SQLException e) { // TODO Auto-generated catch block
	 * DBException.DBExceptions("Грешка", e); DB_Err.writeErros(e.toString());
	 * e.printStackTrace(); return update; // e.printStackTrace(); } finally {
	 * try { if(stat != null) { stat.close(); } if(connect != null) {
	 * connect.close(); } } catch (SQLException e) { // TODO Auto-generated
	 * catch block DB_Err.writeErros(e.toString()); e.printStackTrace(); return
	 * update; } } }
	 */

	public static String getInvoiceCount(String dbPath) {
		Connection connection = null;
		Statement statement = null;
		String sqlCommand = String.format("select max(integer(id)) from %s where length(id) = 10",dbPath);
		ResultSet resultSet = null;
		int num = 0;
		try {
			connection = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sqlCommand);
			while (resultSet.next()) {
				num = resultSet.getInt(1);
				break;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return String.format("%010d",num+1);
	}
}
