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
import java.util.Map;
import java.util.TreeMap;

public class ProtokolPDFBase extends PdfCreator {

	private final String target = MainPanel.PROTOKOL_PDF_PATH;
	// private final String target =
	// System.getProperty("user.home")+"/Desktop/";
	private float X = 400;
	public float Y = PageSize.A4.getHeight() - 25;// = 692.0

	public String protokolDate;
	public Image technikImage;
	public Image technikImage3;
	public Image technikImage4;
	public Image technikImage5;


	public ProtokolPDFBase() {
	}
	public static void main(String[] args) {

		// TODO Auto-generated method stub
		ProtokolPDFBase pdf = new ProtokolPDFBase();
		pdf.processPdf(new DefaultTableModel(), new TreeMap<Object, Integer>(),
				null, "0000000", MyGetDate.getTimeStamp(), 0, 0,
				MyGetDate.getReversedSystemDate());
		pdf.init("", "");
	}

	public boolean processPdf(DefaultTableModel dm,
							  TreeMap<Object, Integer> PARTS, Firm firm,
							  String protokolNumber, String timeStamp, int startIndex,
							  int endIndex, String protokolDate) {

		this.protokolDate = protokolDate;

		if (!init(timeStamp, protokolNumber)) {
			return false;
		}

		setHeaderText(protokolNumber);

		if (!setDynamicTable(X - 10, dm, PARTS, firm,
				startIndex, endIndex)) {
			return false;
		}

		finish();
		return true;
	}



	public void setHeaderText(String protokolNumber) {
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
	}
	public boolean setDynamicTable(float x, DefaultTableModel dm,
									TreeMap<Object, Integer> PARTS, Firm firm,
									int startIndex, int endIndex) {
		return true;
	}

	public float setFootText(int total, float x, float y, Firm firm, TreeMap<Object,Integer> parts) {
		if (y - 20 <= document.bottom()) {
			document.newPage();
			y = document.top();
		}
		setText("����� " + total + " ��", 30,y -= 15 ,arial,9);
        y -= 5;

		if (y - 20 <= document.bottom()) {
			document.newPage();
			y = document.top();
		}

		float[][] footX2 =
				{
						{30},
						{30},
						{30},
						{30},
						{30},
						{100,350},
						{30,170,300,420},
						{38,300},
						{29,170,400},
						{170,400},
						{30},
						{30},
				};


		String client = firm.getFirm() != null ? firm.getFirm() : "";
		String mol = firm.getMol() != null ? firm.getMol() : "";
		if(client != null && client.equalsIgnoreCase("����� ����� ���� �� ����-�����".toLowerCase())) {
			mol = "";
		}
		String[][] bottomText =
				{
						{"���������� �� ��������������/��� " + client + "  ���: " + mol},
						{"�����: ��./�. "
								+ (firm.getCity() != null && !firm.getCity().equals("") ? (" " + firm.getCity()) : "") + " "
								+ (firm.getAddress() != null && !firm.getAddress().equals("") ? (" " + firm.getAddress()) : "") +
								" ������: "
								+ " ���: "
								+ (firm.getTelPerson() != null ? firm.getTelPerson() : "")},
						{"(������������, �����, � ������� �� �������������/������, ���������� �� ��������������/���)"},
						{"���� �������� �� ������� � ��� ����������� ���������� - �� ���� �� �������������, ��������� ������������, "},
						{"� �� ����������� �� ��������������/���."},
						{"      ������:","�����: "},
						{"     (�����������/�����������","(������, �����)","( ����������/�������-","(������)"},
						{"����������� �� �������������, ","����� �� �����������)"},
						{"    ��������� ������������)","������ �����",mol+ " ,��� "},
						{"(���, �������)","(���, �������, ��������)"},
						{"���������: ���������� �� ��������� �� ������� �� ���������� �� ���������� ���������� ����������, ������������"},
						{"��� ������������� ��������� �� ����������� �� ��������."},
				};

		for(int i = 0;i < bottomText.length;i++) {
			y -= 15;
			for(int j = 0;j < bottomText[i].length;j++) {

				if (y /*- footY2[i][j]*/ <= document.bottom()) {
			            document.newPage();
			            y = document.top();
		        }
				setText(bottomText[i][j], footX2[i][j],y  /*- footY2[i][j]*/ , arial, 9);
			}

		}

		return y;

	}

	private boolean init(String timeStamp, String num) {
		super.init(this.target + "\\Protokol2815-"
				,timeStamp, num );

		technikImage = getItextImage(workingDir + "/Images/goshkata.jpg");
		Image technikImage2 = getItextImage(workingDir + "/Images/���.jpg");
		technikImage3 = getItextImage(workingDir + "/Images/shef.jpg");
		technikImage4 = getItextImage(workingDir + "/Images/spas.jpg");
		technikImage5 = getItextImage(workingDir+"/Images/vasil.jpg");
		return true;
	}


}
