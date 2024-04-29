package admin.extinguishingagent.workers;

import db.ExtinguishingAgent.ExtinguishingAgentDB;
import utils.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LoadExtinguishingTableValues extends SwingWorker {

    private final JDialog jDialog;
    public LoadExtinguishingTableValues(JDialog jDialog) {
        this.jDialog = jDialog;
    }
    @Override
    public ArrayList<Object[]> doInBackground() throws Exception {
        ArrayList<Object[]> result = null;
        try {
          result = ExtinguishingAgentDB.getExtinguishingTableValues(MainPanel.EXTINGUISHING_AGENT_TABLE);
        }finally {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    jDialog.setCursor(Cursor.getDefaultCursor());
                }
            });
        }
        return result;
    }

}
