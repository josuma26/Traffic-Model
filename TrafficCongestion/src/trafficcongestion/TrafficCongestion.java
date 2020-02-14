/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficcongestion;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author jsula
 */
public class TrafficCongestion {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        drawModel(new Intersection());
        
    }
    
    public static void drawModel(Component model){
        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                JFrame frame = new JFrame("peep this jack");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 800);
                frame.add(model);
                frame.setVisible(true);
                        
            }
        });
    };
    
}

class Intersection extends Component{
    
    @Override
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        TrafficGraphics.drawLane(g2);
    }
}