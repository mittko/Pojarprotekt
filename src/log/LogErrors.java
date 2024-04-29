package log;

import mydate.MyGetDate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static utils.MainPanel.DOCUMENTS_PATH;

public class LogErrors {
    static File f;
    public static void write(String output) {
        if(f == null) {
            return;
        }
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            if(!f.exists()) {
                f.createNewFile();
            }
            fw = new FileWriter(f.getAbsolutePath(),true);
            bw = new BufferedWriter(fw);
            bw.write("\n"+output + "  "+ MyGetDate.getSystemDate2());
            bw.newLine();
            //		bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            IOErrorsWriter.writeIO(e.toString());
            //	e.printStackTrace();
        } finally {
            try {
                if(bw != null) {
                    bw.close();
                }

                if(fw != null)
                    fw.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                IOErrorsWriter.writeIO(e.toString());
                //	e.printStackTrace();
            }
        }
    }
    public static void writeErros(String output, String filePath) {
        f = new File(DOCUMENTS_PATH+filePath);
        write(output);
    }




}
