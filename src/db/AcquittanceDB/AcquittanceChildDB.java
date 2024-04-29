package db.AcquittanceDB;

import exceptions.DBException;
import net.GetCurrentIP;
import utils.MainPanel;

import java.sql.*;
import java.util.ArrayList;

public class AcquittanceChildDB extends MainPanel {

	private void createAcquittanceChildDB() {
		Connection connect = null;
		Statement stat = null;
		String command = "create table " + ACQUITTANCE_CHILD + " "
				+ "(id varchar(20),artikul varchar(200),med varchar(20),quantity varchar(50),price varchar(20),"
				+ "value varchar(20),client varchar(100),foreign key (id) references " + 
				ACQUITTANCE_PARENT + " (id))";
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			stat.execute(command);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static int insertIntoAcquittanceChildDB(String id,String artikul,String med,
			String quantity,String price,String value,String client) {
		Connection connect = null;
	//	Statement stat = null;
		PreparedStatement ps = null;
		/*String comand = "insert into " + ACQUITTANCE_CHILD + " values ('" 
				+ id + "','" + artikul + "','" + med + "','" + quantity 
				+ "','" + price + "','" + value  + "','" + client + "')";*/
		String comand = "insert into " + ACQUITTANCE_CHILD + 
				" values ( ?, ?, ?, ?, ?, ?, ?)";
		int insert = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			ps = connect.prepareStatement(comand);
			ps.setString(1, id);
			ps.setString(2, artikul);
			ps.setString(3, med);
			ps.setString(4, quantity);
			ps.setString(5, price);
			ps.setString(6, value);
			ps.setString(7, client);
			insert = ps.executeUpdate();
		//	stat = connect.createStatement();
		//	stat.executeUpdate(comand);
		//	insert = stat.getUpdateCount();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.showErrorMessage("Грешка", e);
			log.DB_Err.writeErros(e.toString());
			e.printStackTrace();
		} finally {
			try {
				if(ps != null) {
				ps.close();
				}
				if(connect != null) {
					connect.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return insert;
	}
	
	public static ArrayList<Object[]> getInfoForAcquittanceChildDB() {
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		Connection connect = null;
		Statement stat = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		String command = "select * from " + ACQUITTANCE_CHILD;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
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
			DBException.showErrorMessage("Грешка", e);
			log.DB_Err.writeErros(e.toString());
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
	}
	
	private void printInfo() {
		ArrayList<Object[]> result = getInfoForAcquittanceChildDB();
		for(int i = 0;i < result.size();i++) {
			Object[] obj = result.get(i);
			for(int o = 0;o < obj.length;o++) {
				System.out.print("*" + obj[o] + "*" + "\n");
			}
			System.out.println();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
      AcquittanceChildDB acq3 = new AcquittanceChildDB();
      acq3.printInfo();
	}

}
