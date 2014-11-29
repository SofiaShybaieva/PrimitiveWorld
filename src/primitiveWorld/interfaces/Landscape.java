package primitiveWorld.interfaces;

import java.awt.Graphics;
import java.awt.Point;

public interface Landscape {
    
    void draw(Graphics g, Point coord);
    
    boolean isCanEnterOn(Movable traveler);
    
    boolean isCanStayOn(Movable traveler);
    
}
