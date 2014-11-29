package primitiveWorld.interfaces;

import java.awt.Point;

public interface LocalObject {

    String getTypeName();

    Point getCoordinate();

    void setCoordinate(Point coord);

    boolean is(String ability);
}
