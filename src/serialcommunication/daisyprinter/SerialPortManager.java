//package serialcommunication.daisyprinter;
//  RUN ON JAVA 7
//
//import gnu.io.*;
//import serialcommunication.datecsLP50.CommPortReceiver;
//import serialcommunication.datecsLP50.CommPortSender;
//import serialcommunication.datecsLP50.ProtocolImpl;
//
//import javax.swing.*;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.Enumeration;
//import java.util.TooManyListenersException;
//
//public class SerialPortManager {
//    private static boolean connected = false;
//    private static InputStream inputStream;
//    private static OutputStream outputStream;
//    private static SerialPort serialPort;
//    private final GetAnswer getAnswer;
//    public SerialPortManager(GetAnswer getAnswer){
//        this.getAnswer = getAnswer;
//    }
//    public void test(String test) {
//        CommPortSender.send(new ProtocolImpl().getMessage(test));
//    }
//
//    public boolean connectTo(String portName) {
//        CommPortIdentifier portIdentifier = null;
//        try {
//            portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
//        } catch (NoSuchPortException e) {
//            JOptionPane.showMessageDialog(null,
//                    e.getMessage(),"Not connected to " + portName,JOptionPane.ERROR_MESSAGE);
//            return false;
//        }
//
//        if (portIdentifier.isCurrentlyOwned()) {
//            System.out.println("Port in use!");
//            JOptionPane.showMessageDialog(null, "Port in use!","Error",JOptionPane.ERROR_MESSAGE);
//            return false;
//        } else {
//            // points who owns the port and connection timeout
//            serialPort = null;
//            try {
//                serialPort = (SerialPort) portIdentifier.open("RS232Example", 2000);
//            } catch (PortInUseException e) {
//                JOptionPane.showMessageDialog(null,
//                        e.getMessage(),"Port in use!",JOptionPane.ERROR_MESSAGE);
//                return false;
//            }
//            // setup connection parameters
//            try {
//                serialPort.setSerialPortParams(
//                        9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
//                inputStream = serialPort.getInputStream();
//                outputStream = serialPort.getOutputStream();
//                serialPort.addEventListener(new SerialReader(inputStream,getAnswer));
//                return true;
//            } catch (UnsupportedCommOperationException | IOException | TooManyListenersException e) {
//                JOptionPane.showMessageDialog(null,
//                        e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
//                return false;
//            }
//        }
//    }
//    public static boolean connect(String portName) {
//        CommPortIdentifier portIdentifier = null;
//        try {
//            portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
//        } catch (NoSuchPortException e) {
//            JOptionPane.showMessageDialog(null,e.getMessage());
//            e.printStackTrace();
//            return false;
//        }
//
//        if (portIdentifier.isCurrentlyOwned()) {
//            System.out.println("Port in use!");
//            JOptionPane.showMessageDialog(null,"\"Port in use!\"");
//            return false;
//        } else {
//            // points who owns the port and connection timeout
//            serialPort = null;
//            try {
//                serialPort = (SerialPort) portIdentifier.open("RS232Example", 2000);
//            } catch (PortInUseException e) {
//                JOptionPane.showMessageDialog(null,e.getMessage());
//                e.printStackTrace();
//                return false;
//            }
//            // setup connection parameters
//            try {
//                serialPort.setSerialPortParams(
//                        9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
//            } catch (UnsupportedCommOperationException e) {
//                JOptionPane.showMessageDialog(null,e.getMessage());
//                e.printStackTrace();
//                return false;
//            }
//            // setup serial port writer
//            try {
//                CommPortSender.setWriterStream(serialPort.getOutputStream());
//            } catch (IOException e) {
//                JOptionPane.showMessageDialog(null,e.getMessage());
//                e.printStackTrace();
//                return false;
//            }
//            // setup serial port reader
//            try {
//                new CommPortReceiver(serialPort.getInputStream()).start();
//            } catch (IOException e) {
//                JOptionPane.showMessageDialog(null,e.getMessage());
//                e.printStackTrace();
//                return false;
//            }
//            return connected = true;
//        }
//    }
//    public static void closePort() {
//        if (serialPort != null) {
//            serialPort.close();
//            serialPort = null;
//        }
//    }
//
//    public static void main(String[] args) throws Exception {
//
//    }
//    public static boolean isConnected() {
//        return connected;
//    }
//
//    public static void setConnected(boolean connected) {
//        SerialPortManager.connected = connected;
//    }
//    public static void list() {
//        Enumeration ports = CommPortIdentifier.getPortIdentifiers();
//           while (ports.hasMoreElements()) {
//                System.out.println(((CommPortIdentifier) ports.nextElement()).getName());
//            }
//    }
//    public static InputStream getInputStream() {
//        return inputStream;
//    }
//    public static OutputStream getOutputStream() {
//        return outputStream;
//    }
//}
