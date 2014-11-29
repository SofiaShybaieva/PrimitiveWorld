package primitiveWorld.landscapes;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import primitiveWorld.engine.EventCollector;
import primitiveWorld.engine.event.GlobalEvent;
import primitiveWorld.interfaces.Landscape;
import primitiveWorld.interfaces.Movable;
import primitiveWorld.localObjects.HomoSapiens;

public class Cave implements Landscape {
	Image image;
	File file = new File("cave.png");

	// BufferedImage bufferedImage;

	public Cave() {

		try {
			image = ImageIO.read(file);
			// bufferedImage = new BufferedImage(20, 20,
			// BufferedImage.TYPE_INT_RGB);
			// Graphics g = bufferedImage.getGraphics();
			// g.drawImage(image, 0, 0, 20, 20, null);
		} catch (IOException e) {
			System.err.println("Image Cave not found!");
		}
	}

	@Override
	public void draw(Graphics g, Point coord) {
		g.drawImage(image, coord.x, coord.y, 20, 20, null);

	}

	@Override
	public boolean isCanEnterOn(Movable traveler) {
		String rights = traveler.getPassRights();
		if (rights.contains("f"))
			return true;

		if (traveler instanceof HomoSapiens) {

			return true;
		}
		return false;
	}

	@Override
	public boolean isCanStayOn(Movable traveler) {
		String rights = traveler.getPassRights();
		if (rights.contains("f"))
			return true;
		if (traveler instanceof HomoSapiens) {
			EventCollector.addEvent(new GlobalEvent("Victory"));
			return true;
		}
		return false;
	}

}
