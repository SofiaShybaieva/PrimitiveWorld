package primitiveWorld.location;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import primitiveWorld.engine.EventCollector;
import primitiveWorld.interfaces.LocalObject;
import primitiveWorld.landscapes.Cave;
import primitiveWorld.landscapes.Forest;
import primitiveWorld.landscapes.Marshland;
import primitiveWorld.landscapes.Rock;
import primitiveWorld.landscapes.Water;
import primitiveWorld.localObjects.LocalObjectsFactory;

public class LocationLoader {

	private int sizeX;
	private int sizeY;

	Location location;
	Collection<LocalObject> localObjectsList;

	public EventCollector eventCollector;

	public void loadLocation(File file) throws FileNotFoundException,
			WrongFileFormat {

		Scanner input = new Scanner(file);
		String version = input.nextLine(); // read 1st line
		try {
			if (version.equals("Primitive World v.1.2") != true) {
				throw new WrongFileFormat();
			}
			sizeY = input.nextInt(); // read number of rows
			sizeX = input.nextInt(); // read number of columns
			Location location = new Location(sizeY, sizeX);
			this.location = location;
			String sizeLine = input.nextLine(); // read the rest of 2nd line

			for (int i = 0; i < sizeY; i++) {
				String row = input.nextLine();
				byte[] bytes = row.getBytes();
				for (int j = 0; j < sizeX; j++) {
					switch (bytes[j]) {
					case 'R':
						location.area[i][j] = new Rock();
						break;
					case 'W':
						location.area[i][j] = new Water();
						break;
					case 'e':
						location.area[i][j] = null;
						break;
					case 'M':
						location.area[i][j] = new Marshland();
						break;
					case 'F':
						location.area[i][j] = new Forest();
						break;
					case 'C':
						location.area[i][j] = new Cave();
						break;
					} // switch
				} // for j

			} // for i

			// check for the LocalObjects string
			String localObjectsString = input.nextLine();
			if (localObjectsString.equals("LocalObjects") != true) {
				throw new WrongFileFormat();
			}

			// read the number of local objects
			int num = input.nextInt();
			this.localObjectsList = new ArrayList<LocalObject>(num);
			input.nextLine(); // go to the next line

			for (int i = 0; i < num; i++) {
				String type = input.nextLine();
				String argsLine = input.nextLine();
				String[] args = argsLine.split(" ");
				int x = Integer.parseInt(args[0]);
				int y = Integer.parseInt(args[1]);
				// input.nextLine();
				LocalObject localObject = LocalObjectsFactory.buildLocalObject(
						type, argsLine);
				localObjectsList.add(localObject);

			} // for i
		} finally {
			input.close();
		}

	}

	public Location getLocation() {

		return this.location;
	}

	public Collection<LocalObject> getLocalObjects() {

		return this.localObjectsList;
	}

}
