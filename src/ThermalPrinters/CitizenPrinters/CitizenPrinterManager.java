package ThermalPrinters.CitizenPrinters;

import admin.Artikul.AddArtikulDialog;
import mydate.MyGetDate;

import javax.print.*;
import javax.print.attribute.HashAttributeSet;
import javax.print.attribute.standard.PrinterName;
import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CitizenPrinterManager {

   // this class communicate with thermal printer as send bytes in hexadecimal format

   private final byte SOX = 0x01; /*System level command*/
   private final byte STX = 0x02;/*System level command*/
    private byte Z = 'Z';// printer status
   private final char L = 'L'; /*STX + L switch from system level interpreter to label format interpreter*/
   private final char E = 'E';/*when label format ends with E defined data is printed and system level interpreter is started*/
  /*  Label format commands follow System level  commands STX and L
   and ends with CR*/
   private final byte CR = 0x0D; /*Carriage Return*/
    private final char n = 'n'; /*changing units from metric to inch system*/
   /*add 48 to every value to get real represanttion of char
   when add it to bytearray output stream*/
   private static final byte patch = 48;

   private final byte horizontalAngle = 1; // 0 degrees horizontal
     private final byte verticalUpwardAngle = 2;// 90 degrees vertical upward
    private final byte reversedUpwardAngle = 3;// 180 degrees reversed upward
    private final byte verticalDownwardAngle = 4;// 270 degrees vertical downward
   private static byte printingAngle = (byte) (1 + patch);
   private static byte fontSelectionNumber = (byte)(6 + patch);
   private static byte horizontalDirectionExpansionRate = (byte)(1 + patch);
   private static byte verticalDirectionExpansionRate = (byte)(1 + patch);
   private static byte[] expansionFontSelection = new byte[]{patch,
           (byte)(3 + patch), patch};
   private static byte[] rowAddress = new byte[]{patch,
           patch,(byte)(2 + patch), patch};
   private static byte[] columnAddress = new byte[]{patch,
           patch,(byte)(4 + patch), patch};
    private static byte[] barCodeHeight = new byte[]{patch,
            (byte)(2 + patch), patch};

   private static String stringPixelSize = "D";
   private static byte barcodeEAN13 = 'F';// EAN13

   private static byte barcodeUPC_2DIG_ADD = 'M';
   private static byte wideBarWidth = (byte)(3 + patch);//4
   private static byte narrowBarWidth = (byte) (4 + patch);

   public static void setPixelSize(int newSize) {
       stringPixelSize = stringPixelSize + newSize ;
   }

   public static void setPrintingAngle(byte newAngle) {
      printingAngle = (byte)(newAngle + patch);
   }

   public static void setFontSelectionNumber(byte newFontSelectionNumber) {
      CitizenPrinterManager.fontSelectionNumber = (byte)(newFontSelectionNumber + patch);
   }


   public static void setRowAddress(byte[] rowAddress) {
      CitizenPrinterManager.rowAddress = new byte[]{
              (byte)(rowAddress[0]+patch),(byte)(rowAddress[1]+patch),
              (byte)(rowAddress[2]+patch),(byte) (rowAddress[3]+patch)};
   }

   public static void setColumnAddress(byte[] newAddress) {
       columnAddress = new byte[]{
               (byte)(newAddress[0]+patch),(byte)(newAddress[1]+patch),
               (byte)(newAddress[2]+patch),(byte) (newAddress[3]+patch)};
   }



   public static void main(String[] args) throws IOException {
//       // sending bytes to IP : PORT also works !!!
//      Printer printer = new NetworkPrinter("192.168.1.111", 9100);
//      PrinterService printerService = new PrinterService(printer);
//      printerService.write(byteArrayOutputStream.toByteArray());
//
//      printerService.close();

       CitizenPrinterManager citizenPrinterManager =
               new CitizenPrinterManager();
     //  citizenPrinterManager.printPrinterStatus();
       citizenPrinterManager.printBarcodeAndCharacterPrinting("333333333333","05.06.2022",
               "05.06.2022", "05.06.2022","Georgi Ильов","1");
   }


   public void printBarcodeAndCharacterPrinting(String barcode, String toDate, String pDate, String hiDate, String tehnik
   , String helpDigit) {
       if(barcode.length() != 12) {
           System.out.println("barcode lenght should be equal to 12");
           return;
       }
       // should add setPixelSize(?)
       setPixelSize(10);//10
       setFontSelectionNumber((byte)3);
       String currDate = MyGetDate.getReversedSystemDate();
       System.out.println("DATE = " + currDate);
       PrintService printService;
       String printerName = "Citizen CL-S631";
       HashAttributeSet attributeSet = new HashAttributeSet();
       attributeSet.add(new PrinterName(printerName, null));
       PrintService[] services = PrintServiceLookup.lookupPrintServices(null, attributeSet);
       if (services.length == 0) {
           throw new IllegalArgumentException("Printer not found.");
       } else if (services.length > 1) {
           System.out.println("Found more than one printer. Only the first printer will be used.");
       }
       printService = services[0];
       System.out.println("Printer found: "+printService.getName());
       try {
           DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
           ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
           byteArrayOutputStream.write(STX);
           byteArrayOutputStream.write(n);
           byteArrayOutputStream.write(STX);
           byteArrayOutputStream.write(L);/* STX + L changes to label format*/
           byteArrayOutputStream.write(stringPixelSize.getBytes());

           ///////// CHARACTERS PRINTING ///////////////////////////////////
       //    setPrintingAngle(verticalUpwardAngle);

           DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
           String dateAsString = "";
           if(!toDate.isEmpty()) {
              dateAsString = toDate;
           }
           if(!pDate.isEmpty()) {
              dateAsString = pDate;
           }
           if(!hiDate.isEmpty())  {
              dateAsString = hiDate;
           }
           try {
               Date date1 = format.parse(dateAsString);
               System.out.println("tehnichesko " + date1);

      /*hak*/         setRowAddress(new byte[]{0, 0, 0, 0});   // move on top/down on portrait model
               setColumnAddress(new byte[]{0, 0, 0, 0});// move on left/right on portrait model
               byteArrayOutputStream.write("".getBytes());
               byteArrayOutputStream.write(CR);


                   if(!toDate.isEmpty()) {
                       System.out.println("TO");
                       setPixelSize(12);//10
                       setFontSelectionNumber((byte) 4);

                       setRowAddress(new byte[]{0, 1, 4, 7});
                       setColumnAddress(new byte[]{0, 2, 3, 0});
                       byteArrayOutputStream.write(constructCharacterDataToSend("X").getBytes());
                       byteArrayOutputStream.write(CR);
                   }


                   if(!pDate.isEmpty()) {
                       System.out.println("P");
                       setPixelSize(12);//10
                       setFontSelectionNumber((byte) 4);

                       setRowAddress(new byte[]{0, 1, 2, 5});
                       setColumnAddress(new byte[]{0, 2, 3, 0});
                       byteArrayOutputStream.write(constructCharacterDataToSend("X").getBytes());
                       byteArrayOutputStream.write(CR);
                   }

                   if(!hiDate.isEmpty())  {
                       System.out.println("HI");
                       setPixelSize(12);//10
                       setFontSelectionNumber((byte)4);

                   setRowAddress(new byte[]{0, 1, 0, 2});
                   setColumnAddress(new byte[]{0, 2, 3, 0});
                   byteArrayOutputStream.write(constructCharacterDataToSend("X").getBytes());
                   byteArrayOutputStream.write(CR);
                 }


               setPixelSize(10);//10
               setFontSelectionNumber((byte)3);

               // ДАТА НА СЛЕДВАЩО ТЕХНИЧЕСКО ОБСЛУЖВАНЕ
               // ДАТА ОТЛЯВО
               setRowAddress(new byte[]{0, 0, 5, 3});   // move on top/down on portrait model //{0, 3, 1, 5}
               setColumnAddress(new byte[]{0, 0, 3, 5});// move on left/right on portrait mode //{0, 0, 2, 5}
               byteArrayOutputStream.write(constructCharacterDataToSend(currDate).getBytes());
               byteArrayOutputStream.write(CR);

               // ДАТА ОТДЯСНО
               setRowAddress(new byte[]{0, 0, 5, 3});   // move on top/down on portrait model //{0, 3, 1, 5}
               setColumnAddress(new byte[]{0, 1, 5, 0});// move on left/right on portrait mode // {0, 1, 5, 0}
               byteArrayOutputStream.write(constructCharacterDataToSend(dateAsString).getBytes());
               byteArrayOutputStream.write(CR);


               // БАРКОД ТЕХНИЧЕСКО ОБСЛУЖВАНЕ
               setPrintingAngle(horizontalAngle);
               setRowAddress(new byte[]{0, 0, 1, 4});   // move on top/down on portrait model
               setColumnAddress(new byte[]{0, 0, 5, 2});// move on left/right on portrait mode
               byteArrayOutputStream.write( (constructBarcodeEAN13ToSend(barcode)).getBytes());
               byteArrayOutputStream.write(CR);

               setPrintingAngle(horizontalAngle);
               setRowAddress(new byte[]{0, 0, 1, 4});   // move on top/down on portrait model
               setColumnAddress( new byte[]{0, 2, 0, 2});// move on left/right on portrait mode
               byteArrayOutputStream.write(constructCharacterDataToSend(helpDigit).getBytes());
               byteArrayOutputStream.write(CR);

//               setPrintingAngle(horizontalAngle);
//               setRowAddress(new byte[]{0, 2, 7, 3});   // move on top/down on portrait model
//               setColumnAddress( new byte[]{0, 1, 9,8});// move on left/right on portrait mode
//               byteArrayOutputStream.write(constructBarcodeUPC2DIG_ADD("12").getBytes());
//               byteArrayOutputStream.write(CR);
           } catch (ParseException e) {
               System.out.println(e.getMessage());
           }


/*           try {
               Date date1 = format.parse(pDate);
               System.out.println("prezarevdane " + date1);

               setRowAddress(new byte[]{0, 0, 0, 0});   // move on top/down on portrait model
               setColumnAddress(new byte[]{0, 0, 0, 0});// move on left/right on portrait model
               byteArrayOutputStream.write("".getBytes());
               byteArrayOutputStream.write(CR);

               // ДАТА НА СЛЕДВАЩО ПРЕЗАРЕЖДАНЕ
               // ДАТА ОТЛЯВО
               setRowAddress(new byte[]{0, 1, 8, 8});   // move on top/down on portrait model
               setColumnAddress(new byte[]{0, 0, 2, 5});// move on left/right on portrait mode
               byteArrayOutputStream.write(constructCharacterDataToSend(currDate).getBytes());
               byteArrayOutputStream.write(CR);

               // ДАТА ОТДЯСНО
               setRowAddress(new byte[]{0, 1, 8, 8});   // move on top/down on portrait model
               setColumnAddress(new byte[]{0, 1, 5, 0});// move on left/right on portrait mode
               byteArrayOutputStream.write(constructCharacterDataToSend(pDate).getBytes());
               byteArrayOutputStream.write(CR);

               // БАРКОД ПРЕЗАРЕЖДАНЕ !!!
               setRowAddress(new byte[]{0, 1, 4, 4});   // move on top/down on portrait model
               setColumnAddress(new byte[]{0, 0, 4, 2});// move on left/right on portrait mode
               byteArrayOutputStream.write((constructBarcodeEAN13ToSend(barcode)).getBytes());//"1F6405000200040111111111111".getBytes());
               byteArrayOutputStream.write(CR); *//*data for printing data format is delimeted by A CR and then transmitted*//*

               setPrintingAngle(horizontalAngle);
               setRowAddress(new byte[]{0, 1, 4, 4});   // move on top/down on portrait model
               setColumnAddress( new byte[]{0, 1, 9, 2});// move on left/right on portrait mode
               byteArrayOutputStream.write(constructCharacterDataToSend("2").getBytes());
               byteArrayOutputStream.write(CR);

           } catch (ParseException e) {
               System.out.println(e.getMessage());
           }*/


         /*  try {
               Date date1 = format.parse(hiDate);
               System.out.println("hidrostaticno izpitwane " + date1);

               setRowAddress(new byte[]{0, 0, 0, 0});   // move on top/down on portrait model
               setColumnAddress(new byte[]{0, 0, 0, 0});// move on left/right on portrait model
               byteArrayOutputStream.write("".getBytes());
               byteArrayOutputStream.write(CR);


               //  ДАТА НА СЛЕДВАЩО ХИДРОСТАТИЧНО ИЗПИТВАНЕ
               //  ДАТА ОТЛЯВО
               setRowAddress(new byte[]{0, 0, 6, 0});   // move on top/down on portrait model
               setColumnAddress(new byte[]{0, 0, 2, 5});// move on left/right on portrait mode
               byteArrayOutputStream.write(constructCharacterDataToSend(currDate).getBytes());
               byteArrayOutputStream.write(CR);

               // ДАТА ОТДЯСНО
               setRowAddress(new byte[]{0, 0, 6, 0});   // move on top/down on portrait model
               setColumnAddress(new byte[]{0, 1, 5, 0});// move on left/right on portrait mode
               byteArrayOutputStream.write(constructCharacterDataToSend(hiDate).getBytes());
               byteArrayOutputStream.write(CR);

               // БАРКОД ХИДРОСТАТИЧНО ИЗПИТВАНЕ



               setPrintingAngle(horizontalAngle);
               setRowAddress(new byte[]{0, 0, 1, 6});   // move on top/down on portrait model
               setColumnAddress(new byte[]{0, 0, 4, 2});// move on left/right on portrait mode
               byteArrayOutputStream.write( (constructBarcodeEAN13ToSend(barcode)).getBytes());
               byteArrayOutputStream.write(CR);

//               setPrintingAngle(printingAngle);
//               setRowAddress(new byte[]{0, 0, 1, 8});   // move on top/down on portrait model
//               setColumnAddress( new byte[]{0, 2, 0, 3});// move on left/right on portrait mode
//               byteArrayOutputStream.write(constructBarcodeUPC2DIG_ADD("12").getBytes());
//               byteArrayOutputStream.write(CR);


               setPrintingAngle(horizontalAngle);
               setRowAddress(new byte[]{0, 0, 1, 6});   // move on top/down on portrait model
               setColumnAddress( new byte[]{0, 1, 9, 2});// move on left/right on portrait mode
               byteArrayOutputStream.write(constructCharacterDataToSend("3").getBytes());
               byteArrayOutputStream.write(CR);

           } catch (ParseException e) {
               System.out.println(e.getMessage());
           }*/


           byteArrayOutputStream.write(E);/*changing to the system level interpreter from label format interpreter*/
           byteArrayOutputStream.close();
           SimpleDoc doc = new SimpleDoc(byteArrayOutputStream.toByteArray(), flavor, null);
           DocPrintJob job = printService.createPrintJob();
           job.print(doc, null);
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
    private static String constructCharacterDataToSend(String characters) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream =
                new ByteArrayOutputStream();
        byteArrayOutputStream.write(printingAngle);
        byteArrayOutputStream.write(fontSelectionNumber);
        byteArrayOutputStream.write(horizontalDirectionExpansionRate);
        byteArrayOutputStream.write(verticalDirectionExpansionRate);
        byteArrayOutputStream.write(expansionFontSelection);
        byteArrayOutputStream.write(rowAddress);
        byteArrayOutputStream.write(columnAddress);
        byteArrayOutputStream.write(characters.getBytes());
        //for(int i = 0;i < byteArrayOutputStream.toByteArray().length;i++) {
        //   System.out.println(byteArrayOutputStream.toByteArray()[i]);
        //}
        return byteArrayOutputStream.toString("UTF-8");
    }

    private static String constructBarcodeEAN13ToSend(String barcode) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream =
                new ByteArrayOutputStream();
        byteArrayOutputStream.write(printingAngle);
        byteArrayOutputStream.write(barcodeEAN13);
        byteArrayOutputStream.write(wideBarWidth);
        byteArrayOutputStream.write(narrowBarWidth);
        byteArrayOutputStream.write(barCodeHeight);
        byteArrayOutputStream.write(rowAddress);
        byteArrayOutputStream.write(columnAddress);
        byteArrayOutputStream.write(barcode.getBytes());
//        for(int i = 0;i < byteArrayOutputStream.toByteArray().length;i++) {
//           System.out.println(byteArrayOutputStream.toByteArray()[i]);
//        }
        return byteArrayOutputStream.toString("UTF-8");
    }
    private static String constructBarcodeUPC2DIG_ADD(String barcode) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream =
                new ByteArrayOutputStream();
        byteArrayOutputStream.write(printingAngle);
        byteArrayOutputStream.write(barcodeUPC_2DIG_ADD);
        byteArrayOutputStream.write(wideBarWidth);
        byteArrayOutputStream.write(narrowBarWidth);
        byteArrayOutputStream.write(barCodeHeight);
        byteArrayOutputStream.write(rowAddress);
        byteArrayOutputStream.write(columnAddress);
        byteArrayOutputStream.write(barcode.getBytes());
//        for(int i = 0;i < byteArrayOutputStream.toByteArray().length;i++) {
//           System.out.println(byteArrayOutputStream.toByteArray()[i]);
//        }
        return byteArrayOutputStream.toString("UTF-8");
    }
    public static void setBarCodeHeight(byte[] newBarCodeHeight) {
        barCodeHeight =
                new byte[]{(byte)(newBarCodeHeight[0]+patch),(byte)(newBarCodeHeight[1]+patch)
                        ,(byte)(newBarCodeHeight[2]+patch)};
    }

    public static void setBarcodeEAN13(byte newNumber) {
        barcodeEAN13 = newNumber;
    }

    public static void setWideBarWidth(byte newWideBarWidth) {
        wideBarWidth = (byte)(newWideBarWidth + patch);
    }

    public static void setNarrowBarWidth(byte newNarrowBarWidth) {
        narrowBarWidth = (byte)(newNarrowBarWidth + patch);
    }
    public static void setHorizontalDirectionExpansionRate(byte newHorizontalDirectionExpansionRate) {
        horizontalDirectionExpansionRate = (byte)(newHorizontalDirectionExpansionRate + patch);
    }

    public static void setVerticalDirectionExpansionRate(byte newVerticalDirectionExpansionRate) {
        verticalDirectionExpansionRate = (byte)(verticalDirectionExpansionRate + patch);
    }

    public static void setExpansionFontSelection(byte[] newExpansionFontSelection) {
        expansionFontSelection =
                new byte[]{(byte)(newExpansionFontSelection[0]+patch),(byte)(newExpansionFontSelection[1]+patch),
                        (byte)(newExpansionFontSelection[2]+patch)};
    }
   public void barcodePrinting(String barcode, String printerName) {
       if(barcode.length() != 12) {
           JOptionPane.showMessageDialog(null,"barcode length should be equal to 12");
           return;
       }
       // should add setPixelSize(?)
       setPixelSize(11);
       PrintService printService = null;
       HashAttributeSet attributeSet = new HashAttributeSet();
       attributeSet.add(new PrinterName(printerName, null));
       PrintService[] services = PrintServiceLookup.lookupPrintServices(null, attributeSet);
       if (services.length == 0) {
           JOptionPane.showMessageDialog(null,"Printer not found !");
           return;
       } else if (services.length > 1) {
           System.out.println("Found more than one printer. Only the first printer will be used.");
       }
       printService = services[0];
       System.out.println("Printer found: "+printService.getName());
       try {
           DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
           ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
           byteArrayOutputStream.write(STX);
           byteArrayOutputStream.write(n);
           byteArrayOutputStream.write(STX);
           byteArrayOutputStream.write(L);/* STX + L changes to label format*/
           byteArrayOutputStream.write(stringPixelSize.getBytes());
           byteArrayOutputStream.write(constructBarcodeEAN13ToSend(barcode).getBytes());//"1F6405000200040111111111111".getBytes());
           byteArrayOutputStream.write(CR); /*data for printing data format is delimeted by A CR and then transmitted*/
           byteArrayOutputStream.write(E);/*changing to the system level interpreter from label format interpreter*/
           byteArrayOutputStream.close();
           SimpleDoc doc = new SimpleDoc(byteArrayOutputStream.toByteArray(), flavor, null);
           DocPrintJob job = printService.createPrintJob();
           job.print(doc, null);
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
   public void characterPrinting(String characters, String printerName) {
      PrintService printService;
      HashAttributeSet attributeSet = new HashAttributeSet();
      attributeSet.add(new PrinterName(printerName, null));
      PrintService[] services = PrintServiceLookup.lookupPrintServices(null, attributeSet);
      if (services.length == 0) {
        JOptionPane.showMessageDialog(null,"Printer not found !");
        return;
      } else if (services.length > 1) {
         System.out.println("Found more than one printer. Only the first printer will be used.");
      }
      printService = services[0];
      System.out.println("Printer found: "+printService.getName());
      try {
         DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
         byteArrayOutputStream.write(STX);
         byteArrayOutputStream.write(n);
         byteArrayOutputStream.write(STX);
         byteArrayOutputStream.write(L);/* STX + L changes to label format*/
          // should add setPixelSize(?)
         byteArrayOutputStream.write(stringPixelSize.getBytes());
         byteArrayOutputStream.write(constructCharacterDataToSend(characters).getBytes());
         byteArrayOutputStream.write(CR); /*data for printing data format is delimeted by A CR and then transmitted*/
         byteArrayOutputStream.write(E);/*changing to the system level interpreter from label format interpreter*/
     //    byteArrayOutputStream.write(CR);
         byteArrayOutputStream.close();

         //  String _ESC_P_Code ="02 L E CR";
         SimpleDoc doc = new SimpleDoc(byteArrayOutputStream.toByteArray(), flavor, null);
         DocPrintJob job = printService.createPrintJob();
         job.print(doc, null);
       //   byteArrayOutputStream.close();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   public void printPrinterStatus(String printerName) {
        PrintService printService = null;
        HashAttributeSet attributeSet = new HashAttributeSet();
        attributeSet.add(new PrinterName(printerName, null));
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, attributeSet);
        if (services.length == 0) {
            JOptionPane.showMessageDialog(null,"Printer not found !");
            return;
        } else if (services.length > 1) {
            System.out.println("Found more than one printer. Only the first printer will be used.");
        }
        printService = services[0];
        System.out.println("Printer found: "+printService.getName());
        try {
            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(STX);
            byteArrayOutputStream.write(Z);
            byteArrayOutputStream.write(CR);
            byteArrayOutputStream.close();
            SimpleDoc doc = new SimpleDoc(byteArrayOutputStream.toByteArray(), flavor, null);
            DocPrintJob job = printService.createPrintJob();
            job.print(doc, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
