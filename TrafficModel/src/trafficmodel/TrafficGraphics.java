/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficmodel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author jsula
 */
public class TrafficGraphics {
    
    public static void drawCar(Graphics2D g2,Car c,double cX,double cY,AffineTransform rotation){
        g2.setColor(Color.GREEN);
        Shape rotated = rotation.createTransformedShape(new Rectangle2D.Double(cX + c.point.getX(), cY + c.point.getY(), c.width, c.height));
        g2.fill(rotated);    
    }
    
    public static void drawCar(Graphics2D g2,Car c,double cX,double cY){
        g2.setColor(Color.GREEN);
        g2.fill(new Rectangle2D.Double(cX + c.point.getX(), cY + c.point.getY(), c.width, c.height));
    }
    
    
    public static void drawLane(Graphics2D g2,Lane l){
        g2.setColor(Color.BLACK);
        AffineTransform r = new AffineTransform();
        double angle = l.direction + Math.PI/2 ;
        r.setToRotation(angle, l.gPoint.getX(), l.gPoint.getY());
        Shape rotated = r.createTransformedShape(new Rectangle2D.Double(l.gPoint.getX(),l.gPoint.getY(),l.width,l.length));
        g2.setStroke(new BasicStroke(5));
        g2.draw(rotated);
        
        if (l.light != null){
            Color[] colors = {Color.GREEN,Color.YELLOW,Color.RED};
            g2.setColor(colors[l.light.getState()]);
            g2.fill(r.createTransformedShape(new Rectangle2D.Double(l.gPoint.getX(),l.gPoint.getY(),l.width,10)));
            
        }
        for (Car c:l.cars){
            TrafficGraphics.drawCar(g2, c,l.gPoint.getX(),l.gPoint.getY(),r);
        }
    }
    
    public static void drawJoint(Graphics2D g2,Joint j){
        g2.setColor(Color.BLACK);
        g2.draw(new Rectangle2D.Double(j.gPoint.x,j.gPoint.y,j.width,j.length));
        
        for (Car c:j.cars){
            AffineTransform r = new AffineTransform();
            double angle = c.direction + Math.PI/2 ;
            r.setToRotation(angle,c.point.getX() + j.gPoint.getX(),c.point.getY() + j.gPoint.getY());
            TrafficGraphics.drawCar(g2, c,j.gPoint.getX(),j.gPoint.getY() ,r);
        }
    }
    
    
    public static void drawSection(Graphics2D g2,CitySection section){
        for (Lane l:section.lanes){
            TrafficGraphics.drawLane(g2, l);
        }
        
        TrafficGraphics.drawJoint(g2,section.joint);
    }
    
    
}
