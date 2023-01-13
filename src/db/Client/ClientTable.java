package db.Client;

import Exceptions.DBException;
import Log.DB_Err;
import NewClient.editClient.IncorrectPerson;
import db.Conecting;
import db.modify.AddColumn;
import net.GetCurrentIP;
import utility.MainPanel;

import java.sql.*;
import java.util.ArrayList;

public class ClientTable extends MainPanel {

	/*
	 * private static Connection connect = null; private static Statement
	 * statement = null;
	 */

	/*
	 * private void createNewDB(String dbName) { try {
	 * Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
	 * connect =
	 * DriverManager.getConnection("jdbc:derby://"+getCurrentIP.getIP()
	 * +":1527/" +"C:\\"+dbName+";create=true"); } catch (ClassNotFoundException
	 * e) { // TODO Auto-generated catch block e.printStackTrace(); } catch
	 * (InstantiationException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (IllegalAccessException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } catch (SQLException e)
	 * { // TODO Auto-generated catch block e.printStackTrace(); } }
	 */
	private void createPersonTable() { // -> Person
		Connection connection = null;
		Statement statement = null;
		String sql = null;
		try {
			connection = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			statement = connection.createStatement();
			sql = "create table "
					+ PERSON
					+ " (name varchar(50),tel varchar(20),discount varchar(10),"
					+ "primary key (name))";
			statement.execute(sql);
			// System.out.println("done!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}

				if (connection != null) {
					connection.close();
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
				MainPanel.PERSON, column, value);
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

	public static int insertIntoPersonTable(String db, String name, String tel,
			String discount, String incorrectPerson) {
		Connection connection = null;
		Statement statement = null;
		String sql = null;
		int insert = 0;
		if (discount.isEmpty()) {
			discount = "0";
		}
		try {
			connection = DriverManager.getConnection(db);
			statement = connection.createStatement();
			sql = "insert into " + PERSON + " values (" + "'" + name + "'"
					+ "," + "'" + tel + "'" + "," + "'" + discount + "'" + ","
					+ "'" + incorrectPerson + "')";
			insert = statement.executeUpdate(sql);

			return insert;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка ", e);
			DB_Err.writeErros(e.toString());
			Log.DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return insert;

		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
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

	public static int editPersonTable(String OLD_FIRM_NAME,
			String NEW_FIRM_NAME, String tel, String discount,
			String incorrectPerson) {
		Connection connection = null;
		Statement statement = null;
		String sql = null;
		int insert = 0;
		if (discount.isEmpty()) {
			discount = "0";
		}
		try {
			connection = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			statement = connection.createStatement();
			sql = "update " + PERSON + " set name = '" + NEW_FIRM_NAME
					+ "',tel = '" + tel + "',discount = '" + discount
					+ "',incorrectPerson = '" + incorrectPerson
					+ "' where name = '" + OLD_FIRM_NAME + "'";
			insert = statement.executeUpdate(sql);
			return insert;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка ", e);
			DB_Err.writeErros(e.toString());
			Log.DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return insert;
			// e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
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

	public static ArrayList<String> getClientInfo(String client) {
		Connection connect = null;
		Statement stat = null;
		String query = "select tel from " + MainPanel.PERSON
				+ " where name = '" + client + "'";
		ResultSet rs = null;
		ArrayList<String> result = new ArrayList<String>();

		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(query);
			while (rs.next()) {
				result.add(rs.getString(1));
			}
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
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
			}
		}

		return result;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClientTable ct = new ClientTable();
		// ct.createPersonTable();
		Conecting.testInfo("PersonTable");
		AddColumn addColumn = new AddColumn();
		addColumn.addColumn(PERSON, "incorrectPerson", 2);
	}

	// this is old method work very good except names containing apostrofs !!!!!
	/*
	 * public static ArrayList<String> getClientDetails(String client) {
	 * ArrayList<String> details = new ArrayList<String>(); Connection connect =
	 * null; Connection connect2 = null; Statement stat = null; Statement stat2
	 * = null; ResultSet rs = null; ResultSet rs2 = null; String sql1 =
	 * "select * from " + PERSON + " where name like '" + client + "'" ;
	 * 
	 * String sql2 = "select * from " + FIRM + " where firm like '" + client +
	 * "'";
	 * 
	 * try { connect = DriverManager.getConnection(getCurrentIP.DB_PATH);
	 * 
	 * stat = connect.createStatement(); rs = stat.executeQuery(sql1);
	 * ResultSetMetaData rsmd = rs.getMetaData(); while(rs.next()) { for(int i =
	 * 0;i < rsmd.getColumnCount();i++) { details.add(rs.getString(i+1)); } }
	 * if(details.isEmpty()) { connect2 =
	 * DriverManager.getConnection(getCurrentIP.DB_PATH); stat2 =
	 * connect2.createStatement(); rs2 = stat2.executeQuery(sql2);
	 * ResultSetMetaData rsmd2 = rs2.getMetaData(); while(rs2.next()) { for(int
	 * i = 0;i < rsmd2.getColumnCount();i++) { details.add(rs2.getString(i+1));
	 * } } } } catch (SQLException e) { // TODO Auto-generated catch block
	 * DBException.DBExceptions("Грешка", e); DB_Err.writeErros(e.toString());
	 * e.printStackTrace(); return null; } finally { try { if(rs != null) {
	 * rs.close(); } if(rs2 != null) { rs2.close(); }
	 * 
	 * if(stat != null) { stat.close(); } if(stat2 != null) { stat2.close(); }
	 * if(connect != null) { connect.close(); } if(connect2 != null) {
	 * connect2.close(); } } catch (SQLException e) { // TODO Auto-generated
	 * catch block DB_Err.writeErros(e.toString()); // e.printStackTrace(); } }
	 * return details; }
	 */
	public static ArrayList<String> getClientDetails(String client) {
		ArrayList<String> details = new ArrayList<String>();
		Connection connect = null;
		Connection connect2 = null;
		PreparedStatement stat = null;
		PreparedStatement stat2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		String sql1 = "select * from " + PERSON + " where name = ?";

		String sql2 = "select * from " + FIRM + " where firm = ?";

		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);

			stat = connect.prepareStatement(sql1);
			stat.setString(1, client);
			rs = stat.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					details.add(rs.getString(i + 1));
				}
			}
			if (details.isEmpty()) {
				connect2 = DriverManager.getConnection(GetCurrentIP.DB_PATH);
				stat2 = connect2.prepareStatement(sql2);
				stat2.setString(1, client);
				rs2 = stat2.executeQuery();
				ResultSetMetaData rsmd2 = rs2.getMetaData();
				while (rs2.next()) {
					for (int i = 0; i < rsmd2.getColumnCount(); i++) {
						details.add(rs2.getString(i + 1));
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (rs2 != null) {
					rs2.close();
				}

				if (stat != null) {
					stat.close();
				}
				if (stat2 != null) {
					stat2.close();
				}
				if (connect != null) {
					connect.close();
				}
				if (connect2 != null) {
					connect2.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				DB_Err.writeErros(e.toString());
				// e.printStackTrace();
			}
		}
		return details;
	}

	public static ArrayList<IncorrectPerson> getClients2() {
		Connection connect = null;
		Statement stat = null;
		Statement stat2 = null;
		ResultSet r = null;
		ResultSet r2 = null;
		ArrayList<IncorrectPerson> list = new ArrayList<IncorrectPerson>();
		// ArrayList<String> output = new ArrayList<String>();
		String sql1 = "select firm, incorrectPerson from " + FIRM; // +
																	// " order by firm";
		String sql = "select name, incorrectPerson from " + PERSON;// +
																	// " order by name";
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			stat2 = connect.createStatement();
			r = stat.executeQuery(sql);
			while (r.next()) {
				list.add(new IncorrectPerson(r.getString(1), r.getString(2)));
			}
			r2 = stat2.executeQuery(sql1);
			while (r2.next()) {
				list.add(new IncorrectPerson(r2.getString(1), r2.getString(2)));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			DB_Err.writeErros(e.toString());
			e.printStackTrace();
		} finally {
			try {
				if (r != null) {
					r.close();
				}
				if (r2 != null) {
					r2.close();
				}
				if (stat != null) {
					stat.close();
				}
				if (stat2 != null) {
					stat2.close();
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
		// output = new String[list.size()];
		// output = list.toArray(output);

		return list;
	}

	public static ArrayList<String> getClients() {
		Connection connect = null;
		Statement stat = null;
		Statement stat2 = null;
		ResultSet r = null;
		ResultSet r2 = null;
		ArrayList<String> list = new ArrayList<String>();
		// ArrayList<String> output = new ArrayList<String>();
		String sql1 = "select firm from " + FIRM; // + " order by firm";
		String sql = "select name from " + PERSON;// + " order by name";
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			stat2 = connect.createStatement();
			r = stat.executeQuery(sql);
			while (r.next()) {
				list.add(r.getString(1));
			}
			r2 = stat2.executeQuery(sql1);
			while (r2.next()) {
				list.add(r2.getString(1));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			DB_Err.writeErros(e.toString());
			e.printStackTrace();
		} finally {
			try {
				if (r != null) {
					r.close();
				}
				if (r2 != null) {
					r2.close();
				}
				if (stat != null) {
					stat.close();
				}
				if (stat2 != null) {
					stat2.close();
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
		// output = new String[list.size()];
		// output = list.toArray(output);

		return list;
	}

	public static ArrayList<String> getClientData(String client,
			String destination, String name) {
		ArrayList<String> details = new ArrayList<String>();
		Connection connect = null;

		PreparedStatement stat = null;

		ResultSet rs = null;

		String sql1 = "select * from " + destination + " where " + name
				+ " = ?";

		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);

			stat = connect.prepareStatement(sql1);
			stat.setString(1, client);
			rs = stat.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {

				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					details.add(rs.getString(i + 1)); //
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return null;
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
		return details;
	}

	/*
	 * public static ArrayList<String> getClientData(String client,String
	 * destination,String name) { ArrayList<String> details = new
	 * ArrayList<String>(); Connection connect = null;
	 * 
	 * Statement stat = null;
	 * 
	 * ResultSet rs = null;
	 * 
	 * String sql1 = "select * from " + destination + " where " + name +
	 * " like " + "'" + client + "'" ;
	 * 
	 * try { connect = DriverManager.getConnection(getCurrentIP.DB_PATH);
	 * 
	 * stat = connect.createStatement(); rs = stat.executeQuery(sql1);
	 * ResultSetMetaData rsmd = rs.getMetaData(); while(rs.next()) {
	 * 
	 * for(int i = 0;i < rsmd.getColumnCount();i++) {
	 * details.add(rs.getString(i+1)); // } }
	 * 
	 * 
	 * } catch (SQLException e) { // TODO Auto-generated catch block
	 * DBException.DBExceptions("Грешка", e); DB_Err.writeErros(e.toString());
	 * e.printStackTrace(); return null; } finally { try { if(rs != null) {
	 * rs.close(); }
	 * 
	 * if(stat != null) { stat.close(); }
	 * 
	 * if(connect != null) { connect.close(); }
	 * 
	 * } catch (SQLException e) { // TODO Auto-generated catch block
	 * DB_Err.writeErros(e.toString()); e.printStackTrace(); } } return details;
	 * }
	 */
	/*
	 * int returnVal() { int[] arr = new int[4]; int x = 0; try { x = arr[4];
	 * return 1; } catch (Exception e) { return -1; } finally { //return 0;
	 * System.out.println("close"); } }
	 */
}
