package primitiveWorld.localObjects;

import java.awt.Point;
import java.util.Collection;

import primitiveWorld.interfaces.LocalObject;

public class LocalObjectsFactory {
	public static LocalObject buildLocalObject(String className,
			Collection<Point> points) {
		String[] args = arguments.split(" ");
		int x = Integer.parseInt(args[0]);
		int y = Integer.parseInt(args[1]);
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
			Krokodile krokodile = new Krokodile(x, y);
			return krokodile;
		case "Snake":
			Snake snake = new Snake(x, y);
			return snake;
		case "Wolf":
			Wolf wolf = new Wolf(x, y);
			return wolf;
		case "Pterodactel":
			Pterodactel pterodactel = new Pterodactel(x, y);
			return pterodactel;

		}
		throw new IllegalArgumentException();
	}
}
