/*package PDF.SO;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.TreeMap;

import CommonResources.MainPanel;
import Date.GetDate;
import Exceptions.InOutException;
import Exceptions.PDFException;
import Log.IOErrorsWriter;
import Log.OtherErr;
import Log.PdfErr;
import PDF.MyPDFEventHandler;
import PDF.PdfCreator;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PDF_SO  extends PdfCreator {

		private final String target = MainPanel.SERVICE_ORDER_PDF_PATH;//System.getProperty("user.home")+"/Desktop/";
	
		private  Image logo = null;
		private  float logoX = 100;
		private  float logoY = PageSize.A4.getHeight() - 60;
		private  String logoPath = MainPanel.LOGO_PATH;
		private  int fontSize = 14;
		private float sumOfRows = 0;
		private float tablePos = 640;
		private int from = 0;

		private float endX;
		private float endY;
		
	
		
		public  boolean createServiceOrderPDF(String num,String setClient,String setCity,String setAddress,
				String setMol,String setEIK
				,String setTel,TreeMap<Object,Integer> soMap) {
			
			// set document
			Document document = new Document(PageSize.A4,50.f,50.f,50.f,50.f);
			
			// get pdf writer
			PdfWriter pdfWriter = null;
			try {
				pdfWriter = PdfWriter.getInstance(document, 
						new FileOutputStream(this.target+"\\СервизнаПоръчка-"+num+".pdf")); // to add so number
				
				pdfWriter.setBoxSize("art", new Rectangle(36, 54, 559, 788));
				
				// set num of pages handler
				MyPDFEventHandler pdfEventHandler = new MyPDFEventHandler();
				pdfWriter.setPageEvent(pdfEventHandler);
				
				document.open();
				
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
			
			
	    this.cyrylic = new Font(getBaseFont("arial"),fontSize);
		
	    for(int i = 0;i < 3;i++) {
	    	paintMainContent(logoX, logoY,
					pdfWriter,document,
					num,setClient,setCity,
					setAddress,setMol,setEIK
					,setTel,soMap);
	    }
		 document.close();
		 return true;
//		 System.out.println("pdf is creted successfully...");
			
		}
		
		public void paintMainContent(float startX, float startY,
				PdfWriter pdfWriter,Document document,
				String num,String setClient,String setCity,
				String setAddress,String setMol,String setEIK
				,String setTel,TreeMap<Object,Integer> soMap) {
			
			
			  try {
				logo = Image.getInstance(String.format(logoPath,"777"));
				logo.setAbsolutePosition(logoX,logoY);
				document.add(logo);
			} catch (BadElementException e) {
				// TODO Auto-generated catch block
				PDFException.showPDFException(e);
		         PdfErr.pdfErros(e.toString());
		         e.printStackTrace();
		         
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				OtherErr.otherErros(e.toString());
				e.printStackTrace();
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				InOutException.showIOException(e);
				IOErrorsWriter.writeIO(e.toString());
				e.printStackTrace();
				
				
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  
			
		   
			// get pdf content byte
			  PdfContentByte pcb = pdfWriter.getDirectContent();
			 
			  // begin text
			  float titleX = logoX;
			  float titleY = logoY-25;
			  
			  pcb.beginText();
			  pcb.moveText(titleX,titleY);
			  pcb.setFontAndSize(getBaseFont("arial"), 20);
			  String title = "СЕРВИЗНА ПОРЪЧКА";
			  pcb.showText(title);
			  pcb.endText();
			  
			  // set No: text 
			  float serviceNoX = logoX + MainPanel.fm.stringWidth(title) + 70;
			  float serviceNoY = logoY-15;
			  pcb.beginText();
			  pcb.moveText(serviceNoX,serviceNoY);
			  pcb.setFontAndSize(getBaseFont("arial"), 15);
			  pcb.showText("\u2116");
			  pcb.endText();
			  
			  // set service number
			  float serviceNumX = serviceNoX + 50;
			  float serviceNumY = serviceNoY;
			  pcb.beginText();
			  pcb.moveText(serviceNumX,serviceNumY);
			  pcb.setFontAndSize(getBaseFont("arial"), 15);
			  pcb.showText(num);
			  pcb.endText();
			  
			  // set date title
			  pcb.beginText();
			  pcb.moveText(serviceNoX, serviceNoY-15);
			  pcb.setFontAndSize(getBaseFont("arial"), 15);
			  pcb.showText("Дата : ");
			  pcb.endText();
			  
			  // set date 
			  pcb.beginText();
			  pcb.moveText(serviceNumX, serviceNumY-15);
			  pcb.setFontAndSize(getBaseFont("arial"), 15);
			  pcb.showText(GetDate.getReversedSystemDate());
			  pcb.endText();
			  
			  // set saller title
			  float sallerTitleX = titleX;
			  float sallerTitleY = titleY - 20;
			  pcb.beginText();
			  pcb.moveText(sallerTitleX, sallerTitleY);
			  
		//	  pcb.setFontAndSize(getBoldFont(),9);
			  
			  pcb.showText("Продавач : ");
			  pcb.endText();
			  
			  // set saller text
			  float sallerTextX = sallerTitleX;
			  float sallerTextY = sallerTitleY-10;
			  pcb.beginText();
			  pcb.moveText(sallerTextX, sallerTextY);
		//	  pcb.setFontAndSize(getBoldFont(), 11);
			  pcb.showText(MainPanel.SALLER_NAME);
			  pcb.endText();
			  
			  // set svidetelstvo No:
			  float svidetelstvoNoX = sallerTextX;
			  float svidetelstvoNoY = sallerTextY -10;
			  pcb.beginText();
			  pcb.moveText(svidetelstvoNoX, svidetelstvoNoY);
			  pcb.setFontAndSize(getBaseFont("arial"), 11);
			  pcb.showText("Свидетелство " + "\u2116" + " 477/27.8.2015г.");
			  pcb.endText();
			  
			  // set address
			  float sallerAddressX = svidetelstvoNoX;
			  float sallerAddressY = svidetelstvoNoY -10;
			  pcb.beginText();
			  pcb.moveText(sallerAddressX, sallerAddressY);
			  pcb.setFontAndSize(getBaseFont("arial"), 9);
			  pcb.showText(MainPanel.SALLER_ADDRESS);
			  pcb.endText();
			  
			  // set city
			  float cityX = sallerAddressX;
			  float cityY = sallerAddressY-10;
			  pcb.beginText();
			  pcb.moveText(cityX, cityY);
			  pcb.setFontAndSize(getBaseFont("arial"), 9);
			  pcb.showText(MainPanel.SALLER_CITY);
			  pcb.endText();
			  
			  // set MOl
			  float molX = cityX;
			  float molY = cityY-10;
			  pcb.beginText();
			  pcb.moveText(molX, molY);
			  pcb.setFontAndSize(getBaseFont("arial"), 9);
			  pcb.showText(MainPanel.SALLER_MOL);
			  pcb.endText();
			  
			  // set EIK
			  float eikX = molX;
			  float eikY = molY-10;
			  pcb.beginText();
			  pcb.moveText(eikX, eikY);
			  pcb.setFontAndSize(getBaseFont("arial"), 9);
			  pcb.showText(MainPanel.SALLER_EIK);
			  pcb.endText();
			  
			  // set saller tel EIK
			  float telX = eikX;
			  float telY = eikY-10;
			  pcb.beginText();
			  pcb.moveText(telX, telY);
			  pcb.setFontAndSize(getBaseFont("arial"), 9);
			  pcb.showText("0701/51546 , 0886 39 00 20");
			  pcb.endText();
			  
			  // set buyer title
			  float buyerTitleX = serviceNoX;
			  float buyerTitleY = titleY - 20;
			  pcb.beginText();
			  pcb.moveText(buyerTitleX, buyerTitleY);
		//	  pcb.setFontAndSize(getBoldFont(), 9);
			  pcb.showText("Купувач : ");
			  pcb.endText();
			  
			  // set buyer text
			  float buyerTextX = buyerTitleX;
			  float buyerTextY = buyerTitleY-10;
			  pcb.beginText();
			  pcb.moveText(buyerTextX, buyerTextY);
	//		  pcb.setFontAndSize(getBoldFont(), 11);
			  pcb.showText(setClient);
			  pcb.endText();
			  
			  // set buyer address
			  float buyerAddressX = buyerTextX;
			  float buyerAddressY = buyerTextY-20;
			  pcb.beginText();
			  pcb.moveText(buyerAddressX, buyerAddressY);
			  pcb.setFontAndSize(getBaseFont("arial"), 9);
			  pcb.showText(setAddress);
			  pcb.endText();
			  
			// set buyer city
			  float buyerCityX = buyerAddressX;
			  float buyerCityY = buyerAddressY-10;
			  pcb.beginText();
			  pcb.moveText(buyerCityX, buyerCityY);
			  pcb.setFontAndSize(getBaseFont("arial"), 9);
			  pcb.showText(setCity);
			  pcb.endText();
			  
			// set buyer mol
			  float buyerMolX = buyerCityX;
			  float buyerMolY = buyerCityY-10;
			  pcb.beginText();
			  pcb.moveText(buyerMolX, buyerMolY);
			  pcb.setFontAndSize(getBaseFont("arial"), 9);
			  pcb.showText(setMol);
			  pcb.endText();
			  
			// set buyer eik
			  float buyerEikX = buyerMolX;
			  float buyerEikY = buyerMolY-10;
			  pcb.beginText();
			  pcb.moveText(buyerEikX, buyerEikY);
			  pcb.setFontAndSize(getBaseFont("arial"), 9);
			  pcb.showText(setEIK);
			  pcb.endText();
			  
			// set buyer tel
			  float buyerTelX = buyerEikX;
			  float buyerTelY = buyerEikY-10;
			  pcb.beginText();
			  pcb.moveText(buyerTelX, buyerTelY);
			  pcb.setFontAndSize(getBaseFont("arial"), 9);
			  pcb.showText(setTel);
			  pcb.endText();
		//	  pasteQRBarcode(document,barcodInfo);
		
			    
			// create main table 
			createMainTable(pdfWriter,document,soMap,telX,telY-10);
			  
			 
			  
		}
		
		public  void createMainTable(PdfWriter writer,Document document,TreeMap<Object,Integer> soMap,
				float tableX,float tableY) {
	 
			  float nextTablePos = tableY;
			  
			  PdfPTable table = new PdfPTable(4);
			  table.setTotalWidth(480);
			  
			  try {
					table.setWidths(new int[]{14,130,30,110});
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					PDFException.showPDFException(e);
			         PdfErr.pdfErros(e.toString());
				//	e.printStackTrace();
				}
			  
			  PdfPCell n = new PdfPCell(new Phrase("\u2116",this.cyrylic));
			  n.setPaddingLeft(5);
			  PdfPCell model = new PdfPCell(new Phrase("Модел",this.cyrylic));
			  model.setPaddingLeft(90);
			  PdfPCell quantity = new PdfPCell(new Phrase(" К-во",this.cyrylic));
			  quantity.setPaddingLeft(2);
			  PdfPCell warn = new PdfPCell(new Phrase("Забележка",this.cyrylic));
			  warn.setPaddingLeft(60);
			  table.addCell(n);
			  table.addCell(model);
			  table.addCell(quantity);
			  table.addCell(warn);
			  
			  sumOfRows += table.getRowHeight(0);
			  int row = 1; // show number of rows
			  int allSum = 0;
			  int sum = 0;
			  String вид = null;
	       //   for(int c = 0; c < 44;c++) 
			  for(Map.Entry<Object, Integer> m : soMap.entrySet()) {
				  вид = m.getKey()+"";
				  sum = m.getValue();
				  allSum += sum;
				  
				  for(int j = 1;j <= 4;j++) {
					  switch(j) {
					  case 1 :   table.addCell(" " + row); break;
					  case 2:  PdfPCell cell = new PdfPCell(new Phrase(вид,this.cyrylic)); 
					  table.addCell(cell);break;
					  case 3 : PdfPCell cell2 = new PdfPCell(new Phrase(sum+"бр.",this.cyrylic)); 
					  cell2.setPaddingLeft(5);
					  table.addCell(cell2);break;
					  case 4: table.addCell("");break;
					  default:break;
					  }
					}
				// check if should go to the next page
				  sumOfRows += table.getRowHeight(row);
				  if( (tablePos - (sumOfRows))
	        			   < document.bottom()) {
	        		    sumOfRows = 0;
	        		    table.writeSelectedRows(0,-1,from,row,tableX,nextTablePos,writer.getDirectContent());
	        		    nextTablePos = 820;
	        		    from = row;
	        		    document.newPage();
	        	   }
				  row++;
			  }
			 
			  
			  
			  PdfPCell empty = new PdfPCell(new Phrase("",this.cyrylic));
			  PdfPCell all = new PdfPCell(new Phrase("Общо получени " +
			  		"пожарогасители за сервизно обслужване",this.cyrylic));
			  PdfPCell amounts = new PdfPCell(new Phrase(allSum+" бр.",this.cyrylic));
			  amounts.setPaddingLeft(5);
			  table.addCell(empty);
			  table.addCell(all);
			  table.addCell(amounts);
			  table.addCell(empty);
			  
              table.setTotalWidth(480);
			  
			  try {
					table.setWidths(new int[]{14,130,30,110});
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					PDFException.showPDFException(e);
			         PdfErr.pdfErros(e.toString());
				//	e.printStackTrace();
				}
			   table.writeSelectedRows(0,-1,from,-1,tableX,nextTablePos,writer.getDirectContent());
			 
			   
			       // set bottom
			   
			     // get pdf content byte
				  PdfContentByte pcb = writer.getDirectContent();
				 
				  // set sale man 
				  float sallerX = tableX;
				  float sallerY = nextTablePos - (sumOfRows + 70);
				  
				  pcb.beginText();
				  pcb.moveText(sallerX,sallerY);
				  pcb.setFontAndSize(getBaseFont("arial"), 9);
				  String taken = "подпис";//
				  pcb.showText(taken);
				  pcb.endText();
				  
				  // set sale man signature
				  float sallerSignatureX = sallerX;
				  float sallerSignatureY = sallerY - 10;
				  
				  pcb.beginText();
				  pcb.moveText(sallerSignatureX,sallerSignatureY);
				  pcb.setFontAndSize(getBaseFont("arial"), 9);
				  pcb.showText("Приел : / " + MainPanel.personName + " /");
				  pcb.endText();
				  
				  // set client title  
				  float clientX = sallerX + 300;
				  float clientY = sallerY;
				  
				  pcb.beginText();
				  pcb.moveText(clientX,clientY);
				  pcb.setFontAndSize(getBaseFont("arial"), 9);
				  pcb.showText("подпис : ");
				  pcb.endText();
				  
				  // set client signature
				  float clientSignatureX = clientX;
				  float clientSignatureY = clientY - 10;
				  
				  pcb.beginText();
				  pcb.moveText(clientSignatureX,clientSignatureY);
				  pcb.setFontAndSize(getBaseFont("arial"), 9);
				  pcb.showText("Клиент : ");
				  pcb.endText();
				  
				  
				//  logoX = clientSignatureX;
				  logoY = clientSignatureY;
		}
	
		
		
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	

}
*/