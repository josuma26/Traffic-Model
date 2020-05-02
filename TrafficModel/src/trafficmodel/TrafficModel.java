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
        
        
        //runGraphics(new SimpleTraffic());
        //runGraphics(new RunIntersection());
        //runGraphics(new FourIntersections(3,40,25,0,0,300,50,100,28,30));
        runGraphics(new MemorialDriveSimulation());   
        
                
        
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
class Test extends Component{
    public Test(){
        
    }
    
    @Override
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.yellow);
        AffineTransform r = new AffineTransform();
        int x = 10,y=10;
        r.setToRotation(Math.PI/2, x + 6, y);
        Rectangle2D rect = new Rectangle2D.Double(700,700,50,200);
        Shape s = r.createTransformedShape(rect);
        g2.fill(s);
    }
}