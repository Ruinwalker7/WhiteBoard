package client.ui;

import client.ClientThread;
import client.DataBuffer;
import client.model.entity.MyCellRenderer;
import client.model.entity.OnlineUserListModel;
import client.util.ClientUtil;
import common.entity.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

import static javax.swing.JOptionPane.showInputDialog;

public class ClientFrame extends JFrame {
    /**聊天信息列表区域*/
    public static JTextArea msgListArea;
    /**白板*/
    public static WhiteBroad whiteBroad;
    /**要发送的信息区域*/
    public static JTextArea sendArea;
    /** 在线用户列表 */
    public static JList onlineList;
    public JCheckBox rybqBtn;
    public ClientFrame(){
        login();
        init();
        setVisible(true);
        DataBuffer.g=(Graphics2D) whiteBroad.getGraphics();
    }

    public void login(){
        String name = showInputDialog(null, "请输入姓名");
        User user = new User(name);
        Response response;
        try {
            Request request = new Request();
            request.setAction("Login");
            request.setAttribute("user",user);
            response = ClientUtil.sendTextRequest(request);
            DataBuffer.currentUser = new User(name);
            DataBuffer.onlineUsers = (List<User>)response.getData("onlineUsers");
            DataBuffer.ellipseList = (ArrayList<Ellipse>) response.getData("Ellipse");
            DataBuffer.LineList = (ArrayList<Line>) response.getData("Line");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

    }

    // 界面初始化方法
    public void init() {
        this.setTitle("学生端");//设置服务器启动标题
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

        JPanel whiteBroadPane = new JPanel();
        whiteBroadPane.setLayout(new BorderLayout());


        whiteBroad = new WhiteBroad();

        whiteBroadPane.add(whiteBroad);


        drawPane.add(whiteBroadPane);

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
        rybqBtn = new JCheckBox("私聊老师");
        btn2Panel.add(rybqBtn);
        JButton saveBtn = new JButton("保存");
        saveBtn.setToolTipText("保存画板内容");
        btn2Panel.add(saveBtn);
        JButton closeBtn = new JButton("关闭");
        closeBtn.setToolTipText("退出整个程序");
;
        btn2Panel.add(closeBtn);
        JButton submitBtn = new JButton("发送");
        submitBtn.setToolTipText("按Enter键发送消息");
        btn2Panel.add(submitBtn);


        //发送文本区
        sendArea = new JTextArea();
        sendArea.setLineWrap(true);
        sendArea.setFont(font);


        //发送panel
        JPanel sendPanel = new JPanel();        //私聊按钮


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
        saveBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }});
        //关闭窗口
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                logout();
            }
        });

        closeBtn.addActionListener(event -> logout());

        submitBtn.addActionListener(event -> sendTxtMsg());

        new ClientThread(this).start();
    }
    /** 保存图片*/
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

    /** 发送文本消息 */
    public void sendTxtMsg(){
        String content = sendArea.getText();
        if ("".equals(content)) { //无内容
            JOptionPane.showMessageDialog(ClientFrame.this, "不能发送空消息!",
                    "不能发送", JOptionPane.ERROR_MESSAGE);
        } else { //发送
//            User selectedUser = (User)onlineList.getSelectedValue();
            Message msg = new Message();

            if(rybqBtn.isSelected()){
                msg.setToTeacher(true);
            }else {
                msg.setToTeacher(false);
            }

            msg.setFromUser(DataBuffer.currentUser);
            msg.setSendTime(new Date());

            DateFormat df = new SimpleDateFormat("HH:mm:ss");
            StringBuffer sb = new StringBuffer();
            sb.append(" ").append(df.format(msg.getSendTime())).append(" ")
                    .append(DataBuffer.currentUser.getNickname()).append(" ");

            StringBuffer sb2 = new StringBuffer();
            sb2.append(" ").append(df.format(msg.getSendTime())).append(" ")
                    .append("你 ");

            if(rybqBtn.isSelected()){
                sb.append("私信你说");
            }else {
                sb.append("对大家说");
            }


            sb2.append("对大家说");

            sb.append("\n  ").append(content).append("\n");
            sb2.append("\n  ").append(content).append("\n");
            msg.setMessage(sb.toString());

//            msg
            try {
                Request request = new Request();
                request.setAction("chat");
                request.setAttribute("msg", msg);
                ClientUtil.sendTextRequestWithoutReceive(request);
            } catch (IOException e) {
                e.printStackTrace();}


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
            ClientUtil.appendTxt2MsgListArea(msg.getMessage());
        }
    }


    /** 关闭客户端 */
    private void logout() {
        int select = JOptionPane.showConfirmDialog(ClientFrame.this,
                "确定退出吗？\n\n退出程序将中断与服务器的连接!", "退出聊天室",
                JOptionPane.YES_NO_OPTION);
        if (select == JOptionPane.YES_OPTION) {
            Request req = new Request();
            req.setAction("exit");
            req.setAttribute("user", DataBuffer.currentUser);
            try {
                ClientUtil.sendTextRequest(req);
            } catch (IOException ex) {
                ex.printStackTrace();
            }finally{
                System.exit(0);
            }
        }else{
            this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }

}