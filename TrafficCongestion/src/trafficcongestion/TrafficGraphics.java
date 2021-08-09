/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficcongestion;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author liquelyn
 */
public class TrafficGraphics {
    public static Color[] CAR_COLORS =
        {Color.cyan, Color.magenta, Color.yellow, Color.gray,Color.BLUE,Color.RED,Color.GREEN};
    
    public static void drawLane(Graphics2D g2,Lane l){
        g2.setColor(Color.BLACK);
        g2.draw(new Rectangle2D.Double(l.point.x,l.point.y,l.LANE_WIDTH,l.LANE_LENGTH));
        for(Car c:l.cars){
            TrafficGraphics.drawCar(g2, c,l.point.x,l.point.y);
        }
    }
    
    public static void drawCar(Graphics2D g2,Car c,double dX,double dY){
        g2.setColor(CAR_COLORS[(int)(Math.random()*7)]);
        g2.fill(new Rectangle2D.Double(dX + c.x,dY + c.y,c.width,c.length));
    }
}
