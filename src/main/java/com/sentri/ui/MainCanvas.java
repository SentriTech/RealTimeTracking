package com.sentri.ui;

import com.sentri.model.Particle;
import com.sentri.model.Prediction;
import com.sentri.service.DataHolder;
import com.sentri.service.TrackSystem;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import javax.swing.*;

/**
 * Created by sanjun.yyj on 11/29/14.
 */

public class MainCanvas extends JFrame {
    public static void main(String[] args) {
        TrackSystem trackSystem = new TrackSystem();
        DataHolder dh = DataHolder.getInstance();

        trackSystem.initSystem();
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
    Particle[] particles = new Particle[2000];

    public void refresh() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("果壳安防跟踪系统");
        setBounds(100, 100, 500, 500);

        JPanel interfacePanel = new JPanel();
        interfacePanel.setLayout(new BorderLayout());

        JMenuBar mainMenuBar = new JMenuBar();
        mainMenuBar.setBounds(0,0, 20, 400);

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

        JPanel graphPanel = new JPanel();
        graphPanel.setLayout(new BorderLayout(10, 10));
        graphPanel.setBounds(0, 0, 400, 400);

        JComponent graph  = new JComponent() {
            public void paintComponent(Graphics g) {
                Graphics2D g2=(Graphics2D)g;
                double x=100;
                double y=100;
                double w=400;
                double h=400;

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
                double prevY = 0;
                for (Prediction prediction : DataHolder.getInstance().targets.get(0).getPredictions()) {
                    Ellipse2D circle = new Ellipse2D.Double();
                    double currX = prediction.getLocation().getX()*100;
                    double currY = prediction.getLocation().getY()*100;
                    circle.setFrameFromCenter(currX,currY,currX+2,currY+2);
                    g2.draw(circle);

                    Point2D p1=new Point2D.Double(prevX,prevY);
                    Point2D p2=new Point2D.Double(currX,currY);
                    g2.draw(new Line2D.Double(p1,p2));
                    prevX = currX;
                    prevY = currY;
                }

                for (Particle particle : DataHolder.getInstance().targets.get(0).getCurrPrediction().getParticles()) {
                    Ellipse2D circle = new Ellipse2D.Double();
                    double pX = particle.getLocation().getX()*100;
                    double pY = particle.getLocation().getY()*100;
                    circle.setFrameFromCenter(pX,pY,pX+1,pY+1);
                    g2.draw(circle);
                }
                /*
                if (DataHolder.getInstance().targets.get(0).getPredictions().size() == 61) {
                    for (int t = 0; t < 2000; t++) {
                        particles[t] = DataHolder.getInstance().particles[t].clone();
                    }
                }
                if (DataHolder.getInstance().targets.get(0).getPredictions().size() > 60) {

                    for (Particle particle : particles) {
                        Ellipse2D circle = new Ellipse2D.Double();
                        double pX = particle.getLocation().getX()*100;
                        double pY = particle.getLocation().getY()*100;
                        circle.setFrameFromCenter(pX,pY,pX+1,pY+2);
                        g2.draw(circle);
                    }
                }*/
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
