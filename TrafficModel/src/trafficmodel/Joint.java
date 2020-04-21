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
    double width,length;
    ArrayList<Car> cars;
    
    HashMap<Lane,Lane[]> connections;
    Connection[] paths;
    
    Lane[] outLanes;
    
    
    Rectangle2D[] quadrants;
    ArrayList<ArrayList<Car>> carsInQuadrants;
    List<Lane> orderedInLanes;
    public Joint(Point p1,double width,double length){
        this.width = width;
        this.length = length;
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
        c.point.setLocation(newCoords[0],newCoords[1]);
        c.direction = lane.direction;
        this.cars.add(c);
        
        
        
        
    }
    
    private void carsInQuadrants(){
        carsInQuadrants = new ArrayList<>();
        for(int i = 0;i<4;i++){
            carsInQuadrants.add(new ArrayList<>());
        }
        for (Car c:cars){
            for(int i = 0;i<4;i++){
                Rectangle2D rect = quadrants[i];
                if (rect.contains(c.point)){
                    carsInQuadrants.get(i).add(c);
                }
            }
        }
    }
    
    protected double[] coordsFromLane(Car c,Lane lane){
        double x = lane.gPoint.getX() -  gPoint.getX() - Math.sin(lane.direction)*c.point.getX();
        double y = lane.gPoint.getY() - gPoint.getY() + Math.cos(lane.direction)*c.point.getX();
        return new double[]{x,y};
    }
    
    public void update(double a,double max,double interval){
        carsInQuadrants();
        ArrayList<Car> toRemove = new ArrayList<>();
        for(Car c:cars){
            c.updateConnection(a,max,interval,this);
            Lane t = c.steps.get(0);
            
            if (!inJoint(c)){
                toRemove.add(c);
                t.addCar(c);
                double newX = -(gPoint.getX() + c.point.getX() - t.gPoint.getX())*Math.sin(t.direction) + (gPoint.getY() + c.point.getY() - t.gPoint.getY())*Math.cos(t.direction);
                c.point.setLocation(newX, t.length);
                c.turning = Double.NaN;
                c.steps.remove(0);
            }
        }
        cars.removeAll(toRemove);
        
    }
    
    private boolean inJoint(Car c){
        boolean xIn = 0 <= c.point.getX() && c.point.getX() <= width;
        boolean yIn = 0 <= c.point.getY() && c.point.getY() <= length;
        return xIn && yIn;
    }
    
}


class DirectJoint extends Joint{
    Connection[] paths;
    public DirectJoint(Point p1,double width,double height){
        super(p1,width,height);
        paths = new Connection[]{new Straight(),new TurnRight(),new TurnLeft()};
    }
    
    @Override
    public void enter(Car c,Lane lane){
        double[] newCoords = coordsFromLane(c,lane);
        c.point.setLocation(newCoords[0],newCoords[1]);
        c.direction = lane.direction ;
        this.cars.add(c);
        
        Lane[] possibleDestinations = (Lane[]) connections.get(lane);
        for(int i = 0;i<possibleDestinations.length;i++){
            if (c.steps.get(0).equals(possibleDestinations[i])){
                c.setConenction(paths[i]);
                break;
            }
        }
        
        c.lookingOutForCoords =  rotateQuadrants(orderedInLanes.indexOf(lane));
    }
    
}

