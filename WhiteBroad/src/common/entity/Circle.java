package common.entity;

import java.awt.*;
import java.io.Serializable;

public class Circle implements Serializable {
    private static final long serialVersionUID = 1820192762144114657L;

    public int x1,y1,a,b;
    private Color color;

    public Circle(int x1, int y1, int a, int b, Color color) {
        this.x1 = x1;
        this.y1 = y1;
        this.a = a;
        this.b = b;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
