package primitiveWorld.engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JPanel;

import primitiveWorld.engine.event.Command;
import primitiveWorld.engine.event.CommandEvent;
import primitiveWorld.engine.event.Event;
import primitiveWorld.engine.event.EventType;
import primitiveWorld.engine.event.FreeEvent;
import primitiveWorld.engine.event.GlobalEvent;
import primitiveWorld.interfaces.Active;
import primitiveWorld.interfaces.Drawable;
import primitiveWorld.interfaces.Enginable;
import primitiveWorld.interfaces.LocalObject;
import primitiveWorld.interfaces.Movable;
import primitiveWorld.interfaces.Tight;
import primitiveWorld.interfaces.Visible;
import primitiveWorld.interfaces.Watcher;
import primitiveWorld.location.Location;
import primitiveWorld.location.LocationLoader;
import primitiveWorld.location.WrongFileFormat;

public class Engine implements Enginable {

	private JPanel panel;
	private Location location;
	private Collection<LocalObject> localObjects;
	private Graphics graphics;
	private BufferedImage bufferedImage;
	public EventCollector eventCollector;
	boolean isLockControl;
	String globalEventsMessages;

	public Engine() {
		// instantiating singleton
		this.eventCollector = EventCollector.getEventCollector();
		this.globalEventsMessages = new String(
				"Welcome to Primitive World v1.2!");
	}

	@Override
	public void setPanel(JPanel panel) {
		this.panel = panel;

		if (panel == null)
			return;
		bufferedImage = new BufferedImage(panel.getWidth(), panel.getHeight(),
				BufferedImage.TYPE_INT_RGB);
	}

	@Override
	public void loadLocation(File file) {
		this.isLockControl = false;
		LocationLoader locationLoader = new LocationLoader();
		locationLoader.eventCollector = this.eventCollector;
		// ensure LocationLoader has the pointer to Engine for
		// firing the global event
		try {
			locationLoader.loadLocation(file);
		} catch (FileNotFoundException | WrongFileFormat e) {
			System.out.println(e.toString());
		}
		Location location = locationLoader.getLocation();
		this.location = location;
		Collection<LocalObject> localObjects = locationLoader.getLocalObjects();
		this.localObjects = localObjects;

	}

	public void redraw() {
		if (location == null || localObjects == null || panel == null) {
			System.err
					.println("Error! Location or Local Objects or Panel is NULL");
			return;
		}

		graphics = bufferedImage.getGraphics();
		// location.draw(this.graphics);
		graphics.setColor(Color.WHITE);

		graphics.fillRect(0, 0, panel.getWidth(), panel.getHeight());
		location.draw(this.graphics);

		for (LocalObject t : this.localObjects) {
			if (t.is("Drawable"))
				((Drawable) t).draw(this.graphics);

		}
		this.drawMessage(this.globalEventsMessages);
		graphics = panel.getGraphics();
		graphics.drawImage(bufferedImage, 0, 0, null);
	}

	// process the queue of events and forward events to the
	private void processEvents() {
		globalEventsMessages = "";
		while (true) {
			Event event = eventCollector.getEvent();
			if (event == null)
				break;

			EventCollector.eventOccurred(event);

			if (event.getType() == EventType.Global) {
				GlobalEvent globalEvent = (GlobalEvent) event;
				globalEventsMessages += globalEvent.getMessage() + "\n";
			}
			if (event.getType() == EventType.Comand) {
				CommandEvent commandEvent = (CommandEvent) event;
				processCommand(commandEvent);
			}
			if (event.getType() == EventType.Free) {
				FreeEvent freeEvent = (FreeEvent) event;
				freeEvent.run();
			}
		}
		// System.out.println("Global Events Messages: " +
		// globalEventsMessages);

//		if (globalEventsMessages.isEmpty() != true) {
//			drawMessage(globalEventsMessages);
//		}

	}

	// nextStep, touch and moveTo for all local objects
	private void moveLocalObjects() {
		for (LocalObject t : this.localObjects) {
			if (t.is("Active") != true)
				continue;
			((Active) t).nextStep();
			if (t.is("Movable") != true)
				continue;
			// lab 11 - check if current object t touches any other object, if
			// so, then do not move

			Point target = ((Movable) t).getStepTarget();
			LocalObject touched = checkTouch((Movable) t, target);
			if (touched != null) // if touch event
			{
				((Tight) touched).touch(t);
				((Tight) t).touch(touched);
			} // else // ДОБАВЛЕНО ТОЛЬКО ELSE!!!

			if (target != t.getCoordinate()) {
				if (location.isCanMove((Movable) t)) {
					((Movable) t).moveTo(((Movable) t).getStepTarget());
				}

			}
		} // for

		// after the objects are moved, process the watchers
		for (LocalObject t0 : this.localObjects) {
			if (t0.is("Watcher") != true) // loop only watchers
				continue;
			int radius = ((Watcher) t0).getContactRadius();
			Point p0 = t0.getCoordinate();
			Collection<Visible> atZone = new ArrayList<Visible>();
			for (LocalObject t : this.localObjects) {
				if (t == t0 || t.is("Visible") == false)
					continue; // skip the self-watch and non-visible objects
				Point p = t.getCoordinate();
				if (p.distance(p0) <= radius && ((Visible)t).getVisibleRating()>0)
					atZone.add((Visible) t);
			}
			((Watcher) t0).atZone(atZone);
		}
	}

	@Override
	public String nextStep() {
		moveLocalObjects();
		processEvents();
		return globalEventsMessages;
	}

	private LocalObject checkTouch(Movable test, Point target) {
		for (LocalObject obj : this.localObjects) {
			if (test == obj || !obj.is("Tight") || !test.is("Tight"))
				continue; // skip self-check and non-tight objects check
			double distance = obj.getCoordinate().distance(target.getX(),
					target.getY());
			if (distance < 20) {
				// System.err.println("Touch event " + test.getTypeName());
				return obj;
			}
		}
		return null;
	}

	private void drawMessage(String message) {
		Graphics g = this.graphics;
		if (g instanceof Graphics2D) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2.drawString(message, 10, 10);
		}
	}

	// process required command
	private void processCommand(CommandEvent event) {
		Command command = event.getCommand();
		switch (command) {
		case loadLocation:
			this.loadLocation((File) event.getArgument());
			break;
		case addLocalObject:
			this.localObjects.add((LocalObject) event.getArgument());
			break;
		case removeLocalObject:
			this.localObjects.remove((LocalObject) event.getArgument());
			break;
		case redraw:
			this.redraw();
			break;
		case lockControl:
			this.isLockControl = !this.isLockControl;
			break;
		case unlockControl:
			this.isLockControl = !this.isLockControl;
			break;
		case mousePress:
			break;
		case mouseMove:
			break;
		default:
			break;
		}

	}

	@Override
	public void mousePress(Point coord) {
		EventCollector.addEvent(new CommandEvent(Command.mousePress, coord));

	}

	@Override
	public void mouseMove(Point coord) {
		EventCollector.addEvent(new CommandEvent(Command.mouseMove, coord));

	}

}
