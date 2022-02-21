package Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import mydate.MyGetDate;

public class OtherErr {

	private static final String workOutputTarget = "C:/Program1/Errors/";
	public static void otherErros(String output) {
		File f = new File(workOutputTarget+"Other_Error.log");
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
			//	e.printStackTrace();
			}
		}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
