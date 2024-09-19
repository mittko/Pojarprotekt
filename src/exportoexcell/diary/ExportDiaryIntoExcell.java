package exportoexcell.diary;

import exceptions.InOutException;
import http.RequestCallback2;
import http.reports.GetReportsService;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.*;
import jxl.write.WritableFont.FontName;

import models.ClientInfo;
import models.DiaryModel;
import models.ProtokolModel;
import mydate.MyGetDate;
import utils.MainPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class ExportDiaryIntoExcell {

	WritableCellFormat font9 = getExcellCellFormat(WritableFont.ARIAL, 9,
			Colour.BLACK);
	WritableCellFormat font8 = getExcellCellFormat(WritableFont.ARIAL, 8,
			Colour.BLACK);
	WritableCellFormat font7 = getExcellCellFormat(WritableFont.ARIAL, 7,
			Colour.BLACK);
	WritableCellFormat fontH10 = getExcellCellFormat(WritableFont.ARIAL, 10,
			Colour.BLACK);
	WritableCellFormat fontH9 = getExcellCellFormat(WritableFont.ARIAL, 9,
			Colour.BLACK);
	WritableCellFormat fontH8 = getExcellCellFormat(WritableFont.ARIAL, 8,
			Colour.BLACK);
	WritableCellFormat fontH7 = getExcellCellFormat(WritableFont.ARIAL, 7,
			Colour.BLACK);
	WritableCellFormat titleFont10 = getExcellCellFormat(WritableFont.ARIAL,
			10, Colour.BLACK);

	String[] headers = { "No", "No на\nпрото-\nкол",
			"No на\nфактура\n/Дата на\nиздаване/", "Клиент /Фирма/", "Адрес",
			"Телефон", "Общ\n брой\n пожа-рогаси-\nтели",
			"Забележки (резервни части,бракуване \nи др.)", "Кат-\nего\nрия",
			"No", "Дата на\nобслуж-\nването", "Вид на\nпротивопожарния уред",
			"Марка,модел", "Сериен No",
			"Вид на из-\nвършеното\nобслуж-\nване (ТО,П,\nХИ)", "Стикер No",
			"Търговско наименова-\nние на пожарога-\nсителното в-во",
			"Дата на\nследващо\nобслужва-\nне (по коло-\nна 6)",
			"Подпис на\nтехника" };

	String[] headers2 = { "А", "Б", "В", "Г", "Д", "Е", "Ж", "З", "И", "1",
			"2", "3", "4", "5", "6", "7", "8", "9", "10" };

	int[] columnWidths = { 4, 8, 21, 26, 14, 11, 5, 13, 4, // 97
			4, 9, 13, 12, 8, 7, 14, 11, 8, 11 };// 97

	WritableCellFormat[] fonts = { fontH9, fontH8, fontH8, fontH8, fontH8,
			fontH9, fontH8, fontH8, fontH8, fontH9, fontH8, fontH8, fontH8,
			fontH9, fontH8, fontH9, fontH8, fontH8, fontH8 };

	String TO;
	String P;
	String HI;
	HashMap<String, ClientInfo> clientMap = new HashMap<String, ClientInfo>();


	byte[] imageInBytes1;// Гошката
	byte[] imageInBytes2; // Ице
	byte[] imageInBytes3; // Шеф
	byte[] imageInBytes4;// Спас

	public ExportDiaryIntoExcell() {
		// TODO Auto-generated constructor stub
	}

	private void init() {
		try {
			fontH10.setWrap(true);
			fontH10.setAlignment(Alignment.CENTRE);
			fontH10.setVerticalAlignment(VerticalAlignment.CENTRE);
			fontH9.setWrap(true);
			fontH9.setAlignment(Alignment.CENTRE);
			fontH9.setVerticalAlignment(VerticalAlignment.CENTRE);
			fontH8.setWrap(true);
			fontH8.setAlignment(Alignment.CENTRE);
			fontH8.setVerticalAlignment(VerticalAlignment.CENTRE);
			fontH7.setWrap(true);
			fontH7.setAlignment(Alignment.CENTRE);
			fontH7.setVerticalAlignment(VerticalAlignment.CENTRE);
		} catch (WriteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String workingDir = System.getProperty("user.dir");
		imageInBytes1 = getImageInBytes(workingDir + "/Images/гошката.jpg");
		imageInBytes2 = getImageInBytes(workingDir + "/Images/ице.jpg");
		imageInBytes3 = getImageInBytes(workingDir + "/Images/шеф.jpg");
		imageInBytes4 = getImageInBytes(workingDir + "/Images/спас.jpg");
	}

	public void createDiary(String from, String to, JDialog jDialog)  {

		jDialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));

		init();

		WritableWorkbook workBook;
		// String userhomeFolder = System.getProperty("user.home");
		// File excellFileOnDesktop = new
		// File(userhomeFolder+"/Desktop/"+output);
		File diaryFile = new File(MainPanel.DIARY_EXCELL_PATH + "/"
				+ MyGetDate.getReversedSystemDate() + " Дневник.xls");
		try {
			workBook = Workbook.createWorkbook(diaryFile);
			WritableSheet sheet = workBook.createSheet("Fisrt Sheet", 0);

			setFoot(sheet);
			// sheet.setPageSetup(PageOrientation.PORTRAIT, PaperSize.A4, 50,
			// 20);

			// sheet.setRowView(1, 26*50);

			titleFont10.setAlignment(Alignment.RIGHT);
			Label title = new Label(0, 0, "Приложение No:8 към член. 31 ал. 4",
					titleFont10);
			sheet.addCell(title);
			sheet.mergeCells(0, 0, 16, 0); // (col,row,cellspan,row);

			// get data from Protokol

			GetReportsService service = new GetReportsService();
			service.getDiary(from,to, new RequestCallback2() {
				@Override
				public <T> void callback(T t) {


					DiaryModel<T> diary = (DiaryModel<T>) t;

					List<ProtokolModel> objects = diary.getProtokolModels();

					if (objects != null) {

						try {

							Collections.sort(objects, new Comparator<ProtokolModel>() {
								@Override
								public int compare(ProtokolModel o1, ProtokolModel o2) {

									String a = o1.getDate();
									String b = o2.getDate();
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
							});

							// get invoice numbers
							HashMap<String, String> invoiceNumbersMap = diary.getInvoiceNumbers();

							sheet.getSettings().setScaleFactor(95);
							sheet.getSettings().setPrintGridLines(true);
							sheet.getSettings().setLeftMargin(0.25);// <- use inches this is
							// santimeters -> (0.64);
							sheet.getSettings().setRightMargin(0.25);// (0.64);
							sheet.getSettings().setTopMargin(0.35);// (0.91);
							sheet.getSettings().setBottomMargin(0.35);// (0.91);
							sheet.getSettings().setPageBreakPreviewMode(true);// !!!!!!

							// sheet.getSettings().setFitToPages(true);
							// sheet.getSettings().setShowGridLines(true);
							// sheet.getSettings().setPaperSize(PaperSize.A4);

							setHeaders(sheet, 1, 2);
							int currentHeight = (sheet.getRowView(0).getSize()
									+ sheet.getRowView(1).getSize() + sheet.getRowView(2)
									.getSize());
							double pageHeight = 56 * sheet.getSettings().getDefaultRowHeight();// 55

							// sheet.getSettings().setZoomFactor(100);

							clientMap = diary.getClientModelHashMap();

							int tableRow = 3;
							int dataRow = 0;
							int countEqualsName = 1;
							int lastIndex = tableRow;
							while (dataRow < objects.size()) {
								ProtokolModel prevModel = null;
								if (dataRow > 0) {
									prevModel =  objects.get(dataRow - 1);
								}
								ProtokolModel model =  objects.get(dataRow);
								if (model.getNumber() == null) {
									dataRow++;
									continue;
								}
								if (currentHeight >= pageHeight) {
									// sheet.addRowPageBreak(tableRow);
									setHeaders(sheet, tableRow, tableRow + 1);
									currentHeight = (sheet.getRowView(tableRow).getSize() + sheet
											.getRowView(tableRow + 1).getSize());
									tableRow += 2;

								}

								// String clientName = extractedData.get(dataRow)[0].toString();
								String protokolNumber = model.getNumber();// extractedData.get(dataRow)[12].toString();
								if (dataRow == 0 || !protokolNumber.equals(prevModel.getNumber())) {

									String clientString = model.getClient();// (String) extractedData.get(dataRow)[0];
									// set left alignment

									Label protokolNumberLabel = new Label(1, tableRow,
											protokolNumber, font9);

									sheet.addCell(protokolNumberLabel);


									String invoiceNum = invoiceNumbersMap.get(protokolNumber);
									if(invoiceNum == null) { // no made invoice for this protokol
										invoiceNum = "    -     ";
									}
									Label invoiceNumberLabel = new Label(2, tableRow,invoiceNum
											, font9);

									try {
										sheet.addCell(invoiceNumberLabel);
									} catch (WriteException e) {
										throw new RuntimeException(e);
									}

									Label client = new Label(3, tableRow, clientString, font8);

									sheet.addCell(client);

									ClientInfo clientInfo = clientMap.get(clientString);

									if(clientInfo == null) { // occasionally this client is deleted
										clientInfo = new ClientInfo();
										clientInfo.setCity("    -     ");
										clientInfo.setTel("    -      ");
									};

									Label address = new Label(4, tableRow,clientInfo.getCity(), font8);
									sheet.addCell(address);

									Label telefon = new Label(5, tableRow, clientInfo.getTel(), font9);

									sheet.addCell(telefon);

									// // obsht broi za daden client
									Label broiPojarogasiteli = new Label(6, lastIndex,
											countEqualsName + "", fontH8);
									sheet.addCell(broiPojarogasiteli);

									lastIndex = tableRow;

									countEqualsName = 1;
								} else {
									countEqualsName++;
								}

								// int add = 0;

								// sheet.setRowView(row+c+add, 230);

								// set category
								Label category = new Label(8, tableRow,
										model.getCategory(), fontH8);
								sheet.addCell(category);

								// set numbers in first column
								sheet.addCell(new Label(0, tableRow, (tableRow - 2) + "",
										fontH8));

								// set number in second column
								Label No = new Label(9, tableRow, (tableRow - 2) + "", fontH8);
								sheet.addCell(No);

								// set date on doing
								Label date = new Label(10, tableRow,
										model.getDate(), font8);
								sheet.addCell(date);

								// set type and wheight
								String wheight = model.getWheight();
								if (wheight.contains("литра")) {
									wheight = wheight.replace("литра", "л");
								}
								// get type of doing
								String stringType = model.getType();
								// remove unused characters
								String originType = stringType;
								if (originType.contains("Пожарогасител")) {
									originType = originType.replace("Пожарогасител", "");
								} else if (originType.contains("пожарогасител")) {
									originType = originType.replace("пожарогасител", "");
								}
								if (originType.contains("( Нов )")) {
									originType = originType.replace("( Нов )", "");
								}
								Label type = new Label(11, tableRow, originType.trim() + " "
										+ wheight, font8);

								sheet.addCell(type);

								// chasti
								String stringChasti = null;

								if (stringType.endsWith("( Нов )")) {
									// ako e nov miama chasti
									stringChasti = "нов";
								} else {
									// ako ne e nov ima chasti
									stringChasti = getParts(model.getParts());

									stringChasti = stringChasti.replace("Техническо обслужване на Пожарогасител", "ТО")
											.replace("Презареждане на Пожарогасител", "П")
											.replace("Хидростатично Изпитване на Пожарогасител", "ХИ");

								}

								Label chasti = new Label(7, tableRow, stringChasti, fontH8);// ???
								// fontH8
								// gi
								// sabira
								// vsichkite
								sheet.addCell(chasti);

								// set model
								Label brand = new Label(12, tableRow,
										model.getBrand(), font8);
								sheet.addCell(brand);
								// set serial
								Label serial = new Label(13, tableRow,
										model.getSerial(), fontH9);
								sheet.addCell(serial);
								// obslujvane
								TO = model.getT_O();
								P = model.getP();
								HI = model.getHi();
								String doing = (!TO.equals("не") ? "ТО;" : "")
										+ (!P.equals("не") ? "П;" : "")
										+ (!HI.equals("не") ? "ХИ" : "");
								Label obslujvane = new Label(14, tableRow, doing, font8);
								sheet.addCell(obslujvane);
								// set barcod
								Label barcod = new Label(15, tableRow,
										model.getBarcod(), font9);
								sheet.addCell(barcod);
								// targovsko naimenovanie
								// convert string type to get targovsko naimenovanie na poj v-vo
								if (stringType.endsWith("( Нов )")) {
									// do nothing
								} else if (stringType.toUpperCase().contains("ABC")
										|| stringType.toUpperCase().contains("АВС")) {
									stringType = MainPanel.type_Prah_ABC;
								} else if (stringType.toUpperCase().contains("BC")
										|| stringType.toUpperCase().contains("ВС")) {
									stringType = MainPanel.type_Prah_BC;
								} else if (stringType.toLowerCase().contains("воден")) {
									stringType = MainPanel.type_Water;
								} else if (stringType.toLowerCase().contains("водопенен")) {
									stringType = MainPanel.type_Water_Fame;
								} else if (stringType.toUpperCase().contains("CO2")
										|| stringType.toUpperCase().contains("СО2")) {
									stringType = MainPanel.type_CO2;
								}
								// постави номер на разрешителното
								String nomerRazreshitelno;
								if (stringType.equals(MainPanel.type_Prah_BC)
										&& (!TO.equals("не") && !P.equals("не"))) {
									nomerRazreshitelno = "Кобра ABC";
								} else if (stringType.equals(MainPanel.type_Prah_ABC)
										&& (!TO.equals("не") && !P.equals("не"))) {
									nomerRazreshitelno = "Кобра ABC";
								} else if (stringType.equals(MainPanel.type_Water_Fame)
										&& (!TO.equals("не") && !P.equals("не"))) {
									nomerRazreshitelno = "STHAMEX";
								} else if (stringType.endsWith("( Нов )")) {
									nomerRazreshitelno = "нов";
								} else {
									nomerRazreshitelno = "";
								}
								//
								Label targovsko = new Label(16, tableRow, nomerRazreshitelno,
										font8);
								sheet.addCell(targovsko);
								// next date
								String nextObslujvane = "";
								if (doing.equals("ХИ")) {//
									// ako e samo HI -> 5 godini
									nextObslujvane = HI;// GetDate.getDateAfterToday(5 * 365);
								} else {
									nextObslujvane = TO;// GetDate.getDateAfterToday(1 * 365);
								}
								Label nextDate = new Label(17, tableRow, nextObslujvane, font8);
								sheet.addCell(nextDate);

								String technikName = model.getPerson();
								// System.out.println(technikName);
								switch (technikName) {
									case "Георги Ковачки":
										sheet.addImage(getImage(tableRow, 18, imageInBytes1));
										break;
									case "Христо Георгин":
										sheet.addImage(getImage(tableRow, 18, imageInBytes2));
										break;
									case "Георги Ильов":
										sheet.addImage(getImage(tableRow, 18, imageInBytes3));
										break;
									case "Спас Ильов":
										sheet.addImage(getImage(tableRow, 18, imageInBytes4));
										break;
									default: // ->Георги Ильов
										sheet.addImage(getImage(tableRow, 18, imageInBytes3));
										break;
								}

								currentHeight += sheet.getRowView(tableRow).getSize();
								tableRow++;
								dataRow++;

							}

							// add for the last element obsht broi za daden client
							Label broiPojarogasiteli = new Label(6, lastIndex, countEqualsName
									+ "", fontH8);

							sheet.addCell(broiPojarogasiteli);

							workBook.write();
							workBook.close();

						} catch (Exception e) {

						}

						jDialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
						jDialog.dispose();
					} else {
						jDialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
						jDialog.dispose();
					}

					if (Desktop.isDesktopSupported()) {
						try {
							Desktop.getDesktop().open(diaryFile);
						} catch (IOException e) {
							throw new RuntimeException(e);
						}
					} else {
						System.err.println("Desktop is not supported !!!");
					}

				}
			});


		} catch (IOException | WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	private void setFoot(WritableSheet sheet) {
		/*
		 * HeaderFooter footer = new HeaderFooter();
		 * footer.getLeft().appendDate();
		 * 
		 * footer.getCentre().append("Fuck off ");
		 * footer.getCentre().appendPageNumber();
		 * footer.getCentre().append("/");
		 * footer.getCentre().appendTotalPages();
		 * 
		 * footer.getRight().appendTime();
		 * sheet.getSettings().setFooter(footer);
		 */
	}

	private WritableImage getImage(int row, int column, byte[] bytes) {
		// image.setImageAnchor(WritableImage.NO_MOVE_OR_SIZE_WITH_CELLS);
		return new WritableImage(column, row, 1, 1, bytes);
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ExportDiaryIntoExcell ex = new ExportDiaryIntoExcell();
		// ex.init();
		ex.createDiary("25.02.2018", "01.03.2018",null);// "25.02.2018","01.03.2018"
		// ex.read();
		// ex.extractDataFromDatabase(GetDate.getReversedSystemDate()) ;
	}

	private void read() throws BiffException, IOException {
		// Workbook workbook = Workbook.getWorkbook(
		// new File(MainPanel.DIARY_EXCELL_PATH + "/"
		// + GetDate.getReversedSystemDate() + " Дневник.xls"));
		// Sheet sheet = workbook.getSheet(1);
		// for(int i = 0;i < sheet.getColumns();i++) {
		// for(int j = 0;j < sheet.getRows();j++) {
		// System.out.println(sheet.getRowView(j).getSize());
		// Cell cell1 = sheet.getCell(i,j);
		// System.out.print("*"+cell1.getCellFeatures().getCommentHeight()+"*   ");
		//
		// }
		// System.out.println();
		// }
	}

	@SuppressWarnings("deprecation")
	private void setHeaders(WritableSheet sheet, int row1, int row2) {

		int row = row1;
		// sheet.insertRow(row);
		for (int column = 0; column < headers.length; column++) {
			Label label = new Label(column, row, headers[column], fonts[column]);

			try {
				sheet.addCell(label);

				sheet.setColumnView(column, columnWidths[column]);
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		row = row2;

		for (int column = 0; column < headers2.length; column++) {
			Label label = new Label(column, row, headers2[column],
					fonts[column]);
			try {
				sheet.addCell(label);

				sheet.setColumnView(column, columnWidths[column]);
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private WritableCellFormat getExcellCellFormat(FontName fontName,
												   int fontSize, Colour color) {
		/*
		 * Colour colour = Colour.BLACK; WritableFont wfontStatus = new
		 * WritableFont(WritableFont.createFont("Arial"),
		 * WritableFont.DEFAULT_POINT_SIZE, WritableFont.NO_BOLD, false,
		 * UnderlineStyle.NO_UNDERLINE, colour);
		 */
		WritableCellFormat sheet = new WritableCellFormat();// (wfontStatus);
		sheet.setFont(new WritableFont(fontName, fontSize,
				WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, color));

		/*
		 * try { fCellstatus.setWrap(true);
		 * fCellstatus.setAlignment(jxl.format.Alignment.CENTRE);
		 * fCellstatus.setVerticalAlignment
		 * (jxl.format.VerticalAlignment.CENTRE);
		 * fCellstatus.setBorder(jxl.format.Border.ALL,
		 * jxl.format.BorderLineStyle.MEDIUM, jxl.format.Colour.BLUE2); } catch
		 * (WriteException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		return sheet;
	}

	private String getParts(String parts) {
		String[] p = parts.split(",");
		StringBuilder sb = new StringBuilder();
		for (String s : p) {
			if (s.equals(MainPanel.Plomba) || s.equals(MainPanel.Sphlend)
					|| s.equals(MainPanel.PrahBC)
					|| s.equals(MainPanel.PrahABC)
					|| s.equals(MainPanel.GasitelnoVeshtestvoVoda)
					|| s.equals(MainPanel.GasitelnoVeshtestvoVodaPyana)
					|| s.equals(MainPanel.GasitelnoVeshtestvoCO2)) {

				continue;
			}
			sb.append(s).append(";");

		}
		return sb.toString();
	}

	private byte[] getImageInBytes(String pathToFile) {
		File imageFile = new File(pathToFile);
		BufferedImage buffImage = null;
		ByteArrayOutputStream baos = null;
		try {
			buffImage = ImageIO.read(imageFile);
			baos = new ByteArrayOutputStream();
			ImageIO.write(buffImage, "JPG", baos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			InOutException.showErrorMessage(e);
			e.printStackTrace();
		}
		return baos.toByteArray();
	}
}
