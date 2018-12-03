package mastermind.gui;

import javax.swing.*;
import java.awt.*;

public class ColorButton extends JButton {
    public final int index;

    public ColorButton(int theIndex,Color theColor)
    {

        index = theIndex;
        setPreferredSize(new Dimension(30, 30));
        setBackground(theColor);
        setOpaque(true);
        setBorderPainted(false);
    }
}
