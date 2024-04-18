package admin.àrtikul.Workers;

import db.àrtikul.Artikuli_DB;
import utils.MainPanel;

import javax.swing.*;

public class RenameArtikulNameWorker extends SwingWorker {
    private final String oldName;
    private final String newName;
    public RenameArtikulNameWorker(String oldName, String newName) {
        this.oldName = oldName;
        this.newName = newName;
    }
    @Override
    public Boolean doInBackground() throws Exception {
       boolean availableArtikuls = Artikuli_DB.editArtikulName(MainPanel.AVAILABLE_ARTIKULS,"artikul",oldName,newName) > 0;
       boolean deliveryArtikuls = Artikuli_DB.editArtikulName(MainPanel.DELIVERY_ARTIKULS,"artikul", oldName, newName) > 0;
       boolean invoiceArtikuls = Artikuli_DB.editArtikulName(MainPanel.INVOICE_CHILD,"artikul",oldName,newName) > 0;
       boolean proformArtikuls = Artikuli_DB.editArtikulName(MainPanel.PROFORM_CHILD,"make",oldName,newName) > 0;
       boolean acquitanceArtikuls = Artikuli_DB.editArtikulName(MainPanel.ACQUITTANCE_CHILD,"artikul",oldName,newName) > 0;
       return availableArtikuls || deliveryArtikuls || invoiceArtikuls || proformArtikuls || acquitanceArtikuls;

    }
}
