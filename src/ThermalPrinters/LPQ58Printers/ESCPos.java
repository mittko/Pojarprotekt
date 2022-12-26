/**
 * ESC/POS library for communicating with thermal printers
 *
 * ##copyright##
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 * 
 * @author		##author##
 * @modified	##date##
 * @version		##version##
 */

package ThermalPrinters.LPQ58Printers;

import mydate.MyGetDate;

import javax.print.*;
import javax.print.attribute.HashAttributeSet;
import javax.print.attribute.standard.PrinterName;
import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Copyright (c) 2011 Joseph Bergen metalab(at)harvard
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * ESCPos is designed to facilitate serial communication between Processing and
 * ESC/POS thermal printers. This library is designed to make it easier to send
 * simple (and not so simple) commands to the printer more efficiently and cleanly
 * than would be otherwise possible
 * 
 * @example Hello 
 * 
 * (the tag @example followed by the name of an example included in folder 'examples' will
 * automatically include the example in the javadoc.)
 *
 */

public class ESCPos {
	
	// myParent is a reference to the parent sketch
	ByteArrayOutputStream printer;  // Create object from Serial class

	public Boolean debug = false; //debug switch
	
	public final static String VERSION = "##version##";

	public ESCPos() {
		printer = new ByteArrayOutputStream();
	}

	public ByteArrayOutputStream getPrinter() {
		return printer;
	}

	private void welcome() {
		System.out.println("\n##name## ##version## by ##author##!\n");
	}

	public void sayHello() throws IOException {
		
		printer.write(0x1B);
		printer.write('@');
		printer.write("Hello World".getBytes());
		printer.write(0x1B);
	    printer.write('d');
	    printer.write(6);

	/*	1B
		@
		Hello World
		1B
		d
		6*/
	}
	
	/**
	 * return the version of the library.
	 * 
	 * @return String
	 */
	public static String version() {
		return VERSION;
	}

	/**
	 * 
	 * reusable init esc code
	 * 
	 */
	public void escInit(){
		printer.write(0x1B);
		printer.write('@');
	}

	/**
	 * resets all printer settings to default
	 * 
	 */
	public void resetToDefault(){
		setInverse(false);
		setBold(false);
		setUnderline(0);
		setJustification(0);
	}

	public ByteArrayOutputStream printString(String str) throws IOException {
		escInit();
		printer.write(str.getBytes());
		printer.write(0xA);
		/*string
		A*/
		return printer;
	}
	
	public void storeString(String str) throws IOException {
		printer.write(str.getBytes());
	}
	
	public void storeChar(int hex){
		printer.write(hex);
	}
	
	public void printStorage(){
		printer.write(0xA);
	}
	/**
	 * Prints n lines of blank paper.
	 * */
	public ByteArrayOutputStream feed(int feed){

		//escInit();
		printer.write(0x1B);
		printer.write('d');
		printer.write(feed);
		return printer;
	}
	
	/**
	 * Prints a string and outputs n lines of blank paper.
	 * */
	
	public ByteArrayOutputStream printAndFeed(String str, int feed) throws IOException {
		ByteArrayOutputStream printer =
				new ByteArrayOutputStream();
		//escInit();
		printer.write(str.getBytes());
		//output extra paper
		printer.write(0x1B);
		printer.write('d');
		printer.write(feed);
		return printer;
	}
		
	/**
	 * Sets bold
	 * */
	public void setBold(Boolean bool){
		printer.write(0x1B);
		printer.write('E');
		printer.write((int)(bool?1:0));
	}
		
	/**
	 * Sets white on black printing
	 * */
	public void setInverse(Boolean bool){
		printer.write(0x1D);
		printer.write('B');
		printer.write( (int)(bool?1:0) );
	}
	
	/**
	 * Sets underline and weight
	 * 
	 * @param val
	 * 		0 = no underline.
	 * 		1 = single weight underline.
	 * 		2 = double weight underline.
	 * */
	
	public void setUnderline(int val){
		printer.write(0x1B);
		printer.write('-');
		printer.write(val);
	}
		 
	
	/**
	 * Sets left, center, right justification
	 * 
	 * @param val
	 * 		0 = left justify.
	 * 		1 = center justify.
	 * 		2 = right justify.
	 * */
	
