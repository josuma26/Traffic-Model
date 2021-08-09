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
import java.util.HashMap;
import java.util.Map;
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
    ArrayList<int[]> entryIndices;
    
    HashMap<Lane,Object[] > fromPath;
    
    double carWidth,carHeight ,separationX,separation;
    
    public CitySection(double a,double d,int x,int y){
        //laneParam must have direction,maxSpeed,width,length,point
        standardA = a;
        standardD = d;
        this.x = x;
        this.y = y;
        
        this.fromPath = new HashMap<>();
        
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
        for(Car c:from.cars){
            c.setPath(p);
        }
        
    }
    
    public void setCarPath(Car c,Path p){
        c.setPath(p);
    }
    
    
    
    public void scheduleCars(Navigation nav,int from,int n,double carWidth,double carHeight,double separationX,int ... to){
        Node fromNode = nav.endpoints.get(from);
        Node[] destinations = new Node[to.length];
        Path[] paths = new Path[to.length];
        for(int i = 0;i<to.length;i++){
            Node dest = nav.endpoints.get(to[i]);
            Path p = nav.getPath(fromNode, dest);
            destinations[i] = dest;
            paths[i] = p;
        }
        Object[] pathCount = {fromNode,destinations,n,paths};
        fromPath.put(fromNode.outEdges.get(0).lane,pathCount);
        this.carWidth = carWidth;
        this.carHeight = carHeight;
        this.separationX = separationX;
        this.separation = 2*(carHeight) + carWidth;
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
    
    public void configureJoint(int[] order,Lane[][] ... configs){
        //configs first item is the lane, and the second is the conencted lanes (in correct format)
        int count = 0;
        ArrayList<Lane> laneList = new ArrayList<>();
        for (Lane[][] config:configs){
            Lane l = config[0][0];
            Lane[] lanes = config[1];
            l.setOut(joint);
            joint.setConnections(l, lanes);
            l.checkingIndex = order[count];
            laneList.add(l);
            count += 1;
        }
        joint.inLanes = laneList;
        
    }
   
    public void addLoneLane(Lane l,int lightIndex,int index,String name){
        //first item in enry index is index of index, second is opposite lane index
        addLanePair(l,lightIndex,name);
        
        this.entryIndices.add(new int[]{this.lanes.indexOf(l),index});
        
    }
    
    public void addLanePair(Lane l,int lightIndex,String name){
        this.lanes.add(l);
        l.setName(name);
        if (lightIndex == 0){
            l.setTrafficLight(light1);
        }
        else{
            l.setTrafficLight(light2);
        }
    }
    
    public void controlTraffic(){
        controller.control();
        
        //overwrite code in implementation
    }
    
    
    public void update(double interval,Navigation nav,boolean intelligent){
        controlTraffic();
        if (nav != null){
            enterScheduled(nav,intelligent);
        }
        else{
            enter();
        }
        for(Lane l:lanes){
            l.update(standardA,standardD,interval,intelligent,nav);
        }
        joint.update(standardA,interval);
    }
    
    public void updateAuto(double interval,Navigation nav,boolean intelligent){
        controlTraffic();
        if (nav != null){
            enterScheduled(nav,intelligent);
        }
        else{
            enter();
        }
        for(Lane l:lanes){
            l.updateAuto(standardA,interval,intelligent,nav);
        }
        joint.updateAuto(standardA,interval,this.separation);
    }
    
    private void enter(){
        for(Map.Entry<Lane,Object[]> entry:fromPath.entrySet()){
            Lane lane = entry.getKey();
            Object[] data = entry.getValue();
            int n = (Integer)data[2];
            Path paths[] = (Path[])data[3];
            if ((lane.distanceToEnd >= separation || lane.cars.size() == 0) && n> 0){
                Car c = new Car(separationX,lane.length,carWidth,carHeight);
                c.speed = lane.maxSpeed;
                lane.addCar(c);
                
                setCarPath(c,paths[(int)(Math.random()*paths.length)]);
                data[2] = n - 1;
            }
        }
    }
    private void enterScheduled(Navigation nav,boolean intelligent){
        for(Map.Entry<Lane,Object[]> entry:fromPath.entrySet()){
            Lane lane = entry.getKey();
            Object[] data = entry.getValue();
            Node from = (Node) data[0];
            Node[] destinations = (Node[])data[1];
            int n = (Integer)data[2];
            Path[] paths = (Path[])data[3];
            if ((lane.distanceToEnd >= separation || lane.cars.size() == 0) && n> 0){
                Car c = new Car(separationX,lane.length,carWidth,carHeight);
                c.speed = lane.maxSpeed;
                lane.addCar(c);
                Path p;
                int choice = (int)(Math.random()*destinations.length);
                Path path = paths[choice];
                if (intelligent){
                    Node to = destinations[choice];
                   p = nav.getPath(from,to);
                }
                else{
                    p = path;
                }
                setCarPath(c,p);
                data[2] = n - 1;
            }
        }
    }
    
    @Override
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        TrafficGraphics.drawSection(g2, this);
    } 
   
}
