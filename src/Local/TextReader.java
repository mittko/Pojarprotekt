package Local;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

import Exceptions.InOutException;
import Log.IOErrorsWriter;

public class TextReader {

	public static Object[] getData(String file) {
		ArrayList<String> list = new ArrayList<String>();
		InputStream is = ClassLoader.getSystemResourceAsStream(file);
		BufferedReader buff = new BufferedReader(
				new InputStreamReader(is,Charset.forName("windows-1251")));
		String line = null;
		try {
			while((line = buff.readLine()) != null) {
				list.add(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			InOutException.showIOException(e);
			IOErrorsWriter.writeIO(e.toString());
		//	e.printStackTrace();
		}
		return list.toArray();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
            Object[] data = getData("Local/values.txt");
            for(Object s : data) {
            //	System.out.println(s);
            	String[] spl = ((String)s).split("=");
				for (int i = 0; i < spl.length; i++) {
					System.out.printf("%s ",spl[i]);
				}
				System.out.printf("\n");
            }
        //    System.out.println(Charset.defaultCharset());
      /*      SortedMap<String, Charset> sm = Charset.availableCharsets();
            for(Map.Entry<String, Charset> st : sm.entrySet()) {
            	System.out.print(st.getKey() + "   " + st.getValue() + "\n");
            
            }*/
	}

}
