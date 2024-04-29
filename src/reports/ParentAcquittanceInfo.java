package reports;

public class ParentAcquittanceInfo {

	private String key; // id
	private String value;
	private String client;
	private String saller;
	private String date;
	
	
	public ParentAcquittanceInfo(String key,String value,String client,String saller,String date) {
		this.key = key;
		this.value = value;
		this.client = client;
		this.saller = saller;
		this.date = date;
	}
	
	public String getKey() {
		return this.key;
	}
	public String getValue() {
		return this.value;
	}
	public String getClient() {
		return this.client;
	}
	public String getSaller() {
		return this.saller;
	}
	public String getDate() {
		return this.date;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
