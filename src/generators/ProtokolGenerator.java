package generators;


public class ProtokolGenerator extends BaseGenerator {
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
		/*StringBuilder	sb = new StringBuilder();
			for(int i : digits) {
				sb.append(i);
			}*/
			return digToString(digits);// sb.toString();
		}
	private int[] next_protokol(int[] digits) {
		return next_variation(digits,7); // digtits for serial -> 7
	}
	public int[] updateProtokol(String serial) {
		int[] next_protokol = new int[7];
		for(int i = 0;i < next_protokol.length;i++) {
			next_protokol[i] = (int)(serial.charAt(i) - 48);
		}
		next_protokol = next_protokol(next_protokol);
		return next_protokol;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	/*	ProtokolGenerator pr = new ProtokolGenerator();
		ProtokolNumber pn = new ProtokolNumber();
	//	pn.clearProtokolNumber();
	//	pn.initProtokolNumber("0000000");
		for(int i = 0;i < 3;i++) {
       String pr_number = ProtokolNumber.getProtokolNumber();
       int[] next_number = pr.updateProtokol(pr_number);
       pr.print(next_number, 7);
       ProtokolNumber.updateProtokolNumberInDB(pr.digitsToString(next_number));
		}*/
       
	}

}
