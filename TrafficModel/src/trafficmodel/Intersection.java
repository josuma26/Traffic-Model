/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficmodel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author jsula
 */
public class Intersection extends Component {
    ArrayList<Lane> lanes = new ArrayList<>();
    /*
    Lane rightLane = new Lane(3*Math.PI/2,30,new Point(600,800),new Point(700,1400)), leftLane = new Lane(3*Math.PI/2,30,new Point(700,800),new Point(800,1400));
    Lane rightUpLane = new Lane(3*Math.PI/2,30,new Point(600,0),new Point(700,600)),leftUpLane = new Lane(3*Math.PI/2,30,new Point(700,0),new Point(800,600));
    Lane sidewaysLane = new Lane(0,30,new Point(0,600),new Point(600,700)),sidewaysLaneUpRight = new Lane(0,30,new Point(800,600),new Point(1400,700));
    */
    InfiniteLane rightLane = new InfiniteLane(3*Math.PI/2,30,100,600,new Point(700,800)), leftLane = new InfiniteLane(3*Math.PI/2,30,100,600,new Point(600,800));
    InfiniteLane rightUpLane = new InfiniteLane(3*Math.PI/2,30,100,600,new Point(700,0)),leftUpLane = new InfiniteLane(3*Math.PI/2,30,100,600,new Point(600,0));
    InfiniteLane sidewaysLane = new InfiniteLane(0,30,100,600,new Point(600,600)),sidewaysLaneUpRight = new InfiniteLane(0,30,100,600,new Point(1400,600));
    InfiniteLane sidewaysLaneBottom = new InfiniteLane(0,30,100,600,new Point(600,700)),sidewaysLaneBottomRight = new InfiniteLane(0,30,100,600,new Point(1400,700));
    //Joint j = new Joint(new Point(600,600),200,200);
    DirectJoint j = new DirectJoint(new Point(600,600),200,200);
    
    
    double a = 5,d = 40;
    long start = 0;
    TrafficController controller = new TrafficController(new double[]{3,0.5,3,0.5});
    TrafficLight light = new TrafficLight(controller,0);
    TrafficLight light2 = new TrafficLight(controller,1);
    
    public Intersection(){
        ArrayList<Car> carsRightLane = new ArrayList<>();
        ArrayList<Car> carsLeftLane = new ArrayList<>();
        ArrayList<Car> sideways = new ArrayList<>();
        ArrayList<Car> sidewaysBottom = new ArrayList<>();
        
        double width = 60;
        double height = 70;
        double startX = 10;
        double startY = 10;
        double separation = 5;
        for(int i = 0;i<20;i++){
            Car c = new Car(startX,startY + i*(height + separation),width,height);
            c.addStep(sidewaysLaneBottomRight);
            Car c2 = new Car(startX,startY + i*(height + separation),width,height);
            c2.addStep(leftUpLane);
            Car c3 = new Car(startX,startY + i*(height + separation),width,height);
            c3.addStep(sidewaysLaneUpRight);
            Car c4 = new Car(startX,startY + i*(height + separation),width,height);
            c4.addStep(sidewaysLaneBottomRight);
            carsRightLane.add(c);
            carsLeftLane.add(c2);
            sideways.add(c3);
            sidewaysBottom.add(c4);
        }
        lanes.add(rightLane);
        lanes.add(leftLane);
        lanes.add(rightUpLane);
        lanes.add(leftUpLane);
        lanes.add(sidewaysLane);
        lanes.add(sidewaysLaneUpRight);
        lanes.add(sidewaysLaneBottom);
        lanes.add(sidewaysLaneBottomRight);
        
        rightLane.setCars(carsRightLane,0);
        leftLane.setCars(carsLeftLane,0);
        sidewaysLane.setCars(sideways, 0);
        sidewaysLaneBottom.setCars(sidewaysBottom,0);
        
        rightLane.setTrafficLight(light);
        leftLane.setTrafficLight(light);
        sidewaysLane.setTrafficLight(light2);
        sidewaysLaneBottom.setTrafficLight(light2);
        
        rightLane.toggleGo();
        leftLane.toggleGo();
        rightUpLane.toggleGo();
        leftUpLane.toggleGo();
        
        sidewaysLaneUpRight.toggleGo();
        sidewaysLaneBottomRight.toggleGo();
        
        
        //sidewaysLane.toggleGo();
        //sidewaysLaneBottom.toggleGo();
        
        rightLane.setOut(j);
        leftLane.setOut(j);
        sidewaysLane.setOut(j);
        sidewaysLaneBottom.setOut(j);
        
        //Lane[][] pairs = {{rightLane,rightUpLane},{leftLane,leftUpLane},{sidewaysLane,sidewaysLaneUpRight},{sidewaysLaneBottom,sidewaysLaneBottomRight}};
        //j.addLanes(pairs);
        
        j.setConnections(leftLane, new Lane[]{leftUpLane});
        j.setConnections(rightLane,new Lane[]{rightUpLane,sidewaysLaneBottomRight});
        j.setConnections(sidewaysLane, new Lane[]{sidewaysLaneUpRight});
        j.setConnections(sidewaysLaneBottom, new Lane[]{sidewaysLaneBottomRight});
        
        controller.start();
        start = seconds();
        Timer timer = new Timer(10,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //controlTraffic();
                
                if (rightLane.left == 20 && !sidewaysLane.go){
                    sidewaysLane.toggleGo();
                    sidewaysLaneBottom.toggleGo();
                }
                repaint();
            }
        });
        timer.start();
        
    }
    
    private void controlTraffic(){
        controller.control();
        boolean go1 = light.getState() == 0;
        boolean go2 = light2.getState() == 0;
        rightLane.setGo(go1);
        leftLane.setGo(go1);
        sidewaysLane.setGo(go2);
        sidewaysLaneBottom.setGo(go2);
    }
    private long seconds(){
        return System.nanoTime()/1000000000;
    }
    
    @Override
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        String t = "Time: " + (seconds() - start);
        g2.setFont(new Font("Arial",Font.PLAIN,30));
        g2.drawString(t, 1200, 200);
        g2.setStroke(new BasicStroke(5));
       
        for (Lane l:lanes){
            //l.update(a,d);
            l.updateAuto(g2, a,0.1);
            TrafficGraphics.drawLane(g2, l);
        }
        
        j.update(30,0.1);
        TrafficGraphics.drawJoint(g2,j);
        
    }
}
