package db.Protokol;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.GetCurrentIP;
import exceptions.DBException;
import log.DB_Err;
import utils.MainPanel;

public class ProtokolNumber extends MainPanel {
	/* 0000000 */

	/*
	 * private void createProtokolNumber() { Connection connect = null;
	 * Statement stat = null; String sql =
	 * "create table PR_Number (pr_number varchar(20))"; try { connect =
	 * DriverManager.getConnection("jdbc:derby://" + getCurrentIP.getIP() +
	 * ":1527/C:/DB"); stat = connect.createStatement(); stat.execute(sql);
	 * System.out.println("protkol number table created succesfully!"); } catch
	 * (SQLException e) { // TODO Auto-generated catch block
	 * DBException.DBExceptions("Грешка", e); e.printStackTrace(); } finally {
	 * try { if(stat != null) { stat.close(); } if(connect != null) {
	 * connect.close(); } } catch (SQLException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); } }
	 * 
	 * }
	 */
	public void initProtokolNumber(String pr_number) {
		Connection connect = null;
		Statement stat = null;
		String sql = "insert into PR_Number values " + "('" + pr_number + "')";
		try {
			connect = DriverManager.getConnection("jdbc:derby://"
					+ GetCurrentIP.getIP() + ":1527/C:/DB");
			stat = connect.createStatement();
			stat.execute(sql);
			System.out.println("protokol number init succesfully!");
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

	public static String getProtokolNumber() {
		Connection connect = null;
		Statement stat = null;
		String sql = "select * from " + PROTOKOL_NUMBER;
		ResultSet rs = null;
		String pr_number = null;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				pr_number = rs.getString(1);

				break;
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
		return pr_number;
	}

	public static int updateProtokolNumberInDB(String newNumber) {
		Connection connect = null;
		Statement stat = null;
		String sql = "update " + PROTOKOL_NUMBER + " set pr_number = " + "'"
				+ newNumber + "'";
		int update = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			update = stat.executeUpdate(sql);
			return update;
			// System.out.println("protokol number updated succesfully!");
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
				DBException.showErrorMessage("Грешка", e);
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
				// e.printStackTrace();
			}
		}
	}

	/*
	 * public void clearProtokolNumber() { Connection connect = null; Statement
	 * stat = null; String sql = "delete from PR_Number"; try { connect =
	 * DriverManager.getConnection("jdbc:derby://" + getCurrentIP.getIP() +
	 * ":1527/C:/DB"); stat = connect.createStatement(); stat.execute(sql);
	 * System.out.println("protokol numbers deleted succesfully!"); } catch
	 * (SQLException e) { // TODO Auto-generated catch block
	 * DBException.DBExceptions("Грешка", e); e.printStackTrace(); } finally {
	 * try { if(stat != null) { stat.close(); } if(connect != null) {
	 * connect.close(); } } catch (SQLException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); } } }
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ProtokolNumber pr = new ProtokolNumber();

		 updateProtokolNumberInDB("0017027"); // 7
		String protokolNumber = getProtokolNumber();// 0010295
		System.out.println(protokolNumber);
	}

}
