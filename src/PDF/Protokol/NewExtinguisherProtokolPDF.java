package PDF.Protokol;

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
import utils.MainPanel;

import javax.swing.table.DefaultTableModel;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.TreeMap;

public class NewExtinguisherProtokolPDF extends PdfCreator {

	private final String target = MainPanel.PROTOKOL_PDF_PATH;
	private float X = 400;
	private float Y = PageSize.A4.getHeight() - 25;
	private Document document;
	private PdfWriter writer;
	private PdfContentByte content;
	private Image technikImage;
    private String arial = "arial";
    private String italic = "italic";
    private String arialbd = "arialbd";
	public NewExtinguisherProtokolPDF() {
	}

	public boolean processPdf2(DefaultTableModel dm,
			TreeMap<Object, Integer> PARTS, String[] clData,
			String protokolNumber, String timeStamp, int startIndex,
			int endIndex) {

		if (!init(timeStamp, protokolNumber)) {
			return false;
		}

		setText("Приложение \u2116 9 към чл. 31, ал. 5", X, Y, arialbd, 10);// НАРЕДБА
		// 8121з-531,
		setText("(Доп. - ДВ, бр. 33 от 2017 г.)", X + 40, Y - 15, arial, 9);
		X = (document.right() - 50) / 2;
		Y = Y - 30;
		setText("ПРОТОКОЛ \u2116 " + protokolNumber, X, Y, arialbd, 10);
		X = 35;
		Y = Y - 15;
		setText("за предаване и приемане на пожарогасители, на които е извършено техническо обслужване, презареждане",
				X, Y, arialbd, 10);
		X = (document.right() / 2) - 100;
		Y = Y - 15;
		setText("или хидростатично изпитване (в комбинация или поотделно)", X,
				Y, arialbd, 10);
		X = 35;
		Y = Y - 15;
		setText("Днес, "
				+ MyGetDate.getReversedSystemDate()
				+ " в гр. Дупница, ръководителят "
				+ "Спас Георгиев Ильов", X, Y, arial, 9);
		X = (document.right() / 2) - 20;
		Y = Y - 15;
		setText("(име, презиме, фамилия)", X, Y, italic, 8);
		X = 35;
		Y = Y - 15;
		setText("на " + "\"" + MainPanel.SALLER_NAME + "\""
				+ ", адрес: гр./с. София " + MainPanel.SALLER_ADDRESS
				+ ", обл. София-Град,", X, Y, arial, 9);
		X = 35;
		Y = Y - 15;
		setText("ЕИК: " + MainPanel.SALLER_EIK + ", тел.: "
				+ MainPanel.SALLER_PERSON_TELEFON, X, Y, arial, 9);
		X = 35;
		Y = Y - 15;
		setText("( наименование, адрес, единен идентификационен код (ЕИК) и телефон на организациата за обслужбане на пожарогасители)",
				X, Y, italic, 8);
		X = 35;
		Y = Y - 15;
		setText("предаде на собственика (или негов представител) пожарогасителите , на които е извършено обслужване, както следва:",
				X, Y, italic, 9);
		Y = Y - 10;

		if (!setDynamicTable(X - 10, Y, dm, PARTS, clData, protokolNumber,
				startIndex, endIndex)) {
			return false;
		}

		finish();
		return true;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NewExtinguisherProtokolPDF pdf = new NewExtinguisherProtokolPDF();
		pdf.processPdf2(new DefaultTableModel(),
				new TreeMap<Object, Integer>(), new String[11], "0000000",
				MyGetDate.getTimeStamp(), 0, 0);
	}

