package primitiveWorld.localObjects;

import java.awt.Point;
import java.util.ArrayList;

import primitiveWorld.interfaces.LocalObject;

public class LocalObjectsFactory {
	public static LocalObject buildLocalObject(String className,
			ArrayList<Point> points) {
		
		int x = points.get(0).x;
		int y = points.get(0).y;
		switch (className) { 
//		case "Asteroid":
//			Asteroid totem = new Asteroid(x, y);
//			return totem;
		case "Shuttle":
			Shuttle shuttle = new Shuttle(x, y);
			return shuttle;
		case "Planet":
			Planet planet = new Planet(x, y);
			return planet;

//		case "Asteroid":
//			Asteroid krokodile = new Asteroid(x, y, points);
//			return krokodile;
//		case "Snake":
//			Snake snake = new Snake(x, y);
//			return snake;
//		case "Wolf":
//			Wolf wolf = new Wolf(x, y, points);
//			return wolf;

		case "Pirateship":
			Pirateship pirateship = new Pirateship(x, y, points);
			return pirateship;

		}
		throw new IllegalArgumentException();
		
	}
}

