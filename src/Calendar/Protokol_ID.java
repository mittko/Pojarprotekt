package Calendar;

public class Protokol_ID implements Comparable<Protokol_ID> {

	private String client;
	private String number;

	public Protokol_ID(String client, String number) {
		this.client = client;
		this.number = number;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public int compareTo(Protokol_ID arg0) {
		// TODO Auto-generated method stub
		return this.number.compareTo(arg0.number);
	}

}
