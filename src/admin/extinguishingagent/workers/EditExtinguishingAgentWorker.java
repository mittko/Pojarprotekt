package admin.extinguishingagent.workers;

import db.ExtinguishingAgent.ExtinguishingAgentDB;
import utils.MainPanel;

import javax.swing.*;
import java.awt.*;

public class EditExtinguishingAgentWorker extends SwingWorker {

    private final String agent;
    private final String brand;
    private final String batch;
    private final String fit;
    private final JDialog jd;
    public EditExtinguishingAgentWorker(String agent, String brand, String batch, String fit, JDialog jDialog) {
        this.agent = agent;
        this.brand = brand;
        this.batch = batch;
        this.fit = fit;
        this.jd = jDialog;
    }
    @Override
    public Integer doInBackground() throws Exception {
      int result =  -1;
      try {
          result = ExtinguishingAgentDB.editExtinguishingAgentTable(MainPanel.EXTINGUISHING_AGENT_TABLE,
                  agent, brand, batch, fit);
      } finally {
          final int finalResult = result;
          SwingUtilities.invokeLater(new Runnable() {
              @Override
              public void run() {
                  jd.setCursor(Cursor.getDefaultCursor());
                  if(finalResult > 0) {
                  //    JOptionPane.showMessageDialog(null,"Данните са записани успешно !");
                  }
              }
          });
      }
      return result;
    }
}
