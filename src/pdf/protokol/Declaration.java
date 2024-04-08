package pdf.protokol;


import com.itextpdf.text.*;
import mydate.MyGetDate;
import pdf.PdfCreator;
import utils.MainPanel;

import java.io.*;
import java.net.URL;

public class Declaration extends PdfCreator {

    private final ClassLoader classLoader = getClass().getClassLoader();
    private final String target = MainPanel.DECLARATIONS_PDF_PATH;

    private float X = 400;
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
            while( (line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                setText(line, 0, 0, arial,10);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }



    public boolean init(String timeStamp, String num) {
        return super.init(this.target + "\\Декларации-",timeStamp, num);
    }


    public static void main(String[] args) {
        new Declaration().createDeclaration(MyGetDate.getTimeStamp(),"0000000");
    }
}
