package Reports.ReportsWorkers;

import db.Artikul.Artikuli_DB;

import javax.swing.*;
import java.io.IOError;

public class RestoreQuantity extends SwingWorker {
    String dbTable;
    String artikulName;
    String kontragent;
    String invoiceByKontragent;
    String quantityAsString;
    private int quantity;
    public RestoreQuantity(String dbTable,String artikulName, String kontragent,
									String invoiceByKontragent,
                                            String quantityAsString) {
        this.dbTable = dbTable;
        this.artikulName = artikulName;
        this.kontragent = kontragent;
        this.invoiceByKontragent = invoiceByKontragent;
        this.quantityAsString = quantityAsString;
        try {
            quantity = Integer.parseInt(quantityAsString);
        } catch (Exception ignored) {}
    }
    @Override
    protected Object doInBackground() throws Exception {
        System.out.println( artikulName + " " + kontragent + " " + invoiceByKontragent + " " + quantity);

//        Artikuli_DB.decreaseArtikul_Quantity(dbTable,artikulName, kontragent,
//                invoiceByKontragent,
//                quantity);
        return null;
    }
}
