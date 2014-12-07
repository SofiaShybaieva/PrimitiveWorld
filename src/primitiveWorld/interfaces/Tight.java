package primitiveWorld.interfaces;

import java.awt.Dimension;

public interface Tight extends LocalObject{
    
    Dimension getSize();
    
    void setSize(Dimension size);
    
    void touch(LocalObject object);
    
}
