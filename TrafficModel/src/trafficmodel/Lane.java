/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficmodel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 *
 * @author jsula
 */
public class Lane  {
    ArrayList<Car> cars;
    double direction, maxSpeed;
    int length,width;
    boolean go = false,full = false,decelerating = false;
    double overflow = 0,distanceToEnd;
    double acceleration;
    int checkingIndex;
    Joint out;
    int firstIndex = 0,dir = -1;
    int left = 0;
    Point2D gPoint;
    TrafficLight light;
    String name;
    
    double targetSpeed;
    
   public Lane(double direction,double maxSpeed,int width,int length,Point2D p){
       this.direction = direction;
        this.maxSpeed = maxSpeed;
        this.width = width;
        this.length = length;
        this.gPoint = p;
        this.cars = new ArrayList<>();
        light = null;
        this.targetSpeed = maxSpeed;
        this.acceleration= 0;
   }
    
   public void setName(String name){
       this.name = name;
   }
   
    public void setOut(Joint out){
        this.out = out;
    }
    
    public void setTrafficLight(TrafficLight light){
        this.light = light;
    }
    
    public void setCars(ArrayList<Car> cars,int firstIndex){
        this.cars = cars;
        for(Car c:this.cars){
            c.direction = 3*Math.PI/2;
            c.n = firstIndex  - dir*cars.indexOf(c);
        }
        this.firstIndex = firstIndex;
        this.dir = (firstIndex == 0)?-1:1;
        
    }
    
    public void addCar(Car c){
        this.cars.add(c);
        c.direction = 3*Math.PI/2;
        c.n = firstIndex  - dir*cars.indexOf(c);
        
    }
    
    public void toggleGo(){
        go = !go;
    }
    
    public void setGo(boolean go){
        this.go = go;
    }
      
    
    public void update(double a,double d,double interval){
        ArrayList<Car> toRemove= new ArrayList<>();
        if (light != null){
            go = light.getState() == 0;
        }
        
        for(Car c: cars){
            int index = cars.indexOf(c);
            if (index == firstIndex){
                if (go && overflow == 0){
                    c.acceleration = a;
                }
                else if (c.acceleration >= 0){
                    double accel = -Math.pow(c.speed,2)/(2*(distanceToEdge(c)));
                    if (Math.abs(accel) >= a){
                        c.breakCar(accel);
                    }
                }
            }
            else{
                Car inFront = cars.get(index + dir);
                double dist = distance(c,inFront) - inFront.height;
                
                if (dist >= d){
                    c.acceleration = a;
                }
                
                else if (c.acceleration >= 0){
                    if (inFront.acceleration == 0){
                        c.breakCar(-Math.pow(c.speed,2)/(2*(dist -10)));
                    }
                    else {
                        double futureDistance = -Math.pow(inFront.speed,2)/(2*inFront.acceleration) ;
                        double accel = -Math.pow(c.speed,2)/(2*(dist + futureDistance - 10));
                        c.breakCar(accel);
                        
                    }
                }
                        
            }
                
            if (distanceToEdge(c) < 0){
                toRemove.add(c);
                left += 1;
                if (out != null){
                    out.enter(c,this);
                }
            }
            
            if (index == cars.size() - 1){
                distanceToEnd = length - c.point2.getY();
                if(c.point.getY() + c.height > length - 20 && c.speed == 0){
                    full = true;
                }
                else{
                    full = false;
                }
            }
            c.updateNormal(maxSpeed,interval);
            
        }
        cars.removeAll(toRemove);
        
    }
    
    private double distanceToEdge(Car c){
        return c.point.getY() - overflow;
    }
    
    public void updateAuto(double a,double interval){
        ArrayList<Car> toRemove = new ArrayList<>();
        for(int index = 0;index < cars.size();index++){
            Car c = cars.get(index);
            if (index == 0){
                if (c.speed > targetSpeed){
                    c.acceleration = -a;
                }
                else if (c.speed < targetSpeed){
                    c.acceleration = a;
                    targetSpeed = maxSpeed;

                }
                else{
                    c.acceleration = a;
                    targetSpeed = maxSpeed;
                } 
                
            }
            else{
                Car inFront = cars.get(index - 1);
                double d = distance(c,inFront) - c.height - 2*c.height - c.width;
                double t = (inFront.point.getY())/(2*inFront.speed);
                c.acceleration = 2*(d + (1/2)*inFront.acceleration*Math.pow(t,2) + (inFront.speed - c.speed)*t)/(Math.pow(t,2));
                
            }
                        
            
            
            c.updateNormal(maxSpeed, interval);
            
            if (distanceToEdge(c) < 0){
                toRemove.add(c);
                left += 1;
                
            }
            
            if (index == cars.size() - 1){
                distanceToEnd = length - c.point2.getY();
                if(c.point.getY() + c.height > length - 10 && c.speed == 0){
                    full = true;
                }
                else{
                    full = false;
                }
            }
        }
        for(Car c:toRemove){
            if (out != null){
                out.enter(c,this);
             }
        }
        cars.removeAll(toRemove);
    }
    
    
    public int timeToCross(){
        return (int) (length/maxSpeed + cars.size());
    }
    
    public double accelerationFunction(int n,double a){
        return 0;
    }
    
    public double aofn(double k,double a){
        return a*(1 - a*k);
    }
    
    private double distance(Car c1,Car c2){
        return c1.point.distance(c2.point);
    }
    
    public boolean inLane(Point2D point,Point2D reference){
        boolean xIn = 0 <= point.getX() + reference.getX() && point.getX() + reference.getX() <= width;
        boolean yIn = 0 <= point.getY() + reference.getY() && point.getY() + reference.getY() <= length;
        return xIn && yIn;
    }
    
}

class InfiniteLane extends Lane{
    public InfiniteLane(double direction,double maxSpeed,int width,int length,Point2D p){
        super(direction,maxSpeed,width,length,p);
    }
    
    @Override
    public double accelerationFunction(int n,double a){
        return 2*a/(n+1);
    }
    
    
}