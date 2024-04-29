package admin.team.workers;

import java.awt.Cursor;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import db.TeamDB.Member;

public class SeeMembersWorker extends SwingWorker {

	JDialog jd;
	public SeeMembersWorker(JDialog jd) {
		this.jd = jd;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
   
	}
	@Override
	public ArrayList<Object[]> doInBackground() throws Exception {
		// TODO Auto-generated method stub
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		try {
		
			result = Member.getMembers();
		}  finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
				
			});
		}
		return result;
	}

}
