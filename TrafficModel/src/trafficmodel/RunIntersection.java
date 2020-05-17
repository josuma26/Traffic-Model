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
        intersection = new FourWayIntersection(3,60,30,0,0,650,70,140);
        intersection.carWidth = 30;
        intersection.carHeight = 32;
        intersection.separation = 2*(32) + 30;
        intersection.separationX = (70 - 30)/2;
             
       
    }
    
    @Override
    public void initialize(){
        int n = 20;
        
        if (random){
            schedule(0,n,5,6,7);
            schedule(2,n,5,4);
            schedule(1,n,4,7);
            schedule(3,n,6,7);
        }
        else{
           schedule(0,n,6);
           schedule(2,n,5);
           schedule(1,n,4);
           schedule(3,n,7); 
        }
        
        
       
                
    }
    
    private void schedule(int from,int n, int ... to){
        Lane fromLane = intersection.lanes.get(from);
        Path[] paths = new Path[to.length];
        for(int i = 0;i<to.length;i++){
           Lane toLane = intersection.lanes.get(to[i]);
           Path p = new Path(new Lane[]{fromLane,toLane});
           paths[i] = p;
        }
        intersection.fromPath.put(fromLane, new Object[]{null,null,n,paths});
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
