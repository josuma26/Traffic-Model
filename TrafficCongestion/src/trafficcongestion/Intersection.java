/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficcongestion;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import javax.swing.Timer;

/**
 *
 * @author liquelyn
 */
public class Intersection extends Component{
    Lane right,left,up,down;
    public Intersection(){
        up = new Lane(10,350,60,0,new Point(400,0));
        down = new Lane(10,350,60,0,new Point(400,500));
        right = new Lane(20,60,350,0,new Point(0,400));
        left = new Lane(20,60,350,0,new Point(500,400));
        up.toggleGo();
        Car c = new Car(10,10,20,10);
        up.addCar(c);
        
        
        Timer timer = new Timer(40,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                repaint();
            }
        });
        
        timer.start();
    }
    @Override
    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g.create();
        float dash1[] = {10.0f};
        Stroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 10.0f, dash1, 0.0f);
        g2d.setStroke(dashed);
        g2d.draw(new Line2D.Float(0, 430, 350, 430));
        g2d.draw(new Line2D.Float(430, 0, 430, 350));
        g2d.draw(new Line2D.Float(430, 500, 430, 850));
        g2d.draw(new Line2D.Float(500, 430, 850, 430));
        
        up.update(1, 4   , 10);
        Graphics2D g2 = (Graphics2D)g;
        TrafficGraphics.drawLane(g2,up);
        TrafficGraphics.drawLane(g2, down);
        TrafficGraphics.drawLane(g2, right);
        TrafficGraphics.drawLane(g2, left);
        
    }
}
