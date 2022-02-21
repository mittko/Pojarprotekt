package PDF.ReadPDF;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class PDFRead {

	public PDFRead() {
		// TODO Auto-generated constructor stub
	}

	private String extractText(String path) {
		 PdfReader reader;

	        try {

	            reader = new PdfReader(path);

	            // pageNumber = 1
	            String textFromPage = PdfTextExtractor.getTextFromPage(reader, 1);

	        //    System.out.println(textFromPage);

	            reader.close();
                return textFromPage;
	        } catch (IOException e) {
	      //      e.printStackTrace();
	            return null;
	        }
	      
	}
	
	private void traverseFiles(File file, String client) {
		if(file != null) {
			File[] files = file.listFiles();
			if(files != null && files.length > 0) {
			     for(int i = 0;i < files.length;i++) {
			    	 traverseFiles(files[i],client);
			     }
			}
			if(!file.isDirectory() && file.isFile()) {
				String name = file.getName();
				if(name.endsWith(".pdf")) {
					String text =  extractText(file.getAbsolutePath());
					if(text != null && text.contains(client)) {
					System.out.println(file.getAbsolutePath());
					}
				}
			}
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
     PDFRead reader = new PDFRead();
     Scanner scaner = new Scanner(System.in);
     System.out.println("Enter directory path : ");
     String path = scaner.nextLine();
     File file = new File(path);
     String client  = scaner.nextLine();
     reader.traverseFiles(file,client);
     scaner.close();
   //  reader.extractText();
	}

}
