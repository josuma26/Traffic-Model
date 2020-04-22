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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.Timer;

/**
 *
 * @author jsula
 */
public class CitySection extends Model {
    ArrayList<Lane> lanes;
    
    Joint joint;
    
    double standardA,standardD;
    int x,y;
    TrafficController controller;
    TrafficLight light1,light2;
    
    String name;
    int[][] entryIndices;
    public CitySection(double a,double d,int x,int y){
        //laneParam must have direction,maxSpeed,width,length,point
        standardA = a;
        standardD = d;
        this.x = x;
        this.y = y;
        
    }
    
    
    public void addLanes(Lane ... lanes){
        this.lanes = new ArrayList<>();
        for  (Lane l:lanes){
            this.lanes.add(l);
        }
    }
    
    public void addJoint(Joint j){
        joint = j;
    }
    
    public void addCars(double cWidth,double cLength,double startX,double startY,double separation,int[]... params){
        //params contains index and number of cars for each lane
        for(int[] p:params){
            Lane toAdd = lanes.get(p[0]);
            int nCars = p[1];
            for(int i = 0;i<nCars;i++){
                Car c = new Car(startX,startY + i*(cLength + separation),cWidth,cLength);
                toAdd.addCar(c);
            }
        }
    }
    
    public void addCarsLane(double cWidth,double cLength,double startX,double startY,double separation,Lane l,int n){
        for(int i = 0;i<n;i++){
            Car car = new Car(startX,startY + i*(cLength + separation),cWidth,cLength);
            l.addCar(car);
        }
    }
    
    public void setLaneNextStep(int a,int b){
        Lane l = lanes.get(a);
        Lane step = lanes.get(b);
        for(Car c:l.cars){
            c.addStep(step);
        }
    }
    
    public void setLaneNextStep(int a,Lane b){
        Lane from = lanes.get(a);
        for (Car c:from.cars){
            c.addStep(b);
        }
    }
    
    public void setLanePath(Lane from,Path p){
        for(Lane step:p.connections){
            for(Car c:from.cars){
                c.addStep(step);
            }
        }
    }
    
    
    public void sendCarsFromTo(Navigation nav,int from,int to,int n,double cWidth,double cLength,double startX,double startY,double separation){
        Node fromNode = nav.endpoints.get(from),toNode = nav.endpoints.get(to);
        Path p = nav.getPath(fromNode, toNode);
        Lane entry = fromNode.outEdges.get(0).lane;
        addCarsLane(cWidth,cLength,startX,startY,separation,entry,n);
        setLanePath(entry,p);
    }
    
    public void joinSection(int ownIndex,CitySection other,int otherLaneIndex){
        Lane own = lanes.get(ownIndex);
        Lane otherLane = other.lanes.get(otherLaneIndex);
        if (own.out != null){
            own.length += otherLane.length;
            other.lanes.remove(otherLane);
            
        }
        else{
            otherLane.length += own.length;
            lanes.remove(own);
        }
     
        
    }
    
    public void setTrafficLights(double[] times,int[] a,int[] b){
        controller = new TrafficController(times);
        light1 = new TrafficLight(controller,0);
        light2 = new TrafficLight(controller,1);
        for(int i: a){
            lanes.get(i).setTrafficLight(light1);
        }
        for(int i:b){
            lanes.get(i).setTrafficLight(light2);
        }
    }
    
    public void configureJoint(Lane[][] ... configs){
        //configs first item is the lane, and the second is the conencted lanes (in correct format)
        int[] order = {0,3,2,1};
        int count = 0;
        Lane[] list = new Lane[4];
        for (Lane[][] config:configs){
            Lane l = config[0][0];
            Lane[] lanes = config[1];
            l.setOut(joint);
            joint.setConnections(l, lanes);
            list[order[count]] = l;
            count += 1;
        }
        joint.orderedInLanes = Arrays.asList(list);
    }
   
    
    public void controlTraffic(){
        controller.control();
        
        //overwrite code in implementation
    }
    
    
    public void update(double interval){
        controlTraffic();
        for(Lane l:lanes){
            l.update(standardA,standardD,interval);
        }
        joint.update(standardA,20,interval);
    }
    
    @Override
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        TrafficGraphics.drawSection(g2, this);
    } 
   
}
