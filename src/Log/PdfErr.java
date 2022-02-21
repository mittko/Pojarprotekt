package Log;

import mydate.MyGetDate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PdfErr {
	private static final String workOutputTarget = "C:/Program1/Errors/";
	public static void pdfErros(String output) {
		File f = new File(workOutputTarget+"Pdf_Error.log");
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
				if(bw != null)
				bw.close();
				
				if(fw != null)
					fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				IOErrorsWriter.writeIO(e.toString());
		//		e.printStackTrace();
			}
		}
		
	}
}
