package primitiveWorld.interfaces;

import java.util.Collection;

public interface Watcher extends LocalObject{
    
    int getContactRadius();
    
    void setContactRadius(int radius);
    
    void atZone(Collection<Visible> objects);
    
}
