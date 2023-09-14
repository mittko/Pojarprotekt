package Reports;

import utils.MainPanel;
import utils.MyCallback;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreditNoteDialog extends MainPanel {

    private String client;
    private String invoice;
    public CreditNoteDialog(String client, String invoice, final MyCallback<String> callback) {
        this.client = client;
        this.invoice = invoice;
        BorderLayout rootLayout = new BorderLayout();
        setLayout(rootLayout);
        JPanel jPanel = new JPanel();
        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setVgap(20);
        jPanel.setLayout(borderLayout);
        JLabel titleLabel = new JLabel("Желаете ли да създадете кредитно известие за този документ ?");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        jPanel.add(titleLabel,BorderLayout.NORTH);
        JLabel clientLabel = new JLabel("Клиент:     " + client);
        clientLabel.setHorizontalAlignment(JLabel.CENTER);
        jPanel.add(clientLabel,BorderLayout.CENTER);
        JLabel invoiceLabel = new JLabel("Фактура:     " + invoice);
        invoiceLabel.setHorizontalAlignment(JLabel.CENTER);
        jPanel.add(invoiceLabel,BorderLayout.SOUTH);
        JPanel southPanel = new JPanel();

        southPanel.setLayout(new FlowLayout());
        JButton confirmButton = new JButton("Потвърди");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callback.call("Потвърди");
            }
        });

        JButton printButton = new JButton("Принтирай");

        printButton.setHorizontalAlignment(JButton.RIGHT);
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callback.call("Принтирай");
            }
        });
        southPanel.add(confirmButton,BorderLayout.WEST);
        southPanel.add(printButton,BorderLayout.EAST);
        this.add(jPanel,BorderLayout.NORTH);
        this.add(southPanel,BorderLayout.SOUTH);
    }
}
