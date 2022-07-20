package hotel.reservations.views.styles;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedTextField extends JTextField {
    public RoundedTextField(int columns){
        super(columns);
    }
    private Shape shape;
    @Override
    protected void paintComponent(Graphics g){
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth(), getHeight(), (int) (getWidth() * 0.09), getHeight() - 1);
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g){
        g.setColor(getForeground());
        g.drawRoundRect(0, 0, getWidth(), getHeight(), (int) (getWidth() * 0.09), getHeight() - 1);
    }

    @Override
    public boolean contains(int x, int y){
        if(shape == null || !shape.getBounds().equals(getBounds())){
            shape = new RoundRectangle2D.Float(0,0,getWidth(), getHeight(), (int) (getWidth() * 0.09),
                    getHeight() - 1);
        }
        return shape.contains(x, y);
    }
}
