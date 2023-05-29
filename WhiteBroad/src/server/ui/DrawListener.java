package server.ui;

import common.entity.Circle;
import common.entity.Line;
import common.entity.Response;
import common.entity.ResponseType;
import server.DataBuffer;
import server.ServerUtil;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.*;

public class DrawListener implements MouseListener, MouseMotionListener,
        ActionListener {

    static private int x1, y1, x2, y2,x3,y3,now;// 记录两次鼠标的点击坐标
    static private Graphics g;// 从界面获取画布对象
    static private String type;// 记录当前按钮的信息，区分不同的按钮
    static private Color color;// 记录画笔的颜色信息
    private JPanel jf;
    public DrawListener(Graphics g, JPanel jf) {
        now = 0;
        type = "直线";
        this.g = g;
        this.jf = jf;
    }

    public static void setColor(Color c){
        color=c;
    }

    public static void setType(String str){
        type=str;
        x1=y1=y2=x2=0;
        now = 0;
    }

    //鼠标按下时的处理方法
    public void mousePressed(MouseEvent e) {
        // 记录第一次点击的位置；由对象e得到
        if("直线".equals(type)){
            x1 = e.getX();
            y1 = e.getY();
        } else if ("曲线".equals(type)) {
            x1 = e.getX();
            y1 = e.getY();
        }
        else if("椭圆".equals(type)){
            x1 = e.getX();
            y1 = e.getY();
        }
        else if("三角形".equals(type)&&now%3==0){
            x1 = e.getX();
            y1 = e.getY();
            now++;
        }
        else if ("三角形".equals(type)&&now%3 == 1) {
            x2 = e.getX();
            y2 = e.getY();
            now++;
        }
        else if("三角形".equals(type)&&now%3==2){
            x3=e.getX();
            y3=e.getY();
            now++;
            drawLine(x1,y1,x2,y2,color);
            drawLine(x2,y2,x3,y3,color);
            drawLine(x3,y3,x1,y1,color);
        }
    }

    //鼠标点击时的处理方法
    public void mouseClicked(MouseEvent e) {

    }

    // 鼠标释放时的处理方法
    public void mouseReleased(MouseEvent e) {
        // 两个坐标的得到了，可以用于直线的绘制，调用画布对象g方法，在界面上面画出直线
        if ("直线".equals(type)) {
            x2 = e.getX();
            y2 = e.getY();
            drawLine(x1, y1, x2, y2,color);
        }        // 记录鼠标释放时的坐标
        else if ("椭圆".equals(type)) {
            x2 = e.getX();
            y2 = e.getY();
            int d = (int)Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
            drawOval(x1, y1, d, d,color);
        }
    }

    // 鼠标进入时的处理方法
    public void mouseEntered(MouseEvent e) {

    }

    // 鼠标退出时的处理方法
    public void mouseExited(MouseEvent e) {

    }


    // 鼠标拖动时的处理方法
    public void mouseDragged(MouseEvent e) {
        if ("曲线".equals(type)) {
            int x, y;
            x = e.getX();
            y = e.getY();
            drawLine(x1, y1, x, y,color);
            x1 = x;
            y1 = y;
        }
    }

    // 鼠标释放时的移动方法
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void drawLine(int x1,int y1,int x2,int y2,Color color){
        g.setColor(color);
        g.drawLine(x1, y1, x2, y2);
        Line line = new Line(x1,y1,x2,y2,color);
        DataBuffer.LineList.add(line);
        try{
            Response response = new Response();
            response.setType(ResponseType.LINE);
            response.setData("line",line);
            ServerUtil.iteratorResponse(response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void drawOval(int x1,int y1,int a,int b,Color color){
        g.setColor(color);
        g.drawOval(x1, y1, a, b);
        Circle circle = new Circle(x1,y1,a,b,color);
        DataBuffer.CircleList.add(circle);
        try{
            Response response = new Response();
            response.setType(ResponseType.OVAL);
            response.setData("circle",circle);
            ServerUtil.iteratorResponse(response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}