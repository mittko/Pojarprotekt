package db.Invoice;

import Exceptions.DBException;
import Log.DB_Err;
import net.GetCurrentIP;
import utility.MainPanel;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class InvoiceParent_DB extends MainPanel {

	public void createInvoiceParent_DB() {
		Connection connect = null;
		Statement stat = null;
		String command = "create table " + INVOICE_PARENT
				+ " (id varchar(20),payment varchar(100),"
				+ "discount varchar(10),value varchar(10),client varchar(100),"
				+ "saller varchar(100),date varchar(20),primary key (id))";

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

	public static int insertIntoInvoiceParent(String dbTable, String id, String protokol,
			String payment, String discount, String value, String client,
			String saller, String date, String invoiceName) {
		Connection connect = null;
		Statement stat = null;
		String command = "insert into " + dbTable + " values ('" + id
				+ "','" + payment + "','" + discount + "','" + value + "','"
				+ client + "','" + saller + "','" + date + "','" + protokol
				+ "','" + invoiceName +"')";
		int insert = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			insert = stat.executeUpdate(command);
			return insert;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			Log.DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return insert;
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

	public static HashMap<String, String> getInvoiceNumbersFromProtokol(
			TreeSet<String> protokolNumbers) {
		Connection connect = null;
		Statement stat = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		HashMap<String, String> hashMap = new HashMap<String, String>();
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			for (String protokol : protokolNumbers) {
				String command = "select id, date from " + INVOICE_PARENT
						+ " where protokol = '" + protokol + "'";
				rs = stat.executeQuery(command);
				rsmd = rs.getMetaData();
				while (rs.next()) {
					for (int i = 0; i < rsmd.getColumnCount(); i++) {
						hashMap.put(protokol,
								rs.getString(1) + " /" + rs.getString(2) + "/");
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hashMap;
	}

	/*
	 * private void getInvoiceInfo(String id) { Connection connect = null;
	 * Statement stat = null; String command = "select * from " + INVOICE_PARENT
	 * + "," + INVOICE_CHILD + " where '" + INVOICE_PARENT + ".id'" + " like '"
	 * + id + "'" + " and '" + INVOICE_CHILD + ".id'" + " like '" + id + "'";
	 * ResultSet rs = null; ResultSetMetaData rsmd = null; try { connect =
	 * DriverManager.getConnection(getCurrentIP.DB_PATH); stat =
	 * connect.createStatement(); rs = stat.executeQuery(command);
	 * while(rs.next()) { rsmd = rs.getMetaData(); for(int i = 0;i <
	 * rsmd.getColumnCount();i++) { System.out.printf("%s ",rs.getString(i +
	 * 1)); } System.out.printf("\n"); } } catch (SQLException e) { // TODO
	 * Auto-generated catch block DBException.DBExceptions("Грешка", e);
	 * Log.DB_Err.writeErros(e.toString()); e.printStackTrace(); } finally { try
	 * { if(stat != null) { stat.close(); } if(connect != null) {
	 * connect.close(); } } catch (SQLException e) { // TODO Auto-generated
	 * catch block DBException.DBExceptions("Грешка", e);
	 * Log.DB_Err.writeErros(e.toString()); e.printStackTrace(); } } }
	 */
	public static ArrayList<Object[]> getParentInfo(String dest, String command) {
		Connection connect = null;
		Statement stat = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		ArrayList<Object[]> info = new ArrayList<Object[]>();
		ArrayList<String> details = null;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(command);
			while (rs.next()) {
				rsmd = rs.getMetaData();
				details = new ArrayList<String>();
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					details.add(rs.getString(i + 1));
				}
				info.add(details.toArray());
			}
			return info;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			Log.DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return null;
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

	public static int deleteInvoiceParent(String id) {
		Connection connect = null;
		Statement stat = null;
		String command = "delete from " + INVOICE_PARENT + " where id like "
				+ "'" + id + "'";
		int del = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			del = stat.executeUpdate(command);
			return del;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			Log.DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return del;
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

	public static int deleteInvoiceChild(String id) {
		Connection connect = null;
		Statement stat = null;
		String command = "delete from " + INVOICE_CHILD + " where id like "
				+ "'" + id + "'";
		int del = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			del = stat.executeUpdate(command);
			return del;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			Log.DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return del;
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

	private String buildCommandForInvoice(String parent_db, String child_db,
			String client) {
		String command = /*
						 * "select " + parent_db+".payment, " + parent_db +
						 * ".discount, " + parent_db+".value, " +
						 * parent_db+".client, " + parent_db+".saller, " +
						 * parent_db+".date, " + child_db+".make, " +
						 * child_db+".med, " + child_db+".quantity, " +
						 * child_db+".price, " + child_db+".value from " +
						 * parent_db + " inner join " + child_db + " on (" +
						 * parent_db+".id = " + child_db+".id) where client = "
						 * + client;
						 */
		"select ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? from " + parent_db
				+ " inner join " + child_db + " on (? = ?) where client like ?";
		return command;
	}

	private void getInfoFromParentAndChild_Invoice(String parent, String child,
			String client) {
		Connection connect = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select " + parent + ".payment, " + parent
				+ ".discount, " + parent + ".value, " + parent + ".client, "
				+ parent + ".saller, " + parent + ".date, " + child + ".make, "
				+ child + ".med, " + child + ".quantity, " + child + ".price, "
				+ child + ".value from " + parent + " join " + child + " on "
				+ parent + ".id = " + child + ".id" + " and " + parent
				+ ".client = " + child + ".client" + " where " + parent
				+ ".client = ?";

		// System.out.println(query);

		/*
		 * "select ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? from " + parent +
		 * " inner join " + child + " on (? = ?) where client like ?";
		 */
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);

			ps = connect.prepareStatement(query);

			// ps.setString(1,"0000004875");//child+".id");
			ps.setString(1, client);
			rs = ps.executeQuery();

			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					System.out.println(rs.getString(i + 1));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			DB_Err.writeErros(e.toString());
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (ps != null) {
					ps.close();
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InvoiceParent_DB invoice = new InvoiceParent_DB();
		// System.out.println(deleteInvoiceChild("0000005047"));
		// System.out.println(deleteInvoiceParent("0000005047"));

		/*
		 * String query = invoice.buildCommandForInvoice( INVOICE_PARENT,
		 * INVOICE_CHILD,"ГАЛКОМ ЕООД");
		 */
		// invoice.getInfoFromParentAndChild_Invoice(
		// INVOICE_PARENT, INVOICE_CHILD,"\"ИННА\" ООД");
		// invoice.createInvoiceParent_DB();
		// invoice.insertIntoInvoiceParent("1001", "в брой", "5", "30.5",
		// "Красимир Ушатов", "Г.Ковачки", GetDate.getSystemDate());
		// String num = "0000000044";
		// invoice.getInvoiceInfo(num);
	}

}
