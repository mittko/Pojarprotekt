package db.Brack;

import exceptions.DBException;
import net.GetCurrentIP;
import utils.MainPanel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ProtBrackTable extends MainPanel {
    
	private void createBrackTableDB() {
		Connection connect = null;
		Statement stat = null;
		String command = "create table " + BRACK + " (client varchar(50),type varchar(20),"
				+ "wheight varchar(30),barcod varchar(20),serial varchar(30),category varchar(20),"
				+ "brand varchar(20),reasons varchar(200),number varchar(20),tehnik varchar(50),"
				+ "date varchar(20),primary key (barcod))";
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
				log.DB_Err.writeErros(e.toString());
				DBException.showErrorMessage("Грешка", e);
				e.printStackTrace();
			}
		}
	}
	
	public static int insertIntoBrackTableDB(String client,String type,String wheight,
			String barcod,String serial,String category,String brand,String reasons,String number,
			String tehnik,String date) {
		Connection connect = null;
		Statement stat = null;
		String command = "insert into " + BRACK + " values ('" + client + "','" + type + "','"
				+ wheight + "','"	+ barcod + "','" + serial + "','" + category + "','" + brand + "','" 
				+ reasons + "','" + number + "','" + tehnik + "','" + date + "')";
		int insert = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			insert = stat.executeUpdate(command);
			return insert;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.DB_Err.writeErros(e.toString());
			DBException.showErrorMessage("Грешка", e);
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
				log.DB_Err.writeErros(e.toString());
				DBException.showErrorMessage("Грешка", e);
				e.printStackTrace();
				return insert;
			}
		}
	}
	public static int deleteExtinguisher(String serial,String dest) {
		Connection connect = null;
		Statement stat = null;
		String command = "delete from " + dest + " where serial like '" + serial + "'";
		int delete = 0;
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			delete = stat.executeUpdate(command);
			return delete;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.DB_Err.writeErros(e.toString());
			DBException.showErrorMessage("Грешка", e);
			e.printStackTrace();
			return delete;
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
				log.DB_Err.writeErros(e.toString());
				DBException.showErrorMessage("Грешка", e);
				e.printStackTrace();
				return delete;
			}
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
         ProtBrackTable pbt = new ProtBrackTable();
         pbt.createBrackTableDB();
	}

}
