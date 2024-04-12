package pdf.protokol;


import com.itextpdf.text.*;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
import mydate.MyGetDate;
import pdf.PdfCreator;
import utils.MainPanel;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class Declaration extends PdfCreator {

    private final ClassLoader classLoader = getClass().getClassLoader();
    private final String target = MainPanel.DECLARATIONS_PDF_PATH;

    private float X = 20;
    private float Y = PageSize.A4.getHeight() - 25;// = 692.0

    public Declaration() {}

    public boolean createDeclaration(String timeStamp, String documentNumber) {
        if(!init(timeStamp,documentNumber)) {
            return false;
        }

        try {
            URL url = classLoader.getResource("pdf/protokol/resources/declaration.txt");
            File file = new File(url.getPath());
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;

            StringBuilder sb = new StringBuilder();
            ArrayList<String> lines = new ArrayList<>();
            while((line = bufferedReader.readLine()) != null) {
               sb.append(String.format("%s\n",line));
               // setText(line, X, Y, arial,10);
                Y -= 20;
                lines.add(line);
            }
            ArrayList p;

            StringReader strReader = new StringReader(sb.toString());

            p = (ArrayList) HTMLWorker.parseToList(strReader, null);

            for (int k = 0; k < lines.size(); ++k){
                Paragraph paragraph=new Paragraph(lines.get(k),arial8);
                document.add(paragraph);
            }

         //   HtmlConverter.convertToPdf(HTML, new FileOutputStream("string-to-pdf.pdf"));
        } catch (IOException | DocumentException e) {
            throw new RuntimeException(e);
        }

        finish();
        return true;
    }



    public boolean init(String timeStamp, String num) {
        return super.init(this.target + "\\Декларации-",timeStamp, num);
    }


    public static void main(String[] args) {
        new Declaration().createDeclaration(MyGetDate.getTimeStamp(),"0000000");
    }
}
