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
import utility.MainPanel;

import javax.swing.table.DefaultTableModel;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.TreeMap;

public class ProtokolPDF3 extends PdfCreator {

	private final String target = MainPanel.PROTOKOL_PDF_PATH;
	// private final String target =
	// System.getProperty("user.home")+"/Desktop/";
	private float X = 400;
	private float Y = PageSize.A4.getHeight() - 25;// = 692.0
	private Document document;
	private PdfWriter writer;
	private PdfContentByte content;
	private float dynamicTablePos;
	private String protokolDate;
	private Image technikImage;
	private Image technikImage2;
	private Image technikImage3;
	private Image technikImage4;
	private Image technikImage5;
	private final String arial = "arial";
	private String arialbd = "arialbd";
	private final String italic = "italic";
	private final Font font10 = getFontAndSize(arial, 10);
	private final Font font9 = getFontAndSize(arial, 9);
	private final Font font8 = getFontAndSize(arial, 8);
	private final Font font7 = getFontAndSize(arial, 7);
	public ProtokolPDF3() {
	}

	public boolean processPdf(DefaultTableModel dm,
			TreeMap<Object, Integer> PARTS, String[] clData,
			String protokolNumber, String timeStamp, int startIndex,
			int endIndex, String protokolDate) {

		this.protokolDate = protokolDate;

		if (!init(timeStamp, protokolNumber)) {
			return false;
		}

		setText("���������� \u2116 9 ��� ��. 31, ��. 5", X, Y, arialbd, 10);// �������
																				// 8121�-531,
		setText("(���. - ��, ��. 33 �� 2017 �.)", X + 40, Y - 15, arial, 9);
		X = (document.right() - 50) / 2;
		Y = Y - 30;
		setText("�������� \u2116 " + protokolNumber, X, Y, arialbd, 10);
		X = 35;
		Y = Y - 15;
		setText("�� ��������� � �������� �� ��������������, �� ����� � ��������� ���������� ����������, ������������",
				X, Y, arialbd, 10);
		X = (document.right() / 2) - 100;
		Y = Y - 15;
		setText("��� ������������� ��������� (� ���������� ��� ���������)", X,
				Y, arialbd, 10);
		X = 35;
		Y = Y - 15;
		setText("����, "
				+ protokolDate
				+ " � ��. �������, �������������  "
				+ "���� �������� �����", X, Y, arial, 9);
		X = (document.right() / 2) - 20;
		Y = Y - 15;
		setText("(���, �������, �������)", X, Y, italic, 8);
		X = 35;
		Y = Y - 15;
		setText("�� " + "\"" + MainPanel.SALLER_NAME + "\""
				+ ", �����: ��./�. ����� " + MainPanel.SALLER_ADDRESS
				+ ", ���. �����-����,", X, Y, arial, 9);
		X = 35;
		Y = Y - 15;
		setText("���: " + MainPanel.SALLER_EIK + ", ���.: "
				+ MainPanel.SALLER_PERSON_TELEFON, X, Y, arial, 9);
		X = 35;
		Y = Y - 15;
		setText("( ������������, �����, ������ ���������������� ��� (���) � ������� �� ������������� �� ���������� �� ��������������)",
				X, Y, italic, 8);
		X = 35;
		Y = Y - 15;
		setText("������� �� ����������� (��� ����� ������������) ���������������� , �� ����� � ��������� ����������, ����� ������:",
				X, Y, arial, 9);
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
		ProtokolPDF3 pdf = new ProtokolPDF3();
		pdf.processPdf(new DefaultTableModel(), new TreeMap<Object, Integer>(),
				new String[11], "0000000", MyGetDate.getTimeStamp(), 0, 0,
				MyGetDate.getReversedSystemDate());
		pdf.init("", "");
	}

