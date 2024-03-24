package db.creditnote;

import Exceptions.DBException;
import Log.DB_Err;
import db.modify.AddColumn;
import db.modify.InitColumnsTable;
import mydate.MyGetDate;
import net.GetCurrentIP;
import utils.MainPanel;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;

import static net.GetCurrentIP.DB_PATH;
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
            connection = DriverManager.getConnection(DB_PATH);
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
                                 String price,String value, String kontragent,String invoiceByKontragent,
                                       String note_id, String credit_note_date) {
        String command = "insert into " + CREDIT_NOTE + " values ('" + id + "','" + payment + "','" + discount
                + "','" + sum + "','" + client + "','" + saller + "','" + date + "','" + protokol_id +
                "','" + artikul + "','" + med + "','" + quantity + "','" + price + "','" + value
                + "','" + kontragent + "','" + invoiceByKontragent+ "','" + note_id + "','"+credit_note_date+"')";
        Connection connection = null;
        Statement statement = null;
        int insert = 0;
        try {
            connection = DriverManager.getConnection(DB_PATH);
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
                "invoiceByKontragent, note_id, credit_note_date  from " + CREDIT_NOTE + " order by CAST(credit_note_date as DATE) desc";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet;
        ResultSetMetaData resultSetMetaData;
        ArrayList<ArrayList<Object>> data = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(DB_PATH);
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
            connection = DriverManager.getConnection(DB_PATH);
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

    public int updateCreditNoteNumber(String oldNoteId, String newNoteId) {
        Connection connection = null;
        Statement statement = null;
        String command = String.format("update %s set note_id = '%s' where note_id = '%s'", CREDIT_NOTE, newNoteId, oldNoteId);
        int update = -2;
        try {
            connection = DriverManager.getConnection(DB_PATH);
            statement = connection.createStatement();
            update = statement.executeUpdate(command);
            return update;
        } catch (SQLException e) {
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
                    DBException.DBExceptions("Грешка",e);
                }
        }
        return update;
    }


    public static HashSet<String> getCreditNotesNumberSet() {
        String command = "select id from " + CREDIT_NOTE;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet;
        HashSet<String> numberOfSet = new HashSet<>();
        try {
            connection = DriverManager.getConnection(DB_PATH);
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

    private static int deleteCreditNote(String destination, String numOfDocument) {
        Connection connect = null;
        Statement stat = null;
        int update = 0;
        String sqlCommand = "delete from " + destination + " where note_id = '"
                + numOfDocument + "'";
        try {
            connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
            stat = connect.createStatement();
            update = stat.executeUpdate(sqlCommand);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
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
        return update;
    }

    public static void main(String[] args) throws SQLException {
        CreditNoteTable creditNoteTable = new CreditNoteTable();
     //   creditNoteTable.updateCreditNoteNumber("3","1");
     //   creditNoteTable.createCreditNoteTable();
     //  getCreditNotes();
     //   int delete = deleteCreditNote(CREDIT_NOTE,"0");
     //   System.out.println(delete);

        AddColumn alertTable = new AddColumn();

    //    alertTable.addColumn(MainPanel.CREDIT_NOTE,"credit_note_date",20);
    //    int update = InitColumnsTable.updateAnyColumnValue(MainPanel.CREDIT_NOTE,"credit_note_date", "27.02.2024");
    //    System.out.println(update);
    }
}
