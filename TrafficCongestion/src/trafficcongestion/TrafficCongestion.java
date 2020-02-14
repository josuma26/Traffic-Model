/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficcongestion;

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
        Lane lane = new Lane(10,100,20,1);
        ArrayList<Car> cars = new ArrayList<>();
        int startX = 200;
        int y = 200;
        int width = 5;
        int length = 20;
        int separation = length + 5;
        for(int i = 0;i < 4;i++){
            cars.add(new Car(startX + separation*i,y,length,width));
        }
        
        lane.addCars(cars);
        lane.update(1, 5, 10);
        System.out.println();
        lane.toggleGo();
        for(int i = 0;i < 5;i++){
            lane.update(1, 5, 10);
            System.out.println();
        }
        
    }
    
}
