package primitiveWorld.engine;

import primitiveWorld.interfaces.EventListener;

public class EventSubscriber {
	public String eventName;
	public EventListener listener;

	public EventSubscriber(EventListener listener, String eventName) {
		this.eventName = eventName;
		this.listener = listener;
	}
}
