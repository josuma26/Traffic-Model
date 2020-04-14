/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficmodel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author jsula
 */
public class SimpleTraffic extends Component{
    
    Lane selfDriving,normal;
       
    
    double a = 5;
    double d = 50;
    
    double x = 1000;
    double y = 170;
    int laneLength = 700;
    int laneWidth = 50;
    double width = 20;
    double height = 50;
    double separation = 10;
    double normalY = 200;
    double autoY = 300;
    
    public SimpleTraffic(){
        
        selfDriving = new Lane(0,20,laneWidth,laneLength,new Point2D.Double(x ,autoY )){
            @Override
            public double accelerationFunction(int n,double a){
                return 2*a/n;
            }

        };
        normal = new Lane(0,20,laneWidth,laneLength,new Point2D.Double(x ,y )); 
        ArrayList<Car> cars = new ArrayList<>();
        ArrayList<Car> advancedCars = new ArrayList<>();
        
        for (int i = 0;i<10;i++){
            double x =   separation;
            double y = separation + i*(height + separation);
            Car c = new Car(x,y,width,height);
            Car c2 = new Car(x,y,width,height);
            cars.add(c);
            advancedCars.add(c2);
        }
        
        selfDriving.setCars(advancedCars,0);
        normal.setCars(cars,0);
        selfDriving.toggleGo();
        normal.toggleGo();
        
        
        Timer timer = new Timer(100,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
               repaint(); 
            }
        });
        timer.start();
        
    }
    
    @Override
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        g2.setBackground(Color.WHITE);
        Font f = new Font(Font.SANS_SERIF,Font.PLAIN,32);
        g2.setFont(f);
        g2.drawString("Human Drivers:", 350, 150);
        normal.update(a,d);
        TrafficGraphics.drawLane(g2,normal);
        TrafficGraphics.drawLane(g2, selfDriving);
        g2.setColor(Color.BLACK);
        g2.drawString("Self-Driving cars:",350,250);
        selfDriving.updateAuto(g2,a,1);
      
    }
}