	private boolean setDynamicTable(float x, float y, DefaultTableModel dm,
			TreeMap<Object, Integer> PARTS, String[] clData,
			String protokolNumber, int startIndex, int endIndex) {

		PdfPTable dynamicTable = new PdfPTable(11);

		PdfPCell n = new PdfPCell(new Phrase("\u2116\n��\n���", font9));
		n.setNoWrap(true);
		PdfPCell mark = new PdfPCell(new Phrase("���������������� ���������a" +
				"\n�� ����� ������������� (�����,\n" +
				" �����, ������ ����� � ��)", font8));
		mark.setNoWrap(true);
		PdfPCell category = new PdfPCell(new Phrase(
				"������\n���\n�� ���.\n������\n�� �.\n4.3,4.4\n� 5 ��\n��"
						+ " ISO\n/TS 116\n02-2:\n2015.", font7));
		category.setNoWrap(true);
		PdfPCell wheight = new PdfPCell(new Phrase(
				"���� \n��\n����-\n�����\n����\n����-\n�����,\nkg.",
				font9));
		wheight.setNoWrap(true);
		PdfPCell antifire = new PdfPCell(
				new Phrase(
						"��� ��\n��������\n�������\n" +
								"�� ����\n���� (��-\n�� ����,\nCO2\n���\n��.)",
						font7));
		antifire.setNoWrap(true);
		PdfPCell number = new PdfPCell(
				new Phrase(
						"���������\n���������-\n��� �� "
								+ "��-\n��������-\n�������\n" +
								"��������\n(��� �����\n" +
								"������� �\n���� ���\n" +
								"��������\n�������)",
						font7));
		number.setNoWrap(true);
		PdfPCell serving = new PdfPCell(new Phrase("��� �� ����������� �����-" +
				"\n����� (���������� ��������-" +
				"\n�� ,������������, ���"
				+ "\n������������� ��������� ��\n"
				+ "����������� �� ��������", font7));
		serving.setNoWrap(true);
		PdfPCell date = new PdfPCell(new Phrase(
				"���� ��\n���������-\n�� ������-\n����", font8));
		date.setNoWrap(true);
		PdfPCell name = new PdfPCell(new Phrase(
				"��� ��\n������,\n�������-\n�� �����-\n�������", font7));
		name.setNoWrap(true);
		PdfPCell signature = new PdfPCell(new Phrase(
				"������\n��\n������,\n��������o\n��������-\n����",
				font8));
		signature.setNoWrap(true);
		PdfPCell stikernumber = new PdfPCell(new Phrase(
				"�����\n    ��\n������", font9));
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
			dynamicTable.setWidths(new float[] { 2.5f, 17f, 3.3f, 4f, 4.5f, 5f,
					12.5f, 6f, 4f, 6f, 5f });
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			PDFException.showPDFException(e);
			PdfErr.pdfErros(e.toString());
			e.printStackTrace();
			return false;
		}

		int sumOfRows = 0;
		int fromRows = 0;
		int toRows = 2;
		int numer = 0;

		sumOfRows += dynamicTable.getRowHeight(0);

		// �������������� �� ��������
		String[] romanNumbers = {"I","1","2","3","4","5","6","7","8","9","10"};
		for (int nomer = 0; nomer <= 10; nomer++) {
			Phrase p1 = new Phrase(romanNumbers[nomer] + "", font10);
			PdfPCell cell = new PdfPCell(p1);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			dynamicTable.addCell(cell);
		}
		sumOfRows += dynamicTable.getRowHeight(1);

		// for(int cycle = 0;cycle < 5;cycle++) {


		// ���������
		for (int row = 0; row < endIndex; row++) {
		//	toRows++;
			String _tehnichesko = dm.getValueAt(row + startIndex, 7)
					+ ""; // ���������� ����������
			String _prezarejdane = dm.getValueAt(row + startIndex, 8) + ""; // ������������
			String _hidrostatichno = dm
					.getValueAt(row + startIndex, 9) + ""; // �������������
			String[] doing = {_tehnichesko,_prezarejdane,_hidrostatichno};
			++numer;
            for(int j = 0;j < 3;j++) {
                if(doing[j].equals("��")) {
                	continue;
				}
				toRows++;

				PdfPCell cell1 = new PdfPCell(new Phrase((numer + "." + (j + 1)), font7));
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);

				dynamicTable.addCell(cell1); // column 1

				PdfPCell cell2 = new PdfPCell(new Phrase(dm.getValueAt(row
						+ startIndex, 6)
						+ " , " + MainPanel.numeroSign
						+ " - " + dm.getValueAt(row + startIndex, 4), font9));

				dynamicTable.addCell(cell2); // column 2 ����� + �������� �����

				PdfPCell cell3 = new PdfPCell(new Phrase(dm.getValueAt(row
						+ startIndex, 5)
						+ "", font9));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);

				dynamicTable.addCell(cell3); // column 3 ���������

				String wheightStr = dm.getValueAt(row + startIndex, 2).toString();
				if (wheightStr.contains("�����")) {
					wheightStr = wheightStr.replace("�����", "�");
				}
				PdfPCell cell4 = new PdfPCell(new Phrase(wheightStr, font9));
				cell4.setHorizontalAlignment(Element.ALIGN_CENTER);

