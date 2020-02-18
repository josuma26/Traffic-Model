/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficmodel;

/**
 *
 * @author jsula
 */
public class TrafficLight {
    TrafficController controller;
    int index;
    
    public TrafficLight(TrafficController ctrl,int index){
        controller = ctrl;
        this.index = index;
    }
    
    public int getState(){
        return controller.getStates()[index];
    }
    
}
