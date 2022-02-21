/*package PDF.SO;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

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
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

public class SO_Original extends PdfCreator{

	private final String targetDirectory =  MainPanel.SERVICE_ORDER_PDF_PATH;//System.getProperty("user.home") + "/Desktop";
			//MainPanel.SERVICE_ORDER_PDF_PATH;
	private Document document;
	private PdfWriter pdfWriter;
	private PdfContentByte content;

	private  final String logoPath = MainPanel.LOGO_PATH;
	private float baseX = 60;
	private float baseY = 820;
	private PdfPTable headerTable;
	private PdfPTable extraInfoTable;
	private PdfPTable dynamicTable;
	
	public SO_Original() {
		
	}
	public boolean createSO_Original(String num,String setClient,String setCity,String setAddress,
			String setEIK,String setDDS, String setMol,String setBank,String setBIC, String setIBAN
			,String setTel,TreeMap<Object,Integer> soMap) {
		// set document
		document = new Document(PageSize.A4,50.f,50.f,50.f,50.f);
		
		initPdfDocument(num);
		
		setHeaderTable(baseX, baseY,
				setClient,setCity,setAddress,setEIK,setDDS,setMol,setBank,setBIC,setIBAN,setTel);
		
		float nextX = baseX + 15;
		float nextY = baseY - headerTable.getTotalHeight() - 13;
		setText("Сервизна поръчка за обслужване на противопожарни уреди",
				nextX,nextY,"arial",16);
		nextX = baseX;
		nextY = nextY - 9;
        setExtraInfoTable(nextX,nextY,num );
      //  nextX = nextX;
        nextY = nextY - extraInfoTable.getTotalHeight() - 8;
        setDynamicTable(nextX,nextY,soMap);
        nextY = nextY - dynamicTable.getTotalHeight();
        setText("Подпис:",nextX,nextY - 10,"arial",9);
        setText("Приел:",nextX,nextY - 20,"arial",9);
        setText("Подпис:",nextX + 400,nextY - 10,"arial",9);
        setText("Клиент:",nextX + 400,nextY - 20,"arial",9);
        document.close();
		return true;
	}
	private boolean createSO_Original(String num) {
		// set document
		document = new Document(PageSize.A4,50.f,50.f,50.f,50.f);
		
		initPdfDocument(num);
		setHeaderTable(baseX, baseY,null,null,null,null,null,null,null,null,null,null);
		float nextX = baseX + 15;
		float nextY = baseY - headerTable.getTotalHeight() - 13;
		setText("Сервизна поръчка за обслужване на противопожарни уреди",
				nextX,nextY,"arialbd",15);
		nextX = baseX;
		nextY = nextY - 7;
        setExtraInfoTable(nextX,nextY,num );
      //  nextX = nextX;
        nextY = nextY - extraInfoTable.getTotalHeight() - 5;
        setDynamicTable(nextX,nextY,new TreeMap<Object,Integer>());
        nextY = nextY - dynamicTable.getTotalHeight();
        setText("Подпис:",nextX,nextY - 10,"arial",9);
        setText("Приел: /",nextX,nextY - 20,"arial",9);
        setText("Подпис:",nextX + 400,nextY - 10,"arial",9);
        setText("Клиент:",nextX + 400,nextY - 20,"arial",9);
       
        document.close();
		return true;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
    SO_Original so = new SO_Original();
    so.createSO_Original("0000000000");
    so.setStampa("0000000000");
	}
	void setText(String text,float x, float y,String font, float size) {
		  content.beginText();
		  content.moveText(x,y);
		  content.setFontAndSize(getBaseFont(font), size);
		  content.showText(text);
		  content.endText();
	}
	void initPdfDocument(String num) {
		// get pdf writer
		
		try {
			
			pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(
					this.targetDirectory +"\\СервизнаПоръчкаT-"+num+".pdf"));
		
			pdfWriter.setBoxSize("art", new Rectangle(36,54,559,788));
			
			
			// catch events
			MyPDFEventHandler pdfEventHandler = new MyPDFEventHandler();
			pdfWriter.setPageEvent(pdfEventHandler);
			
			// open document
		
			document.open();
		
			
			content = pdfWriter.getDirectContent();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			InOutException.showIOException(e);
			IOErrorsWriter.writeIO(e.toString());
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			PDFException.showPDFException(e);
	         PdfErr.pdfErros(e.toString());
			e.printStackTrace();
		}
		//  get cyrylic font 
		this.cyrylic = getFontAndSize("arial",16);
	
	}
	
	void setExtraInfoTable(float x, float y,String num) {
		extraInfoTable = new PdfPTable(1);
		PdfPCell cell = new PdfPCell();
		cell.setPaddingBottom(4);
		cell.setFixedHeight(26);
		Phrase phrase = new Phrase("Място:  " 
		         + "                                               Дата: " + GetDate.getReversedSystemDate()
		         + "                             " + "\u2116 " + num,
				getFontAndSize("arial",12));
		cell.addElement(phrase);
	
		extraInfoTable.addCell(cell);
		extraInfoTable.setTotalWidth(480);
		  
		  try {
				extraInfoTable.setWidths(new int[]{100});
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				PDFException.showPDFException(e);
		         PdfErr.pdfErros(e.toString());
				e.printStackTrace();
			}
		   extraInfoTable.writeSelectedRows(0,-1,x,y,pdfWriter.getDirectContent());
	}
	void setHeaderTable(float x, float y,String setClient,String setCity,String setAddress,
			String setEIK,String setDDS, String setMol,String setBank,String setBIC, String setIBAN
			,String setTel) {
		headerTable = new PdfPTable(2);
		headerTable.setTotalWidth(480); // ???
		  
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
	//	  clientCell.setNoWrap(true);
		  
			clientCell.addElement(new Phrase(14,"Собственик (Клиент):  " + (setClient != null ? setClient : "-")+ "\n",
					getFontAndSize("arial",12)));
			clientCell.addElement(new Phrase(14,"Град: " + (setCity != null ? setCity : "-") + "\n",
					getFontAndSize("arial",12)));
			clientCell.addElement(new Phrase(14,"Адрес: "+ (setAddress != null ? setAddress : "-") + "\n",
					getFontAndSize("arial",12)));
		
			clientCell.addElement(new Phrase(14,"ЕИК:" + (setEIK != null ? setEIK : "-") + "\n",
					getFontAndSize("arial",12)));
			clientCell.addElement(new Phrase(14,"ДДС \u2116 " + (setDDS  != null ? setDDS : "-") + "\n",
					getFontAndSize("arial",12)));
			clientCell.addElement(new Phrase(14,"МОЛ: " + (setMol != null ? setMol : "-") + "\n",
					getFontAndSize("arial",12)));
			clientCell.addElement(new Phrase(14,"Банка: "+ (setBank != null ? setBank : "-") + "\n",
					getFontAndSize("arial",12)));
			clientCell.addElement(new Phrase(14,"BIC: " + (setBIC != null ? setBIC : "-") + "\n",
					getFontAndSize("arial",12)));
			
			Font f = getFontAndSize("arial",12);
		    clientCell.addElement(new Phrase(14,"IBAN:" + (setIBAN != null ? setIBAN : "-") + "\n",
					f));


		clientCell.addElement(new Phrase(14,"Тел: " +( setTel != null ? setTel : "-") ,getFontAndSize("arial",12)));
		
			PdfPCell sallerCell = new PdfPCell();
			sallerCell.addElement(new Phrase(14,"Доставчик: "	+ MainPanel.SALLER_NAME + "\n" ,
					getFontAndSize("arial",12)));
		     sallerCell.addElement(new Phrase(14,"Разрешение \u2116 477/27.08.2015г.",
	            		getFontAndSize("arial",12)));
            sallerCell.addElement(new Phrase(14,"Град: " + MainPanel.SALLER_CITY + "\n",
            		getFontAndSize("arial",12)));
            sallerCell.addElement(new Phrase(14,"Адрес: " + MainPanel.SALLER_ADDRESS + "\n",
            		getFontAndSize("arial",12)));
            sallerCell.addElement(new Phrase(14,"ЕИК: " + MainPanel.SALLER_EIK + "\n",
            		getFontAndSize("arial",12)));
            sallerCell.addElement(new Phrase(14,"ДДС \u2116 BG" + MainPanel.SALLER_EIK + "\n",
            		getFontAndSize("arial",12)));
            sallerCell.addElement(new Phrase(14,"Мол: " + MainPanel.SALLER_MOL + "\n",
            		getFontAndSize("arial",12)));
            sallerCell.addElement(new Phrase(14,"Банка: " + MainPanel.SALLER_BANK + "\n",
            		getFontAndSize("arial",12)));
            sallerCell.addElement(new Phrase(14,"BIC: " + MainPanel.SALLER_BIC + "\n",
            		getFontAndSize("arial",12)));
            sallerCell.addElement(new Phrase(14,"IBAN: " + MainPanel.SALLER_IBAN + "\n",
            		getFontAndSize("arial",12)));
		  
		  headerTable.addCell(clientCell);
		  headerTable.addCell(sallerCell);
		  
		  headerTable.setTotalWidth(480);
		  
		  try {
				headerTable.setWidths(new int[]{100,100});
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				PDFException.showPDFException(e);
		         PdfErr.pdfErros(e.toString());
				e.printStackTrace();
			}
		   headerTable.writeSelectedRows(0,-1,baseX,baseY,pdfWriter.getDirectContent());
		  
		   
	}

	
	void setDynamicTable(float x, float y,TreeMap<Object,Integer> soMap) {

		 dynamicTable = new PdfPTable(4);
		  dynamicTable.setTotalWidth(480);
		  
		  try {
				dynamicTable.setWidths(new int[]{14,130,30,110});
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				PDFException.showPDFException(e);
		         PdfErr.pdfErros(e.toString());
				e.printStackTrace();
			}
		
		  PdfPCell n = new PdfPCell(new Phrase("\u2116",getFontAndSize("arial",13)));
		  n.setPaddingLeft(5);
	
		  PdfPCell model = new PdfPCell(new Phrase("Вид на противопожарния уред",
				  getFontAndSize("arial",13)));
		  model.setPaddingLeft(20);
		  
		  PdfPCell quantity = new PdfPCell(new Phrase("К-во",
				  getFontAndSize("arial",13)));
		  quantity.setPaddingLeft(12);
		  
		  PdfPCell warn = new PdfPCell(new Phrase("Забележка",
				  getFontAndSize("arial",13)));
		  warn.setPaddingLeft(60);

		  dynamicTable.addCell(n);
		  dynamicTable.addCell(model);
		  dynamicTable.addCell(quantity);
		  dynamicTable.addCell(warn);
		  
		  float sumOfRows = dynamicTable.getRowHeight(0);
		  float nextY = y;
		  int from = 0;
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
				  case 1 :   dynamicTable.addCell(" " + row); break;
				  case 2:  PdfPCell cell = new PdfPCell(new Phrase(вид,getFontAndSize("arial",12))); 
				  dynamicTable.addCell(cell);break;
				  case 3 : PdfPCell cell2 = new PdfPCell(new Phrase(sum+"бр.",getFontAndSize("arial",12))); 
				  cell2.setPaddingLeft(12);
				  dynamicTable.addCell(cell2);break;
				  case 4: dynamicTable.addCell("");break;
				  default:break;
				  }
				}
			// check if should go to the next page
			  sumOfRows += dynamicTable.getRowHeight(row);
			  if( (y - (sumOfRows))
      			   < document.bottom()) {
      		    sumOfRows = 0;
      		    dynamicTable.writeSelectedRows(0,-1,from,row,x,nextY,pdfWriter.getDirectContent());
      		    nextY= 820;
      		    from = row;
      		    document.newPage();
      	   }
			  row++;
		  }
		 
		  
		  
		  PdfPCell empty = new PdfPCell(new Phrase("",this.cyrylic));
		  PdfPCell all = new PdfPCell(new Phrase("Общо получени " +
		  		"пожарогасители за сервизно обслужване",getFontAndSize("arial",12)));
		  PdfPCell amounts = new PdfPCell(new Phrase(allSum+"бр.",getFontAndSize("arial",12)));
		  amounts.setPaddingLeft(12);
		  
		  dynamicTable.addCell(empty);
		  dynamicTable.addCell(all);
		  dynamicTable.addCell(amounts);
		  dynamicTable.addCell(empty);
		  
        dynamicTable.setTotalWidth(480);
		  
		  try {
				dynamicTable.setWidths(new int[]{14,130,30,110});
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				PDFException.showPDFException(e);
		         PdfErr.pdfErros(e.toString());
			//	e.printStackTrace();
			}
		   dynamicTable.writeSelectedRows(0,-1,from,-1,x,nextY,pdfWriter.getDirectContent());
		 
		   
	}
	
	public void setStampa(String num) {
		 PdfReader pdfReader;
		 PdfStamper stamper;
		try {
			pdfReader = new PdfReader(
					this.targetDirectory +"\\СервизнаПоръчкаT-"+num+".pdf");

	         stamper = new PdfStamper(pdfReader,
	                 new FileOutputStream(this.targetDirectory +"\\СервизнаПоръчка-"+num+".pdf"));

	         Image image = Image.getInstance(logoPath);
	         PdfContentByte content = stamper.getUnderContent(1);
            image.setAbsolutePosition(480f, baseY - 35);
         
            content.addImage(image);
            
            stamper.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         System.out.println("done!");
	}
}
*/