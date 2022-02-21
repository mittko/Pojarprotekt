package serialcommunication.DatecsLP50;


import static serialcommunication.SerialPortManager.connect;

public class DatecsLP50SerialPrinter {
    private final String deleteForm = "FK\"Test\"";
    private final String startNewForm = "FS\"Test\"";
    private final String createBarcodeEAN13 = "B0,0,0,E30,3,3,100,B,";
    private final String endForm =  "FE";
    private final String loadForm = "FR\"Test\"";
    private final String printLabel = "P1,1";
    private static final String COM_PORT = "COM 1";
    public void sendMessage(String barcode) {
        CommPortSender.send(new ProtocolImpl().getMessage(deleteForm));
        String createNewForm = startNewForm+"\n" +
                createBarcodeEAN13 +  "\""+barcode+"\""+"\n"  + endForm;
        CommPortSender.send(new ProtocolImpl().getMessage(createNewForm));
        CommPortSender.send(new ProtocolImpl().getMessage(loadForm));
        CommPortSender.send(new ProtocolImpl().getMessage(printLabel));

    }
    public static void main(String[] args) {
        DatecsLP50SerialPrinter datecsLP50SerialPrinter =
                new DatecsLP50SerialPrinter();
// connects to the port which name (e.g. COM1) is in the first argument
        connect("COM2");
        datecsLP50SerialPrinter.sendMessage("123456789012");
        // if stream is not bound in.read() method returns -1
    }
}
