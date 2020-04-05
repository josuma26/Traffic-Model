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
    
    public void addCars(double cWidth,double cLength,double startX,double startY,double separation, int[] ...params){
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
    
    public void setLaneNextStep(Lane l,Lane step){
        for(Car c:l.cars){
            c.addStep(step);
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
        for (Lane[][] config:configs){
            Lane l = config[0][0];
            Lane[] lanes = config[1];
            l.setOut(joint);
            joint.setConnections(l, lanes);
        }
    }
   
    
    public void controlTraffic(){
        controller.control();
        
        //overwrite code in implementation
    }
    
    
    
    @Override
    public void paint(Graphics g){
        controlTraffic();
        Graphics2D g2 = (Graphics2D)g;
        
       
        for (Lane l:lanes){
            l.update(standardA,standardD);
            //l.updateAuto(g2, standardA,0.1);
            TrafficGraphics.drawLane(g2, l);
        }
        
        joint.update(30,0.1);
        TrafficGraphics.drawJoint(g2,joint);
        
    } 
   
}
