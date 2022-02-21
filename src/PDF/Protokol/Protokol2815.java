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
	     pcb.showText("НАРЕДБА No: Iз-2815, Приложение No: 8 към чл. 40 ал. 6"); // малко по едър шрифт
	     pcb.endText();
	     
	     
	     pcb.beginText();
	     pcb.moveText(160, 790);
	     pcb.setFontAndSize(getBaseFont("arial"), 14);
	     pcb.showText("ПРОТОКОЛ No: "+protokolNumber);// TO DO 
	     pcb.endText();
	     
	     pcb.beginText();
	     pcb.moveText(60, 770);
	     pcb.setFontAndSize(getBaseFont("arial"), 9);
	     pcb.showText("за предаване и приемане на пожарогасители, на които е " +
	     		"извършено техническо обслужване, презареждане"); // по тъмен цвят
	    pcb.endText();
	  
	    
	    pcb.beginText();
	     pcb.moveText(90, 755);
	     pcb.setFontAndSize(getBaseFont("arial"), 9);
	     pcb.showText(" или хидростатично изпитване" +
	     		" (в комбинация или поотделно)"); // по тъмен цвят
	    pcb.endText();
	    
	    pcb.beginText();
	     pcb.moveText(65, 730);
	     pcb.setFontAndSize(getBaseFont("arial"), 9);
	     pcb.showText("Днес "+Date+" в гр. Дупница, ръководителят (упълномощеният представител)  " +
	     		" " + MainPanel.personName + " "); // малко по едър шрифт
	    pcb.endText();
	    
	    pcb.beginText();
	     pcb.moveText(60, 720);
	     pcb.setFontAndSize(getBaseFont("arial"), 9);
	     pcb.showText("на  "
	     		+  MainPanel.SALLER_NAME + 
	     		" " + MainPanel.SALLER_CITY + " " +
	     		MainPanel.SALLER_ADDRESS + " ЕИК : " +
	     		MainPanel.SALLER_EIK + " " + " тел 0886 39 00");
	    pcb.endText(); // мало по едър шрифт
	    
	    pcb.beginText();
	     pcb.moveText(60, 710);
	     pcb.setFontAndSize(getBaseFont("arial"), 9);
	     pcb.showText(", предаде на собственика (или негов представител) пожарогасителите на които е извършено обслужване, както следва.");
	    pcb.endText(); // малко по едър шрифт
	    
	    // create table 
	    PdfPTable table = null;//new PdfPTable(11);
	 //   table.setSplitLate(false);
	    
	   int from = 0;
	//   int to = 0;
	    
	    PdfPCell n = new PdfPCell(new Phrase("No: по ред",cyrylic));
	    PdfPCell mark = new PdfPCell(new Phrase("Идентификационна маркировка на всеки пожарогасител" +
	    		" (марка, модел,сериен номер и др)",cyrylic));
	    PdfPCell category = new PdfPCell(new Phrase("Категория съгласно т. 4.3.2.2 от БДС " +
	    		"ISO 11602-2:2002",cyrylic));
	    PdfPCell wheight = new PdfPCell(new Phrase("Маса на заредения пожарогасител, kg",cyrylic));
	    PdfPCell antifire = new PdfPCell(new Phrase("Вид на пожарогасително вещество (вода,прах,CO2 или др.)",cyrylic));
	    PdfPCell number = new PdfPCell(new Phrase("Търговско наименование на " +
	    		"на пожарогасителното вещество (при зареждане с прах и пенообразувател)",cyrylic));
	    PdfPCell serving = new PdfPCell(new Phrase("Вид на извършеното обслужване " +
	    		"(техническо обслужване( ТО), презареждане (П), или хидростатично изпитване" +
	    		" на устойчивост на налягане (ХИ)- в комбинация или поотделно )",cyrylic));
	    PdfPCell date = new PdfPCell(new Phrase("Дата на извършеното обслужване",cyrylic));
	    PdfPCell name = new PdfPCell(new Phrase("Име на лицето, извършило обслужването",cyrylic));
	    PdfPCell signature = new PdfPCell(new Phrase("Подпис на лицето извършило обслужването",cyrylic));
	    PdfPCell stikernumber = new PdfPCell(new Phrase("Номер на стикер",cyrylic));
	     
	    
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
	        			   " "+dm.getValueAt(row+startIndex, 4),cyrylic)));  //  column 2 марка + монтажен номер
	        	   table.addCell(new PdfPCell(new Phrase(dm.getValueAt(row + startIndex, 5)+"",cyrylic))); // column 3 категория
	        	   table.addCell(new PdfPCell(new Phrase(dm.getValueAt(row+startIndex, 2)+"",cyrylic))); // column 4 маса
	        	   
	        	   // column 0 пож. в-во
	        	   String type = dm.getValueAt(row+startIndex, 1).toString();
	        	
	        	   switch(type) {
	        	   case  MainPanel.type_Prah_ABC : table.addCell(new PdfPCell(new Phrase("прах АВС",
	        			   cyrylic))); break;
	        	   case MainPanel.type_Prah_BC :  table.addCell(new PdfPCell(new Phrase("прах ВС",
	        			   cyrylic)));break;
	        	   case MainPanel.type_Water :  table.addCell(new PdfPCell(new Phrase("вода"
	        			   ,cyrylic))); break;
	        	   case MainPanel.type_Water_Fame :  table.addCell(new PdfPCell(new Phrase("водопенен"
	        			   ,cyrylic))); break;
	        	   case MainPanel.type_CO2 :  table.addCell(new PdfPCell(new Phrase("CO2"
	        			   ,cyrylic))); break;
	        	   default : break;
	        	   }
	        	  
	        	  // column 6 номер на разрешителното за гасителна ефективност 
	       	  String tehnichesko_obslujvane = dm.getValueAt(row + startIndex, 7)+""; // техническо обслужване
	          String prezarejdane = dm.getValueAt(row + startIndex, 8)+""; // презареждане
	          String hidrostatichno_izpitvane = dm.getValueAt(row + startIndex, 9)+""; // хидростатично изпитване
	          String nomerRazreshitelno  = ""; // номер на разрешителното
	          String Obslujvane = "";
	          boolean comma = false;
	          // make type obslujvane
	          if(!tehnichesko_obslujvane.equals("не"))  {
	        	  Obslujvane += "ТО";
	        	  comma = true;
	          }
	          if(!prezarejdane.equals("не")) {
	        	  if(comma) {
	        		  Obslujvane += ",";
	        	  }
	        	  Obslujvane += "П ";
	        	  comma = true;
	          }
	          if(!hidrostatichno_izpitvane.equals("не")) {
	        	  if(comma) {
	        		  Obslujvane += ",";
	        	  }
	        	  Obslujvane += "ХИ";
	          }
	          // постави номер на разрешителното
	        	  if(type.equals(MainPanel.type_Prah_BC) && (!tehnichesko_obslujvane.equals("") && !prezarejdane.equals(""))) {
	        		  nomerRazreshitelno = "КОБРА BC Стандарт";
	        	  } else if(type.equals(MainPanel.type_Prah_ABC) && (!tehnichesko_obslujvane.equals("") && !prezarejdane.equals(""))) {
	        		  nomerRazreshitelno = "KОБРА ABC-50";
	        	  } else if(type.equals(MainPanel.type_Water_Fame) && (!tehnichesko_obslujvane.equals("") && !prezarejdane.equals(""))) {
	        		  nomerRazreshitelno = "STHAMEX F-15";
	        	  } else {
	        		  nomerRazreshitelno = "";
	        	  }
	        	  //
	        	  table.addCell(new PdfPCell(new Phrase(nomerRazreshitelno,cyrylic)));
	        	  table.addCell(new PdfPCell(new Phrase(Obslujvane,cyrylic)));// column 7 обслужване 
	        	  table.addCell(new PdfPCell(new Phrase(Date,cyrylic))); // column 8 дата
	        	  table.addCell(new PdfPCell(new Phrase(MainPanel.personName,cyrylic))); // column 9 име на лицето извършило обслужване
	        	  table.addCell(""); // column 10 подпис на лицето извършило обслужването
	        	  table.addCell(new PdfPCell(new Phrase("?",cyrylic))); //column 11 номер на стикер
	        
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
	    		 "Съгласно Наредба Iз-2377 бяха монтирани следните резервни части.",
	    		 "Собственик на пожарогасителя/ите:",
	    		 "\\" +clData[0]+ "\\                    "+clData[1]+"    "+clData[2],
	    		 "Този протокол се състави в два еднообразни екземпляра - по един за организацията, извършила обслужването, ",
	    		 "и за собственика на пожарогасителя/ите.",
	    		 "Предал: ............................................................                        Приел: ......................................................",
	    		 "  (ръководител/упълномощен             (подпис, печат)                   ( собственик/предста-                           (подпис)",
	    		 "Преставител на организацията,                                                            вител на собственика)",
	    		 "    извършила обслужването)            " + "Даниела Стойчкова" +"                                                                          "+ "Иван Стойчков",
	    		 "                                                            "+"(име, фамилия)"+"                                                                           "+"(име, фамилия, длъжност)"
	    		 };
	     
	     for(int i = 0;i < x.length;i++) {
	       if(endY - y[i] <= document.bottom()) {
	    		  document.newPage();
	    		   endY = 820;
	        }
	         pcb.setFontAndSize(getBaseFont("arial"), 9); // малко по едър шрифт
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