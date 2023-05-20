package server.ui;

import java.awt.*;
import javax.swing.JButton;
import javax.swing.JFrame;

public class ServerFrame extends JFrame {

    public ServerFrame(){
        init();
        setVisible(true);
    }
    // 界面初始化方法
    public void init() {
        this.setTitle("服务器");//设置服务器启动标题
        this.setBounds((1920 - 720)/2,
                (720 - 475)/2, 720, 475);
        this.setLayout(new BorderLayout());

        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo(null);

        FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
        this.setLayout(layout);
        JButton drawLine = new JButton("画直线");
        this.add(drawLine);
        JButton drawOval = new JButton("画椭圆");
        this.add(drawOval);
        JButton drawArc = new JButton("画曲线");
        this.add(drawArc);
        JButton drawPolygon = new JButton("三角形");
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

        Graphics g = this.getGraphics();// 获取当前的画笔
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

    }

}