	private void setFootText(int total, float x, float y, String[] clData) {
		float[] footX = new float[] {  30, 30, 30, 30, 30, 30, 100, 350, 30, 38, 29,
				170, 170, 170, 300, 300, 420, 400, 400, 30, 30 };
		float[] footY = new float[] { 5,  20, 35, 50, 65, 100, 115, 115, 130, 145,
				160, 130, 160, 175, 130, 145, 130, 160, 175, 190, 205 };

		for (int s = 5; s < footY.length; s++) {
			footY[s] -= 20;
		}
		setText("Тотал " + total + " бр", footX[0],y - footY[0],arial,9);
		setText("Собственик на пожарогасителя/ите " + clData[0] + "  МОЛ: "
				+ clData[4], footX[1], y - footY[1], arial, 9);
		setText("адрес: гр./с. "
				+ (!clData[2].equals("") ? (" " + clData[2]) : "") + " "
				+ (!clData[3].equals("") ? (" " + clData[3]) : "") + " тел: "
				+ clData[1], footX[2], y - footY[2], arial, 9);
		setText("(наименование, адрес, и телефон на организацията/лицето, собственик на пожарогасителя/ите)",
				footX[3], y - footY[3], italic, 8);
		setText("Този протокол се състави в два еднообразни екземпляра - по един за организацията, извършила обслужването, ",
				footX[4], y - footY[4], arial, 9);
		setText("и за собственика на пожарогасителя/ите.", footX[5], y
				- footY[5], arial, 9);
		setText("      ПРЕДАЛ:", footX[6], y - footY[6], arial, 10);
		setText("ПРИЕЛ: ", footX[7], y - footY[7], arial, 10);
		setText("     (ръководител/упълномощен", footX[8], y - footY[8],
				italic, 9);
		setText("Преставител на организацията, ", footX[9], y - footY[9],
				italic, 9);
		setText("    извършила обслужването)", footX[10], y - footY[10],
				italic, 9);
		setText("(подпис, печат)", footX[11], y - footY[11], italic, 9);
		setText("Георги Ильов", footX[12], y - footY[12], italic, 9);
		setText("(име, фамилия)", footX[13], y - footY[13], italic, 9);

		setText("( собственик/предста-", footX[14], y - footY[14], italic, 9);
		setText("вител на собственика)", footX[15], y - footY[15], italic, 9);
		setText("(подпис)", footX[16], y - footY[16], italic, 9);
		setText(clData[4] + " ,МОЛ", footX[17], y - footY[17], italic, 9);
		setText("(име, фамилия, длъжност)", footX[18], y - footY[18], italic,
				9);
		setText("Забележка: Протоколът се съхранява до времето за извършване на следващото техническо обслужване, презареждане",
				footX[19], y - footY[19], arial, 9);
		setText("или хидростатично изпитване.", footX[20], y - footY[20],
				arial, 9);

		boolean newPage = false;
		for (int i = 0; i < footX.length; i++) {
			if (y - footY[i] <= document.bottom()) {// + 30 ???? da probvam da
				// razbera zashto
				newPage = true;
				document.newPage();
				break;
			}
		}

		if (newPage) {
			y = document.top();
		}

	}

