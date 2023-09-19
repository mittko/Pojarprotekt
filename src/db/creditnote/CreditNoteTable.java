package db.creditnote;

import Exceptions.DBException;
import Log.DB_Err;
import net.GetCurrentIP;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;

import static utils.MainPanel.CREDIT_NOTE;

public class CreditNoteTable {

    private void createCreditNoteTable() {
        String command = "create table " + CREDIT_NOTE + " (id varchar(20), payment varchar(100)," +
                "discount varchar(10), invoiceSum varchar(10), client varchar(200), saller varchar(100)," +
                "date varchar(20), protokol_id varchar(20), artikul varchar(200), med varchar(20)," +
                "quantity varchar(50), price varchar(20), value varchar(20), kontragent varchar(200)," +
                "invoiceByKontragent varchar(20),note_id varchar(20) ) ";
        Connection connection = null;
        Statement statement = null;

        try {
            connection = DriverManager.getConnection(GetCurrentIP.DB_PATH);
            statement = connection.createStatement();
            statement.execute(command);
            System.out.println("table created successfully !");
        } catch (SQLException e) {
          e.printStackTrace();
        } finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null) {
                try {
                    connection.commit();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static int insertCreditNote(String id, String  payment, String discount ,
                                 String sum,String client,String saller,
            String date,String protokol_id,String artikul,String  med,String quantity,
                                 String price,String value, String kontragent,String invoiceByKontragent,String note_id) {
        String command = "insert into " + CREDIT_NOTE + " values ('" + id + "','" + payment + "','" + discount
                + "','" + sum + "','" + client + "','" + saller + "','" + date + "','" + protokol_id +
                "','" + artikul + "','" + med + "','" + quantity + "','" + price + "','" + value
                + "','" + kontragent + "','" + invoiceByKontragent+ "','" + note_id + "')";
        Connection connection = null;
        Statement statement = null;
        int insert = 0;
        try {
            connection = DriverManager.getConnection(GetCurrentIP.DB_PATH);
            statement = connection.createStatement();
            insert = statement.executeUpdate(command);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.DB_Err.writeErros(e.getMessage());
            DBException.DBExceptions("Грешка",e);
        } finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
       return insert;
    }
    public static ArrayList<ArrayList<Object>> getCreditNotes() throws SQLException {
        String command = "select id ,payment ," +
                "discount , invoiceSum , client , saller, " +
                "date , protokol_id , artikul , med, " +
                "quantity , price , value , kontragent, " +
                "invoiceByKontragent, note_id  from " + CREDIT_NOTE + " order by CAST(date as DATE) desc";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet;
        ResultSetMetaData resultSetMetaData;
        ArrayList<ArrayList<Object>> data = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(GetCurrentIP.DB_PATH);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(command);
            while (resultSet.next()) {
                resultSetMetaData = resultSet.getMetaData();
                ArrayList<Object> list = new ArrayList<>();
                for(int column = 0;column < resultSetMetaData.getColumnCount();column++) {
                  //  System.out.print(resultSet.getString(column+1)+" ");
                    list.add(resultSet.getString(column+1));
                }
                data.add(list);
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Log.DB_Err.writeErros(e.getMessage());
            DBException.DBExceptions("Грешка",e);
        } finally {
            if(statement != null) {
                statement.close();
            }
            if(connection != null) {
                connection.commit();
            }
        }
       return data;
    }

    public static boolean checkIsDocumentWritten(String numOfDocument) {
        String command = "select * from " + CREDIT_NOTE + " where id='"+numOfDocument+"'";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet;
        try {
            connection = DriverManager.getConnection(GetCurrentIP.DB_PATH);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(command);
            return resultSet.next();
        } catch (SQLException e) {
            return false;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.commit();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static HashSet<String> getCreditNotesNumberSet() {
        String command = "select id from " + CREDIT_NOTE;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet;
        HashSet<String> numberOfSet = new HashSet<>();
        try {
            connection = DriverManager.getConnection(GetCurrentIP.DB_PATH);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(command);
            while (resultSet.next()) {
                String numberAsString = resultSet.getString(1);
                numberOfSet.add(numberAsString);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Log.DB_Err.writeErros(e.getMessage());
            DBException.DBExceptions("Грешка",e);
        } finally {
            try {
            if(statement != null) {
                statement.close();
            }
            if(connection != null) {
                connection.commit();
            }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return numberOfSet;
    }

    public static void main(String[] args) throws SQLException {
        CreditNoteTable creditNoteTable = new CreditNoteTable();
        creditNoteTable.createCreditNoteTable();
     //  getCreditNotes();
    }
}
