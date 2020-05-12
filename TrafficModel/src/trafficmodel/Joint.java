/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficmodel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author jsula
 */
public class Joint {
    Point gPoint;
    double width,length,maxSpeed;
    ArrayList<Car> cars;
    
    HashMap<Lane,Lane[]> connections;
    Connection[] paths;
    
    Lane[] outLanes;
    
    Rectangle2D[] quadrants;
    ArrayList<ArrayList<Car>> carsInQuadrants;
    List<Lane> inLanes;
    public Joint(Point p1,double width,double length,double maxSpeed){
        this.width = width;
        this.length = length;
        this.maxSpeed = maxSpeed;
        this.gPoint = p1;
        this.cars = new ArrayList<Car>();
        
        this.connections = new HashMap();
        
        this.quadrants = new Rectangle2D[4];
        for(int i = 0;i<2;i++){
            for(int j = 0;j<2;j++){
                double x =  i*width/2;
                double y =  j*length/2;
                Rectangle2D rect = new Rectangle2D.Double(x,y,width/2,length/2);
                this.quadrants[i + 2*j] = rect;
            }
        }
        
        
    }
    
    public int[] rotateQuadrants(int n){
        //n is int form 0-3
        int[] from = {0,1,2,3};
        for (int i = 0;i<n;i++){
            int[] copy = from.clone();
            copy[0] = from[2];
            copy[1] = from[0];
            copy[2] = from[3];
            copy[3] = from[1];
            from = copy;
        }
        return from;
    }
    
    public void setConnections(Lane a,Lane[] b){
        connections.put(a, b);
    }
    
    public void setOutLanes(Lane[] outLanes){
        this.outLanes = outLanes;
    }
    
    
    public void enter(Car c,Lane lane){
        double[] newCoords = coordsFromLane(c,lane);
        c.direction = lane.direction ;
        c.point.setLocation(newCoords[0],newCoords[1]);
        c.point2.setLocation(newCoords[0] -c.height*Math.cos(c.direction) - c.width*Math.sin(c.direction),
                newCoords[1] + c.width*Math.cos(c.direction) - c.height*Math.sin(c.direction));
        
        
        this.cars.add(c);

        Lane[] possibleDestinations = (Lane[]) connections.get(lane);
        for(int i = 0;i<possibleDestinations.length;i++){
            if (c.steps.get(c.currentStep).equals(possibleDestinations[i])){
                c.setConenction(paths[i]);
                break;
            }
        }
        
        c.lookingOutForCoords =  rotateQuadrants(lane.checkingIndex);
        
        
        
        
    }
    
    private void carsInQuadrants(){
        carsInQuadrants = new ArrayList<>();
        for(int i = 0;i<4;i++){
            carsInQuadrants.add(new ArrayList<>());
        }
        for(int i = 0;i<inLanes.size();i++){
            inLanes.get(i).overflow = 0;
        }
        
        for (Car c:cars){
            for(int i = 0;i<4;i++){
                Rectangle2D rect = quadrants[i];
                if (rect.contains(c.point) || rect.contains(c.point2)){
                    carsInQuadrants.get(i).add(c);
                }
                
            }
            if (!inJoint(c.point2) && c.speed == 0){
                c.steps.get(c.currentStep - 1).overflow = c.height;
            }
        }
    }
    
    protected double[] coordsFromLane(Car c,Lane lane){
        double x = lane.gPoint.getX() -  gPoint.getX() - Math.sin(lane.direction)*c.point.getX();
        double y = lane.gPoint.getY() - gPoint.getY() + Math.cos(lane.direction)*c.point.getX();
        return new double[]{x,y};
    }
    
    public void update(double a,double interval){
        carsInQuadrants();
        ArrayList<Car> toRemove = new ArrayList<>();
        for(Car c:cars){
            c.updateConnection(a,maxSpeed,interval,this);
            Lane t = c.steps.get(c.currentStep);
            
            if (!inJoint(c)){
                toRemove.add(c);
                t.addCar(c);
                double newX = -(gPoint.getX() + c.point.getX() - t.gPoint.getX())*Math.sin(t.direction) + (gPoint.getY() + c.point.getY() - t.gPoint.getY())*Math.cos(t.direction);
                c.point.setLocation(newX, t.length);
                c.point2.setLocation(newX + c.width,t.length + c.height);
                c.turning = Double.NaN;
                c.currentStep += 1;
            }
        }
        cars.removeAll(toRemove);
        
    }
    
