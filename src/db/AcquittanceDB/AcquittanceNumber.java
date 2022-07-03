package db.AcquittanceDB;

import Exceptions.DBException;
import net.GetCurrentIP;
import utility.MainPanel;

import java.sql.*;

public class AcquittanceNumber extends MainPanel {

	private void setAcquittanceNumberTable() {
		Connection connect = null;
		Statement stat = null;
		String command = "create table " + ACQUITTANCE_NUMBER + " (number varchar(20))";
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			stat.execute(command);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	private void initAcquittanceNumber(String number) {
		Connection connect = null;
		Statement stat = null;
		String command = "insert into " + ACQUITTANCE_NUMBER + " values ('" + number + "')";
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			stat.execute(command);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getAcquittanceNumber() {
		Connection connect = null;
		Statement stat = null;
		String command = "select number from " + ACQUITTANCE_NUMBER;
		ResultSet rs = null;
		String number = null;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(command);
			while(rs.next()) {
				number = rs.getString(1);
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			Log.DB_Err.writeErros(e.toString());
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {
				rs.close();
				}
				if(stat != null) {
					stat.close();
				}
				if(connect != null) {
					connect.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return number;
	}
	public static int updateAcquittanceNumber(String number) {
		Connection connect = null;
		Statement stat = null;
		int updateCount = 0;
		String command = "update " + ACQUITTANCE_NUMBER + " set number = '" + number + "'";
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			stat.executeUpdate(command);
			updateCount = stat.getUpdateCount();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("", e);
			Log.DB_Err.writeErros(e.toString());
			e.printStackTrace();
		} finally {
			try {
				if(stat != null) {
				stat.close();
				}
				if(connect != null) {
					connect.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return updateCount;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
         AcquittanceNumber acq = new AcquittanceNumber();
     //    acq.setAcquittanceNumberTable();
     //    updateAcquittanceNumber("0000002631");
         String acquittanceNumber = getAcquittanceNumber();
         System.out.println(acquittanceNumber);
	}

}
