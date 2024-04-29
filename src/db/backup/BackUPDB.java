package db.backup;

import exceptions.DBException;
import net.GetCurrentIP;
import utils.MainPanel;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BackUPDB extends MainPanel {

	public static boolean backUpDatabase()
	{
	Connection connect = null;
	CallableStatement cs = null;
	try {
		connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
		cs = connect.prepareCall("CALL SYSCS_UTIL.SYSCS_BACKUP_DATABASE(?)"); 
		cs.setString(1, BACKUP_PATH);
		cs.execute(); 
		cs.close();
		return true;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		DBException.showErrorMessage("Грешка", e);
		log.DB_Err.writeErros(e.toString());
		e.printStackTrace();
	} finally {
	
			try {
				if(cs != null) {
				cs.close();
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
//	System.out.println("backed up database to "+BACKUP_PATH);
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
         backUpDatabase();
	}

}
