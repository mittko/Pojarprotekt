package log;

import mydate.MyGetDate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class IOErrorsWriter extends LogErrors{

	public static void writeIO(String output) {
		writeErros(output,"\\Logs\\db_errors.txt");
	}
}
