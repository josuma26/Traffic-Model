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
    public double dTheta(Car c,Joint j){
        return 0;
    }
}

class Straight extends Connection{
    @Override 
    public double dTheta(Car c,Joint j){
        return 0;
    }
}

class Turn extends Connection{
    @Override
    public double dTheta(Car c,Joint j){
        if (Double.isNaN(c.turning)){
            c.turning =  Math.ceil(j.width - c.point.getX());
        }
        return c.speed / (c.turning);
        
    }


            
    
    
}

