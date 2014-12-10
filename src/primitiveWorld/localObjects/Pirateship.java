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

public class Pirateship implements Movable, Drawable, Visible, Watcher, Tight {

	private Dimension size = new Dimension(20, 20);
	private Point coord;
	private Image image;
	private File file = new File("spaceship3.png");
	private String passRights = "";
	private int nextX;
	private int nextY;
	// Point pathPoints[] = null;
	private ArrayList<Point> patrol = null;
	private int currentPoint = 0;
	private int targetX, targetY;
	private int oldX, oldY;
	private int visibility = 100;
	private int contactRadius = 60;

	private static enum Speed {
		slow, fast
	}

	private Speed speed = Speed.slow;

	private void init() {
		this.setPassRights("f");
		this.coord = new Point();
		this.coord.setLocation(0, 0);
		this.targetX = this.targetY = this.nextX = this.nextY = 0;// = this.oldX
																	// =
																	// this.oldY
																	// = 0;
		loadImage(file);

	}

	public Pirateship() {
		init();
	}

	public Pirateship(int x, int y, ArrayList<Point> points) {
		init();
		this.coord.setLocation(x, y);
		this.patrol = points;
		this.targetX = this.nextX = this.coord.x;
		this.targetY = this.nextY = this.coord.y;
	}

	public Pirateship(int x, int y) {
		init();
		this.coord.setLocation(x, y);

	}

	@Override
	public String getTypeName() {

		return "Pirateship";
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
		if (ability.equals("Active"))
			return true;
		if (ability.equals("Movable"))
			return true;
		if (ability.equals("Visible"))
			return true;
		if (ability.equals("Tight"))
			return true;
		if (ability.equals("Watcher"))
			return true;
		return false;
	}

	@Override
	public void draw(Graphics g) {

//		 g.setColor(Color.YELLOW);
//		 g.drawArc(this.coord.x - this.contactRadius, this.coord.y
//		 - this.contactRadius, this.contactRadius * 2,
//		 this.contactRadius * 2, 0, 360);

		// display patrol points
		// for (int i = 0; i < patrol.size(); i++) {
		// if (i == this.currentPoint)
		// g.setColor(Color.RED);
		// else
		// g.setColor(Color.GREEN);
		// g.fillArc(patrol.get(i).x, patrol.get(i).y, 4, 4, 0, 360);
		// g.drawLine(this.coord.x, this.coord.y, patrol.get(i).x,
		// patrol.get(i).y);
		// }

		g.drawImage(image, this.coord.x - 10, this.coord.y - 10, 20, 20, null);
	}

	private void loadImage(File file) {
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			System.err.println("Image Pirateship not found!");
		}
	}

	@Override
	public void nextStep() {

		// no move was possible after last step, direction is blocked, make new
		// target
		if (this.oldX == this.coord.x && this.oldY == this.coord.y) {
			this.currentPoint = (int) (Math.random() * this.patrol.size());
			this.targetX = this.patrol.get(currentPoint).x;
			this.targetY = this.patrol.get(currentPoint).y;

		}

		// target point reached, make new random target point
		if (this.targetX == this.coord.x && this.targetY == this.coord.y) {
			if (this.currentPoint < patrol.size() - 1)
				this.currentPoint++;
			else
				this.currentPoint = 0;

			this.targetX = this.patrol.get(currentPoint).x;
			this.targetY = this.patrol.get(currentPoint).y;
		}
		int step = (this.speed == Speed.fast ? 5 : 2);
		// int stepX = (int) (1 + Math.random() * step);
		// int stepY = (int) (1 + Math.random() * step);
		int x0 = (int) (this.coord.getX());
		int y0 = (int) (this.coord.getY());

		int dx = this.targetX - x0;
		int signX = Integer.signum(dx); // get the sign of the dx (1-, 0, or
										// +1)
		int dy = this.targetY - y0;
		int signY = Integer.signum(dy);
		this.nextX = x0 + step * signX;
		this.nextY = y0 + step * signY;
		this.oldX = this.coord.x;
		this.oldY = this.coord.y;
//		System.err.println("targetX,targetY: " + targetX + " " + targetY
//				+ "nextX,nextY: " + nextX + " " + nextY);
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
	public int getVisibleRating() {

		return this.visibility;
	}

	@Override
	public void setVisibleRating(int visibleRating) {

		this.visibility = visibleRating;
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
			this.speed = Speed.slow;
			this.targetX = this.patrol.get(currentPoint).x;
			this.targetY = this.patrol.get(currentPoint).y;
			return;
		}

		for (Visible t : objects) {
			// order of the checks is: HomoSapiens, Wolf (decreasing
			// value of catching the object)
			if (t.getTypeName().equals("Shuttle")) {
				this.speed = Speed.fast;
				this.targetX = (int) t.getCoordinate().getX();
				this.targetY = (int) t.getCoordinate().getY();

				break;
			}
			if (t.getTypeName().equals("Planet")) {
				this.speed = Speed.fast;
				this.targetX = (int) t.getCoordinate().getX();
				this.targetY = (int) t.getCoordinate().getY();

				break;
			}

		}
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

		// eat (remove) 4 types of objects when in touch
		if (object.getTypeName().equals("Shuttle")) {
			EventCollector.addEvent(new CommandEvent(Command.removeLocalObject,
					object));
			this.speed = Speed.slow;
		}
	}

}
