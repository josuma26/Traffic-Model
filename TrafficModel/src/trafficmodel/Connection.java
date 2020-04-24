/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficmodel;

import java.awt.Point;

/**
 *
 * @author jsula
 */
public class Connection {
    int[] quadrants;
    public double dTheta(Car c,Joint j){
        return 0;
    }
    
    
}

class Straight extends Connection{
    public Straight(){
        this.quadrants = new int[]{1,3};
    }
    @Override 
    public double dTheta(Car c,Joint j){
        return 0;
    }
}

class TurnRight extends Connection{
    
    public TurnRight(){
        this.quadrants = new int[]{};
    }
    
    @Override
    public double dTheta(Car c,Joint j){
        if (Double.isNaN(c.turning)){
            if (c.direction == 3*Math.PI/2){
                c.turning =  Math.ceil(j.width - c.point.getX());
            }
            else if (c.direction == Math.PI/2){
                c.turning = Math.ceil(c.point.getX());
            }
            else if (c.direction == 0){
                c.turning = Math.ceil(j.width - c.point.getY());
            }
            else{
                c.turning = Math.ceil(c.point.getY());
            }
        }
        
        
        return c.speed / (c.turning);
        
    } 
}

class TurnLeft extends Connection{
    public TurnLeft(){
        this.quadrants = new int[]{0,1,2,3};
    }
    @Override
    public double dTheta(Car c,Joint j){
        if (Double.isNaN(c.turning)){
            if (c.direction == 3*Math.PI/2){
                c.turning = Math.ceil(c.point.getX());
            }
            else if (c.direction == Math.PI/2){
                c.turning =  Math.ceil(j.width - c.point.getX());
                
            }
            else if (c.direction == 0){
                c.turning = Math.ceil(c.point.getY());
                
            }
            else{
                c.turning = Math.ceil(j.width - c.point.getY());
            }
        }
        return -c.speed/c.turning;
    }
}