	public void setJustification(int val){
		printer.write(0x1B);
		printer.write('a');
		printer.write(val);
	}

	public void printQR(String str, int errCorrect, int moduleSize) throws IOException {
		//save data function 80
		printer.write(0x1D);//init
		printer.write("(k".getBytes());//adjust height of barcode
		printer.write(str.length()+3); //pl
		printer.write(0); //ph
		printer.write(49); //cn
		printer.write(80); //fn
		printer.write(48); //
		printer.write(str.getBytes());

		  //error correction function 69
		printer.write(0x1D);
		printer.write("(k".getBytes());
		printer.write(3); //pl
		printer.write(0); //ph
		printer.write(49); //cn
		printer.write(69); //fn
		printer.write(errCorrect); //48<= n <= 51
		  
		  //size function 67
		printer.write(0x1D);
		printer.write("(k".getBytes());
		printer.write(3);
		printer.write(0);
		printer.write(49);
		printer.write(67);
		printer.write(moduleSize);//1<= n <= 16
		  
		  //print function 81
		printer.write(0x1D);
		printer.write("(k".getBytes());
		printer.write(3); //pl
		printer.write(0); //ph
		printer.write(49); //cn
		printer.write(81); //fn
		printer.write(48); //m   
	}
	
	/**
	 * Encode and print barcode
	 * 
	 * @param code
	 *          String to be encoded in the barcode. 
	 *          Different barcodes have different requirements on the length
	 *          of data that can be encoded.
	 * @param type
	 *          Specify the type of barcode
	 *          65 = UPC-A.
	 *          66 = UPC-E.
	 *          67 = JAN13(EAN).
	 *          68 = JAN8(EAN).
	 *          69 = CODE39.
	 *          70 = ITF.
	 *          71 = CODABAR.
	 *          72 = CODE93.
	 *          73 = CODE128.
	 *          
	 *  @param h
	 *  		height of the barcode in points (1 <= n <= 255)
	 *  @param w
	 *  		width of module (2 <= n <=6).
	 *  		Barcode will not print if this value is too large.
	 *  @param font
	 *  		Set font of HRI characters
	 *  		0 = font A
	 *  		1 = font B
	 *  @param pos
	 *  		set position of HRI characters
	 *  		0 = not printed.
	 *  		1 = Above barcode.
	 *  		2 = Below barcode.
	 *  		3 = Both above and below barcode.
	 */

	public static void main(String[] args) throws IOException, PrintException {

		final ESCPos escPos = new ESCPos();
		//final String[] spl = this.jCommandsTextField.getText().split(" ");
		final String barcode = "5555555555555";//spl[0];
		final int type = 67;//Integer.parseInt(spl[1]);
		final int h = 65;//Integer.parseInt(spl[2]);
		final int w = 3;//Integer.parseInt(spl[3]);
		final int font = 0;//Integer.parseInt(spl[4]);
		final int pos = 1;//Integer.parseInt(spl[5]);
	//	final int lineSpacing = 31;//Integer.parseInt(spl[6]);
	//	escPos.setLineSpacing(lineSpacing);
		escPos.escInit();
		escPos.printBarcode(barcode, type, h, w, font, pos,"КИРИЛИЦА");


		final ByteArrayOutputStream byteArrayOutputStream = escPos.getPrinter();
		escPos.printHexBytes(byteArrayOutputStream.toByteArray(),"LPQ58(ESC)");
	}
	public void printBarcode(String code, int type, int h, int w, int font, int pos, String text) throws IOException, PrintException {

		System.out.println("printing barcode...");

		//need to test for errors in length of code
		//also control for input type=0-6

		// HRI indicates Human Readable Interpretation.
        // [Default] n = 0
		//GS H = HRI position
		printer.write(0x1D);
		printer.write('H');
		printer.write(pos); //  0/48=no print, 1/49=above, 2/50=below, 3/51=above & below

		// HRI characters are printed using the font specified by GS f.
		//GS f = set barcode characters
		printer.write(0x1D);
		printer.write('f');
		printer.write(font);

		//HRI characters are printed using the font specified by GS H.
		//GS h = sets barcode height
		printer.write(0x1D);
		printer.write('h');
		printer.write(h);

		//GS w = sets barcode width
		printer.write(0x1D);
		printer.write('w');
		printer.write(w);//module = 1-6

		// TWO POSSIBLE VARIANTS

		if(type >= 0 && type <= 10) {
			// FIRST VARIANT
			System.out.println("first variant barcode");
			//GS k m d1 d2...dk 0x00
			printer.write(0x1D); //GS
			printer.write(0x6B); //k    0x6B='k'
			printer.write(type);// 0<=m<=10 = barcode type 0-10
			printer.write(code.getBytes());//d1-dk // можее би трябва да има разтояние между символите
			printer.write(0x00);//print barcode ??

		} else if(type >= 65 && type <= 73) {
			// SECOND VARIANT
			System.out.println("second variant barcode");
			//GS k m n d1 d2...dk
			printer.write(0x1D); //GS
			printer.write(0x6B); //k    0x6B='k'
			printer.write(type);// 65<=m<=73 = barcode type 0-10
			printer.write(code.length()); // n -> length of encoded string
			printer.write(code.getBytes());//d1-dn

			printer.write(new byte[]{0x1B,0x74,17});// try to set cyryllic code page
			printer.write((MyGetDate.getDate_Days_Hours() +  " " +  text).getBytes("cp866"));
			printer.write(0xA);
			printer.write(("\n\n").getBytes());
			printer.write(0xA);
			//output extra paper
		//	printer.write('d');
		//	printer.write(1);

		} else {
			JOptionPane.showMessageDialog(null,"Грешен баркод формат");
		}

	/*	This command feeds as much paper as is required to print the bar code,
		regardless of the line spacing specified by line space setting commands.*/
	}
	