    public void updateAuto(double a,double interval,double targetSeparation){
        ArrayList<Car> toRemove = new ArrayList<>();
        inLanes = sortInLanes();
        for(int i = 0;i<inLanes.size()-1;i++){
            Lane lane = inLanes.get(i);
            if (lane.cars.size() != 0){
                for(int j = i+1;j<inLanes.size();j++){
                    Lane l2 = inLanes.get(j);
                    if (l2.cars.size() != 0 && (!l2.decelerating)){
                        double dist = lane.gPoint.distance(l2.gPoint);
                        double cos = calculateAngleBetweenLanes(dist,lane,l2);
                        Car c1 = lane.cars.get(0),c2 = l2.cars.get(0);
                        double target = c1.height + c1.width;
                        if (Math.abs(cos) > 0.001){
                            double x = dist*cos + c2.point.getY() + c1.point.getX()+ c1.width; //horizontal distance from l1
                            double y = dist*Math.sin(Math.acos(cos)) + c1.point.getY() + c2.point.getX() + c2.width; //vertical distance from l1
                            double t,deltaY = 0,yPrime = 0;
                           
                            if (x >= y){
                                t = x / l2.maxSpeed;
                                deltaY =  lane.maxSpeed * t - y;
                            }
                            else if (x < y){
                                t = y / lane.maxSpeed;
                                deltaY = l2.maxSpeed * t - x;
                            }
                            yPrime = target - deltaY%targetSeparation;
                            
                            
                            if (yPrime < 0){
                                yPrime = targetSeparation - deltaY%targetSeparation + target + c1.width;
                                System.out.println("shift");
                            }
                            double targetSpeed = l2.maxSpeed*0.9;//l2.maxSpeed + deltaV(l2.maxSpeed,a,c2.point.getY(),yPrime);
                            l2.acceleration = (Math.pow(l2.maxSpeed,2) - Math.pow(targetSpeed,2))/(c2.point.getY() -yPrime);
                            l2.targetSpeed = targetSpeed;
                            l2.decelerating = true;
                        }
                        else{
                            double t = c1.point.getY()/lane.maxSpeed;
                            double y2 = c2.point.getY();
                            double deltaY = l2.maxSpeed*t;
                            double yPrime = target + (deltaY - y2)*targetSeparation;
                            double targetSpeed = l2.maxSpeed*0.9;
                            l2.acceleration = (Math.pow(l2.maxSpeed,2) - Math.pow(targetSpeed,2))/(y2 - yPrime);
                            l2.targetSpeed = targetSpeed;
                            l2.decelerating = true;
                            
                        }
                    }
                }
                break;
            }
        }
        
        for(Car c:cars){
            c.updateConnectionAuto(a,maxSpeed,interval,this);
            if (!inJoint(c)){
                Lane t = c.steps.get(c.currentStep);
                toRemove.add(c);
                t.addCar(c);
                double newX = -(gPoint.getX() + c.point.getX() - t.gPoint.getX())*Math.sin(t.direction) + (gPoint.getY() + c.point.getY() - t.gPoint.getY())*Math.cos(t.direction);
                c.point.setLocation(newX, t.length);
                c.point2.setLocation(newX + c.width,t.length + c.height);
                c.turning = Double.NaN;
                c.currentStep += 1;
            }
        }
        cars.removeAll(toRemove);
    }
    
    private List<Lane> sortInLanes(){
        ArrayList<Lane> sorted = new ArrayList<>();
        for(Lane l:inLanes){
            int n = l.cars.size();
            int count = 0;
            for(int i = 0;i<sorted.size();i++){
                if (n > sorted.get(i).cars.size()){
                    break;
                }
                count += 1;
            }
            sorted.add(count,l);
            
        }
        return sorted;
    }
    
   
    private double calculateAngleBetweenLanes(double dist,Lane l1,Lane l2){
        double l1x = l1.gPoint.getX(),l1y = l1.gPoint.getY(),l2x =  l2.gPoint.getX() ,l2y = l2.gPoint.getY() ;
        double sinL1 = Math.sin(l1.direction),cosL1 = Math.cos(l1.direction);
        double cosTheta = (sinL1*(l2x - l1x)+ cosL1*(l2y - l1y))/dist;
        
        return cosTheta;
    }
    private double deltaV(double max,double accel,double d,double deltaD){
        double a = accel;
        double b = (2*accel*d)/max;
        double c = -(accel/2)*Math.pow(d/max, 2) + deltaD;
        
        //return -Math.sqrt(Math.pow(max,2) - accel*(d - deltaD));
        return -accel*(-b + Math.sqrt(Math.pow(b,2) - 4*a*c))/(2*a);
    }
    
    private boolean inJoint(Car c){
        boolean xIn = 0 <= c.point.getX() && c.point.getX() <= width;
        boolean yIn = 0 <= c.point.getY() && c.point.getY() <= length;
        return xIn && yIn;
    }
    
    private boolean inJoint(Point2D point){
        boolean xIn = 0 <= point.getX() && point.getX() <= width;
        boolean yIn = 0 <= point.getY() && point.getY() <= length;
        return xIn && yIn;
    }
    
}


class DirectJoint extends Joint{
    public DirectJoint(Point p1,double width,double height,double maxSpeed){
        super(p1,width,height,maxSpeed);
        paths = new Connection[]{new Straight(),new TurnRight(),new TurnLeft()};
    }
}

class Junction extends Joint{
    public Junction(Point p1,double width,double height,double maxSpeed){
        super(p1,width,height,maxSpeed);
        paths = new Connection[]{new Straight(), new TurnRight()};
    }
}

