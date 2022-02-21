/*package PDF.Protokol;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.TreeMap;

import javax.swing.table.DefaultTableModel;

import CommonResources.MainPanel;
import Date.GetDate;
import Exceptions.InOutException;
import Exceptions.PDFException;
import Log.IOErrorsWriter;
import Log.PdfErr;
import PDF.MyPDFEventHandler;
import PDF.PdfCreator;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class Protokol2815 extends PdfCreator{
	
	private float endY = 0; 
	private String Date = null;
	private float tablePos = 690;
	private float nextTablePos = 690;
	
	float sumOfRows = 0;
	
    public Protokol2815()  {
		super();
		Date = GetDate.getReversedSystemDate();
		// TODO Auto-generated constructor stub
	}

       public boolean createProtocol(DefaultTableModel dm,TreeMap<Object,Integer> PARTS
    		   ,String[] clData,String protokolNumber,int startIndex,int endIndex) throws DocumentException {
	
		
		Document document = new Document(PageSize.A4,50,50,50,50);
		PdfWriter writer = null;
		try {
			 writer = PdfWriter.getInstance(document, 
					new FileOutputStream(MainPanel.PROTOKOL_PDF_PATH +"\\Protokol2815-"+protokolNumber+".pdf"));
			 writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
			 
			 MyPDFEventHandler pdfEventHandler = new MyPDFEventHandler();
			 writer.setPageEvent(pdfEventHandler);
			 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			InOutException.showIOException(e);
			IOErrorsWriter.writeIO(e.toString());
			e.printStackTrace();
			return false;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			PDFException.showPDFException(e);
	         PdfErr.pdfErros(e.toString());
			e.printStackTrace();
	         return false;
		}
	     document.open();
	     
	     PdfContentByte pcb = writer.getDirectContent();
	     pcb.beginText();
	     pcb.moveText(360,820);
	     pcb.setFontAndSize(getBaseFont("arial"), 8); 
	     pcb.showText("������� No: I�-2815, ���������� No: 8 ��� ��. 40 ��. 6"); // ����� �� ���� �����
	     pcb.endText();
	     
	     
	     pcb.beginText();
	     pcb.moveText(160, 790);
	     pcb.setFontAndSize(getBaseFont("arial"), 14);
	     pcb.showText("�������� No: "+protokolNumber);// TO DO 
	     pcb.endText();
	     
	     pcb.beginText();
	     pcb.moveText(60, 770);
	     pcb.setFontAndSize(getBaseFont("arial"), 9);
	     pcb.showText("�� ��������� � �������� �� ��������������, �� ����� � " +
	     		"��������� ���������� ����������, ������������"); // �� ����� ����
	    pcb.endText();
	  
	    
	    pcb.beginText();
	     pcb.moveText(90, 755);
	     pcb.setFontAndSize(getBaseFont("arial"), 9);
	     pcb.showText(" ��� ������������� ���������" +
	     		" (� ���������� ��� ���������)"); // �� ����� ����
	    pcb.endText();
	    
	    pcb.beginText();
	     pcb.moveText(65, 730);
	     pcb.setFontAndSize(getBaseFont("arial"), 9);
	     pcb.showText("���� "+Date+" � ��. �������, ������������� (�������������� ������������)  " +
	     		" " + MainPanel.personName + " "); // ����� �� ���� �����
	    pcb.endText();
	    
	    pcb.beginText();
	     pcb.moveText(60, 720);
	     pcb.setFontAndSize(getBaseFont("arial"), 9);
	     pcb.showText("��  "
	     		+  MainPanel.SALLER_NAME + 
	     		" " + MainPanel.SALLER_CITY + " " +
	     		MainPanel.SALLER_ADDRESS + " ��� : " +
	     		MainPanel.SALLER_EIK + " " + " ��� 0886 39 00");
	    pcb.endText(); // ���� �� ���� �����
	    
	    pcb.beginText();
	     pcb.moveText(60, 710);
	     pcb.setFontAndSize(getBaseFont("arial"), 9);
	     pcb.showText(", ������� �� ����������� (��� ����� ������������) ���������������� �� ����� � ��������� ����������, ����� ������.");
	    pcb.endText(); // ����� �� ���� �����
	    
	    // create table 
	    PdfPTable table = null;//new PdfPTable(11);
	 //   table.setSplitLate(false);
	    
	   int from = 0;
	//   int to = 0;
	    
	    PdfPCell n = new PdfPCell(new Phrase("No: �� ���",cyrylic));
	    PdfPCell mark = new PdfPCell(new Phrase("���������������� ���������� �� ����� �������������" +
	    		" (�����, �����,������ ����� � ��)",cyrylic));
	    PdfPCell category = new PdfPCell(new Phrase("��������� �������� �. 4.3.2.2 �� ��� " +
	    		"ISO 11602-2:2002",cyrylic));
	    PdfPCell wheight = new PdfPCell(new Phrase("���� �� ��������� �������������, kg",cyrylic));
	    PdfPCell antifire = new PdfPCell(new Phrase("��� �� ��������������� �������� (����,����,CO2 ��� ��.)",cyrylic));
	    PdfPCell number = new PdfPCell(new Phrase("��������� ������������ �� " +
	    		"�� ����������������� �������� (��� ��������� � ���� � ���������������)",cyrylic));
	    PdfPCell serving = new PdfPCell(new Phrase("��� �� ����������� ���������� " +
	    		"(���������� ����������( ��), ������������ (�), ��� ������������� ���������" +
	    		" �� ����������� �� �������� (��)- � ���������� ��� ��������� )",cyrylic));
	    PdfPCell date = new PdfPCell(new Phrase("���� �� ����������� ����������",cyrylic));
	    PdfPCell name = new PdfPCell(new Phrase("��� �� ������, ��������� ������������",cyrylic));
	    PdfPCell signature = new PdfPCell(new Phrase("������ �� ������ ��������� ������������",cyrylic));
	    PdfPCell stikernumber = new PdfPCell(new Phrase("����� �� ������",cyrylic));
	     
	    
	     table = new PdfPTable(11);
	     table.setTotalWidth(550);
	     try {
				table.setWidths(new float[]{2.5f,10f,4.5f,4.5f,7f,10f,10f,7f,5f,5f,6f});
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				PDFException.showPDFException(e);
		         PdfErr.pdfErros(e.toString());
				e.printStackTrace();
		         return false;
			}
	     
	     table.addCell(n);
	     table.addCell(mark);
	     table.addCell(category);
	     table.addCell(wheight);
	     table.addCell(antifire);
	     table.addCell(number);
	     table.addCell(serving);
	     table.addCell(date);
	     table.addCell(name);
	     table.addCell(signature);
	     table.addCell(stikernumber);
	     
	     sumOfRows += table.getRowHeight(0);
	     
	   
	     table.addCell("1");
	     table.addCell("2");
	     table.addCell("3");
	     table.addCell("4");
	     table.addCell("5");
	     table.addCell("6");
	     table.addCell("7");
	     table.addCell("8");
	     table.addCell("9");
	     table.addCell("10");
	     table.addCell("11");
	     
	    
	     sumOfRows += table.getRowHeight(1);

	    int stop = endIndex;
	    
	     int row = 0;
	     int numer = 0;
	     int help = 0;


	     for(row = 0;row < stop;row++) {
	    	     
	        	   table.addCell(++numer +"");   // column 1
	        	   table.addCell(new PdfPCell(new Phrase(dm.getValueAt(row + startIndex,6)+
	        			   " "+dm.getValueAt(row+startIndex, 4),cyrylic)));  //  column 2 ����� + �������� �����
	        	   table.addCell(new PdfPCell(new Phrase(dm.getValueAt(row + startIndex, 5)+"",cyrylic))); // column 3 ���������
	        	   table.addCell(new PdfPCell(new Phrase(dm.getValueAt(row+startIndex, 2)+"",cyrylic))); // column 4 ����
	        	   
	        	   // column 0 ���. �-��
	        	   String type = dm.getValueAt(row+startIndex, 1).toString();
	        	
	        	   switch(type) {
	        	   case  MainPanel.type_Prah_ABC : table.addCell(new PdfPCell(new Phrase("���� ���",
	        			   cyrylic))); break;
	        	   case MainPanel.type_Prah_BC :  table.addCell(new PdfPCell(new Phrase("���� ��",
	        			   cyrylic)));break;
	        	   case MainPanel.type_Water :  table.addCell(new PdfPCell(new Phrase("����"
	        			   ,cyrylic))); break;
	        	   case MainPanel.type_Water_Fame :  table.addCell(new PdfPCell(new Phrase("���������"
	        			   ,cyrylic))); break;
	        	   case MainPanel.type_CO2 :  table.addCell(new PdfPCell(new Phrase("CO2"
	        			   ,cyrylic))); break;
	        	   default : break;
	        	   }
	        	  
	        	  // column 6 ����� �� �������������� �� ��������� ����������� 
	       	  String tehnichesko_obslujvane = dm.getValueAt(row + startIndex, 7)+""; // ���������� ����������
	          String prezarejdane = dm.getValueAt(row + startIndex, 8)+""; // ������������
	          String hidrostatichno_izpitvane = dm.getValueAt(row + startIndex, 9)+""; // ������������� ���������
	          String nomerRazreshitelno  = ""; // ����� �� ��������������
	          String Obslujvane = "";
	          boolean comma = false;
	          // make type obslujvane
	          if(!tehnichesko_obslujvane.equals("��"))  {
	        	  Obslujvane += "��";
	        	  comma = true;
	          }
	          if(!prezarejdane.equals("��")) {
	        	  if(comma) {
	        		  Obslujvane += ",";
	        	  }
	        	  Obslujvane += "� ";
	        	  comma = true;
	          }
	          if(!hidrostatichno_izpitvane.equals("��")) {
	        	  if(comma) {
	        		  Obslujvane += ",";
	        	  }
	        	  Obslujvane += "��";
	          }
	          // ������� ����� �� ��������������
	        	  if(type.equals(MainPanel.type_Prah_BC) && (!tehnichesko_obslujvane.equals("") && !prezarejdane.equals(""))) {
	        		  nomerRazreshitelno = "����� BC ��������";
	        	  } else if(type.equals(MainPanel.type_Prah_ABC) && (!tehnichesko_obslujvane.equals("") && !prezarejdane.equals(""))) {
	        		  nomerRazreshitelno = "K���� ABC-50";
	        	  } else if(type.equals(MainPanel.type_Water_Fame) && (!tehnichesko_obslujvane.equals("") && !prezarejdane.equals(""))) {
	        		  nomerRazreshitelno = "STHAMEX F-15";
	        	  } else {
	        		  nomerRazreshitelno = "";
	        	  }
	        	  //
	        	  table.addCell(new PdfPCell(new Phrase(nomerRazreshitelno,cyrylic)));
	        	  table.addCell(new PdfPCell(new Phrase(Obslujvane,cyrylic)));// column 7 ���������� 
	        	  table.addCell(new PdfPCell(new Phrase(Date,cyrylic))); // column 8 ����
	        	  table.addCell(new PdfPCell(new Phrase(MainPanel.personName,cyrylic))); // column 9 ��� �� ������ ��������� ����������
	        	  table.addCell(""); // column 10 ������ �� ������ ��������� ������������
	        	  table.addCell(new PdfPCell(new Phrase("?",cyrylic))); //column 11 ����� �� ������
	        
	               sumOfRows += table.getRowHeight(row + 2);
	             
	        	  if((tablePos - (sumOfRows))
	        			   < document.bottom()) {
	        		    sumOfRows = 0;
	        		    table.writeSelectedRows(0,-1,from,row + 2,30,nextTablePos,writer.getDirectContent());
	        		    nextTablePos = 820;
	        		    from = row + 2;
	        	//	    next = true;
	        		    document.newPage();
	        	   }
	     }
	     try {
				table.setWidths(new float[]{2.5f,10f,4.5f,4.5f,7f,10f,10f,7f,5f,5f,6f});
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				 PDFException.showPDFException(e);
		         PdfErr.pdfErros(e.toString());
				e.printStackTrace();
		         return false;
			}
	 
	     table.writeSelectedRows(0, -1,from,-1,30,nextTablePos,writer.getDirectContent());
	
	     endY = nextTablePos - (sumOfRows + 40);
	     
	     float[] x = new float[]{150,50,50,50,50,130,50,40,50,50};
	     float[] y = new float[]{0,20,30,40,50,65,75,85,95,105};
	     
	   
	     String[] text = new String[]{
	    		 "�������� ������� I�-2377 ���� ��������� �������� �������� �����.",
	    		 "���������� �� ��������������/���:",
	    		 "\\" +clData[0]+ "\\                    "+clData[1]+"    "+clData[2],
	    		 "���� �������� �� ������� � ��� ����������� ���������� - �� ���� �� �������������, ��������� ������������, ",
	    		 "� �� ����������� �� ��������������/���.",
	    		 "������: ............................................................                        �����: ......................................................",
	    		 "  (�����������/�����������             (������, �����)                   ( ����������/�������-                           (������)",
	    		 "����������� �� �������������,                                                            ����� �� �����������)",
	    		 "    ��������� ������������)            " + "������� ���������" +"                                                                          "+ "���� ��������",
	    		 "                                                            "+"(���, �������)"+"                                                                           "+"(���, �������, ��������)"
	    		 };
	     
	     for(int i = 0;i < x.length;i++) {
	       if(endY - y[i] <= document.bottom()) {
	    		  document.newPage();
	    		   endY = 820;
	        }
	         pcb.setFontAndSize(getBaseFont("arial"), 9); // ����� �� ���� �����
	         pcb.beginText();
	    	 pcb.moveText(x[i], endY - y[i]);
	    	 pcb.showText(text[i]);
	    	 pcb.endText();
	     }
	    
	     document.close();
	 //    System.out.println("pdf created successfully...");
	     return true;
	}

	public static void main(String[] args)  {
		// TODO Auto-generated method stub
		    Protokol2815 p = new Protokol2815();
	}
	
}

*/