package db.ServiceOrder;

import exceptions.DBException;
import log.DB_Err;
import net.GetCurrentIP;
import utils.MainPanel;

import java.sql.*;

public class ServiceNumber extends MainPanel {
    /* 1000000000 */
	
	  /*public void createso_table() { 
		  Connection connect = null; 
		  Statement stat  = null; 
		  String sql = "create table so_table (so varchar(20))"; try {
	  connect = DriverManager.getconnection("jdbc:derby://"+getcurrentip.getip() +
	  ":1527/c:/db"); stat = connect.createstatement(); stat.execute(sql);
	  system.out.println("service order table created succesfully!"); } catch
	  (sqlexception e) { // todo auto-generated catch block
	  e.printstacktrace(); } }
	 
	*/
	public void insertSO_Number(String so) {
		Connection connect = null;
		Statement stat = null;
		String sql = "insert into SO_Table values ('" + so + "')";
		try {
			connect = DriverManager.getConnection("jdbc:derby://"
					+ GetCurrentIP.getIP() + ":1527/C:/DB");
			stat = connect.createStatement();
			stat.execute(sql);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getSO_Number() {
		Connection connect = null;
		Statement stat = null;
		String sql = "select so from " + SERVICE_NUMBER;
		ResultSet rs = null;
		String so = "no";
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(sql);
		
			while(rs.next()) {
				so = rs.getString(1);
				break;
			}
		//	System.out.printf("so number%s taken succesfully\n",so);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.showErrorMessage("Грешка", e);
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
		return so;
	}

	public int updateSO_InDB(String updatedNumber) {
		Connection connect = null;
		Statement stat = null;
		String sql = "update " + SERVICE_NUMBER + " set so = '" + updatedNumber + "'";
		int update = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			update = stat.executeUpdate(sql);
		    return update;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.showErrorMessage("Грешка", e);
			DB_Err.writeErros(e.toString());
			e.printStackTrace();
			return update;
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
				DBException.showErrorMessage("Грешка", e);
				DB_Err.writeErros(e.toString());
				e.printStackTrace();
				return update;
			//	e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServiceNumber sot = new ServiceNumber();
		String num = sot.getSO_Number();
		System.out.println(num);
	//	sot.updateSO_InDB("1000020651");
	 
	}

}
