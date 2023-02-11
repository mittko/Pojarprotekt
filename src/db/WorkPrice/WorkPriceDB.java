package db.WorkPrice;

import Exceptions.DBException;
import net.GetCurrentIP;
import utils.MainPanel;

import java.sql.*;

public class WorkPriceDB extends MainPanel {

	private void createWorkPriceDB() {
		Connection connect = null;
		Statement stat = null;
		String command = "create table " + WORK + " (do varchar(50),price double)";
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
				if(stat != null) {
				stat.close();
				}
				if(connect != null) {
					connect.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				Log.DB_Err.writeErros(e.toString());
				e.printStackTrace();
			}
		}
	}
	
	private int initValues(String work,double price) {
		Connection connect = null;
		Statement stat = null;
		String command = "insert into " + WORK + " values ("
				+  "'" + work + "',"  + price + ")";
		int insert = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			insert = stat.executeUpdate(command);
			return insert;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Log.DB_Err.writeErros(e.toString());
			DBException.DBExceptions("Грешка", e);
			e.printStackTrace();
			return insert;
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
				Log.DB_Err.writeErros(e.toString());
				e.printStackTrace();
			}
		}
		
	}
	
	public static double getWorkValue(String work) {
		Connection connect = null;
		Statement stat = null;
		String command = "select price from " + WORK + " where do = '" + work + "'";
		ResultSet rs = null;
		double price = 0.0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(command);
			while(rs.next()) {
				price = rs.getDouble(1);
				break;
			}
			return price;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Log.DB_Err.writeErros(e.toString());
			DBException.DBExceptions("Грешка", e);
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
				Log.DB_Err.writeErros(e.toString());
				e.printStackTrace();
			}
		}
		return price;
	}
	public static int updateWorkValue(String work,double price) {
		Connection connect = null;
		Statement stat = null;
		String command = "update " + WORK + " set price = "
				 + price  + " where do like '" + work + "'";
		int update = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			update = stat.executeUpdate(command);
			return update;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Log.DB_Err.writeErros(e.toString());
			DBException.DBExceptions("Грешка", e);
			e.printStackTrace();
			return update;
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
				Log.DB_Err.writeErros(e.toString());
				e.printStackTrace();
				return update;
			}
		}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		    WorkPriceDB wpdb = new WorkPriceDB();
		//    wpdb.createWorkPriceDouble();
	       wpdb.initValues("ХИ", 30.0);
		
	    /*	System.out.println("ТО = " + getWorkPrice("ТО"));
		   	System.out.println("П = " + getWorkPrice("П"));
		   	System.out.println("ХИ = " + getWorkPrice("ХИ"));*/
	}

}
