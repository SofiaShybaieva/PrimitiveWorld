package primitiveWorld.localObjects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import primitiveWorld.interfaces.Drawable;
import primitiveWorld.interfaces.Movable;

public class Snake implements Movable, Drawable {

	private Point coord;
	private Image image;
	private File file = new File("snake.png");
	private String passRights = "";
	private int nextX;
	private int nextY;
	private int counter;
	private int targetX, targetY;
	private int oldX, oldY;

	// private String ability = "Drawable Active Movable";

	private void init() {
		this.setPassRights("");
		this.coord = new Point();
		this.coord.setLocation(0, 0);
		this.targetX = this.targetY = this.nextX = this.nextY = this.oldX = this.oldY = 0;
		loadImage(file);
		counter = 0;
	}

	public Snake() {
		init();
	}

	public Snake(int x, int y) {
		init();
		this.coord.setLocation(x, y);

	}

	@Override
	public String getTypeName() {

		return "Snake";
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

		// g.setColor(Color.WHITE);
		// g.fillRect(this.coord.x, this.coord.y, 20, 20);
		g.drawImage(image, this.coord.x - 10, this.coord.y - 10, 20, 20, null);
	}

	private void loadImage(File file) {
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			System.err.println("Image Snake not found!");
		}
	}

	@Override
	public void nextStep() {
		// snake doesn't move
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

}
