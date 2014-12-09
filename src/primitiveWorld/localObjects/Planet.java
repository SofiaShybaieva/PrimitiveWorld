package primitiveWorld.localObjects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import primitiveWorld.interfaces.Drawable;

// planet 1
public class Planet implements Drawable {

	private Point coord;
	private Image image;
	private File file = new File("planet.png");

	public Planet() {
		// this.setPassRights("f");
		this.coord = new Point();
		this.coord.setLocation(0, 0);
		loadImage(file);
	}

	public Planet(int x, int y) {
		// this.setPassRights("f");
		this.coord = new Point();
		this.coord.setLocation(x, y);
		loadImage(file);
	}

	@Override
	public String getTypeName() {
		// TODO Auto-generated method stub
		return "Planet";
	}

	@Override
	public Point getCoordinate() {
		return this.coord;
	}

	@Override
	public void setCoordinate(Point coord) {
		this.coord.x = coord.x;
		this.coord.y = coord.y;

	}

	@Override
	public boolean is(String ability) {
		if (ability.equals("Drawable"))
			return true;
		return false;
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(image, this.coord.x - 30, this.coord.y - 30, 60, 60, null);
	}

	private void loadImage(File file) {
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			System.err.println("Image Planet not found!");
		}
	}

}
