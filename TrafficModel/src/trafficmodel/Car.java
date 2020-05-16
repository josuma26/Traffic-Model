/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficmodel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author jsula
 */
public class Car extends Component{
    Point2D point,point2;
    double width,height,speed,acceleration,direction;
    boolean braking,priority;
    ArrayList<Lane> steps;
    ArrayList<Node> nodes;
    int currentStep = 1;
    Connection connection;
    double t = 0;
    
    Color color = Color.GREEN;
    
    
    int n = -1;
    double turning = Double.NaN;
    
    int[] lookingOutForCoords;
    public Car(double x,double y,double w,double h){
        this.point = new Point2D.Double(x ,y);
        this.point2 = new Point2D.Double(x + w,y + h);
        this.width = w;
        this.height = h;
        
        this.speed = 0;
        this.acceleration = 0;
        
        this.braking = false;
        this.priority = false;
        
        this.steps = new ArrayList<>();
        this.connection = null;
        
    }
   
    
    public void updateNormal(double max,double interval){
        this.speed += interval*this.acceleration;
        if (this.speed > max){
            this.speed = max;
            this.acceleration = 0;
        }
        if (this.braking && this.speed <= 0){
            this.speed = 0;
            this.acceleration = 0;
            this.braking = false;
        }
        this.point.setLocation(this.point.getX() +interval*this.speed*Math.cos(this.direction),this.point.getY() + interval*this.speed*Math.sin(this.direction));
        this.point2.setLocation(this.point2.getX() +interval*this.speed*Math.cos(this.direction),this.point2.getY() + interval*this.speed*Math.sin(this.direction));
        //this.point.setLocation(this.point.getX(),this.point.getY() - interval*this.speed);
    }
    
    public void updateConnectionAuto(double a,double max, double interval, Joint j){
        this.speed += interval*this.acceleration;
        if (this.speed > max){
            this.speed = max;
            this.acceleration = 0;
        }
        
        this.direction += interval*this.connection.dTheta(this,j);
        this.point.setLocation(this.point.getX() +interval*this.speed*Math.cos(this.direction),this.point.getY() + interval*this.speed*Math.sin(this.direction));
        this.point2.setLocation(this.point2.getX() +interval*this.speed*Math.cos(this.direction),this.point2.getY() + interval*this.speed*Math.sin(this.direction));
    }
    
    public void updateConnection(double a,double max,double interval,Joint j){
        this.speed += interval*this.acceleration;
        
        boolean stop = false;
        for(int index:this.connection.quadrants){
            int quadIndex = this.lookingOutForCoords[index];
            ArrayList<Car> inIndex = j.carsInQuadrants.get(quadIndex);
            
            for(int i = inIndex.size() - 1;i >= 0;i--){
                Car other = inIndex.get(i);
                if (willCollide(other) && other.priority){
                    stop = true;
                }
            }
        }
        
        this.priority = !stop;
        
        if (stop || steps.get(currentStep).full){
            this.acceleration = 0;
            this.speed = 0;
        }
        else{
            this.acceleration = a;
        }
        
        if (this.speed > max){
            this.speed = max;
            this.acceleration = 0;
        }
        if (this.braking && this.speed <= 0){
            this.speed = 0;
            this.acceleration = 0;
            this.braking = false;
        }
        this.direction += interval*this.connection.dTheta(this,j);
        this.point.setLocation(this.point.getX() +interval*this.speed*Math.cos(this.direction),this.point.getY() + interval*this.speed*Math.sin(this.direction));
        this.point2.setLocation(this.point2.getX() +interval*this.speed*Math.cos(this.direction),this.point2.getY() + interval*this.speed*Math.sin(this.direction));
    }
    
    public void setPath(Path p){
        this.steps = p.connections;
        this.nodes = p.steps;
    }
    
    public void recalculateRoute(Navigation nav) {
       Node from = this.nodes.get(this.currentStep),to = this.nodes.get(nodes.size() - 1);
       Lane current = steps.get(currentStep-1);
       Path p = nav.getPath(from, to);
       p.connections.add(0,current);
       
       setPath(p);
       currentStep = 1;
        
    }
    
    private boolean willCollide(Car c){
        double x = this.point.getX(),y = this.point.getY();
        double xSpeed = this.speed*Math.cos(this.direction),ySpeed = this.speed*Math.sin(this.direction);
        
        double tX = (x - c.point.getX())/(c.speed*Math.cos(c.direction) - xSpeed);
        double tY = (y - c.point.getY())/(c.speed*Math.sin(c.direction) - ySpeed);
        
        //return (tX > 0 || tY > 0) && c.speed != 0;
        double angleDifference = c.direction - this.direction;
        return (Math.cos(angleDifference) < 0 && c.speed != 0);
        //return c.speed*Math.cos(angleDifference) <= 0 && c.speed != 0 && !c.equals(this);
    }
    
    
    public void breakCar(double acceleration){
        this.braking = true;
        this.acceleration = acceleration;
    }
    
    
    public void setSteps(ArrayList<Lane> steps){
        this.steps = steps;
    }
    
    
    public void addStep(Lane s){
        this.steps.add(s);
    }
    public void setConenction(Connection c){
        this.connection = c;
        this.t = 0;
    }
    
}
