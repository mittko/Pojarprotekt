package reports.workers;

import exceptions.ErrorDialog;
import db.artikul.Artikuli_DB;
import db.creditnote.CreditNoteTable;
import mydate.MyGetDate;
import utils.MainPanel;
import utils.MyCallback;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class RestoreQuantity extends SwingWorker {
 private String invoiceNumOfDocument;

 private final String creditNoteNumOfDocument;
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

 private final int startRow;
 private final int endRow;
 private final DefaultTableModel dftm;
 private final MyCallback<Object> callback;

    public RestoreQuantity(DefaultTableModel dftm, int startRow, int endRow, String creditNoteNumOfDocument,
                           final MyCallback<Object> callback) {
        this.dftm = dftm;
        this.startRow = startRow;
        this.endRow = endRow;
        this.creditNoteNumOfDocument = creditNoteNumOfDocument;
        this.callback = callback;
    }


    @Override
    protected Void doInBackground() throws Exception {
        for (int i = 0; i < endRow; i++) {
            String invoiceNumOfDocument = dftm.getValueAt(i + startRow, 0).toString();
            String payment = dftm.getValueAt(i + startRow, 1).toString();
            String discount = dftm.getValueAt(i + startRow, 2).toString();
            String sum = dftm.getValueAt(i + startRow, 3).toString();
            String client = dftm.getValueAt(i + startRow, 4).toString();
            String saller = dftm.getValueAt(i + startRow, 5).toString();
            String date = dftm.getValueAt(i + startRow, 6).toString();
            String protokolNumber = dftm.getValueAt(i + startRow, 7).toString();

            String artikul = dftm.getValueAt(i + startRow, 9)+"";
            String med = dftm.getValueAt(i + startRow, 10).toString();
            String quantity = dftm.getValueAt(i + startRow, 11).toString();
            String price = dftm.getValueAt(i + startRow, 12).toString();
            String value = dftm.getValueAt(i + startRow, 13).toString();

            String kontragent = dftm.getValueAt(i + startRow, 15).toString();
            String invoiceByKontragent = dftm.getValueAt(i + startRow, 16).toString();

            try {
                this.quantityAsInt = Integer.parseInt(quantity);
            } catch (Exception ignored) {}

//            System.out.println("invoiceNumOfDocument " + invoiceNumOfDocument);
//            System.out.println("payment "+payment);
//            System.out.println("discount "+discount);
//            System.out.println("sum "+sum);
//            System.out.println("client "+client);
//            System.out.println("saller "+saller);
//            System.out.println("date "+date);
//            System.out.println("protokolNumber "+protokolNumber);
//            System.out.println("artikul "+artikul);
//            System.out.println("med "+med);
//            System.out.println("quantity "+quantity);
//            System.out.println("price "+price);
//            System.out.println("value "+value);
//            System.out.println("kontragent "+kontragent);
//            System.out.println("invoiceByKontragent "+invoiceByKontragent);


            int restore = Artikuli_DB.decreaseArtikul_Quantity(MainPanel.AVAILABLE_ARTIKULS,
                    artikul, kontragent, "client",
                    invoiceByKontragent, "invoice", quantityAsInt * -1);
            System.out.println("restore quantity " + restore);
            // returned result may be 0 because artikul not available in sklad but come from workshop

            int update = Artikuli_DB.setArtikulQuantityInInvoiceAsZero(MainPanel.INVOICE_CHILD, artikul,
                        kontragent, "kontragent",
                        invoiceByKontragent, "invoiceByKontragent", invoiceNumOfDocument, 0);
            System.out.println("UPDATE " + update);
                if (update == 1) {
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
                            invoiceByKontragent,
                            creditNoteNumOfDocument,
                            MyGetDate.getReversedSystemDate());
                    if (insert == 1) {
                        System.out.println("артикулът е записан в кредитното известие успешно");
                    } else {
                        ErrorDialog.showErrorMessage("Грешка при записът на артикулът в кредитното известие");
                        System.out.println("Грешка при записът на артикулът в кредитното известие");
                        return null;

                    }
                } else {
                    ErrorDialog.showErrorMessage("Грешка при нулиране на артикулът във фактурата");
                    System.out.println("Грешка при нулиране на артикулът във фактурата");
                    return null;
                }
        }
       // JOptionPane.showMessageDialog(null,"Кредитното известие е записано успешно");
        callback.call("OK");
        return null;
    }
}
