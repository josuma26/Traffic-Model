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
        this.WINDOW_WIDTH = 1400;
        intersection = new FourWayIntersection(5,40,40,0,0,650,50,100);
        intersection.carWidth = 30;
        intersection.carHeight = 32;
        intersection.separation = 2*(32) + 30;
        intersection.separationX = 10;
             
       
    }
    
    @Override
    public void initialize(){
        int n = 20;
        if (selfDriving){
            schedule(0,6,n);
            schedule(2,5,n);
            schedule(1,4,n);
            schedule(3,7,n);
            
            
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
    
    private void schedule(int from,int to,int n){
        Lane fromLane = intersection.lanes.get(from);
        Lane toLane = intersection.lanes.get(to);
        Path p = new Path(new Lane[]{fromLane,toLane});
        intersection.fromPath.put(fromLane, new Object[]{null,null,n,new Path[]{p}});
    }
    @Override
    public void paint(Graphics g){
        intersection.paint(g);
        drawTimer(g);
    }
    
    @Override
    public void update(){
        intersection.update(0.05,null,intelligent);
    }
    
    @Override
    public void updateAuto(){
        intersection.updateAuto(0.05,null,intelligent);
        
    }
}
