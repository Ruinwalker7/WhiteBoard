package server.ui;

import common.entity.User;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import server.DataBuffer;
import server.controller.RequestProcessor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Map;
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
    JButton jb3;
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
        splitPane.setDividerSize(5);
        splitPane.setOneTouchExpandable(false);

        this.add(splitPane, BorderLayout.CENTER);


        //画板工具
        drawLine = new JButton();
        drawLine.setSize(40,40);
        setIcon(this.getClass().getResource("/").getPath()+"\\image\\line.png",drawLine);
        drawLine.setBorder(null);
        drawLine.setContentAreaFilled(false);//除去默认的背景填充

        drawOval = new JButton();
        drawOval.setSize(40,40);
        setIcon(this.getClass().getResource("/").getPath()+"\\image\\oval.png",drawOval);
        drawOval.setBorder(null);
        drawOval.setContentAreaFilled(false);//除去默认的背景填充

        drawArc = new JButton();
        drawArc.setSize(40,40);
        setIcon(this.getClass().getResource("/").getPath()+"\\image\\curve.png",drawArc);
        drawArc.setBorder(null);
        drawArc.setContentAreaFilled(false);//除去默认的背景填充

        drawPolygon = new JButton();
        drawPolygon.setSize(40,40);
        setIcon(this.getClass().getResource("/").getPath()+"\\image\\triangle.png",drawPolygon);
        drawPolygon.setBorder(null);
        drawPolygon.setContentAreaFilled(false);//除去默认的背景填充

        jb2 = new JButton();
        jb1 = new JButton();
        jb3 = new JButton();

        JPanel drawUtilPane = new JPanel();

        FlowLayout f=(FlowLayout)drawUtilPane.getLayout();
        f.setHgap(20);

        JPanel whiteBroadPane = new JPanel();
        whiteBroadPane.setLayout(new BorderLayout());

//        jb1.setUI();
        jb1.setPreferredSize(new Dimension(40, 40));
        jb2.setPreferredSize(new Dimension(40, 40));
        jb3.setPreferredSize(new Dimension(40,40));

        jb2.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.blue));
        jb1.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
        jb3.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));

        drawUtilPane.setBackground(new Color(252,252,252));
        drawUtilPane.add(drawLine);
        drawUtilPane.add(drawOval);
        drawUtilPane.add(drawArc);
        drawUtilPane.add(drawPolygon);
        drawUtilPane.add(jb1);
        drawUtilPane.add(jb2);
        drawUtilPane.add(jb3);

        whiteBroad = new WhiteBroad();

        whiteBroadPane.add(whiteBroad);

        JSplitPane splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                drawUtilPane, whiteBroadPane);
        splitPane1.setDividerLocation(55);
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



        //工具栏响应
        drawLine.setActionCommand("直线");
        drawLine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DrawListener.setType(drawLine.getActionCommand());
            }
        });

        drawPolygon.setActionCommand("三角形");
        drawPolygon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DrawListener.setType(drawPolygon.getActionCommand());
            }
        });

        drawArc.setActionCommand("曲线");
        drawArc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DrawListener.setType(drawArc.getActionCommand());
            }
        });

        drawOval.setActionCommand("椭圆");
        drawOval.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DrawListener.setType(drawOval.getActionCommand());
            }
        });
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                logout();
            }
        });
    }

    public void setIcon(String file,JButton com)
    {
        ImageIcon ico=new ImageIcon(file);
        Image temp=ico.getImage().getScaledInstance(com.getWidth(),com.getHeight(),ico.getImage().SCALE_DEFAULT);
        ico=new ImageIcon(temp);
        com.setIcon(ico);
    }

    /** 关闭服务器 **/
    private void logout() {
//        int select = JOptionPane.showConfirmDialog(ServerFrame.this,
//                "确定关闭吗？\n关闭服务器将中断与所有客户端的连接!",
//                "关闭服务器",
//                JOptionPane.YES_NO_OPTION);
//        //如果用户点击的是关闭服务器按钮时会提示是否确认关闭。
//        if (select == JOptionPane.YES_OPTION) {
//            for(Map.Entry<Long, User> i : DataBuffer.onlineUsersMap.entrySet()){
//                try {
//                    RequestProcessor.remove(i.getValue());
//                }
//                catch (IOException e){
//                    e.printStackTrace();
//                }catch (NullPointerException e){
//                    e.printStackTrace();
//                }
//            }
            System.exit(0);
//        }else{
//            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//        }
//    }
}
}