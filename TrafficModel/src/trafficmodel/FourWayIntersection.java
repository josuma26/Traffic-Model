/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficmodel;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author jsula
 */
public class FourWayIntersection extends CitySection{
   
    DirectJoint joint;
    public FourWayIntersection(double accel,double dist,double maxSpeed,int x,int y,int laneLength,int laneWidth,int jWidth){
        super(accel,dist,x,y);
        joint = new DirectJoint(new Point(x + laneLength,y + laneLength),jWidth,jWidth,maxSpeed);
        Lane rightLane = new Lane(3*Math.PI/2,maxSpeed,laneWidth,100 + laneLength,new Point(x + laneLength + laneWidth,y + laneLength + jWidth));
        Lane leftLane = new Lane(Math.PI/2,maxSpeed,laneWidth,laneLength,new Point(x + laneLength + laneWidth,y + 2*laneLength + jWidth));
        Lane rightUpLane = new Lane(3*Math.PI/2,maxSpeed,laneWidth,laneLength,new Point(x + laneLength + laneWidth,y));
        Lane leftUpLane = new Lane(Math.PI/2,maxSpeed,laneWidth,20 + laneLength,new Point(x + laneLength + laneWidth,y + laneLength));
        Lane sidewaysLane = new Lane(Math.PI,maxSpeed,laneWidth,laneLength,new Point(x,y + laneLength + laneWidth));
        Lane sidewaysLaneUpRight = new Lane(Math.PI,maxSpeed,laneWidth,laneLength,new Point(x + laneLength + jWidth,y + laneLength + laneWidth));
        Lane sidewaysLaneBottom = new Lane(0,maxSpeed,laneWidth,laneLength,new Point(x + laneLength,y + laneLength + laneWidth));
        Lane sidewaysLaneBottomRight = new Lane(0,maxSpeed,laneWidth,laneLength,new Point(x + 2*laneLength + jWidth,y + laneLength + laneWidth));
        addLanes(rightLane,leftUpLane,sidewaysLaneBottom,sidewaysLaneUpRight,leftLane,sidewaysLaneBottomRight,rightUpLane,sidewaysLane);
        String[] names = {"Right","Ledt up","sideways bottom","sidways up right","left","sideways bottom right","right up","sideways"};
        for(int i = 0;i<lanes.size();i++){
            lanes.get(i).setName(names[i]);
        }
        
        addJoint(joint);
        
        
        setTrafficLights(new double[]{3,0.5,3,0.5}, new int[]{2,3,4,5}, new int[]{0,1,6,7});
        
        Lane[][] a = {{leftUpLane}, {leftLane,sidewaysLane,sidewaysLaneBottomRight}};
        Lane[][] b = {{rightLane},{rightUpLane,sidewaysLaneBottomRight,sidewaysLane}};
        Lane[][] c = {{sidewaysLaneBottom},{sidewaysLaneBottomRight,leftLane,rightUpLane}};
        Lane[][] d = {{sidewaysLaneUpRight},{sidewaysLane,rightUpLane,leftLane}};
        int[] order = {2,0,3,1};
        configureJoint(order,a,b,c,d);
        joint.setOutLanes(new Lane[]{rightUpLane,leftLane,sidewaysLane,sidewaysLaneBottomRight});
        
        }
    
    
}
