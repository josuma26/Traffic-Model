/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficmodel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author jsula
 */
public class FourIntersections extends Model{
    FourWayIntersectionCombine intersection1,intersection2,intersection3,intersection4;
    Navigation nav;
    double carWidth,carHeight;
    public FourIntersections(double accel,double dist,double maxSpeed,int x,int y,int laneLength,int laneWidth,int jWidth,int carWidth,int carHeight){
        this.WINDOW_WIDTH = 1400;
        this.carHeight = carHeight;
        this.carWidth = carWidth;
        Lane up1 = new Lane(3*Math.PI/2,maxSpeed,laneWidth,laneLength,new Point2D.Double(x + laneLength + laneWidth,y));
        Lane side1 = new Lane(Math.PI,maxSpeed,laneWidth,laneLength,new Point2D.Double(x,y + laneLength + laneWidth));
        intersection1 = new FourWayIntersectionCombine(accel,dist,maxSpeed,x,y,laneLength,laneWidth,jWidth,"Intersection1"); 
        intersection1.addLoneLane(up1, 1, 1, "Up");
        intersection1.addLoneLane(side1,0,2,"Side");
        
        Lane up2 = new Lane(3*Math.PI/2,maxSpeed,laneWidth,laneLength,new Point2D.Double(x + 3*laneLength + jWidth + laneWidth,y));
        Lane side2 = new Lane(0,maxSpeed,laneWidth,laneLength,new Point2D.Double(x + 4*laneLength + 2*jWidth,laneLength + laneWidth));
        intersection2 = new FourWayIntersectionCombine(accel,dist,maxSpeed,x + 2*laneLength + jWidth,y,laneLength,laneWidth,jWidth,"Intersection2");
        intersection2.addLoneLane(up2,1,1,"Up");
        intersection2.addLoneLane(side2,0,3,"Side");
        
        Lane up3 = new Lane(Math.PI/2,maxSpeed,laneWidth,laneLength,new Point2D.Double(x + laneLength + laneWidth,4*laneLength + 2*jWidth));
        Lane side3 = new Lane(Math.PI,maxSpeed,laneWidth,laneLength,new Point2D.Double(x,3*laneLength + jWidth + laneWidth));
        intersection3 = new FourWayIntersectionCombine(accel,dist,maxSpeed,x,y + 2*laneLength + jWidth,laneLength,laneWidth,jWidth,"Intersection3");
        intersection3.addLoneLane(up3,1,0,"Up");
        intersection3.addLoneLane(side3,0,2,"Side");
        
        Lane up4 = new Lane(Math.PI/2,maxSpeed,laneWidth,laneLength,new Point2D.Double(3*laneLength + jWidth + laneWidth,4*laneLength + 2*jWidth));
        Lane side4 = new Lane(0,maxSpeed,laneWidth,laneLength,new Point2D.Double(4*laneLength + 2*jWidth,3*laneLength + jWidth +  laneWidth));
        intersection4 = new FourWayIntersectionCombine(accel,dist,maxSpeed,x+ 2*laneLength + jWidth,y+ 2*laneLength + jWidth,laneLength,laneWidth,jWidth,"Intersection4");
        intersection4.addLoneLane(up4, 0, 0, "Up");
        intersection4.addLoneLane(side4,1,3,"Side");
       
        intersection1.setConnections(up1, intersection2.lanes.get(2), side1, intersection3.lanes.get(1));
        intersection2.setConnections(up2, side2, intersection1.lanes.get(3), intersection4.lanes.get(1));
        intersection3.setConnections(intersection1.lanes.get(0), intersection4.lanes.get(2), side3, up3);
        intersection4.setConnections(intersection2.lanes.get(0),side4,intersection3.lanes.get(3),up4);
                
        nav = new Navigation(intersection1,intersection2,intersection3,intersection4);
        
    }
    
    @Override
    public void initialize(DataReader reader,String selection){
        int n = 20;
        totalCars = 8*n;
        if (selfDriving){
            intersection1.scheduleCars(nav, 0, n, carWidth, carHeight, 10, 4);
            intersection2.scheduleCars(nav, 1, n, carWidth, carHeight, 10, 3);
            intersection3.scheduleCars(nav, 4, n, carWidth, carHeight, 10, 0);
            intersection4.scheduleCars(nav, 3, n, carWidth, carHeight, 10, 1);

            intersection1.scheduleCars(nav, 2, n, carWidth, carHeight, 10, 6);
            intersection1.scheduleCars(nav, 5, n, carWidth, carHeight, 10, 7);
            intersection3.scheduleCars(nav, 6, n, carWidth, carHeight, 10, 2);
            intersection4.scheduleCars(nav, 7, n, carWidth, carHeight, 10, 5);
        }
        else{
            intersection1.scheduleCars(nav, 0, n, carWidth, carHeight, 10, 4);
            intersection2.scheduleCars(nav, 1, n, carWidth, carHeight, 10, 0);
            intersection3.scheduleCars(nav, 4, n, carWidth, carHeight, 10, 0);
            intersection4.scheduleCars(nav, 3, n, carWidth, carHeight, 10, 1);

            intersection1.scheduleCars(nav, 2, n, carWidth, carHeight, 10, 6);
            intersection1.scheduleCars(nav, 5, n, carWidth, carHeight, 10, 7);
            intersection3.scheduleCars(nav, 6, n, carWidth, carHeight, 10, 2);
            intersection4.scheduleCars(nav, 7, n, carWidth, carHeight, 10, 5);
        }
        
        
    }
    
    @Override
    public void update(){
        double interval = 0.05;
        intersection1.update(interval,nav,intelligent);
        intersection2.update(interval,nav,intelligent);
        intersection3.update(interval,nav,intelligent);
        intersection4.update(interval,nav,intelligent);  
        
        done = left(nav) == totalCars;
    }
    
    @Override
    public void updateAuto(){
        double interval = 0.05;
        intersection1.updateAuto(interval,nav,intelligent);
        intersection2.updateAuto(interval,nav,intelligent);
        intersection3.updateAuto(interval,nav,intelligent);
        intersection4.updateAuto(interval,nav,intelligent); 
        
        done = left(nav) == totalCars;
        
    }
    
    @Override
    public void paint(Graphics g){
        drawTimer(g);
        intersection1.paint(g);
        intersection2.paint(g);
        intersection3.paint(g);
        intersection4.paint(g);
    }
}
