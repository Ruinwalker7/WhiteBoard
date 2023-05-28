package common.util;

import javax.swing.*;
import java.awt.*;

public class WhiteBroad extends JPanel implements Runnable {

    private static final long serialVersionUID = 1141241251L;

    public void run()   //进程run()方法重写
    {
        this.setBackground(new Color(255,255,255));
        Graphics g=this.getGraphics();   //Graphics对象 g的获取
        System.out.println(g);
        DrawListener dl = new DrawListener(g,this);// 实例化DrawListener类的对象
        this.addMouseListener(dl);// 为窗体添加鼠标事件监听方法
        this.addMouseMotionListener(dl);// 为窗体添加鼠标移动事件监听方法
    }

}
