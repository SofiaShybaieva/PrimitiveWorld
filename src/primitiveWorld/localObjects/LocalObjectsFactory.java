package primitiveWorld.localObjects;

import java.awt.Point;
import java.util.ArrayList;

import primitiveWorld.interfaces.LocalObject;
import primitiveWorld.landscapes.Wormhole;

public class LocalObjectsFactory {
	public static LocalObject buildLocalObject(String className,
			ArrayList<Point> points) {
		
		int x = points.get(0).x;
		int y = points.get(0).y;
		switch (className) { 
		case "Asteroid":
			Asteroid a = new Asteroid(x, y, points);
			return a;
		case "Shuttle":
			Shuttle shuttle = new Shuttle(x, y);
			return shuttle;
		case "Planet":
			Planet planet = new Planet(x, y);
			return planet;

		case "Comet":
			Comet c = new Comet(x, y, points);
			return c;
//		case "Snake":
//			Snake snake = new Snake(x, y);
//			return snake;
		

		case "Pirateship":
			Pirateship pirateship = new Pirateship(x, y, points);
			return pirateship;

		}
		throw new IllegalArgumentException();
		
	}
}

