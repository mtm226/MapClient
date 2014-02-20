package mapPack;

public class Map {

	private double low_x, low_y, high_x, high_y;
	
	//T‰ll‰ yll‰pidet‰‰n kartan voimassa olevat layerit
	private String tasot;

	// Konstruktori
	public Map(double low_x, double low_y, double high_x, double high_y) {
		this.low_x = low_x;
		this.low_y = low_y;
		this.high_x = high_x;
		this.high_y = high_y;
	}
	
	public double getLow_x() {
		return low_x;
	}

	public void setLow_x(double low_x) {
		this.low_x = low_x;
	}

	public double getLow_y() {
		return low_y;
	}

	public void setLow_y(double low_y) {
		this.low_y = low_y;
	}

	public double getHigh_x() {
		return high_x;
	}

	public void setHigh_x(double high_x) {
		this.high_x = high_x;
	}

	public double getHigh_y() {
		return high_y;
	}

	public void setHigh_y(double high_y) {
		this.high_y = high_y;
	}

	public String getTasot(){
		return tasot;
	}
	
	//String muuttujan avulla hallitaan layereita. currentLayers = MapDialog.updateImage-metodin String s muuttuja
	public void setTasot(String currentLayers){
		tasot = "";
		tasot = currentLayers;
	}
}