				dynamicTable.addCell(cell4); // column 4 ����

				// column 0 ���. �-��
				String type = dm.getValueAt(row + startIndex, 1).toString();

				// filter
				if (type.toUpperCase().contains("ABC")
						|| type.toUpperCase().contains("���")) {
					type = MainPanel.type_Prah_ABC;
				} else if (type.toUpperCase().contains("BC")
						|| type.toUpperCase().contains("��")) {
					type = MainPanel.type_Prah_BC;
				} else if (type.toLowerCase().contains("�����")) {
					type = MainPanel.type_Water;
				} else if (type.toLowerCase().contains("���������")) {
					type = MainPanel.type_Water_Fame;
				} else if (type.toUpperCase().contains("CO2")
						|| type.toUpperCase().contains("��2")) {
					type = MainPanel.type_CO2;
				}

				switch (type) {
					case MainPanel.type_Prah_ABC:
						PdfPCell cell5 = new PdfPCell(new Phrase("���� ���", font7));
						cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
						dynamicTable.addCell(cell5);
						break;
					case MainPanel.type_Prah_BC:
						PdfPCell cell6 = new PdfPCell(new Phrase("���� ��", font7));
						cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
						dynamicTable.addCell(cell6);
						break;
					case MainPanel.type_Water:
						PdfPCell cell7 = new PdfPCell(new Phrase("�����", font7));
						cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
						dynamicTable.addCell(cell7);
						break;
					case MainPanel.type_Water_Fame:
						PdfPCell cell8 = new PdfPCell(new Phrase("���������", font7));
						cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
						dynamicTable.addCell(cell8);
						break;
					case MainPanel.type_CO2:
						PdfPCell cell9 = new PdfPCell(new Phrase("CO2", font7));
						cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
						dynamicTable.addCell(cell9);
						break;
					default:
						break;
				}

				// column 6 ����� �� �������������� �� ��������� �����������

				String nomerRazreshitelno = ""; // ����� �� ��������������
				String Obslujvane = "";
				if(!doing[j].equals("��")) {
					if(j == 0) {
						Obslujvane = "���������� ���������";
					} else if(j == 1) {
						Obslujvane = "������������";
					} else if(j == 2) {
						String differenceDayHI = MyGetDate.getUrgentDays(
								MyGetDate.getReversedSystemDate(),
								doing[j]);
						int ostawashtiDni = Integer.parseInt(differenceDayHI);
						if (ostawashtiDni > 1460 && ostawashtiDni <= 1825) { // 4*365 &&
							// 5*365
							Obslujvane = "������������� ��������� �� ����������� �� ��������";
						} else if (ostawashtiDni > 1095 && ostawashtiDni <= 1460) { // 3*365
							// &&
							// 4*365
							Obslujvane += "������������� ��������� �� ����������� �� �������� (1)";
						} else if (ostawashtiDni > 730 && ostawashtiDni <= 1095) { // 2*365
							// &&
							// 3*365
							Obslujvane += "������������� ��������� �� ����������� �� �������� (2) ";
						} else if (ostawashtiDni > 365 && ostawashtiDni <= 730) { // 365
							// &&
							// 2*365
							Obslujvane += "������������� ��������� �� ����������� �� �������� (3)";
						} else if (ostawashtiDni <= 365) {
							Obslujvane += "������������� ��������� �� ����������� �� �������� (4)";
						}
					}
				}
				// ������� ����� �� ��������������

				// doing[0] = ���������� doing[1] = ������������
				switch (type) {
					case MainPanel.type_Prah_BC:
					case MainPanel.type_Prah_ABC:
						if (!doing[j].equals("��")) {
							if (j == 0) {
								nomerRazreshitelno = "ABC";
							} else if (j == 1) {
								nomerRazreshitelno = "����� ABC";
							} else if (j == 2) {
								nomerRazreshitelno = "�������";
							}
						}
						break;
					case MainPanel.type_Water:
						if (!doing[j].equals("��")) {
							if (j == 0) {
								nomerRazreshitelno = "����";
							} else if (j == 1) {
								nomerRazreshitelno = "����";
							} else if (j == 2) {
								nomerRazreshitelno = "�������";
							}
						}
						break;
					case MainPanel.type_Water_Fame:
						if (!doing[j].equals("��")) {
							if (j == 0) {
								nomerRazreshitelno = "STHAMEX";
							} else if (j == 1) {
								nomerRazreshitelno = "STHAMEX";
							} else if (j == 2) {
								nomerRazreshitelno = "�������";
							}
						}
						break;
					case MainPanel.type_CO2:
						if (!doing[j].equals("��")) {
							if (j == 0) {
								nomerRazreshitelno = "CO2";
							} else if (j == 1) {
								nomerRazreshitelno = "CO2";
							} else if (j == 2) {
								nomerRazreshitelno = "�������";
							}
						}
						break;
					default:
						nomerRazreshitelno = "";
						break;
				}
				//
				PdfPCell cell10 = new PdfPCell(
						new Phrase(nomerRazreshitelno, font7));
				cell10.setHorizontalAlignment(Element.ALIGN_CENTER);
				dynamicTable.addCell(cell10);

