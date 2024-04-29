package log;

import mydate.MyGetDate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PdfErr extends LogErrors{

	public static void pdfErros(String output) {
		writeErros(output,"\\Logs\\pdf_errors.txt");
	}
}
