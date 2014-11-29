package primitiveWorld.interfaces;

import java.awt.Point;

public interface Movable extends Active{

    Point getStepTarget();

    void moveTo(Point coord);
    
    String getPassRights();

    void setPassRights(String passRights);
    
}
