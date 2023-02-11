package utils;


import mydate.MyGetDate;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.SQLException;


/**
 * @author MITKO JAVA
 *
 */
public class MainPanel extends JPanel {
	public int HEIGHT = 0;
	public int WIDTH = 0;
	private static int FONT_SIZE = 0;
	protected int headerWidth = 0;
	protected int headerHeight = 100;

	public static String personName;

	public static final String BACKUP_PATH = "D:/CopyDB";
	// Вид обслужване
	public static final String ТО = "ТО";
	public static final String П = "П";
	public static final String ХИ = "ХИ";
	public static final String ТО_П = "ТО,П";
	public static final String ТО_П_ХИ = "ТО,П,ХИ";

	public static final String Брак = "Брак";

	// Категория
	public static final String category1 = "1";
	public static final String category2 = "2";
	public static final String category3 = "3";
	public static final String category4 = "4";
	public static final String category5 = "5";
	// за Прахов
	public static final String Glava = "Глава";
	public static final String Manometar = "Манометър";
	public static final String Sphlend = "Шпленд";
	public static final String Uplatnenie = "О-пръстен (Уплътнение)";
	public static final String Zatvor = "Затвор (Игла)";
	public static final String Plomba = "Пломба";
	public static final String Markuch = "Маркуч";
	public static final String DarjachZaMarkuch = "Държач за маркуч";
	public static final String Prujina = "Пружина";
	public static final String Struinik = "Струйник";
	public static final String Patron = "Патрон";
	public static final String Sonda = "Сонда";
	public static final String Struinik4 = "Струйник (4 категория)";
	public static final String BarbutajnaTraba = "Барбутажна тръба";
	public static final String IglichkaZaPompane = "Игличка за помпане";
	public static final String KapachkaZaUplatnenie = "Капачка за уплътнение";
	public static final String TvardoHodovoKolelo = "Твърдо ходово колело";
	public static final String KoleloZaVisokoTeglo = "Ходово колело за високо тегло";
	public static final String RemontKolicka = "Ремонт на количка";
	public static final String PrahBC = "Гасително вещество (Прах BC)";
	public static final String PrahABC = "Гасително вещество (Прах ABC)";
	public static final String BoyaPojarogasitel = "Боядисване на пожарогасител";
	public static final String BoyaKolichka = "Боядисване на количка";
	public static final String Etiket = "Етикет";
	//

	// за Воден и Водопенен
	// Glava
	// Manometar
	// Sphlend
	// O-prasten Uplatnenie
	// Zatvor Igla
	// Plomba
	// Markuch
	// Odrjach za markuch
	// Prujina
	// Struinik
	// Patron
	// Sonda
	// Struinik 4 kategoria
	// Barbutajna traba
	// Iglichka za pompane
	// Kapachka za uplatnenie
	// Boya pojarogasitel
	// etiket
	public static final String SadZaGasitelnoVeshtestvo = "Съд за гасително вещество";
	public static final String GasitelnoVeshtestvoVoda = "Гасително вещество (Вода)";
	public static final String GasitelnoVeshtestvoVodaPyana = "Гасително вещество (Вода + Пяна)";
	//

	// CO2
	public static final String BarzVentil1_Model = "Бърз вентил модел 1";
	public static final String BarzVentil2_Model = "Бърз вентил модел 2";
	public static final String BarzVentil3_Model = "Бърз вентил модел 3";
	public static final String BarzVentil3Model_Prava_Rezba = "Бърз вентил модел 3 права резба";
	// Splend
	// Plomba
	public static final String Snegoobrazuvatel1_Model = "Снегообразувател модел 1";
	public static final String Snegoobrazuvatel2_Model = "Снегообразувател модел 2";
	public static final String Snegoobrazuvatel3_Model = "Снегообразувател модел 3";
	// Sonda
	public static final String DrajkaZaPojarogasitel = "Дръжка за пожарогасител";
	public static final String ZapresovaneNaNakrainik = "Запресоване на накрайник";
	// Tvardo Hodovo Kolelo
	// Hodovo Kolelo Za Visoko teglo
	// Remont Kolichka
	// Boya Kolichka
	// Boya Pojarogasitel
	// Etiket
	// Sad za gasitelno veshtestvo
	public static final String GasitelnoVeshtestvoCO2 = "Гасително вещество (Въглероден диоксид)";

