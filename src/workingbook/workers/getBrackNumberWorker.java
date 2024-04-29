package workingbook.workers;

import javax.swing.SwingWorker;

import db.Brack.BrackNumber;

public class getBrackNumberWorker extends SwingWorker{

	@Override
	public String doInBackground() throws Exception {
		// TODO Auto-generated method stub
		return BrackNumber.getBrackNumber();
	}

}
