package primitiveWorld.localObjects;

import java.awt.Color;
import java.awt.Dimension;
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
import primitiveWorld.interfaces.LocalObject;
import primitiveWorld.interfaces.Movable;
import primitiveWorld.interfaces.Tight;
import primitiveWorld.interfaces.Visible;

public class Shuttle implements Movable, Drawable, EventListener, Visible,
		Tight {
	private Dimension size = new Dimension(20, 20);
	private Point coord;
	private Image image;
	private File file = new File("shuttle2.png");
	private String passRights = "";
	private int nextX;
	private int nextY;
	private int targetX, targetY;
	boolean isUnderControl;
	private int visibility = 0;

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

	public Shuttle() {
		init();
	}

	public Shuttle(int x, int y) {
		init();
		this.coord.setLocation(x, y);
	}

	@Override
	public String getTypeName() {

		return "Shuttle";
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
			System.err.println("Image Shuttle not found!");
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
				this.changeUnderControl();

			break;
		}

	}

	private void changeUnderControl() {
		this.isUnderControl = !this.isUnderControl;
		if (this.isUnderControl)
			this.visibility = 100;
		else
			this.visibility = 0;
		// System.err.println("UnderControl=" + this.isUnderControl +
		// ", visibility=" + this.visibility);
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
	public Dimension getSize() {
		// TODO Auto-generated method stub
		return new Dimension(20, 20);
	}

	@Override
	public void setSize(Dimension size) {
		// TODO Auto-generated method stub

	}

	@Override
	public void touch(LocalObject object) {
		if (object.getTypeName().equals("HomoSapiens")) {
			// bounce away from other homo sapiens
			int deltaX, deltaY;
			deltaX = -5 + (int) (Math.random() * 10);
			deltaY = -5 + (int) (Math.random() * 10);
			this.nextX += deltaX;
			this.nextY += deltaY;
		}

	}

}
