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
		case "Gopher":
			Gopher gopher = new Gopher(x, y);
			return gopher;
		case "Totem":
			Totem totem = new Totem(x, y);
			return totem;
		case "HomoSapiens":
			HomoSapiens homoSapiens = new HomoSapiens(x, y);
			return homoSapiens;
		case "CampFire":
			CampFire campFire = new CampFire(x, y);
			return campFire;
		case "Krokodile":
			Krokodile krokodile = new Krokodile(x, y, points);
			return krokodile;
		case "Snake":
			Snake snake = new Snake(x, y);
			return snake;
		case "Wolf":
			Wolf wolf = new Wolf(x, y, points);
			return wolf;
		case "Pterodactel":
			Pterodactel pterodactel = new Pterodactel(x, y, points);
			return pterodactel;

		}
		throw new IllegalArgumentException();
	}
}
