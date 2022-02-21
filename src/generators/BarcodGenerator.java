package generators;

public class BarcodGenerator extends BaseGenerator {

	public int[] next_barcod(int[] arr) {
		return next_variation(arr, 2);
	}

	// private int[] next_variation(int[] arr,int k) {
	// int i = k - 1;
	//
	// while (i >= 0) {
	//
	// if (arr[i] < 9) {
	// arr[i] = arr[i] + 1;
	// return arr;
	// }
	// arr[i] = 0;
	// i--;
	// }
	// return null;
	// }

	/*
	 * private void print(int[] arr,int k) { for (int i = 0; i < k; i++) {
	 * System.out.print(arr[i] + " "); } System.out.println(); }
	 */

	public int getCheckSum13(int[] digits) {
		int sum = 0;
		int rem = 0;
		int i = 1;

		for (i = 0; i < digits.length - 1; i++) { // len - 1 beacause of last
													// position is preserved for
													// checksum
			if (i % 2 == 0) {
				sum += (digits[i]);
			} else if (i % 2 != 0) {
				sum += (digits[i] * 3);
			}
		}

		rem = sum % 10;
		if (rem != 0) {
			rem = 10 - rem;
		}

		return rem;
	}

	public static String getEan13Barcode(String code) {
		String year;// 2
		String dayOfYear;// 3
		String hour; // 2
		// String minutes; // 2
		String sec; // 2
		int evenSum = 0;
		int oddSum = 0;
		for (int i = code.length() - 1; i >= 0; i -= 2) {
			evenSum += (code.charAt(i - 1) - 48);
			oddSum += (code.charAt(i) - 48);
		}
		// Calculating the EAN 13 checksum
		return code + ((10 - (3 * oddSum + evenSum) % 10) % 10);

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub / 93.475
		BarcodGenerator bg = new BarcodGenerator();
		// int[] digits = {0,0,7,5,6,7,8,1,6,4,1,2};
		// int checkSum = bg.getCheckSum13(digits);
		// System.out.println(checkSum);
		String barcode = getEan13Barcode("888356759828");
		System.out.println(barcode);
	}

}
