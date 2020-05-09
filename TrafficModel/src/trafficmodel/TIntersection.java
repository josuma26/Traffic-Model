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
public class TIntersection extends CitySection {
    Junction joint;
    
    public TIntersection(double accel,double dist,double maxSpeed,int x,int y,int[] laneLengths,int laneWidth,int jWidth,String name,int[] ... entryIndices){
        super(accel,dist,x,y);
        this.name = name;
        joint = new Junction(new Point(x + laneLengths[1],y),jWidth,jWidth,maxSpeed);
        Lane rightLane = new Lane(3*Math.PI/2,maxSpeed,laneWidth,laneLengths[0],new Point(x + laneLengths[1] + laneWidth,y + jWidth));
        Lane sidewaysLaneBottom = new Lane(0,maxSpeed,laneWidth,laneLengths[1],new Point(x + laneLengths[1],y));
        Lane sidewaysLane = new Lane(0,maxSpeed,laneWidth,laneLengths[2],new Point(x + laneLengths[2],y + laneWidth));
        addLanes(rightLane,sidewaysLaneBottom,sidewaysLane);
        
        this.entryIndices = new ArrayList<>();
        
        String[] names = {"Right Lane","Sideways Lane Bottom","Sideways Lane"};
        for(int i = 0;i<lanes.size();i++){
            lanes.get(i).setName(this.name + " " + names[i]);
        }
        addJoint(joint);
        
        
        setTrafficLights(new double[]{3,0.5,3,0.5}, new int[]{1,2}, new int[]{0});
        
          
        
    }
    
    public void setConnections(Lane rightBottom,Lane sidewaysUpRight,Lane left){
        Lane[][] a = {{lanes.get(0)},{null,rightBottom}};
        Lane[][] b = {{lanes.get(1)},{rightBottom,null}};
        Lane[][] c = {{lanes.get(2)},{sidewaysUpRight,null}};
        
        joint.setOutLanes(new Lane[]{rightBottom,sidewaysUpRight,left});
        int[] order = {0,3,3};
        configureJoint(order,a,b,c);
    }
    
}
    
    