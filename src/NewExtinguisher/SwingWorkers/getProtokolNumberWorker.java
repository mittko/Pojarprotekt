package NewExtinguisher.SwingWorkers;

import generators.ProtokolGenerator;

import javax.swing.SwingWorker;

import db.Protokol.ProtokolNumber;

public class getProtokolNumberWorker extends SwingWorker {

	private ProtokolGenerator pg = null;
	
	public getProtokolNumberWorker() {
		pg = new ProtokolGenerator();
	}
	@Override
	public String doInBackground() throws Exception {
		// TODO Auto-generated method stub
		//int[] next_number = pg.updateProtokol(protokolNumber);
	     //ProtokolNumber.updateProtokolNumberInDB(pg.digitsToString(next_number));
		return ProtokolNumber.getProtokolNumber();
	}

}
