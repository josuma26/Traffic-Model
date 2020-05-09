/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficmodel;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author jsula
 */
public class ThreeWayIntersection extends CitySection{
    DirectJoint joint;
    public ThreeWayIntersection(double accel,double dist,double maxSpeed,int x,int y,int[] laneLengths,int laneWidth,int jWidth,String name,int[] ... entryIndices){
        //entry indices given as pair of integers where the first number is the index of the LEAVING lane, dsecond INCOMING lane
        super(accel,dist,x,y);
        this.name = name;
        joint = new DirectJoint(new Point(x + laneLengths[2],y + laneLengths[1]),jWidth,jWidth,maxSpeed);
        Lane rightLane = new Lane(3*Math.PI/2,maxSpeed,laneWidth,laneLengths[0],new Point(x + laneLengths[2] + laneWidth,y + laneLengths[1] + jWidth));
        Lane leftUpLane = new Lane(Math.PI/2,maxSpeed,laneWidth,laneLengths[1],new Point(x + laneLengths[2] + laneWidth,y + laneLengths[1]));
        Lane sidewaysLaneBottom = new Lane(0,maxSpeed,laneWidth,laneLengths[2],new Point(x + laneLengths[2],y + laneLengths[1] + laneWidth));
        Lane sidewaysLane = new Lane(0,maxSpeed,laneWidth,laneLengths[3],new Point(x + laneLengths[2],y + laneLengths[1]));
        addLanes(rightLane,leftUpLane,sidewaysLaneBottom,sidewaysLane);
        //this.entryIndices = entryIndices;
        this.entryIndices = new ArrayList<>();
        
        String[] names = {"Right Lane","Left Up Lane","Sideways Lane Bottom","Sideways Lane","Up","Down"};
        for(int i = 0;i<lanes.size();i++){
            lanes.get(i).setName(this.name + " " + names[i]);
        }
        addJoint(joint);
        
        
        setTrafficLights(new double[]{3,0.5,3,0.5}, new int[]{2,3}, new int[]{0,1});
        
        
        
        
    }
    
    public void setConnections(Lane upRight,Lane rightBottom,Lane sidewaysUpRight,Lane left){
        Lane[][] a = {{lanes.get(0)},{upRight,rightBottom,null}};
        Lane[][] b = {{lanes.get(1)},{left,null,rightBottom}};
        Lane[][] c = {{lanes.get(2)},{rightBottom,left,upRight}};
        Lane[][] d = {{lanes.get(3)},{sidewaysUpRight, left,upRight}};
        
        joint.setOutLanes(new Lane[]{upRight,rightBottom,sidewaysUpRight,left});
        int[] order = {0,2,2,3};
        configureJoint(order,a,b,c,d);
    }
    
    
}
