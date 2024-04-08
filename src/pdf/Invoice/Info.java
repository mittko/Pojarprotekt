package pdf.Invoice;

class Info {
	public String obslujvane;
	public String measure;
	public float quantity;
	public float price;
	public float value;
	 public Info(String obslujvane,String measure,float quantity,float price, float value) {
		 this.obslujvane = obslujvane;
		 this.measure = measure;
		 this.quantity = quantity;
		 this.price = price;
		 this.value = value;
	 };
}
