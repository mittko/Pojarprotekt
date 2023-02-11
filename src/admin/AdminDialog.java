package admin;

import NewClient.editClient.EditClientPanel;
import admin.Artikul.AvailableArtikulsTable;
import admin.Doing.UpdateWorkPrice;
import admin.Parts.Price.UpdatePriceOfParts;
import admin.Parts.Quantity.PartsQuantityTable;
import admin.SkladExtinguisher.MainFrame_SkladNewExtinguisher;
import admin.Team.TeamTable;
import admin.extinguishingagent.ExtinguishingAgentDialog;
import run.JDialoger;
import utils.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AdminDialog extends MainPanel implements ActionListener {

	private JPasswordField passField = null;

	private String command = "";
	private final String PARTS_PRICES = "Цени на резервни части";
	private final String PARTS_QUANTITY = "Резервни части в наличност";
	private final String CLIENTS = "Клиенти";
	private final String WORK_PRICES = "Обслужване";
	private final String NEW_EXTINGUISHER_PRICE = "Нови Пожарогасители";
	private final String ARTIKUL_PRICE = "Наличност Артикули";
	private final String USERS = "Потребители";
	private final String EXTINGUISHING_AGENT = "Гасително Вещество";

	public AdminDialog() {
		this.setPreferredSize(new Dimension(400, 300));

		passField = new JPasswordField(12);
		passField.setEnabled(false);

		ButtonGroup bg = new ButtonGroup();

		JRadioButton parts_PriceRadioButton = new JRadioButton(PARTS_PRICES);
		parts_PriceRadioButton.setOpaque(false);
		parts_PriceRadioButton.setFont(getFONT());
		parts_PriceRadioButton.addActionListener(this);

		JRadioButton parts_QuantityRadioButton = new JRadioButton(PARTS_QUANTITY);
		parts_QuantityRadioButton.setOpaque(false);
		parts_QuantityRadioButton.setFont(getFONT());
		parts_QuantityRadioButton.addActionListener(this);

		JRadioButton editClientRadioButton = new JRadioButton(CLIENTS);
		editClientRadioButton.setOpaque(false);
		editClientRadioButton.setFont(getFONT());
		editClientRadioButton.addActionListener(this);

		JRadioButton work_PriceRadioButton = new JRadioButton(WORK_PRICES);
		work_PriceRadioButton.setOpaque(false);
		work_PriceRadioButton.setFont(getFONT());
		work_PriceRadioButton.addActionListener(this);

		JRadioButton new_ExtRadioButton = new JRadioButton(NEW_EXTINGUISHER_PRICE);
		new_ExtRadioButton.setOpaque(false);
		new_ExtRadioButton.setFont(getFONT());
		new_ExtRadioButton.addActionListener(this);

		JRadioButton artikulRadioButton = new JRadioButton(ARTIKUL_PRICE);
		artikulRadioButton.setOpaque(false);
		artikulRadioButton.setFont(getFONT());
		artikulRadioButton.addActionListener(this);



		JRadioButton usersRadioButton = new JRadioButton(USERS);
		usersRadioButton.setOpaque(false);
		usersRadioButton.setFont(getFONT());
		usersRadioButton.addActionListener(this);


		JRadioButton extinguishingAgentButton = new JRadioButton(EXTINGUISHING_AGENT);
		extinguishingAgentButton.setOpaque(false);
		extinguishingAgentButton.setFont(getFONT());
		extinguishingAgentButton.addActionListener(this);

		bg.add(parts_PriceRadioButton);
		bg.add(parts_QuantityRadioButton);
		bg.add(editClientRadioButton);
		bg.add(work_PriceRadioButton);
		bg.add(new_ExtRadioButton);
		bg.add(artikulRadioButton);
		bg.add(usersRadioButton);
        bg.add(extinguishingAgentButton);

		passField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				if (ke.getKeyChar() == KeyEvent.VK_ENTER) {
					if (checkAdminPassword(passField.getPassword())) {

						switch (command) {
							case PARTS_PRICES: {
								UpdatePriceOfParts update = new UpdatePriceOfParts();
								JDialoger jDialog = new JDialoger();
								jDialog.setContentPane(update);
								jDialog.setResizable(false);
								jDialog.setTitle("Цени на резервни части");
								jDialog.Show();

								break;
							}
							case PARTS_QUANTITY: {

								PartsQuantityTable quantity = new PartsQuantityTable();
								JDialoger jDialog = new JDialoger();
								jDialog.setTitle("Резервни части");
								jDialog.setResizable(false);
								jDialog.setContentPane(quantity);
								jDialog.Show();

								break;
							}
							case CLIENTS: {

								EditClientPanel editClient = new EditClientPanel();
								JDialoger jDialog = new JDialoger();
								jDialog.setContentPane(editClient);
								jDialog.setResizable(false);
								jDialog.setTitle("Клиенти (Редактиране) ");
								jDialog.Show();

								break;
							}
							case WORK_PRICES: {
								UpdateWorkPrice updateWorkPrice = new UpdateWorkPrice();
								JDialoger jDialog = new JDialoger();
								jDialog.setContentPane(updateWorkPrice);
								jDialog.setResizable(false);
								jDialog.setTitle("Цени на обслужване");
								jDialog.Show();

								break;
							}
							case NEW_EXTINGUISHER_PRICE: {
								// open frame
								MainFrame_SkladNewExtinguisher skladNewExtinguisher = new MainFrame_SkladNewExtinguisher(
										null);
								JDialoger jDialog = new JDialoger();
								jDialog.setContentPane(skladNewExtinguisher);
								jDialog.setResizable(false);
								jDialog.setTitle("Нови пожарогасители");
								jDialog.Show();

								break;
							}
							case ARTIKUL_PRICE: {
								AvailableArtikulsTable artikul = new AvailableArtikulsTable();
								JDialoger jDialog = new JDialoger();
								jDialog.setContentPane(artikul);
								jDialog.setResizable(false);
								jDialog.setTitle("Артикули");
								jDialog.Show();

								break;
							}

							case USERS: {
								TeamTable team = new TeamTable();
								JDialoger jDialog = new JDialoger();
								jDialog.setContentPane(team);
								jDialog.setResizable(false);
								jDialog.setTitle("Потребители");
								jDialog.Show();
								break;
							}
							case EXTINGUISHING_AGENT:{
								ExtinguishingAgentDialog extinguishingAgentDialog =
										new ExtinguishingAgentDialog();
								JDialoger jDialog = new JDialoger();
								jDialog.setContentPane(extinguishingAgentDialog);
								jDialog.setResizable(false);
								jDialog.setTitle("Гасително вещество");
								jDialog.Show();
								break;
							}
						}
					} else {
						JOptionPane.showMessageDialog(null, "Грешна парола!");
					}
				}
			}
		});
		JPanel basePanel = new JPanel();// GradientPanel();
		basePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		basePanel.setPreferredSize(new Dimension(400, 300));
		basePanel.setLayout(new GridLayout(10, 1));
		basePanel.setLocation((int) (this.getPreferredSize().getWidth() / 2),
				(int) (this.getPreferredSize().getHeight() / 2));
		JPanel helpPanel1 = new JPanel();
		helpPanel1.setOpaque(false);
		helpPanel1.setLayout(new FlowLayout(FlowLayout.LEFT));
		helpPanel1.add(passField);


		basePanel.add(helpPanel1);
		basePanel.add(extinguishingAgentButton);
		basePanel.add(parts_PriceRadioButton);
		basePanel.add(parts_QuantityRadioButton);
		basePanel.add(editClientRadioButton);
		basePanel.add(work_PriceRadioButton);
		basePanel.add(artikulRadioButton);
		basePanel.add(new_ExtRadioButton);
		basePanel.add(usersRadioButton);

		this.setBackground(basePanel.getBackground());
		this.add(basePanel);

	}

	private boolean checkAdminPassword(char[] password) {
		char[] pass = { 'п', 'о', 'ж', 'а', 'р', 'п', 'р', 'о', 'т', 'е', 'к',
				'т' };
		if (pass.length != password.length) {
			return false;
		}
		for (int ch = 0; ch < password.length; ch++) {
			if (password[ch] != pass[ch]) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		command = arg0.getActionCommand();
		passField.setEnabled(true);
		passField.requestFocusInWindow();
	}

}
