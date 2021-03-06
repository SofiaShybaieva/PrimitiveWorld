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
import primitiveWorld.interfaces.Drawable;
import primitiveWorld.interfaces.LocalObject;
import primitiveWorld.interfaces.Movable;
import primitiveWorld.interfaces.Tight;

public class Gopher implements Movable, Drawable, Tight {

	private Dimension size = new Dimension(20, 20);
	private Point coord;
	private Image image;
	private File file = new File("gopher.png");
	private String passRights = "";
	private int nextX;
	private int nextY;
	private int counter;
	private int targetX, targetY;
	private int oldX, oldY;


	private void init() {
		this.setPassRights("");
		this.coord = new Point();
		this.coord.setLocation(0, 0);
		this.targetX = this.targetY = this.nextX = this.nextY = this.oldX = this.oldY = 0;
		loadImage(file);
		counter = 0;
	}

	public Gopher() {
		init();
	}

	public Gopher(int x, int y) {
		init();
		this.coord.setLocation(x, y);

	}

	@Override
	public String getTypeName() {

		return "Gopher";
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
			System.err.println("Image Gopher not found!");
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

		int stepX = (int) (1 + Math.random() * 5);
		int stepY = (int) (1 + Math.random() * 5);
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
		counter++;
		int rnd = (int) (90 + Math.random() * 20); // liveability
		if (counter % rnd == 0) {
			counter = 0;
			int x = this.coord.x;
			int y = this.coord.y;
			EventCollector.addEvent(new CommandEvent(Command.addLocalObject,
					new Gopher(x, y)));
		}
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
		if (object.getTypeName().equals("Gopher")) {
			// bounce away from other gophers
			int deltaX, deltaY;
			deltaX = -5 + (int) (Math.random() * 10);
			deltaY = -5 + (int) (Math.random() * 10);
			this.nextX += deltaX;
			this.nextY += deltaY;
			
			
		}
	}

}
