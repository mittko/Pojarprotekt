package Calendar;

import java.util.ArrayList;

public class Extinguishers {

	public Object client;
	public Object type;
	public Object wheight;
	public Object TO_date;
	public Object P_Date;
	public Object HI_Date;
	public Object additional_data;
	ArrayList<String> dates;

	public Extinguishers(Object client, Object type, Object wheight,
			Object TO_Date, Object P_Date, Object HI_Date,
			Object additional_data) {
		this.client = client;
		this.type = type;
		this.wheight = wheight;
		this.TO_date = TO_Date;
		this.P_Date = P_Date;
		this.HI_Date = HI_Date;
		this.additional_data = additional_data;
	}

}
