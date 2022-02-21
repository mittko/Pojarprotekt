package admin.SkladExtinguisher.Workers;

import javax.swing.SwingWorker;

import db.NewExtinguisher.NewExtinguishers_DB;

public class GetCurrentExtPriceWorker extends SwingWorker {

	private String type;
	private String wheight;
	private String category;
	private String brand;

	public GetCurrentExtPriceWorker(String type, String wheight,
			String category, String brand) {
		super();
		this.type = type;
		this.wheight = wheight;
		this.category = category;
		this.brand = brand;
	}

	@Override
	public String doInBackground() {
		// TODO Auto-generated method stub
		String maxPrice = NewExtinguishers_DB.getCurrPriceForNewExtinguishers(
				type, wheight, category, brand);
		return maxPrice;
	}

}
