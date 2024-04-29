package log;

import mydate.MyGetDate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static utils.MainPanel.DOCUMENTS_PATH;


public class DB_Err extends LogErrors {


	public static void writeErros(String output) {
		writeErros(output,"\\Logs\\db_errors.txt");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
