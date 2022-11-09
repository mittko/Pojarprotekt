package WorkingBook;

import NewExtinguisher.SwingWorkers.StickerPrinterWorkerOldVersion;
import mydate.MyGetDate;
import utility.LoadIcon;
import utility.MainPanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StickerJDialogOldVersion extends MainPanel {
    private StickerButton TO_Button;
    private StickerButton P_Button;
    private StickerButton HI_Button;
    private JPanel buttonPanel;
    private MainPanel mp;
    private JDialog parentDialog;

    public StickerJDialogOldVersion(final JDialog parentDialog, final boolean isTO, final boolean isP, final boolean isHI, final String barcod) {
        this.TO_Button = null;
        this.P_Button = null;
        this.HI_Button = null;
        this.buttonPanel = null;
        this.mp = null;
        this.parentDialog = parentDialog;
        this.mp = new MainPanel();
        this.buttonPanel = new JPanel();
        if (isTO) {
            final JPanel helpPanel1 = new JPanel();
            helpPanel1.setLayout(new BorderLayout());
            final JPanel labelPanel = new JPanel();
            final JLabel TO_Label = new JLabel("“≈’Õ»◊≈— Œ Œ¡—À”∆¬¿Õ≈");
            (this.TO_Button = new StickerButton(isTO, parentDialog, barcod, MainPanel.TO)).setActionCommand("button1");
            this.TO_Button.setIcon(new LoadIcon().setIcons("Stiker2.jpg"));
            final int w = this.TO_Button.getIcon().getIconWidth();
            final int h = this.TO_Button.getIcon().getIconHeight();
            this.TO_Button.setPreferredSize(new Dimension(w, h));
            labelPanel.add(TO_Label);
            final JPanel buttonPanel1 = new JPanel();
            buttonPanel1.add(this.TO_Button);
            helpPanel1.add(labelPanel, "North");
            helpPanel1.add(buttonPanel1, "Center");
            this.buttonPanel.add(helpPanel1);
        }
        if (isHI) {
            final JPanel helpPanel2 = new JPanel();
            helpPanel2.setLayout(new BorderLayout());
            final JPanel labelPanel2 = new JPanel();
            final JLabel TO_P_HI_Label = new JLabel("’»ƒ–Œ—“¿“»◊ÕŒ »«œ»“¬¿Õ≈");
            (this.HI_Button = new StickerButton(isHI, parentDialog, barcod, MainPanel.HI)).setPreferredSize(new Dimension(300, 200));
            this.HI_Button.setActionCommand("button3");
            this.HI_Button.setIcon(new LoadIcon().setIcons("Stiker2.jpg"));
            final int w2 = this.HI_Button.getIcon().getIconWidth();
            final int h2 = this.HI_Button.getIcon().getIconHeight();
            this.HI_Button.setPreferredSize(new Dimension(w2, h2));
            labelPanel2.add(TO_P_HI_Label);
            final JPanel buttonPanel2 = new JPanel();
            buttonPanel2.add(this.HI_Button);
            helpPanel2.add(labelPanel2, "North");
            helpPanel2.add(buttonPanel2, "Center");
            this.buttonPanel.add(helpPanel2);
        }
        this.add(this.buttonPanel);
        this.setLayout(new FlowLayout());
    }

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                final JFrame jf = new JFrame();
                jf.setSize(800, 700);
                jf.setDefaultCloseOperation(3);
                jf.setVisible(true);
            }
        });
    }

    private boolean isButtonsEnabled() {
        return (this.TO_Button != null && this.TO_Button.isEnabled()) || (this.HI_Button != null && this.HI_Button.isEnabled());
    }

    class StickerButton extends JButton implements ActionListener
    {
        boolean isPrintable;
        Border defaultBorder;
        Border redBorder;
        JDialog parent;
        String barcod;
        String doing;

        public StickerButton(final boolean isPrintable, final JDialog parent, final String barcod, final String doing) {
            this.defaultBorder = null;
            this.redBorder = null;
            this.parent = parent;
            this.barcod = barcod;
            this.doing = doing;
            this.defaultBorder = this.getBorder();
            this.redBorder = BorderFactory.createLineBorder(Color.red, 5);
            this.addActionListener(this);
            this.setEnabled(true);
            this.setBorder(this.redBorder);
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            this.setEnabled(false);
            this.setBorder(this.defaultBorder);
            String nextDate = null;
            if (!doing.equals(MainPanel.HI)) {
                nextDate = MyGetDate.getDateAfterToday(365);
            }
            else {
                nextDate = MyGetDate.getDateAfterToday(1825);
            }
            final StickerPrinterWorkerOldVersion sp = new
                    StickerPrinterWorkerOldVersion(this.doing, this.barcod, nextDate);
            sp.execute();
            if (!StickerJDialogOldVersion.this.isButtonsEnabled()) {
                this.parent.dispose();
            }
        }
    }
}
