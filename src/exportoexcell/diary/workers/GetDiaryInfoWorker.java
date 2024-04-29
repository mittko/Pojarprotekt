package exportoexcell.diary.workers;

import db.Report.ReportRequest;
import utils.MainPanel;

import javax.swing.*;
import java.util.ArrayList;

public class GetDiaryInfoWorker extends SwingWorker<ArrayList<Object[]>,Object> {

	private String from;
	private String to;
	private String query;
	public GetDiaryInfoWorker(String from, String to) {
		// TODO Auto-generated constructor stub
		this.from = from;
		this.to = to;
		query = "select * from " + MainPanel.PROTOKOL +
				" where date between Date('"+this.from+"') and Date('"
				+this.to+"') order by number";
	}

	@Override
	public ArrayList<Object[]> doInBackground() throws Exception {
		return ReportRequest.getReports(query);//ProtokolTable.extractDataFromProtokolByCurrentDate
				//(GetDate.getReversedSystemDate());
	}
	

	
}
