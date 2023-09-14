package Reports.ReportsWorkers;

import Exceptions.ErrorDialog;
import db.Artikul.Artikuli_DB;
import db.creditnote.CreditNoteTable;
import utils.MainPanel;

import javax.swing.*;

public class RestoreQuantity extends SwingWorker {
 private String invoiceNumOfDocument;
 private String payment;
 private String discount;
 private String sum;
 private String client;
 private String saller;
 private String date;
 private String protokolNumber;
 private String artikul;
 private String med;
 private String quantity;
 private String price;
 private String value;
 private String kontragent;
 private String invoiceByKontragent;

 private int quantityAsInt;

    public RestoreQuantity(String invoiceNumOfDocument, String payment, String discount, String sum, String client, String saller, String date, String protokolNumber, String artikul, String med, String quantity, String price, String value, String kontragent, String invoiceByKontragent) {
        this.invoiceNumOfDocument = invoiceNumOfDocument;
        this.payment = payment;
        this.discount = discount;
        this.sum = sum;
        this.client = client;
        this.saller = saller;
        this.date = date;
        this.protokolNumber = protokolNumber;
        this.artikul = artikul;
        this.med = med;
        this.quantity = quantity;
        this.price = price;
        this.value = value;
        this.kontragent = kontragent;
        this.invoiceByKontragent = invoiceByKontragent;
        try {
            this.quantityAsInt = Integer.parseInt(quantity);
        } catch (Exception ignored) {}
    }



    @Override
    protected Object doInBackground() throws Exception {
 //       System.out.println( artikul + " " + kontragent + " " + invoiceByKontragent + " " + quantity);

        int decrease = Artikuli_DB.decreaseArtikul_Quantity(MainPanel.AVAILABLE_ARTIKULS,
                artikul, kontragent,"client" ,
                invoiceByKontragent, "invoice", quantityAsInt * -1);
        if(decrease == 1) {
            decrease = Artikuli_DB.decreaseArtikul_Quantity(MainPanel.DELIVERY_ARTIKULS,artikul, kontragent,
                    "kontragent" ,
                invoiceByKontragent, "invoiceByKontragent", quantityAsInt * -1);
            if(decrease == 1) {
                int update =  Artikuli_DB.markInvoiceAsCreditNote(MainPanel.INVOICE_CHILD,artikul,
                        kontragent,"kontragent" ,
                invoiceByKontragent, "invoiceByKontragent", invoiceNumOfDocument,0);
                if(update == 1) {
                    int insert = CreditNoteTable.insertCreditNote(
                            invoiceNumOfDocument,
                            payment,
                            discount,
                            sum,
                            client,
                            saller,
                            date,
                            protokolNumber,
                           artikul,
                            med,
                            quantity,
                            price,
                            value,
                            kontragent,
                            invoiceByKontragent);
                    System.out.println("credit note insert " + insert);
                    if(insert == 1) {
                        JOptionPane.showMessageDialog(null,"Кредитното известие е записано успешно");
                    } else {
                        ErrorDialog.showErrorMessage("Грешка при записът на кредитното известие");
                    }
                } else {
                    ErrorDialog.showErrorMessage("Грешка при нулиране на количествата във фактурата");
                }
            } else {
                ErrorDialog.showErrorMessage("Грешка при възстановяване на количествата в доставки");
            }
        } else {
            ErrorDialog.showErrorMessage("Грешка при възстановяване на количествата в склада");
        }
        return null;
    }
}
