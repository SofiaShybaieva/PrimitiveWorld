package primitiveWorld.engine.event;

public class CommandEvent implements Event {
    
    protected Command command;
    protected Object argument;

    public CommandEvent(Command comand, Object argument) {
        this.command = comand;
        this.argument = argument;
    }
    
    @Override
    public String getName() {        
        return "comand." + command.name();
    }

    @Override
    public EventType getType() {
        return EventType.Comand;
    }
    
    public Command getCommand() {
        return command;
    }
    
    public Object getArgument() {
        return argument;
    }
    
}
