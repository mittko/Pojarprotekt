package admin.Doing;

import Document.TextFieldLimit;
import admin.Doing.Worker.changePriceOfDoingWorker;
import admin.Doing.Worker.seePriceOfDoingWorker;
import utility.MainPanel;
import utility.TooltipButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class UpdateWorkPrice extends MainPanel {
	public static JTextField viewField = null;

	public UpdateWorkPrice() {
		// this.setLayout(new BorderLayout());

		JPanel centerPanel = new JPanel();

		centerPanel.setOpaque(false);

		final JComboBox<String> workCombo = new JComboBox<String>(new String[] {
				"Обслужване", "ТО", "П", "ХИ" });
		workCombo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
				viewField.setText("");
			}

		});
		viewField = new JTextField(3);
		viewField.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
		viewField.setForeground(Color.red);
		viewField.setEditable(false);
		viewField.setPreferredSize(new Dimension(50, 35));
		TooltipButton viewButton = new TooltipButton("Виж");

		viewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String work = workCombo.getSelectedItem().toString();
				if (work.isEmpty() || work.equals("Обслужване")) {
					return;
				}
				JDialog jd = (JDialog) SwingUtilities
						.getWindowAncestor(UpdateWorkPrice.this);
				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				seePriceOfDoingWorker vw = new seePriceOfDoingWorker(jd, work);
				vw.execute();
			}

		});

		final JTextField updateField = new JTextField(3);
		updateField.setPreferredSize(new Dimension(50, 35));
		updateField.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
		updateField.setForeground(Color.red);
		updateField.setDocument(new TextFieldLimit(4));
		TooltipButton updateButton = new TooltipButton("Промени");

		updateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (workCombo.getSelectedItem().equals("Обслужване")) {
					return;
				}
				if (updateField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Не е въведена стойност!");
					return;
				}
				double newPrice = 0.0;
				try {
					newPrice = Double.parseDouble(updateField.getText());
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Невалидни данни!");
					return;
				}
				int yes_no = JOptionPane.showOptionDialog(null,
						"Желаете ли да съхраните въведените данни?", "",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, new String[] {
								"Да", "Не" }, // this is the array
						"default");
				if (yes_no == 0) {

					JDialog jd = (JDialog) SwingUtilities
							.getWindowAncestor(UpdateWorkPrice.this);
					jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
					changePriceOfDoingWorker uw = new changePriceOfDoingWorker(
							jd, workCombo.getSelectedItem().toString(),
							newPrice);
					uw.execute();
				}
			}

		});

		centerPanel.add(workCombo);
		centerPanel.add(viewField);
		centerPanel.add(viewButton);
		centerPanel.add(updateField);
		centerPanel.add(updateButton);
		// centerPanel.setPreferredSize(new Dimension(
		// (int)centerPanel.getPreferredSize().getWidth(),200));
		JPanel helpPanel1 = new JPanel();
		// helpPanel1.setPreferredSize(new
		// Dimension((int)centerPanel.getPreferredSize().getWidth(),100));
		helpPanel1.setOpaque(false);

		JPanel basePanel = new JPanel();// GradientPanel();
		basePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		basePanel.setLayout(new BorderLayout());

		basePanel.add(helpPanel1, BorderLayout.NORTH);
		basePanel.add(centerPanel, BorderLayout.CENTER);

		this.add(basePanel);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				UpdateWorkPrice uwp = new UpdateWorkPrice();
				JFrame jf = new JFrame();
				jf.add(uwp);
				jf.pack();
				jf.setLocation(400, 100);
				jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				jf.setVisible(true);
			}

		});
	}

}