	public static final String type_Prah_BC = "Прахов BC";
	public static final String type_Prah_ABC = "Прахов ABC";
	public static final String type_Water = "Воден";
	public static final String type_Water_Fame = "Водопенен";
	public static final String type_CO2 = "CO2";
	public static final String type_classF = "F клас";
	public static final String type_classD = "D клас";

	// data tables
	public static final String SERVICE = "ServiceTableDB";
	public static final String PROTOKOL = "ProtokolTableDB5"; // -> 5
	public static final String BRACK = "BrackTableDB2";
	public static final String INVOICE_PARENT = "InvoiceParentDB5";
	public static final String INVOICE_CHILD = "InvoiceChildDB7";
	public static final String PROFORM_PARENT = "ProformParentDB";
	public static final String PROFORM_CHILD = "ProformChildDB2";
	public static final String ACQUITTANCE_PARENT = "AcquittanceParentDB";
	public static final String ACQUITTANCE_CHILD = "AcquittanceChildDB";
	public static final String SOLD_BY_PROFORM_DB = "SoldByProformDB";
	public static final String TEAM = "TeamDB";
	// public static final String SALES = "SalesDB2";
	// num tables
	protected static final String SERIAL_TABLE = "SerialTable"; // 0000000
	public static final String BRACK_NUMBER = "BrackNumber"; // 0000000
	public static final String PROTOKOL_NUMBER = "PR_Number"; // 0000000
	public static final String SERVICE_NUMBER = "SO_Table"; // 1000000000
	public static final String INVOICE_NUMBER = "InvoiceNumber"; // 0000000000
	public static final String PROFORM_NUMBER = "ProformNumber"; // 0000000000
	public static final String ACQUITTANCE_NUMBER = "AcquittanceNumber"; // 0000000000
	// client tables
	public static final String PERSON = "PersonsTable";
	public static final String FIRM = "FirmsTable";

	// parts and artikuls tables
	public static final String PARTS_PRICE = "PartsTableDB";
	public static final String PARTS_QUANTITY = "PartsQuantityDB";
	public static final String AVAILABLE_ARTIKULS = "ArtikulsDB";
	public static final String GREY_AVAILABLE_ARTIKULS = "GreyArtikulsDB";
	public static final String AVAILABLE_SERVICES = "ServiceDB";
	public static final String DELIVERY_ARTIKULS = "DeliveryArtikulsDB2";
	public static final String SELLS = "Продажби";// взима данни от няколко
													// таблици
	public static final String AVAILABILITY = "Наличност";// взима данни от
															// няколко таблици
	public static final String NEW_EXTINGUISHERS = "NewExtinguishersDB3";
	public static final  String EXTINGUISHING_AGENT_TABLE = "ExtinguishingAgentTable";
	// price table
	public static final String WORK = "WorkPriceDB";
	public static final String NEW_EXT_PRICE = "NewExtPrice";

	public static final BufferedImage img = new BufferedImage(1, 1,
			BufferedImage.TYPE_INT_ARGB);
	public static FontMetrics fm = null;

	public static final String DOCUMENTS_PATH = System.getProperty("user.dir");
	// public static final String STICKER_PATH = DOCUMENTS_PATH +"\\Stickers";
	public static final String LOGO_PATH = DOCUMENTS_PATH
			+ "\\Images\\logo5.png";
	public static final String BARCODE_PDF_PATH = DOCUMENTS_PATH
			+ "\\tmp\\BarcodeImage\\";

