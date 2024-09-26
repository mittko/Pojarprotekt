package pdf.protokol;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import exceptions.PDFException;
import log.PdfErr;
import models.Firm;
import mydate.MyGetDate;
import utils.MainPanel;

import javax.swing.table.DefaultTableModel;
import java.util.Map;
import java.util.TreeMap;

public class ProtokolPDF extends ProtokolPDFBase {


    @Override
    public boolean setDynamicTable(float x, DefaultTableModel dm,
                                   TreeMap<Object, Integer> PARTS, Firm firm,
                                   int startIndex, int endIndex) {

        PdfPTable dynamicTable = new PdfPTable(11);

        PdfPCell n = new PdfPCell(new Phrase("\u2116\nпо\nред", arial9));
        n.setNoWrap(true);
        PdfPCell mark = new PdfPCell(new Phrase("Идентификационна маркировкa" +
                "\nна всеки пожарогасител (марка,\n" +
                " модел, сериен номер и др)", arial8));
        mark.setNoWrap(true);
        PdfPCell category = new PdfPCell(new Phrase(
                "Катего\nрия\nна пож.\nсъглас\nно т.\n4.3,4.4\nи 5 от\nСД"
                        + " ISO\n/TS 116\n02-2:\n2015.", arial7));
        category.setNoWrap(true);
        PdfPCell wheight = new PdfPCell(new Phrase(
                "Маса \nна\nзаре-\nдения\nпожа\nрога-\nсител,\nkg.",
                arial9));
        wheight.setNoWrap(true);
        PdfPCell antifire = new PdfPCell(
                new Phrase(
                        "Вид на\nпожарога\nсително\n" +
                                "то веще\nство (во-\nда прах,\nCO2\nили\nдр.)",
                        arial7));
        antifire.setNoWrap(true);
        PdfPCell number = new PdfPCell(
                new Phrase(
                        "Търговско\nнаименова-\nние на "
                                + "по-\nжарогаси-\nтелното\n" +
                                "вещество\n(при преза\n" +
                                "реждане с\nпрах или\n" +
                                "пенообра\nзувател)",
                        arial7));
        number.setNoWrap(true);
        PdfPCell serving = new PdfPCell(new Phrase("Вид на извършеното обслу-" +
                "\nжване (техническо обслужва-" +
                "\nне ,презареждане, или"
                + "\nхидростатично изпитване на\n"
                + "устойчивост на налягане", arial7));
        serving.setNoWrap(true);
        PdfPCell date = new PdfPCell(new Phrase(
                "Дата на\nизвършено-\nто обслуж-\nване", arial8));
        date.setNoWrap(true);
        PdfPCell name = new PdfPCell(new Phrase(
                "Име на\nлицето,\nизвърши-\nло обслу-\nжването", arial7));
        name.setNoWrap(true);
        PdfPCell signature = new PdfPCell(new Phrase(
                "Подпис\nна\nлицето,\nизвършилo\nобслужва-\nнето",
                arial8));
        signature.setNoWrap(true);
        PdfPCell stikernumber = new PdfPCell(new Phrase(
                "Номер\n    на\nстикер", arial9));
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
            PDFException.showErrorMessage(e);
            PdfErr.pdfErros(e.toString());
            e.printStackTrace();
            return false;
        }

        int sumOfRows = 0;
        int fromRows = 0;
        int toRows = 2;
        int numer = 0;

        sumOfRows += dynamicTable.getRowHeight(0);

