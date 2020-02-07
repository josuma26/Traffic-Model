/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficcongestion;

/**
 *
 * @author alanwang
 */
import java.util.ArrayList;
public class Lane {
    private int trafficflow = 0;
    private int vehicledensity = 0;
    private int MAX_SPEED = 0;
    private int LANE_LENGTH = 0;
    private int LANE_WIDTH = 0;
    private String DIRECTION = "";
    
    private ArrayList<Car> cars;
    
    public Lane(int speed, int length, int width, String direction){
        MAX_SPEED = speed;
        LANE_LENGTH = length;
        LANE_WIDTH = width;
        DIRECTION = direction;
    }
    public int setTrafficFlow(int carNum, int time){
        trafficflow = (carNum * 3600)/time;
        return trafficflow;
        // trafficflow = vehicle flow (no. vehicles per hour)
        // carNum = no. vehicles passing in t seconds
        // time = time for passing vehicles (s)

    }
    public int setVehicleDensity(int carNum, int length){
        vehicledensity = (carNum * 5280)/length;
        return vehicledensity;
        // k = vehicle density (vehicles per mile)
        // n = no. of vehicles occupying a length l of the road
        // l = the length of road occupied by the vehicles (ft)
    }
    public void addCars(ArrayList<Car> carList){
        cars = carList;
    }
    public void update(){
        for (Car vehicle : cars){
            vehicle.update();
        }
    }
}
