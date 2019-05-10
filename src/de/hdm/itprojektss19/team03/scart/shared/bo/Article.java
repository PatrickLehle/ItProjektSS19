package de.hdm.itprojektss19.team03.scart.shared.bo;

public class Article extends BusinessObject {

	private static final long serialVersionUID = 1L;
	private String name = "";
	private int quantity = 1;
	private Retailer retailer = new Retailer();
	private Unit unit = new Unit();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Retailer getRetailer() {
		return retailer;
	}

	public void setRetailer(Retailer retailer) {
		this.retailer = retailer;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

}
