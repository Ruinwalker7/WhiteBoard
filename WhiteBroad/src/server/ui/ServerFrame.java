package server.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class ServerFrame extends JFrame {
    /**聊天信息列表区域*/
    public static JTextArea msgListArea;
    /**白板*/
    public static WhiteBroad whiteBroad;
    /**要发送的信息区域*/
    public static JTextArea sendArea;
    JButton drawLine;
    JButton drawOval;
    JButton drawArc;
    JButton drawPolygon;
    JButton jb2;
    JButton jb1;
    public ServerFrame(){
        init();
        setVisible(true);
        Thread t1 = new Thread(whiteBroad);
        t1.start();
    }

    // 界面初始化方法
    public void init() {
        this.setTitle("教师端");//设置服务器启动标题
        this.setResizable(false);

        //设置默认窗体在屏幕中央
        int x = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int y = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setSize(x-400, y-400);
        this.setLocation((x - this.getWidth()) / 2, (y-this.getHeight())/ 2);

        //绘画主面板
        JPanel drawPane = new JPanel();
        drawPane.setLayout(new BorderLayout());

        //聊天主面板
        JPanel chatPane = new JPanel();
        chatPane.setLayout(new BorderLayout());

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                drawPane, chatPane);
        splitPane.setDividerLocation(1000);
        splitPane.setDividerSize(10);
        splitPane.setOneTouchExpandable(false);

        this.add(splitPane, BorderLayout.CENTER);

        //画板工具
         drawLine = new JButton("画直线");
         drawOval = new JButton("画椭圆");
         drawArc = new JButton("画曲线");
         drawPolygon = new JButton("三角形");
         jb2 = new JButton();
         jb1 = new JButton();

        JPanel drawUtilPane = new JPanel();
        JPanel whiteBroadPane = new JPanel();
        whiteBroadPane.setLayout(new BorderLayout());

        jb1.setBackground(Color.RED);
        jb1.setPreferredSize(new Dimension(30, 30));
        jb2.setBackground(Color.GREEN);
        jb2.setPreferredSize(new Dimension(30, 30));

        drawUtilPane.add(drawLine);
        drawUtilPane.add(drawOval);
        drawUtilPane.add(drawArc);
        drawUtilPane.add(drawPolygon);
        drawUtilPane.add(jb1);
        drawUtilPane.add(jb2);

        whiteBroad = new WhiteBroad();

        whiteBroadPane.add(whiteBroad);

        JSplitPane splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                drawUtilPane, whiteBroadPane);
        splitPane1.setDividerLocation(50);
        splitPane1.setEnabled(false);
        splitPane1.setDividerSize(0);

        splitPane1.setOneTouchExpandable(false);

        drawPane.add(splitPane1);

        Font font = new Font("黑体",0,20);
        msgListArea = new JTextArea();
        msgListArea.setLineWrap(true);
        msgListArea.setEditable(false);
        msgListArea.setFont(font);



        JPanel sendPanel = new JPanel();
        sendPanel.setLayout(new BorderLayout());

        sendArea = new JTextArea();
        sendArea.setLineWrap(true);
        sendPanel.add(new JScrollPane(sendArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        sendArea.setFont(font);

        chatPane.add(new JScrollPane(msgListArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));

        drawLine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DrawListener.setType(drawLine.getText());
            }
        });

        drawLine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DrawListener.setType(drawLine.getText());
            }
        });

        drawPolygon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DrawListener.setType(drawPolygon.getText());
            }
        });

        drawArc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DrawListener.setType(drawArc.getText());
            }
        });

        drawOval.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DrawListener.setType(drawOval.getText());
            }
        });

    }


}