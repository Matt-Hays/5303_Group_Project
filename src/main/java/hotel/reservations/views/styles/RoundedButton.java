/**
 * @file RoundedButton.java
 * @author Matthew Hays
 * @brief A JButton wrapper class that overrides the default look of the JButton.
 *        Provides a rounded button that conforms to theme styles.
 * @dependencies JButton
 */

package hotel.reservations.views.styles;

import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {
    public RoundedButton(String text){
        super(text);

        setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.decode("#4183C4"));
        setForeground(Color.WHITE);
        setBorderPainted(false);
        setPreferredSize(new Dimension(getWidth(), 28));

        g.fillRoundRect(0, 0, getSize().width, getSize().height, 10, 10);

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawRoundRect(-2, -2, getSize().width + 4, getSize().height + 4, 10,10);
    }
}