				PdfPCell cell11 = new PdfPCell(new Phrase(Obslujvane, font7));
				cell11.setHorizontalAlignment(Element.ALIGN_CENTER);

				dynamicTable.addCell(cell11);// column 7 ����������

				PdfPCell cell12 = new PdfPCell(new Phrase(protokolDate, font7));
				cell12.setHorizontalAlignment(Element.ALIGN_LEFT);

				dynamicTable.addCell(cell12); // column 8 ����

				// set name of person
				if (dm.getColumnCount() >= 13) {
					// generira se ot spravki
					String technikName = dm.getValueAt(row + startIndex, 13).toString();
					System.out.println("tehnik " + technikName);

					switch (technikName) {
						case "������ �������": {
							PdfPCell cellName = new PdfPCell(new Phrase(technikName, font7));
							cellName.setHorizontalAlignment(Element.ALIGN_LEFT);
							dynamicTable.addCell(cellName);

							PdfPCell cellSign = new PdfPCell(technikImage, true);
							dynamicTable.addCell(cellSign); // column 10 ������ �� ������
							// ��������� ������������
							break;
						}
						case "������ �����": {
							PdfPCell cellName = new PdfPCell(new Phrase(technikName, font7));
							cellName.setHorizontalAlignment(Element.ALIGN_LEFT);
							dynamicTable.addCell(cellName);

							PdfPCell cellSign = new PdfPCell(technikImage3, true);
							dynamicTable.addCell(cellSign);
							break;
						}
						case "���� �����": {
							PdfPCell cellName = new PdfPCell(new Phrase(technikName, font7));
							cellName.setHorizontalAlignment(Element.ALIGN_LEFT);
							dynamicTable.addCell(cellName);

							PdfPCell cellSign = new PdfPCell(technikImage4, true);
							dynamicTable.addCell(cellSign);
							break;
						}
						case "����� ��������": {
							PdfPCell cellName = new PdfPCell(new Phrase(technikName, font7));
							cellName.setHorizontalAlignment(Element.ALIGN_LEFT);
							dynamicTable.addCell(cellName); // column 9 ��� �� ������

							PdfPCell cellSign = new PdfPCell(technikImage5, true);
							dynamicTable.addCell(cellSign); // column 10 ������ �� ������
							// ��������� ������������
							break;
						}
						default:
							PdfPCell cellName = new PdfPCell(new Phrase("������ �����", font7));
							cellName.setHorizontalAlignment(Element.ALIGN_LEFT);
							dynamicTable.addCell(cellName);

							PdfPCell cellSign = new PdfPCell(technikImage3, true);
							dynamicTable.addCell(cellSign);
							break;
					}

				    } else {
				//	 generira se na momenta

					String technikName = MainPanel.personName.trim();

					System.out.println("tehnik " + technikName);

					switch (technikName) {
						case "������ �������": {
							PdfPCell cellName = new PdfPCell(new Phrase(technikName, font7));
							cellName.setHorizontalAlignment(Element.ALIGN_LEFT);
							dynamicTable.addCell(cellName); // column 9 ��� �� ������

							PdfPCell cellSign = new PdfPCell(technikImage, true);
							dynamicTable.addCell(cellSign); // column 10 ������ �� ������
							// ��������� ������������
							break;
						}
						case "������ �����": {
							PdfPCell cellName = new PdfPCell(new Phrase(technikName, font7));
							cellName.setHorizontalAlignment(Element.ALIGN_LEFT);
							dynamicTable.addCell(cellName); // column 9 ��� �� ������

							PdfPCell cellSign = new PdfPCell(technikImage3, true);
							dynamicTable.addCell(cellSign);
							break;
						}
						case "���� �����": {
							PdfPCell cellName = new PdfPCell(new Phrase(technikName, font7));
							cellName.setHorizontalAlignment(Element.ALIGN_LEFT);
							dynamicTable.addCell(cellName); // column 9 ��� �� ������

							PdfPCell cellSign = new PdfPCell(technikImage4, true);
							dynamicTable.addCell(cellSign);
							break;
						}
						case "����� ��������": {
							PdfPCell cellName = new PdfPCell(new Phrase(technikName, font7));
							cellName.setHorizontalAlignment(Element.ALIGN_LEFT);
							dynamicTable.addCell(cellName); // column 9 ��� �� ������

							PdfPCell cellSign = new PdfPCell(technikImage5, true);
							dynamicTable.addCell(cellSign); // column 10 ������ �� ������
							// ��������� ������������
							break;
						}
						default:
							PdfPCell cellName = new PdfPCell(new Phrase("������ �����", font7));
							cellName.setHorizontalAlignment(Element.ALIGN_LEFT);
							dynamicTable.addCell(cellName); // column 9 ��� �� ������

							PdfPCell cellSign = new PdfPCell(technikImage3, true);
							dynamicTable.addCell(cellSign);
							break;
					}
				}

