package primitiveWorld.interfaces;

import java.awt.Point;
import java.io.File;
import javax.swing.JPanel;

public interface Enginable {
    
    void setPanel(JPanel panel);

    void loadLocation(File file);

    String nextStep();
    
    void redraw();

    void mousePress(Point coord);

    void mouseMove(Point coord);

}
