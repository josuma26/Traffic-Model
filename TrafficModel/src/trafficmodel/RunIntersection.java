/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficmodel;

import java.awt.BasicStroke;
import java.awt.Color;
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
        intersection = new FourWayIntersection(5,40,40,0,0,650,50,100);
        intersection.carWidth = 30;
        intersection.carHeight = 32;
        intersection.separation = 2*(32) + 30;
        intersection.separationX = 10;
             
        
        run();
    }
    
    @Override
    public void initialize(){
        int n = 20;
        if (selfDriving){
            Path p = new Path(new Lane[]{intersection.lanes.get(0),intersection.lanes.get(6)});
            intersection.fromPath.put(intersection.lanes.get(0),new Object[]{p,n});

            Path p2 = new Path(new Lane[]{intersection.lanes.get(2),intersection.lanes.get(5)});
            intersection.fromPath.put(intersection.lanes.get(2),new Object[]{p2,n});

            Path p3 = new Path(new Lane[]{intersection.lanes.get(1),intersection.lanes.get(4)});
            intersection.fromPath.put(intersection.lanes.get(1),new Object[]{p3,n});

            Path p4 = new Path(new Lane[]{intersection.lanes.get(3),intersection.lanes.get(7)});
            intersection.fromPath.put(intersection.lanes.get(3),new Object[]{p4,n});
        }
        else{
           intersection.addCars(intersection.carWidth, intersection.carHeight, intersection.separationX , intersection.separationX , 12, new int[]{0,n},new int[]{2,n},new int[]{1,n},new int[]{3,n});
           intersection.setLaneNextStep(0, 0);
           intersection.setLaneNextStep(0, 6);
           intersection.setLaneNextStep(2,2);
           intersection.setLaneNextStep(2,5);
           intersection.setLaneNextStep(1,1);
           intersection.setLaneNextStep(1,4);
           intersection.setLaneNextStep(3,3);
           intersection.setLaneNextStep(3,7);
        }
    }
    @Override
    public void paint(Graphics g){
        intersection.paint(g);
        drawTimer(g);
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
            //System.out.printf("A: %.1f S: %.1f T: %.1f\n",c.acceleration,c.speed,l.targetSpeed);
        }
        
        
        
    }
}
