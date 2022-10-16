package invoice;

import invoice.worker.PrintAcquittancePdfWorker;
import invoice.worker.PrintInvoicePdfWorker;
import invoice.worker.PrintProformPdfWorker;
import generators.InvoiceGenerator;
import utility.ChoisePrinterDialog;
import utility.MainPanel;

import javax.print.PrintService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class PrintInvoiceDialog extends JPanel {

	private JButton printButton;
	private String pdfCommand = null;
	private final String INVOICE_PDF = "generate invoice pdf";
	private final String PROFORM_PDF = "generate proform pdf";
	private final String ACQIUTTANCE_PDF = "generate acquittance pdf";

	public PrintInvoiceDialog(final DefaultTableModel dftm,
			final String invoiceNumber, final String proformNumber,
			final String acquittanceNumber, final String currentClient,
			final String datePdf,final String invoiceName,  final double sum, final String payment,
			boolean isInvoice, boolean isProform, boolean isAcquittance) {

		// varaibles to create pdf

		InvoiceGenerator ig = new InvoiceGenerator();

		JPanel basePanel = new JPanel();
		basePanel.setBorder(BorderFactory.createRaisedBevelBorder());
		basePanel.setLayout(new GridBagLayout());

		ButtonGroup bg = new ButtonGroup();

		JRadioButton invoicePdf = new JRadioButton("  Фактура");
		Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
		invoicePdf.setCursor(HAND_CURSOR);
		invoicePdf.setPreferredSize(new Dimension(200, 50));
		invoicePdf.setOpaque(false);
		invoicePdf.setFont(MainPanel.getFONT());
		invoicePdf.setEnabled(isInvoice);
		invoicePdf.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				PrintInvoiceDialog.this.printButton.setEnabled(true);
				pdfCommand = INVOICE_PDF;
			}

		});

		JRadioButton proformPdf = new JRadioButton("  Про-форма");
		proformPdf.setCursor(HAND_CURSOR);
		proformPdf.setPreferredSize(new Dimension(200, 50));
		proformPdf.setOpaque(false);
		proformPdf.setFont(MainPanel.getFONT());
		proformPdf.setEnabled(isProform);
		proformPdf.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				PrintInvoiceDialog.this.printButton.setEnabled(true);
				pdfCommand = PROFORM_PDF;
			}

		});

		JRadioButton acquittancePdf = new JRadioButton("  Стокова Разписка");
		acquittancePdf.setVisible(MainPanel.ACCESS_MENU[MainPanel.ACCESS_ACQUITTANCE]);
		acquittancePdf.setCursor(HAND_CURSOR);
		acquittancePdf.setPreferredSize(new Dimension(200, 50));
		acquittancePdf.setOpaque(false);
		acquittancePdf.setEnabled(isAcquittance);
		acquittancePdf.setFont(MainPanel.getFONT());

		acquittancePdf.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				PrintInvoiceDialog.this.printButton.setEnabled(true);
				pdfCommand = ACQIUTTANCE_PDF;
			}

		});
		JLabel questionLabel = new JLabel(" Какъв документ желаете да принтирате ?  ");
		questionLabel.setOpaque(false);

		printButton = new JButton("Принтирай");
		printButton.setCursor(HAND_CURSOR);
		printButton.setEnabled(false);

		printButton.addActionListener(new ActionListener() {

			// private ArrayList<String> clientInfo = null;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				JDialog jd = (JDialog) SwingUtilities
						.getWindowAncestor(PrintInvoiceDialog.this);

				switch (pdfCommand) {
					case INVOICE_PDF: {
						PrintService ps = ChoisePrinterDialog.showPrinters();
						if (ps == null) {
							return;
						}
						PrintInvoicePdfWorker printInvoicePdf = new PrintInvoicePdfWorker(
								dftm, currentClient, invoiceNumber, datePdf,invoiceName,
								sum, payment, ps, jd);
						printInvoicePdf.execute();
						jd.dispose();
						break;
					}
					case PROFORM_PDF: {
						PrintService ps = ChoisePrinterDialog.showPrinters();
						if (ps != null) {

							PrintProformPdfWorker printProformPdf = new PrintProformPdfWorker(
									dftm, currentClient, proformNumber, datePdf,
									sum, payment, ps, jd);
							printProformPdf.execute();

							// bye
							jd.dispose();

						}
						break;
					}
					case ACQIUTTANCE_PDF: {
						PrintService ps = ChoisePrinterDialog.showPrinters();
						if (ps != null) {

							PrintAcquittancePdfWorker printAcquittance = new PrintAcquittancePdfWorker(
									dftm, currentClient, acquittanceNumber,
									datePdf, sum, ps, jd);
							printAcquittance.execute();

							// bye
							jd.dispose();

						}
						break;
					}
				}

			}

		});
		bg.add(invoicePdf);
		bg.add(proformPdf);
		bg.add(acquittancePdf);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		basePanel.add(questionLabel, gbc);

		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.fill = GridBagConstraints.HORIZONTAL;
		gbc2.gridx = 0;
		gbc2.gridy = 1;
		gbc2.gridwidth = 2;
		gbc2.insets = new Insets(5, 0, 5, 5);

		basePanel.add(invoicePdf, gbc2);

		GridBagConstraints gbc3 = new GridBagConstraints();
		gbc3.fill = GridBagConstraints.HORIZONTAL;
		gbc3.gridx = 0;
		gbc3.gridy = 2;
		gbc3.gridwidth = 2;
		gbc3.insets = new Insets(15, 0, 5, 5);

		basePanel.add(proformPdf, gbc3);

		GridBagConstraints gbc4 = new GridBagConstraints();
		gbc4.fill = GridBagConstraints.HORIZONTAL;
		gbc4.gridx = 0;
		gbc4.gridy = 3;
		gbc4.gridwidth = 1;
		gbc4.insets = new Insets(5, 0, 5, 5);

		basePanel.add(acquittancePdf, gbc4);

		GridBagConstraints gbc5 = new GridBagConstraints();
		gbc5.fill = GridBagConstraints.HORIZONTAL;
		gbc5.gridx = 1;
		gbc5.gridy = 3;
		gbc5.gridwidth = 1;
		gbc5.insets = new Insets(5, 0, 5, 5);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.add(printButton);

		basePanel.add(buttonPanel, gbc5);

		this.add(basePanel);

	}
}
