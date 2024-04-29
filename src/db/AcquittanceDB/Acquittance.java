package db.AcquittanceDB;

import exceptions.DBException;
import net.GetCurrentIP;
import utils.MainPanel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Acquittance extends MainPanel {

	private void createAcquittanceTable() {
		Connection connect = null;
		Statement stat = null;
		String command = "create table Acquittance (artikul varchar(200),broi varchar(20),med varchar(50),"
				+ "value varchar(20),number varchar(20),saller varchar(50),client varchar(50),date varchar(20),"
				+ "primary key (number))";
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			stat.execute(command);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.DB_Err.writeErros(e.toString());
			DBException.showErrorMessage("Грешка", e);
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
	
	public static int insertIntoAcquittanceTable(String dbTable,String artikul,String broi,String med,
			String value,String number,String saller,String client,String date) {
		Connection connect = null;
		Statement stat = null;
		String command = "insert into " + dbTable + " values (' " + artikul + "','" + broi + "','" + 
		med + "','" + value + "','" + number + "','" + saller + "','" + client + "','" + date + "')";
		int updateCount = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			stat.execute(command);
			updateCount = stat.getUpdateCount();
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
				DBException.showErrorMessage("Грешка", e);
				log.DB_Err.writeErros(e.toString());
				e.printStackTrace();
			}
		}
	
			return updateCount;
	
	}
	
	/*public static ArrayList<Object[]> getInfoForAcquittanceDB() {
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		Connection connect = null;
		Statement stat = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		String command = "select * from " + MainPanel.ACQUITTANCE;
		try {
			connect = DriverManager.getConnection(getCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(command);
			while(rs.next()) {
				ArrayList<Object> list = new ArrayList<Object>();
				rsmd = rs.getMetaData();
				for(int i = 0;i < rsmd.getColumnCount();i++) {
					list.add(rs.getString(i+1));
				}
				result.add(list.toArray());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			Log.DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return null;
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
	}*/
	/*private void printInfo() {
		ArrayList<Object[]> result = getInfoForAcquittanceDB();
		for(int i = 0;i < result.size();i++) {
			Object[] obj = result.get(i);
			for(int o = 0;o < obj.length;o++) {
				System.out.print("*" + obj[o] + "*" + "\n");
			}
			System.out.println();
		}
	}*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        Acquittance acq = new Acquittance();
      
	}

}
