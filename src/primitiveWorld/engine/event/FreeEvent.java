package primitiveWorld.engine.event;

public abstract class FreeEvent implements Event{
    
    protected String name = "";

    public FreeEvent(String name) {
        this.name = name;
    }
    
    @Override
    public String getName() {
        return "free." + name;
    }

    @Override
    public EventType getType() {
        return EventType.Free;
    }
    
    public abstract void run();
    
}
