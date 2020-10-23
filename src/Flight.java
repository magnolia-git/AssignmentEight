public class Flight {
	private String start;
	private String destination;
	
	Flight(String start, String destination) {
		this.start = start;
		this.destination = destination;
	}
	
	public String getStart() {
		return start;
	}
	
	public String getDestination() {
		return destination;
	}
	
	public String toString() {
		return start + " -> " + destination;
	}
}
