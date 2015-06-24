package cn.edu;

public class Location {
	private double lon;
	private double lat;		
	private long time;
	private short direction;
	public Location(String value) {	
		this.time = Long.parseLong(value.split(",")[0]);
		this.lon = Double.parseDouble(value.split(",")[1]);
		this.lat = Double.parseDouble(value.split(",")[2]);
		this.direction = Short.parseShort(value.split(",")[3]);
	}

	public short getDirection() {
		return direction;
	}

	public void setDirection(short direction) {
		this.direction = direction;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
		

}
