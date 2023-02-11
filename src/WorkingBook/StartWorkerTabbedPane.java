package WorkingBook;

import utils.MainPanel;

import javax.swing.*;
import java.awt.*;

public class StartWorkerTabbedPane extends MainPanel {

	public WorkingBook worker = null;
	public View view = null;
	public Brack brack = null;
	
	public StartWorkerTabbedPane(String protokolNumber, String brackNumber) {
		
		JTabbedPane tabbedPane = new JTabbedPane();
		
		worker = new WorkingBook();
		
		view = new View(protokolNumber);
		
		brack = new Brack(brackNumber);
		
		tabbedPane.addTab("Работна книга",worker);
		
		tabbedPane.addTab("Обработени", view);
	//	tabbedPane.setIconAt(1, setIcons("hammer.png"));
		
		tabbedPane.addTab("Бракувани", brack);
	//	tabbedPane.setIconAt(2, setIcons("brak2.png"));
		
		this.setPreferredSize(new Dimension(this.WIDTH-20,this.HEIGHT-70));
		this.add(tabbedPane);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
