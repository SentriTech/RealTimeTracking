package com.sentri.ui;

import com.sentri.service.DataHolder;
import com.sentri.service.TrackSystem;
import java.awt.*;
import javax.swing.*;

/**
 * Created by sanjun.yyj on 11/29/14.
 */

public class MainCanvas {
    public static void main(String[] args) {
        TrackSystem trackSystem = new TrackSystem();
        DataHolder dh = DataHolder.getInstance();

        //trackSystem.initSystem();
        //trackSystem.tracking();

        new MainCanvas();

    }

    public MainCanvas() {
        initFrame();
    }

    private void initFrame() {
        JFrame mainFrame = new JFrame("果壳安防跟踪系统");
        mainFrame.setSize(1024, 768);

        Container mainContainer = mainFrame.getContentPane();
        mainContainer.setLayout(new BorderLayout());

        JMenuBar mainMenuBar = new JMenuBar();

        JMenu configMenu = new JMenu();
        configMenu.setText("设置");
        configMenu.setFont(new Font("Dialog", 0, 12));
        JMenuItem itemConfig = new JMenuItem("模型设置");
        configMenu.add(itemConfig);
        mainMenuBar.add(configMenu);

        JMenu aboutMenu = new JMenu();
        aboutMenu.setText("关于");
        aboutMenu.setFont(new Font("Dialog", 0, 12));
        JMenuItem itemAbout = new JMenuItem("关于");
        aboutMenu.add(itemAbout);
        mainMenuBar.add(aboutMenu);

        mainContainer.add(mainMenuBar, "North");

        JTextField username = new JTextField();
        JPasswordField password = new JPasswordField();
        JButton ok = new JButton("确定");
        JButton cancel = new JButton("取消");

        //中部表单
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(null);
        JLabel l1 = new JLabel("用户名:");
        l1.setBounds(50, 20, 50, 20);
        JLabel l2 = new JLabel("密    码:");
        l2.setBounds(50, 60, 50, 20);
        fieldPanel.add(l1);
        fieldPanel.add(l2);
        username.setBounds(110, 20, 120, 20);
        password.setBounds(110, 60, 120, 20);
        fieldPanel.add(username);
        fieldPanel.add(password);
        mainContainer.add(fieldPanel, "Center");

        //底部按钮
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(ok);
        buttonPanel.add(cancel);
        mainContainer.add(buttonPanel, "South");

        mainFrame.setVisible(true);

    }
}
