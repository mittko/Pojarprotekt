package Reports;

import utils.MainPanel;
import utils.MyCallback;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreditNoteDialog extends MainPanel {

    public CreditNoteDialog(final MyCallback<String> callback) {
        setLayout(new BorderLayout());
        JPanel jPanel = new JPanel();
        jPanel.add(new JLabel("�������� �������"));
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.LEFT,20,5));
        JButton confirmButton = new JButton("��������");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callback.call("��������");
            }
        });
        JButton printButton = new JButton("���������");
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callback.call("���������");
            }
        });
        southPanel.add(confirmButton);
        southPanel.add(new JLabel("                "),BorderLayout.CENTER);
        southPanel.add(printButton);
        this.add(jPanel,BorderLayout.NORTH);
        this.add(southPanel,BorderLayout.SOUTH);
    }
}
