package db.SerialNumber;

import Exceptions.DBException;
import Log.DB_Err;
import generators.BarcodGenerator;
import generators.SerialGenerator;
import net.GetCurrentIP;
import utils.MainPanel;

import java.sql.*;

public class SerialTable extends MainPanel {
	
    BarcodGenerator bg = new BarcodGenerator();
    
    public String updateSerial() {
		Connection connect = null;
		Statement stat = null;
		String sql = null;
		ResultSet rs = null;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			sql = "select * from " + SERIAL_TABLE;
			rs = stat.executeQuery(sql);
			String updatedSerial = null;
	        while(rs.next()) {
				updatedSerial = rs.getString(1);
			}
		
			int[] next_serial = new SerialGenerator().updateSerial(updatedSerial);
			updatedSerial = new SerialGenerator().digitsToString(next_serial);
			stat.execute("update " + SERIAL_TABLE + " set serial = '" + updatedSerial + "'");
			return updatedSerial;
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
		return null;
	}
	
	
	// first serial initialization (0000000)
	private void insertIntoSerialTable(String serial) {
		Connection connect = null;
		Statement stat = null;
		String sql = null;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			sql = "insert into SerialTable values ('" + serial + "')";
			stat.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBException.DBExceptions("Грешка", e);
			e.printStackTrace();
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
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
         SerialTable bt = new SerialTable();
         String serial = "0000000";
      bt.insertIntoSerialTable(serial);
	}

}