				// barcod number
				PdfPCell cell15 = new PdfPCell(new Phrase(dm.getValueAt(
						row + startIndex, 3).toString(), font7));
				cell15.setHorizontalAlignment(Element.ALIGN_LEFT);

				dynamicTable.addCell(cell15); // column 11 ����� �� ������

				sumOfRows += dynamicTable.getRowHeight(row + 2);

				if (row == endIndex - 1 || ((Y - 5) - sumOfRows) < document.bottom()) {
					// System.out.println("fAK YOU 1");
					dynamicTable.writeSelectedRows(0, -1, fromRows, toRows, x,
							Y - 5, writer.getDirectContent());
					if ((Y - 5) - sumOfRows < document.bottom()) {
						document.newPage();
						Y = document.top();
						fromRows = toRows;
						sumOfRows = 0;
					}
				}
			}
		}
		// }// end of test cycles

		try {
			dynamicTable.setWidths(new float[] {2.5f, 17f, 3.3f, 4f, 4.5f, 5f,
					12.5f, 6f, 4f, 6f, 5f });
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			PDFException.showPDFException(e);
			PdfErr.pdfErros(e.toString());
			e.printStackTrace();
			System.out.println("Document Exception");
			return false;
		}

		float nextY = 0;
		for (int i = fromRows; i < toRows; i++) {
			nextY += dynamicTable.getRowHeight(i);
		}
		float footY = Y - nextY;
		setFootText(numer, x, footY - 15, clData);
		return true;
	}



	private void setFootText(int total, float x, float y, String[] clData) {
		float[] footX = new float[] { 30, 30, 30, 30, 30, 30, 100, 350, 30, 38, 29,
				170, 170, 170, 300, 300, 420, 400, 400, 30, 30 };
//		float[] footY = new float[] { 5, 20, 35, 50, 85, 100, 100, 115, 130,
//				145, 115, 145, 160, 115, 130, 115, 145, 160, 175, 190 };
//		for (int s = 4; s < footY.length; s++) { // ???????
//			footY[s] -= 20;
//		}
		float[] footY = new float[] { 5,  20, 35, 50, 65, 100, 115, 115, 130, 145,
				160, 130, 160, 175, 130, 145, 130, 160, 175, 190, 205 };

		for (int s = 5; s < footY.length; s++) {
			footY[s] -= 20;
		}
		setText("����� " + total + " ��", footX[0],y - footY[0],arial,9);
		if (y - footY[1] <= document.bottom()) {
			document.newPage();
			y = document.top();
		}
		setText("���������� �� ��������������/��� " + clData[0] + "  ���: "
				+ clData[4], footX[1], y - footY[1], arial, 9);

		if (y - footY[2] <= document.bottom()) {
			document.newPage();
			y = document.top();
		}
		setText("�����: ��./�. "
				+ (!clData[2].equals("") ? (" " + clData[2]) : "") + " "
				+ (!clData[3].equals("") ? (" " + clData[3]) : "") +
				" ������: "
				+ " ���: "
				+ clData[1], footX[2], y - footY[2], arial, 9);

		if (y - footY[3] <= document.bottom()) {
			document.newPage();
			y = document.top();
		}
		setText("(������������, �����, � ������� �� �������������/������, ���������� �� ��������������/���)",
				footX[3], y - footY[3], italic, 8);

		if (y - footY[4] <= document.bottom()) {
			document.newPage();
			y = document.top();
		}
		setText("���� �������� �� ������� � ��� ����������� ���������� - �� ���� �� �������������, ��������� ������������, ",
				footX[4], y - footY[4], arial, 9);

		if (y - footY[5] <= document.bottom()) {
			document.newPage();
			y = document.top();
		}
		setText("� �� ����������� �� ��������������/���.", footX[5], y
				- footY[5], arial, 9);

		if (y - footY[6] <= document.bottom()) {
			document.newPage();
			y = document.top();
		}
		setText("      ������:", footX[6], y - footY[6], arial, 10);

		if (y - footY[7] <= document.bottom()) {
			document.newPage();
			y = document.top();
		}
		setText("�����: ", footX[7], y - footY[7], arial, 10);

		if (y - footY[8] <= document.bottom()) {
			document.newPage();
			y = document.top();
		}
		setText("     (�����������/�����������", footX[8], y - footY[8],
				italic, 9);

		if (y - footY[9] <= document.bottom()) {
			document.newPage();
			y = document.top();
		}
		setText("����������� �� �������������, ", footX[9], y - footY[9],
				italic, 9);

		if (y - footY[10] <= document.bottom()) {
			document.newPage();
			y = document.top();
		}
		setText("    ��������� ������������)", footX[10], y - footY[10],
				italic, 9);

		if (y - footY[11] <= document.bottom()) {
			document.newPage();
			y = document.top();
		}
		setText("(������, �����)", footX[11], y - footY[11], italic, 9);

		if (y - footY[12] <= document.bottom()) {
			document.newPage();
			y = document.top();
		}
		setText("������ �����", footX[12], y - footY[12], italic, 9);

		if (y - footY[13] <= document.bottom()) {
			document.newPage();
			y = document.top();
		}
		setText("(���, �������)", footX[13], y - footY[13], italic, 9);

		if (y - footY[14] <= document.bottom()) {
			document.newPage();
			y = document.top();
		}
		setText("( ����������/�������-", footX[14], y - footY[14], italic, 9);

		if (y - footY[15] <= document.bottom()) {
			document.newPage();
			y = document.top();
		}
		setText("����� �� �����������)", footX[15], y - footY[15], italic, 9);

		if (y - footY[16] <= document.bottom()) {
			document.newPage();
			y = document.top();
		}
		setText("(������)", footX[16], y - footY[16], italic, 9);

		if (y - footY[17] <= document.bottom()) {
			document.newPage();
			y = document.top();
		}
		setText("" + clData[4] + " ,��� ", footX[17], y - footY[17], italic, 9);

		if (y - footY[18] <= document.bottom()) {
			document.newPage();
			y = document.top();
		}
		setText("(���, �������, ��������)", footX[18], y - footY[18], italic,
				9);

		if (y - footY[19] <= document.bottom()) {
			document.newPage();
			y = document.top();
		}
		setText("���������: ���������� �� ��������� �� ������� �� ���������� �� ���������� ���������� ����������, ������������",
				footX[19], y - footY[19], arial, 9);

		if (y - footY[20] <= document.bottom()) {
			document.newPage();
			y = document.top();
		}
		setText("��� ������������� ��������� �� ����������� �� ��������.", footX[20], y - footY[20],
				arial, 9);

		// };

		// boolean newPage = false;
		// for (int i = 0; i < footX.length; i++) {
		// if (y - footY[i] <= document.bottom()) {// + 30 ???? da probvam da
		// // razbera zashto
		// newPage = true;
		// document.newPage();
		// break;
		// }
		// }
		//
		// if (newPage) {
		// y = document.top();
		// }

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

		technikImage = getItextImage(workingDir + "/Images/goshkata.jpg");
		technikImage2 = getItextImage(workingDir + "/Images/���.jpg");
		technikImage3 = getItextImage(workingDir + "/Images/shef.jpg");
		technikImage4 = getItextImage(workingDir + "/Images/spas.jpg");
		technikImage5 = getItextImage(workingDir+"/Images/vasil.jpg");
		return true;
	}

	private Image getItextImage(String path) {
		Image image = null;
		try {
			image = Image.getInstance(path);
		} catch (BadElementException | IOException e) {
			// TODO Auto-generated catch block
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
