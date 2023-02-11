package NewExtinguisher.SwingWorkers;

import JPrinter.PrintSticker.PrintSticker;
import generators.GenerateBarcod;
import utils.MainPanel;

import javax.swing.*;

public class StickerPrinterWorkerOldVersion extends SwingWorker {
    private String doing;
    private String barcod;
    private String nextDate;

    public StickerPrinterWorkerOldVersion(final String doing, final String barcod, final String nextDate) {
        this.doing = null;
        this.barcod = null;
        this.nextDate = null;
        this.doing = doing;
        this.barcod = barcod;
        this.nextDate = nextDate;
    }

    @Override
    protected Object doInBackground() throws Exception {
        final boolean pdf = GenerateBarcod.generateBarcodOnStickerAsPDF(this.barcod, this.barcod + this.doing + "-стикер.pdf", this.nextDate);
        if (pdf) {
            PrintSticker.printPDF(MainPanel.BARCODE_PDF_PATH, this.barcod + this.doing + "-стикер.pdf");
        }
        return null;
    }
}
