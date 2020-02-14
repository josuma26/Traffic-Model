/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficcongestion;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 *
 * @author jsula
 */
public class Joint {
    Lane[][] pairs;
    
    public Joint(){
       
    }
    
    public void addLanes(Lane[] ... pairs){
        this.pairs = pairs;
    }
    
   
    public void switchLane(Car c,Lane from){
        Lane destination = null;
        for (Lane[] pair:pairs){
            if (pair[0].equals(from)){
                destination = pair[1];
            }
            else if (pair[1].equals(from)){
                destination = pair[0];
            }
        }
        if (destination != null){
            destination.addCar(c);
        }
    }
}
