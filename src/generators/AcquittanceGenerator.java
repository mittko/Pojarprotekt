package generators;

public class AcquittanceGenerator extends BaseGenerator {
//	private static int[] next_variation(int[] arr,int k) {
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
	public static String digitsToString(int[] digits) {
		StringBuilder	sb = new StringBuilder();
			for(int i : digits) {
			sb.append(i);
			}
			return  sb.toString();
		}
	private static int[] next_acquittance(int[] digits) {
		return next_variation(digits,10); // digtits for invoice number -> 10
	}
	public static int[] updateAcquittance(String invoice) {
		int[] next_acquittance = new int[10];
		for(int i = 0;i < next_acquittance.length;i++) {
			next_acquittance[i] = (int)(invoice.charAt(i) - 48);
		}
		next_acquittance = next_acquittance(next_acquittance);
		return next_acquittance;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
