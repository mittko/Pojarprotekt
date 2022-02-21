package db.Brack;

import Exceptions.DBException;
import Log.DB_Err;
import net.GetCurrentIP;
import utility.MainPanel;

import java.sql.*;

public class BrackNumber extends MainPanel {
      /* 0000000 */
	
	/*public void createBrackNumber() {
		Connection connect = null;
		Statement stat = null;
		String sql = "create table BrackNumber (br_number varchar(20))";
		try {
			connect = DriverManager.getConnection("jdbc:derby://" + getCurrentIP.getIP() + 
					":1527/C:/DB");
			stat = connect.createStatement();
			stat.execute(sql);
			System.out.println("BrackNumber table created succesfully!");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
				e.printStackTrace();
			}
		}
	}*/
	public int insertBrackNumber(String br_number) {
		Connection connect = null;
		Statement stat = null;
		String sql = "insert into " + BRACK_NUMBER + " values (" + "'" + br_number + "'" + ")";
		int insert = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			insert = stat.executeUpdate(sql);
			return insert;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return insert;
		//	e.printStackTrace();
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
				return insert;
			//	e.printStackTrace();
			}
		}
	}
	public void clearBrackNumber() {
		Connection connect = null;
		Statement stat = null;
		String sql = "delete from BrackNumber";
		try {
			connect = DriverManager.getConnection("jdbc:derby://" + GetCurrentIP.getIP() +
					":1527/C:/DB");
			stat = connect.createStatement();
			stat.execute(sql);
			System.out.println("BrackNumber table deleted succesfully!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			e.printStackTrace();
		}    finally {
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
	public int updateBrackNumber(String newNumber) {
		Connection connect = null;
		Statement stat = null;
		String sql = "update " + BRACK_NUMBER + " set br_number = " + "'" + newNumber + "'";
		int update = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			update = stat.executeUpdate(sql);
			return update;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return update;
		//	e.printStackTrace();
		}  finally {
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
				return update;
			}
		}
	}
	public static String getBrackNumber() {
		Connection connect = null;
		Statement stat = null;
		String sql = "select br_number from " + BRACK_NUMBER;
		ResultSet rs = null;
		String br_number = null;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(sql);
			while(rs.next()) {
				br_number = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			DB_Err.writeErros(e.toString());
			e.printStackTrace();
		}  finally {
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
		return br_number;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        BrackNumber bn = new BrackNumber();
  
        bn.updateBrackNumber("0000000");
      
   
	}

}
