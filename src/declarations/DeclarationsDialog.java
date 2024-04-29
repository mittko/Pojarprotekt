package declarations;

import pdf.OpenPDFDocument;
import utils.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeclarationsDialog extends MainPanel implements ActionListener {

	public static final String dir = DOCUMENTS_PATH
			+ "\\declarations\\";

	public DeclarationsDialog() {

		ButtonGroup bg = new ButtonGroup();

		String POJARNI_KRANOVE = "œŒ∆¿–Õ»  –¿ÕŒ¬≈";
		JRadioButton rb1 = new JRadioButton(POJARNI_KRANOVE);
		rb1.setOpaque(false);
		rb1.setFont(getFONT());
		rb1.addActionListener(this);

		String POJAROIZVESTITELNI_SISTEMI = "œŒ∆¿–Œ»«¬≈—“»“≈ÀÕ» —»—“≈Ã»";
		JRadioButton rb2 = new JRadioButton(POJAROIZVESTITELNI_SISTEMI);
		rb2.setOpaque(false);
		rb2.setFont(getFONT());
		rb2.addActionListener(this);

		String POJAROGASITELNI_INSTALACII = "œŒ∆¿–Œ√¿—»“≈ÀÕ» »Õ—“¿À¿÷»…";
		JRadioButton rb3 = new JRadioButton(POJAROGASITELNI_INSTALACII);
		rb3.setOpaque(false);
		rb3.setFont(getFONT());
		rb3.addActionListener(this);

		String SISTEMI_OTVEVDANE_DIM_TOPLINA = "—»—“≈Ã» «¿ Œ“¬≈∆ƒ¿Õ≈ Õ¿ ƒ»Ã » “ŒœÀ»Õ¿";
		JRadioButton rb4 = new JRadioButton(SISTEMI_OTVEVDANE_DIM_TOPLINA);
		rb4.setOpaque(false);
		rb4.setFont(getFONT());
		rb4.addActionListener(this);

		String AVARIINO_EVAKUACIONNO_OSVETLENIE = "¿¬¿–»…ÕŒ ≈¬¿ ”¿÷»ŒÕÕŒ Œ—¬≈“À≈Õ»≈";
		JRadioButton rb5 = new JRadioButton(AVARIINO_EVAKUACIONNO_OSVETLENIE);
		rb5.setOpaque(false);
		rb5.setFont(getFONT());
		rb5.addActionListener(this);

		String POJAROUSTOICIVI_VRATI = "œŒ∆¿–Œ”—“Œ…◊»¬» ¬–¿“»";
		JRadioButton rb6 = new JRadioButton(POJAROUSTOICIVI_VRATI);
		rb6.setOpaque(false);
		rb6.setFont(getFONT());
		rb6.addActionListener(this);

		String ANTIPANIK_BRAVI = "¿Õ“»œ¿Õ»  ¡–¿¬»";
		JRadioButton rb7 = new JRadioButton(ANTIPANIK_BRAVI);
		rb7.setOpaque(false);
		rb7.setFont(getFONT());
		rb7.addActionListener(this);


		String EXTINGUISHING_AGENT = "";
		JRadioButton extinguishingAgentButton = new JRadioButton(EXTINGUISHING_AGENT);
		extinguishingAgentButton.setOpaque(false);
		extinguishingAgentButton.setFont(getFONT());
		extinguishingAgentButton.addActionListener(this);

		bg.add(rb1);
		bg.add(rb2);
		bg.add(rb3);
		bg.add(rb4);
		bg.add(rb5);
		bg.add(rb6);
		bg.add(rb7);

		JPanel basePanel = new JPanel();
		basePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		basePanel.setLayout(new GridLayout(7, 1));
		basePanel.setLocation((int) (this.getPreferredSize().getWidth() / 2),
				(int) (this.getPreferredSize().getHeight() / 2));

		basePanel.add(rb1);
		basePanel.add(rb2);
		basePanel.add(rb3);
		basePanel.add(rb4);
		basePanel.add(rb5);
		basePanel.add(rb6);
		basePanel.add(rb7);

		this.setBackground(basePanel.getBackground());
		this.add(basePanel);

	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		String command = arg0.getActionCommand();
		OpenPDFDocument.pdfRunner(dir + command +".pdf");
	}

}