	private boolean setDynamicTable(float x, float y, DefaultTableModel dm,
			TreeMap<Object, Integer> PARTS, String[] clData,
			String protokolNumber, int startIndex, int endIndex) {

		PdfPTable dynamicTable = new PdfPTable(11);
		Font font9 = getFontAndSize(arial, 9);
		Font font8 = getFontAndSize(arial, 8);
		Font font7 = getFontAndSize(arial, 7);
		PdfPCell n = new PdfPCell(new Phrase("\u2116\nпо\nред", font9));
		n.setNoWrap(true);
		PdfPCell mark = new PdfPCell(new Phrase("Идентификационна маркировкa" +
				"\nна всеки пожарогасител (марка,\n" +
				" модел, сериен номер и др)", font8));
		mark.setNoWrap(true);
		PdfPCell category = new PdfPCell(new Phrase(
				"Катего\nрия\nна пож.\nсъглас\nно т.\n4.3,4.4\nи 5 от\nСД"
						+ " ISO\n/TS 116\n02-2:\n2015.", font7));
		category.setNoWrap(true);
		PdfPCell wheight = new PdfPCell(new Phrase(
				"Маса \nна\nзаре-\nдения\nпожа\nрога-\nсител,\nkg.",
				font9));
		wheight.setNoWrap(true);
		PdfPCell antifire = new PdfPCell(
				new Phrase(
						"Вид на\nпожарога\nсително\n" +
								"то веще\nство (во-\nда прах,\nCO2\nили\nдр.)",
						font7));
		antifire.setNoWrap(true);
		PdfPCell number = new PdfPCell(
				new Phrase(
						"Търговско\nнаименова-\nние на "
								+ "по-\nжарогаси-\nтелното\n" +
								"вещество\n(при преза\n" +
								"реждане с\nпрах или\n" +
								"пенообра\nзувател)",
						font7));
		number.setNoWrap(true);
		PdfPCell serving = new PdfPCell(new Phrase("Вид на извършеното обслу-" +
				"\nжване (техническо обслужва-" +
				"\nне ,презареждане, или"
				+ "\nхидростатично изпитване на\n"
				+ "устойчивост на налягане", font7));
		serving.setNoWrap(true);
		PdfPCell date = new PdfPCell(new Phrase(
				"Дата на\nизвършено-\nто обслуж-\nване", font8));
		date.setNoWrap(true);
		PdfPCell name = new PdfPCell(new Phrase(
				"Име на\nлицето,\nизвърши-\nло обслу-\nжването", font7));
		name.setNoWrap(true);
		PdfPCell signature = new PdfPCell(new Phrase(
				"Подпис\nна\nлицето,\nизвършилo\nобслужва-\nнето",
				font8));
		signature.setNoWrap(true);
		PdfPCell stikernumber = new PdfPCell(new Phrase(
				"Номер\n    на\nстикер", font9));
		stikernumber.setNoWrap(true);

		dynamicTable.setTotalWidth(540);

		dynamicTable.addCell(n);
		dynamicTable.addCell(mark);
		dynamicTable.addCell(category);
		dynamicTable.addCell(wheight);
		dynamicTable.addCell(antifire);
		dynamicTable.addCell(number);
		dynamicTable.addCell(serving);
		dynamicTable.addCell(date);
		dynamicTable.addCell(name);
		dynamicTable.addCell(signature);
		dynamicTable.addCell(stikernumber);

		try {
			dynamicTable.setWidths(new float[] {  2.5f, 17f, 3.3f, 4f, 4.5f, 5f,
					12.5f, 6f, 4f, 6f, 5f });
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			PDFException.showPDFException(e);
			PdfErr.pdfErros(e.toString());
			e.printStackTrace();
			return false;
		}

		int sumOfRows = 0;
		int from = 0;
		sumOfRows += dynamicTable.getRowHeight(0);

		// Font font10 = getFontAndSize("arial",10);
		String[] romanNumbers = {"I","1","2","3","4","5","6","7","8","9","10"};
		for (int nomer = 0; nomer <= 10; nomer++) {
			Phrase p1 = new Phrase(romanNumbers[nomer] + "", font9);
			PdfPCell cell = new PdfPCell(p1);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			dynamicTable.addCell(cell);
		}
		sumOfRows += dynamicTable.getRowHeight(1);

		int RANGE = 0;
		int getFootY = 0; // to calculate foot position
		int numer = 0;
		float dynamicNextTablePos = y;

		// for(int cycle = 0;cycle < 22;cycle++) {
		for (int row = 0; row < endIndex; row++) {
			RANGE++;
			PdfPCell rowCell = new PdfPCell(new Phrase(++numer + "", font9));
			rowCell.setHorizontalAlignment(Element.ALIGN_CENTER);

			dynamicTable.addCell(rowCell); // column 1


			PdfPCell brandCell = new PdfPCell(new Phrase(dm.getValueAt(row, 5)
					 + " , " + MainPanel.numeroSign +
					" " +  dm.getValueAt(row, 3), font9));

			brandCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			dynamicTable.addCell(brandCell); // column 2 марка + монтажен номер

			PdfPCell catCell = new PdfPCell(new Phrase(dm.getValueAt(row, 4)
					+ "", font9));
			catCell.setHorizontalAlignment(Element.ALIGN_CENTER);

			dynamicTable.addCell(new PdfPCell(catCell)); // column 3 категория

			String wheightStr = dm.getValueAt(row, 1).toString();
			if (wheightStr.contains("литра")) {
				wheightStr = wheightStr.replace("литра", "л");
			}
			PdfPCell wheightCell = new PdfPCell(new Phrase(wheightStr, font9));
			wheightCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			dynamicTable.addCell(new PdfPCell(wheightCell)); // column 4 маса

			// column 0 пож. в-во
			String type = dm.getValueAt(row, 0).toString();

			// filter
			if (type.toUpperCase().contains("ABC")
					|| type.toUpperCase().contains("АВС")) {
				type = MainPanel.type_Prah_ABC;
			} else if (type.toUpperCase().contains("BC")
					|| type.toUpperCase().contains("ВС")) {
				type = MainPanel.type_Prah_BC;
			} else if (type.toLowerCase().contains("воден")) {
				type = MainPanel.type_Water;
			} else if (type.toLowerCase().contains("водопенен")) {
				type = MainPanel.type_Water_Fame;
			} else if (type.toUpperCase().contains("CO2")
					|| type.toUpperCase().contains("СО2")) {
				type = MainPanel.type_CO2;
			}

			PdfPCell typeCell = null;
			switch (type) {
			case MainPanel.type_Prah_ABC:
				typeCell = new PdfPCell(new Phrase("прах АВС", font7));
				break;
			case MainPanel.type_Prah_BC:
				typeCell = new PdfPCell(new Phrase("прах ВС", font7));
				break;
			case MainPanel.type_Water:
				typeCell = new PdfPCell(new Phrase("вода", font7));
				break;
			case MainPanel.type_Water_Fame:
				typeCell = new PdfPCell(new Phrase("водопенен", font7));
				break;
			case MainPanel.type_CO2:
				typeCell = new PdfPCell(new Phrase("CO2", font7));
				break;
			default:
				typeCell = new PdfPCell(new Phrase(""));
				break;
			}
			typeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			dynamicTable.addCell(typeCell);

			String nomerRazreshitelno = " Нов "; // номер на разрешителното
			String Obslujvane = "Техническо обслужване";

			// постави номер на разрешителното

			PdfPCell razreshitelnoCell = new PdfPCell(new Phrase(
					nomerRazreshitelno, font7));
			razreshitelnoCell.setHorizontalAlignment(Element.ALIGN_CENTER);

			dynamicTable.addCell(new PdfPCell(razreshitelnoCell));

			PdfPCell doingCell = new PdfPCell(new Phrase(Obslujvane, font7));
			doingCell.setHorizontalAlignment(Element.ALIGN_CENTER);

			dynamicTable.addCell(doingCell);// column 7 обслужване

			PdfPCell dateCell = new PdfPCell(new Phrase(
					MyGetDate.getReversedSystemDate(), font7));
			dateCell.setHorizontalAlignment(Element.ALIGN_LEFT);

			dynamicTable.addCell(dateCell); // column 8 дата

			//String person[] = new String[] { "Георги", "Ильов" };// MainPanel.personName.trim().split("[  ]+");
			PdfPCell personCell = new PdfPCell(new Phrase(MainPanel.personName.trim()/*"Георги\nИльов"*/, font7));
			personCell.setHorizontalAlignment(Element.ALIGN_LEFT);

			dynamicTable.addCell(personCell); // column 9 име на лицето
												// извършило обслужване

			PdfPCell cell = new PdfPCell(technikImage, true);
			dynamicTable.addCell(cell); // column 10 подпис на лицето извършило
										// обслужването

			PdfPCell stickerCell = new PdfPCell(new Phrase(dm
					.getValueAt(row, 2).toString(), font7));
			stickerCell.setHorizontalAlignment(Element.ALIGN_CENTER);

			dynamicTable.addCell(stickerCell); // column 11 номер на стикер

			sumOfRows += dynamicTable.getRowHeight(row + 2);

			if ((y - (sumOfRows)) < document.bottom()) {
				sumOfRows = 0;
				dynamicTable.writeSelectedRows(0, -1, from, row + 2, 30,
						dynamicNextTablePos, writer.getDirectContent());
				dynamicNextTablePos = 820;
				from = RANGE + 2;
				// next = true;
				document.newPage();
			}

		}
		// }// end of test cycles

		try {
			dynamicTable.setWidths(new float[] { 2.5f, 17f, 3.3f, 4f, 4.5f, 5f,
					12.5f, 6f, 4f, 6f, 5f });
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			PDFException.showPDFException(e);
			PdfErr.pdfErros(e.toString());
			e.printStackTrace();
			return false;
		}

		dynamicTable.writeSelectedRows(0, -1, from, -1, x, dynamicNextTablePos,
				writer.getDirectContent());

		float nextY = 0;
		from = RANGE + 2;
		for (int i = getFootY; i < from; i++) {
			nextY += dynamicTable.getRowHeight(i);
		}
		float footY = dynamicNextTablePos - (nextY + 20);
		setFootText(numer, x, footY, clData);
		return true;
	}

