package primitiveWorld.localObjects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import primitiveWorld.interfaces.Drawable;

public class Totem implements Drawable {// , Movable {
	// Totem-� �� ����� ������������� ��������� Movable

	private Point coord;
	private Image image;
	private File file = new File("totem.png");
        //private String passRights;
	//private int nextX;
	//private int nextY;

	// private String ability = "Drawable";

	public Totem() {
		// this.setPassRights("f");
		this.coord = new Point();
		this.coord.setLocation(0, 0);
		loadImage(file);
	}

	public Totem(int x, int y) {
		// this.setPassRights("f");
		this.coord = new Point();
		this.coord.setLocation(x, y);
		loadImage(file);
	}

	@Override
	public String getTypeName() {
		// TODO Auto-generated method stub
		return "Totem";
	}

	@Override
	public Point getCoordinate() { // ���������� ����������
		return this.coord;
	}

	@Override
	public void setCoordinate(Point coord) { // ������������� ����������
		this.coord.x = coord.x;
		this.coord.y = coord.y;

	}

	@Override
	public boolean is(String ability) {
		if (ability.equals("Drawable"))
			return true;
		// else if (ability.equals("Active"))
		// return true;
		else
			return false;
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(image, this.coord.x - 10, this.coord.y - 10, 20, 20, null);
	}

	private void loadImage(File file) { // ����� �������� �������� ������
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			System.err.println("Image Totem not found!");
		}
	}
	/*
	 * @Override public void nextStep() {
	 * 
	 * // this.nextX = (int) (this.coord.getX() + 5); // this.nextY = (int)
	 * (this.coord.getY() + 5); do { int maximum = 10; int minimum = -5; int
	 * randomNumX = minimum + (int) (Math.random() * maximum); int randomNumY =
	 * minimum + (int) (Math.random() * maximum); this.nextX = (int)
	 * (this.coord.getX() + randomNumX); this.nextY = (int) (this.coord.getY() +
	 * randomNumY); } while (this.nextX < 0 || this.nextY < 0); }
	 * 
	 * @Override public Point getStepTarget() { Point p = new Point(this.nextX,
	 * this.nextY); return p; }
	 * 
	 * @Override public void moveTo(Point coord) { this.coord = coord; }
	 * 
	 * @Override public String getPassRights() { return this.passRights; }
	 * 
	 * @Override public void setPassRights(String passRights) { // :)
	 * this.passRights = passRights;
	 * 
	 * }
	 */
}
