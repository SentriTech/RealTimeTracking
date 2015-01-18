package com.sentri.ui;

import com.google.gson.Gson;
import com.sentri.model.Particle;
import com.sentri.model.Prediction;
import com.sentri.service.DataHolder;
import com.sentri.service.TrackSystem;
import com.sentri.utils.MatlabHelper;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.AttributedCharacterIterator;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

/**
 * Created by sanjun.yyj on 11/29/14.
 */

public class MainCanvas extends JFrame {
    public static void main(String[] args) {
        DataHolder dh = DataHolder.getInstance();
        try {
            Map<String, String> configMap = new HashMap<String, String>();
            //String configFile = args[0];
            String configFile = "/Users/sanjun.yyj/Develop/workspace/sentri/RealTimeTracking/new_config.prop";
            File file=new File(configFile);
            if(file.exists()||!file.isDirectory()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String temp = null;
                StringBuffer sb = new StringBuffer();
                temp = br.readLine();
                while(temp != null) {
                    String[] parts = temp.split("=", -1);
                    configMap.put(parts[0], parts[1]);
                    temp=br.readLine();
                }
            }
            System.out.println(new Gson().toJson(configMap));
            dh.init(configMap);
        } catch (Exception e) {
            System.out.println("get config error");
        }

        TrackSystem trackSystem = new TrackSystem();
        trackSystem.initSystem();
        System.out.println(new Gson().toJson(dh.network.getNodes()));
        System.out.println(new Gson().toJson(dh.network.getRssEmpty()));
        int k = 0;
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 24; j++) {
                if (dh.network.getAbnormalLinks()[j][i] == 1) {
                    int id = j-i;
                    for (int t = 0; t < i; t++) {
                        id += 23-t;
                    }
                    System.out.println((k++) + "," + id + "," + j + "," + i);
                }
            }
        }
        MainCanvas frame = new MainCanvas();

        for (int round = 0; round < dh.trackConfig.getNumRound(); round++) {
            System.out.println("=====round " + round + " start======");
            trackSystem.track();
            frame.refresh();
        }
    }

    public MainCanvas() {
        refresh();
    }

    public void refresh() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("果壳安防跟踪系统");
        setBounds(100, 100, 1000, 1000);

        JPanel interfacePanel = new JPanel();
        interfacePanel.setLayout(new BorderLayout());

        JMenuBar mainMenuBar = new JMenuBar();
        mainMenuBar.setBounds(0,0, 20, 400);

        JMenu configMenu = new JMenu();
        configMenu.setText("setting");
        configMenu.setFont(new Font("Dialog", 0, 12));
        JMenuItem itemConfig = new JMenuItem("model setting");
        configMenu.add(itemConfig);
        mainMenuBar.add(configMenu);

        JMenu aboutMenu = new JMenu();
        aboutMenu.setText("about");
        aboutMenu.setFont(new Font("Dialog", 0, 12));
        JMenuItem itemAbout = new JMenuItem("about");
        aboutMenu.add(itemAbout);
        mainMenuBar.add(aboutMenu);

        JPanel graphPanel = new JPanel();
        graphPanel.setLayout(new BorderLayout(10, 10));
        graphPanel.setBounds(0, 0, 800, 800);

        JComponent graph  = new JComponent() {
            public void paintComponent(Graphics g) {
                Graphics2D g2=(Graphics2D)g;
                double x=100;
                double y=100;
                double w=800;
                double h=800;

                /*
                Rectangle2D rect=new Rectangle2D.Double(x,y,w,h);
                g2.setPaint(Color.black);
                g2.draw(rect);

                Ellipse2D ellipse = new Ellipse2D.Double();
                ellipse.setFrame(rect);
                g2.draw(ellipse);

                Point2D p1=new Point2D.Double(x-40,y-30);
                Point2D p2=new Point2D.Double(x+w+40,y+h+30);
                g2.draw(new Line2D.Double(p1,p2));

                double centerx=rect.getCenterX();
                double centery=rect.getCenterY();
                double radius=150;

                Ellipse2D circle=new Ellipse2D.Double();
                circle.setFrameFromCenter(centerx,centery,centerx+2,centery+2);
                g2.draw(circle);
                */
                double prevX = 0;
                double prevY = DataHolder.getInstance().siteConfig.getWidth()*100;
                for (Prediction prediction : DataHolder.getInstance().targets.get(0).getPredictions()) {
                    Ellipse2D circle = new Ellipse2D.Double();
                    double currX = prediction.getLocation().getX()*100;
                    double currY = (DataHolder.getInstance().siteConfig.getWidth()-prediction.getLocation().getY())*100;
                    circle.setFrameFromCenter(currX,currY,currX+2,currY+2);
                    g2.draw(circle);

                    Point2D p1=new Point2D.Double(prevX,prevY);
                    Point2D p2=new Point2D.Double(currX,currY);
                    g2.draw(new Line2D.Double(p1,p2));
                    prevX = currX;
                    prevY = currY;
                }

                /*
                //for (Particle particle : DataHolder.getInstance().targets.get(0).getCurrPrediction().getParticles()) {
                for (Particle particle : DataHolder.getInstance().particles) {
                    Ellipse2D circle = new Ellipse2D.Double();
                    double pX = particle.getLocation().getX()*100;
                    double pY = (DataHolder.getInstance().siteConfig.getWidth()-particle.getLocation().getY())*100;
                    circle.setFrameFromCenter(pX,pY,pX+1,pY+1);
                    g2.draw(circle);
                }

                for (Particle particle : DataHolder.getInstance().targets.get(0).getCurrPrediction().getParticles()) {
                    Ellipse2D circle = new Ellipse2D.Double();
                    double pX = particle.getLocation().getX()*100;
                    double pY = (DataHolder.getInstance().siteConfig.getWidth()-particle.getLocation().getY())*100;
                    circle.setFrameFromCenter(pX,pY,pX+20,pY+20);
                    g2.draw(circle);
                }
                */
            }
        };
        graph.setVisible(true);
        graphPanel.add(graph);
        graphPanel.setVisible(true);

        interfacePanel.add(mainMenuBar, BorderLayout.NORTH);
        interfacePanel.add(graphPanel);
        interfacePanel.setVisible(true);
        this.add(interfacePanel);

        this.setVisible(true);
    }
}
