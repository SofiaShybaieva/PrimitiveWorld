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

public class Space implements Landscape {
	Image image;
	File file = new File("space.jpg");

	// BufferedImage bufferedImage;

	public Space() {

		try {
			image = ImageIO.read(file);
			// bufferedImage = new BufferedImage(20, 20,
			// BufferedImage.TYPE_INT_RGB);
			// Graphics g = bufferedImage.getGraphics();
			// g.drawImage(image, 0, 0, 20, 20, null);
		} catch (IOException e) {
			System.err.println("Image Space not found!");
		}
	}

	@Override
	public void draw(Graphics g, Point coord) {
		g.drawImage(image, 0, 0, 600, 600, null);

	}

	@Override
	public boolean isCanEnterOn(Movable traveler) {
		return true;
	}

	@Override
	public boolean isCanStayOn(Movable traveler) {
		
		return true;
	}

}
