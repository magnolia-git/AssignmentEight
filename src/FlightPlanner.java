import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import acm.program.ConsoleProgram;

public class FlightPlanner extends ConsoleProgram {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String startLocation = "";
	private static String location = "";
	private static String destination = "";
	private static boolean running = true;
	private static File flightsFile = new File("flights.txt");
	private static ArrayList<Flight> flights = new ArrayList<>();
	private static ArrayList<String> flightPath = new ArrayList<>();
	private static ArrayList<String> cities = new ArrayList<>();

	public void run() {
		generateFlightList(flightsFile);
		println("Welcome to Flight Planner!");
		println("Here's a list of all the cities in our database:");
		listCities();
		println("Let's plan a round-trip route!");
		while (true) {
			location = readLine("Enter the starting city: ");
			if (cities.contains(location)) break;
			println("Incorrect input: " + location + " is not in our database. Try again.");
		}
		startLocation = location;		// Remembers where you started
		flightPath.add(startLocation);
		while (running) {
			destination = selectDestination();
			if (destination.equals(startLocation)) {
				running = false;		// False means desired destination and starting location are the same
			}
			setDestination(location, destination);
		}
		println("The route you've chosen is:");
		flightPath.forEach(l -> print(l));
	}

	private void generateFlightList(File file) {
		String temp = "";
		try {
			BufferedReader bufferReader = new BufferedReader(new FileReader(file));
			while ((temp = bufferReader.readLine()) != null) {			// If there's still another line to read...
				if (temp.isEmpty()) {									// If there's a blank line, ignore it

				} else {
					String[] line = temp.split(" -> ");					// Split up the line
					Flight record = new Flight(line[0], line[1]);		// Make new flight record
					flights.add(record);								// Add to flights array
					for (int i = 0; i < line.length; i++) {
						if (!cities.contains(line[i])) {
							cities.add(line[i]);
			}	}	}	}
		
			bufferReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void listCities() {
		cities.forEach(c -> println(c));
	}

	private String selectDestination() {
		println("From " + location + " you can fly directly to:");
		listDestinations(location);
		return readLine("Where do you want to go from " + location + "? ");
	}

	private void listDestinations(String str) {
		for (Flight i : flights) {
			if (i.getStart().equals(str)) {
				println(i.getDestination());
			}
		}
	}

	private void setDestination(String from, String to) {
		boolean isPositive = false;
		for (Flight i : flights) {
			if (i.getStart().equals(from) && i.getDestination().equals(to)) {
				location = destination;
				flightPath.add(" -> " + location);
				isPositive = true;
			}
		}
		if (!isPositive) {
			println("City specified unavailable, or doesn't exist. Please try again.");
		}
	}
}