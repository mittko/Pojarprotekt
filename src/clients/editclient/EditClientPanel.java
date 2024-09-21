package clients.editclient;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.Period;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import http.RequestCallback2;
import http.client.GetClientService;
import models.Firm;
import run.JDialoger;
import utils.ClientsListComboBox2;
import utils.LoadIcon;
import utils.MainPanel;
import utils.TooltipButton;
import clients.workers.GetDataForClientWorker;
import clients.workers.RemoveClientWorker;

public class EditClientPanel extends MainPanel {

	private final ImageIcon on = null;
	private final ImageIcon off = null;
	private boolean on_off = false;

	public static ClientsListComboBox2 clientCombo2 = null;
	private TooltipButton viewDataButton = null;
	private TooltipButton switchButton = null;
	private String DESTINATION = FIRM;
	private String NAME = "firm";

	public EditClientPanel() {

		EditFirm editFirm = new EditFirm();
		editFirm.setOpaque(false);

		EditPerson editPerson = new EditPerson();

		editPerson.setOpaque(false);

		JPanel panel = new JPanel();// GradientPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		panel.setBackground(panel.getBackground());// (Color.decode("0xDACB6D"));
													// // DACB6D,F38B5D,FDC251

		panel.setLayout(new BorderLayout());

		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension((int) (editFirm.getPreferredSize()
				.getWidth()), (int) (this.HEIGHT * 0.1)));

		final JPanel center = new JPanel();
		center.setOpaque(false);

		final CardLayout card = new CardLayout();

		center.setLayout(card);

		center.add(editFirm);
		center.add(editPerson);

		switchButton = new TooltipButton("");
		//switchButton.setVisible(false);
		switchButton.setPreferredSize(new Dimension((int) (north
				.getPreferredSize().getWidth() * 0.09), (int) (north
				.getPreferredSize().getHeight() * 0.82)));
		switchButton.setAutoSizedIcon(switchButton, new LoadIcon().setIcons("off2.png"));
		switchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		switchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				card.next(center);
				if (on_off) {
					// switchButton.setIcon(new LoadIcon().setIcons("off.png")) ;
					switchButton.setAutoSizedIcon(switchButton,
							new LoadIcon().setIcons("off2.png"));
					DESTINATION = FIRM;
					NAME = "firm";
				} else {
					// switchButton.setIcon(new LoadIcon().setIcons("on.png"));
					switchButton.setAutoSizedIcon(switchButton,
							new LoadIcon().setIcons("on2.png"));
					DESTINATION = PERSON;
					NAME = "name";
				}
				on_off = !on_off;
				clientCombo2.setSelectedItem("");
			}

		});

		clientCombo2 = new ClientsListComboBox2();
		clientCombo2.setPreferredSize(new Dimension((int) (north
				.getPreferredSize().getWidth() * 0.4), (int) (north
				.getPreferredSize().getHeight() * 0.5)));
		clientCombo2.setBorder(BorderFactory.createLoweredBevelBorder());
		clientCombo2.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
				viewDataButton.setEnabled(arg0.getItem().toString().length() > 0);
			}

		});

		viewDataButton = new TooltipButton("");
		viewDataButton.setPreferredSize(new Dimension((int) (north
				.getPreferredSize().getWidth() * 0.09), (int) (north
				.getPreferredSize().getHeight() * 0.82)));
		viewDataButton.setToolTipText(getHTML_Text("��� �������"));
		viewDataButton.setAutoSizedIcon(viewDataButton, new LoadIcon().setIcons("lupa2.png"));

		viewDataButton.setEnabled(false);
		viewDataButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		viewDataButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JDialog jd = (JDialoger) SwingUtilities
						.getWindowAncestor(EditClientPanel.this);
				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));

				GetClientService  service = new GetClientService();
				service.getFirm(clientCombo2.getSelectedItem().toString(), new RequestCallback2() {
					@Override
					public <T> void callback(T t) {
						Firm firm = (Firm) t;
						if(DESTINATION.equals(FIRM)) {
							EditFirm.firm.setText(firm.getFirm());
							EditFirm.address.setText(firm.getAddress());
							EditFirm.eik.setText(firm.getEik());
							EditFirm.city.setText(firm.getCity());
							EditFirm.mol.setText(firm.getMol());
							EditFirm.email.setText(firm.getEmail());
							EditFirm.person.setText(firm.getPerson());
							EditFirm.telPerson.setText(firm.getTelPerson());
							EditFirm.bank.setText(firm.getBank());
							EditFirm.BIC.setText(firm.getBic());
							EditFirm.IBAN.setText(firm.getIban());
							EditFirm.discountField.setText(String.valueOf(firm.getDiscount()));
							EditFirm.incorrectClientCheckBox.setSelected(firm.getIncorrect_person().equals("��"));
							EditFirm.registrationVatCheckBox.setSelected(firm.getVat_registration().equals("��"));
						} else {
							EditPerson.name.setText(firm.getFirm());
							EditPerson.tel.setText(firm.getTelPerson());
							EditPerson.discountField.setText(String.valueOf(firm.getDiscount()));
							EditFirm.incorrectClientCheckBox.setSelected(firm.getIncorrect_person().equals("��"));
						}

						jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					}
				});

			}

		});

		TooltipButton removeClientButton = new TooltipButton("");
		removeClientButton.setEnabled(ACCESS_MENU[5]); // HIDDEN MENU !!!!!
		removeClientButton.setToolTipText(getHTML_Text("������ ������"));
		removeClientButton.setPreferredSize(new Dimension((int) (north
				.getPreferredSize().getWidth() * 0.09), (int) (north
				.getPreferredSize().getHeight() * 0.82)));
		removeClientButton.setAutoSizedIcon(removeClientButton,
				new LoadIcon().setIcons("�����.gif"));

		removeClientButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		removeClientButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (clientCombo2.getSelectedItem().toString().length() == 0) {
					JOptionPane.showMessageDialog(null, "�� � ������ ������ !");
					return;
				}
				int yes_no = JOptionPane.showOptionDialog(null,
						"�������� �� ������ �� �������� ������� ?", "",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, new String[] {
								"��", "��" }, // this is the array
						"default");
				if (yes_no != 0) {
					return;
				}
				JDialog jd = (JDialoger) SwingUtilities
						.getWindowAncestor(EditClientPanel.this);
				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));

				GetClientService service = new GetClientService();
				service.deleteClient(clientCombo2.getSelectedItem().toString(), new RequestCallback2() {
					@Override
					public <T> void callback(T t) {
						jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
						int delete = (Integer)t;
						if(delete > 0) {
							JOptionPane.showMessageDialog(null,"�������� � ������ �������");
						} else {
							JOptionPane.showMessageDialog(null,"�� � ������� ����� ������");
						}
					}
				});


			}

		});
		north.setOpaque(false);
		north.add(switchButton);
		north.add(removeClientButton);
		north.add(viewDataButton);
		north.add(clientCombo2);

		panel.add(north, BorderLayout.NORTH);
		panel.add(center, BorderLayout.CENTER);
		this.add(panel);

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EditClientPanel edcp = new EditClientPanel();
		JDialoger jDialog = new JDialoger();
		jDialog.setContentPane(edcp);
		jDialog.Show();

	}

	public static boolean isOnlyDigits(String text) {
		if (text.isEmpty()) {
			text = "0";
		}
		String regex = "\\d+"; // [0-9]+
		return text.matches(regex);
	}

}
