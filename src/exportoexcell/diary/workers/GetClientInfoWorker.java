package exportoexcell.diary.workers;

import java.util.ArrayList;

import javax.swing.SwingWorker;

import db.Client.ClientTable;
import db.Client.FirmTable;

public class GetClientInfoWorker extends SwingWorker <ArrayList<String>,Void>{

	private String client;
	public GetClientInfoWorker(String client) {
		// TODO Auto-generated constructor stub
		this.client = client;
	}

	@Override
	public ArrayList<String> doInBackground() throws Exception {
		// TODO Auto-generated method stub
		  ArrayList<String> info = FirmTable.getFirmInfo(client);
		  if(info.size() == 0) {
			  info = ClientTable.getClientInfo(client);
		  }
		return info;
	}
	 
	
}
