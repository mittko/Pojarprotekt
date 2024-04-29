package db.Part_Quantity_DB;

import exceptions.DBException;
import net.GetCurrentIP;
import utils.MainPanel;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Part_Quantity extends MainPanel {

	public void createPart_Quantity_Table() {
		Connection connect = null;
		Statement stat = null;
		String command = "create table " + PARTS_QUANTITY + " (part varchar(200),quantity int)";
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			stat.execute(command);
			System.out.println("part quantity table created successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
	public static int insertPartIntoQuantity(String part,int quantity) {
		Connection connect = null;
		Statement stat = null;
		String command = "insert into " + PARTS_QUANTITY + " values('" + part + "',"+ quantity + ")";
		int update = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			update = stat.executeUpdate(command);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.DB_Err.writeErros(e.toString());
			DBException.showErrorMessage("Грешка", e);
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
		return update;
	}
	
	public static ArrayList<Object[]> seePartsQuantity() {
		Connection connect = null;
		Statement stat = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		String command = "select * from " + PARTS_QUANTITY;
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(command);
			while(rs.next()) {
				Object[] obj = new Object[2];
				rsmd = rs.getMetaData();
				for(int i = 0 ;i < rsmd.getColumnCount();i++) {
					obj[i] = rs.getString(i+1);
				}
				result.add(obj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DBException.showErrorMessage("Грешка", e);
			log.DB_Err.writeErros(e.toString());
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
		return result;
	}
	
	public static int increaseQuantity(String part, int quantity) {
		Connection connect = null;
		Statement stat = null;
		String command = "update " + PARTS_QUANTITY  + 
				" set quantity = (quantity + " + quantity + ")" + " where part like '" + part + "'";
		int update = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			update = stat.executeUpdate(command);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DBException.showErrorMessage("Грешка", e);
			log.DB_Err.writeErros(e.toString());
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
		return update;
	}
	
	public static void decreaseQuantityWithBatch(TreeMap<Object,Integer> partsQuantityMap) {
	    Connection connection = null;
	    PreparedStatement ps = null;
	    String preparedCommand = "update " + PARTS_QUANTITY + 
				" set quantity = (quantity - ?)  where part like ?";
	    try {
			connection = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			connection.setAutoCommit(false);
			
	        ps = connection.prepareStatement(preparedCommand);
	        for(Map.Entry<Object, Integer> quantityMap : partsQuantityMap.entrySet()) {
	        	  ps.setInt(1, quantityMap.getValue());
	    		  ps.setString(2, quantityMap.getKey().toString());
	    		  ps.addBatch();
			}
	        
	        ps.executeBatch();
	        connection.commit();
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.DB_Err.writeErros(e.toString());
			DBException.showErrorMessage("Грешка", e);
			
		} finally {
		
				try {
					if(connection != null) {
					connection.close();
					}
					if(ps != null) {
						ps.close();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
	}
	
	public static int decreaseQuantity(String part, int quantity) {
		Connection connect = null;
		Statement stat = null;
		String command = "update " + PARTS_QUANTITY + " set quantity = (quantity - " 
				+ quantity + ")" + " where part like '" + part + "'";
		int update = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			update = stat.executeUpdate(command);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.showErrorMessage("Грешка", e);
			log.DB_Err.writeErros(e.toString());
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
		return update;
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        Part_Quantity pq = new Part_Quantity();
       // pq.createPart_Quantity_Table();
        ArrayList<Object[]> res = seePartsQuantity();
        for(int i = 0;i < res.size();i++) {
        	for(int j = 0;j < res.get(j).length;j++) {
        		System.out.println(res.get(i)[j]);
        	}
        }
     //   pq.createPart_Quantity_Table();
	}

}
