package db.Discount;

import Exceptions.DBException;
import Log.DB_Err;
import net.GetCurrentIP;
import utility.MainPanel;

import java.sql.*;

public class DiscountDB extends MainPanel {

	public static String getDiscount(String clientName) {
		Connection connect = null;
		Statement stat = null;
		Statement stat2 = null;
		String sql = "select discount from " + PERSON + " where name like '" + clientName + "'";
		String sql2 = "select discount from " + FIRM + " where firm like '" + clientName + "'";
		ResultSet rs = null;
		ResultSet rs2 = null;
		String discount = "";
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
	        rs = stat.executeQuery(sql);
			while(rs.next()) {
				discount = rs.getString(1);
				break;
			}
			if(discount.equals("")) {
				stat2 = connect.createStatement();
				rs2 = stat2.executeQuery(sql2);
				while(rs2.next()) {
					discount = rs2.getString(1);
					break;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			DB_Err.writeErros(e.toString());
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
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
			}
		}
		//System.out.println(discount);
		return discount;
	}
	
	public static int updateDiscount(String client,String discount) {
		int  update = 0; //  true if data are written succesfully!
		Connection connect = null;
		Statement stat = null;
		Statement stat2 = null;
		String sql1 = "update " + FIRM + " set discount = '" + discount + "'" + " where firm = '" + client + "'";
		String sql2 = "update " + PERSON + " set discount = '" + discount + "'" + " where name = '" + client + "'";
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			update = stat.executeUpdate(sql1);
			if(update == 0) {
				stat2 = connect.createStatement();
			    update = stat2.executeUpdate(sql2);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DB_Err.writeErros(e.toString());
			DBException.DBExceptions("Грешка", e);
			e.printStackTrace();
			return 0;
		//	e.printStackTrace();
		} finally {
			try {
				if(stat2 != null) {
				stat2.close();
				}
				if(stat != null) {
					stat.close();
				}
				if(connect != null) {
					connect.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				DB_Err.writeErros(e.toString());
				DBException.DBExceptions("Грешка", e);
				e.printStackTrace();
				return 0;
			//	e.printStackTrace();
			}
		}
		return update;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
