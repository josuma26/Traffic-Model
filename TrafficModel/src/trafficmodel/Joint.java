/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficmodel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
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
    
    HashMap connections;
    Connection[] paths;
    
    public Joint(Point p1,double width,double length){
        this.width = width;
        this.length = length;
        this.gPoint = p1;
        this.cars = new ArrayList<Car>();
        
        this.connections = new HashMap();
       
    }
    
   
    
    public void setConnections(Lane a,Lane[] b){
        connections.put(a, b);
    }
    
    
    public void enter(Car c,Lane lane){
        double[] newCoords = coordsFromLane(c,lane);
        c.point.setLocation(newCoords[0],newCoords[1]);
        c.direction = lane.direction;
        this.cars.add(c);
        
    }
    
    protected double[] coordsFromLane(Car c,Lane lane){
        double x = lane.gPoint.getX() -  gPoint.getX() - Math.sin(lane.direction)*c.point.getX();
        double y = lane.gPoint.getY() - gPoint.getY() + Math.cos(lane.direction)*c.point.getX();
        return new double[]{x,y};
    }
    
    public void update(double max,double interval){
        ArrayList<Car> toRemove = new ArrayList<>();
        for(Car c:cars){
            c.updateConnection(max,interval,this);
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
        paths = new Connection[]{new Straight(),new Turn()};
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
    }
    
}

