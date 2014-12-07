package primitiveWorld.engine.event;

public class GlobalEvent implements Event{
    
    protected String message;

    public GlobalEvent(String message) {
        this.message = message;
    }
    
    @Override
    public String getName() {
        return "GlobalEvent";
    }

    @Override
    public EventType getType() {
        return EventType.Global;
    }
    
    public String getMessage() {
        return message;
    }
            
    
}
