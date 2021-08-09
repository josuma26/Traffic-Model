/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficmodel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author jsula
 */
public class FourWayIntersectionCombine extends CitySection{
    
    DirectJoint joint;
    public FourWayIntersectionCombine(double accel,double dist,double maxSpeed,int x,int y,int laneLength,int laneWidth,int jWidth,String name){
        //entry indices given as pair of integers where the first number is the index of the LEAVING lane, dsecond INCOMING lane
        super(accel,dist,x,y);
        this.name = name;
        joint = new DirectJoint(new Point(x + laneLength,y + laneLength),jWidth,jWidth,maxSpeed);
        Lane rightLane = new Lane(3*Math.PI/2,maxSpeed,laneWidth,2*laneLength,new Point(x + laneLength + laneWidth,y + laneLength + jWidth));
        Lane leftUpLane = new Lane(Math.PI/2,maxSpeed,laneWidth,2*laneLength,new Point(x + laneLength + laneWidth,y + laneLength));
        Lane sidewaysLaneBottom = new Lane(0,maxSpeed,laneWidth,2*laneLength,new Point(x + laneLength,y + laneLength + laneWidth));
        Lane sidewaysLaneUpRight = new Lane(Math.PI,maxSpeed,laneWidth,2*laneLength,new Point(x + laneLength + jWidth,y + laneLength + laneWidth));
        addLanes(rightLane,leftUpLane,sidewaysLaneBottom,sidewaysLaneUpRight);
        this.entryIndices = new ArrayList<>();
        
        String[] names = {"Right Lane","Left Up Lane","Sideways Lane Bottom","Sideways Lane Up Right","Up","Side"};
        for(int i = 0;i<lanes.size();i++){
            lanes.get(i).setName(this.name + " " + names[i]);
        }
        addJoint(joint);
        
        
        setTrafficLights(new double[]{3,0.5,3,0.5}, new int[]{2,3}, new int[]{0,1});
        
        
        
        
    }
    
    public void setConnections(Lane upRight,Lane rightBottom,Lane sideways,Lane left){
        Lane[][] a = {{lanes.get(0)},{upRight,rightBottom,sideways}};
        Lane[][] b = {{lanes.get(1)},{left,sideways,rightBottom}};
        Lane[][] c = {{lanes.get(2)},{rightBottom,left,upRight}};
        Lane[][] d = {{lanes.get(3)},{sideways, upRight,left}};
        
        joint.setOutLanes(new Lane[]{upRight,rightBottom,sideways,left});
        int[] order = {0,2,3,1};
        configureJoint(order,a,b,c,d);
    }
  
   
}
