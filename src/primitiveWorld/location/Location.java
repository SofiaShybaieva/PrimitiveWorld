package primitiveWorld.location;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import primitiveWorld.engine.EventCollector;
import primitiveWorld.engine.event.CommandEvent;
import primitiveWorld.engine.event.Event;
import primitiveWorld.engine.event.GlobalEvent;
import primitiveWorld.interfaces.EventListener;
import primitiveWorld.interfaces.Landscape;
import primitiveWorld.interfaces.LocalObject;
import primitiveWorld.interfaces.Movable;
import primitiveWorld.landscapes.Cave;
import primitiveWorld.landscapes.Forest;
import primitiveWorld.landscapes.Marshland;
import primitiveWorld.landscapes.Rock;
import primitiveWorld.landscapes.Water;

public class Location implements EventListener {

	private Image image;
	private File file = new File("grass2.png");
	private BufferedImage bufferedImage;
	public Landscape area[][] = null;

	int x = 0, y = 0;

	public Location(int y, int x) {
		area = new Landscape[y][x];
		this.x = x;
		this.y = y;
		try {
			image = ImageIO.read(file);

			// bufferedImage = new BufferedImage(800, 600,
			bufferedImage = new BufferedImage(20 * this.x, 20 * this.y,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = bufferedImage.getGraphics();
			// g.drawImage(image, 0, 0, 800, 600, null);
			g.drawImage(image, 0, 0, 20 * this.x, 20 * this.y, null);
		} catch (IOException e) {
			System.err.println("Image Grass not found!");
		}
		EventCollector.addEventListener(this, "comand.removeLocalObject");
	}

	public boolean isCanMove(Movable traveler) {
		Point coordLocalObject = traveler.getStepTarget();

		int i, j;
		int delta = 20 / 2;

		if ((coordLocalObject.x - delta < 0)
				|| (coordLocalObject.y - delta < 0)
				|| ((coordLocalObject.x + delta) > (x * 20))
				|| ((coordLocalObject.y + delta) > (y * 20)))
			return false;

		i = (coordLocalObject.y - delta) / 20;
		j = (coordLocalObject.x - delta) / 20;
		if (area[i][j] != null && !area[i][j].isCanEnterOn(traveler))
			return false;

		i = (coordLocalObject.y - delta) / 20;
		j = (coordLocalObject.x + delta - 1) / 20;
		if (area[i][j] != null && !area[i][j].isCanEnterOn(traveler))
			return false;

		i = (coordLocalObject.y + delta - 1) / 20;
		j = (coordLocalObject.x + delta - 1) / 20;
		if (area[i][j] != null && !area[i][j].isCanEnterOn(traveler))
			return false;

		i = (coordLocalObject.y + delta - 1) / 20;
		j = (coordLocalObject.x - delta) / 20;
		if (area[i][j] != null && !area[i][j].isCanEnterOn(traveler))
			return false;

		i = (coordLocalObject.y) / 20;
		j = (coordLocalObject.x) / 20;
		if (area[i][j] != null && !area[i][j].isCanStayOn(traveler))
			return false;

		return true;
		/*
		 * if (obj instanceof Gopher) { Gopher gopher = (Gopher) obj; Point p =
		 * gopher.getStepTarget(); // get the desired new coordinate int x =
		 * (int) (p.x / 20); // convert coordinate to matrix // indices int y =
		 * (int) (p.y / 20); if (x <= 0) x = 0; if (y <= 0) y = 0;
		 * 
		 * Landscape landscapeObject = area[y][x]; // get corresponding //
		 * Landscape object from // matrix if (landscapeObject == null) // an
		 * object can always move to empty // space // enter return true;
		 * 
		 * return landscapeObject.isCanEnterOn(gopher); // ask Landscape //
		 * object if // Gopher can // enter } if (obj instanceof Totem) { Totem
		 * totem = (Totem) obj; Point p = totem.getStepTarget(); int x = (int)
		 * (p.x / 20); int y = (int) (p.y / 20); if (x <= 0) x = 0; if (y <= 0)
		 * y = 0; Landscape landscapeObject = area[y][x]; if (landscapeObject ==
		 * null) return true;
		 * 
		 * return landscapeObject.isCanEnterOn(totem); } return false;
		 */
	}

	public void draw(Graphics g) {
		// g.drawImage(bufferedImage, 0, 0, 800, 600, null);
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				Point coord = new Point(j * 20, i * 20);

				g.drawImage(bufferedImage, coord.x, coord.y, 20, 20, null);
				// g.drawImage(bufferedImage, coord.x, coord.y, 20, 20, null);
				if (this.area[i][j] instanceof Forest) {
					((Forest) area[i][j]).draw(g, coord);
				}
				if (this.area[i][j] instanceof Water) {
					((Water) area[i][j]).draw(g, coord);
				}
				if (this.area[i][j] instanceof Rock) {
					((Rock) area[i][j]).draw(g, coord);
				}
				if (this.area[i][j] instanceof Marshland) {
					((Marshland) area[i][j]).draw(g, coord);
				}
				if (this.area[i][j] instanceof Cave) {
					((Cave) area[i][j]).draw(g, coord);
				}
			}
		}
	}

	@Override
	public void eventOccurred(Event event) {
		if (event.getName().equals("comand.removeLocalObject")) {
			CommandEvent commandEvent = (CommandEvent) event;
			LocalObject obj = (LocalObject) commandEvent.getArgument();
			if (obj.getTypeName().equals("HomoSapiens")) {
				GlobalEvent globalEvent = new GlobalEvent("Defeat");
				EventCollector.addEvent(globalEvent);
			}
		}

	}
}
