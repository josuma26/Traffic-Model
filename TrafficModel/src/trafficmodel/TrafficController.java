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
public class TrafficController {
    int[] states;
    double[] times;
    int state;
    long current;
    
    public TrafficController(double[] times){
        states = new int[]{0,1,2,2};
        this.times = times;
        this.state = 0;
        
    }
    
    public void start(){
        current = seconds();
    }
    
    public void control(){
        if (seconds() > current + times[state]){
            state = (state + 1)%4;
            current = seconds();
        }
    }
    
    public int[] getStates(){
        return new int[]{states[state],states[(2 + state)%4]};
    }
    
    private long seconds(){
        return System.nanoTime()/1000000000;
    }
}
