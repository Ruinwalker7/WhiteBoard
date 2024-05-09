package common.entity;

import java.awt.*;
import java.awt.geom.Line2D;
import java.io.Serializable;

public class Line implements Serializable {

    private static final long serialVersionUID = 1825432075144114657L;

    private Line2D line2D;
    private Color color;
    private float f;

    public Line(Line2D line2D, Color color, float f) {
        this.line2D = line2D;
        this.color = color;
        this.f = f;
    }

    public Line2D getLine2D() {
        return line2D;
    }

    public void setLine2D(Line2D line2D) {
        this.line2D = line2D;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getF() {
        return f;
    }

    public void setF(float f) {
        this.f = f;
    }
}
