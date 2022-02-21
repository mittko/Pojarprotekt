package generators;

public class BaseGenerator {
	protected static int[] next_variation(int[] arr, int k) {
		int i = k - 1;

		while (i >= 0) {

			if (arr[i] < 9) {
				arr[i] = arr[i] + 1;
				return arr;
			}
			arr[i] = 0;
			i--;
		}
		return null;
	}
	public String digToString(int[] digits) {
		StringBuilder	sb = new StringBuilder();
			for(int i : digits) {
				sb.append(i);
			}
			return sb.toString();
		}
}