	private boolean init(String timeStamp, String num) {
		document = new Document(PageSize.A4, 50.f, 50.f, 50.f, 50.f);

		try {
			FileOutputStream fos = new FileOutputStream(this.target + "\\Protokol2815-"
					+ timeStamp + "-" + num + ".pdf");

			writer = PdfWriter.getInstance(document, fos);

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
		String workingDir = System.getProperty("user.dir");
		// System.out.println(workingDir);

		switch (MainPanel.personName.trim()) {
			case "Георги Ковачки": {
				technikImage = getItextImage(workingDir + "/Images/goshkata.jpg");
				break;
			}
			case "Георги Ильов": {
				technikImage = getItextImage(workingDir + "/Images/shef.jpg");
				break;
			}
			case "Спас Ильов": {
				technikImage = getItextImage(workingDir + "/Images/spas.jpg");
				break;
			}
			case "Васил Димитров": {
				technikImage = getItextImage(workingDir+"/Images/vasil.jpg");
				break;
			}
			default:
				technikImage = getItextImage(workingDir + "/Images/shef.jpg");
				break;
		}

		return true;
	}

	private Image getItextImage(String path) {
		Image image = null;
		try {
			image = Image.getInstance(path);
		} catch (BadElementException | MalformedURLException e) {
			// TODO Auto-generated catch block
			PDFException.showPDFException(e);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			InOutException.showIOException(e);
			e.printStackTrace();
		}
		return image;
	}

	private void setText(String text, float x, float y, String font, float size) {
		content.beginText();
		content.moveText(x, y);
		content.setFontAndSize(getBaseFont(font), size);
		content.showText(text);
		content.endText();
	}

	private void finish() {
		document.close();
		// System.out.println("done!");
	}

}
