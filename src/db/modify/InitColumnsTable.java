package db.modify;

//
// Decompiled by Procyon v0.5.36
//

import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import log.DB_Err;
import exceptions.DBException;
import java.sql.DriverManager;

import mydate.MyGetDate;
import net.GetCurrentIP;
import utils.MainPanel;

public class InitColumnsTable
{
    public static void main(String[] args) {
        int update = updateAnyColumnValue(MainPanel.CREDIT_NOTE,"credit_note_date", MyGetDate.getReversedSystemDate());
        // int update = updateAnyColumnValue(MainPanel.FIRM,"vat_registration","не");
        System.out.println(update);
    }
    public static int updateAnyColumnValue(final String table, final String column, final String value) {
        Connection connect = null;
        Statement stat = null;
        final String command = String.format("update %s set %s = '%s'", table, column, value);
        int update = 0;
        try {
            connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
            stat = connect.createStatement();
            update = stat.executeUpdate(command);
            return update;
        }
        catch (SQLException e) {
            DBException.showErrorMessage("Error", e);
            DB_Err.writeErros(e.toString());
            e.printStackTrace();
            return update;
        }
        finally {
            try {
                if (stat != null) {
                    stat.close();
                }
                if (connect != null) {
                    connect.close();
                }
            }
            catch (SQLException e2) {
                DBException.showErrorMessage("Error", e2);
                DB_Err.writeErros(e2.toString());
                e2.printStackTrace();
            }
        }
    }
}

