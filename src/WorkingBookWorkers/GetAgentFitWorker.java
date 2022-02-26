package WorkingBookWorkers;

import db.ExtinguishingAgent.ExtinguishingAgentDB;
import utility.MainPanel;

import javax.swing.*;

public class GetAgentFitWorker extends SwingWorker {
    private String agent;
    public GetAgentFitWorker(String agent) {
        this.agent = agent;
    }
    @Override
    public String doInBackground()  {
        return ExtinguishingAgentDB.getFitExtinguishingAgent(MainPanel.EXTINGUISHING_AGENT_TABLE,agent);
    }
}
