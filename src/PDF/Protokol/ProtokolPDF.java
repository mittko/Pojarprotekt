//package PDF.Protokol;
//
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.util.TreeMap;
//
//import javax.swing.table.DefaultTableModel;
//
//import utility.MainPanel;
//import Date.GetDate;
//import Exceptions.InOutException;
//import Exceptions.PDFException;
//import Log.IOErrorsWriter;
//import Log.PdfErr;
//import PDF.MyPDFEventHandler;
//import PDF.PdfCreator;
//
//import com.itextpdf.text.BadElementException;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.Image;
//import com.itextpdf.text.PageSize;
//import com.itextpdf.text.Phrase;
//import com.itextpdf.text.Rectangle;
//import com.itextpdf.text.pdf.PdfContentByte;
//import com.itextpdf.text.pdf.PdfPCell;
//import com.itextpdf.text.pdf.PdfPTable;
//import com.itextpdf.text.pdf.PdfWriter;
//
//public class ProtokolPDF extends PdfCreator {
//
//	
//	private final String target = MainPanel.PROTOKOL_PDF_PATH;
////	private final String target = System.getProperty("user.home")+"/Desktop/";
//	private float X = 320;
//	private float Y = PageSize.A4.getHeight() - 25;//= 692.0
//	private Document document;
//	private PdfWriter writer;
//	private PdfContentByte content;
//	private FileOutputStream fos;
//	private PdfPTable dynamicTable;
//	private float dynamicTablePos;
//	private String protokolDate;
//	private Image technikImage;
//	private Image technikImage2;
//	private Image technikImage3;
//	private Image technikImage4;
//	public ProtokolPDF() {}
//	
//	public boolean processPdf(DefaultTableModel dm,TreeMap<Object,Integer> PARTS
//	 		   ,String[] clData,String protokolNumber,String timeStamp,
//	 		   int startIndex,
//	 		   int endIndex,
//	 		   String protokolDate) {
//		
//         this.protokolDate = protokolDate;
//			
//		if(init(timeStamp,protokolNumber) == false){
//			return false;
//		}
//	
//		setText("НАРЕДБА 8121з-531, Приложение \u2116 9 към чл.31, ал 5", X,Y,"arial",9);
//		X = (document.right() - 50 ) / 2;
//		Y = Y - 30;
//		setText("ПРОТОКОЛ \u2116 " + protokolNumber, X,Y,"arialbd",9 );
//		X = 30;
//		Y = Y - 15;
//		setText("за предаване и приемане на пожарогасители, на които е извършено техническо обслужване, презареждане",X,Y,"arialbd",9);
//		X = 40;
//		Y = Y - 15;
//		setText("или хидростатично изпитване на устойчивост на налягане (в комбинация или поотделно)",X,Y,"arialbd",9);
//		X = 45;
//		Y = Y - 25;
//		setText("Днес, " + protokolDate + " в гр. Дупница, упълномощеният представител " + "Петя Мертакчийска" + " на " + "\"" + MainPanel.SALLER_NAME +"\" ",X,Y,"arial",10);
//		X = 20;
//		Y = Y - 15;
//		setText(MainPanel.SALLER_ADDRESS + " ЕИК: " + 
//					MainPanel.SALLER_EIK + " тел: " + 
//				MainPanel.SALLER_PERSON_TELEFON  + " " + " предаде на собственика" ,X,Y,"arial",10);
//	//	setText("“ПОЖАРПРОТЕКТ“ ООД ж.к “Люлин-10“ ул. Ген. Н. Генев 14, ЕИК: 201 775 049, предаде на собственика (или негов",X,Y,"arial",10);
//		X = 20;
//		Y = Y - 15;
//		setText("(или негов представител) пожарогасителите , на които е извършено обслужване, както следва:",X,Y,"arial",10);
//		Y = Y - 10;
//	
//		if(setDynamicTable(X, Y,dm, PARTS,
//				clData,protokolNumber,startIndex,endIndex) == false) {
//		return false;
//		}
//		
//		finish();
//		return true;
//	}
//	public static void main(String[] args) {
//	
//		// TODO Auto-generated method stub
//		ProtokolPDF pdf = new ProtokolPDF();
////        pdf.processPdf(new DefaultTableModel(),new TreeMap<Object,Integer>(),
////        		new String[11],"0000000",GetDate.getTimeStamp(),0,0, GetDate.getReversedSystemDate());
//	   pdf.init("", "");
//	   System.out.println(10 -5 - 2);
//	}
//	
//
//	private boolean setDynamicTable(float x, float y,
//			DefaultTableModel dm,TreeMap<Object,Integer> PARTS
// 		   ,String[] clData,String protokolNumber,int startIndex,int endIndex) {
//		 
//	
//		dynamicTable = new PdfPTable(11);
//		Font font9 = getFontAndSize("arial",9);
//		Font font8 = getFontAndSize("arial",8);
//		Font font7 = getFontAndSize("arial",7);
//		 PdfPCell n = new PdfPCell(new Phrase("\u2116 по\nред",font9));
//		 n.setNoWrap(true);
//		    PdfPCell mark = new PdfPCell(new Phrase(
//		    		"Идентификационна маркировка\n"
//		    		+ " на всеки пожарогасител (марка,\n"
//		    		+ "модел,сериен номер и др)",font9));
//		    mark.setNoWrap(true);
//		    PdfPCell category = new PdfPCell(new Phrase("Катего\n   рия\nна пож.\nсъглас\n  но т.\n4.3.2.2\nот БДС\n " +
//		    		" ISO\n11602-\n2:2002",font9));
//		    category.setNoWrap(true);
//		    PdfPCell wheight = new PdfPCell(new Phrase("Маса на\n  заре-\n  дения\n  пожа\n  рога-\n  сител,\n  kg.",font9));
//		    wheight.setNoWrap(true);
//		    PdfPCell antifire = new PdfPCell(new Phrase("  Вид на\n пожарога\n сителното\nвещество\n(вода,прах,\n CO2 или\n   др.)",font9));
//		    antifire.setNoWrap(true);
//		    PdfPCell number = new PdfPCell(new Phrase("   Търговско\nнаименование\nна " +
//		    		"пожарогаси-\n  телното\nвещество (при\nпрезареждане\n  с прах или\n пенообразу-\n  вател)",font7));
//		    number.setNoWrap(true);
//		    PdfPCell serving = new PdfPCell(new Phrase("Вид на"
//		    		+ "\nизвърше-"
//		    		+ "\nното "
//		    		+ "обслу-"
//		    		+ "\nжване"
//		    		+ "\n(техни-\n"
//		    		+ "ческо\n"
//		    		+ "обслу-\nжване"
//		    		+ "\n(ТО),пре-"
//		    		+ "\nзарежда-"
//		    		+ "\nне (П), или"
//		    		+ "\nхидроста-"
//		    		+ "\nтично"
//		    		+ "\nизпитва-\n"
//		    		+ "не на\n"
//		    		+ "устойчи-\n"
//		    		+ "вост на"
//		    		+ "\nналяга-\n"
//		    		+ "не (ХИ)"/*- в \n"
//		    		+ "комбина-\n"
//		    		+ "ция или-\n"
//		    		+ "поотделно )"*/,font7));
//		    serving.setNoWrap(true);
//		    PdfPCell date = new PdfPCell(new Phrase("Дата на\nизвърше-\n  ното\n обслуж-\n  ване",font8));
//		    date.setNoWrap(true);
//		    PdfPCell name = new PdfPCell(new Phrase("  Име на\n  лицето,\nизвършило\n обслужва-\n   нето",font7));
//		    name.setNoWrap(true);
//		    PdfPCell signature = new PdfPCell(new Phrase("Подпис\n   на\nлицето,\n извър-\n шило\nобслуж-\nването",font8));
//		    signature.setNoWrap(true);
//		    PdfPCell stikernumber = new PdfPCell(new Phrase(" Номер\n    на\n стикер",font9));
//		    stikernumber.setNoWrap(true);
//		     
//		
//		     dynamicTable.setTotalWidth(540);
//		    
//		     dynamicTable.addCell(n);
//		     dynamicTable.addCell(mark);
//		     dynamicTable.addCell(category);
//		     dynamicTable.addCell(wheight);
//		     dynamicTable.addCell(antifire);
//		     dynamicTable.addCell(number);
//		     dynamicTable.addCell(serving);
//		     dynamicTable.addCell(date);
//		     dynamicTable.addCell(name);
//		     dynamicTable.addCell(signature);
//		     dynamicTable.addCell(stikernumber);
//		     
//		     try {
//					dynamicTable.setWidths(new float[]{3.5f,18f,4.3f,5f,6.5f,7f,5.5f,5f,5f,4.3f,5f});
//				} catch (DocumentException e) {
//					// TODO Auto-generated catch block
//					 PDFException.showPDFException(e);
//			         PdfErr.pdfErros(e.toString());
//					e.printStackTrace();
//					return false;
//				}
//	
//		     int sumOfRows = 0;
//		     int fromRows = 0; 
//		     int stop = endIndex;
//		      int toRows = 2;
//		      int numer = 0;
//		      
//		     sumOfRows += dynamicTable.getRowHeight(0);
//		     
//		     Font font10 =  getFontAndSize("arial",10);
//		     // инициализиране на колоните
//		     for(int nomer = 1;nomer <= 11;nomer++) {
//		     Phrase p1 = new Phrase(nomer+"",font10);
//		     PdfPCell cell = new PdfPCell(p1);
//		     cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		     dynamicTable.addCell(cell);
//		     }
//		      sumOfRows += dynamicTable.getRowHeight(1);
//		      
//		   
//		   //   for(int cycle = 0;cycle < 5;cycle++) {
//		      for(int row = 0;row < stop;row++) {
//		    	     
//		    	 toRows++;
//		    	 
//		    	  PdfPCell cell1 = new PdfPCell(new Phrase((++numer +""),font9));
//		    	  cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
//		    	
//	        	   dynamicTable.addCell(cell1);   // column 1
//	        	   
//	        	   PdfPCell cell2 = new PdfPCell(new Phrase(dm.getValueAt(row + startIndex,6)+
//	        			   " "+dm.getValueAt(row+startIndex, 4),font9));
//	      
//	       
//	        	   dynamicTable.addCell(cell2);  //  column 2 марка + монтажен номер
//	        	   
//	        	   PdfPCell cell3 = new PdfPCell(new Phrase(dm.getValueAt(row + startIndex, 5)+"",font9));
//	        	   cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
//	        	 
//	        	   dynamicTable.addCell(cell3); // column 3 категория
//	        	   
//	        	   String wheightStr = dm.getValueAt(row + startIndex, 2).toString();
//	        	   if(wheightStr.contains("литра")) {
//	        		   wheightStr = wheightStr.replace("литра", "л");
//	        	   }
//	        	   PdfPCell cell4 = new PdfPCell(new Phrase(wheightStr,font9));
//	        	   cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
//	        	
//	        	   dynamicTable.addCell(cell4); // column 4 маса
//	        	   
//	        	   // column 0 пож. в-во
//	        	   String type = dm.getValueAt(row+startIndex, 1).toString();
//	        
//	        	   // filter 
//	        	   if(type.toUpperCase().contains("ABC") || type.toUpperCase().contains("АВС")) {
//		       			type = MainPanel.type_Prah_ABC;
//		       		} else if(type.toUpperCase().contains("BC") || type.toUpperCase().contains("ВС")) {
//		       			type = MainPanel.type_Prah_BC;
//		       		} else if(type.toLowerCase().contains("воден")) {
//		       			type = MainPanel.type_Water;
//		       		}else if(type.toLowerCase().contains("водопенен")) {
//		       			type = MainPanel.type_Water_Fame;
//		       		} else if(type.toUpperCase().contains("CO2") || type.toUpperCase().contains("СО2")) {
//		       			type = MainPanel.type_CO2;
//		       		}
//	        	
//	        		
//	        	   switch(type) {
//	        	   case  MainPanel.type_Prah_ABC : 
//	        		   PdfPCell cell5 = new PdfPCell(new Phrase("прах АВС",font8));
//	        		   cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
//	        		   dynamicTable.addCell(cell5); 
//	        		   break;
//	        	   case MainPanel.type_Prah_BC :  
//	        		   PdfPCell cell6 = new PdfPCell(new Phrase("прах ВС", font8));
//	        		   cell6.setHorizontalAlignment(Element.ALIGN_LEFT);
//	        		   dynamicTable.addCell(cell6);
//	        		   break;
//	        	   case MainPanel.type_Water :  
//	        		   PdfPCell cell7 = new PdfPCell(new Phrase("воден"
//		        			   ,font8));
//	        		   cell7.setHorizontalAlignment(Element.ALIGN_LEFT);
//	        		   dynamicTable.addCell(cell7); 
//	        		   break;
//	        	   case MainPanel.type_Water_Fame :  
//	        		   PdfPCell cell8 = new PdfPCell(new Phrase("водопенен",font8));
//	        		   cell8.setHorizontalAlignment(Element.ALIGN_LEFT);
//	        		   dynamicTable.addCell(cell8); 
//	        	   break;
//	        	   case MainPanel.type_CO2 : 
//	        		   PdfPCell cell9 = new PdfPCell(new Phrase("CO2"
//		        			   ,font8));
//	        		   cell9.setHorizontalAlignment(Element.ALIGN_LEFT);
//	        		   dynamicTable.addCell(cell9);
//	        	   break;
//	        	   default :
//	        		   break;
//	        	   }
//	        	  
//	        	  // column 6 номер на разрешителното за гасителна ефективност 
//	       
//	       	  String tehnichesko_obslujvane = dm.getValueAt(row + startIndex, 7)+""; // техническо обслужване
//	          String prezarejdane = dm.getValueAt(row + startIndex, 8)+""; // презареждане
//	          String hidrostatichno_izpitvane = dm.getValueAt(row + startIndex, 9)+""; // хидростатично изпитване
//	          String nomerRazreshitelno  = ""; // номер на разрешителното
//	          String Obslujvane = "";
//	     
//	          boolean comma = false;
//	          // make type obslujvane
//	          if(!tehnichesko_obslujvane.equals("не"))  {
//	        	  Obslujvane += "ТО";
//	        	  comma = true;
//	          }
//	          if(!prezarejdane.equals("не")) {
//	        	  if(comma) {
//	        		  Obslujvane += ",";
//	        	  }
//	        	  Obslujvane += "П ";
//	        	  comma = true;
//	          }
//	          
//	       
//	       
//	          if( !hidrostatichno_izpitvane.equals("не")) {
//	        	   /// впиши в протокола само ако хидростатичното
//	 	         /// изпитание е направено сега
//	 	         String izminaliDniOtHI = 
//	 	        		 GetDate.getUrgentDays(GetDate.getReversedSystemDate(), 
//	 	        				hidrostatichno_izpitvane);
//	 	      //   System.out.printf("izminaliDni = %s ",izminaliDniOtHI);
//	 	         int izminaliDni = Integer.parseInt(izminaliDniOtHI);
//		 	         if(izminaliDni >= 1460) { // направено е тази година 4 * 365
//			        	  if(comma) {
//			        		  Obslujvane += ",";
//			        	  }
//			        	  Obslujvane += "ХИ";
//			 	       }
//		          }
//	          // постави номер на разрешителното
//	      
//	        	  if(type.equals(MainPanel.type_Prah_BC) && (!tehnichesko_obslujvane.equals("") && !prezarejdane.equals(""))) {
//	        		  nomerRazreshitelno = "Кобра ABC";
//	        	  } else if(type.equals(MainPanel.type_Prah_ABC) && (!tehnichesko_obslujvane.equals("") && !prezarejdane.equals(""))) {
//	        		  nomerRazreshitelno = "Кобра ABC";
//	        	  } else if(type.equals(MainPanel.type_Water) && (!tehnichesko_obslujvane.equals("") && !prezarejdane.equals(""))) {
//	        		 nomerRazreshitelno = "Вода";
//	        	  } else   if(type.equals(MainPanel.type_Water_Fame) && (!tehnichesko_obslujvane.equals("") && !prezarejdane.equals(""))) {
//	        		  nomerRazreshitelno = "STHAMEX";
//	        	  } else if(type.equals(MainPanel.type_CO2) && (!tehnichesko_obslujvane.equals("") && !prezarejdane.equals(""))){
//	        		  nomerRazreshitelno = "CO2";
//	        	  } else {
//	        		  nomerRazreshitelno = "";
//	        	  }
//	        	  //
//	        	  PdfPCell cell10 = new PdfPCell(new Phrase(nomerRazreshitelno,font8));
//	        	  cell10.setHorizontalAlignment(Element.ALIGN_CENTER);
//	        	  dynamicTable.addCell(cell10);
//	        	  
//	        	  PdfPCell cell11 = new PdfPCell(new Phrase(Obslujvane,font9));
//	        	  cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
//	        	  
//	        	  dynamicTable.addCell(cell11);// column 7 обслужване 
//	        	  
//	        	  PdfPCell cell12 = new PdfPCell(new Phrase(protokolDate,font7));
//	        	  cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
//	        	  
//	        	  dynamicTable.addCell(cell12); // column 8 дата
//	        	  
//	        	  // set name of person
//	        	  if(dm.getColumnCount() >= 13) {
//	        		  // generira se ot spravki
//	        			String technikName = dm.getValueAt(row + startIndex, 13).toString();
//	        			PdfPCell cell13 = new PdfPCell(new Phrase(technikName, font7));
//			        	  
//			            cell13.setHorizontalAlignment(Element.ALIGN_LEFT);
//			        	dynamicTable.addCell(cell13); //
//			       
//			        	  if(technikName.equals("Георги Ковачки")) {
//			            	  PdfPCell cell = new PdfPCell(technikImage,true);
//				        	  dynamicTable.addCell(cell); // column 10 подпис на лицето извършило обслужването
//			              } else if(technikName.equals("Христо Георгин")){
//			            	 PdfPCell cell = new PdfPCell(technikImage2,true);
//				        	 dynamicTable.addCell(cell);  // column 10 подпис на лицето извършило обслужването
//			              } else if(technikName.equals("Георги Ильов")) {
//			            	  PdfPCell cell = new PdfPCell(technikImage3,true);
//					         dynamicTable.addCell(cell);  
//			              } else if(technikName.equals("Спас Ильов")) {
//			            	  PdfPCell cell = new PdfPCell(technikImage4, true);
//			            	  dynamicTable.addCell(cell);
//			              }   else {
//			            	    dynamicTable.addCell("");  
//			              }
//			        
//	        	  } else {
//	        		
//	        		  // generira se na momenta
//		              String[] person =  MainPanel.personName.trim().split("[  ]+");
//		        	  Phrase personName;
//		        	  if(person.length > 1) {
//		        		  personName = new Phrase(person[0] + "\n" + person[1],font7);
//		        	  } else {
//		        		  personName = new Phrase(person[0],font7);
//		        	  }
//		        	  
//		        	  PdfPCell cell13 = new PdfPCell(personName);
//		        	  
//		        	  cell13.setHorizontalAlignment(Element.ALIGN_LEFT);
//		        	  dynamicTable.addCell(cell13); // column 9 име на лицето извършило обслужване
//		        	  
//		        	  String technikName = MainPanel.personName.trim();
//		        	 
//		        	  if(technikName.equals("Георги Ковачки")) {
//		
//		            	  PdfPCell cell = new PdfPCell(technikImage,true);
//			        	  dynamicTable.addCell(cell); // column 10 подпис на лицето извършило обслужването
//		              } else if(technikName.equals("Христо Георгин")){
//		            	 PdfPCell cell = new PdfPCell(technikImage2,true);
//			        	 dynamicTable.addCell(cell);  // column 10 подпис на лицето извършило обслужването
//		              } else if(technikName.equals("Георги Ильов")) {
//		            	  PdfPCell cell = new PdfPCell(technikImage3,true);
//				         dynamicTable.addCell(cell);
//		              } else if(technikName.equals("Спас Ильов")) {
//		                 PdfPCell cell = new PdfPCell(technikImage4, true);
//		                 dynamicTable.addCell(cell);
//		              }   else {
//		            	  dynamicTable.addCell("");  
//		              }
//	        	  }
//	        
//	        	  // barcod number
//	        	  PdfPCell cell15 = new PdfPCell(new Phrase(dm.getValueAt(row + startIndex, 3).toString(),font7));
//	        	  cell15.setHorizontalAlignment(Element.ALIGN_LEFT);
//	        	  
//	        	  dynamicTable.addCell(cell15); //column 11 номер на стикер
//	        
//	               sumOfRows += dynamicTable.getRowHeight(row + 2);
//	               
//	         
//	          if(row == stop - 1 || ( (Y - 5) - (sumOfRows))
//       			   < document.bottom()) {
//	        	 dynamicTable.writeSelectedRows(0,-1,fromRows,toRows,x,Y - 5,writer.getDirectContent());
//	        	  if( (Y -5) - sumOfRows
//	        			   < document.bottom()) {
//	        		  document.newPage();
//	        		  Y = document.top();
//	        		  fromRows = toRows;
//	        		  sumOfRows = 0;
//	        	  }
//	          }
//
//		      } 
//	 //    }// end of test cycles
//		
//		      try {
//					dynamicTable.setWidths(new float[]{3.5f,18f,4.3f,5f,6.5f,7f,5.5f,5f,5f,4.3f,5f});
//				} catch (DocumentException e) {
//					// TODO Auto-generated catch block
//					 PDFException.showPDFException(e);
//			         PdfErr.pdfErros(e.toString());
//					e.printStackTrace();
//					return false;
//				}
//		   
//		     float nextY = 0;
//		     for(int i = fromRows;i < toRows;i++) {
//		    	 nextY += dynamicTable.getRowHeight(i);
//		     }
//		     float footY = Y  - nextY ;
//		     setFootText(x, footY - 15, clData);
//		     return true;
//	}
//	private void setFootText(float x, float y,String[] clData) {
//		 float[] footX = new float[]{20,20, 20, 20,100,  350,20,  11,  29,170,170,170,300,300,420,300,400,20,20};
//	     float[] footY = new float[]{  5,20, 40, 75,  90,  90,105,120,135,105,135,150,105,120,105,135,150,165,180};
//	
//	     for(int s = 3;s < footY.length;s++) {
//	    	 footY[s] -= 20;
//	     }
//	     String[] text = new String[]{
//	    		 "Собственик на пожарогасителя/ите " + clData[0] + "  МОЛ: " + clData[4], 
//	    	" тел: " + clData[1] + " "+   (!clData[2].equals("") ? ("     град " + clData[2]) : "") + (!clData[3].equals("") ? ("      адрес " + clData[3]) : ""),
//	    		 "Този протокол се състави в два еднообразни екземпляра - по един за организацията, извършила обслужването, ",
//	    		 "и за собственика на пожарогасителя/ите.",
//	    		 "      Предал:",
//	    		 "" ,
//	    		 "     (ръководител/упълномощен",
//	    		 "Преставител на организацията, ",
//	    		 "    извършила обслужването)",
//	    		 "(подпис, печат)",
//	    		 "Петя Мертакчийска" ,
//	    			"(име, фамилия)",
//	    		
//	    		"( собственик/предста-",
//	    		 "вител на собственика)",
//	    		 "(подпис)",
//	    		 "Приел: " + clData[4] + " ,МОЛ",
//	            "(име, фамилия, длъжност)",
//	    		 "Забележка: Протоколът се съхранява до времето за извършване на следващото техническо обслужване,",
//	    		 "презареждане или хидростатично изпитване.",
//	    		 };
//	     
//	 
//	      boolean newPage = false;
//	     for(int i = 0;i < footX.length;i++) {
//	       if(y - footY[i] <= document.bottom() ) {// + 30 ???? da probvam da razbera zashto
//	    	          newPage = true;
//	    	           document.newPage();
//	    	           break;
//	        } 
//	     }
//
//	              if(newPage) {
//	    		   y =   document.top();
//	              }
//	    	 for(int i1 = 0;i1 < footX.length;i1++) {
//	    	
//	    		  if(i1 == 0) {
//			    	   setText(text[i1],footX[i1],y-footY[i1],"arialbd",10);
//			       } else  {
//					       if(  (i1 >= 6 && i1 <= 16 && i1 != 10) ) {
//					    	   setText(text[i1],footX[i1],y-footY[i1],"italic",9);
//					       } else {
//					          setText(text[i1],footX[i1],y-footY[i1],"arial",10);
//					       }
//			        }
//	    	 }
//	     }
//
//    private boolean init(String timeStamp,String num) {
//    	document = new Document(PageSize.A4,50.f,50.f,50.f,50.f);
//    	
//    	try {
//    		fos = new FileOutputStream(this.target +  "\\Protokol2815-" + 
//    	timeStamp + "-" + num + ".pdf");
//    		
//    				
//    		
//			writer = PdfWriter.getInstance(document, fos);
//					
//            writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
//			
//			// set num of pages handler
//			MyPDFEventHandler pdfEventHandler = new MyPDFEventHandler();
//			writer.setPageEvent(pdfEventHandler);
//			
//	       document.open();
//			
//			content = writer.getDirectContent();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			InOutException.showIOException(e);
//			IOErrorsWriter.writeIO(e.toString());
//			e.printStackTrace();
//			return false;
//		} catch (DocumentException e) {
//			// TODO Auto-generated catch block
//			PDFException.showPDFException(e);
//	         PdfErr.pdfErros(e.toString());
//			e.printStackTrace();
//			return false;
//		}
//    	String workingDir = System.getProperty("user.dir");
// 
//    	technikImage = getItextImage(workingDir+"/Images/гошката.jpg");
//    	technikImage2 = getItextImage(workingDir+"/Images/ице.jpg");
//    	technikImage3 = getItextImage(workingDir+"/Images/шеф.jpg");
//        technikImage4 = getItextImage(workingDir+"/Images/спас.jpg");
//    	return true;
//    }
//    
//    private Image getItextImage(String path) {
//    	Image image = null;
//    	try {
//			image =  Image.getInstance(path);
//		} catch (BadElementException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	return image;
//    }
//    private void setText(String text,float x, float y,String font, float size) {
//		  content.beginText();
//		  content.moveText(x,y);
//		  content.setFontAndSize(getBaseFont(font), size);
//		  content.showText(text);
//		  content.endText();
//	}
//    private void finish() {
//		document.close();
//	//	System.out.println("done!");
//	}
// }
