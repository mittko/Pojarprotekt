package pdf.brack;

import exceptions.PDFException;
import log.PdfErr;
import models.Firm;
import pdf.PdfCreator;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import mydate.MyGetDate;
import utils.MainPanel;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.HashMap;

public class BrackPDF extends PdfCreator{

	private float endY = PageSize.A4.getHeight() - 25;;
	private float sumOfRows = 0.0f;

	private Firm firm;

	public BrackPDF() {
		super();
	}

	public boolean createBrak(Firm firm, DefaultTableModel dm,HashMap<String,ArrayList<Object>> pricini,
							  String timeStamp,String brackNumber,
							  int startIndex,int endIndex, String brackDate) {

		super.init(MainPanel.BRACK_PDF_PATH +
				"\\Брак-", timeStamp, brackNumber);

		document.open();


		setText("НАРЕДБА 8121з-531, Приложение \u2116 9 към чл.31, ал 51", 300, endY, "arial", 10);

		String protokolText = "ПРОТОКОЛ No: " + brackNumber;
		setText(protokolText,
				(document.right() - 100) / 2, endY -= 30, "arialbd", 12);

		setText("за предаване и приемане на пожарогасители, за които е установено" +
				" невъзможност да се възстановят", 50, endY -= 20, "arialbd", 10);

		setText("Днес " + brackDate + " в гр. Дупница, ръководителят (упълномощеният представител)  " +
				" " + MainPanel.personName + " ", 70, endY -= 20, "arial", 10);

		setText("на   \"ПОЖАРПРОТЕКТ\"  ООД, гр. София, обл Софийска, ж.к \"Люлин-10\" ул. Ген. Н. Генев 14, ЕИК: 201 775 049,",
				60, endY -= 10, "arial", 10);

		setText("предаде на собственика (или негов представител) пожарогасителите, за които е установено следното:"
				, 60, endY -= 10, "arial", 10);


          /*  pcb.beginText();
	 	    pcb.moveText(60, endY -= 10);
	 	    pcb.endText();
	 	    
	 
		    for(Object pr : pricini) {
		    	pcb.beginText();
		 	    pcb.moveText(60, endY -= 10);
		 	    pcb.setFontAndSize(getBaseFont("arial"), 9);
		 	    pcb.showText("Поради " + pr+"");
		 	    pcb.endText();
		    }*/

		PdfPTable table = new PdfPTable(6);
		table.setTotalWidth(510);

		try {
			table.setWidths(new float[]{32f, 133f, 32f, 27f, 48f, 180f});
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			PDFException.showErrorMessage(e);
			PdfErr.pdfErros(e.toString());
			e.printStackTrace();
			return false;
		}

		Font arial10 = getFontAndSize("arial", 10);
		Font arial9 = getFontAndSize("arial", 9);
		PdfPCell num = new PdfPCell(new Phrase("No: по \nред", arial10));
		num.setHorizontalAlignment(Element.ALIGN_LEFT);
		num.setNoWrap(true);

		PdfPCell model = new PdfPCell(new Phrase("Идентификационна маркировка\nна " +
				"всеки пожарогасител (марка,\nмодел, сериен номер и др.)", arial10));
		model.setHorizontalAlignment(Element.ALIGN_LEFT);
		model.setNoWrap(true);

		PdfPCell category = new PdfPCell(new Phrase("Кате-\nгория\nсъгла-\nсно\nт.4.3.2.2\nот" +
				" БДС\n ICO \n11602\n-2:2002", arial9));
		category.setHorizontalAlignment(Element.ALIGN_LEFT);
		category.setNoWrap(true);

		PdfPCell wheight = new PdfPCell(new Phrase("Маса\nна за-\nреде-\nния по-\nжаро-\nгаси-\nтел,\nkg,I", arial9));
		wheight.setHorizontalAlignment(Element.ALIGN_LEFT);
		wheight.setNoWrap(true);

		PdfPCell entity = new PdfPCell(new Phrase("Пожаро-\n"
				+ "гасите-\n"
				+ "лно ве-\n"
				+ "щество\n"
				+ "(вода,"
				+ "\n прах ,"
				+ "\nCO2 "
				+ "\nили др.)", arial10));
		entity.setHorizontalAlignment(Element.ALIGN_MIDDLE);
		entity.setNoWrap(true);

		PdfPCell reasons = new PdfPCell(new Phrase("Основание за бракуване", arial10));
		table.addCell(num);
		table.addCell(model);
		table.addCell(category);
		table.addCell(wheight);
		table.addCell(entity);
		table.addCell(reasons);

		int row = 0;
		sumOfRows += table.getRowHeight(0);
		float tablePos = endY - 10;
		float nextTablePos = endY - 10;
		int fromRows = 0;
		int toRows = 1;


  //  for(int test = 0;test < 4;test++) { // test when rows overflow page length
		for (row = 0; row < endIndex; row++) {
			toRows++;

			PdfPCell rowCell = new PdfPCell(new Phrase(row + 1 + "", arial10));
			rowCell.setHorizontalAlignment(Element.ALIGN_CENTER);

			table.addCell(rowCell); // column 0

			PdfPCell brandCell = new PdfPCell(new Phrase(dm.getValueAt(row + startIndex, 6) + " "
					+ dm.getValueAt(row + startIndex, 4), arial10));
			brandCell.setHorizontalAlignment(Element.ALIGN_LEFT);

			table.addCell(brandCell); // column1 марка + сериен номер

			PdfPCell catCell = new PdfPCell(new Phrase(dm.getValueAt(row + startIndex, 5) + "", arial10));
			catCell.setHorizontalAlignment(Element.ALIGN_CENTER);

			table.addCell(catCell); //column 2 категория

			String str = dm.getValueAt(row + startIndex, 2).toString();
			if (str.contains("литра")) {
				str = str.replace("литра", "л");
			}

			PdfPCell wheightCell = new PdfPCell(new Phrase(str, arial10));
			wheightCell.setHorizontalAlignment(Element.ALIGN_CENTER);

			table.addCell(wheightCell); // column 3 маса

			String str2 = dm.getValueAt(row + startIndex, 1).toString();
			if (str2.contains("Прахов")) {
				str2 = str2.replace("Прахов", "Прах");
			}
			PdfPCell entityCell = new PdfPCell(new Phrase(str2, arial10));
			entityCell.setHorizontalAlignment(Element.ALIGN_LEFT);

			table.addCell(entityCell); // column 4 пож. в-во

			// key is a barcod
			ArrayList<Object> pricina = pricini.get(dm.getValueAt(row + startIndex, 3) + "");
			StringBuilder allReasons = new StringBuilder();
			for(int i = 0;i < pricina.size();i++) {
				allReasons.append(pricina.get(i));
				if(i < pricina.size() - 1) {
					allReasons.append(", ");
				}
			}
			PdfPCell pricinaCell;
			if (pricina.size() == 0) {
				pricinaCell = new PdfPCell(new Phrase("", arial10));
			} else {
				pricinaCell = new PdfPCell(new Phrase(allReasons.toString()/*pricina.get(0).toString()*/, arial10));
			}
			pricinaCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(pricinaCell);

			sumOfRows += table.getRowHeight(row + 1);
		//	System.out.printf("SumOfRows %f %f\n", tablePos - sumOfRows, document.bottom());

			if (tablePos - (sumOfRows) < document.bottom()) {

				System.out.printf("from rows %d toRows %d\n",fromRows,toRows);

				table.writeSelectedRows(0, -1, fromRows, toRows, 50, nextTablePos, writer.getDirectContent());

				document.newPage();
				nextTablePos = document.top();
				tablePos = document.top();
				fromRows = toRows;
				sumOfRows = 0;

			}
		}
//	} // end of test cycle

		try {
			table.setWidths(new float[]{32f,133f,32f,27f,48f,180f});
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			PDFException.showErrorMessage(e);
			PdfErr.pdfErros(e.toString());
			e.printStackTrace();
			return false;
		}

		table.writeSelectedRows(0, -1, fromRows,toRows,50, nextTablePos, writer.getDirectContent());

		float nextY = 0;
		for (int i = fromRows; i < toRows; i++) {
			nextY += table.getRowHeight(i);
		}
		float footY = nextTablePos - nextY  ;


		if(footY - 25 <= document.bottom()) {
			footY = document.top();
			document.newPage();
		}
		setText("Да бъде бракуван / бъдат бракувани.",
				(document.right() - 120) / 2,footY - 25,"arial",10);
		if(footY - 40 <= document.bottom()) {
			footY = document.top();
			document.newPage();
		}
		setText("Общо пожарогасители за брак - " + endIndex + " бр.",
				(document.right() - 120) / 2,footY - 40,"arial",10);
		if(footY - 50 <= document.bottom()) {
			footY = document.top();
			document.newPage();
		}
		setFootText(footY - 50, firm);

		document.close();

		return true;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BrackPDF br = new BrackPDF();
		br.createBrak(null, new DefaultTableModel(),
				new HashMap<String,ArrayList<Object>>(), MyGetDate.getTimeStamp(),"0000000",
				0,0,  MyGetDate.getReversedSystemDate());
	}

