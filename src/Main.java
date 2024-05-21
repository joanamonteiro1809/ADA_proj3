import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import VultBurglary.*;

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
			VultBurglarySystem vb = (VultBurglarySystem) new VultBurglarySystemClass();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

}
