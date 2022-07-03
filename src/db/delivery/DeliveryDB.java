package db.delivery;

import Exceptions.DBException;
import Log.DB_Err;
import net.GetCurrentIP;
import utility.MainPanel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DeliveryDB extends MainPanel {
    public static void createDeliveryArtikulDB() {
        Connection connect = null;
        Statement stat = null;
        String command = "create table "
                + DELIVERY_ARTIKULS
                + " (artikul varchar(300),quantity int,"
                + "med varchar(20),value varchar(20),kontragent varchar(100),invoiceByKontragent varchar(20),"
                + "date varchar(20),operator varchar(20))";

        try {
            connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
            stat = connect.createStatement();
            stat.execute(command);
            System.out.println("table created succesfully!");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            DBException.DBExceptions("Грешка", e);
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
    }
}
