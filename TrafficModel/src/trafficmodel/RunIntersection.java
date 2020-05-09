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
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author jsula
 */
public class RunIntersection extends Model {
    FourWayIntersection intersection;
    
    public RunIntersection(){
        intersection = new FourWayIntersection(3,40,40,0,0,650,50,100);
        intersection.carWidth = 30;
        intersection.carHeight = 32;
        intersection.separation = 2*(30 + 32);
        intersection.separationX = 10;
        
        int n = 50;
        Path p = new Path(new Lane[]{intersection.lanes.get(0),intersection.lanes.get(6)});
        intersection.fromPath.put(intersection.lanes.get(0),new Object[]{p,n});
        
        Path p2 = new Path(new Lane[]{intersection.lanes.get(2),intersection.lanes.get(5)});
        //intersection.fromPath.put(intersection.lanes.get(2),new Object[]{p2,n});
        
        Path p3 = new Path(new Lane[]{intersection.lanes.get(1),intersection.lanes.get(4)});
        intersection.fromPath.put(intersection.lanes.get(1),new Object[]{p3,n});
        
        Path p4 = new Path(new Lane[]{intersection.lanes.get(3),intersection.lanes.get(7)});
        intersection.fromPath.put(intersection.lanes.get(3),new Object[]{p4,n});
    
        
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
        intersection.update(0.05);
    }
    
    @Override
    public void updateAuto(){
        intersection.updateAuto(0.05);
        Lane l = intersection.lanes.get(2);
        if (l.cars.size() > 0){
            Car c = l.cars.get(0);
            System.out.printf("Accel: %.1f Speed: %.1f Target: %.1f\n",c.acceleration,c.speed,l.targetSpeed);
        }
        
    }
}
