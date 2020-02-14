/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficcongestion;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.util.Scanner;

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
        //testCar(new Car(0,0,10,30));
        //testJoint();
        drawModel(new Intersection());

        
        //testCar(new Car(0,0,10,30));
        testJoint();
        //testLane();
        
    }
    
    public static void drawModel(Component model){
        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                JFrame frame = new JFrame("Traffic Simulation");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 800);
                frame.add(model);
                frame.setVisible(true);
                        
            }
        });
    };
    



    
    
    public static void testCar(Car car){
    Scanner Input = new Scanner(System.in);
    System.out.println("Pleasen input an acceleration");
    car.Acceleration = Input.nextInt();
    System.out.println("Pleasen input a speed");
    car.Speed = Input.nextInt();
    for(int i =0;i<10;i++){
    car.update(1, 30);
    }
      
    }


        
    
    
    private static void testJoint(){
        Lane lane = new Lane(10,100,20,1,new Point(100,200));
        Lane lane2 = new Lane(20,100,20,1,new Point(100,300));
        
        Joint j = new Joint();
        lane.setOut(j);
        j.addLanes(new Lane[]{lane,lane2});
        
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
        
        lane.toggleGo();

        for(int i = 0;i<12;i++){
            lane.update(1, 5, 10);
            System.out.println();
        }
        
    }
    public static void testLane(){
        Lane lane = new Lane(10,100,20,1,new Point(200,100));
        lane.toggleGo();
        
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
        for(int i = 0;i<7;i++){
            lane.update(1, 5, 10);
            System.out.println();
        }
        
    }



}
