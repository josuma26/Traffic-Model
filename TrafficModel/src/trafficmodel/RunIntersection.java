/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficmodel;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author jsula
 */
public class RunIntersection extends Model {
    Intersection intersection;
    
    public RunIntersection(){
        intersection = new Intersection(3,40,30,0,0,600,100,200);
        
        run();
    }
    
    @Override
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        intersection.paint(g);
        String t = "Time: " + (seconds() - startTime);
        g2.setFont(new Font("Arial",Font.PLAIN,30));
        g2.drawString(t, x + 1200, y + 200);
        g2.setStroke(new BasicStroke(5));
    }
    
    @Override
    public void update(){
        intersection.update();
    }
}
