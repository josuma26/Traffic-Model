/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficmodel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 *
 * @author jsula
 */
public class SelfDrivingTest extends Model {
    Lane lane;
    double a = 5;
    double d = 40;
    double dt = 2*40 + 30;
    public SelfDrivingTest(){
        lane = new Lane(0,30,50,1400,new Point2D.Double(1400,700));
        
        int n = 2;
        for(int i = 0;i<n;i++){
            lane.addCar(new Car(10,1000 + (40 + 5)*i,30,40));
        }
        
        lane.toggleGo();
        run();
    }
    
    @Override
    public void update(){
        double k = 2*(dt - 30 - 5)/(Math.pow(30,2));
        lane.updateAuto(a,k, 0.05);
    }
    
    @Override
    public void paint(Graphics g){
        TrafficGraphics.drawLane((Graphics2D) g, lane);
    }
}
