import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import VultBurglary.VultBurglarySystem;
import VultBurglary.VultBurglarySystemClass;

public class Main {

	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			String[] line = reader.readLine().split(" ");
			int numThieves = Integer.parseInt(line[0]);
			int numGoldBars = Integer.parseInt(line[1]);
			int numLocations = Integer.parseInt(line[2]);
			int numRoads = Integer.parseInt(line[3]);

			VultBurglarySystem vb = new VultBurglarySystemClass(numLocations, numThieves, numRoads, numGoldBars);

			for (int i = 0; i < numRoads; i++) {
				line = reader.readLine().split(" ");
				int firstNode = Integer.parseInt(line[0]);
				int secondNode = Integer.parseInt(line[1]);
				vb.addPassage(firstNode, secondNode);
			}
			line = reader.readLine().split(" ");
			int firstNode = Integer.parseInt(line[0]); //fluxo
			int lastNode = Integer.parseInt(line[1]); //dreno

			System.out.println(vb.getMaxFlux(firstNode, lastNode));

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

}
