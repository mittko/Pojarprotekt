package enterdialog;

import admin.team.AddUserDialog;
import admin.team.TeamTable;
import calendar.GeneralTechnicalReview;
import exceptions.ErrorDialog;
import exceptions.InOutException;
import http.RequestCallback2;
import http.client.GetClientService;
import http.user.GetUserService;
import log.IOErrorsWriter;
import models.AuthRequest;
import models.Firm;
import models.LoginRes;
import models.User;
import net.GetCurrentIP;
import run.DeleteFiles;
import run.JDialoger;
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

public class PasswordDialog extends MainPanel {

	private JTextField field = null;
	private JPasswordField enterPassword = null;
	private final User user = null;
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

		GridBagConstraints gbc6 = new GridBagConstraints();
		gbc6.gridx = 2;
		gbc6.gridy = 0;
		gbc6.insets = new Insets(2,5,2,5);


		TooltipButton createUserButton = new TooltipButton();// (setIcons(enterImage));
		createUserButton.setToolTipText(getHTML_Text("Регистрирай нов потребител"));
		createUserButton.setPreferredSize(new Dimension((int) (field.getPreferredSize()
				.getWidth() * 0.1),
				(int) (field.getPreferredSize().getHeight())));

		createUserButton.setAutoSizedIcon(createUserButton, new LoadIcon().setIcons(clientsImage));
        createUserButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddUserDialog addUser = new AddUserDialog();
				final JDialoger jDialog = new JDialoger();
				jDialog.setContentPane(addUser);
				jDialog.setTitle("Добави нов потребител");
				jDialog.setResizable(false);
				jDialog.Show();
			}
		});
		basePanel.add(createUserButton,gbc6);


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

		GetUserService userService = new GetUserService();

		AuthRequest loginRequest = new AuthRequest();
		String userName = field.getText();
		String password = new String(enterPassword.getPassword());
		loginRequest.setUsername(userName);
		loginRequest.setPassword(password);
		userService.init(loginRequest, new RequestCallback2() {
			@Override
			public <T> void callback(T t) {
				frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

				LoginRes loginRes = (LoginRes) t;
				if(loginRes != null) {

					frame.dispose();

					ACCESS_TOKEN = String.format("Bearer %s",loginRes.getToken());


					User user1 = loginRes.getUser();

					// variable init
					personName = user1.getUsser();

					ACCESS_MENU[0] = user1.getService_Order().equals("yes");
					ACCESS_MENU[1] = user1.getWorking_Book().equals("yes");
					ACCESS_MENU[2] = user1.getInvoice().equals("yes");
					ACCESS_MENU[3] = user1.getReports().equals("yes");
					ACCESS_MENU[4] = user1.getNew_Ext().equals("yes");
					ACCESS_MENU[5] = user1.getHidden_Menu().equals("yes");
					ACCESS_MENU[6] = user1.getAcquittance().equals("yes");

					tr = new GeneralTechnicalReview();
					enterFrame = new JustFrame();
					enterFrame.setResizable(false);
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
					enterFrame.add(tr);
					enterFrame.setSize(tr.WIDTH, tr.HEIGHT - 50);
					enterFrame.pack();
					tr.loadData();

					loadSallerData();
				}

			}
		});
//				userService.getUser(field.getText(), new RequestCallback2() {
//					@Override
//					public <T> void callback(T t) {
//
//						frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//
//						User user = (User) t;
//
//						if (!field.getText().equals(user.getUsser())) {
//							ErrorDialog.showErrorMessage("Грешно потребителско име!");
//							return;
//						} else {
//							String password = new String(
//									enterPassword.getPassword());
//							if (!password.equals(user.getPassword())) {
//								ErrorDialog.showErrorMessage("Грешно въведена парола!");
//								return;
//							}
//						}
//
//						frame.dispose();
//
//
//						// variable init
//						personName = user.getUsser();
//
//						ACCESS_MENU[0] = user.getService_Order().equals("yes");
//						ACCESS_MENU[1] = user.getWorking_Book().equals("yes");
//						ACCESS_MENU[2] = user.getInvoice().equals("yes");
//						ACCESS_MENU[3] = user.getReports().equals("yes");
//						ACCESS_MENU[4] = user.getNew_Ext().equals("yes");
//						ACCESS_MENU[5] = user.getHidden_Menu().equals("yes");
//						ACCESS_MENU[6] = user.getAcquittance().equals("yes");
//
//						tr = new GeneralTechnicalReview();
//						enterFrame = new JustFrame();
//						enterFrame.setResizable(false);
//						enterFrame.setFrameLocationOnTheCenter();
//						enterFrame.addWindowListener(new WindowAdapter() {
//							@Override
//							public void windowClosing(WindowEvent we) {
//								int yes_no = JOptionPane.showOptionDialog(
//										null, "Изход", "ПОЖАРПРОТЕКТ",
//										JOptionPane.YES_NO_OPTION,
//										JOptionPane.QUESTION_MESSAGE, null,
//										new String[] { "Да", "Не" }, // this
//										// is
//										// the
//										// array
//										"default");
//
//								if (yes_no == 0) {
//
//									DeleteFiles df = new DeleteFiles();
//									df.removeTmpFile();
//
//									enterFrame
//											.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//								} else {
//									enterFrame
//											.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
//								}
//							}
//						});
//						enterFrame.add(tr);
//						enterFrame.setSize(tr.WIDTH, tr.HEIGHT - 50);
//						enterFrame.pack();
//						tr.loadData();
//
//						loadSallerData();
//					}
//				});


	}

	private void loadSallerData() {

		GetClientService clientService = new GetClientService();
		clientService.getFirm(SALLER_TITLE, new RequestCallback2() {
			@Override
			public <T> void callback(T t) {
				Firm firm = (Firm) t;
				if(firm != null) {
					SALLER_NAME = firm.getFirm();
					SALLER_CITY = firm.getCity();
					SALLER_ADDRESS = firm.getAddress();
					SALLER_EIK = firm.getEik();
					SALLER_MOL = firm.getMol();
					SALLER_E_MAIL = firm.getEmail();
					SALLER_PERSON_CONTACT = firm.getPerson();
					SALLER_PERSON_TELEFON = firm.getTelPerson();
					SALLER_BANK = firm.getBank();
					SALLER_BIC = firm.getBic();
					SALLER_IBAN = firm.getIban();
				}
			}
		});

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
