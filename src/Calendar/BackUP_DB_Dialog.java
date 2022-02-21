//package Calendar;
//
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.Font;
//import java.awt.GridBagConstraints;
//import java.awt.GridBagLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.File;
//
//import javax.swing.BorderFactory;
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JProgressBar;
//import javax.swing.SwingUtilities;
//import javax.swing.SwingWorker;
//
//import Calendar.backupDB.Back_Up_Executor;
//import CommonResources.MainPanel;
//
//public class BackUP_DB_Dialog extends MainPanel{
//
//	JPanel basePanel;
//	JLabel infoLabel;
//	JLabel progressLabel;
//	JProgressBar pbar;
//	JButton copyButton;
//
//	
//
//	File parentFile;
//	File backupFile;
//	File oldBackupFile;
//	
//	public BackUP_DB_Dialog() {
//		parentFile = new File("D:/DB");
//		backupFile = new File("D:/CopyDB");
//		oldBackupFile = new File("D:/CopyDB/DB");
//		
//		basePanel = new JPanel();
//		basePanel.setBorder(BorderFactory.createLineBorder(Color.black));
//		basePanel.setLayout(new GridBagLayout());
//		
//		infoLabel = new JLabel();
//		infoLabel.setText("<html>" + "<div>" +  " Копирането на данните може да отнеме известно време." + "</div>"
//				+ "<div>" + "По време на копирането не могат да бъдат извършвани - " + "</div>" + 
//				 "<div>" +"действия като запис на нови данни!" + "</div>" +  "</html>");
//		
//		progressLabel = new JLabel();
//		progressLabel.setText("                         ");
//		
//		pbar = new JProgressBar();
//		pbar.setStringPainted(true);
//		pbar.setFont(new Font(Font.SERIF,Font.BOLD,30));
//		pbar.setPreferredSize(new Dimension( 520, 35) );
//		copyButton = new JButton("Копирай");
//		copyButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				// TODO Auto-generated method stub
//				if(oldBackupFile.exists()) {
//					deleteDir(oldBackupFile);
//				}
//				copyButton.setEnabled(false);
//				
//				progressLabel.setText("Копиране...........");
//				// paint progress
//				PaintProgress ppr = new PaintProgress();
//				ppr.execute();
//				
//				// copy db content
//				Back_Up_Executor backup = new Back_Up_Executor();
//				backup.execute();
//			}
//			
//		});
//
//		GridBagConstraints gbc1 = new GridBagConstraints();
//		gbc1.gridx = 0;
//		gbc1.gridy = 0;
//		
//		GridBagConstraints gbc2 = new GridBagConstraints();
//		gbc2.gridx = 0;
//		gbc2.gridy = 1;
//	
//		
//		GridBagConstraints gbc3 = new GridBagConstraints();
//		gbc3.gridx = 0;
//		gbc3.gridy = 2;
//		
//		GridBagConstraints gbc4 = new GridBagConstraints();
//		gbc4.gridx = 2;
//		gbc4.gridy = 2;
//
//		
//		GridBagConstraints gbc5 = new GridBagConstraints();
//		gbc5.gridx = 3;
//		gbc5.gridy = 2;
//	
//		
//		basePanel.add(infoLabel, gbc1);
//		basePanel.add(progressLabel, gbc2);
//		basePanel.add(pbar, gbc3);
//		basePanel.add(copyButton, gbc4);
//		
//		this.add(basePanel);
//		
//	}
//	void deleteDir(File file) {
//	    File[] contents = file.listFiles();
//	    if (contents != null) {
//	        for (File f : contents) {
//	            deleteDir(f);
//	        }
//	    }
//	    file.delete();
//	}
//	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		JFrame jf = new JFrame();
//		jf.add(new BackUP_DB_Dialog());
//		jf.pack();
//		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		jf.setVisible(true);
//	}
//	private long getFileMB(File folder) {
//		long fileSizeInBytes = getFolderSize(folder);
//		// Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
//		long fileSizeInKB = fileSizeInBytes / 1024;
//		// Convert the KB to MegaBytes (1 MB = 1024 KBytes)
//		return fileSizeInKB / 1024;
//	}
//	private long getFolderSize(File folder) {
//	    long length = 0;
//	    File[] files = folder.listFiles();
//	 
//	    int count = files.length;
//	 
//	    for (int i = 0; i < count; i++) {
//	        if (files[i].isFile()) {
//	            length += files[i].length();
//	        }
//	        else {
//	            length += getFolderSize(files[i]);
//	        }
//	    }
//	    return length;
//	}
//	class PaintProgress extends SwingWorker {
//		long totalMB;
//		long mb;
//		int value;
//		
//		public PaintProgress() {
//			totalMB = getFileMB( parentFile ); 
//		}
//
//		@Override
//		protected Object doInBackground() throws Exception {
//			// TODO Auto-generated method stub
//			
//			while(true) {
//			mb = getFileMB( backupFile );
//			value = (int) ( (mb * 100) / totalMB);
//			SwingUtilities.invokeLater(new Runnable() {
//
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//				
//					pbar.setValue(value);
//					pbar.setString(value + "%");
//					if(value == 100){
//						progressLabel.setText("Данните са записани успешно !");
//					}
//				}
//				
//			});
//			if(value >= 100) 
//				break;
//			}
//		
//			return null;
//		}
//		
//	}
//}
