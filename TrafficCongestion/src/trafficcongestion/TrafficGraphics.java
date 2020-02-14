/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficcongestion;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author liquelyn
 */
public class TrafficGraphics {
    
    public static void drawLane(Graphics2D g2 ){
        g2.draw(new Rectangle2D.Double(100,100,100,100));
    }
}
