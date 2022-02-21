package generators;

public class SerialGenerator extends BaseGenerator {
//	private int[] next_variation(int[] arr,int k) {
//		int i = k - 1;
//		
//		while (i >= 0) {
//
//			if (arr[i] < 9) {
//				arr[i] = arr[i] + 1;
//				return  arr;
//			}
//			arr[i] = 0;
//			i--;
//		}
//		return null;
//	}
	
	private void print(int[] arr,int k) {
		for (int i = 0; i < k; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}
	public String digitsToString(int[] digits) {
//		StringBuilder	sb = new StringBuilder();
//			for(int i : digits) {
//				sb.append(i);
//			}
			return  digToString(digits);// sb.toString();
	}
		
		private int[] next_serial(int[] digits) {
			return next_variation(digits,7); // digtits for serial -> 7
		}
		public int[] updateSerial(String serial) {
			int[] next_serial = new int[7];
			for(int i = 0;i < next_serial.length;i++) {
				next_serial[i] = (int)(serial.charAt(i) - 48);
			}
			next_serial = next_serial(next_serial);
			return next_serial;
		}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
