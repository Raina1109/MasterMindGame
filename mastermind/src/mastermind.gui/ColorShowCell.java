package mastermind.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ColorShowCell extends JLabel {
    public final int row;
    public final int column;

    public ColorShowCell(int theRow, int theColumn) {
        row = theRow;
        column = theColumn;
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        setBorder(border);
        setMinimumSize(new Dimension(30, 30));
        setPreferredSize(new Dimension(30, 30));
        setMaximumSize(new Dimension(30, 30));
        setBackground(Color.white);
        setOpaque(true);
    }

    public void changeColor(Color theColor) {
        setBackground(theColor);
    }
}
