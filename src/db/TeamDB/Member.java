package db.TeamDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import net.GetCurrentIP;
import Exceptions.DBException;
import utils.MainPanel;

public class Member extends MainPanel {

	private void createTeamTable() {
		Connection connect = null;
		Statement stat = null;
		String command = "create table " + TEAM
				+ " (usser varchar(50),password varchar(50),"
				+ "Service_Order varchar(20),Working_Book varchar (20),"
				+ "Invoice varchar(20),Reports varchar(20),"
				+ "New_Ext varchar(20),Hidden_Menu varchar(20) )";
		// add column Acquittance
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			stat.execute(command);
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
				e.printStackTrace();
			}

		}
	}

	public static ArrayList<Object[]> getMembers() {
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		Connection connect = null;
		Statement stat = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		String command = "select * from " + MainPanel.TEAM;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(command);
			while (rs.next()) {
				ArrayList<Object> list = new ArrayList<Object>();
				rsmd = rs.getMetaData();
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					list.add(rs.getString(i + 1));
				}
				result.add(list.toArray());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Log.DB_Err.writeErros(e.toString());
			DBException.DBExceptions("Грешка", e);
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
				e.printStackTrace();
			}
		}

		return result;
	}

	public static int addMemeber(String user, String password,
			String accessService_Order, String accessWorking_Book,
			String accessInvoice, String accessReports, String accessNew_Ext,
			String accessHidden_Menu, String acquittance) {
		Connection connect = null;
		Statement stat = null;
		String command = "insert into " + MainPanel.TEAM + " values ('" + user
				+ "','" + password + "','" + accessService_Order + "','"
				+ accessWorking_Book + "','" + accessInvoice + "','"
				+ accessReports + "','" + accessNew_Ext + "','"
				+ accessHidden_Menu + "','" + acquittance + "')";
		int result = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			result = stat.executeUpdate(command);
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
				e.printStackTrace();
			}
		}
		return result;
	}

	public static int removeMember(String usser) {
		Connection connect = null;
		Statement stat = null;
		String command = "delete from " + TEAM + " where usser like " + "'"
				+ usser + "'";
		int result = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			result = stat.executeUpdate(command);
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
				e.printStackTrace();
			}
		}

		return result;
	}

	public static String[] getUser(String usser) {
		Connection connect = null;
		Statement stat = null;
		ResultSet rs = null;
		String command = "select usser, password, Service_Order, Working_Book,"
				+ " Invoice, Reports,"
				+ " New_Ext , Hidden_Menu, Acquittance from " + TEAM// ,
				// Acquittance
				+ " where usser = " + "'" + usser + "'";
		String[] obj = new String[] { "user", "password", "no", "no", "no",
				"no", "no", "no", "no" };//
		// da se dobawi , Acquittance no
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(command);
			while (rs.next()) {
				for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
					obj[i] = rs.getString(i + 1);
				}
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Log.DB_Err.writeErros(e.toString());
			DBException.DBExceptions("Грешка", e);
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
				e.printStackTrace();
			}
		}
		return obj;
	}

	public static int updateAllColumnValues(String column, String value) {
		Connection connect = null;
		Statement stat = null;
		String command = String.format("update %s set %s = '%s'",
				MainPanel.TEAM, column, value);
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Member m = new Member();
		int update = m.updateAllColumnValues("Acquittance", "yes");
		System.out.println(update);
	}

}
