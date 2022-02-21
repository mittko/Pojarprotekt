package PDF.Brack;

import Exceptions.InOutException;
import Exceptions.PDFException;
import Log.IOErrorsWriter;
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

import javax.swing.table.DefaultTableModel;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class BrackPDF extends PdfCreator{
	
		private float endY = 820;
	    private float tablePos;
	    private float nextTablePos;
	    private float sumOfRows = 0.0f;
	    private int from = 0;
	    private Document document;
	    private PdfWriter writer;
	    private PdfContentByte pcb;
	    private String brackDate;
	    
	    public BrackPDF() {
		   super();
	   }
		
		public boolean createBrak(DefaultTableModel dm,String[] clData,HashMap<String,ArrayList<Object>> pricini,
				String timeStamp,String brackNumber,
				int startIndex,int endIndex, String brackDate) {
			
			this.brackDate = brackDate;
			
		    document = new Document(PageSize.A4,50,50,50,50);
		    writer = null;
		  
		    try {
				writer = PdfWriter.getInstance(document,
						new FileOutputStream(MainPanel.BRACK_PDF_PATH+
								"\\����-"+ timeStamp + "-" + brackNumber+".pdf"));
				writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
				
				// set nums of pages
				MyPDFEventHandler pdfEventHandler = new MyPDFEventHandler();
				writer.setPageEvent(pdfEventHandler);
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
			InOutException.showIOException(e);
			IOErrorsWriter.writeIO(e.toString());
			return false;
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
			PDFException.showPDFException(e);
	         PdfErr.pdfErros(e.toString());
			//	e.printStackTrace();
	         return false;
			}
		   
			document.open();
			
		    pcb = writer.getDirectContent();
			
			setText("������� 8121�-531, ���������� \u2116 9 ��� ��.31, �� 51",300,endY,"arial",10);
			
			String protokolText = "�������� No: "+brackNumber;
			setText(protokolText,
					(document.right()  - 100) / 2,endY -= 30,"arialbd",12);
	
	        setText("�� ��������� � �������� �� ��������������, �� ����� � ����������" +
		     		" ������������ �� �� �����������",50,endY -= 20,"arialbd",10);		
		    
	        setText("���� "+ brackDate +" � ��. �������, ������������� (�������������� ������������)  " +
		     		" " + MainPanel.personName + " ",70,endY -= 20,"arial",10);
	        
		    setText("��   \"������������\"  ���, ��. �����, ��� ��������, �.� \"�����-10\" ��. ���. �. ����� 14, ���: 201 775 049,",
		    		60,endY -= 10, "arial",10);
		  
		    setText("������� �� ����������� (��� ����� ������������) ����������������, �� ����� � ���������� ��������:"
		    		,60,endY -= 10,"arial",10);
		   
		    
		  /*  pcb.beginText();
	 	    pcb.moveText(60, endY -= 10);
	 	    pcb.endText();
	 	    
	 
		    for(Object pr : pricini) {
		    	pcb.beginText();
		 	    pcb.moveText(60, endY -= 10);
		 	    pcb.setFontAndSize(getBaseFont("arial"), 9);
		 	    pcb.showText("������ " + pr+"");
		 	    pcb.endText();
		    }*/
		   
		    PdfPTable table = new PdfPTable(6);
		    table.setTotalWidth(510);
		    
		    try {
				table.setWidths(new float[]{32f,133f,32f,27f,48f,120f});
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				PDFException.showPDFException(e);
		         PdfErr.pdfErros(e.toString());
				e.printStackTrace();
		         return false;
			}
		  
		    Font arial10 = getFontAndSize("arial",10);
		    Font arial9 = getFontAndSize("arial",9);
		    PdfPCell num = new PdfPCell(new Phrase("No: �� \n���",arial10));
		    num.setHorizontalAlignment(Element.ALIGN_LEFT);
		    num.setNoWrap(true);
		    
		    PdfPCell model = new PdfPCell(new Phrase("���������������� ����������\n�� " +
		    		"����� ������������� (�����,\n�����, ������ ����� � ��.)",arial10));
		    model.setHorizontalAlignment(Element.ALIGN_LEFT);
		    model.setNoWrap(true);
		    
		    PdfPCell category = new PdfPCell(new Phrase("����-\n�����\n�����-\n���\n�.4.3.2.2\n��" +
		    		" ���\n ICO \n11602\n-2:2002",arial9));
		    category.setHorizontalAlignment(Element.ALIGN_LEFT);
		    category.setNoWrap(true);
		    
		    PdfPCell wheight = new PdfPCell(new Phrase("����\n�� ��-\n����-\n��� ��-\n����-\n����-\n���,\nkg,I",arial9));
		    wheight.setHorizontalAlignment(Element.ALIGN_LEFT);
		    wheight.setNoWrap(true);
		    
		    PdfPCell entity = new PdfPCell(new Phrase("������-\n"
		    		+ "������-\n"
		    		+ "��� ��-\n"
		    		+ "������\n"
		    		+ "(����,"
		    		+ "\n ���� ,"
		    		+ "\nCO2 "
		    		+ "\n��� ��.)",arial10));
		    entity.setHorizontalAlignment(Element.ALIGN_MIDDLE);
		    entity.setNoWrap(true);
		    
		    PdfPCell reasons = new PdfPCell(new Phrase("��������� �� ���������",arial10));
		    table.addCell(num);
		    table.addCell(model);
		    table.addCell(category);
		    table.addCell(wheight);
		    table.addCell(entity);
		    table.addCell(reasons);
	
		     int row = 0;
		     int numer = 0;
		     int stop = endIndex;
		     sumOfRows += table.getRowHeight(0);
		     tablePos = endY - 10;
		     nextTablePos = endY - 10;
		     int help = 0;
		    
		//     Font arial9 = getFontAndSize("arial",9);
		     for(row = 0;row < stop;row++) {
		    	
		    	          if(tablePos - (sumOfRows) < document.bottom()) {
		            	   sumOfRows = 0;
		            	   table.writeSelectedRows(0,-1,from,row + 1,100,nextTablePos,writer.getDirectContent());
		            	   nextTablePos = 820;
		            	   from = row + 1;
		            	   document.newPage();
		               }
		    	      
		    	          PdfPCell rowCell = new PdfPCell(new Phrase(row + 1 +"",arial10));
		    	          rowCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		    	          
		        	      table.addCell(rowCell); // column 0 
		        	  
		        	      PdfPCell brandCell = new PdfPCell(new Phrase(dm.getValueAt(row + startIndex, 6)+" "
		  		        		+ dm.getValueAt(row + startIndex, 4),arial10));
		        	      brandCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		        	
		        	      
		        	   table.addCell(brandCell); // column1 ����� + ������ �����
		        	
		        	   PdfPCell catCell = new PdfPCell(new Phrase(dm.getValueAt(row + startIndex, 5)+"",arial10));
		        	   catCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		        	  
		        	 table.addCell(catCell); //column 2 ���������
		        	
		        	  String str = dm.getValueAt(row + startIndex, 2).toString();
		        	  if(str.contains("�����")) {
		        		 str = str.replace("�����", "�");
		        	  } 
		     
		        	 PdfPCell wheightCell = new PdfPCell(new Phrase(str,arial10));
		        	 wheightCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		        	
		        	  table.addCell(wheightCell); // column 3 ����
		        	
		        	  String str2 = dm.getValueAt(row + startIndex, 1).toString();
		        	  if(str2.contains("������")) {
		        		  str2 = str2.replace("������", "����");
		        	  }
		        	  PdfPCell entityCell = new PdfPCell(new Phrase(str2,arial10));
		        	  entityCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		        	
		        	  table.addCell(entityCell); // column 4 ���. �-��
		        	 
		        	  // key is a barcod
		        	  ArrayList<Object> pricina = pricini.get(dm.getValueAt(row + startIndex, 3)+""); 
		        	 if(pricina == null || pricina.size() == 0) {
		        		  PdfPCell pricinaCell = new PdfPCell(new Phrase("",arial10));
			        	  pricinaCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			        	  table.addCell(pricinaCell);
			        	 } else {
			        	  PdfPCell pricinaCell = new PdfPCell(new Phrase(pricina.get(0).toString(),arial10));
			        	  pricinaCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			        	  table.addCell(pricinaCell);
			        	 }
		        	
		        	  //
		        	  
		        	     sumOfRows += table.getRowHeight(row + 1);
		        	  
		     }
		    
		   
		    
		     try {
					table.setWidths(new float[]{32f,133f,32f,27f,48f,180f});
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					PDFException.showPDFException(e);
			         PdfErr.pdfErros(e.toString());
					e.printStackTrace();
			         return false;
				}
		     table.writeSelectedRows(0, -1, from,-1,50,nextTablePos,writer.getDirectContent());
		     
		     endY =   nextTablePos -  (sumOfRows + 25)  ; //document.top() - (sumOfRows + (document.top() - nextTablePos + 25));            //endY - ((int)table.getTotalHeight()+25);
		     
		     setText("�� ���� �������� / ����� ���������.",
		    		 (document.right() - 120) / 2,endY,"arial",10);
		     
		     setText("���� �������������� �� ���� - " + stop + " ��.", 
		    		 (document.right() - 120) / 2,endY - 15,"arial",10);
		     
		     setFootText(endY - 35, clData);
		 
			document.close();
			
			return true;
		}
		
		/**
		 * @param args
		 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BrackPDF br = new BrackPDF();
		br.createBrak(new DefaultTableModel(),new String[11],
				new HashMap<String,ArrayList<Object>>(), MyGetDate.getTimeStamp(),"0000000",
				0,0,  MyGetDate.getReversedSystemDate());
	}
	private void setFootText(float y,String[] clData) {
		 float[] footX = new float[]{20,20, 20, 20,100,  350,20,  11,  29,170,170,170,300,300,420,300,400,20,20};
	     float[] footY = new float[]{  5,20, 40, 75,  90,  90,105,120,135,105,135,150,105,120,105,135,150,165,180};
	
	    
	     for(int s = 3;s < footY.length;s++) {
	    	 footY[s] -= 20;
	     }
	     String[] text = new String[]{
	    		 "���������� �� ��������������/��� " + getDetail(clData[0]) + "  ���: " + getDetail(clData[4]),
	    	" ���: " + getDetail(clData[1]) + " "+   (!getDetail(clData[2]).equals("")
					? ("     ���� " + getDetail(clData[2])) : "") + (!getDetail(clData[3]).equals("")
					? ("      ����� " + getDetail(clData[3])) : ""),
	    		 "���� �������� �� ������� � ��� ����������� ���������� - �� ���� �� �������������, ��������� ������������, ",
	    		 "� �� ����������� �� ��������������/���.",
	    		 "      ������:",
	    		 "" ,
	    		 "     (�����������/�����������",
	    		 "����������� �� �������������, ",
	    		 "    ��������� ������������)",
	    		 "(������, �����)",
	    		 "���� ������������",
	    			"(���, �������)",

	    		"( ����������/�������-",
	    		 "����� �� �����������)",
	    		 "(������)",
	    		 "�����: " + getDetail(clData[4]) + " ,���",
	            "(���, �������, ��������)",
	    		 "���������: ���������� �� ��������� �� ������� �� ���������� �� ���������� ���������� ����������,",
	    		 "������������ ��� ������������� ���������.",
	    		 };

	   
	       boolean newPage = false;
		     for(int i = 0;i < footX.length;i++) {
		       if(y - footY[i] <= document.bottom() ) {// + 30 ???? da probvam da razbera zashto
		    	          newPage = true;
		    	           document.newPage();
		    	           break;
		        } 
		     }

	              if(newPage) {
	    		   y =  document.top();
	              }
	    	 for(int i1 = 0;i1 < footX.length;i1++) {
	    	
	    		  if(i1 == 0) {
			    	   setText(text[i1],footX[i1],y-footY[i1],"arialbd",10);//text[i1]
			       } else  {
					       if(  (i1 >= 6 && i1 <= 16 && i1 != 10) ) {
					    	   setText(text[i1],footX[i1],y-footY[i1],"italic",9);//text[i1]
					       } else {
					          setText(text[i1],footX[i1],y-footY[i1],"arial",10);// text[i1];
					       }
			        }
	    	 }
	    	
	     }
	/* ������ ����� - ������
	 * private void setFootText(float y,String[] clData) {
	    float[] footX = new float[]{20,20,20,100,20,11,29,170,170,170,350,300,300,420,400,400};
	     float[] footY = new float[]{10,30,45,75,90,105,120,90,120,135,75,90,105,90,75,135};
	
	   
	     String[] text = new String[]{
	    	       //	 "�������� ������� I�-2377 ���� ��������� �������� �������� �����.",
	    		 "���������� �� ��������������/���:            " +
	    	    		   clData[0],
	    		 "���� �������� �� ������� � ��� ����������� ���������� - �� ���� �� �������������, ��������� ������������, ",
	    		 "� �� ����������� �� ��������������/���.",
	    		 "      ������:",
	    		 "     (�����������/�����������",
	    		 "����������� �� �������������, ",
	    		 "    ��������� ������������)",
	    		 "(������, �����)",
	    		 "������ �����",
	    			"(���, �������)",
	    		 "      �����: ",
	    		"( ����������/�������-",
	    		 "����� �� �����������)",
	    		 "(������)",
	    		 "",
	            "(���, �������, ��������)"
	    		 };
	     
	     for(int i = 0;i < footX.length;i++) {
	       if(y - footY[i] <= document.bottom()) {
	    		  document.newPage();
	    		   y = 820;
	        }
	       footX[i] += 30;
	       if(i == 0) {
	    	   setText(text[i],footX[i],y-footY[i],"arialbd",10);
	       } else
	       if( (i >= 4 && i <= 7) || i == 9 || ( i >= 11 && i <= 15) ) {
	    	   setText(text[i],footX[i],y-footY[i],"italic",9);
	       } else {
	          setText(text[i],footX[i],y-footY[i],"arial",10);
	       }
	     }
	}*/
	private void setText(String text,float x, float y,String font, float size) {
		  pcb.beginText();
		  pcb.moveText(x,y);
		  pcb.setFontAndSize(getBaseFont(font), size);
		  pcb.showText(text);
		  pcb.endText();
	}
	private String getDetail(String str) {
		return str != null ? str : "          ";
	}
}
