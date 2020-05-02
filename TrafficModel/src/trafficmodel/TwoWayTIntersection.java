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
public class TwoWayTIntersection extends CitySection{
    DirectJoint joint;
    
    public TwoWayTIntersection(double accel,double dist,double maxSpeed,int x,int y,int[] laneLengths,int laneWidth,int jWidth,String name){
        super(accel,dist,x,y);
        this.name = name;
        joint = new DirectJoint(new Point(x,y + laneLengths[1]),jWidth,jWidth);
        Lane rightLane = new Lane(3*Math.PI/2,maxSpeed,laneWidth,laneLengths[0],new Point(x  + laneWidth,y + laneLengths[1] + jWidth));
        Lane leftUpLane = new Lane(Math.PI/2,maxSpeed,laneWidth,laneLengths[1],new Point(x  + laneWidth,y + laneLengths[1]));
        addLanes(rightLane,leftUpLane);
        
        this.entryIndices = new ArrayList<>();
        
        String[] names = {"Right Lane","Left Up Lane"};
        for(int i = 0;i<lanes.size();i++){
            lanes.get(i).setName(this.name + " " + names[i]);
        }
        addJoint(joint);
        
        
        setTrafficLights(new double[]{3,0.5,3,0.5}, new int[]{}, new int[]{0,1});
    }
    
    public void setConnections(Lane rightBottom,Lane sidewaysUpRight,Lane rightUp,Lane left){
        Lane[][] a = {{lanes.get(0)},{rightUp,rightBottom,null}};
        Lane[][] b = {{lanes.get(1)},{left,null,rightBottom}};
        
        joint.setOutLanes(new Lane[]{rightBottom,sidewaysUpRight,rightUp,left});
        configureJoint(a,b);
    }
    
}
