package exportoexcell;

import jxl.Cell;
import jxl.CellView;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.*;
import jxl.write.WritableFont.FontName;
import jxl.write.biff.RowsExceededException;
import net.GetCurrentIP;
import utils.MainPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class WriteToExcellFile {

	public static void main(String[] args) throws RowsExceededException,
			WriteException, IOException {
		// TODO Auto-generated method stub
		WriteToExcellFile excell = new WriteToExcellFile();
		// excell.Export(MainPanel.SERVICE, "Сервизни Поръчки.xls");
		excell.saveInExcelAvailables(null, null, "nqkakwa data",
				"nqkakwa data", "Цени на Части.xls");
	}

	public void Export(String dbTable, String excellFile) { // .xls

		try {
			ArrayList<Object[]> data = copyData(dbTable);
			writeInFile(data, excellFile);
		} catch (WriteException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveInExcelAvailables(JTable table, DefaultTableModel dftm,
			String from, String to, String output) throws IOException,
			RowsExceededException, WriteException {
		WritableWorkbook wworkbook;
		File excellFileIntoDesktop = new File(MainPanel.SALES_EXCELL_PATH
				+ "\\" + output);
		wworkbook = Workbook.createWorkbook(excellFileIntoDesktop);
		WritableSheet wsheet = wworkbook.createSheet("First Sheet", 0);

		WritableCellFormat headerFormat = getExcellCellFormat(
				WritableFont.ARIAL, 15, Colour.BLACK);

		// set header
		Label firstColumn = new Label(0, 1, "Артикул");
		firstColumn.setCellFormat(headerFormat);
		wsheet.addCell(firstColumn);

		Label secondColumn = new Label(1, 0, "Наличност към " + from);
		secondColumn.setCellFormat(headerFormat);
		wsheet.addCell(secondColumn);

		Label thirdColumn = new Label(2, 0, "");
		thirdColumn.setCellFormat(headerFormat);
		wsheet.addCell(thirdColumn);

		Label fourthColumn = new Label(3, 0, "");
		fourthColumn.setCellFormat(headerFormat);
		wsheet.addCell(fourthColumn);

		Label fifthColumn = new Label(4, 0, "Доставки за период " + from
				+ " : " + to);
		fifthColumn.setCellFormat(headerFormat);
		wsheet.addCell(fifthColumn);

		Label sixthColumn = new Label(5, 0, "");
		sixthColumn.setCellFormat(headerFormat);
		wsheet.addCell(sixthColumn);

		Label sevenColumn = new Label(6, 0, "");
		sevenColumn.setCellFormat(headerFormat);
		wsheet.addCell(sevenColumn);

		Label eightColumn = new Label(7, 0, "Продажби за период " + from
				+ " : " + to);
		eightColumn.setCellFormat(headerFormat);
		wsheet.addCell(eightColumn);

		Label nineColumn = new Label(8, 0, "");
		nineColumn.setCellFormat(headerFormat);
		wsheet.addCell(nineColumn);

		Label tenColumn = new Label(9, 0, "");
		tenColumn.setCellFormat(headerFormat);
		wsheet.addCell(tenColumn);

		Label elevenColumn = new Label(10, 0, "Наличност към " + to);
		elevenColumn.setCellFormat(headerFormat);
		wsheet.addCell(elevenColumn);

		Label twelveColumn = new Label(11, 0, "");
		twelveColumn.setCellFormat(headerFormat);
		wsheet.addCell(twelveColumn);

		Label thirteenColumn = new Label(12, 0, "");
		thirteenColumn.setCellFormat(headerFormat);
		wsheet.addCell(thirteenColumn);

		CellView cellView = wsheet.getColumnView(0);
		wsheet.setColumnView(0, cellView);

		wsheet.mergeCells(1, 0, 3, 0);
		wsheet.mergeCells(4, 0, 6, 0);
		wsheet.mergeCells(7, 0, 9, 0);
		wsheet.mergeCells(10, 0, 12, 0);

		// add second columns headers
		for (int i = 0; i < 12; i += 3) {
			Label brojColumn = new Label(1 + i, 1, "брой");
			brojColumn.setCellFormat(headerFormat);
			wsheet.addCell(brojColumn);

			Label priceColumn = new Label(2 + i, 1, "ед. цена");
			priceColumn.setCellFormat(headerFormat);
			wsheet.addCell(priceColumn);

			Label valueColumn = new Label(3 + i, 1, "ст-ст");
			valueColumn.setCellFormat(headerFormat);
			wsheet.addCell(valueColumn);

		}

		Colour currColor = Colour.BLACK;
		for (int i = 2; i < dftm.getRowCount(); i++) {

			for (int j = 0; j < dftm.getColumnCount(); j++) {

				if (j != 0 && j % 3 == 0) {
					currColor = Colour.RED;
				} else {
					currColor = Colour.BLACK;
				}
				WritableCellFormat cellFormat = getExcellCellFormat(
						WritableFont.ARIAL, 14, currColor);
				String contents = dftm.getValueAt(i,j).toString();
				Label label = new Label(j, i, contents);

				if(j != 0 && j % 3 == 0) {
					Number number = new Number(j,i,Double.parseDouble(contents));
					wsheet.addCell(number);
					number.setCellFormat(cellFormat);
				} else {
					wsheet.addCell(label);
					label.setCellFormat(cellFormat);
				}

				CellView cellView2 = wsheet.getColumnView(j);
				setColumnWidths(j, cellView2);
				wsheet.setColumnView(j, cellView2); // this set auto size of
				// // cell
			}

		}
		wworkbook.write();
		wworkbook.close();
		if (Desktop.isDesktopSupported()) {
			java.awt.Desktop.getDesktop().open(excellFileIntoDesktop);
		}
	}

	public void saveInExcelSales(JTable table, DefaultTableModel dftm,
			String output) throws IOException, RowsExceededException,
			WriteException {
		WritableWorkbook wworkbook;
		File excellFileIntoDesktop = new File(MainPanel.SALES_EXCELL_PATH
				+ "\\" + output);
		wworkbook = Workbook.createWorkbook(excellFileIntoDesktop);
		WritableSheet wsheet = wworkbook.createSheet("First Sheet", 0);

		WritableCellFormat headerFormat = getExcellCellFormat(
				WritableFont.ARIAL, 15, Colour.BLACK);

		// set header
		Label firstColumn = new Label(0, 0, "1");
		firstColumn.setCellFormat(headerFormat);
		wsheet.addCell(firstColumn);

		Label secondColumn = new Label(1, 0, "2");
		secondColumn.setCellFormat(headerFormat);
		wsheet.addCell(secondColumn);

		Label thirdColumn = new Label(2, 0, "3");
		thirdColumn.setCellFormat(headerFormat);
		wsheet.addCell(thirdColumn);

		Label fourthColumn = new Label(3, 0, "4");
		fourthColumn.setCellFormat(headerFormat);
		wsheet.addCell(fourthColumn);

		Label fifthColumn = new Label(4, 0, "5");
		fifthColumn.setCellFormat(headerFormat);
		wsheet.addCell(fifthColumn);

		Label sixthColumn = new Label(4, 0, "5");
		sixthColumn.setCellFormat(headerFormat);
		wsheet.addCell(sixthColumn);

		CellView cellView = wsheet.getColumnView(0);
		wsheet.setColumnView(0, cellView);

		Colour currColor = Colour.BLACK;
		for (int i = 0; i < dftm.getRowCount(); i++) {

			if (dftm.getValueAt(i, 0).toString().contains("Фактура No:")) {
				currColor = Colour.RED;
			} else {
				currColor = Colour.BLACK;
			}

			WritableCellFormat cellFormat = getExcellCellFormat(
					WritableFont.ARIAL, 14, currColor);

			for (int j = 0; j < dftm.getColumnCount(); j++) {
				Label label = new Label(j, i + 1, dftm.getValueAt(i, j)
						.toString());

				label.setCellFormat(cellFormat);

				wsheet.addCell(label);
				CellView cellView2 = wsheet.getColumnView(j);
				if (j == 0) {
					cellView2.setSize(65 * 256);
				} else {
					cellView2.setAutosize(true);
				}
				wsheet.setColumnView(j, cellView2); // this set auto size of
													// cell
			}

		}
		wworkbook.write();
		wworkbook.close();
		if (Desktop.isDesktopSupported()) {
			java.awt.Desktop.getDesktop().open(excellFileIntoDesktop);
		}
	}

	public void saveInExcelDelivery(JTable table, DefaultTableModel dftm,
			String output) throws IOException, RowsExceededException,
			WriteException {
		WritableWorkbook wworkbook;
		File excellFileIntoDesktop = new File(MainPanel.DELIVERY_EXCELL_PATH
				+ "\\" + output);
		wworkbook = Workbook.createWorkbook(excellFileIntoDesktop);
		WritableSheet wsheet = wworkbook.createSheet("First Sheet", 0);

		WritableCellFormat headerFormat = getExcellCellFormat(
				WritableFont.ARIAL, 15, Colour.BLACK);

		// set header
		Label firstColumn = new Label(0, 0, "1");
		firstColumn.setCellFormat(headerFormat);
		wsheet.addCell(firstColumn);

		Label secondColumn = new Label(1, 0, "2");
		secondColumn.setCellFormat(headerFormat);
		wsheet.addCell(secondColumn);

		Label thirdColumn = new Label(2, 0, "3");
		thirdColumn.setCellFormat(headerFormat);
		wsheet.addCell(thirdColumn);

		Label fourthColumn = new Label(3, 0, "4");
		fourthColumn.setCellFormat(headerFormat);
		wsheet.addCell(fourthColumn);

		Label fifthColumn = new Label(4, 0, "5");
		fifthColumn.setCellFormat(headerFormat);
		wsheet.addCell(fifthColumn);

		CellView cellView = wsheet.getColumnView(0);
		wsheet.setColumnView(0, cellView);

		Colour currColor = Colour.BLACK;
		for (int i = 0; i < dftm.getRowCount(); i++) {

			if (dftm.getValueAt(i, 0).toString().contains("Фактура No:")) {
				currColor = Colour.RED;
			} else {
				currColor = Colour.BLACK;
			}

			WritableCellFormat cellFormat = getExcellCellFormat(
					WritableFont.ARIAL, 14, currColor);

			for (int j = 0; j < dftm.getColumnCount(); j++) {
				Label label = new Label(j, i + 1, dftm.getValueAt(i, j)
						.toString());

				label.setCellFormat(cellFormat);

				wsheet.addCell(label);
				CellView cellView2 = wsheet.getColumnView(j);
				if (j == 0) {
					cellView2.setSize(65 * 256);
				} else {
					cellView2.setAutosize(true);
				}
				wsheet.setColumnView(j, cellView2); // this set auto size of
													// cell
			}

		}
		wworkbook.write();
		wworkbook.close();
		if (Desktop.isDesktopSupported()) {
			java.awt.Desktop.getDesktop().open(excellFileIntoDesktop);
		}
	}

	public void saveInExcelNewExtinguishers(JTable table,
			DefaultTableModel dftm, String output) throws IOException,
			RowsExceededException, WriteException {
		long allQuantity = 0;
		WritableWorkbook wworkbook;
		File excellFileIntoDesktop = new File(
				MainPanel.NEWEXTINGUISHERS_EXCELL_PATH + "\\" + output);
		wworkbook = Workbook.createWorkbook(excellFileIntoDesktop);
		WritableSheet wsheet = wworkbook.createSheet("First Sheet", 0);

		WritableCellFormat headerFormat = getExcellCellFormat(
				WritableFont.ARIAL, 15, Colour.BLACK);

		// set header
		Label type = new Label(0, 0, "Вид");
		type.setCellFormat(headerFormat);
		wsheet.addCell(type);

		Label wheight = new Label(1, 0, "Маса");
		wheight.setCellFormat(headerFormat);
		wsheet.addCell(wheight);

		Label category = new Label(2, 0, "Категория");
		category.setCellFormat(headerFormat);
		wsheet.addCell(category);

		Label brand = new Label(3, 0, "Марка");
		brand.setCellFormat(headerFormat);
		wsheet.addCell(brand);

		Label quantity = new Label(4, 0, "Количество");
		quantity.setCellFormat(headerFormat);
		wsheet.addCell(quantity);

		Label value = new Label(5, 0, "Ед. цена");
		value.setCellFormat(headerFormat);
		wsheet.addCell(value);

		// invoice, client, date, operator, percentProfit
		Label invoice = new Label(6, 0, "Фактура No");
		invoice.setCellFormat(headerFormat);
		wsheet.addCell(invoice);

		Label client = new Label(7, 0, "Контрагент");
		client.setCellFormat(headerFormat);
		wsheet.addCell(client);

		Label date = new Label(8, 0, "Дата");
		date.setCellFormat(headerFormat);
		wsheet.addCell(date);

		Label seller = new Label(9, 0, "Оператор");
		seller.setCellFormat(headerFormat);
		wsheet.addCell(seller);

		Label percentProfit = new Label(10, 0, "Процент печалба");
		percentProfit.setCellFormat(headerFormat);
		wsheet.addCell(percentProfit);

		CellView cellView = wsheet.getColumnView(0);
		cellView.setAutosize(true);
		wsheet.setColumnView(0, cellView);


		Colour currColor = Colour.BLUE;
		// boolean boolColor = true;
		for (int i = 0; i < dftm.getRowCount(); i++) {

			if (i > 0) {
				if (!dftm.getValueAt(i - 1, 7).equals(dftm.getValueAt(i, 7))) {
					currColor = (currColor == Colour.GREEN) ? Colour.BLUE
							: Colour.GREEN;
				}
			}

			WritableCellFormat cellFormat = getExcellCellFormat(
					WritableFont.ARIAL, 14, currColor);

			for (int j = 0; j < dftm.getColumnCount(); j++) {
				Label label = new Label(j, i + 1, dftm.getValueAt(i, j)
						.toString());
				// calculate all available artikuls
				if (j == 4) {
					int q = 0;
					try {
						q = Integer.parseInt(label.getContents());
					} catch (Exception ex) {
						q = 0;
					}
					allQuantity += q;
				}

				label.setCellFormat(cellFormat);

				wsheet.addCell(label);
				CellView cellView2 = wsheet.getColumnView(j);
				cellView2.setAutosize(true);
				wsheet.setColumnView(j, cellView); // this set auto size of cell
			}

		}

		Label broiPojarogasiteli = new Label(4, dftm.getRowCount() + 1,
				"Общо : " + allQuantity, headerFormat);

		wsheet.addCell(broiPojarogasiteli);

		wworkbook.write();
		wworkbook.close();
		if (Desktop.isDesktopSupported()) {
			java.awt.Desktop.getDesktop().open(excellFileIntoDesktop);
		}
	}

	public void saveInExcelArtikuls(JTable table, DefaultTableModel dftm,
			String output) throws IOException, RowsExceededException,
			WriteException {
		long allQuantity = 0;
		WritableWorkbook wworkbook;
		// na desktopa String userHomeFolder = System.getProperty("user.home"+
		// "/Desktop/");
		File excellFileIntoDesktop = new File(MainPanel.ARTIKULS_EXCELL_PATH
				+ "\\" + output);
		wworkbook = Workbook.createWorkbook(excellFileIntoDesktop);
		WritableSheet wsheet = wworkbook.createSheet("First Sheet", 0);

		WritableCellFormat headerFormat = getExcellCellFormat(
				WritableFont.ARIAL, 15, Colour.BLACK);

		// set header
		Label artikul = new Label(0, 0, "Артикули");
		artikul.setCellFormat(headerFormat);
		wsheet.addCell(artikul);

		Label quantity = new Label(1, 0, "Налични");
		quantity.setCellFormat(headerFormat);
		wsheet.addCell(quantity);

		Label measure = new Label(2, 0, "М.ед");
		measure.setCellFormat(headerFormat);
		wsheet.addCell(measure);

		Label value = new Label(3, 0, "Единична цена");
		value.setCellFormat(headerFormat);
		wsheet.addCell(value);

		// invoice, client, date, operator, percentProfit
		Label invoice = new Label(4, 0, "Фактура No");
		invoice.setCellFormat(headerFormat);
		wsheet.addCell(invoice);

		Label client = new Label(5, 0, "Контрагент");
		client.setCellFormat(headerFormat);
		wsheet.addCell(client);

		Label date = new Label(6, 0, "Дата");
		date.setCellFormat(headerFormat);
		wsheet.addCell(date);

		Label seller = new Label(7, 0, "Оператор");
		seller.setCellFormat(headerFormat);
		wsheet.addCell(seller);

		Label percentProfit = new Label(8, 0, "Процент печалба");
		percentProfit.setCellFormat(headerFormat);
		wsheet.addCell(percentProfit);

		CellView cellView = wsheet.getColumnView(0);
		cellView.setAutosize(true);
		wsheet.setColumnView(0, cellView);


		Colour currColor = Colour.BLUE;
		for (int i = 0; i < dftm.getRowCount(); i++) {
			if (i > 0) {
				if (!dftm.getValueAt(i - 1, 5).equals(dftm.getValueAt(i, 5))) {
					currColor = (currColor == Colour.GREEN) ? Colour.BLUE
							: Colour.GREEN;
				}
			}
			WritableCellFormat cellFormat = getExcellCellFormat(
					WritableFont.ARIAL, 14, currColor);

			for (int j = 0; j < dftm.getColumnCount(); j++) {
				Label label = new Label(j, i + 1, dftm.getValueAt(i, j)
						.toString());
				// calculate all available artikuls
				if (j == 1) {
					int q = 0;
					try {
						q = Integer.parseInt(label.getContents());
					} catch (Exception ex) {
						q = 0;
					}
					allQuantity += q;
				}
				label.setCellFormat(cellFormat);

				wsheet.addCell(label);
				CellView cellView2 = wsheet.getColumnView(j);
				cellView2.setAutosize(true);
				wsheet.setColumnView(j, cellView); // this set auto size of cell
			}

		}

		Label broiArtikuls = new Label(1, dftm.getRowCount() + 1, "Общо : "
				+ allQuantity, headerFormat);

		wsheet.addCell(broiArtikuls);

		wworkbook.write();
		wworkbook.close();
		if (Desktop.isDesktopSupported()) {
			java.awt.Desktop.getDesktop().open(excellFileIntoDesktop);
		}
	}

	public void saveInExcelInvoice(DefaultTableModel reportInvoiceTableModel,
			String output) throws IOException, RowsExceededException,
			WriteException {
		WritableWorkbook wworkbook;
		// String userHomeFolder = System.getProperty("user.home");
		File excellFileIntoDesktop = new File(
				MainPanel.SELL_BY_PROFORM_EXCELL_PATH + "\\" + output);
		wworkbook = Workbook.createWorkbook(excellFileIntoDesktop);
		WritableSheet wsheet = wworkbook.createSheet("First Sheet", 0);

		WritableCellFormat headerFormat = getExcellCellFormat(
				WritableFont.ARIAL, 15, Colour.BLACK);

		// set header
		Label type = new Label(0, 0, "\u2116 на Документ");
		type.setCellFormat(headerFormat);
		wsheet.addCell(type);

		Label medLabelTitle = new Label(1, 0, "Плащане");
		medLabelTitle.setCellFormat(headerFormat);
		wsheet.addCell(medLabelTitle);

		Label qunatity = new Label(2, 0, "Отстъпка");
		qunatity.setCellFormat(headerFormat);
		wsheet.addCell(qunatity);

		Label price = new Label(3, 0, "Стойност");
		price.setCellFormat(headerFormat);
		wsheet.addCell(price);

		Label value = new Label(4, 0, "Клиент");
		value.setCellFormat(headerFormat);
		wsheet.addCell(value);

		Label proform = new Label(5, 0, "Продавач");
		proform.setCellFormat(headerFormat);
		wsheet.addCell(proform);

		Label client = new Label(6, 0, "Дата");
		client.setCellFormat(headerFormat);
		wsheet.addCell(client);

		Label payment = new Label(7, 0, "Протокол");
		payment.setCellFormat(headerFormat);
		wsheet.addCell(payment);

		Label seller = new Label(8, 0, "Артикул");
		seller.setCellFormat(headerFormat);
		wsheet.addCell(seller);

		Label date = new Label(9, 0, "Мер. ед");
		date.setCellFormat(headerFormat);
		wsheet.addCell(date);

		Label protokol = new Label(10, 0, "Количество");
		protokol.setCellFormat(headerFormat);
		wsheet.addCell(protokol);

		Label discount = new Label(11, 0, "Ед. цена");
		discount.setCellFormat(headerFormat);
		wsheet.addCell(discount);

		CellView cellView = wsheet.getColumnView(0);
		cellView.setAutosize(true);
		wsheet.setColumnView(0, cellView);

		Colour currColor = Colour.BLUE;

		// int j = 1;
		WritableCellFormat cellFormat = getExcellCellFormat(WritableFont.ARIAL,
				12, currColor);
		long allQuantity = 0;
		for (int row = 0; row < reportInvoiceTableModel.getRowCount(); row++) {

			String proformNumber = reportInvoiceTableModel.getValueAt(row, 0)
					.toString();
			Label proformLabel = new Label(0, row + 1, proformNumber);

			Label paymentLabel = new Label(1, row + 1, reportInvoiceTableModel
					.getValueAt(row, 1).toString());

			Label discountLabel = new Label(2, row + 1, reportInvoiceTableModel
					.getValueAt(row, 2).toString());

			Label valueLabel = new Label(3, row + 1, reportInvoiceTableModel
					.getValueAt(row, 3).toString());

			Label clientLabel = new Label(4, row + 1, reportInvoiceTableModel
					.getValueAt(row, 4).toString());

			Label sellerLabel = new Label(5, row + 1, reportInvoiceTableModel
					.getValueAt(row, 5).toString());

			Label dateLabel = new Label(6, row + 1, reportInvoiceTableModel
					.getValueAt(row, 6).toString());

			String protokolNumber = reportInvoiceTableModel.getValueAt(row, 7) != null ? reportInvoiceTableModel
					.getValueAt(row, 7).toString() : "";
			Label protokolLabel = new Label(7, row + 1, protokolNumber);

			Label typeLabel = new Label(8, row + 1, reportInvoiceTableModel
					.getValueAt(row, 9).toString());

			Label medLabel = new Label(9, row + 1, reportInvoiceTableModel
					.getValueAt(row, 10).toString());

			Label quantityLabel = new Label(10, row + 1,
					reportInvoiceTableModel.getValueAt(row, 11).toString());

			Label priceLabel = new Label(11, row + 1, reportInvoiceTableModel
					.getValueAt(row, 12).toString());

			if (row > 0) {
				if (!proformNumber.equals(reportInvoiceTableModel.getValueAt(
						row - 1, 0).toString())) {
					currColor = (currColor == Colour.BLUE) ? Colour.GREEN
							: Colour.BLUE;
					cellFormat = getExcellCellFormat(WritableFont.ARIAL, 12,
							currColor);
				} else {
					proformLabel.setString("");
					paymentLabel.setString("");
					discountLabel.setString("");
					valueLabel.setString("");
					clientLabel.setString("");
					sellerLabel.setString("");
					dateLabel.setString("");
					protokolLabel.setString("");
				}
			}
			proformLabel.setCellFormat(cellFormat);
			wsheet.addCell(proformLabel);

			paymentLabel.setCellFormat(cellFormat);
			wsheet.addCell(paymentLabel);

			discountLabel.setCellFormat(cellFormat);
			wsheet.addCell(discountLabel);

			valueLabel.setCellFormat(cellFormat);
			wsheet.addCell(valueLabel);

			clientLabel.setCellFormat(cellFormat);
			wsheet.addCell(clientLabel);

			sellerLabel.setCellFormat(cellFormat);
			wsheet.addCell(sellerLabel);

			dateLabel.setCellFormat(cellFormat);
			wsheet.addCell(dateLabel);

			protokolLabel.setCellFormat(cellFormat);
			wsheet.addCell(protokolLabel);

			typeLabel.setCellFormat(cellFormat);
			wsheet.addCell(typeLabel);

			medLabel.setCellFormat(cellFormat);
			wsheet.addCell(medLabel);

			// calc all quantity
			int q = Integer.parseInt(quantityLabel.getContents());
			allQuantity += q;

			quantityLabel.setCellFormat(cellFormat);
			wsheet.addCell(quantityLabel);

			priceLabel.setCellFormat(cellFormat);
			wsheet.addCell(priceLabel);

			//
			CellView cellView2 = wsheet.getColumnView(row);
			cellView2.setAutosize(true);
			wsheet.setColumnView(row, cellView2); // this set auto size of cell

		}

		Label finalQunatityLabel = new Label(10,
				reportInvoiceTableModel.getRowCount() + 1, "Общо Артикули: "
						+ allQuantity);
		finalQunatityLabel.setCellFormat(cellFormat);
		wsheet.addCell(finalQunatityLabel);

		wworkbook.write();
		wworkbook.close();

		if (Desktop.isDesktopSupported()) {
			java.awt.Desktop.getDesktop().open(excellFileIntoDesktop);
		}
	}

	public void saveInExcelAcquittance(DefaultTableModel dftm, String output)
			throws IOException, RowsExceededException, WriteException {

		WritableWorkbook wworkbook;
		// String userHomeFolder = System.getProperty("user.home");
		File excellFileIntoDesktop = new File(MainPanel.ACQUITTANCE_EXCELL_PATH
				+ "\\" + output);
		wworkbook = Workbook.createWorkbook(excellFileIntoDesktop);
		WritableSheet wsheet = wworkbook.createSheet("First Sheet", 0);

		WritableCellFormat headerFormat = getExcellCellFormat(
				WritableFont.ARIAL, 15, Colour.BLACK);

		// set header
		Label acquittanceHeaderLabel = new Label(0, 0, "\u2116 на Документ");
		acquittanceHeaderLabel.setCellFormat(headerFormat);
		wsheet.addCell(acquittanceHeaderLabel);

		Label valueHeaderLabel = new Label(1, 0, "Стойност");
		valueHeaderLabel.setCellFormat(headerFormat);
		wsheet.addCell(valueHeaderLabel);

		Label operatorHeaderLabel = new Label(2, 0, "Оператор");
		operatorHeaderLabel.setCellFormat(headerFormat);
		wsheet.addCell(operatorHeaderLabel);

		Label dateHeaderLabel = new Label(3, 0, "Дата");
		dateHeaderLabel.setCellFormat(headerFormat);
		wsheet.addCell(dateHeaderLabel);

		Label artikulHeaderLabel = new Label(4, 0, "Артикул");
		artikulHeaderLabel.setCellFormat(headerFormat);
		wsheet.addCell(artikulHeaderLabel);

		Label medHeaderLabel = new Label(5, 0, "Мер. ед");
		medHeaderLabel.setCellFormat(headerFormat);
		wsheet.addCell(medHeaderLabel);

		Label quantityHeaderLabel = new Label(6, 0, "Количество");
		quantityHeaderLabel.setCellFormat(headerFormat);
		wsheet.addCell(quantityHeaderLabel);

		Label priceHeaderLabel = new Label(7, 0, "Ед. цена");
		priceHeaderLabel.setCellFormat(headerFormat);
		wsheet.addCell(priceHeaderLabel);

		Label sumaHeaderLabel = new Label(8, 0, "Сума");
		sumaHeaderLabel.setCellFormat(headerFormat);
		wsheet.addCell(sumaHeaderLabel);

		Label clientHeaderLabel = new Label(9, 0, "Клиент");
		clientHeaderLabel.setCellFormat(headerFormat);
		wsheet.addCell(clientHeaderLabel);

		CellView cellView = wsheet.getColumnView(0);
		cellView.setAutosize(true);
		wsheet.setColumnView(0, cellView);

		Colour currColor = Colour.BLUE;
		// boolean boolColor = true;

		long allQuantity = 0;
		WritableCellFormat cellFormat = getExcellCellFormat(WritableFont.ARIAL,
				12, currColor);
		for (int j = 0; j < dftm.getRowCount(); j++) {

			String acquittanceNumber = dftm.getValueAt(j, 0).toString();
			Label acquittanceLabel = new Label(0, j + 1, acquittanceNumber);
			Label valueLabel = new Label(1, j + 1, dftm.getValueAt(j, 1)
					.toString());
			Label operatorLabel = new Label(2, j + 1, dftm.getValueAt(j, 3)
					.toString());
			Label dateLabel = new Label(3, j + 1, dftm.getValueAt(j, 4)
					.toString());
			Label artikulLabel = new Label(4, j + 1, dftm.getValueAt(j, 6)
					.toString());
			Label medLabel = new Label(5, j + 1, dftm.getValueAt(j, 7)
					.toString());
			Label quantityLabel = new Label(6, j + 1, dftm.getValueAt(j, 8)
					.toString());
			int q = Integer.parseInt(quantityLabel.getContents());
			allQuantity += q;
			Label priceLabel = new Label(7, j + 1, dftm.getValueAt(j, 9)
					.toString());
			Label sumaLabel = new Label(8, j + 1, dftm.getValueAt(j, 10)
					.toString());
			Label clientLabel = new Label(9, j + 1, dftm.getValueAt(j, 11)
					.toString());
			if (j > 0) {
				if (!acquittanceNumber.equals(dftm.getValueAt(j - 1, 0)
						.toString())) {
					currColor = (currColor == Colour.BLUE) ? Colour.GREEN
							: Colour.BLUE;
					cellFormat = getExcellCellFormat(WritableFont.ARIAL, 12,
							currColor);
				} else {
					acquittanceLabel.setString("");
					operatorLabel.setString("");
					dateLabel.setString("");
					clientLabel.setString("");
					valueLabel.setString("");
				}
			}
			acquittanceLabel.setCellFormat(cellFormat);
			wsheet.addCell(acquittanceLabel);

			valueLabel.setCellFormat(cellFormat);
			wsheet.addCell(valueLabel);

			operatorLabel.setCellFormat(cellFormat);
			wsheet.addCell(operatorLabel);

			dateLabel.setCellFormat(cellFormat);
			wsheet.addCell(dateLabel);

			artikulLabel.setCellFormat(cellFormat);
			wsheet.addCell(artikulLabel);

			medLabel.setCellFormat(cellFormat);
			wsheet.addCell(medLabel);

			quantityLabel.setCellFormat(cellFormat);
			wsheet.addCell(quantityLabel);

			priceLabel.setCellFormat(cellFormat);
			wsheet.addCell(priceLabel);

			sumaLabel.setCellFormat(cellFormat);
			wsheet.addCell(sumaLabel);

			clientLabel.setCellFormat(cellFormat);
			wsheet.addCell(clientLabel);

			CellView cellView2 = wsheet.getColumnView(j);
			cellView2.setAutosize(true);
			wsheet.setColumnView(j, cellView2); // this set auto size of cell

		}
		Label finalQunatityLabel = new Label(6, dftm.getRowCount() + 1,
				"Общо Артикули: " + allQuantity);
		finalQunatityLabel.setCellFormat(cellFormat);
		wsheet.addCell(finalQunatityLabel);

		wworkbook.write();
		wworkbook.close();

		if (Desktop.isDesktopSupported()) {
			java.awt.Desktop.getDesktop().open(excellFileIntoDesktop);
		}
	}

	public void saveInExcelSO_PR_BR(JTable table, DefaultTableModel dftm,
			String output) throws IOException, RowsExceededException,
			WriteException {

		int countBC = 0;
		int countABC = 0;
		int countWater = 0;
		int countWaterFame = 0;
		int countCO2 = 0;

		int countBCNew = 0;
		int countABCNew = 0;
		int countWaterNew = 0;
		int countWaterFameNew = 0;
		int countCO2New = 0;

		WritableWorkbook wworkbook;
		// String userHomeFolder = System.getProperty("user.home");
		File excellFileIntoDesktop = new File(
				MainPanel.SERVICE_ORDER_AND_PROTOKOL_EXCELL_PATH + "\\"
						+ output);
		wworkbook = Workbook.createWorkbook(excellFileIntoDesktop);
		WritableSheet wsheet = wworkbook.createSheet("First Sheet", 0);

		WritableCellFormat headerFormat = getExcellCellFormat(
				WritableFont.ARIAL, 15, Colour.BLACK);

		JTableHeader th = table.getTableHeader();
		TableColumnModel tcm = th.getColumnModel();
		for (int x = 0, y = tcm.getColumnCount(); x < y; x++) {
			Label label = new Label(x, 0, tcm.getColumn(x).getHeaderValue()
					.toString());
			label.setCellFormat(headerFormat);
			wsheet.addCell(label);
		}

		Colour currColor = Colour.BLUE;
		boolean boolColor = true;
		int i = 0;
		for (; i < dftm.getRowCount(); i++) {
			if (i > 0) {
				if (!dftm.getValueAt(i - 1, 0).equals(dftm.getValueAt(i, 0))) {
					boolColor = !boolColor;
				}
				currColor = (boolColor) ? Colour.BLUE
						: Colour.GREEN;
			}
			WritableCellFormat cellFormat = getExcellCellFormat(
					WritableFont.ARIAL, 12, currColor);

			String content = dftm.getValueAt(i, 1).toString();
			if (!content.contains("( Нов )")) {
				switch (dftm.getValueAt(i, 1).toString()) {
				case MainPanel.type_Prah_BC:
					countBC++;
					break;
				case MainPanel.type_Prah_ABC:
					countABC++;
					break;
				case MainPanel.type_Water:
					countWater++;
					break;
				case MainPanel.type_Water_Fame:
					countWaterFame++;
					break;
				case MainPanel.type_CO2:
					countCO2++;
					break;
				default:
					break;
				}
			} else {
				// compare from different ASCII characters
				if ((content.contains("Прахов ВС") || content
						.contains("Прахов BC"))) {
					// System.out.println(content + " " +
					// (!content.contains("ABC")) + " " +(
					// !content.contains("АВС") ) );
					countBCNew++;
				} else if (content.contains("Прахов АВС")
						|| content.contains("Прахов ABC")) { // compare from
																// different
																// ASCII
																// characters
					countABCNew++;
				} else if (content.toLowerCase().contains("воден")) {
					countWaterNew++;
				} else if (content.toLowerCase().contains("водопенен")) {
					countWaterFameNew++;
				} else if (content.contains("CO2") || content.contains("СО2")) {
					countCO2New++;
				}
			}
			for (int j = 0; j < dftm.getColumnCount(); j++) {
				String str = dftm.getValueAt(i, j).toString();
				if (j == 2) {
					if (str.contains("литра")) {
						str = str.replace("литра", "л");
					}
				}
				Label label = new Label(j, i + 1, str);
				label.setCellFormat(cellFormat);

				wsheet.addCell(label);
				CellView cellView = wsheet.getColumnView(j);
				cellView.setAutosize(true);
				wsheet.setColumnView(j, cellView); // this set auto size of cell
			}
		}
		int col = 1;// column in excell table
		int row = 0; // number of row in excell table

		Label label = new Label(col, i + 1, "ОБСЛУЖЕНИ : ");
		label.setCellFormat(headerFormat);
		wsheet.addCell(label);
		CellView cellView = wsheet.getColumnView(col);
		cellView.setAutosize(true);
		wsheet.setColumnView(col, cellView); // this set auto size of cell

		String[] COLUMNS = { MainPanel.type_Prah_BC, MainPanel.type_Prah_ABC,
				MainPanel.type_Water, MainPanel.type_Water_Fame,
				MainPanel.type_CO2 };
		int[] COUNT = { countBC, countABC, countWater, countWaterFame, countCO2 };
		for (; row < COLUMNS.length; row++) {
			label = new Label(col, row + i + 2, COLUMNS[row] + " => "
					+ COUNT[row]);
			label.setCellFormat(headerFormat);
			wsheet.addCell(label);
			cellView = wsheet.getColumnView(col);
			cellView.setAutosize(true);
			wsheet.setColumnView(col, cellView); // this set auto size of cell
		}

		Label label7 = new Label(col, row + i + 2, "ОБЩО : "
				+ (countBC + countABC + countWater + countWaterFame + countCO2));
		label7.setCellFormat(headerFormat);
		wsheet.addCell(label7);
		CellView cellView7 = wsheet.getColumnView(col);
		cellView7.setAutosize(true);
		wsheet.setColumnView(col, cellView7); // this set auto size of cell

		i = row + i + 2;
		row = 0;

		label = new Label(col, i + 1, "ПРОДАДЕНИ : ");
		label.setCellFormat(headerFormat);
		wsheet.addCell(label);
		cellView = wsheet.getColumnView(col);
		cellView.setAutosize(true);
		wsheet.setColumnView(col, cellView); // this set auto size of cell

		int[] COUNT2 = { countBCNew, countABCNew, countWaterNew,
				countWaterFameNew, countCO2New };
		for (; row < COLUMNS.length; row++) {
			label = new Label(col, row + i + 2, COLUMNS[row] + " => "
					+ COUNT2[row]);
			label.setCellFormat(headerFormat);
			wsheet.addCell(label);
			cellView = wsheet.getColumnView(col);
			cellView.setAutosize(true);
			wsheet.setColumnView(col, cellView); // this set auto size of cell
		}

		label7 = new Label(col, row + i + 2,
				"ОБЩО : "
						+ (countBCNew + countABCNew + countWaterNew
								+ countWaterFameNew + countCO2New));
		label7.setCellFormat(headerFormat);
		wsheet.addCell(label7);
		cellView7 = wsheet.getColumnView(col);
		cellView7.setAutosize(true);
		wsheet.setColumnView(col, cellView7); // this set auto size of cell
		wworkbook.write();
		wworkbook.close();

		if (Desktop.isDesktopSupported()) {
			java.awt.Desktop.getDesktop().open(excellFileIntoDesktop);
		}
	}

	public static Object[][] readFromFile(String fileName) {
		Object[][] obj = null;
		try {

			File f = new File(fileName);
			Workbook workBook = Workbook.getWorkbook(f.getAbsoluteFile());
			Sheet sheet = workBook.getSheet(0);
			obj = new Object[sheet.getRows()][sheet.getColumns()];
			for (int column = 0; column < sheet.getColumns(); column++) {

				for (int row = 0; row < sheet.getRows(); row++) {
					Cell cell = sheet.getCell(column, row);
					obj[row][column] = cell.getContents();
				}

			}
		} catch (BiffException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}

	private void writeInFile(ArrayList<Object[]> data, String output)
			throws IOException, RowsExceededException, WriteException {
		WritableWorkbook wworkbook;
		String userHomeFolder = System.getProperty("user.home");
		File excellFileIntoDesktop = new File(userHomeFolder + "/Desktop/"
				+ output);
		wworkbook = Workbook.createWorkbook(excellFileIntoDesktop);
		WritableSheet wsheet = wworkbook.createSheet("First Sheet", 0);

		// PAINT CELLS
		WritableCellFormat headerFormat = getExcellCellFormat(
				WritableFont.ARIAL, 15, Colour.BLACK);

		String[] headers = { "Резервна Част", "Вид на Пожарогасителя",
				"Вместимост", "Категория", "Цена" };
		for (int x = 0; x < headers.length; x++) {
			Label label = new Label(x, 0, headers[x]);
			label.setCellFormat(headerFormat);
			wsheet.addCell(label);
		}

		Colour currColor = Colour.BLACK;
		WritableCellFormat cellFormat = getExcellCellFormat(WritableFont.ARIAL,
				12, currColor);

		for (int i = 0; i < data.size(); i++) {
			// painting...
			/*
			 * if(i % 2 == 0) { currColor = Colour.GREEN; } else { currColor =
			 * Colour.BLUE; }
			 * 
			 * WritableCellFormat cellFormat =
			 * getExcellCellFormat(WritableFont.ARIAL,12,currColor);
			 */
			// end of painting

			Object[] obj = data.get(i);

			for (int j = 0; j < obj.length; j++) {
				Label label = new Label(j, i + 1, obj[j].toString());

				label.setCellFormat(cellFormat); // set color

				// set auto size of cells
				CellView cellView = wsheet.getColumnView(j);
				cellView.setAutosize(true);
				wsheet.setColumnView(j, cellView);
				// end of set auto size

				wsheet.addCell(label);
			}

		}
		/*
		 * Number number = new Number(3, 4, 3.1459); wsheet.addCell( number);
		 */
		wworkbook.write();
		wworkbook.close();
		// System.out.println(excellFileIntoDesktop);
		if (Desktop.isDesktopSupported()) {
			java.awt.Desktop.getDesktop().open(excellFileIntoDesktop);
		}
	}

	private ArrayList<Object[]> copyData(String dbTable) {
		Connection connect = null;
		Statement stat = null;
		String query = "select * from " + dbTable;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		ArrayList<Object[]> allData = new ArrayList<Object[]>();
		ArrayList<Object> data = new ArrayList<Object>();
		try {
			connect = DriverManager.getConnection(GetCurrentIP.DB_PATH);
			stat = connect.createStatement();
			rs = stat.executeQuery(query);
			while (rs.next()) {
				rsmd = rs.getMetaData();
				data.clear();
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					data.add(rs.getString(i + 1));
				}
				allData.add(data.toArray());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allData;
	}

	private WritableCellFormat getExcellCellFormat(FontName fontName,
			int fontSize, Colour color) {

		WritableCellFormat sheet = new WritableCellFormat();// (wfontStatus);
		sheet.setFont(new WritableFont(fontName, fontSize,
				WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, color));

		return sheet;
	}

	private void setColumnWidths(int column, CellView cellView2) {
		switch (column) {
		case 0:
			cellView2.setSize(65 * 256);
			break;
		case 1:
			case 4:
			case 7:
			case 10:
				cellView2.setSize(10 * 256);
			break;
		case 2:
			case 3:
			case 11:
			case 12:
				cellView2.setSize(20 * 256);
			break;
			case 5:
			case 6:
			case 9:
			case 8:
				cellView2.setSize(25 * 256);
			break;
			default:
			break;
		}
	}

}
