package pdf.protokol;

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
import java.util.TreeMap;

public class NewExtinguisherProtokolPDF extends ProtokolPDFBase {

	private final String target = MainPanel.PROTOKOL_PDF_PATH;
	private float X = 400;


	public NewExtinguisherProtokolPDF() {
	}


	@Override
	public boolean setDynamicTable(float x, DefaultTableModel dm,
									TreeMap<Object, Integer> PARTS, Firm firm,
									int startIndex, int endIndex) {

		PdfPTable dynamicTable = new PdfPTable(11);
		Font font9 = getFontAndSize(arial, 9);
		Font font8 = getFontAndSize(arial, 8);
		Font font7 = getFontAndSize(arial, 7);
		PdfPCell n = new PdfPCell(new Phrase("�\n��\n���", font9));
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
			dynamicTable.setWidths(new float[] {  2.5f, 17f, 3.3f, 4f, 4.5f, 5f,
					12.5f, 6f, 4f, 6f, 5f });
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			PDFException.showErrorMessage(e);
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
		int numer = 0;
		float dynamicNextTablePos = Y;

		//for(int cycle = 0;cycle < 10;cycle++) {
			for (int row = 0; row < endIndex; row++) {
				RANGE++;
				PdfPCell rowCell = new PdfPCell(new Phrase(++numer + "", font9));
				rowCell.setHorizontalAlignment(Element.ALIGN_CENTER);

				dynamicTable.addCell(rowCell); // column 1


				PdfPCell brandCell = new PdfPCell(new Phrase(dm.getValueAt(row, 5)
						+ " , " + MainPanel.numeroSign +
						" " + dm.getValueAt(row, 3), font9));

				brandCell.setHorizontalAlignment(Element.ALIGN_LEFT);
				dynamicTable.addCell(brandCell); // column 2 ����� + �������� �����

				PdfPCell catCell = new PdfPCell(new Phrase(dm.getValueAt(row, 4)
						+ "", font9));
				catCell.setHorizontalAlignment(Element.ALIGN_CENTER);

				dynamicTable.addCell(new PdfPCell(catCell)); // column 3 ���������

				String wheightStr = dm.getValueAt(row, 1).toString();
				if (wheightStr.contains("�����")) {
					wheightStr = wheightStr.replace("�����", "�");
				}
				PdfPCell wheightCell = new PdfPCell(new Phrase(wheightStr, font9));
				wheightCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				dynamicTable.addCell(new PdfPCell(wheightCell)); // column 4 ����

				// column 0 ���. �-��
				String type = dm.getValueAt(row, 0).toString();

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

				PdfPCell typeCell = null;
				switch (type) {
					case MainPanel.type_Prah_ABC:
						typeCell = new PdfPCell(new Phrase("���� ���", font7));
						break;
					case MainPanel.type_Prah_BC:
						typeCell = new PdfPCell(new Phrase("���� ��", font7));
						break;
					case MainPanel.type_Water:
						typeCell = new PdfPCell(new Phrase("����", font7));
						break;
					case MainPanel.type_Water_Fame:
						typeCell = new PdfPCell(new Phrase("���������", font7));
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

				String nomerRazreshitelno = " ��� "; // ����� �� ��������������
				String Obslujvane = "���������� ����������";

				// ������� ����� �� ��������������

				PdfPCell razreshitelnoCell = new PdfPCell(new Phrase(
						nomerRazreshitelno, font7));
				razreshitelnoCell.setHorizontalAlignment(Element.ALIGN_CENTER);

				dynamicTable.addCell(new PdfPCell(razreshitelnoCell));

				PdfPCell doingCell = new PdfPCell(new Phrase(Obslujvane, font7));
				doingCell.setHorizontalAlignment(Element.ALIGN_CENTER);

				dynamicTable.addCell(doingCell);// column 7 ����������

				PdfPCell dateCell = new PdfPCell(new Phrase(
						MyGetDate.getReversedSystemDate(), font7));
				dateCell.setHorizontalAlignment(Element.ALIGN_LEFT);

				dynamicTable.addCell(dateCell); // column 8 ����

				//String person[] = new String[] { "������", "�����" };// MainPanel.personName.trim().split("[  ]+");
				PdfPCell personCell = new PdfPCell(new Phrase(MainPanel.personName.trim()/*"������\n�����"*/, font7));
				personCell.setHorizontalAlignment(Element.ALIGN_LEFT);

				dynamicTable.addCell(personCell); // column 9 ��� �� ������
				// ��������� ����������

				PdfPCell cell = new PdfPCell(technikImage, true);
				dynamicTable.addCell(cell); // column 10 ������ �� ������ ���������
				// ������������

				PdfPCell stickerCell = new PdfPCell(new Phrase(dm
						.getValueAt(row, 2).toString() + "1", font7));// we add 1 to number just dummy to distinguishable every number from another
				stickerCell.setHorizontalAlignment(Element.ALIGN_CENTER);

				dynamicTable.addCell(stickerCell); // column 11 ����� �� ������

				sumOfRows += dynamicTable.getRowHeight(row + 2);

				if ((dynamicNextTablePos - (sumOfRows)) < document.bottom()) {
					dynamicTable.writeSelectedRows(0, -1, from, RANGE + 2, x,
							dynamicNextTablePos, writer.getDirectContent());
					sumOfRows = 0;
					from = RANGE + 2;
					dynamicNextTablePos = document.top();
					document.newPage();
				}

			}
	//	}     // end of test cycles

		try {
			dynamicTable.setWidths(new float[] { 2.5f, 17f, 3.3f, 4f, 4.5f, 5f,
					12.5f, 6f, 4f, 6f, 5f });

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			PDFException.showErrorMessage(e);
			PdfErr.pdfErros(e.toString());
			e.printStackTrace();
			return false;
		}

		dynamicTable.writeSelectedRows(0, -1, from, -1, x, dynamicNextTablePos,
				writer.getDirectContent());

		float nextY = 0;
		for (int i = from; i < RANGE+2; i++) {
			nextY += dynamicTable.getRowHeight(i);
		}
		float footY =  dynamicNextTablePos - (nextY + 20);
		setFootText(numer, x, footY, firm,PARTS);
		return true;
	}



}
