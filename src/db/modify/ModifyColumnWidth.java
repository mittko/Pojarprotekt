package db.modify;

import exceptions.DBException;
import log.DB_Err;
import net.GetCurrentIP;
import utils.MainPanel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ModifyColumnWidth {

    public static void main(String[] args) {
       int modify =  modifyColumnWidth(MainPanel.PROTOKOL,"wheight",50);
        System.out.println(modify);
    }
    public static int modifyColumnWidth(String table, String column, int width) {
        Connection connect = null;
        Statement stat = null;
        String modifyString = String.format(
                "alter table %s alter column %s set data type varchar(%d)",
                table, column, width);
        // origin "alter table " + ARTIKULS +
        // " alter column artikul set data type varchar(" + width + ")";
        int modify = 0;
        try {
            connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
            stat = connect.createStatement();
            modify = stat.executeUpdate(modifyString);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            DBException.showErrorMessage("Грешка", e);
            DB_Err.writeErros(e.toString());
            e.printStackTrace();
        } finally {

            try {
                if (stat != null) {
                    stat.close();
                }
                if (connect != null) {
                    connect.close();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return modify;
    }


}
