package PDF.SO;

import Exceptions.InOutException;
import Exceptions.PDFException;
import Log.IOErrorsWriter;
import Log.OtherErr;
import Log.PdfErr;
import PDF.MyPDFEventHandler;
import PDF.PdfCreator;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import mydate.MyGetDate;
import utility.MainPanel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.TreeMap;

public class ServiceOrderPDF extends PdfCreator {

	private final String logoPath = MainPanel.LOGO_PATH;
	private Image logo;
	private float logoX = 20;
	private float logoY = PageSize.A4.getHeight() - 60;
//	private final String target =   System.getProperty("user.home")+"/Desktop/";
	private final String target = MainPanel.SERVICE_ORDER_PDF_PATH;
	private Document document;
	private PdfWriter writer;
	private PdfContentByte content;
	private FileOutputStream fos;
	private PdfPTable headerTable;
	private PdfPTable titleTable;
	private PdfPTable dynamicTable;
	
	public ServiceOrderPDF() {}
	
public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServiceOrderPDF so = new ServiceOrderPDF();
       so.processPDF(MyGetDate.getTimeStamp(),"0000000000",null,null,null,null,null,null,null,null,null,null,
    		   new TreeMap<Object,Integer>());
	}

	public boolean processPDF(String timeStamp,String num,String setClient,String setCity,String setAddress,
			String setEIK,String setDDS, String setMol,String setBank,String setBIC, String setIBAN
			,String setTel,TreeMap<Object,Integer> soMap) {
		   if(init(timeStamp,num) == false) {
			   return false;
		   }
	       if(setLogo() == false) {
	    	   return false;
	       }
	       float nextX = logoX;
	       float nextY = logoY - 10;
	       setText("Дупница. ул “Никола Малашевски“ 5, тел.:",nextX,nextY,"arial",8);
	       setText(" 0886 390 020",nextX+ 160,nextY,"arialbd",8);
	       setText("e-mail: ",nextX+220,nextY,"arial",8);
	       setText("office@pojarprotekt.com;",nextX + 250,nextY,"arialbd",8);
	       setText("______________________",nextX+250,nextY-2,"arial",8);
	       setText("pojarprotect@abv.bg", nextX + 350, nextY, "arial", 8);
	       setHeaderTable(nextX,nextY -10,setClient,setCity,setAddress,
	   			setEIK,setDDS, setMol, setBank,setBIC, setIBAN
				,setTel);
	       nextY = nextY - (headerTable.getTotalHeight() + 30);
	       setText("СЕРВИЗНА ПОРЪЧКА",nextX + 160,nextY,"arialbd",15);
	       nextY = nextY - 20;
	       setText(" за обслужване на противопожарни уреди",nextX + 90,nextY,"arialbd",15);
	       nextY = nextY -15;
	       setTitleTable(
	    	nextX,nextY,num);
	       nextY = nextY - titleTable.getTotalHeight() - 10;
	       if(setDynamicTable(nextX,nextY, soMap) == false) {
	    	   return false;
	       }
	 //      nextY =  nextY - (dynamicTable.getTotalHeight() + 20);
	     
	       finish();
	       return true;
	}
	private void setFootText(float nextX, float nextY) {
		/*  setText("Предал: ",nextX + 10,nextY -15,"arial",10);
	       setText("/ подпис /",nextX + 75,nextY - 25,"arial",8);
	   //    setText(" _________________",nextX + 50,nextY - 30,"arial",10);
	       setText("/ име и фамилия /",nextX + 65,nextY - 55,"arial",8);
	       
	       setText("Телефон:",nextX + 10, nextY - 85,"arial",10);
	       
	       
	       setText("Приел: ",nextX + 10 + 300,nextY - 15 ,"arial",10);
	       setText("/ подпис /",nextX + 75 + 300,nextY - 25,"arial",8);
	  //     setText(" _________________",nextX + 50 + 300,nextY - 30,"arial",10);
	       setText("/ име и фамилия /",nextX + 65 + 300,nextY - 55,"arial",8);*/
	       int[] footX = {10,75,75,10};
	       int[] footY = {15,25,55,85};
	       String[] text = {"Предал:                                                                  "
	       		+ "                Приел: ",
	    		   "/ подпис /                                                                                  "
	    		   + "                                        / подпис / ",
	    		   "/ име и фамилия /                                                                 "
	    		   + "                                           /име и фамилия /",
	    		   "Телефон:",
	    		   };
	       
	       for(int i = 0;i < footX.length;i++) {
		       if(nextY - footY[i] <= document.bottom()) {
		    		  document.newPage();
		    		   nextY = 820;
		        }
		          if(text[i].contains("Предал")
		        		  || text[i].contains("Приел")
		        		   || text[i].contains("Телефон")) {
		          setText(text[i],nextX + footX[i],nextY-footY[i],"arial",12);
		          } else {
		        	   setText(text[i],nextX + footX[i],nextY-footY[i],"arial",8);
		          }
		     }
	}
	private void setHeaderTable(float x, float y,String setClient,String setCity,String setAddress,
			String setEIK,String setDDS, String setMol,String setBank,String setBIC, String setIBAN
			,String setTel) {
		headerTable = new PdfPTable(2);
		headerTable.setTotalWidth(520); // ???
		  
		  try {
				headerTable.setWidths(new int[]{100,100});
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				PDFException.showPDFException(e);
		         PdfErr.pdfErros(e.toString());
				e.printStackTrace();
			}
		  // set client and saller info
		  PdfPCell clientCell = new PdfPCell();
		  clientCell.setBorderWidth(1f);
	//	  clientCell.setNoWrap(true);
		  Font arial12 = getFontAndSize("arial",12);
			clientCell.addElement(new Phrase(14,"Собственик:  " + (setClient != null ? setClient : "")+ "\n",
					arial12));
			clientCell.addElement(new Phrase(14,"Град: " + (setCity != null ? setCity : "") + "\n",
					arial12));
			clientCell.addElement(new Phrase(14,"Адрес: "+ (setAddress != null ? setAddress : "") + "\n",
					arial12));
		
			clientCell.addElement(new Phrase(14,"ЕИК:" + (setEIK != null ? setEIK : "") + "\n",
					arial12));
			clientCell.addElement(new Phrase(14,"ДДС \u2116: " + (setDDS  != null ? setDDS : "") + "\n",
					arial12));
			clientCell.addElement(new Phrase(14,"МОЛ: " + (setMol != null ? setMol : "") + "\n",
					arial12));
			clientCell.addElement(new Phrase(14,"Банка: "+ (setBank != null ? setBank : "") + "\n",
					arial12));
			clientCell.addElement(new Phrase(14,"BIC: " + (setBIC != null ? setBIC : "") + "\n",
					arial12));
			
		
		    clientCell.addElement(new Phrase(14,"IBAN:" + (setIBAN != null ? setIBAN : "") + "\n",
		    		arial12));

           clientCell.addElement(new Phrase(14,"Тел: " +( setTel != null ? setTel : "") ,
        		   arial12));
		
			PdfPCell sallerCell = new PdfPCell();
			sallerCell.setBorderWidth(1f);
			
			sallerCell.addElement(new Phrase(14,"Доставчик: "	+ MainPanel.SALLER_NAME + "\n" ,
					arial12));
		     sallerCell.addElement(new Phrase(14,"Разрешение \u2116 477/27.08.2015г.",
		    		 arial12));
            sallerCell.addElement(new Phrase(14,"Град: " + MainPanel.SALLER_CITY + "\n",
            		arial12));
            sallerCell.addElement(new Phrase(14,"Адрес: " + MainPanel.SALLER_ADDRESS + "\n",
            		arial12));
            sallerCell.addElement(new Phrase(14,"ЕИК: " + MainPanel.SALLER_EIK + "\n",
            		arial12));
            sallerCell.addElement(new Phrase(14,"ДДС \u2116 BG" + MainPanel.SALLER_EIK + "\n",
            		arial12));
            sallerCell.addElement(new Phrase(14,"МОЛ: " + MainPanel.SALLER_MOL + "\n",
            		arial12));
            sallerCell.addElement(new Phrase(14,"Банка: " + MainPanel.SALLER_BANK + "\n",
            		arial12));
            sallerCell.addElement(new Phrase(14,"BIC: " + MainPanel.SALLER_BIC + "\n",
            		arial12));
            sallerCell.addElement(new Phrase(14,"IBAN: " + MainPanel.SALLER_IBAN + "\n",
            		arial12));
		  
		  headerTable.addCell(clientCell);
		  headerTable.addCell(sallerCell);
		  
		  headerTable.setTotalWidth(530);
		  
		  try {
				headerTable.setWidths(new int[]{100,100});
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				PDFException.showPDFException(e);
		         PdfErr.pdfErros(e.toString());
				e.printStackTrace();
			}
		   headerTable.writeSelectedRows(0,-1,x,y,writer.getDirectContent());
		  
		   
	}

    private void setTitleTable(float x,float y,String num) {
    	titleTable = new PdfPTable(1);
    	
    	PdfPCell titleCell = new PdfPCell();
    	titleCell.setBorderWidth(1f);
    	titleCell.setPaddingBottom(10);
   // 	titleCell.addElement(new Phrase(20,title,getFontAndSize("arialbd",15)));
    	Font arial10 = getFontAndSize("arial",10);
    	Font arialbd10 = getFontAndSize("arialbd",10);
    	Phrase phrase = new Phrase();
    	phrase.add(new Phrase("Място: ",arial10));
    	phrase.add(new Phrase(" Дупница",arial10));
    	phrase.add(new Phrase("                                                 Дата: ",arial10));
    	phrase.add(new Phrase(MyGetDate.getReversedSystemDate(),
    			arialbd10));
    	phrase.add(new Phrase("                                                          \u2116",
    			arial10));
    	phrase.add(new Phrase(" "+num,arialbd10));
    	titleCell.addElement(phrase);
    	
    	titleTable.addCell(titleCell);
    	
    	titleTable.setTotalWidth(530);
    	titleTable.writeSelectedRows(0,-1,x,y,writer.getDirectContent());
 		  
    	
    }
	private void setText(String text,float x, float y,String font, float size) {
		  content.beginText();
		  content.moveText(x,y);
		  content.setFontAndSize(getBaseFont(font), size);
		  content.showText(text);
		  content.endText();
	}
	private void finish() {
		document.close();
	//	System.out.println("done!");
	}
	private boolean setLogo() {
		try {
			logo = Image.getInstance(logoPath);
			logo.setAbsolutePosition(logoX, logoY);
			document.add(logo);
		} catch (BadElementException e) {
			// TODO Auto-generated catch block
			PDFException.showPDFException(e);
	         PdfErr.pdfErros(e.toString());
			e.printStackTrace();
			return false;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			OtherErr.otherErros(e.toString());
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			InOutException.showIOException(e);
			IOErrorsWriter.writeIO(e.toString());
			e.printStackTrace();
			return false;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			PDFException.showPDFException(e);
	         PdfErr.pdfErros(e.toString());
	     	OtherErr.otherErros(e.toString());
			e.printStackTrace();
			return false;
		}
		return true;
	}
	private boolean setDynamicTable(float x, float y,TreeMap<Object,Integer> soMap) {

		 dynamicTable = new PdfPTable(4);
		
		  dynamicTable.setTotalWidth(530);
		  
		  try {
				dynamicTable.setWidths(new int[]{14,130,30,110});
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				PDFException.showPDFException(e);
		         PdfErr.pdfErros(e.toString());
				e.printStackTrace();
				return false;
			}
		
		  Font arial13 = getFontAndSize("arial",13);
		  PdfPCell n = new PdfPCell(new Phrase("\u2116",arial13));
		  n.setBorderWidth(1f);
		  n.setPaddingLeft(5);
	
		  PdfPCell model = new PdfPCell(new Phrase("Вид на противопожарния уред",
				  arial13));
		  model.setBorderWidth(1f);
		  model.setPaddingLeft(20);
		  
		  PdfPCell quantity = new PdfPCell(new Phrase("К-во",
				  arial13));
		  quantity.setBorderWidth(1f);
		  quantity.setPaddingLeft(12);
		  
		  PdfPCell warn = new PdfPCell(new Phrase("Забележка",
				  arial13));
		  warn.setBorderWidth(1f);
		  warn.setPaddingLeft(60);

		  dynamicTable.addCell(n);
		  dynamicTable.addCell(model);
		  dynamicTable.addCell(quantity);
		  dynamicTable.addCell(warn);
		  
		  float sumOfRows = dynamicTable.getRowHeight(0);
		  float nextY = y;
		  int getFootY = 0;// calc foot text position
		  int from = 0;
		  int row = 1; // show number of rows
		  int allSum = 0;
		  int sum = 0;
		  String вид = null;
		  Font arial12 = getFontAndSize("arial",12);
   //    for(int c = 0; c < 64;c++)  {
		  for(Map.Entry<Object, Integer> m : soMap.entrySet()) {
			  вид = m.getKey()+"";
			
			  sum = m.getValue();
			  allSum += sum;
			  
			  for(int j = 1;j <= 4;j++) {
				  switch(j) {
				  case 1 :   PdfPCell cell1 = new PdfPCell(new Phrase(""+row,arial12)); 
				  cell1.setBorderWidth(1f);
				  cell1.setPaddingLeft(6f);
				  dynamicTable.addCell(cell1); break;
				  case 2:  PdfPCell cell2 = new PdfPCell(new Phrase(вид,arial12)); 
				  cell2.setBorderWidth(1f);
				  dynamicTable.addCell(cell2);break;
				  case 3 : PdfPCell cell3 = new PdfPCell(new Phrase(sum+" бр.",arial12));
				  cell3.setBorderWidth(1f);
				  cell3.setPaddingLeft(12);
				  dynamicTable.addCell(cell3);break;
				  case 4: PdfPCell cell4 = new PdfPCell(new Phrase(""));
				  cell4.setBorderWidth(1f);
				  dynamicTable.addCell(cell4);break;
				  default:break;
				  }
				}
		
			  sumOfRows += dynamicTable.getRowHeight(row);
			  getFootY = from;
			  if( (y - (sumOfRows))
     			   < document.bottom()) {
     		    sumOfRows = 0;
     		    dynamicTable.writeSelectedRows(0,-1,from,row,x,nextY,writer.getDirectContent());
     		    nextY= 820;
     		  
     		    from = row;
     		    document.newPage();
     	   }
			  row++;
		  }
    //   }	// check if should go to the next page
		  
		  Font arial8 = getFontAndSize("arial",8);
		  PdfPCell empty = new PdfPCell(new Phrase("",arial8));
		  empty.setBorderWidth(1f);
		  PdfPCell all = new PdfPCell(new Phrase("Общо получени " +
		  		"пожарогасители за сервизно обслужване",arial12));
		  all.setBorderWidth(1f);
		  PdfPCell amounts = new PdfPCell(new Phrase(allSum+" бр.",arial12));
		  amounts.setBorderWidth(1f);
		  amounts.setPaddingLeft(12);
		  
		  dynamicTable.addCell(empty);
		  dynamicTable.addCell(all);
		  dynamicTable.addCell(amounts);
		  dynamicTable.addCell(empty);
		  
       dynamicTable.setTotalWidth(530);
		  
		  try {
				dynamicTable.setWidths(new int[]{14,130,30,110});
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				PDFException.showPDFException(e);
		         PdfErr.pdfErros(e.toString());
				e.printStackTrace();
				return false;
			}
		   dynamicTable.writeSelectedRows(0,-1,from,-1,x,nextY,writer.getDirectContent());
		 
		   float pos = 0;
		
		   for(int i = getFootY;i < row;i++) {
			   pos += dynamicTable.getRowHeight(i);
		   }
		
		   setFootText(x,nextY - (pos + 50));
		   return true;
	}
	
	 boolean init(String timeStamp,String num) {
		document = new Document(PageSize.A4,50.f,50.f,50.f,50.f);
		try {
			
			fos = new FileOutputStream(this.target + "\\СервизнаПоръчка-"
			+ timeStamp + "-"+num+".pdf");
			
			writer = PdfWriter.getInstance(document,fos);
			
			writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
			
			// set num of pages handler
			MyPDFEventHandler pdfEventHandler = new MyPDFEventHandler();
			writer.setPageEvent(pdfEventHandler);
			
			document.open();
			
			content = writer.getDirectContent();
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
		return true;
	}
}
