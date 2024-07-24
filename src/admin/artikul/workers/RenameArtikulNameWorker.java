package admin.artikul.workers;

import db.artikul.Artikuli_DB;
import utils.MainPanel;

import javax.swing.*;

public class RenameArtikulNameWorker extends SwingWorker {
    private final String oldName;
    private final String newName;

    private String skladTable;
    public RenameArtikulNameWorker(String dbTable, String oldName, String newName) {
        this.skladTable = dbTable;
        this.oldName = oldName;
        this.newName = newName;
    }
    @Override
    public Boolean doInBackground() throws Exception {
       boolean availableArtikuls = Artikuli_DB.editArtikulName(skladTable,"artikul",oldName,newName) > 0;
       boolean deliveryArtikuls = Artikuli_DB.editArtikulName(MainPanel.DELIVERY_ARTIKULS,"artikul", oldName, newName) > 0;
       boolean invoiceArtikuls = Artikuli_DB.editArtikulName(MainPanel.INVOICE_CHILD,"artikul",oldName,newName) > 0;
       boolean proformArtikuls = Artikuli_DB.editArtikulName(MainPanel.PROFORM_CHILD,"make",oldName,newName) > 0;
       boolean acquitanceArtikuls = Artikuli_DB.editArtikulName(MainPanel.ACQUITTANCE_CHILD,"artikul",oldName,newName) > 0;
       return availableArtikuls || deliveryArtikuls || invoiceArtikuls || proformArtikuls || acquitanceArtikuls;

    }
}
