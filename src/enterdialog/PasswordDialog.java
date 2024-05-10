package enterdialog;

import calendar.GeneralTechnicalReview;
import exceptions.InOutException;
import log.IOErrorsWriter;
import workingbook.WorkingBook;
import db.Client.ClientTable;
import db.TeamDB.Member;
import db.WorkPrice.WorkPriceDB;
import net.GetCurrentIP;
import run.DeleteFiles;
import run.JustFrame;
import utils.LoadIcon;
import utils.MainPanel;
import utils.TooltipButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PasswordDialog extends MainPanel {

	private JTextField field = null;
	private JPasswordField enterPassword = null;
	private String[] user_password = null;
	JustFrame enterFrame = null;
	GeneralTechnicalReview tr = null;

	public PasswordDialog() {

		JPanel basePanel = new JPanel();
		basePanel.setOpaque(false);
		GridBagLayout gridBagLayout = new GridBagLayout();

		JLabel user = new JLabel("ПОТРЕБИТЕЛ");
		user.setForeground(Color.WHITE);

		user.setFont(new Font(Font.DIALOG, Font.BOLD, getFontSize()));

		field = new JTextField(20);
		field.setBorder(BorderFactory.createLoweredBevelBorder());

		TooltipButton button = new TooltipButton("");// (setIcons(enterImage));

		button.setPreferredSize(new Dimension((int) (field.getPreferredSize()
				.getWidth() * 0.1),
				(int) (field.getPreferredSize().getHeight())));
		;
		button.setAutoSizedIcon(button, new LoadIcon().setIcons(enterImage));

		JLabel password = new JLabel("ПАРОЛА");
		password.setFont(new Font(Font.DIALOG, Font.BOLD, getFontSize()));
		password.setForeground(Color.WHITE);

		enterPassword = new JPasswordField(20);
		enterPassword.setBorder(BorderFactory.createLoweredBevelBorder());
		enterPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
					validateEnter();
				}
			}
		});
		basePanel.setLayout(gridBagLayout);

		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.gridx = 0;
		gbc1.gridy = 0;

		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.gridx = 1;
		gbc2.gridy = 0;

		basePanel.add(user, gbc1);
		basePanel.add(field, gbc2);

		GridBagConstraints gbc3 = new GridBagConstraints();
		gbc3.gridx = 0;
		gbc3.gridy = 1;

		GridBagConstraints gbc4 = new GridBagConstraints();
		gbc4.gridx = 1;
		gbc4.gridy = 1;

		basePanel.add(password, gbc3);
		basePanel.add(enterPassword, gbc4);

		GridBagConstraints gbc5 = new GridBagConstraints();
		gbc5.gridx = 2;
		gbc5.gridy = 1;
		gbc5.insets = new Insets(2, 5, 2, 5);

		basePanel.add(button, gbc5);

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				validateEnter();

			}

		});

		this.add(basePanel);
		this.setOpaque(false);

		init();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Framer framer = new Framer(new FrontComponents());
	}


	private void init() {
		FileReader fileReader = null;
		BufferedReader buff = null;
		String line = null;
		try {
			fileReader =  new FileReader("./values.txt");
			buff = new BufferedReader(fileReader);
		//	while( (line = buff.readLine()) != null){
			    line = buff.readLine();
				String[] spl = line.split("=");
				GetCurrentIP.DB_PATH = spl[1];
				line = buff.readLine();
			    String[] spl1 = line.split("=");
				GetCurrentIP.LPS_DB_PATH = spl1[1];

			System.out.println("POJARPROTEKT "+GetCurrentIP.DB_PATH);
			System.out.println("LPS "+GetCurrentIP.LPS_DB_PATH);
			//	String[] spl1 = line.split("=");
			//	MainPanel.LABEL_PRINTER1 = spl1[1];

		//	}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			IOErrorsWriter.writeIO(e.toString());
			InOutException.showErrorMessage(e);
			e.printStackTrace();
		} finally {
			try {
				if(fileReader != null) {
					fileReader.close();
				}
				if(buff != null) {
					buff.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				IOErrorsWriter.writeIO(e.toString());
				InOutException.showErrorMessage(e);
				e.printStackTrace();
			}
		}
	}
	private void validateEnter() {
		final JFrame frame = (JFrame) SwingUtilities
				.getWindowAncestor(PasswordDialog.this);

		frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));

		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				// TODO Auto-generated method stub
				try {

					user_password = Member.getUser(field.getText());
					if (user_password == null) {
						return null;
					}

					if (!field.getText().equals(user_password[0])) {
						JOptionPane.showMessageDialog(null,
								"Грешно потребителско име!");
						return null;
					} else {
						String password = new String(
								enterPassword.getPassword());
						if (!password.equals(user_password[1])) {
							JOptionPane.showMessageDialog(null,
									"Грешно въведена парола!");
							return null;
						}
					}

					loadSallerDataAndDoingPrice();

					SwingUtilities.invokeLater(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub

							frame.dispose();
							// variable init
							personName = user_password[0];

							for (int i = 2; i < user_password.length; i++) {
								if (user_password[i].equals("yes"))
									ACCESS_MENU[i - 2] = true;
							}

							tr = new GeneralTechnicalReview();
							enterFrame = new JustFrame();
							enterFrame.add(tr);
							enterFrame.setResizable(false);
							tr.loadData();

							enterFrame.setSize(tr.WIDTH, tr.HEIGHT - 50);
							enterFrame.setFrameLocationOnTheCenter();
							enterFrame.addWindowListener(new WindowAdapter() {
								@Override
								public void windowClosing(WindowEvent we) {
									int yes_no = JOptionPane.showOptionDialog(
											null, "Изход", "ПОЖАРПРОТЕКТ",
											JOptionPane.YES_NO_OPTION,
											JOptionPane.QUESTION_MESSAGE, null,
											new String[] { "Да", "Не" }, // this
																			// is
																			// the
																			// array
											"default");

									if (yes_no == 0) {

										DeleteFiles df = new DeleteFiles();
										df.removeTmpFile();

										enterFrame
												.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

									} else {
										enterFrame
												.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
									}
								}
							});
						}

					});
				} finally {
					frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
				return null;
			}

		};
		worker.execute();
	}

	private void loadSallerDataAndDoingPrice() {
		SwingWorker<Void, Void> sw = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				// TODO Auto-generated method stub

				ArrayList<String> sallerData = ClientTable
						.getClientDetails(SALLER_TITLE);

				SALLER_NAME = sallerData.get(0);
				SALLER_CITY = sallerData.get(1);
				SALLER_ADDRESS = sallerData.get(2);
				SALLER_EIK = sallerData.get(3);
				SALLER_MOL = sallerData.get(4);
				SALLER_E_MAIL = sallerData.get(5);
				SALLER_PERSON_CONTACT = sallerData.get(6);
				SALLER_PERSON_TELEFON = sallerData.get(7);
				SALLER_BANK = sallerData.get(8);
				SALLER_BIC = sallerData.get(9);
				SALLER_IBAN = sallerData.get(10);


				WorkingBook.TO_PRICE = WorkPriceDB.getWorkValue(MainPanel.TO);

				WorkingBook.HI_PRICE = WorkPriceDB.getWorkValue(HI);

				return null;
			}

		};
		sw.execute();

	}

//	  private void loadWorkPrice() { SwingWorker<Void, Void> sw = new
//	  SwingWorker<Void, Void>() {
//
//	  @Override protected Void doInBackground() throws Exception { // TODO
//	  Worker.TO_PRICE =
//	  WorkPriceDB.getWorkValue(ТО);
//
//	  Worker.HI_PRICE = WorkPriceDB.getWorkValue(ХИ);
//
//	  return null; }
//
//	  }; sw.execute(); }

}
