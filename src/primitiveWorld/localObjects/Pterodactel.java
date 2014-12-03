package primitiveWorld.localObjects;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
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

public class Pterodactel implements Movable, Drawable, Visible, Watcher, Tight {

	private Dimension size = new Dimension(20, 20);
	private Point coord;
	private Image image;
	private File file = new File("pterodactel.png");
	private String passRights = "";
	private int nextX;
	private int nextY;
	Point pathPoints[] = null;
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
		this.targetX = this.targetY = this.nextX = this.nextY = this.oldX = this.oldY = 0;
		loadImage(file);

	}

	public Pterodactel() {
		init();
	}

	public Pterodactel(int x, int y, String pathPoints) {
		init();
		this.coord.setLocation(x, y);

	}

	public Pterodactel(int x, int y) {
		init();
		this.coord.setLocation(x, y);

	}

	@Override
	public String getTypeName() {

		return "Pterodactel";
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

		// g.setColor(Color.WHITE);
		// g.fillRect(this.coord.x, this.coord.y, 20, 20);
		g.drawImage(image, this.coord.x - 10, this.coord.y - 10, 20, 20, null);
	}

	private void loadImage(File file) {
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			System.err.println("Image Pterodactel not found!");
		}
	}

	@Override
	public void nextStep() {

		// no move was possible after last step, direction is blocked, make new
		// target
		if (this.oldX == this.coord.x && this.oldY == this.coord.y) {
			this.targetX = (int) (Math.random() * 800);
			this.targetY = (int) (Math.random() * 600);
		}

		// target point reached, make new random target point
		if (this.targetX == this.coord.x && this.targetY == this.coord.y) {
			this.targetX = (int) (Math.random() * 800);
			this.targetY = (int) (Math.random() * 600);
		}

		int step = (this.speed == Speed.fast ? 8 : 4);
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
		if (objects.isEmpty())
			return;

		for (Visible t : objects) {
			// order of the checks is: HomoSapiens, Wolf (decreasing
			// value of catching  the object)
			if (t.getTypeName().equals("HomoSapiens")) {
				this.speed = Speed.fast;
				this.targetX = (int) t.getCoordinate().getX();
				this.targetY = (int) t.getCoordinate().getY();
				System.err.println("Pterodactel is Targeting HomoSapiens!");
				break;
			}
			if (t.getTypeName().equals("Wolf")) {
				this.speed = Speed.fast;
				this.targetX = (int) t.getCoordinate().getX();
				this.targetY = (int) t.getCoordinate().getY();
				System.err.println("Pterodactel is Targeting Wolf!");
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
		if (object.getTypeName().equals("Wolf")
				|| object.getTypeName().equals("HomoSapiens")
				|| object.getTypeName().equals("Snake")
				|| object.getTypeName().equals("Krokodile")) {
			EventCollector.addEvent(new CommandEvent(Command.removeLocalObject,
					object));
			this.speed = Speed.slow;
		}
	}

}
