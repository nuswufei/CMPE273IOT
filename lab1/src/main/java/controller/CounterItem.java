package controller;

public class CounterItem {
	String id;
	String num;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	@Override
	public String toString() {
		return "user " + id + " time " + num;
	}
	public CounterItem() {
		
	}

}
