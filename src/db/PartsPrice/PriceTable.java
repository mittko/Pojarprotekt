package db.PartsPrice;

import Exceptions.DBException;
import Log.DB_Err;
import net.GetCurrentIP;
import utils.MainPanel;

import java.sql.*;
import java.util.ArrayList;

public class PriceTable extends MainPanel {



	public static void main(String[] args) {
		// TODO Auto-generated method stub
       PriceTable pt = new PriceTable();
   //    pt.createPartsTableDB();
         pt.getAllPrices();
     double d =  
    		 pt.getPartPriceFromDB("Гасително вещество (Въглероден диоксид)","CO2","5","45 кг");
     System.out.println(d);
    
	}
	private void createPartsTableDB() {
		Connection connect = null;
		Statement stat = null;
		String sql = null;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			sql = "create table " + PARTS_PRICE + " (part varchar(100),type varchar(20),"
					+ "wheight varchar(20),category varchar(20),price double)";
			stat.execute(sql);
	//		System.out.println(PARTS + " created succesfully");
		//	Logger2.writeWork("pricetable created succesfully!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			DB_Err.writeErros(e.toString());
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
	
		}
	public static boolean initPartPriceTable(String part,String type,String wheight,String category,double value) {
		Connection connect = null;
		Statement stat = null;
		String sql = "insert into " + PARTS_PRICE + " values ('" + part + "','" + type + "','" + wheight + "','" +
		category + "'," +  value + ")";
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			stat.execute(sql);
		//	Logger2.writeWork("Данните са записани успешно!");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			DB_Err.writeErros(e.toString());
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
		return false;
	}
	public static int updatePartsTableDB(String part,String type,String category,String wheight,double price) {
		int upd = 0;
		Connection connect = null;
		Statement stat = null;
		
		String update = "update " + PARTS_PRICE + " set price = " + price  + " where " + "part = "
		+ "'" + part + "'" + " and " + "type = " + "'" + type + "'" + " and " + "wheight = " 
				+ "'" + wheight + "'" + " and " + "category = " + "'" + category + "'";
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			upd = stat.executeUpdate(update);
			return upd;
		//	Logger2.writeWork("price updated succesfully!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return upd;
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
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
				return upd;
			}
		}
	}
	public static ArrayList<Object[]> getAllPrices() {
		Connection connect = null;
		Statement stat = null;
		String sql = "select * from " + PARTS_PRICE;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		ArrayList<Object> list = new ArrayList<Object>();
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(sql);
			while(rs.next()) {
				rsmd = rs.getMetaData();
				list.clear();
				for(int col = 0;col < rsmd.getColumnCount();col++) {
					System.out.print(rs.getString(col + 1)+" ");
					list.add(rs.getString(col+1));
				}
				System.out.println();
				result.add(list.toArray());
				//System.out.println(rs.getDouble(rsmd.getColumnCount()-1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public static double getPartPriceFromDB(String part,String type,String category,String wheight) {
		Connection connect = null;
		Statement stat = null;

		String takePrice = "select price from " + PARTS_PRICE + " where part = '" + part + "'"
				+ " and type = '" + type + "'" + " and category = '" + category + "'" +
			" and wheight = '" + wheight + "'";
	
    	ResultSet rs = null;
		double price = 0;  
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(takePrice);
			while(rs.next()) {
				price = rs.getDouble(1);
			}
		//	System.out.println("price in db = " + price);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return price;
			//e.printStackTrace();
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
				return price;
			//	e.printStackTrace();
			}
		}
		
		return price;
	}
}
