package admin.аrtikul;

import Exceptions.DBException;
import admin.аrtikul.Workers.RenameArtikulNameWorker;
import run.JustFrame;
import utils.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RenameArtikulNameDialog extends MainPanel {

    private String oldArtikulName;
    public RenameArtikulNameDialog(final String oldArtikulName) {
        this.oldArtikulName = oldArtikulName;
        final JTextField oldNameTextField = new JTextField(20);
        oldNameTextField.setText(oldArtikulName);
        oldNameTextField.setEditable(false);
        JLabel oldNameLabel = new JLabel("Артикул");

        JLabel newNameLabel = new JLabel("Преименувай");
        final JTextField newNameJTextField = new JTextField(40);
        newNameJTextField.setText(oldArtikulName);

        JButton saveButton = new JButton("Запиши");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RenameArtikulNameWorker renameArtikulNameWorker =
                        new RenameArtikulNameWorker(oldNameTextField.getText(),
                                newNameJTextField.getText());
                try {
                    boolean renameSuccess =  renameArtikulNameWorker.doInBackground();
                    if(renameSuccess) {
                        JOptionPane.showMessageDialog(null,"Артикулът е преименуван успешно !");
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Неуспешна операция !","Error",JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception exception) {
                    DBException.DBExceptions("Error",exception);
                }
            }
        });
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.gridwidth = 1;
        gbc1.insets = new Insets(0, 0, 0, 0);
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.gridx = 1;
        gbc2.gridy = 0;
        gbc2.gridwidth = 1;
        gbc2.insets = new Insets(0, 0, 0, 0);
        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.fill = GridBagConstraints.HORIZONTAL;
        gbc3.gridx = 0;
        gbc3.gridy = 1;
        gbc3.gridwidth = 1;
        gbc3.insets = new Insets(0, 0, 0, 0);
        GridBagConstraints gbc4 = new GridBagConstraints();
        gbc4.fill = GridBagConstraints.HORIZONTAL;
        gbc4.gridx = 1;
        gbc4.gridy = 1;
        gbc4.gridwidth = 1;
        gbc4.insets = new Insets(0, 0, 0, 0);
        GridBagConstraints gbc5 = new GridBagConstraints();
        gbc5.fill = GridBagConstraints.HORIZONTAL;
        gbc5.gridx = 0;
        gbc5.gridy = 2;
        gbc5.gridwidth = 1;
        gbc5.insets = new Insets(0, 0, 0, 0);
        this.add(oldNameLabel,gbc1);
        this.add(oldNameTextField,gbc2);
        this.add(newNameLabel, gbc3);
        this.add(newNameJTextField,gbc4);
        this.add(saveButton,gbc5);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                UIManager.put("swing.boldMetal", Boolean.FALSE);
                JustFrame start = new JustFrame(new RenameArtikulNameDialog("артикул"));
                start.pack();
                start.setResizable(false);
                start.setFrameLocationOnTheCenter();

            }

        });
    }
}
