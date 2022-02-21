package generators;

public class InvoiceGenerator extends BaseGenerator {
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
			return digToString(digits);//sb.toString();
		}
	private int[] next_invoice(int[] digits) {
		return next_variation(digits,10); // digtits for invoice number -> 10
	}
	public int[] updateInvocie(String invoice) {
		int[] next_invoice = new int[10];
		for(int i = 0;i < next_invoice.length;i++) {
			next_invoice[i] = (int)(invoice.charAt(i) - 48);
		}
		next_invoice = next_invoice(next_invoice);
		return next_invoice;
	}
	public int[] updateProformInvoice(String proform) {
		int[] next_invoice = new int[10];
		for(int i = 0;i < next_invoice.length;i++) {
			next_invoice[i] = (int)(proform.charAt(i) - 48);
		}
		next_invoice = next_invoice(next_invoice);
		return next_invoice;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	 InvoiceGenerator ig = new InvoiceGenerator();
     String invoice = "0000000999";
     int[] next_inv = ig.updateInvocie(invoice);
     System.out.println(ig.digitsToString(next_inv));
	}

}