	public static final String SERVICE_ORDER_PDF_PATH = createFolder(DOCUMENTS_PATH
			+ "\\tmp\\PDF\\Сервизна Поръчка");
	public static final String PROTOKOL_PDF_PATH = createFolder(DOCUMENTS_PATH
			+ "\\tmp\\PDF\\Протокол");
	public static final String BRACK_PDF_PATH = createFolder(DOCUMENTS_PATH
			+ "\\tmp\\PDF\\Протокол Брак");
	public static final String INVOICE_PDF_PATH = createFolder(DOCUMENTS_PATH
			+ "\\tmp\\PDF\\Фактура");
	public static final String PROFORM_PDF_PATH = createFolder(DOCUMENTS_PATH
			+ "\\tmp\\PDF\\Проформа");
	public static final String ACQUITTANCE_PDF_PATH = createFolder(DOCUMENTS_PATH
			+ "\\tmp\\PDF\\Стокова Разписка");

	public static final String DIARY_EXCELL_PATH = createFolder(DOCUMENTS_PATH
			+ "\\tmp\\Excell\\Дневник");
	public static final String SERVICE_ORDER_AND_PROTOKOL_EXCELL_PATH = createFolder(DOCUMENTS_PATH
			+ "\\tmp\\Excell\\Сервизна Поръчка и Протоколи");;
	public static final String NEWEXTINGUISHERS_EXCELL_PATH = createFolder(DOCUMENTS_PATH
			+ "\\tmp\\Excell\\Нови Пожарогасители");
	public static final String ARTIKULS_EXCELL_PATH = createFolder(DOCUMENTS_PATH
			+ "\\tmp\\Excell\\Артикули");;
	public static final String SELL_BY_PROFORM_EXCELL_PATH = createFolder(DOCUMENTS_PATH
			+ "\\tmp\\Excell\\Фактури и Проформи");
	public static final String ACQUITTANCE_EXCELL_PATH = createFolder(DOCUMENTS_PATH
			+ "\\tmp\\Excell\\Стокови Разписки");
	public static final String DELIVERY_EXCELL_PATH = createFolder(DOCUMENTS_PATH
			+ "\\tmp\\Excell\\Доставки");
	public static final String SALES_EXCELL_PATH = createFolder(DOCUMENTS_PATH
			+ "\\tmp\\Excell\\Продажби");
	protected static final String SALLER_TITLE = "ПОЖАРПРОТЕКТ ООД";
	public static String SALLER_NAME;
	public static String SALLER_CITY;
	public static String SALLER_ADDRESS;
	public static String SALLER_EIK;
	public static String SALLER_MOL;
	public static String SALLER_E_MAIL;
	public static String SALLER_PERSON_CONTACT;
	public static String SALLER_PERSON_TELEFON;
	public static String SALLER_BANK;
	public static String SALLER_BIC;
	public static String SALLER_IBAN;


	public static int ACCESS_SO = 0;
	public static int ACCESS_WORKING_BOOK = 1;
	public static int ACCESS_INVOICE = 2;
	public static int ACCESS_REPORTS = 3;
	public static int ACCESS_NEW_EXT = 4;
	public static int ACCESS_HIDDEN_MENU = 5;
	public static int ACCESS_ACQUITTANCE = 6;
	public static boolean[] ACCESS_MENU = new boolean[7];

	// IMAGES
	public static final String enterImage = "go.png";
	public static final String enterImage2 = "go.png";// "strelka4.png";

	public static final String printerImage = "doc.jpg";// "printer.png";
	public static final String barcodeImage = "barcode.png";
	public static final String dbImage = "db7.png";
	public static final String eraserImage = "eraser4.png";
	public static final String clientsImage = "clients5.png";
	public static final String enterNumberImage = "enter2.png";
	public static final String penImage = "pen3.png";
	public static final String reportsImage = "reports2.png";
	public static final String invoiceImage = "inv2.png";
	public static final String stickerImage = "barcode2.png";
	public static final String skladExtImage = "skladExt2.png";
	public static final String artikuliImage = "artikuli2.png";
	public static final String greyArtikuliImage = "grey_artikuli.png";
	public static final String calendarImage = "calendar3.png";
	public static final String acceptImage = "accept5.png";
	public static final String yesImage = "accept5.png";
	public static final String noImage = "no.gif";
	public static final String refreshImage = "refresh2.png";
	public static final String excellImage = "excel.png";
	public static final String diaryImage = "diary3.png";
	public static final String attentionImage = "attention.png";
    public static final String addPercentImage = "add_percent.png";
    public static final String substractPercentImage = "substract_percent.png";

