package admin.Artikul.Workers;

import db.Artikul.Artikuli_DB;
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
       boolean availableArtikuls = Artikuli_DB.editArtikulName(MainPanel.AVAILABLE_ARTIKULS,oldName,newName) > 0;
       boolean deliveryArtikuls = Artikuli_DB.editArtikulName(MainPanel.DELIVERY_ARTIKULS, oldName, newName) > 0;
       return availableArtikuls && deliveryArtikuls;

    }
}
