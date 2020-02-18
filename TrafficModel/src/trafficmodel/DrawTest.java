/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficmodel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author jsula
 */
public class DrawTest extends Component {
    double x = 500,y=500,w=50,speed = 50;
    
    public DrawTest() {
        
    }
    
    
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100, 100);
    }

    
    @Override
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        //g2.rotate(-20);
        g2.setColor(Color.GREEN);
        g2.fill(new Rectangle2D.Double(x,y,w,w + 10));
        
    }
}
