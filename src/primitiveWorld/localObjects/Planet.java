package primitiveWorld.localObjects;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import primitiveWorld.engine.EventCollector;
import primitiveWorld.engine.event.Command;
import primitiveWorld.engine.event.CommandEvent;
import primitiveWorld.engine.event.GlobalEvent;
import primitiveWorld.interfaces.Drawable;
import primitiveWorld.interfaces.LocalObject;
import primitiveWorld.interfaces.Tight;
import primitiveWorld.interfaces.Visible;

// planet 1
public class Planet implements Drawable, Tight, Visible {

	private Point coord;
	private Image image;
	private File file = new File("planet.png");

	public Planet() {

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
		if (ability.equals("Tight"))
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

	@Override
	public int getVisibleRating() {

		return 200;
	}

	@Override
	public void setVisibleRating(int visibleRating) {

	}

	@Override
	public Dimension getSize() {

		return new Dimension(60, 60);
	}

	@Override
	public void setSize(Dimension size) {

	}

	@Override
	public void touch(LocalObject object) {
		if (object.getTypeName().equals("Asteroid")
				|| object.getTypeName().equals("Comet")) {
			EventCollector.addEvent(new CommandEvent(Command.removeLocalObject,
					object));
		}
		if (object.getTypeName().equals("Comet")) {
			int x = 40;
			int y = 300;
			EventCollector.addEvent(new CommandEvent(Command.addLocalObject,
					new Comet(x, y)));
			int rx = (int) (Math.random() * 600);
			int ry = (int) (Math.random() * 600);
			EventCollector.addEvent(new CommandEvent(Command.addLocalObject,
					new Asteroid(rx, ry)));
			rx = (int) (Math.random() * 600);
			ry = (int) (Math.random() * 600);
			EventCollector.addEvent(new CommandEvent(Command.addLocalObject,
					new Asteroid(rx, ry)));
		}
		if (object.getTypeName().equals("Pirateship")) {
			GlobalEvent globalEvent = new GlobalEvent("Defeat");
			EventCollector.addEvent(globalEvent);
		}
		if (object.getTypeName().equals("Shuttle")) {
			GlobalEvent globalEvent = new GlobalEvent("Victory");
			EventCollector.addEvent(globalEvent);
		}
		
	}

}
