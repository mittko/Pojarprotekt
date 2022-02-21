package NewClient.editClient;

public class IncorrectPerson {
	private String name;
	private String icorrect;

	public IncorrectPerson(String name, String icorrect) {
		super();
		this.name = name;
		this.icorrect = icorrect;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcorrect() {
		return icorrect;
	}

	public void setIcorrect(String icorrect) {
		this.icorrect = icorrect;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.name;
	}

}
