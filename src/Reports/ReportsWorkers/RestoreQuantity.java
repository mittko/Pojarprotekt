package Reports.ReportsWorkers;

import db.Artikul.Artikuli_DB;

import javax.swing.*;

public class RestoreQuantity extends SwingWorker {
    String dbTable;
    String artikulName;
    String client;
    String clientColumnName;// column name in database
    String invoice;
    String invoiceColumnName; // column name in database
    String quantityAsString;
    private int quantity;

    private String invoiceNumOfDocument;
    public RestoreQuantity(String dbTable,String artikulName, String client,
                                    String clientColumnName,
									String invoice,
                                            String invoiceColumnName,
                                            String invoiceNumOfDocument,
                                            String quantityAsString) {
        this.dbTable = dbTable;
        this.artikulName = artikulName;
        this.client = client;
        this.clientColumnName = clientColumnName;
        this.invoice = invoice;
        this.invoiceColumnName = invoiceColumnName;
        this.invoiceNumOfDocument = invoiceNumOfDocument;
        this.quantityAsString = quantityAsString;
        try {
            quantity = Integer.parseInt(quantityAsString);
        } catch (Exception ignored) {}
    }
    @Override
    protected Object doInBackground() throws Exception {
        System.out.println( artikulName + " " + client + " " + invoice + " " + quantity);
//        Artikuli_DB.decreaseArtikul_Quantity(dbTable,artikulName, client,clientColumnName ,
//                invoice, invoiceColumnName, quantity * -1);

        Artikuli_DB.markInvoiceAsCreditNote(dbTable,artikulName, client,clientColumnName ,
                invoice, invoiceColumnName, invoiceNumOfDocument,0);
        return null;
    }
}
