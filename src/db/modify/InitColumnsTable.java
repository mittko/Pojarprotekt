package db.modify;

//
// Decompiled by Procyon v0.5.36
//

import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import Log.DB_Err;
import Exceptions.DBException;
import java.sql.DriverManager;
import net.GetCurrentIP;

public class InitColumnsTable
{
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
            DBException.DBExceptions("Error", e);
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
                DBException.DBExceptions("Error", e2);
                DB_Err.writeErros(e2.toString());
                e2.printStackTrace();
            }
        }
    }
}

