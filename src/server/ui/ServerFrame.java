package server.ui;

import client.DataBuffer;
import client.model.entity.MyCellRenderer;
import client.model.entity.OnlineUserListModel;
import client.util.ClientUtil;
import common.entity.*;
import server.OnlineClientIOCache;
import server.ServerUtil;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.*;


public class ServerFrame extends JFrame {
    /**聊天信息列表区域*/
    public static JTextArea msgListArea;
    /**白板*/
    public static WhiteBroad whiteBroad;
    /**要发送的信息区域*/
    public static JTextArea sendArea;
    /** 在线用户列表 */
    public static JList onlineList;
    /** 准备发送的文件 */
    public static FileInfo sendFile;
    JButton drawLine;
    JButton drawOval;
    JButton drawArc;
    JButton drawPolygon;
    JButton jb2;
    JButton jb1;
    JButton jb3;
    JButton jb4;
    JButton jb5;
    JButton jb6;
    JButton clearBtn;
    JButton fileBtn;
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
        this.setSize(x-400, y-200);
        this.setLocation((x - this.getWidth()) / 2, (y-this.getHeight())/ 2);

        //绘画主面板
        JPanel drawPane = new JPanel();
        drawPane.setLayout(new BorderLayout());

        //聊天主面板
        JPanel chatPane = new JPanel();
        chatPane.setLayout(new BorderLayout());

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                drawPane, chatPane);
        splitPane.setDividerLocation(1100);
        splitPane.setDividerSize(10);
        splitPane.setEnabled(false);
        splitPane.setOneTouchExpandable(false);

        this.add(splitPane, BorderLayout.CENTER);


        //画板工具
        drawLine = new JButton();
        drawLine.setSize(40,40);
        setIcon(this.getClass().getResource("/").getPath()+"\\image\\line.png",drawLine,40,40);
        drawLine.setBorder(null);
        drawLine.setContentAreaFilled(false);//除去默认的背景填充

        drawOval = new JButton();
        drawOval.setSize(40,40);
        setIcon(this.getClass().getResource("/").getPath()+"\\image\\oval.png",drawOval,35,35);
        drawOval.setBorder(null);
        drawOval.setContentAreaFilled(false);//除去默认的背景填充

        drawArc = new JButton();
        drawArc.setSize(40,40);
        setIcon(this.getClass().getResource("/").getPath()+"\\image\\curve.png",drawArc,40,40);
        drawArc.setBorder(null);
        drawArc.setContentAreaFilled(false);//除去默认的背景填充

        drawPolygon = new JButton();
        drawPolygon.setSize(40,40);
        setIcon(this.getClass().getResource("/").getPath()+"\\image\\triangle.png",drawPolygon,40,40);
        drawPolygon.setBorder(null);
        drawPolygon.setContentAreaFilled(false);//除去默认的背景填充

        jb2 = new JButton();
        jb1 = new JButton();
        jb3 = new JButton();
        jb4 = new JButton();
        jb5 = new JButton();
        jb6 = new JButton();
        fileBtn = new JButton("文件");
        clearBtn = new JButton("清除");

        jb1.setSize(40, 40);
        jb2.setSize(40, 40);
        jb3.setSize(40, 40);
        setIcon(this.getClass().getResource("/").getPath()+"\\image\\Black.png",jb1,30,30);
        setIcon(this.getClass().getResource("/").getPath()+"\\image\\Blue.png",jb2,30,30);
        setIcon(this.getClass().getResource("/").getPath()+"\\image\\Red.png",jb3,30,30);
        jb1.setBorder(null);
        jb2.setBorder(null);
        jb3.setBorder(null);
        jb1.setContentAreaFilled(false);//除去默认的背景填充
        jb2.setContentAreaFilled(false);//除去默认的背景填充
        jb3.setContentAreaFilled(false);//除去默认的背景填充

        jb4.setSize(40, 40);
        jb5.setSize(40, 40);
        jb6.setSize(40, 40);
        setIcon(this.getClass().getResource("/").getPath()+"\\image\\Black.png",jb4,40,40);
        setIcon(this.getClass().getResource("/").getPath()+"\\image\\mid.png",jb5,40,40);
        setIcon(this.getClass().getResource("/").getPath()+"\\image\\tiny.png",jb6,40,40);
        jb4.setBorder(null);
        jb5.setBorder(null);
        jb6.setBorder(null);

        jb4.setContentAreaFilled(false);//除去默认的背景填充
        jb5.setContentAreaFilled(false);//除去默认的背景填充
        jb6.setContentAreaFilled(false);//除去默认的背景填充

        JPanel drawUtilPane = new JPanel();

        FlowLayout f=(FlowLayout)drawUtilPane.getLayout();
        f.setHgap(20);

        JPanel whiteBroadPane = new JPanel();
        whiteBroadPane.setLayout(new BorderLayout());

        drawUtilPane.setBackground(new Color(252,252,252));


        drawUtilPane.add(drawLine);
        drawUtilPane.add(drawOval);
        drawUtilPane.add(drawArc);
        drawUtilPane.add(drawPolygon);
        drawUtilPane.add(jb1);
        drawUtilPane.add(jb2);
        drawUtilPane.add(jb3);
        drawUtilPane.add(jb4);
        drawUtilPane.add(jb5);
        drawUtilPane.add(jb6);
        drawUtilPane.add(clearBtn);

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

        
        sendArea = new JTextArea();
        sendArea.setEditable(true);
        sendArea.setFont(font);

        JPanel btn2Panel = new JPanel();
        btn2Panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.add(btn2Panel, BorderLayout.SOUTH);
        fileBtn.setToolTipText("选择文件发送");
        btn2Panel.add(fileBtn);
        JButton saveBtn = new JButton("保存");
        saveBtn.setToolTipText("保存画板内容");
        btn2Panel.add(saveBtn);
        JButton closeBtn = new JButton("关闭");
        closeBtn.setToolTipText("退出整个程序");
        btn2Panel.add(closeBtn);
        JButton submitBtn = new JButton("发送");
        submitBtn.setToolTipText("按Enter键发送消息");
        btn2Panel.add(submitBtn);


        //发送文本区
        sendArea = new JTextArea();
        sendArea.setLineWrap(true);
        sendArea.setFont(font);


        //发送panel
        JPanel sendPanel = new JPanel();
        sendPanel.setLayout(new BorderLayout());
        sendPanel.add(new JScrollPane(sendArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        sendPanel.add(btn2Panel, BorderLayout.SOUTH);


        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());
        infoPanel.add(msgListArea);

        JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                infoPanel, sendPanel);
        splitPane2.setDividerLocation(600);
        splitPane2.setDividerSize(10);
        splitPane2.setEnabled(false);
        JLabel otherInfoLbl = new JLabel("群聊中...");
        chatPane.add(otherInfoLbl, BorderLayout.NORTH);
        chatPane.add(splitPane2, BorderLayout.CENTER);


        //获取在线用户并缓存
        DataBuffer.onlineUserListModel = new OnlineUserListModel(DataBuffer.onlineUsers);
        //在线用户列表
        onlineList = new JList(DataBuffer.onlineUserListModel);
        onlineList.setCellRenderer(new MyCellRenderer());
        //设置为单选模式
        onlineList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //键盘事件
        sendArea.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == Event.ENTER){
                    sendTxtMsg();
                }
            }
        });

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

        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DrawListener.setColor(new Color(0,0,0));
            }
        });

        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DrawListener.setColor(new Color(70,70,255));
            }
        });

        jb3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DrawListener.setColor(new Color(255,50,50));
            }
        });

        jb4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DrawListener.setStroke(6);
            }
        });

        jb5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DrawListener.setStroke(4);
            }
        });

        jb6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DrawListener.setStroke(2);
            }
        });

        saveBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }});
        clearBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
        }});

        //发送文件
        fileBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                sendFile();
            }
        });
        //关闭服务器事件
        closeBtn.addActionListener(event -> logout());

        submitBtn.addActionListener(event -> sendTxtMsg());
    };

    public void save()  {
        Container content=whiteBroad;
        //创建缓冲图片对象
        BufferedImage img=new BufferedImage(
                whiteBroad.getWidth(),whiteBroad.getHeight(),BufferedImage.TYPE_INT_RGB);
        //得到图形对象
        Graphics2D g2d = img.createGraphics();
        //将窗口内容面板输出到图形对象中
        content.printAll(g2d);
        //保存为图片
        File f=new File("C:\\test\\saveScreen"+".jpg");
        g2d.dispose();

        BufferedImage myImage = null;

        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = jfc.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION){
            try {
                String path = jfc.getSelectedFile().getCanonicalPath();
                path+="/pic.jpg";
                System.out.println(path);
                ImageIO.write(img, "jpg", new File(path));
            }  catch (IOException e) {
                e.printStackTrace();
        }

        }
    }

    public void sendFile(){
        JFileChooser jfc = new JFileChooser();
        if (jfc.showOpenDialog(ServerFrame.this) == JFileChooser.APPROVE_OPTION) {
            File file = jfc.getSelectedFile();
            sendFile = new FileInfo();
            sendFile.setFromUser(DataBuffer.currentUser);
            try {
                sendFile.setSrcName(file.getCanonicalPath());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            sendFile.setSendTime(new Date());

            Response response = new Response();
            response.setType(ResponseType.FILE);
            response.setData("file", sendFile);
            try {
                ServerUtil.iteratorResponse(response);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ServerUtil.appendTxt2MsgListArea("【文件消息】发送文件 ["
                    + file.getName() + "]\n");
        }
    }


    public void clear(){
        server.DataBuffer.LineList.clear();
        server.DataBuffer.ellipseList.clear();
        whiteBroad.paint(whiteBroad.getGraphics());
        try {
            Response response = new Response();
            response.setType(ResponseType.CLEAR);
            ServerUtil.iteratorResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
}

    public void setIcon(String file,JButton com,int x,int y)
    {
        ImageIcon ico=new ImageIcon(file);
        Image temp=ico.getImage().getScaledInstance(x,y,ico.getImage().SCALE_DEFAULT);
        ico=new ImageIcon(temp);
        com.setIcon(ico);
    }

    public void sendTxtMsg(){
        String content = sendArea.getText();
        if ("".equals(content)) { //无内容
            JOptionPane.showMessageDialog(ServerFrame.this, "不能发送空消息!",
                    "不能发送", JOptionPane.ERROR_MESSAGE);
        } else { //发送
            User selectedUser = (User)onlineList.getSelectedValue();
            java.util.List<User> list = new ArrayList<>();
            //如果设置了ToUser表示私聊，否则群聊
            Message msg = new Message();
//            msg.setToUser(selectedUser);

            msg.setFromUser(client.DataBuffer.currentUser);
            msg.setSendTime(new Date());

            DateFormat df = new SimpleDateFormat("HH:mm:ss");
            StringBuffer sb = new StringBuffer();
            sb.append(" ").append(df.format(msg.getSendTime())).append(" ")
                    .append("老师 ");

            StringBuffer sb2 = new StringBuffer();
            sb2.append(" ").append(df.format(msg.getSendTime())).append(" ")
                    .append("你 ");


            sb.append("对大家说");
            sb2.append("对大家说");

            sb.append("\n  ").append(content).append("\n");
            sb2.append("\n  ").append(content).append("\n");
            msg.setMessage(sb.toString());
            try {
                Response response = new Response();
                response.setType(ResponseType.CHAT);
                response.setData("txtMsg", msg);
                response.setStatus(ResponseStatus.OK);
                iteratorResponse(response);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //JTextArea中按“Enter”时，清空内容并回到首行
            InputMap inputMap = sendArea.getInputMap();
            ActionMap actionMap = sendArea.getActionMap();
            Object transferTextActionKey = "TRANSFER_TEXT";
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0),transferTextActionKey);
            actionMap.put(transferTextActionKey, new AbstractAction() {
                private static final long serialVersionUID = 7041841945830590229L;
                public void actionPerformed(ActionEvent e) {
                    sendArea.setText("");
                    sendArea.requestFocus();
                }
            });
            sendArea.setText("");
            msg.setMessage(sb2.toString());
            ServerUtil.appendTxt2MsgListArea(msg.getMessage());
        }
    }

    private void iteratorResponse(Response response) throws IOException {
        for(OnlineClientIOCache onlineUserIO : server.DataBuffer.onlineUserIOCacheMap.values()){
            ObjectOutputStream oos = onlineUserIO.getOos();
            oos.writeObject(response);
            oos.flush();
        }
    }

    /** 关闭服务器 **/
    private void logout() {
        int select = JOptionPane.showConfirmDialog(ServerFrame.this,
                "确定关闭吗？\n关闭服务器将中断与所有客户端的连接!",
                "关闭服务器",
                JOptionPane.YES_NO_OPTION);
//        //如果用户点击的是关闭服务器按钮时会提示是否确认关闭。
        if (select == JOptionPane.YES_OPTION) {
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
        }else{
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        }
//    }
}

}