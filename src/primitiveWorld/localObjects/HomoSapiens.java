package primitiveWorld.localObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import primitiveWorld.engine.EventCollector;
import primitiveWorld.engine.event.CommandEvent;
import primitiveWorld.engine.event.Event;
import primitiveWorld.interfaces.Drawable;
import primitiveWorld.interfaces.EventListener;
import primitiveWorld.interfaces.Movable;

public class HomoSapiens implements Movable, Drawable, EventListener {

	private Point coord;
	private Image image;
	private File file = new File("boy.png");
	private String passRights = "";
	private int nextX;
	private int nextY;
	private int targetX, targetY;
	boolean isUnderControl;

	// private String ability = "Drawable Active Movable";

	private void init() {
		this.isUnderControl = false;
		this.setPassRights("");
		this.coord = new Point();
		this.coord.setLocation(0, 0);
		this.nextX = this.nextY = this.targetX = this.targetY = 0;
		loadImage(file);
		EventCollector.addEventListener(this, "comand.mousePress");
		EventCollector.addEventListener(this, "comand.mouseMove");

	}

	public HomoSapiens() {
		init();
	}

	public HomoSapiens(int x, int y) {
		init();
		this.coord.setLocation(x, y);
	}

	@Override
	public String getTypeName() {

		return "HomoSapiens";
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
		return false;
	}

	@Override
	public void draw(Graphics g) {

		// If the boy is selected (under control) draw a circle to indicate that
		if (this.isUnderControl) {
			g.setColor(Color.WHITE);
			g.drawArc(this.coord.x - 10, this.coord.y - 10, 20, 20, 0, 360);
		}
		g.drawImage(image, this.coord.x - 10, this.coord.y - 10, 20, 20, null);
	}

	private void loadImage(File file) {
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			System.err.println("Image Boy not found!");
		}
	}

	@Override
	public void nextStep() {

		if (this.isUnderControl == false)
			return;

		int step = 2;
		int x0 = (int) (this.coord.getX());
		int y0 = (int) (this.coord.getY());

		int dx = this.targetX - x0;
		int signX = Integer.signum(dx); // get the sign of the dx (1-, 0, or
										// +1)
		int dy = this.targetY - y0;
		int signY = Integer.signum(dy);
		this.nextX = x0 + step * signX;
		this.nextY = y0 + step * signY;

		// int maximum = 10;
		// int minimum = -15;
		// int randomNumX = minimum + (int) (Math.random() * maximum);
		// int randomNumY = 0; // minimum + (int) (Math.random() * maximum);
		// this.nextX = (int) (this.coord.getX() + randomNumX);
		// this.nextY = (int) (this.coord.getY() + randomNumY);
		// this.nextX = 0;
		// this.nextY = 0;
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
	public void eventOccurred(Event event) {
		CommandEvent commandEvent = (CommandEvent) event;
		Point p = (Point) commandEvent.getArgument();
		Point p0 = this.getCoordinate();

		switch (commandEvent.getName()) {
		case "comand.mouseMove":
			if (this.isUnderControl) {
				this.targetX = p.x;
				this.targetY = p.y;
				this.nextStep();
			}
			break;
		case "comand.mousePress":
			if (p.distance(p0.x, p0.y) < 20)
				this.isUnderControl = !this.isUnderControl;

			break;
		}

	}

}
