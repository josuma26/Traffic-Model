/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficcongestion;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author jsula
 */
public class TrafficCongestion {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        testJoint();
        
    }
    
    private static void testJoint(){
        Lane lane = new Lane(10,100,20,1,new Point(200,100));
        //Lane lane2 = new Lane(20,100,20,1,new Point(300,100));
        
        //Joint j = new Joint();
        //lane.setOut(j);
        //j.addLanes(new Lane[]{lane,lane2});
        ArrayList<Car> cars = new ArrayList<>();
        int startX = 150;
        int y = 110;
        int width = 5;
        int length = 20;
        int separation = length + 5;

        for(int i = 0;i<4;i++){
            cars.add(new Car(startX - separation*i,y,length,width));
        }
        
        lane.addCars(cars);
        
        testLane(lane);
        
        //lane.update(1, 5, 10);
        
        //System.out.println();
        

        /*for(int i = 0;i<7;i++){
            lane.update(1, 5, 10);
            System.out.println();
        }*/
        
    }
    public static void testLane(Lane lane){
        lane.toggleGo();
        for(int i = 0;i<7;i++){
            lane.update(1, 5, 10);
            System.out.println();
        }
    }
}