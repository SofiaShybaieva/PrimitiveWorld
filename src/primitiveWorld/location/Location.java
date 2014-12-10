package primitiveWorld.location;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import primitiveWorld.engine.EventCollector;
import primitiveWorld.engine.event.CommandEvent;
import primitiveWorld.engine.event.Event;
import primitiveWorld.engine.event.GlobalEvent;
import primitiveWorld.interfaces.Drawable;
import primitiveWorld.interfaces.EventListener;
import primitiveWorld.interfaces.Landscape;
import primitiveWorld.interfaces.LocalObject;
import primitiveWorld.interfaces.Movable;
import primitiveWorld.landscapes.Forest; 
import primitiveWorld.landscapes.Water;

public class Location implements EventListener {

	
	public Landscape area[][] = null;

	int x = 0, y = 0;

	public Location(int y, int x) {
		area = new Landscape[y][x];
		this.x = x;
		this.y = y;
		
		EventCollector.addEventListener(this, "comand.removeLocalObject");
	}

	public boolean isCanMove(Movable traveler) {
		Point coordLocalObject = traveler.getStepTarget();

		int i, j;
		int delta = 20 / 2;

		if ((coordLocalObject.x - delta < 0)
				|| (coordLocalObject.y - delta < 0)
				|| ((coordLocalObject.x + delta) > (x * 20))
				|| ((coordLocalObject.y + delta) > (y * 20)))
			return false;

		i = (coordLocalObject.y - delta) / 20;
		j = (coordLocalObject.x - delta) / 20;
		if (area[i][j] != null && !area[i][j].isCanEnterOn(traveler))
			return false;

		i = (coordLocalObject.y - delta) / 20;
		j = (coordLocalObject.x + delta - 1) / 20;
		if (area[i][j] != null && !area[i][j].isCanEnterOn(traveler))
			return false;

		i = (coordLocalObject.y + delta - 1) / 20;
		j = (coordLocalObject.x + delta - 1) / 20;
		if (area[i][j] != null && !area[i][j].isCanEnterOn(traveler))
			return false;

		i = (coordLocalObject.y + delta - 1) / 20;
		j = (coordLocalObject.x - delta) / 20;
		if (area[i][j] != null && !area[i][j].isCanEnterOn(traveler))
			return false;

		i = (coordLocalObject.y) / 20;
		j = (coordLocalObject.x) / 20;
		if (area[i][j] != null && !area[i][j].isCanStayOn(traveler))
			return false;

		return true;

	}

	public void draw(Graphics g) {
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				Point coord = new Point(j * 20, i * 20);
				if ((Landscape) this.area[i][j] != null)
					((Landscape) this.area[i][j]).draw(g, coord);
			}
		}
	}

	@Override
	public void eventOccurred(Event event) {
		if (event.getName().equals("comand.removeLocalObject")) {
			CommandEvent commandEvent = (CommandEvent) event;
			LocalObject obj = (LocalObject) commandEvent.getArgument();
			if (obj.getTypeName().equals("Shuttle")) {
				GlobalEvent globalEvent = new GlobalEvent("Defeat");
				EventCollector.addEvent(globalEvent);
			}
		}

	}
}
