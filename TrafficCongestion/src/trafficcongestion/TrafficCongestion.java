/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficcongestion;

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
        Car c = new Car(200,200,30,10);
        c.SetAcceleration(0);
        c.SetSpeed(1);
        c.setDirection(0);
        for(int i = 0;i<10;i++){
            c.update(1);
        }
    }
    
}
