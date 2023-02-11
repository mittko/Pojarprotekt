package admin.Parts.Quantity;

import admin.Parts.Quantity.Worker.UpdatePartQuantityWorker;
import utils.EditableField;
import utils.MainPanel;
import utils.TooltipButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddPartDialog extends MainPanel {

	private JPanel basePanell;
	private JComboBox<String> comboParts;
	private EditableField quantityField;
	private TooltipButton saveButton;
	
	public AddPartDialog() {
		comboParts = new JComboBox<String>(PartsQuantityTable.PARTS);
	//	comboParts.setEditable(true);
		
		quantityField = new EditableField("Количество",10);
		quantityField.setPreferredSize(new Dimension(50,getFontSize() * 2));
		saveButton = new TooltipButton("Добави количество");
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(quantityField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Не е въведено количество!");
					return;
				}
				String part =  comboParts.getSelectedItem().toString();
				int quantity = -1;
				try {
					quantity = Integer.parseInt(quantityField.getText());
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Грешен формат на стойност");
					return;
				}
				JDialog jd =  (JDialog)SwingUtilities.getWindowAncestor(AddPartDialog.this);
				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				
				 UpdatePartQuantityWorker  qw = new UpdatePartQuantityWorker(
						 part,
						 quantity,jd
						);
				 try {
					int update = qw.doInBackground();
					if(update > 0) {
						JOptionPane.showMessageDialog(null, "Промените са записани успешно!");
					} else {
						JOptionPane.showMessageDialog(null, "Неуспешна операция!");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		GridBagLayout gridLayout = new GridBagLayout();
		
		basePanell = new JPanel();
		basePanell.setBorder(BorderFactory.createLineBorder(Color.black));
		basePanell.setLayout(gridLayout);
		
		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		 
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.gridx = 0;
		gbc2.gridy = 1;
		gbc2.gridwidth = 20;
		
		
		JPanel helpPanel = new JPanel();
		helpPanel.add(quantityField);
		helpPanel.add(saveButton);
		
		basePanell.add(comboParts,gbc1);
		basePanell.add(helpPanel,gbc2);
		this.add(basePanell);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
   
	}

}
