/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficmodel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 *
 * @author jsula
 */
public class FourIntersections extends Model{
    
    IntersectionCombine intersection1,intersection2,intersection3,intersection4;
     
    public FourIntersections(double accel,double dist,double maxSpeed,int x,int y,int laneLength,int laneWidth,int jWidth,int carWidth,int carHeight){
        Lane up1 = new Lane(3*Math.PI/2,maxSpeed,laneWidth,laneLength,new Point2D.Double(x + laneLength + laneWidth,y));
        Lane side1 = new Lane(Math.PI,maxSpeed,laneWidth,laneLength,new Point2D.Double(x,y + laneLength + laneWidth));
        intersection1 = new IntersectionCombine(accel,dist,maxSpeed,x,y,laneLength,laneWidth,jWidth,up1,side1); 
        
        Lane up2 = new Lane(3*Math.PI/2,maxSpeed,laneWidth,laneLength,new Point2D.Double(x + 3*laneLength + jWidth + laneWidth,y));
        Lane side2 = new Lane(0,maxSpeed,laneWidth,laneLength,new Point2D.Double(x + 4*laneLength + 2*jWidth,laneLength + laneWidth));
        intersection2 = new IntersectionCombine(accel,dist,maxSpeed,x + 2*laneLength + jWidth,y,laneLength,laneWidth,jWidth,up2,side2);
        
        Lane up3 = new Lane(Math.PI/2,maxSpeed,laneWidth,laneLength,new Point2D.Double(x + laneLength + laneWidth,4*laneLength + 2*jWidth));
        Lane side3 = new Lane(Math.PI,maxSpeed,laneWidth,laneLength,new Point2D.Double(x,3*laneLength + jWidth + laneWidth));
        intersection3 = new IntersectionCombine(accel,dist,maxSpeed,x,y + 2*laneLength + jWidth,laneLength,laneWidth,jWidth,up3,side3);
        
        Lane up4 = new Lane(Math.PI/2,maxSpeed,laneWidth,laneLength,new Point2D.Double(3*laneLength + jWidth + laneWidth,4*laneLength + 2*jWidth));
        Lane side4 = new Lane(0,maxSpeed,laneWidth,laneLength,new Point2D.Double(4*laneLength + 2*jWidth,3*laneLength + jWidth +  laneWidth));
        intersection4 = new IntersectionCombine(accel,dist,maxSpeed,x+ 2*laneLength + jWidth,y+ 2*laneLength + jWidth,laneLength,laneWidth,jWidth,up4,side4);
        
       
        intersection1.setConnections(up1, intersection2.lanes.get(2), side1, intersection2.lanes.get(1));
        intersection2.setConnections(up2, side2, intersection1.lanes.get(3), intersection4.lanes.get(1));
        intersection3.setConnections(intersection1.lanes.get(0), intersection4.lanes.get(3), side3, up3);
        intersection4.setConnections(intersection2.lanes.get(0),side4,intersection3.lanes.get(3),up4);
        
        /*
        intersection3.addCars(carWidth, carHeight, 10, 10, 20, new int[]{0,5});
        intersection3.setLaneNextStep(0, intersection1.lanes.get(0));
        intersection3.setLaneNextStep(0,intersection2.lanes.get(2));
        intersection3.setLaneNextStep(0,intersection4.lanes.get(1));
        */
        
        
        intersection3.addCars(carWidth,carHeight,10,10,20,new int[]{2,5});
        intersection3.setLaneNextStep(2, 4);
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
