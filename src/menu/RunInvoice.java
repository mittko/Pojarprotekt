package menu;

import invoicewindow.ArtikulTab;
import invoicewindow.InvoiceFrame;
import invoicewindow.SearchFromProformTab;
import invoicewindow.SearchFromProtokolTab;
import run.JDialoger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RunInvoice implements Runnable {

    private String invoiceNumber = null;
    private String proformNumber = null;

    private String protokolNumber;
    private JFrame jf = null;

    public RunInvoice(String invoiceNumber, String proformNumber,String protokolNumber, JFrame jf) {
        this.invoiceNumber = invoiceNumber;
        this.proformNumber = proformNumber;
        this.protokolNumber = protokolNumber;
        this.jf = jf;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        if(jf != null) {
            jf.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        if (invoiceNumber != null) {

            final InvoiceFrame invoice = new InvoiceFrame(protokolNumber);
            final JDialoger jDialog = new JDialoger();
            jDialog.setTitle("Фактури / Проформи / Стокови разписки");
            jDialog.setContentPane(invoice);
            jDialog.setResizable(false);
            jDialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent we) {
                    if (SearchFromProtokolTab.invoiceTableModel
                            .getRowCount() > 0
                            || SearchFromProformTab.proformTableModel
                            .getRowCount() > 0
                            || ArtikulTab.dftm.getRowCount() > 0) {
                        int yes_no = JOptionPane
                                .showOptionDialog(
                                        null,
                                        "Наистина ли искате да затворите прозореца ?",
                                        "ПОЖАРПРОТЕКТ",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE, null,
                                        new String[] { "Да", "Не" },
                                        "default");
                        if (yes_no == 0) {
                            jDialog.dispose();
                        } else {
                            jDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                        }
                    } else {
                        jDialog.dispose();
                    }
                }
            });
            jDialog.Show();
        }

    }

}