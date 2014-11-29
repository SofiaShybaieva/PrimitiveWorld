package primitiveWorld.engine;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import primitiveWorld.engine.event.Event;
import primitiveWorld.interfaces.EventListener;

public class EventCollector {
	private static EventCollector eventCollector = null;
	static Set<EventSubscriber> listeners = null;
	static LinkedList<Event> eventsQueue = null;

	// Make constructor private to block default instantiation
	private EventCollector() {

		// Organize subscriber to a set using HashSet class
		// It represents set of objects, meaning each element can only exists
		// once in a Set.
		// see examples at http://tutorials.jenkov.com/java-collections/set.html
		listeners = new HashSet<EventSubscriber>();

		// LinkedList is a good implementation of the queue.
		// A queue is designed to have elements inserted at the end of the
		// queue, and elements removed from the beginning of the queue. Just
		// like a queue in a supermarket.
		// see examples at
		// http://tutorials.jenkov.com/java-collections/queue.html
		eventsQueue = new LinkedList<Event>();
	}

	// Singleton implementation
	public static EventCollector getEventCollector() {
		if (eventCollector == null)
			eventCollector = new EventCollector();
		return eventCollector;
	}

	// Add new listener to the set of listeners
	public static void addEventListener(EventListener object, String eventName) {
		listeners.add(new EventSubscriber(object, eventName));
	}

	public static void removeEventListener(EventListener object,
			String eventName) {
		// access via for-each
		for (Object element : listeners) {
			// next subscriber object
			EventSubscriber subscriber = (EventSubscriber) element;

			// remove when it's matching the listener-event tuple
			if (subscriber.listener.equals(object)
					&& subscriber.eventName.equals(eventName)) {
				listeners.remove(subscriber);
			}
		}
	}

	// Add new event to the queue
	public static void addEvent(Event event) {
		eventsQueue.add(event);
	}

	public Event getEvent() {
		if (eventsQueue.isEmpty())
			return null;
		Event firstEvent = eventsQueue.remove(); // retrieves and removes
													// the head
		return firstEvent;
	}

	public static void eventOccurred(Event event) {
		String eventName = event.getName();

		// access via for-each
		for (Object element : listeners) {
			// next subscriber object
			EventSubscriber subscriber = (EventSubscriber) element;

			// call eventOccured when it's been subscribed
			if (subscriber.eventName.equals(eventName)) {

				subscriber.listener.eventOccurred(event);
			}
		}
	}
}
