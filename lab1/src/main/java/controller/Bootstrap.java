package controller;


public class Bootstrap {
	private String registerServerUrl;
	private String brands;
	private String model;
	private String year;
	@Override
	public String toString() {
		return "brands: " + brands
				+ " model: " + model
				+ " year: " + year
				+ " registerurl: " + registerServerUrl;
	}
	public String getBrands() {
		return brands;
	}
	public void setBrands(String brands) {
		this.brands = brands;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public Bootstrap() {
		
	}
	public String getRegisterServerUrl() {
		return registerServerUrl;
	}
	public void setRegisterServerUrl(String registerServerUrl) {
		this.registerServerUrl = registerServerUrl;
	}

}