	private void setFootText(float y,Firm firm) {
		float[] footX = new float[]{20,20, 20, 20,100,  350,20,  11,  29,170,170,170,300,300,420,300,400,20,20};
		float[] footY = new float[]{  5,20, 40, 75,  90, 90,105,120,135,105,135,150,105,120,105,135,150,165,180};


		for(int s = 3;s < footY.length;s++) {
			footY[s] -= 20;
		}
		String[] text = new String[]{
				"Собственик на пожарогасителя/ите " + (firm.getFirm() != null ? firm.getFirm() : "") + "  МОЛ: " +
						(firm.getMol() != null ? firm.getMol() : ""),
				" тел: " + (firm.getTelPerson() != null ? firm.getTelPerson() : "") + " " +
						(firm.getCity() != null && !firm.getCity().equals("")
						? ("     град " + firm.getCity()) : "") + (firm.getAddress() != null && !firm.getAddress().equals("")
						? ("      адрес " + firm.getAddress()) : ""),
				"Този протокол се състави в два еднообразни екземпляра - по един за организацията, извършила обслужването, ",
				"и за собственика на пожарогасителя/ите.",
				"      Предал:",
				"" ,
				"     (ръководител/упълномощен",
				"Преставител на организацията, ",
				"    извършила обслужването)",
				"(подпис, печат)",
				"Георги Ильов",
				"(име, фамилия)",

				"( собственик/предста-",
				"вител на собственика)",
				"(подпис)",
				"Приел: " + (firm.getMol() != null ? firm.getMol() : "") + " ,МОЛ",
				"(име, фамилия, длъжност)",
				"Забележка: Протоколът се съхранява до времето за извършване на следващото техническо обслужване,",
				"презареждане или хидростатично изпитване.",
		};


		for(int i = 0;i < footX.length;i++) {
			if(y - footY[i] <= document.bottom() ) {// + 30 ???? da probvam da razbera zashto
				document.newPage();
				y =  document.top();
				break;
			}
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

    /* Стария метод - работи
     * private void setFootText(float y,String[] clData) {
        float[] footX = new float[]{20,20,20,100,20,11,29,170,170,170,350,300,300,420,400,400};
         float[] footY = new float[]{10,30,45,75,90,105,120,90,120,135,75,90,105,90,75,135};


         String[] text = new String[]{
                   //	 "Съгласно Наредба Iз-2377 бяха монтирани следните резервни части.",
                 "Собственик на пожарогасителя/ите:            " +
                           clData[0],
                 "Този протокол се състави в два еднообразни екземпляра - по един за организацията, извършила обслужването, ",
                 "и за собственика на пожарогасителя/ите.",
                 "      Предал:",
                 "     (ръководител/упълномощен",
                 "Преставител на организацията, ",
                 "    извършила обслужването)",
                 "(подпис, печат)",
                 "Георги Ильов",
                    "(име, фамилия)",
                 "      Приел: ",
                "( собственик/предста-",
                 "вител на собственика)",
                 "(подпис)",
                 "",
                "(име, фамилия, длъжност)"
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


	private String getDetail(String str) {
		return str != null ? str : "          ";
	}
}
