package generators;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import Exceptions.InOutException;
import Log.IOErrorsWriter;


public class GenerateSO extends BaseGenerator {
	private final int MAXL = 10;
//	private int[] next_variation(int[] arr, int k) {
//		int i = k - 1;
//
//		while (i >= 0) {
//
//			if (arr[i] < 9) {
//				arr[i] = arr[i] + 1;
//				return arr;
//			}
//			arr[i] = 0;
//			i--;
//		}
//		return null;
//	}

	public int[] updateSO_Number(int[] toUpdate) {
			return next_variation(toUpdate, MAXL);
		}

	public String digitsToString(int[] digits) {
//		StringBuilder sb = new StringBuilder();
//		for (int i : digits) {
//			sb.append(i);
//		}
		return  digToString(digits);//b.toString();
	}
    public int[] stringToArray(String number) {
    	int[] toUpdate = new int[MAXL];
		for (int i = 0; i < toUpdate.length; i++) {
			toUpdate[i] = (int) (number.charAt(i) - 48);
		}
		return toUpdate;
    }
/*	private void print(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}
*/
   private void createFile() throws IOException {
	   File f = new File("service order");
	   f.createNewFile();
   }
   public static String nextSO() {
	   FileReader fileReader = null; 
	   BufferedReader buff = null;
	   String line = null;
	   try {
		fileReader =  new FileReader("./service order");
		buff = new BufferedReader(fileReader);
		line = buff.readLine();
	
//		while( (line = buff.readLine()) != null){
//			break;
//		}
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		IOErrorsWriter.writeIO(e.toString());
		InOutException.showIOException(e);
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		IOErrorsWriter.writeIO(e.toString());
		InOutException.showIOException(e);
		e.printStackTrace();
	} finally {
		try {
			if(fileReader != null) {
			fileReader.close();
			}
			if(buff != null) {
				buff.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			IOErrorsWriter.writeIO(e.toString());
			InOutException.showIOException(e);
			e.printStackTrace();
		}
	}
	   return line;
   }
   public static int updateServiceOrder(String num) {
	
	   FileWriter fos = null;
	   BufferedWriter buff = null;
	try {
		fos = new FileWriter("./service order");
		buff  = new BufferedWriter(fos);
		buff.write(num);
		buff.flush();
		return 1;
	} catch (IOException e) {
		// TODO Auto-generated catch block
		IOErrorsWriter.writeIO(e.toString());
		InOutException.showIOException(e);
		e.printStackTrace();
		return 0;
	} finally {
		try {
			if(fos != null) {
			fos.close();
			}
			if(buff != null) {
				buff.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			IOErrorsWriter.writeIO(e.toString());
			InOutException.showIOException(e);
			e.printStackTrace();
			return 0;
		}
	}
	 
   }
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		GenerateSO sot = new GenerateSO();

		long start = System.currentTimeMillis();
        String line = sot.nextSO();
        if(line == null) {
        	System.err.println("NULL POINTER!\n");
        	return;
        }
        for(int i = 0;i < 1;i++) {
        int[] nums = sot.stringToArray(line);
        int[] updateNum = sot.updateSO_Number(nums);
        line = sot.digitsToString(updateNum);
        System.out.println(line);
        updateServiceOrder(line);
        }
	    long end = System.currentTimeMillis();
	    System.out.println((end - start) / 1000.0);
	}

}
