package primitiveWorld.landscapes;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import primitiveWorld.interfaces.Landscape;
import primitiveWorld.interfaces.Movable;

public class Rock implements Landscape {
	Image image;
	File file = new File("rock.png");
	//BufferedImage bufferedImage;

	public Rock() {
		try {
			image = ImageIO.read(file);
			//bufferedImage = new BufferedImage(20, 20,
			//		BufferedImage.TYPE_INT_RGB);
			//Graphics g = bufferedImage.getGraphics();
			//g.drawImage(image, 0, 0, 20, 20, null);
		} catch (IOException e) {
			System.err.println("Image Rock not found!");
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
		return false;
	}

	@Override
	public boolean isCanStayOn(Movable traveler) {
		String rights = traveler.getPassRights();
		if (rights.contains("f"))
			return true;
		return false;
	}

}