	/**
	 * Encode and print PDF 417 barcode
	 * 
	 * @param code
	 *          String to be encoded in the barcode. 
	 *          Different barcodes have different requirements on the length
	 *          of data that can be encoded.
	 * @param type
	 *          Specify the type of barcode
	 *          0 - Standard PDF417
	 *          1 - Standard PDF417
	 *          
	 *  @param h
	 *  		Height of the vertical module in dots 2 <= n <= 8.
	 *  @param w
	 *  		Height of the horizontal module in dots 1 <= n <= 4.
	 *  @param cols
	 *  		Number of columns 0 <= n <= 30.
	 *  @param rows
	 *  		Number of rows 0 (automatic), 3 <= n <= 90.
	 *  @param error
	 *  		set error correction level 48 <= n <= 56 (0 - 8).
	 *  		
	 */
	public void printPSDCode(String code, int type, int h, int w, int cols, int rows, int error) throws IOException {

	 //print function 82
	  printer.write(0x1D);
	  printer.write("(k".getBytes());
	  printer.write(code.length()); //pl Code length
	  printer.write(0); //ph
	  printer.write(48); //cn
	  printer.write(80); //fn
	  printer.write(48); //m
	  printer.write(code.getBytes()); //data to be encoded
	  
	  
	  //function 65 specifies the number of columns
	  printer.write(0x1D);//init
	  printer.write("(k".getBytes());//adjust height of barcode
	  printer.write(3); //pl
	  printer.write(0); //pH
	  printer.write(48); //cn
	  printer.write(65); //fn
	  printer.write(cols);

	  //function 66 number of rows
	  printer.write(0x1D);//init
	  printer.write("(k".getBytes());//adjust height of barcode
	  printer.write(3); //pl
	  printer.write(0); //pH
	  printer.write(48); //cn
	  printer.write(66); //fn 
	  printer.write(rows); //num rows
	    
	  //module width function 67
	  printer.write(0x1D);
	  printer.write("(k".getBytes());
	  printer.write(3);//pL
	  printer.write(0);//pH
	  printer.write(48);//cn
	  printer.write(67);//fn
	  printer.write(w);//size of module 1<= n <= 4
	  
	  //module height fx 68
	  printer.write(0x1D);
	  printer.write("(k".getBytes());
	  printer.write(3);//pL
	  printer.write(0);//pH
	  printer.write(48);//cn
	  printer.write(68);//fn
	  printer.write(h);//size of module 2 <= n <= 8
	  
	  //error correction function 69
	  printer.write(0x1D);
	  printer.write("(k".getBytes());
	  printer.write(4);//pL
	  printer.write(0);//pH
	  printer.write(48);//cn
	  printer.write(69);//fn
	  printer.write(48);//m
	  printer.write(error);//error correction
	  
	  //choose pdf417 type function 70
	  printer.write(0x1D);
	  printer.write("(k".getBytes());
	  printer.write(3);//pL
	  printer.write(0);//pH
	  printer.write(48);//cn
	  printer.write(70);//fn
	  printer.write(type);//set mode of pdf 0 or 1
	  
	  //print function 81
	  printer.write(0x1D);
	  printer.write("(k".getBytes());
	  printer.write(3); //pl
	  printer.write(0); //ph
	  printer.write(48); //cn
	  printer.write(81); //fn
	  printer.write(48); //m
	  
	}
	
	
	/**
	 * Store custom character
	 * input array of column bytes
	 * @param columnArray
	 * 		Array of bytes (0-255). Ideally not longer than 24 bytes.
	 * 		
	 * @param mode
	 * 		0 - 8-dot single-density.
	 * 		1 - 8-dot double-density.
	 * 		32 - 24-dot single density.
	 * 		33 - 24-dot double density.
	 */
	public void storeCustomChar(int[] columnArray, int mode){
		
		//function GS*
		printer.write(0x1B);
		printer.write('*');
		printer.write(mode);
		printer.write( (mode==0 ||mode==1)? columnArray.length : columnArray.length / 3 );//number of cols
		printer.write(0);
		for (int j : columnArray) {
			printer.write(j);
		}
		
	}
	
