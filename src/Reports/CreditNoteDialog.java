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
    public CreditNoteDialog(String client, String invoice,final String title, final MyCallback<Object> callback) {
        this.client = client;
        this.invoice = invoice;
        BorderLayout rootLayout = new BorderLayout();
        setLayout(rootLayout);
        JPanel jPanel = new JPanel();
        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setVgap(20);
        jPanel.setLayout(borderLayout);
        JLabel titleLabel = new JLabel(title);
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
        JButton okButton = new JButton("Да");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComponent comp = (JComponent) e.getSource();
                Window win = SwingUtilities.getWindowAncestor(comp);
                win.dispose();

                callback.call("Да");
            }
        });

        JButton closeButton = new JButton("Отмени");

        closeButton.setHorizontalAlignment(JButton.RIGHT);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // close JPanel (Window)
                JComponent comp = (JComponent) e.getSource();
                Window win = SwingUtilities.getWindowAncestor(comp);
                win.dispose();
               // callback.call("Отмени");
            }
        });
        southPanel.add(okButton,BorderLayout.WEST);
        southPanel.add(closeButton,BorderLayout.EAST);
        this.add(jPanel,BorderLayout.NORTH);
        this.add(southPanel,BorderLayout.SOUTH);
    }
}
