package exportoexcell.diary;

import exportoexcell.diary.workers.ExportToExcelWorker;
import mydate.MyGetDate;
import run.JDialoger;
import utils.EditableField;
import utils.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChoiseDateForDiary extends MainPanel {

	private final EditableField fromDateField;
	private final EditableField toDateField;

	public ChoiseDateForDiary(final JDialoger jd) {
		// TODO Auto-generated constructor stub
		GridLayout layout = new GridLayout(3, 1, 5, 5);
		this.setLayout(layout);
		
		JPanel fromPanel = new JPanel();
		int size = 12;
		fromDateField = new EditableField("от XX.XX.XXXX", size, false);
		fromDateField.setPreferredSize(new Dimension(50,getFontSize() + 15));
		fromDateField.setText(MyGetDate.getReversedSystemDate());
		
		JPanel toPanel = new JPanel();
		toDateField = new EditableField("до XX.XX.XXXX", size, false);
		toDateField.setPreferredSize(new Dimension(50,getFontSize() + 15));
		toDateField.setText(MyGetDate.getReversedSystemDate());
		
		JPanel buttonPanel = new JPanel();
		JButton generateDiary = new JButton("Създай дневник");
		generateDiary.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				// first check is user input date is correct !!!
				if(fromDateField.getText().length() != 10 ||
						toDateField.getText().length() != 10) {
					JOptionPane.showMessageDialog(null, "Грешен формат на дата",null,JOptionPane.ERROR_MESSAGE);
				    return;
				}
				SimpleDateFormat formatter = new
						SimpleDateFormat("dd.mm.yyyy");
				try {
					Date parsedDate = formatter.parse(fromDateField.getText());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Грешен формат на дата",null,JOptionPane.ERROR_MESSAGE);
				    return;
				}
				try {
					Date parsedDate2 = formatter.parse(toDateField.getText());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Грешен формат на дата",null,JOptionPane.ERROR_MESSAGE);
				    return;
				}
				ExportToExcelWorker exportToExcelWorker = 
						new ExportToExcelWorker(fromDateField.getText(), 
								toDateField.getText(), jd);
				exportToExcelWorker.execute();
				
			}
			
		});
		fromPanel.add(fromDateField);
		toPanel.add(toDateField);
		buttonPanel.add(generateDiary);
		
		this.add(fromPanel);
		this.add(toPanel);
		this.add(buttonPanel);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
       JFrame jf = new JFrame();
       jf.getContentPane().add(new ChoiseDateForDiary(null));
       jf.pack();
       jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       jf.setVisible(true);
	}

}
