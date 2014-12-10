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

public class Comet implements Movable, Drawable, Tight, Visible, Watcher {

	private Point coord;
	private Image image;
	private File file = new File("comet.png");
	private String passRights = "";
	private int nextX;
	private int nextY;
	private int targetX, targetY;
	private int oldX, oldY;
	private Dimension size = new Dimension(20, 20);
	private int visibility = 100;
	private int contactRadius = 100;
	private static ArrayList<Point> patrol = null;
	private int speed = 5;
	private int currentPoint = 0;
	private int ticks = 0;

	private void init() {
		this.setPassRights("m");
		this.coord = new Point();
		this.coord.setLocation(0, 0);
		this.targetX = this.targetY = this.nextX = this.nextY = this.oldX = this.oldY = 0;
		loadImage(file);

	}

	public Comet() {
		init();
	}

	public Comet(int x, int y) {
		init();
		this.coord.setLocation(x, y);

	}

	public Comet(int x, int y, ArrayList<Point> points) {
		init();
		this.coord.setLocation(x, y);
		this.patrol = points;

		this.currentPoint = 0;
		this.targetX = this.patrol.get(currentPoint).x;
		this.targetY = this.patrol.get(currentPoint).y;
	}

	@Override
	public String getTypeName() {

		return "Comet";
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
		if (ability.equals("Watcher"))
			return true;
		if (ability.equals("Visible"))
			return true;
		if (ability.equals("Tight"))
			return true;
		return false;
	}

	@Override
	public void draw(Graphics g) {

//		g.setColor(Color.GREEN);
//		g.drawArc(this.coord.x - this.contactRadius, this.coord.y
//				- this.contactRadius, this.contactRadius * 2,
//				this.contactRadius * 2, 0, 360);

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

			g.drawImage(image, this.coord.x - 20, this.coord.y - 15, 40, 30,
					null);
	}

	private void loadImage(File file) {
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			System.err.println("Image Comet not found!");
		}
	}

	@Override
	public void nextStep() {
		 
		this.speed = 5;
		// no move was possible after last step, direction is blocked, make new
		// target
		if (this.oldX == this.coord.x && this.oldY == this.coord.y) {
			// this.targetX = (int) (Math.random() * 800);
			// this.targetY = (int) (Math.random() * 600);
			this.currentPoint = (int) (Math.random() * this.patrol.size());

			this.targetX = this.patrol.get(currentPoint).x;
			this.targetY = this.patrol.get(currentPoint).y;
		}

		// target point reached, make new target point from the partol list
		if (this.targetX == this.coord.x && this.targetY == this.coord.y) {
			if (this.currentPoint < patrol.size() - 1)
				this.currentPoint++;
			else
				this.currentPoint = 0;
			this.targetX = this.patrol.get(currentPoint).x;
			this.targetY = this.patrol.get(currentPoint).y;
			
		}

		int stepX = (int) (1 + Math.random() * speed);
		int stepY = (int) (1 + Math.random() * speed);
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
	public void atZone(Collection<Visible> objects) {
		if (objects.isEmpty()) {
			this.speed = 5;
			return;
		}

		for (Visible t : objects) {
			// hide when other objects are approaching
			if (t.getTypeName().equals("?")
					|| t.getTypeName().equals("?")
					|| t.getTypeName().equals("Planet")) {

				int distance = (int) t.getCoordinate().distance(this.coord);

				// attack closes object
				if (distance<=100) {
					this.targetX = (int) t.getCoordinate().getX();
					this.targetY = (int) t.getCoordinate().getY();
					this.speed=10;
					
					break;
				}
				
				break;
			}

		}

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
	public Dimension getSize() {

		return this.size;
	}

	@Override
	public void setSize(Dimension size) {
		this.size.setSize(size);

	}

	@Override
	public void touch(LocalObject object) {
		if (object.getTypeName().equals("?")
				|| object.getTypeName().equals("Shuttle")) {
			EventCollector.addEvent(new CommandEvent(Command.removeLocalObject,
					object));
		}
	}

}
