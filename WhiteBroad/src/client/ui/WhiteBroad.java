package client.ui;

import client.DataBuffer;

import javax.swing.*;
import java.awt.*;

public class WhiteBroad extends JPanel  {

    private static final long serialVersionUID = 1141241251L;

//    public void run()   //进程run()方法重写
//    {
    WhiteBroad(){
        this.setBackground(new Color(255,255,255));//Graphics对象 g的获取
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    //        DrawListener dl = new DrawListener(g,this);// 实例化DrawListener类的对象
//        this.addMouseListener(dl);// 为窗体添加鼠标事件监听方法
//        this.addMouseMotionListener(dl);// 为窗体添加鼠标移动事件监听方法
//    }
}
