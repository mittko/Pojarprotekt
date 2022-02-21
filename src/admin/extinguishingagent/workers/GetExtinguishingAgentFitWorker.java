package admin.extinguishingagent.workers;

import db.ExtinguishingAgent.ExtinguishingAgentDB;
import utility.MainPanel;

import javax.swing.*;

public class GetExtinguishingAgentFitWorker extends SwingWorker {

    private String agent;
    public GetExtinguishingAgentFitWorker(String agent) {
        this.agent = agent;
    }
    @Override
    public String doInBackground() throws Exception {
        return ExtinguishingAgentDB.getFitExtinguishingAgent(MainPanel.EXTINGUISHING_AGENT_TABLE, agent);
    }
}
