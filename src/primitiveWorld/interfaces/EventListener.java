package primitiveWorld.interfaces;

import primitiveWorld.engine.event.Event;

public interface EventListener {
    
    void eventOccurred(Event event);
    
}
