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
    
    IntersectionCombine intersection1,intersection2,intersection3,intersection4;
    Navigation nav;
    public FourIntersections(double accel,double dist,double maxSpeed,int x,int y,int laneLength,int laneWidth,int jWidth,int carWidth,int carHeight){
        Lane up1 = new Lane(3*Math.PI/2,maxSpeed,laneWidth,laneLength,new Point2D.Double(x + laneLength + laneWidth,y));
        Lane side1 = new Lane(Math.PI,maxSpeed,laneWidth,laneLength,new Point2D.Double(x,y + laneLength + laneWidth));
        int[] index11 = {4,1},index12 = {5,2};
        intersection1 = new IntersectionCombine(accel,dist,maxSpeed,x,y,laneLength,laneWidth,jWidth,up1,side1,"Intersection1",index11,index12); 
        
        Lane up2 = new Lane(3*Math.PI/2,maxSpeed,laneWidth,laneLength,new Point2D.Double(x + 3*laneLength + jWidth + laneWidth,y));
        Lane side2 = new Lane(0,maxSpeed,laneWidth,laneLength,new Point2D.Double(x + 4*laneLength + 2*jWidth,laneLength + laneWidth));
        int[] index21 = {4,1},index22 = {5,3};
        intersection2 = new IntersectionCombine(accel,dist,maxSpeed,x + 2*laneLength + jWidth,y,laneLength,laneWidth,jWidth,up2,side2,"Intersection2",index21,index22);
        
        Lane up3 = new Lane(Math.PI/2,maxSpeed,laneWidth,laneLength,new Point2D.Double(x + laneLength + laneWidth,4*laneLength + 2*jWidth));
        Lane side3 = new Lane(Math.PI,maxSpeed,laneWidth,laneLength,new Point2D.Double(x,3*laneLength + jWidth + laneWidth));
        int[] index31 = {4,0},index32 = {5,2};
        intersection3 = new IntersectionCombine(accel,dist,maxSpeed,x,y + 2*laneLength + jWidth,laneLength,laneWidth,jWidth,up3,side3,"Intersection3",index31,index32);
        
        Lane up4 = new Lane(Math.PI/2,maxSpeed,laneWidth,laneLength,new Point2D.Double(3*laneLength + jWidth + laneWidth,4*laneLength + 2*jWidth));
        Lane side4 = new Lane(0,maxSpeed,laneWidth,laneLength,new Point2D.Double(4*laneLength + 2*jWidth,3*laneLength + jWidth +  laneWidth));
        int[] index41 = {4,0},index42 = {5,3};
        intersection4 = new IntersectionCombine(accel,dist,maxSpeed,x+ 2*laneLength + jWidth,y+ 2*laneLength + jWidth,laneLength,laneWidth,jWidth,up4,side4,"Intersection4",index41,index42);
       
        intersection1.setConnections(up1, intersection2.lanes.get(2), side1, intersection3.lanes.get(1));
        intersection2.setConnections(up2, side2, intersection1.lanes.get(3), intersection4.lanes.get(1));
        intersection3.setConnections(intersection1.lanes.get(0), intersection4.lanes.get(2), side3, up3);
        intersection4.setConnections(intersection2.lanes.get(0),side4,intersection3.lanes.get(3),up4);
        
        
        
        
        
        nav = new Navigation(intersection1,intersection2,intersection3,intersection4);
        
        intersection1.sendCarsFromTo(nav, 1, 6, 5, carWidth, carHeight, 10, 10, 20);
        intersection1.sendCarsFromTo(nav,0,5,5,carWidth,carHeight,10,10,20);
        intersection2.sendCarsFromTo(nav, 3, 2, 5 , carWidth, carHeight, 10, 10, 20);
        intersection2.sendCarsFromTo(nav,2,5,3,carWidth, carHeight, 10, 10, 20);
        intersection3.sendCarsFromTo(nav,4,3,5,carWidth,carHeight,10,10,20);
        
        
        run();
    }
    
    
    @Override
    public void update(){
        intersection1.update();
        intersection2.update();
        intersection3.update();
        intersection4.update();
        
    }
    
    @Override
    public void paint(Graphics g){
        intersection1.paint(g);
        intersection2.paint(g);
        intersection3.paint(g);
        intersection4.paint(g);
    }
}
