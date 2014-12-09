package primitiveWorld.localObjects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.imageio.ImageIO;

import primitiveWorld.engine.EventCollector;
import primitiveWorld.engine.event.Command;
import primitiveWorld.engine.event.CommandEvent;
import primitiveWorld.interfaces.Drawable;
import primitiveWorld.interfaces.LocalObject;
import primitiveWorld.interfaces.Movable;
import primitiveWorld.interfaces.Tight;
import primitiveWorld.interfaces.Visible;
import primitiveWorld.interfaces.Watcher;

public class Asteroid implements Movable, Drawable, Tight, Visible, Watcher {

	private static enum Speed {
		slow, fast
	}

	private Dimension size = new Dimension(20, 20);
	private Point coord;
	private Image image;
	private File file = new File("asteroid.png");
	private String passRights = "";
	private int nextX;
	private int nextY;

	private int targetX, targetY;
	private int oldX, oldY;

	private int visibility = 100;
	private int contactRadius = 50;
	private Speed speed;
	private ArrayList<Point> patrol = null;
	private int currentPoint=0;

	private void init() {
		this.setPassRights("");
		this.coord = new Point();
		this.coord.setLocation(0, 0);
		this.targetX = this.targetY = this.nextX = this.nextY = this.oldX = this.oldY = 0;
		// size.setSize(20, 20);
		loadImage(file);

	}

	public Asteroid() {
		init();
	}

	public Asteroid(int x, int y, ArrayList<Point> points) {
		init();
		this.coord.setLocation(x, y);
		this.patrol = points;

		this.currentPoint = 0;
		this.targetX = this.patrol.get(currentPoint).x;
		this.targetY = this.patrol.get(currentPoint).y;

	}

	@Override
	public String getTypeName() {

		return "Asteroid";
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
		if (ability.equals("Active"))
			return true;
		if (ability.equals("Movable"))
			return true;
		if (ability.equals("Watcher"))
			return true;
		if (ability.equals("Visible"))
			return true;
		return false;
	}

	@Override
	public void draw(Graphics g) {

		g.setColor(Color.GRAY);
		g.drawArc(this.coord.x - this.contactRadius, this.coord.y
				- this.contactRadius, this.contactRadius * 2,
				this.contactRadius * 2, 0, 360);
		// display patrol points
//		for (int i = 0; i < patrol.size(); i++) {
//			if (i == this.currentPoint)
//				g.setColor(Color.RED);
//			else
//				g.setColor(Color.GREEN);
//			g.fillArc(patrol.get(i).x, patrol.get(i).y, 4, 4, 0, 360);
//			g.drawLine(this.coord.x, this.coord.y, patrol.get(i).x,
//					patrol.get(i).y);
//		}
		g.drawImage(image, this.coord.x - 10, this.coord.y - 10, 20, 20, null);
	}

	private void loadImage(File file) {
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			System.err.println("Image Asteroid not found!");
		}
	}

	@Override
	public void nextStep() {

		// no move was possible after last step, direction is blocked, make new
		// target
		if (this.oldX == this.coord.x && this.oldY == this.coord.y) {
//			this.targetX = (int) (Math.random() * 800);
//			this.targetY = (int) (Math.random() * 600);
			this.currentPoint = (int) (Math.random()*this.patrol.size());
			this.targetX = this.patrol.get(currentPoint).x;
			this.targetY = this.patrol.get(currentPoint).y;

		}

		// target point reached, make new random target point
		if (this.targetX == this.coord.x && this.targetY == this.coord.y) {
//			this.targetX = (int) (Math.random() * 800);
//			this.targetY = (int) (Math.random() * 600);
			if (this.currentPoint < patrol.size() - 1)
				this.currentPoint++;
			else
				this.currentPoint = 0;
			this.targetX = this.patrol.get(currentPoint).x;
			this.targetY = this.patrol.get(currentPoint).y;
		}

		int step = (this.speed == Speed.fast ? 4 : 2);
		int stepX = (int) (1 + Math.random() * step);
		int stepY = (int) (1 + Math.random() * step);
		int x0 = (int) (this.coord.getX());
		int y0 = (int) (this.coord.getY());

		int dx = this.targetX - x0;
		int signX = Integer.signum(dx); // get the sign of the dx (1-, 0, or
										// +1)
		int dy = this.targetY - y0;
		int signY = Integer.signum(dy);
		this.nextX = x0 + stepX * signX;
		this.nextY = y0 + stepY * signY;
		this.oldX = this.coord.x;
		this.oldY = this.coord.y;

	}

	@Override
	public Point getStepTarget() {
		Point p = new Point(this.nextX, this.nextY);
		return p;
	}

	@Override
	public void moveTo(Point coord) {
		this.coord = coord;
	}

	@Override
	public String getPassRights() {
		return this.passRights;
	}

	@Override
	public void setPassRights(String passRights) { // :)
		this.passRights = passRights;

	}

	@Override
	public Dimension getSize() {

		return this.size;
	}

	@Override
	public void setSize(Dimension size) {
		this.size.setSize(size);

	}

	@Override
	public void touch(LocalObject object) {
		if (object.getTypeName().equals("HomoSapiens")) {
			EventCollector.addEvent(new CommandEvent(Command.removeLocalObject,
					object));
		}

	}

	@Override
	public int getVisibleRating() {
		// TODO Auto-generated method stub
		return 100;
	}

	@Override
	public void setVisibleRating(int visibleRating) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getContactRadius() {

		return this.contactRadius;
	}

	@Override
	public void setContactRadius(int radius) {
		this.contactRadius = radius;

	}

	@Override
	public void atZone(Collection<Visible> objects) {
		if (objects.isEmpty()) {
			this.speed = Speed.slow; // restore slow speed after all objects
										// gone from visibility
			return;
		}
		for (Visible t : objects) {
			// order of the checks is: Pterodactel, Wolf, HomoSapiens,
			// (decreasing danger)

			if (t.getTypeName().equals("Pterodactel")) {
				this.speed = Speed.fast;

				Point runAway = getRunAwayCoord(t.getCoordinate());
				this.targetX = (int) runAway.getX();
				this.targetY = (int) runAway.getY();

				// System.err.println("Pterodactel is Approaching Wolf!");
				break;
			}
			if (t.getTypeName().equals("Wolf")) {
				this.speed = Speed.fast;
				Point runAway = getRunAwayCoord(t.getCoordinate());
				this.targetX = (int) runAway.getX();
				this.targetY = (int) runAway.getY();
				// System.err.println("Pterodactel is Approaching Wolf!");
				break;
			}
			if (t.getTypeName().equals("HomoSapiens")) {
				this.speed = Speed.fast;
				// targeting HomoSapiens object
				this.targetX = (int) t.getCoordinate().getX();
				this.targetY = (int) t.getCoordinate().getY();
				// System.err.println("Wolf is Targeting HomoSapiens!");
				break;
			}

		}

	}

	// calculate the run away point when a monster is approaching at coordinate
	private Point getRunAwayCoord(Point coordinate) {
		Point p = new Point();
		p.x = (this.coord.x - coordinate.x) + this.coord.x;
		p.y = (this.coord.y - coordinate.y) + this.coord.y;
		return p;
	}

}