        // инициализиране на колоните
        String[] romanNumbers = {"I","1","2","3","4","5","6","7","8","9","10"};
        for (int nomer = 0; nomer <= 10; nomer++) {
            Phrase p1 = new Phrase(romanNumbers[nomer] + "", arial10);
            PdfPCell cell = new PdfPCell(p1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            dynamicTable.addCell(cell);
        }
        sumOfRows += dynamicTable.getRowHeight(1);

       //  for(int cycle = 0;cycle < 10;cycle++) {


        // изпитване
        for (int row = 0; row < endIndex; row++) {
            String _tehnichesko = dm.getValueAt(row + startIndex, 7)
                    + ""; // техническо обслужване
            String _prezarejdane = dm.getValueAt(row + startIndex, 8) + ""; // презареждане
            String _hidrostatichno = dm
                    .getValueAt(row + startIndex, 9) + ""; // хидростатично
            String[] doing = {_tehnichesko,_prezarejdane,_hidrostatichno};
            ++numer;
            for(int j = 0;j < 3;j++) {
                if(doing[j].equals("не")) {
                    continue;
                }
                toRows++;

                PdfPCell cell1 = new PdfPCell(new Phrase((numer + "." + (j + 1)), arial7));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);

                dynamicTable.addCell(cell1); // column 1

                PdfPCell cell2 = new PdfPCell(new Phrase(dm.getValueAt(row
                        + startIndex, 6)
                        + " , " + MainPanel.numeroSign
                        + " - " + dm.getValueAt(row + startIndex, 4), arial9));

                dynamicTable.addCell(cell2); // column 2 марка + монтажен номер

                PdfPCell cell3 = new PdfPCell(new Phrase(dm.getValueAt(row
                        + startIndex, 5)
                        + "", arial9));
                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);

                dynamicTable.addCell(cell3); // column 3 категория

                String wheightStr = dm.getValueAt(row + startIndex, 2).toString();
                String[] spl = wheightStr.split("/");
                String _wheight = spl[0].trim();
                if (_wheight.contains("литра")) {
                    _wheight = _wheight.replace("литра", "л");
                }
                PdfPCell cell4 = new PdfPCell(new Phrase(_wheight, arial9));
                cell4.setHorizontalAlignment(Element.ALIGN_CENTER);

                dynamicTable.addCell(cell4); // column 4 маса

                // column 0 пож. в-во
                String type = dm.getValueAt(row + startIndex, 1).toString();

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

                switch (type) {
                    case MainPanel.type_Prah_ABC:
                        PdfPCell cell5 = new PdfPCell(new Phrase("прах АВС", arial7));
                        cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                        dynamicTable.addCell(cell5);
                        break;
                    case MainPanel.type_Prah_BC:
                        PdfPCell cell6 = new PdfPCell(new Phrase("прах ВС", arial7));
                        cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
                        dynamicTable.addCell(cell6);
                        break;
                    case MainPanel.type_Water:
                        PdfPCell cell7 = new PdfPCell(new Phrase("воден", arial7));
                        cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
                        dynamicTable.addCell(cell7);
                        break;
                    case MainPanel.type_Water_Fame:
                        PdfPCell cell8 = new PdfPCell(new Phrase("водопенен", arial7));
                        cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
                        dynamicTable.addCell(cell8);
                        break;
                    case MainPanel.type_CO2:
                        PdfPCell cell9 = new PdfPCell(new Phrase("CO2", arial7));
                        cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
                        dynamicTable.addCell(cell9);
                        break;
                    default:
                        break;
                }

                // column 6 номер на разрешителното за гасителна ефективност

                String nomerRazreshitelno = ""; // номер на разрешителното
                String Obslujvane = "";
                if(!doing[j].equals("не")) {
                    if(j == 0) {
                        Obslujvane = "Техническо обслужвне";
                    } else if(j == 1) {
                        Obslujvane = "Презареждане";
                    } else {
                        String differenceDayHI = MyGetDate.getUrgentDays(
                                MyGetDate.getReversedSystemDate(),
                                doing[j]);
                        int ostawashtiDni = Integer.parseInt(differenceDayHI);
                        if (ostawashtiDni > 1460 && ostawashtiDni <= 1825) { // 4*365 &&
                            // 5*365
                            Obslujvane = "Хидростатично изпитване на устойчивост на налягане";
                        } else if (ostawashtiDni > 1095 && ostawashtiDni <= 1460) { // 3*365
                            // &&
                            // 4*365
                            Obslujvane += "Хидростатично изпитване на устойчивост на налягане (1)";
                        } else if (ostawashtiDni > 730 && ostawashtiDni <= 1095) { // 2*365
                            // &&
                            // 3*365
                            Obslujvane += "Хидростатично изпитване на устойчивост на налягане (2) ";
                        } else if (ostawashtiDni > 365 && ostawashtiDni <= 730) { // 365
                            // &&
                            // 2*365
                            Obslujvane += "Хидростатично изпитване на устойчивост на налягане (3)";
                        } else if (ostawashtiDni <= 365) {
                            Obslujvane += "Хидростатично изпитване на устойчивост на налягане (4)";
                        }
                    }
                }
                // постави номер на разрешителното

                // doing[0] = техническо doing[1] = презареждане
                switch (type) {
                    case MainPanel.type_Prah_BC:
                    case MainPanel.type_Prah_ABC:
                        if (!doing[j].equals("не")) {
                            if (j == 0) {
                                nomerRazreshitelno = "ABC";
                            } else if (j == 1) {
                                nomerRazreshitelno = "Кобра ABC";
                            } else {
                                nomerRazreshitelno = "Изпитан";
                            }
                        }
                        break;
                    case MainPanel.type_Water:
                        if (!doing[j].equals("не")) {
                            if (j == 0) {
                                nomerRazreshitelno = "Вода";
                            } else if (j == 1) {
                                nomerRazreshitelno = "Вода";
                            } else {
                                nomerRazreshitelno = "Изпитан";
                            }
                        }
                        break;
                    case MainPanel.type_Water_Fame:
                        if (!doing[j].equals("не")) {
                            if (j == 0) {
                                nomerRazreshitelno = "STHAMEX";
                            } else if (j == 1) {
                                nomerRazreshitelno = "STHAMEX";
                            } else {
                                nomerRazreshitelno = "Изпитан";
                            }
                        }
                        break;
                    case MainPanel.type_CO2:
                        if (!doing[j].equals("не")) {
                            if (j == 0) {
                                nomerRazreshitelno = "CO2";
                            } else if (j == 1) {
                                nomerRazreshitelno = "CO2";
                            } else {
                                nomerRazreshitelno = "Изпитан";
                            }
                        }
                        break;
                    default:
                        nomerRazreshitelno = "";
                        break;
                }
                //
                PdfPCell cell10 = new PdfPCell(
                        new Phrase(nomerRazreshitelno, arial7));
                cell10.setHorizontalAlignment(Element.ALIGN_CENTER);
                dynamicTable.addCell(cell10);

                PdfPCell cell11 = new PdfPCell(new Phrase(Obslujvane, arial7));
                cell11.setHorizontalAlignment(Element.ALIGN_CENTER);

                dynamicTable.addCell(cell11);// column 7 обслужване

                PdfPCell cell12 = new PdfPCell(new Phrase(protokolDate, arial7));
                cell12.setHorizontalAlignment(Element.ALIGN_LEFT);

                dynamicTable.addCell(cell12); // column 8 дата

                // set name of person
                if (dm.getColumnCount() >= 13) {
                    // generira se ot spravki
                    String technikName = dm.getValueAt(row + startIndex, 13).toString();

                    switch (technikName) {
                        case "Георги Ковачки": {
                            PdfPCell cellName = new PdfPCell(new Phrase(technikName, arial7));
                            cellName.setHorizontalAlignment(Element.ALIGN_LEFT);
                            dynamicTable.addCell(cellName);

                            PdfPCell cellSign = new PdfPCell(technikImage, true);
                            dynamicTable.addCell(cellSign); // column 10 подпис на лицето
                            // извършило обслужването
                            break;
                        }
                        case "Георги Ильов": {
                            PdfPCell cellName = new PdfPCell(new Phrase(technikName, arial7));
                            cellName.setHorizontalAlignment(Element.ALIGN_LEFT);
                            dynamicTable.addCell(cellName);

                            PdfPCell cellSign = new PdfPCell(technikImage3, true);
                            dynamicTable.addCell(cellSign);
                            break;
                        }
                        case "Спас Ильов": {
                            PdfPCell cellName = new PdfPCell(new Phrase(technikName, arial7));
                            cellName.setHorizontalAlignment(Element.ALIGN_LEFT);
                            dynamicTable.addCell(cellName);

                            PdfPCell cellSign = new PdfPCell(technikImage4, true);
                            dynamicTable.addCell(cellSign);
                            break;
                        }
                        case "Васил Димитров": {
                            PdfPCell cellName = new PdfPCell(new Phrase(technikName, arial7));
                            cellName.setHorizontalAlignment(Element.ALIGN_LEFT);
                            dynamicTable.addCell(cellName); // column 9 име на лицето

                            PdfPCell cellSign = new PdfPCell(technikImage5, true);
                            dynamicTable.addCell(cellSign); // column 10 подпис на лицето
                            // извършило обслужването
                            break;
                        }
                        default:
                            PdfPCell cellName = new PdfPCell(new Phrase("Георги Ильов", arial7));
                            cellName.setHorizontalAlignment(Element.ALIGN_LEFT);
                            dynamicTable.addCell(cellName);

                            PdfPCell cellSign = new PdfPCell(technikImage3, true);
                            dynamicTable.addCell(cellSign);
                            break;
                    }

                } else {
                    //	 generira se na momenta

                    String technikName = MainPanel.personName.trim();



                    switch (technikName) {
                        case "Георги Ковачки": {
                            PdfPCell cellName = new PdfPCell(new Phrase(technikName, arial7));
                            cellName.setHorizontalAlignment(Element.ALIGN_LEFT);
                            dynamicTable.addCell(cellName); // column 9 име на лицето

                            PdfPCell cellSign = new PdfPCell(technikImage, true);
                            dynamicTable.addCell(cellSign); // column 10 подпис на лицето
                            // извършило обслужването
                            break;
                        }
                        case "Георги Ильов": {
                            PdfPCell cellName = new PdfPCell(new Phrase(technikName, arial7));
                            cellName.setHorizontalAlignment(Element.ALIGN_LEFT);
                            dynamicTable.addCell(cellName); // column 9 име на лицето

                            PdfPCell cellSign = new PdfPCell(technikImage3, true);
                            dynamicTable.addCell(cellSign);
                            break;
                        }
                        case "Спас Ильов": {
                            PdfPCell cellName = new PdfPCell(new Phrase(technikName, arial7));
                            cellName.setHorizontalAlignment(Element.ALIGN_LEFT);
                            dynamicTable.addCell(cellName); // column 9 име на лицето

                            PdfPCell cellSign = new PdfPCell(technikImage4, true);
                            dynamicTable.addCell(cellSign);
                            break;
                        }
                        case "Васил Димитров": {
                            PdfPCell cellName = new PdfPCell(new Phrase(technikName, arial7));
                            cellName.setHorizontalAlignment(Element.ALIGN_LEFT);
                            dynamicTable.addCell(cellName); // column 9 име на лицето

                            PdfPCell cellSign = new PdfPCell(technikImage5, true);
                            dynamicTable.addCell(cellSign); // column 10 подпис на лицето
                            // извършило обслужването
                            break;
                        }
                        default:
                            PdfPCell cellName = new PdfPCell(new Phrase("Георги Ильов", arial7));
                            cellName.setHorizontalAlignment(Element.ALIGN_LEFT);
                            dynamicTable.addCell(cellName); // column 9 име на лицето

                            PdfPCell cellSign = new PdfPCell(technikImage3, true);
                            dynamicTable.addCell(cellSign);
                            break;
                    }
                }

                // barcod number
                PdfPCell cell15 = new PdfPCell(new Phrase(dm.getValueAt(
                        row + startIndex, 3).toString() + (j+1), arial7));
                cell15.setHorizontalAlignment(Element.ALIGN_LEFT);

                dynamicTable.addCell(cell15); // column 11 номер на стикер

                sumOfRows += dynamicTable.getRowHeight(row + 2);

                if (row == endIndex - 1 || ((Y - 5) - sumOfRows) < document.bottom()) {

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
  //    }// end of test cycles

        try {
            dynamicTable.setWidths(new float[] {2.5f, 17f, 3.3f, 4f, 4.5f, 5f,
                    12.5f, 6f, 4f, 6f, 5f });
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            PDFException.showErrorMessage(e);
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

        setFootText(numer, x, footY - 15, firm, PARTS);
        return true;
    }

    @Override
    public float setFootText(int total, float x, float y, Firm firm, TreeMap<Object,Integer> parts) {

        y = super.setFootText(total,x,y,firm,parts);

        y -= 15;
        if(y <= document.bottom()) {
            y = document.top();
            document.newPage();
        }
        setText("- По време на извършеното обслужване на посочените пожарогасители са сменени части, консумативи и извършени",
        30,y , arial,9);
        y -= 15;
        if(y <= document.bottom()) {
            y = document.top();
            document.newPage();
        }
        setText("дейности както следва:",30,y ,arial,9);

        for(Map.Entry<Object,Integer> entry : parts.entrySet()) {
            if (y - 15 <= document.bottom()) {
                document.newPage();
                y = document.top();
            }
            setText(entry.getKey() + " - " + entry.getValue() + " бр.", 30,y -= 15,arial,9);
        }

        setText("- Заменените стари негодни части и материали са предадени на клиента.", 30, y - 15, arial,9);

        return y;
    }
}