	/**
	 * Store custom character
	 * input array of column bytes.	NOT WORKING
	 * @param spacing
	 * 		Integer representing Vertical motion of unit in inches. 0-255
	 * 
	 */
	public void setLineSpacing(int spacing){
	/*	Default] n =31.*/
		//function ESC 3
		printer.write(0x1B);
		printer.write('3');
		printer.write(spacing);
		
	}
	
	public void cut(){
		printer.write(0x1D);
		printer.write('V');
		printer.write(48);
		printer.write(0);
	}
	
	public void feedAndCut(int feed){
		
		feed(feed);
		cut();
	}
	
	public void beep() throws IOException {
		printer.write(0x1B);
		printer.write("(A".getBytes());
		printer.write(4);
		printer.write(0);
		printer.write(48);
		printer.write(55);
		printer.write(3);
		printer.write(15);
	}
	
	
	/**
	 * 
	 * Print a sample sheet
	 * 
	 */
	public void printSampler() throws IOException {
		//print samples of all functions here
		resetToDefault();
		escInit();
		storeChar(178);
		storeChar(177);
		storeChar(176);

		storeString("Hello World");
		printStorage();
		
		
		printString("printString();");
		setBold(true);
		printString("setBold(true)");
		setBold(false);
		setUnderline(1);
		printString("setUnderline(1)");
		setUnderline(2);
		printString("setUnderline(2)");
		setUnderline(0);
		setInverse(true);
		printString("setInverse(true)");
		setInverse(false);
		setJustification(0);
		printString("setJustification(0)\n//left - default");
		setJustification(1);
		printString("setJustification(1)\n//center");
		setJustification(2);
		printString("setJustification(2)\n//right");
		setJustification(1);
		printQR("http://www.josephbergen.com", 51, 8);
		printAndFeed("\n##name## ##version##\nby Joseph Bergen\nwww.josephbergen.com", 4);
		resetToDefault();
	}
	public void printHexBytes(final byte[] bytes, String printerName) {
		PrintService printService;
		final HashAttributeSet attributeSet = new HashAttributeSet();
		attributeSet.add(new PrinterName(printerName, null));
		final PrintService[] services = PrintServiceLookup.lookupPrintServices(null, attributeSet);
		if (services.length == 0) {
			JOptionPane.showMessageDialog(null, "Printer not found !");
			return;
		}
		if (services.length > 1) {
			System.out.println("Found more than one printer. Only the first printer will be used.");
		}
		printService = services[0];
		System.out.println("Printer found: " + printService.getName());
		try {
			final DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
			final SimpleDoc doc = new SimpleDoc(bytes, flavor, null);
			final DocPrintJob job = printService.createPrintJob();
			job.print(doc, null);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}