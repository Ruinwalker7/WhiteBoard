package common.entity;

import java.awt.*;
import java.io.Serializable;

public class Line implements Serializable {

    private static final long serialVersionUID = 1825432075144114657L;

    public int x1,x2,y1,y2;
    private Color color;
    private BasicStroke S;

    public Line(int x1, int x2, int y1, int y2, BasicStroke s, Color color) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        S = s;
        this.color = color;
    }



    public Line(int x1, int y1, int x2, int y2, Color color) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.color = color;
    }

    public BasicStroke getS() {
        return S;
    }

    public void setS(BasicStroke s) {
        S = s;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