	public static String LABEL_PRINTER1;//LPQ58 "DATECS LP-50";
// TO DO ->	public static String LABEL_PRINTER2;// Citizen
	public static final String numeroSign = "\u2116";


	@Override
	public int getWidth() {
		return this.WIDTH;
	}

	public MainPanel(boolean b) {
	}

	public MainPanel() {
		// ipAddress = getCurrentIP.getIP();

		this.setBackground(Color.WHITE);
		HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.9);
		WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
		FONT_SIZE = (int) (HEIGHT * 0.027);
		setProperties();
		headerWidth = WIDTH;
		fm = img.getGraphics().getFontMetrics(getFONT());

		// loadSallerData();
		// loadWorkPrice();

	}

	public void setProperties() {

		UIManager.put("Button.font", getFONT());
		UIManager.put("Label.font", getFONT());
		UIManager.put("TextField.font", getFONT());
		UIManager.put("PasswordField.font", getFONT());
		UIManager.put("ComboBox.font", getFONT());
		UIManager.put("List.font", getFONT());
		UIManager.put("OptionPane.messageFont", getFONT());
		UIManager.put("TextArea.font", getFONT());
		UIManager.put("Table.font", getFONT());
		UIManager.put("TableHeader.font", getFONT());
		UIManager.put("ScrollPane.font", getFONT());
		UIManager.put("ToolTip.background", Color.TRANSLUCENT);
		UIManager.put("Tooltip.font", getFONT());
		UIManager.put("TitledBorder.font", getFONT());
		UIManager.put("CheckBox.font", getFONT());
		UIManager.put("TabbedPane.font", getFONT());
		UIManager.put("MenuItem.font", getFONT());
		UIManager.put("RadioButtonMenuItem.font", getFONT());
		UIManager.put("Menu.font", getFONT());

		ToolTipManager.sharedInstance().setDismissDelay(15000);// 15 seconds
	}

	public static Font getFONT() {
		Font font = new Font(Font.DIALOG, Font.LAYOUT_LEFT_TO_RIGHT,
				getFontSize());
		return font;// new Font(Font.SANS_SERIF,Font.BOLD,17);
	}

	public static int getFontSize() {
		return FONT_SIZE;
	}

	public static String getHTML_Text(String text) {
		return "<html><font size=" + (getFontSize() / 3) + ">" + text
				+ "</html>";
	}

	private static String createFolder(String path) {
		File folder = new File(path + "/" + MyGetDate.getYear() + "/"
				+ MyGetDate.getCurrentMonth() + "/" + MyGetDate.getCurrentDay());
		if (!folder.exists()) {
			folder.mkdirs();
		}
		return folder.getAbsolutePath();
	}

	public static int compareStringsInReverseOrder(String a, String b) {
		for (int i = 6; i <= 9; i++) {
			if (a.charAt(i) < b.charAt(i)) {
				return -1;
			} else if (a.charAt(i) > b.charAt(i)) {
				return 1;
			}
		}
		for (int i = 3; i <= 4; i++) {
			if (a.charAt(i) < b.charAt(i)) {
				return -1;
			} else if (a.charAt(i) > b.charAt(i)) {
				return 1;
			}
		}
		for (int i = 0; i <= 1; i++) {
			if (a.charAt(i) < b.charAt(i)) {
				return -1;
			} else if (a.charAt(i) > b.charAt(i)) {
				return 1;
			}
		}
		return 0;
	}
	public static void setDynamicSizedIcon(JButton button, ImageIcon icon) {
		Image img = icon.getImage();
		Image newimg = img.getScaledInstance(button.getPreferredSize().width,
				button.getPreferredSize().height, Image.SCALE_SMOOTH);
		icon = new ImageIcon(newimg);
		button.setIcon(icon);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub

	}

}
