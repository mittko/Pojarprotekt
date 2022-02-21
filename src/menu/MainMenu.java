package menu;

import NewClient.NewClient;
import NewClient.editClient.EditClientPanel;
import NewExtinguisher.NewExtinguisherWindow;
import Reports.ReportDialog;
import ServiceOrder.ServiceOrder;
import WorkingBook.Brack;
import WorkingBook.StartWorkerTabbedPane;
import WorkingBook.View;
import admin.AdminDialog;
import db.Brack.BrackNumber;
import db.Invoice.InvoiceNumber;
import db.Protokol.ProtokolNumber;
import generators.GenerateSO;
import invoicewindow.InvoiceFrame;
import invoicewindow.SearchFromProformTab;
import invoicewindow.SearchFromProtokolTab;
import run.JDialoger;
import utility.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends MainPanel {

	// private GridLayout grid = null;
	private JButton serviceButton = null;
	private JButton newExtinguisher_Button = null;
	private JButton workingBook_Button = null;
	private JButton invoiceButton = null;
	private JButton reportsButton = null;
	private JButton editClientButton = null;
	private JButton newClientButton = null;

	private final JPanel menu = new JPanel();

	private final Color currColor = Color.decode("0xFF1141");// Color.LIGHT_GRAY.brighter().darker();//
																// Color.decode("0xA3BBC1");

	private final GridBagLayout gbl = new GridBagLayout();

	// private Dimension buttonSize = new Dimension(this.WIDTH / 3,
	// this.HEIGHT / 7);

	public MainMenu() {

		menu.setLayout(gbl);

		menu.setOpaque(false);

		serviceButton = new JButton("СЕРВИЗНА ПОРЪЧКА");
		// serviceButton.setPreferredSize(buttonSize);
		// serviceButton.setEnabled(ACCESS_MENU[0]);
		serviceButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFrame ancestor = (JFrame) SwingUtilities
						.getWindowAncestor(MainMenu.this);
				ancestor.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				SwingWorker<Void, Void> sw = new SwingWorker<Void, Void>() {

					@Override
					protected Void doInBackground() throws Exception {
						// TODO Auto-generated method stub
						String number = "no";
						number = GenerateSO.nextSO();
						SwingUtilities.invokeLater(new RunSO(number));
						return null;
					}

				};
				sw.execute();
			}
		});
		newExtinguisher_Button = new JButton("НОВИ ПОЖАРОГАСИТЕЛИ");
		// newExtinguisher_Button.setPreferredSize(buttonSize);
		// newExtinguisher_Button.setEnabled(ACCESS_MENU[4]);
		newExtinguisher_Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFrame ancestor = (JFrame) SwingUtilities
						.getWindowAncestor(MainMenu.this);
				ancestor.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				SwingWorker sw = new SwingWorker() {

					@Override
					protected Object doInBackground() throws Exception {
						// TODO Auto-generated method stub
						String serviceNumber = GenerateSO.nextSO(); // new
																	// SO_Table().getSO_Number();
						String protokolNumber = ProtokolNumber
								.getProtokolNumber();

						SwingUtilities.invokeLater(new Run_NewExt(
								serviceNumber, protokolNumber));
						return null;
					}

				};
				sw.execute();
			}

		});
		workingBook_Button = new JButton("РАБОТНА КНИГА");
		// workingBook_Button.setPreferredSize(buttonSize);
		// workingBook_Button.setEnabled(ACCESS_MENU[1]);
		workingBook_Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				final JFrame jf = (JFrame) SwingUtilities
						.getWindowAncestor(MainMenu.this);
				jf.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				SwingWorker startWorker = new SwingWorker() {
					private String protokolNumber = null;
					private String brackNumber = null;

					@Override
					protected Object doInBackground() throws Exception {
						// TODO Auto-generated method stub
						try {
							brackNumber = BrackNumber.getBrackNumber();
							protokolNumber = ProtokolNumber.getProtokolNumber();
						} finally {
							SwingUtilities.invokeLater(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									StartWorkerTabbedPane swf = new StartWorkerTabbedPane(
											protokolNumber, brackNumber);
									final JDialoger jDialog = new JDialoger();
									jDialog.setContentPane(swf);
									jDialog.setTitle("Работна книга");
									jDialog.setResizable(false);
									jDialog.addWindowListener(new WindowAdapter() {
										@Override
										public void windowClosing(WindowEvent we) {
											if (View.dtm_Extinguisher
													.getRowCount() > 0
													|| Brack.dtm_Scrab
															.getRowCount() > 0) {
												int yes_no = JOptionPane
														.showOptionDialog(
																null,
																"Наистина ли искате да затворите прозореца ?",
																"ПОЖАРПРОТЕКТ",
																JOptionPane.YES_NO_OPTION,
																JOptionPane.QUESTION_MESSAGE,
																null,
																new String[] {
																		"Да",
																		"Не" },
																"default");
												if (yes_no == 0) {
													View.dtm_Extinguisher
															.setRowCount(0);
													Brack.dtm_Scrab
															.setRowCount(0);
													jDialog.dispose();
												} else {
													jDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
												}
											} else {
												jDialog.dispose();
											}
										}
									});
									jDialog.Show();
									jf.setCursor(new Cursor(
											Cursor.DEFAULT_CURSOR));
								}

							});
						}
						return null;
					}

				};
				startWorker.execute();

			}

		});

		invoiceButton = new JButton("ФАКТУРИ");
		// invoiceButton.setPreferredSize(buttonSize);
		// invoiceButton.setEnabled(ACCESS_MENU[2]);
		invoiceButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SwingWorker sw = new SwingWorker() {
					private JFrame jf = (JFrame) SwingUtilities
							.getWindowAncestor(MainMenu.this);
					private String invoiceNumber = null;
					private String proformNumber = null;

					@Override
					protected Object doInBackground() throws Exception {
						// TODO Auto-generated method stub
						try {
							jf.setCursor(new Cursor(Cursor.WAIT_CURSOR));
							invoiceNumber = InvoiceNumber.getInvoiceNumber();
							proformNumber = InvoiceNumber.getProformNumber();
						} finally {
							SwingUtilities.invokeLater(new RunInvoice(
									invoiceNumber, proformNumber, jf));
						}
						return null;
					}

				};
				sw.execute();
			}

		});

		reportsButton = new JButton("СПРАВКИ");
		// reportsButton.setPreferredSize(buttonSize);
		// reportsButton.setEnabled(ACCESS_MENU[3]);
		reportsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ReportDialog mr = new ReportDialog();
				JDialoger jd = new JDialoger();
				jd.setTitle("Справки");
				jd.setContentPane(mr);
				jd.setResizable(false);
				jd.Show();
			}

		});
		editClientButton = new JButton("РЕДАКТИРАНЕ НА \n КЛИЕНТИ");
		// editClientButton.setPreferredSize(buttonSize);
		editClientButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				EditClientPanel client = new EditClientPanel();
				JDialoger jd = new JDialoger();
				jd.setTitle("Редактиране на клиенти");
				jd.setContentPane(client);
				jd.setResizable(false);
				jd.Show();
			}

		});
		newClientButton = new JButton("НОВ КЛИЕНТ");
		// newClientButton.setPreferredSize(buttonSize);
		newClientButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				NewClient client = new NewClient();
				JDialoger jd = new JDialoger();
				jd.setTitle("Въвеждане на клиенти");
				jd.setContentPane(client);
				jd.setResizable(false);
				jd.Show();
			}

		});
		int[] posX = { 0, 1 };
		int[] posY = { 0, 1, 2, 3 };
		GridBagConstraints[] gbc = new GridBagConstraints[posX.length
				* posY.length];
		int g = 0;
		// Insets insets = new Insets(5, 5, 5, 5);
		int jj = 0;
		int ii = 0;
		for (jj = 0; jj < posY.length; jj++) {
			for (ii = 0; ii < posX.length; ii++) {
				gbc[g] = new GridBagConstraints();
				gbc[g].fill = GridBagConstraints.HORIZONTAL;
				// gbc[g].insets = insets;
				gbc[g].gridx = posX[ii];
				gbc[g].gridy = posY[jj];
				g++;
			}
		}

		JButton[] menuButtons = { newClientButton, editClientButton,
				serviceButton, workingBook_Button, invoiceButton,
				reportsButton, newExtinguisher_Button };

		// stickerButton.setAutoSizedIcon(stickerButton,
		// setIcons(stickerImage));
		int nextPos = 0;
		// !!! Нови Клиенти и реадктиране на клиенти си ги има по подразбиране
		menu.add(menuButtons[0], gbc[nextPos]);
		nextPos++;
		menu.add(menuButtons[1], gbc[nextPos]);
		nextPos++;
		for (int i = 2; i < menuButtons.length; i++) {
			if (ACCESS_MENU[i - 2]) {
				menu.add(menuButtons[i], gbc[nextPos]);
				nextPos++;
			}
		}
		Dimension buttonSize = new Dimension(this.WIDTH / (ii + 1), this.HEIGHT
				/ (jj + 3));
		// !!! Нови Клиенти и реадктиране на клиенти си ги има по подразбиране
		menuButtons[0].setPreferredSize(buttonSize);
		menuButtons[1].setPreferredSize(buttonSize);
		for (int i = 2; i < menuButtons.length; i++) {
			if (ACCESS_MENU[i - 2]) {
				menuButtons[i].setPreferredSize(buttonSize);
			}
		}
		JPanel noMinimizable = new JPanel();
		noMinimizable.setOpaque(false);

		Action someAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (ACCESS_MENU[5] == false) {// HIDDEN_MENU IS ALLOWED !!!
					return;
				}
				AdminDialog sp = new AdminDialog();
				JDialoger jDialog = new JDialoger();
				jDialog.setTitle("");
				jDialog.setContentPane(sp);
				jDialog.setResizable(false);
				jDialog.Show();
			}
		};
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_A,
						java.awt.event.InputEvent.CTRL_DOWN_MASK),
				"actionMapKey");
		this.getActionMap().put("actionMapKey", someAction);

		// menu.setAlignmentY(Component.CENTER_ALIGNMENT);
		this.add(menu);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.setBackground(currColor);

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	class RunSO implements Runnable {
		String number;

		public RunSO(String number) {
			this.number = number;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub

			if (!number.equals("no")) {
				final ServiceOrder so = new ServiceOrder(number);
				final JDialoger jDialog = new JDialoger();
				jDialog.setContentPane(so);
				jDialog.setResizable(false);
				jDialog.setTitle("Сервизна Поръчка ( Завеждане на пожарогасители )");
				jDialog.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent we) {
						if (so.tModel.getRowCount() > 0) {
							int yes_no = JOptionPane
									.showOptionDialog(
											null,
											"Наистина ли искате да затворите прозореца ?",
											"ПОЖАРПРОТЕКТ",
											JOptionPane.YES_NO_OPTION,
											JOptionPane.QUESTION_MESSAGE, null,
											new String[] { "Да", "Не" },
											"default");
							if (yes_no == 0) {
								jDialog.dispose();
							} else {
								jDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
							}
						} else {
							jDialog.dispose();
						}
					}
				});
				jDialog.Show();
			}
			JFrame ancestor = (JFrame) SwingUtilities
					.getWindowAncestor(MainMenu.this);
			ancestor.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

	class Run_NewExt implements Runnable {

		private String serviceNumber = null;
		private String protokolNumber = null;

		public Run_NewExt(String serviceNumber, String protokolNumber) {
			this.serviceNumber = serviceNumber;
			this.protokolNumber = protokolNumber;
		}

		@Override
		public void run() {
			if (!serviceNumber.equals("no")) {
				NewExtinguisherWindow new_ext = new NewExtinguisherWindow(
						serviceNumber, protokolNumber);
				final JDialoger jDialog = new JDialoger();
				jDialog.setContentPane(new_ext);
				jDialog.setResizable(false);
				jDialog.setTitle("Продажба на нови пожарогасители");
				jDialog.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent we) {
						if (NewExtinguisherWindow.dftm.getRowCount() > 0) {
							int yes_no = JOptionPane
									.showOptionDialog(
											null,
											"Наистина ли искате да затворите прозореца ?",
											"ПОЖАРПРОТЕКТ",
											JOptionPane.YES_NO_OPTION,
											JOptionPane.QUESTION_MESSAGE, null,
											new String[] { "Да", "Не" },
											"default");
							if (yes_no == 0) {
								jDialog.dispose();
							} else {
								jDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
							}
						} else {
							jDialog.dispose();
						}
					}
				});
				jDialog.Show();
			}
			JFrame ancestor = (JFrame) SwingUtilities
					.getWindowAncestor(MainMenu.this);
			ancestor.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}

	}

	class RunInvoice implements Runnable {

		private String invoiceNumber = null;
		private String proformNumber = null;
		private JFrame jf = null;

		public RunInvoice(String invoiceNumber, String proformNumber, JFrame jf) {
			this.invoiceNumber = invoiceNumber;
			this.proformNumber = proformNumber;
			this.jf = jf;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			jf.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			if (invoiceNumber != null) {
				// MyInvoice2 invoice = new MyInvoice2(invoiceNumber,
				// proformNumber);
				final InvoiceFrame invoice = new InvoiceFrame();
				final JDialoger jDialog = new JDialoger();
				jDialog.setTitle("Фактури / Проформи / Стокови разписки");
				jDialog.setContentPane(invoice);
				jDialog.setResizable(false);
				jDialog.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent we) {
						if (SearchFromProtokolTab.invoiceTableModel
								.getRowCount() > 0
								|| SearchFromProformTab.proformTableModel
										.getRowCount() > 0
								|| invoice.acqTab.dftm.getRowCount() > 0) {
							int yes_no = JOptionPane
									.showOptionDialog(
											null,
											"Наистина ли искате да затворите прозореца ?",
											"ПОЖАРПРОТЕКТ",
											JOptionPane.YES_NO_OPTION,
											JOptionPane.QUESTION_MESSAGE, null,
											new String[] { "Да", "Не" },
											"default");
							if (yes_no == 0) {
								jDialog.dispose();
							} else {
								jDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
							}
						} else {
							jDialog.dispose();
						}
					}
				});
				jDialog.Show();
			}

		}

	}

	/*
	 * @Override public void paintComponent(Graphics g) {
	 * super.paintComponent(g);
	 * g.drawImage(background,0,0,this.WIDTH,this.HEIGHT,null); }
	 */

}
