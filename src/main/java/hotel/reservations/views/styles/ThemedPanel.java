/**
 * @file ThemedPanel.java
 * @author Matthew Hays
 * @brief A JPanel wrapper class that overrides the default look of the JPanel.
 *        Provides a top (dark) to bottom (light) gradient background.
 * @dependencies JPanel
 */

package hotel.reservations.views.styles;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class ThemedPanel extends JPanel {

    /**
     * Paint the background with a gradient.
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth(), h = getHeight();
        Color color1 = Color.decode("#202124");
        Color color2 = Color.decode("#3d3d3d");
        final float[] FRACTIONS = {0.0f, 0.7f, 1.0f};
        final Color[] COLORS = {color1, color1, color2};
        MultipleGradientPaint gp = new LinearGradientPaint(new Point2D.Double(w/2, 0), new Point2D.Double(w/2,
                h), FRACTIONS, COLORS);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }
}