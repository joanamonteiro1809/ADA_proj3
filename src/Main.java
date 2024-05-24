import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import VultBurglary.VultBurglarySystem;
import VultBurglary.VultBurglarySystemClass;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			String[] line = reader.readLine().split(" ");
			int numThieves = Integer.parseInt(line[0]);
			int numGoldBars = Integer.parseInt(line[1]);
			int numLocations = Integer.parseInt(line[2]);
			int numRoads = Integer.parseInt(line[3]);

			String[] roads = new String[numRoads];
			int roadIndex = 0;
			for (int i = 0; i < numRoads; i++) {
				line = reader.readLine().split(" ");
				int firstNode = Integer.parseInt(line[0]);
				int secondNode = Integer.parseInt(line[1]);
				roads[roadIndex] = firstNode + " " + secondNode;
				roadIndex++;
				//vb.addPassage(firstNode, secondNode);
			}
			line = reader.readLine().split(" ");
			int firstNode = Integer.parseInt(line[0]); //fluxo
			int lastNode = Integer.parseInt(line[1]); //dreno
			//vb.addFirstFlux(firstNode);
		
			VultBurglarySystem vb = new VultBurglarySystemClass(numLocations, firstNode, numThieves, lastNode, numRoads);

			
		
			for(int i = 0; i< numRoads; i++){
				String[] locations = roads[i].split(" ");
				vb.addPassage(Integer.parseInt(locations[0]), Integer.parseInt(locations[1]));
			}

			System.out.println(Math.min(vb.getMaxFlux(),numThieves)*numGoldBars);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

}
