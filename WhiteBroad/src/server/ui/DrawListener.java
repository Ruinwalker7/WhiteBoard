package server.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.xml.bind.SchemaOutputResolver;

public class DrawListener implements MouseListener, MouseMotionListener,
        ActionListener {

    static private int x1, y1, x2, y2;// 记录两次鼠标的点击坐标
    static private Graphics g;// 从界面获取画布对象
    static private String type;// 记录当前按钮的信息，区分不同的按钮
    static private Color color;// 记录画笔的颜色信息
    static private int f = 1;// 控制变量，用于更新坐标

    public DrawListener(Graphics g) {
        this.g = g;
    }

    public DrawListener() {
    }


    public static void setColor(Color c){
        color=c;
    }

    public static void setType(String str){
        type=str;
    }

    //鼠标按下时的处理方法
    public void mousePressed(MouseEvent e) {
        System.out.println("11111");
        // 记录第一次点击的位置；由对象e得到
        if (f == 1) {
            x1 = e.getX();
            y1 = e.getY();
        }
    }

    //鼠标点击时的处理方法
    public void mouseClicked(MouseEvent e) {
        System.out.println("222");
        if ("三角形".equals(type)) {
            System.out.println("sanjaioxing");
            int x, y;
            x = e.getX();
            y = e.getY();
            g.setColor(color);
            g.drawLine(x, y, x1, y1);
            g.drawLine(x2, y2, x, y);
            f = 1;
        }

    }

    // 鼠标释放时的处理方法
    public void mouseReleased(MouseEvent e) {
        // 记录鼠标释放时的坐标
        if (f == 1) {
            x2 = e.getX();
            y2 = e.getY();
        }
        // 两个坐标的得到了，可以用于直线的绘制，调用画布对象g方法，在界面上面画出直线
        if ("画直线".equals(type)) {
            g.setColor(color);
            g.drawLine(x1, y1, x2, y2);
        }
        if ("画椭圆".equals(type)) {
            g.setColor(color);
            g.drawOval(x1, y1, x2, y2);
        }
        if ("三角形".equals(type) && f == 1) {
            g.setColor(color);
            g.drawLine(x1, y1, x2, y2);
            f++;
        }
    }

    // 鼠标进入时的处理方法
    public void mouseEntered(MouseEvent e) {

    }

    // 鼠标退出时的处理方法
    public void mouseExited(MouseEvent e) {

    }

    public void actionPerformed(ActionEvent e) {
        if ("".equals(e.getActionCommand())) {
            JButton jb = (JButton) e.getSource();
            color = jb.getBackground();
        } else {
            type = e.getActionCommand();
        }
    }

    // 鼠标拖动时的处理方法
    public void mouseDragged(MouseEvent e) {
        if ("画曲线".equals(type)) {
            int x, y;
            x = e.getX();
            y = e.getY();
            g.setColor(color);
            g.drawLine(x1, y1, x, y);
            x1 = x;
            y1 = y;
        }

    }

    // 鼠标释放时的移动方法
    public void mouseMoved(MouseEvent e) {

    }

}