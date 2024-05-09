package common.entity;



import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;

public class Ellipse implements Serializable {
    private static final long serialVersionUID = 1820192762144114657L;

    private Ellipse2D ellipse2D;
    private Color color;

    private float f;

    public Ellipse(Ellipse2D ellipse2D, Color color, float f) {
        this.ellipse2D = ellipse2D;
        this.color = color;
        this.f = f;
    }

    public Ellipse2D getEllipse2D() {
        return ellipse2D;
    }

    public void setEllipse2D(Ellipse2D ellipse2D) {
        this.ellipse2D = ellipse2D;
    }

    public float getF() {
        return f;
    }

    public void setF(float f) {
        this.f = f;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
