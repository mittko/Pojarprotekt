package log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import mydate.MyGetDate;

public class OtherErr extends LogErrors {

	public static void otherErros(String output) {
		writeErros(output,"\\Logs\\other_errors.txt");
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
