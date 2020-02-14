/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficcongestion;

import java.awt.Point;
import java.util.ArrayList;
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
        
        testCar(new Car(0,0,10,30));
        
    }
    
    
    public static void testCar(Car car){
    
    Scanner Input = new Scanner(System.in);
    System.out.println("Pleasen input an acceleration");
    car.Acceleration = Input.nextInt();
    System.out.println("Pleasen input a speed");
    car.Speed = Input.nextInt();
    for(int i =0;i<10;i++){
    car.update(1, 1);
    }
    //simulate somethhing
      
    }
}