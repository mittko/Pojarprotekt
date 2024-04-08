package pdf;

import java.io.File;

public class PDFRemover {

	public static boolean remove(String target) {
		File file = new File(target);
		if(file.exists()) {
		file.delete();
		System.out.println("pdf is deleted succesfully!");
		return true;
		}
		System.out.println("pdf is NOT deleted!");
		return false;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
         System.out.println(remove("C:/Program1/PDF/2815/Protokol2815.pdf"));
	}

}
