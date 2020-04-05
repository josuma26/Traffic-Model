/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficmodel;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author jsula
 */
public class Intersection extends CitySection{
   
    DirectJoint joint = new DirectJoint(new Point(600,600),200,200);;
    
    public Intersection(double accel,double dist,int x,int y){
        super(accel,dist,x,y);
        
        Lane rightLane = new Lane(3*Math.PI/2,30,100,600,new Point(x + 700,y + 800));
        Lane leftLane = new Lane(3*Math.PI/2,30,100,600,new Point(x + 600,y + 800));
        Lane rightUpLane = new Lane(3*Math.PI/2,30,100,600,new Point(x + 700,y));
        Lane leftUpLane = new Lane(3*Math.PI/2,30,100,600,new Point(x + 600,y));
        Lane sidewaysLane = new Lane(0,30,100,600,new Point(x + 600,y + 600));
        Lane sidewaysLaneUpRight = new Lane(0,30,100,600,new Point(x + 1400,y + 600));
        Lane sidewaysLaneBottom = new Lane(0,30,100,600,new Point(x + 600,y + 700));
        Lane sidewaysLaneBottomRight = new Lane(0,30,100,600,new Point(x + 1400,y + 700));
        
        addLanes(rightLane,leftLane,rightUpLane,leftUpLane,sidewaysLane,sidewaysLaneUpRight,sidewaysLaneBottom,sidewaysLaneBottomRight);
        addJoint(joint);
        addCars(60, 70, 10, 10, 20, new int[]{0,10},new int[]{1,10});
        setLaneNextStep(rightLane, sidewaysLaneBottomRight);
        setLaneNextStep(leftLane, leftUpLane);
        setTrafficLights(new double[]{3,0.5,3,0.5}, new int[]{4,5,6,7}, new int[]{0,1,2,3});
        
        Lane[][] a = {{leftLane}, {leftUpLane}};
        Lane[][] b = {{rightLane},{rightUpLane,sidewaysLaneBottomRight}};
        Lane[][] c = {{sidewaysLane},{sidewaysLaneUpRight}};
        Lane[][] d = {{sidewaysLaneBottom},{sidewaysLaneBottomRight}};
        configureJoint(a,b,c,d);
        
        
        run();
     
        
    }
    
    @Override
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        String t = "Time: " + (seconds() - startTime);
        g2.setFont(new Font("Arial",Font.PLAIN,30));
        g2.drawString(t, x + 1200, y + 200);
        g2.setStroke(new BasicStroke(5));
        super.paint(g);
    }
    
    
    
}
