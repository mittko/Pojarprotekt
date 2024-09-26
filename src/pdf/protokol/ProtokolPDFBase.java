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
				+ protokolDate
				+ " в гр. Дупница, ръководителят  "
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
		setText("Тотал " + total + " бр", 30,y -= 15 ,arial,9);
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
		if(client != null && client.equalsIgnoreCase("ПОРШЕ ИНТЕР АУТО БГ ЕООД-ЗАПАД".toLowerCase())) {
			mol = "";
		}
		String[][] bottomText =
				{
						{"Собственик на пожарогасителя/ите " + client + "  МОЛ: " + mol},
						{"адрес: гр./с. "
								+ (firm.getCity() != null && !firm.getCity().equals("") ? (" " + firm.getCity()) : "") + " "
								+ (firm.getAddress() != null && !firm.getAddress().equals("") ? (" " + firm.getAddress()) : "") +
								" Област: "
								+ " тел: "
								+ (firm.getTelPerson() != null ? firm.getTelPerson() : "")},
						{"(наименование, адрес, и телефон на организацията/лицето, собственик на пожарогасителя/ите)"},
						{"Този протокол се състави в два еднообразни екземпляра - по един за организацията, извършила обслужването, "},
						{"и за собственика на пожарогасителя/ите."},
						{"      ПРЕДАЛ:","ПРИЕЛ: "},
						{"     (ръководител/упълномощен","(подпис, печат)","( собственик/предста-","(подпис)"},
						{"Преставител на организацията, ","вител на собственика)"},
						{"    извършила обслужването)","Георги Ильов",mol+ " ,МОЛ "},
						{"(име, фамилия)","(име, фамилия, длъжност)"},
						{"Забележка: Протоколът се съхранява до времето за извършване на следващото техническо обслужване, презареждане"},
						{"или хидростатично изпитване на устойчивост на налягане."},
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
		Image technikImage2 = getItextImage(workingDir + "/Images/ице.jpg");
		technikImage3 = getItextImage(workingDir + "/Images/shef.jpg");
		technikImage4 = getItextImage(workingDir + "/Images/spas.jpg");
		technikImage5 = getItextImage(workingDir+"/Images/vasil.jpg");
		return true;
	}


}
