package primitiveWorld.localObjects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import primitiveWorld.interfaces.Active;
import primitiveWorld.interfaces.Drawable;

public class CampFire implements Active, Drawable {
	private Point coord;
	private int maxFrames = 4;
	private Image images[];
	private int currentFrame = 0;
	private String passRights;

	// private String ability = "Drawable Active";

	private void loadFrames() {
		images = new Image[maxFrames];
		loadImage(0, "campfire-0.png");
		loadImage(1, "campfire-1.png");
		loadImage(2, "campfire-2.png");
		loadImage(3, "campfire-3.png");

	}

	public CampFire() {
		this.coord = new Point();
		this.coord.setLocation(0, 0);
		loadFrames();
	}

	public CampFire(int x, int y) {

		this.coord = new Point();
		this.coord.setLocation(x, y);
		loadFrames();
	}

	private void loadImage(int frame, String fileName) { // �����
															// ��������
															// ��������

		try {
			File file = new File(fileName);
			images[frame] = ImageIO.read(file);
		} catch (IOException e) {
			System.err.println("Image Totem not found!");
		}
	}

	@Override
	public void nextStep() {
		currentFrame++;
		if (currentFrame >= maxFrames)
			currentFrame = 0;
	}

	@Override
	public String getTypeName() {
		// TODO Auto-generated method stub
		return "CampFire";
	}

	@Override
	public Point getCoordinate() {

		return this.coord;
	}

	@Override
	public void setCoordinate(Point coord) {
		this.coord = coord;

	}

	@Override
	public boolean is(String ability) {
		if (ability.equals("Drawable"))
			return true;
		else if (ability.equals("Active"))
			return true;
		else
			return false;
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(images[currentFrame], this.coord.x - 10, this.coord.y - 10,
				20, 20, null);

	}
	/*
	 * @Override public Point getStepTarget() { // TODO Auto-generated method
	 * stub return null; }
	 * 
	 * @Override public void moveTo(Point coord) { // TODO Auto-generated method
	 * stub
	 * 
	 * }
	 * 
	 * @Override public String getPassRights() {
	 * 
	 * return this.passRights; }
	 * 
	 * @Override public void setPassRights(String passRights) { this.passRights
	 * = passRights;
	 * 
	 * }
	 */
}
