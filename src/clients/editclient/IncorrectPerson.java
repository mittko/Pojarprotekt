package clients.editclient;

public class IncorrectPerson {
	private String name;
	private String incorrectPerson;

	public IncorrectPerson(String name, String icorrect) {
		super();
		this.name = name;
		this.incorrectPerson = icorrect;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcorrect() {
		return incorrectPerson;
	}

	public void setIncorrect(String icorrect) {
		this.incorrectPerson = icorrect;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.name;
	}

}
