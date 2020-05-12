/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficmodel;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

/**
 *
 * @author jsula
 */
public class TrafficModel {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GUI gui = new GUI();
        
        //runGraphics(new SimpleTraffic());
        //runGraphics(new RunIntersection());
        //runGraphics(new FourIntersections(3,40,50,0,0,300,50,100,28,30));
        //runGraphics(new MemorialDriveSimulation());   
        //runGraphics(new SelfDrivingTest());
                
        
    }
    
    private static void runGraphics(Component model){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame f = new JFrame();
                f.setBounds(0, 0, 1700, 1400);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.add(model);
                f.setVisible(true);
            }
        });
                
    }
    
}
