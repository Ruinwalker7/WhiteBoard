package client.ui;

import client.DataBuffer;
import common.entity.Ellipse;
import common.entity.Line;

import javax.swing.*;
import java.awt.*;

public class WhiteBroad extends JPanel  {

    private static final long serialVersionUID = 1141241251L;

//    public void run()   //进程run()方法重写
//    {
    WhiteBroad(){
        this.setBackground(new Color(255,255,255));//Graphics对象 g的获取
    }

    public void paint(Graphics g) {
        super.paint(g);

        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON); //清除锯齿

        Graphics2D g2 = (Graphics2D)g;

        for(int i = 0; i< DataBuffer.LineList.size(); i++){ // 重新绘制
            Line line = DataBuffer.LineList.get(i);
            g2.setColor(line.getColor());
            g2.setStroke(new BasicStroke(line.getF()));
            g2.draw(line.getLine2D());
        }

        for(int i = 0; i< DataBuffer.ellipseList.size(); i++){ // 重新绘制
            Ellipse ellipse = DataBuffer.ellipseList.get(i);
            g2.setStroke(new BasicStroke(ellipse.getF()));
            g2.setColor(ellipse.getColor());
            g2.draw(ellipse.getEllipse2D());
        }

    }
}
