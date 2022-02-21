package admin.Team.Workers;

import java.awt.Cursor;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import db.TeamDB.Member;

public class RemoveMemberWorker extends SwingWorker {

	private String member = null;
	private JDialog jd = null;
	private int remove = 0;
	public RemoveMemberWorker(String member,JDialog jd) {
		this.member = member;
		this.jd = jd;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub
		
		try {
		
		remove = Member.removeMember(member);
		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(remove > 0) {
						JOptionPane.showMessageDialog(null, "Потребителят е изтрит!");
					}
					jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
				
			});
		}
		return null;
	}

}
