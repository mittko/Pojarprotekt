package serialcommunication;


import serialcommunication.daisyprinter.GetAnswer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;

public class SerialDeviceUtility implements GetAnswer {

    JPanel jPanel = new JPanel();
    JTextArea jTextArea = new JTextArea();
    JButton send = new JButton("send");
    JButton connect = new JButton("connect");
    JComboBox<String> jComboBox = new JComboBox<>(new String[]{"COM 1","COM 2","COM 3","COM 4","COM 5",
            "COM 6","COM 7","COM 8","COM 9","COM 10"});
    JPanel bottomPanel = new JPanel();
    public SerialDeviceUtility() {
        final SerialPortManager serialPortManager =
                new SerialPortManager(this);
        jComboBox.setSelectedItem(jComboBox.getItemAt(0));
        jPanel.setLayout(new BorderLayout());
        jTextArea.setPreferredSize(new Dimension(400,100));
       // send.setPreferredSize(new Dimension(200,50));
        send.addActionListener(new ActionListener() {
                                      @Override
                                      public void actionPerformed(ActionEvent e) {
                                        /*  try {
                                              sendMessage(DaisyCommands.statusFiscalDevice);
                                          } catch (IOException ioException) {
                                              System.out.println(ioException.getMessage());
                                          }*/
                                      }
                                  }
        );
        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jComboBox.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Не е избран порт !");
                    return;
                }
                boolean isConnected = serialPortManager.connectTo(jComboBox.getSelectedItem().toString());
                System.out.println("connected " + isConnected);
                if(isConnected) {
                   JOptionPane.showMessageDialog(null,"Success connected to " +
                           jComboBox.getSelectedItem().toString());
                } else {
                    JOptionPane.showMessageDialog(null,"not connected to " +
                            jComboBox.getSelectedItem().toString(),"Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        jPanel.add(jTextArea,BorderLayout.NORTH);
        jPanel.add(jComboBox,BorderLayout.CENTER);
        bottomPanel.add(connect);
        bottomPanel.add(send);
        jPanel.add(bottomPanel,BorderLayout.SOUTH);
    }

    public void createUI() {
        JFrame jFrame = new JFrame();
        jFrame.add(jPanel);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        SerialDeviceUtility daisyProtocolSender =
                new SerialDeviceUtility();
        daisyProtocolSender.createUI();
        int sum = 0x24 + 0x50 + 0x4a + 0x05;
        String hex = Integer.toHexString(sum);
        System.out.println(sum);
        System.out.println(hex);
    }

    private void sendMessage( byte[] commands) throws IOException {
        if(SerialPortManager.getOutputStream() == null) {
            JOptionPane.showMessageDialog(null,"not connected to " +
                    jComboBox.getSelectedItem().toString(),"Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        OutputStream outputStream = SerialPortManager.getOutputStream();
        outputStream.write(commands);
        outputStream.flush();
    //    outputStream.close();
    }

    @Override
    public void showAnswer(String answer) {
        jTextArea.setText(answer);
    }
}
