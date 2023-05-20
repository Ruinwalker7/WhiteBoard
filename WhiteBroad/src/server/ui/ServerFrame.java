package server.ui;

import java.awt.*;
import javax.swing.*;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import org.jb2011.lnf.beautyeye.ch1_titlepane.*;

public class ServerFrame extends JFrame {

    public ServerFrame(){
        init();
        setVisible(true);
    }
    // 界面初始化方法
    public void init() {
        this.setTitle("服务器");//设置服务器启动标题
        this.setBounds((1920 - 400)/2,
                (720 - 300)/2, 1200, 1000);
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo(null);

        FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
        this.setLayout(layout);
        JButton drawLine = new JButton("画直线");
        JButton drawOval = new JButton("画椭圆");
        JButton drawArc = new JButton("画曲线");
        JButton drawPolygon = new JButton("三角形");

        this.add(drawLine);
        this.add(drawOval);
        this.add(drawArc);
        this.add(drawPolygon);

        JButton jb1 = new JButton();
        jb1.setBackground(Color.RED);
        this.add(jb1);
        jb1.setPreferredSize(new Dimension(30, 30));
        JButton jb2 = new JButton();
        jb2.setBackground(Color.GREEN);
        this.add(jb2);
        jb2.setPreferredSize(new Dimension(30, 30));

        this.setVisible(true);

        JPanel jf = new JPanel();
        Graphics g = this.getGraphics();// 获取当前的画笔
//        g.setPaintMode();
        jf.setVisible(true);
        this.setIgnoreRepaint(true);
        DrawListener dl = new DrawListener(g);// 实例化DrawListener类的对象
        this.addMouseListener(dl);// 为窗体添加鼠标事件监听方法
        this.addMouseMotionListener(dl);// 为窗体添加鼠标移动事件监听方法

        // 为按钮添加动作监听
        drawLine.addActionListener(dl);
        drawOval.addActionListener(dl);
        jb1.addActionListener(dl);
        jb2.addActionListener(dl);
        drawArc.addActionListener(dl);
        drawPolygon.addActionListener(dl);
        this.add(jf,BorderLayout.SOUTH);
    }

}